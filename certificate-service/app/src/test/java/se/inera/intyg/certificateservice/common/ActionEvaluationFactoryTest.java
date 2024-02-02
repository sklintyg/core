package se.inera.intyg.certificateservice.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.common.ActionEvaluationFactory;
import se.inera.intyg.certificateservice.application.common.dto.PatientDTO;
import se.inera.intyg.certificateservice.application.common.dto.PersonIdDTO;
import se.inera.intyg.certificateservice.application.common.dto.PersonIdTypeDTO;
import se.inera.intyg.certificateservice.application.common.dto.UnitDTO;
import se.inera.intyg.certificateservice.application.common.dto.UserDTO;
import se.inera.intyg.certificateservice.domain.patient.model.PersonIdType;

class ActionEvaluationFactoryTest {

  private static final String HSA_ID = "hsaId";
  private static final String EMAIL = "email";
  private static final String ID = "patientId";
  private static final String FIRST_NAME = "firstName";
  private static final String MIDDLE_NAME = "middleName";
  private static final String LAST_NAME = "lastName";
  private static final String FULL_NAME = "fullName";
  private static final String CITY = "city";
  private static final String STREET = "street";
  private static final String NAME = "userName";
  private static final String USER_ID = "userId";
  private ActionEvaluationFactory actionEvaluationFactory;

  private static final UserDTO DEFAULT_USER = UserDTO.builder()
      .name("defaultName")
      .blocked(false)
      .build();

  private static final PatientDTO DEFAULT_PATIENT_DTO = PatientDTO.builder()
      .deceased(false)
      .build();

  private static final String UNIT_NAME = "unitName";
  private static final String UNIT_ADDRESS = "unitAddress";
  private static final String UNIT_CITY = "unitCity";
  private static final String PHONE_NUMBER = "phoneNumber";
  private static final String ZIP_CODE = "zipCode";

  private PatientDTO.PatientDTOBuilder patientBuilder = PatientDTO.builder()
      .deceased(false)
      .protectedPerson(false)
      .testIndicated(false)
      .id(
          PersonIdDTO.builder()
              .type(PersonIdTypeDTO.PERSONAL_IDENTITY_NUMBER)
              .id("defaultId")
              .build()
      )
      .city("defaultCity")
      .street("defaultStreet")
      .zipCode("defaultZipCode")
      .firstName("defaultFirstName")
      .lastName("defaultLastName")
      .middleName("defaultMiddleName")
      .fullName("defaultFullName");
  private UnitDTO.UnitDTOBuilder subUnitBuilder = UnitDTO.builder()
      .id("defaultId")
      .name("defaultName")
      .inactive(false)
      .address("defaultAddress")
      .city("defaultCity")
      .email("defaultEmail")
      .phoneNumber("defaultPhoneNumber")
      .zipCode("defaultZipCode");

  private UnitDTO.UnitDTOBuilder careUnitBuilder = UnitDTO.builder()
      .id("defaultId")
      .name("defaultName")
      .inactive(false)
      .address("defaultAddress")
      .city("defaultCity")
      .email("defaultEmail")
      .phoneNumber("defaultPhoneNumber")
      .zipCode("defaultZipCode");

  private UnitDTO.UnitDTOBuilder careProviderBuilder = UnitDTO.builder()
      .id("defaultId")
      .name("defaultName")
      .inactive(false)
      .address("defaultAddress")
      .city("defaultCity")
      .email("defaultEmail")
      .phoneNumber("defaultPhoneNumber")
      .zipCode("defaultZipCode");

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
        final var unit = careUnitBuilder.id(HSA_ID).build();
        final var actionEvaluation = actionEvaluationFactory.create(patientBuilder.build(),
            DEFAULT_USER, subUnitBuilder.build(), unit, careProviderBuilder.build());

