package se.inera.intyg.intygproxyservice.integration.elva77;

import org.springframework.stereotype.Component;
import se.inera.intyg.intygproxyservice.integration.api.elva77.Elva77Response;
import se.mkv.itintegration.getuserprofileresponder.v2.GetUserProfileResponseType;

@Component
public class GetUserProfileResponseTypeHandler {

  public Elva77Response handle(GetUserProfileResponseType responseType) {
    return null;
  }
}