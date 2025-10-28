package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.schematron;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7804_SCHEMATRON_PATH;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionFactory;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.diagnosiscode.repository.DiagnosisCodeRepository;
import se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.CertificateModelFactoryFK7804;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorBoolean;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorCertificateV4;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorCode;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorCodeList;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorDateList;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorDateRangeList;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorDiagnosisList;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorIcfValue;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorInteger;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorText;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorUnifiedDiagnosisList;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorValue;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.validation.SchemaValidatorV4;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.validation.SchematronValidator;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.validation.XmlValidationService;

@ExtendWith(MockitoExtension.class)
class SchematronValidationFK7804Test {

  @Mock
  private CertificateActionFactory certificateActionFactory;
  @Mock
  private DiagnosisCodeRepository diagnosisCodeRepository;

  private SchematronValidator schematronValidator;
  private XmlGeneratorCertificateV4 generator;
  private CertificateModelFactoryFK7804 certificateModelFactoryFK7804;

  @BeforeEach
  void setUp() {
    schematronValidator = new SchematronValidator();
    certificateModelFactoryFK7804 = new CertificateModelFactoryFK7804(certificateActionFactory,
        diagnosisCodeRepository);
    generator = new XmlGeneratorCertificateV4(
        new XmlGeneratorValue(
            List.of(
                new XmlGeneratorDateRangeList(),
                new XmlGeneratorText(),
                new XmlGeneratorBoolean(),
                new XmlGeneratorCode(),
                new XmlGeneratorCodeList(),
                new XmlGeneratorDateList(),
                new XmlGeneratorDiagnosisList(),
                new XmlGeneratorIcfValue(),
                new XmlGeneratorInteger()
            ),
            List.of(
                new XmlGeneratorUnifiedDiagnosisList(diagnosisCodeRepository)
            )
        ),
        new XmlValidationService(
            new SchematronValidator(),
            new SchemaValidatorV4()
        )
    );
  }

  @Test
  void shallReturnTrueIfAllFieldsHaveValues() {

    final var certificate = TestDataCertificate.fk7804CertificateBuilder()
        .certificateModel(certificateModelFactoryFK7804.create())
        .build();

    final var xml = generator.generate(certificate, false);

    assertTrue(schematronValidator.validate(certificate.id(), xml,
        FK7804_SCHEMATRON_PATH)
    );
  }

  @Nested
  class QuestionGrundForMedicinsktUnderlag {

    @Test
    void shallReturnFalseIfGrundForMedicinsktUnderlagIsMissing() {
      final var certificate = TestDataCertificate.fk7804CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7804.create())
          .elementData(
              TestDataCertificate.fk7804CertificateBuilder().build().elementData().stream()
                  .filter(e -> !e.id().equals(new ElementId("1")))
                  .toList()
          )
          .build();

      final var xml = generator.generate(certificate, false);

