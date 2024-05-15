package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4;


import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.DateRange;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateRangeList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7210.CertificateModelFactoryFK7210;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7443.CertificateModelFactoryFK7443;

class SchematronValidatorTest {

  private SchematronValidator schematronValidator;
  private final XmlGeneratorCertificateV4 generator = new XmlGeneratorCertificateV4(
      new XmlGeneratorValue(),
      new XmlGeneratorIntygsgivare(),
      new XmlValidationService(
          new SchematronValidator(),
          new SchemaValidatorV4()
      )
  );

  @BeforeEach
  void setUp() {
    schematronValidator = new SchematronValidator();
  }

  @Nested
  class FK7210Validation {

    @Test
    void shallReturnTrueForValidCertificate() {
      final var element = ElementData.builder()
          .id(new ElementId("1"))
          .value(
              ElementValueDate.builder()
                  .dateId(new FieldId("1.1"))
                  .date(LocalDate.now())
                  .build()
          ).build();

      final var certificate = TestDataCertificate.fk7210CertificateBuilder()
          .elementData(List.of(element))
          .build();

      final var xml = generator.generate(certificate, true);

      assertTrue(schematronValidator.validate(certificate.id(), xml,
          CertificateModelFactoryFK7210.SCHEMATRON_PATH));
    }

    @Nested
    class QuestionNedkomstdatum {

      @Test
      void shallReturnFalseIfValueIsNull() {
        final var element = ElementData.builder()
            .id(new ElementId("1"))
            .value(
                ElementValueDate.builder()
                    .dateId(new FieldId("1.1"))
                    .build()
            ).build();

        final var certificate = TestDataCertificate.fk7210CertificateBuilder()
            .elementData(List.of(element))
            .build();

        assertThrows(IllegalStateException.class, () -> generator.generate(certificate, true));
      }

      @Test
      void shallReturnFalseIfValueIsBeforeToday() {
        final var element = ElementData.builder()
            .id(new ElementId("1"))
            .value(
                ElementValueDate.builder()
                    .dateId(new FieldId("1.1"))
                    .date(LocalDate.now().minusDays(1))
                    .build()
            ).build();

        final var certificate = TestDataCertificate.fk7210CertificateBuilder()
            .elementData(List.of(element))
            .build();

        assertThrows(IllegalStateException.class, () -> generator.generate(certificate, true));
      }

      @Test
      void shallReturnFalseIfValueIsMoreThanOneYearInTheFuture() {
        final var element = ElementData.builder()
            .id(new ElementId("1"))
            .value(
                ElementValueDate.builder()
                    .dateId(new FieldId("1.1"))
                    .date(LocalDate.now().plusYears(1).plusDays(2))
                    .build()
            ).build();

        final var certificate = TestDataCertificate.fk7210CertificateBuilder()
            .elementData(List.of(element))
            .build();

        assertThrows(IllegalStateException.class, () -> generator.generate(certificate, true));
      }

      @Test
      void shallReturnFalseIfQuestionMissing() {
        final var element = ElementData.builder()
            .id(new ElementId("1"))
            .value(
                ElementValueDate.builder().build()
            ).build();

        final var certificate = TestDataCertificate.fk7210CertificateBuilder()
            .elementData(List.of(element))
            .build();

        assertThrows(IllegalStateException.class, () -> generator.generate(certificate, true));
      }
    }
  }

  @Nested
  class FK7443Validation {

    @Test
    void shallReturnTrueIfAllFieldsHaveValues() {
      final var element = List.of(
          ElementData.builder()
              .id(new ElementId("2"))
              .value(
                  ElementValueText.builder()
                      .textId(new FieldId("2.1"))
                      .text("text")
                      .build()
              )
              .build(),
          ElementData.builder()
              .id(new ElementId("3"))
              .value(
                  ElementValueDateRangeList.builder()
                      .dateRangeList(
                          List.of(
                              DateRange.builder()
                                  .dateRangeId(new FieldId("EN_ATTANDEL"))
                                  .from(LocalDate.now())
                                  .to(LocalDate.now().plusDays(1))
                                  .build()
                          )
                      )
                      .dateRangeListId(new FieldId("3.2"))
                      .build())
              .build()
      );

      final var certificate = TestDataCertificate.fk7443CertificateBuilder()
          .elementData(element)
          .build();

      final var xml = generator.generate(certificate, true);
      assertTrue(schematronValidator.validate(certificate.id(), xml,
          CertificateModelFactoryFK7443.SCHEMATRON_PATH)
      );
    }

