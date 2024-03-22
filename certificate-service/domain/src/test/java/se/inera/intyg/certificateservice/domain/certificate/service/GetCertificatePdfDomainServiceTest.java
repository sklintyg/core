package se.inera.intyg.certificateservice.domain.certificate.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

@ExtendWith(MockitoExtension.class)
class GetCertificatePdfDomainServiceTest {

  private static final CertificateId CERTIFICATE_ID = new CertificateId("certificateId");
  private static final ActionEvaluation ACTION_EVALUATION = ActionEvaluation.builder().build();
  private static final byte[] PDF_DATA = "pdfData".getBytes();
  private static final String FILE_NAME = "fileName";
  private static final Pdf PDF = new Pdf(PDF_DATA, FILE_NAME);

  @Mock
  private Certificate certificate;
  @Mock
  private CertificateRepository certificateRepository;

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
      getCertificatePdfDomainService.get(CERTIFICATE_ID, ACTION_EVALUATION);
      verify(certificate).allowTo(CertificateActionType.PRINT, ACTION_EVALUATION);
    }

    @Test
    void shallUpdateCertificateMetadata() {
      getCertificatePdfDomainService.get(CERTIFICATE_ID, ACTION_EVALUATION);
      verify(certificate).updateMetadata(ACTION_EVALUATION);
    }

    @Test
    void shallReturnResponseWithPdfFromGenerator() throws IOException {
      doReturn(PDF).when(pdfGenerator).generate(certificate);

      final var response = getCertificatePdfDomainService.get(CERTIFICATE_ID, ACTION_EVALUATION);

      assertEquals(PDF, response);
    }
  }

  @Nested
  class NotAllowedToPrint {

    @Test
    void shallThrowIfNotAllowedToPrint() {
      doReturn(false).when(certificate).allowTo(CertificateActionType.PRINT, ACTION_EVALUATION);
      assertThrows(CertificateActionForbidden.class,
          () -> getCertificatePdfDomainService.get(CERTIFICATE_ID, ACTION_EVALUATION)
      );
    }

  }
}