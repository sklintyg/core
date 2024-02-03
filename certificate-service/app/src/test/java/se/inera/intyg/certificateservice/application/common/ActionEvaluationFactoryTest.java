package se.inera.intyg.certificateservice.application.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.ATHENA_REACT_ANDERSSON_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.alveReactAlfredssonDtoBuilder;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.athenaReactAnderssonDtoBuilder;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_HUDMOTTAGNINGEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_REGIONEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.alfaHudmottagningenDtoBuilder;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.alfaMedicincentrumDtoBuilder;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.alfaRegionenDtoBuilder;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.AJLA_DOCTOR_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.ajlaDoktorDtoBuilder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.common.dto.PersonIdDTO;
import se.inera.intyg.certificateservice.application.common.dto.PersonIdTypeDTO;
import se.inera.intyg.certificateservice.domain.patient.model.PersonIdType;

class ActionEvaluationFactoryTest {

  private static final String ID = "patientId";
  private static final String FIRST_NAME = "firstName";
  private static final String MIDDLE_NAME = "middleName";
  private static final String LAST_NAME = "lastName";
  private static final String CITY = "city";
  private static final String STREET = "street";
  private ActionEvaluationFactory actionEvaluationFactory;
  private static final String ZIP_CODE = "zipCode";

  @BeforeEach
  void setUp() {
    actionEvaluationFactory = new ActionEvaluationFactory();
  }

  @Nested
  class CreateWithUserAndPatientAndUnits {

    @Nested
    class IncludeCareUnit {

      @Test
      void shallIncludeCareUnitId() {
        final var unit = alfaMedicincentrumDtoBuilder().build();
        final var actionEvaluation = actionEvaluationFactory.create(ATHENA_REACT_ANDERSSON_DTO,
            AJLA_DOCTOR_DTO, ALFA_HUDMOTTAGNINGEN_DTO, unit, ALFA_REGIONEN_DTO);

        assertEquals(actionEvaluation.getCareUnit().getHsaId().id(), unit.getId());
      }

      @Test
      void shallIncludeCareUnitName() {
        final var unit = alfaMedicincentrumDtoBuilder().build();

        final var actionEvaluation = actionEvaluationFactory.create(ATHENA_REACT_ANDERSSON_DTO,
            AJLA_DOCTOR_DTO, ALFA_HUDMOTTAGNINGEN_DTO, unit, ALFA_REGIONEN_DTO);

        assertEquals(actionEvaluation.getCareUnit().getName().name(), unit.getName());
      }

      @Test
      void shallIncludeCareUnitAddress() {
        final var unit = alfaMedicincentrumDtoBuilder().build();

        final var actionEvaluation = actionEvaluationFactory.create(ATHENA_REACT_ANDERSSON_DTO,
            AJLA_DOCTOR_DTO, ALFA_HUDMOTTAGNINGEN_DTO, unit, ALFA_REGIONEN_DTO);

        assertEquals(actionEvaluation.getCareUnit().getAddress().getAddress(), unit.getAddress());
      }

      @Test
      void shallIncludeCareUnitZipCode() {
        final var unit = alfaMedicincentrumDtoBuilder().build();

        final var actionEvaluation = actionEvaluationFactory.create(ATHENA_REACT_ANDERSSON_DTO,
            AJLA_DOCTOR_DTO, ALFA_HUDMOTTAGNINGEN_DTO, unit, ALFA_REGIONEN_DTO);

        assertEquals(actionEvaluation.getCareUnit().getAddress().getZipCode(), unit.getZipCode());
      }

      @Test
      void shallIncludeCareUnitCity() {
        final var unit = alfaMedicincentrumDtoBuilder().build();

        final var actionEvaluation = actionEvaluationFactory.create(ATHENA_REACT_ANDERSSON_DTO,
            AJLA_DOCTOR_DTO, ALFA_HUDMOTTAGNINGEN_DTO, unit, ALFA_REGIONEN_DTO);

        assertEquals(actionEvaluation.getCareUnit().getAddress().getCity(), unit.getCity());
      }

      @Test
      void shallIncludeCareUnitPhoneNumber() {
        final var unit = alfaMedicincentrumDtoBuilder().build();

        final var actionEvaluation = actionEvaluationFactory.create(ATHENA_REACT_ANDERSSON_DTO,
            AJLA_DOCTOR_DTO, ALFA_HUDMOTTAGNINGEN_DTO, unit, ALFA_REGIONEN_DTO);

        assertEquals(actionEvaluation.getCareUnit().getContactInfo().getPhoneNumber(),
            unit.getPhoneNumber());
      }

