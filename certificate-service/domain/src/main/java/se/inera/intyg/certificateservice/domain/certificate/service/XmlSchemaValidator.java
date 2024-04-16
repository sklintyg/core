package se.inera.intyg.certificateservice.domain.certificate.service;

import se.inera.intyg.certificateservice.domain.certificate.model.Xml;

public interface XmlSchemaValidator {

  boolean validate(String certificateId, Xml xml);

}
