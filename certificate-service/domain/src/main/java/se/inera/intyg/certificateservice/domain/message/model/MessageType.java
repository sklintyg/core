package se.inera.intyg.certificateservice.domain.message.model;

public enum MessageType {
  COMPLEMENT("KOMPLT", "Komplettering"),
  REMINDER("PAMINN", "Påminnelse"),
  CONTACT("KONTKT", "Kontakt"),
  OTHER("OVRIGT", "Övrigt"),
  ANSWER("SVAR", "Svar"),
  COORDINATION("AVSTMN", "Avstämningsmöte"),
  MISSING("SAKNAS", "Välj typ av fråga");

  public static final String OID = "ffa59d8f-8d7e-46ae-ac9e-31804e8e8499";

  private final String code;
  private final String displayName;

  MessageType(String code, String displayName) {
    this.code = code;
    this.displayName = displayName;
  }

  public String code() {
    return code;
  }

  public String displayName() {
    return displayName;
  }
}