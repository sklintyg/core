package se.inera.intyg.certificateservice.domain.patient.model;

import java.util.List;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;

@Value
@Builder
public class CertificatesWithQARequest {

  List<CertificateId> certificateIds;
}
