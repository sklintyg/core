package se.inera.intyg.certificateservice.integrationtest.common.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.ANONYMA_REACT_ATTILA_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_HUDMOTTAGNINGEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_VARDCENTRAL_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.AJLA_DOCTOR_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.ALVA_VARDADMINISTRATOR_DTO;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.customTestabilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.customUpdateCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultTestablilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.certificate;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.certificateId;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.getBooleanValue;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.getValueDate;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.getValueText;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.updateBooleanValue;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.updateDateValue;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.updateTextValue;

import java.time.LocalDate;
import java.util.Objects;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDataElement;
import se.inera.intyg.certificateservice.application.certificate.dto.UpdateCertificateResponse;
import se.inera.intyg.certificateservice.integrationtest.common.setup.BaseIntegrationIT;

public abstract class UpdateCertificateIT extends BaseIntegrationIT {

  @Test
  @DisplayName("Vårdadmin - Om intyget är utfärdat på en patient som har skyddade personuppgifter skall felkod 403 (FORBIDDEN) returneras")
  void shallNotUpdateDataIfUserIsCareAdminAndPatientIsProtectedPerson() {
    final var testCertificates = testabilityApi().addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion())
            .patient(ANONYMA_REACT_ATTILA_DTO)
            .build()
    );

    final var certificate = certificate(testCertificates);

    final var response = api().updateCertificate(
        customUpdateCertificateRequest()
            .user(ALVA_VARDADMINISTRATOR_DTO)
            .certificate(certificate)
            .build(),
        certificateId(testCertificates)
    );

    assertEquals(403, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Om intyget är utfärdat på en annan mottagning skall felkod 403 (FORBIDDEN) returneras")
  void shallReturn403IfUnitIsSubUnitAndNotOnSameUnit() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var certificate = certificate(testCertificates);

    final var response = api().updateCertificate(
        customUpdateCertificateRequest()
            .certificate(certificate)
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
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var certificate = certificate(testCertificates);

    final var response = api().updateCertificate(
        customUpdateCertificateRequest()
            .certificate(certificate)
            .unit(ALFA_VARDCENTRAL_DTO)
            .build(),
        certificateId(testCertificates)
    );

    assertEquals(403, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Om utkastet sparas med en inaktuell revision av utkastet skall felkod 409 (CONFLICT) returneras")
  void shallNotUpdateDataIfUserIsTryingToSaveWithAnOldRevision() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var certificate = certificate(testCertificates);

    api().updateCertificate(
        customUpdateCertificateRequest()
            .certificate(certificate)
            .build(),
        certificateId(testCertificates)
    );

    final var response = api().updateCertificateWithConcurrentError(
        customUpdateCertificateRequest()
            .certificate(certificate)
            .build(),
        certificateId(testCertificates)
    );

    assertEquals(409, response.getStatusCode().value());
  }


  @Test
  @DisplayName("Läkare - Om intyget är utfärdat på en patient som har skyddade personuppgifter skall svarsalternativ uppdateras")
  void shallUpdateDataIfUserIsDoctorAndPatientIsProtectedPerson() {
    final var testCertificates = testabilityApi().addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion())
            .patient(ANONYMA_REACT_ATTILA_DTO)
            .build()
    );
    final var expectedValue = value();
    final var certificate = certificate(testCertificates);

    Objects.requireNonNull(
        certificate.getData().put(
            element().id(),
            updateValue(certificate, element().id(), expectedValue)
        )
    );

    final var response = api().updateCertificate(
        customUpdateCertificateRequest()
            .user(AJLA_DOCTOR_DTO)
            .certificate(certificate)
            .build(),
        certificateId(testCertificates)
    );

    assertValue(expectedValue, response, element().id());
  }

  @Test
  @DisplayName("Om intyget är utfärdat på mottagning men på samma vårdenhet skall svarsalternativ uppdateras")
  void shallUpdateDataIfUnitIsCareUnitAndOnSameCareUnit() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var expectedValue = value();
    final var certificate = certificate(testCertificates);

    Objects.requireNonNull(
        certificate.getData().put(
            element().id(),
            updateValue(certificate, element().id(), expectedValue)
        )
    );

    final var response = api().updateCertificate(
        customUpdateCertificateRequest()
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .certificate(certificate)
            .build(),
        certificateId(testCertificates)
    );

    assertValue(expectedValue, response, element().id());
  }

  @Test
  @DisplayName("Om intyget är utfärdat på samma vårdenhet skall svarsalternativ uppdateras")
  void shallUpdateDataIfUnitIsCareUnitAndIssuedOnSameCareUnit() {
    final var testCertificates = testabilityApi().addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion())
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .build()
    );

    final var expectedValue = value();
    final var certificate = certificate(testCertificates);

    Objects.requireNonNull(
        certificate.getData().put(
            element().id(),
            updateValue(certificate, element().id(), expectedValue)
        )
    );

    final var response = api().updateCertificate(
        customUpdateCertificateRequest()
            .certificate(certificate)
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .build(),
        certificateId(testCertificates)
    );

    assertValue(expectedValue, response, element().id());
  }

  @Test
  @DisplayName("Om intyget är utfärdat på samma mottagning skall svarsalternativ uppdateras")
  void shallUpdateDataIfUnitIsSubUnitAndOnSameUnit() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var expectedValue = value();
    final var certificate = certificate(testCertificates);

    Objects.requireNonNull(
        certificate.getData().put(
            element().id(),
            updateValue(certificate, element().id(), expectedValue)
        )
    );

    final var response = api().updateCertificate(
        customUpdateCertificateRequest()
            .certificate(certificate)
            .build(),
        certificateId(testCertificates)
    );

    assertValue(expectedValue, response, element().id());
  }

  private CertificateDataElement updateValue(CertificateDTO certificate, String id,
      Object expectedValue) {
    if (expectedValue instanceof String expectedText) {
      return updateTextValue(certificate, id, expectedText);
    }
    if (expectedValue instanceof LocalDate expectedDate) {
      return updateDateValue(certificate, id, expectedDate);
    }
    if (expectedValue instanceof Boolean expectedBoolean) {
      return updateBooleanValue(certificate, id, expectedBoolean);
    }
    throw new IllegalStateException("No update function available for type %s"
        .formatted(expectedValue.getClass()));
  }

  private void assertValue(Object expectedValue, ResponseEntity<UpdateCertificateResponse> response,
      String id) {
    if (expectedValue instanceof String expectedText) {
      assertEquals(expectedText, getValueText(response, id));
      return;
    }
    if (expectedValue instanceof LocalDate expectedDate) {
      assertEquals(expectedDate, getValueDate(response, id));
      return;
    }
    if (expectedValue instanceof Boolean expectedBoolean) {
      assertEquals(expectedBoolean, getBooleanValue(response, id));
      return;
    }
    fail("Assertion for type %s has not been implemented".formatted(expectedValue.getClass()));
  }
}
