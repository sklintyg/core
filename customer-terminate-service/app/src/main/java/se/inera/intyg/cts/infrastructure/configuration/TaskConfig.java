package se.inera.intyg.cts.infrastructure.configuration;

import java.util.stream.Stream;
import javax.annotation.Resource;
import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.redis.spring.RedisLockProvider;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@EnableSchedulerLock(defaultLockAtMostFor = "PT30S")
public class TaskConfig {

  private static final String ENVIRONMENT = "cts";

  @Value("${redis.host}")
  protected String redisHost;
  @Value("${redis.port}")
  protected String redisPort;
  @Value("${redis.password}")
  protected String redisPassword;
  @Value("${redis.sentinel.master.name}")
  protected String redisSentinelMasterName;

  @Resource
  Environment environment;

  @Bean
  public LockProvider lockProvider(RedisConnectionFactory jedisConnectionFactory) {
    return new RedisLockProvider(jedisConnectionFactory, ENVIRONMENT);
  }

  @Bean
  public JedisConnectionFactory jedisConnectionFactory() {
    String[] activeProfiles = environment.getActiveProfiles();
    if (Stream.of(activeProfiles).noneMatch("redis-sentinel"::equalsIgnoreCase)) {
      return plainConnectionFactory();
    } else {
      return sentinelConnectionFactory();
    }
  }

  private JedisConnectionFactory plainConnectionFactory() {
    final var standaloneConfiguration = new RedisStandaloneConfiguration();
    standaloneConfiguration.setHostName(redisHost);
    standaloneConfiguration.setPort(Integer.parseInt(redisPort));
    standaloneConfiguration.setPassword(redisPassword);

    return new JedisConnectionFactory(standaloneConfiguration);
  }

  private JedisConnectionFactory sentinelConnectionFactory() {
    var sentinelConfig = new RedisSentinelConfiguration().master(redisSentinelMasterName);

    final var hosts = redisHost.split(";");
    final var ports = redisPort.split(";");

    for (int i = 0; i < hosts.length; i++) {
      sentinelConfig = sentinelConfig.sentinel(hosts[i], Integer.parseInt(ports[i]));
    }

    return new JedisConnectionFactory(sentinelConfig);
  }
}
