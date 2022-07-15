package se.inera.intyg.cts.application.service;

import static se.inera.intyg.cts.application.dto.TerminationDTOMapper.toDTO;

import java.util.Arrays;
import org.springframework.stereotype.Service;
import se.inera.intyg.cts.application.dto.TerminationDTO;
import se.inera.intyg.cts.domain.model.TerminationId;
import se.inera.intyg.cts.domain.model.TerminationStatus;
import se.inera.intyg.cts.domain.repository.TerminationRepository;
import se.inera.intyg.cts.domain.service.EraseDataForCareProvider;

@Service
public class EraseService {

  private final TerminationRepository terminationRepository;
  private final EraseDataForCareProvider eraseDataForCareProvider;

  public EraseService(TerminationRepository terminationRepository,
      EraseDataForCareProvider eraseDataForCareProvider) {
    this.terminationRepository = terminationRepository;
    this.eraseDataForCareProvider = eraseDataForCareProvider;
  }

  public void erase() {
    final var terminations = terminationRepository.findByStatuses(
        Arrays.asList(TerminationStatus.START_ERASE, TerminationStatus.ERASE_IN_PROGRESS)
    );

    terminations.forEach(
        termination -> eraseDataForCareProvider.erase(termination)
    );
  }

  public TerminationDTO initiateErase(TerminationId terminationId) {
    final var termination = terminationRepository.findByTerminationId(terminationId)
        .orElseThrow(() -> new IllegalArgumentException(
            String.format("Termination with id '%s' doesn't exists.", terminationId.id())
        ));

    termination.initiateErase();

    terminationRepository.store(termination);

    return toDTO(termination);
  }
}
