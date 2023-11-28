package se.inera.intyg.intygproxyservice.integration.authorization.client.converter;

import org.springframework.stereotype.Component;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.CommissionRight;
import se.riv.infrastructure.directory.authorizationmanagement.v2.CommissionRightType;

@Component
public class CommissionRightTypeConverter {

  public CommissionRight convert(CommissionRightType type) {
    if (type == null) {
      return CommissionRight.builder().build();
    }

    return CommissionRight.builder()
        .activity(type.getActivity())
        .informationClass(type.getInformationClass())
        .scope(type.getScope())
        .build();
  }
}
