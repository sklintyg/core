package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalCertificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.TemplatePdfSpecification;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.CertificatePdfContext;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfField;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value.PdfElementValueGenerator;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value.PdfPatientValueGenerator;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value.PdfSignatureValueGenerator;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value.PdfUnitValueGenerator;

@ExtendWith(MockitoExtension.class)
class PdfFieldGeneratorServiceTest {

  @Mock
  private PdfUnitValueGenerator pdfUnitValueGenerator;
  @Mock
  private PdfPatientValueGenerator pdfPatientValueGenerator;
  @Mock
  private PdfSignatureValueGenerator pdfSignatureValueGenerator;
  @Mock
  private PdfElementValueGenerator pdfElementValueGenerator;

  @InjectMocks
  private PdfFieldGeneratorService pdfFieldGeneratorService;

  private CertificatePdfContext context;
  private Certificate certificate;
  private TemplatePdfSpecification templatePdfSpecification;

  private static final PdfField UNIT_FIELD = PdfField.builder()
      .id("unit-field")
      .value("Unit Value")
      .build();

  private static final PdfField PATIENT_FIELD = PdfField.builder()
      .id("patient-field")
      .value("Patient Value")
      .patientField(true)
      .build();

  private static final PdfField SIGNATURE_FIELD = PdfField.builder()
      .id("signature-field")
      .value("Signature Value")
      .build();

  private static final PdfField ELEMENT_FIELD = PdfField.builder()
      .id("element-field")
      .value("Element Value")
      .build();

  @BeforeEach
  void setUp() {
    templatePdfSpecification = TemplatePdfSpecification.builder()
        .patientIdFieldIds(List.of())
        .build();

    final var certificateModel = CertificateModel.builder()
        .pdfSpecification(templatePdfSpecification)
        .build();

    certificate = MedicalCertificate.builder()
        .certificateModel(certificateModel)
        .status(Status.DRAFT)
        .build();

    context = CertificatePdfContext.builder()
        .certificate(certificate)
        .templatePdfSpecification(templatePdfSpecification)
        .build();
  }

  @Nested
  class GeneratePdfFieldsTests {

    @Test
    void shouldGenerateAllPdfFieldsForDraftCertificate() {
      when(pdfUnitValueGenerator.generate(certificate))
          .thenReturn(List.of(UNIT_FIELD));
      when(pdfPatientValueGenerator.generate(certificate,
          templatePdfSpecification.patientIdFieldIds()))
          .thenReturn(List.of(PATIENT_FIELD));
      when(pdfElementValueGenerator.generate(certificate))
          .thenReturn(List.of(ELEMENT_FIELD));

      final var result = pdfFieldGeneratorService.generatePdfFields(context);

      assertAll(
          () -> assertEquals(3, result.size()),
          () -> assertTrue(result.contains(UNIT_FIELD)),
          () -> assertTrue(result.contains(PATIENT_FIELD)),
          () -> assertTrue(result.contains(ELEMENT_FIELD))
      );
    }

    @Test
    void shouldGenerateAllPdfFieldsIncludingSignatureForSignedCertificate() {
      final var signedCertificate = MedicalCertificate.builder()
          .certificateModel(certificate.certificateModel())
          .status(Status.SIGNED)
          .build();

      final var signedContext = CertificatePdfContext.builder()
          .certificate(signedCertificate)
          .templatePdfSpecification(templatePdfSpecification)
          .build();

      when(pdfUnitValueGenerator.generate(signedCertificate))
          .thenReturn(List.of(UNIT_FIELD));
      when(pdfPatientValueGenerator.generate(signedCertificate,
          templatePdfSpecification.patientIdFieldIds()))
          .thenReturn(List.of(PATIENT_FIELD));
      when(pdfSignatureValueGenerator.generate(signedCertificate))
          .thenReturn(List.of(SIGNATURE_FIELD));
      when(pdfElementValueGenerator.generate(signedCertificate))
          .thenReturn(List.of(ELEMENT_FIELD));

      final var result = pdfFieldGeneratorService.generatePdfFields(signedContext);

      assertAll(
          () -> assertEquals(4, result.size()),
          () -> assertTrue(result.contains(UNIT_FIELD)),
          () -> assertTrue(result.contains(PATIENT_FIELD)),
          () -> assertTrue(result.contains(SIGNATURE_FIELD)),
          () -> assertTrue(result.contains(ELEMENT_FIELD))
      );
    }

