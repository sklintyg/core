package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.text;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.documentinterchange.logicalstructure.PDStructureElement;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.util.Matrix;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PdfTextGeneratorTest {

  private static final String VALUE = "VALUE";

  private static final PdfTextGenerator pdfTextGenerator = new PdfTextGenerator();

  PDAcroForm pdAcroForm;
  PDDocument document;

  @Nested
  class FK7210 {

    @BeforeEach
    void setup() throws IOException {
      ClassLoader classloader = getClass().getClassLoader();
      final var inputStream = classloader.getResourceAsStream("fk7210/pdf/fk7210_v1.pdf");
      document = Loader.loadPDF(inputStream.readAllBytes());
      final var documentCatalog = document.getDocumentCatalog();
      pdAcroForm = documentCatalog.getAcroForm();
    }

    @Test
    void shouldAddTopWatermarkToDocument() throws IOException {
      pdfTextGenerator.addTopWatermark(document, VALUE, Matrix.getTranslateInstance(40, 665), 10,
          100, false);

      final var pdfText = getTextForDocument();
      assertTrue(pdfText.contains(VALUE),
          String.format(
              "Expect to find text '%s' in pdf but pdf text does not contain it '%s'",
              VALUE, pdfText)
      );
    }

    @Test
    void shouldAddNewDivForTopWatermarkText() throws IOException {
      final var pageTag = getMainTag();
      final var divsInPageBefore = pageTag.getKids().size();
      pdfTextGenerator.addTopWatermark(document, VALUE, Matrix.getTranslateInstance(40, 665), 10,
          100, false);

      assertEquals(divsInPageBefore + 1, pageTag.getKids().size());
    }

    @Test
    void shouldNotAddNewDivForTopWatermarkTextIfBooleanIsTrue() throws IOException {
      final var pageTag = getMainTag();
      final var divsInPageBefore = pageTag.getKids().size();
      pdfTextGenerator.addTopWatermark(document, VALUE, Matrix.getTranslateInstance(40, 665), 10,
          100, true);

      assertEquals(divsInPageBefore, pageTag.getKids().size());
    }

    @Test
    void shouldAddTopWatermarkTagInFirstOriginalDiv() throws IOException {
      final var firstDiv = getFirstDiv();
      final var nbrOfKidsBeforeAddedText = firstDiv.getKids().size();
      pdfTextGenerator.addTopWatermark(document, VALUE, Matrix.getTranslateInstance(40, 665), 8,
          100, true);
      final var addedTag = (PDStructureElement) firstDiv.getKids()
          .get(firstDiv.getKids().size() - 1);

      assertAll(
          () -> assertEquals(nbrOfKidsBeforeAddedText + 1, firstDiv.getKids().size()),
          () -> assertEquals(VALUE, addedTag.getActualText())
      );
    }

    @Test
    void shouldAddTopWatermarkTagInNewlyCreatedDiv() throws IOException {
      pdfTextGenerator.addTopWatermark(document, VALUE, Matrix.getTranslateInstance(40, 665), 8,
          100, false);
      final var firstDiv = getFirstDiv();
      final var addedTag = (PDStructureElement) firstDiv.getKids()
          .get(firstDiv.getKids().size() - 1);

      assertAll(
          () -> assertEquals(1, firstDiv.getKids().size()),
          () -> assertEquals(VALUE, addedTag.getActualText())
      );
    }

    @Test
    void shouldAddMarginText() throws IOException {
      pdfTextGenerator.addMarginText(document, VALUE, 100, 0);

      final var pdfText = getTextForDocument();
      assertAll(
          () -> assertTrue(pdfText.contains("V")),
          () -> assertTrue(pdfText.contains("A")),
          () -> assertTrue(pdfText.contains("L")),
          () -> assertTrue(pdfText.contains("U")),
          () -> assertTrue(pdfText.contains("E"))
      );
    }

    @Test
    void shouldAddMarginTagInLastDiv() throws IOException {
      final var lastDiv = getLastDivInTree();
      final var kidsOfLastDivBeforeAddedText = lastDiv.getKids().size();

      pdfTextGenerator.addMarginText(document, VALUE, 100, 0);
      final var addedTag = (PDStructureElement) lastDiv.getKids()
          .get(lastDiv.getKids().size() - 1);

      assertEquals(kidsOfLastDivBeforeAddedText + 1, lastDiv.getKids().size());
      assertEquals(VALUE, addedTag.getActualText());
    }

    @Test
    void shouldThrowErrorIfTryingToPlaceSignatureInIndexThatDoesntExist() {
      assertThrows(
          IllegalStateException.class,
          () -> pdfTextGenerator.addDigitalSignatureText(document, VALUE, 30F, 30F, 100, 500, 0)
      );
    }

    @Test
    void shouldAddSignatureInCorrectDiv() throws IOException {
      pdfTextGenerator.addDigitalSignatureText(document, VALUE, 30F, 30F, 100, 18, 0);

      final var pageTag = getMainTag();
      final var questionDiv = (PDStructureElement) pageTag.getKids().get(0);
      final var signatureDiv = (PDStructureElement) questionDiv.getKids().get(18);
      assertEquals(2, signatureDiv.getKids().size());
    }

    @Test
    void shouldAddSignatureTextInCorrectDiv() throws IOException {
      pdfTextGenerator.addDigitalSignatureText(document, VALUE, 30F, 30F, 100, 18, 0);

      final var pageTag = getMainTag();
      final var questionDiv = (PDStructureElement) pageTag.getKids().get(0);
      final var signatureDiv = (PDStructureElement) questionDiv.getKids().get(18);
      final var signatureValue = (PDStructureElement) signatureDiv.getKids().get(1);
      assertEquals(VALUE, signatureValue.getActualText());
    }

    @Test
    void shouldAddWatermark() throws IOException {
      pdfTextGenerator.addWatermark(document, "UTKAST", 100);

      final var pdfText = getTextForDocument();
      assertAll(
          () -> assertTrue(pdfText.contains("U")),
          () -> assertTrue(pdfText.contains("TK")),
          () -> assertTrue(pdfText.contains("AS")),
          () -> assertTrue(pdfText.contains("T"))
      );
    }

    @Test
    void shouldAddWatermarkTag() throws IOException {
      pdfTextGenerator.addWatermark(document, "UTKAST", 100);
      final var firstDiv = getFirstDiv();
      final var addedTag = (PDStructureElement) firstDiv.getKids()
          .get(firstDiv.getKids().size() - 1);

      assertEquals("Detta är ett utkast", addedTag.getActualText());
    }
  }

  @Nested
  class FK7472 {

    @BeforeEach
    void setup() throws IOException {
      ClassLoader classloader = getClass().getClassLoader();
      final var inputStream = classloader.getResourceAsStream("fk7472/pdf/fk7472_v1.pdf");
      document = Loader.loadPDF(inputStream.readAllBytes());
      final var documentCatalog = document.getDocumentCatalog();
      pdAcroForm = documentCatalog.getAcroForm();
    }

    @Test
    void shouldAddTopWatermarkToDocument() throws IOException {
      pdfTextGenerator.addTopWatermark(document, VALUE, Matrix.getTranslateInstance(40, 665), 10,
          100, false);

      final var pdfText = getTextForDocument();
      assertTrue(pdfText.contains(VALUE),
          String.format(
              "Expect to find text '%s' in pdf but pdf text does not contain it '%s'",
              VALUE, pdfText)
      );
    }

    @Test
    void shouldAddNewDivForTopWatermarkText() throws IOException {
      final var pageTag = getMainTag();
      final var divsInPageBefore = pageTag.getKids().size();
      pdfTextGenerator.addTopWatermark(document, VALUE, Matrix.getTranslateInstance(40, 665), 10,
          100, false);

      assertEquals(divsInPageBefore + 1, pageTag.getKids().size());
    }

    @Test
    void shouldNotAddNewDivForTopWatermarkTextIfBooleanIsTrue() throws IOException {
      final var pageTag = getMainTag();
      final var divsInPageBefore = pageTag.getKids().size();
      pdfTextGenerator.addTopWatermark(document, VALUE, Matrix.getTranslateInstance(40, 665), 10,
          100, true);

      assertEquals(divsInPageBefore, pageTag.getKids().size());
    }

    @Test
    void shouldAddTopWatermarkTagInFirstOriginalDiv() throws IOException {
      final var firstDiv = getFirstDiv();
      final var nbrOfKidsBeforeAddedText = firstDiv.getKids().size();
      pdfTextGenerator.addTopWatermark(document, VALUE, Matrix.getTranslateInstance(40, 665), 8,
          100, true);
      final var addedTag = (PDStructureElement) firstDiv.getKids()
          .get(firstDiv.getKids().size() - 1);

      assertAll(
          () -> assertEquals(nbrOfKidsBeforeAddedText + 1, firstDiv.getKids().size()),
          () -> assertEquals(VALUE, addedTag.getActualText())
      );
    }

    @Test
    void shouldAddTopWatermarkTagInNewlyCreatedDiv() throws IOException {
      pdfTextGenerator.addTopWatermark(document, VALUE, Matrix.getTranslateInstance(40, 665), 8,
          100, false);
      final var firstDiv = getFirstDiv();
      final var addedTag = (PDStructureElement) firstDiv.getKids()
          .get(firstDiv.getKids().size() - 1);

      assertAll(
          () -> assertEquals(1, firstDiv.getKids().size()),
          () -> assertEquals(VALUE, addedTag.getActualText())
      );
    }

    @Test
    void shouldAddMarginText() throws IOException {
      pdfTextGenerator.addMarginText(document, VALUE, 100, 0);

      final var pdfText = getTextForDocument();
      assertAll(
          () -> assertTrue(pdfText.contains("V")),
          () -> assertTrue(pdfText.contains("A")),
          () -> assertTrue(pdfText.contains("L")),
          () -> assertTrue(pdfText.contains("U")),
          () -> assertTrue(pdfText.contains("E"))
      );
    }

    @Test
    void shouldAddMarginTagInLastDiv() throws IOException {
      final var lastDiv = getLastDivInTree();
      final var kidsOfLastDivBeforeAddedText = lastDiv.getKids().size();

      pdfTextGenerator.addMarginText(document, VALUE, 100, 0);
      final var addedTag = (PDStructureElement) lastDiv.getKids()
          .get(lastDiv.getKids().size() - 1);

      assertEquals(kidsOfLastDivBeforeAddedText + 1, lastDiv.getKids().size());
      assertEquals(VALUE, addedTag.getActualText());
    }

    @Test
    void shouldThrowErrorIfTryingToPlaceSignatureInIndexThatDoesntExist() {
      assertThrows(
          IllegalStateException.class,
          () -> pdfTextGenerator.addDigitalSignatureText(document, VALUE, 30F, 30F, 100, 500, 0)
      );
    }

    @Test
    void shouldAddSignatureToDocument() throws IOException {
      pdfTextGenerator.addDigitalSignatureText(document, VALUE, 10F, 10F, 100, 34, 0);

      final var pdfText = getTextForDocument();
      assertTrue(pdfText.contains(VALUE),
          String.format(
              "Expect to find text '%s' in pdf but pdf text does not contain it '%s'",
              VALUE, pdfText)
      );
    }

    @Test
    void shouldAddSignatureInCorrectDiv() throws IOException {
      pdfTextGenerator.addDigitalSignatureText(document, VALUE, 30F, 30F, 100, 34, 0);

      final var pageTag = getMainTag();
      final var questionDiv = (PDStructureElement) pageTag.getKids().get(0);
      final var signatureDiv = (PDStructureElement) questionDiv.getKids().get(34);
      assertEquals(2, signatureDiv.getKids().size());
    }

    @Test
    void shouldAddSignatureTextInCorrectDiv() throws IOException {
      pdfTextGenerator.addDigitalSignatureText(document, VALUE, 30F, 30F, 100, 34, 0);

      final var pageTag = getMainTag();
      final var questionDiv = (PDStructureElement) pageTag.getKids().get(0);
      final var signatureDiv = (PDStructureElement) questionDiv.getKids().get(34);
      final var signatureValue = (PDStructureElement) signatureDiv.getKids().get(1);
      assertEquals(VALUE, signatureValue.getActualText());
    }

    @Test
    void shouldAddWatermark() throws IOException {
      pdfTextGenerator.addWatermark(document, "UTKAST", 100);

      final var pdfText = getTextForDocument();
      assertAll(
          () -> assertTrue(pdfText.contains("U")),
          () -> assertTrue(pdfText.contains("TK")),
          () -> assertTrue(pdfText.contains("AS")),
          () -> assertTrue(pdfText.contains("T"))
      );
    }

    @Test
    void shouldAddWatermarkTag() throws IOException {
      pdfTextGenerator.addWatermark(document, "UTKAST", 100);
      final var firstDiv = getFirstDiv();
      final var addedTag = (PDStructureElement) firstDiv.getKids()
          .get(firstDiv.getKids().size() - 1);

      assertEquals("Detta är ett utkast", addedTag.getActualText());
    }
  }

  private String getTextForDocument() throws IOException {
    final var textStripper = new PDFTextStripper();
    return textStripper.getText(document);
  }

  private PDStructureElement getFirstDiv() {
    return (PDStructureElement) getMainTag().getKids().get(0);
  }

  private PDStructureElement getMainTag() {
    final var documentTag = (PDStructureElement) document.getDocumentCatalog()
        .getStructureTreeRoot()
        .getKids().get(0);
    return (PDStructureElement) documentTag.getKids().get(0);
  }

  private PDStructureElement getLastDivInTree() {
    final var section = getMainTag();
    return (PDStructureElement) section.getKids().get(section.getKids().size() - 1);
  }

}
