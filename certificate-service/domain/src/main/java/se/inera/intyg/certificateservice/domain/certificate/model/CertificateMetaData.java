package se.inera.intyg.certificateservice.domain.certificate.model;

import lombok.Builder;
import se.inera.intyg.certificateservice.domain.patient.model.Patient;


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
