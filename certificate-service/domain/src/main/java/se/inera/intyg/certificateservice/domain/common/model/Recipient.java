package se.inera.intyg.certificateservice.domain.common.model;

public record Recipient(RecipientId id, String name, String logicalAddress, String logo) {

  public Recipient(RecipientId id, String name, String logicalAddress) {
    this(id, name, logicalAddress, null);
  }
}
