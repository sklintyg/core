package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate;

class SchematronValidatorTest {

  private SchematronValidator schematronValidator;

  @BeforeEach
  void setUp() {
    schematronValidator = new SchematronValidator();
  }

  @Nested
  class FK7211Validation {

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

        final var certificate = TestDataCertificate.fk7211CertificateBuilder()
            .elementData(List.of(element))
            .build();

        final var generator = new XmlGeneratorCertificateV4(new XmlGeneratorValue());
        final var xml = generator.generate(certificate);

        final var certificate1 = TestDataCertificate.fk7211CertificateBuilder()
            .xml(xml)
            .build();

        assertFalse(schematronValidator.validate(certificate1));
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

        final var certificate = TestDataCertificate.fk7211CertificateBuilder()
            .elementData(List.of(element))
            .build();

        final var generator = new XmlGeneratorCertificateV4(new XmlGeneratorValue());
        final var xml = generator.generate(certificate);

        final var certificate1 = TestDataCertificate.fk7211CertificateBuilder()
            .xml(xml)
            .build();

        assertFalse(schematronValidator.validate(certificate1));
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

        final var certificate = TestDataCertificate.fk7211CertificateBuilder()
            .elementData(List.of(element))
            .build();

        final var generator = new XmlGeneratorCertificateV4(new XmlGeneratorValue());
        final var xml = generator.generate(certificate);

        final var certificate1 = TestDataCertificate.fk7211CertificateBuilder()
            .xml(xml)
            .build();

        assertFalse(schematronValidator.validate(certificate1));
      }

      @Test
      void shallReturnFalseIfQuestionMissing() {
        final var element = ElementData.builder()
            .id(new ElementId("1"))
            .value(
                ElementValueDate.builder().build()
            ).build();

        final var certificate = TestDataCertificate.fk7211CertificateBuilder()
            .elementData(List.of(element))
            .build();

        final var generator = new XmlGeneratorCertificateV4(new XmlGeneratorValue());
        final var xml = generator.generate(certificate);

        final var certificate1 = TestDataCertificate.fk7211CertificateBuilder()
            .xml(xml)
            .build();

        assertFalse(schematronValidator.validate(certificate1));
      }
    }
  }

  @Nested
  class FK7443Validation {

    @Test
    void shallReturnTrueIfAllFieldsHaveValues() {
      final var element = ElementData.builder()
          .id(new ElementId("1"))
          .value(
              ElementValueText.builder()
                  .textId(new FieldId("1.1"))
                  .text("text")
                  .build()
          ).build();

      final var certificate = TestDataCertificate.fk443CertificateBuilder()
          .elementData(List.of(element))
          .build();

      final var generator = new XmlGeneratorCertificateV4(new XmlGeneratorValue());
      final var xml = generator.generate(certificate);

      final var certificate1 = TestDataCertificate.fk443CertificateBuilder()
          .xml(xml)
          .build();

      assertTrue(schematronValidator.validate(certificate1));
    }

    @Nested
    class QuestionDiagnosEllerSymtom {

      @Test
      void shallReturnFalseIfValueIsNull() {
        final var element = ElementData.builder()
            .id(new ElementId("1"))
            .value(
                ElementValueText.builder()
                    .textId(new FieldId("1.1"))
                    .build()
            ).build();

        final var certificate = TestDataCertificate.fk443CertificateBuilder()
            .elementData(List.of(element))
            .build();

        final var generator = new XmlGeneratorCertificateV4(new XmlGeneratorValue());
        final var xml = generator.generate(certificate);

        final var certificate1 = TestDataCertificate.fk443CertificateBuilder()
            .xml(xml)
            .build();

        assertFalse(schematronValidator.validate(certificate1));
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

        final var certificate = TestDataCertificate.fk443CertificateBuilder()
            .elementData(List.of(element))
            .build();

        final var generator = new XmlGeneratorCertificateV4(new XmlGeneratorValue());
        final var xml = generator.generate(certificate);

        final var certificate1 = TestDataCertificate.fk443CertificateBuilder()
            .xml(xml)
            .build();

        assertFalse(schematronValidator.validate(certificate1));
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

        final var certificate = TestDataCertificate.fk443CertificateBuilder()
            .elementData(List.of(element))
            .build();

        final var generator = new XmlGeneratorCertificateV4(new XmlGeneratorValue());
        final var xml = generator.generate(certificate);

        final var certificate1 = TestDataCertificate.fk443CertificateBuilder()
            .xml(xml)
            .build();

        assertFalse(schematronValidator.validate(certificate1));
      }

      @Test
      void shallReturnFalseIfQuestionMissing() {
        final var element = ElementData.builder()
            .id(new ElementId("1"))
            .value(ElementValueText.builder().build())
            .build();

        final var certificate = TestDataCertificate.fk443CertificateBuilder()
            .elementData(List.of(element))
            .build();

        final var generator = new XmlGeneratorCertificateV4(new XmlGeneratorValue());
        final var xml = generator.generate(certificate);

        final var certificate1 = TestDataCertificate.fk443CertificateBuilder()
            .xml(xml)
            .build();

        assertFalse(schematronValidator.validate(certificate1));
      }
    }
  }
}
