package se.inera.intyg.intygproxyservice.authorization.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.authorization.dto.HandleCertificationPersonRequest;
import se.inera.intyg.intygproxyservice.integration.api.authorization.GetHandleCertificationPersonIntegrationRequest;
import se.inera.intyg.intygproxyservice.integration.api.authorization.GetHandleCertificationPersonIntegrationResponse;
import se.inera.intyg.intygproxyservice.integration.api.authorization.GetHandleCertificationPersonIntegrationService;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.Result;

@ExtendWith(MockitoExtension.class)
class HandleCertificationPersonServiceTest {

  private static final String PERSON_ID = "PERSON_ID";
  private static final String CERTIFICATION_ID = "CERTIFICATION_ID";
  private static final String OPERATION = "OPERATION";
  private static final String REASON = "REASON";

  private static final HandleCertificationPersonRequest REQUEST = HandleCertificationPersonRequest
      .builder()
      .personId(PERSON_ID)
      .certificationId(CERTIFICATION_ID)
      .operation(OPERATION)
      .reason(REASON)
      .build();

  private static final GetHandleCertificationPersonIntegrationResponse RESPONSE = GetHandleCertificationPersonIntegrationResponse
      .builder()
      .result(Result.builder()
          .resultCode("CODE")
          .resultText("TEXT")
          .build()
      )
      .build();

  @Mock
  private GetHandleCertificationPersonIntegrationService getHandleCertificationPersonIntegrationService;

  @InjectMocks
  private HandleCertificationPersonService handleCertificationPersonService;

  @Test
  void shouldThrowIllegalArgumentExceptionIfRequestIsNull() {
    assertThrows(IllegalArgumentException.class, () -> handleCertificationPersonService.get(null));
  }

  @Test
  void shouldThrowIllegalArgumentExceptionIfPersonIdIsNull() {
    final var request = HandleCertificationPersonRequest.builder()
        .reason(REASON)
        .certificationId(CERTIFICATION_ID)
        .operation(OPERATION)
        .build();
    assertThrows(IllegalArgumentException.class,
        () -> handleCertificationPersonService.get(request));
  }

  @Test
  void shouldThrowIllegalArgumentExceptionIfPersonIdIsEmpty() {
    final var request = HandleCertificationPersonRequest.builder()
        .personId("")
        .reason(REASON)
        .certificationId(CERTIFICATION_ID)
        .operation(OPERATION)
        .build();
    assertThrows(IllegalArgumentException.class,
        () -> handleCertificationPersonService.get(request));
  }

  @Test
  void shouldThrowIllegalArgumentExceptionIfPersonIdIsBlank() {
    final var request = HandleCertificationPersonRequest.builder()
        .personId("   ")
        .reason(REASON)
        .certificationId(CERTIFICATION_ID)
        .operation(OPERATION)
        .build();
    assertThrows(IllegalArgumentException.class,
        () -> handleCertificationPersonService.get(request));
  }

  @Test
  void shouldThrowIllegalArgumentExceptionIfCertificationIdIsNull() {
    final var request = HandleCertificationPersonRequest.builder()
        .personId(PERSON_ID)
        .reason(REASON)
        .operation(OPERATION)
        .build();
    assertThrows(IllegalArgumentException.class,
        () -> handleCertificationPersonService.get(request));
  }

  @Test
  void shouldThrowIllegalArgumentExceptionIfCertificationIdIsEmpty() {
    final var request = HandleCertificationPersonRequest.builder()
        .personId(PERSON_ID)
        .reason(REASON)
        .certificationId("")
        .operation(OPERATION)
        .build();
    assertThrows(IllegalArgumentException.class,
        () -> handleCertificationPersonService.get(request));
  }

  @Test
  void shouldThrowIllegalArgumentExceptionIfCertificationIdIsBlank() {
    final var request = HandleCertificationPersonRequest.builder()
        .personId(PERSON_ID)
        .certificationId("    ")
        .reason(REASON)
        .operation(OPERATION)
        .build();
    assertThrows(IllegalArgumentException.class,
        () -> handleCertificationPersonService.get(request));
  }

