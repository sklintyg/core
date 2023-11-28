package se.inera.intyg.intygproxyservice.integration.authorization.client.converter;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.HsaSystemRole;
import se.riv.infrastructure.directory.authorizationmanagement.v2.HsaSystemRoleType;

class HsaSystemRoleTypeConverterTest {

  private static final HsaSystemRoleTypeConverter converter = new HsaSystemRoleTypeConverter();

  @Test
  void shouldConvertNull() {
    final var response = converter.convert(null);

    assertEquals(HsaSystemRole.builder().build(), response);
  }


  @Test
  void shouldConvertRole() {
    final var type = new HsaSystemRoleType();
    type.setRole("ROLE");

    final var response = converter.convert(type);

    assertEquals(type.getRole(), response.getRole());
  }

  @Test
  void shouldConvertSystemId() {
    final var type = new HsaSystemRoleType();
    type.setSystemId("SYSTEM ID");

    final var response = converter.convert(type);

    assertEquals(type.getSystemId(), response.getSystemId());
  }

}