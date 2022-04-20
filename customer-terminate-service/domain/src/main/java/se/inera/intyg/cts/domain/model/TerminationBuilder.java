package se.inera.intyg.cts.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class TerminationBuilder {

  private UUID terminationId;
  private LocalDateTime created;
  private String creatorHSAId;
  private String creatorName;
  private String careProviderHSAId;
  private String careProviderOrganizationalNumber;
  private String careProviderOrganisationalRepresentativePersonId;
  private String careProviderOrganisationalRepresentativePhoneNumber;
  private TerminationStatus status;
  private int total;
  private int revoked;
  private String packagePassword;

  public static TerminationBuilder getInstance() {
    return new TerminationBuilder();
  }

  public TerminationBuilder terminationId(UUID terminationId) {
    this.terminationId = terminationId;
    return this;
  }

  public TerminationBuilder created(LocalDateTime created) {
    this.created = created;
    return this;
  }

  public TerminationBuilder creatorHSAId(String creatorHSAId) {
    this.creatorHSAId = creatorHSAId;
    return this;
  }

  public TerminationBuilder creatorName(String creatorName) {
    this.creatorName = creatorName;
    return this;
  }

  public TerminationBuilder careProviderHSAId(String hsaId) {
    this.careProviderHSAId = hsaId;
    return this;
  }

  public TerminationBuilder careProviderOrganizationalNumber(String organizationalNumber) {
    this.careProviderOrganizationalNumber = organizationalNumber;
    return this;
  }

  public TerminationBuilder careProviderOrganisationalRepresentativePersonId(String personId) {
    this.careProviderOrganisationalRepresentativePersonId = personId;
    return this;
  }

  public TerminationBuilder careProviderOrganisationalRepresentativePhoneNumber(
      String phoneNumber) {
    this.careProviderOrganisationalRepresentativePhoneNumber = phoneNumber;
    return this;
  }

  public TerminationBuilder status(TerminationStatus status) {
    this.status = status;
    return this;
  }

  public TerminationBuilder total(int total) {
    this.total = total;
    return this;
  }
  
  public TerminationBuilder revoked(int revoked) {
    this.revoked = revoked;
    return this;
  }

  public TerminationBuilder packagePassword(String packagePassword) {
    this.packagePassword = packagePassword;
    return this;
  }

  public Termination create() {
    if (terminationId == null) {
      terminationId = UUID.randomUUID();
      created = LocalDateTime.now();
      status = TerminationStatus.CREATED;
    }

    final var creator = new Staff(new HSAId(creatorHSAId), creatorName);
    final var hsaId = new HSAId(careProviderHSAId);
    final var organisationalNumber = new OrganisationalNumber(careProviderOrganizationalNumber);
    final var personId = new PersonId(careProviderOrganisationalRepresentativePersonId);
    final var phoneNumber = new PhoneNumber(careProviderOrganisationalRepresentativePhoneNumber);

    return new Termination(
        new TerminationId(terminationId),
        created,
        creator,
        new CareProvider(hsaId, organisationalNumber),
        status,
        new Export(
            new OrganisationalRepresentative(personId, phoneNumber),
            new CertificateSummary(total, revoked),
            packagePassword != null ? new Password(packagePassword) : null
        )
    );
  }
}