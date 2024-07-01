package se.inera.intyg.certificateservice.application.unit.dto;

public enum QuestionSenderTypeDTO {
  FK("Försäkringskassan"),
  WC("Vårdenheten"),
  SHOW_ALL("Visa alla");

  private final String text;

  QuestionSenderTypeDTO(String text) {
    this.text = text;
  }

  public String getName() {
    return text;
  }
}
