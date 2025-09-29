package se.inera.intyg.certificateanalyticsservice.application.messages.model.common;

import lombok.Getter;

@Getter
public enum OriginType {
  NORMAL(1),
  INTEGRATION(2);

  private final int key;

  OriginType(int key) {
    this.key = key;
  }
}

