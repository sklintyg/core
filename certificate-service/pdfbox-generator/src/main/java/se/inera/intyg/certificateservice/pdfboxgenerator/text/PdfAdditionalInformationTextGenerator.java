package se.inera.intyg.certificateservice.pdfboxgenerator.text;

import static se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfConstants.DIGITALLY_SIGNED_TEXT;
import static se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfConstants.WATERMARK_DRAFT;

import java.awt.Color;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.Matrix;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;

@Component
@RequiredArgsConstructor
public class PdfAdditionalInformationTextGenerator {

  private final PdfTextGenerator pdfTextGenerator;

  public void addSentText(PDDocument document, Certificate certificate) throws IOException {
    pdfTextGenerator.addText(
        document,
        "Intyget har skickats digitalt till %s".formatted(certificate.sent().recipient().name()),
        22,
        Matrix.getTranslateInstance(40, 665),
        Color.gray
    );
  }

  public void addSentVisibilityText(PDDocument document) throws IOException {
    pdfTextGenerator.addText(
        document,
        "Du kan se intyget genom att logga in på 1177.se",
        16,
        Matrix.getTranslateInstance(40, 645),
        Color.gray
    );
  }

  public void addMarginAdditionalInfoText(PDDocument document, String id,
      String additionalInfoText) throws IOException {
    pdfTextGenerator.addText(
        document,
        "Intygsid: %s. %s".formatted(id, additionalInfoText),
        8,
        Matrix.getRotateInstance(Math.PI / 2, 600, 25),
        Color.black,
        30F,
        30F,
        false
    );
  }

  public void addDraftWatermark(PDDocument document) throws IOException {
    pdfTextGenerator.addWatermark(document, WATERMARK_DRAFT);
  }

  public void addDigitalSignatureText(PDDocument pdDocument, Float xPosition, Float yPosition)
      throws IOException {
    pdfTextGenerator.addText(
        pdDocument,
        DIGITALLY_SIGNED_TEXT,
        8,
        null,
        Color.gray,
        xPosition,
        yPosition,
        true
    );
  }
}