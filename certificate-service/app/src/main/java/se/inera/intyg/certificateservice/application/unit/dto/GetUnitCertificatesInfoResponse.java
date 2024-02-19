package se.inera.intyg.certificateservice.application.unit.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.StaffDTO;
import se.inera.intyg.certificateservice.application.unit.dto.GetUnitCertificatesInfoResponse.GetUnitCertificatesInfoResponseBuilder;

@JsonDeserialize(builder = GetUnitCertificatesInfoResponseBuilder.class)
@Value
@Builder
public class GetUnitCertificatesInfoResponse {

  List<StaffDTO> staffs;

  @JsonPOJOBuilder(withPrefix = "")
  public static class GetUnitCertificatesInfoResponseBuilder {

  }
}
