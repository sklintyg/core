package se.inera.intyg.certificateservice.domain.certificate.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static se.inera.intyg.certificateservice.domain.patient.model.PersonIdType.COORDINATION_NUMBER;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProvider.ALFA_REGIONEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProvider.BETA_REGIONEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProvider.alfaRegionenBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProviderConstants.BETA_REGIONEN_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProviderConstants.BETA_REGIONEN_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnit.ALFA_MEDICINCENTRUM;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnit.ALFA_VARDCENTRAL;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnit.alfaMedicincentrumBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_VARDCENTRAL_ADDRESS;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_VARDCENTRAL_CITY;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_VARDCENTRAL_EMAIL;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_VARDCENTRAL_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_VARDCENTRAL_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_VARDCENTRAL_PHONENUMBER;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_VARDCENTRAL_ZIP_CODE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.ALVE_REACT_ALFREDSSON;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.ATHENA_REACT_ANDERSSON;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.athenaReactAnderssonBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.DECEASED_TRUE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.PROTECTED_PERSON_TRUE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.TEST_INDICATED_TRUE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnit.ALFA_ALLERGIMOTTAGNINGEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnit.ALFA_HUDMOTTAGNINGEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnit.alfaAllergimottagningenBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_HUDMOTTAGNINGEN_ADDRESS;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_HUDMOTTAGNINGEN_CITY;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_HUDMOTTAGNINGEN_EMAIL;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_HUDMOTTAGNINGEN_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_HUDMOTTAGNINGEN_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_HUDMOTTAGNINGEN_PHONENUMBER;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_HUDMOTTAGNINGEN_ZIP_CODE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.INACTIVE_TRUE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUser.AJLA_DOKTOR;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUser.ajlaDoctorBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALVA_VARDADMINISTRATOR_HSA_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALVA_VARDADMINISTRATOR_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.BLOCKED_TRUE;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.action.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.model.CertificateAction;
import se.inera.intyg.certificateservice.domain.action.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.patient.model.PersonId;
import se.inera.intyg.certificateservice.domain.testdata.TestDataStaff;

@ExtendWith(MockitoExtension.class)
class CertificateTest {

  private Certificate certificate;
  private ActionEvaluation.ActionEvaluationBuilder actionEvaluationBuilder;

  @BeforeEach
  void setUp() {
    certificate = Certificate.builder()
        .id(new CertificateId("defaultCertificateId"))
        .certificateModel(mock(CertificateModel.class))
        .created(LocalDateTime.now(ZoneId.systemDefault()))
        .certificateMetaData(
            CertificateMetaData.builder()
                .patient(ATHENA_REACT_ANDERSSON)
                .issuer(TestDataStaff.AJLA_DOKTOR)
                .issuingUnit(ALFA_ALLERGIMOTTAGNINGEN)
                .careUnit(ALFA_MEDICINCENTRUM)
                .careProvider(ALFA_REGIONEN)
                .build()
        )
        .build();

    actionEvaluationBuilder = ActionEvaluation.builder()
        .patient(ATHENA_REACT_ANDERSSON)
        .user(AJLA_DOKTOR)
        .subUnit(ALFA_ALLERGIMOTTAGNINGEN)
        .careUnit(ALFA_MEDICINCENTRUM)
        .careProvider(ALFA_REGIONEN);
  }

  @Nested
  class UpdateMetaData {

    @Nested
    class UpdatePatient {

      @Test
      void shallUpdatePatientIdType() {
        certificate.updateMetadata(
            actionEvaluationBuilder
                .patient(
                    athenaReactAnderssonBuilder()
                        .id(
                            PersonId.builder()
                                .id(ATHENA_REACT_ANDERSSON_ID)
                                .type(COORDINATION_NUMBER)
                                .build()
                        )
                        .build()
                )
                .build()
        );

        assertEquals(COORDINATION_NUMBER,
            certificate.certificateMetaData().getPatient().getId().getType()
        );
      }

      @Test
      void shallUpdatePatientIdId() {
        certificate.updateMetadata(
            actionEvaluationBuilder
                .patient(
                    athenaReactAnderssonBuilder()
                        .id(ALVE_REACT_ALFREDSSON.getId())
                        .build()
                )
                .build()
        );

        assertEquals(ALVE_REACT_ALFREDSSON.getId().getId(),
            certificate.certificateMetaData().getPatient().getId().getId());
      }

