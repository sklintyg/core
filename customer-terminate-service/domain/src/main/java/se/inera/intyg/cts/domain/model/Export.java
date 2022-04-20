package se.inera.intyg.cts.domain.model;

public class Export {

  private final OrganisationalRepresentative organisationalRepresentative;
  private CertificateSummary certificateSummary;
  private Password password;

  Export(OrganisationalRepresentative organisationalRepresentative) {
    this(organisationalRepresentative, new CertificateSummary(0, 0), null);
  }

  Export(OrganisationalRepresentative organisationalRepresentative,
      CertificateSummary certificateSummary, Password password) {
    if (organisationalRepresentative == null) {
      throw new IllegalArgumentException("Missing OrganisationalRepresentative");
    }
    if (certificateSummary == null) {
      throw new IllegalArgumentException("Missing CertificateSummary");
    }
    this.organisationalRepresentative = organisationalRepresentative;
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

  public OrganisationalRepresentative organisationalRepresentative() {
    return organisationalRepresentative;
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
        "organisationalRepresentative=" + organisationalRepresentative +
        ", certificateSummary=" + certificateSummary +
        ", password=" + password +
        '}';
  }
}
