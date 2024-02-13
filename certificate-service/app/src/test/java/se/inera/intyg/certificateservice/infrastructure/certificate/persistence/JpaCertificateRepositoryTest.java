package se.inera.intyg.certificateservice.infrastructure.certificate.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateModelRepository;
import se.inera.intyg.certificateservice.domain.patient.model.PersonIdType;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateModelEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.PatientEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.PatientIdTypeEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.StaffEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.UnitEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.UnitType;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.UnitTypeEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.CertificateEntityMapper;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.CertificateModelEntityMapper;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.CertificateEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.CertificateModelEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.PatientEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.StaffEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.UnitEntityRepository;

@ExtendWith(MockitoExtension.class)
class JpaCertificateRepositoryTest {

  @Mock
  CertificateEntityRepository certificateEntityRepository;
  @Mock
  CertificateModelEntityRepository certificateModelEntityRepository;
  @Mock
  StaffEntityRepository staffEntityRepository;
  @Mock
  UnitEntityRepository unitEntityRepository;
  @Mock
  PatientEntityRepository patientEntityRepository;
  @Mock
  CertificateModelRepository certificateModelRepository;

  @InjectMocks
  JpaCertificateRepository jpaCertificateRepository;

  private static final CertificateModelEntity MODEL = CertificateModelEntity.builder()
      .name("NAME")
      .type("TYPE")
      .version("VERSION")
      .build();

  private static final CertificateModelEntity MODEL_FROM_DB = CertificateModelEntity.builder()
      .name("NAME_1")
      .type("TYPE_1")
      .version("VERSION_1")
      .build();

  private static final CertificateModel CONVERTED_MODEL = CertificateModelEntityMapper.toDomain(
      MODEL);
  private static final CertificateModel SAVED_MODEL = CertificateModelEntityMapper.toDomain(
      MODEL_FROM_DB);

  private static final UnitEntity CARE_PROVIDER = UnitEntity.builder()
      .type(
          UnitTypeEntity.builder()
              .type(UnitType.CARE_PROVIDER.name())
              .key(UnitType.CARE_PROVIDER.getKey())
              .build()
      )
      .hsaId("HSA_ID_PROVIDER")
      .name("NAME_PROVIDER")
      .build();

  private static final UnitEntity CARE_UNIT = UnitEntity.builder()
      .type(
          UnitTypeEntity.builder()
              .type(UnitType.CARE_UNIT.name())
              .key(UnitType.CARE_UNIT.getKey())
              .build()
      )
      .hsaId("HSA_ID_UNIT")
      .name("NAME_UNIT")
      .build();

  private static final UnitEntity ISSUED_ON_UNIT = UnitEntity.builder()
      .type(
          UnitTypeEntity.builder()
              .type(UnitType.SUB_UNIT.name())
              .key(UnitType.SUB_UNIT.getKey())
              .build()
      )
      .hsaId("HSA_ID_ISSUED")
      .name("NAME_ISSUED")
      .build();

  private static final PatientEntity PATIENT = PatientEntity.builder()
      .id("ID")
      .protectedPerson(false)
      .testIndicated(false)
      .deceased(false)
      .type(PatientIdTypeEntity.builder()
          .type(PersonIdType.PERSONAL_IDENTITY_NUMBER.name())
          .key(1)
          .build())
      .firstName("FIRST")
      .middleName("MIDDLE")
      .lastName("LAST")
      .build();

  private static final StaffEntity ISSUED_BY = StaffEntity.builder()
      .name("NAME")
      .hsaId("HSA_ID")
      .build();

  private static final CertificateEntity CERTIFICATE_ENTITY = CertificateEntity.builder()
      .version(1L)
      .modified(LocalDateTime.now())
      .certificateId("ID")
      .created(LocalDateTime.now())
      .careProvider(CARE_PROVIDER)
      .careUnit(CARE_UNIT)
      .issuedOnUnit(ISSUED_ON_UNIT)
      .issuedBy(ISSUED_BY)
      .patient(PATIENT)
      .certificateModel(MODEL)
      .build();

