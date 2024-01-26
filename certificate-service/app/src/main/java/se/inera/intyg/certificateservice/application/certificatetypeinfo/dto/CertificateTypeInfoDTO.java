package se.inera.intyg.certificateservice.application.certificatetypeinfo.dto;


import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import se.inera.intyg.certificateservice.application.common.dto.ResourceLinkDTO;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CertificateTypeInfoDTO {

  private String type;
  private String name;
  private String description;
  private List<ResourceLinkDTO> links;
}
