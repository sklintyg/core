package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.validation;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Objects;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.Xml;

@Slf4j
@RequiredArgsConstructor
public class SchemaValidatorV4 implements XmlSchemaValidator {

  private static final String XSD_PATH_V4 = "schemas/certificate/4.0/interactions/RegisterCertificateInteraction/RegisterCertificateResponder_3.1.xsd";

  @Override
  public boolean validate(CertificateId certificateId, Xml xml) {
    try {
      final var factory = SchemaFactory.newDefaultInstance();
      factory.setResourceResolver(new SchemaResourceResolverV4());

      final var schema = factory.newSchema(getFileStreamSource());
      final var validator = schema.newValidator();
      final var xsdErrorHandler = new SchemaValidatorErrorHandler();
      validator.setErrorHandler(xsdErrorHandler);
      validator.validate(
          new StreamSource(new ByteArrayInputStream(xml.xml().getBytes()))
      );
      return schemaValidationResult(xsdErrorHandler, certificateId.id());
    } catch (Exception e) {
      log.error("Validation failed", e);
      return false;
    }
  }

  private StreamSource getFileStreamSource() throws IOException {
    final var classLoader = getClass().getClassLoader();
    try (final var inputStream = classLoader.getResourceAsStream(XSD_PATH_V4)) {
      return new StreamSource(
          new ByteArrayInputStream(Objects.requireNonNull(inputStream).readAllBytes()));
    }
  }

  private boolean schemaValidationResult(SchemaValidatorErrorHandler xsdErrorHandler,
      String certificateId) {
    final var exceptions = xsdErrorHandler.getExceptions();
    if (exceptions.isEmpty()) {
      log.info("Schema validation passed for certificate id {}.", certificateId);
      return true;
    } else {
      final var failures = exceptions.stream()
          .map(e -> "Line number: %s, Column number: %s. %s".formatted(e.getLineNumber(),
              e.getColumnNumber(), e.getMessage()))
          .toList();
      log.warn("Schema validation failed for certificate id {}.\n{}", certificateId, failures);
      return false;
    }
  }
}
