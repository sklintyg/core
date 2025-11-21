package se.inera.intyg.certificateservice.infrastructure.certificate.persistence;

import static org.junit.jupiter.api.Assertions.assertAll;
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
import static se.inera.intyg.certificateservice.application.testdata.TestDataPatientVersionEntity.ATHENA_REACT_ANDERSSON_VERSION_ENTITY;
import static se.inera.intyg.certificateservice.application.testdata.TestDataPatientVersionEntity.athenaReactAnderssonVersionEntityBuilder;
import static se.inera.intyg.certificateservice.application.testdata.TestDataStaffVersionEntity.AJLA_DOKTOR_VERSION_ENTITY;
import static se.inera.intyg.certificateservice.application.testdata.TestDataStaffVersionEntity.ALF_DOKTOR_VERSION_ENTITY;
import static se.inera.intyg.certificateservice.application.testdata.TestDataUnitVersionEntity.ALFA_ALLERGIMOTTAGNINGEN_VERSION_ENTITY;
import static se.inera.intyg.certificateservice.application.testdata.TestDataUnitVersionEntity.ALFA_MEDICINCENTRUM_VERSION_ENTITY;
import static se.inera.intyg.certificateservice.application.testdata.TestDataUnitVersionEntity.ALFA_REGIONEN_VERSION_ENTITY;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProvider.ALFA_REGIONEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProviderConstants.ALFA_REGIONEN_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnit.ALFA_MEDICINCENTRUM;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.CERTIFICATE_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.CERTIFICATE_META_DATA;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.FK3226_CERTIFICATE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.athenaReactAnderssonBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataStaff.AJLA_DOKTOR;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataStaff.ALF_DOKTOR;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnit.ALFA_ALLERGIMOTTAGNINGEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_ALLERGIMOTTAGNINGEN_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_HSA_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALF_DOKTOR_HSA_ID;

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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.util.ReflectionTestUtils;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateExportPage;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateMetaData;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalCertificate;
import se.inera.intyg.certificateservice.domain.certificate.model.PlaceholderCertificate;
import se.inera.intyg.certificateservice.domain.certificate.model.RelationType;
import se.inera.intyg.certificateservice.domain.certificate.model.Revision;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PlaceholderCertificateRequest;
import se.inera.intyg.certificateservice.domain.common.model.CertificatesRequest;
import se.inera.intyg.certificateservice.domain.common.model.HsaId;
import se.inera.intyg.certificateservice.domain.common.model.PersonId;
import se.inera.intyg.certificateservice.domain.common.model.PersonIdType;
import se.inera.intyg.certificateservice.domain.patient.model.Deceased;
import se.inera.intyg.certificateservice.domain.patient.model.Name;
import se.inera.intyg.certificateservice.domain.patient.model.Patient;
import se.inera.intyg.certificateservice.domain.patient.model.ProtectedPerson;
import se.inera.intyg.certificateservice.domain.patient.model.TestIndicated;
import se.inera.intyg.certificateservice.domain.staff.model.Staff;
import se.inera.intyg.certificateservice.domain.unit.model.CareProvider;
import se.inera.intyg.certificateservice.domain.unit.model.CareUnit;
import se.inera.intyg.certificateservice.domain.unit.model.SubUnit;
import se.inera.intyg.certificateservice.domain.unit.model.UnitName;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateDataEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateModelEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateStatusEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.PatientEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.PatientIdTypeEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.StaffEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.UnitEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.UnitType;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.UnitTypeEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.CertificateEntityMapper;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.CertificateModelEntityMapper;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.PlaceholderCertificateEntityMapper;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.CertificateEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.CertificateEntitySpecificationFactory;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.CertificateRelationRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.PatientVersionEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.StaffVersionEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.UnitVersionEntityRepository;

@ExtendWith(MockitoExtension.class)
class JpaCertificateRepositoryTest {

  @Mock
  CertificateRepository certificateRepository;
  @Mock
  CertificateRelationRepository certificateRelationRepository;
  @Mock
  CertificateEntityRepository certificateEntityRepository;
  @Mock
  CertificateEntityMapper certificateEntityMapper;
  @Mock
  PlaceholderCertificateEntityMapper placeHolderEntityMapper;
  @Mock
  StaffVersionEntityRepository staffVersionEntityRepository;
  @Mock
  PatientVersionEntityRepository patientVersionEntityRepository;
  @Mock
  UnitVersionEntityRepository unitVersionEntityRepository;
  @Mock
  CertificateEntitySpecificationFactory certificateEntitySpecificationFactory;
  @InjectMocks
  JpaCertificateRepository jpaCertificateRepository;

