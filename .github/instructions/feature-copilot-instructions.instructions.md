# Feature Tool Instructions

## Overview
The `/feature` tool is a composite tool that orchestrates the creation of a complete feature, including specification, implementation, and testing. It leverages the `RequirementsGatherer`, `SpecCreator`, `FeatureGuideCreator`, `FeatureImplementer`, and `TestCreator` agents to deliver a fully realized feature based on user requirements. The process is iterative, using a feature implementation guide README to provide clarity and structure throughout development.

IMPORTANT:
- Make sure you follow step by step the instructions provided in this file
- Do not start gathering context or looking through files before you get to step 3 of the workflow
- Make sure you use the correct agents which are specified in `.github/copilot-agent-index.md`

## Workflow
1. **Gather Requirements**:
   - Invoke the `RequirementsGatherer` agent to gather and structure the requirements for the feature.
   - The RequirementsGatherer will prompt the user for detailed requirements, including functional requirements, user stories, technical constraints, and any other relevant details.
   - Ensure the requirements are clear and comprehensive to serve as input for the SpecCreator agent.

2. **Create Specification**:
   - Invoke the `SpecCreator` agent using the provided requirements.
   - The SpecCreator will generate a detailed feature specification file based on the `general-feature-spec-template.md` template.
   - Save the generated spec file in an appropriate location (e.g., `.github/specs/` or project-specific specs directory).
   - Confirm with the user that the spec is acceptable before proceeding.

3. **Create Feature Implementation Guide**:
   - Invoke the `FeatureGuideCreator` agent to generate a README document outlining the development plan, iterations, milestones, and setup instructions for the feature.
   - The guide should start with sketching the high-level steps for implementing the feature (e.g., gather requirements, create spec, implement code, create tests, validate).
   - Include sections for overview, setup, development phases, testing, and contribution guidelines.
   - Note that the guide may need to read existing code to understand the context and architecture for proper implementation.
   - Save the guide in the feature's directory or a central location for easy access.
   - Use this guide to iterate through the development process, ensuring the developer has a clear roadmap.

4. **Implement the Feature**:
   - Invoke the `FeatureImplementer` agent, providing the generated spec as input.
   - The FeatureImplementer will create the necessary code files, following the project's coding standards and architecture principles.
   - Implement the feature in the appropriate modules/packages, ensuring modularity and reusability.
   - Validate the implementation by checking for errors and ensuring it aligns with the spec.
   - Iterate as needed based on the feature implementation guide.

5. **Create Tests**:
   - Invoke the `TestCreator` agent, using the spec and implemented code as input.
   - The TestCreator will generate unit and integration tests for the feature.
   - Ensure high test coverage and include mocks/fixtures where appropriate.
   - Integrate the tests into the project's test structure.
   - Iterate on testing as per the guide.

6. **Final Validation**:
   - Run the tests to verify the implementation.
   - Provide a summary of the created files, including the spec, implementation, and tests.
   - Offer to make any adjustments based on user feedback.
   - Update the feature implementation guide with final notes if necessary.

## Usage Guidelines
- Always confirm with the user at key points (e.g., after spec generation) to ensure alignment.
- If any step fails or requires clarification, pause and seek user input.
- Maintain separation of concerns by using the appropriate agent for each phase.
- Document any assumptions or decisions made during the process.

## Dependencies
- Relies on the `RequirementsGatherer`, `SpecCreator`, `FeatureGuideCreator`, `FeatureImplementer`, and `TestCreator` agents.
- Requires the `general-feature-spec-template.md` template for spec generation.
