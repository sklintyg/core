package se.inera.intyg.intygproxyservice.integration.elva77.converter;

import org.springframework.stereotype.Component;
import se.inera.intyg.intygproxyservice.integration.api.elva77.Citizen;
import se.inera.intyg.intygproxyservice.integration.api.elva77.Elva77Response;
import se.inera.intyg.intygproxyservice.integration.api.elva77.Result;
import se.mkv.itintegration.getuserprofileresponder.v2.GetUserProfileResponseType;

@Component
public class UserProfileResponseTypeConverter {

  public Elva77Response convert(GetUserProfileResponseType responseType) {
    final var userProfile = responseType.getUserProfile();
    if (Boolean.FALSE.equals(responseType.isIsActive())) {
      return Elva77Response.inactive(userProfile.getSubjectOfCareId());
    }

    return Elva77Response.builder()
        .citizen(
            Citizen.builder()
                .isActive(true)
                .fornamn(userProfile.getFirstName())
                .efternamn(userProfile.getLastName())
                .personnummer(userProfile.getSubjectOfCareId())
                .postadress(userProfile.getStreetAddress())
                .postnummer(userProfile.getZip())
                .postort(userProfile.getCity())
                .build()
        )
        .result(Result.OK)
        .build();
  }
}