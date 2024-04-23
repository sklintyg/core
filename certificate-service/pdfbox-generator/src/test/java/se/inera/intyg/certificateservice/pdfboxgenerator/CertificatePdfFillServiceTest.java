package se.inera.intyg.certificateservice.pdfboxgenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.fk7443CertificateBuilder;
import static se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfConstants.SIGNATURE_CARE_UNIT_CONTACT_INFORMATION_FIELD_ID;
import static se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfConstants.SIGNATURE_DATE_FIELD_ID;
import static se.inera.intyg.certificateservice.pdfboxgenerator.pdf.fk7443.FK7443PdfFillService.PATIENT_ID_FIELD_ID;
import static se.inera.intyg.certificateservice.pdfboxgenerator.pdf.fk7443.FK7443PdfFillService.SYMPTOM_FIELD_ID;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Sent;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.CertificatePdfFillService;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfField;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.fk7443.FK7443PdfFillService;
import se.inera.intyg.certificateservice.pdfboxgenerator.text.PdfAdditionalInformationTextGenerator;
import se.inera.intyg.certificateservice.pdfboxgenerator.value.PdfPatientValueGenerator;
import se.inera.intyg.certificateservice.pdfboxgenerator.value.PdfSignatureValueGenerator;
import se.inera.intyg.certificateservice.pdfboxgenerator.value.PdfUnitValueGenerator;

@ExtendWith(MockitoExtension.class)
class CertificatePdfFillServiceTest {

  private static final String TEXT = "TEXT";

  private static final PdfField SIGNED_DATE_FIELD = PdfField.builder()
      .id(SIGNATURE_DATE_FIELD_ID)
      .value(LocalDate.now().toString())
      .build();

  private static final PdfField PATIENT_FIELD = PdfField.builder()
      .id(PATIENT_ID_FIELD_ID)
      .value("191212121212")
      .build();

  private static final PdfField UNIT_FIELD = PdfField.builder()
      .id(SIGNATURE_CARE_UNIT_CONTACT_INFORMATION_FIELD_ID)
      .value("Contact information")
      .build();

  private static final PdfField SYMPTOM_FIELD = PdfField.builder()
      .id(SYMPTOM_FIELD_ID)
      .value("Symptom")
      .build();


  @Mock
  PdfUnitValueGenerator pdfUnitValueGenerator;

  @Mock
  PdfPatientValueGenerator pdfPatientValueGenerator;

  @Mock
  PdfSignatureValueGenerator pdfSignatureValueGenerator;

  @Mock
  PdfAdditionalInformationTextGenerator pdfAdditionalInformationTextGenerator;

  @Mock
  FK7443PdfFillService fk7443PdfFillService;

  @InjectMocks
  CertificatePdfFillService certificatePdfFillService;

  private Certificate certificate;

  @BeforeEach
  void setup() {
    when(fk7443PdfFillService.getPatientIdFieldId())
        .thenReturn(PATIENT_ID_FIELD_ID);

    when(pdfPatientValueGenerator.generate(any(Certificate.class), eq(PATIENT_ID_FIELD_ID)))
        .thenReturn(List.of(PATIENT_FIELD));

    when(pdfUnitValueGenerator.generate(any(Certificate.class)))
        .thenReturn(List.of(UNIT_FIELD));

    when(fk7443PdfFillService.getFields(any(Certificate.class)))
        .thenReturn(List.of(SYMPTOM_FIELD));
  }

  @Nested
  class NotSignedCertificate {

    @BeforeEach
    void setup() {
      certificate = getDraft();
    }

    @Test
    void shouldNotSetMarginText() throws IOException {
      certificatePdfFillService.fillDocument(certificate, TEXT, fk7443PdfFillService);

      verify(pdfAdditionalInformationTextGenerator, times(0))
          .addMarginAdditionalInfoText(any(), anyString(), anyString());
    }

    @Test
    void shouldNotSetSignatureText() throws IOException {
      certificatePdfFillService.fillDocument(certificate, TEXT, fk7443PdfFillService);

      verify(pdfAdditionalInformationTextGenerator, times(0))
          .addDigitalSignatureText(any(), anyFloat(), anyFloat());
    }

    @Test
    void shouldSetDraftWatermark() throws IOException {
      certificatePdfFillService.fillDocument(certificate, TEXT, fk7443PdfFillService);

      verify(pdfAdditionalInformationTextGenerator, times(1))
          .addDraftWatermark(any());
    }

    @Test
    void shouldNotSetSentText() throws IOException {
      certificatePdfFillService.fillDocument(certificate, TEXT, fk7443PdfFillService);

      verify(pdfAdditionalInformationTextGenerator, times(0))
          .addSentText(any(), any());
    }

