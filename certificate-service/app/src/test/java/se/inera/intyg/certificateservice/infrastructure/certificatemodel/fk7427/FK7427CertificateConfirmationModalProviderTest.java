package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnit.ALFA_MEDICINCENTRUM;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnit.BETA_VARDCENTRAL;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.fk7427CertificateBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUser.ajlaDoctorBuilder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateModalActionType;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateMetaData;
import se.inera.intyg.certificateservice.domain.certificate.model.Revision;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.Alert;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.MessageLevel;
import se.inera.intyg.certificateservice.domain.common.model.AccessScope;
import se.inera.intyg.certificateservice.domain.common.model.PersonId;
import se.inera.intyg.certificateservice.domain.patient.model.Name;
import se.inera.intyg.certificateservice.domain.patient.model.Patient;
import se.inera.intyg.certificateservice.domain.unit.model.SubUnit;

class FK7427CertificateConfirmationModalProviderTest {

  private static Certificate certificate;
  private static ActionEvaluation actionEvaluation;
  private static AccessScope accessScope;
  private static final FK7427CertificateConfirmationModalProvider provider = new FK7427CertificateConfirmationModalProvider();

  private static final String PATIENT_NAME = "First";
  private static final String PATIENT_MIDDLE_NAME = "Middle";
  private static final String PATIENT_LAST_NAME = "Last";
  private static final String PATIENT_FULL_NAME = "%s %s %s".formatted(
      PATIENT_NAME,
      PATIENT_MIDDLE_NAME,
      PATIENT_LAST_NAME
  );
  private static final String PATIENT_ID = "191212121212";
  private static final String PATIENT_ID_WITH_DASH = "19121212-1212";

  @Test
  void shouldReturnNullIfActionEvaluationIsNull() {
    assertNull(provider.of(certificate, null));
  }

  @Nested
  class WithinCareUnit {

    @BeforeEach
    void setup() {
      accessScope = AccessScope.WITHIN_CARE_UNIT;
    }

    @Nested
    class CertificateExists {

      @BeforeEach
      void setup() {
        certificate = fk7427CertificateBuilder()
            .certificateMetaData(
                CertificateMetaData.builder()
                    .patient(
                        Patient.builder()
                            .name(
                                Name.builder()
                                    .firstName(PATIENT_NAME)
                                    .middleName(PATIENT_MIDDLE_NAME)
                                    .lastName(PATIENT_LAST_NAME)
                                    .build()
                            )
                            .id(
                                PersonId.builder()
                                    .id(PATIENT_ID)
                                    .build()
                            )
                            .build()
                    )
                    .issuingUnit(ALFA_MEDICINCENTRUM)
                    .build()
            )
            .build();

        actionEvaluation = ActionEvaluation.builder()
            .user(
                ajlaDoctorBuilder()
                    .accessScope(accessScope)
                    .build()
            )
            .careUnit(ALFA_MEDICINCENTRUM)
            .build();
      }

      @Test
      void shouldReturnNull() {
        assertNull(provider.of(certificate, actionEvaluation));
      }
    }

    @Nested
    class CertificateDoesNotExists {

      @BeforeEach
      void setup() {
        actionEvaluation = ActionEvaluation.builder()
            .user(
                ajlaDoctorBuilder()
                    .accessScope(accessScope)
                    .build()
            )
            .patient(
                Patient.builder()
                    .name(
                        Name.builder()
                            .firstName(PATIENT_NAME)
                            .middleName(PATIENT_MIDDLE_NAME)
                            .lastName(PATIENT_LAST_NAME)
                            .build()
                    )
                    .id(
                        PersonId.builder()
                            .id(PATIENT_ID)
                            .build()
                    )
                    .build()
            )
            .careUnit(ALFA_MEDICINCENTRUM)
            .build();
      }

      @Test
      void shallContainTitle() {
        final var certificateConfirmationModal = provider.of(null, actionEvaluation);
        assertEquals("Kontrollera att du använder dig av rätt läkarutlåtande",
            certificateConfirmationModal.title());
      }

      @Test
      void shallContainAlert() {
        final var expectedAlert = Alert.builder()
            .type(MessageLevel.INFO)
            .text(
                """
                    <p>Du är på väg att utfärda Läkarutlåtande tillfällig föräldrapenning Barn 12-16 år för</p>
                    <b>%s - %s</b>
                    """.formatted(
                    PATIENT_FULL_NAME,
                    PATIENT_ID_WITH_DASH
                )
            ).build();
        final var certificateConfirmationModal = provider.of(null, actionEvaluation);
        assertEquals(expectedAlert, certificateConfirmationModal.alert());
      }

