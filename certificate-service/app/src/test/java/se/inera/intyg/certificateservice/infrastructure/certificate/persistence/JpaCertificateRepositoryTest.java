package se.inera.intyg.certificateservice.infrastructure.certificate.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.CERTIFICATE_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.FK3226_CERTIFICATE;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.util.ReflectionTestUtils;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateExportPage;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.common.model.PersonIdType;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateDataEntity;
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
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.CertificateEntitySpecificationFactory;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.CertificateRelationRepository;

@ExtendWith(MockitoExtension.class)
class JpaCertificateRepositoryTest {

  @Mock
  CertificateEntitySpecificationFactory certificateEntitySpecificationFactory;
  @Mock
  CertificateRelationRepository certificateRelationRepository;
  @Mock
  CertificateEntityRepository certificateEntityRepository;
  @Mock
  UnitRepository unitRepository;
  @Mock
  PatientRepository patientRepository;
  @Mock
  CertificateEntityMapper certificateEntityMapper;
  @InjectMocks
  JpaCertificateRepository jpaCertificateRepository;

  private static final CertificateModelEntity MODEL = CertificateModelEntity.builder()
      .name("NAME")
      .type("TYPE")
      .version("VERSION")
      .build();

  private static final CertificateModel CONVERTED_MODEL = CertificateModelEntityMapper.toDomain(
      MODEL);
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
      .firstName("NAME")
      .middleName("NAME")
      .lastName("NAME")
      .hsaId("HSA_ID")
      .build();

  private static final LocalDate NOW = LocalDate.now();
  private static final String JSON =
      "[{\"id\":\"F10\",\"value\":{\"type\":\"DATE\",\"date\":[" + NOW.getYear() + ","
          + NOW.getMonthValue() + "," + NOW.getDayOfMonth() + "]}}]";

  private static final CertificateDataEntity DATA = new CertificateDataEntity(JSON);

  private static final CertificateEntity CERTIFICATE_ENTITY = CertificateEntity.builder()
      .revision(1L)
      .modified(LocalDateTime.now())
      .certificateId("ID")
      .created(LocalDateTime.now())
      .careProvider(CARE_PROVIDER)
      .careUnit(CARE_UNIT)
      .issuedOnUnit(ISSUED_ON_UNIT)
      .issuedBy(ISSUED_BY)
      .patient(PATIENT)
      .certificateModel(MODEL)
      .data(DATA)
      .build();

  private static final Certificate CERTIFICATE = Certificate.builder().build();

  @Nested
  class Create {

    @Test
    void shouldThrowExceptionIfCertificateModelIsNull() {
      assertThrows(IllegalArgumentException.class,
          () -> jpaCertificateRepository.create(null));
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

    @Test
    void shouldCreateCertificateWithRevision() {
      final var response = jpaCertificateRepository.create(CONVERTED_MODEL);

      assertEquals(0, response.revision().value());
    }
  }

  @Nested
  class Save {

    @Test
    void shouldThrowExceptionIfCertificateIsNull() {
      assertThrows(IllegalArgumentException.class,
          () -> jpaCertificateRepository.save(null)
      );
    }

    @Test
    void shouldReturnCertificate() {
      final var certificate = Certificate.builder().build();

      doReturn(CERTIFICATE_ENTITY).when(certificateEntityMapper).toEntity(certificate);
      doReturn(CERTIFICATE_ENTITY).when(certificateEntityRepository).save(CERTIFICATE_ENTITY);
      doReturn(certificate).when(certificateEntityMapper).toDomain(CERTIFICATE_ENTITY);

      final var response = jpaCertificateRepository.save(certificate);

      assertEquals(certificate, response);
    }

    @Test
    void shouldDeleteCertificate() {
      final var expectedResult = Certificate.builder()
          .id(CERTIFICATE_ID)
          .status(Status.DELETED_DRAFT)
          .build();

      doReturn(Optional.of(CERTIFICATE_ENTITY)).when(certificateEntityRepository)
          .findByCertificateId(CERTIFICATE_ID.id());
      final var actualResult = jpaCertificateRepository.save(expectedResult);

      verify(certificateEntityRepository).delete(CERTIFICATE_ENTITY);
      verify(certificateRelationRepository).deleteRelations(CERTIFICATE_ENTITY);
      assertEquals(expectedResult, actualResult);
    }

    @Test
    void shouldDeleteRelationsIfStatusLockedDraft() {
      final var expectedResult = Certificate.builder()
          .id(CERTIFICATE_ID)
          .status(Status.LOCKED_DRAFT)
          .build();

      doReturn(Optional.of(CERTIFICATE_ENTITY)).when(certificateEntityRepository)
          .findByCertificateId(CERTIFICATE_ID.id());
      jpaCertificateRepository.save(expectedResult);

      verify(certificateRelationRepository).deleteRelations(CERTIFICATE_ENTITY);
    }

