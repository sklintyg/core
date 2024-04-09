package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.elementdata;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.DateRange;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateRangeList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

class ElementValueMapperDateRangeListTest {

  private ElementValueMapperDateRangeList elementValueMapper;

  @BeforeEach
  void setUp() {
    elementValueMapper = new ElementValueMapperDateRangeList();
  }

  @Test
  void shallReturnTrueIfClassMappedIsMappedElementValueDateRangeList() {
    assertTrue(elementValueMapper.supports(MappedElementValueDateRangeList.class));
  }

  @Test
  void shallReturnTrueIfClassElementValueIsElementValueDateRangeList() {
    assertTrue(elementValueMapper.supports(ElementValueDateRangeList.class));
  }

  @Test
  void shallReturnFalseForUnsupportedClass() {
    assertFalse(elementValueMapper.supports(String.class));
  }

  @Test
  void shallMapToDomain() {
    final var to = LocalDate.now();
    final var from = LocalDate.now().minusDays(1);
    final var expectedValue = ElementValueDateRangeList.builder()
        .dateRangeListId(new FieldId("ID"))
        .dateRangeList(
            List.of(
                DateRange.builder()
                    .dateRangeId(new FieldId("RANGE_ID"))
                    .to(to)
                    .from(from)
                    .build()
            )
        )
        .build();

    final var mappedElementValue = MappedElementValueDateRangeList.builder()
        .dateRangeListId("ID")
        .dateRangeList(
            List.of(
                MappedDateRange.builder()
                    .id("RANGE_ID")
                    .to(to)
                    .from(from)
                    .build()
            )
        )
        .build();

    final var actualValue = elementValueMapper.toDomain(
        mappedElementValue
    );

    assertEquals(expectedValue, actualValue);
  }

  @Test
  void shallMapToMapped() {
    final var to = LocalDate.now();
    final var from = LocalDate.now().minusDays(1);
    final var expectedValue = MappedElementValueDateRangeList.builder()
        .dateRangeListId("ID")
        .dateRangeList(
            List.of(
                MappedDateRange.builder()
                    .id("RANGE_ID")
                    .to(to)
                    .from(from)
                    .build()
            )
        )
        .build();

    final var elementValue = ElementValueDateRangeList.builder()
        .dateRangeListId(new FieldId("ID"))
        .dateRangeList(
            List.of(
                DateRange.builder()
                    .dateRangeId(new FieldId("RANGE_ID"))
                    .to(to)
                    .from(from)
                    .build()
            )
        )
        .build();

    final var actualValue = elementValueMapper.toMapped(
        elementValue
    );

    assertEquals(expectedValue, actualValue);
  }
}