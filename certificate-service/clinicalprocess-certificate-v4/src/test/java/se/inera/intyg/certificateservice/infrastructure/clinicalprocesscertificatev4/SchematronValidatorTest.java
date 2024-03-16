package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4;


import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate;

class SchematronValidatorTest {

  private SchematronValidator schematronValidator;

  @BeforeEach
  void setUp() {
    schematronValidator = new SchematronValidator();
  }

  @Test
  void name() {
    final var element = ElementData.builder()
        .id(new ElementId("1"))
        .value(
            ElementValueDate.builder()
                .dateId(new FieldId("1.1"))
                .date(LocalDate.now())
                .build()
        ).build();

    final var certificate = TestDataCertificate.fk7211CertificateBuilder()
        .elementData(List.of(element))
        .build();

    final var generator = new XmlGeneratorCertificateV4(new XmlGeneratorValue());
    final var xml = generator.generate(certificate);

    final var certificate1 = TestDataCertificate.fk7211CertificateBuilder()
        .xml(xml)
        .build();
    assertTrue(schematronValidator.validate(certificate1));
  }
}