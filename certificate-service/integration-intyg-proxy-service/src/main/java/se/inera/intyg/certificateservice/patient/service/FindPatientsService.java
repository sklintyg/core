package se.inera.intyg.certificateservice.patient.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.domain.common.model.PersonId;
import se.inera.intyg.certificateservice.domain.patient.model.Patient;
import se.inera.intyg.certificateservice.patient.converter.PatientConverter;
import se.inera.intyg.certificateservice.patient.dto.PersonResponseDTO;
import se.inera.intyg.certificateservice.patient.dto.PersonsRequestDTO;
import se.inera.intyg.certificateservice.patient.dto.StatusDTOType;
import se.inera.intyg.certificateservice.patient.integration.IPSIntegrationService;

@Service
@RequiredArgsConstructor
public class FindPatientsService {

  private final IPSIntegrationService ipsIntegrationService;
  private final PatientConverter patientConverter;

  public List<Patient> find(List<PersonId> personIds) {
    final var personResponse = ipsIntegrationService.findPersons(
        PersonsRequestDTO.builder()
            .personIds(
                personIds.stream()
                    .map(PersonId::id)
                    .distinct()
                    .toList()
            )
            .build()
    );

    return personResponse.getPersons().stream()
        .filter(response -> response.getStatus().equals(StatusDTOType.FOUND))
        .map(PersonResponseDTO::getPerson)
        .map(patientConverter::convert)
        .toList();
  }
}