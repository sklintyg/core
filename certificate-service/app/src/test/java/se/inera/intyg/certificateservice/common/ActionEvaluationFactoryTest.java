package se.inera.intyg.certificateservice.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.common.ActionEvaluationFactory;
import se.inera.intyg.certificateservice.application.common.dto.PatientDTO;
import se.inera.intyg.certificateservice.application.common.dto.UnitDTO;
import se.inera.intyg.certificateservice.application.common.dto.UserDTO;

class ActionEvaluationFactoryTest {

  private static final String HSA_ID = "hsaId";
  private static final String EMAIL = "email";
  private ActionEvaluationFactory actionEvaluationFactory;

  private static final UserDTO DEFAULT_USER = UserDTO.builder()
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
  class Maximal {

    @Nested
    class IncludeCareUnit {

      @Test
      void shallIncludeCareUnitId() {
        final var unit = careUnitBuilder.id(HSA_ID).build();
        final var actionEvaluation = actionEvaluationFactory.create(DEFAULT_PATIENT_DTO,
            DEFAULT_USER, subUnitBuilder.build(), unit, careProviderBuilder.build());

        assertEquals(actionEvaluation.getCareUnit().getHsaId().id(), unit.getId());
      }

      @Test
      void shallIncludeCareUnitName() {
        final var unit = careUnitBuilder.name(UNIT_NAME).build();

        final var actionEvaluation = actionEvaluationFactory.create(DEFAULT_PATIENT_DTO,
            DEFAULT_USER, subUnitBuilder.build(), unit, careProviderBuilder.build());

        assertEquals(actionEvaluation.getCareUnit().getName(), unit.getName());
      }

      @Test
      void shallIncludeCareUnitAddress() {
        final var unit = careUnitBuilder.address(UNIT_ADDRESS).build();

        final var actionEvaluation = actionEvaluationFactory.create(DEFAULT_PATIENT_DTO,
            DEFAULT_USER, subUnitBuilder.build(), unit, careProviderBuilder.build());

        assertEquals(actionEvaluation.getCareUnit().getAddress(), unit.getAddress());
      }

      @Test
      void shallIncludeCareUnitZipCode() {
        final var unit = careUnitBuilder.zipCode(ZIP_CODE).build();

        final var actionEvaluation = actionEvaluationFactory.create(DEFAULT_PATIENT_DTO,
            DEFAULT_USER, subUnitBuilder.build(), unit, careProviderBuilder.build());

        assertEquals(actionEvaluation.getCareUnit().getZipCode(), unit.getZipCode());
      }

      @Test
      void shallIncludeCareUnitCity() {
        final var unit = careUnitBuilder.city("UNIT_CITY").build();

        final var actionEvaluation = actionEvaluationFactory.create(DEFAULT_PATIENT_DTO,
            DEFAULT_USER, subUnitBuilder.build(), unit, careProviderBuilder.build());

        assertEquals(actionEvaluation.getCareUnit().getCity(), unit.getCity());
      }

      @Test
      void shallIncludeCareUnitPhoneNumber() {
        final var unit = careUnitBuilder.phoneNumber(PHONE_NUMBER).build();

        final var actionEvaluation = actionEvaluationFactory.create(DEFAULT_PATIENT_DTO,
            DEFAULT_USER, subUnitBuilder.build(), unit, careProviderBuilder.build());

        assertEquals(actionEvaluation.getCareUnit().getPhoneNumber(), unit.getPhoneNumber());
      }

      @Test
      void shallIncludeCareUnitEmail() {
        final var unit = careUnitBuilder.email(EMAIL).build();

        final var actionEvaluation = actionEvaluationFactory.create(DEFAULT_PATIENT_DTO,
            DEFAULT_USER,
            subUnitBuilder.build(), unit, careProviderBuilder.build());

        assertEquals(actionEvaluation.getCareUnit().getEmail(), unit.getEmail());
      }

      @Test
      void shallIncludeCareUnitInactive() {
        final var unit = careUnitBuilder.inactive(true).build();

        final var actionEvaluation = actionEvaluationFactory.create(DEFAULT_PATIENT_DTO,
            DEFAULT_USER, subUnitBuilder.build(), unit, careProviderBuilder.build());

        assertEquals(actionEvaluation.getCareUnit().getInactive(), unit.getInactive());
      }
    }

    @Nested
    class IncludeSubUnit {

      @Test
      void shallIncludeSubUnitId() {
        final var unit = subUnitBuilder.id(HSA_ID).build();
        final var actionEvaluation = actionEvaluationFactory.create(DEFAULT_PATIENT_DTO,
            DEFAULT_USER, unit, careUnitBuilder.build(), careProviderBuilder.build());

        assertEquals(actionEvaluation.getSubUnit().getHsaId().id(), unit.getId());
      }

