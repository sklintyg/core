package se.inera.intyg.cts.infrastructure.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static se.inera.intyg.cts.infrastructure.persistence.entity.TerminationEntityMapper.toEntity;
import static se.inera.intyg.cts.testutil.TerminationTestDataBuilder.DEFAULT_TERMINATION_ID;
import static se.inera.intyg.cts.testutil.TerminationTestDataBuilder.defaultTermination;
import static se.inera.intyg.cts.testutil.TerminationTestDataBuilder.defaultTerminationEntity;

import java.util.Arrays;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.cts.domain.model.Termination;
import se.inera.intyg.cts.domain.model.TerminationId;
import se.inera.intyg.cts.domain.model.TerminationStatus;
import se.inera.intyg.cts.infrastructure.persistence.repository.InMemoryTerminationEntityRepository;

class JpaTerminationRepositoryTest {

  private JpaTerminationRepository jpaTerminationRepository;
  private InMemoryTerminationEntityRepository inMemoryTerminationEntityRepository;
  private Termination termination;

  @BeforeEach
  void setUp() {
    inMemoryTerminationEntityRepository = new InMemoryTerminationEntityRepository();
    jpaTerminationRepository = new JpaTerminationRepository(inMemoryTerminationEntityRepository);
    termination = defaultTermination();
  }

  @Test
  void shallStoreNewTermination() {
    jpaTerminationRepository.store(termination);
    assertEquals(1, inMemoryTerminationEntityRepository.count());
  }

  @Test
  void shallStoreUpdatedTermination() {
    inMemoryTerminationEntityRepository.save(toEntity(termination));
    jpaTerminationRepository.store(termination);
    assertEquals(1, inMemoryTerminationEntityRepository.count());
  }

  @Test
  void shallReturnTerminationWhenFindingByTerminationId() {
    inMemoryTerminationEntityRepository.save(defaultTerminationEntity());
    assertNotNull(
        jpaTerminationRepository.findByTerminationId(new TerminationId(DEFAULT_TERMINATION_ID)),
        "Should return stored Termination");
  }

  @Test
  void shallReturnAllTerminationsWhenFindingAll() {
    final var numberOfTerminations = 10;
    for (int i = 0; i < numberOfTerminations; i++) {
      inMemoryTerminationEntityRepository.save(
          defaultTerminationEntity(UUID.randomUUID())
      );
    }

    assertEquals(numberOfTerminations, jpaTerminationRepository.findAll().size());
  }

  @Test
  void shallReturnTerminationsWithCorrectStatus() {
    final var numberOfTerminations = 10;
    for (int i = 0; i < numberOfTerminations; i++) {
      inMemoryTerminationEntityRepository.save(
          defaultTerminationEntity(UUID.randomUUID())
      );
    }

    assertEquals(numberOfTerminations,
        jpaTerminationRepository.findByStatuses(Arrays.asList(TerminationStatus.CREATED)).size());
  }

  @Test
  void shallReturnNoTerminationsWithIncorrectStatus() {
    final var numberOfTerminations = 10;
    for (int i = 0; i < numberOfTerminations; i++) {
      inMemoryTerminationEntityRepository.save(
          defaultTerminationEntity(UUID.randomUUID())
      );
    }

    assertEquals(0,
        jpaTerminationRepository.findByStatuses(Arrays.asList(TerminationStatus.COLLECTING))
            .size());
  }
}