      @Test
      void shallIncludeCareUnitEmail() {
        final var unit = alfaMedicincentrumDtoBuilder().build();

        final var actionEvaluation = actionEvaluationFactory.create(ATHENA_REACT_ANDERSSON_DTO,
            AJLA_DOCTOR_DTO,
            ALFA_HUDMOTTAGNINGEN_DTO, unit, ALFA_REGIONEN_DTO);

        assertEquals(actionEvaluation.getCareUnit().getContactInfo().getEmail(), unit.getEmail());
      }
    }

    @Nested
    class IncludeSubUnit {

      @Test
      void shallIncludeSubUnitId() {
        final var unit = alfaHudmottagningenDtoBuilder().build();
        final var actionEvaluation = actionEvaluationFactory.create(ATHENA_REACT_ANDERSSON_DTO,
            AJLA_DOCTOR_DTO, unit, ALFA_MEDICINCENTRUM_DTO, ALFA_REGIONEN_DTO);

        assertEquals(actionEvaluation.getSubUnit().getHsaId().id(), unit.getId());
      }

      @Test
      void shallIncludeSubunitName() {
        final var unit = alfaHudmottagningenDtoBuilder().build();

        final var actionEvaluation = actionEvaluationFactory.create(ATHENA_REACT_ANDERSSON_DTO,
            AJLA_DOCTOR_DTO, unit, ALFA_MEDICINCENTRUM_DTO, ALFA_REGIONEN_DTO);

        assertEquals(actionEvaluation.getSubUnit().getName().name(), unit.getName());
      }

      @Test
      void shallIncludeSubunitAddress() {
        final var unit = alfaHudmottagningenDtoBuilder().build();

        final var actionEvaluation = actionEvaluationFactory.create(ATHENA_REACT_ANDERSSON_DTO,
            AJLA_DOCTOR_DTO, unit, ALFA_MEDICINCENTRUM_DTO, ALFA_REGIONEN_DTO);

        assertEquals(actionEvaluation.getSubUnit().getAddress().getAddress(), unit.getAddress());
      }

      @Test
      void shallIncludeSubunitZipCode() {
        final var unit = alfaHudmottagningenDtoBuilder().build();

        final var actionEvaluation = actionEvaluationFactory.create(ATHENA_REACT_ANDERSSON_DTO,
            AJLA_DOCTOR_DTO, unit, ALFA_MEDICINCENTRUM_DTO, ALFA_REGIONEN_DTO);

        assertEquals(actionEvaluation.getSubUnit().getAddress().getZipCode(), unit.getZipCode());
      }

      @Test
      void shallIncludeSubunitCity() {
        final var unit = alfaHudmottagningenDtoBuilder().build();

        final var actionEvaluation = actionEvaluationFactory.create(ATHENA_REACT_ANDERSSON_DTO,
            AJLA_DOCTOR_DTO, unit, ALFA_MEDICINCENTRUM_DTO, ALFA_REGIONEN_DTO);

        assertEquals(actionEvaluation.getSubUnit().getAddress().getCity(), unit.getCity());
      }

      @Test
      void shallIncludeSubunitPhoneNumber() {
        final var unit = alfaHudmottagningenDtoBuilder().build();

        final var actionEvaluation = actionEvaluationFactory.create(ATHENA_REACT_ANDERSSON_DTO,
            AJLA_DOCTOR_DTO, unit, ALFA_MEDICINCENTRUM_DTO, ALFA_REGIONEN_DTO);

        assertEquals(actionEvaluation.getSubUnit().getContactInfo().getPhoneNumber(),
            unit.getPhoneNumber());
      }

      @Test
      void shallIncludeSubunitEmail() {
        final var unit = alfaHudmottagningenDtoBuilder().build();

        final var actionEvaluation = actionEvaluationFactory.create(ATHENA_REACT_ANDERSSON_DTO,
            AJLA_DOCTOR_DTO, unit, ALFA_MEDICINCENTRUM_DTO, ALFA_REGIONEN_DTO);

        assertEquals(actionEvaluation.getSubUnit().getContactInfo().getEmail(), unit.getEmail());
      }

      @Test
      void shallIncludeSubunitInactiveTrue() {
        final var unit = alfaHudmottagningenDtoBuilder().inactive(true).build();

        final var actionEvaluation = actionEvaluationFactory.create(ATHENA_REACT_ANDERSSON_DTO,
            AJLA_DOCTOR_DTO, unit, ALFA_MEDICINCENTRUM_DTO, ALFA_REGIONEN_DTO);

        assertEquals(actionEvaluation.getSubUnit().getInactive().value(), unit.getInactive());
      }

