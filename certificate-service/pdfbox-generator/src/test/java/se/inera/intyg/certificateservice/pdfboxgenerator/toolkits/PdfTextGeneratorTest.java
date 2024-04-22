package se.inera.intyg.certificateservice.pdfboxgenerator.toolkits;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;
import java.io.IOException;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.pdfboxgenerator.text.PdfTextGenerator;

class PdfTextGeneratorTest {

    private static final String VALUE = "This is an example text";

    PDAcroForm pdAcroForm;
    PDDocument document;

    private static final PdfTextGenerator PDF_TEXT_GENERATOR = new PdfTextGenerator();

    @BeforeEach
    void setup() throws IOException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        final var inputStream = classloader.getResourceAsStream("fk7211_v1.pdf");
        document = Loader.loadPDF(inputStream.readAllBytes());
        final var documentCatalog = document.getDocumentCatalog();
        pdAcroForm = documentCatalog.getAcroForm();
    }

    @Test
    void shouldAddTextToDocument() throws IOException {
        PDF_TEXT_GENERATOR.addText(document, VALUE, 10, null, Color.gray);

        final var pdfText = getTextForDocument();
        assertTrue(pdfText.contains(VALUE),
            String.format(
                "Expect to find text '%s' in pdf but pdf text does not contain it '%s'",
                VALUE, pdfText)
        );
    }

    @Test
    void shouldAddTextWithOffsetToDocument() throws IOException {
        PDF_TEXT_GENERATOR.addText(document, VALUE, 10, null, Color.gray, 10F, 10F, false);

        final var pdfText = getTextForDocument();
        assertTrue(pdfText.contains(VALUE),
            String.format(
                "Expect to find text '%s' in pdf but pdf text does not contain it '%s'",
                VALUE, pdfText)
        );
    }

    @Test
    void shouldAddSignatureToDocument() throws IOException {
        final var expected = "Detta är en utskrift av ett elektroniskt intyg. Intyget har signerats elektroniskt av intygsutfärdaren.";
        PDF_TEXT_GENERATOR.addDigitalSignatureText(document, pdAcroForm);

        final var pdfText = getTextForDocument();
        assertTrue(pdfText.contains(
                expected),
            String.format(
                "Expect to find text '%s' in pdf but pdf text does not contain it '%s'",
                expected, pdfText)
        );
    }

    private String getTextForDocument() throws IOException {
        final var textStripper = new PDFTextStripper();
        return textStripper.getText(document);
    }
}