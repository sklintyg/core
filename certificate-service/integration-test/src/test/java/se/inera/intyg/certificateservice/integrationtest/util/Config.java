package se.inera.intyg.certificateservice.integrationtest.util;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class Config {

  @Bean
  TestListener testListener() {
    return new TestListener();
  }

}

