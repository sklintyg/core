package se.inera.intyg.cts.domain.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import se.inera.intyg.cts.domain.model.Termination;
import se.inera.intyg.cts.domain.model.TerminationId;

public class InMemoryTerminationRepository implements TerminationRepository {

  private final Map<TerminationId, Termination> terminationMap = new HashMap<>();

  @Override
  public Termination store(Termination termination) {
    return terminationMap.put(termination.terminationId(), termination);
  }

  @Override
  public Optional<Termination> findByTerminationId(TerminationId id) {
    return terminationMap.containsKey(id) ? Optional.of(terminationMap.get(id)) : Optional.empty();
  }

  @Override
  public List<Termination> findAll() {
    return terminationMap.values().stream().toList();
  }
}
