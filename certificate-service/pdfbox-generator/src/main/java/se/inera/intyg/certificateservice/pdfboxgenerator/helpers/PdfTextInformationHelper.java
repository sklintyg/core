package se.inera.intyg.certificateservice.pdfboxgenerator.helpers;

import static se.inera.intyg.certificateservice.pdfboxgenerator.util.PdfConstants.WATERMARK_DRAFT;

import java.awt.Color;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.Matrix;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.pdfboxgenerator.toolkits.PdfGeneratorTextToolkit;

public class PdfTextInformationHelper {

  private final PdfGeneratorTextToolkit pdfGeneratorTextToolkit;

  public PdfTextInformationHelper(PdfGeneratorTextToolkit pdfGeneratorTextToolkit) {
    this.pdfGeneratorTextToolkit = pdfGeneratorTextToolkit;
  }

  public void addSentText(PDDocument document, Certificate certificate) throws IOException {
    pdfGeneratorTextToolkit.addText(
        document,
        "Intyget har skickats digitalt till %s".formatted(certificate.sent().recipient().name()),
        22,
        Matrix.getTranslateInstance(40, 665),
        Color.gray
    );
  }

  public void addSentVisibilityText(PDDocument document) throws IOException {
    pdfGeneratorTextToolkit.addText(
        document,
        "Du kan se intyget genom att logga in på 1177.se",
        16,
        Matrix.getTranslateInstance(40, 645),
        Color.gray
    );
  }

  public void addMarginAdditionalInfoText(PDDocument document, String id,
      String additionalInfoText) throws IOException {
    pdfGeneratorTextToolkit.addText(
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
    pdfGeneratorTextToolkit.addWatermark(document, WATERMARK_DRAFT);
  }
}
