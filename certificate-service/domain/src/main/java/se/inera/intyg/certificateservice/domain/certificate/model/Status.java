package se.inera.intyg.certificateservice.domain.certificate.model;

import java.util.List;

public enum Status {
  DRAFT, DELETED_DRAFT, LOCKED_DRAFT, SIGNED, REVOKED;

  public static List<Status> unsigned() {
    return List.of(DRAFT, LOCKED_DRAFT);
  }
}
