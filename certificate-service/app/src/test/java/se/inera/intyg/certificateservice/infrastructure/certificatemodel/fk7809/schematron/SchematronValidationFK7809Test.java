package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.schematron;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7809_SCHEMATRON_PATH;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.diagnosiscode.repository.DiagnosisCodeRepository;
import se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.CertificateModelFactoryFK7809;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorBoolean;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorCertificateV4;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorCode;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorDate;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorDateList;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorDiagnosisList;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorMedicalInvestigationList;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorText;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorValue;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.validation.SchemaValidatorV4;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.validation.SchematronValidator;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.validation.XmlValidationService;

@ExtendWith(MockitoExtension.class)
class SchematronValidationFK7809Test {

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
              new XmlGeneratorMedicalInvestigationList()
          ),
          Collections.emptyList()
      ),
      new XmlValidationService(
          new SchematronValidator(),
          new SchemaValidatorV4()
      )
  );
  private CertificateModelFactoryFK7809 certificateModelFactoryFK7809;

  @BeforeEach
  void setUp() {
    schematronValidator = new SchematronValidator();
    certificateModelFactoryFK7809 = new CertificateModelFactoryFK7809(certificateActionFactory,
        diagnosisCodeRepository);
  }

  @Nested
  class ValidCertificate {

    @Test
    void shallReturnTrueForValidCertificate() {
      final var certificate = TestDataCertificate.fk7809CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7809.create())
          .build();

      final var xml = generator.generate(certificate, true);

      assertTrue(schematronValidator.validate(certificate.id(), xml,
          FK7809_SCHEMATRON_PATH));
    }

    @Test
    void shallReturnFalseForInvalidCertificate() {
      final var certificate = TestDataCertificate.fk7809CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7809.create())
          .elementData(
              Collections.emptyList()
          )
          .build();

      final var xml = generator.generate(certificate, false);

      assertFalse(schematronValidator.validate(certificate.id(), xml,
          FK7809_SCHEMATRON_PATH));
    }

    @Test
    void shallReturnFalseIfSchemaRulesAreNotRespected() {
      final var certificate = TestDataCertificate.fk7809CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7809.create())
          .elementData(
              List.of(
                  ElementData.builder()
                      .id(new ElementId("1"))
                      .value(
                          ElementValueDateList.builder()
                              .dateListId(new FieldId("1.1"))
                              .dateList(
                                  List.of(
                                      ElementValueDate.builder()
                                          .dateId(new FieldId("annat"))
                                          .date(LocalDate.now())
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("3"))
                      .value(
                          ElementValueBoolean.builder()
                              .booleanId(new FieldId("3.1"))
                              .value(true)
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("4"))
                      .value(
                          ElementValueMedicalInvestigationList.builder()
                              .id(new FieldId("52.2"))
                              .list(
                                  List.of(
                                      MedicalInvestigation.builder()
                                          .id(new FieldId("medicalInvestigation1"))
                                          .informationSource(
                                              ElementValueText.builder()
                                                  .textId(new FieldId(
                                                      "medicalInvestigation1_INFORMATION_SOURCE"))
                                                  .text("Example text")
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
                                                  .dateId(
                                                      new FieldId("medicalInvestigation1_DATE"))
                                                  .date(LocalDate.now())
                                                  .build())
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("58"))
                      .value(
                          ElementValueDiagnosisList.builder()
                              .diagnoses(
                                  List.of(
                                      ElementValueDiagnosis.builder()
                                          .code("A013")
                                          .description("Paratyfoidfeber C")
                                          .terminology("ICD_10_SE")
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("5"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("5.1"))
                              .text("Historiken för diagnoserna")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("9"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("9.1"))
                              .text("Funktionsnedsättning")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("51"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("51.1"))
                              .text("prognos")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("1.3"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("1"))
                              .text("Ange vad annat är")
                              .build()
                      ).build()
              )
          )
          .build();

      assertThrows(IllegalStateException.class, () -> generator.generate(certificate, true));
    }
  }

  @Nested
  class QuestionGrundForMU {

    @Test
    void shallReturnTrueIfMUIsAnnatAndQuestionUtlatandeBaseratPaAnnatIsPresent() {
      final var certificate = TestDataCertificate.fk7809CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7809.create())
          .elementData(
              List.of(
                  ElementData.builder()
                      .id(new ElementId("1"))
                      .value(
                          ElementValueDateList.builder()
                              .dateListId(new FieldId("1.1"))
                              .dateList(
                                  List.of(
                                      ElementValueDate.builder()
                                          .dateId(new FieldId("annat"))
                                          .date(LocalDate.now())
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("3"))
                      .value(
                          ElementValueBoolean.builder()
                              .booleanId(new FieldId("3.1"))
                              .value(true)
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("4"))
                      .value(
                          ElementValueMedicalInvestigationList.builder()
                              .id(new FieldId("52.2"))
                              .list(
                                  List.of(
                                      MedicalInvestigation.builder()
                                          .id(new FieldId("medicalInvestigation1"))
                                          .informationSource(
                                              ElementValueText.builder()
                                                  .textId(new FieldId(
                                                      "medicalInvestigation1_INFORMATION_SOURCE"))
                                                  .text("Example text")
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
                                                  .dateId(
                                                      new FieldId("medicalInvestigation1_DATE"))
                                                  .date(LocalDate.now())
                                                  .build())
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("58"))
                      .value(
                          ElementValueDiagnosisList.builder()
                              .diagnoses(
                                  List.of(
                                      ElementValueDiagnosis.builder()
                                          .code("A013")
                                          .description("Paratyfoidfeber C")
                                          .terminology("ICD_10_SE")
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("5"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("5.1"))
                              .text("Historiken för diagnoserna")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("9"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("9.1"))
                              .text("Funktionsnedsättning")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("51"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("51.1"))
                              .text("prognos")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("1.3"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("1.3"))
                              .text("Ange vad annat är")
                              .build()
                      ).build()
              )
          )
          .build();

      final var xml = generator.generate(certificate, true);

      assertTrue(schematronValidator.validate(certificate.id(), xml,
          FK7809_SCHEMATRON_PATH));
    }

    @Test
    void shallReturnFalseIfMUISAnnatAndQuestionUtlatandeBaseratPaAnnatIsMissing() {
      final var certificate = TestDataCertificate.fk7809CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7809.create())
          .elementData(
              List.of(
                  ElementData.builder()
                      .id(new ElementId("1"))
                      .value(
                          ElementValueDateList.builder()
                              .dateListId(new FieldId("1.1"))
                              .dateList(
                                  List.of(
                                      ElementValueDate.builder()
                                          .dateId(new FieldId("annat"))
                                          .date(LocalDate.now())
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("3"))
                      .value(
                          ElementValueBoolean.builder()
                              .booleanId(new FieldId("3.1"))
                              .value(true)
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("4"))
                      .value(
                          ElementValueMedicalInvestigationList.builder()
                              .id(new FieldId("52.2"))
                              .list(
                                  List.of(
                                      MedicalInvestigation.builder()
                                          .id(new FieldId("medicalInvestigation1"))
                                          .informationSource(
                                              ElementValueText.builder()
                                                  .textId(new FieldId(
                                                      "medicalInvestigation1_INFORMATION_SOURCE"))
                                                  .text("Example text")
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
                                                  .dateId(
                                                      new FieldId("medicalInvestigation1_DATE"))
                                                  .date(LocalDate.now())
                                                  .build())
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("58"))
                      .value(
                          ElementValueDiagnosisList.builder()
                              .diagnoses(
                                  List.of(
                                      ElementValueDiagnosis.builder()
                                          .code("A013")
                                          .description("Paratyfoidfeber C")
                                          .terminology("ICD_10_SE")
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("5"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("5.1"))
                              .text("Historiken för diagnoserna")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("9"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("9.1"))
                              .text("Funktionsnedsättning")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("51"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("51.1"))
                              .text("prognos")
                              .build()
                      )
                      .build()
              )
          )
          .build();

      final var xml = generator.generate(certificate, false);

      assertFalse(schematronValidator.validate(certificate.id(), xml,
          FK7809_SCHEMATRON_PATH));
    }

    @Test
    void shallReturnFalseIfMUISAhorigAndTextForAnhorigIsMissing() {
      final var certificate = TestDataCertificate.fk7809CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7809.create())
          .elementData(
              List.of(
                  ElementData.builder()
                      .id(new ElementId("1"))
                      .value(
                          ElementValueDateList.builder()
                              .dateListId(new FieldId("1.1"))
                              .dateList(
                                  List.of(
                                      ElementValueDate.builder()
                                          .dateId(new FieldId("anhorig"))
                                          .date(LocalDate.now())
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("3"))
                      .value(
                          ElementValueBoolean.builder()
                              .booleanId(new FieldId("3.1"))
                              .value(true)
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("4"))
                      .value(
                          ElementValueMedicalInvestigationList.builder()
                              .id(new FieldId("52.2"))
                              .list(
                                  List.of(
                                      MedicalInvestigation.builder()
                                          .id(new FieldId("medicalInvestigation1"))
                                          .informationSource(
                                              ElementValueText.builder()
                                                  .textId(new FieldId(
                                                      "medicalInvestigation1_INFORMATION_SOURCE"))
                                                  .text("Example text")
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
                                                  .dateId(
                                                      new FieldId("medicalInvestigation1_DATE"))
                                                  .date(LocalDate.now())
                                                  .build())
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("58"))
                      .value(
                          ElementValueDiagnosisList.builder()
                              .diagnoses(
                                  List.of(
                                      ElementValueDiagnosis.builder()
                                          .code("A013")
                                          .description("Paratyfoidfeber C")
                                          .terminology("ICD_10_SE")
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("5"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("5.1"))
                              .text("Historiken för diagnoserna")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("9"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("9.1"))
                              .text("Funktionsnedsättning")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("51"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("51.1"))
                              .text("prognos")
                              .build()
                      )
                      .build()
              )
          )
          .build();

      final var xml = generator.generate(certificate, false);

      assertFalse(schematronValidator.validate(certificate.id(), xml,
          FK7809_SCHEMATRON_PATH));
    }

    @Test
    void shallReturnTrueIfMUIsAnhorigAndTextForAnhorigIsFilled() {
      final var certificate = TestDataCertificate.fk7809CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7809.create())
          .elementData(
              List.of(
                  ElementData.builder()
                      .id(new ElementId("1"))
                      .value(
                          ElementValueDateList.builder()
                              .dateListId(new FieldId("1.1"))
                              .dateList(
                                  List.of(
                                      ElementValueDate.builder()
                                          .dateId(new FieldId("anhorig"))
                                          .date(LocalDate.now())
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("3"))
                      .value(
                          ElementValueBoolean.builder()
                              .booleanId(new FieldId("3.1"))
                              .value(true)
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("4"))
                      .value(
                          ElementValueMedicalInvestigationList.builder()
                              .id(new FieldId("52.2"))
                              .list(
                                  List.of(
                                      MedicalInvestigation.builder()
                                          .id(new FieldId("medicalInvestigation1"))
                                          .informationSource(
                                              ElementValueText.builder()
                                                  .textId(new FieldId(
                                                      "medicalInvestigation1_INFORMATION_SOURCE"))
                                                  .text("Example text")
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
                                                  .dateId(
                                                      new FieldId("medicalInvestigation1_DATE"))
                                                  .date(LocalDate.now())
                                                  .build())
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("58"))
                      .value(
                          ElementValueDiagnosisList.builder()
                              .diagnoses(
                                  List.of(
                                      ElementValueDiagnosis.builder()
                                          .code("A013")
                                          .description("Paratyfoidfeber C")
                                          .terminology("ICD_10_SE")
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("5"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("5.1"))
                              .text("Historiken för diagnoserna")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("9"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("9.1"))
                              .text("Funktionsnedsättning")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("51"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("51.1"))
                              .text("prognos")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("1.4"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("1.4"))
                              .text("text")
                              .build()
                      )
                      .build()
              )
          )
          .build();

      final var xml = generator.generate(certificate, false);

      assertTrue(schematronValidator.validate(certificate.id(), xml,
          FK7809_SCHEMATRON_PATH));
    }

    @Test
    void shallReturnTrueIfMUIsJournaluppgifter() {
      final var certificate = TestDataCertificate.fk7809CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7809.create())
          .elementData(
              List.of(
                  ElementData.builder()
                      .id(new ElementId("1"))
                      .value(
                          ElementValueDateList.builder()
                              .dateListId(new FieldId("1.1"))
                              .dateList(
                                  List.of(
                                      ElementValueDate.builder()
                                          .dateId(new FieldId("journaluppgifter"))
                                          .date(LocalDate.now())
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("3"))
                      .value(
                          ElementValueBoolean.builder()
                              .booleanId(new FieldId("3.1"))
                              .value(true)
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("4"))
                      .value(
                          ElementValueMedicalInvestigationList.builder()
                              .id(new FieldId("52.2"))
                              .list(
                                  List.of(
                                      MedicalInvestigation.builder()
                                          .id(new FieldId("medicalInvestigation1"))
                                          .informationSource(
                                              ElementValueText.builder()
                                                  .textId(new FieldId(
                                                      "medicalInvestigation1_INFORMATION_SOURCE"))
                                                  .text("Example text")
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
                                                  .dateId(
                                                      new FieldId("medicalInvestigation1_DATE"))
                                                  .date(LocalDate.now())
                                                  .build())
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("58"))
                      .value(
                          ElementValueDiagnosisList.builder()
                              .diagnoses(
                                  List.of(
                                      ElementValueDiagnosis.builder()
                                          .code("A013")
                                          .description("Paratyfoidfeber C")
                                          .terminology("ICD_10_SE")
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("5"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("5.1"))
                              .text("Historiken för diagnoserna")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("9"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("9.1"))
                              .text("Funktionsnedsättning")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("51"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("51.1"))
                              .text("prognos")
                              .build()
                      )
                      .build()
              )
          )
          .build();

      final var xml = generator.generate(certificate, false);

      assertTrue(schematronValidator.validate(certificate.id(), xml,
          FK7809_SCHEMATRON_PATH));
    }

    @Test
    void shallReturnFalseIfMUIsMissing() {
      final var certificate = TestDataCertificate.fk7809CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7809.create())
          .elementData(
              List.of(
                  ElementData.builder()
                      .id(new ElementId("1"))
                      .value(
                          ElementValueDateList.builder()
                              .dateListId(new FieldId("1.1"))
                              .dateList(
                                  Collections.emptyList()
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("3"))
                      .value(
                          ElementValueBoolean.builder()
                              .booleanId(new FieldId("3.1"))
                              .value(true)
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("4"))
                      .value(
                          ElementValueMedicalInvestigationList.builder()
                              .id(new FieldId("52.2"))
                              .list(
                                  List.of(
                                      MedicalInvestigation.builder()
                                          .id(new FieldId("medicalInvestigation1"))
                                          .informationSource(
                                              ElementValueText.builder()
                                                  .textId(new FieldId(
                                                      "medicalInvestigation1_INFORMATION_SOURCE"))
                                                  .text("Example text")
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
                                                  .dateId(
                                                      new FieldId("medicalInvestigation1_DATE"))
                                                  .date(LocalDate.now())
                                                  .build())
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("58"))
                      .value(
                          ElementValueDiagnosisList.builder()
                              .diagnoses(
                                  List.of(
                                      ElementValueDiagnosis.builder()
                                          .code("A013")
                                          .description("Paratyfoidfeber C")
                                          .terminology("ICD_10_SE")
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("5"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("5.1"))
                              .text("Historiken för diagnoserna")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("9"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("9.1"))
                              .text("Funktionsnedsättning")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("51"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("51.1"))
                              .text("prognos")
                              .build()
                      )
                      .build()
              )
          )
          .build();

      final var xml = generator.generate(certificate, false);

      assertFalse(schematronValidator.validate(certificate.id(), xml,
          FK7809_SCHEMATRON_PATH));
    }
  }

  @Nested
  class QuestionDiagnos {

    @Test
    void shallReturnFalseIfQuestionDiagnosIsMissing() {
      final var certificate = TestDataCertificate.fk7809CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7809.create())
          .elementData(
              List.of(
                  ElementData.builder()
                      .id(new ElementId("1"))
                      .value(
                          ElementValueDateList.builder()
                              .dateListId(new FieldId("1.1"))
                              .dateList(List.of(
                                      ElementValueDate.builder()
                                          .dateId(new FieldId("journaluppgifter"))
                                          .date(LocalDate.now())
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("3"))
                      .value(
                          ElementValueBoolean.builder()
                              .booleanId(new FieldId("3.1"))
                              .value(true)
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("4"))
                      .value(
                          ElementValueMedicalInvestigationList.builder()
                              .id(new FieldId("52.2"))
                              .list(
                                  List.of(
                                      MedicalInvestigation.builder()
                                          .id(new FieldId("medicalInvestigation1"))
                                          .informationSource(
                                              ElementValueText.builder()
                                                  .textId(new FieldId(
                                                      "medicalInvestigation1_INFORMATION_SOURCE"))
                                                  .text("Example text")
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
                                                  .dateId(
                                                      new FieldId("medicalInvestigation1_DATE"))
                                                  .date(LocalDate.now())
                                                  .build())
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("58"))
                      .value(
                          ElementValueDiagnosisList.builder()
                              .diagnoses(
                                  Collections.emptyList()
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("5"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("5.1"))
                              .text("Historiken för diagnoserna")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("9"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("9.1"))
                              .text("Funktionsnedsättning")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("51"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("51.1"))
                              .text("prognos")
                              .build()
                      )
                      .build()
              )
          )
          .build();

      final var xml = generator.generate(certificate, false);

      assertFalse(schematronValidator.validate(certificate.id(), xml,
          FK7809_SCHEMATRON_PATH));
    }

    @Test
    void shallReturnFalseIfDiagnosisMissingDescription() {
      final var certificate = TestDataCertificate.fk7809CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7809.create())
          .elementData(
              List.of(
                  ElementData.builder()
                      .id(new ElementId("1"))
                      .value(
                          ElementValueDateList.builder()
                              .dateListId(new FieldId("1.1"))
                              .dateList(
                                  List.of(
                                      ElementValueDate.builder()
                                          .dateId(new FieldId("journaluppgifter"))
                                          .date(LocalDate.now())
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("3"))
                      .value(
                          ElementValueBoolean.builder()
                              .booleanId(new FieldId("3.1"))
                              .value(true)
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("4"))
                      .value(
                          ElementValueMedicalInvestigationList.builder()
                              .id(new FieldId("52.2"))
                              .list(
                                  List.of(
                                      MedicalInvestigation.builder()
                                          .id(new FieldId("medicalInvestigation1"))
                                          .informationSource(
                                              ElementValueText.builder()
                                                  .textId(new FieldId(
                                                      "medicalInvestigation1_INFORMATION_SOURCE"))
                                                  .text("Example text")
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
                                                  .dateId(
                                                      new FieldId("medicalInvestigation1_DATE"))
                                                  .date(LocalDate.now())
                                                  .build())
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("58"))
                      .value(
                          ElementValueDiagnosisList.builder()
                              .diagnoses(
                                  List.of(
                                      ElementValueDiagnosis.builder()
                                          .code("A013")
                                          .terminology("ICD_10_SE")
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("5"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("5.1"))
                              .text("Historiken för diagnoserna")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("9"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("9.1"))
                              .text("Funktionsnedsättning")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("51"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("51.1"))
                              .text("prognos")
                              .build()
                      )
                      .build()
              )
          )
          .build();

      final var xml = generator.generate(certificate, false);

      assertFalse(schematronValidator.validate(certificate.id(), xml,
          FK7809_SCHEMATRON_PATH));
    }

    @Test
    void shallReturnFalseIfDiagnosisMissingCode() {
      final var certificate = TestDataCertificate.fk7809CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7809.create())
          .elementData(
              List.of(
                  ElementData.builder()
                      .id(new ElementId("1"))
                      .value(
                          ElementValueDateList.builder()
                              .dateListId(new FieldId("1.1"))
                              .dateList(
                                  List.of(
                                      ElementValueDate.builder()
                                          .dateId(new FieldId("journaluppgifter"))
                                          .date(LocalDate.now())
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("3"))
                      .value(
                          ElementValueBoolean.builder()
                              .booleanId(new FieldId("3.1"))
                              .value(true)
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("4"))
                      .value(
                          ElementValueMedicalInvestigationList.builder()
                              .id(new FieldId("52.2"))
                              .list(
                                  List.of(
                                      MedicalInvestigation.builder()
                                          .id(new FieldId("medicalInvestigation1"))
                                          .informationSource(
                                              ElementValueText.builder()
                                                  .textId(new FieldId(
                                                      "medicalInvestigation1_INFORMATION_SOURCE"))
                                                  .text("Example text")
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
                                                  .dateId(
                                                      new FieldId("medicalInvestigation1_DATE"))
                                                  .date(LocalDate.now())
                                                  .build())
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("58"))
                      .value(
                          ElementValueDiagnosisList.builder()
                              .diagnoses(
                                  List.of(
                                      ElementValueDiagnosis.builder()
                                          .description("Paratyfoidfeber C")
                                          .terminology("ICD_10_SE")
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("5"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("5.1"))
                              .text("Historiken för diagnoserna")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("9"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("9.1"))
                              .text("Funktionsnedsättning")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("51"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("51.1"))
                              .text("prognos")
                              .build()
                      )
                      .build()
              )
          )
          .build();

      final var xml = generator.generate(certificate, false);

      assertFalse(schematronValidator.validate(certificate.id(), xml,
          FK7809_SCHEMATRON_PATH));
    }

    @Test
    void shallReturnFalseIfDiagnosisHasTooLongDiagnosisCode() {
      final var certificate = TestDataCertificate.fk7809CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7809.create())
          .elementData(
              List.of(
                  ElementData.builder()
                      .id(new ElementId("1"))
                      .value(
                          ElementValueDateList.builder()
                              .dateListId(new FieldId("1.1"))
                              .dateList(
                                  List.of(
                                      ElementValueDate.builder()
                                          .dateId(new FieldId("journaluppgifter"))
                                          .date(LocalDate.now())
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("3"))
                      .value(
                          ElementValueBoolean.builder()
                              .booleanId(new FieldId("3.1"))
                              .value(true)
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("4"))
                      .value(
                          ElementValueMedicalInvestigationList.builder()
                              .id(new FieldId("52.2"))
                              .list(
                                  List.of(
                                      MedicalInvestigation.builder()
                                          .id(new FieldId("medicalInvestigation1"))
                                          .informationSource(
                                              ElementValueText.builder()
                                                  .textId(new FieldId(
                                                      "medicalInvestigation1_INFORMATION_SOURCE"))
                                                  .text("Example text")
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
                                                  .dateId(
                                                      new FieldId("medicalInvestigation1_DATE"))
                                                  .date(LocalDate.now())
                                                  .build())
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("58"))
                      .value(
                          ElementValueDiagnosisList.builder()
                              .diagnoses(
                                  List.of(
                                      ElementValueDiagnosis.builder()
                                          .code("AAAAAAAAAAAA")
                                          .description("Paratyfoidfeber C")
                                          .terminology("ICD_10_SE")
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("5"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("5.1"))
                              .text("Historiken för diagnoserna")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("9"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("9.1"))
                              .text("Funktionsnedsättning")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("51"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("51.1"))
                              .text("prognos")
                              .build()
                      )
                      .build()
              )
          )
          .build();

      final var xml = generator.generate(certificate, false);

      assertFalse(schematronValidator.validate(certificate.id(), xml,
          FK7809_SCHEMATRON_PATH));
    }

    @Test
    void shallReturnFalseIfDiagnosisHasInvalidDiagnosisCode() {
      final var certificate = TestDataCertificate.fk7809CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7809.create())
          .elementData(
              List.of(
                  ElementData.builder()
                      .id(new ElementId("1"))
                      .value(
                          ElementValueDateList.builder()
                              .dateListId(new FieldId("1.1"))
                              .dateList(
                                  List.of(
                                      ElementValueDate.builder()
                                          .dateId(new FieldId("journaluppgifter"))
                                          .date(LocalDate.now())
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("3"))
                      .value(
                          ElementValueBoolean.builder()
                              .booleanId(new FieldId("3.1"))
                              .value(true)
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("4"))
                      .value(
                          ElementValueMedicalInvestigationList.builder()
                              .id(new FieldId("52.2"))
                              .list(
                                  List.of(
                                      MedicalInvestigation.builder()
                                          .id(new FieldId("medicalInvestigation1"))
                                          .informationSource(
                                              ElementValueText.builder()
                                                  .textId(new FieldId(
                                                      "medicalInvestigation1_INFORMATION_SOURCE"))
                                                  .text("Example text")
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
                                                  .dateId(
                                                      new FieldId("medicalInvestigation1_DATE"))
                                                  .date(LocalDate.now())
                                                  .build())
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("58"))
                      .value(
                          ElementValueDiagnosisList.builder()
                              .diagnoses(
                                  List.of(
                                      ElementValueDiagnosis.builder()
                                          .code("A!!")
                                          .description("Paratyfoidfeber C")
                                          .terminology("ICD_10_SE")
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("5"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("5.1"))
                              .text("Historiken för diagnoserna")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("9"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("9.1"))
                              .text("Funktionsnedsättning")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("51"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("51.1"))
                              .text("prognos")
                              .build()
                      )
                      .build()
              )
          )
          .build();

      final var xml = generator.generate(certificate, false);

      assertFalse(schematronValidator.validate(certificate.id(), xml,
          FK7809_SCHEMATRON_PATH));
    }

    @Test
    void shallReturnTrueIfFiveDiagnoses() {
      final var certificate = TestDataCertificate.fk7809CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7809.create())
          .elementData(
              List.of(
                  ElementData.builder()
                      .id(new ElementId("1"))
                      .value(
                          ElementValueDateList.builder()
                              .dateListId(new FieldId("1.1"))
                              .dateList(
                                  List.of(
                                      ElementValueDate.builder()
                                          .dateId(new FieldId("journaluppgifter"))
                                          .date(LocalDate.now())
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("3"))
                      .value(
                          ElementValueBoolean.builder()
                              .booleanId(new FieldId("3.1"))
                              .value(true)
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("4"))
                      .value(
                          ElementValueMedicalInvestigationList.builder()
                              .id(new FieldId("52.2"))
                              .list(
                                  List.of(
                                      MedicalInvestigation.builder()
                                          .id(new FieldId("medicalInvestigation1"))
                                          .informationSource(
                                              ElementValueText.builder()
                                                  .textId(new FieldId(
                                                      "medicalInvestigation1_INFORMATION_SOURCE"))
                                                  .text("Example text")
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
                                                  .dateId(
                                                      new FieldId("medicalInvestigation1_DATE"))
                                                  .date(LocalDate.now())
                                                  .build())
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("58"))
                      .value(
                          ElementValueDiagnosisList.builder()
                              .diagnoses(
                                  List.of(
                                      ElementValueDiagnosis.builder()
                                          .code("A20")
                                          .description("Paratyfoidfeber C")
                                          .terminology("ICD_10_SE")
                                          .build(),
                                      ElementValueDiagnosis.builder()
                                          .code("A20")
                                          .description("Paratyfoidfeber C")
                                          .terminology("ICD_10_SE")
                                          .build(),
                                      ElementValueDiagnosis.builder()
                                          .code("A20")
                                          .description("Paratyfoidfeber C")
                                          .terminology("ICD_10_SE")
                                          .build(),
                                      ElementValueDiagnosis.builder()
                                          .code("A20")
                                          .description("Paratyfoidfeber C")
                                          .terminology("ICD_10_SE")
                                          .build(),
                                      ElementValueDiagnosis.builder()
                                          .code("A20")
                                          .description("Paratyfoidfeber C")
                                          .terminology("ICD_10_SE")
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("5"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("5.1"))
                              .text("Historiken för diagnoserna")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("9"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("9.1"))
                              .text("Funktionsnedsättning")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("51"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("51.1"))
                              .text("prognos")
                              .build()
                      )
                      .build()
              )
          )
          .build();

      final var xml = generator.generate(certificate, false);

      assertTrue(schematronValidator.validate(certificate.id(), xml,
          FK7809_SCHEMATRON_PATH));
    }

    @Test
    void shallReturnFalseIfMoreThanFiveDiagnoses() {
      final var certificate = TestDataCertificate.fk7809CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7809.create())
          .elementData(
              List.of(
                  ElementData.builder()
                      .id(new ElementId("1"))
                      .value(
                          ElementValueDateList.builder()
                              .dateListId(new FieldId("1.1"))
                              .dateList(
                                  List.of(
                                      ElementValueDate.builder()
                                          .dateId(new FieldId("journaluppgifter"))
                                          .date(LocalDate.now())
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("3"))
                      .value(
                          ElementValueBoolean.builder()
                              .booleanId(new FieldId("3.1"))
                              .value(true)
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("4"))
                      .value(
                          ElementValueMedicalInvestigationList.builder()
                              .id(new FieldId("52.2"))
                              .list(
                                  List.of(
                                      MedicalInvestigation.builder()
                                          .id(new FieldId("medicalInvestigation1"))
                                          .informationSource(
                                              ElementValueText.builder()
                                                  .textId(new FieldId(
                                                      "medicalInvestigation1_INFORMATION_SOURCE"))
                                                  .text("Example text")
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
                                                  .dateId(
                                                      new FieldId("medicalInvestigation1_DATE"))
                                                  .date(LocalDate.now())
                                                  .build())
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("58"))
                      .value(
                          ElementValueDiagnosisList.builder()
                              .diagnoses(
                                  List.of(
                                      ElementValueDiagnosis.builder()
                                          .code("A20")
                                          .description("Paratyfoidfeber C")
                                          .terminology("ICD_10_SE")
                                          .build(),
                                      ElementValueDiagnosis.builder()
                                          .code("A20")
                                          .description("Paratyfoidfeber C")
                                          .terminology("ICD_10_SE")
                                          .build(),
                                      ElementValueDiagnosis.builder()
                                          .code("A20")
                                          .description("Paratyfoidfeber C")
                                          .terminology("ICD_10_SE")
                                          .build(),
                                      ElementValueDiagnosis.builder()
                                          .code("A20")
                                          .description("Paratyfoidfeber C")
                                          .terminology("ICD_10_SE")
                                          .build(),
                                      ElementValueDiagnosis.builder()
                                          .code("A20")
                                          .description("Paratyfoidfeber C")
                                          .terminology("ICD_10_SE")
                                          .build(),
                                      ElementValueDiagnosis.builder()
                                          .code("A20")
                                          .description("Paratyfoidfeber C")
                                          .terminology("ICD_10_SE")
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("5"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("5.1"))
                              .text("Historiken för diagnoserna")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("9"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("9.1"))
                              .text("Funktionsnedsättning")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("51"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("51.1"))
                              .text("prognos")
                              .build()
                      )
                      .build(
                      )
              )
          ).build();

      final var xml = generator.generate(certificate, false);

      assertFalse(schematronValidator.validate(certificate.id(), xml,
          FK7809_SCHEMATRON_PATH));
    }

    @Test
    void shallReturnFalseIfDiagnosisMotivationIsMissing() {
      final var certificate = TestDataCertificate.fk7809CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7809.create())
          .elementData(
              List.of(
                  ElementData.builder()
                      .id(new ElementId("1"))
                      .value(
                          ElementValueDateList.builder()
                              .dateListId(new FieldId("1.1"))
                              .dateList(
                                  List.of(
                                      ElementValueDate.builder()
                                          .dateId(new FieldId("journaluppgifter"))
                                          .date(LocalDate.now())
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("3"))
                      .value(
                          ElementValueBoolean.builder()
                              .booleanId(new FieldId("3.1"))
                              .value(true)
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("4"))
                      .value(
                          ElementValueMedicalInvestigationList.builder()
                              .id(new FieldId("52.2"))
                              .list(
                                  List.of(
                                      MedicalInvestigation.builder()
                                          .id(new FieldId("medicalInvestigation1"))
                                          .informationSource(
                                              ElementValueText.builder()
                                                  .textId(new FieldId(
                                                      "medicalInvestigation1_INFORMATION_SOURCE"))
                                                  .text("Example text")
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
                                                  .dateId(
                                                      new FieldId("medicalInvestigation1_DATE"))
                                                  .date(LocalDate.now())
                                                  .build())
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("58"))
                      .value(
                          ElementValueDiagnosisList.builder()
                              .diagnoses(
                                  List.of(
                                      ElementValueDiagnosis.builder()
                                          .code("A20")
                                          .description("Paratyfoidfeber C")
                                          .terminology("ICD_10_SE")
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("9"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("9.1"))
                              .text("Funktionsnedsättning")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("51"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("51.1"))
                              .text("prognos")
                              .build()
                      )
                      .build(
                      )
              )
          ).build();

      final var xml = generator.generate(certificate, false);

      assertFalse(schematronValidator.validate(certificate.id(), xml,
          FK7809_SCHEMATRON_PATH));
    }
  }

  @Nested
  class QuestionPrognos {

    @Test
    void shallReturnTrueIfQuestionIsAddedOnce() {
      final var certificate = TestDataCertificate.fk7809CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7809.create())
          .elementData(
              List.of(
                  ElementData.builder()
                      .id(new ElementId("1"))
                      .value(
                          ElementValueDateList.builder()
                              .dateListId(new FieldId("1.1"))
                              .dateList(List.of(
                                      ElementValueDate.builder()
                                          .dateId(new FieldId("journaluppgifter"))
                                          .date(LocalDate.now())
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("3"))
                      .value(
                          ElementValueBoolean.builder()
                              .booleanId(new FieldId("3.1"))
                              .value(true)
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("4"))
                      .value(
                          ElementValueMedicalInvestigationList.builder()
                              .id(new FieldId("52.2"))
                              .list(
                                  List.of(
                                      MedicalInvestigation.builder()
                                          .id(new FieldId("medicalInvestigation1"))
                                          .informationSource(
                                              ElementValueText.builder()
                                                  .textId(new FieldId(
                                                      "medicalInvestigation1_INFORMATION_SOURCE"))
                                                  .text("Example text")
                                                  .build()
                                          )
                                          .investigationType(
                                              ElementValueCode.builder()
                                                  .codeId(new FieldId(
                                                      "medicalInvestigation1_INVESTIGATION_TYPE"))
                                                  .code("OVRIGT_UTLATANDE")
                                                  .build()
                                          )
                                          .date(
                                              ElementValueDate.builder()
                                                  .dateId(
                                                      new FieldId("medicalInvestigation1_DATE"))
                                                  .date(LocalDate.now())
                                                  .build())
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("58"))
                      .value(
                          ElementValueDiagnosisList.builder()
                              .diagnoses(
                                  List.of(
                                      ElementValueDiagnosis.builder()
                                          .code("A20")
                                          .description("Paratyfoidfeber C")
                                          .terminology("ICD_10_SE")
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("5"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("5.1"))
                              .text("Historiken för diagnoserna")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("9"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("9.1"))
                              .text("text 2")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("51"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("51.1"))
                              .text("prognos")
                              .build()
                      )
                      .build()
              )
          )
          .build();

      final var xml = generator.generate(certificate, false);

      assertTrue(schematronValidator.validate(certificate.id(), xml,
          FK7809_SCHEMATRON_PATH));
    }

    @Test
    void shallReturnFalseIfQuestionIsAddedTwice() {
      final var certificate = TestDataCertificate.fk7809CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7809.create())
          .elementData(
              List.of(
                  ElementData.builder()
                      .id(new ElementId("1"))
                      .value(
                          ElementValueDateList.builder()
                              .dateListId(new FieldId("1.1"))
                              .dateList(List.of(
                                      ElementValueDate.builder()
                                          .dateId(new FieldId("journaluppgifter"))
                                          .date(LocalDate.now())
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("3"))
                      .value(
                          ElementValueBoolean.builder()
                              .booleanId(new FieldId("3.1"))
                              .value(true)
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("4"))
                      .value(
                          ElementValueMedicalInvestigationList.builder()
                              .id(new FieldId("52.2"))
                              .list(
                                  List.of(
                                      MedicalInvestigation.builder()
                                          .id(new FieldId("medicalInvestigation1"))
                                          .informationSource(
                                              ElementValueText.builder()
                                                  .textId(new FieldId(
                                                      "medicalInvestigation1_INFORMATION_SOURCE"))
                                                  .text("Example text")
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
                                                  .dateId(
                                                      new FieldId("medicalInvestigation1_DATE"))
                                                  .date(LocalDate.now())
                                                  .build())
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("58"))
                      .value(
                          ElementValueDiagnosisList.builder()
                              .diagnoses(
                                  List.of(
                                      ElementValueDiagnosis.builder()
                                          .code("A20")
                                          .description("Paratyfoidfeber C")
                                          .terminology("ICD_10_SE")
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("5"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("5.1"))
                              .text("Historiken för diagnoserna")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("9"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("9.1"))
                              .text("text 1")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("51"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("51.1"))
                              .text("text 2")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("51"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("51.1"))
                              .text("prognos")
                              .build()
                      )
                      .build()
              )
          )
          .build();

      final var xml = generator.generate(certificate, false);

      assertFalse(schematronValidator.validate(certificate.id(), xml,
          FK7809_SCHEMATRON_PATH));
    }

    @Test
    void shallReturnFalseIfQuestionIsMissing() {
      final var certificate = TestDataCertificate.fk7809CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7809.create())
          .elementData(
              List.of(
                  ElementData.builder()
                      .id(new ElementId("1"))
                      .value(
                          ElementValueDateList.builder()
                              .dateListId(new FieldId("1.1"))
                              .dateList(List.of(
                                      ElementValueDate.builder()
                                          .dateId(new FieldId("journaluppgifter"))
                                          .date(LocalDate.now())
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("3"))
                      .value(
                          ElementValueBoolean.builder()
                              .booleanId(new FieldId("3.1"))
                              .value(true)
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("4"))
                      .value(
                          ElementValueMedicalInvestigationList.builder()
                              .id(new FieldId("52.2"))
                              .list(
                                  List.of(
                                      MedicalInvestigation.builder()
                                          .id(new FieldId("medicalInvestigation1"))
                                          .informationSource(
                                              ElementValueText.builder()
                                                  .textId(new FieldId(
                                                      "medicalInvestigation1_INFORMATION_SOURCE"))
                                                  .text("Example text")
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
                                                  .dateId(
                                                      new FieldId("medicalInvestigation1_DATE"))
                                                  .date(LocalDate.now())
                                                  .build())
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("58"))
                      .value(
                          ElementValueDiagnosisList.builder()
                              .diagnoses(
                                  List.of(
                                      ElementValueDiagnosis.builder()
                                          .code("A20")
                                          .description("Paratyfoidfeber C")
                                          .terminology("ICD_10_SE")
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("5"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("5.1"))
                              .text("Historiken för diagnoserna")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("9"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("9.1"))
                              .text("prognos")
                              .build()
                      )
                      .build()
              )
          )
          .build();

      final var xml = generator.generate(certificate, false);

      assertFalse(schematronValidator.validate(certificate.id(), xml,
          FK7809_SCHEMATRON_PATH));
    }
  }

  @Nested
  class QuestionFunktionsnedsattning {

    @Test
    void shallReturnTrueIfQuestionFunktionsnedsattning8IsAddedOnce() {
      final var certificate = TestDataCertificate.fk7809CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7809.create())
          .elementData(
              List.of(
                  ElementData.builder()
                      .id(new ElementId("1"))
                      .value(
                          ElementValueDateList.builder()
                              .dateListId(new FieldId("1.1"))
                              .dateList(List.of(
                                      ElementValueDate.builder()
                                          .dateId(new FieldId("journaluppgifter"))
                                          .date(LocalDate.now())
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("3"))
                      .value(
                          ElementValueBoolean.builder()
                              .booleanId(new FieldId("3.1"))
                              .value(true)
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("4"))
                      .value(
                          ElementValueMedicalInvestigationList.builder()
                              .id(new FieldId("52.2"))
                              .list(
                                  List.of(
                                      MedicalInvestigation.builder()
                                          .id(new FieldId("medicalInvestigation1"))
                                          .informationSource(
                                              ElementValueText.builder()
                                                  .textId(new FieldId(
                                                      "medicalInvestigation1_INFORMATION_SOURCE"))
                                                  .text("Example text")
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
                                                  .dateId(
                                                      new FieldId("medicalInvestigation1_DATE"))
                                                  .date(LocalDate.now())
                                                  .build())
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("58"))
                      .value(
                          ElementValueDiagnosisList.builder()
                              .diagnoses(
                                  List.of(
                                      ElementValueDiagnosis.builder()
                                          .code("A20")
                                          .description("Paratyfoidfeber C")
                                          .terminology("ICD_10_SE")
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("5"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("5.1"))
                              .text("Historiken för diagnoserna")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("8"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("8.1"))
                              .text("text 2")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("51"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("51.1"))
                              .text("prognos")
                              .build()
                      )
                      .build()
              )
          )
          .build();

      final var xml = generator.generate(certificate, false);

      assertTrue(schematronValidator.validate(certificate.id(), xml,
          FK7809_SCHEMATRON_PATH));
    }

    @Test
    void shallReturnTrueIfQuestionFunktionsnedsattning9IsAddedOnce() {
      final var certificate = TestDataCertificate.fk7809CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7809.create())
          .elementData(
              List.of(
                  ElementData.builder()
                      .id(new ElementId("1"))
                      .value(
                          ElementValueDateList.builder()
                              .dateListId(new FieldId("1.1"))
                              .dateList(List.of(
                                      ElementValueDate.builder()
                                          .dateId(new FieldId("journaluppgifter"))
                                          .date(LocalDate.now())
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("3"))
                      .value(
                          ElementValueBoolean.builder()
                              .booleanId(new FieldId("3.1"))
                              .value(true)
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("4"))
                      .value(
                          ElementValueMedicalInvestigationList.builder()
                              .id(new FieldId("52.2"))
                              .list(
                                  List.of(
                                      MedicalInvestigation.builder()
                                          .id(new FieldId("medicalInvestigation1"))
                                          .informationSource(
                                              ElementValueText.builder()
                                                  .textId(new FieldId(
                                                      "medicalInvestigation1_INFORMATION_SOURCE"))
                                                  .text("Example text")
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
                                                  .dateId(
                                                      new FieldId("medicalInvestigation1_DATE"))
                                                  .date(LocalDate.now())
                                                  .build())
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("58"))
                      .value(
                          ElementValueDiagnosisList.builder()
                              .diagnoses(
                                  List.of(
                                      ElementValueDiagnosis.builder()
                                          .code("A20")
                                          .description("Paratyfoidfeber C")
                                          .terminology("ICD_10_SE")
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("5"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("5.1"))
                              .text("Historiken för diagnoserna")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("9"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("9.1"))
                              .text("text 2")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("51"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("51.1"))
                              .text("prognos")
                              .build()
                      )
                      .build()
              )
          )
          .build();

      final var xml = generator.generate(certificate, false);

      assertTrue(schematronValidator.validate(certificate.id(), xml,
          FK7809_SCHEMATRON_PATH));
    }

    @Test
    void shallReturnTrueIfQuestionFunktionsnedsattning10IsAddedOnce() {
      final var certificate = TestDataCertificate.fk7809CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7809.create())
          .elementData(
              List.of(
                  ElementData.builder()
                      .id(new ElementId("1"))
                      .value(
                          ElementValueDateList.builder()
                              .dateListId(new FieldId("1.1"))
                              .dateList(List.of(
                                      ElementValueDate.builder()
                                          .dateId(new FieldId("journaluppgifter"))
                                          .date(LocalDate.now())
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("3"))
                      .value(
                          ElementValueBoolean.builder()
                              .booleanId(new FieldId("3.1"))
                              .value(true)
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("4"))
                      .value(
                          ElementValueMedicalInvestigationList.builder()
                              .id(new FieldId("52.2"))
                              .list(
                                  List.of(
                                      MedicalInvestigation.builder()
                                          .id(new FieldId("medicalInvestigation1"))
                                          .informationSource(
                                              ElementValueText.builder()
                                                  .textId(new FieldId(
                                                      "medicalInvestigation1_INFORMATION_SOURCE"))
                                                  .text("Example text")
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
                                                  .dateId(
                                                      new FieldId("medicalInvestigation1_DATE"))
                                                  .date(LocalDate.now())
                                                  .build())
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("58"))
                      .value(
                          ElementValueDiagnosisList.builder()
                              .diagnoses(
                                  List.of(
                                      ElementValueDiagnosis.builder()
                                          .code("A20")
                                          .description("Paratyfoidfeber C")
                                          .terminology("ICD_10_SE")
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("5"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("5.1"))
                              .text("Historiken för diagnoserna")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("10"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("10.1"))
                              .text("text 2")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("51"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("51.1"))
                              .text("prognos")
                              .build()
                      )
                      .build()
              )
          )
          .build();

      final var xml = generator.generate(certificate, false);

      assertTrue(schematronValidator.validate(certificate.id(), xml,
          FK7809_SCHEMATRON_PATH));
    }

    @Test
    void shallReturnTrueIfQuestionFunktionsnedsattning11IsAddedOnce() {
      final var certificate = TestDataCertificate.fk7809CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7809.create())
          .elementData(
              List.of(
                  ElementData.builder()
                      .id(new ElementId("1"))
                      .value(
                          ElementValueDateList.builder()
                              .dateListId(new FieldId("1.1"))
                              .dateList(List.of(
                                      ElementValueDate.builder()
                                          .dateId(new FieldId("journaluppgifter"))
                                          .date(LocalDate.now())
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("3"))
                      .value(
                          ElementValueBoolean.builder()
                              .booleanId(new FieldId("3.1"))
                              .value(true)
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("4"))
                      .value(
                          ElementValueMedicalInvestigationList.builder()
                              .id(new FieldId("52.2"))
                              .list(
                                  List.of(
                                      MedicalInvestigation.builder()
                                          .id(new FieldId("medicalInvestigation1"))
                                          .informationSource(
                                              ElementValueText.builder()
                                                  .textId(new FieldId(
                                                      "medicalInvestigation1_INFORMATION_SOURCE"))
                                                  .text("Example text")
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
                                                  .dateId(
                                                      new FieldId("medicalInvestigation1_DATE"))
                                                  .date(LocalDate.now())
                                                  .build())
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("58"))
                      .value(
                          ElementValueDiagnosisList.builder()
                              .diagnoses(
                                  List.of(
                                      ElementValueDiagnosis.builder()
                                          .code("A20")
                                          .description("Paratyfoidfeber C")
                                          .terminology("ICD_10_SE")
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("5"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("5.1"))
                              .text("Historiken för diagnoserna")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("11"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("11.1"))
                              .text("text 2")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("51"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("51.1"))
                              .text("prognos")
                              .build()
                      )
                      .build()
              )
          )
          .build();

      final var xml = generator.generate(certificate, false);

      assertTrue(schematronValidator.validate(certificate.id(), xml,
          FK7809_SCHEMATRON_PATH));
    }

    @Test
    void shallReturnTrueIfQuestionFunktionsnedsattning12IsAddedOnce() {
      final var certificate = TestDataCertificate.fk7809CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7809.create())
          .elementData(
              List.of(
                  ElementData.builder()
                      .id(new ElementId("1"))
                      .value(
                          ElementValueDateList.builder()
                              .dateListId(new FieldId("1.1"))
                              .dateList(List.of(
                                      ElementValueDate.builder()
                                          .dateId(new FieldId("journaluppgifter"))
                                          .date(LocalDate.now())
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("3"))
                      .value(
                          ElementValueBoolean.builder()
                              .booleanId(new FieldId("3.1"))
                              .value(true)
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("4"))
                      .value(
                          ElementValueMedicalInvestigationList.builder()
                              .id(new FieldId("52.2"))
                              .list(
                                  List.of(
                                      MedicalInvestigation.builder()
                                          .id(new FieldId("medicalInvestigation1"))
                                          .informationSource(
                                              ElementValueText.builder()
                                                  .textId(new FieldId(
                                                      "medicalInvestigation1_INFORMATION_SOURCE"))
                                                  .text("Example text")
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
                                                  .dateId(
                                                      new FieldId("medicalInvestigation1_DATE"))
                                                  .date(LocalDate.now())
                                                  .build())
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("58"))
                      .value(
                          ElementValueDiagnosisList.builder()
                              .diagnoses(
                                  List.of(
                                      ElementValueDiagnosis.builder()
                                          .code("A20")
                                          .description("Paratyfoidfeber C")
                                          .terminology("ICD_10_SE")
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("5"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("5.1"))
                              .text("Historiken för diagnoserna")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("12"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("12.1"))
                              .text("text 2")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("51"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("51.1"))
                              .text("prognos")
                              .build()
                      )
                      .build()
              )
          )
          .build();

      final var xml = generator.generate(certificate, false);

      assertTrue(schematronValidator.validate(certificate.id(), xml,
          FK7809_SCHEMATRON_PATH));
    }

    @Test
    void shallReturnTrueIfQuestionFunktionsnedsattning13IsAddedOnce() {
      final var certificate = TestDataCertificate.fk7809CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7809.create())
          .elementData(
              List.of(
                  ElementData.builder()
                      .id(new ElementId("1"))
                      .value(
                          ElementValueDateList.builder()
                              .dateListId(new FieldId("1.1"))
                              .dateList(List.of(
                                      ElementValueDate.builder()
                                          .dateId(new FieldId("journaluppgifter"))
                                          .date(LocalDate.now())
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("3"))
                      .value(
                          ElementValueBoolean.builder()
                              .booleanId(new FieldId("3.1"))
                              .value(true)
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("4"))
                      .value(
                          ElementValueMedicalInvestigationList.builder()
                              .id(new FieldId("52.2"))
                              .list(
                                  List.of(
                                      MedicalInvestigation.builder()
                                          .id(new FieldId("medicalInvestigation1"))
                                          .informationSource(
                                              ElementValueText.builder()
                                                  .textId(new FieldId(
                                                      "medicalInvestigation1_INFORMATION_SOURCE"))
                                                  .text("Example text")
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
                                                  .dateId(
                                                      new FieldId("medicalInvestigation1_DATE"))
                                                  .date(LocalDate.now())
                                                  .build())
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("58"))
                      .value(
                          ElementValueDiagnosisList.builder()
                              .diagnoses(
                                  List.of(
                                      ElementValueDiagnosis.builder()
                                          .code("A20")
                                          .description("Paratyfoidfeber C")
                                          .terminology("ICD_10_SE")
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("5"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("5.1"))
                              .text("Historiken för diagnoserna")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("13"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("13.1"))
                              .text("text 2")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("51"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("51.1"))
                              .text("prognos")
                              .build()
                      )
                      .build()
              )
          )
          .build();

      final var xml = generator.generate(certificate, false);

      assertTrue(schematronValidator.validate(certificate.id(), xml,
          FK7809_SCHEMATRON_PATH));
    }

    @Test
    void shallReturnTrueIfQuestionFunktionsnedsattning14IsAddedOnce() {
      final var certificate = TestDataCertificate.fk7809CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7809.create())
          .elementData(
              List.of(
                  ElementData.builder()
                      .id(new ElementId("1"))
                      .value(
                          ElementValueDateList.builder()
                              .dateListId(new FieldId("1.1"))
                              .dateList(List.of(
                                      ElementValueDate.builder()
                                          .dateId(new FieldId("journaluppgifter"))
                                          .date(LocalDate.now())
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("3"))
                      .value(
                          ElementValueBoolean.builder()
                              .booleanId(new FieldId("3.1"))
                              .value(true)
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("4"))
                      .value(
                          ElementValueMedicalInvestigationList.builder()
                              .id(new FieldId("52.2"))
                              .list(
                                  List.of(
                                      MedicalInvestigation.builder()
                                          .id(new FieldId("medicalInvestigation1"))
                                          .informationSource(
                                              ElementValueText.builder()
                                                  .textId(new FieldId(
                                                      "medicalInvestigation1_INFORMATION_SOURCE"))
                                                  .text("Example text")
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
                                                  .dateId(
                                                      new FieldId("medicalInvestigation1_DATE"))
                                                  .date(LocalDate.now())
                                                  .build())
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("58"))
                      .value(
                          ElementValueDiagnosisList.builder()
                              .diagnoses(
                                  List.of(
                                      ElementValueDiagnosis.builder()
                                          .code("A20")
                                          .description("Paratyfoidfeber C")
                                          .terminology("ICD_10_SE")
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("5"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("5.1"))
                              .text("Historiken för diagnoserna")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("14"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("14.1"))
                              .text("text 2")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("51"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("51.1"))
                              .text("prognos")
                              .build()
                      )
                      .build()
              )
          )
          .build();

      final var xml = generator.generate(certificate, false);

      assertTrue(schematronValidator.validate(certificate.id(), xml,
          FK7809_SCHEMATRON_PATH));
    }

    @Test
    void shallReturnTrueIfQuestionFunktionsnedsattning48IsAddedOnce() {
      final var certificate = TestDataCertificate.fk7809CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7809.create())
          .elementData(
              List.of(
                  ElementData.builder()
                      .id(new ElementId("1"))
                      .value(
                          ElementValueDateList.builder()
                              .dateListId(new FieldId("1.1"))
                              .dateList(List.of(
                                      ElementValueDate.builder()
                                          .dateId(new FieldId("journaluppgifter"))
                                          .date(LocalDate.now())
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("3"))
                      .value(
                          ElementValueBoolean.builder()
                              .booleanId(new FieldId("3.1"))
                              .value(true)
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("4"))
                      .value(
                          ElementValueMedicalInvestigationList.builder()
                              .id(new FieldId("52.2"))
                              .list(
                                  List.of(
                                      MedicalInvestigation.builder()
                                          .id(new FieldId("medicalInvestigation1"))
                                          .informationSource(
                                              ElementValueText.builder()
                                                  .textId(new FieldId(
                                                      "medicalInvestigation1_INFORMATION_SOURCE"))
                                                  .text("Example text")
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
                                                  .dateId(
                                                      new FieldId("medicalInvestigation1_DATE"))
                                                  .date(LocalDate.now())
                                                  .build())
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("58"))
                      .value(
                          ElementValueDiagnosisList.builder()
                              .diagnoses(
                                  List.of(
                                      ElementValueDiagnosis.builder()
                                          .code("A20")
                                          .description("Paratyfoidfeber C")
                                          .terminology("ICD_10_SE")
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("5"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("5.1"))
                              .text("Historiken för diagnoserna")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("48"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("48.1"))
                              .text("text 2")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("51"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("51.1"))
                              .text("prognos")
                              .build()
                      )
                      .build()
              )
          )
          .build();

      final var xml = generator.generate(certificate, false);

      assertTrue(schematronValidator.validate(certificate.id(), xml,
          FK7809_SCHEMATRON_PATH));
    }

    @Test
    void shallReturnTrueIfQuestionFunktionsnedsattning49IsAddedOnce() {
      final var certificate = TestDataCertificate.fk7809CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7809.create())
          .elementData(
              List.of(
                  ElementData.builder()
                      .id(new ElementId("1"))
                      .value(
                          ElementValueDateList.builder()
                              .dateListId(new FieldId("1.1"))
                              .dateList(List.of(
                                      ElementValueDate.builder()
                                          .dateId(new FieldId("journaluppgifter"))
                                          .date(LocalDate.now())
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("3"))
                      .value(
                          ElementValueBoolean.builder()
                              .booleanId(new FieldId("3.1"))
                              .value(true)
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("4"))
                      .value(
                          ElementValueMedicalInvestigationList.builder()
                              .id(new FieldId("52.2"))
                              .list(
                                  List.of(
                                      MedicalInvestigation.builder()
                                          .id(new FieldId("medicalInvestigation1"))
                                          .informationSource(
                                              ElementValueText.builder()
                                                  .textId(new FieldId(
                                                      "medicalInvestigation1_INFORMATION_SOURCE"))
                                                  .text("Example text")
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
                                                  .dateId(
                                                      new FieldId("medicalInvestigation1_DATE"))
                                                  .date(LocalDate.now())
                                                  .build())
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("58"))
                      .value(
                          ElementValueDiagnosisList.builder()
                              .diagnoses(
                                  List.of(
                                      ElementValueDiagnosis.builder()
                                          .code("A20")
                                          .description("Paratyfoidfeber C")
                                          .terminology("ICD_10_SE")
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("5"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("5.1"))
                              .text("Historiken för diagnoserna")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("49"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("49.1"))
                              .text("text 2")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("51"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("51.1"))
                              .text("prognos")
                              .build()
                      )
                      .build()
              )
          )
          .build();

      final var xml = generator.generate(certificate, false);

      assertTrue(schematronValidator.validate(certificate.id(), xml,
          FK7809_SCHEMATRON_PATH));
    }

    @Test
    void shallReturnFalseIfSameFunktionsnedsattningIsAddedTwice() {
      final var certificate = TestDataCertificate.fk7809CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7809.create())
          .elementData(
              List.of(
                  ElementData.builder()
                      .id(new ElementId("1"))
                      .value(
                          ElementValueDateList.builder()
                              .dateListId(new FieldId("1.1"))
                              .dateList(List.of(
                                      ElementValueDate.builder()
                                          .dateId(new FieldId("journaluppgifter"))
                                          .date(LocalDate.now())
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("3"))
                      .value(
                          ElementValueBoolean.builder()
                              .booleanId(new FieldId("3.1"))
                              .value(true)
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("4"))
                      .value(
                          ElementValueMedicalInvestigationList.builder()
                              .id(new FieldId("52.2"))
                              .list(
                                  List.of(
                                      MedicalInvestigation.builder()
                                          .id(new FieldId("medicalInvestigation1"))
                                          .informationSource(
                                              ElementValueText.builder()
                                                  .textId(new FieldId(
                                                      "medicalInvestigation1_INFORMATION_SOURCE"))
                                                  .text("Example text")
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
                                                  .dateId(
                                                      new FieldId("medicalInvestigation1_DATE"))
                                                  .date(LocalDate.now())
                                                  .build())
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("58"))
                      .value(
                          ElementValueDiagnosisList.builder()
                              .diagnoses(
                                  List.of(
                                      ElementValueDiagnosis.builder()
                                          .code("A20")
                                          .description("Paratyfoidfeber C")
                                          .terminology("ICD_10_SE")
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("5"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("5.1"))
                              .text("Historiken för diagnoserna")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("9"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("9.1"))
                              .text("text 1")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("9"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("9.1"))
                              .text("text 2")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("51"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("51.1"))
                              .text("prognos")
                              .build()
                      )
                      .build()
              )
          )
          .build();

      final var xml = generator.generate(certificate, false);

      assertFalse(schematronValidator.validate(certificate.id(), xml,
          FK7809_SCHEMATRON_PATH));
    }

    @Test
    void shallReturnFalseIfQuestionFunktionsnedsattningIsMissing() {
      final var certificate = TestDataCertificate.fk7809CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7809.create())
          .elementData(
              List.of(
                  ElementData.builder()
                      .id(new ElementId("1"))
                      .value(
                          ElementValueDateList.builder()
                              .dateListId(new FieldId("1.1"))
                              .dateList(List.of(
                                      ElementValueDate.builder()
                                          .dateId(new FieldId("journaluppgifter"))
                                          .date(LocalDate.now())
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("3"))
                      .value(
                          ElementValueBoolean.builder()
                              .booleanId(new FieldId("3.1"))
                              .value(true)
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("4"))
                      .value(
                          ElementValueMedicalInvestigationList.builder()
                              .id(new FieldId("52.2"))
                              .list(
                                  List.of(
                                      MedicalInvestigation.builder()
                                          .id(new FieldId("medicalInvestigation1"))
                                          .informationSource(
                                              ElementValueText.builder()
                                                  .textId(new FieldId(
                                                      "medicalInvestigation1_INFORMATION_SOURCE"))
                                                  .text("Example text")
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
                                                  .dateId(
                                                      new FieldId("medicalInvestigation1_DATE"))
                                                  .date(LocalDate.now())
                                                  .build())
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("58"))
                      .value(
                          ElementValueDiagnosisList.builder()
                              .diagnoses(
                                  List.of(
                                      ElementValueDiagnosis.builder()
                                          .code("A20")
                                          .description("Paratyfoidfeber C")
                                          .terminology("ICD_10_SE")
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("5"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("5.1"))
                              .text("Historiken för diagnoserna")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("51"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("51.1"))
                              .text("prognos")
                              .build()
                      )
                      .build()
              )
          )
          .build();

      final var xml = generator.generate(certificate, false);

      assertFalse(schematronValidator.validate(certificate.id(), xml,
          FK7809_SCHEMATRON_PATH));
    }

    @Test
    void shallReturnTrueIfAllQuestionFunktionsnedsattningAreAdded() {
      final var certificate = TestDataCertificate.fk7809CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7809.create())
          .elementData(
              List.of(
                  ElementData.builder()
                      .id(new ElementId("1"))
                      .value(
                          ElementValueDateList.builder()
                              .dateListId(new FieldId("1.1"))
                              .dateList(List.of(
                                      ElementValueDate.builder()
                                          .dateId(new FieldId("journaluppgifter"))
                                          .date(LocalDate.now())
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("3"))
                      .value(
                          ElementValueBoolean.builder()
                              .booleanId(new FieldId("3.1"))
                              .value(true)
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("4"))
                      .value(
                          ElementValueMedicalInvestigationList.builder()
                              .id(new FieldId("52.2"))
                              .list(
                                  List.of(
                                      MedicalInvestigation.builder()
                                          .id(new FieldId("medicalInvestigation1"))
                                          .informationSource(
                                              ElementValueText.builder()
                                                  .textId(new FieldId(
                                                      "medicalInvestigation1_INFORMATION_SOURCE"))
                                                  .text("Example text")
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
                                                  .dateId(
                                                      new FieldId("medicalInvestigation1_DATE"))
                                                  .date(LocalDate.now())
                                                  .build())
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("58"))
                      .value(
                          ElementValueDiagnosisList.builder()
                              .diagnoses(
                                  List.of(
                                      ElementValueDiagnosis.builder()
                                          .code("A20")
                                          .description("Paratyfoidfeber C")
                                          .terminology("ICD_10_SE")
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("5"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("5.1"))
                              .text("Historiken för diagnoserna")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("8"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("8.1"))
                              .text("text 2")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("9"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("9.1"))
                              .text("text 2")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("10"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("10.1"))
                              .text("text 2")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("11"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("11.1"))
                              .text("text 2")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("12"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("12.1"))
                              .text("text 2")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("13"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("13.1"))
                              .text("text 2")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("14"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("14.1"))
                              .text("text 2")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("48"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("48.1"))
                              .text("text 2")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("49"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("49.1"))
                              .text("text 2")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("51"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("51.1"))
                              .text("prognos")
                              .build()
                      )
                      .build()
              )
          )
          .build();

      final var xml = generator.generate(certificate, false);

      assertTrue(schematronValidator.validate(certificate.id(), xml,
          FK7809_SCHEMATRON_PATH));
    }
  }

  @Nested
  class QuestionMedicalInvestigation {

    @Test
    void shallReturnFalseIfBooleanIsMissing() {
      final var certificate = TestDataCertificate.fk7809CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7809.create())
          .elementData(
              List.of(
                  ElementData.builder()
                      .id(new ElementId("1"))
                      .value(
                          ElementValueDateList.builder()
                              .dateListId(new FieldId("1.1"))
                              .dateList(List.of(
                                      ElementValueDate.builder()
                                          .dateId(new FieldId("journaluppgifter"))
                                          .date(LocalDate.now())
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("3"))
                      .value(
                          ElementValueBoolean.builder()
                              .booleanId(new FieldId("3.1"))
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("4"))
                      .value(
                          ElementValueMedicalInvestigationList.builder()
                              .id(new FieldId("52.2"))
                              .list(
                                  Collections.emptyList()
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("58"))
                      .value(
                          ElementValueDiagnosisList.builder()
                              .diagnoses(
                                  List.of(
                                      ElementValueDiagnosis.builder()
                                          .code("A20")
                                          .description("Paratyfoidfeber C")
                                          .terminology("ICD_10_SE")
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("5"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("5.1"))
                              .text("Historiken för diagnoserna")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("9"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("9.1"))
                              .text("text 2")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("51"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("51.1"))
                              .text("prognos")
                              .build()
                      )
                      .build()
              )
          )
          .build();

      final var xml = generator.generate(certificate, false);

      assertFalse(schematronValidator.validate(certificate.id(), xml,
          FK7809_SCHEMATRON_PATH));
    }


    @Test
    void shallReturnTrueIfBooleanIsFalseAndMIIsMissing() {
      final var certificate = TestDataCertificate.fk7809CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7809.create())
          .elementData(
              List.of(
                  ElementData.builder()
                      .id(new ElementId("1"))
                      .value(
                          ElementValueDateList.builder()
                              .dateListId(new FieldId("1.1"))
                              .dateList(List.of(
                                      ElementValueDate.builder()
                                          .dateId(new FieldId("journaluppgifter"))
                                          .date(LocalDate.now())
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("3"))
                      .value(
                          ElementValueBoolean.builder()
                              .booleanId(new FieldId("3.1"))
                              .value(false)
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("4"))
                      .value(
                          ElementValueMedicalInvestigationList.builder()
                              .id(new FieldId("52.2"))
                              .list(
                                  Collections.emptyList()
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("58"))
                      .value(
                          ElementValueDiagnosisList.builder()
                              .diagnoses(
                                  List.of(
                                      ElementValueDiagnosis.builder()
                                          .code("A20")
                                          .description("Paratyfoidfeber C")
                                          .terminology("ICD_10_SE")
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("5"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("5.1"))
                              .text("Historiken för diagnoserna")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("9"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("9.1"))
                              .text("text 2")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("51"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("51.1"))
                              .text("prognos")
                              .build()
                      )
                      .build()
              )
          )
          .build();

      final var xml = generator.generate(certificate, false);

      assertTrue(schematronValidator.validate(certificate.id(), xml,
          FK7809_SCHEMATRON_PATH));
    }

    @Test
    void shallReturnFalseIfQuestionIsAddedWithoutDateAndBooleanForOtherInvestigationsIsTrue() {
      final var certificate = TestDataCertificate.fk7809CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7809.create())
          .elementData(
              List.of(
                  ElementData.builder()
                      .id(new ElementId("1"))
                      .value(
                          ElementValueDateList.builder()
                              .dateListId(new FieldId("1.1"))
                              .dateList(List.of(
                                      ElementValueDate.builder()
                                          .dateId(new FieldId("journaluppgifter"))
                                          .date(LocalDate.now())
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("3"))
                      .value(
                          ElementValueBoolean.builder()
                              .booleanId(new FieldId("3.1"))
                              .value(true)
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("4"))
                      .value(
                          ElementValueMedicalInvestigationList.builder()
                              .id(new FieldId("52.2"))
                              .list(
                                  List.of(
                                      MedicalInvestigation.builder()
                                          .id(new FieldId("medicalInvestigation1"))
                                          .informationSource(
                                              ElementValueText.builder()
                                                  .textId(new FieldId(
                                                      "medicalInvestigation1_INFORMATION_SOURCE"))
                                                  .text("Example text")
                                                  .build()
                                          )
                                          .investigationType(
                                              ElementValueCode.builder()
                                                  .codeId(new FieldId(
                                                      "medicalInvestigation1_INVESTIGATION_TYPE"))
                                                  .code("LOGOPED")
                                                  .build()
                                          )
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("58"))
                      .value(
                          ElementValueDiagnosisList.builder()
                              .diagnoses(
                                  List.of(
                                      ElementValueDiagnosis.builder()
                                          .code("A20")
                                          .description("Paratyfoidfeber C")
                                          .terminology("ICD_10_SE")
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("5"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("5.1"))
                              .text("Historiken för diagnoserna")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("9"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("9.1"))
                              .text("text 2")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("51"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("51.1"))
                              .text("prognos")
                              .build()
                      )
                      .build()
              )
          )
          .build();

      final var xml = generator.generate(certificate, false);

      assertFalse(schematronValidator.validate(certificate.id(), xml,
          FK7809_SCHEMATRON_PATH));
    }

    @Test
    void shallReturnFalseIfQuestionIsAddedWithoutSourceAndBooleanForOtherInvestigationsIsTrue() {
      final var certificate = TestDataCertificate.fk7809CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7809.create())
          .elementData(
              List.of(
                  ElementData.builder()
                      .id(new ElementId("1"))
                      .value(
                          ElementValueDateList.builder()
                              .dateListId(new FieldId("1.1"))
                              .dateList(List.of(
                                      ElementValueDate.builder()
                                          .dateId(new FieldId("journaluppgifter"))
                                          .date(LocalDate.now())
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("3"))
                      .value(
                          ElementValueBoolean.builder()
                              .booleanId(new FieldId("3.1"))
                              .value(true)
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("4"))
                      .value(
                          ElementValueMedicalInvestigationList.builder()
                              .id(new FieldId("52.2"))
                              .list(
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
                                                  .build()
                                          )
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("58"))
                      .value(
                          ElementValueDiagnosisList.builder()
                              .diagnoses(
                                  List.of(
                                      ElementValueDiagnosis.builder()
                                          .code("A20")
                                          .description("Paratyfoidfeber C")
                                          .terminology("ICD_10_SE")
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("5"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("5.1"))
                              .text("Historiken för diagnoserna")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("9"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("9.1"))
                              .text("text 2")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("51"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("51.1"))
                              .text("prognos")
                              .build()
                      )
                      .build()
              )
          )
          .build();

      final var xml = generator.generate(certificate, false);

      assertFalse(schematronValidator.validate(certificate.id(), xml,
          FK7809_SCHEMATRON_PATH));
    }

    @Test
    void shallReturnFalseIfQuestionIsAddedWithoutTypeAndBooleanForOtherInvestigationsIsTrue() {
      final var certificate = TestDataCertificate.fk7809CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7809.create())
          .elementData(
              List.of(
                  ElementData.builder()
                      .id(new ElementId("1"))
                      .value(
                          ElementValueDateList.builder()
                              .dateListId(new FieldId("1.1"))
                              .dateList(List.of(
                                      ElementValueDate.builder()
                                          .dateId(new FieldId("journaluppgifter"))
                                          .date(LocalDate.now())
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("3"))
                      .value(
                          ElementValueBoolean.builder()
                              .booleanId(new FieldId("3.1"))
                              .value(true)
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("4"))
                      .value(
                          ElementValueMedicalInvestigationList.builder()
                              .id(new FieldId("52.2"))
                              .list(
                                  List.of(
                                      MedicalInvestigation.builder()
                                          .id(new FieldId("medicalInvestigation1"))
                                          .informationSource(
                                              ElementValueText.builder()
                                                  .textId(new FieldId(
                                                      "medicalInvestigation1_INFORMATION_SOURCE"))
                                                  .text("Example text")
                                                  .build()
                                          )
                                          .date(
                                              ElementValueDate.builder()
                                                  .dateId(new FieldId("medicalInvestigation1_DATE"))
                                                  .date(LocalDate.now())
                                                  .build()
                                          )
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("58"))
                      .value(
                          ElementValueDiagnosisList.builder()
                              .diagnoses(
                                  List.of(
                                      ElementValueDiagnosis.builder()
                                          .code("A20")
                                          .description("Paratyfoidfeber C")
                                          .terminology("ICD_10_SE")
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("5"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("5.1"))
                              .text("Historiken för diagnoserna")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("9"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("9.1"))
                              .text("text 2")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("51"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("51.1"))
                              .text("prognos")
                              .build()
                      )
                      .build()
              )
          )
          .build();

      final var xml = generator.generate(certificate, false);

      assertFalse(schematronValidator.validate(certificate.id(), xml,
          FK7809_SCHEMATRON_PATH));
    }

    @Test
    void shallReturnTrueIfQuestionIsAddedAndBooleanForOtherInvestigationsIsTrue() {
      final var certificate = TestDataCertificate.fk7809CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7809.create())
          .elementData(
              List.of(
                  ElementData.builder()
                      .id(new ElementId("1"))
                      .value(
                          ElementValueDateList.builder()
                              .dateListId(new FieldId("1.1"))
                              .dateList(List.of(
                                      ElementValueDate.builder()
                                          .dateId(new FieldId("journaluppgifter"))
                                          .date(LocalDate.now())
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("3"))
                      .value(
                          ElementValueBoolean.builder()
                              .booleanId(new FieldId("3.1"))
                              .value(true)
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("4"))
                      .value(
                          ElementValueMedicalInvestigationList.builder()
                              .id(new FieldId("52.2"))
                              .list(
                                  List.of(
                                      MedicalInvestigation.builder()
                                          .id(new FieldId("medicalInvestigation1"))
                                          .informationSource(
                                              ElementValueText.builder()
                                                  .textId(new FieldId(
                                                      "medicalInvestigation1_INFORMATION_SOURCE"))
                                                  .text("Example text")
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
                                                  .dateId(
                                                      new FieldId("medicalInvestigation1_DATE"))
                                                  .date(LocalDate.now())
                                                  .build())
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("58"))
                      .value(
                          ElementValueDiagnosisList.builder()
                              .diagnoses(
                                  List.of(
                                      ElementValueDiagnosis.builder()
                                          .code("A20")
                                          .description("Paratyfoidfeber C")
                                          .terminology("ICD_10_SE")
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("5"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("5.1"))
                              .text("Historiken för diagnoserna")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("9"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("9.1"))
                              .text("text 2")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("51"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("51.1"))
                              .text("prognos")
                              .build()
                      )
                      .build()
              )
          )
          .build();

      final var xml = generator.generate(certificate, false);

      assertTrue(schematronValidator.validate(certificate.id(), xml,
          FK7809_SCHEMATRON_PATH));
    }

    @Test
    void shouldReturnFalseIfMoreThanThreeMI() {
      final var certificate = TestDataCertificate.fk7809CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7809.create())
          .elementData(
              List.of(
                  ElementData.builder()
                      .id(new ElementId("1"))
                      .value(
                          ElementValueDateList.builder()
                              .dateListId(new FieldId("1.1"))
                              .dateList(List.of(
                                      ElementValueDate.builder()
                                          .dateId(new FieldId("journaluppgifter"))
                                          .date(LocalDate.now())
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("3"))
                      .value(
                          ElementValueBoolean.builder()
                              .booleanId(new FieldId("3.1"))
                              .value(true)
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("4"))
                      .value(
                          ElementValueMedicalInvestigationList.builder()
                              .id(new FieldId("52.2"))
                              .list(
                                  List.of(
                                      MedicalInvestigation.builder()
                                          .id(new FieldId("medicalInvestigation1"))
                                          .informationSource(
                                              ElementValueText.builder()
                                                  .textId(new FieldId(
                                                      "medicalInvestigation1_INFORMATION_SOURCE"))
                                                  .text("Example text")
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
                                                  .dateId(
                                                      new FieldId("medicalInvestigation1_DATE"))
                                                  .date(LocalDate.now())
                                                  .build())
                                          .build(),
                                      MedicalInvestigation.builder()
                                          .id(new FieldId("medicalInvestigation2"))
                                          .informationSource(
                                              ElementValueText.builder()
                                                  .textId(new FieldId(
                                                      "medicalInvestigation2_INFORMATION_SOURCE"))
                                                  .text("Example text")
                                                  .build()
                                          )
                                          .investigationType(
                                              ElementValueCode.builder()
                                                  .codeId(new FieldId(
                                                      "medicalInvestigation2_INVESTIGATION_TYPE"))
                                                  .code("LOGOPED")
                                                  .build()
                                          )
                                          .date(
                                              ElementValueDate.builder()
                                                  .dateId(
                                                      new FieldId("medicalInvestigation2_DATE"))
                                                  .date(LocalDate.now())
                                                  .build())
                                          .build(),
                                      MedicalInvestigation.builder()
                                          .id(new FieldId("medicalInvestigation3"))
                                          .informationSource(
                                              ElementValueText.builder()
                                                  .textId(new FieldId(
                                                      "medicalInvestigation3_INFORMATION_SOURCE"))
                                                  .text("Example text")
                                                  .build()
                                          )
                                          .investigationType(
                                              ElementValueCode.builder()
                                                  .codeId(new FieldId(
                                                      "medicalInvestigation3_INVESTIGATION_TYPE"))
                                                  .code("LOGOPED")
                                                  .build()
                                          )
                                          .date(
                                              ElementValueDate.builder()
                                                  .dateId(
                                                      new FieldId("medicalInvestigation3_DATE"))
                                                  .date(LocalDate.now())
                                                  .build())
                                          .build(),
                                      MedicalInvestigation.builder()
                                          .id(new FieldId("medicalInvestigation2"))
                                          .informationSource(
                                              ElementValueText.builder()
                                                  .textId(new FieldId(
                                                      "medicalInvestigation2_INFORMATION_SOURCE"))
                                                  .text("Example text")
                                                  .build()
                                          )
                                          .investigationType(
                                              ElementValueCode.builder()
                                                  .codeId(new FieldId(
                                                      "medicalInvestigation2_INVESTIGATION_TYPE"))
                                                  .code("LOGOPED")
                                                  .build()
                                          )
                                          .date(
                                              ElementValueDate.builder()
                                                  .dateId(
                                                      new FieldId("medicalInvestigation2_DATE"))
                                                  .date(LocalDate.now())
                                                  .build())
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("58"))
                      .value(
                          ElementValueDiagnosisList.builder()
                              .diagnoses(
                                  List.of(
                                      ElementValueDiagnosis.builder()
                                          .code("A20")
                                          .description("Paratyfoidfeber C")
                                          .terminology("ICD_10_SE")
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("5"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("5.1"))
                              .text("Historiken för diagnoserna")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("9"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("9.1"))
                              .text("text 2")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("51"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("51.1"))
                              .text("prognos")
                              .build()
                      )
                      .build()
              )
          )
          .build();

      final var xml = generator.generate(certificate, false);

      assertFalse(schematronValidator.validate(certificate.id(), xml,
          FK7809_SCHEMATRON_PATH));
    }

    @Test
    void shouldReturnTrueIfThreeMI() {
      final var certificate = TestDataCertificate.fk7809CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7809.create())
          .elementData(
              List.of(
                  ElementData.builder()
                      .id(new ElementId("1"))
                      .value(
                          ElementValueDateList.builder()
                              .dateListId(new FieldId("1.1"))
                              .dateList(List.of(
                                      ElementValueDate.builder()
                                          .dateId(new FieldId("journaluppgifter"))
                                          .date(LocalDate.now())
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("3"))
                      .value(
                          ElementValueBoolean.builder()
                              .booleanId(new FieldId("3.1"))
                              .value(true)
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("4"))
                      .value(
                          ElementValueMedicalInvestigationList.builder()
                              .id(new FieldId("52.2"))
                              .list(
                                  List.of(
                                      MedicalInvestigation.builder()
                                          .id(new FieldId("medicalInvestigation1"))
                                          .informationSource(
                                              ElementValueText.builder()
                                                  .textId(new FieldId(
                                                      "medicalInvestigation1_INFORMATION_SOURCE"))
                                                  .text("Example text")
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
                                                  .dateId(
                                                      new FieldId("medicalInvestigation1_DATE"))
                                                  .date(LocalDate.now())
                                                  .build())
                                          .build(),
                                      MedicalInvestigation.builder()
                                          .id(new FieldId("medicalInvestigation2"))
                                          .informationSource(
                                              ElementValueText.builder()
                                                  .textId(new FieldId(
                                                      "medicalInvestigation2_INFORMATION_SOURCE"))
                                                  .text("Example text")
                                                  .build()
                                          )
                                          .investigationType(
                                              ElementValueCode.builder()
                                                  .codeId(new FieldId(
                                                      "medicalInvestigation2_INVESTIGATION_TYPE"))
                                                  .code("LOGOPED")
                                                  .build()
                                          )
                                          .date(
                                              ElementValueDate.builder()
                                                  .dateId(
                                                      new FieldId("medicalInvestigation2_DATE"))
                                                  .date(LocalDate.now())
                                                  .build())
                                          .build(),
                                      MedicalInvestigation.builder()
                                          .id(new FieldId("medicalInvestigation2"))
                                          .informationSource(
                                              ElementValueText.builder()
                                                  .textId(new FieldId(
                                                      "medicalInvestigation3_INFORMATION_SOURCE"))
                                                  .text("Example text")
                                                  .build()
                                          )
                                          .investigationType(
                                              ElementValueCode.builder()
                                                  .codeId(new FieldId(
                                                      "medicalInvestigation3_INVESTIGATION_TYPE"))
                                                  .code("LOGOPED")
                                                  .build()
                                          )
                                          .date(
                                              ElementValueDate.builder()
                                                  .dateId(
                                                      new FieldId("medicalInvestigation3_DATE"))
                                                  .date(LocalDate.now())
                                                  .build())
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("58"))
                      .value(
                          ElementValueDiagnosisList.builder()
                              .diagnoses(
                                  List.of(
                                      ElementValueDiagnosis.builder()
                                          .code("A20")
                                          .description("Paratyfoidfeber C")
                                          .terminology("ICD_10_SE")
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("5"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("5.1"))
                              .text("Historiken för diagnoserna")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("9"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("9.1"))
                              .text("text 2")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("51"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("51.1"))
                              .text("prognos")
                              .build()
                      )
                      .build()
              )
          )
          .build();

      final var xml = generator.generate(certificate, false);

      assertTrue(schematronValidator.validate(certificate.id(), xml,
          FK7809_SCHEMATRON_PATH));
    }

    @Test
    void shallReturnFalseIfAddedButBooleanForOtherInvestigationsIsFalse() {
      final var certificate = TestDataCertificate.fk7809CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7809.create())
          .elementData(
              List.of(
                  ElementData.builder()
                      .id(new ElementId("1"))
                      .value(
                          ElementValueDateList.builder()
                              .dateListId(new FieldId("1.1"))
                              .dateList(List.of(
                                      ElementValueDate.builder()
                                          .dateId(new FieldId("journaluppgifter"))
                                          .date(LocalDate.now())
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("3"))
                      .value(
                          ElementValueBoolean.builder()
                              .booleanId(new FieldId("3.1"))
                              .value(false)
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("4"))
                      .value(
                          ElementValueMedicalInvestigationList.builder()
                              .id(new FieldId("52.2"))
                              .list(
                                  List.of(
                                      MedicalInvestigation.builder()
                                          .id(new FieldId("medicalInvestigation1"))
                                          .informationSource(
                                              ElementValueText.builder()
                                                  .textId(new FieldId(
                                                      "medicalInvestigation1_INFORMATION_SOURCE"))
                                                  .text("Example text")
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
                                                  .dateId(
                                                      new FieldId("medicalInvestigation1_DATE"))
                                                  .date(LocalDate.now())
                                                  .build())
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("58"))
                      .value(
                          ElementValueDiagnosisList.builder()
                              .diagnoses(
                                  List.of(
                                      ElementValueDiagnosis.builder()
                                          .code("A20")
                                          .description("Paratyfoidfeber C")
                                          .terminology("ICD_10_SE")
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("5"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("5.1"))
                              .text("Historiken för diagnoserna")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("9"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("9.1"))
                              .text("text 1")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("51"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("51.1"))
                              .text("text 2")
                              .build()
                      )
                      .build()
              )
          )
          .build();

      final var xml = generator.generate(certificate, false);

      assertFalse(schematronValidator.validate(certificate.id(), xml,
          FK7809_SCHEMATRON_PATH));
    }

    @Test
    void shallReturnFalseIfQuestionIsMissingButBooleanForOtherInvestigationsIsTrue() {
      final var certificate = TestDataCertificate.fk7809CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7809.create())
          .elementData(
              List.of(
                  ElementData.builder()
                      .id(new ElementId("1"))
                      .value(
                          ElementValueDateList.builder()
                              .dateListId(new FieldId("1.1"))
                              .dateList(List.of(
                                      ElementValueDate.builder()
                                          .dateId(new FieldId("journaluppgifter"))
                                          .date(LocalDate.now())
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("3"))
                      .value(
                          ElementValueBoolean.builder()
                              .booleanId(new FieldId("3.1"))
                              .value(true)
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("4"))
                      .value(
                          ElementValueMedicalInvestigationList.builder()
                              .id(new FieldId("52.2"))
                              .list(
                                  Collections.emptyList()
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("58"))
                      .value(
                          ElementValueDiagnosisList.builder()
                              .diagnoses(
                                  List.of(
                                      ElementValueDiagnosis.builder()
                                          .code("A20")
                                          .description("Paratyfoidfeber C")
                                          .terminology("ICD_10_SE")
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("5"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("5.1"))
                              .text("Historiken för diagnoserna")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("9"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("9.1"))
                              .text("prognos")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("51"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("51.1"))
                              .text("text 2")
                              .build()
                      )
                      .build()
              )
          )
          .build();

      final var xml = generator.generate(certificate, false);

      assertFalse(schematronValidator.validate(certificate.id(), xml,
          FK7809_SCHEMATRON_PATH));
    }
  }
}