    @Test
    void shouldSaveCertificateRelations() {
      final var certificate = Certificate.builder().build();

      doReturn(CERTIFICATE_ENTITY).when(certificateEntityMapper).toEntity(certificate);
      doReturn(CERTIFICATE_ENTITY).when(certificateEntityRepository).save(CERTIFICATE_ENTITY);
      doReturn(certificate).when(certificateEntityMapper).toDomain(CERTIFICATE_ENTITY);

      jpaCertificateRepository.save(certificate);
      verify(certificateRelationRepository).save(certificate, CERTIFICATE_ENTITY);
    }
  }

  @Nested
  class GetById {

    @Test
    void shouldThrowExceptionIfIdIsNull() {
      assertThrows(IllegalArgumentException.class,
          () -> jpaCertificateRepository.getById(null));
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
      final var expectedCertificate = Certificate.builder().build();

      when(certificateEntityRepository.findByCertificateId("ID"))
          .thenReturn(Optional.of(CERTIFICATE_ENTITY));
      when(certificateEntityMapper.toDomain(CERTIFICATE_ENTITY))
          .thenReturn(expectedCertificate);

      final var response = jpaCertificateRepository.getById(new CertificateId("ID"));

      assertEquals(expectedCertificate, response);
    }
  }

  @Nested
  class GetByIds {

    @Test
    void shouldThrowExceptionIfIdsIsNull() {
      assertThrows(IllegalArgumentException.class,
          () -> jpaCertificateRepository.getByIds(null));
    }

    @Test
    void shouldThrowExceptionIfIdsIsEmpty() {
      final List<CertificateId> certificateIds = Collections.emptyList();
      assertThrows(IllegalArgumentException.class,
          () -> jpaCertificateRepository.getByIds(certificateIds)
      );
    }

    @Test
    void shouldReturnCertificatesFromRepository() {
      final var expectedCertificate = Certificate.builder().build();
      final var expectedCertificates = List.of(expectedCertificate, expectedCertificate);
      final var certificate1 = new CertificateId("ID1");
      final var certificate2 = new CertificateId("ID2");

      final var certificateIds = List.of(certificate1, certificate2);

      when(certificateEntityRepository.findCertificateEntitiesByCertificateIdIn(
          List.of("ID1", "ID2"))
      ).thenReturn(List.of(CERTIFICATE_ENTITY, CERTIFICATE_ENTITY));
      when(certificateEntityMapper.toDomain(CERTIFICATE_ENTITY))
          .thenReturn(expectedCertificate);

      final var response = jpaCertificateRepository.getByIds(certificateIds);

      assertEquals(expectedCertificates, response);
    }

    @Test
    void shouldThrowIfCertificateEntitiesSizeDontMatchCertificateIds() {
      final var certificate1 = new CertificateId("ID1");
      final var certificate2 = new CertificateId("ID2");

      final var certificateIds = List.of(certificate1, certificate2);

      when(certificateEntityRepository.findCertificateEntitiesByCertificateIdIn(
          List.of("ID1", "ID2"))
      ).thenReturn(List.of(CertificateEntity.builder().certificateId("ID").build()));

      assertThrows(IllegalStateException.class,
          () -> jpaCertificateRepository.getByIds(certificateIds));
    }
  }


  @Nested
  class Exists {

    @Test
    void shouldThrowExceptionIfNoCertificateId() {
      assertThrows(IllegalArgumentException.class,
          () -> jpaCertificateRepository.exists(null));
    }

    @Test
    void shouldReturnFalseIfCertificateDoesNotExist() {
      when(certificateEntityRepository.findByCertificateId("ID"))
          .thenReturn(Optional.empty());
      assertFalse(jpaCertificateRepository.exists(new CertificateId("ID")));
    }

    @Test
    void shouldReturnTrueIfCertificateExists() {
      when(certificateEntityRepository.findByCertificateId("ID"))
          .thenReturn(Optional.of(CertificateEntity.builder().build()));

      assertTrue(jpaCertificateRepository.exists(new CertificateId("ID")));
    }
  }

  @Nested
  class TestabilityFeatures {

    @Test
    void shouldInsertCertificates() {
      doReturn(CERTIFICATE_ENTITY).when(certificateEntityMapper).toEntity(CERTIFICATE);
      doReturn(CERTIFICATE_ENTITY).when(certificateEntityRepository).save(CERTIFICATE_ENTITY);
      doReturn(CERTIFICATE).when(certificateEntityMapper).toDomain(CERTIFICATE_ENTITY);

      final var actualCertificate = jpaCertificateRepository.insert(CERTIFICATE);

      assertEquals(CERTIFICATE, actualCertificate);
    }

    @Test
    void shouldSaveCertificateRelations() {
      doReturn(CERTIFICATE_ENTITY).when(certificateEntityMapper).toEntity(CERTIFICATE);
      doReturn(CERTIFICATE_ENTITY).when(certificateEntityRepository).save(CERTIFICATE_ENTITY);
      doReturn(CERTIFICATE).when(certificateEntityMapper).toDomain(CERTIFICATE_ENTITY);

      jpaCertificateRepository.insert(CERTIFICATE);
      verify(certificateRelationRepository).save(CERTIFICATE, CERTIFICATE_ENTITY);
    }

