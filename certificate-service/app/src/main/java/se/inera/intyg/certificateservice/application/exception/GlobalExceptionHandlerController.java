package se.inera.intyg.certificateservice.application.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import se.inera.intyg.certificateservice.domain.common.exception.CertificateActionForbidden;
import se.inera.intyg.certificateservice.domain.common.exception.ConcurrentModificationException;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandlerController {

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException exception) {
    log.warn("Bad request", exception);

    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .build();
  }

  @ExceptionHandler(CertificateActionForbidden.class)
  public ResponseEntity<String> handleCertificateActionForbidden(
      CertificateActionForbidden exception) {
    log.warn("Forbidden", exception);

    return ResponseEntity
        .status(HttpStatus.FORBIDDEN)
        .body(String.join(" ", exception.reason()));
  }

  @ExceptionHandler(ConcurrentModificationException.class)
  public ResponseEntity<String> handleConcurrentModificationException(
      ConcurrentModificationException exception) {
    log.warn("Conflict", exception);
    return ResponseEntity
        .status(HttpStatus.CONFLICT)
        .body("%s på enheten %s".formatted(
                exception.user().name().fullName(),
                exception.unit().name().name()
            )
        );
  }
}