      @Test
      void shallUpdatePatientFirstName() {
        certificate.updateMetadata(
            actionEvaluationBuilder
                .patient(
                    athenaReactAnderssonBuilder()
                        .name(ALVE_REACT_ALFREDSSON.getName())
                        .build()
                )
                .build()
        );

        assertEquals(ALVE_REACT_ALFREDSSON.getName().getFirstName(),
            certificate.certificateMetaData().getPatient().getName().getFirstName()
        );
      }

      @Test
      void shallUpdatePatientMiddleName() {
        certificate.updateMetadata(
            actionEvaluationBuilder
                .patient(
                    athenaReactAnderssonBuilder()
                        .name(ALVE_REACT_ALFREDSSON.getName())
                        .build()
                )
                .build()
        );

        assertEquals(ALVE_REACT_ALFREDSSON.getName().getMiddleName(),
            certificate.certificateMetaData().getPatient().getName().getMiddleName());
      }

      @Test
      void shallUpdatePatientLastName() {
        certificate.updateMetadata(
            actionEvaluationBuilder
                .patient(
                    athenaReactAnderssonBuilder()
                        .name(ALVE_REACT_ALFREDSSON.getName())
                        .build()
                )
                .build()
        );

        assertEquals(ALVE_REACT_ALFREDSSON.getName().getLastName(),
            certificate.certificateMetaData().getPatient().getName().getLastName());
      }

      @Test
      void shallUpdatePatientAddressCity() {
        certificate.updateMetadata(
            actionEvaluationBuilder
                .patient(
                    athenaReactAnderssonBuilder()
                        .address(ALVE_REACT_ALFREDSSON.getAddress())
                        .build()
                )
                .build()
        );

        assertEquals(ALVE_REACT_ALFREDSSON.getAddress().getCity(),
            certificate.certificateMetaData().getPatient().getAddress().getCity());
      }

      @Test
      void shallUpdatePatientAddressZipCode() {
        certificate.updateMetadata(
            actionEvaluationBuilder
                .patient(
                    athenaReactAnderssonBuilder()
                        .address(ALVE_REACT_ALFREDSSON.getAddress())
                        .build()
                )
                .build()
        );

        assertEquals(ALVE_REACT_ALFREDSSON.getAddress().getZipCode(),
            certificate.certificateMetaData().getPatient().getAddress().getZipCode());
      }

      @Test
      void shallUpdatePatientAddressStreet() {
        certificate.updateMetadata(
            actionEvaluationBuilder
                .patient(
                    athenaReactAnderssonBuilder()
                        .address(ALVE_REACT_ALFREDSSON.getAddress())
                        .build()
                )
                .build()
        );

        assertEquals(ALVE_REACT_ALFREDSSON.getAddress().getStreet(),
            certificate.certificateMetaData().getPatient().getAddress().getStreet());
      }

      @Test
      void shallUpdatePatientProtectedPerson() {
        certificate.updateMetadata(
            actionEvaluationBuilder
                .patient(
                    athenaReactAnderssonBuilder()
                        .protectedPerson(PROTECTED_PERSON_TRUE)
                        .build()
                )
                .build()
        );

        assertTrue(certificate.certificateMetaData().getPatient().getProtectedPerson().value());
      }

      @Test
      void shallUpdatePatientTestIndicated() {
        certificate.updateMetadata(
            actionEvaluationBuilder
                .patient(
                    athenaReactAnderssonBuilder()
                        .testIndicated(TEST_INDICATED_TRUE)
                        .build()
                )
                .build()
        );

        assertTrue(certificate.certificateMetaData().getPatient().getTestIndicated().value());
      }

      @Test
      void shallUpdatePatientDeceased() {
        certificate.updateMetadata(
            actionEvaluationBuilder
                .patient(
                    athenaReactAnderssonBuilder()
                        .deceased(DECEASED_TRUE)
                        .build()
                )
                .build()
        );

        assertTrue(certificate.certificateMetaData().getPatient().getDeceased().value());
      }

      @Test
      void shallNotUpdatePatientIfMissing() {
        final var patientBeforeUpdate = certificate.certificateMetaData().getPatient();

        final var actionEvaluation = actionEvaluationBuilder
            .patient(null)
            .build();

        certificate.updateMetadata(actionEvaluation);

        assertEquals(patientBeforeUpdate, certificate.certificateMetaData().getPatient());
      }
    }

