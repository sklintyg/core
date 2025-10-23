package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.validation;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionFactory;
import se.inera.intyg.certificateservice.domain.diagnosiscode.repository.DiagnosisCodeRepository;
import se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7210.CertificateModelFactoryFK7210;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorCertificateV4;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorCodeListToBoolean;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorDate;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorDateRangeList;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorText;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorUnifiedDiagnosisList;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorValue;

@ExtendWith(MockitoExtension.class)
class SchemaValidatorV4Test {

  @Mock
  private CertificateActionFactory certificateActionFactory;
  @Mock
  private DiagnosisCodeRepository diagnosisCodeRepository;
  private SchemaValidatorV4 schemaValidatorV4;
  private CertificateModelFactoryFK7210 certificateModelFactoryFK7210;

  @BeforeEach
  void setUp() {
    schemaValidatorV4 = new SchemaValidatorV4();
    certificateModelFactoryFK7210 = new CertificateModelFactoryFK7210(certificateActionFactory);
  }

  @Test
  void shallValidateSchema() {
    final var certificate = TestDataCertificate.fk7210CertificateBuilder()
        .certificateModel(certificateModelFactoryFK7210.create())
        .build();

    final var generator = new XmlGeneratorCertificateV4(
        new XmlGeneratorValue(
            List.of(new XmlGeneratorDate(), new XmlGeneratorDateRangeList(),
                new XmlGeneratorText()),
            List.of(new XmlGeneratorCodeListToBoolean(),
                new XmlGeneratorUnifiedDiagnosisList(diagnosisCodeRepository))
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