package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.validation;

import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.Xml;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.SchematronPath;

public interface XmlSchematronValidator {

  boolean validate(CertificateId certificateId, Xml xml, SchematronPath schematronPath);

}
