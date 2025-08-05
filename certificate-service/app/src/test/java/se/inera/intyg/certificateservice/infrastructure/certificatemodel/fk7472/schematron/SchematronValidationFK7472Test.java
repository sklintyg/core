package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7472.schematron;

import static org.junit.jupiter.api.Assertions.assertThrows;
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
import se.inera.intyg.certificateservice.domain.certificate.model.DateRange;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateRangeList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7472.CertificateModelFactoryFK7472;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorCertificateV4;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorDate;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorDateRangeList;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorText;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorValue;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.validation.SchemaValidatorV4;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.validation.SchematronValidator;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.validation.XmlValidationService;

@ExtendWith(MockitoExtension.class)
class SchematronValidationFK7472Test {

  @Mock
  private CertificateActionFactory certificateActionFactory;
  private SchematronValidator schematronValidator;
  private final XmlGeneratorCertificateV4 generator = new XmlGeneratorCertificateV4(
      new XmlGeneratorValue(
          List.of(new XmlGeneratorDate(), new XmlGeneratorDateRangeList(), new XmlGeneratorText()),
          Collections.emptyList()
      ),
      new XmlValidationService(
          new SchematronValidator(),
          new SchemaValidatorV4()
      )
  );
  private CertificateModelFactoryFK7472 certificateModelFactoryFK7472;

  @BeforeEach
  void setUp() {
    schematronValidator = new SchematronValidator();
    certificateModelFactoryFK7472 = new CertificateModelFactoryFK7472(certificateActionFactory);
  }

  @Test
  void shallReturnTrueIfAllFieldsHaveValues() {
    final var element = List.of(
        ElementData.builder()
            .id(new ElementId("55"))
            .value(
                ElementValueText.builder()
                    .textId(new FieldId("55.1"))
                    .text("text")
                    .build()
            )
            .build(),
        ElementData.builder()
            .id(new ElementId("56"))
            .value(
                ElementValueDateRangeList.builder()
                    .dateRangeList(
                        List.of(
                            DateRange.builder()
                                .dateRangeId(new FieldId("EN_ATTONDEL"))
                                .from(LocalDate.now())
                                .to(LocalDate.now().plusDays(1))
                                .build()
                        )
                    )
                    .dateRangeListId(new FieldId("56.2"))
                    .build())
            .build()
    );

    final var certificate = TestDataCertificate.fk7472CertificateBuilder()
        .certificateModel(certificateModelFactoryFK7472.create())
        .elementData(element)
        .build();

    final var xml = generator.generate(certificate, true);
    assertTrue(schematronValidator.validate(certificate.id(), xml,
        CertificateModelFactoryFK7472.SCHEMATRON_PATH)
    );
  }

  @Nested
  class QuestionDiagnosEllerSymtom {

    private static final ElementData QUESTION_PERIOD = ElementData.builder()
        .id(new ElementId("56"))
        .value(
            ElementValueDateRangeList.builder()
                .dateRangeList(
                    List.of(
                        DateRange.builder()
                            .dateRangeId(new FieldId("EN_ATTONDEL"))
                            .from(LocalDate.now())
                            .to(LocalDate.now().plusDays(1))
                            .build()
                    )
                )
                .dateRangeListId(new FieldId("55.1"))
                .build())
        .build();

    @Test
    void shallReturnQuestionMissing() {
      final var certificate = TestDataCertificate.fk7472CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7472.create())
          .elementData(List.of(QUESTION_PERIOD))
          .build();

      assertThrows(IllegalStateException.class, () -> generator.generate(certificate, true));
    }

    @Test
    void shallReturnFalseIfValueIsNull() {
      final var element = ElementData.builder()
          .id(new ElementId("55"))
          .value(
              ElementValueText.builder()
                  .textId(new FieldId("55.1"))
                  .build()
          ).build();

      final var certificate = TestDataCertificate.fk7472CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7472.create())
          .elementData(List.of(element, QUESTION_PERIOD))
          .build();

