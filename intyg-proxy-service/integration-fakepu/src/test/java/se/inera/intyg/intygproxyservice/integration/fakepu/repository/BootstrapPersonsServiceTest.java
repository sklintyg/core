package se.inera.intyg.intygproxyservice.integration.fakepu.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BootstrapPersonsServiceTest {

  private FakePuRepository fakePuRepository;
  private BootstrapPersonsService bootstrapPersonService;

  @BeforeEach
  void setUp() {
    fakePuRepository = new FakePuRepository();
    bootstrapPersonService = new BootstrapPersonsService(
        fakePuRepository,
        new ObjectMapper()
    );
  }

  @Test
  void shallLoadPersonsToRepository() {
    bootstrapPersonService.bootstrapPersoner();
    assertNotNull(fakePuRepository.getPerson("191212121212"));
  }
}