      assertFalse(schematronValidator.validate(certificate.id(), xml,
          FK7804_SCHEMATRON_PATH));
    }
  }

  @Nested
  class QuestionSjukskrivning {

    @Test
    void shallReturnFalseIfSjukskrivningIsMissing() {
      final var certificate = TestDataCertificate.fk7804CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7804.create())
          .elementData(
              TestDataCertificate.fk7804CertificateBuilder().build().elementData().stream()
                  .filter(e -> !e.id().equals(new ElementId("32")))
                  .toList()
          )
          .build();

      final var xml = generator.generate(certificate, false);

      assertFalse(schematronValidator.validate(certificate.id(), xml,
          FK7804_SCHEMATRON_PATH));
    }
  }

  @Nested
  class QuestionFunktionsnedsattning {

    @Test
    void shallReturnFalseIfFunktionsnedsattningIsMissing() {
      final var certificate = TestDataCertificate.fk7804CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7804.create())
          .elementData(
              TestDataCertificate.fk7804CertificateBuilder().build().elementData().stream()
                  .filter(e -> !e.id().equals(new ElementId("35")))
                  .toList()
          )
          .build();

      final var xml = generator.generate(certificate, false);

      assertFalse(schematronValidator.validate(certificate.id(), xml,
          FK7804_SCHEMATRON_PATH));
    }

    @Test
    void shallReturnFalseIfFunktionsnedsattningIsBlank() {
      final var invalidElement = ElementData.builder()
          .id(new ElementId("35"))
          .value(
              se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText.builder()
                  .textId(new FieldId("35.1"))
                  .text("")
                  .build()
          )
          .build();
      final var certificate = TestDataCertificate.fk7804CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7804.create())
          .elementData(
              TestDataCertificate.fk7804CertificateBuilder().build().elementData().stream()
                  .map(e -> e.id().equals(new ElementId("35")) ? invalidElement : e)
                  .toList()
          )
          .build();

      final var xml = generator.generate(certificate, false);

      assertFalse(schematronValidator.validate(certificate.id(), xml,
          FK7804_SCHEMATRON_PATH));
    }
  }

  @Nested
  class QuestionAktivitetsbegransningar {

    @Test
    void shallReturnFalseIfAktivitetsbegransningarIsMissing() {
      final var certificate = TestDataCertificate.fk7804CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7804.create())
          .elementData(
              TestDataCertificate.fk7804CertificateBuilder().build().elementData().stream()
                  .filter(e -> !e.id().equals(new ElementId("17")))
                  .toList()
          )
          .build();

      final var xml = generator.generate(certificate, false);

      assertFalse(schematronValidator.validate(certificate.id(), xml,
          FK7804_SCHEMATRON_PATH));
    }

    @Test
    void shallReturnFalseIfAktivitetsbegransningarIsBlank() {
      final var invalidElement = ElementData.builder()
          .id(new ElementId("17"))
          .value(
              se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText.builder()
                  .textId(new FieldId("17.1"))
                  .text("")
                  .build()
          )
          .build();
      final var certificate = TestDataCertificate.fk7804CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7804.create())
          .elementData(
              TestDataCertificate.fk7804CertificateBuilder().build().elementData().stream()
                  .map(e -> e.id().equals(new ElementId("17")) ? invalidElement : e)
                  .toList()
          )
          .build();

      final var xml = generator.generate(certificate, false);

      assertFalse(schematronValidator.validate(certificate.id(), xml,
          FK7804_SCHEMATRON_PATH));
    }
  }

  @Nested
  class QuestionPrognos {

    @Test
    void shallReturnFalseIfPrognosIsMissing() {
      final var certificate = TestDataCertificate.fk7804CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7804.create())
          .elementData(
              TestDataCertificate.fk7804CertificateBuilder().build().elementData().stream()
                  .filter(e -> !e.id().equals(new ElementId("39")))
                  .toList()
          )
          .build();

      final var xml = generator.generate(certificate, false);

      assertFalse(schematronValidator.validate(certificate.id(), xml,
          FK7804_SCHEMATRON_PATH));
    }
  }

  @Nested
  class QuestionTypAvSysselsattning {

    @Test
    void shallReturnFalseIfTypAvSysselsattningIsMissing() {
      final var certificate = TestDataCertificate.fk7804CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7804.create())
          .elementData(
              TestDataCertificate.fk7804CertificateBuilder().build().elementData().stream()
                  .filter(e -> !e.id().equals(new ElementId("28")))
                  .toList()
          )
          .build();

      final var xml = generator.generate(certificate, false);

      assertFalse(schematronValidator.validate(certificate.id(), xml,
          FK7804_SCHEMATRON_PATH));
    }
  }

  @Nested
  class QuestionNuvarandeArbete {

    @Test
    void shallReturnFalseIfNuvarandeArbeteIsPresentWithoutTypAvSysselsattningNuvarandeArbete() {
      final var nuvarandeArbete = ElementData.builder()
          .id(new ElementId("29"))
          .value(
              se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText.builder()
                  .textId(new FieldId("29.1"))
                  .text("Arbete")
                  .build()
          )
          .build();
      final var baseCertificate = TestDataCertificate.fk7804CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7804.create())
          .build();
      final var newElementData = new ArrayList<>(baseCertificate.elementData());
      newElementData.add(nuvarandeArbete);
      final var updatedCertificate = TestDataCertificate.fk7804CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7804.create())
          .elementData(newElementData)
          .build();

      final var xml = generator.generate(updatedCertificate, false);

      assertFalse(schematronValidator.validate(updatedCertificate.id(), xml,
          FK7804_SCHEMATRON_PATH));
    }
  }

  @Nested
  class QuestionArbetstidsforlaggning {

    @Test
    void shallReturnFalseIfArbetstidsforlaggningIsMissingWhenRequired() {
      // Remove 33 if sjukskrivningsnivå is TRE_FJARDEDEL, HALFTEN, or EN_FJARDEDEL
      final var certificate = TestDataCertificate.fk7804CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7804.create())
          .elementData(
              TestDataCertificate.fk7804CertificateBuilder().build().elementData().stream()
                  .filter(e -> !e.id().equals(new ElementId("33")))
                  .toList()
          )
          .build();

      final var xml = generator.generate(certificate, false);

      assertFalse(schematronValidator.validate(certificate.id(), xml,
          FK7804_SCHEMATRON_PATH));
    }
  }

  @Nested
  class QuestionSmittbararpenning {

    @Test
    void shallReturnTrueIfSmittbararpenningIsMissing() {
      final var certificate = TestDataCertificate.fk7804CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7804.create())
          .elementData(
              TestDataCertificate.fk7804CertificateBuilder().build().elementData().stream()
                  .filter(e -> !e.id().equals(new ElementId("27")))
                  .toList()
          )
          .build();

      final var xml = generator.generate(certificate, false);

      assertTrue(schematronValidator.validate(certificate.id(), xml,
          FK7804_SCHEMATRON_PATH));
    }
  }

  @Nested
  class QuestionDiagnos {

    @Test
    void shallReturnFalseIfDiagnosIsMissing() {
      final var certificate = TestDataCertificate.fk7804CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7804.create())
          .elementData(
              TestDataCertificate.fk7804CertificateBuilder().build().elementData().stream()
                  .filter(e -> !e.id().equals(new ElementId("6")))
                  .toList()
          )
          .build();

      final var xml = generator.generate(certificate, false);

      assertFalse(schematronValidator.validate(certificate.id(), xml,
          FK7804_SCHEMATRON_PATH));
    }
  }
}

