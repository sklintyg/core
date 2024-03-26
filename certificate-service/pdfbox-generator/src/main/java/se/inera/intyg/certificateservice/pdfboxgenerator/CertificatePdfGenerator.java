package se.inera.intyg.certificateservice.pdfboxgenerator;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName;
import org.apache.pdfbox.pdmodel.graphics.state.PDExtendedGraphicsState;
import org.apache.pdfbox.util.Matrix;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Pdf;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.certificate.service.PdfGenerator;

public class CertificatePdfGenerator implements PdfGenerator {

  private static final String WATERMARK_DRAFT = "UTKAST";

  public Pdf generate(Certificate certificate, String additionalInfoText) {
    try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
      final var fk7211Pdf = new FK7211PdfGenerator(certificate).getDocument();

      setMarginText(fk7211Pdf, certificate, additionalInfoText);

      if (certificate.status() == Status.DRAFT) {
        setDraftWatermark(fk7211Pdf);
      }

      fk7211Pdf.getDocumentCatalog().getAcroForm().flatten();
      fk7211Pdf.save(byteArrayOutputStream);
      fk7211Pdf.save("fk7211_test.pdf");
      fk7211Pdf.close();

      return new Pdf(byteArrayOutputStream.toByteArray(), setFileName(certificate));

    } catch (Exception e) {
      throw new IllegalStateException("Could not create Pdf", e);
    }
  }

  private void setMarginText(PDDocument fk7211Pdf, Certificate certificate,
      String additionalInfoText)
      throws IOException {
    final var contentStream = new PDPageContentStream(fk7211Pdf, fk7211Pdf.getPage(0),
        AppendMode.APPEND, true, true);

    final var certificateId = certificate.id().id();
    contentStream.transform(Matrix.getRotateInstance(Math.PI / 2, 607, 280));
    contentStream.beginText();
    contentStream.newLineAtOffset(30, 30);
    contentStream.setNonStrokingColor(Color.black);
    contentStream.setFont(new PDType1Font(FontName.HELVETICA), 8);
    contentStream.showText("Intygsid: %s. %s".formatted(certificateId, additionalInfoText));
    contentStream.endText();
    contentStream.close();
  }

  private void setDraftWatermark(PDDocument fk7211Pdf) throws IOException {
    for (PDPage page : fk7211Pdf.getPages()) {

      final var contentStream = new PDPageContentStream(fk7211Pdf, page, AppendMode.APPEND,
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

  private String setFileName(Certificate certificate) {
    final var certificateName = certificate.certificateModel().name();
    final var timestamp = LocalDateTime.now()
        .format((DateTimeFormatter.ofPattern("yy-MM-dd_HHmm")));

    return String.format("%s_%s", certificateName, timestamp)
        .replace("å", "a")
        .replace("ä", "a")
        .replace("ö", "o")
        .replace(" ", "_")
        .replace("–", "")
        .replace("__", "_")
        .toLowerCase();
  }
}