    @Test
    void shouldSetSentVisibilityText() throws IOException {
      certificatePdfFillService.fillDocument(certificate, TEXT, fk7443PdfFillService);

      verify(pdfAdditionalInformationTextGenerator, times(0))
          .addSentVisibilityText(any());
    }

    @Test
    void shouldNotSetSignedValues() {
      final var document = certificatePdfFillService.fillDocument(certificate, TEXT,
          fk7443PdfFillService);
      final var field = getField(document, SIGNED_DATE_FIELD.getId());

      assertEquals("", field.getValueAsString());
    }

    @Test
    void shouldSetPatientValues() {
      final var document = certificatePdfFillService.fillDocument(certificate, TEXT,
          fk7443PdfFillService);
      final var field = getField(document, PATIENT_FIELD.getId());

      assertEquals(PATIENT_FIELD.getValue(), field.getValueAsString());
    }

    @Test
    void shouldSetUnitContactInformation() {
      final var document = certificatePdfFillService.fillDocument(certificate, TEXT,
          fk7443PdfFillService);
      final var field = getField(document, UNIT_FIELD.getId());

      assertEquals(UNIT_FIELD.getValue(), field.getValueAsString());
    }

    @Test
    void shouldFillPdfWithCertificateTypeSpecificValues() {
      final var document = certificatePdfFillService.fillDocument(certificate, TEXT,
          fk7443PdfFillService);
      final var field = getField(document, SYMPTOM_FIELD.getId());

      assertEquals(SYMPTOM_FIELD.getValue(), field.getValueAsString());
    }
  }


  @Nested
  class SignedCertificate {

    @BeforeEach
    void setup() {
      certificate = getCertificate();
      when(pdfSignatureValueGenerator.generate(any(Certificate.class)))
          .thenReturn(List.of(SIGNED_DATE_FIELD));
    }

    @Test
    void shouldSetMarginText() throws IOException {
      final var captor = ArgumentCaptor.forClass(String.class);
      certificatePdfFillService.fillDocument(certificate, TEXT, fk7443PdfFillService);

      verify(pdfAdditionalInformationTextGenerator, times(1))
          .addMarginAdditionalInfoText(any(), anyString(), captor.capture());

      assertEquals(TEXT, captor.getValue());
    }

    @Test
    void shouldSetSignatureText() throws IOException {
      certificatePdfFillService.fillDocument(certificate, TEXT, fk7443PdfFillService);

      verify(pdfAdditionalInformationTextGenerator, times(1))
          .addDigitalSignatureText(any(), anyFloat(), anyFloat());
    }

    @Test
    void shouldNotSetDraftWatermark() throws IOException {
      certificatePdfFillService.fillDocument(certificate, TEXT, fk7443PdfFillService);

      verify(pdfAdditionalInformationTextGenerator, times(0))
          .addDraftWatermark(any());
    }

    @Test
    void shouldNotSetSentText() throws IOException {
      certificatePdfFillService.fillDocument(certificate, TEXT, fk7443PdfFillService);

      verify(pdfAdditionalInformationTextGenerator, times(0))
          .addSentText(any(), any());
    }

    @Test
    void shouldNotSetSentVisibilityText() throws IOException {
      certificatePdfFillService.fillDocument(certificate, TEXT, fk7443PdfFillService);

      verify(pdfAdditionalInformationTextGenerator, times(0))
          .addSentVisibilityText(any());
    }

    @Test
    void shouldSetSignedValues() {
      final var document = certificatePdfFillService.fillDocument(certificate, TEXT,
          fk7443PdfFillService);
      final var field = getField(document, SIGNED_DATE_FIELD.getId());

      assertEquals(SIGNED_DATE_FIELD.getValue(), field.getValueAsString());
    }

    @Test
    void shouldSetPatientValues() {
      final var document = certificatePdfFillService.fillDocument(certificate, TEXT,
          fk7443PdfFillService);
      final var field = getField(document, PATIENT_FIELD.getId());

      assertEquals(PATIENT_FIELD.getValue(), field.getValueAsString());
    }

    @Test
    void shouldSetUnitContactInformation() {
      final var document = certificatePdfFillService.fillDocument(certificate, TEXT,
          fk7443PdfFillService);
      final var field = getField(document, UNIT_FIELD.getId());

      assertEquals(UNIT_FIELD.getValue(), field.getValueAsString());
    }

