package se.inera.intyg.certificateservice.domain.testdata;

import static se.inera.intyg.certificateservice.domain.testdata.TestDataElementDataConstants.DATE_ELEMENT_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataElementDataConstants.DATE_ELEMENT_VALUE_DATE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataElementDataConstants.UNIT_ELEMENT_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_ALLERGIMOTTAGNINGEN_ADDRESS;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_ALLERGIMOTTAGNINGEN_CITY;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_ALLERGIMOTTAGNINGEN_PHONENUMBER;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_ALLERGIMOTTAGNINGEN_ZIP_CODE;

import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueUnitContactInformation;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;

public class TestDataElementData {

  public static final ElementData DATE = dateElementDataBuilder().build();
  public static final ElementData CONTACT_INFO = contactInfoElementDataBuilder().build();

  public static ElementData.ElementDataBuilder dateElementDataBuilder() {
    return ElementData.builder()
        .id(new ElementId(DATE_ELEMENT_ID))
        .value(
            ElementValueDate.builder()
                .date(DATE_ELEMENT_VALUE_DATE)
                .build()
        );
  }

  public static ElementData.ElementDataBuilder contactInfoElementDataBuilder() {
    return ElementData.builder()
        .id(new ElementId(UNIT_ELEMENT_ID))
        .value(
            ElementValueUnitContactInformation.builder()
                .address(ALFA_ALLERGIMOTTAGNINGEN_ADDRESS)
                .zipCode(ALFA_ALLERGIMOTTAGNINGEN_ZIP_CODE)
                .city(ALFA_ALLERGIMOTTAGNINGEN_CITY)
                .phoneNumber(ALFA_ALLERGIMOTTAGNINGEN_PHONENUMBER)
                .build()
        );
  }
}
