package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3221.schematron;

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
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionFactory;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateList;
import se.inera.intyg.certificateservice.domain.certificate.model.Revision;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.diagnosiscode.repository.DiagnosisCodeRepository;
import se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3221.CertificateModelFactoryFK3221;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.CertificateModelFactoryFK7426;
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
class SchematronValidationFK3221Test {

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
          )
      ),
      new XmlValidationService(
          new SchematronValidator(),
          new SchemaValidatorV4()
      )
  );

  private CertificateModelFactoryFK3221 certificateModelFactoryFK3221;

  @BeforeEach
  void setUp() {
    schematronValidator = new SchematronValidator();
    certificateModelFactoryFK3221 = new CertificateModelFactoryFK3221(certificateActionFactory,
        diagnosisCodeRepository);
  }

  @Test
  void shallReturnTrueForValidCertificate() {
    final var certificate = TestDataCertificate.fk3221CertificateBuilder()
        .certificateModel(certificateModelFactoryFK3221.create())
        .build();

    final var xml = generator.generate(certificate, true);
    assertTrue(schematronValidator.validate(certificate.id(), xml,
        CertificateModelFactoryFK3221.SCHEMATRON_PATH));
  }

  @Nested
  class TestValidateGrundForMU {

    @Test
    void shallReturnFalseIfMoreThanFiveMU() {
      final var certificate = TestDataCertificate.fk3221CertificateBuilder()
          .certificateModel(certificateModelFactoryFK3221.create())
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
          CertificateModelFactoryFK7426.SCHEMATRON_PATH));
    }

    @Test
    void shallReturnFalseIfMissingValues() {
      final var certificate = TestDataCertificate.fk3221CertificateBuilder()
          .certificateModel(certificateModelFactoryFK3221.create())
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
          CertificateModelFactoryFK7426.SCHEMATRON_PATH));
    }
  }
}