    @Test
    void shouldNotIncludeSignatureFieldsForLockedDraftCertificate() {
      final var lockedDraftCertificate = MedicalCertificate.builder()
          .certificateModel(certificate.certificateModel())
          .status(Status.LOCKED_DRAFT)
          .build();

      final var lockedDraftContext = CertificatePdfContext.builder()
          .certificate(lockedDraftCertificate)
          .templatePdfSpecification(templatePdfSpecification)
          .build();

      when(pdfUnitValueGenerator.generate(lockedDraftCertificate))
          .thenReturn(List.of(UNIT_FIELD));
      when(pdfPatientValueGenerator.generate(lockedDraftCertificate,
          templatePdfSpecification.patientIdFieldIds()))
          .thenReturn(List.of(PATIENT_FIELD));
      when(pdfElementValueGenerator.generate(lockedDraftCertificate))
          .thenReturn(List.of(ELEMENT_FIELD));

      final var result = pdfFieldGeneratorService.generatePdfFields(lockedDraftContext);

      assertAll(
          () -> assertEquals(3, result.size()),
          () -> assertTrue(result.contains(UNIT_FIELD)),
          () -> assertTrue(result.contains(PATIENT_FIELD)),
          () -> assertTrue(result.contains(ELEMENT_FIELD))
      );
    }

    @Test
    void shouldNotIncludeSignatureFieldsForRevokedCertificate() {
      final var revokedCertificate = MedicalCertificate.builder()
          .certificateModel(certificate.certificateModel())
          .status(Status.REVOKED)
          .build();

      final var revokedContext = CertificatePdfContext.builder()
          .certificate(revokedCertificate)
          .templatePdfSpecification(templatePdfSpecification)
          .build();

      when(pdfUnitValueGenerator.generate(revokedCertificate))
          .thenReturn(List.of(UNIT_FIELD));
      when(pdfPatientValueGenerator.generate(revokedCertificate,
          templatePdfSpecification.patientIdFieldIds()))
          .thenReturn(List.of(PATIENT_FIELD));
      when(pdfElementValueGenerator.generate(revokedCertificate))
          .thenReturn(List.of(ELEMENT_FIELD));

      final var result = pdfFieldGeneratorService.generatePdfFields(revokedContext);

      assertEquals(3, result.size());
      assertTrue(result.contains(UNIT_FIELD));
      assertTrue(result.contains(PATIENT_FIELD));
      assertTrue(result.contains(ELEMENT_FIELD));
    }

    @Test
    void shouldCallAllGeneratorsWithCorrectParameters() {
      when(pdfUnitValueGenerator.generate(certificate))
          .thenReturn(List.of(UNIT_FIELD));
      when(pdfPatientValueGenerator.generate(certificate,
          templatePdfSpecification.patientIdFieldIds()))
          .thenReturn(List.of(PATIENT_FIELD));
      when(pdfElementValueGenerator.generate(certificate))
          .thenReturn(List.of(ELEMENT_FIELD));

      pdfFieldGeneratorService.generatePdfFields(context);

      verify(pdfUnitValueGenerator).generate(certificate);
      verify(pdfPatientValueGenerator).generate(certificate,
          templatePdfSpecification.patientIdFieldIds());
      verify(pdfElementValueGenerator).generate(certificate);
    }

    @Test
    void shouldHandleEmptyListsFromGenerators() {
      when(pdfUnitValueGenerator.generate(certificate))
          .thenReturn(List.of());
      when(pdfPatientValueGenerator.generate(certificate,
          templatePdfSpecification.patientIdFieldIds()))
          .thenReturn(List.of());
      when(pdfElementValueGenerator.generate(certificate))
          .thenReturn(List.of());

      final var result = pdfFieldGeneratorService.generatePdfFields(context);

      assertTrue(result.isEmpty());
    }

    @Test
    void shouldFlattenMultiplePdfFieldsFromEachGenerator() {
      final var unitField1 = PdfField.builder().id("unit-1").build();
      final var unitField2 = PdfField.builder().id("unit-2").build();
      final var patientField1 = PdfField.builder().id("patient-1").build();
      final var patientField2 = PdfField.builder().id("patient-2").build();
      final var elementField1 = PdfField.builder().id("element-1").build();
      final var elementField2 = PdfField.builder().id("element-2").build();

      when(pdfUnitValueGenerator.generate(certificate))
          .thenReturn(List.of(unitField1, unitField2));
      when(pdfPatientValueGenerator.generate(certificate,
          templatePdfSpecification.patientIdFieldIds()))
          .thenReturn(List.of(patientField1, patientField2));
      when(pdfElementValueGenerator.generate(certificate))
          .thenReturn(List.of(elementField1, elementField2));

      final var result = pdfFieldGeneratorService.generatePdfFields(context);

      assertEquals(6, result.size());
      assertTrue(result.containsAll(
          List.of(unitField1, unitField2, patientField1, patientField2, elementField1,
              elementField2)));
    }
  }
}