package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.schematron;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.CertificateModelFactoryFK7810.SCHEMATRON_PATH;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionFactory;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosis;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosisList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueMedicalInvestigationList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalInvestigation;
import se.inera.intyg.certificateservice.domain.certificate.model.Revision;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.diagnosiscode.repository.DiagnosisCodeRepository;
import se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.CertificateModelFactoryFK7810;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorBoolean;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorCertificateV4;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorCode;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorDate;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorDateList;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorDateRange;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorDiagnosisList;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorMedicalInvestigationList;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorText;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorValue;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.validation.SchemaValidatorV4;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.validation.SchematronValidator;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.validation.XmlValidationService;

@ExtendWith(MockitoExtension.class)
class SchematronValidationFK7810Test {

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
              new XmlGeneratorDateRange(),
              new XmlGeneratorMedicalInvestigationList()
          ),
          Collections.emptyList()
      ),
      new XmlValidationService(
          new SchematronValidator(),
          new SchemaValidatorV4()
      )
  );

  private CertificateModelFactoryFK7810 CertificateModelFactoryFK7810;

  @BeforeEach
  void setUp() {
    schematronValidator = new SchematronValidator();
    CertificateModelFactoryFK7810 = new CertificateModelFactoryFK7810(certificateActionFactory,
        diagnosisCodeRepository);
  }

  @Test
  void shallReturnTrueForValidCertificate() {
    final var certificate = TestDataCertificate.fk7810CertificateBuilder()
        .certificateModel(CertificateModelFactoryFK7810.create())
        .build();

    final var xml = generator.generate(certificate, true);
    assertTrue(schematronValidator.validate(certificate.id(), xml,
        SCHEMATRON_PATH));
  }

  @Nested
  class TestValidateGrundForMU {

    @Test
    void shallReturnFalseIfMoreThanFiveMU() {
      final var certificate = TestDataCertificate.fk7810CertificateBuilder()
          .certificateModel(CertificateModelFactoryFK7810.create())
          .build();

      final var element = certificate.elementData().stream()
          .filter(elementData -> elementData.id().equals(new ElementId("1")))
          .findFirst()
          .orElseThrow();

      final var value = (ElementValueDateList) element.value();
      final var elementData = element.withValue(value.withDateList(
              List.of(
                  ElementValueDate.builder()
                      .dateId(new FieldId("fysisktMote"))
                      .date(LocalDate.now())
                      .build(),
                  ElementValueDate.builder()
                      .dateId(new FieldId("fysisktMote"))
                      .date(LocalDate.now())
                      .build(),
                  ElementValueDate.builder()
                      .dateId(new FieldId("fysisktMote"))
                      .date(LocalDate.now())
                      .build(),
                  ElementValueDate.builder()
                      .dateId(new FieldId("fysisktMote"))
                      .date(LocalDate.now())
                      .build(),
                  ElementValueDate.builder()
                      .dateId(new FieldId("fysisktMote"))
                      .date(LocalDate.now())
                      .build(),
                  ElementValueDate.builder()
                      .dateId(new FieldId("fysisktMote"))
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
          SCHEMATRON_PATH));
    }

    @Test
    void shallReturnFalseIfMoreThanOneOccurenceOfMU() {
      final var certificate = TestDataCertificate.fk7810CertificateBuilder()
          .certificateModel(CertificateModelFactoryFK7810.create())
          .build();

      final var element = certificate.elementData().stream()
          .filter(elementData -> elementData.id().equals(new ElementId("1")))
          .findFirst()
          .orElseThrow();

      final var value = (ElementValueDateList) element.value();
      final var elementData = element.withValue(value.withDateList(
              List.of(
                  ElementValueDate.builder()
                      .dateId(new FieldId("fysisktMote"))
                      .date(LocalDate.now())
                      .build(),
                  ElementValueDate.builder()
                      .dateId(new FieldId("fysisktMote"))
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
          SCHEMATRON_PATH));
    }

    @Test
    void shallReturnFalseIfMissingValues() {
      final var certificate = TestDataCertificate.fk7810CertificateBuilder()
          .certificateModel(CertificateModelFactoryFK7810.create())
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
          SCHEMATRON_PATH));
    }

    @Test
    void shallReturnFalseIfAnnatUnderlagNotAnswered() {
      final var certificate = TestDataCertificate.fk7810CertificateBuilder()
          .certificateModel(CertificateModelFactoryFK7810.create())
          .build();

      final var element = certificate.elementData().stream()
          .filter(elementData -> elementData.id().equals(new ElementId("3")))
          .findFirst()
          .orElseThrow();

      final var value = (ElementValueBoolean) element.value();
      final var elementData = element.withValue(value.withValue(null));

      final var updatedElementData = certificate.elementData().stream()
          .map(data -> data.id().equals(new ElementId("3")) ? elementData : data)
          .toList();
      certificate.updateData(updatedElementData, new Revision(0), ACTION_EVALUATION);

      final var xml = generator.generate(certificate, false);
      assertFalse(schematronValidator.validate(certificate.id(), xml,
          SCHEMATRON_PATH));
    }

    @Test
    void shallReturnFalseIfAnnatHasValueAndTextIsMissing() {
      final var certificate = TestDataCertificate.fk7810CertificateBuilder()
          .certificateModel(CertificateModelFactoryFK7810.create())
          .build();

      final var element = certificate.elementData().stream()
          .filter(elementData -> elementData.id().equals(new ElementId("1")))
          .findFirst()
          .orElseThrow();

      final var value = (ElementValueDateList) element.value();
      final var elementData = element.withValue(value.withDateList(
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
          SCHEMATRON_PATH));
    }

    @Test
    void shallReturnFalseIfAnhorigHasValueAndTextIsMissing() {
      final var certificate = TestDataCertificate.fk7810CertificateBuilder()
          .certificateModel(CertificateModelFactoryFK7810.create())
          .build();

      final var element = certificate.elementData().stream()
          .filter(elementData -> elementData.id().equals(new ElementId("1")))
          .findFirst()
          .orElseThrow();

      final var value = (ElementValueDateList) element.value();
      final var elementData = element.withValue(value.withDateList(
              List.of(
                  ElementValueDate.builder()
                      .dateId(new FieldId("anhorig"))
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
          SCHEMATRON_PATH));
    }

    @Test
    void shallReturnFalseIfAnnatUnderlagAndMissingMedicalInvestigation() {
      final var certificate = TestDataCertificate.fk7810CertificateBuilder()
          .certificateModel(CertificateModelFactoryFK7810.create())
          .build();

      final var element = certificate.elementData().stream()
          .filter(elementData -> elementData.id().equals(new ElementId("4")))
          .findFirst()
          .orElseThrow();

      final var value = (ElementValueMedicalInvestigationList) element.value();
      final var elementData = element.withValue(value.withList(Collections.emptyList()));

      final var updatedElementData = certificate.elementData().stream()
          .map(data -> data.id().equals(new ElementId("4")) ? elementData : data)
          .toList();
      certificate.updateData(updatedElementData, new Revision(0), ACTION_EVALUATION);

      final var xml = generator.generate(certificate, false);
      assertFalse(schematronValidator.validate(certificate.id(), xml,
          SCHEMATRON_PATH));
    }

    @Test
    void shallReturnFalseIfAnnatUnderlagMissingMandatoryFields() {
      final var certificate = TestDataCertificate.fk7810CertificateBuilder()
          .certificateModel(CertificateModelFactoryFK7810.create())
          .build();

      final var element = certificate.elementData().stream()
          .filter(elementData -> elementData.id().equals(new ElementId("4")))
          .findFirst()
          .orElseThrow();

      final var value = (ElementValueMedicalInvestigationList) element.value();
      final var elementData = element.withValue(value.withList(
              List.of(
                  MedicalInvestigation.builder()
                      .id(new FieldId("medicalInvestigation1"))
                      .informationSource(
                          ElementValueText.builder()
                              .textId(new FieldId(
                                  "medicalInvestigation1_INFORMATION_SOURCE"))
                              .build()
                      )
                      .investigationType(
                          ElementValueCode.builder()
                              .codeId(new FieldId(
                                  "medicalInvestigation1_INVESTIGATION_TYPE"))
                              .code("LOGOPED")
                              .build()
                      )
                      .date(
                          ElementValueDate.builder()
                              .dateId(new FieldId("medicalInvestigation1_DATE"))
                              .date(LocalDate.now())
                              .build())
                      .build()
              )
          )
      );

      final var updatedElementData = certificate.elementData().stream()
          .map(data -> data.id().equals(new ElementId("4")) ? elementData : data)
          .toList();
      certificate.updateData(updatedElementData, new Revision(0), ACTION_EVALUATION);

      final var xml = generator.generate(certificate, false);
      assertFalse(schematronValidator.validate(certificate.id(), xml,
          SCHEMATRON_PATH));
    }

    @Test
    void shallReturnFalseIfKannedomNotAnswered() {
      final var certificate = TestDataCertificate.fk7810CertificateBuilder()
          .certificateModel(CertificateModelFactoryFK7810.create())
          .build();

      final var updatedElementData = certificate.elementData().stream()
          .filter(elementData -> !elementData.id().equals(new ElementId("2")))
          .toList();

      certificate.updateData(updatedElementData, new Revision(0), ACTION_EVALUATION);

      final var xml = generator.generate(certificate, false);
      assertFalse(schematronValidator.validate(certificate.id(), xml,
          SCHEMATRON_PATH));

    }
  }

  @Nested
  class TestValidateDiagnos {

    @Test
    void shallReturnFalseIfMoreThanFiveDiagnosis() {
      final var certificate = TestDataCertificate.fk7810CertificateBuilder()
          .certificateModel(CertificateModelFactoryFK7810.create())
          .build();

      final var element = certificate.elementData().stream()
          .filter(elementData -> elementData.id().equals(new ElementId("58")))
          .findFirst()
          .orElseThrow();

      final var value = (ElementValueDiagnosisList) element.value();
      final var elementData = element.withValue(value.withDiagnoses(
              List.of(
                  ElementValueDiagnosis.builder()
                      .code("A013")
                      .description("Paratyfoidfeber C")
                      .terminology("ICD_10_SE")
                      .build(),
                  ElementValueDiagnosis.builder()
                      .code("A013")
                      .description("Paratyfoidfeber C")
                      .terminology("ICD_10_SE")
                      .build(),
                  ElementValueDiagnosis.builder()
                      .code("A013")
                      .description("Paratyfoidfeber C")
                      .terminology("ICD_10_SE")
                      .build(),
                  ElementValueDiagnosis.builder()
                      .code("A013")
                      .description("Paratyfoidfeber C")
                      .terminology("ICD_10_SE")
                      .build(),
                  ElementValueDiagnosis.builder()
                      .code("A013")
                      .description("Paratyfoidfeber C")
                      .terminology("ICD_10_SE")
                      .build(),
                  ElementValueDiagnosis.builder()
                      .code("A013")
                      .description("Paratyfoidfeber C")
                      .terminology("ICD_10_SE")
                      .build()
              )
          )
      );

      final var updatedElementData = certificate.elementData().stream()
          .map(data -> data.id().equals(new ElementId("58")) ? elementData : data)
          .toList();
      certificate.updateData(updatedElementData, new Revision(0), ACTION_EVALUATION);

      final var xml = generator.generate(certificate, false);
      assertFalse(schematronValidator.validate(certificate.id(), xml,
          SCHEMATRON_PATH));
    }

    @Test
    void shallReturnFalseIfMissingValues() {
      final var certificate = TestDataCertificate.fk7810CertificateBuilder()
          .certificateModel(CertificateModelFactoryFK7810.create())
          .build();

      final var element = certificate.elementData().stream()
          .filter(elementData -> elementData.id().equals(new ElementId("58")))
          .findFirst()
          .orElseThrow();

      final var value = (ElementValueDiagnosisList) element.value();
      final var elementData = element.withValue(value.withDiagnoses(
              Collections.emptyList()
          )
      );

      final var updatedElementData = certificate.elementData().stream()
          .map(data -> data.id().equals(new ElementId("58")) ? elementData : data)
          .toList();
      certificate.updateData(updatedElementData, new Revision(0), ACTION_EVALUATION);

      final var xml = generator.generate(certificate, false);
      assertFalse(schematronValidator.validate(certificate.id(), xml,
          SCHEMATRON_PATH));
    }

    @Test
    void shallReturnFalseIfSummaryIsMissing() {
      final var certificate = TestDataCertificate.fk7810CertificateBuilder()
          .certificateModel(CertificateModelFactoryFK7810.create())
          .build();

      final var element = certificate.elementData().stream()
          .filter(elementData -> elementData.id().equals(new ElementId("5")))
          .findFirst()
          .orElseThrow();

      final var value = (ElementValueText) element.value();
      final var elementData = element.withValue(value.withText(""));

      final var updatedElementData = certificate.elementData().stream()
          .map(data -> data.id().equals(new ElementId("5")) ? elementData : data)
          .toList();
      certificate.updateData(updatedElementData, new Revision(0), ACTION_EVALUATION);

      final var xml = generator.generate(certificate, false);
      assertFalse(schematronValidator.validate(certificate.id(), xml,
          SCHEMATRON_PATH));
    }


    @Test
    void shallReturnFalseIfDiagnosisDescriptionIsMissing() {
      final var certificate = TestDataCertificate.fk7810CertificateBuilder()
          .certificateModel(CertificateModelFactoryFK7810.create())
          .build();

      final var element = certificate.elementData().stream()
          .filter(elementData -> elementData.id().equals(new ElementId("58")))
          .findFirst()
          .orElseThrow();

      final var value = (ElementValueDiagnosisList) element.value();
      final var elementData = element.withValue(value.withDiagnoses(
              List.of(
                  ElementValueDiagnosis.builder()
                      .code("A013")
                      .description("")
                      .terminology("ICD_10_SE")
                      .build()
              )
          )
      );

      final var updatedElementData = certificate.elementData().stream()
          .map(data -> data.id().equals(new ElementId("58")) ? elementData : data)
          .toList();
      certificate.updateData(updatedElementData, new Revision(0), ACTION_EVALUATION);

      final var xml = generator.generate(certificate, false);
      assertFalse(schematronValidator.validate(certificate.id(), xml,
          SCHEMATRON_PATH));
    }

    @Test
    void shallReturnFalseIfDiagnosisCodeIsMalformed() {
      final var certificate = TestDataCertificate.fk7810CertificateBuilder()
          .certificateModel(CertificateModelFactoryFK7810.create())
          .build();

      final var element = certificate.elementData().stream()
          .filter(elementData -> elementData.id().equals(new ElementId("58")))
          .findFirst()
          .orElseThrow();

      final var value = (ElementValueDiagnosisList) element.value();
      final var elementData = element.withValue(value.withDiagnoses(
              List.of(
                  ElementValueDiagnosis.builder()
                      .code("AAAAA")
                      .description("Paratyfoidfeber C")
                      .terminology("ICD_10_SE")
                      .build()
              )
          )
      );

      final var updatedElementData = certificate.elementData().stream()
          .map(data -> data.id().equals(new ElementId("58")) ? elementData : data)
          .toList();
      certificate.updateData(updatedElementData, new Revision(0), ACTION_EVALUATION);

      final var xml = generator.generate(certificate, false);
      assertFalse(schematronValidator.validate(certificate.id(), xml,
          SCHEMATRON_PATH));
    }
  }

  @Nested
  class TestValidateFunktionsnedsattning {

    @Test
    void shallReturnFalseIfEmptyValues() {
      final var certificate = TestDataCertificate.fk7810CertificateBuilder()
          .certificateModel(CertificateModelFactoryFK7810.create())
          .build();

      final var element = certificate.elementData().stream()
          .filter(elementData -> elementData.id().equals(new ElementId("8")))
          .findFirst()
          .orElseThrow();

      final var value = (ElementValueText) element.value();
      final var elementData = element.withValue(value.withText(""));

      final var updatedElementData = certificate.elementData().stream()
          .map(data -> data.id().equals(new ElementId("8")) ? elementData : data)
          .toList();
      certificate.updateData(updatedElementData, new Revision(0), ACTION_EVALUATION);

      final var xml = generator.generate(certificate, false);
      assertFalse(schematronValidator.validate(certificate.id(), xml,
          SCHEMATRON_PATH));
    }

    @Test
    void shallReturnFalseIfMissingValues() {
      final var certificate = TestDataCertificate.fk7810CertificateBuilder()
          .certificateModel(CertificateModelFactoryFK7810.create())
          .build();
      final var updatedElementData = certificate.elementData().stream()
          .filter(data -> !data.id().equals(new ElementId("8")))
          .toList();
      certificate.updateData(updatedElementData, new Revision(0), ACTION_EVALUATION);

      final var xml = generator.generate(certificate, false);
      assertFalse(schematronValidator.validate(certificate.id(), xml,
          SCHEMATRON_PATH));
    }

    @ParameterizedTest
    @ValueSource(strings = {"8", "9", "10", "11", "12", "13", "64", "14"})
    void shallReturnTrueIfOneValueExists(String id) {
      final var certificate = TestDataCertificate.fk7810CertificateBuilder()
          .certificateModel(CertificateModelFactoryFK7810.create())
          .build();

      var element = ElementData.builder()
          .id(new ElementId(id))
          .value(
              ElementValueText.builder()
                  .textId(new FieldId("%s.1".formatted(id)))
                  .text("TEXT")
                  .build()
          )
          .build();

      final var updatedElementData = certificate.elementData().stream()
          .filter(data -> !data.id().equals(new ElementId("8")))
          .collect(Collectors.toCollection(ArrayList::new));

      updatedElementData.add(element);
      certificate.updateData(updatedElementData, new Revision(0), ACTION_EVALUATION);

      final var xml = generator.generate(certificate, false);
      assertTrue(schematronValidator.validate(certificate.id(), xml,
          SCHEMATRON_PATH));
    }
  }

  @Nested
  class TestValidateAktivitetsbegransningar {

    @Test
    void shallReturnFalseIfEmptyValue() {
      final var certificate = TestDataCertificate.fk7810CertificateBuilder()
          .certificateModel(CertificateModelFactoryFK7810.create())
          .build();

      final var element = certificate.elementData().stream()
          .filter(elementData -> elementData.id().equals(new ElementId("65")))
          .findFirst()
          .orElseThrow();

      final var value = (ElementValueText) element.value();
      final var elementData = element.withValue(value.withText(""));

      final var updatedElementData = certificate.elementData().stream()
          .map(data -> data.id().equals(new ElementId("65")) ? elementData : data)
          .toList();
      certificate.updateData(updatedElementData, new Revision(0), ACTION_EVALUATION);

      final var xml = generator.generate(certificate, false);
      assertFalse(schematronValidator.validate(certificate.id(), xml,
          SCHEMATRON_PATH));
    }


    @Test
    void shallReturnFalseIfMissingValues() {
      final var certificate = TestDataCertificate.fk7810CertificateBuilder()
          .certificateModel(CertificateModelFactoryFK7810.create())
          .build();
      final var updatedElementData = certificate.elementData().stream()
          .filter(data -> !data.id().equals(new ElementId("65")))
          .toList();
      certificate.updateData(updatedElementData, new Revision(0), ACTION_EVALUATION);

      final var xml = generator.generate(certificate, false);
      assertFalse(schematronValidator.validate(certificate.id(), xml,
          SCHEMATRON_PATH));
    }

    @ParameterizedTest
    @ValueSource(strings = {"65", "66", "67", "68", "69"})
    void shallReturnTrueIfOneValueExists(String id) {
      final var certificate = TestDataCertificate.fk7810CertificateBuilder()
          .certificateModel(CertificateModelFactoryFK7810.create())
          .build();

      var element = ElementData.builder()
          .id(new ElementId(id))
          .value(
              ElementValueText.builder()
                  .textId(new FieldId("%s.1".formatted(id)))
                  .text("TEXT")
                  .build()
          )
          .build();

      final var updatedElementData = certificate.elementData().stream()
          .filter(data -> !data.id().equals(new ElementId("65")))
          .collect(Collectors.toCollection(ArrayList::new));

      updatedElementData.add(element);
      certificate.updateData(updatedElementData, new Revision(0), ACTION_EVALUATION);

      final var xml = generator.generate(certificate, false);
      assertTrue(schematronValidator.validate(certificate.id(), xml,
          SCHEMATRON_PATH));
    }
  }

  @Nested
  class TestValidatePrognos {

    @Test
    void shallReturnFalseIfMissingValues() {
      final var certificate = TestDataCertificate.fk7810CertificateBuilder()
          .certificateModel(CertificateModelFactoryFK7810.create())
          .build();

      final var element = certificate.elementData().stream()
          .filter(elementData -> elementData.id().equals(new ElementId("39")))
          .findFirst()
          .orElseThrow();

      final var value = (ElementValueText) element.value();
      final var elementData = element.withValue(value.withText(""));

      final var updatedElementData = certificate.elementData().stream()
          .map(data -> data.id().equals(new ElementId("39")) ? elementData : data)
          .toList();
      certificate.updateData(updatedElementData, new Revision(0), ACTION_EVALUATION);

      final var xml = generator.generate(certificate, false);
      assertFalse(schematronValidator.validate(certificate.id(), xml,
          SCHEMATRON_PATH));
    }
  }

  @Nested
  class TestValidateMedicinskaBehandlingar {

    @Test
    void shallReturnFalseIfPlannedMedicalTreatmentIsNonEmptyAndResponsibleUnitIsEmpty() {
      final var certificate = TestDataCertificate.fk7810CertificateBuilder()
          .certificateModel(CertificateModelFactoryFK7810.create())
          .build();

      final var elementData = new ArrayList<>(certificate.elementData());
      elementData.add(ElementData.builder()
          .id(new ElementId("50"))
          .value(
              ElementValueText.builder()
                  .textId(new FieldId("50.1"))
                  .text("TEXT")
                  .build()
          )
          .build());

      certificate.updateData(elementData, new Revision(0), ACTION_EVALUATION);

      final var xml = generator.generate(certificate, false);
      assertFalse(schematronValidator.validate(certificate.id(), xml,
          SCHEMATRON_PATH));
    }

    @Test
    void shallReturnFalseIfPlannedMedicalTreatmentIsEmptyAndResponsibleUnitIsNotEmpty() {
      final var certificate = TestDataCertificate.fk7810CertificateBuilder()
          .certificateModel(CertificateModelFactoryFK7810.create())
          .build();

      final var elementData = new ArrayList<>(certificate.elementData());
      elementData.add(ElementData.builder()
          .id(new ElementId("50.2"))
          .value(
              ElementValueText.builder()
                  .textId(new FieldId("50.2"))
                  .text("TEXT")
                  .build()
          )
          .build());

      certificate.updateData(elementData, new Revision(0), ACTION_EVALUATION);

      final var xml = generator.generate(certificate, false);
      assertFalse(schematronValidator.validate(certificate.id(), xml,
          SCHEMATRON_PATH));
    }
  }

  @Nested
  class TestValidateSjukvardandeInsatser {

    @Test
    void shallReturnFalseIfStodHSLNotAnswered() {
      final var certificate = TestDataCertificate.fk7810CertificateBuilder()
          .certificateModel(CertificateModelFactoryFK7810.create())
          .build();

      final var element = certificate.elementData().stream()
          .filter(elementData -> elementData.id().equals(new ElementId("70")))
          .findFirst()
          .orElseThrow();

      final var value = (ElementValueBoolean) element.value();
      final var elementData = element.withValue(value.withValue(null));

      final var updatedElementData = certificate.elementData().stream()
          .map(data -> data.id().equals(new ElementId("70")) ? elementData : data)
          .toList();
      certificate.updateData(updatedElementData, new Revision(0), ACTION_EVALUATION);

      final var xml = generator.generate(certificate, false);
      assertFalse(schematronValidator.validate(certificate.id(), xml,
          SCHEMATRON_PATH));
    }

    @Test
    void shallReturnFalseIfStodEgenVardNotAnswered() {
      final var certificate = TestDataCertificate.fk7810CertificateBuilder()
          .certificateModel(CertificateModelFactoryFK7810.create())
          .build();

      final var element = certificate.elementData().stream()
          .filter(elementData -> elementData.id().equals(new ElementId("70.3")))
          .findFirst()
          .orElseThrow();

      final var value = (ElementValueBoolean) element.value();
      final var elementData = element.withValue(value.withValue(null));

      final var updatedElementData = certificate.elementData().stream()
          .map(data -> data.id().equals(new ElementId("70.3")) ? elementData : data)
          .toList();
      certificate.updateData(updatedElementData, new Revision(0), ACTION_EVALUATION);

      final var xml = generator.generate(certificate, false);
      assertFalse(schematronValidator.validate(certificate.id(), xml,
          SCHEMATRON_PATH));
    }

    @Test
    void shallReturnFalseIfStodHSLAndMissingText() {
      final var certificate = TestDataCertificate.fk7810CertificateBuilder()
          .certificateModel(CertificateModelFactoryFK7810.create())
          .build();

      final var element = certificate.elementData().stream()
          .filter(elementData -> elementData.id().equals(new ElementId("70.2")))
          .findFirst()
          .orElseThrow();

      final var value = (ElementValueText) element.value();
      final var elementData = element.withValue(value.withText(""));

      final var updatedElementData = certificate.elementData().stream()
          .map(data -> data.id().equals(new ElementId("70.2")) ? elementData : data)
          .toList();
      certificate.updateData(updatedElementData, new Revision(0), ACTION_EVALUATION);

      final var xml = generator.generate(certificate, false);
      assertFalse(schematronValidator.validate(certificate.id(), xml,
          SCHEMATRON_PATH));
    }

    @Test
    void shallReturnFalseIfStodEgenVardAndMissingText() {
      final var certificate = TestDataCertificate.fk7810CertificateBuilder()
          .certificateModel(CertificateModelFactoryFK7810.create())
          .build();

      final var element = certificate.elementData().stream()
          .filter(elementData -> elementData.id().equals(new ElementId("70.4")))
          .findFirst()
          .orElseThrow();

      final var value = (ElementValueText) element.value();
      final var elementData = element.withValue(value.withText(""));

      final var updatedElementData = certificate.elementData().stream()
          .map(data -> data.id().equals(new ElementId("70.4")) ? elementData : data)
          .toList();
      certificate.updateData(updatedElementData, new Revision(0), ACTION_EVALUATION);

      final var xml = generator.generate(certificate, false);
      assertFalse(schematronValidator.validate(certificate.id(), xml,
          SCHEMATRON_PATH));
    }
  }
}