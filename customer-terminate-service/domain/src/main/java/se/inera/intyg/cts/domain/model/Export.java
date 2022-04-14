package se.inera.intyg.cts.domain.model;

public class Export {

  private final OrganizationRepresentative organizationRepresentative;

  Export(OrganizationRepresentative organizationRepresentative) {
    if (organizationRepresentative == null) {
      throw new IllegalArgumentException("Missing OrganizationRepresentative");
    }
    this.organizationRepresentative = organizationRepresentative;
  }

  public OrganizationRepresentative organizationRepresentative() {
    return organizationRepresentative;
  }

  @Override
  public String toString() {
    return "Export{" +
        "organizationRepresentative=" + organizationRepresentative +
        '}';
  }
}
