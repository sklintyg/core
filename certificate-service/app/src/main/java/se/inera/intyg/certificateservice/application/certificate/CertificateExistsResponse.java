package se.inera.intyg.certificateservice.application.certificate;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CertificateExistsResponse {

  Boolean exists;
}
