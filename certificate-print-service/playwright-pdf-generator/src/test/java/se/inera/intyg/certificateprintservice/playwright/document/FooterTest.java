package se.inera.intyg.certificateprintservice.playwright.document;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateprintservice.playwright.document.Constants.A;
import static se.inera.intyg.certificateprintservice.playwright.document.Constants.ATTRIBUTES;
import static se.inera.intyg.certificateprintservice.playwright.document.Constants.CLASS;
import static se.inera.intyg.certificateprintservice.playwright.document.Constants.DIV;
import static se.inera.intyg.certificateprintservice.playwright.document.Constants.HREF;
import static se.inera.intyg.certificateprintservice.playwright.document.Constants.NUM_ATTRIBUTES;
import static se.inera.intyg.certificateprintservice.playwright.document.Constants.NUM_CHILDREN;
import static se.inera.intyg.certificateprintservice.playwright.document.Constants.P;
import static se.inera.intyg.certificateprintservice.playwright.document.Constants.TAG_TYPE;
import static se.inera.intyg.certificateprintservice.playwright.document.Constants.TEXT;

import java.util.Objects;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class FooterTest {

  private static final String APPLICATION_ORIGIN = "applicationOrigin";
  private static final String FOOTER_TEXT = "Utskriften skapades med %s - en tjänst som drivs av Inera AB";
  private static final String FOOTER_LINK_URL = "https://inera.se";
  private static final String FOOTER_LINK_TEXT = "www.inera.se";

  private final Footer footer = Footer.builder()
      .applicationOrigin(APPLICATION_ORIGIN)
      .build();

  @Nested
  class Wrapper {

    @Test
    void wrapperDiv() {
      final var element = footer.create();
      assertAll(
          () -> assertEquals(DIV, element.tag(), TAG_TYPE),
          () -> assertEquals(0, element.attributes().asList().size(), ATTRIBUTES),
          () -> assertEquals(2, element.children().size(), NUM_CHILDREN)
      );
    }

    @Nested
    class WrapperElements {

      @Test
      void footerText() throws NullPointerException {
        final var expectedText = FOOTER_TEXT.formatted(APPLICATION_ORIGIN);
        final var element = footer.create().child(0);
        assertAll(
            () -> assertEquals(P, element.tag(), TAG_TYPE),
            () -> assertEquals(0, element.children().size(), NUM_CHILDREN),
            () -> assertEquals(expectedText, element.text(), "Text"),
            () -> assertEquals(1, element.attributes().asList().size(), NUM_ATTRIBUTES),
            () -> assertEquals("block mt-[5mm] mb-[2mm]",
                Objects.requireNonNull(element.attribute(CLASS)).getValue(), ATTRIBUTES)
        );
      }

      @Test
      void footerLink() {
        final var element = footer.create().child(1);
        assertAll(
            () -> assertEquals(A, element.tag(), TAG_TYPE),
            () -> assertEquals(0, element.children().size(), NUM_CHILDREN),
            () -> assertEquals(FOOTER_LINK_TEXT, element.text(), TEXT),
            () -> assertEquals(2, element.attributes().asList().size(), NUM_ATTRIBUTES),
            () -> assertEquals(FOOTER_LINK_URL,
                Objects.requireNonNull(element.attribute(HREF)).getValue(), ATTRIBUTES)
        );
      }
    }
  }

}