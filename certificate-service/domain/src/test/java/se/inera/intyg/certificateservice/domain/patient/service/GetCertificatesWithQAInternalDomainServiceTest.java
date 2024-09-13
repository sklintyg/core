package se.inera.intyg.certificateservice.domain.patient.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.Xml;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.certificate.service.XmlGeneratorCertificatesForCareWithQA;
import se.inera.intyg.certificateservice.domain.patient.model.CertificatesWithQARequest;

@ExtendWith(MockitoExtension.class)
class GetCertificatesWithQAInternalDomainServiceTest {

  @Mock
  CertificateRepository certificateRepository;
  @Mock
  XmlGeneratorCertificatesForCareWithQA xmlGeneratorCertificatesForCareWithQA;
  @InjectMocks
  GetCertificatesWithQAInternalDomainService getCertificatesWithQAInternalDomainService;

  @Test
  void shallReturnXml() {
    final var expectedXml = new Xml("expectedXml");
    final var certificates = List.of(Certificate.builder().build(), Certificate.builder().build());
    final var request = CertificatesWithQARequest.builder()
        .certificateIds(
            List.of(new CertificateId("ID1"), new CertificateId("ID2")))
        .build();
 
    doReturn(certificates).when(certificateRepository).findByIds(request.certificateIds());
    doReturn(expectedXml).when(xmlGeneratorCertificatesForCareWithQA).generate(certificates);

    final var actualXml = getCertificatesWithQAInternalDomainService.get(request);
    assertEquals(expectedXml, actualXml);
  }
}
