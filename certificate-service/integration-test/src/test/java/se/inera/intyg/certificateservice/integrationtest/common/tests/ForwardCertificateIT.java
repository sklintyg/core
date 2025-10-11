package se.inera.intyg.certificateservice.integrationtest.common.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.ANONYMA_REACT_ATTILA_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.ATLAS_REACT_ABRAHAMSSON_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_HUDMOTTAGNINGEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.AJLA_DOCTOR_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.ANNA_SJUKSKOTERSKA_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.BERTIL_BARNMORSKA_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.alvaVardadministratorDtoBuilder;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.customForwardCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.customGetCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.customTestabilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultForwardCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultTestablilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.certificateId;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.forwarded;

import java.util.Objects;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO;
import se.inera.intyg.certificateservice.application.common.dto.ResourceLinkDTO;
import se.inera.intyg.certificateservice.application.common.dto.ResourceLinkTypeDTO;
import se.inera.intyg.certificateservice.integrationtest.common.setup.BaseIntegrationIT;

public abstract class ForwardCertificateIT extends BaseIntegrationIT {

  @Test
  @DisplayName("Om användaren har rollen vårdadministratör och intyget inte är signerat skall intyget gå att vidarebefordra")
  void shallUpdateCertificateWithForwardedTrueIfUserIsCareAdminAndStatusOnCertificateIsDraft() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var response = api().forwardCertificate(
        certificateId(testCertificates),
        defaultForwardCertificateRequest()
    );

