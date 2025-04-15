package se.inera.intyg.certificateservice.application.certificate.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.FK7427_CERTIFICATE;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfigDateRange;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDateRange;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

class CertificateDataDateRangeConfigConverterTest {

  private static final String ID = "ID";
  private static final String NAME = "NAME";
  private static final String LABEL_FROM = "FROM_LABEL";
  private static final String LABEL_TO = "TO_LABEL";
  private static final String FIELD_ID = "FIELD_ID";

  private CertificateDataDateRangeConfigConverter converter;
  private ElementSpecification elementSpecification;

  @BeforeEach
  void setUp() {
    converter = new CertificateDataDateRangeConfigConverter();
    elementSpecification = ElementSpecification.builder()
        .id(new ElementId(ID))
        .configuration(
            ElementConfigurationDateRange.builder()
                .id(new FieldId((FIELD_ID)))
                .name(NAME)
                .labelFrom(LABEL_FROM)
                .labelTo(LABEL_TO)
                .build()
        )
        .build();
  }

  @Test
  void shouldReturnDateRangeType() {
    assertEquals(ElementType.DATE_RANGE, converter.getType());
  }

  @Test
  void shouldThrowExceptionIfWrongClass() {
    final var invalidElementSpecification = ElementSpecification.builder()
        .configuration(
            ElementConfigurationTextArea.builder()
                .build()
        )
        .build();

    assertThrows(IllegalStateException.class,
        () -> converter.convert(invalidElementSpecification, FK7427_CERTIFICATE)
    );
  }

  @Test
  void shouldReturnConvertedConfig() {
    final var expected = CertificateDataConfigDateRange.builder()
        .id(FIELD_ID)
        .text(NAME)
        .fromLabel(LABEL_FROM)
        .toLabel(LABEL_TO)
        .build();

    final var response = converter.convert(elementSpecification, FK7427_CERTIFICATE);

    assertEquals(expected, response);
  }
}