      @Test
      void shallContainText() {
        final var expectedText = """
            <p>Läkarutlåtande tillfällig föräldrapenning barn 12-16 år ska endast användas när ett barn på grund av sjukdom behöver vård eller tillsyn av en förälder.</p><br>
            <p>Om barnet är allvarligt sjukt används istället Läkarutlåtande tillfällig föräldrapenning för ett allvarligt sjukt barn som inte har fyllt 18 år (FK7426).</p>
            """;
        final var certificateConfirmationModal = provider.of(null, actionEvaluation);
        assertEquals(expectedText, certificateConfirmationModal.text());
      }

      @Test
      void shallContainCheckboxText() {
        final var expectedCheckboxText = "Jag är säker på att jag vill utfärda Läkarutlåtande tillfällig föräldrapenning barn 12-16 år";
        final var certificateConfirmationModal = provider.of(null, actionEvaluation);
        assertEquals(expectedCheckboxText, certificateConfirmationModal.checkboxText());
      }

      @Test
      void shallContainPrimaryAction() {
        final var certificateConfirmationModal = provider.of(null, actionEvaluation);
        assertEquals(CertificateModalActionType.READ, certificateConfirmationModal.primaryAction());
      }

      @Test
      void shallContainSecondaryAction() {
        final var certificateConfirmationModal = provider.of(null, actionEvaluation);
        assertEquals(CertificateModalActionType.CANCEL,
            certificateConfirmationModal.secondaryAction());
      }
    }
  }

  @Nested
  class WithinCareProvider {

    @BeforeEach
    void setup() {
      accessScope = AccessScope.WITHIN_CARE_PROVIDER;
    }

    @Nested
    class CertificateExistsWithRevisonOverZero {

      @BeforeEach
      void setup() {
        certificate = fk7427CertificateBuilder()
            .certificateMetaData(
                CertificateMetaData.builder()
                    .patient(
                        Patient.builder()
                            .name(
                                Name.builder()
                                    .firstName(PATIENT_NAME)
                                    .middleName(PATIENT_MIDDLE_NAME)
                                    .lastName(PATIENT_LAST_NAME)
                                    .build()
                            )
                            .id(
                                PersonId.builder()
                                    .id(PATIENT_ID)
                                    .build()
                            )
                            .build()
                    )
                    .issuingUnit(ALFA_MEDICINCENTRUM)
                    .build()
            )
            .revision(new Revision(1L))
            .build();

        actionEvaluation = ActionEvaluation.builder()
            .user(
                ajlaDoctorBuilder()
                    .accessScope(accessScope)
                    .build()
            )
            .careUnit(ALFA_MEDICINCENTRUM)
            .build();
      }

      @Test
      void shouldReturnNull() {
        assertNull(provider.of(certificate, actionEvaluation));
      }
    }

    @Nested
    class CertificateExistsWithRevisonZeroAndWithinCareUnit {

      @BeforeEach
      void setup() {
        certificate = fk7427CertificateBuilder()
            .certificateMetaData(
                CertificateMetaData.builder()
                    .patient(
                        Patient.builder()
                            .name(
                                Name.builder()
                                    .firstName(PATIENT_NAME)
                                    .middleName(PATIENT_MIDDLE_NAME)
                                    .lastName(PATIENT_LAST_NAME)
                                    .build()
                            )
                            .id(
                                PersonId.builder()
                                    .id(PATIENT_ID)
                                    .build()
                            )
                            .build()
                    )
                    .careUnit(ALFA_MEDICINCENTRUM)
                    .issuingUnit(ALFA_MEDICINCENTRUM)
                    .build()
            )
            .revision(new Revision(0L))
            .build();

        actionEvaluation = ActionEvaluation.builder()
            .user(
                ajlaDoctorBuilder()
                    .accessScope(accessScope)
                    .build()
            )
            .careUnit(ALFA_MEDICINCENTRUM)
            .subUnit(
                SubUnit.builder()
                    .hsaId(ALFA_MEDICINCENTRUM.hsaId())
                    .build()
            )
            .build();
      }

      @Test
      void shouldNotReturnNull() {
        assertNotNull(provider.of(certificate, actionEvaluation));
      }
    }

    @Nested
    class CertificateExistsWithRevisonZeroAndNotWithinCareUnit {

