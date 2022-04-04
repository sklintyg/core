package se.inera.intyg.cts.domain.model;

import java.util.Objects;

public record TerminationId(String id) {

  public TerminationId {
    Objects.requireNonNull(id, "Missing terminationId");
  }
}
