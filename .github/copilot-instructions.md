# General Instructions for GitHub Copilot

This document can be used to give GitHub Copilot context but also for GitHub Copilot to verify that
the developer has not missed some of these rules.

## Coding Conventions

- Always use `final var` for local variables when applicable to ensure immutability and readability.
- Use `@Value` annotations for configuration properties to inject values from the environment.
- Prefer `Optional` for handling nullable values to avoid `null` checks.
- Use `LocalDateTime.now(ZoneId.systemDefault())` for consistent timestamp generation.
- Use static imports for constants and utility methods to improve readability.
- Use builder patterns for constructing complex objects to enhance clarity and maintainability.
- Use streams instead of loops for collections to improve readability and reduce boilerplate code.
- **Constants**: Define large amounts of constants in dedicated classes (e.g., `MdcLogConstants`)
  and use static imports for cleaner code.
- **Constructor Injection**: Prefer constructor injection over field injection for better
  testability and immutability.
- **Private Constructors**: Add private constructors to utility classes to prevent instantiation.

## Package Organization Principles

- **Layered Architecture**: Organize code into clear layers: `api` (controllers), `application` (
  services), `domain` (business logic), `logging` (cross-cutting concerns).
- **Feature-Based Packages**: Group related functionality together (e.g., `person`, `organization`,
  `certificate`).
- **Separation of Concerns**: Keep controllers, services, DTOs, and domain models in separate
  packages.
- **Test Structure**: Mirror main package structure in test directories for easy navigation.

## Controller Design Principles

- Use `@RestController` for defining RESTful APIs.
- Group related endpoints under a common `@RequestMapping` path.
- Use HTTP verbs (`@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping`) appropriately to
  represent the operation's intent.
- Include meaningful path variables and request bodies in endpoints for clarity and usability.
- Use `@PerformanceLogging` annotations to log significant actions with `eventAction` and
  `eventType` for observability.
- **Exception Handling**: Wrap service exceptions in `ResponseStatusException` with appropriate HTTP
  status codes and descriptive messages.
- **Path Variables**: Use `UUID` for entity identifiers in path variables to ensure strong typing.
- **Method Documentation**: Add JavaDoc comments for non-trivial endpoints, especially those with
  side effects.

## Service Design Principles

- Use dedicated service classes for business logic to maintain separation of concerns.
- Ensure service methods are descriptive and focused on a single responsibility.
- Use `@RequiredArgsConstructor` for dependency injection to ensure immutability of injected fields.
- **Return Types**: Use `Optional<T>` for methods that may not find results instead of returning
  null.
- **Domain Objects**: Use strongly-typed domain objects (e.g., `TerminationId`) instead of primitive
  types for better type safety.

## DTO Design Principles

- Use specific DTOs for request and response payloads to ensure clear API contracts.
- Avoid exposing internal domain models directly in API responses.
- Use descriptive names for DTOs to reflect their purpose (e.g., `CreateCertificateRequest`,
  `GetCertificateResponse`).
- **Naming Convention**: Use suffixes like `DTO`, `Request`, `Response` to clearly indicate the
  purpose of data transfer objects.

## Logging and Observability

- Use `@PerformanceLogging` annotations to capture performance metrics and log significant events.
- Include `eventAction` and `eventType` in logs to provide context for the logged actions.
- Use MDC (Mapped Diagnostic Context) constants for consistent logging across the application.
- Publish domain events for significant actions to maintain traceability and observability.
- **MDC Pattern**: Use `MdcCloseableMap` with try-with-resources for automatic MDC cleanup.
- **Log Constants**: Define logging constants in dedicated classes and use static imports.
- **Structured Logging**: Use consistent event types (`EVENT_TYPE_ACCESSED`, `EVENT_TYPE_CREATION`,
  `EVENT_TYPE_CHANGE`, etc.) for better log analysis.

## Annotation Patterns

- **Performance Logging**: Always include `eventAction` and `eventType` parameters in
  `@PerformanceLogging` annotations.
- **Component Scanning**: Use `@Component`, `@Service`, `@Repository` appropriately for Spring
  component scanning.
- **Aspect Configuration**: Use `@Aspect` and `@Component` together for cross-cutting concerns like
  logging.
- **Lombok Usage**: Prefer `@Slf4j` for logging, `@RequiredArgsConstructor` for dependency
  injection.

## Error Handling