      @Test
      void shallIncludeSubunitInactiveFalse() {
        final var unit = alfaHudmottagningenDtoBuilder().inactive(false).build();

        final var actionEvaluation = actionEvaluationFactory.create(ATHENA_REACT_ANDERSSON_DTO,
            AJLA_DOCTOR_DTO, unit, ALFA_MEDICINCENTRUM_DTO, ALFA_REGIONEN_DTO);

        assertEquals(actionEvaluation.getSubUnit().getInactive().value(), unit.getInactive());
      }
    }

    @Nested
    class IncludeCareProvider {

      @Test
      void shallIncludeCareProviderId() {
        final var unit = alfaRegionenDtoBuilder().build();
        final var actionEvaluation = actionEvaluationFactory.create(ATHENA_REACT_ANDERSSON_DTO,
            AJLA_DOCTOR_DTO, ALFA_HUDMOTTAGNINGEN_DTO, ALFA_MEDICINCENTRUM_DTO, unit);

        assertEquals(actionEvaluation.getCareProvider().getHsaId().id(), unit.getId());
      }

      @Test
      void shallIncludeCareProviderName() {
        final var unit = alfaRegionenDtoBuilder().build();

        final var actionEvaluation = actionEvaluationFactory.create(ATHENA_REACT_ANDERSSON_DTO,
            AJLA_DOCTOR_DTO, ALFA_HUDMOTTAGNINGEN_DTO, ALFA_MEDICINCENTRUM_DTO, unit);

        assertEquals(actionEvaluation.getCareProvider().getName().name(), unit.getName());
      }
    }

    @Nested
    class ExcludePatient {

      @Test
      void createActionEvaluationWithoutPatient() {
        final var actionEvaluation = actionEvaluationFactory.create(AJLA_DOCTOR_DTO,
            ALFA_HUDMOTTAGNINGEN_DTO, ALFA_HUDMOTTAGNINGEN_DTO, ALFA_HUDMOTTAGNINGEN_DTO);

        assertNull(
            actionEvaluation.getPatient(),
            "Expected patient to be null"
        );
      }
    }

    @Nested
    class IncludePatient {

      @Test
      void shallIncludePatientDeceasedTrue() {
        final var patient = alveReactAlfredssonDtoBuilder().build();

        final var actionEvaluation = actionEvaluationFactory.create(patient, AJLA_DOCTOR_DTO,
            ALFA_HUDMOTTAGNINGEN_DTO, ALFA_HUDMOTTAGNINGEN_DTO, ALFA_HUDMOTTAGNINGEN_DTO);

        assertTrue(actionEvaluation.getPatient().getDeceased().value(),
            "Expected patient.value to be true");
      }

      @Test
      void shallIncludePatientDeceasedFalse() {
        final var patient = athenaReactAnderssonDtoBuilder().build();

        final var actionEvaluation = actionEvaluationFactory.create(patient, AJLA_DOCTOR_DTO,
            ALFA_HUDMOTTAGNINGEN_DTO, ALFA_HUDMOTTAGNINGEN_DTO, ALFA_HUDMOTTAGNINGEN_DTO);

        assertFalse(actionEvaluation.getPatient().getDeceased().value(),
            "Expected patient.value to be false");
      }

      @Test
      void shallIncludePatientIdTypePersonalIdentityNumber() {
        final var patient = athenaReactAnderssonDtoBuilder()
            .build();

        final var actionEvaluation = actionEvaluationFactory.create(patient, AJLA_DOCTOR_DTO,
            ALFA_HUDMOTTAGNINGEN_DTO, ALFA_HUDMOTTAGNINGEN_DTO, ALFA_HUDMOTTAGNINGEN_DTO);

        assertEquals(PersonIdType.PERSONAL_IDENTITY_NUMBER,
            actionEvaluation.getPatient().getId().getType());
      }

      @Test
      void shallIncludePatientIdTypeCoordinationNumber() {
        final var patient = athenaReactAnderssonDtoBuilder()
            .id(
                PersonIdDTO.builder()
                    .type(PersonIdTypeDTO.COORDINATION_NUMBER)
                    .id(ID)
                    .build()
            )
            .build();

        final var actionEvaluation = actionEvaluationFactory.create(patient, AJLA_DOCTOR_DTO,
            ALFA_HUDMOTTAGNINGEN_DTO, ALFA_HUDMOTTAGNINGEN_DTO, ALFA_HUDMOTTAGNINGEN_DTO);

        assertEquals(PersonIdType.COORDINATION_NUMBER,
            actionEvaluation.getPatient().getId().getType());
      }

