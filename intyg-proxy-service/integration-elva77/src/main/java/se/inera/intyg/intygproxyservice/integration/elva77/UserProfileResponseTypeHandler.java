package se.inera.intyg.intygproxyservice.integration.elva77;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import se.inera.intyg.intygproxyservice.integration.api.elva77.Elva77Response;
import se.inera.intyg.intygproxyservice.integration.elva77.converter.UserProfileResponseTypeConverter;
import se.mkv.itintegration.getuserprofileresponder.v2.GetUserProfileResponseType;
import se.mkv.itintegration.userprofile.v2.UserProfileType;

@Component
@RequiredArgsConstructor
public class UserProfileResponseTypeHandler {

  private final UserProfileResponseTypeConverter userProfileResponseTypeConverter;

  public Elva77Response handle(GetUserProfileResponseType responseType) {
    final var isActive = responseType.isIsActive();
    final var userProfile = responseType.getUserProfile();

    if (userNotFound(isActive, userProfile)) {
      return Elva77Response.error();
    }

    return userProfileResponseTypeConverter.convert(responseType);
  }

  private static boolean userNotFound(Boolean isActive, UserProfileType userProfile) {
    return isActive == null || userProfile == null;
  }
}