    @Test
    void shouldRemoveCertificates() {
      final var ids = List.of("ID1", "ID2");

      jpaCertificateRepository.remove(
          List.of(new CertificateId("ID1"), new CertificateId("ID2"))
      );

      verify(certificateEntityRepository).deleteAllByCertificateIdIn(ids);
    }

    @Test
    void shouldRemoveCertificatesRelations() {
      doReturn(Optional.of(CERTIFICATE_ENTITY)).when(certificateEntityRepository)
          .findByCertificateId("ID1");

      jpaCertificateRepository.remove(
          List.of(new CertificateId("ID1"), new CertificateId("ID2"))
      );

      verify(certificateRelationRepository).deleteRelations(CERTIFICATE_ENTITY);
    }
  }

  @Nested
  class FindByIdsTests {

    @Test
    void shallReturnListOfCertificate() {
      final var expectedCertificates = List.of(CERTIFICATE);
      final var request = List.of(CERTIFICATE_ID);
      final var certificateIds = List.of(CERTIFICATE_ID.id());

      doReturn(List.of(CERTIFICATE_ENTITY)).when(certificateEntityRepository)
          .findCertificateEntitiesByCertificateIdIn(certificateIds);
      doReturn(CERTIFICATE).when(certificateEntityMapper).toDomain(CERTIFICATE_ENTITY);

      final var actualCertificates = jpaCertificateRepository.findByIds(request);
      assertEquals(expectedCertificates, actualCertificates);
    }

    @Test
    void shallReturnEmptyListIfNoCertificateIdsArePresent() {
      final var expectedCertificates = Collections.emptyList();
      final var request = List.of(CERTIFICATE_ID);
      final var certificateIds = List.of(CERTIFICATE_ID.id());

      doReturn(Collections.emptyList()).when(certificateEntityRepository)
          .findCertificateEntitiesByCertificateIdIn(certificateIds);

      final var actualCertificates = jpaCertificateRepository.findByIds(request);
      assertEquals(expectedCertificates, actualCertificates);
    }

    @Test
    void shallThrowIfCertificateIdsIsNull() {
      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> jpaCertificateRepository.findByIds(null));

      assertEquals("Cannot get certificate if certificateIds is null",
          illegalArgumentException.getMessage());
    }
  }

  @Nested
  class GetByCareProviderIdTests {

    @Test
    void shallThrowIfCareProviderIdIsNull() {
      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> jpaCertificateRepository.getExportByCareProviderId(null, 0, 0));

      assertEquals("Cannot get certificates if careProviderId is null", illegalArgumentException.getMessage());
    }

    @Test
    void shallReturnCertificateExportPage() {
      final var expectedResult = CertificateExportPage.builder()
          .totalRevoked(2L)
          .total(2L)
          .certificates(Collections.singletonList(FK3226_CERTIFICATE))
          .build();

      final var page = mock(Page.class);
      final var certificateEntity = CertificateEntity.builder().build();

      doReturn(page).when(certificateEntityRepository).findCertificateEntitiesByCareProviderHsaId(
          eq(CARE_PROVIDER.getHsaId()), any(Pageable.class)
      );
      doReturn(2L).when(certificateEntityRepository).findRevokedCertificateEntitiesByCareProviderHsaId(
          CARE_PROVIDER.getHsaId()
      );
      doReturn(Collections.singletonList(certificateEntity)).when(page).getContent();
      doReturn(2L).when(page).getTotalElements();
      doReturn(FK3226_CERTIFICATE).when(certificateEntityMapper).toDomain(certificateEntity);

      final var actualResult = jpaCertificateRepository.getExportByCareProviderId(CARE_PROVIDER.getHsaId(), 0, 10);
      assertEquals(expectedResult, actualResult);
    }
  }

  @Nested
  class DeleteByCareProviderId {

    @BeforeEach
    void setUp() {
      ReflectionTestUtils.setField(jpaCertificateRepository, "eraseCertificatesPageSize", 10);
    }

    @Test
    void shouldThrowExceptionIfCareProviderIdIsNull() {
      assertThrows(IllegalArgumentException.class,
          () -> jpaCertificateRepository.deleteByCareProviderId(null));
    }

    @Test
    void shouldDeleteCertificateByCareProviderId() {
      final var pageable = PageRequest.of(0, 10, Sort.by(Direction.ASC, "signed", "certificateId"));
      final var page = mock(Page.class);
      final var certificateEntity = CertificateEntity.builder()
          .certificateId("ID")
          .build();

      doReturn(page).when(certificateEntityRepository).findCertificateEntitiesByCareProviderHsaId(
          CARE_PROVIDER.getHsaId(), pageable
      );
      doReturn(List.of(certificateEntity)).when(page).getContent();
      doReturn(false).when(page).hasNext();
      doReturn(1L).when(page).getTotalElements();

      final var deletedCount = jpaCertificateRepository.deleteByCareProviderId(CARE_PROVIDER.getHsaId());

      verify(certificateRelationRepository).deleteRelations(certificateEntity);
      verify(certificateEntityRepository).deleteAllByCertificateIdIn(List.of(certificateEntity.getCertificateId()));
      assertEquals(1L, deletedCount);
    }
  }
}