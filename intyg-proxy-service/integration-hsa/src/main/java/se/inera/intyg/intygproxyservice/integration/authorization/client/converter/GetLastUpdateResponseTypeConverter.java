package se.inera.intyg.intygproxyservice.integration.authorization.client.converter;

import static se.inera.intyg.intygproxyservice.integration.common.TypeConverterHelper.toLocalDate;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import se.riv.infrastructure.directory.authorizationmanagement.gethosplastupdateresponder.v1.GetHospLastUpdateResponseType;

@Component
@RequiredArgsConstructor
public class GetLastUpdateResponseTypeConverter {

  public LocalDateTime convert(GetHospLastUpdateResponseType type) {
    if (type == null || type.getLastUpdate() == null) {
      return null;
    }

    return toLocalDate(type.getLastUpdate());
  }
}
