# GitHub Copilot Instructions Index

IMPORTANT: All tools are listed in `.github/copilot-instructions.md`

This document serves as an index for the various GitHub Copilot instruction files in the project. It describes the purpose of each instruction set and when to apply them, helping the Copilot agent mode select the appropriate guidelines based on the context of the work.

### Working with Tools and Specifications

Before starting any certificate-related task, a complete certificate specification file must be created or provided. The specification serves as the blueprint for all subsequent actions and ensures consistency and completeness.

#### Specification Creation Process
1. **Create the Spec**: The specification can be created by the agent (using templates like `certificate-specification-template.spec.md`) or provided by the developer. It must include all required details.
2. **Review the Spec**: The developer and agent should review the specification for accuracy, completeness, and adherence to requirements. Any missing or unclear information should be clarified before proceeding. If the agent fins the spec is incomplete it should be notified to the developer.
3. **Follow Instructions**: Once the spec is reviewed and approved, proceed with the relevant workflows outlined in this document. If any information is missing during implementation, add TODO comments for the developer to resolve.

#### Using Copilot Tools
This document includes specialized tools to assist with specific tasks. When a task relates to one of the available tools, the agent should suggest or invoke the appropriate tool.

- **Invoking a Tool**: Tools are invoked by typing commands like `/certificate` or `/certificateVersionAnalysis`. Upon invocation:
    1. The agent mentions the regulating file for the tool.
    2. Asks the user if they want to proceed with applying the instructions.
    3. If confirmed, applies the relevant guidelines from the tool's instructions to the task at hand.
- **Available Tools**:
    - `/certificate`: For creating, modifying, and versioning certificate models.
    - `/certificateVersionAnalysis`: For analyzing differences between certificate versions.
    - `/certificatePDF`: For mapping certificate data to PDF configurations.
    - `/integration-test`: For creating and maintaining integration tests.
    - `/feature`: Orchestrates the creation of a complete feature including spec, implementation, and tests using SpecCreator, FeatureImplementer, and TestCreator agents.
    - `/template`: Creates a spec or guide from a template.
- **Listing Tools**: Use `/tools` to list all available tools.
- **Suggestion**: If no tool is invoked but the task relates to a tool's area, the agent should suggest using the relevant tool to ensure proper guidelines are applied.

## General Instructions

### `.github/copilot-instructions.md`
**Purpose**: Provides overarching coding standards, design principles, and best practices for the entire Java/Spring Boot project.  
**When to Use**:
- For all Java development tasks, including new features, refactoring, or bug fixes.
- When working on controllers, services, DTOs, domain models, or any application layer.
- For package organization, dependency injection, logging, error handling, security, and testing conventions.
- Applicable to all modules and services in the project (e.g., certificate-service, intyg-proxy-service, etc.).

**Key Topics Covered**:
- Coding conventions (immutability, streams, builder patterns).
- Package organization (layered architecture, feature-based packages).
- Controller, service, and DTO design principles.
- Logging, observability, and performance monitoring.
- Error handling, security principles, and testing conventions.

## Feature Development Instructions

### `.github/instructions/feature-copilot-instructions.instructions.md`
**Tool Name**: `/feature`  
**Purpose**: Orchestrates the creation of a complete feature including spec, implementation, and tests using SpecCreator, FeatureImplementer, and TestCreator agents.  
**When to Use**:
- When developing a new feature from scratch, requiring specification, code implementation, and testing.
- For end-to-end feature development workflows that need coordination between spec creation, coding, and testing phases.
- When the user provides high-level requirements and wants a fully realized feature.

**Key Topics Covered**:
- Gathering user requirements for feature development.
- Generating feature implementation guides and READMEs.
- Generating feature specifications using templates.
- Implementing features based on specs, following coding standards.
- Creating comprehensive tests for the implemented features.
- Validation and final review of the complete feature package.

## Certificate-Specific Instructions

