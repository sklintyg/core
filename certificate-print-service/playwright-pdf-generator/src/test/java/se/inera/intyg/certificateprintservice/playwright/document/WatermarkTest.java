package se.inera.intyg.certificateprintservice.playwright.document;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateprintservice.playwright.document.Constants.DIV;
import static se.inera.intyg.certificateprintservice.playwright.document.Constants.NUM_ATTRIBUTES;
import static se.inera.intyg.certificateprintservice.playwright.document.Constants.NUM_CHILDREN;
import static se.inera.intyg.certificateprintservice.playwright.document.Constants.P;
import static se.inera.intyg.certificateprintservice.playwright.document.Constants.TAG_TYPE;
import static se.inera.intyg.certificateprintservice.playwright.document.Constants.TEXT;

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
          () -> assertEquals(DIV, element.tag(), TAG_TYPE),
          () -> assertEquals(1, element.children().size(), NUM_CHILDREN),
          () -> assertEquals(0, element.attributes().asList().size(), NUM_ATTRIBUTES)
      );
    }

    @Nested
    class WrapperElements {

      @Test
      void watermark() {
        final var element = watermark.create().child(0);
        assertAll(
            () -> assertEquals(P, element.tag(), TAG_TYPE),
            () -> assertEquals(0, element.children().size(), NUM_CHILDREN),
            () -> assertEquals(UTKAST, element.text(), TEXT),
            () -> assertEquals(0, element.attributes().asList().size(), NUM_ATTRIBUTES)
        );
      }
    }
  }

}
