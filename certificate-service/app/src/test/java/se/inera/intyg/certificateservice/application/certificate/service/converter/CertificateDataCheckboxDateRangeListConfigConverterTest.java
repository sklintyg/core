package se.inera.intyg.certificateservice.application.certificate.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Period;
import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfigCheckboxDateRangeList;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CheckboxDateRangeConfig;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CheckboxDateRange;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxDateRangeList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

class CertificateDataCheckboxDateRangeListConfigConverterTest {

  CertificateDataCheckboxDateRangeListConfigConverter converter = new CertificateDataCheckboxDateRangeListConfigConverter();

  @Test
  void shouldReturnCategoryType() {
    assertEquals(ElementType.CHECKBOX_DATE_RANGE_LIST, converter.getType());
  }

  @Test
  void shouldThrowExceptionIfWrongClass() {
    final var elementSpecification = ElementSpecification.builder()
        .configuration(ElementConfigurationDate.builder().build())
        .build();

    assertThrows(IllegalStateException.class,
        () -> converter.convert(elementSpecification)
    );
  }

  @Test
  void shouldReturnConvertedConfig() {
    final var expected = CertificateDataConfigCheckboxDateRangeList.builder()
        .text("NAME")
        .label("LABEL")
        .hideWorkingHours(true)
        .previousDateRangeText("TEST TEST")
        .min(Period.ofMonths(-1))
        .max(Period.ofMonths(2))
        .list(
            List.of(
                CheckboxDateRangeConfig.builder()
                    .id("ID")
                    .label("LABEL")
                    .build(),
                CheckboxDateRangeConfig.builder()
                    .id("ID2")
                    .label("LABEL2")
                    .build()
            )
        )
        .build();

    final var response = converter.convert(
        ElementSpecification.builder()
            .configuration(
                ElementConfigurationCheckboxDateRangeList.builder()
                    .id(new FieldId("ID"))
                    .hideWorkingHours(true)
                    .previousDateRangeText("TEST TEST")
                    .name("NAME")
                    .label("LABEL")
                    .min(Period.ofMonths(-1))
                    .max(Period.ofMonths(2))
                    .dateRanges(
                        List.of(
                            new CheckboxDateRange(new FieldId("ID"), "LABEL"),
                            new CheckboxDateRange(new FieldId("ID2"), "LABEL2")
                        )
                    )
                    .build()
            ).build()
    );

    assertEquals(expected, response);
  }
}
