package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.copilot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDNonTerminalField;
import org.apache.pdfbox.pdmodel.interactive.form.PDRadioButton;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PdfSpecificationCopilotHelperTest {

  /**
   * This class can be used to build PdfSpecification and PdfConfigurations for a certificate using
   * GitHub Copilot.
   * <p>
   * How to use: 1. Generate a file using this test for the new certificate type 2. Generate a file
   * using this test for a similar certificate type (one with overflow page if the new one contains
   * it for example) 3. Attach to context these two files and the already existing PdfSpecification
   * for the similar certificate 4. Use prompts down.
   * <p>
   * Prompt PdfSpecification: Generate a pdf specification for FK4727 following the previous pattern
   * in PdfSpecification file in your context, but not the values. The values like ids, page indexes
   * etc. you will get from certificate_type_structure. If overflow page is defined then set those
   * values. Remember that when values are indexes they start from 0 so it will not be the page
   * number but page index. If more than one page remember there should be more than one patient id
   * field in list, one per page.
   * <p>
   * Prompt PdfConfiguration for question: Generate a pdf configuration for this question using the
   * attached Question as idea for structure but the pdf_specification_context for the actual
   * values
   */

  private PDDocument document;
  private StringBuilder originalStructure;

  private static final String FK_7427 = "fk7427";
  private static final String FK_7426 = "fk7426";

  /**
   * To verify that the PDF templates that are delivered by the certificate recipient follows the
   * correct structure these tests can be used.
   * <p>
   * If completely new PDF: - Generate structure using writeToFile method - Place this in the pdf
   * folder for the type in the resources in app
   * <p>
   * If new PDF templates for existing PDF-implementation: - Run these tests to see if the new
   * templates have differences that disrupt the previous implementation - If so, then fix what
   * needs to be fixed and save a new structure file for the type
   */
  @Nested
  class FK7427 {

    @BeforeEach
    void setup() {
      final var classloader = getClass().getClassLoader();
      final var inputStream = classloader.getResourceAsStream(
          String.format("%s/pdf/%s_v1.pdf", FK_7427, FK_7427));

      try {
        document = Loader.loadPDF(inputStream.readAllBytes());
        originalStructure = readFileFromResources(
            String.format("%s/pdf/%s_structure.txt", FK_7427, FK_7427)
        );
        //writeToFile(FK_7427, getPdfStructure());
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }

    @Test
    void shouldHaveSameStructureAsOriginalDocument() {
      final var contentNewStructure = getPdfStructure();

      final var normalizedOriginalStructure = originalStructure.toString().replaceAll("\r\n", "\n")
          .trim();
      final var normalizedExpectedText = contentNewStructure.toString().replaceAll("\r\n", "\n")
          .trim();

      assertEquals(normalizedExpectedText, normalizedOriginalStructure);
    }
  }

  @Nested
  class FK7426 {

    @BeforeEach
    void setup() {
      final var classloader = getClass().getClassLoader();
      final var inputStream = classloader.getResourceAsStream(
          String.format("%s/pdf/%s_v1.pdf", FK_7426, FK_7426));

      try {
        document = Loader.loadPDF(inputStream.readAllBytes());
        originalStructure = readFileFromResources(
            String.format("%s/pdf/%s_structure.txt", FK_7426, FK_7426)
        );
      } catch (IOException e) {
        throw new RuntimeException(e);
      }

      //writeToFile(FK_7426, getPdfStructure());
    }

    @Test
    void shouldHaveSameStructureAsOriginalDocument() {
      final var contentNewStructure = getPdfStructure();

      final var normalizedOriginalStructure = originalStructure.toString().replaceAll("\r\n", "")
          .trim();
      final var normalizedExpectedText = contentNewStructure.toString().replaceAll("\r\n", "")
          .trim();

      assertEquals(normalizedExpectedText, normalizedOriginalStructure);
    }
  }

  private StringBuilder getPdfStructure() {
    final var acroForm = document.getDocumentCatalog().getAcroForm();
    final var parentField = (PDNonTerminalField) acroForm.getFields().getFirst();
    final var content = new StringBuilder();
    var count = 0;

    content.append(
        "General mapping for pdf specification per question type follows pattern ElementValueBoolean maps to PdfConfigurationBoolean and ElementValueDateList maps to PdfConfigurationDateList etc.\n");
    content.append(
        "This part of the file contains ids and names and types of fields extracted from the pdf.\n");
    for (PDField page : parentField.getChildren()) {
      content.append(String.format("//Page index %s\n", count++));
      for (PDField field : ((PDNonTerminalField) page).getChildren()) {
        if (field.getAlternateFieldName() != null && field.getAlternateFieldName()
            .contains("Fortsättningsblad")) {
          content.append("// This is the overflow page\n");
        }

        final var extraText = new StringBuilder();
        if (field instanceof PDRadioButton radioButtonField) {
          extraText.append(
              "For radio boolean assume first option is true and second option is false. Use the options as field ids. Options:\n");
          for (String option : radioButtonField.getExportValues()) {
            extraText.append(option).append("\n");
          }
        }

        content.append(String.format(
            "Field ID: %s\nName: %s\nField Type: %s\n%s\n",
            field.getFullyQualifiedName(),
            field.getAlternateFieldName(),
            field.getClass(),
            extraText)
        );
      }
    }
    return content;
  }

  private void writeToFile(String certificateType, StringBuilder content) {
    final var fileName = String.format("%s_structure.txt", certificateType);
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
      writer.write(content.toString());
    } catch (IOException e) {
      throw new RuntimeException("Failed to write content to file", e);
    }
  }

  private StringBuilder readFileFromResources(String fileName) throws IOException {
    final var classloader = getClass().getClassLoader();
    final var inputStream = classloader.getResourceAsStream(fileName);
    final var reader = new BufferedReader(new InputStreamReader(inputStream));
    final var fileContent = new StringBuilder();
    String line;
    while ((line = reader.readLine()) != null) {
      fileContent.append(line).append("\n");
    }
    return fileContent;
  }
}
