package se.inera.intyg.certificateservice.infrastructure.certificate.persistence;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static se.inera.intyg.certificateservice.application.testdata.TestDataPatientEntity.athenaReactAnderssonEntityBuilder;
import static se.inera.intyg.certificateservice.application.testdata.TestDataPatientVersionEntity.ATHENA_REACT_ANDERSSON_VERSION_ENTITY;
import static se.inera.intyg.certificateservice.application.testdata.TestDataStaffEntity.ajlaDoctorEntityBuilder;
import static se.inera.intyg.certificateservice.application.testdata.TestDataStaffVersionEntity.AJLA_DOKTOR_VERSION_ENTITY;
import static se.inera.intyg.certificateservice.application.testdata.TestDataUnitEntity.alfaMedicinCentrumEntityBuilder;
import static se.inera.intyg.certificateservice.application.testdata.TestDataUnitVersionEntity.ALFA_MEDICINCENTRUM_VERSION_ENTITY;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.PatientEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.PatientVersionEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.StaffEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.StaffVersionEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.UnitEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.UnitVersionEntityRepository;

@ExtendWith(MockitoExtension.class)
class MetadataVersionRepositoryTest {

  @Mock
  StaffEntityRepository staffEntityRepository;
  @Mock
  UnitEntityRepository unitEntityRepository;
  @Mock
  PatientEntityRepository patientEntityRepository;
  @Mock
  StaffVersionEntityRepository staffVersionEntityRepository;
  @Mock
  PatientVersionEntityRepository patientVersionEntityRepository;
  @Mock
  UnitVersionEntityRepository unitVersionEntityRepository;
  @InjectMocks
  private MetadataVersionRepository metadataVersionRepository;


  private static final LocalDateTime TIMESTAMP = LocalDateTime.now()
      .truncatedTo(ChronoUnit.SECONDS);


  @Nested
  class SaveVersions {

    @Test
    void shouldSaveUpdatedStaff() {
      final var ajlaEntity = ajlaDoctorEntityBuilder().build();
      final var ajlaUpdatedEntity = ajlaDoctorEntityBuilder()
          .firstName("Maria")
          .build();

      metadataVersionRepository.saveStaffVersion(ajlaEntity, ajlaUpdatedEntity);

      verify(staffEntityRepository).save(ajlaUpdatedEntity);
    }

    @Test
    void shouldSaveUpdatedUnit() {
      final var unitEntity = alfaMedicinCentrumEntityBuilder().build();
      final var unitUpdatedEntity = alfaMedicinCentrumEntityBuilder()
          .name("Updated Name")
          .build();

      metadataVersionRepository.saveUnitVersion(unitEntity, unitUpdatedEntity);

      verify(unitEntityRepository).save(unitUpdatedEntity);
    }

    @Test
    void shouldSaveUpdatedPatient() {
      final var patientEntity = athenaReactAnderssonEntityBuilder().build();
      final var patientUpdatedEntity = athenaReactAnderssonEntityBuilder()
          .firstName("Updated Name")
          .build();

      metadataVersionRepository.savePatientVersion(patientEntity, patientUpdatedEntity);

      verify(patientEntityRepository).save(patientUpdatedEntity);
    }

    @Test
    void shouldSaveUpdatedStaffVersion() {
      final var ajlaEntity = ajlaDoctorEntityBuilder().build();
      final var ajlaUpdatedEntity = ajlaDoctorEntityBuilder()
          .firstName("Maria")
          .build();

      metadataVersionRepository.saveStaffVersion(ajlaEntity, ajlaUpdatedEntity);

      verify(staffVersionEntityRepository).save(argThat(saved ->
          saved.getHsaId().equals(AJLA_DOKTOR_VERSION_ENTITY.getHsaId()) &&
              saved.getFirstName().equals(AJLA_DOKTOR_VERSION_ENTITY.getFirstName()) &&
              saved.getLastName().equals(AJLA_DOKTOR_VERSION_ENTITY.getLastName()) &&
              saved.getMiddleName().equals(AJLA_DOKTOR_VERSION_ENTITY.getMiddleName())
      ));
    }

    @Test
    void shouldSaveUpdatedUnitVersion() {
      final var unitEntity = alfaMedicinCentrumEntityBuilder().build();
      final var unitUpdatedEntity = alfaMedicinCentrumEntityBuilder()
          .name("Updated Name")
          .build();

      metadataVersionRepository.saveUnitVersion(unitEntity, unitUpdatedEntity);

      verify(unitVersionEntityRepository).save(argThat(saved ->
          saved.getHsaId().equals(ALFA_MEDICINCENTRUM_VERSION_ENTITY.getHsaId()) &&
              saved.getName().equals(ALFA_MEDICINCENTRUM_VERSION_ENTITY.getName()) &&
              saved.getAddress().equals(ALFA_MEDICINCENTRUM_VERSION_ENTITY.getAddress()) &&
              saved.getZipCode().equals(ALFA_MEDICINCENTRUM_VERSION_ENTITY.getZipCode()) &&
              saved.getCity().equals(ALFA_MEDICINCENTRUM_VERSION_ENTITY.getCity()) &&
              saved.getPhoneNumber().equals(ALFA_MEDICINCENTRUM_VERSION_ENTITY.getPhoneNumber()) &&
              saved.getEmail().equals(ALFA_MEDICINCENTRUM_VERSION_ENTITY.getEmail()) &&
              saved.getWorkplaceCode().equals(ALFA_MEDICINCENTRUM_VERSION_ENTITY.getWorkplaceCode())
      ));
    }

    @Test
    void shouldSaveUpdatedPatientVersion() {
      final var patientEntity = athenaReactAnderssonEntityBuilder().build();
      final var patientUpdatedEntity = athenaReactAnderssonEntityBuilder()
          .firstName("Updated Name")
          .build();

      metadataVersionRepository.savePatientVersion(patientEntity, patientUpdatedEntity);

      verify(patientVersionEntityRepository).save(argThat(saved ->
          saved.getId().equals(ATHENA_REACT_ANDERSSON_VERSION_ENTITY.getId()) &&
              saved.getFirstName().equals(ATHENA_REACT_ANDERSSON_VERSION_ENTITY.getFirstName()) &&
              saved.getLastName().equals(ATHENA_REACT_ANDERSSON_VERSION_ENTITY.getLastName()) &&
              saved.getMiddleName().equals(ATHENA_REACT_ANDERSSON_VERSION_ENTITY.getMiddleName()) &&
              saved.isProtectedPerson() == ATHENA_REACT_ANDERSSON_VERSION_ENTITY.isProtectedPerson()
              &&
              saved.isTestIndicated() == ATHENA_REACT_ANDERSSON_VERSION_ENTITY.isTestIndicated() &&
              saved.isDeceased() == ATHENA_REACT_ANDERSSON_VERSION_ENTITY.isDeceased()
      ));
    }
  }
}