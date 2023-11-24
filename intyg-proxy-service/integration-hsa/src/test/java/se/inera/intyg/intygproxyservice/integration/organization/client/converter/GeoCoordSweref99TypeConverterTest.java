package se.inera.intyg.intygproxyservice.integration.organization.client.converter;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.riv.infrastructure.directory.organization.getunitresponder.v4.GeoCoordSWEREF99Type;

class GeoCoordSweref99TypeConverterTest {

  private final GeoCoordSweref99TypeConverter converter = new GeoCoordSweref99TypeConverter();

  private static GeoCoordSWEREF99Type type;

  @BeforeEach
  void setup() {
    type = new GeoCoordSWEREF99Type();
    type.setECoordinate("E");
    type.setNCoordinate("N");
  }

  @Test
  void shouldConvertE() {
    final var response = converter.convert(type);

    assertEquals(type.getECoordinate(), response.getECoordinate());
  }

  @Test
  void shouldConvertN() {
    final var response = converter.convert(type);

    assertEquals(type.getNCoordinate(), response.getNCoordinate());
  }

}