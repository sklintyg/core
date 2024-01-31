package se.inera.intyg.certificateservice.domain.certificate.model;

import lombok.Builder;
import lombok.Getter;
import se.inera.intyg.certificateservice.domain.patient.model.Patient;


@Getter
@Builder
public class CertificateMetaData {

  private Patient patient;
  private Staff issuer;
  private IssuingUnit issuingUnit;
  private CareUnit careUnit;
  private CareProvider careProvider;

  public void patient(Patient patient) {
    this.patient = patient;
  }
}
