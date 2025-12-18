package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.text;

import static se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfConstants.TEXT_FIELD_LINE_HEIGHT;
import static se.inera.intyg.certificateservice.pdfboxgenerator.pdf.text.PdfAccessibilityUtil.addContentToCurrentSection;
import static se.inera.intyg.certificateservice.pdfboxgenerator.pdf.text.PdfAccessibilityUtil.beginMarkedContent;
import static se.inera.intyg.certificateservice.pdfboxgenerator.pdf.text.PdfAccessibilityUtil.createContentStream;
import static se.inera.intyg.certificateservice.pdfboxgenerator.pdf.text.PdfAccessibilityUtil.createNewDivOnPage;
import static se.inera.intyg.certificateservice.pdfboxgenerator.pdf.text.PdfAccessibilityUtil.getDivInQuestionSection;
import static se.inera.intyg.certificateservice.pdfboxgenerator.pdf.text.PdfAccessibilityUtil.getFirstDiv;
import static se.inera.intyg.certificateservice.pdfboxgenerator.pdf.text.PdfAccessibilityUtil.getLastDivOfPage;

import java.awt.Color;
import java.io.IOException;
import java.util.List;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.documentinterchange.logicalstructure.PDStructureElement;
import org.apache.pdfbox.pdmodel.documentinterchange.taggedpdf.StandardStructureTypes;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName;
import org.apache.pdfbox.pdmodel.graphics.state.PDExtendedGraphicsState;
import org.apache.pdfbox.util.Matrix;
import org.springframework.stereotype.Component;

@Component
public class PdfTextGenerator {

  // TODO: Refactor this method overload approach, maybe use a builder pattern instead?
  private void addText(PDDocument pdf, String text, int fontSize, Matrix matrix,
      Color strokingColor,
      int mcid, PDStructureElement section)
      throws IOException {
    addText(pdf, text, fontSize, matrix, strokingColor, null, null, false, mcid, section, 0, text,
        false);
  }

  public void addText(PDDocument pdf, String text, float fontSize, Float offsetX, Float offsetY,
      int mcid, PDStructureElement section, int pageIndex, String actualText)
      throws IOException {
    addText(pdf, text, fontSize, null, Color.black, offsetX, offsetY, false, mcid, section,
        pageIndex, actualText, false);
  }

  public void addText(PDDocument pdf, String text, float fontSize, Float offsetX, Float offsetY,
      int mcid, PDStructureElement section, int pageIndex, String actualText, boolean prependTag)
      throws IOException {
    addText(pdf, text, fontSize, null, Color.black, offsetX, offsetY, false, mcid, section,
        pageIndex, actualText, prependTag);
  }

  private void addText(
      PDDocument pdf,
      String text,
      float fontSize,
      Matrix matrix,
      Color strokingColor,
      Float offsetX,
      Float offsetY,
      boolean isBold,
      int mcid,
      PDStructureElement section,
      int pageIndex,
      boolean prependTag
  ) throws IOException {
    addText(
        pdf,
        text,
        fontSize,
        matrix,
        strokingColor,
        offsetX,
        offsetY,
        isBold,
        mcid,
        section,
        pageIndex,
        text,
        prependTag
    );
  }

  private void addText(
      PDDocument pdf,
      String text,
      float fontSize,
      Matrix matrix,
      Color strokingColor,
      Float offsetX,
      Float offsetY,
      boolean isBold,
      int mcid,
      PDStructureElement section,
      int pageIndex,
      String actualText,
      boolean prependTag
  ) throws IOException {
    final var page = pdf.getPage(pageIndex);

    try (final var contentStream = createContentStream(pdf, page)) {

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
      if (section != null) {
        addContentToCurrentSection(page, dictionary, section, COSName.P, StandardStructureTypes.P,
            actualText, prependTag);
      }
      contentStream.endText();
    }
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
        addInExistingTopTag ? getFirstDiv(document) : createNewDivOnPage(document, 0, 0)
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
        pageIndex,
        false
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
        pageIndex,
        false
    );
  }

  public void addWatermark(PDDocument document, String text, int mcid) throws IOException {
    int pageIndex = 0;
    for (PDPage page : document.getPages()) {
      final var section = createNewDivOnPage(document, 0, pageIndex);
      try (final var contentStream = createContentStream(document, page)) {

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
      }
      pageIndex++;
    }
  }

  public void addTextLines(PDDocument document, List<String> lines, int fontSize,
      PDFont font, Float xPosition, Float yPosition, int mcid, int pageIndex)
      throws IOException {

    final var page = document.getPage(pageIndex);
    try (final var contentStream = createContentStream(document, page)) {

      contentStream.beginText();
      if (xPosition != null && yPosition != null) {
        contentStream.newLineAtOffset(xPosition, yPosition);
      }
      contentStream.setFont(font, fontSize);

      final var markedContentDictionary = beginMarkedContent(contentStream, COSName.P, mcid);
      final var section = createNewDivOnPage(document, 1, pageIndex);

      addContentToCurrentSection(page, markedContentDictionary, section, COSName.P,
          StandardStructureTypes.P, String.join(" ", lines));

      float leading = TEXT_FIELD_LINE_HEIGHT * fontSize;
      for (String line : lines) {
        contentStream.showText(line);
        contentStream.newLineAtOffset(0, -leading);
      }

      contentStream.endMarkedContent();
      contentStream.endText();
    }
  }
}