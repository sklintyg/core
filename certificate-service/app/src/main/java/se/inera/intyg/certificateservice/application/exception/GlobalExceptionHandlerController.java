package se.inera.intyg.certificateservice.application.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import se.inera.intyg.certificateservice.domain.common.exception.CertificateActionForbidden;
import se.inera.intyg.certificateservice.domain.common.exception.ConcurrentModificationException;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandlerController {

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException exception) {
    log.error("Bad request", exception);

    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .build();
  }

  @ExceptionHandler(CertificateActionForbidden.class)
  public ResponseEntity<String> handleCertificateActionForbidden(
      CertificateActionForbidden exception) {
    log.error("Forbidden", exception);

    return ResponseEntity
        .status(HttpStatus.FORBIDDEN)
        .build();
  }

  @ExceptionHandler(ConcurrentModificationException.class)
  public ResponseEntity<String> handleConcurrentModificationException(
      ConcurrentModificationException exception) {
    log.error("Conflict", exception);
    return ResponseEntity
        .status(HttpStatus.CONFLICT)
        .body("%s på enheten %s".formatted(
                exception.user().name().fullName(),
                exception.unit().name().name()
            )
        );
  }
}
