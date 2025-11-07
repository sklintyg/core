package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.text;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.documentinterchange.logicalstructure.PDStructureElement;
import org.apache.pdfbox.pdmodel.documentinterchange.logicalstructure.PDStructureNode;
import org.apache.pdfbox.pdmodel.documentinterchange.logicalstructure.PDStructureTreeRoot;
import org.apache.pdfbox.pdmodel.documentinterchange.markedcontent.PDMarkedContent;
import org.apache.pdfbox.pdmodel.documentinterchange.markedcontent.PDPropertyList;
import org.apache.pdfbox.pdmodel.documentinterchange.taggedpdf.StandardStructureTypes;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.TemplatePdfSpecification;

public class PdfAccessibilityUtil {

  private PdfAccessibilityUtil() {
    throw new IllegalStateException("Utility class");
  }

  public static PDStructureElement createNewOverflowPageTag(PDDocument pdf, PDPage page,
      TemplatePdfSpecification templatePdfSpecification) {
    final var structuredTree = pdf.getDocumentCatalog().getStructureTreeRoot();
    final var documentTag = getFirstChildFromStructuredElement(
        structuredTree.getKids(),
        "Pdf doesnt have expected root element for tags"
    );

    final var pageContainer = createNewContainer(documentTag, "Page", null);

    final var watermarkContainer = createNewContainer(pageContainer, StandardStructureTypes.DIV, 0);
    templatePdfSpecification.untaggedWatermarks()
        .forEach(watermark -> addContentToSection(page, watermarkContainer, watermark, COSName.P));

    final var section = createNewContainer(pageContainer, StandardStructureTypes.SECT, 0);
    createNewContainer(section, StandardStructureTypes.DIV, 0); //For page number

    final var patientIdDiv = createNewContainer(section, StandardStructureTypes.DIV, 1);
    addContentToSection(page, patientIdDiv, "Personnummer", COSName.P);

    final var h2Div = createNewContainer(section, StandardStructureTypes.DIV, 2);
    addContentToSection(page, h2Div, "Fortsättningsblad", COSName.H, "H2");

    return pageContainer;
  }

  private static void addContentToSection(PDPage page, PDStructureElement section, String text,
      COSName name, String nameAsText) {
    addContentToCurrentSection(
        page,
        null,
        section,
        name,
        nameAsText,
        text
    );
  }

  private static void addContentToSection(PDPage page, PDStructureElement section, String text,
      COSName name) {
    addContentToSection(page, section, text, name, name.getName());
  }

  public static PDStructureElement createNewDivOnPage(PDDocument pdf, int index, int pageIndex) {
    final var structuredTree = pdf.getDocumentCatalog().getStructureTreeRoot();
    final var pageTag = getPageTag(structuredTree, pageIndex);

    return createNewContainer(pageTag, StandardStructureTypes.DIV, index);
  }

  public static PDStructureElement getFirstDiv(PDDocument pdf) {
    final var structuredTree = pdf.getDocumentCatalog().getStructureTreeRoot();

    final var pageTag = getPageTag(structuredTree, 0);

    if (pageTag.getKids().isEmpty()) {
      return pageTag;
    }

    return (PDStructureElement) pageTag.getKids().getFirst();
  }

  public static PDStructureElement getLastDivOfPage(PDDocument pdf, int pageIndex) {
    final var structuredTree = pdf.getDocumentCatalog().getStructureTreeRoot();

    final var pageTag = getPageTag(structuredTree, pageIndex);

    if (pageTag.getKids().isEmpty()) {
      return pageTag;
    }

    return (PDStructureElement) pageTag.getKids().getLast();
  }

  public static PDStructureElement getDivInQuestionSection(PDDocument pdf, int index,
      int pageIndex) {
    final var structuredTree = pdf.getDocumentCatalog().getStructureTreeRoot();

    final var pageTag = getPageTag(structuredTree, pageIndex);

    if (pageTag.getKids().isEmpty()) {
      return pageTag;
    }

    final var containerWithMostKids = pageTag.getKids().stream()
        .map(PDStructureElement.class::cast)
        .map(PDStructureNode::getKids)
        .max(Comparator.comparing(List::size));

    if (containerWithMostKids.isEmpty() || containerWithMostKids.get().size() - 1 < index) {
      throw new IllegalStateException("Does not exist div to place tag in");
    }

    return (PDStructureElement) containerWithMostKids.get().get(index);
  }

  public static PDStructureElement getFirstChildFromStructuredElement(List<Object> kids,
      String s) {
    if (kids.isEmpty()) {
      throw new IllegalStateException(s);
    }

    return (PDStructureElement) kids.getFirst();
  }

  public static PDStructureElement getChildFromStructuredElement(PDStructureElement element,
      int index) {
    final var kids = element.getKids();
    if (kids.isEmpty()) {
      throw new IllegalStateException("Pdf doesnt have expected div/section element");
    }

    if (index >= kids.size() - 1) {
      return (PDStructureElement) kids.getLast();
    }

    return (PDStructureElement) kids.get(index);
  }

  // --- MARKED CONTENT HELPERS ---

  public static PDPageContentStream createContentStream(PDDocument document, PDPage currentPage)
      throws IOException {
    return new PDPageContentStream(document, currentPage, AppendMode.APPEND, true, true);
  }

  public static COSDictionary beginMarkedContent(PDPageContentStream contentStream, COSName name,
      int mcid)
      throws IOException {
    final var currentMarkedContentDictionary = new COSDictionary();
    currentMarkedContentDictionary.setName("Tag" + System.currentTimeMillis(), name.getName());
    currentMarkedContentDictionary.setInt(COSName.MCID, mcid);
    contentStream.beginMarkedContent(name, PDPropertyList.create(currentMarkedContentDictionary));
    return currentMarkedContentDictionary;
  }

  public static void addContentToCurrentSection(PDPage page, COSDictionary markedContentDictionary,
      PDStructureElement currentSection, COSName name, String type, String text, boolean prepend) {
    final var newContent = new PDStructureElement(type, currentSection);
    newContent.setActualText(text);
    newContent.setPage(page);

    if (markedContentDictionary != null) {
      final var markedContent = new PDMarkedContent(name, markedContentDictionary);
      newContent.appendKid(markedContent);
    }

    if (prepend) {
      final var kids = currentSection.getKids();
      kids.addFirst(newContent);
      currentSection.setKids(kids);
    } else {
      currentSection.appendKid(newContent);
    }
  }

  public static void addContentToCurrentSection(PDPage page, COSDictionary markedContentDictionary,
      PDStructureElement currentSection, COSName name, String type, String text) {
    addContentToCurrentSection(page, markedContentDictionary, currentSection, name, type, text,
        false);
  }

  // --- Private helper functions --- //

  private static PDStructureElement getPageTag(PDStructureTreeRoot structuredTree, int pageIndex) {
    final var documentTag = getFirstChildFromStructuredElement(
        structuredTree.getKids(),
        "Pdf doesnt have expected root element for tags"
    );

    return getChildFromStructuredElement(
        documentTag,
        pageIndex
    );
  }


  private static PDStructureElement createNewContainer(PDStructureElement pdStructureElement,
      String type, Integer index) {
    final var newDiv = new PDStructureElement(type, pdStructureElement);

    final var pageTagKids = pdStructureElement.getKids();

    if (index == null) {
      pageTagKids.addLast(newDiv);
    } else {
      pageTagKids.add(index, newDiv);
    }
    pdStructureElement.setKids(pageTagKids);

    return newDiv;
  }

}
