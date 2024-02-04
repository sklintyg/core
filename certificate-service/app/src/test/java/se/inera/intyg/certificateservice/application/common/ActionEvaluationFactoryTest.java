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
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_ALLERGIMOTTAGNINGEN_ADDRESS;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_ALLERGIMOTTAGNINGEN_CITY;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_ALLERGIMOTTAGNINGEN_EMAIL;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_ALLERGIMOTTAGNINGEN_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_ALLERGIMOTTAGNINGEN_INACTIVE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_ALLERGIMOTTAGNINGEN_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_ALLERGIMOTTAGNINGEN_PHONENUMBER;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_ALLERGIMOTTAGNINGEN_ZIP_CODE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.INACTIVE_TRUE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_BLOCKED;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_HSA_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_ROLE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.BLOCKED_TRUE;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.common.dto.PersonIdDTO;
import se.inera.intyg.certificateservice.application.common.dto.PersonIdTypeDTO;
import se.inera.intyg.certificateservice.domain.patient.model.PersonIdType;

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

      assertEquals(ALFA_MEDICINCENTRUM_ID, actionEvaluation.getCareUnit().getHsaId().id());
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

      assertEquals(ALFA_MEDICINCENTRUM_NAME, actionEvaluation.getCareUnit().getName().name());
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
          actionEvaluation.getCareUnit().getAddress().getAddress()
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

      assertEquals(ALFA_MEDICINCENTRUM_ZIP_CODE,
          actionEvaluation.getCareUnit().getAddress().getZipCode()
      );
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
          actionEvaluation.getCareUnit().getAddress().getCity()
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
          actionEvaluation.getCareUnit().getContactInfo().getPhoneNumber()
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
          actionEvaluation.getCareUnit().getContactInfo().getEmail()
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

      assertEquals(ALFA_ALLERGIMOTTAGNINGEN_ID, actionEvaluation.getSubUnit().getHsaId().id());
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

      assertEquals(ALFA_ALLERGIMOTTAGNINGEN_NAME, actionEvaluation.getSubUnit().getName().name());
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
          actionEvaluation.getSubUnit().getAddress().getAddress()
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
          actionEvaluation.getSubUnit().getAddress().getZipCode()
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
          actionEvaluation.getSubUnit().getAddress().getCity()
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
          actionEvaluation.getSubUnit().getContactInfo().getPhoneNumber()
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
          actionEvaluation.getSubUnit().getContactInfo().getEmail()
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
          actionEvaluation.getSubUnit().getInactive().value()
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
          actionEvaluation.getSubUnit().getInactive().value()
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

      assertEquals(ALFA_REGIONEN_ID, actionEvaluation.getCareProvider().getHsaId().id());
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

      assertEquals(ALFA_REGIONEN_NAME, actionEvaluation.getCareProvider().getName().name());
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
          actionEvaluation.getPatient(),
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
          actionEvaluation.getPatient().getId().getType());
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
          actionEvaluation.getPatient().getId().getType()
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

      assertEquals(ATHENA_REACT_ANDERSSON_ID, actionEvaluation.getPatient().getId().getId());
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
          actionEvaluation.getPatient().getName().getFirstName()
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
          actionEvaluation.getPatient().getName().getMiddleName()
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
          actionEvaluation.getPatient().getName().getLastName()
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
          actionEvaluation.getPatient().getAddress().getCity()
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
          actionEvaluation.getPatient().getAddress().getStreet()
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
          actionEvaluation.getPatient().getAddress().getZipCode()
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

      assertTrue(actionEvaluation.getPatient().getDeceased().value(),
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

      assertFalse(actionEvaluation.getPatient().getDeceased().value(),
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
          actionEvaluation.getPatient().getTestIndicated().value()
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
          actionEvaluation.getPatient().getTestIndicated().value()
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
          actionEvaluation.getPatient().getProtectedPerson().value()
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
          actionEvaluation.getPatient().getProtectedPerson().value()
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

      assertEquals(AJLA_DOCTOR_HSA_ID, actionEvaluation.getUser().getHsaId().id());
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

      assertEquals(AJLA_DOCTOR_BLOCKED.value(), actionEvaluation.getUser().getBlocked().value());
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

      assertEquals(BLOCKED_TRUE.value(), actionEvaluation.getUser().getBlocked().value());
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

      assertEquals(AJLA_DOCTOR_NAME, actionEvaluation.getUser().getName().getLastName());
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

      assertEquals(AJLA_DOCTOR_ROLE, actionEvaluation.getUser().getRole());
    }
  }
}
