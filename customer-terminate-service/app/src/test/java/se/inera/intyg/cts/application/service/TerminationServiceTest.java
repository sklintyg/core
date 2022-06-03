package se.inera.intyg.cts.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.cts.testutil.TerminationTestDataBuilder.DEFAULT_CREATOR_HSA_ID;
import static se.inera.intyg.cts.testutil.TerminationTestDataBuilder.DEFAULT_CREATOR_NAME;
import static se.inera.intyg.cts.testutil.TerminationTestDataBuilder.DEFAULT_EMAIL_ADDRESS;
import static se.inera.intyg.cts.testutil.TerminationTestDataBuilder.DEFAULT_HSA_ID;
import static se.inera.intyg.cts.testutil.TerminationTestDataBuilder.DEFAULT_ORGANIZATION_NUMBER;
import static se.inera.intyg.cts.testutil.TerminationTestDataBuilder.DEFAULT_PERSON_ID;
import static se.inera.intyg.cts.testutil.TerminationTestDataBuilder.DEFAULT_PHONE_NUMBER;
import static se.inera.intyg.cts.testutil.TerminationTestDataBuilder.DEFAULT_TERMINATION_ID;
import static se.inera.intyg.cts.testutil.TerminationTestDataBuilder.defaultTermination;
import static se.inera.intyg.cts.testutil.TerminationTestDataBuilder.defaultTerminationEntity;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.cts.application.dto.CreateTerminationDTO;
import se.inera.intyg.cts.domain.model.Termination;
import se.inera.intyg.cts.domain.repository.TerminationRepository;
import se.inera.intyg.cts.infrastructure.persistence.JpaTerminationRepository;
import se.inera.intyg.cts.infrastructure.persistence.repository.InMemoryTerminationEntityRepository;

class TerminationServiceTest {

  private TerminationService terminationService;
  private TerminationRepository terminationRepository;
  private InMemoryTerminationEntityRepository inMemoryTerminationEntityRepository;

  private Termination termination;

  @BeforeEach
  void setUp() {
    inMemoryTerminationEntityRepository = new InMemoryTerminationEntityRepository();
    terminationRepository = new JpaTerminationRepository(inMemoryTerminationEntityRepository);
    terminationService = new TerminationService(terminationRepository);

    termination = defaultTermination();
  }

  @Nested
  class CreateTermination {

    private CreateTerminationDTO createTerminationDTO;

    @BeforeEach
    void setUp() {
      createTerminationDTO = new CreateTerminationDTO(DEFAULT_CREATOR_HSA_ID, DEFAULT_CREATOR_NAME,
          DEFAULT_HSA_ID, DEFAULT_ORGANIZATION_NUMBER, DEFAULT_PERSON_ID, DEFAULT_PHONE_NUMBER,
          DEFAULT_EMAIL_ADDRESS);
    }

    @Test
    void shallCreateTermination() {
      assertNotNull(terminationService.create(createTerminationDTO), "Termination is null");
    }

    @Test
    void shallCreateTerminationWithTerminationId() {
      assertNotNull(terminationService.create(createTerminationDTO).terminationId(),
          "TerminationId is null");
    }

    @Test
    void shallCreateTerminationWithCreatorHSAId() {
      assertEquals(DEFAULT_CREATOR_HSA_ID,
          terminationService.create(createTerminationDTO).creatorHSAId());
    }

    @Test
    void shallCreateTerminationWithCreatorName() {
      assertEquals(DEFAULT_CREATOR_NAME,
          terminationService.create(createTerminationDTO).creatorName());
    }

    @Test
    void shallCreateTerminationWithHSAId() {
      assertEquals(DEFAULT_HSA_ID,
          terminationService.create(createTerminationDTO).hsaId());
    }

    @Test
    void shallCreateTerminationWithOrganizationNumber() {
      assertEquals(DEFAULT_ORGANIZATION_NUMBER,
          terminationService.create(createTerminationDTO).organizationNumber());
    }

    @Test
    void shallCreateTerminationWithPersonId() {
      assertEquals(DEFAULT_PERSON_ID,
          terminationService.create(createTerminationDTO).personId());
    }

    @Test
    void shallCreateTerminationWithPhoneNumber() {
      assertEquals(DEFAULT_PHONE_NUMBER,
          terminationService.create(createTerminationDTO).phoneNumber());
    }

    @Test
    void shallCreateTerminationWithEmailAddress() {
      assertEquals(DEFAULT_EMAIL_ADDRESS,
          terminationService.create(createTerminationDTO).emailAddress());
    }
  }

  @Test
  void shallReturnExistingTermination() {
    inMemoryTerminationEntityRepository.save(defaultTerminationEntity());

    assertTrue(terminationService.findById(DEFAULT_TERMINATION_ID).isPresent(),
        "Shall contain a termination");
  }

  @Test
  void shallReturnEmptyOptionalWhenTerminationIdDoesntExist() {
    assertTrue(terminationService.findById(DEFAULT_TERMINATION_ID).isEmpty(),
        "Shall not contain any termination");
  }

  @Test
  void shallReturnAllExistingTerminations() {
    final var numberOfTerminations = 10;
    for (int i = 0; i < numberOfTerminations; i++) {
      inMemoryTerminationEntityRepository.save(
          defaultTerminationEntity(UUID.randomUUID())
      );
    }

    assertEquals(numberOfTerminations, terminationService.findAll().size());
  }
}