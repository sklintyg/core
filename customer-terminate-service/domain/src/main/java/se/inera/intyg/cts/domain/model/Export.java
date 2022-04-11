package se.inera.intyg.cts.domain.model;

public class Export {

  private final OrganisationalRepresentative organisationalRepresentative;

  Export(OrganisationalRepresentative organisationalRepresentative) {
    if (organisationalRepresentative == null) {
      throw new IllegalArgumentException("Missing OrganisationalRepresentative");
    }
    this.organisationalRepresentative = organisationalRepresentative;
  }

  public OrganisationalRepresentative organisationalRepresentative() {
    return organisationalRepresentative;
  }

  @Override
  public String toString() {
    return "Export{" +
        "organisationalRepresentative=" + organisationalRepresentative +
        '}';
  }
}
