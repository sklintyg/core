# PDF Generation Mapping Documentation

This document describes how different data types in our application are mapped to PDF configurations
for generating PDF documents.

## Mapping ElementValue to PdfConfiguration

- Each question's `ElementValue` type determines which `PdfConfiguration` type is used for PDF
  generation.
- The mapping is implemented in the `Pdf*ValueGenerator` classes, where each generator handles a
  specific `ElementValue` and produces the corresponding `PdfConfiguration`.
- Example mappings from your codebase:

| Value Generator Class                     | Maps From                            | Maps To                                          | Example Usage (Question)                               | Example Test File                               |
|-------------------------------------------|--------------------------------------|--------------------------------------------------|--------------------------------------------------------|-------------------------------------------------|
| PdfTextValueGenerator                     | ElementValueText                     | PdfConfigurationText                             | QuestionHalsotillstandPsykiska                         | QuestionHalsotillstandPsykiskaTest              |
| PdfBooleanValueGenerator                  | ElementValueBoolean                  | PdfConfigurationBoolean                          | QuestionSmittbararpenning                              | QuestionSmittbararpenningTest                   |
| PdfCodeValueGenerator                     | ElementValueCode                     | PdfConfigurationCode / PdfConfigurationRadioCode | QuestionSysselsattning (code), QuestionPrognos (radio) | QuestionSysselsattningTest, QuestionPrognosTest |
| PdfDateValueGenerator                     | ElementValueDate                     | PdfConfigurationDate                             | QuestionNarAktivaBehandlingenAvslutades                | QuestionNarAktivaBehandlingenAvslutadesTest     |
| PdfDateRangeValueGenerator                | ElementValueDateRange                | PdfConfigurationDateRange                        | QuestionPeriod                                         | QuestionPeriodTest                              |
| PdfDateListValueGenerator                 | ElementValueDateList                 | PdfConfigurationDateList                         | QuestionUtlatandeBaseratPa                             | QuestionUtlatandeBaseratPaTest                  |
| PdfDateRangeListValueGenerator            | ElementValueDateRangeList            | PdfConfigurationDateRangeList                    | QuestionPeriod                                         | QuestionPeriodTest                              |
| PdfDiagnosisListValueGenerator            | ElementValueDiagnosisList            | PdfConfigurationDiagnoses                        | QuestionDiagnos                                        | QuestionDiagnosTest                             |
| PdfMedicalInvestigationListValueGenerator | ElementValueMedicalInvestigationList | PdfConfigurationMedicalInvestigationList         | QuestionMedicinskaUtredningar                          | QuestionMedicinskaUtredningarTest               |
| PdfCodeListValueGenerator                 | ElementValueCodeList                 | PdfConfigurationCode                             | QuestionSysselsattning                                 | QuestionSysselsattningTest                      |

- To add or update a mapping, implement or modify the relevant `Pdf*ValueGenerator` class.

## Certificate PDF Specification and Structure

- Each certificate type is associated with a `PdfSpecification` object, which defines the template,
  field IDs, and other metadata required for PDF generation.
- When generating a PDF, both the `PdfSpecification` and the relevant `PdfConfiguration` objects
  must be provided.
- The process should use a structure file (e.g., `fk7804_structure.txt`) that lists all the field
  IDs and their mapping for the PDF. This ensures that the configuration and specification are
  aligned and that all required fields are present.
- The structure file acts as the source of truth for which IDs are expected in the PDF, and should
  be referenced when implementing or updating PDF generation logic for a certificate.

## Implementation Guidance for New PdfConfiguration Mappings

- **Always use existing implementations as reference:**
    - When implementing a new `PdfConfiguration` mapping for a question, always look for and use
      examples from previous implementations in this file.
    - It's important that the new configuration follows the same structure as the examples, so the
      fields are named correct etc. as in the `PdfConfiguration*` that maps the value class.
    - This ensures consistency, reuse of best practices, and reduces the risk of missing important
      logic or conventions.
    - The same applies to tests: always base new tests on the structure and approach of existing
      test files referenced here.
- **How to proceed:**
    - Identify the most similar question and configuration mapping already implemented.
    - Follow the same structure for both the implementation and the test.
    - If you need to introduce a new pattern, document it clearly and update this file with the new
      example.

## Step-by-step Implementation Process

1. **Start by generating a PdfSpecification and its tests.**
    - Use an existing example as a template, such as `FK7804PdfSpecification` and tests
      `FK7804PdfSpecificationTest`.
    - The PdfSpecification should define the template, field IDs, and all required metadata for the
      certificate type.
    - Write corresponding tests to ensure the PdfSpecification is correct and complete.
2. **Configure each question in the certificate model.**
    - For each question in the certificate model, identify the `ElementValue` type it uses looking
      at the Config and Validation of model.
    - Implement the corresponding `PdfConfiguration` mapping for that `ElementValue` using the above
      example files like `QuestionDiagnos`.
    - OBSERVE if you don't find a relevant example, DO NOT implement. Let me know this in the chat.
    - If the structure file has an overflow page then you need to add that id as
      overflowSheetFieldId. Only text and diagnoses can have overflow.
    - The PdfConfiguration is an object placed in the original question file in the
      ElementSpecification, not in its own file or function. So you will add it to
      `Question*.java` and the tests in `Question*Test.java` using an expected value and then check
      that's equal to the actual value)
    - Use the examples and patterns in this file for both implementation and tests.
    - Open and create whatever files necessary.
3. **Source all values like indexes and IDs from the structure file.**
    - Always use the `certificateType_structure.txt` file (e.g., `fk7804_structure.txt`) to get the
      correct field IDs, indexes, and other mapping values.
    - This ensures alignment between the specification, configuration, and the actual PDF template.

Following this process ensures that all PDF generation logic is consistent, maintainable, and
aligned with the structure and requirements of each certificate type.