    @Nested
    class UpdateIssuer {

      @Test
      void shallUpdateIssuerHsaId() {
        certificate.updateMetadata(
            actionEvaluationBuilder
                .user(
                    ajlaDoctorBuilder()
                        .hsaId(TestDataStaff.ALVA_VARDADMINISTRATOR.getHsaId())
                        .build()
                )
                .build()
        );

        assertEquals(ALVA_VARDADMINISTRATOR_HSA_ID,
            certificate.certificateMetaData().getIssuer().getHsaId().id());
      }

      @Test
      void shallUpdateIssuerName() {
        certificate.updateMetadata(
            actionEvaluationBuilder
                .user(
                    ajlaDoctorBuilder()
                        .name(TestDataStaff.ALVA_VARDADMINISTRATOR.getName())
                        .build()
                )
                .build()
        );

        assertEquals(ALVA_VARDADMINISTRATOR_NAME,
            certificate.certificateMetaData().getIssuer().getName().getLastName());
      }

      @Test
      void shallUpdateIssuerBlocked() {
        certificate.updateMetadata(
            actionEvaluationBuilder
                .user(
                    ajlaDoctorBuilder()
                        .blocked(BLOCKED_TRUE)
                        .build()
                )
                .build()
        );

        assertTrue(certificate.certificateMetaData().getIssuer().getBlocked().value());
      }
    }

    @Nested
    class UpdateCareUnit {

      @Test
      void shallUpdateCareUnitHsaId() {
        certificate.updateMetadata(
            actionEvaluationBuilder
                .careUnit(
                    alfaMedicincentrumBuilder()
                        .hsaId(ALFA_VARDCENTRAL.getHsaId())
                        .build()
                )
                .build()
        );

        assertEquals(ALFA_VARDCENTRAL_ID,
            certificate.certificateMetaData().getCareUnit().getHsaId().id());
      }

      @Test
      void shallUpdateCareUnitName() {
        certificate.updateMetadata(
            actionEvaluationBuilder
                .careUnit(
                    alfaMedicincentrumBuilder()
                        .name(ALFA_VARDCENTRAL.getName())
                        .build()
                )
                .build()
        );

        assertEquals(ALFA_VARDCENTRAL_NAME,
            certificate.certificateMetaData().getCareUnit().getName().name());
      }

      @Test
      void shallUpdateCareUnitAddress() {
        certificate.updateMetadata(
            actionEvaluationBuilder
                .careUnit(
                    alfaMedicincentrumBuilder()
                        .address(ALFA_VARDCENTRAL.getAddress())
                        .build()
                )
                .build()
        );

        assertEquals(ALFA_VARDCENTRAL_ADDRESS,
            certificate.certificateMetaData().getCareUnit().getAddress().getAddress());
      }

      @Test
      void shallUpdateCareUnitCity() {
        certificate.updateMetadata(
            actionEvaluationBuilder
                .careUnit(
                    alfaMedicincentrumBuilder()
                        .address(ALFA_VARDCENTRAL.getAddress())
                        .build()
                )
                .build()
        );

        assertEquals(ALFA_VARDCENTRAL_CITY,
            certificate.certificateMetaData().getCareUnit().getAddress().getCity());
      }

      @Test
      void shallUpdateCareUnitZipCode() {
        certificate.updateMetadata(
            actionEvaluationBuilder
                .careUnit(
                    alfaMedicincentrumBuilder()
                        .address(ALFA_VARDCENTRAL.getAddress())
                        .build()
                )
                .build()
        );

        assertEquals(ALFA_VARDCENTRAL_ZIP_CODE,
            certificate.certificateMetaData().getCareUnit().getAddress().getZipCode());
      }

      @Test
      void shallUpdateCareUnitEmail() {
        certificate.updateMetadata(
            actionEvaluationBuilder
                .careUnit(
                    alfaMedicincentrumBuilder()
                        .contactInfo(ALFA_VARDCENTRAL.getContactInfo())
                        .build()
                )
                .build()
        );

        assertEquals(ALFA_VARDCENTRAL_EMAIL,
            certificate.certificateMetaData().getCareUnit().getContactInfo().getEmail());
      }

