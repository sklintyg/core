package se.inera.intyg.intygproxyservice.integration.pu.configuration;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.riv.strategicresourcemanagement.persons.person.getpersonsforprofile.v3.rivtabp21.GetPersonsForProfileResponderInterface;

@Configuration
public class PuClientConfiguration {

  @Bean
  public GetPersonsForProfileResponderInterface getPersonsForProfileResponder() {
    final var jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
    jaxWsProxyFactoryBean.setServiceClass(GetPersonsForProfileResponderInterface.class);
    jaxWsProxyFactoryBean.setAddress(
        "http://localhost:8040/services/stubs/strategicresourcemanagement/persons/person/GetPersonsForProfile/3/rivtabp21");
    return (GetPersonsForProfileResponderInterface) jaxWsProxyFactoryBean.create();
  }
}
