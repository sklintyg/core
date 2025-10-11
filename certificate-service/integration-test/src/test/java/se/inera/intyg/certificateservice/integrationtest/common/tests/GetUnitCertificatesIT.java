package se.inera.intyg.certificateservice.integrationtest.common.tests;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.ALVE_REACT_ALFREDSSON_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.ATHENA_REACT_ANDERSSON_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_ALLERGIMOTTAGNINGEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_HUDMOTTAGNINGEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_VARDCENTRAL_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.AJLA_DOCTOR_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.ALVA_VARDADMINISTRATOR_DTO;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.customGetUnitCertificatesRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.customTestabilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.customUpdateCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultGetUnitCertificatesRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultSignCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultTestablilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.certificate;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.certificateId;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.certificates;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.exists;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.updateBooleanValue;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.updateDateValue;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.updateTextValue;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.version;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDataElement;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.PersonIdDTO;
import se.inera.intyg.certificateservice.application.unit.dto.CertificatesQueryCriteriaDTO;
import se.inera.intyg.certificateservice.integrationtest.common.setup.BaseIntegrationIT;
import se.inera.intyg.certificateservice.testability.certificate.dto.TestabilityFillTypeDTO;

public abstract class GetUnitCertificatesIT extends BaseIntegrationIT {

  @Test
  @DisplayName("Returnera lista med utkast som har sparats på mottagning")
  void shallReturnCertificatesOnTheSameSubUnit() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var response = api().getUnitCertificates(
        defaultGetUnitCertificatesRequest()
    );

