package se.inera.intyg.cts.domain.model;

public class Export {

  private final OrganizationRepresentative organizationRepresentative;
  private CertificateSummary certificateSummary;
  private Password password;

  Export(OrganizationRepresentative organizationRepresentative) {
    this(organizationRepresentative, new CertificateSummary(0, 0), null);
  }

  Export(OrganizationRepresentative organizationRepresentative,
      CertificateSummary certificateSummary, Password password) {
    if (organizationRepresentative == null) {
      throw new IllegalArgumentException("Missing OrganisationalRepresentative");
    }
    if (certificateSummary == null) {
      throw new IllegalArgumentException("Missing CertificateSummary");
    }
    this.organizationRepresentative = organizationRepresentative;
    this.certificateSummary = certificateSummary;
    this.password = password;
  }

  public void processBatch(CertificateBatch certificateBatch) {
    final var total = certificateBatch.certificateList().size();
    final var revokedCount = (int) certificateBatch.certificateList().stream()
        .filter(Certificate::revoked)
        .count();

    certificateSummary = certificateSummary.add(new CertificateSummary(total, revokedCount));
  }

  public void packagePassword(Password password) {
    this.password = password;
  }

  public OrganizationRepresentative organizationRepresentative() {
    return organizationRepresentative;
  }

  public CertificateSummary certificateSummary() {
    return certificateSummary;
  }

  public Password password() {
    return password;
  }

  @Override
  public String toString() {
    return "Export{" +
        "organizationRepresentative=" + organizationRepresentative +
        ", certificateSummary=" + certificateSummary +
        ", password=" + password +
        '}';
  }
}
