package se.inera.intyg.cts.domain.repository;

import se.inera.intyg.cts.domain.model.Termination;
import se.inera.intyg.cts.domain.model.TerminationId;

public interface TerminationRepository {

  Termination getById(TerminationId id);
}
