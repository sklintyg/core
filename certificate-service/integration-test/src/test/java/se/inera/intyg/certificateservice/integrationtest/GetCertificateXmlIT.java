package se.inera.intyg.certificateservice.integrationtest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.ANONYMA_REACT_ATTILA_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_HUDMOTTAGNINGEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_VARDCENTRAL_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.ALVA_VARDADMINISTRATOR_DTO;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.customGetCertificateXmlRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.customTestabilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultGetCertificateXmlRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultTestablilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.certificate;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.certificateId;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.certificateXmlReponse;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.decodeXml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import se.inera.intyg.certificateservice.application.common.dto.UserDTO;

public abstract class GetCertificateXmlIT extends BaseIntegrationIT {

  protected abstract String type();

  protected abstract String typeVersion();

  @Test
  @DisplayName("Om intyget är utfärdat på samma mottagning skall det returneras")
  void shallReturnCertificateXMLIfUnitIsSubUnitAndIssuedOnSameSubUnit() {
    final var testCertificates = testabilityApi.addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var response = api.getCertificateXml(
        defaultGetCertificateXmlRequest(),
        certificateId(testCertificates)
    );

    assertAll(
        () -> assertTrue(decodeXml(certificateXmlReponse(response).getXml()).contains("Läkare"),
            () -> "Expected 'Läkare' to be part of xml: '%s'"
                .formatted(decodeXml(certificateXmlReponse(response).getXml()))),
        () -> assertEquals(certificate(testCertificates).getMetadata().getId(),
            certificateXmlReponse(response).getCertificateId()),
        () -> assertEquals(certificate(testCertificates).getMetadata().getVersion(),
            certificateXmlReponse(response).getVersion())
    );
  }

  @Test
  @DisplayName("Om intyget är utfärdat på mottagning men på samma vårdenhet skall det returneras")
  void shallReturnCertificateXMLIfUnitIsCareUnitAndOnSameCareUnit() {
    final var testCertificates = testabilityApi.addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var response = api.getCertificateXml(
        customGetCertificateXmlRequest()
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .build(),
        certificateId(testCertificates)
    );

    assertAll(
        () -> assertTrue(decodeXml(certificateXmlReponse(response).getXml()).contains("Läkare"),
            () -> "Expected 'Läkare' to be part of xml: '%s'"
                .formatted(decodeXml(certificateXmlReponse(response).getXml()))),
        () -> assertEquals(certificate(testCertificates).getMetadata().getId(),
            certificateXmlReponse(response).getCertificateId()),
        () -> assertEquals(certificate(testCertificates).getMetadata().getVersion(),
            certificateXmlReponse(response).getVersion())
    );
  }

  @Test
  @DisplayName("Om intyget är utfärdat på samma vårdenhet skall det returneras")
  void shallReturnCertificateXMLIfUnitIsCareUnitAndIssuedOnSameCareUnit() {
    final var testCertificates = testabilityApi.addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion())
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .build()
    );

    final var response = api.getCertificateXml(
        customGetCertificateXmlRequest()
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .build(),
        certificateId(testCertificates)
    );

    assertAll(
        () -> assertTrue(decodeXml(certificateXmlReponse(response).getXml()).contains("Läkare"),
            () -> "Expected 'Läkare' to be part of xml: '%s'"
                .formatted(decodeXml(certificateXmlReponse(response).getXml()))),
        () -> assertEquals(certificate(testCertificates).getMetadata().getId(),
            certificateXmlReponse(response).getCertificateId()),
        () -> assertEquals(certificate(testCertificates).getMetadata().getVersion(),
            certificateXmlReponse(response).getVersion())
    );
  }

  @Test
  @DisplayName("Om intyget är utfärdat på en annan mottagning skall felkod 403 (FORBIDDEN) returneras")
  void shallReturn403IfUnitIsSubUnitAndNotOnSameUnit() {
    final var testCertificates = testabilityApi.addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var response = api.getCertificateXml(
        customGetCertificateXmlRequest()
            .unit(ALFA_HUDMOTTAGNINGEN_DTO)
            .build(),
        certificateId(testCertificates)
    );

    assertEquals(403, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Om intyget är utfärdat på en annan vårdenhet skall felkod 403 (FORBIDDEN) returneras")
  void shallReturn403IfUnitIsCareUnitAndNotOnCareUnit() {
    final var testCertificates = testabilityApi.addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var response = api.getCertificateXml(
        customGetCertificateXmlRequest()
            .careUnit(ALFA_VARDCENTRAL_DTO)
            .unit(ALFA_VARDCENTRAL_DTO)
            .build(),
        certificateId(testCertificates)
    );

    assertEquals(403, response.getStatusCode().value());
  }

  @ParameterizedTest
  @DisplayName("Om intyget är utfärdat på en patient som har skyddade personuppgifter skall felkod 403 (FORBIDDEN) returneras")
  @MethodSource("rolesNoAccessToProtectedPerson")
  void shallReturn403IfPatientIsProtectedPerson(UserDTO userDTO) {
    final var testCertificates = testabilityApi.addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion())
            .patient(ANONYMA_REACT_ATTILA_DTO)
            .build()
    );

    final var response = api.getCertificateXml(
        customGetCertificateXmlRequest()
            .user(ALVA_VARDADMINISTRATOR_DTO)
            .build(),
        certificateId(testCertificates)
    );

    assertEquals(403, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Läkare - Om intyget är utfärdat på en patient som har skyddade personuppgifter skall det returneras")
  void shallReturnCertificateXMLIfPatientIsProtectedPersonAndUserIsDoctor() {
    final var testCertificates = testabilityApi.addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion())
            .patient(ANONYMA_REACT_ATTILA_DTO)
            .build()
    );

    final var response = api.getCertificateXml(
        defaultGetCertificateXmlRequest(),
        certificateId(testCertificates)
    );

    assertAll(
        () -> assertTrue(decodeXml(certificateXmlReponse(response).getXml()).contains("Läkare"),
            () -> "Expected 'Läkare' to be part of xml: '%s'"
                .formatted(decodeXml(certificateXmlReponse(response).getXml()))),
        () -> assertEquals(certificate(testCertificates).getMetadata().getId(),
            certificateXmlReponse(response).getCertificateId()),
        () -> assertEquals(certificate(testCertificates).getMetadata().getVersion(),
            certificateXmlReponse(response).getVersion())
    );
  }
}
