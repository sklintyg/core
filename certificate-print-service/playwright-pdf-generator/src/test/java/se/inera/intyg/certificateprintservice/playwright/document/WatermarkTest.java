package se.inera.intyg.certificateprintservice.playwright.document;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.jsoup.parser.Tag;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class WatermarkTest {

  private static final String UTKAST = "UTKAST";
  private final Watermark watermark = Watermark.builder().build();


  @Nested
  class Wrapper {

    @Test
    void wrapperDiv() {
      final var element = watermark.create();
      assertAll(
          () -> assertEquals(Tag.valueOf("div"), element.tag(), "Tag type"),
          () -> assertEquals(1, element.children().size(), "Number of children"),
          () -> assertEquals(0, element.attributes().asList().size(), "Attributes")
      );
    }

    @Nested
    class WrapperElements {

      @Test
      void watermark() {
        final var element = watermark.create().child(0);
        assertAll(
            () -> assertEquals(Tag.valueOf("p"), element.tag(), "Tag type"),
            () -> assertEquals(0, element.children().size(), "Number of children"),
            () -> assertEquals(UTKAST, element.text(), "Text"),
            () -> assertEquals(0, element.attributes().asList().size(), "Attributes")
        );
      }
    }
  }

}
