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
import se.inera.intyg.certificateservice.domain.certificate.model.Xml;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.certificate.service.XmlGeneratorCertificatesForCareWithQA;
import se.inera.intyg.certificateservice.domain.patient.model.CertificatesWithQARequest;

@ExtendWith(MockitoExtension.class)
class GetPatientCertificatesWithQAInternalDomainServiceTest {

  @Mock
  CertificateRepository certificateRepository;
  @Mock
  XmlGeneratorCertificatesForCareWithQA xmlGeneratorCertificatesForCareWithQA;
  @InjectMocks
  GetPatientCertificatesWithQAInternalDomainService getPatientCertificatesWithQAInternalDomainService;

  @Test
  void shallReturnXml() {
    final var expectedXml = new Xml("expectedXml");
    final var certificates = List.of(Certificate.builder().build(), Certificate.builder().build());
    final var request = CertificatesWithQARequest.builder().build();
    doReturn(certificates).when(certificateRepository).findByCertificatesWithQARequest(request);
    doReturn(expectedXml).when(xmlGeneratorCertificatesForCareWithQA).generate(certificates);

    final var actualXml = getPatientCertificatesWithQAInternalDomainService.get(request);
    assertEquals(expectedXml, actualXml);
  }
}