- Ensure exception messages are descriptive and provide relevant context (e.g., IDs, reasons).
- Avoid exposing sensitive information in error messages or logs.
- Error handling should follow fail fast principle, if we can not proceed with a request, we should
  throw an exception as early as possible
- **Controller Exception Handling**: Use `ResponseStatusException` with appropriate HTTP status
  codes in controllers.
- **Service Exception Handling**: Let service methods throw domain-specific exceptions that
  controllers can translate.
- **Exception Chaining**: Preserve original exceptions as causes when wrapping in new exceptions.
- **Global Exception Handlers**: Use `@RestControllerAdvice` with `@ExceptionHandler` methods for
  consistent error responses across the application.
- **Custom Domain Exceptions**: Create domain-specific runtime exceptions (e.g.,
  `ConcurrentModificationException`, `EraseException`) that carry relevant context.
- **Exception Response DTOs**: Use structured error response objects (e.g., `ApiError`) with builder
  pattern for consistent error formatting.
- **Utility Class Protection**: Always add private constructors to utility classes that throw
  `IllegalStateException` with descriptive messages like "Utility class".
- **Null Validation**: Use `IllegalArgumentException` for null parameter validation with clear
  messages like "Cannot be null!".
- **Exception Logging**: Log exceptions at appropriate levels - use `log.warn()` for client errors (
  4xx) and `log.error()` for server errors (5xx).
- **HTTP Status Mapping**:
    - `BAD_REQUEST (400)` for `IllegalArgumentException` and validation errors
    - `NOT_FOUND (404)` for missing resources with `.orElseThrow()` patterns
    - `FORBIDDEN (403)` for authorization/permission violations
    - `CONFLICT (409)` for concurrent modification scenarios
- **Exception Context**: Include relevant domain objects (user, unit, etc.) in custom exceptions
  using `@Getter` for structured error responses.

## Testing Conventions

- **Test Naming**: Use descriptive test class names ending with `Test` (e.g.,
  `TerminationControllerTest`).
- **Test Structure**: Mirror main package structure in test directories.
- **Test Categories**: Separate unit tests, integration tests, and testability endpoints clearly.

## Security Principles

- **Input Validation**: Always validate and sanitize user inputs to prevent injection attacks.
- **Authentication and Authorization**: Use robust authentication mechanisms and enforce role-based
  access control (RBAC) for sensitive operations.
- **Sensitive Data Handling**: Avoid logging sensitive information such as passwords, tokens, or
  personal data. Use encryption for sensitive data at rest and in transit.
- **Dependency Management**: Regularly update dependencies to address known vulnerabilities. Use
  tools like OWASP Dependency-Check to identify risks.
- **Error Messages**: Avoid exposing stack traces or internal details in error messages to prevent
  information leakage.
- **Security Headers**: Use HTTP security headers (e.g., `Content-Security-Policy`,
  `X-Content-Type-Options`, `Strict-Transport-Security`) to protect against common web
  vulnerabilities.
- **Rate Limiting**: Implement rate limiting to prevent abuse of APIs and brute-force attacks.
- **Audit Logging**: Maintain detailed audit logs for critical actions and access attempts for
  traceability.

## Additional Design Principles

- **Idempotency**: Ensure that APIs are idempotent where applicable, especially for `PUT` and
  `DELETE` operations.
- **Graceful Degradation**: Design systems to handle partial failures gracefully without affecting
  the entire application.
- **Configuration Management**: Store configuration securely avoid hardcoding sensitive
  configurations.
- **Scalability**: Design services to scale horizontally to handle increased load efficiently.

## Copilot Concepts
If user writes `/concepts` list the concepts in an easy-to-read format.

- **SPEC**: A specification is a summary of requirements for a feature or a task.
- **GUIDE**: A guide is a detailed step-by-step instruction set that Copilot can follow to implement a feature or task. It combines the information from a specification with additional context from the codebase to create a clear implementation plan.
- **ANALYSIS**: An analysis is a detailed examination of a specific aspect of the codebase or requirements to inform decision-making during implementation. It helps identify patterns, differences, or areas for improvement.
- **INSTRUCTION**: An instruction is a set of guidelines or rules that Copilot should follow when generating code. Instructions are written for reoccurring tasks to ensure consistency and adherence to best practices. Each instruction is linked to a tool. When the tool is called the instruction is invoked. Each instruction needs a specification with requirements.
- **AGENT**: An agent is a specialized role that focuses on a specific aspect of the development process. Each agent has a defined responsibility and set of skills to contribute to the overall implementation of features or tasks. Agents work together to complete complex tasks by leveraging their unique expertise. Agents can be called using `@name`.
- **TOOL**: A tool is a command that a user can invoke to apply specialized instructions for specific tasks. Each tool is linked to an instruction file that contains guidelines for Copilot to follow when generating code related to that task. Tools help ensure that Copilot adheres to best practices and coding standards for particular areas of development. A tool can be called using `/name`.

