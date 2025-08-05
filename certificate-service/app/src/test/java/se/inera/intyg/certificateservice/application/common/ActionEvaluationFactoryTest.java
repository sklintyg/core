package se.inera.intyg.certificateservice.application.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.ATHENA_REACT_ANDERSSON_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.ATLAS_REACT_ABRAHAMSSON_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.athenaReactAnderssonDtoBuilder;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_ALLERGIMOTTAGNINGEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_REGIONEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.alfaHudmottagningenDtoBuilder;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.AJLA_DOCTOR_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.ajlaDoktorDtoBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProviderConstants.ALFA_REGIONEN_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProviderConstants.ALFA_REGIONEN_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_ADDRESS;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_CITY;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_EMAIL;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_PHONENUMBER;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_ZIP_CODE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_CITY;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_FIRST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_LAST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_MIDDLE_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_PROTECTED_PERSON;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_STREET;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_TEST_INDICATED;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_ZIP_CODE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.PROTECTED_PERSON_TRUE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.TEST_INDICATED_TRUE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataStaff.AJLA_DOKTOR;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_ALLERGIMOTTAGNINGEN_ADDRESS;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_ALLERGIMOTTAGNINGEN_CITY;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_ALLERGIMOTTAGNINGEN_EMAIL;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_ALLERGIMOTTAGNINGEN_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_ALLERGIMOTTAGNINGEN_INACTIVE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_ALLERGIMOTTAGNINGEN_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_ALLERGIMOTTAGNINGEN_PHONENUMBER;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_ALLERGIMOTTAGNINGEN_ZIP_CODE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.INACTIVE_TRUE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AGREEMENT_FALSE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_AGREEMENT;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_ALLOW_COPY;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_BLOCKED;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_HEALTH_CARE_PROFESSIONAL_LICENCES;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_HSA_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_RESPONSIBLE_ISSUER;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_ROLE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALLOW_COPY_FALSE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.BLOCKED_TRUE;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.common.dto.AccessScopeTypeDTO;
import se.inera.intyg.certificateservice.application.common.dto.PersonIdDTO;
import se.inera.intyg.certificateservice.application.common.dto.PersonIdTypeDTO;
import se.inera.intyg.certificateservice.domain.common.model.AccessScope;
import se.inera.intyg.certificateservice.domain.common.model.PersonIdType;

class ActionEvaluationFactoryTest {

  private ActionEvaluationFactory actionEvaluationFactory;

  @BeforeEach
  void setUp() {
    actionEvaluationFactory = new ActionEvaluationFactory();
  }

  @Nested
  class IncludeCareUnit {

    @Test
    void shallIncludeCareUnitId() {
      final var actionEvaluation = actionEvaluationFactory.create(
          ATHENA_REACT_ANDERSSON_DTO,
          AJLA_DOCTOR_DTO,
          ALFA_ALLERGIMOTTAGNINGEN_DTO,
          ALFA_MEDICINCENTRUM_DTO,
          ALFA_REGIONEN_DTO
      );

      assertEquals(ALFA_MEDICINCENTRUM_ID, actionEvaluation.careUnit().hsaId().id());
    }

    @Test
    void shallIncludeCareUnitName() {
      final var actionEvaluation = actionEvaluationFactory.create(
          ATHENA_REACT_ANDERSSON_DTO,
          AJLA_DOCTOR_DTO,
          ALFA_ALLERGIMOTTAGNINGEN_DTO,
          ALFA_MEDICINCENTRUM_DTO,
          ALFA_REGIONEN_DTO
      );

      assertEquals(ALFA_MEDICINCENTRUM_NAME, actionEvaluation.careUnit().name().name());
    }

