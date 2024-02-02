package se.inera.intyg.certificateservice.application.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import se.inera.intyg.certificateservice.domain.exception.CertificateActionForbidden;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandlerController {

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException exception) {
    log.error("Bad request", exception);

    return ResponseEntity
        .status(400)
        .build();
  }

  @ExceptionHandler(CertificateActionForbidden.class)
  public ResponseEntity<String> handleCertificateActionForbidden(
      CertificateActionForbidden exception) {
    log.error("Forbidden", exception);

    return ResponseEntity
        .status(403)
        .build();
  }
}
