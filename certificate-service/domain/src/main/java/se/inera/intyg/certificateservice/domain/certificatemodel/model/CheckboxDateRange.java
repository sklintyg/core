package se.inera.intyg.certificateservice.domain.certificatemodel.model;

public record CheckboxDateRange(FieldId id, String label) {

  public static String TO_SUFFIX = ".to";
  public static String FROM_SUFFIX = ".from";
  public static String RANGE_SUFFIX = ".range";
}