      @BeforeEach
      void setup() {
        certificate = fk7427CertificateBuilder()
            .certificateMetaData(
                CertificateMetaData.builder()
                    .patient(
                        Patient.builder()
                            .name(
                                Name.builder()
                                    .firstName(PATIENT_NAME)
                                    .middleName(PATIENT_MIDDLE_NAME)
                                    .lastName(PATIENT_LAST_NAME)
                                    .build()
                            )
                            .id(
                                PersonId.builder()
                                    .id(PATIENT_ID)
                                    .build()
                            )
                            .build()
                    )
                    .careUnit(ALFA_MEDICINCENTRUM)
                    .issuingUnit(ALFA_MEDICINCENTRUM)
                    .build()
            )
            .revision(new Revision(0L))
            .build();

        actionEvaluation = ActionEvaluation.builder()
            .user(
                ajlaDoctorBuilder()
                    .accessScope(accessScope)
                    .build()
            )
            .careUnit(BETA_VARDCENTRAL)
            .subUnit(
                SubUnit.builder()
                    .hsaId(BETA_VARDCENTRAL.hsaId())
                    .build()
            )
            .build();
      }

      @Test
      void shouldReturnNull() {
        assertNull(provider.of(certificate, actionEvaluation));
      }
    }

    @Nested
    class CertificateDoesNotExistsAndRevisonZeroWithinCareUnit {

      @BeforeEach
      void setup() {
        actionEvaluation = ActionEvaluation.builder()
            .user(
                ajlaDoctorBuilder()
                    .accessScope(accessScope)
                    .build()
            )
            .patient(
                Patient.builder()
                    .name(
                        Name.builder()
                            .firstName(PATIENT_NAME)
                            .middleName(PATIENT_MIDDLE_NAME)
                            .lastName(PATIENT_LAST_NAME)
                            .build()
                    )
                    .id(
                        PersonId.builder()
                            .id(PATIENT_ID)
                            .build()
                    )
                    .build()
            )
            .careUnit(ALFA_MEDICINCENTRUM)
            .build();
      }

      @Test
      void shallContainTitle() {
        final var certificateConfirmationModal = provider.of(certificate, actionEvaluation);
        assertEquals("Kontrollera att du använder dig av rätt läkarutlåtande",
            certificateConfirmationModal.title());
      }

      @Test
      void shallContainAlert() {
        final var expectedAlert = Alert.builder()
            .type(MessageLevel.INFO)
            .text(
                """
                    <p>Du är på väg att utfärda Läkarutlåtande tillfällig föräldrapenning Barn 12-16 år för</p>
                    <b>%s - %s</b>
                    """.formatted(
                    PATIENT_FULL_NAME,
                    PATIENT_ID_WITH_DASH
                )
            ).build();
        final var certificateConfirmationModal = provider.of(certificate, actionEvaluation);
        assertEquals(expectedAlert, certificateConfirmationModal.alert());
      }

      @Test
      void shallContainText() {
        final var expectedText = """
            <p>Läkarutlåtande tillfällig föräldrapenning barn 12-16 år ska endast användas när ett barn på grund av sjukdom behöver vård eller tillsyn av en förälder.</p><br>
            <p>Om barnet är allvarligt sjukt används istället Läkarutlåtande tillfällig föräldrapenning för ett allvarligt sjukt barn som inte har fyllt 18 år (FK7426).</p>
            """;
        final var certificateConfirmationModal = provider.of(certificate, actionEvaluation);
        assertEquals(expectedText, certificateConfirmationModal.text());
      }

      @Test
      void shallContainCheckboxText() {
        final var expectedCheckboxText = "Jag är säker på att jag vill utfärda Läkarutlåtande tillfällig föräldrapenning barn 12-16 år";
        final var certificateConfirmationModal = provider.of(certificate, actionEvaluation);
        assertEquals(expectedCheckboxText, certificateConfirmationModal.checkboxText());
      }

      @Test
      void shallContainPrimaryAction() {
        final var certificateConfirmationModal = provider.of(certificate, actionEvaluation);
        assertEquals(CertificateModalActionType.READ, certificateConfirmationModal.primaryAction());
      }

      @Test
      void shallContainSecondaryAction() {
        final var certificateConfirmationModal = provider.of(certificate, actionEvaluation);
        assertEquals(CertificateModalActionType.DELETE,
            certificateConfirmationModal.secondaryAction());
      }
    }
  }
}