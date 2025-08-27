package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static se.inera.intyg.certificateservice.application.testdata.TestDataUnitEntity.ALFA_ALLERGIMOTTAGNINGEN_ENTITY;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnit.ALFA_ALLERGIMOTTAGNINGEN;

import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateMetaData;
import se.inera.intyg.certificateservice.domain.certificate.model.PlaceholderCertificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.unit.model.SubUnit;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.UnitRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateModelEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateStatusEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.PatientEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.StaffEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.UnitEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.CertificateEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.CertificateModelEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.PatientEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.StaffEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.UnitEntityRepository;

@ExtendWith(MockitoExtension.class)
class PlaceholderCertificateEntityMapperTest {


  private static final String PLACEHOLDER = "PLACEHOLDER";
  private static final PatientEntity PATIENT_ENTITY = PatientEntity.builder().build();
  private static final UnitEntity UNIT_ENTITY = UnitEntity.builder().build();
  private static final StaffEntity STAFF_ENTITY = StaffEntity.builder().build();
  private static final CertificateModelEntity CERTIFICATE_MODEL_ENTITY = CertificateModelEntity.builder()
      .build();
  private static final CertificateStatusEntity SIGNED = CertificateStatusEntity.builder()
      .key(2)
      .status("SIGNED")
      .build();

  @Mock
  UnitRepository unitRepository;
  @Mock
  CertificateEntityRepository certificateEntityRepository;
  @Mock
  CertificateModelEntityRepository certificateModelEntityRepository;
  @Mock
  PatientEntityRepository patientEntityRepository;
  @Mock
  StaffEntityRepository staffEntityRepository;
  @Mock
  UnitEntityRepository unitEntityRepository;
  @InjectMocks
  PlaceholderCertificateEntityMapper certificateEntityMapper;

  private static final CertificateId CERTIFICATE_ID = new CertificateId("certificateId");
  private static final SubUnit ISSUING_UNIT = SubUnit.builder().build();
  private static final PlaceholderCertificate PLACEHOLDER_CERTIFICATE = PlaceholderCertificate.builder()
      .id(CERTIFICATE_ID)
      .status(Status.SIGNED)
      .certificateMetaData(
          CertificateMetaData.builder()
              .issuingUnit(ISSUING_UNIT)
              .build()
      )
      .build();

  @Nested
  class ToEntityTests {

    @BeforeEach
    void setUp() {
      when(certificateEntityRepository.findPlaceholderByCertificateId(CERTIFICATE_ID.id()))
          .thenReturn(Optional.empty());
      when(patientEntityRepository.findById(PLACEHOLDER)).thenReturn(Optional.of(PATIENT_ENTITY));
      when(unitEntityRepository.findByHsaId(PLACEHOLDER)).thenReturn(Optional.of(UNIT_ENTITY));
      when(staffEntityRepository.findByHsaId(PLACEHOLDER)).thenReturn(Optional.of(STAFF_ENTITY));
      when(
          certificateModelEntityRepository.findByTypeAndVersion(PLACEHOLDER,
              PLACEHOLDER)).thenReturn(
          Optional.of(CERTIFICATE_MODEL_ENTITY)
      );
      when(unitRepository.issuingUnit(ISSUING_UNIT)).thenReturn(UNIT_ENTITY);
    }

    @Test
    void shouldSetCertificateId() {
      final var entity = certificateEntityMapper.toEntity(PLACEHOLDER_CERTIFICATE);
      assertEquals(CERTIFICATE_ID.id(), entity.getCertificateId());
    }

    @Test
    void shouldSetStatus() {
      final var entity = certificateEntityMapper.toEntity(PLACEHOLDER_CERTIFICATE);
      assertEquals(SIGNED, entity.getStatus());
    }

    @Test
    void shouldSetPatient() {
      final var entity = certificateEntityMapper.toEntity(PLACEHOLDER_CERTIFICATE);
      assertEquals(PATIENT_ENTITY, entity.getPatient());
    }

    @Test
    void shouldSetCareProvider() {
      final var entity = certificateEntityMapper.toEntity(PLACEHOLDER_CERTIFICATE);
      assertEquals(UNIT_ENTITY, entity.getCareProvider());
    }


    @Test
    void shouldSetCareUnit() {
      final var entity = certificateEntityMapper.toEntity(PLACEHOLDER_CERTIFICATE);
      assertEquals(UNIT_ENTITY, entity.getCareUnit());
    }


    @Test
    void shouldSetIssuedOnUnit() {
      final var entity = certificateEntityMapper.toEntity(PLACEHOLDER_CERTIFICATE);
      assertEquals(UNIT_ENTITY, entity.getIssuedOnUnit());
    }

    @Test
    void shouldSetIssuedBy() {
      final var entity = certificateEntityMapper.toEntity(PLACEHOLDER_CERTIFICATE);
      assertEquals(STAFF_ENTITY, entity.getIssuedBy());
    }

    @Test
    void shouldSetCreatedBy() {
      final var entity = certificateEntityMapper.toEntity(PLACEHOLDER_CERTIFICATE);
      assertEquals(STAFF_ENTITY, entity.getCreatedBy());
    }

    @Test
    void shouldSetCertificateModel() {
      final var entity = certificateEntityMapper.toEntity(PLACEHOLDER_CERTIFICATE);
      assertEquals(CERTIFICATE_MODEL_ENTITY, entity.getCertificateModel());
    }

    @Test
    void shouldSetPlaceholder() {
      final var entity = certificateEntityMapper.toEntity(PLACEHOLDER_CERTIFICATE);
      assertTrue(entity.getPlaceholder());
    }
  }

  @Nested
  class ToDomainTests {

    private static final LocalDateTime CREATED = LocalDateTime.now();
    private static final CertificateEntity CERTIFICATE_ENTITY = CertificateEntity.builder()
        .certificateId(PLACEHOLDER)
        .status(SIGNED)
        .created(CREATED)
        .issuedOnUnit(ALFA_ALLERGIMOTTAGNINGEN_ENTITY)
        .build();

    @Test
    void shouldSetCertificateId() {
      final var domain = certificateEntityMapper.toDomain(CERTIFICATE_ENTITY);
      assertEquals(PLACEHOLDER, domain.id().id());
    }

    @Test
    void shouldSetStatus() {
      final var domain = certificateEntityMapper.toDomain(CERTIFICATE_ENTITY);
      assertEquals(Status.SIGNED, domain.status());
    }

    @Test
    void shouldSetCreated() {
      final var domain = certificateEntityMapper.toDomain(CERTIFICATE_ENTITY);
      assertEquals(CREATED, domain.created());
    }

    @Test
    void shouldSetIssuedOnUnit() {
      final var domain = certificateEntityMapper.toDomain(CERTIFICATE_ENTITY);
      assertEquals(ALFA_ALLERGIMOTTAGNINGEN, domain.certificateMetaData().issuingUnit());
    }
  }
}