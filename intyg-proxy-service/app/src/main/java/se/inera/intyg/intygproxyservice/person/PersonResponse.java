package se.inera.intyg.intygproxyservice.person;

import lombok.Builder;
import lombok.Data;
import se.inera.intyg.intygproxyservice.integration.api.pu.Person;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuResponse.Status;

@Data
@Builder
public class PersonResponse {

  private Person person;
  private Status status;

}
