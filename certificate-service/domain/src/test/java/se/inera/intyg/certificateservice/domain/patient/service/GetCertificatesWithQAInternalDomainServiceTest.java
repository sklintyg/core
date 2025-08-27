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
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalCertificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Xml;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.certificate.service.XmlGeneratorCertificatesForCareWithQA;

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
    final List<Certificate> certificates = List.of(
        MedicalCertificate.builder().build(), MedicalCertificate.builder().build());
    final var certificateIds = List.of(new CertificateId("ID1"), new CertificateId("ID2"));

    doReturn(certificates).when(certificateRepository).findByIds(certificateIds);
    doReturn(expectedXml).when(xmlGeneratorCertificatesForCareWithQA).generate(certificates);

    final var actualXml = getCertificatesWithQAInternalDomainService.get(certificateIds);
    assertEquals(expectedXml, actualXml);
  }
}