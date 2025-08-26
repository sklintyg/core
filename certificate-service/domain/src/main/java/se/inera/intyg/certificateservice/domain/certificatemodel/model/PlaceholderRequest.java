package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;

@Value
@Builder
public class PlaceholderRequest {

  CertificateModelId certificateModelId;
  CertificateId certificateId;
  LocalDateTime created;
  Status status;
  long version;
}