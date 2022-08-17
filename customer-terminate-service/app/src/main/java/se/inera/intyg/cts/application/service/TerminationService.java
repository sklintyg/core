package se.inera.intyg.cts.application.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import se.inera.intyg.cts.application.dto.CreateTerminationDTO;
import se.inera.intyg.cts.application.dto.TerminationDTO;

public interface TerminationService {

    TerminationDTO create(CreateTerminationDTO createTerminationDTO);

    Optional<TerminationDTO> findById(UUID terminationId);

    List<TerminationDTO> findAll();

    TerminationDTO resendPassword(UUID terminationId) throws NotFoundException, IllegalArgumentException;
}