  private void stubDomain() {
    when(certificateEntityMapper.toDomain(any(CertificateEntity.class),
        any(CertificateRepository.class)))
        .thenAnswer(inv -> {
          CertificateEntity ce = inv.getArgument(0);

          return MedicalCertificate.builder()
              .id(new CertificateId(ce.getCertificateId()))
              .created(ce.getCreated())
              .signed(ce.getSigned())
              .locked(ce.getLocked())
              .modified(ce.getModified())
              .revision(new Revision(
                  ce.getRevision() == null ? 0 : ce.getRevision()
              ))
              .certificateModel(CONVERTED_MODEL)
              .status(
                  ce.getStatus() != null
                      ? Status.valueOf(ce.getStatus().getStatus())
                      : Status.SIGNED
              )
              .certificateMetaData(
                  CertificateMetaData.builder()
                      .issuer(Staff.builder()
                          .name(Name.builder()
                              .firstName(ce.getIssuedBy().getFirstName())
                              .middleName(ce.getIssuedBy().getMiddleName())
                              .lastName(ce.getIssuedBy().getLastName())
                              .build())
                          .hsaId(new HsaId(ce.getIssuedBy().getHsaId()))
                          .build())
                      .careProvider(CARE_PROVIDER)
                      .careUnit(CARE_UNIT)
                      .issuingUnit(SubUnit.builder()
                          .name(new UnitName(ce.getIssuedOnUnit().getName()))
                          .hsaId(new HsaId(ce.getIssuedOnUnit().getHsaId()))
                          .build()
                      )
                      .patient(Patient.builder()
                          .id(PersonId.builder()
                              .id(ce.getPatient().getId())
                              .type(PersonIdType.valueOf(ce.getPatient().getType().getType()))
                              .build())
                          .testIndicated(new TestIndicated(ce.getPatient().isTestIndicated()))
                          .protectedPerson(new ProtectedPerson(ce.getPatient().isProtectedPerson()))
                          .deceased(new Deceased(ce.getPatient().isDeceased()))
                          .name(Name.builder()
                              .firstName(ce.getPatient().getFirstName())
                              .middleName(ce.getPatient().getMiddleName())
                              .lastName(ce.getPatient().getLastName())
                              .build())
                          .build())
                      .creator(ISSUED_BY)
                      .build()
              )
              .build();
        });
  }

  private void stubEntity() {
    when(certificateEntityMapper.toEntity(any(Certificate.class)))
        .thenAnswer(inv -> {
          Certificate cert = inv.getArgument(0);
          return CertificateEntity.builder()
              .certificateId(cert.id().id())
              .created(cert.created())
              .signed(cert.signed())
              .modified(cert.modified())
              .revision(cert.revision().value())
              .locked(cert.locked())
              .patient(PATIENT_ENTITY)
              .certificateModel(MODEL)
              .createdBy(ISSUED_BY_ENTITY)
              .careProvider(CARE_PROVIDER_ENTITY)
              .careUnit(CARE_UNIT_ENTITY)
              .issuedBy(ISSUED_BY_ENTITY)
              .issuedOnUnit(ISSUED_ON_UNIT_ENTITY)
              .status(new CertificateStatusEntity((short) 1, Status.SIGNED.name()))
              .data(DATA)
              .placeholder(cert.isPlaceholder())
              .build();
        });
  }

  private static final LocalDateTime TIMESTAMP = LocalDateTime.now()
      .truncatedTo(ChronoUnit.SECONDS);

  private static final CertificateModelEntity MODEL = CertificateModelEntity.builder()
      .name("NAME")
      .type("TYPE")
      .version("VERSION")
      .build();

  private static final String ID = "ID";

  private static final CertificateModel CONVERTED_MODEL = CertificateModelEntityMapper.toDomain(
      MODEL);
  private static final UnitEntity CARE_PROVIDER_ENTITY = UnitEntity.builder()
      .type(
          UnitTypeEntity.builder()
              .type(UnitType.CARE_PROVIDER.name())
              .key(UnitType.CARE_PROVIDER.getKey())
              .build()
      )
      .hsaId("HSA_ID_PROVIDER")
      .name("NAME_PROVIDER")
      .build();

  private static final CareProvider CARE_PROVIDER = CareProvider.builder()
      .hsaId(new HsaId("HSA_ID_PROVIDER"))
      .name(new UnitName("NAME_PROVIDER"))
      .build();

  private static final UnitEntity CARE_UNIT_ENTITY = UnitEntity.builder()
      .type(
          UnitTypeEntity.builder()
              .type(UnitType.CARE_UNIT.name())
              .key(UnitType.CARE_UNIT.getKey())
              .build()
      )
      .hsaId("HSA_ID_UNIT")
      .name("NAME_UNIT")
      .build();

  private static final CareUnit CARE_UNIT = CareUnit.builder()
      .hsaId(new HsaId("HSA_ID_UNIT"))
      .name(new UnitName("NAME_UNIT"))
      .build();

  private static final UnitEntity ISSUED_ON_UNIT_ENTITY = UnitEntity.builder()
      .type(
          UnitTypeEntity.builder()
              .type(UnitType.SUB_UNIT.name())
              .key(UnitType.SUB_UNIT.getKey())
              .build()
      )
      .hsaId("HSA_ID_ISSUED")
      .name("NAME_ISSUED")
      .build();


  private static final SubUnit ISSUED_ON_UNIT = SubUnit.builder()
      .hsaId(new HsaId("HSA_ID_ISSUED"))
      .name(new UnitName("NAME_ISSUED"))
      .build();

  private static final PatientEntity PATIENT_ENTITY = PatientEntity.builder()
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


  private static final Patient PATIENT = Patient.builder()
      .id(PersonId.builder()
          .id("ID")
          .type(PersonIdType.PERSONAL_IDENTITY_NUMBER)
          .build())
      .testIndicated(new TestIndicated(false))
      .protectedPerson(new ProtectedPerson(false))
      .deceased(new Deceased(false))
      .name(Name.builder()
          .firstName("FIRST")
          .middleName("MIDDLE")
          .lastName("LAST")
          .build())
      .build();

  private static final Staff ISSUED_BY = Staff.builder()
      .name(Name.builder()
          .firstName("NAME")
          .middleName("NAME")
          .lastName("NAME")
          .build())
      .hsaId(new HsaId("HSA_ID"))
      .build();


