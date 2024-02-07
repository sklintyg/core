package se.inera.intyg.certificateservice.testability.certificate.dto;

import java.util.List;
import lombok.Builder;
import lombok.Value;
import lombok.With;

@Value
@Builder
public class SupportedCertificateTypesResponse {

  String type;
  String internalType;
  @With
  List<String> versions;
  String name;
  List<TestabilityStatusTypeDTO> statuses;
  List<TestabilityFillTypeDTO> fillType;
}
