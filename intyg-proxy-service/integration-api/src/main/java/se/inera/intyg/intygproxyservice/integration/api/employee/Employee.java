package se.inera.intyg.intygproxyservice.integration.api.employee;

import java.io.Serializable;
import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Employee implements Serializable {

    List<PersonalInformation> personalInformation;
}
