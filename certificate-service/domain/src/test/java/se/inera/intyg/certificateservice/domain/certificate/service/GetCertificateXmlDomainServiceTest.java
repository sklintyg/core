package se.inera.intyg.certificateservice.domain.certificate.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

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
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.common.exception.CertificateActionForbidden;

@ExtendWith(MockitoExtension.class)
class GetCertificateXmlDomainServiceTest {

  private static final CertificateId CERTIFICATE_ID = new CertificateId("certificateId");
  private static final ActionEvaluation ACTION_EVALUATION = ActionEvaluation.builder().build();
  public static final String XML = "XML";

  @Mock
  private Certificate certificate;
  @Mock
  private CertificateRepository certificateRepository;
  @Mock
  XmlGenerator xmlGenerator;

  @InjectMocks
  GetCertificateXmlDomainService getCertificateXmlDomainService;

  @BeforeEach
  void setUp() {
    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
  }

  @Nested
  class AllowedToRead {

    @BeforeEach
    void setup() {
      doReturn(true).when(certificate).allowTo(CertificateActionType.READ, ACTION_EVALUATION);
    }

    @Test
    void shallValidateIfAllowedToReadCertificate() {
      getCertificateXmlDomainService.get(CERTIFICATE_ID, ACTION_EVALUATION);
      verify(certificate).allowTo(CertificateActionType.READ, ACTION_EVALUATION);
    }

    @Test
    void shallReturnXmlFromXmlGenerator() {
      doReturn(XML).when(xmlGenerator).generate(certificate);

      final var response = getCertificateXmlDomainService.get(CERTIFICATE_ID, ACTION_EVALUATION);

      assertEquals(XML, response);
    }

  }

  @Test
  void shallThrowIfNotAllowedToRead() {
    doReturn(false).when(certificate).allowTo(CertificateActionType.READ, ACTION_EVALUATION);
    assertThrows(CertificateActionForbidden.class,
        () -> getCertificateXmlDomainService.get(CERTIFICATE_ID, ACTION_EVALUATION)
    );
  }
}