package se.inera.intyg.certificateservice.pdfboxgenerator.toolkits;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.pdfboxgenerator.value.PdfValueGenerator;

class PdfValueGeneratorTest {

    private static final String FIELD_EXISTS = "form1[0].#subform[0].flt_pnr[0]";
    private static final String CHECKBOX_FIELD_EXISTS = "form1[0].#subform[0].ksr_kryssruta[0]";
    private static final String FIELD_NOT_EXISTS = "FIELD_NOT_EXISTS";
    private static final String VALUE = "VALUE";

    PDAcroForm pdAcroForm;

    private static final PdfValueGenerator PDF_VALUE_GENERATOR = new PdfValueGenerator();

    @BeforeEach
    void setup() throws IOException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        final var inputStream = classloader.getResourceAsStream("fk7211_v1.pdf");
        final var document = Loader.loadPDF(inputStream.readAllBytes());
        final var documentCatalog = document.getDocumentCatalog();
        pdAcroForm = documentCatalog.getAcroForm();
    }

    @Nested
    class Value {

        @Test
        void shouldThrowExceptionIfFieldDoesntExist() {
            assertThrows(
                IllegalStateException.class,
                () -> PDF_VALUE_GENERATOR.setValue(pdAcroForm, FIELD_NOT_EXISTS, VALUE)
            );
        }

        @Test
        void shouldSetValueIfFieldExists() throws IOException {
            PDF_VALUE_GENERATOR.setValue(pdAcroForm, FIELD_EXISTS, VALUE);

            assertEquals(VALUE, pdAcroForm.getField(FIELD_EXISTS).getValueAsString());
        }

        @Test
        void shouldNotSetValueIfValueIsNull() throws IOException {
            PDF_VALUE_GENERATOR.setValue(pdAcroForm, FIELD_EXISTS, null);

            assertEquals("", pdAcroForm.getField(FIELD_EXISTS).getValueAsString());
        }
    }

    @Nested
    class Checkbox {

        @Test
        void shouldThrowExceptionIfFieldDoesntExist() {
            assertThrows(
                IllegalStateException.class,
                () -> PDF_VALUE_GENERATOR.setCheckedBoxValue(pdAcroForm, FIELD_NOT_EXISTS)
            );
        }

        @Test
        void shouldSetValueIfFieldExists() throws IOException {
            PDF_VALUE_GENERATOR.setCheckedBoxValue(pdAcroForm, CHECKBOX_FIELD_EXISTS);

            assertEquals("1", pdAcroForm.getField(CHECKBOX_FIELD_EXISTS).getValueAsString());
        }
    }
}