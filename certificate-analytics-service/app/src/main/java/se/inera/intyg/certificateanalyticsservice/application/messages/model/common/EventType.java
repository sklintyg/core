package se.inera.intyg.certificateanalyticsservice.application.messages.model.common;

import lombok.Getter;

@Getter
public enum EventType {
  CREATED(1),
  SENT(2),
  SIGNED(3);

  private final int key;

  EventType(int key) {
    this.key = key;
  }
}

