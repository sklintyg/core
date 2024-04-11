package se.inera.intyg.certificateservice.domain.certificate.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
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
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.CERTIFICATE_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.RECIPIENT;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.REVISION;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.XML;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataElementData.DATE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataElementData.dateElementDataBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataElementDataConstants.DATE_ELEMENT_VALUE_DATE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.ATHENA_REACT_ANDERSSON;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.ATLAS_REACT_ABRAHAMSSON;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.athenaReactAnderssonBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.DECEASED_TRUE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.PROTECTED_PERSON_TRUE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.TEST_INDICATED_TRUE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataStaff.ALVA_VARDADMINISTRATOR;
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
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.BLOCKED_TRUE;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.action.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.model.CertificateAction;
import se.inera.intyg.certificateservice.domain.action.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.service.XmlGenerator;
import se.inera.intyg.certificateservice.domain.certificate.service.XmlSchemaValidator;
import se.inera.intyg.certificateservice.domain.certificate.service.XmlSchematronValidator;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.common.exception.ConcurrentModificationException;
import se.inera.intyg.certificateservice.domain.common.model.RevokedInformation;
import se.inera.intyg.certificateservice.domain.patient.model.PersonId;
import se.inera.intyg.certificateservice.domain.testdata.TestDataStaff;
import se.inera.intyg.certificateservice.domain.validation.model.ErrorMessage;
import se.inera.intyg.certificateservice.domain.validation.model.ValidationError;
import se.inera.intyg.certificateservice.domain.validation.model.ValidationResult;

@ExtendWith(MockitoExtension.class)
class CertificateTest {

  private Certificate certificate;
  private Certificate.CertificateBuilder certificateBuilder;
  private CertificateModel certificateModel;
  private ActionEvaluation.ActionEvaluationBuilder actionEvaluationBuilder;

  @Mock
  private XmlGenerator xmlGenerator = mock(XmlGenerator.class);
  @Mock
  private XmlSchemaValidator xmlSchemaValidator = mock(XmlSchemaValidator.class);
  @Mock
  private XmlSchematronValidator xmlSchematronValidator = mock(XmlSchematronValidator.class);

