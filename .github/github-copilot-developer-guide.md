# GitHub Copilot Developer Guide

This guide is aimed at developers in the project and explains the structure, content, and use of the `.github` folder. The folder is used to customize GitHub Copilot for our project by providing guidelines, tools, specifications, and templates. This ensures that Copilot generates code and suggestions that follow our coding conventions, architectural principles, and best practices.

## Purpose and Overview

The `.github` folder contains documentation and instructions that help Copilot understand the project's context. This includes general guidelines, tools for specific tasks (e.g., certificate management), analyses, specifications, and templates. By using these files, Copilot can provide more precise and project-tailored suggestions, reducing errors and improving productivity.

The structure is organized for easy navigation:
- **Root Files**: Overall index and instructions.
- **instructions/**: Tool-specific instructions and guides.
- **specs/**: Specifications and templates for features and tests.

All files are written in Markdown for readability and easy updating.

## Root Files in .github

### copilot-instructions.md
- **What it is**: The main file with general instructions for Copilot, including coding conventions, design principles, security, and tools.
- **Usage**: This is the core of the customization. Copilot reads this file to follow our rules (e.g., use `final var`, streams instead of loops, @PerformanceLogging).
- **How to update**: Update when new principles are added or existing ones change. Ensure it matches the project's guidelines.

### copilot-agent-index.md
- **What it is**: An index file that lists available agents (e.g., CodeArchitect, FeatureImplementer) and their roles.
- **Usage**: Helps Copilot understand which agents can be used for different tasks. Refer to this when you need to orchestrate multiple agents for a feature.
- **How to update**: Add new agents if new roles are introduced in the project.

### copilot-tools-index.md
- **What it is**: A list of available tools (e.g., /certificate, /feature) and how they work.
- **Usage**: Used to activate specific tools in Copilot. For example, type "/certificate" to get instructions on certificate management.
- **How to update**: Add new tools if new instruction files are created.

## instructions/ Folder

This folder contains tool-specific instructions and guides. Each file is linked to a tool that can be activated in Copilot.

### certificate-copilot-instructions.instructions.md
- **What it is**: Instructions specific to certificate-related code (creation, modification, versioning, rules, testing).
- **Usage**: Activated with the `/certificate` tool. Provides guidelines for certificate APIs, models, and PDF generation.
- **How to update**: Update when new certificate rules, config or value types are added.

### feature-copilot-instructions.instructions.md
- **What it is**: Instructions for orchestrating the creation of new features using multiple agents.
- **Usage**: Activated with the `/feature` tool. The process includes requirements gathering, specification, guide, implementation, tests, and final validation.
- **How to update**: Adjust the steps if the workflow changes.

### guides/ Subfolder
- **Purpose**: Stores implementation guides that complement the tools. A guide can be used to structure and divide complex tasks into iterations so that Copilot can handle them better. A guide uses a specification and looks through the code to define what and how needs to be done in what order.
- **Usage**: Currently empty – fill with guides like "certificate-implementation-guide.md" for step-by-step instructions.
- **How to update**: Create new guides based on needs, e.g., for integration tests or PDF mapping.

### integration-test-copilot-instructions.instructions.md
- **What it is**: Guidelines for creating and maintaining integration tests (structure, setup, best practices).
- **Usage**: Activated with the `/integration-test` tool. Helps Copilot generate tests that follow our conventions.
- **How to update**: Update when the test framework or practices change.

### major-version-analysis.instructions.md
- **What it is**: Instructions for analyzing differences between certificate versions.
- **Usage**: Linked to `/certificateVersionAnalysis`. Focuses on identifying common and unique elements.
- **How to update**: Adjust based on new versions of certificates.

### pdf-copilot-instructions.instructions.md
- **What it is**: Guidelines for mapping certificate data to PDF configurations and ensuring proper generation.
- **Usage**: Activated with the `/certificatePDF` tool. Used for PDF-related code in services like certificate-print-service.
- **How to update**: Update when PDF templates or generators change.


### analysis/ Subfolder
- **Purpose**: Stores analyses made by Copilot to complete a coding task.

## specs/ Folder

This folder contains specifications for features and tests, as well as templates for creating new ones. A specification summarizes the requirements and can be used to create a detailed guide for implementing the feature.

### templates/ Subfolder
- **Purpose**: Stores templates for creating new specifications.
- **Files**:
  - **certificate-specification-template.spec.md**: Template for certificate specifications.
  - **general-feature-spec-template.md**: Template for general features.
  - **integration-test-specification-template.spec.md**: Template for integration test specifications.
- **Usage**: Copy and fill in these templates when creating new specifications. This ensures consistency.
- **How to update**: Improve the templates if new fields are needed (e.g., security requirements).

## How to Use This for Customized Copilot Usage

1. **Activate Tools**: In Copilot, type commands like "/certificate" to load relevant instructions.
2. **Follow Guidelines**: Copilot will suggest code based on these files – always review to ensure it matches our principles.
3. **Contribute**: If you add new code or features, update relevant files (e.g., add a new guide in guides/).
4. **Test**: After updates, test Copilot by using tools and see if suggestions improve.
5. **Feedback**: If something is missing, suggest additions to this guide or other files.

This structure helps keep Copilot customized and effective. Contact the team if you have questions or need help with updates.

*Last updated: November 30, 2025*
