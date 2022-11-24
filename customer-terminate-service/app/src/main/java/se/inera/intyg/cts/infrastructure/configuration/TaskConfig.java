package se.inera.intyg.cts.infrastructure.configuration;

import java.time.Duration;
import java.util.List;
import javax.annotation.Resource;
import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.redis.spring.RedisLockProvider;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@EnableSchedulerLock(defaultLockAtMostFor = "PT30S")
public class TaskConfig {

  private final static Logger LOG = LoggerFactory.getLogger(TaskConfig.class);
  private static final String ENVIRONMENT = "cts";

  @Value("${redis.host}")
  protected String redisHost;
  @Value("${redis.port}")
  protected String redisPort;
  @Value("${redis.password}")
  protected String redisPassword;
  @Value("${redis.sentinel.master.name}")
  protected String redisSentinelMasterName;

  @Value("${redis.cluster.nodes:}")
  private String redisClusterNodes;
  @Value("${redis.cluster.password:}")
  private String redisClusterPassword;
  @Value("${redis.cluster.max.redirects:3}")
  private Integer redisClusterMaxRedirects;
  @Value("${redis.cluster.read.timeout:PT1M}")
  private String redisClusterReadTimeout;

  @Resource
  Environment environment;

  @Bean
  public LockProvider lockProvider(RedisConnectionFactory jedisConnectionFactory) {
    return new RedisLockProvider(jedisConnectionFactory, ENVIRONMENT);
  }

  @Bean
  public JedisConnectionFactory jedisConnectionFactory() {
    final var activeProfiles = List.of(environment.getActiveProfiles());
    if (activeProfiles.contains("redis-cluster")) {
      return clusterConnectionFactory();
    }
    if (activeProfiles.contains("redis-sentinel")) {
      return sentinelConnectionFactory();
    }

    return plainConnectionFactory();
  }

  private JedisConnectionFactory plainConnectionFactory() {
    LOG.info("Using redis standalone configuration.");
    final var standaloneConfiguration = new RedisStandaloneConfiguration();
    standaloneConfiguration.setHostName(redisHost);
    standaloneConfiguration.setPort(Integer.parseInt(redisPort));
    standaloneConfiguration.setPassword(redisPassword);

    return new JedisConnectionFactory(standaloneConfiguration);
  }

  private JedisConnectionFactory sentinelConnectionFactory() {
    LOG.info("Detected spring profile 'redis-sentinel', using redis sentinel configuration.");
    var sentinelConfig = new RedisSentinelConfiguration().master(redisSentinelMasterName);

    final var hosts = redisHost.split(";");
    final var ports = redisPort.split(";");

    for (int i = 0; i < hosts.length; i++) {
      sentinelConfig = sentinelConfig.sentinel(hosts[i], Integer.parseInt(ports[i]));
    }

    return new JedisConnectionFactory(sentinelConfig);
  }

  private JedisConnectionFactory clusterConnectionFactory() {
    LOG.info("Detected spring profile 'redis-cluster', using redis cluster configuration.");
    final var clusterConfig = new RedisClusterConfiguration(List.of(redisClusterNodes.split(";")));
    clusterConfig.setMaxRedirects(redisClusterMaxRedirects);
    clusterConfig.setPassword(redisClusterPassword);

    final var clientConfig = JedisClientConfiguration.builder()
        .readTimeout(Duration.parse(redisClusterReadTimeout)).build();

    return new JedisConnectionFactory(clusterConfig, clientConfig);
  }
}
