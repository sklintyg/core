package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.elementdata;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

class ElementValueMapperDateListTest {

  private ElementValueMapperDateList elementValueMapper;

  @BeforeEach
  void setUp() {
    elementValueMapper = new ElementValueMapperDateList();
  }

  @Test
  void shallReturnTrueIfClassMappedIsMappedElementValueDateList() {
    assertTrue(elementValueMapper.supports(MappedElementValueDateList.class));
  }

  @Test
  void shallReturnTrueIfClassElementValueIsElementValueDateList() {
    assertTrue(elementValueMapper.supports(ElementValueDateList.class));
  }

  @Test
  void shallReturnFalseForUnsupportedClass() {
    assertFalse(elementValueMapper.supports(String.class));
  }

  @Test
  void shallMapToDomain() {
    final var date = LocalDate.now();
    final var expectedValue = ElementValueDateList.builder()
        .dateListId(new FieldId("ID"))
        .dateList(
            List.of(
                ElementValueDate.builder()
                    .dateId(new FieldId("DATE_ID"))
                    .date(date)
                    .build()
            )
        )
        .build();

    final var mappedElementValue = MappedElementValueDateList.builder()
        .dateListId("ID")
        .dateList(
            List.of(
                MappedDate.builder()
                    .id("DATE_ID")
                    .date(date)
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
    final var date = LocalDate.now();
    final var expectedValue = MappedElementValueDateList.builder()
        .dateListId("ID")
        .dateList(
            List.of(
                MappedDate.builder()
                    .id("DATE_ID")
                    .date(date)
                    .build()
            )
        )
        .build();

    final var elementValue = ElementValueDateList.builder()
        .dateListId(new FieldId("ID"))
        .dateList(
            List.of(
                ElementValueDate.builder()
                    .dateId(new FieldId("DATE_ID"))
                    .date(date)
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