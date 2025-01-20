package se.inera.intyg.certificateprintservice.playwright.document;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.jsoup.parser.Tag;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class RightMarginInfoTest {

  private static final String CERTIFICATE_ID = "certificateId";
  private static final String RIGHT_MARGIN_TEXT = "Intygs-ID: %s";

  private final RightMarginInfo rightMarginInfo = RightMarginInfo.builder()
      .certificateId(CERTIFICATE_ID)
      .build();

  @Nested
  class Wrapper {

    @Test
    void wrapperDiv() {
      final var element = rightMarginInfo.create();
      assertAll(
          () -> assertEquals(Tag.valueOf("div"), element.tag(), "Tag type"),
          () -> assertEquals(1, element.children().size(), "Number of children"),
          () -> assertEquals(0, element.attributes().asList().size(), "Attributes")

      );
    }

    @Nested
    class WrapperNodes {

      @Test
      void pElementFirst() {
        final var expectedText = RIGHT_MARGIN_TEXT.formatted(CERTIFICATE_ID);
        final var element = rightMarginInfo.create().child(0);
        assertAll(
            () -> assertEquals(Tag.valueOf("p"), element.tag(), "Wrong tag"),
            () -> assertEquals(0, element.children().size(), "Number of children"),
            () -> assertEquals(expectedText, element.text(), "Text"),
            () -> assertEquals(0, element.attributes().asList().size(), "Attributes")
        );
      }
    }
  }

}
