package se.inera.intyg.certificateservice.domain.testdata;

import java.time.LocalDate;

public class TestDataElementDataConstants {

  public static final String DATE_ELEMENT_ID = "dateElementId";
  public static final String DATE_ELEMENT_NAME = "dateElementName";
  public static final String DATE_ELEMENT_VALUE_ID = "dateElementValueId";
  public static final LocalDate DATE_ELEMENT_VALUE_DATE = LocalDate.now();
  public static final LocalDate DATE_ELEMENT_CONFIGURATION_MIN = LocalDate.now();
  public static final LocalDate DATE_ELEMENT_CONFIGURATION_MAX = LocalDate.now().plusMonths(1);
  public static final String DATE_ELEMENT_RULE_EXPRESSION = "$%s".formatted(DATE_ELEMENT_VALUE_ID);

}
