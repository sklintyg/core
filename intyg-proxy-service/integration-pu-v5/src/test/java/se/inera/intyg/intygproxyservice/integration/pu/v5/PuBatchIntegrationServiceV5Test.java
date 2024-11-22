package se.inera.intyg.intygproxyservice.integration.pu.v5;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import se.inera.intyg.intygproxyservice.integration.api.pu.Person;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuPersonsRequest;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuPersonsResponse;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuResponse;

@ExtendWith(MockitoExtension.class)
class PuBatchIntegrationServiceV5Test {

  @Mock
  PuIntegrationServiceV5 puIntegrationServiceV5;

  @InjectMocks
  PuBatchIntegrationServiceV5 puBatchIntegrationServiceV5;

  private static final PuPersonsResponse RESPONSE_B1 = PuPersonsResponse.builder()
      .persons(List.of(
              PuResponse.found(Person.builder().personnummer("1").build()),
              PuResponse.found(Person.builder().personnummer("2").build()),
              PuResponse.found(Person.builder().personnummer("3").build()),
              PuResponse.found(Person.builder().personnummer("4").build()),
              PuResponse.found(Person.builder().personnummer("5").build())
          )
      )
      .build();

  private static final PuPersonsResponse RESPONSE_B2 = PuPersonsResponse.builder()
      .persons(List.of(
              PuResponse.found(Person.builder().personnummer("6").build()),
              PuResponse.found(Person.builder().personnummer("7").build()),
              PuResponse.found(Person.builder().personnummer("8").build()),
              PuResponse.found(Person.builder().personnummer("9").build())
          )
      )
      .build();

  @BeforeEach
  void setup() {
    ReflectionTestUtils.setField(puBatchIntegrationServiceV5, "batchSize", 5);
    when(puIntegrationServiceV5.findPersons(
        PuPersonsRequest.builder()
            .personIds(List.of("1", "2", "3", "4", "5"))
            .build()
    )).thenReturn(
        RESPONSE_B1
    );

    when(puIntegrationServiceV5.findPersons(
        PuPersonsRequest.builder()
            .personIds(List.of("6", "7", "8", "9"))
            .build()
    )).thenReturn(
        RESPONSE_B2
    );
  }

  @Test
  void shouldMakeOneCallForEachBatchAndMergeResults() {
    final var request = PuPersonsRequest.builder()
        .personIds(List.of("1", "2", "3", "4", "5", "6", "7", "8", "9"))
        .build();

    final var response = puBatchIntegrationServiceV5.findPersons(request);

    assertAll(
        () -> assertEquals(9, response.getPersons().size()),
        () -> assertTrue(response.getPersons().containsAll(RESPONSE_B1.getPersons())),
        () -> assertTrue(response.getPersons().containsAll(RESPONSE_B2.getPersons()))
    );
  }

}