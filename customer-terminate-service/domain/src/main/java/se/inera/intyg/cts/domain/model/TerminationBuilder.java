package se.inera.intyg.cts.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class TerminationBuilder {

  private UUID terminationId;
  private LocalDateTime created;
  private String creatorHSAId;
  private String creatorName;
  private String careProviderHSAId;
  private String careProviderOrganizationNumber;
  private String careProviderOrganizationRepresentativePersonId;
  private String careProviderOrganizationRepresentativePhoneNumber;
  private TerminationStatus status;

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

  public TerminationBuilder careProviderOrganizationNumber(String organizationNumber) {
    this.careProviderOrganizationNumber = organizationNumber;
    return this;
  }

  public TerminationBuilder careProviderOrganizationRepresentativePersonId(String personId) {
    this.careProviderOrganizationRepresentativePersonId = personId;
    return this;
  }

  public TerminationBuilder careProviderOrganizationRepresentativePhoneNumber(
      String phoneNumber) {
    this.careProviderOrganizationRepresentativePhoneNumber = phoneNumber;
    return this;
  }

  public TerminationBuilder status(TerminationStatus status) {
    this.status = status;
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
    final var organizationNumber = new OrganizationNumber(careProviderOrganizationNumber);
    final var personId = new PersonId(careProviderOrganizationRepresentativePersonId);
    final var phoneNumber = new PhoneNumber(careProviderOrganizationRepresentativePhoneNumber);

    return new Termination(
        new TerminationId(terminationId),
        created,
        creator,
        new CareProvider(hsaId, organizationNumber),
        status,
        new Export(
            new OrganizationRepresentative(personId, phoneNumber)
        )
    );
  }
}