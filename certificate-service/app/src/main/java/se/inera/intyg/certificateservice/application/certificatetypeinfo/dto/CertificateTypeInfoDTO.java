package se.inera.intyg.certificateservice.application.certificatetypeinfo.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateConfirmationModalDTO;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.CertificateTypeInfoDTO.CertificateTypeInfoDTOBuilder;
import se.inera.intyg.certificateservice.application.common.dto.ResourceLinkDTO;

@JsonDeserialize(builder = CertificateTypeInfoDTOBuilder.class)
@Value
@Builder
public class CertificateTypeInfoDTO {

  String type;
  String name;
  String description;
  String typeName;
  List<ResourceLinkDTO> links;
  CertificateConfirmationModalDTO confirmationModal;

  @JsonPOJOBuilder(withPrefix = "")
  public static class CertificateTypeInfoDTOBuilder {

  }
}
