package se.inera.intyg.certificateservice.pdfboxgenerator.helpers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.fk7211CertificateBuilder;

import java.awt.Color;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.Matrix;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.pdfboxgenerator.toolkits.PdfGeneratorTextToolkit;

@ExtendWith(MockitoExtension.class)
class PdfTextInformationHelperTest {

  @Mock
  PdfGeneratorTextToolkit pdfGeneratorTextToolkit;

  @InjectMocks
  PdfTextInformationHelper pdfTextInformationHelper;

  @Mock
  PDDocument document;

  @Test
  void shouldSetSentText() throws IOException {
    final var captor = ArgumentCaptor.forClass(String.class);
    final var certificate = fk7211CertificateBuilder().build();
    pdfTextInformationHelper.addSentText(document, certificate);

    verify(pdfGeneratorTextToolkit).addText(
        any(PDDocument.class),
        captor.capture(),
        anyInt(),
        any(Matrix.class),
        any(Color.class)
    );

    assertEquals(
        "Intyget har skickats digitalt till " + certificate.sent().recipient().name(),
        captor.getValue()
    );
  }

  @Test
  void shouldSetSenVisibilityText() throws IOException {
    final var captor = ArgumentCaptor.forClass(String.class);
    pdfTextInformationHelper.addSentVisibilityText(document);

    verify(pdfGeneratorTextToolkit).addText(
        any(PDDocument.class),
        captor.capture(),
        anyInt(),
        any(Matrix.class),
        any(Color.class)
    );

    assertEquals(
        "Du kan se intyget genom att logga in på 1177.se",
        captor.getValue()
    );
  }

  @Test
  void shouldSetMarginText() throws IOException {
    final var captor = ArgumentCaptor.forClass(String.class);
    pdfTextInformationHelper.addMarginAdditionalInfoText(document, "ID", "Additional info.");

    verify(pdfGeneratorTextToolkit).addText(
        any(PDDocument.class),
        captor.capture(),
        anyInt(),
        any(Matrix.class),
        any(Color.class),
        anyFloat(),
        anyFloat(),
        anyBoolean()
    );

    assertEquals(
        "Intygsid: ID. Additional info.",
        captor.getValue()
    );
  }

  @Test
  void shouldSetDraftWatermark() throws IOException {
    final var captor = ArgumentCaptor.forClass(String.class);

    pdfTextInformationHelper.addDraftWatermark(document);

    verify(pdfGeneratorTextToolkit).addWatermark(any(PDDocument.class), captor.capture());
    assertEquals("UTKAST", captor.getValue());
  }

}