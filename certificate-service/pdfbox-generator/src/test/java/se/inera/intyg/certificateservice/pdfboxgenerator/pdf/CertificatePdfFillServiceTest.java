package se.inera.intyg.certificateservice.pdfboxgenerator.pdf;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.fk7472CertificateBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.fk7809CertificateBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK7472_PDF_CONTACT_INFORMATION;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK7472_PDF_SIGNED_DATE_FIELD_ID;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;
import org.apache.pdfbox.text.PDFTextStripper;
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
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.text.PdfAdditionalInformationTextGenerator;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value.PdfElementValueGenerator;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value.PdfPatientValueGenerator;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value.PdfSignatureValueGenerator;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value.PdfUnitValueGenerator;

@ExtendWith(MockitoExtension.class)
class CertificatePdfFillServiceTest {

  // TODO: Need to add full coverage for tests with append field activated

  private static final String TEXT = "TEXT";
  public static final String PATIENT_ID_FIELD_ID = "form1[0].#subform[0].flt_txtPersonNrBarn[0]";
  public static final String SYMPTOM_FIELD_ID = "form1[0].#subform[0].flt_txtDiagnos[0]";

  private static final PdfField SIGNED_DATE_FIELD = PdfField.builder()
      .id(FK7472_PDF_SIGNED_DATE_FIELD_ID.id())
      .value(LocalDate.now().toString())
      .build();

  private static final PdfField PATIENT_FIELD = PdfField.builder()
      .id(PATIENT_ID_FIELD_ID)
      .value("191212121212")
      .build();

  private static final PdfField UNIT_FIELD = PdfField.builder()
      .id(FK7472_PDF_CONTACT_INFORMATION.id())
      .value("Contact information")
      .build();

  private static final PdfField SYMPTOM_FIELD = PdfField.builder()
      .id(SYMPTOM_FIELD_ID)
      .value("Symptom")
      .build();

  private static final PdfField DIAGNOSE_DESCRIPTION_1 = PdfField.builder()
      .id("form1[0].#subform[1].flt_txtAngeFunktionsnedsattning[0]")
      .value("Description 1")
      .appearance("/ArialMT 9.00 Tf 0 g")
      .build();

  private static final PdfField DIAGNOSE_DESCRIPTION_2 = PdfField.builder()
      .id("form1[0].#subform[1].flt_txtAngeFunktionsnedsattning2[0]")
      .value("Description 2")
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
  private PdfElementValueGenerator pdfElementValueGenerator;

  @InjectMocks
  CertificatePdfFillService certificatePdfFillService;

  private Certificate certificate;

  @Nested
  class SinglePagePdf {

    @BeforeEach
    void setup() {
      when(pdfPatientValueGenerator.generate(any(Certificate.class),
          eq(List.of(new PdfFieldId(PATIENT_ID_FIELD_ID)))))
          .thenReturn(List.of(PATIENT_FIELD));

      when(pdfUnitValueGenerator.generate(any(Certificate.class)))
          .thenReturn(List.of(UNIT_FIELD));

      when(pdfElementValueGenerator.generate(any(Certificate.class)))
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
        certificatePdfFillService.fillDocument(certificate, TEXT, false);

        verify(pdfAdditionalInformationTextGenerator, times(0))
            .addMarginAdditionalInfoText(any(), anyString(), anyString(), anyInt(), anyInt());
      }

      @Test
      void shouldNotSetSignatureText() throws IOException {
        certificatePdfFillService.fillDocument(certificate, TEXT, false);

        verify(pdfAdditionalInformationTextGenerator, times(0))
            .addDigitalSignatureText(any(), anyFloat(), anyFloat(), anyInt(), anyInt(), anyInt());
      }

      @Test
      void shouldSetDraftWatermark() throws IOException {
        certificatePdfFillService.fillDocument(certificate, TEXT, false);

        verify(pdfAdditionalInformationTextGenerator, times(1))
            .addDraftWatermark(any(), anyInt());
      }

