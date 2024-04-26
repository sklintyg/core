package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.text;

import java.awt.Color;
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
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName;
import org.apache.pdfbox.pdmodel.graphics.state.PDExtendedGraphicsState;
import org.apache.pdfbox.util.Matrix;
import org.springframework.stereotype.Component;

@Component
public class PdfTextGenerator {

  private void addText(PDDocument pdf, String text, int fontSize, Matrix matrix,
      Color strokingColor,
      int mcid, PDStructureElement section)
      throws IOException {
    addText(pdf, text, fontSize, matrix, strokingColor, null, null, false, mcid, section);
  }

  private void addText(
      PDDocument pdf,
      String text,
      int fontSize,
      Matrix matrix,
      Color strokingColor,
      Float offsetX,
      Float offsetY,
      boolean isBold,
      int mcid,
      PDStructureElement section
  ) throws IOException {
    final var page = pdf.getPage(0);
    final var contentStream = createContentStream(pdf, page);

    if (matrix != null) {
      contentStream.transform(matrix);
    }
    contentStream.beginText();

    if (offsetX != null && offsetY != null) {
      contentStream.newLineAtOffset(offsetX, offsetY);
    }

    contentStream.setNonStrokingColor(strokingColor);
    contentStream.setFont(new PDType1Font(isBold
        ? FontName.HELVETICA
        : FontName.HELVETICA_BOLD
    ), fontSize);
    final var dictionary = beginMarkedContent(contentStream, COSName.P, mcid);
    contentStream.showText(text);
    contentStream.endMarkedContent();
    addContentToCurrentSection(page, dictionary, section, COSName.P, StandardStructureTypes.P,
        text);
    contentStream.endText();
    contentStream.close();
  }

  public void addTopWatermark(PDDocument document, String text, int fontSize, int mcid)
      throws IOException {
    addText(
        document,
        text,
        fontSize,
        Matrix.getTranslateInstance(40, 665),
        Color.gray,
        mcid,
        createNewDivOnTop(document)
    );
  }

  public void addMarginText(PDDocument document, String text, int mcid) throws IOException {
    addText(
        document,
        text,
        8,
        Matrix.getRotateInstance(Math.PI / 2, 600, 25),
        Color.black,
        30F,
        30F,
        false,
        mcid,
        getLastSection(document)
    );
  }

  public void addDigitalSignatureText(PDDocument pdDocument, String text, Float xPosition,
      Float yPosition, int mcid, int index)
      throws IOException {
    addText(
        pdDocument,
        text,
        8,
        null,
        Color.gray,
        xPosition,
        yPosition,
        true,
        mcid,
        getDivInQuestionSection(pdDocument, index)
    );
  }

  public void addWatermark(PDDocument document, String text, int mcid) throws IOException {
    for (PDPage page : document.getPages()) {
      final var section = createNewDivOnTop(document);
      final var contentStream = createContentStream(document, page);

      final var fontHeight = 105;
      final var font = new PDType1Font(FontName.HELVETICA);

      final var width = page.getMediaBox().getWidth();
      final var height = page.getMediaBox().getHeight();

      final var stringWidth = font.getStringWidth(text) / 1000 * fontHeight;
      final var diagonalLength = (float) Math.sqrt(width * width + height * height);
      final var angle = (float) Math.atan2(height, width);
      final var x = (diagonalLength - stringWidth) / 2;
      final var y = -fontHeight / 4;

      contentStream.transform(Matrix.getRotateInstance(angle, 0, 0));
      contentStream.setFont(font, fontHeight);

      final var gs = new PDExtendedGraphicsState();
      gs.setNonStrokingAlphaConstant(0.5f);
      gs.setStrokingAlphaConstant(0.5f);
      contentStream.setGraphicsStateParameters(gs);
      contentStream.setNonStrokingColor(Color.gray);

      contentStream.beginText();
      contentStream.newLineAtOffset(x, y);
      final var markedContentDictionary = beginMarkedContent(contentStream, COSName.P, mcid);
      contentStream.showText(text);
      contentStream.endMarkedContent();
      contentStream.endText();
      addContentToCurrentSection(page, markedContentDictionary, section, COSName.P,
          StandardStructureTypes.P, "Detta är ett " + text.toLowerCase());

      contentStream.close();
    }
  }