      @Test
      void shallIncludePatientIdId() {
        final var patient = athenaReactAnderssonDtoBuilder()
            .id(
                PersonIdDTO.builder()
                    .type(PersonIdTypeDTO.COORDINATION_NUMBER)
                    .id(ID)
                    .build()
            )
            .build();

        final var actionEvaluation = actionEvaluationFactory.create(patient, AJLA_DOCTOR_DTO,
            ALFA_HUDMOTTAGNINGEN_DTO, ALFA_HUDMOTTAGNINGEN_DTO, ALFA_HUDMOTTAGNINGEN_DTO);

        assertEquals(patient.getId().getId(), actionEvaluation.getPatient().getId().getId());
      }

      @Test
      void shallIncludePatientFirstName() {
        final var patient = athenaReactAnderssonDtoBuilder()
            .firstName(FIRST_NAME)
            .build();

        final var actionEvaluation = actionEvaluationFactory.create(patient, AJLA_DOCTOR_DTO,
            ALFA_HUDMOTTAGNINGEN_DTO, ALFA_HUDMOTTAGNINGEN_DTO, ALFA_HUDMOTTAGNINGEN_DTO);

        assertEquals(patient.getFirstName(),
            actionEvaluation.getPatient().getName().getFirstName());
      }

      @Test
      void shallIncludePatientMiddleName() {
        final var patient = athenaReactAnderssonDtoBuilder()
            .middleName(MIDDLE_NAME)
            .build();

        final var actionEvaluation = actionEvaluationFactory.create(patient, AJLA_DOCTOR_DTO,
            ALFA_HUDMOTTAGNINGEN_DTO, ALFA_HUDMOTTAGNINGEN_DTO, ALFA_HUDMOTTAGNINGEN_DTO);

        assertEquals(patient.getMiddleName(),
            actionEvaluation.getPatient().getName().getMiddleName());
      }

      @Test
      void shallIncludePatientLastName() {
        final var patient = athenaReactAnderssonDtoBuilder()
            .lastName(LAST_NAME)
            .build();

        final var actionEvaluation = actionEvaluationFactory.create(patient, AJLA_DOCTOR_DTO,
            ALFA_HUDMOTTAGNINGEN_DTO, ALFA_HUDMOTTAGNINGEN_DTO, ALFA_HUDMOTTAGNINGEN_DTO);

        assertEquals(patient.getLastName(), actionEvaluation.getPatient().getName().getLastName());
      }

      @Test
      void shallIncludePatientCity() {
        final var patient = athenaReactAnderssonDtoBuilder()
            .city(CITY)
            .build();

        final var actionEvaluation = actionEvaluationFactory.create(patient, AJLA_DOCTOR_DTO,
            ALFA_HUDMOTTAGNINGEN_DTO, ALFA_HUDMOTTAGNINGEN_DTO, ALFA_HUDMOTTAGNINGEN_DTO);

        assertEquals(patient.getCity(), actionEvaluation.getPatient().getAddress().getCity());
      }

      @Test
      void shallIncludePatientStreet() {
        final var patient = athenaReactAnderssonDtoBuilder()
            .street(STREET)
            .build();

        final var actionEvaluation = actionEvaluationFactory.create(patient, AJLA_DOCTOR_DTO,
            ALFA_HUDMOTTAGNINGEN_DTO, ALFA_HUDMOTTAGNINGEN_DTO, ALFA_HUDMOTTAGNINGEN_DTO);

        assertEquals(patient.getStreet(), actionEvaluation.getPatient().getAddress().getStreet());
      }

      @Test
      void shallIncludePatientZipCode() {
        final var patient = athenaReactAnderssonDtoBuilder()
            .zipCode(ZIP_CODE)
            .build();

        final var actionEvaluation = actionEvaluationFactory.create(patient, AJLA_DOCTOR_DTO,
            ALFA_HUDMOTTAGNINGEN_DTO, ALFA_HUDMOTTAGNINGEN_DTO, ALFA_HUDMOTTAGNINGEN_DTO);

        assertEquals(patient.getZipCode(), actionEvaluation.getPatient().getAddress().getZipCode());
      }

