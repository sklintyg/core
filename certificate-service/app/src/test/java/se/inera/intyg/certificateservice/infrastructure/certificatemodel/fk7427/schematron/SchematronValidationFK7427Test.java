package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.schematron;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionFactory;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateRange;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosisList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificate.model.Revision;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.diagnosiscode.repository.DiagnosisCodeRepository;
import se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.CertificateModelFactoryFK7427;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorBoolean;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorCertificateV4;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorCode;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorDate;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorDateList;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorDateRange;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorDiagnosisList;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorText;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorValue;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.validation.SchemaValidatorV4;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.validation.SchematronValidator;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.validation.XmlValidationService;

@ExtendWith({MockitoExtension.class})
public class SchematronValidationFK7427Test {

  private static final ActionEvaluation ACTION_EVALUATION = ActionEvaluation.builder().build();
  @Mock
  private CertificateActionFactory certificateActionFactory;
  @Mock
  private DiagnosisCodeRepository diagnosisCodeRepository;
  private SchematronValidator schematronValidator;
  private final XmlGeneratorCertificateV4 generator = new XmlGeneratorCertificateV4(
      new XmlGeneratorValue(
          List.of(
              new XmlGeneratorDate(),
              new XmlGeneratorText(),
              new XmlGeneratorDateList(),
              new XmlGeneratorBoolean(),
              new XmlGeneratorCode(),
              new XmlGeneratorDiagnosisList(),
              new XmlGeneratorDateRange()
          ),
          Collections.emptyList()
      ),
      new XmlValidationService(
          new SchematronValidator(),
          new SchemaValidatorV4()
      )
  );

  private CertificateModelFactoryFK7427 certificateModelFactoryFK7427;

  @BeforeEach
  void setUp() {
    schematronValidator = new SchematronValidator();
    certificateModelFactoryFK7427 = new CertificateModelFactoryFK7427(certificateActionFactory,
        diagnosisCodeRepository);
  }

  @Test
  void shallReturnTrueForValidCertificate() {
    final var certificate = TestDataCertificate.fk7427CertificateBuilder()
        .certificateModel(certificateModelFactoryFK7427.create())
        .build();

    final var xml = generator.generate(certificate, true);
    assertTrue(schematronValidator.validate(certificate.id(), xml,
        CertificateModelFactoryFK7427.SCHEMATRON_PATH));
  }

  @Nested
  class TestValidateGrundForMU {

    @Test
    void shallReturnFalseIfMissingValues() {
      final var certificate = TestDataCertificate.fk7427CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7427.create())
          .build();

      final var element = certificate.elementData().stream()
          .filter(elementData -> elementData.id().equals(new ElementId("1")))
          .findFirst()
          .orElseThrow();

      final var value = (ElementValueDateList) element.value();
      final var elementData = element.withValue(value.withDateList(Collections.emptyList()));

      final var updatedElementData = certificate.elementData().stream()
          .map(data -> data.id().equals(new ElementId("1")) ? elementData : data)
          .toList();
      certificate.updateData(updatedElementData, new Revision(0), ACTION_EVALUATION);

      final var xml = generator.generate(certificate, false);
      assertFalse(schematronValidator.validate(certificate.id(), xml,
          CertificateModelFactoryFK7427.SCHEMATRON_PATH));
    }

    @Test
    void shallReturnFalseIfMultipleOccurences() {
      final var certificate = TestDataCertificate.fk7427CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7427.create())
          .build();

      final var element = certificate.elementData().stream()
          .filter(elementData -> elementData.id().equals(new ElementId("1")))
          .findFirst()
          .orElseThrow();

      final var value = (ElementValueDateList) element.value();
      final var elementData = element.withValue(value);

      final var updatedElementData = certificate.elementData().stream()
          .map(data -> !data.id().equals(new ElementId("1")) ? elementData : data)
          .toList();
      certificate.updateData(updatedElementData, new Revision(0), ACTION_EVALUATION);

