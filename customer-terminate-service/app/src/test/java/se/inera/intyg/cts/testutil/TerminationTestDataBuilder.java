package se.inera.intyg.cts.testutil;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.random.RandomGenerator;
import se.inera.intyg.cts.domain.model.Termination;
import se.inera.intyg.cts.domain.model.TerminationBuilder;
import se.inera.intyg.cts.domain.model.TerminationStatus;
import se.inera.intyg.cts.infrastructure.persistence.entity.ExportEmbeddable;
import se.inera.intyg.cts.infrastructure.persistence.entity.TerminationEntity;

public class TerminationTestDataBuilder {

  public static final Long DEFAULT_ID = RandomGenerator.getDefault().nextLong();
  public static final UUID DEFAULT_TERMINATION_ID = UUID.randomUUID();
  public static final LocalDateTime DEFAULT_CREATED = LocalDateTime.now();
  public static final String DEFAULT_HSA_ID = "hsaId";
  public static final String DEFAULT_CREATOR_HSA_ID = "creatorHSAId";
  public static final String DEFAULT_CREATOR_NAME = "creatorName";
  public static final String DEFAULT_ORGANIZATIONAL_NUMBER = "organizationalNumber";
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
        .careProviderOrganizationalNumber(DEFAULT_ORGANIZATIONAL_NUMBER)
        .careProviderOrganisationalRepresentativePersonId(DEFAULT_PERSON_ID)
        .careProviderOrganisationalRepresentativePhoneNumber(DEFAULT_PHONE_NUMBER)
        .status(TerminationStatus.CREATED)
        .create();
  }

  public static TerminationEntity defaultTerminationEntity() {
    return new TerminationEntity(
        DEFAULT_ID,
        DEFAULT_TERMINATION_ID,
        DEFAULT_CREATED,
        DEFAULT_CREATOR_HSA_ID,
        DEFAULT_CREATOR_NAME,
        DEFAULT_HSA_ID,
        DEFAULT_ORGANIZATIONAL_NUMBER,
        DEFAULT_PERSON_ID,
        DEFAULT_PHONE_NUMBER,
        DEFAULT_STATUS.toString(),
        new ExportEmbeddable(0, 0, null));
  }

  public static TerminationEntity defaultTerminationEntity(UUID terminationId) {
    return new TerminationEntity(
        RandomGenerator.getDefault().nextLong(),
        terminationId,
        DEFAULT_CREATED,
        DEFAULT_CREATOR_HSA_ID,
        DEFAULT_CREATOR_NAME,
        DEFAULT_HSA_ID,
        DEFAULT_ORGANIZATIONAL_NUMBER,
        DEFAULT_PERSON_ID,
        DEFAULT_PHONE_NUMBER,
        DEFAULT_STATUS.toString(),
        new ExportEmbeddable(0, 0, null));
  }
}
