package se.inera.intyg.certificateservice.pdfboxgenerator;

import static se.inera.intyg.certificateservice.pdfboxgenerator.PdfConstants.WATERMARK_DRAFT;

import java.awt.Color;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName;
import org.apache.pdfbox.pdmodel.graphics.state.PDExtendedGraphicsState;
import org.apache.pdfbox.util.Matrix;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;

public class PdfGeneratorTextToolkit {

  private PdfGeneratorTextToolkit() {
    throw new IllegalStateException("Utility class!");
  }

  public static void addText(PDDocument pdf, String text, int fontSize, Matrix matrix,
      Color strokingColor)
      throws IOException {
    addText(pdf, text, fontSize, matrix, strokingColor, null, null, false);
  }

  public static void addText(
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

  public static void addSentText(PDDocument document, Certificate certificate) throws IOException {
    PdfGeneratorTextToolkit.addText(
        document,
        "Intyget har skickats digitalt till %s".formatted(certificate.sent().recipient().name()),
        22,
        Matrix.getTranslateInstance(40, 665),
        Color.gray
    );
  }

  public static void addSentVisibilityText(PDDocument document)
      throws IOException {
    PdfGeneratorTextToolkit.addText(
        document,
        "Du kan se intyget genom att logga in på 1177.se",
        16,
        Matrix.getTranslateInstance(40, 645),
        Color.gray
    );
  }

  public static void addDraftWatermark(PDDocument document) throws IOException {
    for (PDPage page : document.getPages()) {

      final var contentStream = new PDPageContentStream(document, page, AppendMode.APPEND,
          true, true);

      final var fontHeight = 105;
      final var font = new PDType1Font(FontName.HELVETICA);

      final var width = page.getMediaBox().getWidth();
      final var height = page.getMediaBox().getHeight();

      final var stringWidth = font.getStringWidth(WATERMARK_DRAFT) / 1000 * fontHeight;
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
      contentStream.showText(WATERMARK_DRAFT);
      contentStream.endText();
      contentStream.close();
    }
  }

}
