package se.inera.intyg.certificateservice.domain.common.model;

public record Recipient(RecipientId id, String name) {

  public String getLogicalAddress() {
    if (id.id().equals("FKASSA")) {
      return "2021005521";
    } else {
      return id.id();
    }
  }

}
