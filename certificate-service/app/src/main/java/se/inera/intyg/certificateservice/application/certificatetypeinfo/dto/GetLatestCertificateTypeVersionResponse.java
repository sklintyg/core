package se.inera.intyg.certificateservice.application.certificatetypeinfo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetLatestCertificateTypeVersionResponse {

  private CertificateModelIdDTO certificateModelId;
}
