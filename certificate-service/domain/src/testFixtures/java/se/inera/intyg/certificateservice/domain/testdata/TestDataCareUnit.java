package se.inera.intyg.certificateservice.domain.testdata;

import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_ADDRESS;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_CITY;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_EMAIL;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_PHONENUMBER;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_ZIP_CODE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_VARDCENTRAL_ADDRESS;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_VARDCENTRAL_CITY;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_VARDCENTRAL_EMAIL;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_VARDCENTRAL_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_VARDCENTRAL_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_VARDCENTRAL_PHONENUMBER;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_VARDCENTRAL_ZIP_CODE;

import se.inera.intyg.certificateservice.domain.common.model.HsaId;
import se.inera.intyg.certificateservice.domain.unit.model.CareUnit;
import se.inera.intyg.certificateservice.domain.unit.model.CareUnit.CareUnitBuilder;
import se.inera.intyg.certificateservice.domain.unit.model.UnitAddress;
import se.inera.intyg.certificateservice.domain.unit.model.UnitContactInfo;
import se.inera.intyg.certificateservice.domain.unit.model.UnitName;

public class TestDataCareUnit {

  public static final CareUnit ALFA_MEDICINCENTRUM = alfaMedicincentrumBuilder().build();
  public static final CareUnit ALFA_VARDCENTRAL = alfaVardcentralBuilder().build();

  public static CareUnitBuilder alfaMedicincentrumBuilder() {
    return CareUnit.builder()
        .hsaId(new HsaId(ALFA_MEDICINCENTRUM_ID))
        .name(new UnitName(ALFA_MEDICINCENTRUM_NAME))
        .address(
            UnitAddress.builder()
                .address(ALFA_MEDICINCENTRUM_ADDRESS)
                .zipCode(ALFA_MEDICINCENTRUM_ZIP_CODE)
                .city(ALFA_MEDICINCENTRUM_CITY)
                .build()
        )
        .contactInfo(
            UnitContactInfo.builder()
                .email(ALFA_MEDICINCENTRUM_EMAIL)
                .phoneNumber(ALFA_MEDICINCENTRUM_PHONENUMBER)
                .build()
        );
  }

  public static CareUnitBuilder alfaVardcentralBuilder() {
    return CareUnit.builder()
        .hsaId(new HsaId(ALFA_VARDCENTRAL_ID))
        .name(new UnitName(ALFA_VARDCENTRAL_NAME))
        .address(
            UnitAddress.builder()
                .address(ALFA_VARDCENTRAL_ADDRESS)
                .zipCode(ALFA_VARDCENTRAL_ZIP_CODE)
                .city(ALFA_VARDCENTRAL_CITY)
                .build()
        )
        .contactInfo(
            UnitContactInfo.builder()
                .email(ALFA_VARDCENTRAL_EMAIL)
                .phoneNumber(ALFA_VARDCENTRAL_PHONENUMBER)
                .build()
        );
  }
}
