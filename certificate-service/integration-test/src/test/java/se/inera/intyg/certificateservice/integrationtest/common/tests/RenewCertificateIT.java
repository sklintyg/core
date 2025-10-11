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
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.customRenewCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.customTestabilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultRenewCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultTestablilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.certificateId;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.relation;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.renewCertificateResponse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.integrationtest.common.setup.BaseIntegrationIT;

public abstract class RenewCertificateIT extends BaseIntegrationIT {


  @Test
  @DisplayName("Om intyget är utfärdat på samma mottagning skall det gå att förnya")
  void shallSuccessfullyRenewIfUnitIsSubUnitAndIssuedOnSameSubUnit() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED)
    );

    final var response = api().renewCertificate(
        defaultRenewCertificateRequest(),
        certificateId(testCertificates)
    );

    assertAll(
        () -> assertNotNull(
            relation(renewCertificateResponse(response)).getParent()
            , "Should add parent to renewed certificate"),
        () -> assertEquals(certificateId(testCertificates),
            relation(renewCertificateResponse(response)).getParent().getCertificateId())
    );
  }

  @Test
  @DisplayName("Om intyget är utfärdat på mottagning men på samma vårdenhet skall det gå att förnya")
  void shallSuccessfullyRenewIfUnitIsCareUnitAndOnSameCareUnit() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED)
    );

    final var response = api().renewCertificate(
        defaultRenewCertificateRequest(),
        certificateId(testCertificates)
    );

    assertAll(
        () -> assertNotNull(
            relation(renewCertificateResponse(response)).getParent()
            , "Should add parent to renewed certificate"),
        () -> assertEquals(certificateId(testCertificates),
            relation(renewCertificateResponse(response)).getParent().getCertificateId())
    );
  }

  @Test
  @DisplayName("Om intyget är utfärdat på samma vårdenhet skall det gå att förnya")
  void shallSuccessfullyRenewIfUnitIsCareUnitAndIssuedOnSameCareUnit() {
    final var testCertificates = testabilityApi().addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion(), SIGNED)
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .build()
    );

    final var response = api().renewCertificate(
        customRenewCertificateRequest()
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .build(),
        certificateId(testCertificates)
    );

    assertAll(
        () -> assertNotNull(
            relation(renewCertificateResponse(response)).getParent()
            , "Should add parent to renewed certificate"),
        () -> assertEquals(certificateId(testCertificates),
            relation(renewCertificateResponse(response)).getParent().getCertificateId())
    );
  }

  @Test
  @DisplayName("Om intyget är utfärdat på en annan mottagning skall felkod 403 (FORBIDDEN) returneras")
  void shallReturn403IfUnitIsSubUnitAndNotOnSameUnit() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED)
    );

    final var response = api().renewCertificate(
        customRenewCertificateRequest()
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

    final var response = api().renewCertificate(
        customRenewCertificateRequest()
            .careUnit(ALFA_VARDCENTRAL_DTO)
            .unit(ALFA_VARDCENTRAL_DTO)
            .build(),
        certificateId(testCertificates)
    );

    assertEquals(403, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Vårdadministratör - Skall kunna förnya intyg utfärdat på patient utan skyddade personuppgifter")
  void shallSuccessufullyRenewIfCareAdminAndPatientNotProtectedPerson() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED)
    );

    final var response = api().renewCertificate(
        customRenewCertificateRequest()
            .user(ALVA_VARDADMINISTRATOR_DTO)
            .build(),
        certificateId(testCertificates)
    );

    assertAll(
        () -> assertNotNull(
            relation(renewCertificateResponse(response)).getParent()
            , "Should add parent to renewed certificate"),
        () -> assertEquals(certificateId(testCertificates),
            relation(renewCertificateResponse(response)).getParent().getCertificateId())
    );
  }

  @Test
  @DisplayName("Läkare - Om intyget är utfärdat på en patient som har skyddade personuppgifter skall det gå att förnya")
  void shallSuccessfullyRenewIfPatientIsProtectedPersonAndUserIsDoctor() {
    final var testCertificates = testabilityApi().addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion(), SIGNED)
            .patient(ANONYMA_REACT_ATTILA_DTO)
            .build()
    );

    final var response = api().renewCertificate(
        defaultRenewCertificateRequest(),
        certificateId(testCertificates)
    );

    assertAll(
        () -> assertNotNull(
            relation(renewCertificateResponse(response)).getParent()
            , "Should add parent to renewed certificate"),
        () -> assertEquals(certificateId(testCertificates),
            relation(renewCertificateResponse(response)).getParent().getCertificateId())
    );
  }

  @Test
  @DisplayName("Om intyget inte är signerat skall felkod 403 (FORBIDDEN) returneras")
  void shallReturn403IfCertificateNotSigned() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var response = api().renewCertificate(
        defaultRenewCertificateRequest(),
        certificateId(testCertificates)
    );

    assertEquals(403, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Om användaren är blockerad ska inte 'Förnya intyg' vara tillgänglig")
  void shallReturn403IfUserIsBlocked() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED)
    );

    final var response = api().renewCertificate(
        customRenewCertificateRequest()
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
  @DisplayName("Om intyget redan förnyats så ska det gå att förnya pånytt")
  void shallSuccessfullyRenewIfAlreadyRenewed() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED)
    );

    api().renewCertificate(
        defaultRenewCertificateRequest(),
        certificateId(testCertificates)
    );

    final var response = api().renewCertificate(
        defaultRenewCertificateRequest(),
        certificateId(testCertificates)
    );

    assertAll(
        () -> assertNotNull(
            relation(renewCertificateResponse(response)).getParent()
            , "Should add parent to renewed certificate"),
        () -> assertEquals(certificateId(testCertificates),
            relation(renewCertificateResponse(response)).getParent().getCertificateId())
    );
  }
}