package se.inera.intyg.intygproxyservice.integration.common.support.adapter.xml;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.TimeZone;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LocalDateTimeXmlAdapterTest {

  private static final String DATE_TIME_STRING_WITH_TIME_ZONE = "2012-11-13T11:55:50-01:00";
  private static final String DATE_TIME_STRING_WITH_MILLIS_AND_TIME_ZONE = "2012-11-13T11:55:50.12-01:00";
  private static final String DATE_TIME_STRING_WITH_Z = "2012-11-13T12:55:50Z";
  private static final String DATE_TIME_STRING_WITH_MILLIS_AND_Z = "2012-11-13T12:55:50.12Z";
  private static final String DATE_TIME_STRING_WITH_MILLIS = "2012-11-13T13:55:50.120";
  private static final String DATE_TIME_STRING = "2012-11-13T13:55:50";
  private static final String DATE_STRING = "2012-11-13";
  private static final String DATE_STRING_WITH_TIME_ZONE = "2012-11-13-01:00";
  private static final String DATE_STRING_WITH_Z = "2012-11-13Z";
  private static final ZoneId TIME_ZONE = ZoneId.of("Europe/Stockholm");
  private static final LocalDateTime LOCAL_DATE_TIME = ZonedDateTime.of(2012, 11, 13, 13, 55, 50, 0,
      TIME_ZONE).toLocalDateTime();
  private static final LocalDateTime LOCAL_DATE_TIME_WITH_MILLIS = LOCAL_DATE_TIME.plusNanos(
      120_000_000);
  private static final LocalDateTime LOCAL_DATE_TIME_START_OF_DAY_STRING = LOCAL_DATE_TIME.withHour(
      0).withMinute(0).withSecond(0).withNano(0);
  private static final String ISO_DATE_TIME_STRING = "2012-11-13T13:55:50";
  private static TimeZone systemTimeZone;
  private LocalDateTimeXmlAdapter localDateTimeXmlAdapter;


  @BeforeEach
  void setGMTTimeZone() {
    systemTimeZone = TimeZone.getDefault();
    TimeZone GMT = TimeZone.getTimeZone("GMT");
    TimeZone.setDefault(GMT);
    localDateTimeXmlAdapter = new LocalDateTimeXmlAdapter();
  }

  @AfterEach
  void restoreTimeZone() {
    TimeZone.setDefault(systemTimeZone);
  }

  @Test
  void testunmarshal() {
    LocalDateTime date = localDateTimeXmlAdapter.unmarshal(DATE_TIME_STRING);
    assertEquals(LOCAL_DATE_TIME, date);
  }

  @Test
  void testunmarshalWithMillis() {
    LocalDateTime date = localDateTimeXmlAdapter.unmarshal(DATE_TIME_STRING_WITH_MILLIS);
    assertEquals(LOCAL_DATE_TIME_WITH_MILLIS, date);
  }

  @Test
  void testunmarshalWithTimeZone() {
    LocalDateTime date = localDateTimeXmlAdapter.unmarshal(DATE_TIME_STRING_WITH_TIME_ZONE);
    assertEquals(LOCAL_DATE_TIME, date);
  }

  @Test
  void testunmarshalWithMillisAndTimeZone() {
    LocalDateTime date = localDateTimeXmlAdapter.unmarshal(
        DATE_TIME_STRING_WITH_MILLIS_AND_TIME_ZONE);
    assertEquals(LOCAL_DATE_TIME_WITH_MILLIS, date);
  }

  @Test
  void testunmarshalWithZ() {
    LocalDateTime date = localDateTimeXmlAdapter.unmarshal(DATE_TIME_STRING_WITH_Z);
    assertEquals(LOCAL_DATE_TIME, date);
  }

  @Test
  void testunmarshalWithMillisAndZ() {
    LocalDateTime date = localDateTimeXmlAdapter.unmarshal(DATE_TIME_STRING_WITH_MILLIS_AND_Z);
    assertEquals(LOCAL_DATE_TIME_WITH_MILLIS, date);
  }

  @Test
  void testunmarshalWithOnlyDate() {
    LocalDateTime date = localDateTimeXmlAdapter.unmarshal(DATE_STRING);
    assertEquals(LOCAL_DATE_TIME_START_OF_DAY_STRING, date);
  }

  @Test
  void testunmarshalWithOnlyDateAndTimeZone() {
    LocalDateTime date = localDateTimeXmlAdapter.unmarshal(DATE_STRING_WITH_TIME_ZONE);
    assertEquals(LOCAL_DATE_TIME_START_OF_DAY_STRING.plusHours(2), date);
  }

  @Test
  void testunmarshalWithOnlyDateAndZ() {
    LocalDateTime date = localDateTimeXmlAdapter.unmarshal(DATE_STRING_WITH_Z);
    assertEquals(LOCAL_DATE_TIME_START_OF_DAY_STRING.plusHours(1), date);
  }

  @Test
  void testmarshal() {
    String dateString = localDateTimeXmlAdapter.marshal(LOCAL_DATE_TIME);
    assertEquals(DATE_TIME_STRING, dateString);
  }

  @Test
  void testmarshalWithFractions() {
    LocalDateTime time = LocalDateTime.of(2015, 1, 1, 10, 10, 10, 123);
    String dateString = localDateTimeXmlAdapter.marshal(time);
    assertEquals("2015-01-01T10:10:10", dateString);
  }

  @Test
  void testPrintIsoDateTime() {
    String dateString = localDateTimeXmlAdapter.marshal(LOCAL_DATE_TIME);
    assertEquals(ISO_DATE_TIME_STRING, dateString);
  }
}