package se.inera.intyg.intygproxyservice.integration.authorization.client.converter;

import org.springframework.stereotype.Component;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.Result;
import se.riv.infrastructure.directory.authorizationmanagement.handlehospcertificationpersonresponder.v1.HandleHospCertificationPersonResponseType;

@Component
public class HandleCertificationPersonResponseTypeConverter {

  public Result convert(HandleHospCertificationPersonResponseType type) {
    if (type == null) {
      return Result.builder().build();
    }

    return Result.builder()
        .resultText(type.getResultText())
        .resultCode(type.getResultCode() != null ? type.getResultCode().toString() : null)
        .build();
  }
}
