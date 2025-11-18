package se.inera.intyg.certificateservice.application.testdata;

import static se.inera.intyg.certificateservice.application.testdata.TestDataUnitEntity.ALFA_ALLERGIMOTTAGNINGEN_ENTITY;
import static se.inera.intyg.certificateservice.application.testdata.TestDataUnitEntity.ALFA_MEDICINCENTRUM_ENTITY;
import static se.inera.intyg.certificateservice.application.testdata.TestDataUnitEntity.ALFA_REGIONEN_ENTITY;
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

import java.time.LocalDateTime;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.UnitType;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.UnitTypeEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.UnitVersionEntity;

public class TestDataUnitVersionEntity {

  private TestDataUnitVersionEntity() {
    throw new IllegalStateException("Utility class");
  }

  public static final LocalDateTime VALID_TO = LocalDateTime.now().plusYears(1);
  public static final UnitVersionEntity ALFA_REGIONEN_VERSION_ENTITY =
      alfaRegionenVersionEntityBuilder().build();
  public static final UnitVersionEntity ALFA_MEDICINCENTRUM_VERSION_ENTITY =
      alfaMedicinCentrumVersionEntityBuilder().build();
  public static final UnitVersionEntity ALFA_ALLERGIMOTTAGNINGEN_VERSION_ENTITY =
      alfaAllergimottagningenVersionEntityBuilder().build();


  public static UnitVersionEntity.UnitVersionEntityBuilder alfaRegionenVersionEntityBuilder() {
    return UnitVersionEntity.builder()
        .type(
            UnitTypeEntity.builder()
                .type(UnitType.CARE_PROVIDER.name())
                .key(UnitType.CARE_PROVIDER.getKey())
                .build()
        )
        .validTo(VALID_TO)
        .hsaId(ALFA_REGIONEN_ID)
        .name(ALFA_REGIONEN_NAME)
        .unit(ALFA_REGIONEN_ENTITY);
  }


  public static UnitVersionEntity.UnitVersionEntityBuilder alfaMedicinCentrumVersionEntityBuilder() {
    return UnitVersionEntity.builder()
        .type(
            UnitTypeEntity.builder()
                .type(UnitType.CARE_UNIT.name())
                .key(UnitType.CARE_UNIT.getKey())
                .build()
        )
        .validTo(VALID_TO)
        .hsaId(ALFA_MEDICINCENTRUM_ID)
        .name(ALFA_MEDICINCENTRUM_NAME)
        .address(ALFA_MEDICINCENTRUM_ADDRESS)
        .zipCode(ALFA_MEDICINCENTRUM_ZIP_CODE)
        .city(ALFA_MEDICINCENTRUM_CITY)
        .phoneNumber(ALFA_MEDICINCENTRUM_PHONENUMBER)
        .email(ALFA_MEDICINCENTRUM_EMAIL)
        .workplaceCode(ALFA_MEDICINCENTRUM_WORKPLACE_CODE)
        .unit(ALFA_MEDICINCENTRUM_ENTITY);
  }

  public static UnitVersionEntity.UnitVersionEntityBuilder alfaAllergimottagningenVersionEntityBuilder() {
    return UnitVersionEntity.builder()
        .type(
            UnitTypeEntity.builder()
                .type(UnitType.SUB_UNIT.name())
                .key(UnitType.SUB_UNIT.getKey())
                .build()
        )
        .validTo(VALID_TO)
        .hsaId(ALFA_ALLERGIMOTTAGNINGEN_ID)
        .name(ALFA_ALLERGIMOTTAGNINGEN_NAME)
        .address(ALFA_ALLERGIMOTTAGNINGEN_ADDRESS)
        .zipCode(ALFA_ALLERGIMOTTAGNINGEN_ZIP_CODE)
        .city(ALFA_ALLERGIMOTTAGNINGEN_CITY)
        .phoneNumber(ALFA_ALLERGIMOTTAGNINGEN_PHONENUMBER)
        .email(ALFA_ALLERGIMOTTAGNINGEN_EMAIL)
        .workplaceCode(ALFA_ALLERGIMOTTAGNINGEN_WORKPLACE_CODE)
        .unit(ALFA_ALLERGIMOTTAGNINGEN_ENTITY);
  }

}
