package se.inera.intyg.certificateservice.application.common.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.common.dto.UserDTO.UserDTOBuilder;

@JsonDeserialize(builder = UserDTOBuilder.class)
@Value
@Builder
public class UserDTO {

  String id;
  String firstName;
  String middleName;
  String lastName;
  String fullName;
  RoleTypeDTO role;
  Boolean blocked;

  @JsonPOJOBuilder(withPrefix = "")
  public static class UserDTOBuilder {

  }
}
