package se.inera.intyg.certificateservice.integrationtest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.ANONYMA_REACT_ATTILA_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.ATLAS_REACT_ABRAHAMSSON_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_HUDMOTTAGNINGEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.AJLA_DOCTOR_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.alvaVardadministratorDtoBuilder;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.customForwardCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.customTestabilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultForwardCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultTestablilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.certificateId;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.forwarded;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO;

public abstract class ForwardCertificateIT extends BaseIntegrationIT {

  protected abstract String type();

  protected abstract String typeVersion();

  @Test
  @DisplayName("Om användaren har rollen vårdadministratör och intyget inte är signerat skall intyget gå att vidarebefodra")
  void shallUpdateCertificateWithForwardedTrueIfUserIsCareAdminAndStatusOnCertificateIsDraft() {
    final var testCertificates = testabilityApi.addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var response = api.forwardCertificate(
        certificateId(testCertificates),
        defaultForwardCertificateRequest()
    );

    assertTrue(
        forwarded(response.getBody()),
        "Should return true if certificate is forwarded!"
    );
  }

  @Test
  @DisplayName("Om användaren har rollen vårdadministratör och intyget är signerat skall felkod 403 (FORBIDDEN) returneras")
  void shallReturnForbiddenIfUserIsCareAdminAndStatusOnCertificateIsSigned() {
    final var testCertificates = testabilityApi.addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(),
            CertificateStatusTypeDTO.SIGNED)
    );

    final var response = api.forwardCertificate(
        certificateId(testCertificates),
        defaultForwardCertificateRequest()
    );

    assertEquals(403, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Om användaren har rollen läkare skall felkod 403 (FORBIDDEN) returneras")
  void shallReturnForbiddenIfUserIsDcotor() {
    final var testCertificates = testabilityApi.addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var response = api.forwardCertificate(
        certificateId(testCertificates),
        customForwardCertificateRequest()
            .user(AJLA_DOCTOR_DTO)
            .build()
    );

    assertEquals(403, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Om intyget är utfärdat på en patient som har skyddade personuppgifter skall felkod 403 (FORBIDDEN) returneras")
  void shallReturnForbiddenIfPatientIsProtectedPerson() {
    final var testCertificates = testabilityApi.addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion())
            .patient(ANONYMA_REACT_ATTILA_DTO)
            .build()
    );

    final var response = api.forwardCertificate(
        certificateId(testCertificates),
        defaultForwardCertificateRequest()
    );

    assertEquals(403, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Om patienten är avliden skall felkod 403 (FORBIDDEN) returneras")
  void shallReturnForbiddenIfPatientIsDeceased() {
    final var testCertificates = testabilityApi.addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion())
            .patient(ATLAS_REACT_ABRAHAMSSON_DTO)
            .build()
    );

    final var response = api.forwardCertificate(
        certificateId(testCertificates),
        defaultForwardCertificateRequest()
    );

    assertEquals(403, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Om vårdadministratör är blockerad skall felkod 403 (FORBIDDEN) returneras")
  void shallReturnForbiddenIfUserIsBlocked() {
    final var testCertificates = testabilityApi.addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var response = api.forwardCertificate(
        certificateId(testCertificates),
        customForwardCertificateRequest()
            .user(
                alvaVardadministratorDtoBuilder()
                    .blocked(Boolean.TRUE)
                    .build()
            )
            .build()
    );

    assertEquals(403, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Om intyget är utfärdat på samma mottagning skall intyget gå att vidarebefodra av vårdadministratör")
  void shallUpdateCertificateToForwardedIfCertificateOnSameUnit() {
    final var testCertificates = testabilityApi.addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var response = api.forwardCertificate(
        certificateId(testCertificates),
        defaultForwardCertificateRequest()
    );

    assertTrue(
        forwarded(response.getBody()),
        "Should set certificate to forwarded!"
    );
  }

  @Test
  @DisplayName("Om intyget är utfärdat på mottagning men på samma vårdenhet skall intyget gå att vidarebefodra av vårdadministratör")
  void shallUpdateCertificateToForwardedIfIssuedOnSameCareUnitDifferentSubUnit() {
    final var testCertificates = testabilityApi.addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var response = api.forwardCertificate(
        certificateId(testCertificates),
        customForwardCertificateRequest()
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .build()
    );

    assertTrue(
        forwarded(response.getBody()),
        "Should set certificate to forwarded!"
    );
  }

  @Test
  @DisplayName("Om intyget är utfärdat på samma vårdenhet skall intyget gå att vidarebefodra av vårdadministratör")
  void shallUpdateCertificateToForwardedIfIssuedOnSameCareUnit() {
    final var testCertificates = testabilityApi.addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion())
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .build()
    );

    final var response = api.forwardCertificate(
        certificateId(testCertificates),
        customForwardCertificateRequest()
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .build()
    );

    assertTrue(
        forwarded(response.getBody()),
        "Should set certificate to forwarded!"
    );
  }

  @Test
  @DisplayName("Om intyget är utfärdat på en annan mottagning skall felkod 403 (FORBIDDEN) returneras")
  void shallReturn403IfUnitIsSubUnitAndNotOnSameUnit() {
    final var testCertificates = testabilityApi.addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var response = api.forwardCertificate(
        certificateId(testCertificates),
        customForwardCertificateRequest()
            .unit(ALFA_HUDMOTTAGNINGEN_DTO)
            .build()
    );

    assertEquals(403, response.getStatusCode().value());
  }
}