    assertAll(
        () -> assertTrue(
            () -> exists(certificates(response.getBody()), certificate(testCertificates)),
            () -> "Expected '%s' in result: '%s'".formatted(certificateId(testCertificates),
                certificates(response.getBody()))),
        () -> assertEquals(1, certificates(response.getBody()).size())
    );
  }

  @Test
  @DisplayName("Returnera lista med utkast som har sparats på vårdenhet")
  void shallReturnCertificatesOnTheSameCareUnit() {
    final var testCertificates = testabilityApi().addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion())
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .build()
    );

    final var response = api().getUnitCertificates(
        customGetUnitCertificatesRequest()
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .build()
    );

    assertAll(
        () -> assertTrue(
            () -> exists(certificates(response.getBody()), certificate(testCertificates)),
            () -> "Expected '%s' in result: '%s'".formatted(certificateId(testCertificates),
                certificates(response.getBody()))),
        () -> assertEquals(1, certificates(response.getBody()).size())
    );
  }

  @Test
  @DisplayName("Returnera lista med utkast som har sparats på mottagning inom vårdenhet")
  void shallReturnCertificatesIssuedOnSubUnitOnTheSameCareUnit() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var response = api().getUnitCertificates(
        customGetUnitCertificatesRequest()
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .build()
    );

    assertAll(
        () -> assertTrue(
            () -> exists(certificates(response.getBody()), certificate(testCertificates)),
            () -> "Expected '%s' in result: '%s'".formatted(certificateId(testCertificates),
                certificates(response.getBody()))),
        () -> assertEquals(1, certificates(response.getBody()).size())
    );
  }

  @Test
  @DisplayName("Ej returnera utkast som sparats på annan mottagning")
  void shallNotReturnCertificatesOnDifferentSubUnit() {
    testabilityApi().addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion())
            .unit(ALFA_HUDMOTTAGNINGEN_DTO)
            .build()
    );

    final var response = api().getUnitCertificates(
        defaultGetUnitCertificatesRequest()
    );

    assertEquals(0, certificates(response.getBody()).size(),
        "Expect list to be empty but contains: '%s'".formatted(certificates(response.getBody()))
    );
  }

  @Test
  @DisplayName("Ej returnera utkast som sparats på annan vårdenhet")
  void shallNotReturnCertificatesOnDifferentCareUnit() {
    testabilityApi().addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion())
            .unit(ALFA_VARDCENTRAL_DTO)
            .careUnit(ALFA_VARDCENTRAL_DTO)
            .build()
    );

    final var response = api().getUnitCertificates(
        customGetUnitCertificatesRequest()
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .build()
    );

    assertEquals(0, certificates(response.getBody()).size(),
        "Expect list to be empty but contains: '%s'".formatted(certificates(response.getBody()))
    );
  }

  @Test
  @DisplayName("Ej returnera utkast som sparats på vårdenheten när man är på mottagningen")
  void shallNotReturnCertificatesOnCareUnitWhenOnSubUnit() {
    testabilityApi().addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion())
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .build()
    );

    final var response = api().getUnitCertificates(
        customGetUnitCertificatesRequest()
            .unit(ALFA_ALLERGIMOTTAGNINGEN_DTO)
            .build()
    );

    assertEquals(0, certificates(response.getBody()).size(),
        "Expect list to be empty but contains: '%s'".formatted(certificates(response.getBody()))
    );
  }

  @Test
  @DisplayName("Returnera lista med utkast som har sparats datum efter från och med datum")
  void shallReturnCertificatesSavedAfterFrom() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var response = api().getUnitCertificates(
        customGetUnitCertificatesRequest()
            .queryCriteria(
                CertificatesQueryCriteriaDTO.builder()
                    .from(LocalDateTime.now().minusDays(1))
                    .statuses(List.of(CertificateStatusTypeDTO.UNSIGNED))
                    .build()
            )
            .build()
    );

    assertAll(
        () -> assertTrue(
            () -> exists(certificates(response.getBody()), certificate(testCertificates)),
            () -> "Expected '%s' in result: '%s'".formatted(certificateId(testCertificates),
                certificates(response.getBody()))),
        () -> assertEquals(1, certificates(response.getBody()).size())
    );
  }

  @Test
  @DisplayName("Ej returnera utkast som har sparats datum före från och med datum")
  void shallNotReturnCertificatesSavedBeforeFrom() {
    testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var response = api().getUnitCertificates(
        customGetUnitCertificatesRequest()
            .queryCriteria(
                CertificatesQueryCriteriaDTO.builder()
                    .from(LocalDateTime.now().plusDays(1))
                    .statuses(List.of(CertificateStatusTypeDTO.UNSIGNED))
                    .build()
            )
            .build()
    );

    assertEquals(0, certificates(response.getBody()).size(),
        "Expect list to be empty but contains: '%s'".formatted(certificates(response.getBody()))
    );
  }

  @Test
  @DisplayName("Returnera lista med utkast som har sparat datum före till och med datum")
  void shallReturnCertificatesSavedBeforeTo() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var response = api().getUnitCertificates(
        customGetUnitCertificatesRequest()
            .queryCriteria(
                CertificatesQueryCriteriaDTO.builder()
                    .to(LocalDateTime.now().plusDays(1))
                    .statuses(List.of(CertificateStatusTypeDTO.UNSIGNED))
                    .build()
            )
            .build()
    );

    assertAll(
        () -> assertTrue(
            () -> exists(certificates(response.getBody()), certificate(testCertificates)),
            () -> "Expected '%s' in result: '%s'".formatted(certificateId(testCertificates),
                certificates(response.getBody()))),
        () -> assertEquals(1, certificates(response.getBody()).size())
    );
  }

  @Test
  @DisplayName("Ej returnera utkast som har sparats datum efter till och med datum")
  void shallNotReturnCertificatesSavedAfterTo() {
    testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var response = api().getUnitCertificates(
        customGetUnitCertificatesRequest()
            .queryCriteria(
                CertificatesQueryCriteriaDTO.builder()
                    .to(LocalDateTime.now().minusDays(1))
                    .statuses(List.of(CertificateStatusTypeDTO.UNSIGNED))
                    .build()
            )
            .build()
    );

    assertEquals(0, certificates(response.getBody()).size(),
        "Expect list to be empty but contains: '%s'".formatted(certificates(response.getBody()))
    );
  }

  @Test
  @DisplayName("Returnera lista med utkast som har sparats på patienten")
  void shallReturnCertificatesSavedOnPatient() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var response = api().getUnitCertificates(
        customGetUnitCertificatesRequest()
            .queryCriteria(
                CertificatesQueryCriteriaDTO.builder()
                    .personId(
                        PersonIdDTO.builder()
                            .id(ATHENA_REACT_ANDERSSON_DTO.getId().getId())
                            .type(ATHENA_REACT_ANDERSSON_DTO.getId().getType().name())
                            .build()
                    )
                    .statuses(List.of(CertificateStatusTypeDTO.UNSIGNED))
                    .build()
            )
            .build()
    );

    assertAll(
        () -> assertTrue(
            () -> exists(certificates(response.getBody()), certificate(testCertificates)),
            () -> "Expected '%s' in result: '%s'".formatted(certificateId(testCertificates),
                certificates(response.getBody()))),
        () -> assertEquals(1, certificates(response.getBody()).size())
    );
  }

  @Test
  @DisplayName("Ej returnera utkast som har sparats på annan patient")
  void shallNotReturnCertificatesSavedOnDifferentPatient() {
    testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var response = api().getUnitCertificates(
        customGetUnitCertificatesRequest()
            .queryCriteria(
                CertificatesQueryCriteriaDTO.builder()
                    .personId(
                        PersonIdDTO.builder()
                            .id(ALVE_REACT_ALFREDSSON_DTO.getId().getId())
                            .type(ALVE_REACT_ALFREDSSON_DTO.getId().getType().name())
                            .build()
                    )
                    .statuses(List.of(CertificateStatusTypeDTO.UNSIGNED))
                    .build()
            )
            .build()
    );

    assertEquals(0, certificates(response.getBody()).size(),
        "Expect list to be empty but contains: '%s'".formatted(certificates(response.getBody()))
    );
  }

  @Test
  @DisplayName("Returnera lista med utkast som har sparats av vald användare")
  void shallReturnCertificatesSavedBySameStaff() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var response = api().getUnitCertificates(
        customGetUnitCertificatesRequest()
            .queryCriteria(
                CertificatesQueryCriteriaDTO.builder()
                    .issuedByStaffId(AJLA_DOCTOR_DTO.getId())
                    .statuses(List.of(CertificateStatusTypeDTO.UNSIGNED))
                    .build()
            )
            .build()
    );

    assertAll(
        () -> assertTrue(
            () -> exists(certificates(response.getBody()), certificate(testCertificates)),
            () -> "Expected '%s' in result: '%s'".formatted(certificateId(testCertificates),
                certificates(response.getBody()))),
        () -> assertEquals(1, certificates(response.getBody()).size())
    );
  }

  @Test
  @DisplayName("Ej returnera utkast som har sparats av annan användare")
  void shallNotReturnCertificatesSavedByDifferentStaff() {
    testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var response = api().getUnitCertificates(
        customGetUnitCertificatesRequest()
            .queryCriteria(
                CertificatesQueryCriteriaDTO.builder()
                    .issuedByStaffId(ALVA_VARDADMINISTRATOR_DTO.getId())
                    .statuses(List.of(CertificateStatusTypeDTO.UNSIGNED))
                    .build()
            )
            .build()
    );

    assertEquals(0, certificates(response.getBody()).size(),
        "Expect list to be empty but contains: '%s'".formatted(certificates(response.getBody()))
    );
  }

  @Test
  @DisplayName("Ej returnera utkast som har annan status")
  void shallNotReturnCertificatesWithDifferentStatus() {
    testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var response = api().getUnitCertificates(
        customGetUnitCertificatesRequest()
            .queryCriteria(
                CertificatesQueryCriteriaDTO.builder()
                    .statuses(List.of(CertificateStatusTypeDTO.LOCKED))
                    .build()
            )
            .build()
    );

    assertEquals(0, certificates(response.getBody()).size(),
        "Expect list to be empty but contains: '%s'".formatted(certificates(response.getBody()))
    );
  }

  @Test
  @DisplayName("Ej returnera utkast som har signerad status")
  void shallNotReturnCertificatesWithSignedStatus() {
    final var testCertificates = testabilityApi().addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion())
            .fillType(TestabilityFillTypeDTO.MAXIMAL)
            .build()
    );

    api().signCertificate(
        defaultSignCertificateRequest(),
        certificateId(testCertificates),
        version(testCertificates)
    );

    final var response = api().getUnitCertificates(
        customGetUnitCertificatesRequest()
            .queryCriteria(
                CertificatesQueryCriteriaDTO.builder()
                    .statuses(List.of(CertificateStatusTypeDTO.UNSIGNED))
                    .build()
            )
            .build()
    );

    assertEquals(0, certificates(response.getBody()).size(),
        "Expect list to be empty but contains: '%s'".formatted(certificates(response.getBody()))
    );
  }

  @Test
  @DisplayName("Returnera utkast som inte är färdiga för signering när man söker på ej färdiga")
  void shallReturnDraftsWhichAreNotValid() {
    final var testCertificates = testabilityApi().addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion())
            .fillType(TestabilityFillTypeDTO.EMPTY)
            .build()
    );

    final var response = api().getUnitCertificates(
        customGetUnitCertificatesRequest()
            .queryCriteria(
                CertificatesQueryCriteriaDTO.builder()
                    .statuses(List.of(CertificateStatusTypeDTO.UNSIGNED))
                    .validForSign(Boolean.FALSE)
                    .build()
            )
            .build()
    );

    assertAll(
        () -> assertTrue(
            () -> exists(certificates(response.getBody()), certificate(testCertificates)),
            () -> "Expected '%s' in result: '%s'".formatted(certificateId(testCertificates),
                certificates(response.getBody()))),
        () -> assertEquals(1, certificates(response.getBody()).size())
    );
  }

  @Test
  @DisplayName("Ej returnera utkast som är färdiga för signering när man söker på ej färdiga")
  void shallNotReturnDraftsWhichAreValidWhenQueryingInvalid() {
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

    api().updateCertificate(
        customUpdateCertificateRequest()
            .user(AJLA_DOCTOR_DTO)
            .certificate(certificate)
            .build(),
        certificateId(testCertificates)
    );

    final var response = api().getUnitCertificates(
        customGetUnitCertificatesRequest()
            .queryCriteria(
                CertificatesQueryCriteriaDTO.builder()
                    .statuses(List.of(CertificateStatusTypeDTO.UNSIGNED))
                    .validForSign(Boolean.FALSE)
                    .build()
            )
            .build()
    );

    assertEquals(0, certificates(response.getBody()).size(),
        "Expect list to be empty but contains: '%s'".formatted(certificates(response.getBody()))
    );
  }

  @Test
  @DisplayName("Returnera utkast som är färdiga för signering när man söker på färdiga")
  void shallReturnDraftsWhichAreValid() {
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

    api().updateCertificate(
        customUpdateCertificateRequest()
            .user(AJLA_DOCTOR_DTO)
            .certificate(certificate)
            .build(),
        certificateId(testCertificates)
    );

    final var response = api().getUnitCertificates(
        customGetUnitCertificatesRequest()
            .queryCriteria(
                CertificatesQueryCriteriaDTO.builder()
                    .statuses(List.of(CertificateStatusTypeDTO.UNSIGNED))
                    .validForSign(Boolean.TRUE)
                    .build()
            )
            .build()
    );

    assertAll(
        () -> assertTrue(
            () -> exists(certificates(response.getBody()), certificate(testCertificates)),
            () -> "Expected '%s' in result: '%s'".formatted(certificateId(testCertificates),
                certificates(response.getBody()))),
        () -> assertEquals(1, certificates(response.getBody()).size())
    );
  }

  @Test
  @DisplayName("Ej returnera utkast som är inte är färdig för signering när man söker på färdiga")
  void shallNotReturnDraftsWhichAreInvalidWhenQueryingValid() {
    testabilityApi().addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion())
            .fillType(TestabilityFillTypeDTO.EMPTY)
            .build()
    );

    final var response = api().getUnitCertificates(
        customGetUnitCertificatesRequest()
            .queryCriteria(
                CertificatesQueryCriteriaDTO.builder()
                    .statuses(List.of(CertificateStatusTypeDTO.UNSIGNED))
                    .validForSign(Boolean.TRUE)
                    .build()
            )
            .build()
    );

    assertEquals(0, certificates(response.getBody()).size(),
        "Expect list to be empty but contains: '%s'".formatted(certificates(response.getBody()))
    );
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
}
