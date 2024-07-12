package se.inera.intyg.certificateservice.domain.certificate.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType.READ;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.common.exception.CertificateActionForbidden;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEvent;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEventType;
import se.inera.intyg.certificateservice.domain.event.service.CertificateEventDomainService;
import se.inera.intyg.certificateservice.domain.validation.model.ValidationResult;

@ExtendWith(MockitoExtension.class)
class ValidateCertificateDomainServiceTest {

  private static final CertificateId CERTIFICATE_ID = new CertificateId("certificateId");
  private static final ActionEvaluation ACTION_EVALUATION = ActionEvaluation.builder().build();
  private static final List<ElementData> ELEMENT_DATA_LIST = List.of(ElementData.builder().build());
  @Mock
  private CertificateRepository certificateRepository;
  @Mock
  private CertificateEventDomainService certificateEventDomainService;
  @Mock
  private Certificate certificate;
  @InjectMocks
  private ValidateCertificateDomainService validateCertificateDomainService;

  @BeforeEach
  void setUp() {
    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
  }

  @Test
  void shallValidateIfAllowedToReadCertificate() {
    doReturn(true).when(certificate).allowTo(READ, Optional.of(ACTION_EVALUATION));
    validateCertificateDomainService.validate(CERTIFICATE_ID, ELEMENT_DATA_LIST, ACTION_EVALUATION);
    verify(certificate).allowTo(READ, Optional.of(ACTION_EVALUATION));
  }

  @Test
  void shallThrowIfNotAllowedToRead() {
    doReturn(false).when(certificate).allowTo(READ, Optional.of(ACTION_EVALUATION));
    assertThrows(CertificateActionForbidden.class,
        () -> validateCertificateDomainService.validate(CERTIFICATE_ID, ELEMENT_DATA_LIST,
            ACTION_EVALUATION)
    );
  }

  @Test
  void shallReturnValidationResult() {
    final var expectedResult = ValidationResult.builder().build();

    doReturn(true).when(certificate).allowTo(READ, Optional.of(ACTION_EVALUATION));
    doReturn(expectedResult).when(certificate).validate(ELEMENT_DATA_LIST);

    final var actualResult = validateCertificateDomainService.validate(CERTIFICATE_ID,
        ELEMENT_DATA_LIST, ACTION_EVALUATION);
    assertEquals(expectedResult, actualResult);
  }

  @Test
  void shallPublicValidateCertificateEvent() {
    final var expectedResult = ValidationResult.builder().build();

    doReturn(true).when(certificate).allowTo(READ, Optional.of(ACTION_EVALUATION));
    doReturn(expectedResult).when(certificate).validate(ELEMENT_DATA_LIST);

    validateCertificateDomainService.validate(CERTIFICATE_ID, ELEMENT_DATA_LIST, ACTION_EVALUATION);

    final var certificateEventCaptor = ArgumentCaptor.forClass(CertificateEvent.class);
    verify(certificateEventDomainService).publish(certificateEventCaptor.capture());

    assertAll(
        () -> assertEquals(CertificateEventType.VALIDATED,
            certificateEventCaptor.getValue().type()),
        () -> assertEquals(certificate, certificateEventCaptor.getValue().certificate()),
        () -> assertEquals(ACTION_EVALUATION, certificateEventCaptor.getValue().actionEvaluation()),
        () -> assertTrue(certificateEventCaptor.getValue().duration() >= 0)
    );
  }

  @Test
  void shallIncludeReasonNotAllowedToException() {
    final var expectedReason = List.of("expectedReason");
    doReturn(false).when(certificate).allowTo(READ, Optional.of(ACTION_EVALUATION));
    doReturn(expectedReason).when(certificate)
        .reasonNotAllowed(READ, Optional.of(ACTION_EVALUATION));

    final var certificateActionForbidden = assertThrows(CertificateActionForbidden.class,
        () -> validateCertificateDomainService.validate(CERTIFICATE_ID, ELEMENT_DATA_LIST,
            ACTION_EVALUATION)
    );

    assertEquals(expectedReason, certificateActionForbidden.reason());
  }
}
