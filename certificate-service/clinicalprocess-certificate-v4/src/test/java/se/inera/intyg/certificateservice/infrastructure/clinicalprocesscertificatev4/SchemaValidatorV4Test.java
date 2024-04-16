package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate;

class SchemaValidatorV4Test {

  private SchemaValidatorV4 schemaValidatorV4;

  @BeforeEach
  void setUp() {
    schemaValidatorV4 = new SchemaValidatorV4();
  }

  @Test
  void shallValidateSchema() {
    final var certificate = TestDataCertificate.fk7211CertificateBuilder().build();
    final var generator = new XmlGeneratorCertificateV4(
        new XmlGeneratorValue(),
        new XmlGeneratorIntygsgivare()
    );
    final var xml = generator.generate(certificate);
    assertTrue(
        schemaValidatorV4.validate(
            certificate.id().id(),
            xml
        )
    );
  }
}
