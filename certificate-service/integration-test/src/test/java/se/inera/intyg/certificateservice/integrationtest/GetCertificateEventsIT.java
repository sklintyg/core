package se.inera.intyg.certificateservice.integrationtest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.ANONYMA_REACT_ATTILA_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_HUDMOTTAGNINGEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_VARDCENTRAL_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.AJLA_DOCTOR_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.ALVA_VARDADMINISTRATOR_DTO;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.customGetCertificateEventsRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.customTestabilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultGetCertificateEventsRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultTestablilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.certificateId;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateEventTypeDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO;

public abstract class GetCertificateEventsIT extends BaseIntegrationIT {

  protected abstract String type();

  protected abstract String typeVersion();

  @Test
  @DisplayName("Om intyget är utfärdat på samma mottagning skall dess händelser returneras")
  void shallReturnCertificateEventsIfUnitIsSubUnitAndOnSameUnit() {
    final var testCertificates = testabilityApi.addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var response = api.getCertificateEvents(
        defaultGetCertificateEventsRequest(),
        certificateId(testCertificates)
    );

    assertNotNull(
        response.getBody().getEvents(),
        "Should return certificate events"
    );
  }

  @Test
  @DisplayName("Om intyget är ett utkast ska ett event returneras")
  void shallReturnCertificateEventsForDraft() {
    final var testCertificates = testabilityApi.addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var response = api.getCertificateEvents(
        defaultGetCertificateEventsRequest(),
        certificateId(testCertificates)
    );

    assertAll(
        () -> assertEquals(1, response.getBody().getEvents().size()),
        () -> assertEquals(CertificateEventTypeDTO.CREATED,
            response.getBody().getEvents().get(0).getType(),
            "Should return certificate event created for draft")
    );
  }

  @Test
  @DisplayName("Om intyget är signerat ska tre event returneras")
  void shallReturnCertificateEventsForSignedCertificate() {
    final var testCertificates = testabilityApi.addCertificates(
        defaultTestablilityCertificateRequest(
            type(),
            typeVersion(),
            CertificateStatusTypeDTO.SIGNED
        )
    );

    final var response = api.getCertificateEvents(
        defaultGetCertificateEventsRequest(),
        certificateId(testCertificates)
    );

    assertAll(
        () -> assertEquals(3, response.getBody().getEvents().size()),
        () -> assertEquals(CertificateEventTypeDTO.CREATED,
            response.getBody().getEvents().get(0).getType(),
            "Should return certificate event created for signed certificate"),
        () -> assertEquals(CertificateEventTypeDTO.SIGNED,
            response.getBody().getEvents().get(1).getType(),
            "Should return certificate event signed for signed certificate"),
        () -> assertEquals(CertificateEventTypeDTO.AVAILABLE_FOR_PATIENT,
            response.getBody().getEvents().get(2).getType(),
            "Should return certificate event available for patient for signed certificate")
    );
  }

  @Test
  @DisplayName("Om intyget är utfärdat på mottagning men på samma vårdenhet skall dess händelser returneras")
  void shallReturnCertificateEventsIfUnitIsCareUnitAndOnSameCareUnit() {
    final var testCertificates = testabilityApi.addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var response = api.getCertificateEvents(
        customGetCertificateEventsRequest()
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .build(),
        certificateId(testCertificates)
    );

    assertNotNull(
        response.getBody().getEvents(),
        "Should return certificate events"
    );
  }

  @Test
  @DisplayName("Om intyget är utfärdat på samma vårdenhet skall dess händelser returneras")
  void shallReturnCertificateEventsIfUnitIsCareUnitAndIssuedOnSameCareUnit() {
    final var testCertificates = testabilityApi.addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion())
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .build()
    );

    final var response = api.getCertificateEvents(
        customGetCertificateEventsRequest()
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .build(),
        certificateId(testCertificates)
    );

    assertNotNull(
        response.getBody().getEvents(),
        "Should return certificate events"
    );
  }

  @Test
  @DisplayName("Om intyget är utfärdat på en annan mottagning skall felkod 403 (FORBIDDEN) returneras")
  void shallReturn403IfUnitIsSubUnitAndNotOnSameUnit() {
    final var testCertificates = testabilityApi.addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var response = api.getCertificateEvents(
        customGetCertificateEventsRequest().unit(ALFA_HUDMOTTAGNINGEN_DTO).build(),
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

    final var response = api.getCertificateEvents(
        customGetCertificateEventsRequest()
            .careUnit(ALFA_VARDCENTRAL_DTO)
            .unit(ALFA_VARDCENTRAL_DTO)
            .build(),
        certificateId(testCertificates)
    );

    assertEquals(403, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Vårdadministratör - Om intyget är utfärdat på en patient som har skyddade personuppgifter skall felkod 403 (FORBIDDEN) returneras")
  void shallReturn403IfPatientIsProtectedPersonAndUserIsCareAdmin() {
    final var testCertificates = testabilityApi.addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion())
            .patient(ANONYMA_REACT_ATTILA_DTO)
            .build()
    );

    final var response = api.getCertificateEvents(
        customGetCertificateEventsRequest()
            .user(ALVA_VARDADMINISTRATOR_DTO)
            .build(),
        certificateId(testCertificates)
    );

    assertEquals(403, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Läkare - Om intyget är utfärdat på en patient som har skyddade personuppgifter skall dess händelser returneras")
  void shallReturnCertificateEventsIfPatientIsProtectedPersonAndUserIsDoctor() {
    final var testCertificates = testabilityApi.addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion())
            .patient(ANONYMA_REACT_ATTILA_DTO)
            .build()
    );

    final var response = api.getCertificateEvents(
        customGetCertificateEventsRequest()
            .user(AJLA_DOCTOR_DTO)
            .build(),
        certificateId(testCertificates)
    );

    assertNotNull(
        response.getBody().getEvents(),
        "Should return certificate events"
    );
  }
}
