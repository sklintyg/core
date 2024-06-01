package se.inera.intyg.certificateservice.domain.message.model;

public record Author(String author) {

  public String name() {
    if ("FK".equals(author)) {
      return "Försäkringskassan";
    }
    return author;
  }
}
