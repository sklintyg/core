package se.inera.intyg.intygproxyservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.Duration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

@Configuration
public class RedisConfig {

  public static final String PERSON_CACHE = "intygProxyService:personCache";
  public static final int CACHE_SECONDS = 84600; // TODO: Add as config

  @Bean
  public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
    return RedisCacheManager.builder(connectionFactory)
        .withCacheConfiguration(PERSON_CACHE,
            redisCacheConfiguration(Duration.ofSeconds(CACHE_SECONDS)))
        .build();
  }

  private RedisCacheConfiguration redisCacheConfiguration(Duration duration) {
    return RedisCacheConfiguration
        .defaultCacheConfig()
        .entryTtl(duration)
        .serializeValuesWith(serializationPair());
  }

  @Bean
  public RedisSerializationContext.SerializationPair<Object> serializationPair() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    return RedisSerializationContext.SerializationPair.fromSerializer(
        new GenericJackson2JsonRedisSerializer(objectMapper));
  }
}