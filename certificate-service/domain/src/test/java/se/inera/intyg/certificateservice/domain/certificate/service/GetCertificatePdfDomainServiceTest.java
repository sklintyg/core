package se.inera.intyg.certificateservice.domain.certificate.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.action.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.Pdf;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.common.exception.CertificateActionForbidden;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEvent;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEventType;
import se.inera.intyg.certificateservice.domain.event.service.CertificateEventDomainService;

@ExtendWith(MockitoExtension.class)
class GetCertificatePdfDomainServiceTest {

  private static final CertificateId CERTIFICATE_ID = new CertificateId("certificateId");
  private static final ActionEvaluation ACTION_EVALUATION = ActionEvaluation.builder().build();
  private static final byte[] PDF_DATA = "pdfData".getBytes();
  private static final String FILE_NAME = "fileName";
  private static final Pdf PDF = new Pdf(PDF_DATA, FILE_NAME);
  private static final String ADDITIONAL_INFO_TEXT = "additionalInfoText";

  @Mock
  private Certificate certificate;
  @Mock
  private CertificateRepository certificateRepository;

  @Mock
  private CertificateEventDomainService certificateEventDomainService;
  @Mock
  private PdfGenerator pdfGenerator;

  @InjectMocks
  GetCertificatePdfDomainService getCertificatePdfDomainService;

  @BeforeEach
  void setUp() {
    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
  }

  @Nested
  class AllowedToPrint {

    @BeforeEach
    void setUp() {
      doReturn(true).when(certificate).allowTo(CertificateActionType.PRINT, ACTION_EVALUATION);
    }

    @Test
    void shallValidateIfAllowedToPrintCertificate() {
      getCertificatePdfDomainService.get(CERTIFICATE_ID, ACTION_EVALUATION, ADDITIONAL_INFO_TEXT);
      verify(certificate).allowTo(CertificateActionType.PRINT, ACTION_EVALUATION);
    }

    @Test
    void shallUpdateCertificateMetadataIfCertificateIsDraft() {
      doReturn(true).when(certificate).isDraft();
      getCertificatePdfDomainService.get(CERTIFICATE_ID, ACTION_EVALUATION, ADDITIONAL_INFO_TEXT);
      verify(certificate).updateMetadata(ACTION_EVALUATION);
    }

    @Test
    void shallReturnResponseWithPdfFromGenerator() {
      doReturn(PDF).when(pdfGenerator).generate(certificate, ADDITIONAL_INFO_TEXT);

      final var response = getCertificatePdfDomainService.get(CERTIFICATE_ID, ACTION_EVALUATION,
          ADDITIONAL_INFO_TEXT);

      assertEquals(PDF, response);
    }
  }

  @Nested
  class NotAllowedToPrint {

    @Test
    void shallThrowIfNotAllowedToPrint() {
      doReturn(false).when(certificate).allowTo(CertificateActionType.PRINT, ACTION_EVALUATION);
      assertThrows(CertificateActionForbidden.class,
          () -> getCertificatePdfDomainService.get(CERTIFICATE_ID, ACTION_EVALUATION,
              ADDITIONAL_INFO_TEXT)
      );
    }

  }

  @Test
  void shallPublishGetCertificateEvent() {
    doReturn(true).when(certificate).allowTo(CertificateActionType.PRINT, ACTION_EVALUATION);
    getCertificatePdfDomainService.get(CERTIFICATE_ID, ACTION_EVALUATION, ADDITIONAL_INFO_TEXT);

    final var certificateEventCaptor = ArgumentCaptor.forClass(CertificateEvent.class);
    verify(certificateEventDomainService).publish(certificateEventCaptor.capture());

    assertAll(
        () -> assertEquals(CertificateEventType.PRINT, certificateEventCaptor.getValue().type()),
        () -> assertEquals(certificate, certificateEventCaptor.getValue().certificate()),
        () -> assertEquals(ACTION_EVALUATION, certificateEventCaptor.getValue().actionEvaluation()),
        () -> assertTrue(certificateEventCaptor.getValue().duration() >= 0)
    );
  }
}
