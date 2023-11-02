package se.inera.intyg.intygproxyservice.integration.common.support.adapter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.TimeZone;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LocalDateAdapterTest {

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
  private static final LocalDate LOCAL_DATE = LocalDate.of(2012, 11, 13).atStartOfDay(TIME_ZONE)
      .toLocalDate();
  private static final LocalDateTime LOCAL_DATE_TIME = ZonedDateTime.of(2012, 11, 13, 13, 55, 50, 0,
      TIME_ZONE).toLocalDateTime();
  private static final LocalDateTime LOCAL_DATE_TIME_WITH_MILLIS = LOCAL_DATE_TIME.plusNanos(
      120_000_000);
  private static final LocalDateTime LOCAL_DATE_TIME_START_OF_DAY_STRING = LOCAL_DATE_TIME.withHour(
      0).withMinute(0).withSecond(0).withNano(0);
  private static final LocalDate ISO_DATE = LocalDate.of(2012, 11, 13);
  private static final LocalDateTime ISO_DATE_TIME = LocalDateTime.of(2012, 11, 13, 13, 55, 50, 0);
  private static final String ISO_DATE_TIME_STRING = "2012-11-13T13:55:50";
  private static final String ISO_DATE_STRING = "2012-11-13";
  private static TimeZone systemTimeZone;

  @BeforeEach
  void setGMTTimeZone() {
    systemTimeZone = TimeZone.getDefault();
    TimeZone GMT = TimeZone.getTimeZone("GMT");
    TimeZone.setDefault(GMT);
  }

  @AfterEach
  void restoreTimeZone() {
    TimeZone.setDefault(systemTimeZone);
  }

  @Test
  void testParseDateTime() {
    LocalDateTime date = LocalDateAdapter.parseDateTime(DATE_TIME_STRING);
    assertEquals(LOCAL_DATE_TIME, date);
  }

  @Test
  void testParseDateTimeWithMillis() {
    LocalDateTime date = LocalDateAdapter.parseDateTime(DATE_TIME_STRING_WITH_MILLIS);
    assertEquals(LOCAL_DATE_TIME_WITH_MILLIS, date);
  }

  @Test
  void testParseDateTimeWithTimeZone() {
    LocalDateTime date = LocalDateAdapter.parseDateTime(DATE_TIME_STRING_WITH_TIME_ZONE);
    assertEquals(LOCAL_DATE_TIME, date);
  }

  @Test
  void testParseDateTimeWithMillisAndTimeZone() {
    LocalDateTime date = LocalDateAdapter.parseDateTime(DATE_TIME_STRING_WITH_MILLIS_AND_TIME_ZONE);
    assertEquals(LOCAL_DATE_TIME_WITH_MILLIS, date);
  }

  @Test
  void testParseDateTimeWithZ() {
    LocalDateTime date = LocalDateAdapter.parseDateTime(DATE_TIME_STRING_WITH_Z);
    assertEquals(LOCAL_DATE_TIME, date);
  }

  @Test
  void testParseDateTimeWithMillisAndZ() {
    LocalDateTime date = LocalDateAdapter.parseDateTime(DATE_TIME_STRING_WITH_MILLIS_AND_Z);
    assertEquals(LOCAL_DATE_TIME_WITH_MILLIS, date);
  }

  @Test
  void testParseDateTimeWithOnlyDate() {
    LocalDateTime date = LocalDateAdapter.parseDateTime(DATE_STRING);
    assertEquals(LOCAL_DATE_TIME_START_OF_DAY_STRING, date);
  }

  @Test
  void testParseDateTimeWithOnlyDateAndTimeZone() {
    LocalDateTime date = LocalDateAdapter.parseDateTime(DATE_STRING_WITH_TIME_ZONE);
    assertEquals(LOCAL_DATE_TIME_START_OF_DAY_STRING.plusHours(2), date);
  }

  @Test
  void testParseDateTimeWithOnlyDateAndZ() {
    LocalDateTime date = LocalDateAdapter.parseDateTime(DATE_STRING_WITH_Z);
    assertEquals(LOCAL_DATE_TIME_START_OF_DAY_STRING.plusHours(1), date);
  }

  @Test
  void testParseDate() {
    LocalDate date = LocalDateAdapter.parseDate(DATE_STRING);
    assertEquals(LOCAL_DATE, date);
  }

  @Test
  void testParseDateWithTimeZone() {
    LocalDate date = LocalDateAdapter.parseDate(DATE_STRING_WITH_TIME_ZONE);
    assertEquals(LOCAL_DATE, date);
  }

  @Test
  void testParseDateWithZ() {
    LocalDate date = LocalDateAdapter.parseDate(DATE_STRING_WITH_Z);
    assertEquals(LOCAL_DATE, date);
  }

  @Test
  void testParseDateWithDateTime() {
    LocalDate date = LocalDateAdapter.parseDate(DATE_TIME_STRING);
    assertEquals(LOCAL_DATE, date);
  }

  @Test
  void testParseDateWithDateTimeWithMillis() {
    LocalDate date = LocalDateAdapter.parseDate(DATE_TIME_STRING_WITH_MILLIS);
    assertEquals(LOCAL_DATE, date);
  }

  @Test
  void testParseDateWithDateTimeWithZ() {
    LocalDate date = LocalDateAdapter.parseDate(DATE_TIME_STRING_WITH_Z);
    assertEquals(LOCAL_DATE, date);
  }

  @Test
  void testParseDateWithDateTimeWithMillisAndZ() {
    LocalDate date = LocalDateAdapter.parseDate(DATE_TIME_STRING_WITH_MILLIS_AND_Z);
    assertEquals(LOCAL_DATE, date);
  }

  @Test
  void testParseDateWithDateTimeWithTimeZone() {
    LocalDate date = LocalDateAdapter.parseDate(DATE_TIME_STRING_WITH_TIME_ZONE);
    assertEquals(LOCAL_DATE, date);
  }

  @Test
  void testParseDateWithDateTimeWithMillisAndTimeZone() {
    LocalDate date = LocalDateAdapter.parseDate(DATE_TIME_STRING_WITH_MILLIS_AND_TIME_ZONE);
    assertEquals(LOCAL_DATE, date);
  }

  @Test
  void testPrintDate() {
    String dateString = LocalDateAdapter.printDate(LOCAL_DATE);
    assertEquals(DATE_STRING, dateString);
  }

  @Test
  void testPrintDateTime() {
    String dateString = LocalDateAdapter.printDateTime(LOCAL_DATE_TIME);
    assertEquals(DATE_TIME_STRING, dateString);
  }

  @Test
  void testPrintDateTimeWithFractions() {
    LocalDateTime time = LocalDateTime.of(2015, 1, 1, 10, 10, 10, 123);
    String dateString = LocalDateAdapter.printDateTime(time);
    assertEquals("2015-01-01T10:10:10", dateString);
  }


  @Test
  void testParseIsoDate() {
    LocalDate date = LocalDateAdapter.parseIsoDate(ISO_DATE_STRING);
    assertEquals(ISO_DATE, date);
  }

  @Test
  void testParseIsoDateTime() {
    LocalDateTime date = LocalDateAdapter.parseIsoDateTime(ISO_DATE_TIME_STRING);
    assertEquals(ISO_DATE_TIME, date);
  }

  @Test
  void testPrintIsoDate() {
    String dateString = LocalDateAdapter.printDate(LOCAL_DATE);
    assertEquals(ISO_DATE_STRING, dateString);
  }

  @Test
  void testPrintIsoDateTime() {
    String dateString = LocalDateAdapter.printDateTime(LOCAL_DATE_TIME);
    assertEquals(ISO_DATE_TIME_STRING, dateString);
  }

}
