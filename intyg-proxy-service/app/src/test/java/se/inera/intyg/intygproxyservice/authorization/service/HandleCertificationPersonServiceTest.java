package se.inera.intyg.intygproxyservice.authorization.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import se.inera.intyg.intygproxyservice.integration.api.authorization.HandleCertificationPersonIntegrationRequest;
import se.inera.intyg.intygproxyservice.integration.api.authorization.HandleCertificationPersonIntegrationResponse;
import se.inera.intyg.intygproxyservice.integration.api.authorization.HandleCertificationPersonIntegrationService;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.Result;
import se.inera.intyg.intygproxyservice.logging.LogHashUtility;

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

  private static final HandleCertificationPersonIntegrationResponse RESPONSE = HandleCertificationPersonIntegrationResponse
      .builder()
      .result(Result.builder()
          .resultCode("CODE")
          .resultText("TEXT")
          .build()
      )
      .build();

  @Mock
  private HandleCertificationPersonIntegrationService handleCertificationPersonIntegrationService;
  @Mock
  private LogHashUtility logHashUtility;
  
  @InjectMocks
  private HandleCertificationPersonService handleCertificationPersonService;

  @Test
  void shouldThrowIllegalArgumentExceptionIfRequestIsNull() {
    assertThrows(IllegalArgumentException.class,
        () -> handleCertificationPersonService.handle(null));
  }

  @Test
  void shouldThrowIllegalArgumentExceptionIfPersonIdIsNull() {
    final var request = HandleCertificationPersonRequest.builder()
        .reason(REASON)
        .certificationId(CERTIFICATION_ID)
        .operation(OPERATION)
        .build();
    assertThrows(IllegalArgumentException.class,
        () -> handleCertificationPersonService.handle(request));
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
        () -> handleCertificationPersonService.handle(request));
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
        () -> handleCertificationPersonService.handle(request));
  }

  @Test
  void shouldThrowIllegalArgumentExceptionIfOperationIsNull() {
    final var request = HandleCertificationPersonRequest.builder()
        .personId(PERSON_ID)
        .reason(REASON)
        .certificationId(CERTIFICATION_ID)
        .build();
    assertThrows(IllegalArgumentException.class,
        () -> handleCertificationPersonService.handle(request));
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
        () -> handleCertificationPersonService.handle(request));
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
        () -> handleCertificationPersonService.handle(request));
  }

  @Nested
  class ValidRequest {

    @BeforeEach
    void setUp() {
      when(handleCertificationPersonIntegrationService.get(any())).thenReturn(RESPONSE);
    }

    @Test
    void shallReturnResult() {
      final var response = handleCertificationPersonService.handle(REQUEST);

      assertEquals(RESPONSE.getResult(), response.getResult());
    }

    @Test
    void shallSetPersonIdInRequest() {
      handleCertificationPersonService.handle(REQUEST);

      final var captor = ArgumentCaptor.forClass(
          HandleCertificationPersonIntegrationRequest.class);
      verify(handleCertificationPersonIntegrationService).get(captor.capture());

      assertEquals(REQUEST.getPersonId(), captor.getValue().getPersonId());
    }

    @Test
    void shallSetCertificationIdInRequest() {
      handleCertificationPersonService.handle(REQUEST);

      final var captor = ArgumentCaptor.forClass(
          HandleCertificationPersonIntegrationRequest.class);
      verify(handleCertificationPersonIntegrationService).get(captor.capture());

      assertEquals(REQUEST.getCertificationId(), captor.getValue().getCertificationId());
    }

    @Test
    void shallSetReasonInRequest() {
      handleCertificationPersonService.handle(REQUEST);

      final var captor = ArgumentCaptor.forClass(
          HandleCertificationPersonIntegrationRequest.class);
      verify(handleCertificationPersonIntegrationService).get(captor.capture());

      assertEquals(REQUEST.getReason(), captor.getValue().getReason());
    }

    @Test
    void shallSetOperationInRequest() {
      handleCertificationPersonService.handle(REQUEST);

      final var captor = ArgumentCaptor.forClass(
          HandleCertificationPersonIntegrationRequest.class);
      verify(handleCertificationPersonIntegrationService).get(captor.capture());

      assertEquals(REQUEST.getOperation(), captor.getValue().getOperation());
    }
  }
}