    @Test
    void shouldFillPdfWithCertificateTypeSpecificValues() {
      final var document = certificatePdfFillService.fillDocument(certificate, TEXT,
          fk7443PdfFillService);
      final var field = getField(document, SYMPTOM_FIELD.getId());

      assertEquals(SYMPTOM_FIELD.getValue(), field.getValueAsString());
    }
  }

  @Nested
  class SentCertificate {

    @BeforeEach
    void setup() {
      certificate = getSentCertificate();
      when(pdfSignatureValueGenerator.generate(any(Certificate.class)))
          .thenReturn(List.of(SIGNED_DATE_FIELD));
    }

    @Test
    void shouldSetMarginText() throws IOException {
      final var captor = ArgumentCaptor.forClass(String.class);
      certificatePdfFillService.fillDocument(certificate, TEXT, fk7443PdfFillService);

      verify(pdfAdditionalInformationTextGenerator, times(1))
          .addMarginAdditionalInfoText(any(), anyString(), captor.capture());

      assertEquals(TEXT, captor.getValue());
    }

    @Test
    void shouldSetSignatureText() throws IOException {
      certificatePdfFillService.fillDocument(certificate, TEXT, fk7443PdfFillService);

      verify(pdfAdditionalInformationTextGenerator, times(1))
          .addDigitalSignatureText(any(), anyFloat(), anyFloat());
    }

    @Test
    void shouldNotSetDraftWatermark() throws IOException {
      certificatePdfFillService.fillDocument(certificate, TEXT, fk7443PdfFillService);

      verify(pdfAdditionalInformationTextGenerator, times(0))
          .addDraftWatermark(any());
    }

    @Test
    void shouldSetSentText() throws IOException {
      certificatePdfFillService.fillDocument(certificate, TEXT, fk7443PdfFillService);

      verify(pdfAdditionalInformationTextGenerator, times(1))
          .addSentText(any(), any());
    }

    @Test
    void shouldNotSetSentVisibilityTextIfNotAvailableForCitizen() throws IOException {
      certificatePdfFillService.fillDocument(certificate, TEXT, fk7443PdfFillService);

      verify(pdfAdditionalInformationTextGenerator, times(0))
          .addSentVisibilityText(any());
    }

    @Test
    void shouldSetSentVisibilityTextIfAvailableForCitizen() throws IOException {
      certificatePdfFillService.fillDocument(certificate, TEXT, fk7443PdfFillService);

      verify(pdfAdditionalInformationTextGenerator, times(0))
          .addSentVisibilityText(any());
    }

    @Test
    void shouldSetSignedValues() {
      final var document = certificatePdfFillService.fillDocument(certificate, TEXT,
          fk7443PdfFillService);
      final var field = getField(document, SIGNED_DATE_FIELD.getId());

      assertEquals(SIGNED_DATE_FIELD.getValue(), field.getValueAsString());
    }

    @Test
    void shouldSetPatientValues() {
      final var document = certificatePdfFillService.fillDocument(certificate, TEXT,
          fk7443PdfFillService);
      final var field = getField(document, PATIENT_FIELD.getId());

      assertEquals(PATIENT_FIELD.getValue(), field.getValueAsString());
    }

    @Test
    void shouldSetUnitContactInformation() {
      final var document = certificatePdfFillService.fillDocument(certificate, TEXT,
          fk7443PdfFillService);
      final var field = getField(document, UNIT_FIELD.getId());

      assertEquals(UNIT_FIELD.getValue(), field.getValueAsString());
    }

    @Test
    void shouldFillPdfWithCertificateTypeSpecificValues() {
      final var document = certificatePdfFillService.fillDocument(certificate, TEXT,
          fk7443PdfFillService);
      final var field = getField(document, SYMPTOM_FIELD.getId());

      assertEquals(SYMPTOM_FIELD.getValue(), field.getValueAsString());
    }
  }

  private static Certificate getSentCertificate() {
    return fk7443CertificateBuilder()
        .signed(LocalDateTime.now())
        .status(Status.SIGNED)
        .sent(
            Sent.builder()
                .sentAt(LocalDateTime.now())
                .build()
        )
        .build();
  }

  private static Certificate getCertificate() {
    return fk7443CertificateBuilder()
        .signed(LocalDateTime.now())
        .status(Status.SIGNED)
        .sent(null)
        .build();
  }

  private static Certificate getDraft() {
    return fk7443CertificateBuilder()
        .status(Status.DRAFT)
        .sent(null)
        .signed(null)
        .build();
  }

  private static PDField getField(PDDocument document, String fieldId) {
    return document.getDocumentCatalog().getAcroForm().getField(fieldId);
  }
}