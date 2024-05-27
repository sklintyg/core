package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.text;

import static se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfConstants.DIGITALLY_SIGNED_TEXT;
import static se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfConstants.WATERMARK_DRAFT;

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

  public void addSentText(PDDocument document, Certificate certificate, int mcid)
      throws IOException {
    pdfTextGenerator.addTopWatermark(
        document,
        "Intyget har skickats digitalt till %s".formatted(certificate.sent().recipient().name()),
        Matrix.getTranslateInstance(40, 685),
        22,
        mcid,
        false
    );
  }

  public void addSentVisibilityText(PDDocument document, int mcid) throws IOException {
    pdfTextGenerator.addTopWatermark(
        document,
        "Du kan se intyget genom att logga in på 1177.se",
        Matrix.getTranslateInstance(40, 665),
        16,
        mcid,
        true
    );
  }

  public void addMarginAdditionalInfoText(PDDocument document, String id,
      String additionalInfoText, int mcid) throws IOException {
    pdfTextGenerator.addMarginText(
        document,
        "Intygsid: %s. %s".formatted(id, additionalInfoText),
        mcid
    );
  }

  public void addDraftWatermark(PDDocument document, int mcid) throws IOException {
    pdfTextGenerator.addWatermark(document, WATERMARK_DRAFT, mcid);
  }

  public void addDigitalSignatureText(PDDocument pdDocument, Float xPosition, Float yPosition,
      int mcid, int signatureIndex)
      throws IOException {
    pdfTextGenerator.addDigitalSignatureText(
        pdDocument,
        DIGITALLY_SIGNED_TEXT,
        xPosition,
        yPosition,
        mcid,
        signatureIndex
    );
  }
}
