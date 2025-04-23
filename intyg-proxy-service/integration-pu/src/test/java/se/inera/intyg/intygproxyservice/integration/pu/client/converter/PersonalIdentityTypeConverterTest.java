package se.inera.intyg.intygproxyservice.integration.pu.client.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static se.inera.intyg.intygproxyservice.integration.pu.TestData.PERSON_ID_AS_PERSONNUMMER;
import static se.inera.intyg.intygproxyservice.integration.pu.TestData.personalIdentity;
import static se.inera.intyg.intygproxyservice.integration.pu.client.converter.PersonalIdentityTypeConverter.personId;

import org.junit.jupiter.api.Test;

class PersonalIdentityTypeConverterTest {

  @Test
  void shallReturnPersonIdAsPersonnummer() {
    assertEquals(PERSON_ID_AS_PERSONNUMMER, personId(personalIdentity()).id());
  }

  @Test
  void shallReturnNullIfPersonalIdentityIsNull() {
    assertNull(personId(null));
  }
}