      @Test
      void shouldNotSetSentText() throws IOException {
        certificatePdfFillService.fillDocument(certificate, TEXT, false);

        verify(pdfAdditionalInformationTextGenerator, times(0))
            .addSentText(any(), any(), anyInt());
      }

      @Test
      void shouldSetSentVisibilityText() throws IOException {
        certificatePdfFillService.fillDocument(certificate, TEXT, false);

        verify(pdfAdditionalInformationTextGenerator, times(0))
            .addSentVisibilityText(any(), anyInt());
      }

      @Test
      void shouldNotSetSignedValues() {
        final var document = certificatePdfFillService.fillDocument(certificate, TEXT, false);
        final var field = getField(document, SIGNED_DATE_FIELD.getId());

        assertEquals("", field.getValueAsString());
      }

      @Test
      void shouldSetPatientValues() {
        final var document = certificatePdfFillService.fillDocument(certificate, TEXT, false);
        final var field = getField(document, PATIENT_FIELD.getId());

        assertEquals(PATIENT_FIELD.getValue(), field.getValueAsString());
      }

      @Test
      void shouldSetUnitContactInformation() {
        final var document = certificatePdfFillService.fillDocument(certificate, TEXT, false);
        final var field = getField(document, UNIT_FIELD.getId());

        assertEquals(UNIT_FIELD.getValue(), field.getValueAsString());
      }

      @Test
      void shouldFillPdfWithCertificateTypeSpecificValues() {
        final var document = certificatePdfFillService.fillDocument(certificate, TEXT, false);
        final var field = getField(document, SYMPTOM_FIELD.getId());

        assertEquals(SYMPTOM_FIELD.getValue(),
            field.getValueAsString());
      }

      @Test
      void shouldUseAddressTemplate() throws IOException {
        final var document = certificatePdfFillService.fillDocument(certificate, TEXT, false);

        final var texts = getTextForDocument(document);
        assertTrue(texts.contains("Skicka blanketten till"));
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
        certificatePdfFillService.fillDocument(certificate, TEXT, false);

        verify(pdfAdditionalInformationTextGenerator, times(1))
            .addMarginAdditionalInfoText(any(), anyString(), captor.capture(), anyInt(), anyInt());

        assertEquals(TEXT, captor.getValue());
      }

      @Test
      void shouldSetSignatureText() throws IOException {
        certificatePdfFillService.fillDocument(certificate, TEXT, false);

        verify(pdfAdditionalInformationTextGenerator, times(1))
            .addDigitalSignatureText(any(), anyFloat(), anyFloat(), anyInt(), anyInt(), anyInt());
      }

      @Test
      void shouldNotSetDraftWatermark() throws IOException {
        certificatePdfFillService.fillDocument(certificate, TEXT, false);

        verify(pdfAdditionalInformationTextGenerator, times(0))
            .addDraftWatermark(any(), anyInt());
      }

      @Test
      void shouldNotSetSentText() throws IOException {
        certificatePdfFillService.fillDocument(certificate, TEXT, false);

        verify(pdfAdditionalInformationTextGenerator, times(0))
            .addSentText(any(), any(), anyInt());
      }

      @Test
      void shouldNotSetSentVisibilityText() throws IOException {
        certificatePdfFillService.fillDocument(certificate, TEXT, false);

        verify(pdfAdditionalInformationTextGenerator, times(0))
            .addSentVisibilityText(any(), anyInt());
      }

      @Test
      void shouldSetSignedValues() {
        final var document = certificatePdfFillService.fillDocument(certificate, TEXT, false);
        final var field = getField(document, SIGNED_DATE_FIELD.getId());

        assertEquals(SIGNED_DATE_FIELD.getValue(), field.getValueAsString());
      }