      @Test
      void shallIncludeSubunitName() {
        final var unit = subUnitBuilder.name(UNIT_NAME).build();

        final var actionEvaluation = actionEvaluationFactory.create(DEFAULT_PATIENT_DTO,
            DEFAULT_USER, unit, careUnitBuilder.build(), careProviderBuilder.build());

        assertEquals(actionEvaluation.getSubUnit().getName(), unit.getName());
      }

      @Test
      void shallIncludeSubunitAddress() {
        final var unit = subUnitBuilder.address(UNIT_ADDRESS).build();

        final var actionEvaluation = actionEvaluationFactory.create(DEFAULT_PATIENT_DTO,
            DEFAULT_USER, unit, careUnitBuilder.build(), careProviderBuilder.build());

        assertEquals(actionEvaluation.getSubUnit().getAddress(), unit.getAddress());
      }

      @Test
      void shallIncludeSubunitZipCode() {
        final var unit = subUnitBuilder.zipCode(ZIP_CODE).build();

        final var actionEvaluation = actionEvaluationFactory.create(DEFAULT_PATIENT_DTO,
            DEFAULT_USER, unit, careUnitBuilder.build(), careProviderBuilder.build());

        assertEquals(actionEvaluation.getSubUnit().getZipCode(), unit.getZipCode());
      }

      @Test
      void shallIncludeSubunitCity() {
        final var unit = subUnitBuilder.city(UNIT_CITY).build();

        final var actionEvaluation = actionEvaluationFactory.create(DEFAULT_PATIENT_DTO,
            DEFAULT_USER, unit, careUnitBuilder.build(), careProviderBuilder.build());

        assertEquals(actionEvaluation.getSubUnit().getCity(), unit.getCity());
      }

      @Test
      void shallIncludeSubunitPhoneNumber() {
        final var unit = subUnitBuilder.phoneNumber(PHONE_NUMBER).build();

        final var actionEvaluation = actionEvaluationFactory.create(DEFAULT_PATIENT_DTO,
            DEFAULT_USER, unit, careUnitBuilder.build(), careProviderBuilder.build());

        assertEquals(actionEvaluation.getSubUnit().getPhoneNumber(), unit.getPhoneNumber());
      }

      @Test
      void shallIncludeSubunitEmail() {
        final var unit = subUnitBuilder.email(EMAIL).build();

        final var actionEvaluation = actionEvaluationFactory.create(DEFAULT_PATIENT_DTO,
            DEFAULT_USER, unit, careUnitBuilder.build(), careProviderBuilder.build());

        assertEquals(actionEvaluation.getSubUnit().getEmail(), unit.getEmail());
      }

      @Test
      void shallIncludeSubunitInactive() {
        final var unit = subUnitBuilder.inactive(true).build();

        final var actionEvaluation = actionEvaluationFactory.create(DEFAULT_PATIENT_DTO,
            DEFAULT_USER, unit, careUnitBuilder.build(), careProviderBuilder.build());

        assertEquals(actionEvaluation.getSubUnit().getInactive(), unit.getInactive());
      }
    }

    @Nested
    class IncludeCareProvider {

      @Test
      void shallIncludeCareProviderId() {
        final var unit = careProviderBuilder.id(HSA_ID).build();
        final var actionEvaluation = actionEvaluationFactory.create(DEFAULT_PATIENT_DTO,
            DEFAULT_USER, subUnitBuilder.build(), careUnitBuilder.build(), unit);

        assertEquals(actionEvaluation.getCareProvider().getHsaId().id(), unit.getId());
      }

      @Test
      void shallIncludeCareProviderName() {
        final var unit = careProviderBuilder.name(UNIT_NAME).build();

        final var actionEvaluation = actionEvaluationFactory.create(DEFAULT_PATIENT_DTO,
            DEFAULT_USER, subUnitBuilder.build(), careUnitBuilder.build(), unit);

        assertEquals(actionEvaluation.getCareProvider().getName(), unit.getName());
      }

      @Test
      void shallIncludeCareProviderAddress() {
        final var unit = careProviderBuilder.address(UNIT_ADDRESS).build();

        final var actionEvaluation = actionEvaluationFactory.create(DEFAULT_PATIENT_DTO,
            DEFAULT_USER, subUnitBuilder.build(), careUnitBuilder.build(), unit);

        assertEquals(actionEvaluation.getCareProvider().getAddress(), unit.getAddress());
      }

      @Test
      void shallIncludeCareProviderZipCode() {
        final var unit = careProviderBuilder.zipCode(ZIP_CODE).build();

        final var actionEvaluation = actionEvaluationFactory.create(DEFAULT_PATIENT_DTO,
            DEFAULT_USER, subUnitBuilder.build(), careUnitBuilder.build(), unit);

        assertEquals(actionEvaluation.getCareProvider().getZipCode(), unit.getZipCode());
      }

