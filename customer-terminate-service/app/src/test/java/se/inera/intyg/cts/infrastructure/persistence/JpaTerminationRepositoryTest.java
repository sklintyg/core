package se.inera.intyg.cts.infrastructure.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static se.inera.intyg.cts.testutil.TerminationTestDataBuilder.DEFAULT_TERMINATION_ID;
import static se.inera.intyg.cts.testutil.TerminationTestDataBuilder.defaultTermination;
import static se.inera.intyg.cts.testutil.TerminationTestDataBuilder.defaultTerminationEntity;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.cts.domain.model.Termination;
import se.inera.intyg.cts.domain.model.TerminationId;

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
  void shallStoreTermination() {
    assertNotNull(jpaTerminationRepository.store(termination), "Should return stored Termination");
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
}