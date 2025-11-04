package se.inera.intyg.certificateservice.patient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.common.model.PersonId;
import se.inera.intyg.certificateservice.domain.patient.model.Patient;
import se.inera.intyg.certificateservice.patient.service.FindPatientsService;

@ExtendWith(MockitoExtension.class)
class PatientInformationServiceTest {

  @Mock
  FindPatientsService findPatientsService;
  @InjectMocks
  PatientInformationService patientInformationService;

  @Nested
  class FindPersonTests {

    @Test
    void shouldReturnOptionalOfPatient() {
      final var personId = PersonId.builder().build();
      final var patient = Patient.builder().build();
      final var expectedResult = Optional.of(patient);

      when(findPatientsService.find(List.of(personId))).thenReturn(List.of(patient));

      final var result = patientInformationService.findPatient(personId);
      assertEquals(expectedResult, result);
    }

    @Test
    void shouldReturnOptionalEmptyIfPatientIsNotFound() {
      final var personId = PersonId.builder().build();

      when(findPatientsService.find(List.of(personId))).thenReturn(Collections.emptyList());

      final var result = patientInformationService.findPatient(personId);
      assertTrue(result.isEmpty());
    }
  }

  @Nested
  class FindPersonsTests {

    @Test
    void shouldReturnListOfPatient() {
      final var personId = PersonId.builder().build();
      final var patient = Patient.builder().build();
      final var expectedResult = List.of(patient);
      final var personIds = List.of(personId);

      when(findPatientsService.find(personIds)).thenReturn(List.of(patient));

      final var result = patientInformationService.findPatients(personIds);
      assertEquals(expectedResult, result);
    }

    @Test
    void shouldReturnEmptyListIfPatientIsNotFound() {
      final var personId = PersonId.builder().build();
      final var personIds = List.of(personId);

      when(findPatientsService.find(personIds)).thenReturn(Collections.emptyList());

      final var result = patientInformationService.findPatients(personIds);
      assertTrue(result.isEmpty());
    }
  }
}