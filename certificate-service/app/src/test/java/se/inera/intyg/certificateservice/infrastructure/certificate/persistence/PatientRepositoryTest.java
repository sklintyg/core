package se.inera.intyg.certificateservice.infrastructure.certificate.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static se.inera.intyg.certificateservice.application.testdata.TestDataPatientEntity.ATHENA_REACT_ANDERSSON_ENTITY;
import static se.inera.intyg.certificateservice.application.testdata.TestDataPatientEntity.athenaReactAnderssonEntityBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.ATHENA_REACT_ANDERSSON;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_ID_WITHOUT_DASH;

import jakarta.persistence.EntityManager;
import jakarta.persistence.OptimisticLockException;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.PatientEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.PatientVersionEntityRepository;

@ExtendWith(MockitoExtension.class)
class PatientRepositoryTest {

  @Mock
  private PatientEntityRepository patientEntityRepository;
  @Mock
  private PatientVersionEntityRepository patientVersionEntityRepository;
  @Mock
  MetadataVersionRepository metadataVersionRepository;
  @Mock
  EntityManager entityManager;
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

  @Nested
  class UpdateEntitiesTest {

    @Test
    void shallReturnUpdatedEntityIfOptimisticLockIsThrown() {

      final var updatedAthena = athenaReactAnderssonEntityBuilder()
          .middleName("test")
          .build();

      doReturn(Optional.of(updatedAthena)).when(patientEntityRepository).findById(
          ATHENA_REACT_ANDERSSON_ID_WITHOUT_DASH);
      doThrow(OptimisticLockException.class).when(metadataVersionRepository)
          .savePatientVersion(updatedAthena, ATHENA_REACT_ANDERSSON_ENTITY);

      assertThrows(OptimisticLockException.class,
          () -> patientRepository.patient(ATHENA_REACT_ANDERSSON));
    }
  }
}