package se.inera.intyg.certificateservice.patient;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.domain.common.model.PersonId;
import se.inera.intyg.certificateservice.domain.patient.model.Patient;
import se.inera.intyg.certificateservice.domain.patient.service.PatientInformationProvider;
import se.inera.intyg.certificateservice.patient.service.FindPatientsService;

@Service
@RequiredArgsConstructor
public class PatientInformationService implements PatientInformationProvider {

  private final FindPatientsService findPatientsService;

  @Override
  public Optional<Patient> findPatient(PersonId personId) {
    return findPatients(List.of(personId)).stream().findFirst();
  }

  @Override
  public List<Patient> findPatients(List<PersonId> personIds) {
    return findPatientsService.find(personIds);
  }
}