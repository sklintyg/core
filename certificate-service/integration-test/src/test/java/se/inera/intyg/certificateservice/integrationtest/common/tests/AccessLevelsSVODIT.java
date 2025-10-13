package se.inera.intyg.certificateservice.integrationtest.common.tests;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_ALLERGIMOTTAGNINGEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_HUDMOTTAGNINGEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_REGIONEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_VARDCENTRAL_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.BETA_REGIONEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.AJLA_DOCTOR_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.ajlaDoktorDtoBuilder;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.alvaVardadministratorDtoBuilder;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.customGetCertificatePdfRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.customGetCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.customSendCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultTestablilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.certificate;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.certificateId;

import java.util.Objects;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.common.dto.AccessScopeTypeDTO;
import se.inera.intyg.certificateservice.integrationtest.common.setup.BaseIntegrationIT;

public abstract class AccessLevelsSVODIT extends BaseIntegrationIT {
  
  @Test
  @DisplayName("Om intyget är utfärdat inom en annan vårdgivare skall det gå att läsa intyget")
  void shallReturnCertificateIfOnDifferentUnitButSameCareProvider() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var response = api().getCertificate(
        customGetCertificateRequest()
            .user(ajlaDoktorDtoBuilder()
                .accessScope(AccessScopeTypeDTO.ALL_CARE_PROVIDERS)
                .build())
            .unit(ALFA_HUDMOTTAGNINGEN_DTO)
            .careUnit(ALFA_VARDCENTRAL_DTO)
            .careProvider(BETA_REGIONEN_DTO)
            .build(),
        certificateId(testCertificates)
    );

    assertNotNull(
        certificate(response.getBody()),
        "Should return certificate when exists!"
    );
  }

  @Test
  @DisplayName("Om intyget är utfärdat på en annan enhet inom samma vårdgivare skall felkod 403 (FORBIDDEN) returneras vid hämtning av PDF")
  void shallReturnPdfIfOnDifferentUnitButSameCareProvider() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var response = api().getCertificatePdf(
        customGetCertificatePdfRequest()
            .user(ajlaDoktorDtoBuilder()
                .accessScope(AccessScopeTypeDTO.WITHIN_CARE_PROVIDER)
                .build())
            .unit(ALFA_HUDMOTTAGNINGEN_DTO)
            .careUnit(ALFA_VARDCENTRAL_DTO)
            .careProvider(BETA_REGIONEN_DTO)
            .build(),
        certificateId(testCertificates)
    );

    assertEquals(403, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Om intyget är utfärdat på en annan enhet inom samma vårdgivare skall felkod 403 (FORBIDDEN) returneras vid skickande av intyg")
  void shallNotAllowToSendOnDifferentUnitButSameCareProvider() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var response = api().sendCertificate(
        customSendCertificateRequest()
            .user(ajlaDoktorDtoBuilder()
                .accessScope(AccessScopeTypeDTO.WITHIN_CARE_PROVIDER)
                .build())
            .unit(ALFA_HUDMOTTAGNINGEN_DTO)
            .careUnit(ALFA_VARDCENTRAL_DTO)
            .careProvider(BETA_REGIONEN_DTO)
            .build(),
        certificateId(testCertificates)
    );

    assertEquals(403, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Om intyget är utfärdat inom en annan vårdgivare skall det gå att läsa intyget - metadata skall vara oförändrad")
  void shallReturnCertificateIfOnDifferentUnitButSameCareProviderWithSameMetadata() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var response = api().getCertificate(
        customGetCertificateRequest()
            .user(alvaVardadministratorDtoBuilder()
                .accessScope(AccessScopeTypeDTO.ALL_CARE_PROVIDERS)
                .build())
            .unit(ALFA_HUDMOTTAGNINGEN_DTO)
            .careUnit(ALFA_VARDCENTRAL_DTO)
            .careProvider(BETA_REGIONEN_DTO)
            .build(),
        certificateId(testCertificates)
    );

    final var certificate = certificate(response.getBody());

    assertAll(
        () -> assertEquals(ALFA_ALLERGIMOTTAGNINGEN_DTO.getId(),
            Objects.requireNonNull(certificate).getMetadata().getUnit().getUnitId()),
        () -> assertEquals(ALFA_MEDICINCENTRUM_DTO.getId(),
            Objects.requireNonNull(certificate).getMetadata().getCareUnit().getUnitId()),
        () -> assertEquals(ALFA_REGIONEN_DTO.getId(),
            Objects.requireNonNull(certificate).getMetadata().getCareProvider().getUnitId()),
        () -> assertEquals(AJLA_DOCTOR_DTO.getId(),
            Objects.requireNonNull(certificate).getMetadata().getIssuedBy().getPersonId())
    );
  }
}
