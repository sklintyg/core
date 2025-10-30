# Certificate Creation Documentation

This document describes how a new certificate model is created.

### Important points to always follow

- Do not add codes or ids that you have not gotten from the user. In that case a constant with a
  TODO comment so the developer can fix this.

### 1. CertificateModel and ElementSpecification

- The central class is `CertificateModelFactoryTYPE`, which represents a specific certificate type (
  e.g., FK7804).
- Each `CertificateModelFactory` contains a list of `ElementSpecification` objects. Each
  `ElementSpecification` represents a question in the certificate.
- Each `ElementSpecification` contains:
    - An `ElementId` (unique for the field/question)
    - An `ElementConfiguration` (defines the type, options, and validation for the field)
- Add an activation date for the certificate in `application-dev.properties`
  (e.g., `certificate.fk7804.activation-date=2024-06-01`)

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

- Make sure that you verify if mandatory or mandatoryExists is needed. For example for RadioBoolean
  mandatoryExists is needed since both true and false are valid answers, and without exists we will
  look for only true.

### 5. Category and question generation

- You will receive a mapping from the requirements in Swedish of the question.
- The name of the Class should be Category/Question + the name in PascalCase. If the name is too
  long, you can shorten it, but keep it understandable.
- The categories won't have an id in the specification, so put KAT_x where x goes from 1 and up.
- The test class should be the same as the question class but with Test at the end.
- Then you need to find the correct ElementConfiguration and place the values such as id etc.
- For both implementation and tests you should use the examples in the table above since we want the
  code uniform.
- Use a factory like `ElementRuleFactory` and `ElementDataPredicateFactory` if you find a method
  that fits
- If a question is a sub question (ElementId is x.x instead of x for example 1.2) it needs to have
  an ElementMapping to the parent question, 1.2 maps to 1 for example.
- If a question has a show or hide rule, it usually needs a shouldValidate field. This can be set up
  using ElementDataPredicateFactory.

### 6. ElementRules

- Some questions may have `ElementRule` that define conditional logic (e.g., showing/hiding
  questions based on previous answers).
- `ElementRules` can be complex and may involve multiple conditions and actions.
- The rules you will read them from the requirements that you will receive from the user.
- You can use the `ElementRuleFactory` to create rules based on the rule code and parameters.

### 7. Testing

- Each question should have a corresponding test class (e.g., `QuestionHalsotillstandPsykiskaTest`).
- Integration tests should be created to ensure the entire certificate model works as expected.
- Use the existing test classes as references for creating new tests.
- A FillService needs to be created for Testability purposes. For example
  `TestabilityCertificateFillServiceFK7804`.
- For example: `FK7804ActiveIT`, `FK7804CitizenIT` and `FK7804InactiveIT` for integration tests.

### 8. Adding another major version of a certificate

- A new version will be a new CertificateModel with the suffix VX where X is the version number.
- When a new major version is added we will move common elements that is the same between all
  versions to a common package.
- The common elements will have no version suffix.
- The new version will contain common elements as well as unique elements for that version.
- The unique elements will have the version suffix.
- The new version will have its own integration tests.
- A question is common between versions if everything, mappings, rules, config, texts etc. are the
  same. If a text is different this is a new question for this version.

**V2 files must not import from V1 files**: When V1 and V2 have questions with the same name but
different versions, the IDs are often named similarly. However, V2 questions/categories can only
use common or V2 IDs, and must never import anything from V1 files. Always use the corresponding
V2 version of IDs when they exist.

Follow these steps:

1. Generate an analysis file described in `major-version-analysis.md` to identify common and unique
   elements.
2. Have developer review analysis and make changes if needed
3. **IMPORTANT: Create a version lock test for the previous version** (see section 9 below)
4. Create new certificate model class for the new version
5. Add common elements
6. Create new questions that are unique for the new version (with VX suffix for methods and ids
   where X is version)
   ***Example: questionHorselV1(), QUESTION_HORSEL_V1_ID***
7. If a question is common between versions, but an id is used from a version question, then the id
   needs to be passed as an attribute. See `QuestionMissbrukProvtagning` as example. The ids can
   then be sent in each CertificateModelFactory as attributes.
8. Add the unique elements to the model

### 9. Version Lock Testing (Critical for Major Versions)

**When creating a new major version (e.g., V2), you MUST create a version lock test for the
previous version (e.g., V1) to prevent accidental modifications.**

Version lock tests use snapshot testing to ensure that older certificate versions remain exactly as
they were when released. The test will help avoid unintended changes when adding new versions and
having common
elements.

**How to create a version lock test:**

1. In `VersionLockTest.java`, add a test for the previous version.
2. Run the test - it will fail and generate a snapshot in
   `src/test/resources/certificate-snapshots/`
3. Review the generated JSON file to ensure it looks correct
4. Run the test again - it should now pass

**What happens if someone modifies a locked version:**
The test will fail and show exactly what changed, preventing accidental modifications from being
merged.
