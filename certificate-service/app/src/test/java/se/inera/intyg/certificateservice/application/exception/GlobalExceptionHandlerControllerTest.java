package se.inera.intyg.certificateservice.application.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import se.inera.intyg.certificateservice.domain.common.exception.CertificateActionForbidden;

class GlobalExceptionHandlerControllerTest {

  private GlobalExceptionHandlerController globalExceptionHandlerController;

  @BeforeEach
  void setUp() {
    globalExceptionHandlerController = new GlobalExceptionHandlerController();
  }

  @Test
  void shallReturnStatusCode400ForIllegalArgumentException() {
    final var response = globalExceptionHandlerController.handleIllegalArgumentException(
        new IllegalArgumentException("Error!")
    );
    assertEquals(HttpStatusCode.valueOf(400), response.getStatusCode());
  }

  @Test
  void shallReturnStatusCode403ForCertificateActionForbidden() {
    final var response = globalExceptionHandlerController.handleCertificateActionForbidden(
        new CertificateActionForbidden("Error!")
    );
    assertEquals(HttpStatusCode.valueOf(403), response.getStatusCode());
  }
}