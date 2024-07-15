package se.inera.intyg.certificateservice.integrationtest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_HUDMOTTAGNINGEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_VARDCENTRAL_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.ajlaDoktorDtoBuilder;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.alvaVardadministratorDtoBuilder;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.customGetCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultTestablilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.certificateId;

import java.util.Objects;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO;
import se.inera.intyg.certificateservice.application.common.dto.AccessScopeTypeDTO;
import se.inera.intyg.certificateservice.application.common.dto.ResourceLinkDTO;
import se.inera.intyg.certificateservice.application.common.dto.ResourceLinkTypeDTO;

public abstract class ResponsibleIssuerIT extends BaseIntegrationIT {

  protected abstract String type();

  protected abstract String typeVersion();

  @Test
  @DisplayName("Om intyget är ett utkast och användaren inte har signeringsrätt skall länk för att visa ansvarig intygsutfärdare visas")
  void shallDisplayResponsibleIssuerIfDraftAndUserDoesNotHaveRightToSign() {
    final var testCertificates = testabilityApi.addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(),
            CertificateStatusTypeDTO.UNSIGNED)
    );
    final var response = api.getCertificate(
        customGetCertificateRequest()
            .user(alvaVardadministratorDtoBuilder()
                .accessScope(AccessScopeTypeDTO.ALL_CARE_PROVIDERS)
                .build())
            .unit(ALFA_HUDMOTTAGNINGEN_DTO)
            .careUnit(ALFA_VARDCENTRAL_DTO)
            .careProvider(ALFA_MEDICINCENTRUM_DTO)
            .build(),
        certificateId(testCertificates)
    );

    final var resourceLinkTypes = Objects.requireNonNull(response.getBody()).getCertificate()
        .getLinks()
        .stream()
        .map(ResourceLinkDTO::getType)
        .toList();

    assertTrue(resourceLinkTypes.contains(ResourceLinkTypeDTO.RESPONSIBLE_ISSUER),
        "Should return true if resource link responsible issuer is included"
    );
  }

  @Test
  @DisplayName("Om intyget är ett utkast och användaren har signeringsrätt skall länk för att visa ansvarig intygsutfärdare inte visas")
  void shallNotDisplayResponsibleIssuerIfDraftAndUserHaveRightToSign() {
    final var testCertificates = testabilityApi.addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(),
            CertificateStatusTypeDTO.UNSIGNED)
    );
    final var response = api.getCertificate(
        customGetCertificateRequest()
            .user(ajlaDoktorDtoBuilder()
                .accessScope(AccessScopeTypeDTO.ALL_CARE_PROVIDERS)
                .build())
            .unit(ALFA_HUDMOTTAGNINGEN_DTO)
            .careUnit(ALFA_VARDCENTRAL_DTO)
            .careProvider(ALFA_MEDICINCENTRUM_DTO)
            .build(),
        certificateId(testCertificates)
    );

    final var resourceLinkTypes = Objects.requireNonNull(response.getBody()).getCertificate()
        .getLinks()
        .stream()
        .map(ResourceLinkDTO::getType)
        .toList();

    assertFalse(resourceLinkTypes.contains(ResourceLinkTypeDTO.RESPONSIBLE_ISSUER),
        "Should return false if resource link responsible issuer is not included"
    );
  }

  @Test
  @DisplayName("Om intyget inte är ett utkast och användaren inte har signeringsrätt skall länk för att visa ansvarig intygsutfärdare inte visas")
  void shallNotDisplayResponsibleIssuerIfNotDraftAndUserDoesNotHaveRightToSign() {
    final var testCertificates = testabilityApi.addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(),
            CertificateStatusTypeDTO.SIGNED)
    );
    final var response = api.getCertificate(
        customGetCertificateRequest()
            .user(alvaVardadministratorDtoBuilder()
                .accessScope(AccessScopeTypeDTO.ALL_CARE_PROVIDERS)
                .build())
            .unit(ALFA_HUDMOTTAGNINGEN_DTO)
            .careUnit(ALFA_VARDCENTRAL_DTO)
            .careProvider(ALFA_MEDICINCENTRUM_DTO)
            .build(),
        certificateId(testCertificates)
    );

    final var resourceLinkTypes = Objects.requireNonNull(response.getBody()).getCertificate()
        .getLinks()
        .stream()
        .map(ResourceLinkDTO::getType)
        .toList();

    assertFalse(resourceLinkTypes.contains(ResourceLinkTypeDTO.RESPONSIBLE_ISSUER),
        "Should return false if resource link responsible issuer is not included"
    );
  }
}
