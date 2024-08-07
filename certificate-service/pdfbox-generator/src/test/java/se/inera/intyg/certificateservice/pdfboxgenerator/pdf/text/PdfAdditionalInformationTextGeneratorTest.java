package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.fk7210CertificateBuilder;

import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.Matrix;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PdfAdditionalInformationTextGeneratorTest {

  @Mock
  PdfTextGenerator pdfTextGenerator;

  @InjectMocks
  PdfAdditionalInformationTextGenerator pdfAdditionalInformationTextGenerator;

  @Mock
  PDDocument document;

  @Test
  void shouldSetSentText() throws IOException {
    final var captor = ArgumentCaptor.forClass(String.class);
    final var certificate = fk7210CertificateBuilder().build();
    pdfAdditionalInformationTextGenerator.addSentText(document, certificate, 100);

    verify(pdfTextGenerator).addTopWatermark(
        any(PDDocument.class),
        captor.capture(),
        any(Matrix.class),
        anyInt(),
        anyInt(),
        anyBoolean()
    );

    assertEquals(
        "Intyget har skickats digitalt till " + certificate.sent().recipient().name(),
        captor.getValue()
    );
  }

  @Test
  void shouldSetSenVisibilityText() throws IOException {
    final var captor = ArgumentCaptor.forClass(String.class);
    pdfAdditionalInformationTextGenerator.addSentVisibilityText(document, 100);

    verify(pdfTextGenerator).addTopWatermark(
        any(PDDocument.class),
        captor.capture(),
        any(Matrix.class),
        anyInt(),
        anyInt(),
        anyBoolean()
    );

    assertEquals(
        "Du kan se intyget genom att logga in på 1177.se",
        captor.getValue()
    );
  }

  @Test
  void shouldSetMarginText() throws IOException {
    final var captor = ArgumentCaptor.forClass(String.class);
    pdfAdditionalInformationTextGenerator.addMarginAdditionalInfoText(document, "ID",
        "Additional info.", 100, 1);

    verify(pdfTextGenerator).addMarginText(
        any(PDDocument.class),
        captor.capture(),
        anyInt(),
        anyInt()
    );

    assertEquals(
        "Intygsid: ID. Additional info.",
        captor.getValue()
    );
  }

  @Test
  void shouldSetDigitalSignature() throws IOException {
    pdfAdditionalInformationTextGenerator.addDigitalSignatureText(document, 10F, 20F, 100, 34, 0);

    verify(pdfTextGenerator).addDigitalSignatureText(
        document,
        "Detta är en utskrift av ett elektroniskt intyg. Intyget har signerats elektroniskt av intygsutfärdaren.",
        10F,
        20F,
        100,
        34,
        0);
  }

  @Test
  void shouldSetDraftWatermark() throws IOException {
    final var captor = ArgumentCaptor.forClass(String.class);

    pdfAdditionalInformationTextGenerator.addDraftWatermark(document, 100);

    verify(pdfTextGenerator).addWatermark(any(PDDocument.class), captor.capture(), anyInt());
    assertEquals("UTKAST", captor.getValue());
  }

}
