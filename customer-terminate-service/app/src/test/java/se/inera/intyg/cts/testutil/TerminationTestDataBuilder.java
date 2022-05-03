package se.inera.intyg.cts.testutil;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.random.RandomGenerator;
import se.inera.intyg.cts.application.dto.TerminationDTO;
import se.inera.intyg.cts.domain.model.Termination;
import se.inera.intyg.cts.domain.model.TerminationBuilder;
import se.inera.intyg.cts.domain.model.TerminationStatus;
import se.inera.intyg.cts.infrastructure.persistence.entity.ExportEmbeddable;
import se.inera.intyg.cts.infrastructure.persistence.entity.TerminationEntity;

public class TerminationTestDataBuilder {

  public static final Long DEFAULT_ID = RandomGenerator.getDefault().nextLong();
  public static final UUID DEFAULT_TERMINATION_ID = UUID.randomUUID();
  public static final LocalDateTime DEFAULT_CREATED = LocalDateTime.now();
  public static final String DEFAULT_HSA_ID = "TSTNMT2321000156-ALFA";
  public static final String DEFAULT_CREATOR_HSA_ID = "creatorHSAId";
  public static final String DEFAULT_CREATOR_NAME = "creatorName";
  public static final String DEFAULT_ORGANIZATION_NUMBER = "2-orgnr-ALFA";
  public static final String DEFAULT_PERSON_ID = "191212121212";
  public static final String DEFAULT_PHONE_NUMBER = "phoneNumber";

  public static final String DEFAULT_PASSWORD = "Password";
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
        .packagePassword(DEFAULT_PASSWORD)
        .create();
  }

  public static TerminationDTO defaultTerminationDTO() {
    return new TerminationDTO(
        DEFAULT_TERMINATION_ID,
        DEFAULT_CREATED,
        DEFAULT_CREATOR_HSA_ID,
        DEFAULT_CREATOR_NAME,
        DEFAULT_STATUS.toString(),
        DEFAULT_HSA_ID,
        DEFAULT_ORGANIZATION_NUMBER,
        DEFAULT_PERSON_ID,
        DEFAULT_PHONE_NUMBER
    );
  }

  public static TerminationEntity defaultTerminationEntity() {
    return new TerminationEntity(
        DEFAULT_ID,
        DEFAULT_TERMINATION_ID,
        DEFAULT_CREATED,
        DEFAULT_CREATOR_HSA_ID,
        DEFAULT_CREATOR_NAME,
        DEFAULT_HSA_ID,
        DEFAULT_ORGANIZATION_NUMBER,
        DEFAULT_PERSON_ID,
        DEFAULT_PHONE_NUMBER,
        DEFAULT_STATUS.toString(),
        new ExportEmbeddable(0, 0, null, null));
  }

  public static TerminationEntity defaultTerminationEntity(UUID terminationId) {
    return new TerminationEntity(
        RandomGenerator.getDefault().nextLong(),
        terminationId,
        DEFAULT_CREATED,
        DEFAULT_CREATOR_HSA_ID,
        DEFAULT_CREATOR_NAME,
        DEFAULT_HSA_ID,
        DEFAULT_ORGANIZATION_NUMBER,
        DEFAULT_PERSON_ID,
        DEFAULT_PHONE_NUMBER,
        DEFAULT_STATUS.toString(),
        new ExportEmbeddable(0, 0, null, null));
  }
}
