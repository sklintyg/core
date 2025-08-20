package se.inera.intyg.intygproxyservice.integration.elva77.client;

import static se.inera.intyg.intygproxyservice.integration.api.elva77.Elva77Constants.ELVA77_PROFILE_ACTIVE;
import static se.inera.intyg.intygproxyservice.logging.MdcLogConstants.EVENT_TYPE_ACCESSED;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import se.inera.intyg.intygproxyservice.integration.api.elva77.Elva77Request;
import se.inera.intyg.intygproxyservice.integration.api.elva77.Elva77Response;
import se.inera.intyg.intygproxyservice.integration.elva77.UserProfileResponseTypeHandler;
import se.inera.intyg.intygproxyservice.logging.PerformanceLogging;
import se.mkv.itintegration.getuserprofile.v2.GetUserProfileResponderInterface;
import se.mkv.itintegration.getuserprofileresponder.v2.GetUserProfileType;

@Slf4j
@Service
@RequiredArgsConstructor
@Profile(ELVA77_PROFILE_ACTIVE)
public class Elva77Client {

  private final GetUserProfileResponderInterface getUserProfileResponderInterface;
  private final UserProfileResponseTypeHandler userProfileResponseTypeHandler;

  @PerformanceLogging(eventAction = "get-user-profile", eventType = EVENT_TYPE_ACCESSED)
  public Elva77Response findCitizen(Elva77Request request) {
    final var parameters = getParameters(request.getPersonId());

    try {
      final var getUserProfileResponseType = getUserProfileResponderInterface.getUserProfile(
          parameters
      );

      return userProfileResponseTypeHandler.handle(getUserProfileResponseType);
    } catch (Exception ex) {
      log.error("Unexpected error occurred when trying to call Elva77", ex);
      return Elva77Response.error();
    }
  }

  private static GetUserProfileType getParameters(String personId) {
    final var parameters = new GetUserProfileType();
    parameters.setSubjectOfCare(personId);
    return parameters;
  }
}