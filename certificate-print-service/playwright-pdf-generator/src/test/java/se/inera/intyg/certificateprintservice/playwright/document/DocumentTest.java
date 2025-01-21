package se.inera.intyg.certificateprintservice.playwright.document;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@ExtendWith(MockitoExtension.class)
class DocumentTest {

  private static final String HEADER = "header";
  private static final String CONTENT = "content";
  private static final String FOOTER = "footer";
  private static final String LEFT_MARGIN_INFO = "leftMarginInfo";
  private static final String RIGHT_MARGIN_INFO = "rightMarginInfo";
  private static final String WATERMARK = "watermark";
  private static final String HEADER_SPACE = "header-space";
  private static final String TITLE = "title";
  private static final String SCRIPT = "script";
  private static final String TAILWIND_CSS_SCRIPT = "tailwindCssScript";
  private static final String CLASS = "class";
  private static final String SRC = "src";
  private static final String CERTIFICATE_NAME = "certificateName";
  private static final String CERTIFICATE_TYPE = "certificateType";
  private static final String CERTIFICATE_VERSION = "certificateVersion";
  private static final int HEADER_HEIGHT = 77;

  @Mock
  private Page page;
  @Mock
  private Locator locator;

  private final Document.DocumentBuilder documentBuilder = Document.builder()
      .header(Header.builder()
          .certificateName(CERTIFICATE_NAME)
          .certificateType(CERTIFICATE_TYPE)
          .certificateType(CERTIFICATE_TYPE)
          .personId("19121212-1212")
          .recipientLogo("reciptientLogo".getBytes())
          .build())
      .footer(Footer.builder().build())
      .content(Content.builder()
          .issuerName("issuerName")
          .issuingUnit("issuingUnit")
          .issuingUnitInfo(List.of("address", "zipCode", "city"))
          .certificateName(CERTIFICATE_NAME)
          .description("description")
          .categories(Collections.emptyList())
          .isDraft(true)
          .build())
      .leftMarginInfo(LeftMarginInfo.builder().build())
      .rightMarginInfo(RightMarginInfo.builder().build())
      .watermark(Watermark.builder().build())
      .certificateName(CERTIFICATE_NAME)
      .certificateType(CERTIFICATE_TYPE)
      .certificateVersion(CERTIFICATE_VERSION)
      .tailWindScript(TAILWIND_CSS_SCRIPT);

  private static final Resource template = new ClassPathResource("testCertificateTemplate.html");
  private static org.jsoup.nodes.Document jsoupDocument;
  private Document document;

  @BeforeAll
  static void init() throws IOException {
    jsoupDocument = Jsoup.parse(template.getInputStream(), StandardCharsets.UTF_8.name(),
        "", Parser.xmlParser());
  }

  @BeforeEach
  void setup() {
    when(page.getByTitle(HEADER)).thenReturn(locator);
    when(locator.evaluate("node => node.offsetHeight")).thenReturn(HEADER_HEIGHT);
  }

  @Nested
  class Draft {

    @BeforeEach
    void setupDocument() {
      document = documentBuilder.isDraft(true).build();
    }

    @Test
    void shouldSetHeader() throws IOException {
      final var elementBefore = jsoupDocument.getElementById(HEADER);
      final var elementAfter = document.build(template, page).getElementById(HEADER);
      assertEquals(0, requireNonNull(elementBefore).children().size());
      assertNotEquals(0, requireNonNull(elementAfter).children().size());
    }

    @Test
    void shouldSetContent() throws IOException {
      final var elementBefore = jsoupDocument.getElementById(CONTENT);
      final var elementAfter = document.build(template, page).getElementById(CONTENT);
      assertEquals(0, requireNonNull(elementBefore).children().size());
      assertNotEquals(0, requireNonNull(elementAfter).children().size());
    }

    @Test
    void shouldSetFooter() throws IOException {
      final var elementBefore = jsoupDocument.getElementById(FOOTER);
      final var elementAfter = document.build(template, page).getElementById(FOOTER);
      assertEquals(0, requireNonNull(elementBefore).children().size());
      assertNotEquals(0, requireNonNull(elementAfter).children().size());
    }

    @Test
    void shouldSetLeftMarginInfo() throws IOException {
      final var elementBefore = jsoupDocument.getElementById(LEFT_MARGIN_INFO);
      final var elementAfter = document.build(template, page).getElementById(LEFT_MARGIN_INFO);
      assertEquals(0, requireNonNull(elementBefore).children().size());
      assertNotEquals(0, requireNonNull(elementAfter).children().size());
    }

    @Test
    void shouldNotSetRightMarginInfo() throws IOException {
      final var elementBefore = jsoupDocument.getElementById(RIGHT_MARGIN_INFO);
      final var elementAfter = document.build(template, page).getElementById(RIGHT_MARGIN_INFO);
      assertEquals(0, requireNonNull(elementBefore).children().size());
      assertEquals(0, requireNonNull(elementAfter).children().size());
    }

    @Test
    void shouldSetWatermark() throws IOException {
      final var elementBefore = jsoupDocument.getElementById(WATERMARK);
      final var elementAfter = document.build(template, page).getElementById(WATERMARK);
      assertEquals(0, requireNonNull(elementBefore).children().size());
      assertNotEquals(0, requireNonNull(elementAfter).children().size());
    }

