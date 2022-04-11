package se.inera.intyg.cts.domain.repository;

import java.util.List;
import java.util.Optional;
import se.inera.intyg.cts.domain.model.Termination;
import se.inera.intyg.cts.domain.model.TerminationId;

public interface TerminationRepository {

  Termination store(Termination termination);

  Optional<Termination> findByTerminationId(TerminationId id);

  List<Termination> findAll();
}
