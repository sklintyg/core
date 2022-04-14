package se.inera.intyg.cts.testutil;

import java.time.LocalDateTime;
import java.util.UUID;
import se.inera.intyg.cts.domain.model.Termination;
import se.inera.intyg.cts.domain.model.TerminationBuilder;
import se.inera.intyg.cts.domain.model.TerminationStatus;
import se.inera.intyg.cts.infrastructure.persistence.entity.TerminationEntity;

public class TerminationTestDataBuilder {

  public static final UUID DEFAULT_TERMINATION_ID = UUID.randomUUID();
  public static final LocalDateTime DEFAULT_CREATED = LocalDateTime.now();
  public static final String DEFAULT_HSA_ID = "hsaId";
  public static final String DEFAULT_CREATOR_HSA_ID = "creatorHSAId";
  public static final String DEFAULT_CREATOR_NAME = "creatorName";
  public static final String DEFAULT_ORGANIZATION_NUMBER = "organizationNumber";
  public static final String DEFAULT_PERSON_ID = "personId";
  public static final String DEFAULT_PHONE_NUMBER = "phoneNumber";
  public static final TerminationStatus DEFAULT_STATUS = TerminationStatus.CREATED;

  public static Termination defaultTermination() {
    return TerminationBuilder.getInstance()
        .terminationId(DEFAULT_TERMINATION_ID)
        .created(DEFAULT_CREATED)
        .creatorHSAId(DEFAULT_CREATOR_HSA_ID)
        .creatorName(DEFAULT_CREATOR_NAME)
        .careProviderHSAId(DEFAULT_HSA_ID)
        .careProviderOrganizationNumber(DEFAULT_ORGANIZATION_NUMBER)
        .careProviderOrganizationRepresentativePersonId(DEFAULT_PERSON_ID)
        .careProviderOrganizationRepresentativePhoneNumber(DEFAULT_PHONE_NUMBER)
        .status(TerminationStatus.CREATED)
        .create();
  }

  public static TerminationEntity defaultTerminationEntity() {
    return new TerminationEntity(
        DEFAULT_TERMINATION_ID,
        DEFAULT_CREATED,
        DEFAULT_CREATOR_HSA_ID,
        DEFAULT_CREATOR_NAME,
        DEFAULT_HSA_ID,
        DEFAULT_ORGANIZATION_NUMBER,
        DEFAULT_PERSON_ID,
        DEFAULT_PHONE_NUMBER,
        DEFAULT_STATUS.toString());
  }

  public static TerminationEntity defaultTerminationEntity(UUID terminationId) {
    return new TerminationEntity(
        terminationId,
        DEFAULT_CREATED,
        DEFAULT_CREATOR_HSA_ID,
        DEFAULT_CREATOR_NAME,
        DEFAULT_HSA_ID,
        DEFAULT_ORGANIZATION_NUMBER,
        DEFAULT_PERSON_ID,
        DEFAULT_PHONE_NUMBER,
        DEFAULT_STATUS.toString());
  }
}
