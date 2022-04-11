package se.inera.intyg.cts.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class TerminationBuilderTest {

  public static final UUID DEFAULT_TERMINATION_ID = UUID.randomUUID();
  public static final LocalDateTime DEFAULT_CREATED = LocalDateTime.now();
  public static final String HSA_ID = "hsaId";
  public static final String CREATOR_HSA_ID = "creatorHSAId";
  public static final String CREATOR_NAME = "creatorName";
  public static final String ORGANIZATIONAL_NUMBER = "organizationalNumber";
  public static final String PERSON_ID = "personId";
  public static final String PHONE_NUMBER = "phoneNumber";
  public static final TerminationStatus DEFAULT_STATUS = TerminationStatus.CREATED;

  @Nested
  class CreateNewTermination {

    @Test
    void shallCreateTerminationWithTerminationId() {
      assertNotNull(terminationBuilder().create().terminationId(),
          "Expect Termination to have an id");
    }

    @Test
    void shallCreateTerminationWithCreated() {
      assertNotNull(terminationBuilder().create().created(), "Expect Termination to have created");
    }

    @Test
    void shallCreateTerminationWithStatusCreated() {
      assertEquals(TerminationStatus.CREATED, terminationBuilder().create().status());
    }
  }

  @Nested
  class CreateExistingTermination {

    @Test
    void shallCreateTerminationWithTerminationId() {
      assertEquals(DEFAULT_TERMINATION_ID,
          terminationBuilder()
              .terminationId(DEFAULT_TERMINATION_ID)
              .created(DEFAULT_CREATED)
              .status(DEFAULT_STATUS)
              .create().terminationId().id());
    }

    @Test
    void shallCreateTerminationWithCreated() {
      assertEquals(DEFAULT_CREATED,
          terminationBuilder()
              .terminationId(DEFAULT_TERMINATION_ID)
              .created(DEFAULT_CREATED)
              .status(DEFAULT_STATUS)
              .create().created());
    }

    @Test
    void shallCreateTerminationWithStatusCreated() {
      assertEquals(DEFAULT_STATUS,
          terminationBuilder()
              .terminationId(DEFAULT_TERMINATION_ID)
              .created(DEFAULT_CREATED)
              .status(DEFAULT_STATUS)
              .create().status());
    }

    @Test
    void shallNotExceptExistingTerminationWithoutCreated() {
      final var exception = assertThrows(IllegalArgumentException.class,
          () -> terminationBuilder()
              .terminationId(DEFAULT_TERMINATION_ID)
              .status(DEFAULT_STATUS)
              .create());
      assertEquals("Missing Created", exception.getMessage());
    }

    @Test
    void shallNotExceptExistingTerminationWithoutStatus() {
      final var exception = assertThrows(IllegalArgumentException.class,
          () -> terminationBuilder()
              .terminationId(DEFAULT_TERMINATION_ID)
              .created(DEFAULT_CREATED)
              .create());
      assertEquals("Missing Status", exception.getMessage());
    }
  }


  @Test
  void shallCreateTerminationWithHSAId() {
    assertEquals(HSA_ID, terminationBuilder().create().careProvider().hsaId().id());
  }

  @Test
  void shallCreateTerminationWithOrganizationalNumber() {
    assertEquals(ORGANIZATIONAL_NUMBER,
        terminationBuilder().create().careProvider().organisationalNumber().number());
  }

  @Test
  void shallCreateTerminationWithPersonId() {
    assertEquals(PERSON_ID,
        terminationBuilder().create().export().organisationalRepresentative().personId().id());
  }

  @Test
  void shallCreateTerminationWithPhoneNumber() {
    assertEquals(PHONE_NUMBER,
        terminationBuilder().create().export().organisationalRepresentative().phoneNumber()
            .number());
  }

  @Test
  void shallNotExceptTerminationWithoutCreatorHSAId() {
    final var exception = assertThrows(IllegalArgumentException.class,
        () -> terminationBuilder().creatorHSAId("").create());
    assertEquals("Missing HSAId", exception.getMessage());
  }

  @Test
  void shallNotExceptTerminationWithoutCreatorName() {
    final var exception = assertThrows(IllegalArgumentException.class,
        () -> terminationBuilder().creatorName("").create());
    assertEquals("Missing Name", exception.getMessage());
  }

  @Test
  void shallNotExceptTerminationWithoutHSAId() {
    final var exception = assertThrows(IllegalArgumentException.class,
        () -> terminationBuilder().careProviderHSAId("").create());
    assertEquals("Missing HSAId", exception.getMessage());
  }

  @Test
  void shallNotExceptTerminationWithoutOrganizationalNumber() {
    final var exception = assertThrows(IllegalArgumentException.class,
        () -> terminationBuilder().careProviderOrganizationalNumber("").create());
    assertEquals("Missing OrganizationalNumber", exception.getMessage());
  }

  @Test
  void shallNotExceptTerminationWithoutPersonId() {
    final var exception = assertThrows(IllegalArgumentException.class,
        () -> terminationBuilder().careProviderOrganisationalRepresentativePersonId("")
            .create());
    assertEquals("Missing PersonId", exception.getMessage());
  }

  @Test
  void shallNotExceptTerminationWithoutPhoneNumber() {
    final var exception = assertThrows(IllegalArgumentException.class,
        () -> terminationBuilder().careProviderOrganisationalRepresentativePhoneNumber("")
            .create());
    assertEquals("Missing PhoneNumber", exception.getMessage());
  }

  private TerminationBuilder terminationBuilder() {
    return TerminationBuilder.getInstance()
        .creatorHSAId(CREATOR_HSA_ID)
        .creatorName(CREATOR_NAME)
        .careProviderHSAId(HSA_ID)
        .careProviderOrganizationalNumber(ORGANIZATIONAL_NUMBER)
        .careProviderOrganisationalRepresentativePersonId(PERSON_ID)
        .careProviderOrganisationalRepresentativePhoneNumber(PHONE_NUMBER);
  }
}