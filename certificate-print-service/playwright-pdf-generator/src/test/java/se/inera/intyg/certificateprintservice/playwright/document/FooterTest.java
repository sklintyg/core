package se.inera.intyg.certificateprintservice.playwright.document;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Objects;
import org.jsoup.parser.Tag;
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
          () -> assertEquals(Tag.valueOf("div"), element.tag(), "Tag type"),
          () -> assertEquals(0, element.attributes().asList().size(), "Attributes"),
          () -> assertEquals(2, element.children().size(), "Number of children")
      );
    }

    @Nested
    class WrapperElements {

      @Test
      void footerText() throws NullPointerException {
        final var expectedText = FOOTER_TEXT.formatted(APPLICATION_ORIGIN);
        final var element = footer.create().child(0);
        assertAll(
            () -> assertEquals(Tag.valueOf("p"), element.tag(), "Tag type"),
            () -> assertEquals(0, element.children().size(), "Number of children"),
            () -> assertEquals(expectedText, element.text(), "Text"),
            () -> assertEquals(1, element.attributes().asList().size(), "Number of attributes"),
            () -> assertEquals("display: block;\nmargin-top: 5mm;\nmargin-bottom: 2mm;\n",
                Objects.requireNonNull(element.attribute("style")).getValue(), "Attributes")
        );
      }

      @Test
      void footerLink() {
        final var element = footer.create().child(1);
        assertAll(
            () -> assertEquals(Tag.valueOf("a"), element.tag(), "Tag type"),
            () -> assertEquals(0, element.children().size(), "Number of children"),
            () -> assertEquals(FOOTER_LINK_TEXT, element.text(), "Text"),
            () -> assertEquals(1, element.attributes().asList().size(), "Number of attributes"),
            () -> assertEquals(FOOTER_LINK_URL,
                Objects.requireNonNull(element.attribute("href")).getValue(), "Attributes")
        );
      }
    }
  }

}