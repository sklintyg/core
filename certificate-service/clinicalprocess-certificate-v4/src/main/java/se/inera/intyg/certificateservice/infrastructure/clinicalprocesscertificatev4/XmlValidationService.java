package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4;

import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.certificate.model.Xml;
import se.inera.intyg.certificateservice.domain.certificate.service.XmlSchemaValidator;
import se.inera.intyg.certificateservice.domain.certificate.service.XmlSchematronValidator;

@RequiredArgsConstructor
public class XmlValidationService {

  private final XmlSchematronValidator xmlSchematronValidator;
  private final XmlSchemaValidator xmlSchemaValidator;

  public Xml validate(Xml xml, String schematronPath, String certificateId) {
    return null;
  }
}
