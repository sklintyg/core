package se.inera.intyg.cts.domain.model;

import java.util.UUID;

public class TerminationBuilder {

  private String careProviderHSAId;
  private String careProviderOrganizationalNumber;
  private String careProviderOrganisationalRepresentativePersonId;
  private String careProviderOrganisationalRepresentativePhoneNumber;

  public static TerminationBuilder getInstance() {
    return new TerminationBuilder();
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

  public Termination createTermination() {
    final var hsaId = new HSAId(careProviderHSAId);
    final var organisationalNumber = new OrganisationalNumber(careProviderOrganizationalNumber);
    final var personId = new PersonId(careProviderOrganisationalRepresentativePersonId);
    final var phoneNumber = new PhoneNumber(careProviderOrganisationalRepresentativePhoneNumber);

    return new Termination(
        new TerminationId(UUID.randomUUID().toString()),
        new CareProvider(hsaId, organisationalNumber),
        new Export(
            new OrganisationalRepresentative(personId, phoneNumber)
        )
    );
  }
}