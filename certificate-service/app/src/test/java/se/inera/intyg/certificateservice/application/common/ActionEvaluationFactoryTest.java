package se.inera.intyg.certificateservice.application.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.ATHENA_REACT_ANDERSSON_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.athenaReactAnderssonDtoBuilder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
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
  private static final String CITY = "city";
  private static final String STREET = "street";
  private static final String NAME = "userName";
  private static final String USER_ID = "userId";
  private ActionEvaluationFactory actionEvaluationFactory;

  private static final UserDTO DEFAULT_USER = UserDTO.builder()
      .name("defaultName")
      .blocked(false)
      .build();

  private static final String UNIT_NAME = "unitName";
  private static final String UNIT_ADDRESS = "unitAddress";
  private static final String UNIT_CITY = "unitCity";
  private static final String PHONE_NUMBER = "phoneNumber";
  private static final String ZIP_CODE = "zipCode";

  private final UnitDTO.UnitDTOBuilder subUnitBuilder = UnitDTO.builder()
      .id("defaultId")
      .name("defaultName")
      .inactive(false)
      .address("defaultAddress")
      .city("defaultCity")
      .email("defaultEmail")
      .phoneNumber("defaultPhoneNumber")
      .zipCode("defaultZipCode");

  private final UnitDTO.UnitDTOBuilder careUnitBuilder = UnitDTO.builder()
      .id("defaultId")
      .name("defaultName")
      .inactive(false)
      .address("defaultAddress")
      .city("defaultCity")
      .email("defaultEmail")
      .phoneNumber("defaultPhoneNumber")
      .zipCode("defaultZipCode");

  private final UnitDTO.UnitDTOBuilder careProviderBuilder = UnitDTO.builder()
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
        final var actionEvaluation = actionEvaluationFactory.create(ATHENA_REACT_ANDERSSON_DTO,
            DEFAULT_USER, subUnitBuilder.build(), unit, careProviderBuilder.build());

        assertEquals(actionEvaluation.getCareUnit().getHsaId().id(), unit.getId());
      }

      @Test
      void shallIncludeCareUnitName() {
        final var unit = careUnitBuilder.name(UNIT_NAME).build();

        final var actionEvaluation = actionEvaluationFactory.create(ATHENA_REACT_ANDERSSON_DTO,
            DEFAULT_USER, subUnitBuilder.build(), unit, careProviderBuilder.build());

        assertEquals(actionEvaluation.getCareUnit().getName().name(), unit.getName());
      }

      @Test
      void shallIncludeCareUnitAddress() {
        final var unit = careUnitBuilder.address(UNIT_ADDRESS).build();

        final var actionEvaluation = actionEvaluationFactory.create(ATHENA_REACT_ANDERSSON_DTO,
            DEFAULT_USER, subUnitBuilder.build(), unit, careProviderBuilder.build());

        assertEquals(actionEvaluation.getCareUnit().getAddress().getAddress(), unit.getAddress());
      }

      @Test
      void shallIncludeCareUnitZipCode() {
        final var unit = careUnitBuilder.zipCode(ZIP_CODE).build();

        final var actionEvaluation = actionEvaluationFactory.create(ATHENA_REACT_ANDERSSON_DTO,
            DEFAULT_USER, subUnitBuilder.build(), unit, careProviderBuilder.build());

        assertEquals(actionEvaluation.getCareUnit().getAddress().getZipCode(), unit.getZipCode());
      }

      @Test
      void shallIncludeCareUnitCity() {
        final var unit = careUnitBuilder.city("UNIT_CITY").build();

        final var actionEvaluation = actionEvaluationFactory.create(ATHENA_REACT_ANDERSSON_DTO,
            DEFAULT_USER, subUnitBuilder.build(), unit, careProviderBuilder.build());

        assertEquals(actionEvaluation.getCareUnit().getAddress().getCity(), unit.getCity());
      }

      @Test
      void shallIncludeCareUnitPhoneNumber() {
        final var unit = careUnitBuilder.phoneNumber(PHONE_NUMBER).build();

        final var actionEvaluation = actionEvaluationFactory.create(ATHENA_REACT_ANDERSSON_DTO,
            DEFAULT_USER, subUnitBuilder.build(), unit, careProviderBuilder.build());

        assertEquals(actionEvaluation.getCareUnit().getContactInfo().getPhoneNumber(),
            unit.getPhoneNumber());
      }

      @Test
      void shallIncludeCareUnitEmail() {
        final var unit = careUnitBuilder.email(EMAIL).build();

        final var actionEvaluation = actionEvaluationFactory.create(ATHENA_REACT_ANDERSSON_DTO,
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
        final var actionEvaluation = actionEvaluationFactory.create(ATHENA_REACT_ANDERSSON_DTO,
            DEFAULT_USER, unit, careUnitBuilder.build(), careProviderBuilder.build());

        assertEquals(actionEvaluation.getSubUnit().getHsaId().id(), unit.getId());
      }

      @Test
      void shallIncludeSubunitName() {
        final var unit = subUnitBuilder.name(UNIT_NAME).build();

        final var actionEvaluation = actionEvaluationFactory.create(ATHENA_REACT_ANDERSSON_DTO,
            DEFAULT_USER, unit, careUnitBuilder.build(), careProviderBuilder.build());

        assertEquals(actionEvaluation.getSubUnit().getName().name(), unit.getName());
      }

      @Test
      void shallIncludeSubunitAddress() {
        final var unit = subUnitBuilder.address(UNIT_ADDRESS).build();

        final var actionEvaluation = actionEvaluationFactory.create(ATHENA_REACT_ANDERSSON_DTO,
            DEFAULT_USER, unit, careUnitBuilder.build(), careProviderBuilder.build());

        assertEquals(actionEvaluation.getSubUnit().getAddress().getAddress(), unit.getAddress());
      }

      @Test
      void shallIncludeSubunitZipCode() {
        final var unit = subUnitBuilder.zipCode(ZIP_CODE).build();

        final var actionEvaluation = actionEvaluationFactory.create(ATHENA_REACT_ANDERSSON_DTO,
            DEFAULT_USER, unit, careUnitBuilder.build(), careProviderBuilder.build());

        assertEquals(actionEvaluation.getSubUnit().getAddress().getZipCode(), unit.getZipCode());
      }

      @Test
      void shallIncludeSubunitCity() {
        final var unit = subUnitBuilder.city(UNIT_CITY).build();

        final var actionEvaluation = actionEvaluationFactory.create(ATHENA_REACT_ANDERSSON_DTO,
            DEFAULT_USER, unit, careUnitBuilder.build(), careProviderBuilder.build());

        assertEquals(actionEvaluation.getSubUnit().getAddress().getCity(), unit.getCity());
      }

      @Test
      void shallIncludeSubunitPhoneNumber() {
        final var unit = subUnitBuilder.phoneNumber(PHONE_NUMBER).build();

        final var actionEvaluation = actionEvaluationFactory.create(ATHENA_REACT_ANDERSSON_DTO,
            DEFAULT_USER, unit, careUnitBuilder.build(), careProviderBuilder.build());

        assertEquals(actionEvaluation.getSubUnit().getContactInfo().getPhoneNumber(),
            unit.getPhoneNumber());
      }

      @Test
      void shallIncludeSubunitEmail() {
        final var unit = subUnitBuilder.email(EMAIL).build();

        final var actionEvaluation = actionEvaluationFactory.create(ATHENA_REACT_ANDERSSON_DTO,
            DEFAULT_USER, unit, careUnitBuilder.build(), careProviderBuilder.build());

        assertEquals(actionEvaluation.getSubUnit().getContactInfo().getEmail(), unit.getEmail());
      }

      @Test
      void shallIncludeSubunitInactiveTrue() {
        final var unit = subUnitBuilder.inactive(true).build();

        final var actionEvaluation = actionEvaluationFactory.create(ATHENA_REACT_ANDERSSON_DTO,
            DEFAULT_USER, unit, careUnitBuilder.build(), careProviderBuilder.build());

        assertEquals(actionEvaluation.getSubUnit().getInactive().value(), unit.getInactive());
      }

      @Test
      void shallIncludeSubunitInactiveFalse() {
        final var unit = subUnitBuilder.inactive(false).build();

        final var actionEvaluation = actionEvaluationFactory.create(ATHENA_REACT_ANDERSSON_DTO,
            DEFAULT_USER, unit, careUnitBuilder.build(), careProviderBuilder.build());

        assertEquals(actionEvaluation.getSubUnit().getInactive().value(), unit.getInactive());
      }
    }

    @Nested
    class IncludeCareProvider {

      @Test
      void shallIncludeCareProviderId() {
        final var unit = careProviderBuilder.id(HSA_ID).build();
        final var actionEvaluation = actionEvaluationFactory.create(ATHENA_REACT_ANDERSSON_DTO,
            DEFAULT_USER, subUnitBuilder.build(), careUnitBuilder.build(), unit);

        assertEquals(actionEvaluation.getCareProvider().getHsaId().id(), unit.getId());
      }

      @Test
      void shallIncludeCareProviderName() {
        final var unit = careProviderBuilder.name(UNIT_NAME).build();

        final var actionEvaluation = actionEvaluationFactory.create(ATHENA_REACT_ANDERSSON_DTO,
            DEFAULT_USER, subUnitBuilder.build(), careUnitBuilder.build(), unit);

        assertEquals(actionEvaluation.getCareProvider().getName().name(), unit.getName());
      }

      @Test
      void shallIncludeCareProviderAddress() {
        final var unit = careProviderBuilder.address(UNIT_ADDRESS).build();

        final var actionEvaluation = actionEvaluationFactory.create(ATHENA_REACT_ANDERSSON_DTO,
            DEFAULT_USER, subUnitBuilder.build(), careUnitBuilder.build(), unit);

        assertEquals(actionEvaluation.getCareProvider().getAddress().getAddress(),
            unit.getAddress());
      }

      @Test
      void shallIncludeCareProviderZipCode() {
        final var unit = careProviderBuilder.zipCode(ZIP_CODE).build();

        final var actionEvaluation = actionEvaluationFactory.create(ATHENA_REACT_ANDERSSON_DTO,
            DEFAULT_USER, subUnitBuilder.build(), careUnitBuilder.build(), unit);

        assertEquals(actionEvaluation.getCareProvider().getAddress().getZipCode(),
            unit.getZipCode());
      }

      @Test
      void shallIncludeCareProviderCity() {
        final var unit = careProviderBuilder.city(UNIT_CITY).build();

        final var actionEvaluation = actionEvaluationFactory.create(ATHENA_REACT_ANDERSSON_DTO,
            DEFAULT_USER, subUnitBuilder.build(), careUnitBuilder.build(), unit);

        assertEquals(actionEvaluation.getCareProvider().getAddress().getCity(), unit.getCity());
      }

      @Test
      void shallIncludeCareProviderPhoneNumber() {
        final var unit = careProviderBuilder.phoneNumber(PHONE_NUMBER).build();

        final var actionEvaluation = actionEvaluationFactory.create(ATHENA_REACT_ANDERSSON_DTO,
            DEFAULT_USER, subUnitBuilder.build(), careUnitBuilder.build(), unit);

        assertEquals(actionEvaluation.getCareProvider().getContactInfo().getPhoneNumber(),
            unit.getPhoneNumber());
      }

      @Test
      void shallIncludeCareProviderEmail() {
        final var unit = careProviderBuilder.email(EMAIL).build();

        final var actionEvaluation = actionEvaluationFactory.create(ATHENA_REACT_ANDERSSON_DTO,
            DEFAULT_USER, subUnitBuilder.build(), careUnitBuilder.build(), unit);

        assertEquals(actionEvaluation.getCareProvider().getContactInfo().getEmail(),
            unit.getEmail());
      }
    }

    @Nested
    class ExcludePatient {

      @Test
      void createActionEvaluationWithoutPatient() {
        final var actionEvaluation = actionEvaluationFactory.create(DEFAULT_USER,
            subUnitBuilder.build(), subUnitBuilder.build(), subUnitBuilder.build());

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
        final var patient = athenaReactAnderssonDtoBuilder()
            .deceased(true)
            .build();

        final var actionEvaluation = actionEvaluationFactory.create(patient, DEFAULT_USER,
            subUnitBuilder.build(), subUnitBuilder.build(), subUnitBuilder.build());

        assertTrue(actionEvaluation.getPatient().getDeceased().value(),
            "Expected patient.value to be true");
      }

      @Test
      void shallIncludePatientDeceasedFalse() {
        final var patient = athenaReactAnderssonDtoBuilder()
            .deceased(false)
            .build();

        final var actionEvaluation = actionEvaluationFactory.create(patient, DEFAULT_USER,
            subUnitBuilder.build(), subUnitBuilder.build(), subUnitBuilder.build());

        assertFalse(actionEvaluation.getPatient().getDeceased().value(),
            "Expected patient.value to be false");
      }

      @Test
      void shallIncludePatientIdTypePersonalIdentityNumber() {
        final var patient = athenaReactAnderssonDtoBuilder()
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
        final var patient = athenaReactAnderssonDtoBuilder()
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
        final var patient = athenaReactAnderssonDtoBuilder()
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
        final var patient = athenaReactAnderssonDtoBuilder()
            .firstName(FIRST_NAME)
            .build();

        final var actionEvaluation = actionEvaluationFactory.create(patient, DEFAULT_USER,
            subUnitBuilder.build(), subUnitBuilder.build(), subUnitBuilder.build());

        assertEquals(patient.getFirstName(),
            actionEvaluation.getPatient().getName().getFirstName());
      }

      @Test
      void shallIncludePatientMiddleName() {
        final var patient = athenaReactAnderssonDtoBuilder()
            .middleName(MIDDLE_NAME)
            .build();

        final var actionEvaluation = actionEvaluationFactory.create(patient, DEFAULT_USER,
            subUnitBuilder.build(), subUnitBuilder.build(), subUnitBuilder.build());

        assertEquals(patient.getMiddleName(),
            actionEvaluation.getPatient().getName().getMiddleName());
      }

      @Test
      void shallIncludePatientLastName() {
        final var patient = athenaReactAnderssonDtoBuilder()
            .lastName(LAST_NAME)
            .build();

        final var actionEvaluation = actionEvaluationFactory.create(patient, DEFAULT_USER,
            subUnitBuilder.build(), subUnitBuilder.build(), subUnitBuilder.build());

        assertEquals(patient.getLastName(), actionEvaluation.getPatient().getName().getLastName());
      }

      @Test
      void shallIncludePatientCity() {
        final var patient = athenaReactAnderssonDtoBuilder()
            .city(CITY)
            .build();

        final var actionEvaluation = actionEvaluationFactory.create(patient, DEFAULT_USER,
            subUnitBuilder.build(), subUnitBuilder.build(), subUnitBuilder.build());

        assertEquals(patient.getCity(), actionEvaluation.getPatient().getAddress().getCity());
      }

      @Test
      void shallIncludePatientStreet() {
        final var patient = athenaReactAnderssonDtoBuilder()
            .street(STREET)
            .build();

        final var actionEvaluation = actionEvaluationFactory.create(patient, DEFAULT_USER,
            subUnitBuilder.build(), subUnitBuilder.build(), subUnitBuilder.build());

        assertEquals(patient.getStreet(), actionEvaluation.getPatient().getAddress().getStreet());
      }

      @Test
      void shallIncludePatientZipCode() {
        final var patient = athenaReactAnderssonDtoBuilder()
            .zipCode(ZIP_CODE)
            .build();

        final var actionEvaluation = actionEvaluationFactory.create(patient, DEFAULT_USER,
            subUnitBuilder.build(), subUnitBuilder.build(), subUnitBuilder.build());

        assertEquals(patient.getZipCode(), actionEvaluation.getPatient().getAddress().getZipCode());
      }

      @Test
      void shallIncludePatientTestIndicatedTrue() {
        final var patient = athenaReactAnderssonDtoBuilder()
            .testIndicated(true)
            .build();

        final var actionEvaluation = actionEvaluationFactory.create(patient, DEFAULT_USER,
            subUnitBuilder.build(), subUnitBuilder.build(), subUnitBuilder.build());

        assertEquals(patient.getTestIndicated(),
            actionEvaluation.getPatient().getTestIndicated().value());
      }

      @Test
      void shallIncludePatientTestIndicatedFalse() {
        final var patient = athenaReactAnderssonDtoBuilder()
            .testIndicated(false)
            .build();

        final var actionEvaluation = actionEvaluationFactory.create(patient, DEFAULT_USER,
            subUnitBuilder.build(), subUnitBuilder.build(), subUnitBuilder.build());

        assertEquals(patient.getTestIndicated(),
            actionEvaluation.getPatient().getTestIndicated().value());
      }

      @Test
      void shallIncludePatientProtectedPersonTrue() {
        final var patient = athenaReactAnderssonDtoBuilder()
            .protectedPerson(true)
            .build();

        final var actionEvaluation = actionEvaluationFactory.create(patient, DEFAULT_USER,
            subUnitBuilder.build(), subUnitBuilder.build(), subUnitBuilder.build());

        assertEquals(patient.getProtectedPerson(),
            actionEvaluation.getPatient().getProtectedPerson().value());
      }

      @Test
      void shallIncludePatientProtectedPersonFalse() {
        final var patient = athenaReactAnderssonDtoBuilder()
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

        final var actionEvaluation = actionEvaluationFactory.create(ATHENA_REACT_ANDERSSON_DTO,
            user,
            subUnitBuilder.build(), subUnitBuilder.build(), subUnitBuilder.build());

        assertEquals(user.getId(), actionEvaluation.getUser().getHsaId().id());
      }

      @Test
      void shallIncludeUserBlockedFalse() {
        final var user = UserDTO.builder()
            .name(NAME)
            .blocked(false)
            .build();

        final var actionEvaluation = actionEvaluationFactory.create(ATHENA_REACT_ANDERSSON_DTO,
            user,
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

        final var actionEvaluation = actionEvaluationFactory.create(ATHENA_REACT_ANDERSSON_DTO,
            user,
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

        final var actionEvaluation = actionEvaluationFactory.create(ATHENA_REACT_ANDERSSON_DTO,
            user,
            subUnitBuilder.build(), subUnitBuilder.build(), subUnitBuilder.build());

        assertEquals(user.getName(), actionEvaluation.getUser().getName().fullName());
      }
    }
  }
}
