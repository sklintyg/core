package se.inera.intyg.certificateservice.domain.patient.service;

import java.util.List;
import java.util.Optional;
import se.inera.intyg.certificateservice.domain.common.model.PersonId;
import se.inera.intyg.certificateservice.domain.patient.model.Patient;


public interface PatientInformationProvider {

  Optional<Patient> findPatient(PersonId personId);

  List<Patient> findPatients(List<PersonId> personIds);
}