// src/main/java/.../security/TestProfileSecurity.java
package se.inera.intyg.certificateanalyticsservice.testability.configuration;

import static se.inera.intyg.certificateanalyticsservice.testability.common.TestabilityConstants.TESTABILITY_PROFILE;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Profile(TESTABILITY_PROFILE)
public class TestabilitySecurity {

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf ->
            csrf.ignoringRequestMatchers("/testability/**")
        )
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/testability/**").permitAll()
            .requestMatchers("/actuator/health/**", "/actuator/info").permitAll()
            .anyRequest().authenticated()
        );
    return http.build();
  }
}