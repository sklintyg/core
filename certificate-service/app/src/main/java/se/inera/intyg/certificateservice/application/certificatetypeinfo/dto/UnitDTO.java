package se.inera.intyg.certificateservice.application.certificatetypeinfo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnitDTO {

  private String id;
  private String name;
  private String address;
  private String zipCode;
  private String city;
  private String phoneNumber;
  private String email;
  private Boolean isInactive;
}
