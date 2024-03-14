package se.inera.intyg.certificateservice.application.certificate.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.FK7211_CERTIFICATE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.XML;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateConstants.CERTIFICATE_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7211_TYPE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_ALLERGIMOTTAGNINGEN_ID;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateInternalXmlResponse;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;

@ExtendWith(MockitoExtension.class)
class GetCertificateInternalXmlServiceTest {

  private static final String XML_BASE64_ENCODED = Base64.getEncoder()
      .encodeToString(XML.xml().getBytes(StandardCharsets.UTF_8));

  @Mock
  private CertificateRepository certificateRepository;
  @InjectMocks
  private GetCertificateInternalXmlService getCertificateInternalXmlService;

  @Test
  void shallReturnResponse() {
    final var expectedResponse = GetCertificateInternalXmlResponse.builder()
        .certificateId(CERTIFICATE_ID)
        .certificateType(FK7211_TYPE.type())
        .unitId(ALFA_ALLERGIMOTTAGNINGEN_ID)
        .xml(XML_BASE64_ENCODED)
        .build();

    doReturn(FK7211_CERTIFICATE).when(certificateRepository)
        .getById(new CertificateId(CERTIFICATE_ID));

    final var actualResponse = getCertificateInternalXmlService.get(CERTIFICATE_ID);

    assertEquals(expectedResponse, actualResponse);
  }
}