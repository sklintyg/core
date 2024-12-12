package se.inera.intyg.certificateservice.certificate.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PrintCertificateRequestDTO {

  private List<PrintCertificateCategoryDTO> categories;
  private PrintCertificateMetadataDTO metadata;
}
