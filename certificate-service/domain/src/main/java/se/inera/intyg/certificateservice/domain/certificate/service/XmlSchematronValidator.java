package se.inera.intyg.certificateservice.domain.certificate.service;

import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.Xml;

public interface XmlSchematronValidator {

  boolean validate(CertificateId certificateId, Xml xml, String schematronPath);

}
