package se.inera.intyg.certificateservice.pdfboxgenerator.pdf;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.io.IOException;
import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.OverflowPageIndex;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.TemplatePdfSpecification;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.factory.PdfOverflowPageFactory;

@ExtendWith(MockitoExtension.class)
class PdfOverflowPageFactoryTest {

  @Mock
  private CertificatePdfContext context;
  @Mock
  private TemplatePdfSpecification templatePdfSpecification;

  private PdfOverflowPageFactory factory;
  private PDDocument document;

  @BeforeEach
  void setUp() {
    factory = new PdfOverflowPageFactory();
    document = new PDDocument();
  }

  @AfterEach
  void tearDown() throws IOException {
    if (document != null) {
      document.close();
    }
  }

  @Nested
  class CreateOverflowPage {

    @Test
    void shouldCreateOverflowPage() {
      final var templatePage = new PDPage();
      document.addPage(templatePage);

      final var overflowPageIndex = new OverflowPageIndex(0);

      when(context.getDocument()).thenReturn(document);
      when(context.getTemplatePdfSpecification()).thenReturn(templatePdfSpecification);
      when(templatePdfSpecification.overFlowPageIndex()).thenReturn(overflowPageIndex);

      final var overflowPage = factory.create(context);

      assertNotNull(overflowPage);
    }

    @Test
    void shouldCreateOverflowPageWithSameMediaBox() {
      final var templatePage = new PDPage(PDRectangle.A4);
      document.addPage(templatePage);

      final var overflowPageIndex = new OverflowPageIndex(0);

      when(context.getDocument()).thenReturn(document);
      when(context.getTemplatePdfSpecification()).thenReturn(templatePdfSpecification);
      when(templatePdfSpecification.overFlowPageIndex()).thenReturn(overflowPageIndex);

      final var overflowPage = factory.create(context);

      final var expectedMediaBox = templatePage.getMediaBox();
      final var actualMediaBox = overflowPage.getMediaBox();

      assertAll(
          () -> assertEquals(expectedMediaBox.getLowerLeftX(), actualMediaBox.getLowerLeftX()),
          () -> assertEquals(expectedMediaBox.getLowerLeftY(), actualMediaBox.getLowerLeftY()),
          () -> assertEquals(expectedMediaBox.getUpperRightX(), actualMediaBox.getUpperRightX()),
          () -> assertEquals(expectedMediaBox.getUpperRightY(), actualMediaBox.getUpperRightY())
      );
    }

    @Test
    void shouldCreateOverflowPageWithoutAnnotations() throws IOException {
      final var templatePage = new PDPage();
      document.addPage(templatePage);

      final var overflowPageIndex = new OverflowPageIndex(0);

      when(context.getDocument()).thenReturn(document);
      when(context.getTemplatePdfSpecification()).thenReturn(templatePdfSpecification);
      when(templatePdfSpecification.overFlowPageIndex()).thenReturn(overflowPageIndex);

      final var overflowPage = factory.create(context);

      assertTrue(overflowPage.getAnnotations().isEmpty(),
          "Overflow page should not have annotations");
    }

    @Test
    void shouldCopyPageDictionaryFromTemplatePage() {
      final var templatePage = new PDPage();
      final var templateDict = templatePage.getCOSObject();
      templateDict.setString(COSName.TITLE, "Test Title");
      document.addPage(templatePage);

      final var overflowPageIndex = new OverflowPageIndex(0);

      when(context.getDocument()).thenReturn(document);
      when(context.getTemplatePdfSpecification()).thenReturn(templatePdfSpecification);
      when(templatePdfSpecification.overFlowPageIndex()).thenReturn(overflowPageIndex);

      final var overflowPage = factory.create(context);

      assertEquals("Test Title", overflowPage.getCOSObject().getString(COSName.TITLE));
    }

    @Test
    void shouldUseCorrectPageFromIndex() {
      final var page0 = new PDPage(PDRectangle.A4);
      final var page1 = new PDPage(PDRectangle.A3);
      final var page2 = new PDPage(PDRectangle.A5);

      document.addPage(page0);
      document.addPage(page1);
      document.addPage(page2);

      final var overflowPageIndex = new OverflowPageIndex(1);

      when(context.getDocument()).thenReturn(document);
      when(context.getTemplatePdfSpecification()).thenReturn(templatePdfSpecification);
      when(templatePdfSpecification.overFlowPageIndex()).thenReturn(overflowPageIndex);

      final var overflowPage = factory.create(context);

      final var expectedMediaBox = page1.getMediaBox();
      final var actualMediaBox = overflowPage.getMediaBox();

      assertAll(
          () -> assertEquals(expectedMediaBox.getLowerLeftX(), actualMediaBox.getLowerLeftX()),
          () -> assertEquals(expectedMediaBox.getLowerLeftY(), actualMediaBox.getLowerLeftY()),
          () -> assertEquals(expectedMediaBox.getUpperRightX(), actualMediaBox.getUpperRightX()),
          () -> assertEquals(expectedMediaBox.getUpperRightY(), actualMediaBox.getUpperRightY())
      );
    }

    @Test
    void shouldCreateIndependentPageCopy() {
      final var templatePage = new PDPage(PDRectangle.A4);
      final var templateDict = templatePage.getCOSObject();
      templateDict.setItem(COSName.ANNOTS, new COSDictionary());
      document.addPage(templatePage);

      final var overflowPageIndex = new OverflowPageIndex(0);

      when(context.getDocument()).thenReturn(document);
      when(context.getTemplatePdfSpecification()).thenReturn(templatePdfSpecification);
      when(templatePdfSpecification.overFlowPageIndex()).thenReturn(overflowPageIndex);

      final var overflowPage = factory.create(context);

      assertAll(
          () -> assertTrue(overflowPage.getAnnotations().isEmpty(),
              "Annotations should be removed in overflow page"),
          () -> assertNotNull(templatePage.getCOSObject().getItem(COSName.ANNOTS),
              "Template page should still have annotations")
      );
    }
  }
}