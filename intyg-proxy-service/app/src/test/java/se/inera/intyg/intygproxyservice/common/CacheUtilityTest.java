package se.inera.intyg.intygproxyservice.common;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.CacheManager;
import se.inera.intyg.intygproxyservice.config.RedisConfig;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuResponse;

@ExtendWith(MockitoExtension.class)
class CacheUtilityTest {

  @Mock
  private CacheManager cacheManager;
  @Mock
  private ObjectMapper objectMapper;
  @Mock
  private PuResponse puResponse;

  @Test
  void shouldReturnOptionalEmptyIfExceptionOnCacheGetRequest() {
    final var objectKey = "objectKey";
    final var objectClass = PuResponse.class;
    when(cacheManager.getCache(RedisConfig.PERSON_CACHE)).thenThrow(new RuntimeException());

    final var result = CacheUtility.get(cacheManager, objectMapper, objectKey, objectClass);
    assertEquals(Optional.empty(), result);
  }

  @Test
  void shouldNotThrowIfExceptionOnCacheSaveRequest() {
    final var objectKey = "objectKey";
    final var objectClass = PuResponse.class;
    when(cacheManager.getCache(RedisConfig.PERSON_CACHE)).thenThrow(new RuntimeException());

    assertDoesNotThrow(() -> CacheUtility.save(cacheManager, objectMapper, puResponse, objectKey,
        RedisConfig.PERSON_CACHE));
  }

}
