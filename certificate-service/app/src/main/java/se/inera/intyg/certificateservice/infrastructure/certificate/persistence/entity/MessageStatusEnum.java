package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity;

public enum MessageStatusEnum {
  DRAFT(1), SENT(2), HANDLED(3);

  private final int key;

  MessageStatusEnum(int key) {
    this.key = key;
  }

  public int getKey() {
    return key;
  }
}