  private static final StaffEntity ISSUED_BY_ENTITY = StaffEntity.builder()
      .firstName("NAME")
      .middleName("NAME")
      .lastName("NAME")
      .hsaId("HSA_ID")
      .paTitles(Collections.emptyList())
      .specialities(Collections.emptyList())
      .build();


  private static final LocalDate NOW = LocalDate.now();
  private static final String JSON =
      "[{\"id\":\"F10\",\"value\":{\"type\":\"DATE\",\"date\":[" + NOW.getYear() + ","
          + NOW.getMonthValue() + "," + NOW.getDayOfMonth() + "]}}]";

  private static final CertificateDataEntity DATA = new CertificateDataEntity(JSON);

  private static final CertificateEntity CERTIFICATE_ENTITY = CertificateEntity.builder()
      .revision(1L)
      .certificateId("ID")
      .modified(TIMESTAMP)
      .created(TIMESTAMP)
      .careProvider(CARE_PROVIDER_ENTITY)
      .careUnit(CARE_UNIT_ENTITY)
      .issuedOnUnit(ISSUED_ON_UNIT_ENTITY)
      .issuedBy(ISSUED_BY_ENTITY)
      .patient(PATIENT_ENTITY)
      .certificateModel(MODEL)
      .status(new CertificateStatusEntity(1, Status.SIGNED.name()))
      .data(DATA)
      .placeholder(false)
      .build();

  private static final CertificateEntity SIGNED_CERTIFICATE_ENTITY = CertificateEntity.builder()
      .revision(1L)
      .certificateId("ID")
      .modified(TIMESTAMP)
      .signed(TIMESTAMP)
      .created(TIMESTAMP)
      .careProvider(CARE_PROVIDER_ENTITY)
      .careUnit(CARE_UNIT_ENTITY)
      .createdBy(ISSUED_BY_ENTITY)
      .issuedOnUnit(ISSUED_ON_UNIT_ENTITY)
      .issuedBy(ISSUED_BY_ENTITY)
      .patient(PATIENT_ENTITY)
      .certificateModel(MODEL)
      .status(new CertificateStatusEntity(1, Status.SIGNED.name()))
      .data(DATA)
      .placeholder(false)
      .build();


  private static final Certificate EXPECTED_CERTIFICATE = MedicalCertificate.builder()
      .revision(new Revision(1L))
      .id(new CertificateId("ID"))
      .status(Status.SIGNED)
      .created(TIMESTAMP)
      .modified(TIMESTAMP)
      .signed(TIMESTAMP)
      .status(Status.SIGNED)
      .certificateModel(CONVERTED_MODEL)
      .certificateMetaData(
          CertificateMetaData.builder()
              .issuer(ISSUED_BY)
              .careProvider(CARE_PROVIDER)
              .careUnit(CARE_UNIT)
              .issuingUnit(ISSUED_ON_UNIT)
              .patient(PATIENT)
              .creator(ISSUED_BY)
              .build()
      )
      .build();


  @Nested
  class Create {

    @Test
    void shouldThrowExceptionIfCertificateModelIsNull() {
      assertThrows(IllegalArgumentException.class,
          () -> jpaCertificateRepository.create(null, certificateRepository));
    }

    @Test
    void shouldCreateCertificateWithModel() {
      final var response = jpaCertificateRepository.create(CONVERTED_MODEL, certificateRepository);

      assertEquals(CONVERTED_MODEL, response.certificateModel());
    }

    @Test
    void shouldCreateCertificateWithId() {
      final var response = jpaCertificateRepository.create(CONVERTED_MODEL, certificateRepository);

      assertNotNull(response.id().id());
    }

    @Test
    void shouldCreateCertificateWithCreated() {
      final var response = jpaCertificateRepository.create(CONVERTED_MODEL, certificateRepository);

      assertEquals(
          LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS),
          response.created().truncatedTo(ChronoUnit.SECONDS)
      );
    }

    @Test
    void shouldCreateCertificateWithRevision() {
      final var response = jpaCertificateRepository.create(CONVERTED_MODEL, certificateRepository);

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
      stubDomain();
      stubEntity();
      doReturn(SIGNED_CERTIFICATE_ENTITY).when(certificateEntityRepository)
          .save(SIGNED_CERTIFICATE_ENTITY);

      final var response = jpaCertificateRepository.save(EXPECTED_CERTIFICATE,
          certificateRepository);

      assertEquals(EXPECTED_CERTIFICATE, response);
    }

    @Test
    void shouldDeleteCertificate() {
      final var expectedResult = MedicalCertificate.builder()
          .id(CERTIFICATE_ID)
          .status(Status.DELETED_DRAFT)
          .build();

      doReturn(Optional.of(CERTIFICATE_ENTITY)).when(certificateEntityRepository)
          .findByCertificateId(CERTIFICATE_ID.id());
      final var actualResult = jpaCertificateRepository.save(expectedResult, certificateRepository);

      verify(certificateEntityRepository).delete(CERTIFICATE_ENTITY);
      verify(certificateRelationRepository).deleteRelations(CERTIFICATE_ENTITY);
      assertEquals(expectedResult, actualResult);
    }

