package se.inera.intyg.intygproxyservice.person.service;


import static se.inera.intyg.intygproxyservice.config.RedisConfig.PERSON_CACHE;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import se.inera.intyg.intygproxyservice.common.CacheUtility;
import se.inera.intyg.intygproxyservice.common.HashUtility;
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
    final var personsFromPu = getPersonsFromPu(request, personsFromCache);
    personsFromPu.getPersons().forEach(this::savePersonInCache);

    return convert(
        PuPersonsResponse.builder()
            .persons(mergeResponses(personsFromPu, personsFromCache))
            .build()
    );
  }

  private static List<PuResponse> mergeResponses(PuPersonsResponse personsFromPu,
      List<PuResponse> personsFromCache) {
    return Stream.concat(
        personsFromPu.getPersons().stream(),
        personsFromCache.stream()
    ).toList();
  }

  private PuPersonsResponse getPersonsFromPu(PersonsRequest request,
      List<PuResponse> personsFromCache) {
    final var requestWithIdsNotInCache = getPersonIdsNotInCache(request, personsFromCache);
    return requestWithIdsNotInCache.getPersonIds().isEmpty()
        ? PuPersonsResponse.empty()
        : findPersonsInPu(requestWithIdsNotInCache);
  }

  private List<PuResponse> getPersonsFromCache(PersonsRequest request) {
    return request.getPersonIds()
        .stream()
        .map(this::getPersonFromCache)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .toList();
  }

  private Optional<PuResponse> getPersonFromCache(String id) {
    return CacheUtility.get(cacheManager, objectMapper, HashUtility.hash(id), PuResponse.class);
  }

  private void savePersonInCache(PuResponse puResponse) {
    CacheUtility.save(cacheManager, objectMapper, puResponse,
        HashUtility.hash(puResponse.person().getPersonnummer()), PERSON_CACHE);
  }

  private static PersonsRequest getPersonIdsNotInCache(PersonsRequest request,
      List<PuResponse> personsFromCache) {
    return PersonsRequest.builder()
        .personIds(
            request.getPersonIds()
                .stream()
                .filter(
                    id -> personsFromCache.stream()
                        .noneMatch(person -> person.person().getPersonnummer().equals(id))
                )
                .toList()
        )
        .build();
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

  private static void validateRequest(PersonsRequest request) {
    if (request == null) {
      throw new IllegalArgumentException("PersonsRequest is null");
    }
  }
}