package se.inera.intyg.intygproxyservice.integration.pu.v5.client.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static se.inera.intyg.intygproxyservice.integration.pu.v5.TestData.PERSON_ID_AS_PERSONNUMMER;
import static se.inera.intyg.intygproxyservice.integration.pu.v5.TestData.personalIdentity;
import static se.inera.intyg.intygproxyservice.integration.pu.v5.client.converter.PersonalIdentityTypeConverter.extension;

import org.junit.jupiter.api.Test;

class PersonalIdentityTypeConverterTest {

  @Test
  void shallReturnExtensionAsPersonnummer() {
    assertEquals(PERSON_ID_AS_PERSONNUMMER, extension(personalIdentity()));
  }

  @Test
  void shallReturnNullIfPersonalIdentityIsNull() {
    assertNull(extension(null));
  }
}