      @Test
      void shallIncludePatientTestIndicatedTrue() {
        final var patient = athenaReactAnderssonDtoBuilder()
            .testIndicated(true)
            .build();

        final var actionEvaluation = actionEvaluationFactory.create(patient, AJLA_DOCTOR_DTO,
            ALFA_HUDMOTTAGNINGEN_DTO, ALFA_HUDMOTTAGNINGEN_DTO, ALFA_HUDMOTTAGNINGEN_DTO);

        assertEquals(patient.getTestIndicated(),
            actionEvaluation.getPatient().getTestIndicated().value());
      }

      @Test
      void shallIncludePatientTestIndicatedFalse() {
        final var patient = athenaReactAnderssonDtoBuilder()
            .testIndicated(false)
            .build();

        final var actionEvaluation = actionEvaluationFactory.create(patient, AJLA_DOCTOR_DTO,
            ALFA_HUDMOTTAGNINGEN_DTO, ALFA_HUDMOTTAGNINGEN_DTO, ALFA_HUDMOTTAGNINGEN_DTO);

        assertEquals(patient.getTestIndicated(),
            actionEvaluation.getPatient().getTestIndicated().value());
      }

      @Test
      void shallIncludePatientProtectedPersonTrue() {
        final var patient = athenaReactAnderssonDtoBuilder()
            .protectedPerson(true)
            .build();

        final var actionEvaluation = actionEvaluationFactory.create(patient, AJLA_DOCTOR_DTO,
            ALFA_HUDMOTTAGNINGEN_DTO, ALFA_HUDMOTTAGNINGEN_DTO, ALFA_HUDMOTTAGNINGEN_DTO);

        assertEquals(patient.getProtectedPerson(),
            actionEvaluation.getPatient().getProtectedPerson().value());
      }

      @Test
      void shallIncludePatientProtectedPersonFalse() {
        final var patient = athenaReactAnderssonDtoBuilder()
            .protectedPerson(false)
            .build();

        final var actionEvaluation = actionEvaluationFactory.create(patient, AJLA_DOCTOR_DTO,
            ALFA_HUDMOTTAGNINGEN_DTO, ALFA_HUDMOTTAGNINGEN_DTO, ALFA_HUDMOTTAGNINGEN_DTO);

        assertEquals(patient.getProtectedPerson(),
            actionEvaluation.getPatient().getProtectedPerson().value());
      }
    }

    @Nested
    class IncludeUser {

      @Test
      void shallIncludeUserId() {
        final var user = ajlaDoktorDtoBuilder().build();

        final var actionEvaluation = actionEvaluationFactory.create(ATHENA_REACT_ANDERSSON_DTO,
            user, ALFA_HUDMOTTAGNINGEN_DTO, ALFA_HUDMOTTAGNINGEN_DTO, ALFA_HUDMOTTAGNINGEN_DTO);

        assertEquals(user.getId(), actionEvaluation.getUser().getHsaId().id());
      }

      @Test
      void shallIncludeUserBlockedFalse() {
        final var user = ajlaDoktorDtoBuilder().build();

        final var actionEvaluation = actionEvaluationFactory.create(ATHENA_REACT_ANDERSSON_DTO,
            user, ALFA_HUDMOTTAGNINGEN_DTO, ALFA_HUDMOTTAGNINGEN_DTO, ALFA_HUDMOTTAGNINGEN_DTO);

        assertFalse(actionEvaluation.getUser().getBlocked().value(),
            "Expected user.value to be false");
      }

      @Test
      void shallIncludeUserBlockedTrue() {
        final var user = ajlaDoktorDtoBuilder()
            .blocked(true)
            .build();

        final var actionEvaluation = actionEvaluationFactory.create(ATHENA_REACT_ANDERSSON_DTO,
            user, ALFA_HUDMOTTAGNINGEN_DTO, ALFA_HUDMOTTAGNINGEN_DTO, ALFA_HUDMOTTAGNINGEN_DTO);

        assertTrue(actionEvaluation.getUser().getBlocked().value(),
            "Expected user.value to be true");
      }

      @Test
      void shallIncludeUserName() {
        final var user = ajlaDoktorDtoBuilder().build();

        final var actionEvaluation = actionEvaluationFactory.create(ATHENA_REACT_ANDERSSON_DTO,
            user,
            ALFA_HUDMOTTAGNINGEN_DTO, ALFA_HUDMOTTAGNINGEN_DTO, ALFA_HUDMOTTAGNINGEN_DTO);

        assertEquals(user.getName(), actionEvaluation.getUser().getName().fullName());
      }
    }
  }
}
