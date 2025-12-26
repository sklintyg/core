package se.inera.intyg.certificateservice.application.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import se.inera.intyg.certificateservice.domain.common.exception.CertificateActionForbidden;
import se.inera.intyg.certificateservice.domain.common.exception.CitizenCertificateForbidden;
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
  public ResponseEntity<ApiError> handleCertificateActionForbidden(
      CertificateActionForbidden exception) {
    log.warn("Forbidden - %s".formatted(String.join(" - ", exception.reason())), exception);

    return ResponseEntity
        .status(HttpStatus.FORBIDDEN)
        .body(
            ApiError.builder()
                .message(String.join(" ", exception.reason()))
                .build()
        );
  }

  @ExceptionHandler(ConcurrentModificationException.class)
  public ResponseEntity<ApiError> handleConcurrentModificationException(
      ConcurrentModificationException exception) {
    log.warn("Conflict", exception);
    return ResponseEntity
        .status(HttpStatus.CONFLICT)
        .body(
            ApiError.builder()
                .message(String.join(" ", "%s på enheten %s".formatted(
                    exception.user().name().fullName(),
                    exception.unit().name().name())))
                .build()
        );
  }

  @ExceptionHandler(CitizenCertificateForbidden.class)
  public ResponseEntity<ApiError> handleCitizenCertificateForbidden(
      CitizenCertificateForbidden exception) {
    log.warn("Forbidden", exception);

    return ResponseEntity
        .status(HttpStatus.FORBIDDEN)
        .body(
            ApiError.builder()
                .message(exception.getMessage())
                .build()
        );
  }
}