package se.inera.intyg.certificateservice.integrationtest.common.tests;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO.SIGNED;
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
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultTestablilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.certificate;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.certificateId;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.certificates;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.exists;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.PersonIdDTO;
import se.inera.intyg.certificateservice.application.unit.dto.CertificatesQueryCriteriaDTO;
import se.inera.intyg.certificateservice.integrationtest.common.setup.BaseIntegrationIT;

public abstract class GetUnitCertificatesWhenSignedIT extends BaseIntegrationIT {


  @Test
  @DisplayName("Returnera lista med intyg som har utfärdats på mottagning")
  void shallReturnCertificatesOnTheSameSubUnit() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED)
    );

    final var response = api().getUnitCertificates(
        customGetUnitCertificatesRequest()
            .queryCriteria(
                CertificatesQueryCriteriaDTO.builder()
                    .statuses(List.of(SIGNED))
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
  @DisplayName("Returnera lista med intyg som har utfärdats på vårdenhet")
  void shallReturnCertificatesOnTheSameCareUnit() {
    final var testCertificates = testabilityApi().addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion(), SIGNED)
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .build()
    );

    final var response = api().getUnitCertificates(
        customGetUnitCertificatesRequest()
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .queryCriteria(
                CertificatesQueryCriteriaDTO.builder()
                    .statuses(List.of(SIGNED))
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
  @DisplayName("Returnera lista med intyg som har utfärdats på mottagning inom vårdenhet")
  void shallReturnCertificatesIssuedOnSubUnitOnTheSameCareUnit() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED)
    );

    final var response = api().getUnitCertificates(
        customGetUnitCertificatesRequest()
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .queryCriteria(
                CertificatesQueryCriteriaDTO.builder()
                    .statuses(List.of(SIGNED))
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
  @DisplayName("Ej returnera intyg som utfärdats på annan mottagning")
  void shallNotReturnCertificatesOnDifferentSubUnit() {
    testabilityApi().addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion())
            .unit(ALFA_HUDMOTTAGNINGEN_DTO)
            .build()
    );

    final var response = api().getUnitCertificates(
        customGetUnitCertificatesRequest()
            .queryCriteria(
                CertificatesQueryCriteriaDTO.builder()
                    .statuses(List.of(SIGNED))
                    .build()
            )
            .build()
    );

    assertEquals(0, certificates(response.getBody()).size(),
        "Expect list to be empty but contains: '%s'".formatted(certificates(response.getBody()))
    );
  }

  @Test
  @DisplayName("Ej returnera intyg som utfärdats på annan vårdenhet")
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
            .queryCriteria(
                CertificatesQueryCriteriaDTO.builder()
                    .statuses(List.of(SIGNED))
                    .build()
            )
            .build()
    );

    assertEquals(0, certificates(response.getBody()).size(),
        "Expect list to be empty but contains: '%s'".formatted(certificates(response.getBody()))
    );
  }

  @Test
  @DisplayName("Ej returnera intyg som utfärdats på vårdenheten när man är på mottagningen")
  void shallNotReturnCertificatesOnCareUnitWhenOnSubUnit() {
    testabilityApi().addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion())
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .build()
    );

    final var response = api().getUnitCertificates(
        customGetUnitCertificatesRequest()
            .unit(ALFA_ALLERGIMOTTAGNINGEN_DTO)
            .queryCriteria(
                CertificatesQueryCriteriaDTO.builder()
                    .statuses(List.of(SIGNED))
                    .build()
            )
            .build()
    );

    assertEquals(0, certificates(response.getBody()).size(),
        "Expect list to be empty but contains: '%s'".formatted(certificates(response.getBody()))
    );
  }

  @Test
  @DisplayName("Returnera lista med intyg som har signerats datum efter från och med datum")
  void shallReturnCertificatesSavedAfterFrom() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED)
    );

    final var response = api().getUnitCertificates(
        customGetUnitCertificatesRequest()
            .queryCriteria(
                CertificatesQueryCriteriaDTO.builder()
                    .from(LocalDateTime.now().minusDays(1))
                    .statuses(List.of(SIGNED))
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
  @DisplayName("Ej returnera intyg som har signerats datum före från och med datum")
  void shallNotReturnCertificatesSavedBeforeFrom() {
    testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var response = api().getUnitCertificates(
        customGetUnitCertificatesRequest()
            .queryCriteria(
                CertificatesQueryCriteriaDTO.builder()
                    .from(LocalDateTime.now().plusDays(1))
                    .statuses(List.of(SIGNED))
                    .build()
            )
            .build()
    );

    assertEquals(0, certificates(response.getBody()).size(),
        "Expect list to be empty but contains: '%s'".formatted(certificates(response.getBody()))
    );
  }

  @Test
  @DisplayName("Returnera lista med intyg som har signerats datum före till och med datum")
  void shallReturnCertificatesSavedBeforeTo() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED)
    );

    final var response = api().getUnitCertificates(
        customGetUnitCertificatesRequest()
            .queryCriteria(
                CertificatesQueryCriteriaDTO.builder()
                    .to(LocalDateTime.now().plusDays(1))
                    .statuses(List.of(SIGNED))
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
  @DisplayName("Ej returnera intyg som har signerats datum efter till och med datum")
  void shallNotReturnCertificatesSavedAfterTo() {
    testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var response = api().getUnitCertificates(
        customGetUnitCertificatesRequest()
            .queryCriteria(
                CertificatesQueryCriteriaDTO.builder()
                    .to(LocalDateTime.now().minusDays(1))
                    .statuses(List.of(SIGNED))
                    .build()
            )
            .build()
    );

    assertEquals(0, certificates(response.getBody()).size(),
        "Expect list to be empty but contains: '%s'".formatted(certificates(response.getBody()))
    );
  }

  @Test
  @DisplayName("Returnera lista med intyg som har utfärdats på patienten")
  void shallReturnCertificatesSavedOnPatient() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED)
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
                    .statuses(List.of(SIGNED))
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
  @DisplayName("Ej returnera intyg som har utfärdats på annan patient")
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
                    .statuses(List.of(SIGNED))
                    .build()
            )
            .build()
    );

    assertEquals(0, certificates(response.getBody()).size(),
        "Expect list to be empty but contains: '%s'".formatted(certificates(response.getBody()))
    );
  }


  @Test
  @DisplayName("Returnera lista med intyg som har signerats av vald användare")
  void shallReturnCertificatesSavedBySameStaff() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED)
    );

    final var response = api().getUnitCertificates(
        customGetUnitCertificatesRequest()
            .queryCriteria(
                CertificatesQueryCriteriaDTO.builder()
                    .issuedByStaffId(AJLA_DOCTOR_DTO.getId())
                    .statuses(List.of(SIGNED))
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
  @DisplayName("Ej returnera intyg som har signerats av annan användare")
  void shallNotReturnCertificatesSavedByDifferentStaff() {
    testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var response = api().getUnitCertificates(
        customGetUnitCertificatesRequest()
            .queryCriteria(
                CertificatesQueryCriteriaDTO.builder()
                    .issuedByStaffId(ALVA_VARDADMINISTRATOR_DTO.getId())
                    .statuses(List.of(SIGNED))
                    .build()
            )
            .build()
    );

    assertEquals(0, certificates(response.getBody()).size(),
        "Expect list to be empty but contains: '%s'".formatted(certificates(response.getBody()))
    );
  }

  @Test
  @DisplayName("Ej returnera intyg som inte har signerats")
  void shallNotReturnCertificatesWithDifferentStatus() {
    testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var response = api().getUnitCertificates(
        customGetUnitCertificatesRequest()
            .queryCriteria(
                CertificatesQueryCriteriaDTO.builder()
                    .statuses(List.of(SIGNED))
                    .build()
            )
            .build()
    );

    assertEquals(0, certificates(response.getBody()).size(),
        "Expect list to be empty but contains: '%s'".formatted(certificates(response.getBody()))
    );
  }
}