      @Test
      void shallUpdateCareUnitPhoneNumber() {
        certificate.updateMetadata(
            actionEvaluationBuilder
                .careUnit(
                    alfaMedicincentrumBuilder()
                        .contactInfo(ALFA_VARDCENTRAL.getContactInfo())
                        .build()
                )
                .build()
        );

        assertEquals(ALFA_VARDCENTRAL_PHONENUMBER,
            certificate.certificateMetaData().getCareUnit().getContactInfo().getPhoneNumber());
      }

      @Test
      void shallUpdateCareUnitInactive() {
        certificate.updateMetadata(
            actionEvaluationBuilder
                .careUnit(
                    alfaMedicincentrumBuilder()
                        .inactive(INACTIVE_TRUE)
                        .build()
                )
                .build()
        );

        assertTrue(certificate.certificateMetaData().getCareUnit().getInactive().value());
      }
    }

    @Nested
    class UpdateCareProvider {

      @Test
      void shallUpdateCareProviderHsaId() {
        certificate.updateMetadata(
            actionEvaluationBuilder
                .careProvider(
                    alfaRegionenBuilder()
                        .hsaId(BETA_REGIONEN.getHsaId())
                        .build()
                )
                .build()
        );

        assertEquals(BETA_REGIONEN_ID,
            certificate.certificateMetaData().getCareProvider().getHsaId().id());
      }

      @Test
      void shallUpdateCareProviderName() {
        certificate.updateMetadata(
            actionEvaluationBuilder
                .careProvider(
                    alfaRegionenBuilder()
                        .name(BETA_REGIONEN.getName())
                        .build()
                )
                .build()
        );

        assertEquals(BETA_REGIONEN_NAME,
            certificate.certificateMetaData().getCareProvider().getName().name());
      }
    }

    @Nested
    class UpdateIssuingUnit {

      @Test
      void shallUpdateIssuingUnitHsaId() {
        certificate.updateMetadata(
            actionEvaluationBuilder
                .subUnit(
                    alfaAllergimottagningenBuilder()
                        .hsaId(ALFA_HUDMOTTAGNINGEN.getHsaId())
                        .build()
                )
                .build()
        );

        assertEquals(ALFA_HUDMOTTAGNINGEN_ID,
            certificate.certificateMetaData().getIssuingUnit().getHsaId().id());
      }

      @Test
      void shallUpdateIssuingUnitName() {
        certificate.updateMetadata(
            actionEvaluationBuilder
                .subUnit(
                    alfaAllergimottagningenBuilder()
                        .name(ALFA_HUDMOTTAGNINGEN.getName())
                        .build()
                )
                .build()
        );

        assertEquals(ALFA_HUDMOTTAGNINGEN_NAME,
            certificate.certificateMetaData().getIssuingUnit().getName().name());
      }

      @Test
      void shallUpdateIssuingUnitAddress() {
        certificate.updateMetadata(
            actionEvaluationBuilder
                .subUnit(
                    alfaAllergimottagningenBuilder()
                        .address(ALFA_HUDMOTTAGNINGEN.getAddress())
                        .build()
                )
                .build()
        );

        assertEquals(ALFA_HUDMOTTAGNINGEN_ADDRESS,
            certificate.certificateMetaData().getIssuingUnit().getAddress().getAddress());
      }

      @Test
      void shallUpdateIssuingUnitCity() {
        certificate.updateMetadata(
            actionEvaluationBuilder
                .subUnit(
                    alfaAllergimottagningenBuilder()
                        .address(ALFA_HUDMOTTAGNINGEN.getAddress())
                        .build()
                )
                .build()
        );

        assertEquals(ALFA_HUDMOTTAGNINGEN_CITY,
            certificate.certificateMetaData().getIssuingUnit().getAddress().getCity());
      }

      @Test
      void shallUpdateIssuingUnitZipCode() {
        certificate.updateMetadata(
            actionEvaluationBuilder
                .subUnit(
                    alfaAllergimottagningenBuilder()
                        .address(ALFA_HUDMOTTAGNINGEN.getAddress())
                        .build()
                )
                .build()
        );

        assertEquals(ALFA_HUDMOTTAGNINGEN_ZIP_CODE,
            certificate.certificateMetaData().getIssuingUnit().getAddress().getZipCode());
      }

