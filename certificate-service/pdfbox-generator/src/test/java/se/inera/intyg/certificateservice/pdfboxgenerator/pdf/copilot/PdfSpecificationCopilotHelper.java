package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.copilot;

import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDNonTerminalField;
import org.apache.pdfbox.pdmodel.interactive.form.PDRadioButton;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PdfSpecificationCopilotHelper {

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
   * etc. you will get from pdf_specification_context. If overflow page is defined then set those
   * values. Remember that when values are indexes they start from 0 so it will not be the page
   * number but page index. If more than one page remember there should be more than one patient id
   * field in list, one per page.
   * <p>
   * Prompt PdfConfiguration for question: Generate a pdf configuration for this question using the
   * attached Question as idea for structure but the pdf_specification_context for the actual
   * values
   */

  private PDDocument document;

  private static final String CERTIFICATE_TYPE = "fk7427";

  @BeforeEach
  void setup() {
    final var classloader = getClass().getClassLoader();
    final var inputStream = classloader.getResourceAsStream(
        String.format("%s/pdf/%s_v1.pdf", CERTIFICATE_TYPE, CERTIFICATE_TYPE));

    try {
      document = Loader.loadPDF(inputStream.readAllBytes());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  void shouldPrintFieldIds() {
    PDAcroForm acroForm = document.getDocumentCatalog().getAcroForm();
    final var parentField = (PDNonTerminalField) acroForm.getFields().getFirst();
    var count = 0;
    try (BufferedWriter writer = new BufferedWriter(
        // Changge the file name to generate a new file if you are comparing if a PDF-version has changed when new PDFs are delivered by certificate recipient
        new FileWriter(String.format("pdf_specification_context_%s.txt", CERTIFICATE_TYPE)))) {
      writer.write(
          "General mapping for pdf specification per question type follows pattern ElementValueBoolean maps to PdfConfigurationBoolean and ElementValueDateList maps to PdfConfigurationDateList etc.\n");
      writer.write(
          "This part of the file contains ids and names and types of fields extracted from the pdf.\n");
      for (PDField page : parentField.getChildren()) {
        writer.write(String.format("//Page index %s\n", count++));
        for (PDField field : ((PDNonTerminalField) page).getChildren()) {
          if (field.getAlternateFieldName() != null && field.getAlternateFieldName()
              .contains("Fortsättningsblad")) {
            writer.write("// This is the overflow page\n");
          }

          StringBuilder extraText = new StringBuilder();
          if (field instanceof PDRadioButton radioButtonField) {
            extraText = new StringBuilder(
                "For radio boolean assume first option is false and second option is true. Use the options as field ids. Options:\n");
            for (String option : radioButtonField.getExportValues()) {
              extraText.append(option).append("\n");
            }
          }

          writer.write(String.format(
              "Field ID: %s\nName: %s\nField Type: %s\n%s\n",
              field.getFullyQualifiedName(),
              field.getAlternateFieldName(),
              field.getClass(),
              extraText)
          );
        }
      }
    } catch (IOException e) {
      throw new RuntimeException("Failed to write field IDs to file", e);
    }
  }
}