    @Test
    void shallIncludeCareUnitAddress() {
      final var actionEvaluation = actionEvaluationFactory.create(
          ATHENA_REACT_ANDERSSON_DTO,
          AJLA_DOCTOR_DTO,
          ALFA_ALLERGIMOTTAGNINGEN_DTO,
          ALFA_MEDICINCENTRUM_DTO,
          ALFA_REGIONEN_DTO
      );

      assertEquals(ALFA_MEDICINCENTRUM_ADDRESS,
          actionEvaluation.careUnit().address().address()
      );
    }

    @Test
    void shallIncludeCareUnitZipCode() {
      final var actionEvaluation = actionEvaluationFactory.create(
          ATHENA_REACT_ANDERSSON_DTO,
          AJLA_DOCTOR_DTO,
          ALFA_ALLERGIMOTTAGNINGEN_DTO,
          ALFA_MEDICINCENTRUM_DTO,
          ALFA_REGIONEN_DTO
      );

      assertEquals(ALFA_MEDICINCENTRUM_ZIP_CODE, actionEvaluation.careUnit().address().zipCode());
    }

    @Test
    void shallIncludeCareUnitCity() {
      final var actionEvaluation = actionEvaluationFactory.create(
          ATHENA_REACT_ANDERSSON_DTO,
          AJLA_DOCTOR_DTO,
          ALFA_ALLERGIMOTTAGNINGEN_DTO,
          ALFA_MEDICINCENTRUM_DTO,
          ALFA_REGIONEN_DTO
      );

      assertEquals(ALFA_MEDICINCENTRUM_CITY,
          actionEvaluation.careUnit().address().city()
      );
    }

    @Test
    void shallIncludeCareUnitPhoneNumber() {
      final var actionEvaluation = actionEvaluationFactory.create(
          ATHENA_REACT_ANDERSSON_DTO,
          AJLA_DOCTOR_DTO,
          ALFA_ALLERGIMOTTAGNINGEN_DTO,
          ALFA_MEDICINCENTRUM_DTO,
          ALFA_REGIONEN_DTO
      );

      assertEquals(ALFA_MEDICINCENTRUM_PHONENUMBER,
          actionEvaluation.careUnit().contactInfo().phoneNumber()
      );
    }

    @Test
    void shallIncludeCareUnitEmail() {
      final var actionEvaluation = actionEvaluationFactory.create(
          ATHENA_REACT_ANDERSSON_DTO,
          AJLA_DOCTOR_DTO,
          ALFA_ALLERGIMOTTAGNINGEN_DTO,
          ALFA_MEDICINCENTRUM_DTO,
          ALFA_REGIONEN_DTO
      );

      assertEquals(ALFA_MEDICINCENTRUM_EMAIL,
          actionEvaluation.careUnit().contactInfo().email()
      );
    }
  }

  @Nested
  class IncludeSubUnit {

    @Test
    void shallIncludeSubUnitId() {
      final var actionEvaluation = actionEvaluationFactory.create(
          ATHENA_REACT_ANDERSSON_DTO,
          AJLA_DOCTOR_DTO,
          ALFA_ALLERGIMOTTAGNINGEN_DTO,
          ALFA_MEDICINCENTRUM_DTO,
          ALFA_REGIONEN_DTO
      );

      assertEquals(ALFA_ALLERGIMOTTAGNINGEN_ID, actionEvaluation.subUnit().hsaId().id());
    }

    @Test
    void shallIncludeSubUnitName() {
      final var actionEvaluation = actionEvaluationFactory.create(
          ATHENA_REACT_ANDERSSON_DTO,
          AJLA_DOCTOR_DTO,
          ALFA_ALLERGIMOTTAGNINGEN_DTO,
          ALFA_MEDICINCENTRUM_DTO,
          ALFA_REGIONEN_DTO
      );

      assertEquals(ALFA_ALLERGIMOTTAGNINGEN_NAME, actionEvaluation.subUnit().name().name());
    }