    @Test
    void shouldSetTitle() throws IOException {
      final var elementBefore = jsoupDocument.getElementById(TITLE);
      final var elementAfter = document.build(template, page).getElementById(TITLE);
      assertEquals("", requireNonNull(elementBefore).text());
      assertEquals("%s (%s v%s)".formatted(CERTIFICATE_NAME, CERTIFICATE_TYPE, CERTIFICATE_VERSION),
          requireNonNull(elementAfter).text());
    }

    @Test
    void shouldSetTailwindScript() throws IOException {
      final var elementBefore = jsoupDocument.getElementById(SCRIPT);
      final var elementAfter = document.build(template, page).getElementById(SCRIPT);
      assertNull(requireNonNull(elementBefore).attribute(SRC));
      assertEquals("data:text/javascript;base64, %s".formatted(TAILWIND_CSS_SCRIPT), requireNonNull(
          requireNonNull(elementAfter).attribute(SRC)).getValue());
    }

    @Test
    void shouldSetHeaderHeight() throws IOException {
      final var elementBefore = jsoupDocument.getElementById(HEADER_SPACE);
      final var elementAfter = document.build(template, page).getElementById(HEADER_SPACE);
      assertNull(requireNonNull(elementBefore).attribute(CLASS));
      assertEquals("h-[%spx]".formatted(HEADER_HEIGHT), requireNonNull(
          requireNonNull(elementAfter).attribute(CLASS)).getValue());
    }
  }

  @Nested
  class Signed {

    @BeforeEach
    void setupDocument() {
      document = documentBuilder.isDraft(false).build();
    }

    @Test
    void shouldSetHeader() throws IOException {
      final var elementBefore = jsoupDocument.getElementById(HEADER);
      final var elementAfter = document.build(template, page).getElementById(HEADER);
      assertEquals(0, requireNonNull(elementBefore).children().size());
      assertNotEquals(0, requireNonNull(elementAfter).children().size());
    }

    @Test
    void shouldSetContent() throws IOException {
      final var elementBefore = jsoupDocument.getElementById(CONTENT);
      final var elementAfter = document.build(template, page).getElementById(CONTENT);
      assertEquals(0, requireNonNull(elementBefore).children().size());
      assertNotEquals(0, requireNonNull(elementAfter).children().size());
    }

    @Test
    void shouldSetFooter() throws IOException {
      final var elementBefore = jsoupDocument.getElementById(FOOTER);
      final var elementAfter = document.build(template, page).getElementById(FOOTER);
      assertEquals(0, requireNonNull(elementBefore).children().size());
      assertNotEquals(0, requireNonNull(elementAfter).children().size());
    }

    @Test
    void shouldSetLeftMarginInfo() throws IOException {
      final var elementBefore = jsoupDocument.getElementById(LEFT_MARGIN_INFO);
      final var elementAfter = document.build(template, page).getElementById(LEFT_MARGIN_INFO);
      assertEquals(0, requireNonNull(elementBefore).children().size());
      assertNotEquals(0, requireNonNull(elementAfter).children().size());
    }

    @Test
    void shouldSetRightMarginInfo() throws IOException {
      final var elementBefore = jsoupDocument.getElementById(RIGHT_MARGIN_INFO);
      final var elementAfter = document.build(template, page).getElementById(RIGHT_MARGIN_INFO);
      assertEquals(0, requireNonNull(elementBefore).children().size());
      assertNotEquals(0, requireNonNull(elementAfter).children().size());
    }

    @Test
    void shouldNotSetWatermark() throws IOException {
      final var elementBefore = jsoupDocument.getElementById(WATERMARK);
      final var elementAfter = document.build(template, page).getElementById(WATERMARK);
      assertEquals(0, requireNonNull(elementBefore).children().size());
      assertEquals(0, requireNonNull(elementAfter).children().size());
    }

    @Test
    void shouldSetTitle() throws IOException {
      final var elementBefore = jsoupDocument.getElementById(TITLE);
      final var elementAfter = document.build(template, page).getElementById(TITLE);
      assertEquals("", requireNonNull(elementBefore).text());
      assertEquals("%s (%s v%s)".formatted(CERTIFICATE_NAME, CERTIFICATE_TYPE, CERTIFICATE_VERSION),
          requireNonNull(elementAfter).text());
    }

    @Test
    void shouldSetTailwindScript() throws IOException {
      final var elementBefore = jsoupDocument.getElementById(SCRIPT);
      final var elementAfter = document.build(template, page).getElementById(SCRIPT);
      assertNull(requireNonNull(elementBefore).attribute(SRC));
      assertEquals("data:text/javascript;base64, %s".formatted(TAILWIND_CSS_SCRIPT), requireNonNull(
          requireNonNull(elementAfter).attribute(SRC)).getValue());
    }

    @Test
    void shouldSetHeaderHeight() throws IOException {
      final var elementBefore = jsoupDocument.getElementById(HEADER_SPACE);
      final var elementAfter = document.build(template, page).getElementById(HEADER_SPACE);
      assertNull(requireNonNull(elementBefore).attribute(CLASS));
      assertEquals("h-[%spx]".formatted(HEADER_HEIGHT), requireNonNull(
          requireNonNull(elementAfter).attribute(CLASS)).getValue());
    }
  }

}