        assertEquals(actionEvaluation.getCareUnit().getHsaId().id(), unit.getId());
      }

      @Test
      void shallIncludeCareUnitName() {
        final var unit = careUnitBuilder.name(UNIT_NAME).build();

        final var actionEvaluation = actionEvaluationFactory.create(patientBuilder.build(),
            DEFAULT_USER, subUnitBuilder.build(), unit, careProviderBuilder.build());

        assertEquals(actionEvaluation.getCareUnit().getName().name(), unit.getName());
      }

      @Test
      void shallIncludeCareUnitAddress() {
        final var unit = careUnitBuilder.address(UNIT_ADDRESS).build();

        final var actionEvaluation = actionEvaluationFactory.create(patientBuilder.build(),
            DEFAULT_USER, subUnitBuilder.build(), unit, careProviderBuilder.build());

        assertEquals(actionEvaluation.getCareUnit().getAddress().getAddress(), unit.getAddress());
      }

      @Test
      void shallIncludeCareUnitZipCode() {
        final var unit = careUnitBuilder.zipCode(ZIP_CODE).build();

        final var actionEvaluation = actionEvaluationFactory.create(patientBuilder.build(),
            DEFAULT_USER, subUnitBuilder.build(), unit, careProviderBuilder.build());

        assertEquals(actionEvaluation.getCareUnit().getAddress().getZipCode(), unit.getZipCode());
      }

      @Test
      void shallIncludeCareUnitCity() {
        final var unit = careUnitBuilder.city("UNIT_CITY").build();

        final var actionEvaluation = actionEvaluationFactory.create(patientBuilder.build(),
            DEFAULT_USER, subUnitBuilder.build(), unit, careProviderBuilder.build());

        assertEquals(actionEvaluation.getCareUnit().getAddress().getCity(), unit.getCity());
      }

      @Test
      void shallIncludeCareUnitPhoneNumber() {
        final var unit = careUnitBuilder.phoneNumber(PHONE_NUMBER).build();

        final var actionEvaluation = actionEvaluationFactory.create(patientBuilder.build(),
            DEFAULT_USER, subUnitBuilder.build(), unit, careProviderBuilder.build());

        assertEquals(actionEvaluation.getCareUnit().getContactInfo().getPhoneNumber(),
            unit.getPhoneNumber());
      }

      @Test
      void shallIncludeCareUnitEmail() {
        final var unit = careUnitBuilder.email(EMAIL).build();

        final var actionEvaluation = actionEvaluationFactory.create(patientBuilder.build(),
            DEFAULT_USER,
            subUnitBuilder.build(), unit, careProviderBuilder.build());

        assertEquals(actionEvaluation.getCareUnit().getContactInfo().getEmail(), unit.getEmail());
      }
    }

    @Nested
    class IncludeSubUnit {

      @Test
      void shallIncludeSubUnitId() {
        final var unit = subUnitBuilder.id(HSA_ID).build();
        final var actionEvaluation = actionEvaluationFactory.create(patientBuilder.build(),
            DEFAULT_USER, unit, careUnitBuilder.build(), careProviderBuilder.build());

        assertEquals(actionEvaluation.getSubUnit().getHsaId().id(), unit.getId());
      }

      @Test
      void shallIncludeSubunitName() {
        final var unit = subUnitBuilder.name(UNIT_NAME).build();

        final var actionEvaluation = actionEvaluationFactory.create(patientBuilder.build(),
            DEFAULT_USER, unit, careUnitBuilder.build(), careProviderBuilder.build());

        assertEquals(actionEvaluation.getSubUnit().getName().name(), unit.getName());
      }

      @Test
      void shallIncludeSubunitAddress() {
        final var unit = subUnitBuilder.address(UNIT_ADDRESS).build();

        final var actionEvaluation = actionEvaluationFactory.create(patientBuilder.build(),
            DEFAULT_USER, unit, careUnitBuilder.build(), careProviderBuilder.build());

        assertEquals(actionEvaluation.getSubUnit().getAddress().getAddress(), unit.getAddress());
      }

      @Test
      void shallIncludeSubunitZipCode() {
        final var unit = subUnitBuilder.zipCode(ZIP_CODE).build();

        final var actionEvaluation = actionEvaluationFactory.create(patientBuilder.build(),
            DEFAULT_USER, unit, careUnitBuilder.build(), careProviderBuilder.build());

        assertEquals(actionEvaluation.getSubUnit().getAddress().getZipCode(), unit.getZipCode());
      }

      @Test
      void shallIncludeSubunitCity() {
        final var unit = subUnitBuilder.city(UNIT_CITY).build();

        final var actionEvaluation = actionEvaluationFactory.create(patientBuilder.build(),
            DEFAULT_USER, unit, careUnitBuilder.build(), careProviderBuilder.build());

        assertEquals(actionEvaluation.getSubUnit().getAddress().getCity(), unit.getCity());
      }

      @Test
      void shallIncludeSubunitPhoneNumber() {
        final var unit = subUnitBuilder.phoneNumber(PHONE_NUMBER).build();

        final var actionEvaluation = actionEvaluationFactory.create(patientBuilder.build(),
            DEFAULT_USER, unit, careUnitBuilder.build(), careProviderBuilder.build());

        assertEquals(actionEvaluation.getSubUnit().getContactInfo().getPhoneNumber(),
            unit.getPhoneNumber());
      }

      @Test
      void shallIncludeSubunitEmail() {
        final var unit = subUnitBuilder.email(EMAIL).build();

        final var actionEvaluation = actionEvaluationFactory.create(patientBuilder.build(),
            DEFAULT_USER, unit, careUnitBuilder.build(), careProviderBuilder.build());

        assertEquals(actionEvaluation.getSubUnit().getContactInfo().getEmail(), unit.getEmail());
      }

      @Test
      void shallIncludeSubunitInactiveTrue() {
        final var unit = subUnitBuilder.inactive(true).build();

        final var actionEvaluation = actionEvaluationFactory.create(patientBuilder.build(),
            DEFAULT_USER, unit, careUnitBuilder.build(), careProviderBuilder.build());

        assertEquals(actionEvaluation.getSubUnit().getInactive().value(), unit.getInactive());
      }

      @Test
      void shallIncludeSubunitInactiveFalse() {
        final var unit = subUnitBuilder.inactive(false).build();

        final var actionEvaluation = actionEvaluationFactory.create(patientBuilder.build(),
            DEFAULT_USER, unit, careUnitBuilder.build(), careProviderBuilder.build());

        assertEquals(actionEvaluation.getSubUnit().getInactive().value(), unit.getInactive());
      }
    }

    @Nested
    class IncludeCareProvider {

      @Test
      void shallIncludeCareProviderId() {
        final var unit = careProviderBuilder.id(HSA_ID).build();
        final var actionEvaluation = actionEvaluationFactory.create(patientBuilder.build(),
            DEFAULT_USER, subUnitBuilder.build(), careUnitBuilder.build(), unit);

        assertEquals(actionEvaluation.getCareProvider().getHsaId().id(), unit.getId());
      }

      @Test
      void shallIncludeCareProviderName() {
        final var unit = careProviderBuilder.name(UNIT_NAME).build();

        final var actionEvaluation = actionEvaluationFactory.create(patientBuilder.build(),
            DEFAULT_USER, subUnitBuilder.build(), careUnitBuilder.build(), unit);

        assertEquals(actionEvaluation.getCareProvider().getName().name(), unit.getName());
      }

      @Test
      void shallIncludeCareProviderAddress() {
        final var unit = careProviderBuilder.address(UNIT_ADDRESS).build();

        final var actionEvaluation = actionEvaluationFactory.create(patientBuilder.build(),
            DEFAULT_USER, subUnitBuilder.build(), careUnitBuilder.build(), unit);

        assertEquals(actionEvaluation.getCareProvider().getAddress().getAddress(),
            unit.getAddress());
      }

      @Test
      void shallIncludeCareProviderZipCode() {
        final var unit = careProviderBuilder.zipCode(ZIP_CODE).build();

        final var actionEvaluation = actionEvaluationFactory.create(patientBuilder.build(),
            DEFAULT_USER, subUnitBuilder.build(), careUnitBuilder.build(), unit);

        assertEquals(actionEvaluation.getCareProvider().getAddress().getZipCode(),
            unit.getZipCode());
      }

      @Test
      void shallIncludeCareProviderCity() {
        final var unit = careProviderBuilder.city(UNIT_CITY).build();

        final var actionEvaluation = actionEvaluationFactory.create(patientBuilder.build(),
            DEFAULT_USER, subUnitBuilder.build(), careUnitBuilder.build(), unit);

        assertEquals(actionEvaluation.getCareProvider().getAddress().getCity(), unit.getCity());
      }

      @Test
      void shallIncludeCareProviderPhoneNumber() {
        final var unit = careProviderBuilder.phoneNumber(PHONE_NUMBER).build();

        final var actionEvaluation = actionEvaluationFactory.create(patientBuilder.build(),
            DEFAULT_USER, subUnitBuilder.build(), careUnitBuilder.build(), unit);

        assertEquals(actionEvaluation.getCareProvider().getContactInfo().getPhoneNumber(),
            unit.getPhoneNumber());
      }

      @Test
      void shallIncludeCareProviderEmail() {
        final var unit = careProviderBuilder.email(EMAIL).build();

        final var actionEvaluation = actionEvaluationFactory.create(patientBuilder.build(),
            DEFAULT_USER, subUnitBuilder.build(), careUnitBuilder.build(), unit);

        assertEquals(actionEvaluation.getCareProvider().getContactInfo().getEmail(),
            unit.getEmail());
      }
    }

    @Nested
    class IncludePatient {

      @Test
      void shallIncludePatientDeceasedTrue() {
        final var patient = patientBuilder
            .deceased(true)
            .build();

        final var actionEvaluation = actionEvaluationFactory.create(patient, DEFAULT_USER,
            subUnitBuilder.build(), subUnitBuilder.build(), subUnitBuilder.build());

        assertTrue(actionEvaluation.getPatient().getDeceased().value(),
            "Expected patient.value to be true");
      }

      @Test
      void shallIncludePatientDeceasedFalse() {
        final var patient = patientBuilder
            .deceased(false)
            .build();

        final var actionEvaluation = actionEvaluationFactory.create(patient, DEFAULT_USER,
            subUnitBuilder.build(), subUnitBuilder.build(), subUnitBuilder.build());

        assertFalse(actionEvaluation.getPatient().getDeceased().value(),
            "Expected patient.value to be false");
      }

      @Test
      void shallIncludePatientIdTypePersonalIdentityNumber() {
        final var patient = patientBuilder
            .id(
                PersonIdDTO.builder()
                    .type(PersonIdTypeDTO.PERSONAL_IDENTITY_NUMBER)
                    .id(ID)
                    .build()
            )
            .build();

        final var actionEvaluation = actionEvaluationFactory.create(patient, DEFAULT_USER,
            subUnitBuilder.build(), subUnitBuilder.build(), subUnitBuilder.build());

        assertEquals(PersonIdType.PERSONAL_IDENTITY_NUMBER,
            actionEvaluation.getPatient().getId().getType());
      }

      @Test
      void shallIncludePatientIdTypeCoordinationNumber() {
        final var patient = patientBuilder
            .id(
                PersonIdDTO.builder()
                    .type(PersonIdTypeDTO.COORDINATION_NUMBER)
                    .id(ID)
                    .build()
            )
            .build();

        final var actionEvaluation = actionEvaluationFactory.create(patient, DEFAULT_USER,
            subUnitBuilder.build(), subUnitBuilder.build(), subUnitBuilder.build());

        assertEquals(PersonIdType.COORDINATION_NUMBER,
            actionEvaluation.getPatient().getId().getType());
      }

      @Test
      void shallIncludePatientIdId() {
        final var patient = patientBuilder
            .id(
                PersonIdDTO.builder()
                    .type(PersonIdTypeDTO.COORDINATION_NUMBER)
                    .id(ID)
                    .build()
            )
            .build();

        final var actionEvaluation = actionEvaluationFactory.create(patient, DEFAULT_USER,
            subUnitBuilder.build(), subUnitBuilder.build(), subUnitBuilder.build());

        assertEquals(patient.getId().getId(), actionEvaluation.getPatient().getId().getId());
      }

      @Test
      void shallIncludePatientFirstName() {
        final var patient = patientBuilder
            .firstName(FIRST_NAME)
            .build();

        final var actionEvaluation = actionEvaluationFactory.create(patient, DEFAULT_USER,
            subUnitBuilder.build(), subUnitBuilder.build(), subUnitBuilder.build());

        assertEquals(patient.getFirstName(),
            actionEvaluation.getPatient().getName().getFirstName());
      }

      @Test
      void shallIncludePatientMiddleName() {
        final var patient = patientBuilder
            .middleName(MIDDLE_NAME)
            .build();

        final var actionEvaluation = actionEvaluationFactory.create(patient, DEFAULT_USER,
            subUnitBuilder.build(), subUnitBuilder.build(), subUnitBuilder.build());

        assertEquals(patient.getMiddleName(),
            actionEvaluation.getPatient().getName().getMiddleName());
      }

      @Test
      void shallIncludePatientLastName() {
        final var patient = patientBuilder
            .lastName(LAST_NAME)
            .build();

        final var actionEvaluation = actionEvaluationFactory.create(patient, DEFAULT_USER,
            subUnitBuilder.build(), subUnitBuilder.build(), subUnitBuilder.build());

        assertEquals(patient.getLastName(), actionEvaluation.getPatient().getName().getLastName());
      }

      @Test
      void shallIncludePatientFullName() {
        final var patient = patientBuilder
            .fullName(FULL_NAME)
            .build();

        final var actionEvaluation = actionEvaluationFactory.create(patient, DEFAULT_USER,
            subUnitBuilder.build(), subUnitBuilder.build(), subUnitBuilder.build());

        assertEquals(patient.getFullName(), actionEvaluation.getPatient().getName().getFullName());
      }

      @Test
      void shallIncludePatientCity() {
        final var patient = patientBuilder
            .city(CITY)
            .build();

        final var actionEvaluation = actionEvaluationFactory.create(patient, DEFAULT_USER,
            subUnitBuilder.build(), subUnitBuilder.build(), subUnitBuilder.build());

        assertEquals(patient.getCity(), actionEvaluation.getPatient().getAddress().getCity());
      }

      @Test
      void shallIncludePatientStreet() {
        final var patient = patientBuilder
            .street(STREET)
            .build();

        final var actionEvaluation = actionEvaluationFactory.create(patient, DEFAULT_USER,
            subUnitBuilder.build(), subUnitBuilder.build(), subUnitBuilder.build());

        assertEquals(patient.getStreet(), actionEvaluation.getPatient().getAddress().getStreet());
      }

      @Test
      void shallIncludePatientZipCode() {
        final var patient = patientBuilder
            .zipCode(ZIP_CODE)
            .build();

        final var actionEvaluation = actionEvaluationFactory.create(patient, DEFAULT_USER,
            subUnitBuilder.build(), subUnitBuilder.build(), subUnitBuilder.build());

        assertEquals(patient.getZipCode(), actionEvaluation.getPatient().getAddress().getZipCode());
      }

      @Test
      void shallIncludePatientTestIndicatedTrue() {
        final var patient = patientBuilder
            .testIndicated(true)
            .build();

        final var actionEvaluation = actionEvaluationFactory.create(patient, DEFAULT_USER,
            subUnitBuilder.build(), subUnitBuilder.build(), subUnitBuilder.build());

        assertEquals(patient.getTestIndicated(),
            actionEvaluation.getPatient().getTestIndicated().value());
      }

      @Test
      void shallIncludePatientTestIndicatedFalse() {
        final var patient = patientBuilder
            .testIndicated(false)
            .build();

        final var actionEvaluation = actionEvaluationFactory.create(patient, DEFAULT_USER,
            subUnitBuilder.build(), subUnitBuilder.build(), subUnitBuilder.build());

        assertEquals(patient.getTestIndicated(),
            actionEvaluation.getPatient().getTestIndicated().value());
      }

      @Test
      void shallIncludePatientProtectedPersonTrue() {
        final var patient = patientBuilder
            .protectedPerson(true)
            .build();

        final var actionEvaluation = actionEvaluationFactory.create(patient, DEFAULT_USER,
            subUnitBuilder.build(), subUnitBuilder.build(), subUnitBuilder.build());

        assertEquals(patient.getProtectedPerson(),
            actionEvaluation.getPatient().getProtectedPerson().value());
      }

      @Test
      void shallIncludePatientProtectedPersonFalse() {
        final var patient = patientBuilder
            .protectedPerson(false)
            .build();

        final var actionEvaluation = actionEvaluationFactory.create(patient, DEFAULT_USER,
            subUnitBuilder.build(), subUnitBuilder.build(), subUnitBuilder.build());

        assertEquals(patient.getProtectedPerson(),
            actionEvaluation.getPatient().getProtectedPerson().value());
      }
    }

    @Nested
    class IncludeUser {

      @Test
      void shallIncludeUserId() {
        final var user = UserDTO.builder()
            .id(USER_ID)
            .name(NAME)
            .blocked(true)
            .build();

        final var actionEvaluation = actionEvaluationFactory.create(patientBuilder.build(), user,
            subUnitBuilder.build(), subUnitBuilder.build(), subUnitBuilder.build());

        assertEquals(user.getId(), actionEvaluation.getUser().getHsaId().id());
      }

      @Test
      void shallIncludeUserBlockedFalse() {
        final var user = UserDTO.builder()
            .name(NAME)
            .blocked(false)
            .build();

        final var actionEvaluation = actionEvaluationFactory.create(patientBuilder.build(), user,
            subUnitBuilder.build(), subUnitBuilder.build(), subUnitBuilder.build());

        assertFalse(actionEvaluation.getUser().getBlocked().value(),
            "Expected user.value to be false");
      }

      @Test
      void shallIncludeUserBlockedTrue() {
        final var user = UserDTO.builder()
            .name(NAME)
            .blocked(true)
            .build();

        final var actionEvaluation = actionEvaluationFactory.create(patientBuilder.build(), user,
            subUnitBuilder.build(), subUnitBuilder.build(), subUnitBuilder.build());

        assertTrue(actionEvaluation.getUser().getBlocked().value(),
            "Expected user.value to be true");
      }

      @Test
      void shallIncludeUserName() {
        final var user = UserDTO.builder()
            .name(NAME)
            .blocked(true)
            .build();

        final var actionEvaluation = actionEvaluationFactory.create(patientBuilder.build(), user,
            subUnitBuilder.build(), subUnitBuilder.build(), subUnitBuilder.build());

        assertEquals(user.getName(), actionEvaluation.getUser().getName().getFullName());
      }
    }
  }
}
