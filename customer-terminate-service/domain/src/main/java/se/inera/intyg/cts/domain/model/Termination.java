package se.inera.intyg.cts.domain.model;

import java.time.LocalDateTime;

public class Termination {

  private final TerminationId terminationId;
  private final LocalDateTime created;
  private final Staff creator;
  private final CareProvider careProvider;
  private final TerminationStatus status;
  private final Export export;

  Termination(TerminationId terminationId, LocalDateTime created, Staff creator,
      CareProvider careProvider, TerminationStatus status, Export export) {
    if (terminationId == null) {
      throw new IllegalArgumentException("Missing TerminationId");
    }
    if (created == null) {
      throw new IllegalArgumentException("Missing Created");
    }
    if (creator == null) {
      throw new IllegalArgumentException("Missing Creator");
    }
    if (careProvider == null) {
      throw new IllegalArgumentException("Missing CareProvider");
    }
    if (status == null) {
      throw new IllegalArgumentException("Missing Status");
    }
    if (export == null) {
      throw new IllegalArgumentException("Missing Export");
    }
    this.terminationId = terminationId;
    this.created = created;
    this.creator = creator;
    this.careProvider = careProvider;
    this.status = status;
    this.export = export;
  }

  public TerminationId terminationId() {
    return terminationId;
  }

  public LocalDateTime created() {
    return created;
  }

  public Staff creator() {
    return creator;
  }

  public CareProvider careProvider() {
    return careProvider;
  }

  public TerminationStatus status() {
    return status;
  }

  public Export export() {
    return export;
  }

  @Override
  public String toString() {
    return "Termination{" +
        "id=" + terminationId +
        ", careProvider=" + careProvider +
        ", export=" + export +
        '}';
  }
}
