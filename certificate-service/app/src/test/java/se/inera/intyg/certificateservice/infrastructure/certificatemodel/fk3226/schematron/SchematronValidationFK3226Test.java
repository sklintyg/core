package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.schematron;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.diagnosiscode.repository.DiagnosisCodeRepository;
import se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemIcd10Se;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.CertificateModelFactoryFK3226;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorBoolean;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorCertificateV4;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorCode;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorDate;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorDateList;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorDiagnosisList;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorText;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorValue;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.validation.SchemaValidatorV4;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.validation.SchematronValidator;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.validation.XmlValidationService;

@ExtendWith(MockitoExtension.class)
class SchematronValidationFK3226Test {

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
              new XmlGeneratorDiagnosisList()
          ),
          Collections.emptyList()
      ),
      new XmlValidationService(
          new SchematronValidator(),
          new SchemaValidatorV4()
      )
  );
  private CertificateModelFactoryFK3226 certificateModelFactoryFK3226;

  @BeforeEach
  void setUp() {
    schematronValidator = new SchematronValidator();
    certificateModelFactoryFK3226 = new CertificateModelFactoryFK3226(diagnosisCodeRepository,
        certificateActionFactory);
  }

  @Nested
  class ValidCertificate {

    @Test
    void shallReturnTrueForValidCertificate() {
      final var certificate = TestDataCertificate.fk3226CertificateBuilder()
          .certificateModel(certificateModelFactoryFK3226.create())
          .build();

      final var xml = generator.generate(certificate, true);

      assertTrue(schematronValidator.validate(certificate.id(), xml,
          CertificateModelFactoryFK3226.SCHEMATRON_PATH));
    }

    @Test
    void shallReturnFalseForInvalidCertificate() {
      final var certificate = TestDataCertificate.fk3226CertificateBuilder()
          .certificateModel(certificateModelFactoryFK3226.create())
          .elementData(
              Collections.emptyList()
          )
          .build();

      final var xml = generator.generate(certificate, false);

      assertFalse(schematronValidator.validate(certificate.id(), xml,
          CertificateModelFactoryFK3226.SCHEMATRON_PATH));
    }
  }

  @Nested
  class QuestionUtlatandeBaseratPaContainsAnswerAnnat {

    @Test
    void shallReturnTrueIfQuestionUtlatandeBaseratPaAnnatIsPresent() {
      final var certificate = TestDataCertificate.fk3226CertificateBuilder()
          .certificateModel(certificateModelFactoryFK3226.create())
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
                      .id(new ElementId("1.3"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("1.3"))
                              .text("Text")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("52"))
                      .value(
                          ElementValueCode.builder()
                              .codeId(new FieldId("ENDAST_PALLIATIV"))
                              .code("ENDAST_PALLIATIV")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("52.2"))
                      .value(
                          ElementValueDate.builder()
                              .dateId(new FieldId("52.2"))
                              .date(LocalDate.now())
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("53"))
                      .value(
                          ElementValueBoolean.builder()
                              .booleanId(new FieldId("53.1"))
                              .value(true)
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
                                          .terminology(CodeSystemIcd10Se.DIAGNOS_ICD_10_ID)
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build()
              )
          )
          .build();

      final var xml = generator.generate(certificate, true);

      assertTrue(schematronValidator.validate(certificate.id(), xml,
          CertificateModelFactoryFK3226.SCHEMATRON_PATH));
    }

    @Test
    void shallReturnFalseIfQuestionUtlatandeBaseratPaAnnatIsMissing() {
      final var certificate = TestDataCertificate.fk3226CertificateBuilder()
          .certificateModel(certificateModelFactoryFK3226.create())
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
                      .id(new ElementId("52"))
                      .value(
                          ElementValueCode.builder()
                              .codeId(new FieldId("ENDAST_PALLIATIV"))
                              .code("ENDAST_PALLIATIV")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("52.2"))
                      .value(
                          ElementValueDate.builder()
                              .dateId(new FieldId("52.2"))
                              .date(LocalDate.now())
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("53"))
                      .value(
                          ElementValueBoolean.builder()
                              .booleanId(new FieldId("53.1"))
                              .value(true)
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
                                          .terminology(CodeSystemIcd10Se.DIAGNOS_ICD_10_ID)
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build()
              )
          )
          .build();

      final var xml = generator.generate(certificate, false);

      assertFalse(schematronValidator.validate(certificate.id(), xml,
          CertificateModelFactoryFK3226.SCHEMATRON_PATH));
    }
  }

  @Nested
  class QuestionPatientBehandlingOchVardsituationHasValueEndastPalliativ {

    @Test
    void shallReturnTrueIfQuestionNarAktivaBehandlingenAvslutadesIsPresent() {
      final var certificate = TestDataCertificate.fk3226CertificateBuilder()
          .certificateModel(certificateModelFactoryFK3226.create())
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
                      .id(new ElementId("1.3"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("1.3"))
                              .text("Text")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("52"))
                      .value(
                          ElementValueCode.builder()
                              .codeId(new FieldId("ENDAST_PALLIATIV"))
                              .code("ENDAST_PALLIATIV")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("52.2"))
                      .value(
                          ElementValueDate.builder()
                              .dateId(new FieldId("52.2"))
                              .date(LocalDate.now())
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("53"))
                      .value(
                          ElementValueBoolean.builder()
                              .booleanId(new FieldId("53.1"))
                              .value(true)
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
                                          .terminology(CodeSystemIcd10Se.DIAGNOS_ICD_10_ID)
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build()
              )
          )
          .build();

      final var xml = generator.generate(certificate, true);

      assertTrue(schematronValidator.validate(certificate.id(), xml,
          CertificateModelFactoryFK3226.SCHEMATRON_PATH));
    }

    @Test
    void shallReturnFalseIfQuestionNarAktivaBehandlingenAvslutadesIsMissing() {
      final var certificate = TestDataCertificate.fk3226CertificateBuilder()
          .certificateModel(certificateModelFactoryFK3226.create())
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
                      .id(new ElementId("1.3"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("1.3"))
                              .text("Text")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("52"))
                      .value(
                          ElementValueCode.builder()
                              .codeId(new FieldId("ENDAST_PALLIATIV"))
                              .code("ENDAST_PALLIATIV")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("53"))
                      .value(
                          ElementValueBoolean.builder()
                              .booleanId(new FieldId("53.1"))
                              .value(true)
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
                                          .terminology(CodeSystemIcd10Se.DIAGNOS_ICD_10_ID)
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build()
              )
          )
          .build();

      final var xml = generator.generate(certificate, false);

      assertFalse(schematronValidator.validate(certificate.id(), xml,
          CertificateModelFactoryFK3226.SCHEMATRON_PATH));
    }
  }

  @Nested
  class QuestionPatientBehandlingOchVardsituationHasValueAkutLivshotande {

    @Test
    void shallReturnTrueIfRelatedSubQuestionsIsPresent() {
      final var certificate = TestDataCertificate.fk3226CertificateBuilder()
          .certificateModel(certificateModelFactoryFK3226.create())
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
                      .id(new ElementId("1.3"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("1.3"))
                              .text("Text")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("52"))
                      .value(
                          ElementValueCode.builder()
                              .codeId(new FieldId("AKUT_LIVSHOTANDE"))
                              .code("AKUT_LIVSHOTANDE")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("52.3"))
                      .value(
                          ElementValueDate.builder()
                              .dateId(new FieldId("52.3"))
                              .date(LocalDate.now())
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("52.4"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("52.4"))
                              .text("text")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("52.5"))
                      .value(
                          ElementValueBoolean.builder()
                              .booleanId(new FieldId("52.5"))
                              .value(false)
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("53"))
                      .value(
                          ElementValueBoolean.builder()
                              .booleanId(new FieldId("53.1"))
                              .value(true)
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
                                          .terminology(CodeSystemIcd10Se.DIAGNOS_ICD_10_ID)
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build()
              )
          )
          .build();

      final var xml = generator.generate(certificate, true);

      assertTrue(schematronValidator.validate(certificate.id(), xml,
          CertificateModelFactoryFK3226.SCHEMATRON_PATH));
    }

    @Test
    void shallReturnFalseIfQuestionNarTillstandetBlevAkutLivshotandeIsMissing() {
      final var certificate = TestDataCertificate.fk3226CertificateBuilder()
          .certificateModel(certificateModelFactoryFK3226.create())
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
                      .id(new ElementId("1.3"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("1.3"))
                              .text("Text")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("52"))
                      .value(
                          ElementValueCode.builder()
                              .codeId(new FieldId("AKUT_LIVSHOTANDE"))
                              .code("AKUT_LIVSHOTANDE")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("52.4"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("52.4"))
                              .text("text")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("52.5"))
                      .value(
                          ElementValueBoolean.builder()
                              .booleanId(new FieldId("52.5"))
                              .value(false)
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("53"))
                      .value(
                          ElementValueBoolean.builder()
                              .booleanId(new FieldId("53.1"))
                              .value(true)
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
                                          .terminology(CodeSystemIcd10Se.DIAGNOS_ICD_10_ID)
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build()
              )
          )
          .build();

      final var xml = generator.generate(certificate, false);

      assertFalse(schematronValidator.validate(certificate.id(), xml,
          CertificateModelFactoryFK3226.SCHEMATRON_PATH));
    }

    @Test
    void shallReturnFalseIfQuestionPatagligtHotMotPatientensLivAkutLivshotandeIsMissing() {
      final var certificate = TestDataCertificate.fk3226CertificateBuilder()
          .certificateModel(certificateModelFactoryFK3226.create())
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
                      .id(new ElementId("1.3"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("1.3"))
                              .text("Text")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("52"))
                      .value(
                          ElementValueCode.builder()
                              .codeId(new FieldId("AKUT_LIVSHOTANDE"))
                              .code("AKUT_LIVSHOTANDE")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("52.3"))
                      .value(
                          ElementValueDate.builder()
                              .dateId(new FieldId("52.3"))
                              .date(LocalDate.now())
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("52.5"))
                      .value(
                          ElementValueBoolean.builder()
                              .booleanId(new FieldId("52.5"))
                              .value(false)
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("53"))
                      .value(
                          ElementValueBoolean.builder()
                              .booleanId(new FieldId("53.1"))
                              .value(true)
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
                                          .terminology(CodeSystemIcd10Se.DIAGNOS_ICD_10_ID)
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build()
              )
          )
          .build();

      final var xml = generator.generate(certificate, false);

      assertFalse(schematronValidator.validate(certificate.id(), xml,
          CertificateModelFactoryFK3226.SCHEMATRON_PATH));
    }

    @Test
    void shallReturnFalseIfQuestionUppskattaHurLangeTillstandetKommerVaraLivshotandeIsMissing() {
      final var certificate = TestDataCertificate.fk3226CertificateBuilder()
          .certificateModel(certificateModelFactoryFK3226.create())
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
                      .id(new ElementId("1.3"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("1.3"))
                              .text("Text")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("52"))
                      .value(
                          ElementValueCode.builder()
                              .codeId(new FieldId("AKUT_LIVSHOTANDE"))
                              .code("AKUT_LIVSHOTANDE")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("52.3"))
                      .value(
                          ElementValueDate.builder()
                              .dateId(new FieldId("52.3"))
                              .date(LocalDate.now())
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("52.4"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("52.4"))
                              .text("text")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("53"))
                      .value(
                          ElementValueBoolean.builder()
                              .booleanId(new FieldId("53.1"))
                              .value(true)
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
                                          .terminology(CodeSystemIcd10Se.DIAGNOS_ICD_10_ID)
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build()
              )
          )
          .build();

      final var xml = generator.generate(certificate, false);

      assertFalse(schematronValidator.validate(certificate.id(), xml,
          CertificateModelFactoryFK3226.SCHEMATRON_PATH));
    }

    @Nested
    class QuestionUppskattaHurLangeTillstandetKommerVaraLivshotandeIsTrue {

      @Test
      void shallReturnTrueIfQuestionTillstandetUppskattasLivshotandeTillOchMedIsPresent() {
        final var certificate = TestDataCertificate.fk3226CertificateBuilder()
            .certificateModel(certificateModelFactoryFK3226.create())
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
                        .id(new ElementId("1.3"))
                        .value(
                            ElementValueText.builder()
                                .textId(new FieldId("1.3"))
                                .text("Text")
                                .build()
                        )
                        .build(),
                    ElementData.builder()
                        .id(new ElementId("52"))
                        .value(
                            ElementValueCode.builder()
                                .codeId(new FieldId("AKUT_LIVSHOTANDE"))
                                .code("AKUT_LIVSHOTANDE")
                                .build()
                        )
                        .build(),
                    ElementData.builder()
                        .id(new ElementId("52.3"))
                        .value(
                            ElementValueDate.builder()
                                .dateId(new FieldId("52.3"))
                                .date(LocalDate.now())
                                .build()
                        )
                        .build(),
                    ElementData.builder()
                        .id(new ElementId("52.4"))
                        .value(
                            ElementValueText.builder()
                                .textId(new FieldId("52.4"))
                                .text("text")
                                .build()
                        )
                        .build(),
                    ElementData.builder()
                        .id(new ElementId("52.5"))
                        .value(
                            ElementValueBoolean.builder()
                                .booleanId(new FieldId("52.5"))
                                .value(true)
                                .build()
                        )
                        .build(),
                    ElementData.builder()
                        .id(new ElementId("52.6"))
                        .value(
                            ElementValueDate.builder()
                                .dateId(new FieldId("52.6"))
                                .date(LocalDate.now())
                                .build()
                        )
                        .build(),
                    ElementData.builder()
                        .id(new ElementId("53"))
                        .value(
                            ElementValueBoolean.builder()
                                .booleanId(new FieldId("53.1"))
                                .value(true)
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
                                            .terminology(CodeSystemIcd10Se.DIAGNOS_ICD_10_ID)
                                            .build()
                                    )
                                )
                                .build()
                        )
                        .build()
                )
            )
            .build();

        final var xml = generator.generate(certificate, true);

        assertTrue(schematronValidator.validate(certificate.id(), xml,
            CertificateModelFactoryFK3226.SCHEMATRON_PATH));
      }

      @Test
      void shallReturnFalseIfQuestionTillstandetUppskattasLivshotandeTillOchMedIsMissing() {
        final var certificate = TestDataCertificate.fk3226CertificateBuilder()
            .certificateModel(certificateModelFactoryFK3226.create())
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
                        .id(new ElementId("1.3"))
                        .value(
                            ElementValueText.builder()
                                .textId(new FieldId("1.3"))
                                .text("Text")
                                .build()
                        )
                        .build(),
                    ElementData.builder()
                        .id(new ElementId("52"))
                        .value(
                            ElementValueCode.builder()
                                .codeId(new FieldId("AKUT_LIVSHOTANDE"))
                                .code("AKUT_LIVSHOTANDE")
                                .build()
                        )
                        .build(),
                    ElementData.builder()
                        .id(new ElementId("52.3"))
                        .value(
                            ElementValueDate.builder()
                                .dateId(new FieldId("52.3"))
                                .date(LocalDate.now())
                                .build()
                        )
                        .build(),
                    ElementData.builder()
                        .id(new ElementId("52.4"))
                        .value(
                            ElementValueText.builder()
                                .textId(new FieldId("52.4"))
                                .text("text")
                                .build()
                        )
                        .build(),
                    ElementData.builder()
                        .id(new ElementId("52.5"))
                        .value(
                            ElementValueBoolean.builder()
                                .booleanId(new FieldId("52.5"))
                                .value(true)
                                .build()
                        )
                        .build(),
                    ElementData.builder()
                        .id(new ElementId("53"))
                        .value(
                            ElementValueBoolean.builder()
                                .booleanId(new FieldId("53.1"))
                                .value(true)
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
                                            .terminology(CodeSystemIcd10Se.DIAGNOS_ICD_10_ID)
                                            .build()
                                    )
                                )
                                .build()
                        )
                        .build()
                )
            )
            .build();

        final var xml = generator.generate(certificate, false);

        assertFalse(schematronValidator.validate(certificate.id(), xml,
            CertificateModelFactoryFK3226.SCHEMATRON_PATH));
      }
    }
  }

  @Nested
  class QuestionPatientBehandlingOchVardsituationHasValueAnnat {

    @Test
    void shallReturnTrueIfQuestionPatagligtHotMotPatientensLivAnnatIsPresent() {
      final var certificate = TestDataCertificate.fk3226CertificateBuilder()
          .certificateModel(certificateModelFactoryFK3226.create())
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
                      .id(new ElementId("1.3"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("1.3"))
                              .text("Text")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("52"))
                      .value(
                          ElementValueCode.builder()
                              .codeId(new FieldId("ANNAT"))
                              .code("ANNAT")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("52.7"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("52.7"))
                              .text("text")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("53"))
                      .value(
                          ElementValueBoolean.builder()
                              .booleanId(new FieldId("53.1"))
                              .value(true)
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
                                          .terminology(CodeSystemIcd10Se.DIAGNOS_ICD_10_ID)
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build()
              )
          )
          .build();

      final var xml = generator.generate(certificate, true);

      assertTrue(schematronValidator.validate(certificate.id(), xml,
          CertificateModelFactoryFK3226.SCHEMATRON_PATH));
    }

    @Test
    void shallReturnFalseIfQuestionPatagligtHotMotPatientensLivAnnatIsMissing() {
      final var certificate = TestDataCertificate.fk3226CertificateBuilder()
          .certificateModel(certificateModelFactoryFK3226.create())
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
                      .id(new ElementId("1.3"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("1.3"))
                              .text("Text")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("52"))
                      .value(
                          ElementValueCode.builder()
                              .codeId(new FieldId("ANNAT"))
                              .code("ANNAT")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("53"))
                      .value(
                          ElementValueBoolean.builder()
                              .booleanId(new FieldId("53.1"))
                              .value(true)
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
                                          .terminology(CodeSystemIcd10Se.DIAGNOS_ICD_10_ID)
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build()
              )
          )
          .build();

      final var xml = generator.generate(certificate, false);

      assertFalse(schematronValidator.validate(certificate.id(), xml,
          CertificateModelFactoryFK3226.SCHEMATRON_PATH));
    }
  }

  @Nested
  class QuestionDiagnos {

    @Test
    void shallReturnFalseIfQuestionDiagnosIsMissing() {
      final var certificate = TestDataCertificate.fk3226CertificateBuilder()
          .certificateModel(certificateModelFactoryFK3226.create())
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
                      .id(new ElementId("1.3"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("1.3"))
                              .text("Text")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("52"))
                      .value(
                          ElementValueCode.builder()
                              .codeId(new FieldId("ENDAST_PALLIATIV"))
                              .code("ENDAST_PALLIATIV")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("52.2"))
                      .value(
                          ElementValueDate.builder()
                              .dateId(new FieldId("52.2"))
                              .date(LocalDate.now())
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("53"))
                      .value(
                          ElementValueBoolean.builder()
                              .booleanId(new FieldId("53.1"))
                              .value(true)
                              .build()
                      )
                      .build()
              )
          )
          .build();

      final var xml = generator.generate(certificate, false);

      assertFalse(schematronValidator.validate(certificate.id(), xml,
          CertificateModelFactoryFK3226.SCHEMATRON_PATH));
    }

    @Test
    void shallReturnFalseIfDiagnosisMissingDescription() {
      final var certificate = TestDataCertificate.fk3226CertificateBuilder()
          .certificateModel(certificateModelFactoryFK3226.create())
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
                      .id(new ElementId("1.3"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("1.3"))
                              .text("Text")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("52"))
                      .value(
                          ElementValueCode.builder()
                              .codeId(new FieldId("ENDAST_PALLIATIV"))
                              .code("ENDAST_PALLIATIV")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("52.2"))
                      .value(
                          ElementValueDate.builder()
                              .dateId(new FieldId("52.2"))
                              .date(LocalDate.now())
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("53"))
                      .value(
                          ElementValueBoolean.builder()
                              .booleanId(new FieldId("53.1"))
                              .value(true)
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
                                          .description("")
                                          .terminology(CodeSystemIcd10Se.DIAGNOS_ICD_10_ID)
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build()
              )
          )
          .build();

      final var xml = generator.generate(certificate, false);

      assertFalse(schematronValidator.validate(certificate.id(), xml,
          CertificateModelFactoryFK3226.SCHEMATRON_PATH));
    }

    @Test
    void shallReturnFalseIfDiagnosisMissingCode() {
      final var certificate = TestDataCertificate.fk3226CertificateBuilder()
          .certificateModel(certificateModelFactoryFK3226.create())
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
                      .id(new ElementId("1.3"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("1.3"))
                              .text("Text")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("52"))
                      .value(
                          ElementValueCode.builder()
                              .codeId(new FieldId("ENDAST_PALLIATIV"))
                              .code("ENDAST_PALLIATIV")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("52.2"))
                      .value(
                          ElementValueDate.builder()
                              .dateId(new FieldId("52.2"))
                              .date(LocalDate.now())
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("53"))
                      .value(
                          ElementValueBoolean.builder()
                              .booleanId(new FieldId("53.1"))
                              .value(true)
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
                                          .code("")
                                          .description("Paratyfoidfeber C")
                                          .terminology(CodeSystemIcd10Se.DIAGNOS_ICD_10_ID)
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build()
              )
          )
          .build();

      final var xml = generator.generate(certificate, false);

      assertFalse(schematronValidator.validate(certificate.id(), xml,
          CertificateModelFactoryFK3226.SCHEMATRON_PATH));
    }

    @Test
    void shallReturnFalseMoreThanFiveDiagnosesArePresent() {
      final var certificate = TestDataCertificate.fk3226CertificateBuilder()
          .certificateModel(certificateModelFactoryFK3226.create())
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
                      .id(new ElementId("1.3"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("1.3"))
                              .text("Text")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("52"))
                      .value(
                          ElementValueCode.builder()
                              .codeId(new FieldId("ENDAST_PALLIATIV"))
                              .code("ENDAST_PALLIATIV")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("52.2"))
                      .value(
                          ElementValueDate.builder()
                              .dateId(new FieldId("52.2"))
                              .date(LocalDate.now())
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("53"))
                      .value(
                          ElementValueBoolean.builder()
                              .booleanId(new FieldId("53.1"))
                              .value(true)
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
                                          .terminology(CodeSystemIcd10Se.DIAGNOS_ICD_10_ID)
                                          .build(),
                                      ElementValueDiagnosis.builder()
                                          .code("A013")
                                          .description("Paratyfoidfeber C")
                                          .terminology(CodeSystemIcd10Se.DIAGNOS_ICD_10_ID)
                                          .build(),
                                      ElementValueDiagnosis.builder()
                                          .code("A013")
                                          .description("Paratyfoidfeber C")
                                          .terminology(CodeSystemIcd10Se.DIAGNOS_ICD_10_ID)
                                          .build(),
                                      ElementValueDiagnosis.builder()
                                          .code("A013")
                                          .description("Paratyfoidfeber C")
                                          .terminology(CodeSystemIcd10Se.DIAGNOS_ICD_10_ID)
                                          .build(),
                                      ElementValueDiagnosis.builder()
                                          .code("A013")
                                          .description("Paratyfoidfeber C")
                                          .terminology(CodeSystemIcd10Se.DIAGNOS_ICD_10_ID)
                                          .build(),
                                      ElementValueDiagnosis.builder()
                                          .code("A013")
                                          .description("Paratyfoidfeber C")
                                          .terminology(CodeSystemIcd10Se.DIAGNOS_ICD_10_ID)
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build()
              )
          )
          .build();

      final var xml = generator.generate(certificate, false);

      assertFalse(schematronValidator.validate(certificate.id(), xml,
          CertificateModelFactoryFK3226.SCHEMATRON_PATH));
    }

    @Test
    void shallReturnTrueIfFiveDiagnosesArePresent() {
      final var certificate = TestDataCertificate.fk3226CertificateBuilder()
          .certificateModel(certificateModelFactoryFK3226.create())
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
                      .id(new ElementId("1.3"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("1.3"))
                              .text("Text")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("52"))
                      .value(
                          ElementValueCode.builder()
                              .codeId(new FieldId("ENDAST_PALLIATIV"))
                              .code("ENDAST_PALLIATIV")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("52.2"))
                      .value(
                          ElementValueDate.builder()
                              .dateId(new FieldId("52.2"))
                              .date(LocalDate.now())
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("53"))
                      .value(
                          ElementValueBoolean.builder()
                              .booleanId(new FieldId("53.1"))
                              .value(true)
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
                                          .terminology(CodeSystemIcd10Se.DIAGNOS_ICD_10_ID)
                                          .build(),
                                      ElementValueDiagnosis.builder()
                                          .code("A013")
                                          .description("Paratyfoidfeber C")
                                          .terminology(CodeSystemIcd10Se.DIAGNOS_ICD_10_ID)
                                          .build(),
                                      ElementValueDiagnosis.builder()
                                          .code("A013")
                                          .description("Paratyfoidfeber C")
                                          .terminology(CodeSystemIcd10Se.DIAGNOS_ICD_10_ID)
                                          .build(),
                                      ElementValueDiagnosis.builder()
                                          .code("A013")
                                          .description("Paratyfoidfeber C")
                                          .terminology(CodeSystemIcd10Se.DIAGNOS_ICD_10_ID)
                                          .build(),
                                      ElementValueDiagnosis.builder()
                                          .code("A013")
                                          .description("Paratyfoidfeber C")
                                          .terminology(CodeSystemIcd10Se.DIAGNOS_ICD_10_ID)
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build()
              )
          )
          .build();

      final var xml = generator.generate(certificate, false);

      assertTrue(schematronValidator.validate(certificate.id(), xml,
          CertificateModelFactoryFK3226.SCHEMATRON_PATH));
    }

    @Test
    void shallReturnTrueIfFourDiagnosesArePresent() {
      final var certificate = TestDataCertificate.fk3226CertificateBuilder()
          .certificateModel(certificateModelFactoryFK3226.create())
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
                      .id(new ElementId("1.3"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("1.3"))
                              .text("Text")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("52"))
                      .value(
                          ElementValueCode.builder()
                              .codeId(new FieldId("ENDAST_PALLIATIV"))
                              .code("ENDAST_PALLIATIV")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("52.2"))
                      .value(
                          ElementValueDate.builder()
                              .dateId(new FieldId("52.2"))
                              .date(LocalDate.now())
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("53"))
                      .value(
                          ElementValueBoolean.builder()
                              .booleanId(new FieldId("53.1"))
                              .value(true)
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
                                          .terminology(CodeSystemIcd10Se.DIAGNOS_ICD_10_ID)
                                          .build(),
                                      ElementValueDiagnosis.builder()
                                          .code("A013")
                                          .description("Paratyfoidfeber C")
                                          .terminology(CodeSystemIcd10Se.DIAGNOS_ICD_10_ID)
                                          .build(),
                                      ElementValueDiagnosis.builder()
                                          .code("A013")
                                          .description("Paratyfoidfeber C")
                                          .terminology(CodeSystemIcd10Se.DIAGNOS_ICD_10_ID)
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build()
              )
          )
          .build();

      final var xml = generator.generate(certificate, false);

      assertTrue(schematronValidator.validate(certificate.id(), xml,
          CertificateModelFactoryFK3226.SCHEMATRON_PATH));
    }

    @Test
    void shallReturnTrueIfThreeDiagnosesArePresent() {
      final var certificate = TestDataCertificate.fk3226CertificateBuilder()
          .certificateModel(certificateModelFactoryFK3226.create())
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
                      .id(new ElementId("1.3"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("1.3"))
                              .text("Text")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("52"))
                      .value(
                          ElementValueCode.builder()
                              .codeId(new FieldId("ENDAST_PALLIATIV"))
                              .code("ENDAST_PALLIATIV")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("52.2"))
                      .value(
                          ElementValueDate.builder()
                              .dateId(new FieldId("52.2"))
                              .date(LocalDate.now())
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("53"))
                      .value(
                          ElementValueBoolean.builder()
                              .booleanId(new FieldId("53.1"))
                              .value(true)
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
                                          .terminology(CodeSystemIcd10Se.DIAGNOS_ICD_10_ID)
                                          .build(),
                                      ElementValueDiagnosis.builder()
                                          .code("A013")
                                          .description("Paratyfoidfeber C")
                                          .terminology(CodeSystemIcd10Se.DIAGNOS_ICD_10_ID)
                                          .build(),
                                      ElementValueDiagnosis.builder()
                                          .code("A013")
                                          .description("Paratyfoidfeber C")
                                          .terminology(CodeSystemIcd10Se.DIAGNOS_ICD_10_ID)
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build()
              )
          )
          .build();

      final var xml = generator.generate(certificate, false);

      assertTrue(schematronValidator.validate(certificate.id(), xml,
          CertificateModelFactoryFK3226.SCHEMATRON_PATH));
    }

    @Test
    void shallReturnTrueIfTwoDiagnosesArePresent() {
      final var certificate = TestDataCertificate.fk3226CertificateBuilder()
          .certificateModel(certificateModelFactoryFK3226.create())
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
                      .id(new ElementId("1.3"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("1.3"))
                              .text("Text")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("52"))
                      .value(
                          ElementValueCode.builder()
                              .codeId(new FieldId("ENDAST_PALLIATIV"))
                              .code("ENDAST_PALLIATIV")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("52.2"))
                      .value(
                          ElementValueDate.builder()
                              .dateId(new FieldId("52.2"))
                              .date(LocalDate.now())
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("53"))
                      .value(
                          ElementValueBoolean.builder()
                              .booleanId(new FieldId("53.1"))
                              .value(true)
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
                                          .terminology(CodeSystemIcd10Se.DIAGNOS_ICD_10_ID)
                                          .build(),
                                      ElementValueDiagnosis.builder()
                                          .code("A013")
                                          .description("Paratyfoidfeber C")
                                          .terminology(CodeSystemIcd10Se.DIAGNOS_ICD_10_ID)
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build()
              )
          )
          .build();

      final var xml = generator.generate(certificate, false);

      assertTrue(schematronValidator.validate(certificate.id(), xml,
          CertificateModelFactoryFK3226.SCHEMATRON_PATH));
    }

    @Test
    void shallReturnTrueIfOneDiagnosesArePresent() {
      final var certificate = TestDataCertificate.fk3226CertificateBuilder()
          .certificateModel(certificateModelFactoryFK3226.create())
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
                      .id(new ElementId("1.3"))
                      .value(
                          ElementValueText.builder()
                              .textId(new FieldId("1.3"))
                              .text("Text")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("52"))
                      .value(
                          ElementValueCode.builder()
                              .codeId(new FieldId("ENDAST_PALLIATIV"))
                              .code("ENDAST_PALLIATIV")
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("52.2"))
                      .value(
                          ElementValueDate.builder()
                              .dateId(new FieldId("52.2"))
                              .date(LocalDate.now())
                              .build()
                      )
                      .build(),
                  ElementData.builder()
                      .id(new ElementId("53"))
                      .value(
                          ElementValueBoolean.builder()
                              .booleanId(new FieldId("53.1"))
                              .value(true)
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
                                          .terminology(CodeSystemIcd10Se.DIAGNOS_ICD_10_ID)
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build()
              )
          )
          .build();

      final var xml = generator.generate(certificate, false);

      assertTrue(schematronValidator.validate(certificate.id(), xml,
          CertificateModelFactoryFK3226.SCHEMATRON_PATH));
    }
  }
}