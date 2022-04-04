package se.inera.intyg.cts.domain.model;

import java.util.Objects;

public record PersonId(String id) {

  public PersonId {
    Objects.requireNonNull(id, "Missing PersonId");
  }
}
