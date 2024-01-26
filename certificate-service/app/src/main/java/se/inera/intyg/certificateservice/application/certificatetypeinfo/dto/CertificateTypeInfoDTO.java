package se.inera.intyg.certificateservice.application.certificatetypeinfo.dto;


import java.util.List;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.common.dto.ResourceLinkDTO;

@Value
@Builder
public class CertificateTypeInfoDTO {

  String type;
  String name;
  String description;
  List<ResourceLinkDTO> links;
}
