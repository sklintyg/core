package se.inera.intyg.intygproxyservice.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Objects;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import se.inera.intyg.intygproxyservice.config.RedisConfig;

@Slf4j
public class CacheUtility {

  private CacheUtility() {
    throw new IllegalStateException("Utility class");
  }

  public static <T> Optional<T> get(CacheManager cacheManager, ObjectMapper objectMapper,
      String objectKey, Class<T> objectClass) {
    try {
      final var cacheValue = Objects.requireNonNull(cacheManager.getCache(RedisConfig.PERSON_CACHE))
          .get(objectKey, String.class);

      if (cacheValue == null || cacheValue.isEmpty()) {
        return Optional.empty();
      }

      return Optional.of(objectMapper.readValue(cacheValue, objectClass));
    } catch (Exception e) {
      log.error("Error retrieving person from personCache", e);
      return Optional.empty();
    }
  }

  public static <T> void save(CacheManager cacheManager, ObjectMapper objectMapper, T object,
      String objectKey, String cacheKey) {
    try {
      Objects.requireNonNull(cacheManager.getCache(cacheKey))
          .put(objectKey, objectMapper.writeValueAsString(object));
    } catch (Exception e) {
      log.error("Error saving person to personCache", e);
    }
  }

}
