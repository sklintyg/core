package se.inera.intyg.cts.application.service;

import static se.inera.intyg.cts.application.dto.TerminationDTOMapper.toDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;
import se.inera.intyg.cts.application.dto.CreateTerminationDTO;
import se.inera.intyg.cts.application.dto.TerminationDTO;
import se.inera.intyg.cts.application.dto.TerminationDTOMapper;
import se.inera.intyg.cts.domain.model.Termination;
import se.inera.intyg.cts.domain.model.TerminationBuilder;
import se.inera.intyg.cts.domain.model.TerminationId;
import se.inera.intyg.cts.domain.model.TerminationStatus;
import se.inera.intyg.cts.domain.repository.TerminationRepository;
import se.inera.intyg.cts.domain.service.SendPackagePassword;

@Service
public class TerminationServiceImpl implements TerminationService {

  private static final Logger LOG = LoggerFactory.getLogger(TerminationServiceImpl.class);

  private final TerminationRepository terminationRepository;
  private final SendPackagePassword sendPackagePassword;

  public TerminationServiceImpl(TerminationRepository terminationRepository, SendPackagePassword sendPackagePassword) {
    this.terminationRepository = terminationRepository;
    this.sendPackagePassword = sendPackagePassword;
  }

  @Override
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

  @Override
  @Transactional
  public Optional<TerminationDTO> findById(UUID terminationId) {
    final var termination = terminationRepository.findByTerminationId(
        new TerminationId(terminationId));

    return termination.map(TerminationDTOMapper::toDTO);
  }

  @Override
  @Transactional
  public List<TerminationDTO> findAll() {
    return terminationRepository.findAll().stream()
        .map(TerminationDTOMapper::toDTO)
        .collect(Collectors.toList());
  }

  /**
   * Resend password if all criteria are fulfilled
   * @param terminationId
   * @return
   * @throws NotFoundException
   */
  @Override
  @Transactional
  public TerminationDTO resendPassword(UUID terminationId) throws NotFoundException {
    Termination termination = terminationRepository.findByTerminationId(new TerminationId(terminationId)).orElseThrow(
        () -> new NotFoundException()
    );
    if(termination.status().equals(TerminationStatus.PASSWORD_SENT) || termination.status().equals(TerminationStatus.PASSWORD_RESENT)){
      sendPackagePassword.resendPassword(termination);
      return TerminationDTOMapper.toDTO(termination);
    }
    throw new IllegalArgumentException(String.format("Invalid status: %s to resend password.", terminationId));
  }
}
