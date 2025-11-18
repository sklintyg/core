package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.application.testdata.TestDataPatientEntity.ATHENA_REACT_ANDERSSON_ENTITY;
import static se.inera.intyg.certificateservice.application.testdata.TestDataPatientVersionEntity.ATHENA_REACT_ANDERSSON_VERSION_ENTITY;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_FIRST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_ID_WITHOUT_DASH;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_LAST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_MIDDLE_NAME;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.PatientEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.PatientIdTypeEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.PatientVersionEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.PersonEntityIdType;

class PatientVersionEntityMapperTest {

  private static final PatientEntity PATIENT_ENTITY_PROTECTED = getPatientEntity(false, false,
      true);
  private static final PatientEntity PATIENT_ENTITY_DECEASED = getPatientEntity(false, true, false);
  private static final PatientEntity PATIENT_ENTITY_TEST = getPatientEntity(true, false, false);

  private static final PatientVersionEntity PATIENT_VERSION_ENTITY_PROTECTED = getPatientVersionEntity(
      false, false, true);
  private static final PatientVersionEntity PATIENT_VERSION_ENTITY_DECEASED = getPatientVersionEntity(
      false, true, false);
  private static final PatientVersionEntity PATIENT_VERSION_ENTITY_TEST = getPatientVersionEntity(
      true, false, false);


  @Test
  void shouldReturnPatientEntity() {
    final var response = PatientVersionEntityMapper.toPatient(
        ATHENA_REACT_ANDERSSON_VERSION_ENTITY);
    assertEquals(ATHENA_REACT_ANDERSSON_ENTITY, response);
  }

  @Test
  void shouldReturnPatientVersionEntity() {
    final var response =
        PatientVersionEntityMapper.toPatientVersion(ATHENA_REACT_ANDERSSON_ENTITY);

    assertAll(
        () -> assertEquals(ATHENA_REACT_ANDERSSON_ID_WITHOUT_DASH, response.getId()),
        () -> assertEquals(ATHENA_REACT_ANDERSSON_FIRST_NAME, response.getFirstName()),
        () -> assertEquals(ATHENA_REACT_ANDERSSON_MIDDLE_NAME, response.getMiddleName()),
        () -> assertEquals(ATHENA_REACT_ANDERSSON_LAST_NAME, response.getLastName()),
        () -> assertFalse(response.isProtectedPerson()),
        () -> assertFalse(response.isDeceased()),
        () -> assertFalse(response.isTestIndicated()),
        () -> assertEquals(PersonEntityIdType.PERSONAL_IDENTITY_NUMBER.name(),
            response.getType().getType()),
        () -> assertNotNull(response.getPatient())
    );
  }


  @Nested
  class ToPatientVersion {

    @Test
    void shouldMapId() {
      final var response = PatientVersionEntityMapper.toPatientVersion(PATIENT_ENTITY_PROTECTED);
      assertEquals(PATIENT_ENTITY_PROTECTED.getId(), response.getId());
    }

    @Test
    void shouldMapType() {
      final var response = PatientVersionEntityMapper.toPatientVersion(PATIENT_ENTITY_PROTECTED);
      assertEquals(PATIENT_ENTITY_PROTECTED.getType(), response.getType());
    }

    @Test
    void shouldMapFirstName() {
      final var response = PatientVersionEntityMapper.toPatientVersion(PATIENT_ENTITY_PROTECTED);
      assertEquals(PATIENT_ENTITY_PROTECTED.getFirstName(), response.getFirstName());
    }

    @Test
    void shouldMapMiddleName() {
      final var response = PatientVersionEntityMapper.toPatientVersion(PATIENT_ENTITY_PROTECTED);
      assertEquals(PATIENT_ENTITY_PROTECTED.getMiddleName(), response.getMiddleName());
    }

    @Test
    void shouldMapLastName() {
      final var response = PatientVersionEntityMapper.toPatientVersion(PATIENT_ENTITY_PROTECTED);
      assertEquals(PATIENT_ENTITY_PROTECTED.getLastName(), response.getLastName());
    }

    @Test
    void shouldMapProtectedPerson() {
      final var response = PatientVersionEntityMapper.toPatientVersion(PATIENT_ENTITY_PROTECTED);
      assertEquals(PATIENT_ENTITY_PROTECTED.isProtectedPerson(), response.isProtectedPerson());
    }

    @Test
    void shouldMapDeceased() {
      final var response = PatientVersionEntityMapper.toPatientVersion(PATIENT_ENTITY_DECEASED);
      assertEquals(PATIENT_ENTITY_DECEASED.isDeceased(), response.isDeceased());
    }

    @Test
    void shouldMapTestIndicated() {
      final var response = PatientVersionEntityMapper.toPatientVersion(PATIENT_ENTITY_TEST);
      assertEquals(PATIENT_ENTITY_TEST.isTestIndicated(), response.isTestIndicated());
    }

    @Test
    void shouldMapPatient() {
      final var response = PatientVersionEntityMapper.toPatientVersion(PATIENT_ENTITY_PROTECTED);
      assertEquals(PATIENT_ENTITY_PROTECTED, response.getPatient());
    }