    @Test
    void shallIncludeSubUnitAddress() {
      final var actionEvaluation = actionEvaluationFactory.create(
          ATHENA_REACT_ANDERSSON_DTO,
          AJLA_DOCTOR_DTO,
          ALFA_ALLERGIMOTTAGNINGEN_DTO,
          ALFA_MEDICINCENTRUM_DTO,
          ALFA_REGIONEN_DTO
      );

      assertEquals(ALFA_ALLERGIMOTTAGNINGEN_ADDRESS,
          actionEvaluation.subUnit().address().address()
      );
    }

    @Test
    void shallIncludeSubUnitZipCode() {
      final var actionEvaluation = actionEvaluationFactory.create(
          ATHENA_REACT_ANDERSSON_DTO,
          AJLA_DOCTOR_DTO,
          ALFA_ALLERGIMOTTAGNINGEN_DTO,
          ALFA_MEDICINCENTRUM_DTO,
          ALFA_REGIONEN_DTO
      );

      assertEquals(ALFA_ALLERGIMOTTAGNINGEN_ZIP_CODE,
          actionEvaluation.subUnit().address().zipCode()
      );
    }

    @Test
    void shallIncludeSubUnitCity() {
      final var actionEvaluation = actionEvaluationFactory.create(
          ATHENA_REACT_ANDERSSON_DTO,
          AJLA_DOCTOR_DTO,
          ALFA_ALLERGIMOTTAGNINGEN_DTO,
          ALFA_MEDICINCENTRUM_DTO,
          ALFA_REGIONEN_DTO
      );

      assertEquals(ALFA_ALLERGIMOTTAGNINGEN_CITY,
          actionEvaluation.subUnit().address().city()
      );
    }

    @Test
    void shallIncludeSubUnitPhoneNumber() {
      final var actionEvaluation = actionEvaluationFactory.create(
          ATHENA_REACT_ANDERSSON_DTO,
          AJLA_DOCTOR_DTO,
          ALFA_ALLERGIMOTTAGNINGEN_DTO,
          ALFA_MEDICINCENTRUM_DTO,
          ALFA_REGIONEN_DTO
      );

      assertEquals(ALFA_ALLERGIMOTTAGNINGEN_PHONENUMBER,
          actionEvaluation.subUnit().contactInfo().phoneNumber()
      );
    }

    @Test
    void shallIncludeSubUnitEmail() {
      final var actionEvaluation = actionEvaluationFactory.create(
          ATHENA_REACT_ANDERSSON_DTO,
          AJLA_DOCTOR_DTO,
          ALFA_ALLERGIMOTTAGNINGEN_DTO,
          ALFA_MEDICINCENTRUM_DTO,
          ALFA_REGIONEN_DTO
      );

      assertEquals(ALFA_ALLERGIMOTTAGNINGEN_EMAIL,
          actionEvaluation.subUnit().contactInfo().email()
      );
    }

    @Test
    void shallIncludeSubunitInactiveTrue() {
      final var unit = alfaHudmottagningenDtoBuilder()
          .inactive(true)
          .build();

      final var actionEvaluation = actionEvaluationFactory.create(
          ATHENA_REACT_ANDERSSON_DTO,
          AJLA_DOCTOR_DTO,
          unit,
          ALFA_MEDICINCENTRUM_DTO,
          ALFA_REGIONEN_DTO
      );

      assertEquals(INACTIVE_TRUE.value(),
          actionEvaluation.subUnit().inactive().value()
      );
    }

    @Test
    void shallIncludeSubunitInactiveFalse() {
      final var actionEvaluation = actionEvaluationFactory.create(
          ATHENA_REACT_ANDERSSON_DTO,
          AJLA_DOCTOR_DTO,
          ALFA_ALLERGIMOTTAGNINGEN_DTO,
          ALFA_MEDICINCENTRUM_DTO,
          ALFA_REGIONEN_DTO
      );

      assertEquals(ALFA_ALLERGIMOTTAGNINGEN_INACTIVE.value(),
          actionEvaluation.subUnit().inactive().value()
      );
    }
  }

  @Nested
  class IncludeCareProvider {

