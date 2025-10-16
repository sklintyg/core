package se.inera.intyg.certificateservice.integrationtest.common.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.ANONYMA_REACT_ATTILA_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_HUDMOTTAGNINGEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_VARDCENTRAL_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.ALVA_VARDADMINISTRATOR_DTO;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.customDeleteCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.customTestabilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultDeleteCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultTestablilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.certificateId;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.exists;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.version;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.integrationtest.common.setup.BaseIntegrationIT;

public abstract class DeleteCertificateIT extends BaseIntegrationIT {


  @Test
  @DisplayName("Om utkastet är skapat på samma mottagning skall det gå att tas bort")
  void shallDeleteCertificateIfCertificateIsOnSameSubUnit() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var certificateId = certificateId(testCertificates);
    api().deleteCertificate(
        defaultDeleteCertificateRequest(),
        certificateId,
        version(testCertificates)
    );

    assertFalse(
        exists(api().certificateExists(certificateId).getBody())
    );
  }

  @Test
  @DisplayName("Om utkastet är skapat på samma vårdenhet skall det gå att tas bort")
  void shallDeleteCertificateIfCertificateIsOnSameCareUnit() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var certificateId = certificateId(testCertificates);
    api().deleteCertificate(
        customDeleteCertificateRequest()
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .build(),
        certificateId,
        version(testCertificates)
    );

    assertFalse(
        exists(api().certificateExists(certificateId).getBody())
    );
  }

  @Test
  @DisplayName("Om utkastet är skapat på annan mottagning skall felkod 403 (FORBIDDEN) returneras")
  void shallReturn403IfUnitIsSubUnitAndNotOnSameSubUnit() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var certificateId = certificateId(testCertificates);
    final var response = api().deleteCertificate(
        customDeleteCertificateRequest()
            .unit(ALFA_HUDMOTTAGNINGEN_DTO)
            .build(),
        certificateId,
        version(testCertificates)
    );

    assertEquals(403, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Om utkastet är skapat på annan vårdenhet skall felkod 403 (FORBIDDEN) returneras")
  void shallReturn403IfUnitIsCareUnitAndNotOnSameCareUnit() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var certificateId = certificateId(testCertificates);
    final var response = api().deleteCertificate(
        customDeleteCertificateRequest()
            .unit(ALFA_VARDCENTRAL_DTO)
            .careUnit(ALFA_VARDCENTRAL_DTO)
            .build(),
        certificateId,
        version(testCertificates)
    );

    assertEquals(403, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Vårdadmin - Om utkastet är skapat på en patient som har skyddade personuppgifter skall felkod 403 (FORBIDDEN) returneras")
  void shallNotDeleteDataIfUserIsCareAdminAndPatientIsProtectedPerson() {
    final var testCertificates = testabilityApi().addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion())
            .patient(ANONYMA_REACT_ATTILA_DTO)
            .build()
    );

    final var certificateId = certificateId(testCertificates);
    final var response = api().deleteCertificate(
        customDeleteCertificateRequest()
            .user(ALVA_VARDADMINISTRATOR_DTO)
            .build(),
        certificateId,
        version(testCertificates)
    );

    assertEquals(403, response.getStatusCode().value());
  }
}