  @BeforeEach
  void setUp() {
    certificateModel = mock(CertificateModel.class);
    certificateBuilder = Certificate.builder()
        .id(CERTIFICATE_ID)
        .revision(REVISION)
        .certificateModel(certificateModel)
        .created(LocalDateTime.now(ZoneId.systemDefault()))
        .status(Status.DRAFT)
        .certificateMetaData(
            CertificateMetaData.builder()
                .patient(ATHENA_REACT_ANDERSSON)
                .issuer(TestDataStaff.AJLA_DOKTOR)
                .issuingUnit(ALFA_ALLERGIMOTTAGNINGEN)
                .careUnit(ALFA_MEDICINCENTRUM)
                .careProvider(ALFA_REGIONEN)
                .build()
        )
        .elementData(
            List.of(DATE)
        );

    certificate = certificateBuilder.build();

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
            certificate.certificateMetaData().patient().id().type()
        );
      }

      @Test
      void shallUpdatePatientIdId() {
        certificate.updateMetadata(
            actionEvaluationBuilder
                .patient(
                    athenaReactAnderssonBuilder()
                        .id(ATLAS_REACT_ABRAHAMSSON.id())
                        .build()
                )
                .build()
        );

        assertEquals(ATLAS_REACT_ABRAHAMSSON.id().id(),
            certificate.certificateMetaData().patient().id().id());
      }

      @Test
      void shallUpdatePatientFirstName() {
        certificate.updateMetadata(
            actionEvaluationBuilder
                .patient(
                    athenaReactAnderssonBuilder()
                        .name(ATLAS_REACT_ABRAHAMSSON.name())
                        .build()
                )
                .build()
        );

        assertEquals(ATLAS_REACT_ABRAHAMSSON.name().firstName(),
            certificate.certificateMetaData().patient().name().firstName()
        );
      }

      @Test
      void shallUpdatePatientMiddleName() {
        certificate.updateMetadata(
            actionEvaluationBuilder
                .patient(
                    athenaReactAnderssonBuilder()
                        .name(ATLAS_REACT_ABRAHAMSSON.name())
                        .build()
                )
                .build()
        );

        assertEquals(ATLAS_REACT_ABRAHAMSSON.name().middleName(),
            certificate.certificateMetaData().patient().name().middleName());
      }

      @Test
      void shallUpdatePatientLastName() {
        certificate.updateMetadata(
            actionEvaluationBuilder
                .patient(
                    athenaReactAnderssonBuilder()
                        .name(ATLAS_REACT_ABRAHAMSSON.name())
                        .build()
                )
                .build()
        );

        assertEquals(ATLAS_REACT_ABRAHAMSSON.name().lastName(),
            certificate.certificateMetaData().patient().name().lastName());
      }

      @Test
      void shallUpdatePatientAddressCity() {
        certificate.updateMetadata(
            actionEvaluationBuilder
                .patient(
                    athenaReactAnderssonBuilder()
                        .address(ATLAS_REACT_ABRAHAMSSON.address())
                        .build()
                )
                .build()
        );

        assertEquals(ATLAS_REACT_ABRAHAMSSON.address().city(),
            certificate.certificateMetaData().patient().address().city());
      }

      @Test
      void shallUpdatePatientAddressZipCode() {
        certificate.updateMetadata(
            actionEvaluationBuilder
                .patient(
                    athenaReactAnderssonBuilder()
                        .address(ATLAS_REACT_ABRAHAMSSON.address())
                        .build()
                )
                .build()
        );

        assertEquals(ATLAS_REACT_ABRAHAMSSON.address().zipCode(),
            certificate.certificateMetaData().patient().address().zipCode());
      }

      @Test
      void shallUpdatePatientAddressStreet() {
        certificate.updateMetadata(
            actionEvaluationBuilder
                .patient(
                    athenaReactAnderssonBuilder()
                        .address(ATLAS_REACT_ABRAHAMSSON.address())
                        .build()
                )
                .build()
        );

        assertEquals(ATLAS_REACT_ABRAHAMSSON.address().street(),
            certificate.certificateMetaData().patient().address().street());
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

        assertTrue(certificate.certificateMetaData().patient().protectedPerson().value());
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

        assertTrue(certificate.certificateMetaData().patient().testIndicated().value());
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

        assertTrue(certificate.certificateMetaData().patient().deceased().value());
      }

      @Test
      void shallNotUpdatePatientIfMissing() {
        final var patientBeforeUpdate = certificate.certificateMetaData().patient();

        final var actionEvaluation = actionEvaluationBuilder
            .patient(null)
            .build();

        certificate.updateMetadata(actionEvaluation);

        assertEquals(patientBeforeUpdate, certificate.certificateMetaData().patient());
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
                        .hsaId(ALVA_VARDADMINISTRATOR.hsaId())
                        .build()
                )
                .build()
        );

        assertEquals(ALVA_VARDADMINISTRATOR_HSA_ID,
            certificate.certificateMetaData().issuer().hsaId().id());
      }

      @Test
      void shallUpdateIssuerName() {
        certificate.updateMetadata(
            actionEvaluationBuilder
                .user(
                    ajlaDoctorBuilder()
                        .name(ALVA_VARDADMINISTRATOR.name())
                        .build()
                )
                .build()
        );

        assertEquals(ALVA_VARDADMINISTRATOR.name(),
            certificate.certificateMetaData().issuer().name());
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

        assertTrue(certificate.certificateMetaData().issuer().blocked().value());
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
                        .hsaId(ALFA_VARDCENTRAL.hsaId())
                        .build()
                )
                .build()
        );

        assertEquals(ALFA_VARDCENTRAL_ID,
            certificate.certificateMetaData().careUnit().hsaId().id());
      }

      @Test
      void shallUpdateCareUnitName() {
        certificate.updateMetadata(
            actionEvaluationBuilder
                .careUnit(
                    alfaMedicincentrumBuilder()
                        .name(ALFA_VARDCENTRAL.name())
                        .build()
                )
                .build()
        );

        assertEquals(ALFA_VARDCENTRAL_NAME,
            certificate.certificateMetaData().careUnit().name().name());
      }

      @Test
      void shallUpdateCareUnitAddress() {
        certificate.updateMetadata(
            actionEvaluationBuilder
                .careUnit(
                    alfaMedicincentrumBuilder()
                        .address(ALFA_VARDCENTRAL.address())
                        .build()
                )
                .build()
        );

        assertEquals(ALFA_VARDCENTRAL_ADDRESS,
            certificate.certificateMetaData().careUnit().address().address());
      }

      @Test
      void shallUpdateCareUnitCity() {
        certificate.updateMetadata(
            actionEvaluationBuilder
                .careUnit(
                    alfaMedicincentrumBuilder()
                        .address(ALFA_VARDCENTRAL.address())
                        .build()
                )
                .build()
        );

        assertEquals(ALFA_VARDCENTRAL_CITY,
            certificate.certificateMetaData().careUnit().address().city());
      }

      @Test
      void shallUpdateCareUnitZipCode() {
        certificate.updateMetadata(
            actionEvaluationBuilder
                .careUnit(
                    alfaMedicincentrumBuilder()
                        .address(ALFA_VARDCENTRAL.address())
                        .build()
                )
                .build()
        );

        assertEquals(ALFA_VARDCENTRAL_ZIP_CODE,
            certificate.certificateMetaData().careUnit().address().zipCode());
      }

      @Test
      void shallUpdateCareUnitEmail() {
        certificate.updateMetadata(
            actionEvaluationBuilder
                .careUnit(
                    alfaMedicincentrumBuilder()
                        .contactInfo(ALFA_VARDCENTRAL.contactInfo())
                        .build()
                )
                .build()
        );

        assertEquals(ALFA_VARDCENTRAL_EMAIL,
            certificate.certificateMetaData().careUnit().contactInfo().email());
      }

      @Test
      void shallUpdateCareUnitPhoneNumber() {
        certificate.updateMetadata(
            actionEvaluationBuilder
                .careUnit(
                    alfaMedicincentrumBuilder()
                        .contactInfo(ALFA_VARDCENTRAL.contactInfo())
                        .build()
                )
                .build()
        );

        assertEquals(ALFA_VARDCENTRAL_PHONENUMBER,
            certificate.certificateMetaData().careUnit().contactInfo().phoneNumber());
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

        assertTrue(certificate.certificateMetaData().careUnit().inactive().value());
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
                        .hsaId(BETA_REGIONEN.hsaId())
                        .build()
                )
                .build()
        );

        assertEquals(BETA_REGIONEN_ID,
            certificate.certificateMetaData().careProvider().hsaId().id());
      }

      @Test
      void shallUpdateCareProviderName() {
        certificate.updateMetadata(
            actionEvaluationBuilder
                .careProvider(
                    alfaRegionenBuilder()
                        .name(BETA_REGIONEN.name())
                        .build()
                )
                .build()
        );

        assertEquals(BETA_REGIONEN_NAME,
            certificate.certificateMetaData().careProvider().name().name());
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
                        .hsaId(ALFA_HUDMOTTAGNINGEN.hsaId())
                        .build()
                )
                .build()
        );

        assertEquals(ALFA_HUDMOTTAGNINGEN_ID,
            certificate.certificateMetaData().issuingUnit().hsaId().id());
      }

      @Test
      void shallUpdateIssuingUnitName() {
        certificate.updateMetadata(
            actionEvaluationBuilder
                .subUnit(
                    alfaAllergimottagningenBuilder()
                        .name(ALFA_HUDMOTTAGNINGEN.name())
                        .build()
                )
                .build()
        );

        assertEquals(ALFA_HUDMOTTAGNINGEN_NAME,
            certificate.certificateMetaData().issuingUnit().name().name());
      }

      @Test
      void shallUpdateIssuingUnitAddress() {
        certificate.updateMetadata(
            actionEvaluationBuilder
                .subUnit(
                    alfaAllergimottagningenBuilder()
                        .address(ALFA_HUDMOTTAGNINGEN.address())
                        .build()
                )
                .build()
        );

        assertEquals(ALFA_HUDMOTTAGNINGEN_ADDRESS,
            certificate.certificateMetaData().issuingUnit().address().address());
      }

      @Test
      void shallUpdateIssuingUnitCity() {
        certificate.updateMetadata(
            actionEvaluationBuilder
                .subUnit(
                    alfaAllergimottagningenBuilder()
                        .address(ALFA_HUDMOTTAGNINGEN.address())
                        .build()
                )
                .build()
        );

        assertEquals(ALFA_HUDMOTTAGNINGEN_CITY,
            certificate.certificateMetaData().issuingUnit().address().city());
      }

      @Test
      void shallUpdateIssuingUnitZipCode() {
        certificate.updateMetadata(
            actionEvaluationBuilder
                .subUnit(
                    alfaAllergimottagningenBuilder()
                        .address(ALFA_HUDMOTTAGNINGEN.address())
                        .build()
                )
                .build()
        );

        assertEquals(ALFA_HUDMOTTAGNINGEN_ZIP_CODE,
            certificate.certificateMetaData().issuingUnit().address().zipCode());
      }

      @Test
      void shallUpdateIssuingUnitEmail() {
        certificate.updateMetadata(
            actionEvaluationBuilder
                .subUnit(
                    alfaAllergimottagningenBuilder()
                        .contactInfo(ALFA_HUDMOTTAGNINGEN.contactInfo())
                        .build()
                )
                .build()
        );

        assertEquals(ALFA_HUDMOTTAGNINGEN_EMAIL,
            certificate.certificateMetaData().issuingUnit().contactInfo().email());
      }

      @Test
      void shallUpdateIssuingUnitPhoneNumber() {
        certificate.updateMetadata(
            actionEvaluationBuilder
                .subUnit(
                    alfaAllergimottagningenBuilder()
                        .contactInfo(ALFA_HUDMOTTAGNINGEN.contactInfo())
                        .build()
                )
                .build()
        );

        assertEquals(ALFA_HUDMOTTAGNINGEN_PHONENUMBER,
            certificate.certificateMetaData().issuingUnit().contactInfo().phoneNumber());
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

        assertTrue(certificate.certificateMetaData().issuingUnit().inactive().value());
      }
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
      final var actionEvaluation = actionEvaluationBuilder.build();
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
      final var actionEvaluation = actionEvaluationBuilder.build();
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
      final var actionEvaluation = actionEvaluationBuilder.build();
      final var certificateAction = mock(CertificateAction.class);
      final var actions = List.of(certificateAction);

      doReturn(actions).when(certificate.certificateModel()).actions();

      doReturn(CertificateActionType.CREATE).when(certificateAction).getType();

      assertFalse(
          certificate.allowTo(CertificateActionType.READ, actionEvaluation),
          "Expected allowTo to return 'false'"
      );
    }

    @Test
    void shallUseCertificatePatientIfPatientNotPresentInActionEvalutaion() {
      final var actionEvaluation = actionEvaluationBuilder.patient(null).build();
      final var actionEvaluationArgumentCaptor = ArgumentCaptor.forClass(ActionEvaluation.class);

      final var certificateAction = mock(CertificateAction.class);
      final var actions = List.of(certificateAction);

      doReturn(actions).when(certificate.certificateModel()).actions();
      doReturn(CertificateActionType.DELETE).when(certificateAction).getType();

      certificate.allowTo(CertificateActionType.DELETE, actionEvaluation);

      verify(certificateAction).evaluate(any(Optional.class),
          actionEvaluationArgumentCaptor.capture());

      assertEquals(ATHENA_REACT_ANDERSSON, actionEvaluationArgumentCaptor.getValue().patient());
    }
  }

  @Nested
  class TestUpdateData {

    @Test
    void shallUpdateDataIfChanged() {
      final var newValue = List.of(
          dateElementDataBuilder()
              .value(
                  ElementValueDate.builder()
                      .date(DATE_ELEMENT_VALUE_DATE.plusDays(1))
                      .build()
              )
              .build()
      );
      doReturn(true).when(certificateModel).elementSpecificationExists(DATE.id());
      certificate.updateData(newValue, REVISION, actionEvaluationBuilder.build());

      assertEquals(newValue, certificate.elementData());
    }

    @Test
    void shallUpdateDataIfChangedAndHaveOwnCopyOfList() {
      final var newValue = new ArrayList<>(
          List.of(
              dateElementDataBuilder()
                  .value(
                      ElementValueDate.builder()
                          .date(DATE_ELEMENT_VALUE_DATE.plusDays(1))
                          .build()
                  )
                  .build()
          )
      );
      doReturn(true).when(certificateModel).elementSpecificationExists(DATE.id());
      certificate.updateData(newValue, REVISION, actionEvaluationBuilder.build());

      newValue.remove(0);

      assertNotEquals(newValue, certificate.elementData());
    }

    @Test
    void shallIncrementOnUpdateData() {
      final var newValue = List.of(
          dateElementDataBuilder()
              .value(
                  ElementValueDate.builder()
                      .date(DATE_ELEMENT_VALUE_DATE.plusDays(1))
                      .build()
              )
              .build()
      );
      doReturn(true).when(certificateModel).elementSpecificationExists(DATE.id());
      certificate.updateData(newValue, REVISION, actionEvaluationBuilder.build());

      long version = 0L;
      assertEquals(version + 1, certificate.revision().value());
    }

    @Test
    void shallThrowExceptionIfElementIdDontExists() {
      final var actionEvaluation = actionEvaluationBuilder.build();
      final var newValue = List.of(
          DATE,
          dateElementDataBuilder()
              .id(new ElementId("NOT_EXISTS"))
              .build()
      );

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> certificate.updateData(newValue, REVISION, actionEvaluation)
      );

      assertTrue(illegalArgumentException.getMessage().contains("NOT_EXISTS"),
          () -> "Expected to contain 'NOT_EXISTS' in exception message '%s'"
              .formatted(illegalArgumentException.getMessage())
      );
    }

    @Test
    void shallThrowExceptionIfRevisionDontMatch() {
      final var actionEvaluation = actionEvaluationBuilder.build();
      final var revision = new Revision(1);
      final var newValue = List.of(
          dateElementDataBuilder()
              .value(
                  ElementValueDate.builder()
                      .date(DATE_ELEMENT_VALUE_DATE.plusDays(1))
                      .build()
              )
              .build()
      );

      final var concurrentModificationException = assertThrows(
          ConcurrentModificationException.class,
          () -> certificate.updateData(newValue, revision, actionEvaluation)
      );

      assertTrue(concurrentModificationException.getMessage().contains("Incorrect revision"),
          () -> "Received message was: %s".formatted(concurrentModificationException.getMessage())
      );
    }
  }

  @Nested
  class TestDelete {

    @Test
    void shallThrowExceptionIfRevisionDontMatch() {
      final var actionEvaluation = actionEvaluationBuilder.build();
      final var revision = new Revision(2);
      final var concurrentModificationException = assertThrows(
          ConcurrentModificationException.class,
          () -> certificate.delete(revision, actionEvaluation)
      );
      assertTrue(concurrentModificationException.getMessage().contains("Incorrect revision"),
          () -> "Received message was: %s".formatted(concurrentModificationException.getMessage())
      );
    }

    @Test
    void shallThrowExceptionIfStatusDoesntMatchDraft() {
      final var actionEvaluation = actionEvaluationBuilder.build();
      final var deletedCertificate = certificateBuilder
          .status(Status.DELETED_DRAFT)
          .build();

      final var illegalStateException = assertThrows(IllegalStateException.class,
          () -> deletedCertificate.delete(REVISION, actionEvaluation)
      );

      assertTrue(illegalStateException.getMessage().contains("Incorrect status"),
          () -> "Received message was: %s".formatted(illegalStateException.getMessage())
      );
    }

    @Test
    void shallReturnStateDeleteDraftWhenDeleted() {
      certificate.delete(REVISION, actionEvaluationBuilder.build());
      assertEquals(Status.DELETED_DRAFT, certificate.status());
    }
  }

  @Nested
  class TestValidate {

    @Test
    void shallReturnValidationResultWithNoErrors() {
      final var expectedResult = ValidationResult.builder()
          .errors(Collections.emptyList())
          .build();

      final var specification = mock(ElementSpecification.class);
      doReturn(Collections.emptyList()).when(specification)
          .validate(certificate.elementData(), Optional.empty());
      doReturn(List.of(specification)).when(certificateModel).elementSpecifications();

      final var actualResult = certificate.validate();
      assertEquals(expectedResult, actualResult);
    }

    @Test
    void shallReturnValidationResultWithErrors() {
      final var validationErrors = List.of(ValidationError.builder().build());
      final var expectedResult = ValidationResult.builder()
          .errors(validationErrors)
          .build();

      final var specification = mock(ElementSpecification.class);
      doReturn(validationErrors).when(specification)
          .validate(certificate.elementData(), Optional.empty());
      doReturn(List.of(specification)).when(certificateModel).elementSpecifications();

      final var actualResult = certificate.validate();
      assertEquals(expectedResult, actualResult);
    }

    @Test
    void shallReturnValidationResultWithErrorsFromMultipleSpecifications() {
      final var validationErrorOne = ValidationError.builder()
          .message(new ErrorMessage("ErrorOne"))
          .build();
      final var validationErrorTwo = ValidationError.builder()
          .message(new ErrorMessage("ErrorTwo"))
          .build();

      final var expectedResult = ValidationResult.builder()
          .errors(List.of(validationErrorOne, validationErrorTwo))
          .build();

      final var specificationOne = mock(ElementSpecification.class);
      final var specificationTwo = mock(ElementSpecification.class);
      final var validationErrorsOne = List.of(validationErrorOne);
      final var validationErrorsTwo = List.of(validationErrorTwo);
      doReturn(validationErrorsOne).when(specificationOne)
          .validate(certificate.elementData(), Optional.empty());
      doReturn(validationErrorsTwo).when(specificationTwo)
          .validate(certificate.elementData(), Optional.empty());
      doReturn(List.of(specificationOne, specificationTwo)).when(certificateModel)
          .elementSpecifications();

      final var actualResult = certificate.validate();
      assertEquals(expectedResult, actualResult);
    }

    @Test
    void shallValidateWithProvidedElementData() {
      final var expectedElementData = List.of(ElementData.builder().build());

      final var specification = mock(ElementSpecification.class);
      doReturn(Collections.emptyList()).when(specification)
          .validate(expectedElementData, Optional.empty());
      doReturn(List.of(specification)).when(certificateModel).elementSpecifications();

      certificate.validate(expectedElementData);

      final ArgumentCaptor<List<ElementData>> listArgumentCaptor = ArgumentCaptor.forClass(
          List.class);
      verify(specification).validate(listArgumentCaptor.capture(), eq(Optional.empty()));

      assertEquals(expectedElementData, listArgumentCaptor.getValue());
    }
  }

  @Nested
  class TestIsDraft {

    @Test
    void shallReturnTrueIfStatusIsDraft() {
      assertTrue(certificate.isDraft());
    }

    @Test
    void shallReturnFalseIfStatusIsNotDraft() {
      final var certificateWithStatusDeleted = certificateBuilder
          .status(Status.DELETED_DRAFT)
          .build();
      assertFalse(certificateWithStatusDeleted.isDraft());
    }
  }

  @Nested
  class TestSign {

    private final Signature SIGNATURE = new Signature("Signature");

    @Test
    void shallThrowExceptionIfRevisionDontMatch() {
      doReturn(true).when(xmlSchematronValidator).validate(certificate);
      doReturn(true).when(xmlSchemaValidator).validate(certificate);
      final var actionEvaluation = actionEvaluationBuilder.build();
      final var revision = new Revision(2);
      final var concurrentModificationException = assertThrows(
          ConcurrentModificationException.class,
          () -> certificate.sign(xmlGenerator, SIGNATURE, revision, actionEvaluation,
              xmlSchematronValidator, xmlSchemaValidator)
      );
      assertTrue(concurrentModificationException.getMessage().contains("Incorrect revision"),
          () -> "Received message was: %s".formatted(concurrentModificationException.getMessage())
      );
    }

    @Test
    void shallThrowExceptionIfStatusDoesntMatchDraft() {
      final var actionEvaluation = actionEvaluationBuilder.build();
      final var deletedCertificate = certificateBuilder
          .status(Status.DELETED_DRAFT)
          .build();

      doReturn(true).when(xmlSchematronValidator).validate(deletedCertificate);
      doReturn(true).when(xmlSchemaValidator).validate(deletedCertificate);

      final var illegalStateException = assertThrows(IllegalStateException.class,
          () -> deletedCertificate.sign(xmlGenerator, SIGNATURE, REVISION, actionEvaluation,
              xmlSchematronValidator, xmlSchemaValidator)
      );

      assertTrue(illegalStateException.getMessage().contains("Incorrect status"),
          () -> "Received message was: %s".formatted(illegalStateException.getMessage())
      );
    }

    @Test
    void shallThrowExceptionIfSignatureIsNull() {
      final var actionEvaluation = actionEvaluationBuilder.build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> certificate.sign(xmlGenerator, null, REVISION, actionEvaluation,
              xmlSchematronValidator, xmlSchemaValidator)
      );

      assertTrue(illegalArgumentException.getMessage().contains("Incorrect signature"),
          () -> "Received message was: %s".formatted(illegalArgumentException.getMessage())
      );
    }

    @Test
    void shallThrowExceptionIfSignatureIsEmpty() {
      final var actionEvaluation = actionEvaluationBuilder.build();
      final var signatureEmpty = new Signature(" ");

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> certificate.sign(xmlGenerator, signatureEmpty, REVISION, actionEvaluation,
              xmlSchematronValidator, xmlSchemaValidator)
      );

      assertTrue(illegalArgumentException.getMessage().contains("Incorrect signature"),
          () -> "Received message was: %s".formatted(illegalArgumentException.getMessage())
      );
    }

    @Test
    void shallReturnStateSignedWhenSigned() {
      doReturn(true).when(xmlSchematronValidator).validate(certificate);
      doReturn(true).when(xmlSchemaValidator).validate(certificate);
      certificate.sign(xmlGenerator, SIGNATURE, REVISION, actionEvaluationBuilder.build(),
          xmlSchematronValidator, xmlSchemaValidator);
      assertEquals(Status.SIGNED, certificate.status());
    }

    @Test
    void shallReturnXmlWhenSigned() {
      doReturn(true).when(xmlSchematronValidator).validate(certificate);
      doReturn(true).when(xmlSchemaValidator).validate(certificate);
      doReturn(XML).when(xmlGenerator).generate(certificate, SIGNATURE);
      certificate.sign(xmlGenerator, SIGNATURE, REVISION, actionEvaluationBuilder.build(),
          xmlSchematronValidator, xmlSchemaValidator);
      assertEquals(XML, certificate.xml());
    }

    @Test
    void shallThrowIfXmlSchematronValidationIsFalse() {
      doReturn(false).when(xmlSchematronValidator).validate(certificate);
      final var actionEvaluation = actionEvaluationBuilder.build();
      final var illegalStateException = assertThrows(IllegalStateException.class,
          () -> certificate.sign(xmlGenerator, SIGNATURE, REVISION, actionEvaluation,
              xmlSchematronValidator, xmlSchemaValidator)
      );

      assertEquals(
          "Certificate did not pass schematron validation",
          illegalStateException.getMessage()
      );
    }


    @Test
    void shallThrowIfXmlSchemaValidationIsFalse() {
      doReturn(false).when(xmlSchemaValidator).validate(certificate);
      doReturn(true).when(xmlSchematronValidator).validate(certificate);
      final var actionEvaluation = actionEvaluationBuilder.build();
      final var illegalStateException = assertThrows(IllegalStateException.class,
          () -> certificate.sign(xmlGenerator, SIGNATURE, REVISION, actionEvaluation,
              xmlSchematronValidator, xmlSchemaValidator)
      );

      assertEquals(
          "Certificate did not pass schematron validation",
          illegalStateException.getMessage()
      );
    }
  }

  @Nested
  class TestSignWithoutSignature {

    @Test
    void shallThrowExceptionIfRevisionDontMatch() {
      final var actionEvaluation = actionEvaluationBuilder.build();
      final var revision = new Revision(2);
      final var concurrentModificationException = assertThrows(
          ConcurrentModificationException.class,
          () -> certificate.sign(xmlGenerator, revision, actionEvaluation)
      );
      assertTrue(concurrentModificationException.getMessage().contains("Incorrect revision"),
          () -> "Received message was: %s".formatted(concurrentModificationException.getMessage())
      );
    }

    @Test
    void shallThrowExceptionIfStatusDoesntMatchDraft() {
      final var actionEvaluation = actionEvaluationBuilder.build();
      final var deletedCertificate = certificateBuilder
          .status(Status.DELETED_DRAFT)
          .build();

      final var illegalStateException = assertThrows(IllegalStateException.class,
          () -> deletedCertificate.sign(xmlGenerator, REVISION, actionEvaluation)
      );

      assertTrue(illegalStateException.getMessage().contains("Incorrect status"),
          () -> "Received message was: %s".formatted(illegalStateException.getMessage())
      );
    }

    @Test
    void shallReturnStateSignedWhenSigned() {
      certificate.sign(xmlGenerator, REVISION, actionEvaluationBuilder.build());
      assertEquals(Status.SIGNED, certificate.status());
    }

    @Test
    void shallReturnXmlWhenSigned() {
      doReturn(XML).when(xmlGenerator).generate(certificate);
      certificate.sign(xmlGenerator, REVISION, actionEvaluationBuilder.build());
      assertEquals(XML, certificate.xml());
    }
  }

  @Nested
  class TestSend {

    @Test
    void shallIncludeRecipientWhenSent() {
      final var actionEvaluation = actionEvaluationBuilder.build();
      final var certificate = certificateBuilder
          .status(Status.SIGNED)
          .build();

      doReturn(RECIPIENT).when(certificateModel).recipient();
      certificate.send(actionEvaluation);

      assertEquals(RECIPIENT, certificate.sent().recipient());
    }

    @Test
    void shallIncludeSentTimestampWhenSent() {
      final var actionEvaluation = actionEvaluationBuilder.build();
      final var certificate = certificateBuilder
          .status(Status.SIGNED)
          .build();

      doReturn(RECIPIENT).when(certificateModel).recipient();
      certificate.send(actionEvaluation);

      assertNotNull(certificate.sent().sentAt());
    }

    @Test
    void shallIncludeSentByWhenSent() {
      final var actionEvaluation = actionEvaluationBuilder.build();
      final var certificate = certificateBuilder
          .status(Status.SIGNED)
          .build();

      doReturn(RECIPIENT).when(certificateModel).recipient();
      certificate.send(actionEvaluation);

      assertEquals(TestDataStaff.AJLA_DOKTOR, certificate.sent().sentBy());
    }

    @Test
    void shallThrowIfStatusIsNotSigned() {
      final var actionEvaluation = actionEvaluationBuilder.build();
      final var certificate = certificateBuilder
          .status(Status.DRAFT)
          .build();

      final var illegalStateException = assertThrows(IllegalStateException.class,
          () -> certificate.send(actionEvaluation));

      assertTrue(illegalStateException.getMessage().contains("Incorrect status"),
          () -> "Received message was: %s".formatted(illegalStateException.getMessage())
      );
    }

    @Test
    void shallThrowIfCertificateAlreadyBeenSent() {
      final var actionEvaluation = actionEvaluationBuilder.build();
      final var certificate = certificateBuilder
          .status(Status.SIGNED)
          .sent(
              Sent.builder()
                  .recipient(RECIPIENT)
                  .build()
          )
          .build();

      final var illegalStateException = assertThrows(IllegalStateException.class,
          () -> certificate.send(actionEvaluation));

      assertTrue(illegalStateException.getMessage().contains("has already been sent to"),
          () -> "Received message was: %s".formatted(illegalStateException.getMessage())
      );
    }
  }

  @Nested
  class TestRevoke {

    private static final String MESSAGE = "message";
    private static final String REASON = "reason";
    private static final RevokedInformation REVOKED_INFORMATION = new RevokedInformation(REASON,
        MESSAGE);

    @Test
    void shallSetStatusToRevoked() {
      final var expectedRevokeInformation = Status.REVOKED;
      final var actionEvaluation = actionEvaluationBuilder.build();
      final var certificate = certificateBuilder
          .status(Status.SIGNED)
          .build();

      certificate.revoke(actionEvaluation, REVOKED_INFORMATION);

      assertEquals(expectedRevokeInformation, certificate.status());
    }

    @Test
    void shallIncludeRevokeInformationWhenRevoked() {
      final var expectedRevokeInformation = new RevokedInformation(REASON, MESSAGE);
      final var actionEvaluation = actionEvaluationBuilder.build();
      final var certificate = certificateBuilder
          .status(Status.SIGNED)
          .build();

      certificate.revoke(actionEvaluation, REVOKED_INFORMATION);

      assertEquals(expectedRevokeInformation, certificate.revoked().revokedInformation());
    }

    @Test
    void shallIncludeRevokedTimestampWhenRevoked() {
      final var actionEvaluation = actionEvaluationBuilder.build();
      final var certificate = certificateBuilder
          .status(Status.SIGNED)
          .build();

      certificate.revoke(actionEvaluation, REVOKED_INFORMATION);

      assertNotNull(certificate.revoked().revokedAt());
    }

    @Test
    void shallIncludeRevokedByWhenRevoked() {
      final var actionEvaluation = actionEvaluationBuilder.build();
      final var certificate = certificateBuilder
          .status(Status.SIGNED)
          .build();

      certificate.revoke(actionEvaluation, REVOKED_INFORMATION);

      assertEquals(TestDataStaff.AJLA_DOKTOR, certificate.revoked().revokedBy());
    }

    @Test
    void shallThrowIfStatusIsNotSigned() {
      final var actionEvaluation = actionEvaluationBuilder.build();
      final var certificate = certificateBuilder
          .status(Status.DRAFT)
          .build();

      final var illegalStateException = assertThrows(IllegalStateException.class,
          () -> certificate.revoke(actionEvaluation, REVOKED_INFORMATION));

      assertTrue(illegalStateException.getMessage().contains("Incorrect status"),
          () -> "Received message was: %s".formatted(illegalStateException.getMessage())
      );
    }

    @Test
    void shallThrowIfCertificateAlreadyBeenRevoked() {
      final var actionEvaluation = actionEvaluationBuilder.build();
      final var certificate = certificateBuilder
          .status(Status.SIGNED)
          .revoked(Revoked.builder().build())
          .build();

      final var illegalStateException = assertThrows(IllegalStateException.class,
          () -> certificate.revoke(actionEvaluation, REVOKED_INFORMATION));

      assertTrue(illegalStateException.getMessage().contains("has already been revoked"),
          () -> "Received message was: %s".formatted(illegalStateException.getMessage())
      );
    }
  }
}
