# GitHub Copilot Instructions Index

This document serves as an index for the various GitHub Copilot instruction files in the project. It describes the purpose of each instruction set and when to apply them, helping the Copilot agent mode select the appropriate guidelines based on the context of the work.

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
**Tool Name**: `/certificateVersionAnalysis`  
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
**Tool Name**: `/certificatePDF`  
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

## Usage Guidelines for Copilot Agent Mode

- **Context Detection**: The agent should scan the current file, task description, or workspace context to determine the relevant instruction sets.
- **Priority**: Start with the most specific instructions (e.g., certificate or PDF) and fall back to general instructions if needed.
- **Combination**: For certificate-related tasks, apply both certificate-specific and general instructions. For PDF tasks, combine PDF and general instructions.
- **Versioning Tasks**: When working on certificate versioning, include major-version-analysis instructions alongside certificate instructions.
- **New Features**: For entirely new features outside certificates/PDFs, rely primarily on general instructions.

This index ensures that Copilot applies the right set of rules to maintain consistency, follow best practices, and adhere to project-specific conventions.
