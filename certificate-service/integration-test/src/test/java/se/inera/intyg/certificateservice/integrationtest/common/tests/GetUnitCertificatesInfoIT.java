package se.inera.intyg.certificateservice.integrationtest.common.tests;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonStaffDTO.AJLA_DOKTOR;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonStaffDTO.ALVA_VARDADMINISTRATOR;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_HUDMOTTAGNINGEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_VARDCENTRAL_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.ALVA_VARDADMINISTRATOR_DTO;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.customGetUnitCertificatesInfoRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.customTestabilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultGetUnitCertificatesInfoRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultTestablilityCertificateRequest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.integrationtest.common.setup.BaseIntegrationIT;
import se.inera.intyg.certificateservice.integrationtest.common.util.StaffUtil;

public abstract class GetUnitCertificatesInfoIT extends BaseIntegrationIT {


  @Test
  @DisplayName("Returnera lista av personal som har sparat utkast på mottagning")
  void shallReturnAListOfStaffOnTheSameSubUnit() {
    testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion()),
        defaultTestablilityCertificateRequest(type(), typeVersion()),
        customTestabilityCertificateRequest(type(), typeVersion())
            .user(ALVA_VARDADMINISTRATOR_DTO)
            .build()
    );

    final var response = api().getUnitCertificatesInfo(
        defaultGetUnitCertificatesInfoRequest()
    );

    final var staff = StaffUtil.staff(response.getBody());

    assertAll(
        () -> assertTrue(() -> staff.contains(AJLA_DOKTOR),
            () -> "Expected '%s' in result: '%s'".formatted(AJLA_DOKTOR, staff)),
        () -> assertTrue(() -> staff.contains(ALVA_VARDADMINISTRATOR),
            () -> "Expected '%s' in result: '%s'".formatted(ALVA_VARDADMINISTRATOR, staff)),
        () -> assertEquals(2, staff.size())
    );
  }

  @Test
  @DisplayName("Returnera lista av personal som har sparat utkast på vårdenhet")
  void shallReturnAListOfStaffOnTheSameCareUnit() {
    testabilityApi().addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion())
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .build(),
        customTestabilityCertificateRequest(type(), typeVersion())
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .build(),
        customTestabilityCertificateRequest(type(), typeVersion())
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .user(ALVA_VARDADMINISTRATOR_DTO)
            .build()
    );

    final var response = api().getUnitCertificatesInfo(
        customGetUnitCertificatesInfoRequest()
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .build()
    );

    final var staff = StaffUtil.staff(response.getBody());

    assertAll(
        () -> assertTrue(() -> staff.contains(AJLA_DOKTOR),
            () -> "Expected '%s' in result: '%s'".formatted(AJLA_DOKTOR, staff)),
        () -> assertTrue(() -> staff.contains(ALVA_VARDADMINISTRATOR),
            () -> "Expected '%s' in result: '%s'".formatted(ALVA_VARDADMINISTRATOR, staff)),
        () -> assertEquals(2, staff.size())
    );
  }

  @Test
  @DisplayName("Inkludera inte personal som har sparat utkast på annan mottagning")
  void shallReturnAListOfStaffNotIncludingStaffOnDifferentSubUnit() {
    testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion()),
        customTestabilityCertificateRequest(type(), typeVersion())
            .unit(ALFA_HUDMOTTAGNINGEN_DTO)
            .user(ALVA_VARDADMINISTRATOR_DTO)
            .build()
    );

    final var response = api().getUnitCertificatesInfo(
        defaultGetUnitCertificatesInfoRequest()
    );

    final var staff = StaffUtil.staff(response.getBody());

    assertAll(
        () -> assertTrue(() -> staff.contains(AJLA_DOKTOR),
            () -> "Expected '%s' in result: '%s'".formatted(AJLA_DOKTOR, staff)),
        () -> assertFalse(() -> staff.contains(ALVA_VARDADMINISTRATOR),
            () -> "Didnt expect '%s' in result: '%s'".formatted(ALVA_VARDADMINISTRATOR, staff)),
        () -> assertEquals(1, staff.size())
    );
  }

  @Test
  @DisplayName("Inkludera inte personal som har sparat utkast på annan vårdenhet")
  void shallReturnAListOfStaffNotIncludingStaffOnDifferentCareUnit() {
    testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion()),
        customTestabilityCertificateRequest(type(), typeVersion())
            .careUnit(ALFA_VARDCENTRAL_DTO)
            .unit(ALFA_VARDCENTRAL_DTO)
            .user(ALVA_VARDADMINISTRATOR_DTO)
            .build()
    );

    final var response = api().getUnitCertificatesInfo(
        customGetUnitCertificatesInfoRequest()
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .build()
    );

    final var staff = StaffUtil.staff(response.getBody());

    assertAll(
        () -> assertTrue(() -> staff.contains(AJLA_DOKTOR),
            () -> "Expected '%s' in result: '%s'".formatted(AJLA_DOKTOR, staff)),
        () -> assertFalse(() -> staff.contains(ALVA_VARDADMINISTRATOR),
            () -> "Didnt expect '%s' in result: '%s'".formatted(ALVA_VARDADMINISTRATOR, staff)),
        () -> assertEquals(1, staff.size())
    );
  }
}
