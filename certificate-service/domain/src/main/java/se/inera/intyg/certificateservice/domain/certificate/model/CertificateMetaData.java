package se.inera.intyg.certificateservice.domain.certificate.model;

import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.patient.model.Patient;


@Value
@Builder
public class CertificateMetaData {

  Patient patient;
  Staff issuer;
  IssuingUnit issuingUnit;
  CareUnit careUnit;
  CareProvider careProvider;

}