      assertThrows(IllegalStateException.class, () -> generator.generate(certificate, true));
    }

    @Test
    void shallReturnFalseIfValueIsBlank() {
      final var element = ElementData.builder()
          .id(new ElementId("55"))
          .value(
              ElementValueText.builder()
                  .textId(new FieldId("55.1"))
                  .text("")
                  .build()
          ).build();

      final var certificate = TestDataCertificate.fk7472CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7472.create())
          .elementData(List.of(element, QUESTION_PERIOD))
          .build();

      assertThrows(IllegalStateException.class, () -> generator.generate(certificate, true));
    }

    @Test
    void shallReturnFalseIfValueHasLengthGreaterThan318() {
      final var element = ElementData.builder()
          .id(new ElementId("55"))
          .value(
              ElementValueText.builder()
                  .textId(new FieldId("55.1"))
                  .text(
                      "awddawadwawdawdawdawdawdadwadwawdadawda"
                          + "wawddawadwawdawdawdawdawdadwadwawd"
                          + "adawdawawddawadwawdawdawdawdawdadw"
                          + "adwawdadawdawawddawadwawdawdawdawd"
                          + "awdadwadwawdadawdawawddawadwawdawd"
                          + "awdawdawdadwadwawdadawdawawddawad"
                          + "wawdawdawdawdawdadwadwawdadawdawawd"
                          + "dawadwawdawdawdawdawdadwadwawdadawd"
                          + "awawddawadwawdawdawdawdawdadwadwawdadawdddddd")
                  .build()
          ).build();

      final var certificate = TestDataCertificate.fk7472CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7472.create())
          .elementData(List.of(element, QUESTION_PERIOD))
          .build();

      assertThrows(IllegalStateException.class, () -> generator.generate(certificate, true));
    }

    @Test
    void shallReturnFalseIfQuestionMissing() {
      final var element = ElementData.builder()
          .id(new ElementId("55"))
          .value(ElementValueText.builder().build())
          .build();

      final var certificate = TestDataCertificate.fk7472CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7472.create())
          .elementData(List.of(element, QUESTION_PERIOD))
          .build();

      assertThrows(IllegalStateException.class, () -> generator.generate(certificate, true));
    }
  }

  @Nested
  class QuestionPerioda {

    private static final ElementData QUESTION_SYMTOM = ElementData.builder()
        .id(new ElementId("55"))
        .value(
            ElementValueText.builder()
                .textId(new FieldId("55.1"))
                .text("text")
                .build()
        )
        .build();

    @Test
    void shallReturnFalseIfQuestionIdIsMissing() {
      final var element = List.of(
          QUESTION_SYMTOM
      );

      final var certificate = TestDataCertificate.fk7472CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7472.create())
          .elementData(element)
          .build();

      assertThrows(IllegalStateException.class, () -> generator.generate(certificate, true));
    }

    @Test
    void shallReturnFalseIfDateRangeIsMissing() {
      final var element = List.of(
          QUESTION_SYMTOM,
          ElementData.builder()
              .id(new ElementId("56"))
              .value(
                  ElementValueDateRangeList.builder()
                      .dateRangeList(
                          Collections.emptyList()
                      )
                      .dateRangeListId(new FieldId("56.1"))
                      .build())
              .build()
      );

      final var certificate = TestDataCertificate.fk7472CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7472.create())
          .elementData(element)
          .build();

      assertThrows(IllegalStateException.class, () -> generator.generate(certificate, true));
    }

    @Test
    void shallReturnFalseIfDateRangeIsOverlapping() {
      final var element = List.of(
          QUESTION_SYMTOM,
          ElementData.builder()
              .id(new ElementId("56"))
              .value(
                  ElementValueDateRangeList.builder()
                      .dateRangeList(
                          List.of(
                              DateRange.builder()
                                  .dateRangeId(new FieldId("EN_ATTONDEL"))
                                  .from(LocalDate.now())
                                  .to(LocalDate.now().plusDays(1))
                                  .build(),
                              DateRange.builder()
                                  .dateRangeId(new FieldId("EN_FJARDEDEL"))
                                  .from(LocalDate.now().minusDays(1))
                                  .to(LocalDate.now().plusDays(2))
                                  .build()
                          )
                      )
                      .dateRangeListId(new FieldId("56.1"))
                      .build())
              .build()
      );

      final var certificate = TestDataCertificate.fk7472CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7472.create())
          .elementData(element)
          .build();

      assertThrows(IllegalStateException.class, () -> generator.generate(certificate, true));
    }

    @Test
    void shallReturnFalseIfMultipleDateRangeWithSameCodeAppear() {
      final var element = List.of(
          QUESTION_SYMTOM,
          ElementData.builder()
              .id(new ElementId("56"))
              .value(
                  ElementValueDateRangeList.builder()
                      .dateRangeList(
                          List.of(
                              DateRange.builder()
                                  .dateRangeId(new FieldId("EN_ATTONDEL"))
                                  .from(LocalDate.now())
                                  .to(LocalDate.now().plusDays(1))
                                  .build(),
                              DateRange.builder()
                                  .dateRangeId(new FieldId("EN_ATTONDEL"))
                                  .from(LocalDate.now().minusDays(4))
                                  .to(LocalDate.now().plusDays(9))
                                  .build()
                          )
                      )
                      .dateRangeListId(new FieldId("56.1"))
                      .build())
              .build()
      );

      final var certificate = TestDataCertificate.fk7472CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7472.create())
          .elementData(element)
          .build();

      assertThrows(IllegalStateException.class, () -> generator.generate(certificate, true));
    }

    @Test
    void shallReturnFalseIfDateRangeFromIsNull() {
      final var element = List.of(
          QUESTION_SYMTOM,
          ElementData.builder()
              .id(new ElementId("56"))
              .value(
                  ElementValueDateRangeList.builder()
                      .dateRangeList(
                          List.of(
                              DateRange.builder()
                                  .dateRangeId(new FieldId("EN_ATTONDEL"))
                                  .to(LocalDate.now().plusDays(1))
                                  .build()
                          )
                      )
                      .dateRangeListId(new FieldId("56.1"))
                      .build())
              .build()
      );

      final var certificate = TestDataCertificate.fk7472CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7472.create())
          .elementData(element)
          .build();

      assertThrows(IllegalStateException.class, () -> generator.generate(certificate, true));
    }


    @Test
    void shallReturnFalseIfDateRangeToIsNull() {
      final var element = List.of(
          QUESTION_SYMTOM,
          ElementData.builder()
              .id(new ElementId("56"))
              .value(
                  ElementValueDateRangeList.builder()
                      .dateRangeList(
                          List.of(
                              DateRange.builder()
                                  .dateRangeId(new FieldId("EN_ATTONDEL"))
                                  .from(LocalDate.now())
                                  .build()
                          )
                      )
                      .dateRangeListId(new FieldId("56.1"))
                      .build())
              .build()
      );

      final var certificate = TestDataCertificate.fk7472CertificateBuilder()
          .certificateModel(certificateModelFactoryFK7472.create())
          .elementData(element)
          .build();

      assertThrows(IllegalStateException.class, () -> generator.generate(certificate, true));
    }
  }
}