### `.github/instructions/certificate-copilot-instructions.instructions.md`
**Tool Name**: `/certificate`  
**Purpose**: Detailed guidelines for creating, modifying, and versioning certificate models, including elements, configurations, rules, and testing.  
**When to Use**:
- When implementing new certificate types or modifying existing ones.
- For adding questions, categories, element specifications, or rules to certificates.
- During certificate versioning (e.g., creating V2 from V1).
- For generating tests, integration tests, and fill services for certificates.

**Key Topics Covered**:
- Certificate model structure (CertificateModelFactory, ElementSpecification).
- Mapping element configurations to values and validations.
- Rule creation and conditional logic.
- Testing strategies, including version lock tests.
- Major version upgrades and common element extraction.

### `.github/instructions/major-version-analysis.instructions.md`
**Tool Name**: `/certificate-version-analysis`  
**Purpose**: Instructions for analyzing differences between certificate versions to identify common and unique elements.  
**When to Use**:
- Before creating a new major version of a certificate (e.g., V2).
- To compare specifications against existing versions and determine what needs to be refactored into common elements.
- For generating analysis reports on model changes, texts, rules, and configurations.

**Key Topics Covered**:
- Matching elements by ID.
- Identifying non-common elements.
- Detailed comparison of texts, rules, validations, and configurations.
- Model-level changes (descriptions, metadata).

## PDF Generation Instructions

### `.github/instructions/pdf-copilot-instructions.instructions.md`
**Tool Name**: `/certificate-pdf`  
**Purpose**: Guidelines for mapping certificate data to PDF configurations and ensuring proper PDF generation.  
**When to Use**:
- When implementing or updating PDF generation for certificates.
- For creating new PdfConfiguration mappings or value generators.
- When working on PDF specifications, structure files, or field mappings.

**Key Topics Covered**:
- Mapping ElementValue types to PdfConfiguration types.
- Using structure files as source of truth for field IDs.
- Implementation guidance based on existing examples.
- Testing PDF configurations.

## Integration Test Instructions

### `.github/instructions/integration-test-copilot-instructions.instructions.md`
**Tool Name**: `/integration-test`  
**Purpose**: Guidelines for creating and maintaining integration tests, including test structure, setup, and best practices.  
**When to Use**:
- When creating integration tests for new certificate types or services.
- For adding or modifying test scenarios in existing integration tests.
- When updating tests due to certificate model changes.
- For ensuring integration tests follow project conventions.

**Key Topics Covered**:
- Integration test structure and organization.
- TestSetup classes and IT classes.
- Common test utilities and data.
- Running and maintaining integration tests.
- Best practices for test coverage and reliability.

## Template Instructions

### `.github/instructions/template-copilot-instructions.instructions.md`
**Tool Name**: `/template`  
**Purpose**: Creates a spec or guide from a template. It asks the user for the kind of document (spec or guide), and the task it should be created for, and looks for an appropriate template to use. If no template exists it first creates the template for the task, and then the specific document from that template. Depending on the kind of document, either requirements (for spec) or a spec file (for guide).  
**When to Use**:
- When creating a new specification document from user requirements.
- When generating an implementation guide based on an existing specification.

**Key Topics Covered**:
- Interactively gathering template type (spec or guide).
- Collecting task description and relevant details.
- For specs: eliciting and structuring requirements into a specification template.
- For guides: using a provided spec file to create a step-by-step implementation guide.
- Ensuring output follows project templates and conventions.

## Usage Guidelines for Copilot Agent Mode

- **Context Detection**: The agent should scan the current file, task description, or workspace context to determine the relevant instruction sets.
- **Priority**: Start with the most specific instructions (e.g., certificate or PDF) and fall back to general instructions if needed.
- **Combination**: For certificate-related tasks, apply both certificate-specific and general instructions. For PDF tasks, combine PDF and general instructions.
- **Versioning Tasks**: When working on certificate versioning, include major-version-analysis instructions alongside certificate instructions.
- **New Features**: For entirely new features outside certificates/PDFs, rely primarily on general instructions.

This index ensures that Copilot applies the right set of rules to maintain consistency, follow best practices, and adhere to project-specific conventions.
