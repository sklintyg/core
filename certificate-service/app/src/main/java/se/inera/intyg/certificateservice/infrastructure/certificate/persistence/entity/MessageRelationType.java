package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity;

import lombok.Getter;

@Getter
public enum MessageRelationType {
  ANSWER(1), REMINDER(2);

  private final int key;

  MessageRelationType(int key) {
    this.key = key;
  }
}
