package se.inera.intyg.certificateprintservice.playwright;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static se.inera.intyg.certificateprintservice.pdfgenerator.api.Certificate.builder;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Page.PdfOptions;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.util.ReflectionTestUtils;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.Category;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.Certificate;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.Metadata;
import se.inera.intyg.certificateprintservice.playwright.browserpool.BrowserPool;
import se.inera.intyg.certificateprintservice.playwright.browserpool.PlaywrightBrowser;
import se.inera.intyg.certificateprintservice.playwright.converters.ContentConverter;
import se.inera.intyg.certificateprintservice.playwright.converters.FooterConverter;
import se.inera.intyg.certificateprintservice.playwright.converters.HeaderConverter;
import se.inera.intyg.certificateprintservice.playwright.converters.LeftMarginInfoConverter;
import se.inera.intyg.certificateprintservice.playwright.converters.RightMarginInfoConverter;

@ExtendWith(MockitoExtension.class)
class CertificatePrintGeneratorTest {

  @Mock
  private BrowserPool browserPool;
  @Mock
  private PlaywrightBrowser playwrightBrowser;
  @Mock
  private BrowserContext browserContext;
  @Mock
  private Page page;
  @Mock
  private Locator locator;
  @Spy
  private ContentConverter contentConverter;
  @Spy
  private FooterConverter footerConverter;
  @Spy
  private HeaderConverter headerConverter;
  @Spy
  private LeftMarginInfoConverter leftMarginInfoConverter;
  @Spy
  private RightMarginInfoConverter rightMarginInfoConverter;

  @InjectMocks
  private CertificatePrintGenerator certificatePrintGenerator;

  final Certificate certificate = builder()
      .metadata(Metadata.builder()
          .recipientLogo("RECIPIENT_LOGO".getBytes())
          .personId("PERSON_ID")
          .name("CERTIFICATE_NAME")
          .typeId("CERTIFICATE_TYPE")
          .version("CERTIFICATE_VERSION")
          .recipientName("RECIPIENT_NAME")
          .issuerName("ISSUER_NAME")
          .issuingUnit("ISSUING_UNIT")
          .issuingUnitInfo(List.of("ADDRESS", "ZIP_CODE", "CITY"))
          .name("CERTIFICATE_NAME")
          .description("DESCRIPTION")
          .build())
      .categories(List.of(
          Category.builder()
              .id("CATEGORY_ID")
              .name("CATEGORY_NAME")
              .questions(Collections.emptyList())
              .build()))
      .build();

  private static final Resource template = new ClassPathResource("testCertificateTemplate.html");
  private static final Resource tailwind = new ClassPathResource("testTailwindScript.js");
  private static final byte[] PDF = "pdf".getBytes();

  @Nested
  class Pdf {

    @BeforeEach
    void setup() throws Exception {
      ReflectionTestUtils.setField(certificatePrintGenerator, "template", template);
      when(browserPool.borrowObject()).thenReturn(playwrightBrowser);
      when(playwrightBrowser.getBrowserContext()).thenReturn(browserContext);
      when(browserContext.newPage()).thenReturn(page);
      when(page.getByTitle("header")).thenReturn(locator);
      when(locator.evaluate("node => node.offsetHeight")).thenReturn(77);
    }

    @Test
    void shouldReturnPdfAsBytes() {
      when(page.pdf(any(PdfOptions.class))).thenReturn(PDF);
      final var pdf = certificatePrintGenerator.generate(certificate);
      verify(page, times(2)).setContent(anyString());
      verify(page).pdf(any(PdfOptions.class));
      assertEquals(PDF, pdf);
    }

    @Test
    void shouldThrowIllegalStateException() {
      when(page.pdf(any(PdfOptions.class))).thenThrow(IllegalStateException.class);
      assertThrows(IllegalStateException.class,
          () -> certificatePrintGenerator.generate(certificate));
    }
  }

  @Test
  void shouldProcessTailwindScriptSuccessfully() {
    ReflectionTestUtils.setField(certificatePrintGenerator, "tailwindScript", tailwind);
    assertDoesNotThrow(() -> certificatePrintGenerator.afterPropertiesSet());
  }

}