  private static final CertificateEntity SAVED_ENTITY = CertificateEntity.builder()
      .key(2L)
      .version(1L)
      .modified(LocalDateTime.now())
      .certificateId("ID")
      .created(LocalDateTime.now())
      .careProvider(CARE_PROVIDER)
      .careUnit(CARE_UNIT)
      .issuedOnUnit(ISSUED_ON_UNIT)
      .issuedBy(ISSUED_BY)
      .patient(PATIENT)
      .certificateModel(MODEL)
      .build();

  @Nested
  class Create {

    @Test
    void shouldThrowExceptionIfCertificateModelIsNull() {
      assertThrows(IllegalArgumentException.class, () -> jpaCertificateRepository.create(null));
    }

    @Test
    void shouldCreateCertificateWithModel() {
      final var response = jpaCertificateRepository.create(CONVERTED_MODEL);

      assertEquals(CONVERTED_MODEL, response.certificateModel());
    }

    @Test
    void shouldCreateCertificateWithId() {
      final var response = jpaCertificateRepository.create(CONVERTED_MODEL);

      assertNotNull(response.id().id());
    }

    @Test
    void shouldCreateCertificateWithCreated() {
      final var response = jpaCertificateRepository.create(CONVERTED_MODEL);

      assertEquals(
          LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS),
          response.created().truncatedTo(ChronoUnit.SECONDS)
      );
    }
  }

  @Nested
  class Save {

    @Test
    void shouldThrowExceptionIfCertificateIsNull() {
      assertThrows(IllegalArgumentException.class, () -> jpaCertificateRepository.save(null));
    }

    @Test
    void shouldReturnCertificate() {
      final var certificate = CertificateEntityMapper.toDomain(CERTIFICATE_ENTITY, SAVED_MODEL);

      final var response = jpaCertificateRepository.save(certificate);

      assertEquals(certificate, response);
    }

  }

  @Nested
  class GetById {

    @Test
    void shouldThrowExceptionIfIdIsNull() {
      assertThrows(IllegalArgumentException.class, () -> jpaCertificateRepository.getById(null));
    }

    @Test
    void shouldThrowExceptionIfCertificateIsNull() {
      final var id = new CertificateId("ID");
      assertThrows(IllegalArgumentException.class,
          () -> jpaCertificateRepository.getById(id)
      );
    }

    @Test
    void shouldReturnCertificateFromRepository() {
      when(certificateEntityRepository.findByCertificateId("ID"))
          .thenReturn(CERTIFICATE_ENTITY);
      when(certificateModelRepository.getById(CONVERTED_MODEL.id()))
          .thenReturn(SAVED_MODEL);

      final var convertedCertificate = CertificateEntityMapper.toDomain(CERTIFICATE_ENTITY,
          SAVED_MODEL);
      final var response = jpaCertificateRepository.getById(new CertificateId("ID"));

      assertEquals(convertedCertificate, response);
    }
  }

  @Nested
  class Exists {

    @Test
    void shouldThrowExceptionIfNoCertificateId() {
      assertThrows(IllegalArgumentException.class, () -> jpaCertificateRepository.exists(null));
    }

    @Test
    void shouldReturnFalseIfCertificateDoesNotExist() {
      assertFalse(jpaCertificateRepository.exists(new CertificateId("ID")));
    }

    @Test
    void shouldReturnTrueIfCertificateExists() {
      when(certificateEntityRepository.findByCertificateId("ID"))
          .thenReturn(CertificateEntity.builder().build());

      assertTrue(jpaCertificateRepository.exists(new CertificateId("ID")));
    }

  }

  @Nested
  class Insert {

  }

  @Nested
  class Remove {

    @Test
    void shouldRemoveCertificates() {
      final var ids = List.of("ID1", "ID2");

      jpaCertificateRepository.remove(List.of(new CertificateId("ID1"), new CertificateId("ID2")));

      verify(certificateEntityRepository, times(1)).deleteAllByCertificateIdIn(ids);
    }
  }

}