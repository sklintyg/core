package se.inera.intyg.certificateservice.patient.converter;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.patient.model.Patient;
import se.inera.intyg.certificateservice.patient.dto.PersonDTO;

@Component
public class PatientConverter {

  public Patient convert(PersonDTO person) {
    return null;
  }
}