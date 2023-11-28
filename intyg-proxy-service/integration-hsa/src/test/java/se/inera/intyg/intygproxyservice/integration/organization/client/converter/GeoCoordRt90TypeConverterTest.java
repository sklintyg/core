package se.inera.intyg.intygproxyservice.integration.organization.client.converter;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.GeoCoordRt90;
import se.riv.infrastructure.directory.organization.getunitresponder.v4.GeoCoordRt90Type;

class GeoCoordRt90TypeConverterTest {

  private final GeoCoordRt90TypeConverter geoCoordRt90TypeConverter = new GeoCoordRt90TypeConverter();

  private static GeoCoordRt90Type type;

  @BeforeEach
  void setup() {
    type = new GeoCoordRt90Type();
    type.setXCoordinate("X");
    type.setYCoordinate("Y");
  }

  @Test
  void shouldConvertNull() {
    final var response = geoCoordRt90TypeConverter.convert(null);

    assertEquals(GeoCoordRt90.builder().build(), response);
  }

  @Test
  void shouldConvertX() {
    final var response = geoCoordRt90TypeConverter.convert(type);

    assertEquals(type.getXCoordinate(), response.getXCoordinate());
  }

  @Test
  void shouldConvertY() {
    final var response = geoCoordRt90TypeConverter.convert(type);

    assertEquals(type.getYCoordinate(), response.getYCoordinate());
  }

}