      @Test
      void shouldSetPatientValues() {
        final var document = certificatePdfFillService.fillDocument(certificate, TEXT, false);
        final var field = getField(document, PATIENT_FIELD.getId());

        assertEquals(PATIENT_FIELD.getValue(), field.getValueAsString());
      }

      @Test
      void shouldSetUnitContactInformation() {
        final var document = certificatePdfFillService.fillDocument(certificate, TEXT, false);
        final var field = getField(document, UNIT_FIELD.getId());

        assertEquals(UNIT_FIELD.getValue(), field.getValueAsString());
      }

      @Test
      void shouldFillPdfWithCertificateTypeSpecificValues() {
        final var document = certificatePdfFillService.fillDocument(certificate, TEXT, false);
        final var field = getField(document, SYMPTOM_FIELD.getId());

        assertEquals(SYMPTOM_FIELD.getValue(),
            field.getValueAsString());
      }

      @Test
      void shouldUseAddressTemplate() throws IOException {
        final var document = certificatePdfFillService.fillDocument(certificate, TEXT, false);

        final var texts = getTextForDocument(document);
        assertTrue(texts.contains("Skicka blanketten till"));
      }

      @Test
      void shouldUseNoAddressTemplateIfCitizenFormat() throws IOException {
        final var document = certificatePdfFillService.fillDocument(certificate, TEXT, true);

        final var texts = getTextForDocument(document);
        assertFalse(texts.contains("Skicka blanketten till"));
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
        certificatePdfFillService.fillDocument(certificate, TEXT, false);

        verify(pdfAdditionalInformationTextGenerator, times(1))
            .addMarginAdditionalInfoText(any(), anyString(), captor.capture(), anyInt(), anyInt());

        assertEquals(TEXT, captor.getValue());
      }

      @Test
      void shouldSetSignatureText() throws IOException {
        certificatePdfFillService.fillDocument(certificate, TEXT, false);

        verify(pdfAdditionalInformationTextGenerator, times(1))
            .addDigitalSignatureText(any(), anyFloat(), anyFloat(), anyInt(), anyInt(), anyInt());
      }

      @Test
      void shouldNotSetDraftWatermark() throws IOException {
        certificatePdfFillService.fillDocument(certificate, TEXT, false);

        verify(pdfAdditionalInformationTextGenerator, times(0))
            .addDraftWatermark(any(), anyInt());
      }

      @Test
      void shouldSetSentText() throws IOException {
        certificatePdfFillService.fillDocument(certificate, TEXT, false);

        verify(pdfAdditionalInformationTextGenerator, times(1))
            .addSentText(any(), any(), anyInt());
      }

      @Test
      void shouldNotSetSentVisibilityTextIfNotAvailableForCitizen() throws IOException {
        certificatePdfFillService.fillDocument(certificate, TEXT, false);

        verify(pdfAdditionalInformationTextGenerator, times(0))
            .addSentVisibilityText(any(), anyInt());
      }

      @Test
      void shouldSetSentVisibilityTextIfAvailableForCitizen() throws IOException {
        certificatePdfFillService.fillDocument(certificate, TEXT, false);

        verify(pdfAdditionalInformationTextGenerator, times(0))
            .addSentVisibilityText(any(), anyInt());
      }

      @Test
      void shouldSetSignedValues() {
        final var document = certificatePdfFillService.fillDocument(certificate, TEXT, false);
        final var field = getField(document, SIGNED_DATE_FIELD.getId());

        assertEquals(SIGNED_DATE_FIELD.getValue(), field.getValueAsString());
      }

      @Test
      void shouldSetPatientValues() {
        final var document = certificatePdfFillService.fillDocument(certificate, TEXT, false);
        final var field = getField(document, PATIENT_FIELD.getId());

        assertEquals(PATIENT_FIELD.getValue(), field.getValueAsString());
      }

      @Test
      void shouldSetUnitContactInformation() {
        final var document = certificatePdfFillService.fillDocument(certificate, TEXT, false);
        final var field = getField(document, UNIT_FIELD.getId());

        assertEquals(UNIT_FIELD.getValue(), field.getValueAsString());
      }

