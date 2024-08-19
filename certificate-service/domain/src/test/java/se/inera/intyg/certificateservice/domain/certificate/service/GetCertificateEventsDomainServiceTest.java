package se.inera.intyg.certificateservice.domain.certificate.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateEvent;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateEventType;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.common.exception.CertificateActionForbidden;

@ExtendWith(MockitoExtension.class)
class GetCertificateEventsDomainServiceTest {

  private static final CertificateId CERTIFICATE_ID = new CertificateId("C_ID");
  public static final List<CertificateEvent> EVENT = List.of(
      CertificateEvent.builder()
          .certificateId(CERTIFICATE_ID)
          .timestamp(LocalDateTime.now())
          .build()
  );
  private static final ActionEvaluation ACTION_EVALUATION = ActionEvaluation.builder().build();

  @Mock
  private CertificateRepository certificateRepository;

  @Mock
  private GetCertificateEventsOfTypeDomainService getCertificateEventsOfTypeDomainService;

  @Mock
  private Certificate certificate;

  @InjectMocks
  GetCertificateEventsDomainService getCertificateEventsDomainService;

  @BeforeEach
  void setup() {
    when(certificateRepository.getById(CERTIFICATE_ID))
        .thenReturn(certificate);
  }

  @Nested
  class AllowedToRead {

    @BeforeEach
    void setup() {
      when(certificate.allowTo(CertificateActionType.READ, Optional.of(ACTION_EVALUATION)))
          .thenReturn(true);
      when(getCertificateEventsOfTypeDomainService.events(
          any(Certificate.class),
          any(CertificateEventType.class))
      ).thenReturn(EVENT);
    }

    @Test
    void shouldReturnEventsFromService() {
      final var result = getCertificateEventsDomainService.get(CERTIFICATE_ID, ACTION_EVALUATION);

      assertEquals(CertificateEventType.values().length, result.size());
    }

  }

  @Nested
  class NotAllowedToRead {

    @BeforeEach
    void setup() {
      when(certificate.allowTo(CertificateActionType.READ, Optional.of(ACTION_EVALUATION)))
          .thenReturn(false);
    }

    @Test
    void shouldThrowException() {
      assertThrows(
          CertificateActionForbidden.class,
          () -> getCertificateEventsDomainService.get(CERTIFICATE_ID, ACTION_EVALUATION)
      );
    }
  }
}