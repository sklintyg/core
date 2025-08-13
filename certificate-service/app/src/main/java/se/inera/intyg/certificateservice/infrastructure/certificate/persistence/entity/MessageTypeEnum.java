package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity;

import lombok.Getter;

@Getter
public enum MessageTypeEnum {
  COMPLEMENT(1), CONTACT(2), OTHER(3), REMINDER(4), ANSWER(5), MISSING(6), COORDINATION(7);

  private final int key;

  MessageTypeEnum(int key) {
    this.key = key;
  }
}