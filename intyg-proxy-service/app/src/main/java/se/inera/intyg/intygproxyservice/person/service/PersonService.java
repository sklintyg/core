package se.inera.intyg.intygproxyservice.person.service;

import static se.inera.intyg.intygproxyservice.config.RedisConfig.PERSON_CACHE;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import se.inera.intyg.intygproxyservice.common.CacheUtility;
import se.inera.intyg.intygproxyservice.common.HashUtility;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuRequest;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuResponse;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuService;
import se.inera.intyg.intygproxyservice.integration.api.pu.Status;
import se.inera.intyg.intygproxyservice.person.dto.PersonRequest;
import se.inera.intyg.intygproxyservice.person.dto.PersonResponse;

@Service
@RequiredArgsConstructor
public class PersonService {

  private final PuService puService;
  private final PersonDTOMapper personDTOMapper;
  private final CacheManager cacheManager;
  private final ObjectMapper objectMapper;

  public PersonResponse findPerson(PersonRequest personRequest) {
    validateRequest(personRequest);

    final var responseFromCache = getPersonFromCache(personRequest.getPersonId());

    if (responseFromCache.isPresent()) {
      return PuResponseConverter.convert(personDTOMapper, responseFromCache.get());
    }

    final var puResponse = findPersonInPu(personRequest);

    if (puResponse.status().equals(Status.FOUND)) {
      savePersonInCache(puResponse);
    }

    return PuResponseConverter.convert(personDTOMapper, puResponse);
  }

  private static void validateRequest(PersonRequest personRequest) {
    if (personRequest == null) {
      throw new IllegalArgumentException("PersonRequest is null");
    }

    if (personRequest.getPersonId() == null || personRequest.getPersonId().isBlank()) {
      throw new IllegalArgumentException(
          String.format("PersonId is not valid: '%s'", personRequest.getPersonId())
      );
    }
  }

  private PuResponse findPersonInPu(PersonRequest personRequest) {
    return puService.findPerson(
        PuRequest.builder()
            .personId(
                personRequest.getPersonId()
            )
            .build()
    );
  }

  private void savePersonInCache(PuResponse puResponse) {
    CacheUtility.save(cacheManager, objectMapper, puResponse,
        HashUtility.hash(puResponse.person().getPersonnummer()), PERSON_CACHE);
  }

  private Optional<PuResponse> getPersonFromCache(String id) {
    return CacheUtility.get(cacheManager, objectMapper, HashUtility.hash(id), PuResponse.class);
  }
}
