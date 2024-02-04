package se.inera.intyg.certificateservice.domain.testdata;

import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_ALLERGIMOTTAGNINGEN_ADDRESS;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_ALLERGIMOTTAGNINGEN_CITY;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_ALLERGIMOTTAGNINGEN_EMAIL;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_ALLERGIMOTTAGNINGEN_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_ALLERGIMOTTAGNINGEN_INACTIVE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_ALLERGIMOTTAGNINGEN_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_ALLERGIMOTTAGNINGEN_PHONENUMBER;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_ALLERGIMOTTAGNINGEN_ZIP_CODE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_HUDMOTTAGNINGEN_ADDRESS;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_HUDMOTTAGNINGEN_CITY;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_HUDMOTTAGNINGEN_EMAIL;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_HUDMOTTAGNINGEN_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_HUDMOTTAGNINGEN_INACTIVE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_HUDMOTTAGNINGEN_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_HUDMOTTAGNINGEN_PHONENUMBER;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_HUDMOTTAGNINGEN_ZIP_CODE;

import se.inera.intyg.certificateservice.domain.certificate.model.HsaId;
import se.inera.intyg.certificateservice.domain.certificate.model.SubUnit;
import se.inera.intyg.certificateservice.domain.certificate.model.UnitAddress;
import se.inera.intyg.certificateservice.domain.certificate.model.UnitContactInfo;
import se.inera.intyg.certificateservice.domain.certificate.model.UnitName;

public class TestDataSubUnit {

  public static final SubUnit ALFA_ALLERGIMOTTAGNINGEN = alfaAllergimottagningenBuilder().build();
  public static final SubUnit ALFA_HUDMOTTAGNINGEN = alfaHudmottagningenBuilder().build();

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
}
