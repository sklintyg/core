package se.inera.intyg.intygproxyservice.integration.fakepu;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static se.inera.intyg.intygproxyservice.integration.fakepu.TestData.PERSON_ID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.integration.api.pu.Person;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuRequest;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuResponse.Status;
import se.inera.intyg.intygproxyservice.integration.fakepu.repository.FakePuRepository;

@ExtendWith(MockitoExtension.class)
class FakePuIntegrationServiceTest {

  @Mock
  private FakePuRepository fakePuRepository;

  @InjectMocks
  private FakePuIntegrationService fakePuIntegrationService;

  @Nested
  class PersonFound {

    private Person personFound;

    @BeforeEach
    void setUp() {
      personFound = Person.builder()
          .personnummer(PERSON_ID)
          .build();

      doReturn(personFound)
          .when(fakePuRepository)
          .getPerson(PERSON_ID);
    }

    @Test
    void shallReturnStatusFoundWhenPersonExists() {
      final var actualPuResponse = fakePuIntegrationService.findPerson(
          PuRequest.builder()
              .personId(PERSON_ID)
              .build()
      );
      assertEquals(Status.FOUND, actualPuResponse.getStatus());
    }

    @Test
    void shallReturnPersonWhenPersonExists() {
      final var actualPuResponse = fakePuIntegrationService.findPerson(
          PuRequest.builder()
              .personId(PERSON_ID)
              .build()
      );
      assertEquals(personFound, actualPuResponse.getPerson());
    }
  }

  @Nested
  class PersonNotFound {

    @BeforeEach
    void setUp() {
      doReturn(null)
          .when(fakePuRepository)
          .getPerson(PERSON_ID);
    }

    @Test
    void shallReturnStatusFoundWhenPersonExists() {
      final var actualPuResponse = fakePuIntegrationService.findPerson(
          PuRequest.builder()
              .personId(PERSON_ID)
              .build()
      );
      assertEquals(Status.NOT_FOUND, actualPuResponse.getStatus());
    }
  }

  @Test
  void shallThrowExceptionIfPuRequestIsNull() {
    assertThrows(IllegalArgumentException.class,
        () -> fakePuIntegrationService.findPerson(null)
    );
  }
}