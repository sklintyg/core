package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.text;

import static se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfConstants.DIGITALLY_SIGNED_TEXT;
import static se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfConstants.WATERMARK_DRAFT;

import java.awt.Color;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
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
      String additionalInfoText, int mcid, int pageIndex) throws IOException {
    pdfTextGenerator.addMarginText(
        document,
        "Intygsid: %s. %s".formatted(id, additionalInfoText),
        mcid,
        pageIndex
    );
  }

  public void addDraftWatermark(PDDocument document, int mcid) throws IOException {
    pdfTextGenerator.addWatermark(document, WATERMARK_DRAFT, mcid);
  }

  public void addDigitalSignatureText(PDDocument pdDocument, Float xPosition, Float yPosition,
      int mcid, int signatureIndex, int pageIndex)
      throws IOException {
    pdfTextGenerator.addDigitalSignatureText(
        pdDocument,
        DIGITALLY_SIGNED_TEXT,
        xPosition,
        yPosition,
        mcid,
        signatureIndex,
        pageIndex
    );
  }

  public void addPatientId(PDDocument document, int pageIndex, float xPosition, float yPosition,
      String patientId, float fontSize) throws IOException {
    //TODO: crteate new div with paragraph and marked content for patientId
    //TODO new correct mcid
    var mcid = 1;
    pdfTextGenerator.addText(document, patientId, (int) fontSize, null, Color.black, xPosition,
        yPosition,
        false,
        mcid, PdfTextGenerator.createNewDivOnTop(document), pageIndex);
  }

  public void setPageNumber(PDDocument document, int pageIndex, int nbrOfPages, int mcid)
      throws IOException {
    final float MARGIN_LEFT = 63.5F;
    final float MARGIN_TOP = 37;
    //TODO: crteate new div with paragraph and marked content for pagenr
    final var section = PdfTextGenerator.createNewDivOnTop(document);

    PDPage page = document.getPage(pageIndex);
    final var x = page.getMediaBox().getWidth() - MARGIN_LEFT;
    final var y = page.getMediaBox().getHeight() - MARGIN_TOP;

    pdfTextGenerator.addText(document, "%d (%d)".formatted(pageIndex + 1, nbrOfPages), 10, null,
        Color.black, x, y,
        false,
        mcid, section, pageIndex);
  }


  public void addOverFlowPageText(PDDocument document, int originalPageIndex, int pageIndex,
      List<String> lines,
      float xPosition, float yPosition, float leading, float fontSize) throws IOException {

    pdfTextGenerator.addTextLines(document, lines, (int) fontSize, Color.black, xPosition,
        yPosition, leading,
        1, originalPageIndex, pageIndex);

  }
}
