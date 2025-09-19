# Certificate PDF Generation Mapping Documentation

This document describes how the certificate model connects to PDF generation, based on the actual
code and model structure.

## How the Model Connects to PDF Generation

### 1. CertificateModel and ElementSpecification

- The central class is `CertificateModelFactoryTYPE`, which represents a specific certificate type (
  e.g., FK7804).
- Each `CertificateModelFactory` contains a list of `ElementSpecification` objects. Each
  `ElementSpecification` represents a question in the certificate.
- Each `ElementSpecification` contains:
    - An `ElementId` (unique for the field/question)
    - An `ElementConfiguration` (defines the type, options, and validation for the field)

### 2. ElementValue and ElementConfiguration

- Each field/question in the certificate is answered with an `ElementValue` (e.g.,
  `ElementValueText`, `ElementValueBoolean`, etc.).
- Depending on the ElementValue there will also be an ElementValidation added to the specification.
- The type of `ElementConfiguration` determines which `ElementValue` the question will be answered
  with.
- One `ElementValue` type can map to different `ElementConfiguration` types, like
  `ElementConfigurationTextField` and `ElementConfigurationTextArea` both mapping to
  `ElementValueText`.

### 3. Value Generator Mapping Table

| ElementConfiguration                         | SK Code    | ElementValue                         | Example Usage (Question)                     | Example Test File                                |
|----------------------------------------------|------------|--------------------------------------|----------------------------------------------|--------------------------------------------------|
| ElementConfigurationTextField                | SK-006     | ElementValueText                     | QuestionHalsotillstandPsykiska               | QuestionHalsotillstandPsykiskaTest               |
| ElementConfigurationTextArea                 | SK-007     | ElementValueText                     | QuestionHalsotillstandPsykiska               | QuestionHalsotillstandPsykiskaTest               |
| ElementConfigurationCheckboxBoolean          | SK-001     | ElementValueBoolean                  | QuestionSmittbararpenning                    | QuestionSmittbararpenningTest                    |
| ElementConfigurationRadioBoolean             | SK-002     | ElementValueBoolean                  | QuestionSmittbararpenning                    | QuestionSmittbararpenningTest                    |
| ElementConfigurationDropdownCode             | SK-008     | ElementValueCode                     | QuestionKannedomOmPatienten                  | QuestionKannedomOmPatienten                      |
| ElementConfigurationRadioMultipleCode        | SK-002     | ElementValueCode                     | QuestionPrognos (radio)                      | QuestionPrognosTest                              |
| ElementConfigurationCheckboxMultipleCode     | SK-004     | ElementValueCodeList                 | QuestionSysselsattning                       | QuestionSysselsattningTest                       |
| ElementConfigurationCheckboxMultipleDate     | SK-004a    | ElementValueDateList                 | QuestionUtlatandeBaseratPa                   | QuestionUtlatandeBaseratPaTest                   |
| ElementConfigurationDate                     | SK-005     | ElementValueDate                     | QuestionNarAktivaBehandlingenAvslutades      | QuestionNarAktivaBehandlingenAvslutadesTest      |
| ElementConfigurationDateRange                | SK-005     | ElementValueDateRange                | QuestionPeriodVardEllerTillsyn               | QuestionPeriodVardEllerTillsyn                   |
| ElementConfigurationCheckboxDateRangeList    | SK-004a    | ElementValueDateRangeList            | QuestionPeriod                               | QuestionPeriodTest                               |
| ElementConfigurationDiagnosis                |            | ElementValueDiagnosisList            | QuestionDiagnos                              | QuestionDiagnosTest                              |
| ElementConfigurationMedicalInvestigationList |            | ElementValueMedicalInvestigationList | QuestionUtredningEllerUnderlag               | QuestionUtredningEllerUnderlag                   |
| ElementConfigurationInteger                  | SK-009     | ElementValueInteger                  | QuestionAntalManader                         | QuestionAntalManaderTest                         |
| ElementConfigurationIcf                      | SK-007     | ElementValueIcf                      | QuestionAktivitetsbegransningar              | QuestionAktivitetsbegransningarTest              |
| ElementConfigurationMessage                  | SK-A01->04 | N/A                                  | MessageNedsattningArbetsformagaStartDateInfo | MessageNedsattningArbetsformagaStartDateInfoTest |
| ElementConfigurationCategory                 | SK-000     | N/A                                  | CategoryPrognos                              | CategoryPrognosTest                              |
| ElementConfigurationVisualAcuities           |            | ElementValueVisualAcuities           | QuestionSynkarpa                             | QuestionSynskarpaTest                            |

### 4. Rule Mapping Table

| Rule Code                | Description                          | Java Class            | ElementRuleType               |
|--------------------------|--------------------------------------|-----------------------|-------------------------------|
| SR-001                   | Mandatory question                   | ElementRuleExpression | MANDATORY                     |
| SR-002                   | Mandatory category                   | ElementRuleExpression | CATEGORY_MANDATORY            |
| SR-003                   | Show question                        | ElementRuleExpression | SHOW                          |
| SR-004                   | Hide question                        | ElementRuleExpression | HIDE                          |
| SR-005                   | Handle disabled state for components | ElementRuleExpression | DISABLE / DISABLE_SUB_ELEMENT |
| SR-006                   | Prefill question                     | ElementRuleExpression | N/A                           |
| If max text limit is set | Text limit                           | ElementRuleLimit      | TEXT_LIMIT                    |

### 5. Question generation

- You will receive a mapping from the requirements in Swedish of the question.
- The name of the Class should be Question + the name in PascalCase. If the name is too long, you
  can
  shorten it, but keep it understandable.
- The test class should be the same as the question class but with Test at the end.
- Then you need to find the correct ElementConfiguration and place the values such as id etc.
- For both implementation and tests you should use the examples in the table above since we want the
  code uniform.

### 6. ElementRules

- Some questions may have `ElementRule` that define conditional logic (e.g., showing/hiding
  questions based on previous answers).
- `ElementRules` can be complex and may involve multiple conditions and actions.
- The rules you will read them from the requirements that you will receive from the user.
- You can use the `ElementRuleFactory` to create rules based on the rule code and parameters.
