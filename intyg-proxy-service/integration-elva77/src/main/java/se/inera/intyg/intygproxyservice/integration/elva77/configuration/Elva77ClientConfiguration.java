package se.inera.intyg.intygproxyservice.integration.elva77.configuration;

import static se.inera.intyg.intygproxyservice.integration.api.elva77.Elva77Constants.ELVA77_PROFILE_ACTIVE;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import se.inera.intyg.intygproxyservice.integration.common.WebServiceClientFactory;
import se.mkv.itintegration.getuserprofile.v2.GetUserProfileResponderInterface;

@Configuration
@Slf4j
@RequiredArgsConstructor
@Profile(ELVA77_PROFILE_ACTIVE)
public class Elva77ClientConfiguration {

  private final WebServiceClientFactory webServiceClientFactory;

  @Value("${integration.elva77.getuserprofile.endpoint}")
  private String getUserProfileEndpoint;

  @Bean
  public GetUserProfileResponderInterface getUserProfileResponderInterface() {
    return webServiceClientFactory.create(
        GetUserProfileResponderInterface.class,
        getUserProfileEndpoint
    );
  }
}