    @Test
    void shouldDeleteRelationsIfStatusLockedDraft() {
      final var expectedResult = MedicalCertificate.builder()
          .id(CERTIFICATE_ID)
          .status(Status.LOCKED_DRAFT)
          .build();

      doReturn(Optional.of(CERTIFICATE_ENTITY)).when(certificateEntityRepository)
          .findByCertificateId(CERTIFICATE_ID.id());
      jpaCertificateRepository.save(expectedResult, certificateRepository);

      verify(certificateRelationRepository).deleteRelations(CERTIFICATE_ENTITY);
    }

    @Test
    void shouldSaveCertificateRelations() {
      stubDomain();
      stubEntity();

      doReturn(SIGNED_CERTIFICATE_ENTITY).when(certificateEntityRepository)
          .save(SIGNED_CERTIFICATE_ENTITY);

      jpaCertificateRepository.save(EXPECTED_CERTIFICATE, certificateRepository);
      verify(certificateRelationRepository).save(EXPECTED_CERTIFICATE, SIGNED_CERTIFICATE_ENTITY);
    }
  }

  @Nested
  class GetById {

    @Test
    void shouldThrowExceptionIfIdIsNull() {
      assertThrows(IllegalArgumentException.class,
          () -> jpaCertificateRepository.getById(null, certificateRepository));
    }

    @Test
    void shouldThrowExceptionIfCertificateIsNull() {
      final var id = new CertificateId("ID");
      assertThrows(IllegalArgumentException.class,
          () -> jpaCertificateRepository.getById(id, certificateRepository)
      );
    }

    @Test
    void shouldReturnCertificateFromRepository() {
      stubDomain();
      when(certificateEntityRepository.findByCertificateId("ID"))
          .thenReturn(Optional.of(SIGNED_CERTIFICATE_ENTITY));

      final var response = jpaCertificateRepository.getById(new CertificateId("ID"),
          certificateRepository);

      assertEquals(EXPECTED_CERTIFICATE, response);
    }
  }

  @Nested
  class GetByIds {

    @Test
    void shouldThrowExceptionIfIdsIsNull() {
      assertThrows(IllegalArgumentException.class,
          () -> jpaCertificateRepository.getByIds(null, certificateRepository));
    }

    @Test
    void shouldThrowExceptionIfIdsIsEmpty() {
      final List<CertificateId> certificateIds = Collections.emptyList();
      assertThrows(IllegalArgumentException.class,
          () -> jpaCertificateRepository.getByIds(certificateIds, certificateRepository)
      );
    }

    @Test
    void shouldReturnCertificatesFromRepository() {
      stubDomain();
      final var expectedCertificates = List.of(EXPECTED_CERTIFICATE, EXPECTED_CERTIFICATE);
      final var certificate1 = new CertificateId("ID1");
      final var certificate2 = new CertificateId("ID2");

      final var certificateIds = List.of(certificate1, certificate2);

      when(certificateEntityRepository.findCertificateEntitiesByCertificateIdIn(
          List.of("ID1", "ID2"))
      ).thenReturn(List.of(SIGNED_CERTIFICATE_ENTITY, SIGNED_CERTIFICATE_ENTITY));

      final var response = jpaCertificateRepository.getByIds(certificateIds, certificateRepository);

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
          () -> jpaCertificateRepository.getByIds(certificateIds, certificateRepository));
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
      stubDomain();
      stubEntity();
      doReturn(SIGNED_CERTIFICATE_ENTITY).when(certificateEntityRepository)
          .save(SIGNED_CERTIFICATE_ENTITY);

      final var actualCertificate = jpaCertificateRepository.insert(EXPECTED_CERTIFICATE,
          new Revision(1), certificateRepository);

      assertEquals(EXPECTED_CERTIFICATE, actualCertificate);
    }

    @Test
    void shouldSaveCertificateRelations() {
      stubDomain();
      stubEntity();
      doReturn(SIGNED_CERTIFICATE_ENTITY).when(certificateEntityRepository)
          .save(SIGNED_CERTIFICATE_ENTITY);

      jpaCertificateRepository.insert(EXPECTED_CERTIFICATE, new Revision(1), certificateRepository);
      verify(certificateRelationRepository).save(EXPECTED_CERTIFICATE, SIGNED_CERTIFICATE_ENTITY);
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

      stubDomain();

      final var expectedCertificates = List.of(EXPECTED_CERTIFICATE);
      final var request = List.of(CERTIFICATE_ID);
      final var certificateIds = List.of(CERTIFICATE_ID.id());

      doReturn(List.of(SIGNED_CERTIFICATE_ENTITY)).when(certificateEntityRepository)
          .findCertificateEntitiesByCertificateIdIn(certificateIds);

      final var actualCertificates = jpaCertificateRepository.findByIds(request,
          certificateRepository);
      assertEquals(expectedCertificates, actualCertificates);
    }

    @Test
    void shallReturnEmptyListIfNoCertificateIdsArePresent() {
      final var expectedCertificates = Collections.emptyList();
      final var request = List.of(CERTIFICATE_ID);
      final var certificateIds = List.of(CERTIFICATE_ID.id());

      doReturn(Collections.emptyList()).when(certificateEntityRepository)
          .findCertificateEntitiesByCertificateIdIn(certificateIds);

      final var actualCertificates = jpaCertificateRepository.findByIds(request,
          certificateRepository);
      assertEquals(expectedCertificates, actualCertificates);
    }

    @Test
    void shallThrowIfCertificateIdsIsNull() {
      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> jpaCertificateRepository.findByIds(null, certificateRepository));

      assertEquals("Cannot get certificate if certificateIds is null",
          illegalArgumentException.getMessage());
    }
  }

  @Nested
  class GetByCareProviderIdTests {

    @Test
    void shallThrowIfCareProviderIdIsNull() {
      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> jpaCertificateRepository.getExportByCareProviderId(null, 0, 0,
              certificateRepository));

      assertEquals("Cannot get certificates if careProviderId is null",
          illegalArgumentException.getMessage());
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

      doReturn(page).when(certificateEntityRepository)
          .findSignedCertificateEntitiesByCareProviderHsaId(
              eq(CARE_PROVIDER_ENTITY.getHsaId()), any(Pageable.class)
          );
      doReturn(2L).when(certificateEntityRepository)
          .findRevokedCertificateEntitiesByCareProviderHsaId(
              CARE_PROVIDER_ENTITY.getHsaId()
          );
      doReturn(Collections.singletonList(certificateEntity)).when(page).getContent();
      doReturn(2L).when(page).getTotalElements();
      doReturn(FK3226_CERTIFICATE).when(certificateEntityMapper).toDomain(eq(certificateEntity),
          any(CertificateRepository.class));

      final var actualResult = jpaCertificateRepository.getExportByCareProviderId(
          new HsaId(CARE_PROVIDER_ENTITY.getHsaId()), 0, 10, certificateRepository);
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
          CARE_PROVIDER_ENTITY.getHsaId(), pageable
      );
      doReturn(List.of(certificateEntity)).when(page).getContent();
      doReturn(false).when(page).hasNext();
      doReturn(1L).when(page).getTotalElements();

      final var deletedCount = jpaCertificateRepository.deleteByCareProviderId(
          new HsaId(CARE_PROVIDER_ENTITY.getHsaId()));

      verify(certificateRelationRepository).deleteRelations(certificateEntity);
      verify(certificateEntityRepository).deleteAllByCertificateIdIn(
          List.of(certificateEntity.getCertificateId()));
      assertEquals(1L, deletedCount);
    }
  }

  @Nested
  class CreateFromPlaceholder {

    @Test
    void shouldCreateMedicalCertificateWithParentRelationFromPlaceholder() {
      final var placeHolderRequest = PlaceholderCertificateRequest.builder()
          .certificateId(new CertificateId(ID))
          .status(Status.SIGNED)
          .build();

      final var response = jpaCertificateRepository
          .createFromPlaceholder(placeHolderRequest, CONVERTED_MODEL, certificateRepository);

      assertAll(
          () -> assertEquals(RelationType.RENEW, response.parent().type()),
          () -> assertEquals(ID, response.parent().certificate().id().id()),
          () -> assertEquals(Status.SIGNED, response.parent().certificate().status())
      );
    }

    @Test
    void shouldCreateMedicalCertificate() {
      final var placeHolderRequest = PlaceholderCertificateRequest.builder()
          .certificateId(new CertificateId(ID))
          .status(Status.DRAFT)
          .build();

      final var response = jpaCertificateRepository
          .createFromPlaceholder(placeHolderRequest, CONVERTED_MODEL, certificateRepository);

      assertAll(
          () -> assertNotNull(response.id().id()),
          () -> assertNotNull(response.created()),
          () -> assertEquals(CONVERTED_MODEL, response.certificateModel()),
          () -> assertEquals(Status.DRAFT, response.status()),
          () -> assertEquals(0, response.revision().value())
      );
    }

    @Test
    void shouldSavePlaceHolderCertificate() {
      final var issuingUnit = SubUnit.builder().build();
      final var placeHolderRequest = PlaceholderCertificateRequest.builder()
          .certificateId(new CertificateId(ID))
          .status(Status.DRAFT)
          .issuingUnit(issuingUnit)
          .build();

      final var expectedPlaceHolderCertificate = PlaceholderCertificate.builder()
          .id(new CertificateId(ID))
          .status(Status.DRAFT)
          .certificateMetaData(
              CertificateMetaData.builder()
                  .issuingUnit(issuingUnit)
                  .build()
          )
          .build();

      when(placeHolderEntityMapper.toEntity(expectedPlaceHolderCertificate))
          .thenReturn(CERTIFICATE_ENTITY);

      jpaCertificateRepository
          .createFromPlaceholder(placeHolderRequest, CONVERTED_MODEL, certificateRepository);

      verify(certificateEntityRepository).save(CERTIFICATE_ENTITY);
    }
  }

  @Nested
  class PlaceholderExistsTests {

    @Test
    void shouldThrowIfCertificateIdIsNull() {
      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> jpaCertificateRepository.placeholderExists(null));

      assertEquals("Cannot check if placeholder certificate exists since certificateId is null",
          illegalArgumentException.getMessage());
    }

    @Test
    void shouldReturnTrueIfPlaceholderExists() {
      when(certificateEntityRepository.findPlaceholderByCertificateId(
          CERTIFICATE_ID.id())).thenReturn(Optional.of(CERTIFICATE_ENTITY));
      assertTrue(jpaCertificateRepository.placeholderExists(CERTIFICATE_ID));
    }
  }

  @Nested
  class GetPlaceholderByIdTests {

    @Test
    void shouldThrowIfCertificateIdIsNull() {
      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> jpaCertificateRepository.getPlaceholderById(null));

      assertEquals("Cannot get placeholder certificate if certificateId is null",
          illegalArgumentException.getMessage());
    }

    @Test
    void shouldThrowIfPlaceholderCertificateIsNotPresent() {
      when(certificateEntityRepository.findPlaceholderByCertificateId(
          CERTIFICATE_ID.id())).thenReturn(Optional.empty());

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> jpaCertificateRepository.getPlaceholderById(CERTIFICATE_ID));

      assertEquals("CertificateId '%s' not present in repository".formatted(CERTIFICATE_ID),
          illegalArgumentException.getMessage());
    }

    @Test
    void shouldReturnPlaceholderCertificateIfPresent() {
      final var expectedResult = PlaceholderCertificate.builder().build();

      when(certificateEntityRepository.findPlaceholderByCertificateId(
          CERTIFICATE_ID.id())).thenReturn(Optional.of(CERTIFICATE_ENTITY));
      when(placeHolderEntityMapper.toDomain(CERTIFICATE_ENTITY)).thenReturn(expectedResult);

      final var actualResult = jpaCertificateRepository.getPlaceholderById(CERTIFICATE_ID);

      assertEquals(expectedResult, actualResult);
    }
  }

  @Nested
  class SavePlaceholderCertificateTests {

    private static final PlaceholderCertificate PLACEHOLDER_CERTIFICATE = PlaceholderCertificate.builder()
        .build();

    @Test
    void shouldThrowIfCertificateIsNull() {
      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> jpaCertificateRepository.save(null));

      assertEquals("Unable to save, placeholderCertificate was null",
          illegalArgumentException.getMessage());
    }

    @Test
    void shouldSavePlaceholderCertificate() {
      when(placeHolderEntityMapper.toEntity(PLACEHOLDER_CERTIFICATE)).thenReturn(
          CERTIFICATE_ENTITY);

      jpaCertificateRepository.save(PLACEHOLDER_CERTIFICATE);

      verify(certificateEntityRepository).save(CERTIFICATE_ENTITY);
    }

    @Test
    void shouldReturnPersistedPlaceholderCertificate() {
      when(placeHolderEntityMapper.toEntity(PLACEHOLDER_CERTIFICATE)).thenReturn(
          CERTIFICATE_ENTITY);
      when(certificateEntityRepository.save(CERTIFICATE_ENTITY)).thenReturn(CERTIFICATE_ENTITY);
      when(placeHolderEntityMapper.toDomain(CERTIFICATE_ENTITY)).thenReturn(
          PLACEHOLDER_CERTIFICATE);

      final var actualCertificate = jpaCertificateRepository.save(PLACEHOLDER_CERTIFICATE);

      assertEquals(PLACEHOLDER_CERTIFICATE, actualCertificate);
    }
  }

  @Nested
  class GetMetadataFromSignInstance {

    @Test
    void shouldThrowIllegalStateExceptionWhenNotSigned() {
      assertThrows(IllegalStateException.class,
          () -> jpaCertificateRepository.getMetadataFromSignInstance(CERTIFICATE_META_DATA, null));
    }

    @Test
    void shouldReturnVersionedPatientMetadataWhenSigned() {

      final var athenaWithoutAddress = athenaReactAnderssonBuilder().address(null).build();

      doReturn(Optional.of(ATHENA_REACT_ANDERSSON_VERSION_ENTITY)).when(
              patientVersionEntityRepository)
          .findFirstCoveringTimestampOrderByMostRecent(CERTIFICATE_META_DATA
              .patient().id().idWithoutDash(), TIMESTAMP);

      final var result = jpaCertificateRepository.getMetadataFromSignInstance(
          CERTIFICATE_META_DATA, TIMESTAMP);

      assertEquals(athenaWithoutAddress, result.patient());
    }

    @Test
    void shouldReturnVersionedStaffMetadataWhenSigned() {

      doReturn(List.of(AJLA_DOKTOR_VERSION_ENTITY, ALF_DOKTOR_VERSION_ENTITY)).when(
              staffVersionEntityRepository)
          .findAllCoveringTimestampByHsaIdIn(List.of(AJLA_DOCTOR_HSA_ID, ALF_DOKTOR_HSA_ID),
              TIMESTAMP);

      final var result = jpaCertificateRepository.getMetadataFromSignInstance(
          CERTIFICATE_META_DATA, TIMESTAMP);

      assertAll(
          () -> assertEquals(AJLA_DOKTOR, result.issuer()),
          () -> assertEquals(ALF_DOKTOR, result.creator())
      );

    }

    @Test
    void shouldReturnUnitsMetadataWhenSigned() {

      doReturn(List.of(
          ALFA_REGIONEN_VERSION_ENTITY,
          ALFA_MEDICINCENTRUM_VERSION_ENTITY,
          ALFA_ALLERGIMOTTAGNINGEN_VERSION_ENTITY))
          .when(unitVersionEntityRepository)
          .findAllCoveringTimestampByHsaIdIn(List.of(
                  ALFA_REGIONEN_ID, ALFA_MEDICINCENTRUM_ID, ALFA_ALLERGIMOTTAGNINGEN_ID),
              TIMESTAMP);

      final var result = jpaCertificateRepository.getMetadataFromSignInstance(
          CERTIFICATE_META_DATA, TIMESTAMP);

      assertAll(
          () -> assertEquals(ALFA_REGIONEN, result.careProvider()),
          () -> assertEquals(ALFA_MEDICINCENTRUM, result.careUnit()),
          () -> assertEquals(ALFA_ALLERGIMOTTAGNINGEN, result.issuingUnit())
      );
    }
  }

  @Nested
  class FindByCertificatesRequestTest {

    @Test
    void shouldReturnEmptyListIfNoCertificatesAreFound() {
      final var request = CertificatesRequest.builder()
          .build();

      doReturn(List.of()).when(certificateEntityRepository).findAll(any());

      final var actualCertificates = jpaCertificateRepository.findByCertificatesRequest(request,
          certificateRepository);

      assertTrue(actualCertificates.isEmpty());
    }

    @Test
    void shouldReturnListOfCertificates() {
      final var expectedCertificates = List.of(EXPECTED_CERTIFICATE);
      final var request = CertificatesRequest.builder()
          .build();
      doReturn(List.of(CERTIFICATE_ENTITY)).when(certificateEntityRepository).findAll(any());
      doReturn(EXPECTED_CERTIFICATE).when(certificateEntityMapper)
          .toDomain(CERTIFICATE_ENTITY, certificateRepository);

      final var actualCertificates = jpaCertificateRepository.findByCertificatesRequest(request,
          certificateRepository);

      assertEquals(expectedCertificates, actualCertificates);
    }

    @Test
    void shouldCallSpecificationFactoryToCreateSpecification() {
      final var request = CertificatesRequest.builder()
          .build();
      final var specification = mock(Specification.class);
      doReturn(List.of()).when(certificateEntityRepository).findAll(any());
      when(certificateEntitySpecificationFactory.create(request)).thenReturn(specification);

      jpaCertificateRepository.findByCertificatesRequest(request, certificateRepository);

      verify(certificateEntityRepository).findAll(specification);
    }
  }

  @Nested
  class FindIdsByCertificatesRequestTest {

    @Test
    void shouldReturnEmptyListIfNoCertificatesAreFound() {
      final var request = CertificatesRequest.builder()
          .createdTo(LocalDateTime.now())
          .statuses(List.of(Status.DRAFT, Status.LOCKED_DRAFT))
          .build();

      doReturn(List.of()).when(certificateEntityRepository)
          .findCertificateIdsByCreatedBeforeAndStatusIn(request.createdTo(),
              List.of(1, 4));
      final var actualCertificateIds = jpaCertificateRepository.findIdsByCreatedBeforeAndStatusIn(
          request);

      assertTrue(actualCertificateIds.isEmpty());
    }

    @Test
    void shouldReturnListOfCertificateIds() {
      final var request = CertificatesRequest.builder()
          .createdTo(LocalDateTime.now())
          .statuses(List.of(Status.DRAFT, Status.LOCKED_DRAFT))
          .build();
      doReturn(List.of(CERTIFICATE_ID.id())).when(certificateEntityRepository)
          .findCertificateIdsByCreatedBeforeAndStatusIn(request.createdTo(),
              List.of(1, 4));

      final var actualCertificateIds = jpaCertificateRepository.findIdsByCreatedBeforeAndStatusIn(
          request);

      assertEquals(List.of(CERTIFICATE_ID), actualCertificateIds);
    }
  }

  @Nested
  class FindValidSickLeavesByIdsTests {

    private static final String ID1 = "CERTIFICATE_ID";
    private static final List<CertificateId> CERTIFICATE_IDS = List.of(
        new CertificateId(ID1));

    @Test
    void shouldReturnListOfCertificateIds() {
      when(
          certificateEntityRepository.findValidSickLeaveCertificatesByIds(List.of(ID1))).thenReturn(
          List.of(CERTIFICATE_ENTITY.getCertificateId()));

      final var result = jpaCertificateRepository.findValidSickLeavesByIds(
          CERTIFICATE_IDS);
      assertEquals(List.of(EXPECTED_CERTIFICATE.id()), result);
    }
  }

  @Nested
  class UpdateCertificateMetadataFromSignInstancesTest {

    @Test
    void shouldUpdatePatientMetadataFromVersionEntity() {
      final var athenaWithoutAddress = athenaReactAnderssonBuilder().address(null).build();
      final var certificate = MedicalCertificate.builder()
          .id(CERTIFICATE_ID)
          .signed(TIMESTAMP)
          .certificateMetaData(CERTIFICATE_META_DATA)
          .build();

      doReturn(List.of(ATHENA_REACT_ANDERSSON_VERSION_ENTITY))
          .when(patientVersionEntityRepository)
          .findAllByIdIn(List.of(CERTIFICATE_META_DATA.patient().id().idWithoutDash()));
      doReturn(List.of()).when(staffVersionEntityRepository).findAllByHsaIdIn(any());
      doReturn(List.of()).when(unitVersionEntityRepository).findAllByHsaIdIn(any());

      jpaCertificateRepository.updateCertificateMetadataFromSignInstances(List.of(certificate));

      assertEquals(athenaWithoutAddress, certificate.certificateMetaData().patient());
    }

    @Test
    void shouldUpdateStaffMetadataFromVersionEntities() {
      final var certificate = MedicalCertificate.builder()
          .id(CERTIFICATE_ID)
          .signed(TIMESTAMP)
          .certificateMetaData(CERTIFICATE_META_DATA)
          .build();

      doReturn(List.of()).when(patientVersionEntityRepository)
          .findAllByIdIn(any());
      doReturn(List.of(AJLA_DOKTOR_VERSION_ENTITY, ALF_DOKTOR_VERSION_ENTITY))
          .when(staffVersionEntityRepository)
          .findAllByHsaIdIn(List.of(AJLA_DOCTOR_HSA_ID, ALF_DOKTOR_HSA_ID));
      doReturn(List.of()).when(unitVersionEntityRepository).findAllByHsaIdIn(any());

      jpaCertificateRepository.updateCertificateMetadataFromSignInstances(List.of(certificate));

      assertAll(
          () -> assertEquals(AJLA_DOKTOR, certificate.certificateMetaData().issuer()),
          () -> assertEquals(ALF_DOKTOR, certificate.certificateMetaData().creator())
      );
    }

    @Test
    void shouldUpdateUnitMetadataFromVersionEntities() {
      final var certificate = MedicalCertificate.builder()
          .id(CERTIFICATE_ID)
          .signed(TIMESTAMP)
          .certificateMetaData(CERTIFICATE_META_DATA)
          .build();

      doReturn(List.of()).when(patientVersionEntityRepository)
          .findAllByIdIn(any());
      doReturn(List.of()).when(staffVersionEntityRepository).findAllByHsaIdIn(any());
      doReturn(List.of(
          ALFA_REGIONEN_VERSION_ENTITY,
          ALFA_MEDICINCENTRUM_VERSION_ENTITY,
          ALFA_ALLERGIMOTTAGNINGEN_VERSION_ENTITY))
          .when(unitVersionEntityRepository)
          .findAllByHsaIdIn(List.of(
              ALFA_MEDICINCENTRUM_ID, ALFA_REGIONEN_ID, ALFA_ALLERGIMOTTAGNINGEN_ID));

      jpaCertificateRepository.updateCertificateMetadataFromSignInstances(List.of(certificate));

      assertAll(
          () -> assertEquals(ALFA_REGIONEN, certificate.certificateMetaData().careProvider()),
          () -> assertEquals(ALFA_MEDICINCENTRUM, certificate.certificateMetaData().careUnit()),
          () -> assertEquals(ALFA_ALLERGIMOTTAGNINGEN,
              certificate.certificateMetaData().issuingUnit())
      );
    }

    @Test
    void shouldDefaultToOriginalMetadataWhenNoVersionFound() {
      final var certificate = MedicalCertificate.builder()
          .id(CERTIFICATE_ID)
          .signed(TIMESTAMP)
          .certificateMetaData(CERTIFICATE_META_DATA)
          .build();

      doReturn(List.of()).when(patientVersionEntityRepository)
          .findAllByIdIn(any());
      doReturn(List.of()).when(staffVersionEntityRepository).findAllByHsaIdIn(any());
      doReturn(List.of()).when(unitVersionEntityRepository).findAllByHsaIdIn(any());

      jpaCertificateRepository.updateCertificateMetadataFromSignInstances(List.of(certificate));

      assertEquals(CERTIFICATE_META_DATA, certificate.certificateMetaData());
    }

    @Test
    void shouldNotUpdateMetadataForUnsignedCertificate() {
      final var unsignedCertificate = MedicalCertificate.builder()
          .id(CERTIFICATE_ID)
          .signed(null)
          .certificateMetaData(CERTIFICATE_META_DATA)
          .build();

      jpaCertificateRepository.updateCertificateMetadataFromSignInstances(
          List.of(unsignedCertificate));

      assertEquals(CERTIFICATE_META_DATA, unsignedCertificate.certificateMetaData());
    }

    @Test
    void shouldHandleMultipleCertificates() {
      final var certificate1 = MedicalCertificate.builder()
          .id(CERTIFICATE_ID)
          .signed(TIMESTAMP)
          .certificateMetaData(CERTIFICATE_META_DATA)
          .build();

      final var certificate2 = MedicalCertificate.builder()
          .id(new CertificateId("ID2"))
          .signed(TIMESTAMP)
          .certificateMetaData(CERTIFICATE_META_DATA)
          .build();

      doReturn(List.of(ATHENA_REACT_ANDERSSON_VERSION_ENTITY))
          .when(patientVersionEntityRepository)
          .findAllByIdIn(any());
      doReturn(List.of(AJLA_DOKTOR_VERSION_ENTITY, ALF_DOKTOR_VERSION_ENTITY))
          .when(staffVersionEntityRepository).findAllByHsaIdIn(any());
      doReturn(List.of(
          ALFA_REGIONEN_VERSION_ENTITY,
          ALFA_MEDICINCENTRUM_VERSION_ENTITY,
          ALFA_ALLERGIMOTTAGNINGEN_VERSION_ENTITY))
          .when(unitVersionEntityRepository).findAllByHsaIdIn(any());

      jpaCertificateRepository.updateCertificateMetadataFromSignInstances(
          List.of(certificate1, certificate2));

      assertAll(
          () -> assertNotNull(certificate1.certificateMetaData()),
          () -> assertNotNull(certificate2.certificateMetaData())
      );
    }

    @Test
    void shouldFilterVersionsBySignedTimestamp() {
      final var certificate = MedicalCertificate.builder()
          .id(CERTIFICATE_ID)
          .signed(TIMESTAMP)
          .certificateMetaData(CERTIFICATE_META_DATA)
          .build();

      final var versionEntity =
          athenaReactAnderssonVersionEntityBuilder()
              .validFrom(TIMESTAMP.minusDays(1))
              .validTo(TIMESTAMP.plusDays(1)).build();

      doReturn(List.of(versionEntity))
          .when(patientVersionEntityRepository)
          .findAllByIdIn(any());
      doReturn(List.of()).when(staffVersionEntityRepository).findAllByHsaIdIn(any());
      doReturn(List.of()).when(unitVersionEntityRepository).findAllByHsaIdIn(any());

      jpaCertificateRepository.updateCertificateMetadataFromSignInstances(List.of(certificate));

      assertNotNull(certificate.certificateMetaData().patient());
    }
  }
}