    assertTrue(
        forwarded(response.getBody()),
        "Should return true if certificate is forwarded!"
    );
  }

  @Test
  @DisplayName("Om användaren har rollen sjuksköterska och intyget inte är signerat skall intyget gå att vidarebefordra")
  void shallUpdateCertificateWithForwardedTrueIfUserIsNurseAndStatusOnCertificateIsDraft() {
    if (!nurseCanForwardCertificate()) {
      return;
    }
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var response = api().forwardCertificate(
        certificateId(testCertificates),
        customForwardCertificateRequest()
            .user(ANNA_SJUKSKOTERSKA_DTO)
            .build()
    );

    assertTrue(
        forwarded(response.getBody()),
        "Should return true if certificate is forwarded!"
    );
  }

  @Test
  @DisplayName("Om användaren har rollen barnmorska och intyget inte är signerat skall intyget gå att vidarebefordra")
  void shallUpdateCertificateWithForwardedTrueIfUserIsMidWifeAndStatusOnCertificateIsDraft() {
    if (!midwifeCanForwardCertificate()) {
      return;
    }
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var response = api().forwardCertificate(
        certificateId(testCertificates),
        customForwardCertificateRequest()
            .user(BERTIL_BARNMORSKA_DTO)
            .build()
    );

    assertTrue(
        forwarded(response.getBody()),
        "Should return true if certificate is forwarded!"
    );
  }

  @Test
  @DisplayName("Om användaren har rollen vårdadministratör och intyget är signerat skall felkod 403 (FORBIDDEN) returneras")
  void shallReturnForbiddenIfUserIsCareAdminAndStatusOnCertificateIsSigned() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(),
            CertificateStatusTypeDTO.SIGNED)
    );

    final var response = api().forwardCertificate(
        certificateId(testCertificates),
        defaultForwardCertificateRequest()
    );

    assertEquals(403, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Om användaren har signeringsrätt skall intyget inte gå att vidarebefordra från utkastvyn")
  void shallReturnForbiddenIfUserIsDoctor() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var response = api().forwardCertificate(
        certificateId(testCertificates),
        customForwardCertificateRequest()
            .user(AJLA_DOCTOR_DTO)
            .build()
    );

    final var resourceLinkTypes = Objects.requireNonNull(response.getBody()).getCertificate()
        .getLinks()
        .stream()
        .map(ResourceLinkDTO::getType)
        .toList();

    assertFalse(resourceLinkTypes.contains(ResourceLinkTypeDTO.FORWARD_CERTIFICATE),
        "Should return false if resource link forward certificate is not included"
    );
  }

  @Test
  @DisplayName("Om intyget är utfärdat på en patient som har skyddade personuppgifter skall felkod 403 (FORBIDDEN) returneras")
  void shallReturnForbiddenIfPatientIsProtectedPerson() {
    final var testCertificates = testabilityApi().addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion())
            .patient(ANONYMA_REACT_ATTILA_DTO)
            .build()
    );

    final var response = api().forwardCertificate(
        certificateId(testCertificates),
        defaultForwardCertificateRequest()
    );

    assertEquals(403, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Om patienten är avliden skall intyget vidarebefordras")
  void shallReturnForbiddenIfPatientIsDeceased() {
    final var testCertificates = testabilityApi().addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion())
            .patient(ATLAS_REACT_ABRAHAMSSON_DTO)
            .build()
    );

    final var response = api().forwardCertificate(
        certificateId(testCertificates),
        defaultForwardCertificateRequest()
    );

    assertEquals(200, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Om vårdadministratör är blockerad skall felkod 403 (FORBIDDEN) returneras")
  void shallReturnForbiddenIfUserIsBlocked() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var response = api().forwardCertificate(
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
  @DisplayName("Om intyget är utfärdat på samma mottagning skall intyget gå att vidarebefordra av vårdadministratör")
  void shallUpdateCertificateToForwardedIfCertificateOnSameUnit() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var response = api().forwardCertificate(
        certificateId(testCertificates),
        defaultForwardCertificateRequest()
    );

    assertTrue(
        forwarded(response.getBody()),
        "Should set certificate to forwarded!"
    );
  }

  @Test
  @DisplayName("Om intyget är utfärdat på mottagning men på samma vårdenhet skall intyget gå att vidarebefordra av vårdadministratör")
  void shallUpdateCertificateToForwardedIfIssuedOnSameCareUnitDifferentSubUnit() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var response = api().forwardCertificate(
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
  @DisplayName("Om intyget är utfärdat på samma vårdenhet skall intyget gå att vidarebefordra av vårdadministratör")
  void shallUpdateCertificateToForwardedIfIssuedOnSameCareUnit() {
    final var testCertificates = testabilityApi().addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion())
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .build()
    );

    final var response = api().forwardCertificate(
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
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var response = api().forwardCertificate(
        certificateId(testCertificates),
        customForwardCertificateRequest()
            .unit(ALFA_HUDMOTTAGNINGEN_DTO)
            .build()
    );

    assertEquals(403, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Intyget ska gå att vidarebefordra av läkare från utkastlistan")
  void shallBeAbleToForwardCertificateFromDraftListForDoctor() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var response = api().getCertificate(
        customGetCertificateRequest()
            .user(AJLA_DOCTOR_DTO)
            .build(),
        certificateId(testCertificates)
    );

    final var resourceLinkTypes = Objects.requireNonNull(response.getBody()).getCertificate()
        .getLinks()
        .stream()
        .map(ResourceLinkDTO::getType)
        .toList();

    assertTrue(resourceLinkTypes.contains(ResourceLinkTypeDTO.FORWARD_CERTIFICATE_FROM_LIST),
        "Should return true if resource link forward certificate from list is included"
    );
  }

  @Test
  @DisplayName("Intyget ska gå att vidarebefordra av barnmorska från utkastlistan")
  void shallBeAbleToForwardCertificateFromDraftListForMidwife() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var response = api().getCertificate(
        customGetCertificateRequest()
            .user(BERTIL_BARNMORSKA_DTO)
            .build(),
        certificateId(testCertificates)
    );

    final var resourceLinkTypes = Objects.requireNonNull(response.getBody()).getCertificate()
        .getLinks()
        .stream()
        .map(ResourceLinkDTO::getType)
        .toList();

    assertTrue(resourceLinkTypes.contains(ResourceLinkTypeDTO.FORWARD_CERTIFICATE_FROM_LIST),
        "Should return true if resource link forward certificate from list is included"
    );
  }

  @Test
  @DisplayName("Intyget ska gå att vidarebefordra av sjuksköterska från utkastlistan")
  void shallBeAbleToForwardCertificateFromDraftListForNurse() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var response = api().getCertificate(
        customGetCertificateRequest()
            .user(ANNA_SJUKSKOTERSKA_DTO)
            .build(),
        certificateId(testCertificates)
    );

    final var resourceLinkTypes = Objects.requireNonNull(response.getBody()).getCertificate()
        .getLinks()
        .stream()
        .map(ResourceLinkDTO::getType)
        .toList();

    assertTrue(resourceLinkTypes.contains(ResourceLinkTypeDTO.FORWARD_CERTIFICATE_FROM_LIST),
        "Should return true if resource link forward certificate from list is included"
    );
  }

  @Test
  @DisplayName("Intyget ska gå att vidarebefordra av vårdadministratör från utkastlistan")
  void shallBeAbleToForwardCertificateFromDraftListForCareAdmin() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var response = api().getCertificate(
        customGetCertificateRequest()
            .user(AJLA_DOCTOR_DTO)
            .build(),
        certificateId(testCertificates)
    );

    final var resourceLinkTypes = Objects.requireNonNull(response.getBody()).getCertificate()
        .getLinks()
        .stream()
        .map(ResourceLinkDTO::getType)
        .toList();

    assertTrue(resourceLinkTypes.contains(ResourceLinkTypeDTO.FORWARD_CERTIFICATE_FROM_LIST),
        "Should return true if resource link forward certificate from list is included"
    );
  }
}
