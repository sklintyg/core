package se.inera.intyg.certificateprintservice.playwright.document;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.jsoup.parser.Tag;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class LeftMarginInfoTest {

  private static final String CERTIFICATE_TYPE = "certificateType";
  private static final String RECIPIENT_NAME = "recipientName";
  private static final String LEFT_MARGIN_TEXT = "%s - Fastställd av %s";

  private final LeftMarginInfo leftMarginInfo = LeftMarginInfo.builder()
      .certificateType(CERTIFICATE_TYPE)
      .recipientName(RECIPIENT_NAME)
      .build();

  @Nested
  class Wrapper {

    @Test
    void wrapperDiv() {
      final var element = leftMarginInfo.create();
      assertAll(
          () -> assertEquals(Tag.valueOf("div"), element.tag(), "Wrong tag"),
          () -> assertEquals(1, element.children().size(), "Number of children"),
          () -> assertEquals(0, element.attributes().asList().size(), "Attributes")
      );
    }

    @Nested
    class WrapperElements {

      @Test
      void leftMarginInfo() {
        final var expectedText = LEFT_MARGIN_TEXT.formatted(CERTIFICATE_TYPE, RECIPIENT_NAME);
        final var element = leftMarginInfo.create().child(0);
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
