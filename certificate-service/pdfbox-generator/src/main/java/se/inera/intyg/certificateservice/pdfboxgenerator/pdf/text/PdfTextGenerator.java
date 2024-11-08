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
import org.apache.pdfbox.pdmodel.font.PDFont;
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
    addText(pdf, text, fontSize, matrix, strokingColor, null, null, false, mcid, section, 0);
  }

  public void addText(
      PDDocument pdf,
      String text,
      int fontSize,
      Matrix matrix,
      Color strokingColor,
      Float offsetX,
      Float offsetY,
      boolean isBold,
      int mcid,
      PDStructureElement section,
      int pageIndex
  ) throws IOException {
    final var page = pdf.getPage(pageIndex);
    final var contentStream = createContentStream(pdf, page);

    if (matrix != null) {
      contentStream.transform(matrix);
    }
    contentStream.beginText();

    if (offsetX != null && offsetY != null) {
      contentStream.newLineAtOffset(offsetX, offsetY);
    }

    contentStream.setNonStrokingColor(strokingColor);
    contentStream.setFont(new PDType1Font(
        isBold
            ? FontName.HELVETICA_BOLD
            : FontName.HELVETICA
    ), fontSize);
    final var dictionary = beginMarkedContent(contentStream, COSName.P, mcid);
    contentStream.showText(text);
    contentStream.endMarkedContent();
    addContentToCurrentSection(page, dictionary, section, COSName.P, StandardStructureTypes.P,
        text);
    contentStream.endText();
    contentStream.close();
  }

  public void addTopWatermark(PDDocument document, String text, Matrix matrix, int fontSize,
      int mcid, boolean addInExistingTopTag)
      throws IOException {
    addText(
        document,
        text,
        fontSize,
        matrix,
        Color.gray,
        mcid,
        addInExistingTopTag ? getFirstDiv(document) : createNewDivOnTop(document)
    );
  }

  public void addMarginText(PDDocument document, String text, int mcid, int pageIndex)
      throws IOException {
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
        getLastDivOfPage(document, pageIndex),
        pageIndex
    );
  }

  public void addDigitalSignatureText(PDDocument pdDocument, String text, Float xPosition,
      Float yPosition, int mcid, int index, int pageIndex)
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
        getDivInQuestionSection(pdDocument, index, pageIndex),
        pageIndex
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

  public static PDStructureElement createNewDivOnTop(PDDocument pdf) {
    final var structuredTree = pdf.getDocumentCatalog().getStructureTreeRoot();

    final var pageTag = getPageTag(structuredTree, 0);

    final var newDiv = new PDStructureElement(StandardStructureTypes.DIV, pageTag);

    final var pageTagKids = pageTag.getKids();
    pageTagKids.add(0, newDiv);
    pageTag.setKids(pageTagKids);

    return newDiv;
  }

  public static PDStructureElement clonePage(PDDocument pdf, int pageIndex) {
    final var structuredTree = pdf.getDocumentCatalog().getStructureTreeRoot();
    // TODO: must create a deep clone of page

    final var pageTag = getPageTag(structuredTree, pageIndex);

    var documentTree = getFirstChildFromStructuredElement(structuredTree.getKids(),
        "Pdf doesnt have expected root element for tags");
    final var newDiv = new PDStructureElement(StandardStructureTypes.PART, documentTree);

    newDiv.setTitle("Page " + (pageIndex + 1));
    final var sect = new PDStructureElement(StandardStructureTypes.SECT, newDiv);
    sect.appendKid(new PDStructureElement(StandardStructureTypes.DIV, sect));
    newDiv.appendKid(sect);

    final var pageTagKids = pageTag.getKids();
    pageTagKids.add(0, newDiv);
    pageTag.setKids(pageTagKids);

    return newDiv;
  }

