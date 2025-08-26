package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;

@Value
@Builder
public class PlaceholderRequest {

  CertificateModelId certificateModelId;
  CertificateId certificateId;
  Status status;
}