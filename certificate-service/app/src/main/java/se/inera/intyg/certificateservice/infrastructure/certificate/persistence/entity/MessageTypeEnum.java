package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity;

import lombok.Getter;

@Getter
public enum MessageTypeEnum {
  COMPLEMENT(1), QUESTION(2), ANSWER(3), REMINDER(4);

  private final int key;

  MessageTypeEnum(int key) {
    this.key = key;
  }
}
