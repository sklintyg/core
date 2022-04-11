package se.inera.intyg.cts.application.service;

import static se.inera.intyg.cts.application.dto.TerminationDTOMapper.toDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
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
        .careProviderOrganizationalNumber(createTerminationDTO.organizationalNumber())
        .careProviderOrganisationalRepresentativePersonId(createTerminationDTO.personId())
        .careProviderOrganisationalRepresentativePhoneNumber(createTerminationDTO.phoneNumber())
        .create();

    final var createdTermination = terminationRepository.store(termination);

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