  @Test
  void shouldThrowIllegalArgumentExceptionIfOperationIsNull() {
    final var request = HandleCertificationPersonRequest.builder()
        .personId(PERSON_ID)
        .reason(REASON)
        .certificationId(CERTIFICATION_ID)
        .build();
    assertThrows(IllegalArgumentException.class,
        () -> handleCertificationPersonService.get(request));
  }

  @Test
  void shouldThrowIllegalArgumentExceptionIfOperationIdIsEmpty() {
    final var request = HandleCertificationPersonRequest.builder()
        .personId(PERSON_ID)
        .reason(REASON)
        .certificationId(CERTIFICATION_ID)
        .operation("")
        .build();
    assertThrows(IllegalArgumentException.class,
        () -> handleCertificationPersonService.get(request));
  }

  @Test
  void shouldThrowIllegalArgumentExceptionIfOperationIsBlank() {
    final var request = HandleCertificationPersonRequest.builder()
        .personId(PERSON_ID)
        .certificationId(CERTIFICATION_ID)
        .reason(REASON)
        .operation("  ")
        .build();
    assertThrows(IllegalArgumentException.class,
        () -> handleCertificationPersonService.get(request));
  }

  @Test
  void shouldThrowIllegalArgumentExceptionIfReasonIsNull() {
    final var request = HandleCertificationPersonRequest.builder()
        .personId(PERSON_ID)
        .certificationId(CERTIFICATION_ID)
        .operation(OPERATION)
        .build();
    assertThrows(IllegalArgumentException.class,
        () -> handleCertificationPersonService.get(request));
  }

  @Test
  void shouldThrowIllegalArgumentExceptionIfReasonIsEmpty() {
    final var request = HandleCertificationPersonRequest.builder()
        .personId(PERSON_ID)
        .certificationId(CERTIFICATION_ID)
        .operation(OPERATION)
        .build();
    assertThrows(IllegalArgumentException.class,
        () -> handleCertificationPersonService.get(request));
  }

  @Test
  void shouldThrowIllegalArgumentExceptionIfReasonIsBlank() {
    final var request = HandleCertificationPersonRequest.builder()
        .personId(PERSON_ID)
        .certificationId(CERTIFICATION_ID)
        .reason("  ")
        .operation(OPERATION)
        .build();
    assertThrows(IllegalArgumentException.class,
        () -> handleCertificationPersonService.get(request));
  }


  @Nested
  class ValidRequest {

    @BeforeEach
    void setUp() {
      when(getHandleCertificationPersonIntegrationService.get(any())).thenReturn(RESPONSE);
    }

    @Test
    void shallReturnResult() {
      final var response = handleCertificationPersonService.get(REQUEST);

      assertEquals(RESPONSE.getResult(), response.getResult());
    }

    @Test
    void shallSetPersonIdInRequest() {
      handleCertificationPersonService.get(REQUEST);

      final var captor = ArgumentCaptor.forClass(
          GetHandleCertificationPersonIntegrationRequest.class);
      verify(getHandleCertificationPersonIntegrationService).get(captor.capture());

      assertEquals(REQUEST.getPersonId(), captor.getValue().getPersonId());
    }

    @Test
    void shallSetCertificationIdInRequest() {
      handleCertificationPersonService.get(REQUEST);

      final var captor = ArgumentCaptor.forClass(
          GetHandleCertificationPersonIntegrationRequest.class);
      verify(getHandleCertificationPersonIntegrationService).get(captor.capture());

      assertEquals(REQUEST.getCertificationId(), captor.getValue().getCertificationId());
    }

    @Test
    void shallSetReasonInRequest() {
      handleCertificationPersonService.get(REQUEST);

      final var captor = ArgumentCaptor.forClass(
          GetHandleCertificationPersonIntegrationRequest.class);
      verify(getHandleCertificationPersonIntegrationService).get(captor.capture());

      assertEquals(REQUEST.getReason(), captor.getValue().getReason());
    }

    @Test
    void shallSetOperationInRequest() {
      handleCertificationPersonService.get(REQUEST);

      final var captor = ArgumentCaptor.forClass(
          GetHandleCertificationPersonIntegrationRequest.class);
      verify(getHandleCertificationPersonIntegrationService).get(captor.capture());

      assertEquals(REQUEST.getOperation(), captor.getValue().getOperation());
    }
  }
}