package se.inera.intyg.certificateservice.application.certificatetypeinfo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetCertificateTypeInfoRequest {

  private UserDTO user;
  private UnitDTO unit;
  private UnitDTO careUnit;
  private UnitDTO careProvider;
  private PatientDTO patient;
}
