package se.inera.intyg.certificateservice.domain.validation.model;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueUnitContactInformation;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

class ElementValidationDateListTest {

  private static final ElementId ELEMENT_ID = new ElementId("elementId");
  private static final FieldId FIELD_ID = new FieldId("fieldId");
  private static final FieldId FIELD_ID_DATE_ONE = new FieldId("dateIdOne");

  private ElementValidationDateList elementValidationDateList;

  @Nested
  class IllegalStates {

    @BeforeEach
    void setUp() {
      elementValidationDateList = ElementValidationDateList.builder().build();
    }

    @Test
    void shallThrowIllegalArgumentExceptionIfDataIsNull() {
      final Optional<ElementId> categoryId = Optional.empty();
      assertThrows(IllegalArgumentException.class,
          () -> elementValidationDateList.validate(null, categoryId)
      );
    }

    @Test
    void shallThrowIllegalArgumentExceptionIfValueIsNull() {
      final Optional<ElementId> categoryId = Optional.empty();
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .build();

      assertThrows(IllegalArgumentException.class,
          () -> elementValidationDateList.validate(elementData, categoryId));
    }

    @Test
    void shallThrowIllegalArgumentExceptionIfValueIsWrongType() {
      final Optional<ElementId> categoryId = Optional.empty();
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(ElementValueUnitContactInformation.builder().build())
          .build();

      assertThrows(IllegalArgumentException.class,
          () -> elementValidationDateList.validate(elementData, categoryId));
    }
  }

  @Nested
  class DateAfterMaxErrors {

    @Test
    void shallReturnErrorMessageIfDateIsAfterMaxAndMaxIsSet() {
      final var elementData = ElementData.builder()
          .value(
              ElementValueDateList.builder()
                  .dateListId(FIELD_ID)
                  .dateList(
                      List.of(
                          ElementValueDate.builder()
                              .dateId(FIELD_ID_DATE_ONE)
                              .date(LocalDate.now().plusDays(1))
                              .build()
                      )
                  )
                  .build()
          )
          .build();

    }
  }
}