//  public static COSDictionary deepCloneCOSDictionary(COSDictionary original) {
//    // Create a new COSDictionary for the clone
//    COSDictionary clone = new COSDictionary();
//
//    // Iterate through all the keys in the original dictionary
//    for (COSName key : original.keySet()) {
//      // Get the value associated with the key
//      Object value = original.getCOSObject(key);
//
//      // If the value is an object that can be cloned, clone it.
//      if (value instanceof COSDictionary) {
//        // Recursively clone a nested COSDictionary
//        COSDictionary nestedDict = (COSDictionary) value;
//        clone.setItem(key, deepCloneCOSDictionary(nestedDict));
//      } else if (value instanceof COSArray) {
//        // Clone COSArray elements if they are also complex types
//        COSArray array = (COSArray) value;
//        COSArray clonedArray = new COSArray();
//        for (int i = 0; i < array.size(); i++) {
//          Object arrayElement = array.get(i);
//          // You need to deep clone each element inside the array if it's a complex object
//          if (arrayElement instanceof COSObjectable) {
//            clonedArray.add(((COSObjectable) arrayElement).getCOSObject());
//          } else {
//            clonedArray.add(arrayElement);
//          }
//        }
//        clone.setItem(key, clonedArray);
//      } else if (value instanceof COSObjectable) {
//        // Clone COSObjectable (e.g., COSName, COSInteger, COSFloat, etc.)
//        clone.setItem(key, ((COSObjectable) value).getCOSObject());
//      } else {
//        // Directly copy primitive values (COSString, COSInteger, COSFloat, etc.)
//        clone.setItem(key, value);
//      }
//    }
//
//    return clone;
//  }


  private static PDStructureElement getFirstDiv(PDDocument pdf) {
    final var structuredTree = pdf.getDocumentCatalog().getStructureTreeRoot();

    final var pageTag = getPageTag(structuredTree, 0);

    if (pageTag.getKids().isEmpty()) {
      return pageTag;
    }

    return (PDStructureElement) pageTag.getKids().get(0);
  }

  private static PDStructureElement getLastDivOfPage(PDDocument pdf, int pageIndex) {
    final var structuredTree = pdf.getDocumentCatalog().getStructureTreeRoot();

    final var pageTag = getPageTag(structuredTree, pageIndex);

    if (pageTag.getKids().isEmpty()) {
      return pageTag;
    }

    return (PDStructureElement) pageTag.getKids().get(pageTag.getKids().size() - 1);
  }

  private static PDStructureElement getDivInQuestionSection(PDDocument pdf, int index,
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
      throw new IllegalStateException("Doesnt exist div to place signed tag in");
    }

    return (PDStructureElement) containerWithMostKids.get().get(index);
  }

  private static PDStructureElement getPageTag(PDStructureTreeRoot structuredTree, int pageIndex) {
    final var documentTag = getFirstChildFromStructuredElement(
        structuredTree.getKids(),
        "Pdf doesnt have expected root element for tags"
    );

    return getChildFromStructuredElement(
        documentTag.getKids(),
        pageIndex
    );
  }

  private static PDStructureElement getFirstChildFromStructuredElement(List<Object> kids,
      String s) {
    if (kids.isEmpty()) {
      throw new IllegalStateException(s);
    }

    return (PDStructureElement) kids.get(0);
  }

  private static PDStructureElement getChildFromStructuredElement(List<Object> kids,
      int index) {
    if (kids.isEmpty()) {
      throw new IllegalStateException("Pdf doesnt have expected div/section element");
    }

    if (index >= kids.size() - 1) {
      return (PDStructureElement) kids.getLast();
    }

    return (PDStructureElement) kids.get(index);
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

  public void addTextLines(PDDocument document, List<String> lines, int fontSize,
      PDFont font, Float xPosition, Float yPosition,
      int mcid, int originalPageIndex, int pageIndex)
      throws IOException {

    final var page = document.getPage(pageIndex);
    final var contentStream = createContentStream(document, page);

    contentStream.setFont(font, fontSize);

    //TODO: need correct mcid and section
//    var section = PdfTextGenerator.clonePage(document, originalPageIndex);
//    final var dictionary = beginMarkedContent(contentStream, COSName.P, mcid);

    contentStream.beginText();
    if (xPosition != null && yPosition != null) {
      contentStream.newLineAtOffset(xPosition, yPosition);
    }

    float leading = 1.5f * fontSize;
    for (String line : lines) {
      contentStream.showText(line);
      contentStream.newLineAtOffset(0, -leading);
    }
//    contentStream.endMarkedContent();
//    addContentToCurrentSection(page, dictionary, section, COSName.P, StandardStructureTypes.P,
//        String.join(" ", lines));
    contentStream.endText();
    contentStream.close();
  }

}
