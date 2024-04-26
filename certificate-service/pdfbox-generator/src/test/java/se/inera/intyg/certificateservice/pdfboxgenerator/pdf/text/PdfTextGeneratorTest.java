package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.text;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.documentinterchange.logicalstructure.PDStructureElement;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PdfTextGeneratorTest {

  private static final String VALUE = "This is an example text";

  private static final PdfTextGenerator pdfTextGenerator = new PdfTextGenerator();

  PDAcroForm pdAcroForm;
  PDDocument document;

  /*Nested
  class FK7211 {

    @BeforeEach
    void setup() throws IOException {
      ClassLoader classloader = getClass().getClassLoader();
      final var inputStream = classloader.getResourceAsStream("fk7211/pdf/fk7211_v1.pdf");
      document = Loader.loadPDF(inputStream.readAllBytes());
      final var documentCatalog = document.getDocumentCatalog();
      pdAcroForm = documentCatalog.getAcroForm();
    }

   @Test
    void shouldAddTextToDocument() throws IOException {
      pdfTextGenerator.addText(document, VALUE, 10, null, Color.gray, 100);

      final var pdfText = getTextForDocument();
      assertTrue(pdfText.contains(VALUE),
          String.format(
              "Expect to find text '%s' in pdf but pdf text does not contain it '%s'",
              VALUE, pdfText)
      );
    }

    @Test
    void shouldAddTextTag() throws IOException {
      final var root = (PDStructureElement) document.getDocumentCatalog().getStructureTreeRoot()
          .getKids().get(0);
      final var section = (PDStructureElement) root.getKids().get(0);
      final var lastDiv = (PDStructureElement) section.getKids().get(1);
      final var kidsOfLastDivBeforeAddedText = lastDiv.getKids().size();

      pdfTextGenerator.addText(document, VALUE, 10, null, Color.gray, 100);
      final var addedTag = (PDStructureElement) lastDiv.getKids().get(lastDiv.getKids().size() - 1);

      assertEquals(kidsOfLastDivBeforeAddedText + 1, lastDiv.getKids().size());
      assertEquals(VALUE, addedTag.getActualText());
    }

    @Test
    void shouldAddTextWithOffsetToDocument() throws IOException {
      pdfTextGenerator.addText(document, VALUE, 10, null, Color.gray, 10F, 10F, false, 100);

      final var pdfText = getTextForDocument();
      assertTrue(pdfText.contains(VALUE),
          String.format(
              "Expect to find text '%s' in pdf but pdf text does not contain it '%s'",
              VALUE, pdfText)
      );
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
      final var root = (PDStructureElement) document.getDocumentCatalog().getStructureTreeRoot()
          .getKids().get(0);
      final var section = (PDStructureElement) root.getKids().get(0);
      final var lastDiv = (PDStructureElement) section.getKids().get(1);
      final var kidsOfLastDivBeforeAddedText = lastDiv.getKids().size();

      pdfTextGenerator.addWatermark(document, "UTKAST", 100);
      final var addedTag = (PDStructureElement) lastDiv.getKids().get(lastDiv.getKids().size() - 1);

      assertEquals(kidsOfLastDivBeforeAddedText + 1, lastDiv.getKids().size());
      assertEquals("UTKAST", addedTag.getActualText());
    }

    private String getTextForDocument() throws IOException {
      final var textStripper = new PDFTextStripper();
      return textStripper.getText(document);
    }
  }*/

  @Nested
  class FK7443 {

    @BeforeEach
    void setup() throws IOException {
      ClassLoader classloader = getClass().getClassLoader();
      final var inputStream = classloader.getResourceAsStream("fk7443/pdf/fk7443_v1.pdf");
      document = Loader.loadPDF(inputStream.readAllBytes());
      final var documentCatalog = document.getDocumentCatalog();
      pdAcroForm = documentCatalog.getAcroForm();
    }

    @Test
    void shouldAddTopWatermarkToDocument() throws IOException {
      pdfTextGenerator.addTopWatermark(document, VALUE, 10, 100);

      final var pdfText = getTextForDocument();
      assertTrue(pdfText.contains(VALUE),
          String.format(
              "Expect to find text '%s' in pdf but pdf text does not contain it '%s'",
              VALUE, pdfText)
      );
    }

    @Test
    void shouldAddNewDivForTopWatermarkText() throws IOException {
      pdfTextGenerator.addTopWatermark(document, VALUE, 10, 100);

      final var pdfText = getTextForDocument();

      final var documentTag = (PDStructureElement) document.getDocumentCatalog()
          .getStructureTreeRoot()
          .getKids().get(0);
      final var pageTag = (PDStructureElement) documentTag.getKids().get(0);
      assertEquals(3, pageTag.getKids().size());
    }

   /* @Test
    void shouldAddTextTag() throws IOException {
      final var root = (PDStructureElement) document.getDocumentCatalog().getStructureTreeRoot()
          .getKids().get(0);
      final var section = (PDStructureElement) root.getKids().get(0);
      final var lastDiv = (PDStructureElement) section.getKids().get(1);
      final var kidsOfLastDivBeforeAddedText = lastDiv.getKids().size();

      pdfTextGenerator.addText(document, VALUE, 10, null, Color.gray, 100);
      final var addedTag = (PDStructureElement) lastDiv.getKids()
          .get(lastDiv.getKids().size() - 1);

      assertEquals(kidsOfLastDivBeforeAddedText + 1, lastDiv.getKids().size());
      assertEquals(VALUE, addedTag.getActualText());
    }

    @Test
    void shouldAddTextWithOffsetToDocument() throws IOException {
      pdfTextGenerator.addText(document, VALUE, 10, null, Color.gray, 10F, 10F, false, 100);

      final var pdfText = getTextForDocument();
      assertTrue(pdfText.contains(VALUE),
          String.format(
              "Expect to find text '%s' in pdf but pdf text does not contain it '%s'",
              VALUE, pdfText)
      );
    }*/

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
      final var root = (PDStructureElement) document.getDocumentCatalog().getStructureTreeRoot()
          .getKids().get(0);
      final var section = (PDStructureElement) root.getKids().get(0);
      final var lastDiv = (PDStructureElement) section.getKids().get(1);
      final var kidsOfLastDivBeforeAddedText = lastDiv.getKids().size();

      pdfTextGenerator.addWatermark(document, "UTKAST", 100);
      final var addedTag = (PDStructureElement) lastDiv.getKids()
          .get(lastDiv.getKids().size() - 1);

      assertEquals(kidsOfLastDivBeforeAddedText + 1, lastDiv.getKids().size());
      assertEquals("UTKAST", addedTag.getActualText());
    }

    private String getTextForDocument() throws IOException {
      final var textStripper = new PDFTextStripper();
      return textStripper.getText(document);
    }
  }
}