    @Test
    void shallIncludeCareProviderId() {
      final var actionEvaluation = actionEvaluationFactory.create(
          ATHENA_REACT_ANDERSSON_DTO,
          AJLA_DOCTOR_DTO,
          ALFA_ALLERGIMOTTAGNINGEN_DTO,
          ALFA_MEDICINCENTRUM_DTO,
          ALFA_REGIONEN_DTO
      );

      assertEquals(ALFA_REGIONEN_ID, actionEvaluation.careProvider().hsaId().id());
    }

    @Test
    void shallIncludeCareProviderName() {
      final var actionEvaluation = actionEvaluationFactory.create(
          ATHENA_REACT_ANDERSSON_DTO,
          AJLA_DOCTOR_DTO,
          ALFA_ALLERGIMOTTAGNINGEN_DTO,
          ALFA_MEDICINCENTRUM_DTO,
          ALFA_REGIONEN_DTO
      );

      assertEquals(ALFA_REGIONEN_NAME, actionEvaluation.careProvider().name().name());
    }
  }

  @Nested
  class ExcludePatient {

    @Test
    void createActionEvaluationWithoutPatient() {
      final var actionEvaluation = actionEvaluationFactory.create(
          AJLA_DOCTOR_DTO,
          ALFA_ALLERGIMOTTAGNINGEN_DTO,
          ALFA_MEDICINCENTRUM_DTO,
          ALFA_REGIONEN_DTO
      );

      assertNull(
          actionEvaluation.patient(),
          "Expected patient to be null"
      );
    }
  }

  @Nested
  class IncludePatient {

    @Test
    void shallIncludePatientIdTypePersonalIdentityNumber() {
      final var actionEvaluation = actionEvaluationFactory.create(
          ATHENA_REACT_ANDERSSON_DTO,
          AJLA_DOCTOR_DTO,
          ALFA_ALLERGIMOTTAGNINGEN_DTO,
          ALFA_MEDICINCENTRUM_DTO,
          ALFA_REGIONEN_DTO
      );

      assertEquals(PersonIdType.PERSONAL_IDENTITY_NUMBER,
          actionEvaluation.patient().id().type());
    }

    @Test
    void shallIncludePatientIdTypeCoordinationNumber() {
      final var patient = athenaReactAnderssonDtoBuilder()
          .id(
              PersonIdDTO.builder()
                  .type(PersonIdTypeDTO.COORDINATION_NUMBER)
                  .id(ATHENA_REACT_ANDERSSON_ID)
                  .build()
          )
          .build();

      final var actionEvaluation = actionEvaluationFactory.create(
          patient,
          AJLA_DOCTOR_DTO,
          ALFA_ALLERGIMOTTAGNINGEN_DTO,
          ALFA_MEDICINCENTRUM_DTO,
          ALFA_REGIONEN_DTO
      );

      assertEquals(PersonIdType.COORDINATION_NUMBER,
          actionEvaluation.patient().id().type()
      );
    }

    @Test
    void shallIncludePatientIdId() {
      final var actionEvaluation = actionEvaluationFactory.create(
          ATHENA_REACT_ANDERSSON_DTO,
          AJLA_DOCTOR_DTO,
          ALFA_ALLERGIMOTTAGNINGEN_DTO,
          ALFA_MEDICINCENTRUM_DTO,
          ALFA_REGIONEN_DTO
      );

      assertEquals(ATHENA_REACT_ANDERSSON_ID, actionEvaluation.patient().id().id());
    }

    @Test
    void shallIncludePatientFirstName() {
      final var actionEvaluation = actionEvaluationFactory.create(
          ATHENA_REACT_ANDERSSON_DTO,
          AJLA_DOCTOR_DTO,
          ALFA_ALLERGIMOTTAGNINGEN_DTO,
          ALFA_MEDICINCENTRUM_DTO,
          ALFA_REGIONEN_DTO
      );

      assertEquals(ATHENA_REACT_ANDERSSON_FIRST_NAME,
          actionEvaluation.patient().name().firstName()
      );
    }

