package se.inera.intyg.cts.application.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import se.inera.intyg.cts.application.dto.CreateTerminationDTO;
import se.inera.intyg.cts.application.dto.TerminationDTO;

public interface TerminationService {

    TerminationDTO create(CreateTerminationDTO createTerminationDTO);

    Optional<TerminationDTO> findById(UUID terminationId);

    List<TerminationDTO> findAll();

    TerminationDTO resendPassword(UUID terminationId);
  @Transactional
  public TerminationDTO update(UUID terminationId, UpdateTerminationDTO updateTerminationDTO) {
    final var termination = terminationRepository.findByTerminationId(
        new TerminationId(terminationId)).orElseThrow(
        () -> new IllegalArgumentException(
            String.format("TerminationId '%s' doesn't exist!", terminationId)
        )
    );

    final var updatedTermination = updateTermination.update(
        termination,
        new HSAId(updateTerminationDTO.hsaId()),
        new PersonId(updateTerminationDTO.personId()),
        new EmailAddress(updateTerminationDTO.emailAddress()),
        new PhoneNumber(updateTerminationDTO.phoneNumber())
    );

    return toDTO(updatedTermination);
  }

  @Transactional
  public Optional<TerminationDTO> findById(UUID terminationId) {
    final var termination = terminationRepository.findByTerminationId(
        new TerminationId(terminationId));

    return termination.map(TerminationDTOMapper::toDTO);
  }

  @Transactional
  public List<TerminationDTO> findAll() {
    return terminationRepository.findAll().stream()
        .map(TerminationDTOMapper::toDTO)
        .collect(Collectors.toList());
  }
}
