package se.inera.intyg.intygproxyservice.integration.pu.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.inera.intyg.intygproxyservice.integration.common.WebServiceClientFactory;
import se.riv.strategicresourcemanagement.persons.person.getpersonsforprofile.v3.rivtabp21.GetPersonsForProfileResponderInterface;

@Configuration
@Slf4j
@RequiredArgsConstructor
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
