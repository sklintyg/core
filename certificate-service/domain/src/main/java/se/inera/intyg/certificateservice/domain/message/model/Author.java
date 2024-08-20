package se.inera.intyg.certificateservice.domain.message.model;

public record Author(String author) {

  private static final String FK = "FK";

  public boolean fromFK() {
    return this.author.equals(FK);
  }

  public String name() {
    if ("FK".equals(author)) {
      return "Försäkringskassan";
    }
    return author;
  }
}
