package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.copilot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDNonTerminalField;
import org.apache.pdfbox.pdmodel.interactive.form.PDRadioButton;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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
   * Prompt PdfConfiguration for question: Generate a pdf configuration and tests for this question
   * using the attached Question as idea for structure but the pdf structure for the actual values.
   * If overflow sheet use constant from PdfSpecification.
   */

  private PDDocument documentWithAddress;
  private PDDocument documentWithoutAddress;
  private StringBuilder originalStructure;

  private static final String FK_7427 = "fk7427";
  private static final String FK_7426 = "fk7426";
  private static final String FK_3221 = "fk3221";
  private static final String FK_7810 = "fk7810";
  private static final String FK_7804 = "fk7804";
  private static final String FK_7472 = "fk7472";

  private static final Map<String, String> TYPE_TO_VERSION = Map.of(
      FK_7427, "v1",
      FK_7426, "v1",
      FK_3221, "v1",
      FK_7810, "v1",
      FK_7804, "v2",
      FK_7472, "v1"
  );

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

  /**
   * Run to generate structure for the first time and save in resources/pdf folder.
   */
  @Disabled
  @Test
  void shouldCreateStructureFileForPdf() {
    final var certificateType = FK_7804;
    final var classloader = getClass().getClassLoader();
    final var inputStream = classloader.getResourceAsStream(
        String.format("%s/pdf/%s_%s.pdf", certificateType, certificateType,
            TYPE_TO_VERSION.get(certificateType)));

    try {
      final var document = Loader.loadPDF(inputStream.readAllBytes());
      final var structure = getPdfStructure(document);
      writeToFile(certificateType, structure);
      assertFalse(structure.isEmpty());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @ParameterizedTest
  @ValueSource(strings = {FK_7427, FK_7426, FK_3221, FK_7810, FK_7804, FK_7472})
  void shouldHaveSameStructureAsOriginalDocument(String certificateType) {
    setup(certificateType);

    final var contentNewStructure = getPdfStructure();

    final var normalizedOriginalStructure = originalStructure.toString()
        .replaceAll("\r\n", "\n")
        .replaceAll("\\s+", " ")
        .trim();
    final var normalizedExpectedText = contentNewStructure.toString()
        .replaceAll("\r\n", "\n")
        .replaceAll("\\s+", " ")
        .trim();

    assertEquals(normalizedExpectedText, normalizedOriginalStructure);
  }

  @ParameterizedTest
  @ValueSource(strings = {FK_7427, FK_7426, FK_3221, FK_7810, FK_7804, FK_7472})
  void shouldHaveSameIdsForTemplateWithAndWithoutAddress(String certificateType) {
    setup(certificateType);

    final var idsForTemplateWithAddress = getFieldIds(documentWithAddress);
    final var idsForTemplateWithoutAddress = getFieldIds(documentWithoutAddress);

    final var errors = new ArrayList<String>();
    final var minSize = Math.min(idsForTemplateWithAddress.size(),
        idsForTemplateWithoutAddress.size());
    for (int i = 0; i < minSize; i++) {
      final var idWithAddress = idsForTemplateWithAddress.get(i);
      final var idWithoutAddress = idsForTemplateWithoutAddress.get(i);

      if (!idWithAddress.equals(idWithoutAddress)) {
        errors.add(String.format("Mismatch at index %d: '%s' vs '%s'", i, idWithAddress,
            idWithoutAddress));
      }
    }
    if (idsForTemplateWithAddress.size() != idsForTemplateWithoutAddress.size()) {
      errors.add(
          String.format("Different number of fields: with address = %d, without address = %d",
              idsForTemplateWithAddress.size(), idsForTemplateWithoutAddress.size()));
    }

    assertTrue(errors.isEmpty(), String.join("\n", errors));
  }

  private void setup(String certificateType) {
    final var classloader = getClass().getClassLoader();
    final var inputStream = classloader.getResourceAsStream(
        String.format("%s/pdf/%s_%s.pdf", certificateType, certificateType,
            TYPE_TO_VERSION.get(certificateType)));
    final var inputStreamWithoutAddress = classloader.getResourceAsStream(
        String.format("%s/pdf/%s_%s_no_address.pdf", certificateType, certificateType,
            TYPE_TO_VERSION.get(certificateType)));

    try {
      documentWithAddress = Loader.loadPDF(inputStream.readAllBytes());
      documentWithoutAddress = Loader.loadPDF(inputStreamWithoutAddress.readAllBytes());
      originalStructure = readFileFromResources(
          String.format("%s/pdf/%s_structure.txt", certificateType, certificateType)
      );
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private StringBuilder getPdfStructure() {
    return getPdfStructure(documentWithAddress);
  }

  private StringBuilder getPdfStructure(PDDocument document) {
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

  private List<String> getFieldIds(PDDocument document) {
    final var acroForm = document.getDocumentCatalog().getAcroForm();
    final var parentField = (PDNonTerminalField) acroForm.getFields().getFirst();
    final var ids = new ArrayList<String>();

    for (PDField page : parentField.getChildren()) {
      for (PDField field : ((PDNonTerminalField) page).getChildren()) {
        if (field instanceof PDRadioButton radioButtonField) {
          ids.addAll(radioButtonField.getExportValues());
        }
        ids.add(field.getFullyQualifiedName());
      }
    }
    return ids;
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
