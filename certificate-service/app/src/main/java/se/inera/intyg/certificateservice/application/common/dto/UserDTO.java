package se.inera.intyg.certificateservice.application.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

  String name;
  String id;
  RoleTypeDTO role;
  Boolean blocked;
}
