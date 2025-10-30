package se.inera.intyg.certificateservice.infrastructure.patient;

import java.util.List;
import se.inera.intyg.certificateservice.application.common.dto.PatientDTO;
import se.inera.intyg.certificateservice.application.common.dto.PersonIdDTO;

public interface PatientInformationProvider {

  PatientDTO findPatient(PersonIdDTO personId);

  List<PatientDTO> findPatients(List<PersonIdDTO> personIds);
}