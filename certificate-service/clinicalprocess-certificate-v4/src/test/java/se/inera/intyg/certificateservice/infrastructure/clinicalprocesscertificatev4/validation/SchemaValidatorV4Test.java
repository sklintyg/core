package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.validation;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorCertificateV4;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorValue;

class SchemaValidatorV4Test {

  private SchemaValidatorV4 schemaValidatorV4;

  @BeforeEach
  void setUp() {
    schemaValidatorV4 = new SchemaValidatorV4();
  }

  @Test
  void shallValidateSchema() {
    final var certificate = TestDataCertificate.fk7210CertificateBuilder().build();
    final var generator = new XmlGeneratorCertificateV4(
        new XmlGeneratorValue(),
        new XmlValidationService(
            new SchematronValidator(),
            new SchemaValidatorV4()
        )
    );
    final var xml = generator.generate(certificate, true);
    assertTrue(
        schemaValidatorV4.validate(
            certificate.id(),
            xml
        )
    );
  }
}
