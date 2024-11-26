package se.inera.intyg.intygproxyservice.person.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import se.inera.intyg.intygproxyservice.config.RedisConfig;
import se.inera.intyg.intygproxyservice.integration.api.pu.Person;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuPersonsRequest;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuPersonsResponse;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuResponse;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuService;
import se.inera.intyg.intygproxyservice.person.dto.PersonDTO;
import se.inera.intyg.intygproxyservice.person.dto.PersonsRequest;

@ExtendWith(MockitoExtension.class)
class PersonsServiceTest {

  private static final String PERSON_ID_1 = "191212121212";
  private static final String PERSON_ID_2 = "201212121212";
  private static final PersonDTO PERSON_DTO_1 = PersonDTO.builder().build();
  private static final PersonDTO PERSON_DTO_2 = PersonDTO.builder().build();

  private static final PuResponse PERSON_RESPONSE_1 = PuResponse.found(
      Person.builder()
          .personnummer(PERSON_ID_1)
          .build()
  );
  private static final PuResponse PERSON_RESPONSE_2 = PuResponse.found(
      Person.builder()
          .personnummer(PERSON_ID_2)
          .build()
  );

  @Mock
  private ObjectMapper objectMapper;
  @Mock
  private PuService puService;
  @Mock
  private CacheManager cacheManager;
  @Mock
  private Cache cache;
  @Mock
  private PersonDTOMapper personDTOMapper;

  @InjectMocks
  private PersonsService personsService;

  @BeforeEach
  void setup() {
    when(cacheManager.getCache(RedisConfig.PERSON_CACHE))
        .thenReturn(cache);
    when(personDTOMapper.toDTO(PERSON_RESPONSE_1.person()))
        .thenReturn(PERSON_DTO_1);
  }

  @Nested
  class PersonInCache {

    @BeforeEach
    void setup() {
      when(cache.get(PERSON_ID_1, String.class))
          .thenReturn(PERSON_RESPONSE_1.toString());
      try {
        when(objectMapper.readValue(PERSON_RESPONSE_1.toString(), PuResponse.class))
            .thenReturn(PERSON_RESPONSE_1);
      } catch (JsonProcessingException e) {
        throw new RuntimeException(e);
      }
    }

    @Test
    void shouldNotMakeCallToPuServiceIfAllIdsAreInCache() {
      final var response = personsService.findPersons(
          PersonsRequest.builder()
              .personIds(List.of(PERSON_ID_1))
              .build()
      );

      verify(puService, times(0)).findPersons(any());
      assertEquals(PERSON_DTO_1, response.getPersons().getFirst().getPerson());
    }

    @Test
    void shouldCombineResultsInCacheAndFromPu() {
      when(puService.findPersons(any()))
          .thenReturn(
              PuPersonsResponse.builder()
                  .persons(List.of(PERSON_RESPONSE_2))
                  .build()
          );
      when(personDTOMapper.toDTO(PERSON_RESPONSE_2.person()))
          .thenReturn(PERSON_DTO_2);

      final var response = personsService.findPersons(
          PersonsRequest.builder()
              .personIds(List.of(PERSON_ID_1, PERSON_ID_2))
              .build()
      );

      verify(puService, times(1)).findPersons(
          PuPersonsRequest.builder()
              .personIds(List.of(PERSON_ID_2))
              .build()
      );

      assertAll(
          () -> assertEquals(PERSON_DTO_1, response.getPersons().getFirst().getPerson()),
          () -> assertEquals(PERSON_DTO_2, response.getPersons().get(1).getPerson())
      );
    }

  }

  @Test
  void shouldMakeCallToPuServiceIfNoIdsAreInCache() {
    when(puService.findPersons(any()))
        .thenReturn(
            PuPersonsResponse.builder()
                .persons(List.of(PERSON_RESPONSE_1, PERSON_RESPONSE_2))
                .build()
        );
    when(personDTOMapper.toDTO(PERSON_RESPONSE_2.person()))
        .thenReturn(PERSON_DTO_2);

    final var response = personsService.findPersons(
        PersonsRequest.builder()
            .personIds(List.of(PERSON_ID_1, PERSON_ID_2))
            .build()
    );

    verify(puService, times(1)).findPersons(
        PuPersonsRequest.builder()
            .personIds(List.of(PERSON_ID_1, PERSON_ID_2))
            .build()
    );

    assertAll(
        () -> assertEquals(PERSON_DTO_1, response.getPersons().getFirst().getPerson()),
        () -> assertEquals(PERSON_DTO_2, response.getPersons().get(1).getPerson())
    );
  }
}