      @Test
      void shallIncludeCareProviderCity() {
        final var unit = careProviderBuilder.city(UNIT_CITY).build();

        final var actionEvaluation = actionEvaluationFactory.create(DEFAULT_PATIENT_DTO,
            DEFAULT_USER, subUnitBuilder.build(), careUnitBuilder.build(), unit);

        assertEquals(actionEvaluation.getCareProvider().getCity(), unit.getCity());
      }

      @Test
      void shallIncludeCareProviderPhoneNumber() {
        final var unit = careProviderBuilder.phoneNumber(PHONE_NUMBER).build();

        final var actionEvaluation = actionEvaluationFactory.create(DEFAULT_PATIENT_DTO,
            DEFAULT_USER, subUnitBuilder.build(), careUnitBuilder.build(), unit);

        assertEquals(actionEvaluation.getCareProvider().getPhoneNumber(), unit.getPhoneNumber());
      }

      @Test
      void shallIncludeCareProviderEmail() {
        final var unit = careProviderBuilder.email(EMAIL).build();

        final var actionEvaluation = actionEvaluationFactory.create(DEFAULT_PATIENT_DTO,
            DEFAULT_USER, subUnitBuilder.build(), careUnitBuilder.build(), unit);

        assertEquals(actionEvaluation.getCareProvider().getEmail(), unit.getEmail());
      }
    }

    @Nested
    class IncludePatient {

      @Test
      void shallIncludePatientDeceasedTrue() {
        final var patient = PatientDTO.builder()
            .deceased(true)
            .build();

        final var actionEvaluation = actionEvaluationFactory.create(patient, DEFAULT_USER,
            subUnitBuilder.build(), subUnitBuilder.build(), subUnitBuilder.build());

        assertTrue(actionEvaluation.getPatient().isDeceased(),
            "Expected patient.deceased to be true");
      }

      @Test
      void shallIncludePatientDeceasedFalse() {
        final var patient = PatientDTO.builder()
            .deceased(false)
            .build();

        final var actionEvaluation = actionEvaluationFactory.create(patient, DEFAULT_USER,
            subUnitBuilder.build(), subUnitBuilder.build(), subUnitBuilder.build());

        assertFalse(actionEvaluation.getPatient().isDeceased(),
            "Expected patient.deceased to be false");
      }
    }

    @Nested
    class IncludeUser {

      @Test
      void shallIncludeUserBlockedFalse() {
        final var user = UserDTO.builder()
            .blocked(false)
            .build();

        final var actionEvaluation = actionEvaluationFactory.create(DEFAULT_PATIENT_DTO, user,
            subUnitBuilder.build(), subUnitBuilder.build(), subUnitBuilder.build());

        assertFalse(actionEvaluation.getUser().isBlocked(),
            "Expected user.blocked to be false");
      }

      @Test
      void shallIncludeUserBlockedTrue() {
        final var user = UserDTO.builder()
            .blocked(true)
            .build();

        final var actionEvaluation = actionEvaluationFactory.create(DEFAULT_PATIENT_DTO, user,
            subUnitBuilder.build(), subUnitBuilder.build(), subUnitBuilder.build());

        assertTrue(actionEvaluation.getUser().isBlocked(),
            "Expected user.blocked to be true");
      }
    }
  }


  @Nested
  class CreateMinimal {

    @Test
    void shallIncludePatientDeceasedTrue() {
      final var patient = PatientDTO.builder()
          .deceased(true)
          .build();

      final var actionEvaluation = actionEvaluationFactory.create(patient, DEFAULT_USER);

      assertTrue(actionEvaluation.getPatient().isDeceased(),
          "Expected patient.deceased to be true");
    }

    @Test
    void shallIncludePatientDeceasedFalse() {
      final var patient = PatientDTO.builder()
          .deceased(false)
          .build();

      final var actionEvaluation = actionEvaluationFactory.create(patient, DEFAULT_USER);

      assertFalse(actionEvaluation.getPatient().isDeceased(),
          "Expected patient.deceased to be false");
    }

    @Test
    void shallIncludeUserBlockedFalse() {
      final var user = UserDTO.builder()
          .blocked(false)
          .build();

      final var actionEvaluation = actionEvaluationFactory.create(DEFAULT_PATIENT_DTO, user);

      assertFalse(actionEvaluation.getUser().isBlocked(),
          "Expected user.blocked to be false");
    }

    @Test
    void shallIncludeUserBlockedTrue() {
      final var user = UserDTO.builder()
          .blocked(true)
          .build();

      final var actionEvaluation = actionEvaluationFactory.create(DEFAULT_PATIENT_DTO, user);

      assertTrue(actionEvaluation.getUser().isBlocked(),
          "Expected user.blocked to be true");
    }

  }
}
