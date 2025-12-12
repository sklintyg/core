# Certificate Implementation Guide Template

> This is a template. The actual guide should be filled out with specific details from the certificate specification and implementation context.

This guide provides a detailed step-by-step instruction set for implementing a new certificate model using the `/certificate` tool. It combines the information from the certificate specification with additional context from the codebase to create a clear implementation plan. This guide is for non-major version implementations (e.g., new certificates or minor updates).

## Prerequisites
- Complete certificate specification file (use `certificate-specification-template.spec.md` if needed).
- Access to the certificate-service module in the codebase.
- Understanding of the layered architecture: `api`, `application`, `domain`.

## Step 1: Specification Review and Validation
- Review the certificate specification for completeness:
  - PDF type: [e.g., TEMPLATE or GENERAL]
  - Available on 1177: [true/false]
  - Certificate type: [e.g., FK7804]
  - Activation date: [e.g., 2024-06-01]
  - All questions with IDs, texts, types, rules, validations.
- Ensure texts are word-for-word from the spec; no modifications allowed.
- If any IDs or codes are missing, note them for TODO comments.

## Step 2: Determine Implementation Scope
- Confirm this is a new certificate model (not a major version addition).

## Iteration 1: Set Up Certificate Model Factory
- Create `CertificateModelFactory[CERTIFICATE_TYPE]` class in the appropriate package (e.g., `se.inera.intyg.intygstjanst.certificate.service.certificate.model.factory`).
- Implement the factory following the layered architecture.
- Add activation date to `application-dev.properties` (e.g., `certificate.[lowercase_type].activation-date=[date]`).
- Map "Text innan val av intyg" to `description`.
- Map "Text efter val av intyg" to `detailedDescription`.
- Add unit tests for created class. 

## Iteration 2: Create All Categories with Tests
- Identify all categories from the specification (e.g., KAT_1, KAT_2).
- For each category:
  - Create `Category[PascalCaseName]` class.
  - Assign unique IDs (e.g., KAT_1).
  - Generate unit tests (e.g., `Category[PascalCaseName]Test`).
- Use existing category classes as references for uniformity.
- Run tests to ensure categories are correctly implemented.

## Iteration 3: Fill Each Category with Complete Element Specifications
- For each category (iterate one by one):
  - Generate element specifications for all questions in the category.
  - For each question:
    - Create `ElementSpecification` with unique `ElementId`.
    - Determine `ElementConfiguration` and `ElementValue` using the Value Generator Mapping Table.
    - Add validations based on the ElementValue type.
    - Apply rules and conditional logic using the Rule Mapping Table (e.g., mandatory, show/hide).
    - Use `ElementRuleFactory` and `ElementDataPredicateFactory` for rules.
    - Handle sub-questions with `ElementMapping` to parent.
    - Add `shouldValidate` for questions with show/hide rules.
  - Handle special elements (diagnoses, messages, etc.) with appropriate configurations and SK codes.
  - Ensure all texts are copied exactly from the spec.
  - Generate unit tests for each question class (e.g., `Question[PascalCaseName]Test`).
  - Run tests after completing each category to validate incrementally.

## Iteration 4: Implement Testability Certificate Fill Service
- After all element specifications are implemented, create `TestabilityCertificateFillService[CERTIFICATE_TYPE]` for testability purposes.
- Use existing fill services as references for uniformity.

## Iteration 5: Create Integration Tests
- Generate integration tests (e.g., `[CERTIFICATE_TYPE]ActiveIT`, `[CERTIFICATE_TYPE]CitizenIT`, `[CERTIFICATE_TYPE]InactiveIT`).
- Run tests to validate the complete model setup.

## Step 6: Final Validation and Iteration
- Run all tests to ensure the complete model works correctly.
- Check for TODO comments on missing IDs/codes and resolve them.
- Validate that the certificate integrates properly with the rest of the system.
- If issues arise, iterate on the implementation based on test failures or developer feedback.

## Additional Notes
- Always use existing implementations as references for consistency.
- Follow all coding conventions: use `final var`, `@RequiredArgsConstructor`, streams, etc.
- Use `@PerformanceLogging` with `eventAction` and `eventType` for logging.
- Ensure immutability and separation of concerns.
- For complex rules or unclear mappings, add TODO comments and reference the mapping tables.
- This iterative approach allows for incremental development and testing, reducing errors.

This guide ensures consistent implementation of certificates across the codebase.
