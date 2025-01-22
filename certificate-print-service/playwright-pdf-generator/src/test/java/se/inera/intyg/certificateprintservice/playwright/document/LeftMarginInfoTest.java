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
          () -> assertEquals(DIV, element.tag(), TAG_TYPE),
          () -> assertEquals(1, element.children().size(), NUM_CHILDREN),
          () -> assertEquals(0, element.attributes().asList().size(), NUM_ATTRIBUTES)
      );
    }

    @Nested
    class WrapperElements {

      @Test
      void leftMarginInfo() {
        final var expectedText = LEFT_MARGIN_TEXT.formatted(CERTIFICATE_TYPE, RECIPIENT_NAME);
        final var element = leftMarginInfo.create().child(0);
        assertAll(
            () -> assertEquals(P, element.tag(), TAG_TYPE),
            () -> assertEquals(0, element.children().size(), NUM_CHILDREN),
            () -> assertEquals(expectedText, element.text(), TEXT),
            () -> assertEquals(0, element.attributes().asList().size(), NUM_ATTRIBUTES)
        );
      }
    }
  }

}
