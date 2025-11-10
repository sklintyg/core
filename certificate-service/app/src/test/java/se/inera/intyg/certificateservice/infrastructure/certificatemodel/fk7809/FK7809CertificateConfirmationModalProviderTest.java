package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnit.ALFA_MEDICINCENTRUM;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.fk7809CertificateBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUser.ajlaDoctorBuilder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateModalActionType;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateMetaData;
import se.inera.intyg.certificateservice.domain.certificate.model.Revision;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.common.model.AccessScope;
import se.inera.intyg.certificateservice.domain.common.model.HsaId;
import se.inera.intyg.certificateservice.domain.common.model.PersonId;
import se.inera.intyg.certificateservice.domain.patient.model.Name;
import se.inera.intyg.certificateservice.domain.patient.model.Patient;
import se.inera.intyg.certificateservice.domain.unit.model.CareUnit;
import se.inera.intyg.certificateservice.domain.unit.model.SubUnit;

class FK7809CertificateConfirmationModalProviderTest {

  private static final String HSA_ID = "HSA_ID";
  private static Certificate certificate;
  private static ActionEvaluation actionEvaluation;
  private static AccessScope accessScope;
  private static final FK7809CertificateConfirmationModalProvider provider = new FK7809CertificateConfirmationModalProvider();

  private static final String PATIENT_NAME = "First";
  private static final String PATIENT_MIDDLE_NAME = "Middle";
  private static final String PATIENT_LAST_NAME = "Last";
  private static final String PATIENT_ID = "201212121212";
  private static final String OLD_PATIENT_ID = "191212121212";

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
    class CertificateExistsPatientUnder18 {

      @BeforeEach
      void setup() {
        certificate = fk7809CertificateBuilder()
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
    class CertificateDoesNotExistPatientUnder18 {

      @BeforeEach
      void setup() {
        certificate = null;
        actionEvaluation = ActionEvaluation.builder()
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
            .user(
                ajlaDoctorBuilder()
                    .accessScope(accessScope)
                    .build()
            )
            .careUnit(ALFA_MEDICINCENTRUM)
            .build();
      }

      @Test
      void shouldReturnTitle() {
        final var result = provider.of(certificate, actionEvaluation);

        assertEquals("Kontrollera att du använder dig av rätt läkarutlåtande", result.title());
      }

      @Test
      void shouldReturnAlert() {
        final var result = provider.of(certificate, actionEvaluation);

        assertEquals(
            "Du är på väg att utfärda Läkarutlåtande för merkostnadsersättning för First Middle Last - 20121212-1212.",
            result.alert().text());
      }

      @Test
      void shouldReturnText() {
        final var result = provider.of(certificate, actionEvaluation);

        assertEquals(
            "Läkarutlåtande för merkostnadsersättning är till för personer över 18 år som inte har en underhållsskyldig förälder. Om det gäller ett barn ska du istället använda Läkarutlåtande för omvårdnadsbidrag eller merkostnadsersättning.",
            result.text()
        );
      }

      @Test
      void shouldReturnCheckboxText() {
        final var result = provider.of(certificate, actionEvaluation);

        assertEquals(
            "Jag är säker på att jag vill utfärda Läkarutlåtande för merkostnadsersättning.",
            result.checkboxText()
        );
      }

      @Test
      void shouldReturnPrimaryAction() {
        final var result = provider.of(certificate, actionEvaluation);

        assertEquals(
            CertificateModalActionType.READ,
            result.primaryAction()
        );
      }

      @Test
      void shouldReturnSecondaryAction() {
        final var result = provider.of(certificate, actionEvaluation);

        assertEquals(
            CertificateModalActionType.CANCEL,
            result.secondaryAction()
        );
      }
    }

    @Nested
    class PatientOver18 {

      @Test
      void shouldReturnNullIfCertificateExists() {
        certificate = fk7809CertificateBuilder()
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
                                    .id(OLD_PATIENT_ID)
                                    .build()
                            )
                            .build()
                    )
                    .issuingUnit(ALFA_MEDICINCENTRUM)
                    .build()
            )
            .build();
        actionEvaluation = ActionEvaluation.builder()
            .careUnit(ALFA_MEDICINCENTRUM)
            .user(
                ajlaDoctorBuilder()
                    .accessScope(accessScope)
                    .build()
            ).build();

