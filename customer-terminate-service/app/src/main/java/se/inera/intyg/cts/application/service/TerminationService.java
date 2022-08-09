package se.inera.intyg.cts.application.service;

import static se.inera.intyg.cts.application.dto.TerminationDTOMapper.toDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.inera.intyg.cts.application.dto.CreateTerminationDTO;
import se.inera.intyg.cts.application.dto.TerminationDTO;
import se.inera.intyg.cts.application.dto.TerminationDTOMapper;
import se.inera.intyg.cts.domain.model.TerminationBuilder;
import se.inera.intyg.cts.domain.model.TerminationId;
import se.inera.intyg.cts.domain.repository.TerminationRepository;

@Service
public class TerminationService {

  private static final Logger LOG = LoggerFactory.getLogger(TerminationService.class);

  private final TerminationRepository terminationRepository;

  public TerminationService(TerminationRepository terminationRepository) {
    this.terminationRepository = terminationRepository;
  }

  @Transactional
  public TerminationDTO create(CreateTerminationDTO createTerminationDTO) {

    final var termination = TerminationBuilder.getInstance()
        .creatorHSAId(createTerminationDTO.creatorHSAId())
        .creatorName(createTerminationDTO.creatorName())
        .careProviderHSAId(createTerminationDTO.hsaId())
        .careProviderOrganizationNumber(createTerminationDTO.organizationNumber())
        .careProviderOrganizationRepresentativePersonId(createTerminationDTO.personId())
        .careProviderOrganizationRepresentativePhoneNumber(createTerminationDTO.phoneNumber())
        .create();

    final var createdTermination = terminationRepository.store(termination);
    LOG.info("Created termination with id '{}' for care provider '{}'",
        createdTermination.terminationId().id(),
        createdTermination.careProvider().hsaId().id()
    );

    return toDTO(createdTermination);
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
