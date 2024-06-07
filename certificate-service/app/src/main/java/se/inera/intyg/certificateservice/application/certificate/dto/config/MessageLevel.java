package se.inera.intyg.certificateservice.application.certificate.dto.config;

import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMessageLevel;

public enum MessageLevel {
  INFO, OBSERVE, ERROR;

  public static MessageLevel toMessageLevel(ElementMessageLevel messageLevel) {
    return switch (messageLevel) {
      case INFO -> INFO;
      case ERROR -> ERROR;
      case OBSERVE -> OBSERVE;
    };
  }
}