      final var xml = generator.generate(certificate, false);
      assertFalse(schematronValidator.validate(certificate.id(), xml,
          CertificateModelFactoryFK7427.SCHEMATRON_PATH));
    }
  }

  @Nested
  class TestValidateGrundForMUAnnan {

    @Test
    void shallReturnFalseIfTypeAnnatAndMissingValues() {
      final var certificate = TestDataCertificate.fk7427CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7427.create())
          .build();

      final var element = certificate.elementData().stream()
          .filter(elementData -> elementData.id().equals(new ElementId("1")))
          .findFirst()
          .orElseThrow();

      final var value = (ElementValueDateList) element.value();
      final var elementData = element.withValue(
          value.withDateList(
              List.of(
                  ElementValueDate.builder()
                      .dateId(new FieldId("annat"))
                      .date(LocalDate.now())
                      .build()
              )
          )
      );

      final var updatedElementData = certificate.elementData().stream()
          .map(data -> data.id().equals(new ElementId("1")) ? elementData : data)
          .toList();
      certificate.updateData(updatedElementData, new Revision(0), ACTION_EVALUATION);

      final var xml = generator.generate(certificate, false);
      assertFalse(schematronValidator.validate(certificate.id(), xml,
          CertificateModelFactoryFK7427.SCHEMATRON_PATH));
    }
  }

  @Nested
  class TestDiagnos {

    @Test
    void shallReturnFalseIfMissingValuesOnBothBarnetsDiagnosAndSymtom() {
      final var certificate = TestDataCertificate.fk7427CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7427.create())
          .build();

      final var diagnosisId = new ElementId("58");
      final var symtomId = new ElementId("55");

      final var elements = certificate.elementData().stream()
          .filter(elementData -> elementData.id().equals(diagnosisId) || elementData.id()
              .equals(symtomId))
          .collect(Collectors.toMap(ElementData::id, data -> data));

      final var valueSymtom = (ElementValueText) elements.get(symtomId).value();
      final var elementDataSymtom = elements.get(symtomId).withValue(valueSymtom.withText(null));

      final var valueDiagnosis = (ElementValueDiagnosisList) elements.get(diagnosisId).value();
      final var elementDataDiagnosis = elements.get(diagnosisId)
          .withValue(valueDiagnosis.withDiagnoses(Collections.emptyList()));

      final var updatedElementData = certificate.elementData().stream()
          .map(data -> data.id().equals(diagnosisId) ? elementDataDiagnosis : data)
          .map(data -> data.id().equals(symtomId) ? elementDataSymtom : data)
          .toList();

      certificate.updateData(updatedElementData, new Revision(0), ACTION_EVALUATION);

      final var xml = generator.generate(certificate, false);
      assertFalse(schematronValidator.validate(certificate.id(), xml,
          CertificateModelFactoryFK7427.SCHEMATRON_PATH));
    }
  }

  @Nested
  class TestHalsotillstand {

    @Test
    void shallReturnFalseIfMissingValues() {
      final var certificate = TestDataCertificate.fk7427CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7427.create())
          .build();

      final var element = certificate.elementData().stream()
          .filter(elementData -> elementData.id().equals(new ElementId("59")))
          .findFirst()
          .orElseThrow();

      final var value = (ElementValueText) element.value();
      final var elementData = element.withValue(value.withText(null));

      final var updatedElementData = certificate.elementData().stream()
          .map(data -> data.id().equals(new ElementId("59")) ? elementData : data)
          .toList();
      certificate.updateData(updatedElementData, new Revision(0), ACTION_EVALUATION);

      final var xml = generator.generate(certificate, false);
      assertFalse(schematronValidator.validate(certificate.id(), xml,
          CertificateModelFactoryFK7427.SCHEMATRON_PATH));
    }
  }

  @Nested
  class TestVardEllerTillsyn {

    @Test
    void shallReturnFalseIfMissingValues() {
      final var certificate = TestDataCertificate.fk7427CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7427.create())
          .build();

      final var element = certificate.elementData().stream()
          .filter(elementData -> elementData.id().equals(new ElementId("62")))
          .findFirst()
          .orElseThrow();

      final var value = (ElementValueText) element.value();
      final var elementData = element.withValue(value.withText(null));

      final var updatedElementData = certificate.elementData().stream()
          .map(data -> data.id().equals(new ElementId("62")) ? elementData : data)
          .toList();
      certificate.updateData(updatedElementData, new Revision(0), ACTION_EVALUATION);

      final var xml = generator.generate(certificate, false);
      assertFalse(schematronValidator.validate(certificate.id(), xml,
          CertificateModelFactoryFK7427.SCHEMATRON_PATH));
    }

    @Test
    void shallReturnFalseIfMissingValue() {
      final var certificate = TestDataCertificate.fk7427CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7427.create())
          .build();

      final var element = certificate.elementData().stream()
          .filter(elementData -> elementData.id().equals(new ElementId("62.1")))
          .findFirst()
          .orElseThrow();

      final var value = (ElementValueBoolean) element.value();
      final var elementData = element.withValue(value.withValue(null));

      final var updatedElementData = certificate.elementData().stream()
          .map(data -> data.id().equals(new ElementId("62.1")) ? elementData : data)
          .toList();
      certificate.updateData(updatedElementData, new Revision(0), ACTION_EVALUATION);

      final var xml = generator.generate(certificate, false);
      assertFalse(schematronValidator.validate(certificate.id(), xml,
          CertificateModelFactoryFK7427.SCHEMATRON_PATH));
    }
  }

  @Nested
  class TestPeriodVardEllerTillsyn {

    @Test
    void shallReturnFalseIfMissingToValue() {
      final var certificate = TestDataCertificate.fk7427CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7427.create())
          .build();

      final var element = certificate.elementData().stream()
          .filter(elementData -> elementData.id().equals(new ElementId("62.6")))
          .findFirst()
          .orElseThrow();

      final var elementData = element.withValue(
          ElementValueDateRange.builder()
              .id(new FieldId("62.6"))
              .fromDate(LocalDate.now())
              .build()
      );

      final var updatedElementData = certificate.elementData().stream()
          .map(data -> data.id().equals(new ElementId("62.6")) ? elementData : data)
          .toList();
      certificate.updateData(updatedElementData, new Revision(0), ACTION_EVALUATION);

      final var xml = generator.generate(certificate, false);
      assertFalse(schematronValidator.validate(certificate.id(), xml,
          CertificateModelFactoryFK7427.SCHEMATRON_PATH));
    }

    @Test
    void shallReturnFalseIfMissingFromValue() {
      final var certificate = TestDataCertificate.fk7427CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7427.create())
          .build();

      final var element = certificate.elementData().stream()
          .filter(elementData -> elementData.id().equals(new ElementId("62.6")))
          .findFirst()
          .orElseThrow();

      final var elementData = element.withValue(
          ElementValueDateRange.builder()
              .id(new FieldId("62.6"))
              .toDate(LocalDate.now())
              .build()
      );

      final var updatedElementData = certificate.elementData().stream()
          .map(data -> data.id().equals(new ElementId("62.6")) ? elementData : data)
          .toList();
      certificate.updateData(updatedElementData, new Revision(0), ACTION_EVALUATION);

      final var xml = generator.generate(certificate, false);
      assertFalse(schematronValidator.validate(certificate.id(), xml,
          CertificateModelFactoryFK7427.SCHEMATRON_PATH));
    }

    @Test
    void shallReturnFalseIfInvalidDateRange() {
      final var certificate = TestDataCertificate.fk7427CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7427.create())
          .build();

      final var element = certificate.elementData().stream()
          .filter(elementData -> elementData.id().equals(new ElementId("62.6")))
          .findFirst()
          .orElseThrow();

      final var elementData = element.withValue(
          ElementValueDateRange.builder()
              .id(new FieldId("62.6"))
              .fromDate(LocalDate.of(2023, 12, 31))
              .toDate(LocalDate.of(2023, 1, 1))
              .build()
      );

      final var updatedElementData = certificate.elementData().stream()
          .map(data -> data.id().equals(new ElementId("62.6")) ? elementData : data)
          .toList();
      certificate.updateData(updatedElementData, new Revision(0), ACTION_EVALUATION);

      final var xml = generator.generate(certificate, false);
      assertFalse(schematronValidator.validate(certificate.id(), xml,
          CertificateModelFactoryFK7427.SCHEMATRON_PATH));
    }
  }

  @Nested
  class TestPeriodInneliggandePaSjukhus {

    @Test
    void shallReturnFalseIfMissingToValue() {
      final var certificate = TestDataCertificate.fk7427CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7427.create())
          .build();

      final var element = certificate.elementData().stream()
          .filter(elementData -> elementData.id().equals(new ElementId("62.2")))
          .findFirst()
          .orElseThrow();

      final var elementData = element.withValue(
          ElementValueDateRange.builder()
              .id(new FieldId("62.2"))
              .fromDate(LocalDate.now())
              .build()
      );

      final var updatedElementData = certificate.elementData().stream()
          .map(data -> data.id().equals(new ElementId("62.2")) ? elementData : data)
          .toList();
      certificate.updateData(updatedElementData, new Revision(0), ACTION_EVALUATION);

      final var xml = generator.generate(certificate, false);
      assertFalse(schematronValidator.validate(certificate.id(), xml,
          CertificateModelFactoryFK7427.SCHEMATRON_PATH));
    }

    @Test
    void shallReturnFalseIfMissingFromValue() {
      final var certificate = TestDataCertificate.fk7427CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7427.create())
          .build();

      final var element = certificate.elementData().stream()
          .filter(elementData -> elementData.id().equals(new ElementId("62.2")))
          .findFirst()
          .orElseThrow();

      final var elementData = element.withValue(
          ElementValueDateRange.builder()
              .id(new FieldId("62.2"))
              .toDate(LocalDate.now())
              .build()
      );

      final var updatedElementData = certificate.elementData().stream()
          .map(data -> data.id().equals(new ElementId("62.2")) ? elementData : data)
          .toList();
      certificate.updateData(updatedElementData, new Revision(0), ACTION_EVALUATION);

      final var xml = generator.generate(certificate, false);
      assertFalse(schematronValidator.validate(certificate.id(), xml,
          CertificateModelFactoryFK7427.SCHEMATRON_PATH));
    }

    @Test
    void shallReturnFalseIfInvalidDateRange() {
      final var certificate = TestDataCertificate.fk7427CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7427.create())
          .build();

      final var element = certificate.elementData().stream()
          .filter(elementData -> elementData.id().equals(new ElementId("62.2")))
          .findFirst()
          .orElseThrow();

      final var elementData = element.withValue(
          ElementValueDateRange.builder()
              .id(new FieldId("62.2"))
              .fromDate(LocalDate.of(2023, 12, 31))
              .toDate(LocalDate.of(2023, 1, 1))
              .build()
      );

      final var updatedElementData = certificate.elementData().stream()
          .map(data -> data.id().equals(new ElementId("62.2")) ? elementData : data)
          .toList();
      certificate.updateData(updatedElementData, new Revision(0), ACTION_EVALUATION);

      final var xml = generator.generate(certificate, false);
      assertFalse(schematronValidator.validate(certificate.id(), xml,
          CertificateModelFactoryFK7427.SCHEMATRON_PATH));
    }
  }
}