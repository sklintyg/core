package se.inera.intyg.certificateservice.pdfboxgenerator.helpers;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.fk7211CertificateBuilder;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.pdfboxgenerator.toolkits.PdfGeneratorTextToolkit;
import se.inera.intyg.certificateservice.pdfboxgenerator.toolkits.PdfGeneratorValueToolkit;

@ExtendWith(MockitoExtension.class)
class PdfSignatureHelperTest {

  private static final LocalDateTime SIGNED = LocalDateTime.now();

  @Mock
  PdfGeneratorTextToolkit pdfGeneratorTextToolkit;

  @Mock
  PdfGeneratorValueToolkit pdfGeneratorValueToolkit;

  @InjectMocks
  PdfSignatureHelper pdfSignatureHelper;

  @Mock
  PDDocument document;

  @Mock
  PDAcroForm acroForm;

  @Test
  void shouldAddDigitalSignature() throws IOException {
    final var signed = LocalDateTime.now();
    final var certificate = fk7211CertificateBuilder()
        .signed(signed)
        .build();

    pdfSignatureHelper.setSignedValues(document, acroForm, certificate);

    verify(pdfGeneratorTextToolkit).addDigitalSignatureText(document, acroForm);
  }

  @Test
  void shouldAddSignedDate() throws IOException {
    final var certificate = getCertificate();
    final var captor = ArgumentCaptor.forClass(String.class);
    final var idCaptor = ArgumentCaptor.forClass(String.class);

    pdfSignatureHelper.setSignedValues(document, acroForm, certificate);

    verify(pdfGeneratorValueToolkit, times(6)).setValue(eq(acroForm), idCaptor.capture(),
        captor.capture());

    assertAll(
        () -> assertTrue(
            idCaptor.getAllValues().contains("form1[0].#subform[0].flt_datUnderskrift[0]")
        ),
        () -> assertTrue(captor.getAllValues().contains(SIGNED.format(DateTimeFormatter.ISO_DATE)))
    );
  }

  @Test
  void shouldAddIssuerName() throws IOException {
    final var certificate = getCertificate();
    final var captor = ArgumentCaptor.forClass(String.class);
    final var idCaptor = ArgumentCaptor.forClass(String.class);

    pdfSignatureHelper.setSignedValues(document, acroForm, certificate);

    verify(pdfGeneratorValueToolkit, times(6)).setValue(eq(acroForm), idCaptor.capture(),
        captor.capture());

    assertAll(
        () -> assertTrue(
            idCaptor.getAllValues().contains("form1[0].#subform[0].flt_txtNamnfortydligande[0]")
        ),
        () -> assertTrue(captor.getAllValues()
            .contains(certificate.certificateMetaData().issuer().name().fullName())
        )
    );
  }

  @Test
  void shouldAddIssuerHsaId() throws IOException {
    final var certificate = getCertificate();
    final var captor = ArgumentCaptor.forClass(String.class);
    final var idCaptor = ArgumentCaptor.forClass(String.class);

    pdfSignatureHelper.setSignedValues(document, acroForm, certificate);

    verify(pdfGeneratorValueToolkit, times(6)).setValue(eq(acroForm), idCaptor.capture(),
        captor.capture());

    assertAll(
        () -> assertTrue(
            idCaptor.getAllValues().contains("form1[0].#subform[0].flt_txtLakarensHSA-ID[0]")
        ),
        () -> assertTrue(captor.getAllValues()
            .contains(certificate.certificateMetaData().issuer().hsaId().id())
        )
    );
  }

  @Test
  void shouldSetPaTitles() throws IOException {
    final var certificate = getCertificate();
    final var captor = ArgumentCaptor.forClass(String.class);
    final var idCaptor = ArgumentCaptor.forClass(String.class);

    pdfSignatureHelper.setSignedValues(document, acroForm, certificate);

    verify(pdfGeneratorValueToolkit, times(6)).setValue(eq(acroForm), idCaptor.capture(),
        captor.capture());

    assertAll(
        () -> assertTrue(
            idCaptor.getAllValues().contains("form1[0].#subform[0].flt_txtBefattning[0]")
        ),
        () -> assertTrue(captor.getAllValues()
            .contains("203090, 601010")
        )
    );
  }

  @Test
  void shouldSetSpeciality() throws IOException {
    final var certificate = getCertificate();
    final var captor = ArgumentCaptor.forClass(String.class);
    final var idCaptor = ArgumentCaptor.forClass(String.class);

    pdfSignatureHelper.setSignedValues(document, acroForm, certificate);

    verify(pdfGeneratorValueToolkit, times(6)).setValue(eq(acroForm), idCaptor.capture(),
        captor.capture());

    assertAll(
        () -> assertTrue(
            idCaptor.getAllValues()
                .contains("form1[0].#subform[0].flt_txtEventuellSpecialistkompetens[0]")
        ),
        () -> assertTrue(captor.getAllValues()
            .contains("Allmänmedicin, Psykiatri")
        )
    );
  }

  @Test
  void shouldSetWorkplaceCode() throws IOException {
    final var certificate = getCertificate();
    final var captor = ArgumentCaptor.forClass(String.class);
    final var idCaptor = ArgumentCaptor.forClass(String.class);

    pdfSignatureHelper.setSignedValues(document, acroForm, certificate);

    verify(pdfGeneratorValueToolkit, times(6)).setValue(eq(acroForm), idCaptor.capture(),
        captor.capture());

    assertAll(
        () -> assertTrue(
            idCaptor.getAllValues()
                .contains("form1[0].#subform[0].flt_txtArbetsplatskod[0]")
        ),
        () -> assertTrue(captor.getAllValues()
            .contains("1627")
        )
    );
  }

  private static Certificate getCertificate() {
    return fk7211CertificateBuilder()
        .signed(SIGNED)
        .build();
  }

}