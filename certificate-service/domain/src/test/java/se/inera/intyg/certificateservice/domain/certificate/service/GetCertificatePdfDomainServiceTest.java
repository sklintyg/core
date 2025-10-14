package se.inera.intyg.certificateservice.domain.certificate.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType.UPDATE;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType;
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
  @Mock
  private PdfGeneratorProvider pdfGeneratorProvider;

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
      doReturn(true).when(certificate)
          .allowTo(CertificateActionType.PRINT, Optional.of(ACTION_EVALUATION));
      doReturn(pdfGenerator)
          .when(pdfGeneratorProvider).provider(certificate);
    }

    @Test
    void shallValidateIfAllowedToPrintCertificate() {
      getCertificatePdfDomainService.get(CERTIFICATE_ID, ACTION_EVALUATION, ADDITIONAL_INFO_TEXT);
      verify(certificate).allowTo(CertificateActionType.PRINT, Optional.of(ACTION_EVALUATION));
    }

    @Test
    void shallUpdateCertificateMetadataIfDraftAndHasUpdateRights() {
      doReturn(true).when(certificate).allowTo(UPDATE, Optional.of(ACTION_EVALUATION));
      doReturn(true).when(certificate).isDraft();
      getCertificatePdfDomainService.get(CERTIFICATE_ID, ACTION_EVALUATION, ADDITIONAL_INFO_TEXT);
      verify(certificate).updateMetadata(ACTION_EVALUATION);
    }

    @Test
    void shallNotUpdateCertificateMetadataIfDraftAndHasNoUpdateRights() {
      doReturn(false).when(certificate).allowTo(UPDATE, Optional.of(ACTION_EVALUATION));
      doReturn(true).when(certificate).isDraft();
      getCertificatePdfDomainService.get(CERTIFICATE_ID, ACTION_EVALUATION, ADDITIONAL_INFO_TEXT);
      verify(certificate, never()).updateMetadata(ACTION_EVALUATION);
    }

    @Test
    void shallNotUpdateCertificateMetadataIfNotDraft() {
      doReturn(false).when(certificate).isDraft();
      getCertificatePdfDomainService.get(CERTIFICATE_ID, ACTION_EVALUATION, ADDITIONAL_INFO_TEXT);
      verify(certificate, never()).updateMetadata(ACTION_EVALUATION);
    }

    @Test
    void shallReturnResponseWithPdfFromGenerator() {
      final var options = PdfGeneratorOptions.builder()
          .additionalInfoText(ADDITIONAL_INFO_TEXT)
          .citizenFormat(false)
          .hiddenElements(Collections.emptyList())
          .build();

      doReturn(PDF).when(pdfGenerator)
          .generate(certificate, options);

      final var response = getCertificatePdfDomainService.get(CERTIFICATE_ID, ACTION_EVALUATION,
          ADDITIONAL_INFO_TEXT);

      assertEquals(PDF, response);
    }
  }

  @Nested
  class NotAllowedToPrint {

    @Test
    void shallThrowIfNotAllowedToPrint() {
      doReturn(false).when(certificate)
          .allowTo(CertificateActionType.PRINT, Optional.of(ACTION_EVALUATION));
      assertThrows(CertificateActionForbidden.class,
          () -> getCertificatePdfDomainService.get(CERTIFICATE_ID, ACTION_EVALUATION,
              ADDITIONAL_INFO_TEXT)
      );
    }

  }

  @Test
  void shallPublishGetCertificateEvent() {
    doReturn(pdfGenerator)
        .when(pdfGeneratorProvider).provider(certificate);
    doReturn(true).when(certificate)
        .allowTo(CertificateActionType.PRINT, Optional.of(ACTION_EVALUATION));
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

  @Test
  void shallIncludeReasonNotAllowedToException() {
    final var expectedReason = List.of("expectedReason");
    doReturn(false).when(certificate)
        .allowTo(CertificateActionType.PRINT, Optional.of(ACTION_EVALUATION));
    doReturn(expectedReason).when(certificate)
        .reasonNotAllowed(CertificateActionType.PRINT, Optional.of(ACTION_EVALUATION));

    final var certificateActionForbidden = assertThrows(CertificateActionForbidden.class,
        () -> getCertificatePdfDomainService.get(CERTIFICATE_ID, ACTION_EVALUATION,
            ADDITIONAL_INFO_TEXT)
    );

    assertEquals(expectedReason, certificateActionForbidden.reason());
  }
}