      @Test
      void shouldFillPdfWithCertificateTypeSpecificValues() {
        final var document = certificatePdfFillService.fillDocument(certificate, TEXT, false);
        final var field = getField(document, SYMPTOM_FIELD.getId());

        assertEquals(SYMPTOM_FIELD.getValue(),
            field.getValueAsString());
      }

      @Test
      void shouldUseNoAddressTemplate() throws IOException {
        final var document = certificatePdfFillService.fillDocument(certificate, TEXT, false);

        final var texts = getTextForDocument(document);
        assertFalse(texts.contains("Skicka blanketten till"));
      }
    }
  }

  @Nested
  class AppearanceExists {

    @BeforeEach
    void setup() {
      certificate = getfk7809Certificate();
      when(pdfElementValueGenerator.generate(any(Certificate.class)))
          .thenReturn(List.of(DIAGNOSE_DESCRIPTION_1, DIAGNOSE_DESCRIPTION_2));
    }

    @Test
    void shouldSetAppearanceIfTextFieldAndAppearanceNotNull() {
      final var document = certificatePdfFillService.fillDocument(certificate, TEXT, false);
      final var field = getTextField(document, DIAGNOSE_DESCRIPTION_1.getId());
      final var expected = "/ArialMT 9.00 Tf 0 g";

      assertEquals(expected, field.getDefaultAppearance());
    }

    @Test
    void shouldNotSetAppearanceIfTextFieldAndAppearanceIsNull() {
      final var document = certificatePdfFillService.fillDocument(certificate, TEXT, false);
      final var field = getTextField(document, DIAGNOSE_DESCRIPTION_2.getId());
      final var expected = "/ArialMT 10.00 Tf 0 g";

      assertEquals(expected, field.getDefaultAppearance());
    }
  }

  @Nested
  class MultiplePagesPdf {

    @BeforeEach
    void setup() {
      certificate = getCertificateWithSeveralPages();
      when(pdfSignatureValueGenerator.generate(any(Certificate.class)))
          .thenReturn(Collections.emptyList());
    }

    @Test
    void shouldSetMarginTextOnEachPage() throws IOException {
      final var captor = ArgumentCaptor.forClass(String.class);
      certificatePdfFillService.fillDocument(certificate, TEXT, false);

      verify(pdfAdditionalInformationTextGenerator, times(5))
          .addMarginAdditionalInfoText(any(), anyString(), captor.capture(), anyInt(), anyInt());

      assertEquals(TEXT, captor.getValue());
    }
  }

  private static Certificate getSentCertificate() {
    return fk7472CertificateBuilder()
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
    return fk7472CertificateBuilder()
        .signed(LocalDateTime.now())
        .status(Status.SIGNED)
        .sent(null)
        .build();
  }

  private static Certificate getCertificateWithSeveralPages() {
    return fk7809CertificateBuilder()
        .signed(LocalDateTime.now())
        .status(Status.SIGNED)
        .sent(null)
        .build();
  }

  private static Certificate getDraft() {
    return fk7472CertificateBuilder()
        .status(Status.DRAFT)
        .sent(null)
        .signed(null)
        .build();
  }

  private static Certificate getfk7809Certificate() {
    return fk7809CertificateBuilder()
        .status(Status.SIGNED)
        .sent(null)
        .signed(null)
        .build();
  }

  private static PDField getField(PDDocument document, String fieldId) {
    return document.getDocumentCatalog().getAcroForm().getField(fieldId);
  }

  private static PDTextField getTextField(PDDocument document, String fieldId) {
    return (PDTextField) document.getDocumentCatalog().getAcroForm()
        .getField(fieldId);
  }

  private String getTextForDocument(PDDocument document) throws IOException {
    final var textStripper = new PDFTextStripper();
    return textStripper.getText(document);
  }
}
