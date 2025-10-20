package se.inera.intyg.certificateservice.infrastructure.certificate.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static se.inera.intyg.certificateservice.application.testdata.TestDataPatientEntity.ATHENA_REACT_ANDERSSON_ENTITY;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.ATHENA_REACT_ANDERSSON;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_ID_WITHOUT_DASH;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.PatientEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.PatientEntityRepository;

@ExtendWith(MockitoExtension.class)
class PatientRepositoryTest {

  @Mock
  private PatientEntityRepository patientEntityRepository;
  @Mock
  MetadataVersionRepository metadataVersionRepository;
  @InjectMocks
  private PatientRepository patientRepository;


  @Test
  void shallReturnEntityFromRepositoryIfExists() {
    doReturn(Optional.of(ATHENA_REACT_ANDERSSON_ENTITY))
        .when(patientEntityRepository).findById(ATHENA_REACT_ANDERSSON_ID_WITHOUT_DASH);
    assertEquals(ATHENA_REACT_ANDERSSON_ENTITY, patientRepository.patient(ATHENA_REACT_ANDERSSON)
    );
  }

  @Test
  void shallReturnMappedEntityIfEntityDontExistInRepository() {
    doReturn(Optional.empty())
        .when(patientEntityRepository).findById(ATHENA_REACT_ANDERSSON_ID_WITHOUT_DASH);
    doReturn(ATHENA_REACT_ANDERSSON_ENTITY)
        .when(patientEntityRepository).save(ATHENA_REACT_ANDERSSON_ENTITY);

    assertEquals(ATHENA_REACT_ANDERSSON_ENTITY,
        patientRepository.patient(ATHENA_REACT_ANDERSSON)
    );
  }

  @Test
  void shallUpdatePatientWhenPatientHasDifferences() {

    final var updatedPatientEntity = mock(PatientEntity.class);
    final var existingPatientEntity = mock(PatientEntity.class);

    doReturn(Optional.of(existingPatientEntity))
        .when(patientEntityRepository).findById(ATHENA_REACT_ANDERSSON_ID_WITHOUT_DASH);
    when(existingPatientEntity.hasDiff(ATHENA_REACT_ANDERSSON_ENTITY)).thenReturn(true);
    doReturn(updatedPatientEntity)
        .when(metadataVersionRepository)
        .savePatientVersion(existingPatientEntity, ATHENA_REACT_ANDERSSON_ENTITY);
    final var result = patientRepository.patient(ATHENA_REACT_ANDERSSON);

    assertEquals(updatedPatientEntity, result);
    verify(metadataVersionRepository).savePatientVersion(existingPatientEntity,
        ATHENA_REACT_ANDERSSON_ENTITY);
  }

  @Test
  void shallReturnExistingPatientEntityWhenNoDifferences() {
    final var existingPatientEntity = mock(PatientEntity.class);

    doReturn(Optional.of(existingPatientEntity))
        .when(patientEntityRepository).findById(ATHENA_REACT_ANDERSSON_ID_WITHOUT_DASH);
    doReturn(false).when(existingPatientEntity).hasDiff(ATHENA_REACT_ANDERSSON_ENTITY);

    final var result = patientRepository.patient(ATHENA_REACT_ANDERSSON);

    assertEquals(existingPatientEntity, result);
    verify(metadataVersionRepository, never()).savePatientVersion(existingPatientEntity,
        ATHENA_REACT_ANDERSSON_ENTITY);
  }
}