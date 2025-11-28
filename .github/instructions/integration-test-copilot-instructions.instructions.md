# Integration Test Creation Documentation

This document describes how integration tests are created and structured in the project.

Use the `specifications/integration-test-specification-template.spec.md` to structure your input for creating new integration tests or updating existing ones.

## Generating the Specification

For new certificate types, the agent can attempt to generate a draft specification by extracting values from the existing `CertificateModelFactory{TYPE}` class (e.g., type, version, recipient, code). The agent should populate the spec template with these values and include a default list of test scenarios (checked by default for common tests). The developer can then review and modify the draft spec before proceeding with test generation.

Default test scenarios to include (check as appropriate):
- [x] CreateCertificateIT
- [x] SignCertificateIT
- [x] UpdateCertificateIT
- [x] ValidateCertificateIT
- [x] GetCertificateIT
- [x] DeleteCertificateIT
- [x] SendCertificateIT
- [x] RenewCertificateIT
- [x] ReplaceCertificateIT
- [x] RevokeCertificateIT
- [x] ForwardCertificateIT
- [x] GetCertificatePdfIT
- [x] GetCertificateXmlIT
- [x] GetPatientCertificatesIT
- [x] GetUnitCertificatesIT
- [x] AdministrativeMessagesIT
- [x] AnswerComplementIT
- [x] ComplementIT
- [x] UnitStatisticsIT
- [x] AccessLevelsDeepIntegrationIT
- [x] AccessLevelsSVODIT

## Integration Test Workflows

This section outlines the workflows for different integration test-related tasks. Each workflow specifies what input the developer provides, what the agent does, and how iteration works. Refer to the numbered sections below for detailed instructions.

**Important**: For each workflow, if the agent is missing necessary information (e.g., certificate type, versions, specific test data), it must ask the developer before proceeding or generating code.

- **Workflow Creating Integration Tests for a New Certificate Type**:
  - **Developer Input**: Certificate type (e.g., FK7804), active version, recipient, code, test data (e.g., sample ElementData for filling), and access settings.
  - **Agent Actions**: 
    - Create the test folder structure under `integrationtest/{type}/`.
    - Generate `{TYPE}TestSetup.java` with constants and builder method.
    - Create `{TYPE}ActiveIT.java` extending ActiveCertificatesIT with nested test classes.
    - Create `{TYPE}CitizenIT.java` and `{TYPE}InactiveIT.java` similarly.
    - Ensure proper imports and setup/teardown methods.
  - **Iteration**: Agent asks for missing test data or access settings; developer reviews generated code.

- **Adding Tests to an Existing Certificate Type**:
  - **Developer Input**: Certificate type and specific test scenarios to add (e.g., new question validation).
  - **Agent Actions**: 
    - Identify the existing test folder and classes.
    - Add new nested test classes in the IT files based on common/tests templates.
    - Update TestSetup if new test data is needed.
    - Ensure consistency with existing tests.
  - **Iteration**: If new test types are needed, agent suggests and asks for approval; developer reviews additions.

- **Updating Integration Tests for Certificate Changes**:
  - **Developer Input**: Details of certificate model changes (e.g., new questions, rules, version updates).
  - **Agent Actions**: 
    - Update TestSetup with new test data or version.
    - Modify IT classes to include new nested tests if applicable.
    - Run tests to verify they still pass.
    - Add or update prefill XML resources if needed.
  - **Iteration**: Agent runs tests and reports failures, asking for fixes; developer provides additional test data.

- **Creating Testability Fill Services**:
  - **Developer Input**: Certificate type and fill data requirements.
  - **Agent Actions**: Generate `TestabilityCertificateFillService{TYPE}.java` for populating test certificates.
  - **Iteration**: Agent asks for specific fill logic; developer reviews the service.

- **Setting Up Test Resources**:
  - **Developer Input**: Certificate type and prefill XML data.
  - **Agent Actions**: Create or update `src/test/resources/prefill/{TYPE}.xml` for create tests.
  - **Iteration**: Agent asks for XML content; developer provides or reviews.

## Important points to always follow

- Integration tests use TestContainers for database and messaging.
- Each certificate type has its own package under integrationtest/.
- TestSetup classes define the certificate type, version, recipient, and sample data.
- IT classes extend base classes like ActiveCertificatesIT and nest common test classes.
- Use BaseTestabilityUtilities for setup.
- Ensure tests are annotated with @DisplayName for clarity.
- Follow the naming convention: {TYPE}{Active|Citizen|Inactive}IT.java

## 1. Test Structure

- Integration tests are located in `integration-test/src/test/java/se/inera/intyg/{service}/integrationtest/`
- For certificate-service: `se.inera.intyg.certificateservice.integrationtest`
- Each certificate type has a subfolder (e.g., fk7804/)
- Common utilities in `common/setup/` and `common/tests/`

## 2. TestSetup Class

- Named `{TYPE}TestSetup.java` (e.g., FK7804TestSetup)
- Defines constants: TYPE, CODE, CERTIFICATE_TYPE, ACTIVE_CERTIFICATE_TYPE_VERSION, RECIPIENT
- Provides a builder method `fk7804TestSetup()` returning BaseTestabilityUtilities.BaseTestabilityUtilitiesBuilder
- Includes TestabilityCertificate with type, code, version, recipient, sample ElementData, and TestabilityAccess

## 3. IT Classes

- **ActiveIT**: Extends ActiveCertificatesIT, tests active certificates.
- **CitizenIT**: Extends CitizenCertificatesIT (if exists), tests citizen-related functionality.
- **InactiveIT**: Extends InActiveCertificatesIT, tests inactive certificates.
- Each has @BeforeEach setUp() calling super.setUpBaseIT() and setting baseTestabilityUtilities using the TestSetup.
- @AfterEach tearDown() calling super.tearDownBaseIT().
- Nested classes for each test type, extending common test classes and overriding testabilityUtilities().

## 4. Common Test Classes

- Located in `common/tests/`
- Abstract classes like CreateCertificateIT, SignCertificateIT, etc.
- Contain the actual test logic.
- Subclasses provide specific testability utilities.

## 5. Test Data and Utilities

- Use TestDataCommonUserDTO for users.
- ApiUtil, InternalApiUtil, TestabilityApiUtil for API calls.
- Prefill XML resources in `src/test/resources/prefill/` for create tests.
- Awaitility for asynchronous operations.

## 6. Running Tests

- Use Gradle task `integrationTest` to run integration tests.
- Tests require Docker for TestContainers (MySQL, ActiveMQ).
- Ensure minHeapSize 512m, maxHeapSize 3g.

## 7. Best Practices

- Keep tests focused on integration scenarios.
- Use descriptive @DisplayName.
- Mock external services with MockServer if needed.
- Validate both success and error paths.
- Ensure tests are idempotent and clean up after themselves.
