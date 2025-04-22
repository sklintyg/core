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
- Use streams insteads of loops for collections to improve readability and reduce boilerplate code.

## Controller Design Principles

- Use `@RestController` for defining RESTful APIs.
- Group related endpoints under a common `@RequestMapping` path.
- Use HTTP verbs (`@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping`) appropriately to
  represent the operation's intent.
- Include meaningful path variables and request bodies in endpoints for clarity and usability.
- Use `@PerformanceLogging` annotations to log significant actions with `eventAction` and
  `eventType` for observability.

## Service Design Principles

- Use dedicated service classes for business logic to maintain separation of concerns.
- Ensure service methods are descriptive and focused on a single responsibility.
- Use `@RequiredArgsConstructor` for dependency injection to ensure immutability of injected fields.

## DTO Design Principles

- Use specific DTOs for request and response payloads to ensure clear API contracts.
- Avoid exposing internal domain models directly in API responses.
- Use descriptive names for DTOs to reflect their purpose (e.g., `CreateCertificateRequest`,
  `GetCertificateResponse`).

## Logging and Observability

- Use `@PerformanceLogging` annotations to capture performance metrics and log significant events.
- Include `eventAction` and `eventType` in logs to provide context for the logged actions.
- Use MDC (Mapped Diagnostic Context) constants for consistent logging across the application.
- Publish domain events for significant actions to maintain traceability and observability.

## Error Handling

- Ensure exception messages are descriptive and provide relevant context (e.g., IDs, reasons).
- Avoid exposing sensitive information in error messages or logs.

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