    @Test
    void shouldSetValidFromToNull() {
      final var response = PatientVersionEntityMapper.toPatientVersion(PATIENT_ENTITY_PROTECTED);
      assertNull(response.getValidFrom());
    }

    @Test
    void shouldSetValidToToCurrentTime() {
      final var before = LocalDateTime.now();
      final var response = PatientVersionEntityMapper.toPatientVersion(PATIENT_ENTITY_PROTECTED);
      final var after = LocalDateTime.now();

      assertNotNull(response.getValidTo());
      assertTrue(response.getValidTo().isAfter(before.minusSeconds(1)));
      assertTrue(response.getValidTo().isBefore(after.plusSeconds(1)));
    }
  }

  @Nested
  class ToPatient {

    @Test
    void shouldMapId() {
      final var response = PatientVersionEntityMapper.toPatient(PATIENT_VERSION_ENTITY_DECEASED);
      assertEquals(PATIENT_VERSION_ENTITY_DECEASED.getId(), response.getId());
    }

    @Test
    void shouldMapType() {
      final var response = PatientVersionEntityMapper.toPatient(PATIENT_VERSION_ENTITY_DECEASED);
      assertEquals(PATIENT_VERSION_ENTITY_DECEASED.getType(), response.getType());
    }

    @Test
    void shouldMapFirstName() {
      final var response = PatientVersionEntityMapper.toPatient(PATIENT_VERSION_ENTITY_DECEASED);
      assertEquals(PATIENT_VERSION_ENTITY_DECEASED.getFirstName(), response.getFirstName());
    }

    @Test
    void shouldMapMiddleName() {
      final var response = PatientVersionEntityMapper.toPatient(PATIENT_VERSION_ENTITY_DECEASED);
      assertEquals(PATIENT_VERSION_ENTITY_DECEASED.getMiddleName(), response.getMiddleName());
    }

    @Test
    void shouldMapLastName() {
      final var response = PatientVersionEntityMapper.toPatient(PATIENT_VERSION_ENTITY_DECEASED);
      assertEquals(PATIENT_VERSION_ENTITY_DECEASED.getLastName(), response.getLastName());
    }

    @Test
    void shouldMapProtectedPerson() {
      final var response = PatientVersionEntityMapper.toPatient(PATIENT_VERSION_ENTITY_PROTECTED);
      assertEquals(PATIENT_VERSION_ENTITY_PROTECTED.isProtectedPerson(),
          response.isProtectedPerson());
    }

    @Test
    void shouldMapDeceased() {
      final var response = PatientVersionEntityMapper.toPatient(PATIENT_VERSION_ENTITY_DECEASED);
      assertEquals(PATIENT_VERSION_ENTITY_DECEASED.isDeceased(), response.isDeceased());
    }

    @Test
    void shouldMapTestIndicated() {
      final var response = PatientVersionEntityMapper.toPatient(PATIENT_VERSION_ENTITY_TEST);
      assertEquals(PATIENT_VERSION_ENTITY_TEST.isTestIndicated(), response.isTestIndicated());
    }
  }

  private static PatientEntity getPatientEntity(boolean testIndicated, boolean isDeceased,
      boolean isProtectedPerson) {
    return PatientEntity.builder()
        .id(ATHENA_REACT_ANDERSSON_ID_WITHOUT_DASH)
        .protectedPerson(isProtectedPerson)
        .testIndicated(testIndicated)
        .deceased(isDeceased)
        .type(PatientIdTypeEntity.builder()
            .type(PersonEntityIdType.PERSONAL_IDENTITY_NUMBER.name())
            .key(PersonEntityIdType.PERSONAL_IDENTITY_NUMBER.getKey())
            .build())
        .firstName(ATHENA_REACT_ANDERSSON_FIRST_NAME)
        .middleName(ATHENA_REACT_ANDERSSON_MIDDLE_NAME)
        .lastName(ATHENA_REACT_ANDERSSON_LAST_NAME)
        .build();
  }

  private static PatientVersionEntity getPatientVersionEntity(boolean testIndicated,
      boolean isDeceased,
      boolean isProtectedPerson) {
    return PatientVersionEntity.builder()
        .key(1)
        .id(ATHENA_REACT_ANDERSSON_ID_WITHOUT_DASH)
        .protectedPerson(isProtectedPerson)
        .testIndicated(testIndicated)
        .deceased(isDeceased)
        .type(PatientIdTypeEntity.builder()
            .type(PersonEntityIdType.PERSONAL_IDENTITY_NUMBER.name())
            .key(PersonEntityIdType.PERSONAL_IDENTITY_NUMBER.getKey())
            .build())
        .firstName(ATHENA_REACT_ANDERSSON_FIRST_NAME)
        .middleName(ATHENA_REACT_ANDERSSON_MIDDLE_NAME)
        .lastName(ATHENA_REACT_ANDERSSON_LAST_NAME)
        .validFrom(LocalDateTime.of(2024, 1, 1, 0, 0))
        .validTo(LocalDateTime.of(2024, 12, 31, 23, 59))
        .patient(getPatientEntity(testIndicated, isDeceased, isProtectedPerson))
        .build();
  }
}