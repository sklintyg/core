package se.inera.intyg.certificateservice.application.testdata;

import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_ADDRESS;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_CITY;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_EMAIL;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_PHONENUMBER;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_ZIP_CODE;

import se.inera.intyg.certificateservice.application.common.dto.UnitDTO;

public class TestDataCommonUnitDTO {

  public static final UnitDTO ALFA_MEDICINCENTRUM_DTO = alfaMedicincentrumBuilder().build();

  public static UnitDTO.UnitDTOBuilder alfaMedicincentrumBuilder() {
    return UnitDTO.builder()
        .id(ALFA_MEDICINCENTRUM_ID)
        .name(ALFA_MEDICINCENTRUM_NAME)
        .address(ALFA_MEDICINCENTRUM_ADDRESS)
        .zipCode(ALFA_MEDICINCENTRUM_ZIP_CODE)
        .city(ALFA_MEDICINCENTRUM_CITY)
        .email(ALFA_MEDICINCENTRUM_EMAIL)
        .phoneNumber(ALFA_MEDICINCENTRUM_PHONENUMBER);
  }
}
