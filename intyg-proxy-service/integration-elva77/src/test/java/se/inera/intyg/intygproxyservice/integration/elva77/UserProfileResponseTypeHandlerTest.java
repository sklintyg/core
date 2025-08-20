package se.inera.intyg.intygproxyservice.integration.elva77;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.integration.api.elva77.Elva77Response;
import se.inera.intyg.intygproxyservice.integration.api.elva77.Result;
import se.inera.intyg.intygproxyservice.integration.elva77.converter.UserProfileResponseTypeConverter;
import se.mkv.itintegration.getuserprofileresponder.v2.GetUserProfileResponseType;
import se.mkv.itintegration.userprofile.v2.UserProfileType;

@ExtendWith(MockitoExtension.class)
class UserProfileResponseTypeHandlerTest {

  @Mock
  UserProfileResponseTypeConverter userProfileResponseTypeConverter;
  @InjectMocks
  UserProfileResponseTypeHandler userProfileResponseTypeHandler;

  @Test
  void shallReturnErrorResponseIfIsActiveIsNull() {
    final var responseType = new GetUserProfileResponseType();
    responseType.setIsActive(null);
    responseType.setUserProfile(new UserProfileType());

    final var response = userProfileResponseTypeHandler.handle(responseType);
    assertEquals(Result.ERROR, response.getResult());
  }


  @Test
  void shallReturnErrorResponseIfIsUserProfileIsNull() {
    final var responseType = new GetUserProfileResponseType();
    responseType.setIsActive(true);
    responseType.setUserProfile(null);

    final var response = userProfileResponseTypeHandler.handle(responseType);
    assertEquals(Result.ERROR, response.getResult());
  }

  @Test
  void shouldReturnConvertedElva77Response() {
    final var expectedResponse = Elva77Response.builder()
        .build();

    final var responseType = new GetUserProfileResponseType();
    responseType.setIsActive(true);
    responseType.setUserProfile(new UserProfileType());

    when(userProfileResponseTypeConverter.convert(responseType)).thenReturn(expectedResponse);

    final var response = userProfileResponseTypeHandler.handle(responseType);
    assertEquals(expectedResponse, response);
  }
}