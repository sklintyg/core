package se.inera.intyg.cts.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TerminationBuilderTest {

  public static final String HSA_ID = "hsaId";
  public static final String ORGANIZATIONAL_NUMBER = "organizationalNumber";
  public static final String PERSON_ID = "personId";
  public static final String PHONE_NUMBER = "phoneNumber";

  @Test
  void shallCreateTerminationWithTerminationId() {
    assertNotNull(termination().id(), "Expect Termination to have an id");
    System.out.println(termination().toString());
  }

  @Test
  void shallCreateTerminationWithHSAId() {
    assertEquals(HSA_ID, termination().careProvider().hsaId().id());
  }

  @Test
  void shallCreateTerminationWithOrganizationalNumber() {
    assertEquals(ORGANIZATIONAL_NUMBER,
        termination().careProvider().organisationalNumber().number());
  }

  @Test
  void shallCreateTerminationWithPersonId() {
    assertEquals(PERSON_ID, termination().export().organisationalRepresentative().personId().id());
  }

  @Test
  void shallCreateTerminationWithPhoneNumber() {
    assertEquals(PHONE_NUMBER,
        termination().export().organisationalRepresentative().phoneNumber().number());
  }

  @Test
  void shallNotExceptTerminationWithoutHSAId() {
    final var exception = assertThrows(NullPointerException.class, this::terminationMissingHSAId);
    assertEquals("Missing HSAId", exception.getMessage());
  }

  @Test
  void shallNotExceptTerminationWithoutOrganizationalNumber() {
    final var exception = assertThrows(NullPointerException.class,
        this::terminationMissingOrganizationalNumber);
    assertEquals("Missing OrganizationalNumber", exception.getMessage());
  }

  @Test
  void shallNotExceptTerminationWithoutPersonId() {
    final var exception = assertThrows(NullPointerException.class,
        this::terminationMissingPersonId);
    assertEquals("Missing PersonId", exception.getMessage());
  }

  @Test
  void shallNotExceptTerminationWithoutPhoneNumber() {
    final var exception = assertThrows(NullPointerException.class,
        this::terminationMissingPhoneNumber);
    assertEquals("Missing PhoneNumber", exception.getMessage());
  }

  private Termination termination() {
    return TerminationBuilder.getInstance()
        .careProviderHSAId(HSA_ID)
        .careProviderOrganizationalNumber(ORGANIZATIONAL_NUMBER)
        .careProviderOrganisationalRepresentativePersonId(PERSON_ID)
        .careProviderOrganisationalRepresentativePhoneNumber(PHONE_NUMBER)
        .createTermination();
  }

  private Termination terminationMissingHSAId() {
    return TerminationBuilder.getInstance()
        .careProviderOrganizationalNumber(ORGANIZATIONAL_NUMBER)
        .careProviderOrganisationalRepresentativePersonId(PERSON_ID)
        .careProviderOrganisationalRepresentativePhoneNumber(PHONE_NUMBER)
        .createTermination();
  }

  private Termination terminationMissingOrganizationalNumber() {
    return TerminationBuilder.getInstance()
        .careProviderHSAId(HSA_ID)
        .careProviderOrganisationalRepresentativePersonId(PERSON_ID)
        .careProviderOrganisationalRepresentativePhoneNumber(PHONE_NUMBER)
        .createTermination();
  }

  private Termination terminationMissingPersonId() {
    return TerminationBuilder.getInstance()
        .careProviderHSAId(HSA_ID)
        .careProviderOrganizationalNumber(ORGANIZATIONAL_NUMBER)
        .careProviderOrganisationalRepresentativePhoneNumber(PHONE_NUMBER)
        .createTermination();
  }

  private Termination terminationMissingPhoneNumber() {
    return TerminationBuilder.getInstance()
        .careProviderHSAId(HSA_ID)
        .careProviderOrganizationalNumber(ORGANIZATIONAL_NUMBER)
        .careProviderOrganisationalRepresentativePersonId(PERSON_ID)
        .createTermination();
  }
}