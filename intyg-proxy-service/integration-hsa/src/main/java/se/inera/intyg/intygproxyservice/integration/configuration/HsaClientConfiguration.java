package se.inera.intyg.intygproxyservice.integration.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.inera.intyg.intygproxyservice.integration.common.WebServiceClientFactory;
import se.riv.infrastructure.directory.authorizationmanagement.getcredentialsforpersonincludingprotectedperson.v2.rivtabp21.GetCredentialsForPersonIncludingProtectedPersonResponderInterface;
import se.riv.infrastructure.directory.authorizationmanagement.gethosplastupdate.v1.rivtabp21.GetHospLastUpdateResponderInterface;
import se.riv.infrastructure.directory.employee.getemployeeincludingprotectedperson.v3.rivtabp21.GetEmployeeIncludingProtectedPersonResponderInterface;
import se.riv.infrastructure.directory.organization.gethealthcareunit.v2.rivtabp21.GetHealthCareUnitResponderInterface;
import se.riv.infrastructure.directory.organization.gethealthcareunitmembers.v2.rivtabp21.GetHealthCareUnitMembersResponderInterface;
import se.riv.infrastructure.directory.organization.getunit.v4.rivtabp21.GetUnitResponderInterface;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class HsaClientConfiguration {

  private final WebServiceClientFactory webServiceClientFactory;

  @Value("${integration.hsa.getemployeeincludingprotectedperson.endpoint}")
  private String getEmployeeIncludingProtectedPersonEndpoint;

  @Value("${integration.hsa.gethealthcareunit.endpoint}")
  private String getHealthCareUnitEndpoint;

  @Value("${integration.hsa.gethealthcareunitmembers.endpoint}")
  private String getHealthCareUnitMembersEndpoint;

  @Value("${integration.hsa.getunit.endpoint}")
  private String getUnitEndpoint;

  @Value("${integration.hsa.gethealthcareunitmembers.endpoint}")
  private String getCredentialInformationEndpoint;

  @Value("${integration.hsa.getlastupdate.endpoint}")
  private String getLastUpdateEndpoint;

  @Bean
  public GetCredentialsForPersonIncludingProtectedPersonResponderInterface getCredentialInformation() {
    return webServiceClientFactory.create(
        GetCredentialsForPersonIncludingProtectedPersonResponderInterface.class,
        getCredentialInformationEndpoint
    );
  }

  @Bean
  public GetHospLastUpdateResponderInterface getLastUpdate() {
    return webServiceClientFactory.create(
        GetHospLastUpdateResponderInterface.class,
        getLastUpdateEndpoint
    );
  }

  @Bean
  public GetEmployeeIncludingProtectedPersonResponderInterface getEmployeeIncludingProtectedPerson() {
    return webServiceClientFactory.create(
        GetEmployeeIncludingProtectedPersonResponderInterface.class,
        getEmployeeIncludingProtectedPersonEndpoint
    );
  }

  @Bean
  public GetHealthCareUnitResponderInterface getHealthCareUnitResponderInterface() {
    return webServiceClientFactory.create(
        GetHealthCareUnitResponderInterface.class,
        getHealthCareUnitEndpoint
    );
  }

  @Bean
  public GetHealthCareUnitMembersResponderInterface getHealthCareUnitMembersResponderInterface() {
    return webServiceClientFactory.create(
        GetHealthCareUnitMembersResponderInterface.class,
        getHealthCareUnitMembersEndpoint
    );
  }

  @Bean
  public GetUnitResponderInterface getUnitResponderInterface() {
    return webServiceClientFactory.create(
        GetUnitResponderInterface.class,
        getUnitEndpoint
    );
  }
}
