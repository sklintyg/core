package se.inera.intyg.cts.domain.util;

import java.time.LocalDateTime;
import java.util.UUID;
import se.inera.intyg.cts.domain.model.Termination;
import se.inera.intyg.cts.domain.model.TerminationBuilder;
import se.inera.intyg.cts.domain.model.TerminationStatus;

public class TerminationTestDataFactory {

  public static final UUID DEFAULT_TERMINATION_ID = UUID.randomUUID();
  public static final LocalDateTime DEFAULT_CREATED = LocalDateTime.now();
  public static final String DEFAULT_HSA_ID = "hsaId";
  public static final String DEFAULT_CREATOR_HSA_ID = "creatorHSAId";
  public static final String DEFAULT_CREATOR_NAME = "creatorName";
  public static final String DEFAULT_ORGANIZATIONAL_NUMBER = "organizationalNumber";
  public static final String DEFAULT_PERSON_ID = "personId";
  public static final String DEFAULT_PHONE_NUMBER = "phoneNumber";
  public static final String DEFAULT_EMAIL_ADDRESS = "email@address.se";
  public static final TerminationStatus DEFAULT_STATUS = TerminationStatus.CREATED;

  public static Termination defaultTermination() {
    return TerminationBuilder.getInstance()
        .terminationId(DEFAULT_TERMINATION_ID)
        .created(DEFAULT_CREATED)
        .creatorHSAId(DEFAULT_CREATOR_HSA_ID)
        .creatorName(DEFAULT_CREATOR_NAME)
        .careProviderHSAId(DEFAULT_HSA_ID)
        .careProviderOrganizationNumber(DEFAULT_ORGANIZATIONAL_NUMBER)
        .careProviderOrganizationRepresentativePersonId(DEFAULT_PERSON_ID)
        .careProviderOrganizationRepresentativePhoneNumber(DEFAULT_PHONE_NUMBER)
        .careProviderOrganizationRepresentativeEmailAddress(DEFAULT_EMAIL_ADDRESS)
        .status(TerminationStatus.CREATED)
        .create();
  }
}
