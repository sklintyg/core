package se.inera.intyg.certificateservice.pdfboxgenerator.pdf;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PdfFieldTest {


  @Nested
  class SanatizedValue {

    PDFont font;

    @BeforeEach
    void setup() {
      font = new PDType1Font(FontName.HELVETICA);
    }

    @Test
    void shouldReturnEmptySanitizedValueWhenValueIsNull() {
      final var field = PdfField.builder()
          .id("Field1")
          .value(null)
          .build();

      assertEquals("",
          field.sanitizedValue(font));
    }

    @Test
    void shouldSanitizeValue() {
      final var field = PdfField.builder()
          .id("Field1")
          .value("Line1\u20A5Line2")
          .build();

      assertEquals("Line1 Line2",
          field.sanitizedValue(font));
    }

    @Test
    void shouldReturnEmptySanitizedValueWhenValueIsEmpty() {
      final var field = PdfField.builder()
          .id("Field1")
          .value("")
          .build();

      assertEquals("",
          field.sanitizedValue(font));
    }
  }

  @Nested
  class IsPatientField {

    @Test
    void shouldReturnTrueWhenPatientFieldIsTrue() {
      final var field = PdfField.builder()
          .id("Field1")
          .patientField(true)
          .build();

      assertTrue(field.isPatientField(),
          "Expected isPatientField to return true when patientField is true");
    }

    @Test
    void shouldReturnFalseWhenPatientFieldIsFalse() {
      final var field = PdfField.builder()
          .id("Field1")
          .patientField(false)
          .build();

      assertFalse(field.isPatientField(),
          "Expected isPatientField to return false when patientField is false");
    }

    @Test
    void shouldReturnFalseWhenPatientFieldIsNull() {
      final var field = PdfField.builder()
          .id("Field1")
          .patientField(null)
          .build();

      assertFalse(field.isPatientField(),
          "Expected isPatientField to return false when patientField is null");
    }
  }
}