  private static PDStructureElement createNewDivOnTop(PDDocument pdf) {
    final var structuredTree = pdf.getDocumentCatalog().getStructureTreeRoot();

    final var pageTag = getPageTag(structuredTree);

    final var newDiv = new PDStructureElement(StandardStructureTypes.DIV, pageTag);

    final var pageTagKids = pageTag.getKids();
    pageTagKids.add(0, newDiv);
    pageTag.setKids(pageTagKids);

    return newDiv;
  }

  private static PDStructureElement getLastSection(PDDocument pdf) {
    final var structuredTree = pdf.getDocumentCatalog().getStructureTreeRoot();

    final var pageTag = getPageTag(structuredTree);

    if (pageTag.getKids().isEmpty()) {
      return pageTag;
    }

    return (PDStructureElement) pageTag.getKids().get(pageTag.getKids().size() - 1);
  }

  private static PDStructureElement getDivInQuestionSection(PDDocument pdf, int index) {
    final var structuredTree = pdf.getDocumentCatalog().getStructureTreeRoot();

    final var pageTag = getPageTag(structuredTree);

    if (pageTag.getKids().isEmpty()) {
      return pageTag;
    }

    final var divWithMostKids = pageTag.getKids().stream()
        .map(PDStructureElement.class::cast)
        .map(PDStructureNode::getKids)
        .max(Comparator.comparing(List::size));

    if (divWithMostKids.isEmpty() || divWithMostKids.get().size() - 1 < index) {
      throw new IllegalStateException("Doesnt exist div to place signed tag in");
    }

    return (PDStructureElement) divWithMostKids.get().get(index);
  }

  private static PDStructureElement getPageTag(PDStructureTreeRoot structuredTree) {
    final var documentTag = getFirstChildFromStructuredElement(
        structuredTree.getKids(),
        "Pdf doesnt have expected root element for tags"
    );

    return getFirstChildFromStructuredElement(
        documentTag.getKids(),
        "Pdf doesnt have expected section element for tags"
    );
  }

  private static PDStructureElement getFirstChildFromStructuredElement(List<Object> kids,
      String s) {
    if (kids.isEmpty()) {
      throw new IllegalStateException(s);
    }

    return (PDStructureElement) kids.get(0);
  }

  private PDPageContentStream createContentStream(PDDocument document, PDPage currentPage)
      throws IOException {
    return new PDPageContentStream(document, currentPage, AppendMode.APPEND, true, true);
  }

  private COSDictionary beginMarkedContent(PDPageContentStream contentStream, COSName name,
      int mcid)
      throws IOException {
    final var currentMarkedContentDictionary = new COSDictionary();
    currentMarkedContentDictionary.setName("Tag", name.getName());
    currentMarkedContentDictionary.setInt(COSName.MCID, mcid);
    contentStream.beginMarkedContent(name, PDPropertyList.create(currentMarkedContentDictionary));
    return currentMarkedContentDictionary;
  }

  private void addContentToCurrentSection(PDPage page, COSDictionary markedContentDictionary,
      PDStructureElement currentSection, COSName name, String type, String text) {

    final var newContent = new PDStructureElement(type, currentSection);
    newContent.setActualText(text);
    newContent.setPage(page);

    final var markedContent = new PDMarkedContent(name, markedContentDictionary);
    markedContentDictionary.setItem(COSName.P, newContent.getCOSObject());

    newContent.appendKid(markedContent);
    currentSection.appendKid(newContent);
  }
}
