package se.inera.intyg.cts.domain.model;

public class Termination {

  private final TerminationId id;
  private final CareProvider careProvider;
  private final Export export;

  Termination(TerminationId id, CareProvider careProvider, Export export) {
    this.id = id;
    this.careProvider = careProvider;
    this.export = export;
  }

  public TerminationId id() {
    return id;
  }

  public CareProvider careProvider() {
    return careProvider;
  }

  public Export export() {
    return export;
  }

  @Override
  public String toString() {
    return "Termination{" +
        "id=" + id +
        ", careProvider=" + careProvider +
        ", export=" + export +
        '}';
  }
}
