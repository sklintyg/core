package se.inera.intyg.certificateservice.certificate;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.common.dto.PatientDTO;
import se.inera.intyg.certificateservice.application.common.dto.PersonIdDTO;
import se.inera.intyg.certificateservice.certificate.integration.IPSIntegrationService;
import se.inera.intyg.certificateservice.infrastructure.patient.PatientInformationProvider;

@Service
@RequiredArgsConstructor
public class PatientInformationService implements PatientInformationProvider {

  private final IPSIntegrationService ipsIntegrationService;

  @Override
  public PatientDTO findPatient(PersonIdDTO personId) {
    return null;
  }

  @Override
  public List<PatientDTO> findPatients(List<PersonIdDTO> personIds) {
    return List.of();
  }
}