    @Test
    void shallIncludePatientMiddleName() {
      final var actionEvaluation = actionEvaluationFactory.create(
          ATHENA_REACT_ANDERSSON_DTO,
          AJLA_DOCTOR_DTO,
          ALFA_ALLERGIMOTTAGNINGEN_DTO,
          ALFA_MEDICINCENTRUM_DTO,
          ALFA_REGIONEN_DTO
      );

      assertEquals(ATHENA_REACT_ANDERSSON_MIDDLE_NAME,
          actionEvaluation.patient().name().middleName()
      );
    }

    @Test
    void shallIncludePatientLastName() {
      final var actionEvaluation = actionEvaluationFactory.create(
          ATHENA_REACT_ANDERSSON_DTO,
          AJLA_DOCTOR_DTO,
          ALFA_ALLERGIMOTTAGNINGEN_DTO,
          ALFA_MEDICINCENTRUM_DTO,
          ALFA_REGIONEN_DTO
      );

      assertEquals(ATHENA_REACT_ANDERSSON_LAST_NAME,
          actionEvaluation.patient().name().lastName()
      );
    }

    @Test
    void shallIncludePatientCity() {
      final var actionEvaluation = actionEvaluationFactory.create(
          ATHENA_REACT_ANDERSSON_DTO,
          AJLA_DOCTOR_DTO,
          ALFA_ALLERGIMOTTAGNINGEN_DTO,
          ALFA_MEDICINCENTRUM_DTO,
          ALFA_REGIONEN_DTO
      );

      assertEquals(ATHENA_REACT_ANDERSSON_CITY,
          actionEvaluation.patient().address().city()
      );
    }

    @Test
    void shallIncludePatientStreet() {
      final var actionEvaluation = actionEvaluationFactory.create(
          ATHENA_REACT_ANDERSSON_DTO,
          AJLA_DOCTOR_DTO,
          ALFA_ALLERGIMOTTAGNINGEN_DTO,
          ALFA_MEDICINCENTRUM_DTO,
          ALFA_REGIONEN_DTO
      );

      assertEquals(ATHENA_REACT_ANDERSSON_STREET,
          actionEvaluation.patient().address().street()
      );
    }

    @Test
    void shallIncludePatientZipCode() {
      final var actionEvaluation = actionEvaluationFactory.create(
          ATHENA_REACT_ANDERSSON_DTO,
          AJLA_DOCTOR_DTO,
          ALFA_ALLERGIMOTTAGNINGEN_DTO,
          ALFA_MEDICINCENTRUM_DTO,
          ALFA_REGIONEN_DTO
      );

      assertEquals(ATHENA_REACT_ANDERSSON_ZIP_CODE,
          actionEvaluation.patient().address().zipCode()
      );
    }

    @Test
    void shallIncludePatientDeceasedTrue() {
      final var actionEvaluation = actionEvaluationFactory.create(
          ATLAS_REACT_ABRAHAMSSON_DTO,
          AJLA_DOCTOR_DTO,
          ALFA_ALLERGIMOTTAGNINGEN_DTO,
          ALFA_MEDICINCENTRUM_DTO,
          ALFA_REGIONEN_DTO
      );

      assertTrue(actionEvaluation.patient().deceased().value(),
          "Expected patient.value to be true");
    }

    @Test
    void shallIncludePatientDeceasedFalse() {
      final var actionEvaluation = actionEvaluationFactory.create(
          ATHENA_REACT_ANDERSSON_DTO,
          AJLA_DOCTOR_DTO,
          ALFA_ALLERGIMOTTAGNINGEN_DTO,
          ALFA_MEDICINCENTRUM_DTO,
          ALFA_REGIONEN_DTO
      );

      assertFalse(actionEvaluation.patient().deceased().value(),
          "Expected patient.value to be false");
    }

