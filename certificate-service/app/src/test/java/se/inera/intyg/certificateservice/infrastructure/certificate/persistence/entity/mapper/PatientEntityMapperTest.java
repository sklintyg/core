package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_FIRST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_ID_WITHOUT_DASH;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_LAST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_MIDDLE_NAME;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.common.model.PersonId;
import se.inera.intyg.certificateservice.domain.common.model.PersonIdType;
import se.inera.intyg.certificateservice.domain.patient.model.Deceased;
import se.inera.intyg.certificateservice.domain.patient.model.Name;
import se.inera.intyg.certificateservice.domain.patient.model.Patient;
import se.inera.intyg.certificateservice.domain.patient.model.ProtectedPerson;
import se.inera.intyg.certificateservice.domain.patient.model.TestIndicated;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.PatientEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.PatientIdTypeEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.PersonEntityIdType;

class PatientEntityMapperTest {

  private static final Patient PATIENT_PROTECTED = getPatient(false, false, true);
  private static final Patient PATIENT_DECEASED = getPatient(false, true, false);
  private static final Patient PATIENT_TEST = getPatient(true, false, false);

  private static final PatientEntity PATIENT_ENTITY_DECEASED = getPatientEntity(false, true, false);
  private static final PatientEntity PATIENT_ENTITY_PROTECTED = getPatientEntity(false, false,
      true);
  private static final PatientEntity PATIENT_ENTITY_TEST = getPatientEntity(true, false, false);

  @Nested
  class ToEntity {

    @Test
    void shouldMapId() {
      final var response = PatientEntityMapper.toEntity(PATIENT_PROTECTED);
      assertEquals(PATIENT_PROTECTED.id().id(), response.getId());
    }

    @Test
    void shouldMapType() {
      final var response = PatientEntityMapper.toEntity(PATIENT_PROTECTED);
      assertEquals(PATIENT_PROTECTED.id().type().name(), response.getType().getType());
    }

    @Test
    void shouldMapProtectedPerson() {
      final var response = PatientEntityMapper.toEntity(PATIENT_PROTECTED);
      assertEquals(PATIENT_PROTECTED.protectedPerson().value(), response.isProtectedPerson());
    }

    @Test
    void shouldMapDeceased() {
      final var response = PatientEntityMapper.toEntity(PATIENT_DECEASED);
      assertEquals(PATIENT_DECEASED.deceased().value(), response.isDeceased());
    }

    @Test
    void shouldMapTestIndicated() {
      final var response = PatientEntityMapper.toEntity(PATIENT_TEST);
      assertEquals(PATIENT_TEST.testIndicated().value(), response.isTestIndicated());
    }

    @Test
    void shouldMapFirstName() {
      final var response = PatientEntityMapper.toEntity(PATIENT_PROTECTED);
      assertEquals(PATIENT_PROTECTED.name().firstName(), response.getFirstName());
    }

    @Test
    void shouldMapMiddleName() {
      final var response = PatientEntityMapper.toEntity(PATIENT_PROTECTED);
      assertEquals(PATIENT_PROTECTED.name().middleName(), response.getMiddleName());
    }

    @Test
    void shouldMapLastName() {
      final var response = PatientEntityMapper.toEntity(PATIENT_PROTECTED);
      assertEquals(PATIENT_PROTECTED.name().lastName(), response.getLastName());
    }
  }

  @Nested
  class ToDomain {

    @Test
    void shouldMapId() {
      final var response = PatientEntityMapper.toDomain(PATIENT_ENTITY_DECEASED);
      assertEquals(PATIENT_ENTITY_DECEASED.getId(), response.id().id());
    }

    @Test
    void shouldMapType() {
      final var response = PatientEntityMapper.toDomain(PATIENT_ENTITY_DECEASED);
      assertEquals(PATIENT_ENTITY_DECEASED.getType().getType(), response.id().type().name());
    }

    @Test
    void shouldMapFirstName() {
      final var response = PatientEntityMapper.toDomain(PATIENT_ENTITY_DECEASED);
      assertEquals(PATIENT_ENTITY_DECEASED.getFirstName(), response.name().firstName());
    }

    @Test
    void shouldMapMiddleName() {
      final var response = PatientEntityMapper.toDomain(PATIENT_ENTITY_DECEASED);
      assertEquals(PATIENT_ENTITY_DECEASED.getMiddleName(), response.name().middleName());
    }

    @Test
    void shouldMapLastName() {
      final var response = PatientEntityMapper.toDomain(PATIENT_ENTITY_DECEASED);
      assertEquals(PATIENT_ENTITY_DECEASED.getLastName(), response.name().lastName());
    }

    @Test
    void shouldMapProtectedPerson() {
      final var response = PatientEntityMapper.toDomain(PATIENT_ENTITY_PROTECTED);
      assertEquals(PATIENT_ENTITY_PROTECTED.isProtectedPerson(),
          response.protectedPerson().value());
    }

    @Test
    void shouldMapTestIndicated() {
      final var response = PatientEntityMapper.toDomain(PATIENT_ENTITY_PROTECTED);
      assertEquals(PATIENT_ENTITY_PROTECTED.isTestIndicated(), response.testIndicated().value());
    }

    @Test
    void shouldMapDeceased() {
      final var response = PatientEntityMapper.toDomain(PATIENT_ENTITY_TEST);
      assertEquals(PATIENT_ENTITY_PROTECTED.isDeceased(), response.deceased().value());
    }
  }

  private static Patient getPatient(boolean testIndicated, boolean deceased,
      boolean protectedPerson) {
    return Patient.builder()
        .id(PersonId.builder()
            .id(ATHENA_REACT_ANDERSSON_ID_WITHOUT_DASH)
            .type(PersonIdType.PERSONAL_IDENTITY_NUMBER)
            .build())
        .name(
            Name.builder()
                .firstName(ATHENA_REACT_ANDERSSON_FIRST_NAME)
                .lastName(ATHENA_REACT_ANDERSSON_MIDDLE_NAME)
                .middleName(ATHENA_REACT_ANDERSSON_LAST_NAME)
                .build()
        )
        .protectedPerson(new ProtectedPerson(protectedPerson))
        .deceased(new Deceased(deceased))
        .testIndicated(new TestIndicated(testIndicated))
        .build();
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
}