        assertNull(provider.of(certificate, actionEvaluation));
      }

      @Test
      void shouldReturnNullIfCertificateDoesNotExists() {
        actionEvaluation = ActionEvaluation.builder()
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
                            .id(OLD_PATIENT_ID)
                            .build()
                    )
                    .build()
            )
            .careUnit(ALFA_MEDICINCENTRUM)
            .user(
                ajlaDoctorBuilder()
                    .accessScope(accessScope)
                    .build()
            ).build();

        assertNull(provider.of(certificate, actionEvaluation));
      }
    }

    @Test
    void shouldReturnNullIfCertificateIsLocked() {
      certificate = fk7809CertificateBuilder()
          .status(Status.LOCKED_DRAFT)
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
                                  .id(OLD_PATIENT_ID)
                                  .build()
                          )
                          .build()
                  )
                  .issuingUnit(ALFA_MEDICINCENTRUM)
                  .build()
          )
          .build();
      actionEvaluation = ActionEvaluation.builder()
          .careUnit(ALFA_MEDICINCENTRUM)
          .user(
              ajlaDoctorBuilder()
                  .accessScope(accessScope)
                  .build()
          ).build();

      assertNull(provider.of(certificate, actionEvaluation));
    }
  }

  @Nested
  class WithinCareProvider {

    @BeforeEach
    void setup() {
      accessScope = AccessScope.WITHIN_CARE_PROVIDER;
    }

    @Nested
    class Under18YearsAndSameUnitButRevisionNot0 {

      @BeforeEach
      void setup() {
        certificate = fk7809CertificateBuilder()
            .revision(new Revision(10))
            .certificateMetaData(
                CertificateMetaData.builder()
                    .issuingUnit(ALFA_MEDICINCENTRUM)
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
                    .build()
            )
            .build();
        actionEvaluation = ActionEvaluation.builder()
            .careUnit(ALFA_MEDICINCENTRUM)
            .subUnit(
                SubUnit.builder()
                    .hsaId(ALFA_MEDICINCENTRUM.hsaId())
                    .build()
            )
            .user(
                ajlaDoctorBuilder()
                    .accessScope(accessScope)
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
    class CertificateExistsPatientUnder18 {

      @BeforeEach
      void setup() {
        certificate = fk7809CertificateBuilder()
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
                    .careUnit(ALFA_MEDICINCENTRUM)
                    .build()
            )
            .build();
        actionEvaluation = ActionEvaluation.builder()
            .user(
                ajlaDoctorBuilder()
                    .accessScope(accessScope)
                    .build()
            )
            .subUnit(
                SubUnit.builder()
                    .hsaId(ALFA_MEDICINCENTRUM.hsaId())
                    .build()
            )
            .careUnit(ALFA_MEDICINCENTRUM)
            .build();
      }

      @Test
      void shouldReturnTitle() {
        final var result = provider.of(certificate, actionEvaluation);

        assertEquals("Kontrollera att du använder dig av rätt läkarutlåtande", result.title());
      }

      @Test
      void shouldReturnAlert() {
        final var result = provider.of(certificate, actionEvaluation);

        assertEquals(
            "Du är på väg att utfärda Läkarutlåtande för merkostnadsersättning för First Middle Last - 20121212-1212.",
            result.alert().text());
      }

      @Test
      void shouldReturnText() {
        final var result = provider.of(certificate, actionEvaluation);

        assertEquals(
            "Läkarutlåtande för merkostnadsersättning är till för personer över 18 år som inte har en underhållsskyldig förälder. Om det gäller ett barn ska du istället använda Läkarutlåtande för omvårdnadsbidrag eller merkostnadsersättning.",
            result.text()
        );
      }

      @Test
      void shouldReturnCheckboxText() {
        final var result = provider.of(certificate, actionEvaluation);

        assertEquals(
            "Jag är säker på att jag vill utfärda Läkarutlåtande för merkostnadsersättning.",
            result.checkboxText()
        );
      }

      @Test
      void shouldReturnPrimaryAction() {
        final var result = provider.of(certificate, actionEvaluation);

        assertEquals(
            CertificateModalActionType.READ,
            result.primaryAction()
        );
      }

      @Test
      void shouldReturnSecondaryAction() {
        final var result = provider.of(certificate, actionEvaluation);

        assertEquals(
            CertificateModalActionType.DELETE,
            result.secondaryAction()
        );
      }
    }

    @Nested
    class CertificateDoesNotExistPatientUnder18 {

      @BeforeEach
      void setup() {
        certificate = null;
        actionEvaluation = ActionEvaluation.builder()
            .careUnit(ALFA_MEDICINCENTRUM)
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
            .user(
                ajlaDoctorBuilder()
                    .accessScope(accessScope)
                    .build()
            ).build();
      }

      @Test
      void shouldReturnTitle() {
        final var result = provider.of(certificate, actionEvaluation);

        assertEquals("Kontrollera att du använder dig av rätt läkarutlåtande", result.title());
      }

      @Test
      void shouldReturnAlert() {
        final var result = provider.of(certificate, actionEvaluation);

        assertEquals(
            "Du är på väg att utfärda Läkarutlåtande för merkostnadsersättning för First Middle Last - 20121212-1212.",
            result.alert().text());
      }

      @Test
      void shouldReturnText() {
        final var result = provider.of(certificate, actionEvaluation);

        assertEquals(
            "Läkarutlåtande för merkostnadsersättning är till för personer över 18 år som inte har en underhållsskyldig förälder. Om det gäller ett barn ska du istället använda Läkarutlåtande för omvårdnadsbidrag eller merkostnadsersättning.",
            result.text()
        );
      }

      @Test
      void shouldReturnCheckboxText() {
        final var result = provider.of(certificate, actionEvaluation);

        assertEquals(
            "Jag är säker på att jag vill utfärda Läkarutlåtande för merkostnadsersättning.",
            result.checkboxText()
        );
      }

      @Test
      void shouldReturnPrimaryAction() {
        final var result = provider.of(certificate, actionEvaluation);

        assertEquals(
            CertificateModalActionType.READ,
            result.primaryAction()
        );
      }

      @Test
      void shouldReturnSecondaryAction() {
        final var result = provider.of(certificate, actionEvaluation);

        assertEquals(
            CertificateModalActionType.DELETE,
            result.secondaryAction()
        );
      }
    }

    @Nested
    class PatientOver18 {

      @Test
      void shouldReturnNullIfCertificateExists() {
        certificate = fk7809CertificateBuilder()
            .certificateMetaData(
                CertificateMetaData.builder()
                    .issuingUnit(ALFA_MEDICINCENTRUM)
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
                                    .id(OLD_PATIENT_ID)
                                    .build()
                            )
                            .build()
                    )
                    .build()
            )
            .build();
        actionEvaluation = ActionEvaluation.builder()
            .careUnit(ALFA_MEDICINCENTRUM)
            .user(
                ajlaDoctorBuilder()
                    .accessScope(accessScope)
                    .build()
            ).build();

        assertNull(provider.of(certificate, actionEvaluation));
      }

      @Test
      void shouldReturnNullIfCertificateDoesNotExists() {
        actionEvaluation = ActionEvaluation.builder()
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
                            .id(OLD_PATIENT_ID)
                            .build()
                    )
                    .build()
            )
            .user(
                ajlaDoctorBuilder()
                    .accessScope(accessScope)
                    .build()
            ).build();

        assertNull(provider.of(certificate, actionEvaluation));
      }
    }

    @Nested
    class IssuedOnAnotherCareUnit {

      @Test
      void shouldReturnNullIfIssuedOnAnotherCareUnit() {
        certificate = fk7809CertificateBuilder()
            .certificateMetaData(
                CertificateMetaData.builder()
                    .issuingUnit(
                        CareUnit.builder()
                            .hsaId(new HsaId(HSA_ID))
                            .build()
                    )
                    .careUnit(
                        CareUnit.builder()
                            .hsaId(new HsaId(HSA_ID))
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
                    .build()
            )
            .build();
        actionEvaluation = ActionEvaluation.builder()
            .careUnit(ALFA_MEDICINCENTRUM)
            .subUnit(
                SubUnit.builder()
                    .hsaId(ALFA_MEDICINCENTRUM.hsaId())
                    .build()
            )
            .user(
                ajlaDoctorBuilder()
                    .accessScope(accessScope)
                    .build()
            ).build();

        assertNull(provider.of(certificate, actionEvaluation));
      }
    }

    @Test
    void shouldReturnNullIfCertificateIsLocked() {
      certificate = fk7809CertificateBuilder()
          .status(Status.LOCKED_DRAFT)
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
                                  .id(OLD_PATIENT_ID)
                                  .build()
                          )
                          .build()
                  )
                  .issuingUnit(ALFA_MEDICINCENTRUM)
                  .build()
          )
          .build();
      actionEvaluation = ActionEvaluation.builder()
          .careUnit(ALFA_MEDICINCENTRUM)
          .user(
              ajlaDoctorBuilder()
                  .accessScope(accessScope)
                  .build()
          ).build();

      assertNull(provider.of(certificate, actionEvaluation));
    }
  }

  @Nested
  class AllCareProviders {

    @BeforeEach
    void setup() {
      accessScope = AccessScope.ALL_CARE_PROVIDERS;
    }

    @Nested
    class Under18YearsAndSameUnitButRevisionNot0 {

      @BeforeEach
      void setup() {
        certificate = fk7809CertificateBuilder()
            .revision(new Revision(10))
            .certificateMetaData(
                CertificateMetaData.builder()
                    .issuingUnit(ALFA_MEDICINCENTRUM)
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
                    .build()
            )
            .build();
        actionEvaluation = ActionEvaluation.builder()
            .careUnit(ALFA_MEDICINCENTRUM)
            .subUnit(
                SubUnit.builder()
                    .hsaId(ALFA_MEDICINCENTRUM.hsaId())
                    .build()
            )
            .user(
                ajlaDoctorBuilder()
                    .accessScope(accessScope)
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
    class CertificateExistsPatientUnder18SameUnitAsIssuing {

      @BeforeEach
      void setup() {
        certificate = fk7809CertificateBuilder()
            .certificateMetaData(
                CertificateMetaData.builder()
                    .issuingUnit(ALFA_MEDICINCENTRUM)
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
                    .build()
            )
            .build();
        actionEvaluation = ActionEvaluation.builder()
            .careUnit(ALFA_MEDICINCENTRUM)
            .subUnit(
                SubUnit.builder()
                    .hsaId(ALFA_MEDICINCENTRUM.hsaId())
                    .build()
            )
            .user(
                ajlaDoctorBuilder()
                    .accessScope(accessScope)
                    .build()
            )
            .build();
      }

      @Test
      void shouldReturnTitle() {
        final var result = provider.of(certificate, actionEvaluation);

        assertEquals("Kontrollera att du använder dig av rätt läkarutlåtande", result.title());
      }

      @Test
      void shouldReturnAlert() {
        final var result = provider.of(certificate, actionEvaluation);

        assertEquals(
            "Du är på väg att utfärda Läkarutlåtande för merkostnadsersättning för First Middle Last - 20121212-1212.",
            result.alert().text());
      }

      @Test
      void shouldReturnText() {
        final var result = provider.of(certificate, actionEvaluation);

        assertEquals(
            "Läkarutlåtande för merkostnadsersättning är till för personer över 18 år som inte har en underhållsskyldig förälder. Om det gäller ett barn ska du istället använda Läkarutlåtande för omvårdnadsbidrag eller merkostnadsersättning.",
            result.text()
        );
      }

      @Test
      void shouldReturnCheckboxText() {
        final var result = provider.of(certificate, actionEvaluation);

        assertEquals(
            "Jag är säker på att jag vill utfärda Läkarutlåtande för merkostnadsersättning.",
            result.checkboxText()
        );
      }

      @Test
      void shouldReturnPrimaryAction() {
        final var result = provider.of(certificate, actionEvaluation);

        assertEquals(
            CertificateModalActionType.READ,
            result.primaryAction()
        );
      }

      @Test
      void shouldReturnSecondaryAction() {
        final var result = provider.of(certificate, actionEvaluation);

        assertEquals(
            CertificateModalActionType.DELETE,
            result.secondaryAction()
        );
      }
    }

    @Nested
    class CertificateDoesNotExistPatientUnder18 {

      @BeforeEach
      void setup() {
        certificate = null;
        actionEvaluation = ActionEvaluation.builder()
            .careUnit(ALFA_MEDICINCENTRUM)
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
            .user(
                ajlaDoctorBuilder()
                    .accessScope(accessScope)
                    .build()
            ).build();
      }

      @Test
      void shouldReturnTitle() {
        final var result = provider.of(certificate, actionEvaluation);

        assertEquals("Kontrollera att du använder dig av rätt läkarutlåtande", result.title());
      }

      @Test
      void shouldReturnAlert() {
        final var result = provider.of(certificate, actionEvaluation);

        assertEquals(
            "Du är på väg att utfärda Läkarutlåtande för merkostnadsersättning för First Middle Last - 20121212-1212.",
            result.alert().text());
      }

      @Test
      void shouldReturnText() {
        final var result = provider.of(certificate, actionEvaluation);

        assertEquals(
            "Läkarutlåtande för merkostnadsersättning är till för personer över 18 år som inte har en underhållsskyldig förälder. Om det gäller ett barn ska du istället använda Läkarutlåtande för omvårdnadsbidrag eller merkostnadsersättning.",
            result.text()
        );
      }

      @Test
      void shouldReturnCheckboxText() {
        final var result = provider.of(certificate, actionEvaluation);

        assertEquals(
            "Jag är säker på att jag vill utfärda Läkarutlåtande för merkostnadsersättning.",
            result.checkboxText()
        );
      }

      @Test
      void shouldReturnPrimaryAction() {
        final var result = provider.of(certificate, actionEvaluation);

        assertEquals(
            CertificateModalActionType.READ,
            result.primaryAction()
        );
      }

      @Test
      void shouldReturnSecondaryAction() {
        final var result = provider.of(certificate, actionEvaluation);

        assertEquals(
            CertificateModalActionType.DELETE,
            result.secondaryAction()
        );
      }
    }

    @Nested
    class PatientOver18 {

      @Test
      void shouldReturnNullIfCertificateExists() {
        certificate = fk7809CertificateBuilder()
            .certificateMetaData(
                CertificateMetaData.builder()
                    .issuingUnit(ALFA_MEDICINCENTRUM)
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
                                    .id(OLD_PATIENT_ID)
                                    .build()
                            )
                            .build()
                    )
                    .build()
            )
            .build();
        actionEvaluation = ActionEvaluation.builder()
            .careUnit(ALFA_MEDICINCENTRUM)
            .user(
                ajlaDoctorBuilder()
                    .accessScope(accessScope)
                    .build()
            ).build();

        assertNull(provider.of(certificate, actionEvaluation));
      }

      @Test
      void shouldReturnNullIfCertificateDoesNotExists() {
        actionEvaluation = ActionEvaluation.builder()
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
                            .id(OLD_PATIENT_ID)
                            .build()
                    )
                    .build()
            )
            .user(
                ajlaDoctorBuilder()
                    .accessScope(accessScope)
                    .build()
            ).build();

        assertNull(provider.of(certificate, actionEvaluation));
      }
    }

    @Nested
    class IssuedOnAnotherCareUnit {

      @Test
      void shouldReturnNullIfIssuedOnAnotherCareUnit() {
        certificate = fk7809CertificateBuilder()
            .certificateMetaData(
                CertificateMetaData.builder()
                    .issuingUnit(
                        CareUnit.builder()
                            .hsaId(new HsaId(HSA_ID))
                            .build()
                    )
                    .careUnit(
                        CareUnit.builder()
                            .hsaId(new HsaId(HSA_ID))
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
                    .build()
            )
            .build();
        actionEvaluation = ActionEvaluation.builder()
            .careUnit(ALFA_MEDICINCENTRUM)
            .subUnit(
                SubUnit.builder()
                    .hsaId(ALFA_MEDICINCENTRUM.hsaId())
                    .build()
            )
            .user(
                ajlaDoctorBuilder()
                    .accessScope(accessScope)
                    .build()
            ).build();

        assertNull(provider.of(certificate, actionEvaluation));
      }
    }

    @Test
    void shouldReturnNullIfCertificateIsLocked() {
      certificate = fk7809CertificateBuilder()
          .status(Status.LOCKED_DRAFT)
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
                                  .id(OLD_PATIENT_ID)
                                  .build()
                          )
                          .build()
                  )
                  .issuingUnit(ALFA_MEDICINCENTRUM)
                  .build()
          )
          .build();
      actionEvaluation = ActionEvaluation.builder()
          .careUnit(ALFA_MEDICINCENTRUM)
          .user(
              ajlaDoctorBuilder()
                  .accessScope(accessScope)
                  .build()
          ).build();

      assertNull(provider.of(certificate, actionEvaluation));
    }
  }
}