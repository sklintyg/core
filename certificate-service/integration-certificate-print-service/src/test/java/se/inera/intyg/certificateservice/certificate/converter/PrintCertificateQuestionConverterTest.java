package se.inera.intyg.certificateservice.certificate.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.fk7210CertificateBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModel.fk7210certificateModelBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValueText;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

class PrintCertificateQuestionConverterTest {

  private static final LocalDate DATE = LocalDate.now();
  private static final Certificate CERTIFICATE = fk7210CertificateBuilder()
      .certificateModel(
          fk7210certificateModelBuilder()
              .elementSpecifications(List.of(
                      ElementSpecification.builder()
                          .id(new ElementId("1"))
                          .configuration(
                              ElementConfigurationDate.builder()
                                  .name("Beräknat födelsedatum")
                                  .build()
                          )
                          .build()
                  )
              ).build()
      )
      .elementData(
          List.of(
              ElementData.builder()
                  .id(new ElementId("1"))
                  .value(
                      ElementValueDate.builder()
                          .date(DATE)
                          .build()
                  )
                  .build()
          )
      )
      .build();
  ;

  private final PrintCertificateQuestionConverter printCertificateQuestionConverter = new PrintCertificateQuestionConverter();

  @Test
  void shouldSetName() {
    final var response = printCertificateQuestionConverter.convert(
        CERTIFICATE.certificateModel().elementSpecifications().getFirst(), CERTIFICATE
    );

    assertEquals("Beräknat födelsedatum", response.getName());
  }

  @Test
  void shouldReturnNullIfNoElementDate() {
    final var response = printCertificateQuestionConverter.convert(
        ElementSpecification.builder().id(new ElementId("2")).build(), CERTIFICATE
    );

    assertNull(response);
  }

  @Test
  void shouldSetValue() {
    final var expected = ElementSimplifiedValueText.builder()
        .text(DATE.format(DateTimeFormatter.ISO_DATE))
        .build();

    final var response = printCertificateQuestionConverter.convert(
        CERTIFICATE.certificateModel().elementSpecifications().getFirst(), CERTIFICATE
    );

    assertEquals(expected, response.getValue());
  }
}