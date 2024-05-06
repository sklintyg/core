package se.inera.intyg.certificateservice.domain.testdata;

import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_ADDRESS;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_CITY;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_EMAIL;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_PHONENUMBER;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_ZIP_CODE;
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
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_HUDMOTTAGNINGEN_ZIP_CODE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.BETA_HUDMOTTAGNINGEN_ADDRESS;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.BETA_HUDMOTTAGNINGEN_CITY;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.BETA_HUDMOTTAGNINGEN_EMAIL;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.BETA_HUDMOTTAGNINGEN_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.BETA_HUDMOTTAGNINGEN_INACTIVE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.BETA_HUDMOTTAGNINGEN_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.BETA_HUDMOTTAGNINGEN_PHONENUMBER;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.BETA_HUDMOTTAGNINGEN_ZIP_CODE;

import se.inera.intyg.certificateservice.domain.common.model.HsaId;
import se.inera.intyg.certificateservice.domain.unit.model.SubUnit;
import se.inera.intyg.certificateservice.domain.unit.model.UnitAddress;
import se.inera.intyg.certificateservice.domain.unit.model.UnitContactInfo;
import se.inera.intyg.certificateservice.domain.unit.model.UnitName;
import se.inera.intyg.certificateservice.domain.unit.model.WorkplaceCode;

public class TestDataSubUnit {

  public static final SubUnit ALFA_ALLERGIMOTTAGNINGEN = alfaAllergimottagningenBuilder().build();
  public static final SubUnit ALFA_HUDMOTTAGNINGEN = alfaHudmottagningenBuilder().build();
  public static final SubUnit ALFA_MEDICINSKT_CENTRUM = alfaMedicincentrumAsSubUnitBuilder().build();
  public static final SubUnit BETA_HUDMOTTAGNINGEN = betaHudMottagningenBuilder().build();

  public static SubUnit.SubUnitBuilder alfaAllergimottagningenBuilder() {
    return SubUnit.builder()
        .hsaId(new HsaId(ALFA_ALLERGIMOTTAGNINGEN_ID))
        .name(new UnitName(ALFA_ALLERGIMOTTAGNINGEN_NAME))
        .address(
            UnitAddress.builder()
                .address(ALFA_ALLERGIMOTTAGNINGEN_ADDRESS)
                .zipCode(ALFA_ALLERGIMOTTAGNINGEN_ZIP_CODE)
                .city(ALFA_ALLERGIMOTTAGNINGEN_CITY)
                .build()
        )
        .contactInfo(
            UnitContactInfo.builder()
                .email(ALFA_ALLERGIMOTTAGNINGEN_EMAIL)
                .phoneNumber(ALFA_ALLERGIMOTTAGNINGEN_PHONENUMBER)
                .build()
        )
        .workplaceCode(new WorkplaceCode(ALFA_ALLERGIMOTTAGNINGEN_WORKPLACE_CODE))
        .inactive(ALFA_ALLERGIMOTTAGNINGEN_INACTIVE);
  }

  public static SubUnit.SubUnitBuilder alfaHudmottagningenBuilder() {
    return SubUnit.builder()
        .hsaId(new HsaId(ALFA_HUDMOTTAGNINGEN_ID))
        .name(new UnitName(ALFA_HUDMOTTAGNINGEN_NAME))
        .address(
            UnitAddress.builder()
                .address(ALFA_HUDMOTTAGNINGEN_ADDRESS)
                .zipCode(ALFA_HUDMOTTAGNINGEN_ZIP_CODE)
                .city(ALFA_HUDMOTTAGNINGEN_CITY)
                .build()
        )
        .contactInfo(
            UnitContactInfo.builder()
                .email(ALFA_HUDMOTTAGNINGEN_EMAIL)
                .phoneNumber(ALFA_HUDMOTTAGNINGEN_PHONENUMBER)
                .build()
        )
        .inactive(ALFA_HUDMOTTAGNINGEN_INACTIVE);
  }

  public static SubUnit.SubUnitBuilder alfaMedicincentrumAsSubUnitBuilder() {
    return SubUnit.builder()
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

  public static SubUnit.SubUnitBuilder betaHudMottagningenBuilder() {
    return SubUnit.builder()
        .hsaId(new HsaId(BETA_HUDMOTTAGNINGEN_ID))
        .name(new UnitName(BETA_HUDMOTTAGNINGEN_NAME))
        .address(
            UnitAddress.builder()
                .address(BETA_HUDMOTTAGNINGEN_ADDRESS)
                .zipCode(BETA_HUDMOTTAGNINGEN_ZIP_CODE)
                .city(BETA_HUDMOTTAGNINGEN_CITY)
                .build()
        )
        .contactInfo(
            UnitContactInfo.builder()
                .email(BETA_HUDMOTTAGNINGEN_EMAIL)
                .phoneNumber(BETA_HUDMOTTAGNINGEN_PHONENUMBER)
                .build()
        )
        .inactive(BETA_HUDMOTTAGNINGEN_INACTIVE);
  }
}
