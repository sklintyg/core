package se.inera.intyg.intygproxyservice.integration.api.employee;

import java.io.Serializable;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Employee implements Serializable {

    String hsaId;
}
