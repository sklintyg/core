package se.inera.intyg.certificateservice.domain.certificate.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import java.util.List;
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
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateXml;
import se.inera.intyg.certificateservice.domain.certificate.model.Revision;
import se.inera.intyg.certificateservice.domain.certificate.model.Xml;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.common.exception.CertificateActionForbidden;

@ExtendWith(MockitoExtension.class)
class GetCertificateXmlDomainServiceTest {

  private static final CertificateId CERTIFICATE_ID = new CertificateId("certificateId");
  private static final Revision REVISION = new Revision(99);
  private static final ActionEvaluation ACTION_EVALUATION = ActionEvaluation.builder().build();
  public static final Xml XML = new Xml("XML");
  public static final CertificateXml CERTIFICATE_XML = CertificateXml.builder()
      .certificateId(CERTIFICATE_ID)
      .revision(REVISION)
      .xml(XML)
      .build();

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
      doReturn(CERTIFICATE_ID).when(certificate).id();
      doReturn(REVISION).when(certificate).revision();
    }

    @Test
    void shallValidateIfAllowedToReadCertificate() {
      getCertificateXmlDomainService.get(CERTIFICATE_ID, ACTION_EVALUATION);
      verify(certificate).allowTo(CertificateActionType.READ, ACTION_EVALUATION);
    }

    @Test
    void shallReturnResponseWithXMLFromGeneratorIfXmlInCertificateIsNull() {
      doReturn(XML).when(xmlGenerator).generate(certificate);

      final var response = getCertificateXmlDomainService.get(CERTIFICATE_ID, ACTION_EVALUATION);

      assertEquals(CERTIFICATE_XML, response);
    }

    @Test
    void shallReturnResponseWithXMLFromCertificateIfXmlInCertificateIsNotNull() {
      doReturn(XML).when(certificate).xml();

      final var response = getCertificateXmlDomainService.get(CERTIFICATE_ID, ACTION_EVALUATION);

      assertEquals(CERTIFICATE_XML, response);
      verifyNoInteractions(xmlGenerator);
    }

    @Test
    void shallUpdateCertificateMetadata() {
      getCertificateXmlDomainService.get(CERTIFICATE_ID, ACTION_EVALUATION);
      verify(certificate).updateMetadata(ACTION_EVALUATION);
    }
  }

  @Test
  void shallThrowIfNotAllowedToRead() {
    doReturn(false).when(certificate).allowTo(CertificateActionType.READ, ACTION_EVALUATION);
    assertThrows(CertificateActionForbidden.class,
        () -> getCertificateXmlDomainService.get(CERTIFICATE_ID, ACTION_EVALUATION)
    );
  }

  @Test
  void shallIncludeReasonNotAllowedToException() {
    final var expectedReason = List.of("expectedReason");
    doReturn(false).when(certificate).allowTo(CertificateActionType.READ, ACTION_EVALUATION);
    doReturn(expectedReason).when(certificate)
        .reasonNotAllowed(CertificateActionType.READ, ACTION_EVALUATION);

    final var certificateActionForbidden = assertThrows(CertificateActionForbidden.class,
        () -> getCertificateXmlDomainService.get(CERTIFICATE_ID, ACTION_EVALUATION));

    assertEquals(expectedReason, certificateActionForbidden.reason());
  }
}