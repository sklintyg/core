package se.inera.intyg.cts.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static se.inera.intyg.cts.application.dto.TerminationDTOMapper.toDTO;
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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.cts.application.dto.CreateTerminationDTO;
import se.inera.intyg.cts.application.dto.TerminationDTO;
import se.inera.intyg.cts.domain.model.EmailAddress;
import se.inera.intyg.cts.domain.model.HSAId;
import se.inera.intyg.cts.domain.model.PersonId;
import se.inera.intyg.cts.domain.model.PhoneNumber;
import se.inera.intyg.cts.domain.model.Termination;
import se.inera.intyg.cts.domain.repository.TerminationRepository;
import se.inera.intyg.cts.domain.service.UpdateTermination;
import se.inera.intyg.cts.infrastructure.persistence.JpaTerminationRepository;
import se.inera.intyg.cts.infrastructure.persistence.repository.InMemoryTerminationEntityRepository;

@ExtendWith(MockitoExtension.class)
class TerminationServiceTest {

  @Mock
  private UpdateTermination updateTermination;

  private TerminationService terminationService;
  private TerminationRepository terminationRepository;
  private InMemoryTerminationEntityRepository inMemoryTerminationEntityRepository;

  private Termination termination;

  @BeforeEach
  void setUp() {
    inMemoryTerminationEntityRepository = new InMemoryTerminationEntityRepository();
    terminationRepository = new JpaTerminationRepository(inMemoryTerminationEntityRepository);
    terminationService = new TerminationService(terminationRepository, updateTermination);

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

  @Nested
  class UpdateTerminationMetadata {

    private Termination termination;
    private TerminationDTO terminationDTO;

    @BeforeEach
    void setUp() {
      termination = defaultTermination();
      terminationDTO = toDTO(termination);
    }

    @Test
    void shallUpdateTermination() {
      inMemoryTerminationEntityRepository.save(defaultTerminationEntity());
      doReturn(termination)
          .when(updateTermination)
          .update(any(Termination.class),
              eq(new HSAId(terminationDTO.hsaId())),
              eq(new PersonId(terminationDTO.personId())),
              eq(new EmailAddress(terminationDTO.emailAddress())),
              eq(new PhoneNumber(terminationDTO.phoneNumber()))
          );

      final var updatedTermination = terminationService.update(terminationDTO.terminationId(),
          terminationDTO);
      assertNotNull(updatedTermination, "Termination is null");
    }

    @Test
    void shallThrowExceptionIfTerminationIdIsntMatching() {
      final var exception = assertThrows(IllegalArgumentException.class, () ->
          terminationService.update(UUID.randomUUID(), terminationDTO));

      assertTrue(exception.getMessage().contains("doesn't match the id passed in termination"));
    }

    @Test
    void shallThrowExceptionIfTerminationDoesntExists() {
      final var exception = assertThrows(IllegalArgumentException.class, () ->
          terminationService.update(terminationDTO.terminationId(), terminationDTO));

      assertTrue(exception.getMessage().contains("doesn't exist!"));
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