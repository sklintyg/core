package se.inera.intyg.intygproxyservice.integration.common.support.adapter.xml;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.TimeZone;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LocalDateXmlAdapterTest {

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
  private static final String ISO_DATE_STRING = "2012-11-13";
  private static TimeZone systemTimeZone;

  private LocalDateXmlAdapter localDateXmlAdapter;

  @BeforeEach
  public void setGMTTimeZone() {
    systemTimeZone = TimeZone.getDefault();
    TimeZone GMT = TimeZone.getTimeZone("GMT");
    TimeZone.setDefault(GMT);
    localDateXmlAdapter = new LocalDateXmlAdapter();
  }

  @AfterEach
  public void restoreTimeZone() {
    TimeZone.setDefault(systemTimeZone);
  }

  @Test
  void testunmarshal() {
    LocalDate date = localDateXmlAdapter.unmarshal(DATE_STRING);
    assertEquals(LOCAL_DATE, date);
  }

  @Test
  void testunmarshalWithTimeZone() {
    LocalDate date = localDateXmlAdapter.unmarshal(DATE_STRING_WITH_TIME_ZONE);
    assertEquals(LOCAL_DATE, date);
  }

  @Test
  void testunmarshalWithZ() {
    LocalDate date = localDateXmlAdapter.unmarshal(DATE_STRING_WITH_Z);
    assertEquals(LOCAL_DATE, date);
  }

  @Test
  void testunmarshalWithDateTime() {
    LocalDate date = localDateXmlAdapter.unmarshal(DATE_TIME_STRING);
    assertEquals(LOCAL_DATE, date);
  }

  @Test
  void testunmarshalWithDateTimeWithMillis() {
    LocalDate date = localDateXmlAdapter.unmarshal(DATE_TIME_STRING_WITH_MILLIS);
    assertEquals(LOCAL_DATE, date);
  }

  @Test
  void testunmarshalWithDateTimeWithZ() {
    LocalDate date = localDateXmlAdapter.unmarshal(DATE_TIME_STRING_WITH_Z);
    assertEquals(LOCAL_DATE, date);
  }

  @Test
  void testunmarshalWithDateTimeWithMillisAndZ() {
    LocalDate date = localDateXmlAdapter.unmarshal(DATE_TIME_STRING_WITH_MILLIS_AND_Z);
    assertEquals(LOCAL_DATE, date);
  }

  @Test
  void testunmarshalWithDateTimeWithTimeZone() {
    LocalDate date = localDateXmlAdapter.unmarshal(DATE_TIME_STRING_WITH_TIME_ZONE);
    assertEquals(LOCAL_DATE, date);
  }

  @Test
  void testunmarshalWithDateTimeWithMillisAndTimeZone() {
    LocalDate date = localDateXmlAdapter.unmarshal(DATE_TIME_STRING_WITH_MILLIS_AND_TIME_ZONE);
    assertEquals(LOCAL_DATE, date);
  }

  @Test
  void testmarshal() {
    String dateString = localDateXmlAdapter.marshal(LOCAL_DATE);
    assertEquals(DATE_STRING, dateString);
  }


  @Test
  void testPrintIsoDate() {
    String dateString = localDateXmlAdapter.marshal(LOCAL_DATE);
    assertEquals(ISO_DATE_STRING, dateString);
  }
}