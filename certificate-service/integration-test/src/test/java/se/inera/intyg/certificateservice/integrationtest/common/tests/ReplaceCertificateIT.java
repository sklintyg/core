package se.inera.intyg.certificateservice.integrationtest.common.tests;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO.SIGNED;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.ANONYMA_REACT_ATTILA_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_HUDMOTTAGNINGEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_VARDCENTRAL_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.ALVA_VARDADMINISTRATOR_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.ajlaDoktorDtoBuilder;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.customReplaceCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.customTestabilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultReplaceCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultTestablilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.certificateId;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.relation;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.replaceCertificateResponse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.integrationtest.common.setup.BaseIntegrationIT;

public abstract class ReplaceCertificateIT extends BaseIntegrationIT {


  @Test
  @DisplayName("Om intyget är utfärdat på samma mottagning skall det gå att ersätta")
  void shallSuccessfullyReplaceIfUnitIsSubUnitAndIssuedOnSameSubUnit() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED)
    );

    final var response = api().replaceCertificate(
        defaultReplaceCertificateRequest(),
        certificateId(testCertificates)
    );

    assertAll(
        () -> assertNotNull(
            relation(replaceCertificateResponse(response)).getParent()
            , "Should add parent to replaced certificate"),
        () -> assertEquals(certificateId(testCertificates),
            relation(replaceCertificateResponse(response)).getParent().getCertificateId())
    );
  }

  @Test
  @DisplayName("Om intyget är utfärdat på mottagning men på samma vårdenhet skall det gå att ersätta")
  void shallSuccessfullyReplaceIfUnitIsCareUnitAndOnSameCareUnit() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED)
    );

    final var response = api().replaceCertificate(
        defaultReplaceCertificateRequest(),
        certificateId(testCertificates)
    );

    assertAll(
        () -> assertNotNull(
            relation(replaceCertificateResponse(response)).getParent()
            , "Should add parent to replaced certificate"),
        () -> assertEquals(certificateId(testCertificates),
            relation(replaceCertificateResponse(response)).getParent().getCertificateId())
    );
  }

  @Test
  @DisplayName("Om intyget är utfärdat på samma vårdenhet skall det gå att ersätta")
  void shallSuccessfullyReplaceIfUnitIsCareUnitAndIssuedOnSameCareUnit() {
    final var testCertificates = testabilityApi().addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion(), SIGNED)
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .build()
    );

    final var response = api().replaceCertificate(
        customReplaceCertificateRequest()
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .build(),
        certificateId(testCertificates)
    );

    assertAll(
        () -> assertNotNull(
            relation(replaceCertificateResponse(response)).getParent()
            , "Should add parent to replaced certificate"),
        () -> assertEquals(certificateId(testCertificates),
            relation(replaceCertificateResponse(response)).getParent().getCertificateId())
    );
  }

  @Test
  @DisplayName("Om intyget är utfärdat på en annan mottagning skall felkod 403 (FORBIDDEN) returneras")
  void shallReturn403IfUnitIsSubUnitAndNotOnSameUnit() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED)
    );

    final var response = api().replaceCertificate(
        customReplaceCertificateRequest()
            .unit(ALFA_HUDMOTTAGNINGEN_DTO)
            .build(),
        certificateId(testCertificates)
    );

    assertEquals(403, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Om intyget är utfärdat på en annan vårdenhet skall felkod 403 (FORBIDDEN) returneras")
  void shallReturn403IfUnitIsCareUnitAndNotOnCareUnit() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED)
    );

    final var response = api().replaceCertificate(
        customReplaceCertificateRequest()
            .careUnit(ALFA_VARDCENTRAL_DTO)
            .unit(ALFA_VARDCENTRAL_DTO)
            .build(),
        certificateId(testCertificates)
    );

    assertEquals(403, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Vårdadministratör - Felkod 403 (FORBIDDEN) returneras")
  void shallReturn403UserIsCareAdmin() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED)
    );

    final var response = api().replaceCertificate(
        customReplaceCertificateRequest()
            .user(ALVA_VARDADMINISTRATOR_DTO)
            .build(),
        certificateId(testCertificates)
    );

    assertEquals(403, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Läkare - Om intyget är utfärdat på en patient som har skyddade personuppgifter skall det gå att ersätta")
  void shallSuccessfullyRevokeIfPatientIsProtectedPersonAndUserIsDoctor() {
    final var testCertificates = testabilityApi().addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion(), SIGNED)
            .patient(ANONYMA_REACT_ATTILA_DTO)
            .build()
    );

    final var response = api().replaceCertificate(
        defaultReplaceCertificateRequest(),
        certificateId(testCertificates)
    );

    assertAll(
        () -> assertNotNull(
            relation(replaceCertificateResponse(response)).getParent()
            , "Should add parent to replaced certificate"),
        () -> assertEquals(certificateId(testCertificates),
            relation(replaceCertificateResponse(response)).getParent().getCertificateId())
    );
  }

  @Test
  @DisplayName("Om intyget inte är signerat skall felkod 403 (FORBIDDEN) returneras")
  void shallReturn403IfCertificateNotSigned() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var response = api().replaceCertificate(
        defaultReplaceCertificateRequest(),
        certificateId(testCertificates)
    );

    assertEquals(403, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Om användaren är blockerad ska inte 'Ersätt intyg' vara tillgänglig")
  void shallReturn403IfUserIsBlocked() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED)
    );

    final var response = api().replaceCertificate(
        customReplaceCertificateRequest()
            .user(
                ajlaDoktorDtoBuilder()
                    .blocked(Boolean.TRUE)
                    .build()
            )
            .build(),
        certificateId(testCertificates)
    );

    assertEquals(403, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Om intyget redan ersatts så ska felkod 403 (FORBIDDEN) returneras")
  void shallReturn403IfCertificateAlreadyIsReplaced() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED)
    );

    api().replaceCertificate(
        defaultReplaceCertificateRequest(),
        certificateId(testCertificates)
    );

    final var response = api().replaceCertificate(
        defaultReplaceCertificateRequest(),
        certificateId(testCertificates)
    );

    assertEquals(403, response.getStatusCode().value());
  }
}
