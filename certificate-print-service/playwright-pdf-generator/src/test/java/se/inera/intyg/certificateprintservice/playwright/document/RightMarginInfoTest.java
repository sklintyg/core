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
          () -> assertEquals(DIV, element.tag(), TAG_TYPE),
          () -> assertEquals(1, element.children().size(), NUM_CHILDREN),
          () -> assertEquals(0, element.attributes().asList().size(), NUM_ATTRIBUTES)

      );
    }

    @Nested
    class WrapperNodes {

      @Test
      void pElementFirst() {
        final var expectedText = RIGHT_MARGIN_TEXT.formatted(CERTIFICATE_ID);
        final var element = rightMarginInfo.create().child(0);
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
