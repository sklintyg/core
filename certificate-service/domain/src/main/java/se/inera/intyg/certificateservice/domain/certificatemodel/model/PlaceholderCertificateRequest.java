package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.unit.model.SubUnit;
import se.inera.intyg.certificateservice.domain.certificate.model.Xml;

@Value
@Builder
public class PlaceholderCertificateRequest {

  CertificateModelId certificateModelId;
  CertificateId certificateId;
  Status status;
  SubUnit issuingUnit;
  Xml prefillXml;
}