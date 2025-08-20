package se.inera.intyg.intygproxyservice.integration.elva77.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.intygproxyservice.integration.api.elva77.Citizen;
import se.inera.intyg.intygproxyservice.integration.api.elva77.Result;
import se.mkv.itintegration.getuserprofileresponder.v2.GetUserProfileResponseType;
import se.mkv.itintegration.userprofile.v2.UserProfileType;

class UserProfileResponseTypeConverterTest {

  private UserProfileResponseTypeConverter userProfileResponseTypeConverter;

  @BeforeEach
  void setUp() {
    userProfileResponseTypeConverter = new UserProfileResponseTypeConverter();
  }

  @Nested
  class IsActiveFalseTests {

    @Test
    void shouldReturnResultInfo() {
      final var responseType = new GetUserProfileResponseType();
      responseType.setIsActive(false);
      responseType.setUserProfile(new UserProfileType());

      final var result = userProfileResponseTypeConverter.convert(responseType);
      assertEquals(Result.INFO, result.getResult());
    }

    @Test
    void shouldContainSubjectOfCareIdAndSetIsActiveToFalse() {
      final var expectedId = "expectedId";

      final var expectedCitizen = Citizen.builder()
          .subjectOfCareId(expectedId)
          .isActive(false)
          .build();

      final var responseType = new GetUserProfileResponseType();
      responseType.setIsActive(false);
      final var userProfile = new UserProfileType();
      userProfile.setSubjectOfCareId(expectedId);
      responseType.setUserProfile(userProfile);

      final var result = userProfileResponseTypeConverter.convert(responseType);
      assertEquals(expectedCitizen, result.getCitizen());
    }
  }

  @Nested
  class IsActiveTrueTests {

    @Test
    void shouldReturnResultOk() {
      final var responseType = new GetUserProfileResponseType();
      responseType.setIsActive(true);
      responseType.setUserProfile(new UserProfileType());
      final var result = userProfileResponseTypeConverter.convert(responseType);
      assertEquals(Result.OK, result.getResult());
    }

    @Test
    void shouldContainSubjectOfCareId() {
      final var expectedValue = "expectedId";

      final var responseType = new GetUserProfileResponseType();
      responseType.setIsActive(true);
      final var userProfile = new UserProfileType();
      userProfile.setSubjectOfCareId(expectedValue);
      responseType.setUserProfile(userProfile);

      final var result = userProfileResponseTypeConverter.convert(responseType);
      assertEquals(expectedValue, result.getCitizen().getSubjectOfCareId());
    }

    @Test
    void shouldContainFirstName() {
      final var expectedValue = "expectedName";

      final var responseType = new GetUserProfileResponseType();
      responseType.setIsActive(true);
      final var userProfile = new UserProfileType();
      userProfile.setFirstName(expectedValue);
      responseType.setUserProfile(userProfile);

      final var result = userProfileResponseTypeConverter.convert(responseType);
      assertEquals(expectedValue, result.getCitizen().getFirstname());
    }

    @Test
    void shouldContainLastName() {
      final var expectedValue = "expectedName";

      final var responseType = new GetUserProfileResponseType();
      responseType.setIsActive(true);
      final var userProfile = new UserProfileType();
      userProfile.setLastName(expectedValue);
      responseType.setUserProfile(userProfile);

      final var result = userProfileResponseTypeConverter.convert(responseType);
      assertEquals(expectedValue, result.getCitizen().getLastname());
    }

    @Test
    void shouldContainStreetAddress() {
      final var expectedValue = "expectedStreet";

      final var responseType = new GetUserProfileResponseType();
      responseType.setIsActive(true);
      final var userProfile = new UserProfileType();
      userProfile.setStreetAddress(expectedValue);
      responseType.setUserProfile(userProfile);

      final var result = userProfileResponseTypeConverter.convert(responseType);
      assertEquals(expectedValue, result.getCitizen().getStreetAddress());
    }

    @Test
    void shouldContainZipCode() {
      final var expectedValue = "expectedZip";

      final var responseType = new GetUserProfileResponseType();
      responseType.setIsActive(true);
      final var userProfile = new UserProfileType();
      userProfile.setZip(expectedValue);
      responseType.setUserProfile(userProfile);

      final var result = userProfileResponseTypeConverter.convert(responseType);
      assertEquals(expectedValue, result.getCitizen().getZip());
    }

    @Test
    void shouldContainCity() {
      final var expectedValue = "expectedCity";

      final var responseType = new GetUserProfileResponseType();
      responseType.setIsActive(true);
      final var userProfile = new UserProfileType();
      userProfile.setCity(expectedValue);
      responseType.setUserProfile(userProfile);

      final var result = userProfileResponseTypeConverter.convert(responseType);
      assertEquals(expectedValue, result.getCitizen().getCity());
    }

    @Test
    void shouldContainIsActive() {
      final var responseType = new GetUserProfileResponseType();
      responseType.setIsActive(true);
      responseType.setUserProfile(new UserProfileType());

      final var result = userProfileResponseTypeConverter.convert(responseType);
      assertTrue(result.getCitizen().getIsActive());
    }
  }
}