    @Nested
    class QuestionDiagnosEllerSymtom {

      private static final ElementData QUESTION_PERIOD = ElementData.builder()
          .id(new ElementId("2"))
          .value(
              ElementValueDateRangeList.builder()
                  .dateRangeList(
                      List.of(
                          DateRange.builder()
                              .dateRangeId(new FieldId("EN_ATTANDEL"))
                              .from(LocalDate.now())
                              .to(LocalDate.now().plusDays(1))
                              .build()
                      )
                  )
                  .dateRangeListId(new FieldId("2.1"))
                  .build())
          .build();

      @Test
      void shallReturnQuestionMissing() {
        final var certificate = TestDataCertificate.fk7443CertificateBuilder()
            .elementData(List.of(QUESTION_PERIOD))
            .build();

        assertThrows(IllegalStateException.class, () -> generator.generate(certificate, true));
      }

      @Test
      void shallReturnFalseIfValueIsNull() {
        final var element = ElementData.builder()
            .id(new ElementId("1"))
            .value(
                ElementValueText.builder()
                    .textId(new FieldId("1.1"))
                    .build()
            ).build();

        final var certificate = TestDataCertificate.fk7443CertificateBuilder()
            .elementData(List.of(element, QUESTION_PERIOD))
            .build();

        assertThrows(IllegalStateException.class, () -> generator.generate(certificate, true));
      }

      @Test
      void shallReturnFalseIfValueIsBlank() {
        final var element = ElementData.builder()
            .id(new ElementId("1"))
            .value(
                ElementValueText.builder()
                    .textId(new FieldId("1.1"))
                    .text("")
                    .build()
            ).build();

        final var certificate = TestDataCertificate.fk7443CertificateBuilder()
            .elementData(List.of(element, QUESTION_PERIOD))
            .build();

        assertThrows(IllegalStateException.class, () -> generator.generate(certificate, true));
      }

      @Test
      void shallReturnFalseIfValueHasLengthGreaterThan318() {
        final var element = ElementData.builder()
            .id(new ElementId("1"))
            .value(
                ElementValueText.builder()
                    .textId(new FieldId("1.1"))
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

        final var certificate = TestDataCertificate.fk7443CertificateBuilder()
            .elementData(List.of(element, QUESTION_PERIOD))
            .build();

        assertThrows(IllegalStateException.class, () -> generator.generate(certificate, true));
      }

      @Test
      void shallReturnFalseIfQuestionMissing() {
        final var element = ElementData.builder()
            .id(new ElementId("1"))
            .value(ElementValueText.builder().build())
            .build();

        final var certificate = TestDataCertificate.fk7443CertificateBuilder()
            .elementData(List.of(element, QUESTION_PERIOD))
            .build();

        assertThrows(IllegalStateException.class, () -> generator.generate(certificate, true));
      }
    }

    @Nested
    class QuestionPeriod {

      private static final ElementData QUESTION_SYMTOM = ElementData.builder()
          .id(new ElementId("1"))
          .value(
              ElementValueText.builder()
                  .textId(new FieldId("1.1"))
                  .text("text")
                  .build()
          )
          .build();

      @Test
      void shallReturnFalseIfQuestionIdIsMissing() {
        final var element = List.of(
            QUESTION_SYMTOM
        );

        final var certificate = TestDataCertificate.fk7443CertificateBuilder()
            .elementData(element)
            .build();

        assertThrows(IllegalStateException.class, () -> generator.generate(certificate, true));
      }

