package se.inera.intyg.certificateservice.certificate.integration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class IPSIntegrationService {

  private final RestClient ipsRestClient;

  @Value("${integration.intygproxyservice.base.url}")
  private String ipsBaseUrl;

  @Value("${integration.intygproxyservice.person}")
  private String ipsPersonEndpoint;

  @Value("${integration.intygproxyservice.persons}")
  private String ipsPersonsEndpoint;
  
  public IPSIntegrationService(@Qualifier("ipsRestClient") RestClient ipsRestClient) {
    this.ipsRestClient = ipsRestClient;
  }
}