package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.text;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;
import java.io.IOException;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PdfTextGeneratorTest {

  private static final String VALUE = "This is an example text";

  private static final PdfTextGenerator pdfTextGenerator = new PdfTextGenerator();

  PDAcroForm pdAcroForm;
  PDDocument document;


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
    pdfTextGenerator.addText(document, VALUE, 10, null, Color.gray);

    final var pdfText = getTextForDocument();
    assertTrue(pdfText.contains(VALUE),
        String.format(
            "Expect to find text '%s' in pdf but pdf text does not contain it '%s'",
            VALUE, pdfText)
    );
  }

  @Test
  void shouldAddTextWithOffsetToDocument() throws IOException {
    pdfTextGenerator.addText(document, VALUE, 10, null, Color.gray, 10F, 10F, false);

    final var pdfText = getTextForDocument();
    assertTrue(pdfText.contains(VALUE),
        String.format(
            "Expect to find text '%s' in pdf but pdf text does not contain it '%s'",
            VALUE, pdfText)
    );
  }

  private String getTextForDocument() throws IOException {
    final var textStripper = new PDFTextStripper();
    return textStripper.getText(document);
  }
}