      @Test
      void shallReturnFalseIfDateRangeIsMissing() {
        final var element = List.of(
            QUESTION_SYMTOM,
            ElementData.builder()
                .id(new ElementId("2"))
                .value(
                    ElementValueDateRangeList.builder()
                        .dateRangeList(
                            Collections.emptyList()
                        )
                        .dateRangeListId(new FieldId("2.1"))
                        .build())
                .build()
        );

        final var certificate = TestDataCertificate.fk7443CertificateBuilder()
            .elementData(element)
            .build();

        assertThrows(IllegalStateException.class, () -> generator.generate(certificate, true));
      }

      @Test
      void shallReturnFalseIfDateRangeIsOverlapping() {
        final var element = List.of(
            QUESTION_SYMTOM,
            ElementData.builder()
                .id(new ElementId("2"))
                .value(
                    ElementValueDateRangeList.builder()
                        .dateRangeList(
                            List.of(
                                DateRange.builder()
                                    .dateRangeId(new FieldId("EN_ATTANDEL"))
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
                        .dateRangeListId(new FieldId("2.1"))
                        .build())
                .build()
        );

        final var certificate = TestDataCertificate.fk7443CertificateBuilder()
            .elementData(element)
            .build();

        assertThrows(IllegalStateException.class, () -> generator.generate(certificate, true));
      }

      @Test
      void shallReturnFalseIfMultipleDateRangeWithSameCodeAppear() {
        final var element = List.of(
            QUESTION_SYMTOM,
            ElementData.builder()
                .id(new ElementId("2"))
                .value(
                    ElementValueDateRangeList.builder()
                        .dateRangeList(
                            List.of(
                                DateRange.builder()
                                    .dateRangeId(new FieldId("EN_ATTANDEL"))
                                    .from(LocalDate.now())
                                    .to(LocalDate.now().plusDays(1))
                                    .build(),
                                DateRange.builder()
                                    .dateRangeId(new FieldId("EN_ATTANDEL"))
                                    .from(LocalDate.now().minusDays(4))
                                    .to(LocalDate.now().plusDays(9))
                                    .build()
                            )
                        )
                        .dateRangeListId(new FieldId("2.1"))
                        .build())
                .build()
        );

        final var certificate = TestDataCertificate.fk7443CertificateBuilder()
            .elementData(element)
            .build();

        assertThrows(IllegalStateException.class, () -> generator.generate(certificate, true));
      }

      @Test
      void shallReturnFalseIfDateRangeFromIsNull() {
        final var element = List.of(
            QUESTION_SYMTOM,
            ElementData.builder()
                .id(new ElementId("2"))
                .value(
                    ElementValueDateRangeList.builder()
                        .dateRangeList(
                            List.of(
                                DateRange.builder()
                                    .dateRangeId(new FieldId("EN_ATTANDEL"))
                                    .to(LocalDate.now().plusDays(1))
                                    .build()
                            )
                        )
                        .dateRangeListId(new FieldId("2.1"))
                        .build())
                .build()
        );

        final var certificate = TestDataCertificate.fk7443CertificateBuilder()
            .elementData(element)
            .build();

        assertThrows(IllegalStateException.class, () -> generator.generate(certificate, true));
      }


      @Test
      void shallReturnFalseIfDateRangeToIsNull() {
        final var element = List.of(
            QUESTION_SYMTOM,
            ElementData.builder()
                .id(new ElementId("2"))
                .value(
                    ElementValueDateRangeList.builder()
                        .dateRangeList(
                            List.of(
                                DateRange.builder()
                                    .dateRangeId(new FieldId("EN_ATTANDEL"))
                                    .from(LocalDate.now())
                                    .build()
                            )
                        )
                        .dateRangeListId(new FieldId("2.1"))
                        .build())
                .build()
        );

        final var certificate = TestDataCertificate.fk7443CertificateBuilder()
            .elementData(element)
            .build();

        assertThrows(IllegalStateException.class, () -> generator.generate(certificate, true));
      }
    }
  }
}
