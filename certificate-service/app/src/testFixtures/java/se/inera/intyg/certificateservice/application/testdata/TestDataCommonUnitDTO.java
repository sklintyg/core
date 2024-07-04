package se.inera.intyg.certificateservice.application.testdata;

import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProviderConstants.ALFA_REGIONEN_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProviderConstants.ALFA_REGIONEN_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProviderConstants.BETA_REGIONEN_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProviderConstants.BETA_REGIONEN_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_ADDRESS;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_CITY;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_EMAIL;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_INACTIVE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_PHONENUMBER;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_WORKPLACE_CODE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_ZIP_CODE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_VARDCENTRAL_ADDRESS;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_VARDCENTRAL_CITY;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_VARDCENTRAL_EMAIL;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_VARDCENTRAL_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_VARDCENTRAL_INACTIVE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_VARDCENTRAL_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_VARDCENTRAL_PHONENUMBER;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_VARDCENTRAL_WORKPLACE_CODE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_VARDCENTRAL_ZIP_CODE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_ALLERGIMOTTAGNINGEN_ADDRESS;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_ALLERGIMOTTAGNINGEN_CITY;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_ALLERGIMOTTAGNINGEN_EMAIL;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_ALLERGIMOTTAGNINGEN_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_ALLERGIMOTTAGNINGEN_INACTIVE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_ALLERGIMOTTAGNINGEN_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_ALLERGIMOTTAGNINGEN_PHONENUMBER;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_ALLERGIMOTTAGNINGEN_WORKPLACE_CODE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_ALLERGIMOTTAGNINGEN_ZIP_CODE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_HUDMOTTAGNINGEN_ADDRESS;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_HUDMOTTAGNINGEN_CITY;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_HUDMOTTAGNINGEN_EMAIL;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_HUDMOTTAGNINGEN_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_HUDMOTTAGNINGEN_INACTIVE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_HUDMOTTAGNINGEN_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_HUDMOTTAGNINGEN_PHONENUMBER;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_HUDMOTTAGNINGEN_WORKPLACE_CODE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_HUDMOTTAGNINGEN_ZIP_CODE;

import se.inera.intyg.certificateservice.application.common.dto.UnitDTO;

public class TestDataCommonUnitDTO {

  private TestDataCommonUnitDTO() {
    throw new IllegalStateException("Utility class");
  }

  public static final UnitDTO ALFA_REGIONEN_DTO = alfaRegionenDtoBuilder().build();
  public static final UnitDTO ALFA_MEDICINCENTRUM_DTO = alfaMedicincentrumDtoBuilder().build();
  public static final UnitDTO ALFA_ALLERGIMOTTAGNINGEN_DTO = alfaAllergimottagningenDtoBuilder().build();
  public static final UnitDTO ALFA_HUDMOTTAGNINGEN_DTO = alfaHudmottagningenDtoBuilder().build();
  public static final UnitDTO ALFA_VARDCENTRAL_DTO = alfaVardcentralDtoBuilder().build();
  public static final UnitDTO BETA_REGIONEN_DTO = betaRegionenDtoBuilder().build();

  public static UnitDTO.UnitDTOBuilder alfaRegionenDtoBuilder() {
    return UnitDTO.builder()
        .id(ALFA_REGIONEN_ID)
        .name(ALFA_REGIONEN_NAME);
  }

  public static UnitDTO.UnitDTOBuilder alfaMedicincentrumDtoBuilder() {
    return UnitDTO.builder()
        .id(ALFA_MEDICINCENTRUM_ID)
        .name(ALFA_MEDICINCENTRUM_NAME)
        .address(ALFA_MEDICINCENTRUM_ADDRESS)
        .zipCode(ALFA_MEDICINCENTRUM_ZIP_CODE)
        .city(ALFA_MEDICINCENTRUM_CITY)
        .email(ALFA_MEDICINCENTRUM_EMAIL)
        .phoneNumber(ALFA_MEDICINCENTRUM_PHONENUMBER)
        .workplaceCode(ALFA_MEDICINCENTRUM_WORKPLACE_CODE)
        .inactive(ALFA_MEDICINCENTRUM_INACTIVE.value());
  }

  public static UnitDTO.UnitDTOBuilder alfaAllergimottagningenDtoBuilder() {
    return UnitDTO.builder()
        .id(ALFA_ALLERGIMOTTAGNINGEN_ID)
        .name(ALFA_ALLERGIMOTTAGNINGEN_NAME)
        .address(ALFA_ALLERGIMOTTAGNINGEN_ADDRESS)
        .zipCode(ALFA_ALLERGIMOTTAGNINGEN_ZIP_CODE)
        .city(ALFA_ALLERGIMOTTAGNINGEN_CITY)
        .email(ALFA_ALLERGIMOTTAGNINGEN_EMAIL)
        .phoneNumber(ALFA_ALLERGIMOTTAGNINGEN_PHONENUMBER)
        .workplaceCode(ALFA_ALLERGIMOTTAGNINGEN_WORKPLACE_CODE)
        .inactive(ALFA_ALLERGIMOTTAGNINGEN_INACTIVE.value());
  }

  public static UnitDTO.UnitDTOBuilder alfaHudmottagningenDtoBuilder() {
    return UnitDTO.builder()
        .id(ALFA_HUDMOTTAGNINGEN_ID)
        .name(ALFA_HUDMOTTAGNINGEN_NAME)
        .address(ALFA_HUDMOTTAGNINGEN_ADDRESS)
        .zipCode(ALFA_HUDMOTTAGNINGEN_ZIP_CODE)
        .city(ALFA_HUDMOTTAGNINGEN_CITY)
        .email(ALFA_HUDMOTTAGNINGEN_EMAIL)
        .phoneNumber(ALFA_HUDMOTTAGNINGEN_PHONENUMBER)
        .workplaceCode(ALFA_HUDMOTTAGNINGEN_WORKPLACE_CODE)
        .inactive(ALFA_HUDMOTTAGNINGEN_INACTIVE.value());
  }

  public static UnitDTO.UnitDTOBuilder alfaVardcentralDtoBuilder() {
    return UnitDTO.builder()
        .id(ALFA_VARDCENTRAL_ID)
        .name(ALFA_VARDCENTRAL_NAME)
        .address(ALFA_VARDCENTRAL_ADDRESS)
        .zipCode(ALFA_VARDCENTRAL_ZIP_CODE)
        .city(ALFA_VARDCENTRAL_CITY)
        .email(ALFA_VARDCENTRAL_EMAIL)
        .phoneNumber(ALFA_VARDCENTRAL_PHONENUMBER)
        .workplaceCode(ALFA_VARDCENTRAL_WORKPLACE_CODE)
        .inactive(ALFA_VARDCENTRAL_INACTIVE.value());
  }

  public static UnitDTO.UnitDTOBuilder betaRegionenDtoBuilder() {
    return UnitDTO.builder()
        .id(BETA_REGIONEN_ID)
        .name(BETA_REGIONEN_NAME);
  }
}
