package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.application.testdata.TestDataUnitEntity.ALFA_REGIONEN_ENTITY;
import static se.inera.intyg.certificateservice.application.testdata.TestDataUnitEntity.alfaRegionenEntityBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProviderConstants.BETA_REGIONEN_ID;

import org.junit.jupiter.api.Test;

class UnitEntityTest {

  @Test
  void shallUpdateFields() {
    var target = alfaRegionenEntityBuilder().build();
    var originalHsaId = target.getHsaId();
    var originalKey = target.getKey();

    var source = alfaRegionenEntityBuilder()
        .name("NewName")
        .address("NewAddress")
        .zipCode("99999")
        .city("NewCity")
        .phoneNumber("010-00 00 00")
        .email("new@example.com")
        .workplaceCode("NEWCODE")
        .type(new UnitTypeEntity())
        .build();

    target.updateWith(source);

    assertEquals("NewName", target.getName());
    assertEquals("NewAddress", target.getAddress());
    assertEquals("99999", target.getZipCode());
    assertEquals("NewCity", target.getCity());
    assertEquals("010-00 00 00", target.getPhoneNumber());
    assertEquals("new@example.com", target.getEmail());
    assertEquals("NEWCODE", target.getWorkplaceCode());
    assertEquals(originalHsaId, target.getHsaId(), "hsaId must remain unchanged");
    assertEquals(originalKey, target.getKey(), "key must remain unchanged");

  }

  @Test
  void shallThrowExceptionWhenSourceIsNull() {
    assertThrows(IllegalArgumentException.class, () -> ALFA_REGIONEN_ENTITY.updateWith(null));
  }

  @Test
  void shallNotFindDiffWhenEntityHasSameValues() {
    assertFalse(ALFA_REGIONEN_ENTITY.hasDiff(alfaRegionenEntityBuilder().build()));
  }

  @Test
  void shallFindDiffWhenNameDiffers() {
    assertTrue(ALFA_REGIONEN_ENTITY.hasDiff(alfaRegionenEntityBuilder().name("Diff").build()),
        "name");
  }

  @Test
  void shallFindDiffWhenAddressDiffers() {
    assertTrue(ALFA_REGIONEN_ENTITY.hasDiff(alfaRegionenEntityBuilder().address("Addr").build()),
        "address");
  }

  @Test
  void shallFindDiffWhenZipCodeDiffers() {
    assertTrue(ALFA_REGIONEN_ENTITY.hasDiff(alfaRegionenEntityBuilder().zipCode("12345").build()),
        "zipCode");
  }

  @Test
  void shallFindDiffWhenCityDiffers() {
    assertTrue(ALFA_REGIONEN_ENTITY.hasDiff(alfaRegionenEntityBuilder().city("City").build()),
        "city");
  }

  @Test
  void shallFindDiffWhenPhoneNumberDiffers() {
    assertTrue(ALFA_REGIONEN_ENTITY.hasDiff(
        alfaRegionenEntityBuilder().phoneNumber("020-10 10 10").build()), "phoneNumber");
  }

  @Test
  void shallFindDiffWhenEmailDiffers() {
    assertTrue(ALFA_REGIONEN_ENTITY.hasDiff(
        alfaRegionenEntityBuilder().email("other@example.com").build()), "email");
  }

  @Test
  void shallFindDiffWhenWorkplaceCodeDiffers() {
    assertTrue(
        ALFA_REGIONEN_ENTITY.hasDiff(alfaRegionenEntityBuilder().workplaceCode("WC").build()),
        "workplaceCode");
  }

  @Test
  void shallFindDiffWhenAllFieldsDiffer() {
    var allDiff = alfaRegionenEntityBuilder()
        .name("N")
        .address("A")
        .zipCode("Z")
        .city("C")
        .phoneNumber("P")
        .email("e@x.se")
        .workplaceCode("W")
        .build();

    assertTrue(ALFA_REGIONEN_ENTITY.hasDiff(allDiff), "all fields");
  }

  @Test
  void shallThrowExceptionWhenOtherIsNull() {
    assertThrows(IllegalArgumentException.class, () -> ALFA_REGIONEN_ENTITY.hasDiff(null));
  }

  @Test
  void shallThrowExceptionWhenComparingUnitWithDifferentHsaId() {
    var unitWithDifferentHsaId = alfaRegionenEntityBuilder()
        .hsaId(BETA_REGIONEN_ID)
        .build();
    assertThrows(IllegalArgumentException.class,
        () -> ALFA_REGIONEN_ENTITY.hasDiff(unitWithDifferentHsaId));
  }
}