## Available Copilot Tools

When working on specific tasks, you can invoke the following tools to apply specialized instructions. When a user invokes a tool (e.g., by typing "/certificate"), the agent should:

1. Mention which file regulates the tool.
2. Ask if the user wants to proceed with applying those instructions.
3. If the user wants to proceed then the agent should go in the instruction and apply the relevant guidelines to the task at hand.
4. If it is a listing tool like /tools and /agents you should just list the available tools or agents instead.

Available tools:

- `/certificate`: Regulated by `.github/instructions/certificate-copilot-instructions.instructions.md`. Applies certificate-specific instructions for creating, modifying, and versioning certificate models, elements, configurations, rules, and testing.
When the `/certificate` tool is invoked, start with a general message to the user like: "Hi! I'll assist with certificate-specific instructions for creating, modifying, and versioning certificate models, elements, configurations, rules, and testing. Should we proceed?"

- `/certificate-version-analysis`: Regulated by `.github/instructions/major-version-analysis.instructions.md`. Applies instructions for analyzing differences between certificate versions to identify common and unique elements.
When the `/certificate-version-analysis` tool is invoked, start with a general message to the user like: "Hi! I'll help analyze differences between certificate versions to identify common and unique elements. Should we proceed?"

- `/certificate-pdf`: Regulated by `.github/instructions/pdf-copilot-instructions.instructions.md`. Applies guidelines for mapping certificate data to PDF configurations and ensuring proper PDF generation.
When the `/certificate-pdf` tool is invoked, start with a general message to the user like: "Hi! I'll guide you on mapping certificate data to PDF configurations and ensuring proper PDF generation. Should we proceed?"

- `/integration-test`: Regulated by `.github/instructions/integration-test-copilot-instructions.instructions.md`. Applies guidelines for creating and maintaining integration tests, including test structure, setup, and best practices.
When the `/integration-test` tool is invoked, start with a general message to the user like: "Hi! I'll assist with creating and maintaining integration tests, including test structure, setup, and best practices. Should we proceed?"

- `/feature`: Regulated by `.github/instructions/feature-copilot-instructions.instructions.md`. Orchestrates the creation of a complete feature using multiple agents.
When the `/feature` tool is invoked, start with a general message to the user like: "Hi! I'll help you implement a new feature. The process will go: [Gather requirements] -> [Create specification] -> [Create implementation guide]  -> [Implement feature] -> [Create tests] -> [Final validation]. Should we start with step 1?"

- `/template`: Creates a spec or guide from a template. It asks the user for the kind of template (spec or guide), the task it should be created for, and then depending on the kind, either requirements (for spec) or a spec file (for guide).
When the `/template` tool is invoked, start with a general message to the user like: "Hi! I'll help you create a spec or guide from a template. Should we proceed?"

To list all available tools, use the command `/tools`.

To list all available agents, use the command `/agents`.

Do not read any context if you receive a /xxx command where xxx is a word. You need to go fast so only look in this file if you find the tool. Otherwise, ask user, are you looking for a tool? Here are the tools available: list tools. 
When receiving a tool command, always start with the welcome message. After user has confirmed it wants to invoke tool then you go on to reading the instructions file. If the user does not invoke any specific tool, but the task relates to one of the above areas, the agent should suggest using the relevant tool to ensure the appropriate instructions are applied.

# Copilot agents

Agents are specialized roles that focus on specific aspects of the development process. Each agent has a defined responsibility and set of skills to contribute to the overall implementation of features or tasks. Agents work together to complete complex tasks by leveraging their unique expertise. Agents can be called using @name.

Available agents
- `CodeArchitect`: Designs high-level structure and architecture.
- `FeatureImplementer`: Writes functional code based on requirements.
- `DocWriter`: Creates documentation and code comments.
- `TestCreator`: Generates unit and integration tests.
- `RefactorBot`: Improves code quality and maintainability.
- `SpecCreator`: Creates specifications using templates and requirements.
- `RequirementsGatherer`: Gathers and structures requirements.
- `FeatureGuideCreator`: Creates feature implementation guides.
- Roles are defined in `.github/copilot-agent-index.md`.