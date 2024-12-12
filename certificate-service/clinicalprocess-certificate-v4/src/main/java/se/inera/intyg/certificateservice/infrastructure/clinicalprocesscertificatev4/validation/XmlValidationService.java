package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.validation;

import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.Xml;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.SchematronPath;

@RequiredArgsConstructor
public class XmlValidationService {

  private final XmlSchematronValidator xmlSchematronValidator;
  private final XmlSchemaValidator xmlSchemaValidator;

  public void validate(Xml xml, SchematronPath schematronPath, CertificateId certificateId) {
    validateParameters(xml, certificateId);
    final var schemaValidation = xmlSchemaValidator.validate(certificateId, xml);

    final var schematronValidation = schematronPath == null || xmlSchematronValidator.validate(
        certificateId,
        xml,
        schematronPath
    );

    if (!schematronValidation || !schemaValidation) {
      throw new IllegalStateException(
          ("Certificate did not pass schematron validation."
              + " Schematron validation result: '%s'. "
              + "Schema validation result: '%s'"
          ).formatted(
              schematronValidation, schemaValidation
          )
      );
    }
  }

  private void validateParameters(Xml xml, CertificateId certificateId) {
    if (xml == null || xml.xml() == null || xml.xml().isBlank()) {
      throw new IllegalArgumentException(
          "Missing required parameter xml: '%s'".formatted(xml)
      );
    }
    if (certificateId == null || certificateId.id().isBlank()) {
      throw new IllegalArgumentException(
          "Missing required parameter certificateId: '%s'".formatted(certificateId)
      );
    }
  }
}
