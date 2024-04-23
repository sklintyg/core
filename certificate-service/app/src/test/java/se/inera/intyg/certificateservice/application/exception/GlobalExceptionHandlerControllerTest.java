package se.inera.intyg.certificateservice.application.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnit.ALFA_MEDICINCENTRUM;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUser.AJLA_DOKTOR;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import se.inera.intyg.certificateservice.domain.common.exception.CertificateActionForbidden;
import se.inera.intyg.certificateservice.domain.common.exception.ConcurrentModificationException;

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
        new CertificateActionForbidden("Error!", Collections.emptyList())
    );
    assertEquals(HttpStatusCode.valueOf(403), response.getStatusCode());
  }

  @Test
  void shallIncludeReasonForCertificateActionForbidden() {
    final var expectedReason = List.of("expectedReason");
    final var response = globalExceptionHandlerController.handleCertificateActionForbidden(
        new CertificateActionForbidden("Error!", expectedReason)
    );
    assertEquals(expectedReason.get(0), Objects.requireNonNull(response.getBody()).getMessage());
  }

  @Test
  void shallIncludeMultipleReasonsForCertificateActionForbidden() {
    final var expectedReason = List.of("expectedReason", "expectedReason");
    final var response = globalExceptionHandlerController.handleCertificateActionForbidden(
        new CertificateActionForbidden("Error!", expectedReason)
    );
    assertEquals("expectedReason expectedReason",
        Objects.requireNonNull(response.getBody()).getMessage());
  }

  @Test
  void shallReturnStatusCode409ForConcurrentModificationException() {
    final var response = globalExceptionHandlerController.handleConcurrentModificationException(
        new ConcurrentModificationException(
            "Error!",
            AJLA_DOKTOR,
            ALFA_MEDICINCENTRUM
        )
    );
    assertEquals(HttpStatusCode.valueOf(409), response.getStatusCode());
  }
}
