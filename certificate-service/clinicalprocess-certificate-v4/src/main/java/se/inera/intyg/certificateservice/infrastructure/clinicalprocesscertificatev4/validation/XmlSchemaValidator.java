package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.validation;

import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.Xml;

public interface XmlSchemaValidator {

  boolean validate(CertificateId certificateId, Xml xml);

}
