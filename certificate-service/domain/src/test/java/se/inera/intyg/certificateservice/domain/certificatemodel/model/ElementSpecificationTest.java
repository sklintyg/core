package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataElementDataConstants.DATE_ELEMENT_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataElementSpecification.DATE_ELEMENT_SPECIFICATION;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataElementSpecification.dateElementSpecificationBuilder;

import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ElementSpecificationTest {

  @Nested
  class TestElementSpecificationExists {

    @Test
    void shallReturnTrueIfElementExitsOnRootLevel() {
      assertTrue(
          DATE_ELEMENT_SPECIFICATION.exists(new ElementId(DATE_ELEMENT_ID)),
          "Expected to find '%s' among specifications '%s'".formatted(DATE_ELEMENT_ID,
              DATE_ELEMENT_SPECIFICATION)
      );
    }

    @Test
    void shallReturnTrueIfElementExitsOnNextLevel() {
      final var element = dateElementSpecificationBuilder()
          .id(new ElementId("ANOTHER_ELEMENT_ID"))
          .children(List.of(DATE_ELEMENT_SPECIFICATION))
          .build();

      assertTrue(element.exists(new ElementId(DATE_ELEMENT_ID)),
          "Expected to find '%s' among specifications '%s'".formatted(DATE_ELEMENT_ID, element)
      );
    }
  }

  @Nested
  class TestElementSpecificationWithId {

    @Test
    void shallReturnElementIfElementExitsOnRootLevel() {
      assertEquals(DATE_ELEMENT_SPECIFICATION,
          DATE_ELEMENT_SPECIFICATION.elementSpecification(DATE_ELEMENT_SPECIFICATION.id())
      );
    }

    @Test
    void shallReturnElementIfElementExitsOnNextLevel() {
      final var element = dateElementSpecificationBuilder()
          .id(new ElementId("ANOTHER_ELEMENT_ID"))
          .children(List.of(DATE_ELEMENT_SPECIFICATION))
          .build();

      assertEquals(DATE_ELEMENT_SPECIFICATION,
          element.elementSpecification(DATE_ELEMENT_SPECIFICATION.id())
      );
    }

    @Test
    void shallThrowExceptionIfElementDoesntExists() {
      final var element = dateElementSpecificationBuilder()
          .id(new ElementId("ANOTHER_ELEMENT_ID"))
          .build();

      final var elementId = DATE_ELEMENT_SPECIFICATION.id();
      assertThrows(IllegalArgumentException.class,
          () -> element.elementSpecification(elementId)
      );
    }

    @Test
    void shallThrowExceptionIfElementDoesntExistsOnNextLevel() {
      final var element = dateElementSpecificationBuilder()
          .id(new ElementId("ANOTHER_ELEMENT_ID"))
          .children(
              List.of(
                  dateElementSpecificationBuilder()
                      .id(new ElementId("ANOTHER_ELEMENT_ID"))
                      .build()
              )
          )
          .build();

      final var elementId = DATE_ELEMENT_SPECIFICATION.id();
      assertThrows(IllegalArgumentException.class,
          () -> element.elementSpecification(elementId)
      );
    }
  }
}