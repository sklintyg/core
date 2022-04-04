package se.inera.intyg.cts.domain.model;

import java.util.Objects;

public record HSAId(String id) {

  public HSAId {
    Objects.requireNonNull(id, "Missing HSAId");
  }
}
