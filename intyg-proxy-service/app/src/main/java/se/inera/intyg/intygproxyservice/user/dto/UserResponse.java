package se.inera.intyg.intygproxyservice.user.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.intygproxyservice.user.dto.UserResponse.UserResponseBuilder;

@JsonDeserialize(builder = UserResponseBuilder.class)
@Value
@Builder
public class UserResponse {

  UserDTO user;

  @JsonPOJOBuilder(withPrefix = "")
  public static class UserResponseBuilder {

  }
}