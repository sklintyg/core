package se.inera.intyg.intygproxyservice.integration.pu.configuration;

import static se.inera.intyg.intygproxyservice.integration.api.constants.PuConstants.PU_PROFILE_V3;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import se.inera.intyg.intygproxyservice.integration.common.WebServiceClientFactory;
import se.riv.strategicresourcemanagement.persons.person.getpersonsforprofile.v3.rivtabp21.GetPersonsForProfileResponderInterface;

@Configuration
@Slf4j
@RequiredArgsConstructor
@Profile(PU_PROFILE_V3)
public class PuClientConfiguration {

  private final WebServiceClientFactory webServiceClientFactory;

  @Value("${integration.pu.getpersonsforprofile.endpoint}")
  private String getPersonsForProfileEndpoint;

  @Bean
  public GetPersonsForProfileResponderInterface getPersonsForProfileResponder() {
    return webServiceClientFactory.create(
        GetPersonsForProfileResponderInterface.class,
        getPersonsForProfileEndpoint
    );
  }
}
