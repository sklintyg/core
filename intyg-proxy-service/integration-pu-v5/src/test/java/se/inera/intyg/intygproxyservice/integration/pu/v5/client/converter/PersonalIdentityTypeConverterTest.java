package se.inera.intyg.intygproxyservice.integration.pu.v5.client.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static se.inera.intyg.intygproxyservice.integration.pu.v5.TestData.PERSON_ID_AS_PERSONNUMMER;
import static se.inera.intyg.intygproxyservice.integration.pu.v5.TestData.personalIdentity;
import static se.inera.intyg.intygproxyservice.integration.pu.v5.client.converter.PersonalIdentityTypeConverter.personId;

import org.junit.jupiter.api.Test;

class PersonalIdentityTypeConverterTest {

  @Test
  void shallReturnPersonIdAsPersonnummer() {
    assertEquals(PERSON_ID_AS_PERSONNUMMER, personId(personalIdentity()).id());
  }

  @Test
  void shallReturnPersonIdWithNullIfPersonalIdentityIsNull() {
    assertNull(personId(null).id());
  }
}