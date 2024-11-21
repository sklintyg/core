package se.inera.intyg.intygproxyservice.person.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import se.inera.intyg.intygproxyservice.config.RedisConfig;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuPersonsRequest;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuPersonsResponse;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuResponse;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuService;
import se.inera.intyg.intygproxyservice.person.dto.PersonsRequest;
import se.inera.intyg.intygproxyservice.person.dto.PersonsResponse;

@Service
@Slf4j
@RequiredArgsConstructor
public class PersonsService {

  private final ObjectMapper objectMapper;
  private final PuService puService;
  private final CacheManager cacheManager;
  private final PersonDTOMapper personDTOMapper;

  public PersonsResponse findPersons(PersonsRequest request) {
    validateRequest(request);

    final var personsFromCache = getPersonsFromCache(request);

    final var requestWithIdsNotInCache = getPersonIdsNotInCache(request, personsFromCache);
    final var puResponse = requestWithIdsNotInCache.getPersonIds().isEmpty()
        ? PuPersonsResponse.empty()
        : findPersonsInPu(requestWithIdsNotInCache);

    puResponse.getPersons().forEach(this::savePersonInCache);

    final var mergedPersonsFromPuAndCache = Stream.concat(
        puResponse.getPersons().stream(),
        personsFromCache.stream()
    ).toList();

    return convert(
        PuPersonsResponse.builder()
            .persons(mergedPersonsFromPuAndCache)
            .build()
    );
  }

  private List<PuResponse> getPersonsFromCache(PersonsRequest request) {
    return request.getPersonIds()
        .stream()
        .map(this::getPersonFromCache)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .toList();
  }

  private static PersonsRequest getPersonIdsNotInCache(PersonsRequest request,
      List<PuResponse> personsFromCache) {
    return PersonsRequest.builder()
        .personIds(
            request.getPersonIds()
                .stream()
                .filter(
                    id -> personsFromCache.stream()
                        .noneMatch(person -> person.getPerson().getPersonnummer().equals(id))
                )
                .toList()
        )
        .build();
  }

  private void savePersonInCache(PuResponse person) {
    try {
      Objects.requireNonNull(cacheManager.getCache(RedisConfig.PERSON_CACHE))
          .put(person.getPerson().getPersonnummer(),
              objectMapper.writeValueAsString(person));
    } catch (JsonProcessingException e) {
      throw new IllegalStateException("Failed to serialize person", e);
    }
  }

  private static void validateRequest(PersonsRequest request) {
    if (request == null) {
      throw new IllegalArgumentException("PersonsRequest is null");
    }
  }

  private PuPersonsResponse findPersonsInPu(PersonsRequest personRequest) {
    return puService.findPersons(
        PuPersonsRequest.builder()
            .personIds(
                personRequest.getPersonIds()
            )
            .build()
    );
  }

  private PersonsResponse convert(PuPersonsResponse puResponse) {
    return PersonsResponse.builder()
        .persons(
            puResponse.getPersons()
                .stream()
                .map(response -> PuResponseConverter.convert(personDTOMapper, response))
                .toList()
        )
        .build();
  }

  private Optional<PuResponse> getPersonFromCache(String id) {
    try {
      final var cacheValue = Objects.requireNonNull(cacheManager.getCache(RedisConfig.PERSON_CACHE))
          .get(id, String.class);

      if (cacheValue == null || cacheValue.isEmpty()) {
        return Optional.empty();
      }

      return Optional.of(objectMapper.readValue(cacheValue, PuResponse.class));
    } catch (JsonProcessingException e) {
      log.warn("Failed to deserialize PuResponse");
      throw new IllegalStateException(e);
    }
  }
}
