package se.inera.intyg.certificateservice.application.testdata;

import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProviderConstants.ALFA_REGIONEN_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProviderConstants.ALFA_REGIONEN_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_ADDRESS;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_CITY;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_EMAIL;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_PHONENUMBER;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_WORKPLACE_CODE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_ZIP_CODE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_ALLERGIMOTTAGNINGEN_ADDRESS;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_ALLERGIMOTTAGNINGEN_CITY;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_ALLERGIMOTTAGNINGEN_EMAIL;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_ALLERGIMOTTAGNINGEN_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_ALLERGIMOTTAGNINGEN_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_ALLERGIMOTTAGNINGEN_PHONENUMBER;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_ALLERGIMOTTAGNINGEN_WORKPLACE_CODE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_ALLERGIMOTTAGNINGEN_ZIP_CODE;

import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.UnitEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.UnitType;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.UnitTypeEntity;

public class TestDataUnitEntity {

  private TestDataUnitEntity() {
    throw new IllegalStateException("Utility class");
  }

  public static final UnitEntity ALFA_REGIONEN_ENTITY = alfaRegionenEntityBuilder().build();

  public static final UnitEntity ALFA_MEDICINCENTRUM_ENTITY = alfaMedicinCentrumEntityBuilder().build();
  public static final UnitEntity ALFA_ALLERGIMOTTAGNINGEN_ENTITY = alfaAllergimottagningenEntityBuilder().build();


  public static UnitEntity.UnitEntityBuilder alfaRegionenEntityBuilder() {
    return UnitEntity.builder()
        .type(
            UnitTypeEntity.builder()
                .type(UnitType.CARE_PROVIDER.name())
                .key(UnitType.CARE_PROVIDER.getKey())
                .build()
        )
        .hsaId(ALFA_REGIONEN_ID)
        .name(ALFA_REGIONEN_NAME);
  }

  public static UnitEntity.UnitEntityBuilder alfaMedicinCentrumEntityBuilder() {
    return UnitEntity.builder()
        .type(
            UnitTypeEntity.builder()
                .type(UnitType.CARE_UNIT.name())
                .key(UnitType.CARE_UNIT.getKey())
                .build()
        )
        .hsaId(ALFA_MEDICINCENTRUM_ID)
        .name(ALFA_MEDICINCENTRUM_NAME)
        .address(ALFA_MEDICINCENTRUM_ADDRESS)
        .zipCode(ALFA_MEDICINCENTRUM_ZIP_CODE)
        .city(ALFA_MEDICINCENTRUM_CITY)
        .phoneNumber(ALFA_MEDICINCENTRUM_PHONENUMBER)
        .email(ALFA_MEDICINCENTRUM_EMAIL)
        .workplaceCode(ALFA_MEDICINCENTRUM_WORKPLACE_CODE);
  }

  public static UnitEntity.UnitEntityBuilder alfaAllergimottagningenEntityBuilder() {
    return UnitEntity.builder()
        .type(
            UnitTypeEntity.builder()
                .type(UnitType.SUB_UNIT.name())
                .key(UnitType.SUB_UNIT.getKey())
                .build()
        )
        .hsaId(ALFA_ALLERGIMOTTAGNINGEN_ID)
        .name(ALFA_ALLERGIMOTTAGNINGEN_NAME)
        .address(ALFA_ALLERGIMOTTAGNINGEN_ADDRESS)
        .zipCode(ALFA_ALLERGIMOTTAGNINGEN_ZIP_CODE)
        .city(ALFA_ALLERGIMOTTAGNINGEN_CITY)
        .phoneNumber(ALFA_ALLERGIMOTTAGNINGEN_PHONENUMBER)
        .email(ALFA_ALLERGIMOTTAGNINGEN_EMAIL)
        .workplaceCode(ALFA_ALLERGIMOTTAGNINGEN_WORKPLACE_CODE);
  }

}
