package se.inera.intyg.certificateservice.application.certificatetypeinfo.dto;


import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CertificateTypeInfoDTO {

  String id;
  String label;
  String issuerTypeId;
  String description;
  String detailedDescription;
  List<ResourceLinkDTO> links;
  String message;
}
