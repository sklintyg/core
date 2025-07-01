package se.inera.intyg.certificateservice.infrastructure.certificate.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.ATHENA_REACT_ANDERSSON;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_DECEASED;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_FIRST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_ID_WITHOUT_DASH;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_LAST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_MIDDLE_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_PROTECTED_PERSON;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_TEST_INDICATED;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.PatientEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.PatientIdTypeEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.PersonEntityIdType;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.PatientEntityMapper;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.PatientEntityRepository;

@ExtendWith(MockitoExtension.class)
class PatientRepositoryTest {

  @Mock
  private PatientEntityRepository patientEntityRepository;
  @InjectMocks
  private PatientRepository patientRepository;

  private static final PatientEntity PATIENT_ENTITY = PatientEntity.builder()
      .id(ATHENA_REACT_ANDERSSON_ID_WITHOUT_DASH)
      .protectedPerson(ATHENA_REACT_ANDERSSON_PROTECTED_PERSON.value())
      .testIndicated(ATHENA_REACT_ANDERSSON_TEST_INDICATED.value())
      .deceased(ATHENA_REACT_ANDERSSON_DECEASED.value())
      .type(
          PatientIdTypeEntity.builder()
              .type(PersonEntityIdType.PERSONAL_IDENTITY_NUMBER.name())
              .key(PersonEntityIdType.PERSONAL_IDENTITY_NUMBER.getKey())
              .build()
      )
      .firstName(ATHENA_REACT_ANDERSSON_FIRST_NAME)
      .middleName(ATHENA_REACT_ANDERSSON_MIDDLE_NAME)
      .lastName(ATHENA_REACT_ANDERSSON_LAST_NAME)
      .build();

  @Test
  void shallReturnUpdatedEntityFromRepositoryIfExists() {
    doReturn(Optional.of(PATIENT_ENTITY))
        .when(patientEntityRepository).findById(ATHENA_REACT_ANDERSSON_ID_WITHOUT_DASH);
    doReturn(PATIENT_ENTITY)
        .when(patientEntityRepository).save(PATIENT_ENTITY);
    assertEquals(PATIENT_ENTITY,
        patientRepository.patient(ATHENA_REACT_ANDERSSON)
    );
  }

  @Test
  void shallUpdateEntityIfEntityExists() {
    try (MockedStatic<PatientEntityMapper> mockedMapper = Mockito.mockStatic(
        PatientEntityMapper.class)) {
      doReturn(Optional.of(PATIENT_ENTITY))
          .when(patientEntityRepository).findById(ATHENA_REACT_ANDERSSON_ID_WITHOUT_DASH);

      mockedMapper
          .when(() -> PatientEntityMapper.updateEntity(PATIENT_ENTITY, ATHENA_REACT_ANDERSSON))
          .thenReturn(PATIENT_ENTITY);

      patientRepository.patient(ATHENA_REACT_ANDERSSON);

      mockedMapper.verify(
          () -> PatientEntityMapper.updateEntity(PATIENT_ENTITY, ATHENA_REACT_ANDERSSON));
      verify(patientEntityRepository).save(PATIENT_ENTITY);
    }
  }

  @Test
  void shallReturnMappedEntityIfEntityDontExistInRepository() {
    doReturn(Optional.empty())
        .when(patientEntityRepository).findById(ATHENA_REACT_ANDERSSON_ID_WITHOUT_DASH);
    doReturn(PATIENT_ENTITY)
        .when(patientEntityRepository).save(PATIENT_ENTITY);

    assertEquals(PATIENT_ENTITY,
        patientRepository.patient(ATHENA_REACT_ANDERSSON)
    );
  }
}