    @Test
    void shallIncludePatientTestIndicatedTrue() {
      final var patient = athenaReactAnderssonDtoBuilder()
          .testIndicated(true)
          .build();

      final var actionEvaluation = actionEvaluationFactory.create(
          patient,
          AJLA_DOCTOR_DTO,
          ALFA_ALLERGIMOTTAGNINGEN_DTO,
          ALFA_MEDICINCENTRUM_DTO,
          ALFA_REGIONEN_DTO
      );

      assertEquals(TEST_INDICATED_TRUE.value(),
          actionEvaluation.patient().testIndicated().value()
      );
    }

    @Test
    void shallIncludePatientTestIndicatedFalse() {
      final var actionEvaluation = actionEvaluationFactory.create(
          ATHENA_REACT_ANDERSSON_DTO,
          AJLA_DOCTOR_DTO,
          ALFA_ALLERGIMOTTAGNINGEN_DTO,
          ALFA_MEDICINCENTRUM_DTO,
          ALFA_REGIONEN_DTO
      );

      assertEquals(ATHENA_REACT_ANDERSSON_TEST_INDICATED.value(),
          actionEvaluation.patient().testIndicated().value()
      );
    }

    @Test
    void shallIncludePatientProtectedPersonTrue() {
      final var patient = athenaReactAnderssonDtoBuilder()
          .protectedPerson(true)
          .build();

      final var actionEvaluation = actionEvaluationFactory.create(
          patient,
          AJLA_DOCTOR_DTO,
          ALFA_ALLERGIMOTTAGNINGEN_DTO,
          ALFA_MEDICINCENTRUM_DTO,
          ALFA_REGIONEN_DTO
      );

      assertEquals(PROTECTED_PERSON_TRUE.value(),
          actionEvaluation.patient().protectedPerson().value()
      );
    }

    @Test
    void shallIncludePatientProtectedPersonFalse() {
      final var actionEvaluation = actionEvaluationFactory.create(
          ATHENA_REACT_ANDERSSON_DTO,
          AJLA_DOCTOR_DTO,
          ALFA_ALLERGIMOTTAGNINGEN_DTO,
          ALFA_MEDICINCENTRUM_DTO,
          ALFA_REGIONEN_DTO
      );

      assertEquals(ATHENA_REACT_ANDERSSON_PROTECTED_PERSON.value(),
          actionEvaluation.patient().protectedPerson().value()
      );
    }
  }

  @Nested
  class IncludeUser {

    @Test
    void shallIncludeUserId() {
      final var actionEvaluation = actionEvaluationFactory.create(
          ATHENA_REACT_ANDERSSON_DTO,
          AJLA_DOCTOR_DTO,
          ALFA_ALLERGIMOTTAGNINGEN_DTO,
          ALFA_MEDICINCENTRUM_DTO,
          ALFA_REGIONEN_DTO
      );

      assertEquals(AJLA_DOCTOR_HSA_ID, actionEvaluation.user().hsaId().id());
    }

    @Test
    void shallIncludeUserBlockedFalse() {
      final var actionEvaluation = actionEvaluationFactory.create(
          ATHENA_REACT_ANDERSSON_DTO,
          AJLA_DOCTOR_DTO,
          ALFA_ALLERGIMOTTAGNINGEN_DTO,
          ALFA_MEDICINCENTRUM_DTO,
          ALFA_REGIONEN_DTO
      );

      assertEquals(AJLA_DOCTOR_BLOCKED.value(), actionEvaluation.user().blocked().value());
    }

    @Test
    void shallIncludeUserBlockedTrue() {
      final var user = ajlaDoktorDtoBuilder()
          .blocked(true)
          .build();

      final var actionEvaluation = actionEvaluationFactory.create(
          ATHENA_REACT_ANDERSSON_DTO,
          user,
          ALFA_ALLERGIMOTTAGNINGEN_DTO,
          ALFA_MEDICINCENTRUM_DTO,
          ALFA_REGIONEN_DTO
      );

      assertEquals(BLOCKED_TRUE.value(), actionEvaluation.user().blocked().value());
    }

