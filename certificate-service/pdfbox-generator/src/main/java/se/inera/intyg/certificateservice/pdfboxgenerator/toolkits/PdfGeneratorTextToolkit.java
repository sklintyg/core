package se.inera.intyg.certificateservice.pdfboxgenerator.toolkits;

import static se.inera.intyg.certificateservice.pdfboxgenerator.util.PdfConstants.DIGITALLY_SIGNED_TEXT;
import static se.inera.intyg.certificateservice.pdfboxgenerator.util.PdfConstants.SIGNATURE_DATE_FIELD_ID;

import java.awt.Color;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName;
import org.apache.pdfbox.pdmodel.graphics.state.PDExtendedGraphicsState;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.util.Matrix;

public class PdfGeneratorTextToolkit {

  public void addText(PDDocument pdf, String text, int fontSize, Matrix matrix,
      Color strokingColor)
      throws IOException {
    addText(pdf, text, fontSize, matrix, strokingColor, null, null, false);
  }

  public void addText(
      PDDocument pdf,
      String text,
      int fontSize,
      Matrix matrix,
      Color strokingColor,
      Float offsetX,
      Float offsetY,
      boolean isBold
  )
      throws IOException {
    final var contentStream = new PDPageContentStream(pdf, pdf.getPage(0),
        AppendMode.APPEND, true, true);

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
    contentStream.showText(text);
    contentStream.endText();
    contentStream.close();
  }

  public void addWatermark(PDDocument document, String text) throws IOException {
    for (PDPage page : document.getPages()) {

      final var contentStream = new PDPageContentStream(document, page, AppendMode.APPEND,
          true, true);

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
      contentStream.showText(text);
      contentStream.endText();
      contentStream.close();
    }
  }

  public void addDigitalSignatureText(PDDocument pdDocument, PDAcroForm acroForm)
      throws IOException {
    addText(
        pdDocument,
        DIGITALLY_SIGNED_TEXT,
        8,
        null,
        Color.gray,
        getSignatureOffsetX(acroForm),
        getSignatureOffsetY(acroForm),
        true
    );
  }

  private float getSignatureOffsetY(PDAcroForm acroForm) {
    final var signedDate = acroForm.getField(SIGNATURE_DATE_FIELD_ID);
    final var rectangle = signedDate.getWidgets().get(0).getRectangle();
    return rectangle.getLowerLeftY();
  }

  private float getSignatureOffsetX(PDAcroForm acroForm) {
    final var signedDate = acroForm.getField(SIGNATURE_DATE_FIELD_ID);
    final var rectangle = signedDate.getWidgets().get(0).getRectangle();
    return rectangle.getUpperRightX();
  }
}
