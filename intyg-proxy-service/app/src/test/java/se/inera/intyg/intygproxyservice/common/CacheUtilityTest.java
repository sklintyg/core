package se.inera.intyg.intygproxyservice.common;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
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

  private static final String OBJECT_KEY = "objectKey";

  @BeforeEach
  void setUp() {
    when(cacheManager.getCache(RedisConfig.PERSON_CACHE)).thenThrow(new RuntimeException());
  }

  @Test
  void shouldReturnOptionalEmptyIfExceptionOnCacheGetRequest() {
    final var objectClass = PuResponse.class;
    final var result = CacheUtility.get(cacheManager, objectMapper, OBJECT_KEY, objectClass);
    assertEquals(Optional.empty(), result);
  }

  @Test
  void shouldNotThrowIfExceptionOnCacheSaveRequest() {
    assertDoesNotThrow(() -> CacheUtility.save(cacheManager, objectMapper, puResponse, OBJECT_KEY,
        RedisConfig.PERSON_CACHE));
  }

}
