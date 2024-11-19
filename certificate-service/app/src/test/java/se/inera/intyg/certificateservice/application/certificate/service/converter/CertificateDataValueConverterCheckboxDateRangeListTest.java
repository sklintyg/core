package se.inera.intyg.certificateservice.application.certificate.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueDateRangeList;
import se.inera.intyg.certificateservice.domain.certificate.model.DateRange;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateRangeList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxDateRangeList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxDateRangeList.ElementConfigurationCheckboxDateRangeListBuilder;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

class CertificateDataValueConverterCheckboxDateRangeListTest {

  private static final String ELEMENT_ID = "elementId";
  private static final FieldId FIELD_ID = new FieldId("code");
  private final CertificateDataValueConverterCheckboxDateRangeList converter = new CertificateDataValueConverterCheckboxDateRangeList();
  private ElementConfigurationCheckboxDateRangeListBuilder elementConfigurationBuilder;

  @BeforeEach
  void setUp() {
    elementConfigurationBuilder = ElementConfigurationCheckboxDateRangeList.builder()
        .id(FIELD_ID);
  }

  @Test
  void shouldThrowExceptionIfWrongClassOfValue() {
    final var configuration = ElementSpecification.builder()
        .id(new ElementId(ELEMENT_ID))
        .configuration(
            ElementConfigurationCheckboxDateRangeList.builder()
                .id(FIELD_ID)
                .build()
        )
        .build();

    final var elementValue = ElementValueText.builder().build();

    assertThrows(IllegalStateException.class,
        () -> converter.convert(configuration, elementValue)
    );
  }

  @Test
  void shouldNotThrowExceptionForWrongClassOfValueIfElementValueIsNull() {
    final var configuration = ElementSpecification.builder()
        .id(new ElementId(ELEMENT_ID))
        .configuration(
            ElementConfigurationCheckboxDateRangeList.builder()
                .id(FIELD_ID)
                .build()
        )
        .build();

    final var result = converter.convert(configuration, null);
    assertEquals(Collections.emptyList(), ((CertificateDataValueDateRangeList) result).getList());
  }

  @Test
  void shallReturnType() {
    assertEquals(ElementType.CHECKBOX_DATE_RANGE_LIST, converter.getType());
  }

  @Test
  void shallCreateCertificateDataValue() {
    final var configuration = ElementSpecification.builder()
        .id(new ElementId(ELEMENT_ID))
        .configuration(
            elementConfigurationBuilder
                .build()
        )
        .build();

    final var elementValue = ElementValueDateRangeList.builder().build();

    final var result = converter.convert(configuration, elementValue);

    assertInstanceOf(CertificateDataValueDateRangeList.class, result);
  }

  @Test
  void shallSetCorrectIdForDateRangeValue() {
    final var configuration = ElementSpecification.builder()
        .id(new ElementId(ELEMENT_ID))
        .configuration(
            elementConfigurationBuilder.build()
        )
        .build();

    final var elementValue = ElementValueDateRangeList.builder()
        .dateRangeList(
            List.of(
                DateRange.builder()
                    .dateRangeId(new FieldId("RANGE_ID"))
                    .to(LocalDate.now())
                    .from(LocalDate.now().minusDays(1))
                    .build()
            )
        )
        .build();

    final var result = converter.convert(configuration, elementValue);

    assertEquals(elementValue.dateRangeList().get(0).dateRangeId().value(),
        ((CertificateDataValueDateRangeList) result).getList().get(0).getId()
    );
  }

  @Test
  void shallSetCorrectToDateForDateRangeValue() {
    final var configuration = ElementSpecification.builder()
        .id(new ElementId(ELEMENT_ID))
        .configuration(
            elementConfigurationBuilder.build()
        )
        .build();

    final var elementValue = ElementValueDateRangeList.builder()
        .dateRangeList(
            List.of(
                DateRange.builder()
                    .dateRangeId(new FieldId("RANGE_ID"))
                    .to(LocalDate.now())
                    .from(LocalDate.now().minusDays(1))
                    .build()
            )
        )
        .build();

    final var result = converter.convert(configuration, elementValue);

    assertEquals(elementValue.dateRangeList().get(0).to(),
        ((CertificateDataValueDateRangeList) result).getList().get(0).getTo()
    );
  }

  @Test
  void shallSetCorrectFromDateForDateRangeValue() {
    final var configuration = ElementSpecification.builder()
        .id(new ElementId(ELEMENT_ID))
        .configuration(
            elementConfigurationBuilder.build()
        )
        .build();

    final var elementValue = ElementValueDateRangeList.builder()
        .dateRangeList(
            List.of(
                DateRange.builder()
                    .dateRangeId(new FieldId("RANGE_ID"))
                    .to(LocalDate.now())
                    .from(LocalDate.now().minusDays(1))
                    .build()
            )
        )
        .build();

    final var result = converter.convert(configuration, elementValue);

    assertEquals(elementValue.dateRangeList().get(0).from(),
        ((CertificateDataValueDateRangeList) result).getList().get(0).getFrom()
    );
  }

  @Test
  void shallSetValueToNull() {
    final var configuration = ElementSpecification.builder()
        .id(new ElementId(ELEMENT_ID))
        .configuration(
            elementConfigurationBuilder.build()
        )
        .build();

    final var elementValue = ElementValueDateRangeList.builder()
        .build();

    final var result = converter.convert(configuration, elementValue);

    assertTrue(((CertificateDataValueDateRangeList) result).getList().isEmpty(),
        "If no value is provided value should be empty list");
  }

  @Test
  void shallSetIdFromConfiguration() {
    final var configuration = ElementSpecification.builder()
        .id(new ElementId(ELEMENT_ID))
        .configuration(
            elementConfigurationBuilder.build()
        )
        .build();

    final var elementValue = ElementValueDateRangeList.builder()
        .build();

    final var result = converter.convert(configuration, elementValue);

    assertEquals(FIELD_ID.value(), ((CertificateDataValueDateRangeList) result).getId());
  }
}