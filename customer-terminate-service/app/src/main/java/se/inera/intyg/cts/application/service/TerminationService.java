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
import se.inera.intyg.cts.domain.model.EmailAddress;
import se.inera.intyg.cts.domain.model.HSAId;
import se.inera.intyg.cts.domain.model.PersonId;
import se.inera.intyg.cts.domain.model.PhoneNumber;
import se.inera.intyg.cts.domain.model.TerminationBuilder;
import se.inera.intyg.cts.domain.model.TerminationId;
import se.inera.intyg.cts.domain.repository.TerminationRepository;
import se.inera.intyg.cts.domain.service.UpdateTermination;

@Service
public class TerminationService {

  private static final Logger LOG = LoggerFactory.getLogger(TerminationService.class);

  private final TerminationRepository terminationRepository;
  private final UpdateTermination updateTermination;

  public TerminationService(TerminationRepository terminationRepository,
      UpdateTermination updateTermination) {
    this.terminationRepository = terminationRepository;
    this.updateTermination = updateTermination;
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
        .careProviderOrganizationRepresentativeEmailAddress(createTerminationDTO.emailAddress())
        .create();

    final var createdTermination = terminationRepository.store(termination);
    LOG.info("Created termination with id '{}' for care provider '{}'",
        createdTermination.terminationId().id(),
        createdTermination.careProvider().hsaId().id()
    );

    return toDTO(createdTermination);
  }

  public TerminationDTO update(UUID terminationId, TerminationDTO terminationDTO) {
    if (!terminationId.equals(terminationDTO.terminationId())) {
      throw new IllegalArgumentException(
          String.format("TerminationId '%s' doesn't match the id passed in termination '%s'",
              terminationId, terminationDTO.terminationId())
      );
    }

    final var termination = terminationRepository.findByTerminationId(
        new TerminationId(terminationId)).orElseThrow(
        () -> new IllegalArgumentException(
            String.format("TerminationId '%s' doesn't exist!", terminationId)
        )
    );

    final var updatedTermination = updateTermination.update(
        termination,
        new HSAId(terminationDTO.hsaId()),
        new PersonId(terminationDTO.personId()),
        new EmailAddress(terminationDTO.emailAddress()),
        new PhoneNumber(terminationDTO.phoneNumber())
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
