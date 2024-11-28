package se.inera.intyg.intygproxyservice.person.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static se.inera.intyg.intygproxyservice.person.dto.StatusDTOType.FOUND;

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
import se.inera.intyg.intygproxyservice.common.HashUtility;
import se.inera.intyg.intygproxyservice.config.RedisConfig;
import se.inera.intyg.intygproxyservice.integration.api.pu.Person;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuPersonsRequest;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuPersonsResponse;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuResponse;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuService;
import se.inera.intyg.intygproxyservice.person.dto.PersonDTO;
import se.inera.intyg.intygproxyservice.person.dto.PersonsRequest;
import se.inera.intyg.intygproxyservice.person.dto.StatusDTOType;

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
  private static final PuResponse NOT_FOUND = PuResponse.notFound(
      PERSON_ID_1
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
  }

  @Nested
  class PersonInCache {

    @BeforeEach
    void setup() {
      when(personDTOMapper.toDTO(PERSON_RESPONSE_1.person()))
          .thenReturn(PERSON_DTO_1);
      when(cache.get(HashUtility.hash(PERSON_ID_1), String.class))
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
    when(personDTOMapper.toDTO(PERSON_RESPONSE_1.person()))
        .thenReturn(PERSON_DTO_1);
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
        () -> assertEquals(FOUND, response.getPersons().getFirst().getStatus()),
        () -> assertEquals(PERSON_DTO_2, response.getPersons().get(1).getPerson()),
        () -> assertEquals(FOUND, response.getPersons().get(1).getStatus())
    );
  }

  @Test
  void shouldSaveFoundPatientInCache() throws JsonProcessingException {
    when(personDTOMapper.toDTO(PERSON_RESPONSE_1.person()))
        .thenReturn(PERSON_DTO_1);
    when(objectMapper.writeValueAsString(PERSON_RESPONSE_1))
        .thenReturn(PERSON_RESPONSE_1.toString());
    when(puService.findPersons(any()))
        .thenReturn(
            PuPersonsResponse.builder()
                .persons(List.of(PERSON_RESPONSE_1))
                .build()
        );

    personsService.findPersons(
        PersonsRequest.builder()
            .personIds(List.of(PERSON_ID_1))
            .build()
    );

    verify(cache, times(1)).put(HashUtility.hash(PERSON_ID_1), PERSON_RESPONSE_1.toString());
  }

  @Test
  void shouldNotSaveNotFoundPatientInCache() {
    when(puService.findPersons(any()))
        .thenReturn(
            PuPersonsResponse.builder()
                .persons(List.of(NOT_FOUND))
                .build()
        );

    final var response = personsService.findPersons(
        PersonsRequest.builder()
            .personIds(List.of(PERSON_ID_1))
            .build()
    );

    verify(cache, times(0)).put(anyString(), anyString());

    assertAll(
        () -> assertEquals(PersonDTO.builder().personnummer(PERSON_ID_1).build(),
            response.getPersons().getFirst().getPerson()),
        () -> assertEquals(StatusDTOType.NOT_FOUND, response.getPersons().getFirst().getStatus())
    );
  }
}