package se.inera.intyg.certificateservice.domain.certificate.model;

import java.util.List;

public enum Status {
  DRAFT, DELETED_DRAFT, SIGNED;

  public static List<Status> all() {
    return List.of(DRAFT);
  }
}
