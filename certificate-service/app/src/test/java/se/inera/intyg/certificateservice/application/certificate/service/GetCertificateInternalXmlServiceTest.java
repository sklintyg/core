package se.inera.intyg.certificateservice.application.certificate.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCertificateRecipientDTO.CERTIFICATE_RECIPIENT_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCertificateRevokedDTO.REVOKED_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.ATHENA_REACT_ANDERSSON_PERSON_ID_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonWebcertUnitDTO.ALFA_ALLERGIMOTTAGNINGEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonWebcertUnitDTO.ALFA_REGIONEN_DTO;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.FK7210_CERTIFICATE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.XML;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.fk7210CertificateBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateConstants.CERTIFICATE_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7210_TYPE;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateInternalXmlResponse;
import se.inera.intyg.certificateservice.application.certificate.service.converter.CertificateUnitConverter;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.certificate.service.XmlGenerator;

@ExtendWith(MockitoExtension.class)
class GetCertificateInternalXmlServiceTest {

  private static final String XML_BASE64_ENCODED = Base64.getEncoder()
      .encodeToString(XML.xml().getBytes(StandardCharsets.UTF_8));

  @Mock
  private CertificateRepository certificateRepository;
  @Mock
  private CertificateUnitConverter certificateUnitConverter;
  @Mock
  private XmlGenerator xmlGenerator;
  @InjectMocks
  private GetCertificateInternalXmlService getCertificateInternalXmlService;

  @Test
  void shallReturnResponse() {
    final var expectedResponse = GetCertificateInternalXmlResponse.builder()
        .certificateId(CERTIFICATE_ID)
        .certificateType(FK7210_TYPE.type())
        .unit(ALFA_ALLERGIMOTTAGNINGEN_DTO)
        .careProvider(ALFA_REGIONEN_DTO)
        .xml(XML_BASE64_ENCODED)
        .revoked(REVOKED_DTO)
        .recipient(CERTIFICATE_RECIPIENT_DTO)
        .patientId(ATHENA_REACT_ANDERSSON_PERSON_ID_DTO)
        .build();

    doReturn(FK7210_CERTIFICATE).when(certificateRepository)
        .getById(new CertificateId(CERTIFICATE_ID));
    doReturn(ALFA_ALLERGIMOTTAGNINGEN_DTO).when(certificateUnitConverter).convert(
        eq(FK7210_CERTIFICATE.certificateMetaData().issuingUnit()), any()
    );

    final var actualResponse = getCertificateInternalXmlService.get(CERTIFICATE_ID);

    assertAll(
        () -> assertEquals(expectedResponse.getCertificateId(), actualResponse.getCertificateId()),
        () -> assertEquals(expectedResponse.getCertificateType(),
            actualResponse.getCertificateType()),
        () -> assertEquals(expectedResponse.getUnit(), actualResponse.getUnit()),
        () -> assertEquals(expectedResponse.getXml(), actualResponse.getXml()),
        () -> assertEquals(expectedResponse.getRecipient(), actualResponse.getRecipient()),
        () -> assertEquals(expectedResponse.getPatientId(), actualResponse.getPatientId()),
        () -> assertEquals(expectedResponse.getCareProvider(), actualResponse.getCareProvider()),
        () -> assertNotNull(actualResponse.getRevoked())
    );
  }

  @Test
  void shallReturnResponseWhenXmlMissingFromCertificate() {
    final var expectedResponse = GetCertificateInternalXmlResponse.builder()
        .certificateId(CERTIFICATE_ID)
        .certificateType(FK7210_TYPE.type())
        .unit(ALFA_ALLERGIMOTTAGNINGEN_DTO)
        .careProvider(ALFA_REGIONEN_DTO)
        .xml(XML_BASE64_ENCODED)
        .revoked(REVOKED_DTO)
        .recipient(CERTIFICATE_RECIPIENT_DTO)
        .patientId(ATHENA_REACT_ANDERSSON_PERSON_ID_DTO)
        .build();

    final var certificateWithoutXml = fk7210CertificateBuilder()
        .xml(null)
        .build();

    doReturn(certificateWithoutXml).when(certificateRepository)
        .getById(new CertificateId(CERTIFICATE_ID));
    doReturn(XML).when(xmlGenerator).generate(certificateWithoutXml, false);
    doReturn(ALFA_ALLERGIMOTTAGNINGEN_DTO).when(certificateUnitConverter).convert(
        eq(FK7210_CERTIFICATE.certificateMetaData().issuingUnit()), any()
    );

    final var actualResponse = getCertificateInternalXmlService.get(CERTIFICATE_ID);

    assertAll(
        () -> assertEquals(expectedResponse.getCertificateId(), actualResponse.getCertificateId()),
        () -> assertEquals(expectedResponse.getCertificateType(),
            actualResponse.getCertificateType()),
        () -> assertEquals(expectedResponse.getUnit(), actualResponse.getUnit()),
        () -> assertEquals(expectedResponse.getXml(), actualResponse.getXml()),
        () -> assertEquals(expectedResponse.getRecipient(), actualResponse.getRecipient()),
        () -> assertEquals(expectedResponse.getPatientId(), actualResponse.getPatientId()),
        () -> assertEquals(expectedResponse.getCareProvider(), actualResponse.getCareProvider()),
        () -> assertNotNull(actualResponse.getRevoked())
    );
  }
}
