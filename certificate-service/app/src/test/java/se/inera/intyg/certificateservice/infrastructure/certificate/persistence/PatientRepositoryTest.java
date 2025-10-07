package se.inera.intyg.certificateservice.infrastructure.certificate.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.argThat;
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

import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.PatientEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.PatientIdTypeEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.PersonEntityIdType;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.PatientEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.PatientVersionEntityRepository;

@ExtendWith(MockitoExtension.class)
class PatientRepositoryTest {

  @Mock
  private PatientEntityRepository patientEntityRepository;
	@Mock
	private PatientVersionEntityRepository patientVersionEntityRepository;
  @InjectMocks
  private PatientRepository patientRepository;

  private static final PatientEntity PATIENT_ENTITY = PatientEntity.builder()
      .id(ATHENA_REACT_ANDERSSON_ID_WITHOUT_DASH)
      .protectedPerson(ATHENA_REACT_ANDERSSON_PROTECTED_PERSON.value())
      .testIndicated(ATHENA_REACT_ANDERSSON_TEST_INDICATED.value())
      .deceased(ATHENA_REACT_ANDERSSON_DECEASED.value())
      .type(PatientIdTypeEntity.builder()
          .type(PersonEntityIdType.PERSONAL_IDENTITY_NUMBER.name())
          .key(PersonEntityIdType.PERSONAL_IDENTITY_NUMBER.getKey())
          .build())
      .firstName(ATHENA_REACT_ANDERSSON_FIRST_NAME)
      .middleName(ATHENA_REACT_ANDERSSON_MIDDLE_NAME)
      .lastName(ATHENA_REACT_ANDERSSON_LAST_NAME)
      .build();

  @Test
  void shallReturnEntityFromRepositoryIfExists() {
    doReturn(Optional.of(PATIENT_ENTITY))
        .when(patientEntityRepository).findById(ATHENA_REACT_ANDERSSON_ID_WITHOUT_DASH);
    assertEquals(PATIENT_ENTITY,
        patientRepository.patient(ATHENA_REACT_ANDERSSON)
    );
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

	@Nested
	class UpdateEntitiesTest {

		private static PatientEntity PATIENT_ENTITY;

		private static PatientEntity UPDATED_PATIENT_ENTITY;
		private static final String NEW_MIDDLE_NAME = "Angular";

		@BeforeEach
		void setup() {
			PATIENT_ENTITY = PatientEntity.builder()
					.id(ATHENA_REACT_ANDERSSON_ID_WITHOUT_DASH)
					.protectedPerson(ATHENA_REACT_ANDERSSON_PROTECTED_PERSON.value())
					.testIndicated(ATHENA_REACT_ANDERSSON_TEST_INDICATED.value())
					.deceased(ATHENA_REACT_ANDERSSON_DECEASED.value())
					.type(PatientIdTypeEntity.builder()
							.type(PersonEntityIdType.PERSONAL_IDENTITY_NUMBER.name())
							.key(PersonEntityIdType.PERSONAL_IDENTITY_NUMBER.getKey())
							.build())
					.firstName(ATHENA_REACT_ANDERSSON_FIRST_NAME)
					.middleName(ATHENA_REACT_ANDERSSON_MIDDLE_NAME)
					.lastName(ATHENA_REACT_ANDERSSON_LAST_NAME)
					.build();

			UPDATED_PATIENT_ENTITY = PatientEntity.builder()
					.id(ATHENA_REACT_ANDERSSON_ID_WITHOUT_DASH)
					.protectedPerson(ATHENA_REACT_ANDERSSON_PROTECTED_PERSON.value())
					.testIndicated(ATHENA_REACT_ANDERSSON_TEST_INDICATED.value())
					.deceased(ATHENA_REACT_ANDERSSON_DECEASED.value())
					.type(PatientIdTypeEntity.builder()
							.type(PersonEntityIdType.PERSONAL_IDENTITY_NUMBER.name())
							.key(PersonEntityIdType.PERSONAL_IDENTITY_NUMBER.getKey())
							.build())
					.firstName(ATHENA_REACT_ANDERSSON_FIRST_NAME)
					.middleName(NEW_MIDDLE_NAME)
					.lastName(ATHENA_REACT_ANDERSSON_LAST_NAME)
					.build();
		}

		@Test
		void shallSaveOldVersionToUnitVersionEntityRepository() {

			doReturn(Optional.of(UPDATED_PATIENT_ENTITY))
					.when(patientEntityRepository).findById(ATHENA_REACT_ANDERSSON_ID_WITHOUT_DASH);

			doReturn(PATIENT_ENTITY)
					.when(patientEntityRepository).save(Mockito.any(PatientEntity.class));

			doReturn(Collections.emptyList()).when(patientVersionEntityRepository)
					.findAllByIdOrderByValidFromDesc(ATHENA_REACT_ANDERSSON_ID_WITHOUT_DASH);
			patientRepository.patient(ATHENA_REACT_ANDERSSON);

			verify(patientVersionEntityRepository).save(
					argThat(savedVersion ->
							savedVersion.getId().equals(ATHENA_REACT_ANDERSSON_ID_WITHOUT_DASH) &&
									savedVersion.getMiddleName().equals(NEW_MIDDLE_NAME)));
		}

		@Test
		void shallUpdateEntityIfHsaChanged() {
			doReturn(Optional.of(UPDATED_PATIENT_ENTITY))
					.when(patientEntityRepository).findById(ATHENA_REACT_ANDERSSON_ID_WITHOUT_DASH);

			doReturn(PATIENT_ENTITY)
					.when(patientEntityRepository)
					.save(Mockito.any(PatientEntity.class));

			assertEquals(UPDATED_PATIENT_ENTITY,
					patientRepository.patient(ATHENA_REACT_ANDERSSON)
			);
		}
	}
}