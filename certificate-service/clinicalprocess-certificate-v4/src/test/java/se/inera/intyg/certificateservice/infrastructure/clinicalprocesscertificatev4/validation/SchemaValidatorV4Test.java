package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.validation;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7210.CertificateModelFactoryFK7210;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorCertificateV4;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorDate;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorDateRangeList;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorText;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorValue;

class SchemaValidatorV4Test {

  private SchemaValidatorV4 schemaValidatorV4;
  private CertificateModelFactoryFK7210 certificateModelFactoryFK7210;

  @BeforeEach
  void setUp() {
    schemaValidatorV4 = new SchemaValidatorV4();
    certificateModelFactoryFK7210 = new CertificateModelFactoryFK7210();
  }

  @Test
  void shallValidateSchema() {
    final var certificate = TestDataCertificate.fk7210CertificateBuilder()
        .certificateModel(certificateModelFactoryFK7210.create())
        .build();

    final var generator = new XmlGeneratorCertificateV4(
        new XmlGeneratorValue(
            List.of(new XmlGeneratorDate(), new XmlGeneratorDateRangeList(), new XmlGeneratorText())
        ),
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
