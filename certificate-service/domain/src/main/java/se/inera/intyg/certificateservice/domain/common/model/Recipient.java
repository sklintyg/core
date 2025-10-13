package se.inera.intyg.certificateservice.domain.common.model;

public record Recipient(RecipientId id, String name, String logicalAddress, String logo,
                        String generalName) {

  public Recipient(RecipientId id, String name, String logicalAddress) {
    this(id, name, logicalAddress, null, null);
  }

  public boolean canSendElectronically() {
    return this.logicalAddress != null && !this.logicalAddress.isBlank();
  }
}
