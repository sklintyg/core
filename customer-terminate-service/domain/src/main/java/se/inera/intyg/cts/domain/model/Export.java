package se.inera.intyg.cts.domain.model;

import java.time.LocalDateTime;

public class Export {

  private OrganizationRepresentative organizationRepresentative;
  private CertificateSummary certificateSummary;
  private Password password;
  private LocalDateTime receiptTime;

  Export(OrganizationRepresentative organizationRepresentative) {
    this(organizationRepresentative, new CertificateSummary(0, 0), null, null);
  }

  Export(OrganizationRepresentative organizationRepresentative,
      CertificateSummary certificateSummary, Password password, LocalDateTime receiptTime) {
    if (organizationRepresentative == null) {
      throw new IllegalArgumentException("Missing OrganisationalRepresentative");
    }
    if (certificateSummary == null) {
      throw new IllegalArgumentException("Missing CertificateSummary");
    }
    this.organizationRepresentative = organizationRepresentative;
    this.certificateSummary = certificateSummary;
    this.password = password;
    this.receiptTime = receiptTime;
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

  public LocalDateTime receiptTime() {
    return receiptTime;
  }

  public void receiptTime(LocalDateTime receiptTime) {
    this.receiptTime = receiptTime;
  }

  public void update(OrganizationRepresentative organizationRepresentative) {
    this.organizationRepresentative = organizationRepresentative;
  }

  public void reset() {
    this.password = null;
    this.certificateSummary = new CertificateSummary(0, 0);
  }

  @Override
  public String toString() {
    return "Export{" +
        "organizationRepresentative=" + organizationRepresentative +
        ", certificateSummary=" + certificateSummary +
        ", password=" + password +
        ", receiptTime=" + receiptTime +
        '}';
  }
}