    @Test
    void shallIncludeUserAgreementTrue() {
      final var actionEvaluation = actionEvaluationFactory.create(
          ATHENA_REACT_ANDERSSON_DTO,
          AJLA_DOCTOR_DTO,
          ALFA_ALLERGIMOTTAGNINGEN_DTO,
          ALFA_MEDICINCENTRUM_DTO,
          ALFA_REGIONEN_DTO
      );

      assertEquals(AJLA_DOCTOR_AGREEMENT.value(), actionEvaluation.user().agreement().value());
    }

    @Test
    void shallIncludeUserAgreementFalse() {
      final var user = ajlaDoktorDtoBuilder()
          .agreement(false)
          .build();

      final var actionEvaluation = actionEvaluationFactory.create(
          ATHENA_REACT_ANDERSSON_DTO,
          user,
          ALFA_ALLERGIMOTTAGNINGEN_DTO,
          ALFA_MEDICINCENTRUM_DTO,
          ALFA_REGIONEN_DTO
      );

      assertEquals(AGREEMENT_FALSE.value(), actionEvaluation.user().agreement().value());
    }

    @Test
    void shallIncludeUserAllowCopyFalse() {
      final var user = ajlaDoktorDtoBuilder()
          .allowCopy(true)
          .build();

      final var actionEvaluation = actionEvaluationFactory.create(
          ATHENA_REACT_ANDERSSON_DTO,
          user,
          ALFA_ALLERGIMOTTAGNINGEN_DTO,
          ALFA_MEDICINCENTRUM_DTO,
          ALFA_REGIONEN_DTO
      );

      assertEquals(ALLOW_COPY_FALSE.value(), actionEvaluation.user().blocked().value());
    }

    @Test
    void shallIncludeUserAllowCopyTrue() {
      final var actionEvaluation = actionEvaluationFactory.create(
          ATHENA_REACT_ANDERSSON_DTO,
          AJLA_DOCTOR_DTO,
          ALFA_ALLERGIMOTTAGNINGEN_DTO,
          ALFA_MEDICINCENTRUM_DTO,
          ALFA_REGIONEN_DTO
      );

      assertEquals(AJLA_DOCTOR_ALLOW_COPY.value(), actionEvaluation.user().allowCopy().value());
    }

    @Test
    void shallIncludeUserName() {
      final var actionEvaluation = actionEvaluationFactory.create(
          ATHENA_REACT_ANDERSSON_DTO,
          AJLA_DOCTOR_DTO,
          ALFA_ALLERGIMOTTAGNINGEN_DTO,
          ALFA_MEDICINCENTRUM_DTO,
          ALFA_REGIONEN_DTO
      );

      assertEquals(AJLA_DOKTOR.name(), actionEvaluation.user().name());
    }

    @Test
    void shallIncludeUserRole() {
      final var actionEvaluation = actionEvaluationFactory.create(
          ATHENA_REACT_ANDERSSON_DTO,
          AJLA_DOCTOR_DTO,
          ALFA_ALLERGIMOTTAGNINGEN_DTO,
          ALFA_MEDICINCENTRUM_DTO,
          ALFA_REGIONEN_DTO
      );

      assertEquals(AJLA_DOCTOR_ROLE, actionEvaluation.user().role());
    }

    @Test
    void shallIncludeUserAccessScopeWithinCareUnit() {
      final var user = ajlaDoktorDtoBuilder()
          .accessScope(AccessScopeTypeDTO.WITHIN_CARE_UNIT)
          .build();

      final var actionEvaluation = actionEvaluationFactory.create(
          ATHENA_REACT_ANDERSSON_DTO,
          user,
          ALFA_ALLERGIMOTTAGNINGEN_DTO,
          ALFA_MEDICINCENTRUM_DTO,
          ALFA_REGIONEN_DTO
      );

      assertEquals(AccessScope.WITHIN_CARE_UNIT, actionEvaluation.user().accessScope());
    }

