package se.inera.intyg.certificateservice.application.certificatetypeinfo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientDTO {

  private PersonIdDTO id;
  private String firstName;
  private String lastName;
  private String middleName;
  private String fullName;
  private String street;
  private String city;
  private String zipCode;
  private boolean testIndicated;
  private boolean protectedPerson;
  private boolean deceased;
}