      @Test
      void shallUpdateIssuingUnitEmail() {
        certificate.updateMetadata(
            actionEvaluationBuilder
                .subUnit(
                    alfaAllergimottagningenBuilder()
                        .contactInfo(ALFA_HUDMOTTAGNINGEN.getContactInfo())
                        .build()
                )
                .build()
        );

        assertEquals(ALFA_HUDMOTTAGNINGEN_EMAIL,
            certificate.certificateMetaData().getIssuingUnit().getContactInfo().getEmail());
      }

      @Test
      void shallUpdateIssuingUnitPhoneNumber() {
        certificate.updateMetadata(
            actionEvaluationBuilder
                .subUnit(
                    alfaAllergimottagningenBuilder()
                        .contactInfo(ALFA_HUDMOTTAGNINGEN.getContactInfo())
                        .build()
                )
                .build()
        );

        assertEquals(ALFA_HUDMOTTAGNINGEN_PHONENUMBER,
            certificate.certificateMetaData().getIssuingUnit().getContactInfo().getPhoneNumber());
      }

      @Test
      void shallUpdateIssuingUnitInactive() {
        certificate.updateMetadata(
            actionEvaluationBuilder
                .subUnit(
                    alfaAllergimottagningenBuilder()
                        .inactive(INACTIVE_TRUE)
                        .build()
                )
                .build()
        );

        assertTrue(certificate.certificateMetaData().getIssuingUnit().getInactive().value());
      }
    }

    @Nested
    class TestActions {

      @Test
      void shallReturnActionIfEvaluateTrue() {
        final var actionEvaluation = ActionEvaluation.builder().build();
        final var certificateAction = mock(CertificateAction.class);
        final var expectedActions = List.of(certificateAction);

        doReturn(expectedActions).when(certificate.certificateModel()).actions();

        doReturn(true).when(certificateAction).evaluate(Optional.of(certificate), actionEvaluation);

        final var actualActions = certificate.actions(actionEvaluation);

        assertEquals(expectedActions, actualActions);
      }

      @Test
      void shallNotReturnActionIfEvaluateFalse() {
        final var actionEvaluation = ActionEvaluation.builder().build();
        final var certificateAction = mock(CertificateAction.class);
        final var expectedActions = Collections.emptyList();

        doReturn(List.of(certificateAction)).when(certificate.certificateModel()).actions();

        doReturn(false).when(certificateAction)
            .evaluate(Optional.of(certificate), actionEvaluation);

        final var actualActions = certificate.actions(actionEvaluation);

        assertEquals(expectedActions, actualActions);
      }
    }

    @Nested
    class TestAllowTo {

      @Test
      void shallReturnTrueIfExistsAndEvaluateTrue() {
        final var actionEvaluation = ActionEvaluation.builder().build();
        final var certificateAction = mock(CertificateAction.class);
        final var actions = List.of(certificateAction);

        doReturn(actions).when(certificate.certificateModel()).actions();

        doReturn(CertificateActionType.READ).when(certificateAction).getType();
        doReturn(true).when(certificateAction).evaluate(Optional.of(certificate), actionEvaluation);

        assertTrue(
            certificate.allowTo(CertificateActionType.READ, actionEvaluation),
            "Expected allowTo to return 'true'"
        );
      }

      @Test
      void shallReturnFalseIfExistsAndEvaluateFalse() {
        final var actionEvaluation = ActionEvaluation.builder().build();
        final var certificateAction = mock(CertificateAction.class);
        final var actions = List.of(certificateAction);

        doReturn(actions).when(certificate.certificateModel()).actions();

        doReturn(CertificateActionType.READ).when(certificateAction).getType();
        doReturn(false).when(certificateAction)
            .evaluate(Optional.of(certificate), actionEvaluation);

        assertFalse(
            certificate.allowTo(CertificateActionType.READ, actionEvaluation),
            "Expected allowTo to return 'false'"
        );
      }

      @Test
      void shallReturnFalseIfNotExists() {
        final var actionEvaluation = ActionEvaluation.builder().build();
        final var certificateAction = mock(CertificateAction.class);
        final var actions = List.of(certificateAction);

        doReturn(actions).when(certificate.certificateModel()).actions();

        doReturn(CertificateActionType.CREATE).when(certificateAction).getType();

        assertFalse(
            certificate.allowTo(CertificateActionType.READ, actionEvaluation),
            "Expected allowTo to return 'false'"
        );
      }
    }
  }
}