    @Test
    void shallIncludeUserAccessScopeWithinCareProvider() {
      final var user = ajlaDoktorDtoBuilder()
          .accessScope(AccessScopeTypeDTO.WITHIN_CARE_PROVIDER)
          .build();

      final var actionEvaluation = actionEvaluationFactory.create(
          ATHENA_REACT_ANDERSSON_DTO,
          user,
          ALFA_ALLERGIMOTTAGNINGEN_DTO,
          ALFA_MEDICINCENTRUM_DTO,
          ALFA_REGIONEN_DTO
      );

      assertEquals(AccessScope.WITHIN_CARE_PROVIDER, actionEvaluation.user().accessScope());
    }

    @Test
    void shallIncludeUserAccessScopeAllCareProviders() {
      final var user = ajlaDoktorDtoBuilder()
          .accessScope(AccessScopeTypeDTO.ALL_CARE_PROVIDERS)
          .build();

      final var actionEvaluation = actionEvaluationFactory.create(
          ATHENA_REACT_ANDERSSON_DTO,
          user,
          ALFA_ALLERGIMOTTAGNINGEN_DTO,
          ALFA_MEDICINCENTRUM_DTO,
          ALFA_REGIONEN_DTO
      );

      assertEquals(AccessScope.ALL_CARE_PROVIDERS, actionEvaluation.user().accessScope());
    }

    @Test
    void shallSetDefaultAccessScopeWithinCareUnitIfNull() {
      final var user = ajlaDoktorDtoBuilder()
          .accessScope(null)
          .build();

      final var actionEvaluation = actionEvaluationFactory.create(
          ATHENA_REACT_ANDERSSON_DTO,
          user,
          ALFA_ALLERGIMOTTAGNINGEN_DTO,
          ALFA_MEDICINCENTRUM_DTO,
          ALFA_REGIONEN_DTO
      );

      assertEquals(AccessScope.WITHIN_CARE_UNIT, actionEvaluation.user().accessScope());
    }

    @Test
    void shallIncludeLegitimateProfessionalRoles() {
      final var actionEvaluation = actionEvaluationFactory.create(
          ATHENA_REACT_ANDERSSON_DTO,
          AJLA_DOCTOR_DTO,
          ALFA_ALLERGIMOTTAGNINGEN_DTO,
          ALFA_MEDICINCENTRUM_DTO,
          ALFA_REGIONEN_DTO
      );

      assertEquals(AJLA_DOCTOR_HEALTH_CARE_PROFESSIONAL_LICENCES,
          actionEvaluation.user().healthCareProfessionalLicence());
    }

    @Test
    void shallIncludeResponsibleIssuer() {
      final var actionEvaluation = actionEvaluationFactory.create(
          ATHENA_REACT_ANDERSSON_DTO,
          AJLA_DOCTOR_DTO,
          ALFA_ALLERGIMOTTAGNINGEN_DTO,
          ALFA_MEDICINCENTRUM_DTO,
          ALFA_REGIONEN_DTO
      );

      assertEquals(AJLA_DOCTOR_RESPONSIBLE_ISSUER,
          actionEvaluation.user().responsibleIssuer());
    }

    @Test
    void shallIncludeSrs() {
      final var actionEvaluation = actionEvaluationFactory.create(
          ATHENA_REACT_ANDERSSON_DTO,
          AJLA_DOCTOR_DTO,
          ALFA_ALLERGIMOTTAGNINGEN_DTO,
          ALFA_MEDICINCENTRUM_DTO,
          ALFA_REGIONEN_DTO
      );

      assertEquals(AJLA_DOCTOR_RESPONSIBLE_ISSUER,
          actionEvaluation.user().responsibleIssuer());
    }
  }
}