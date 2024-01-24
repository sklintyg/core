package se.inera.intyg.certificateservice.application.certificatetypeinfo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

  String id;
  RoleTypeDTO role;
  Boolean subscription;
  Boolean blocked;
}
