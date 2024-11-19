package se.inera.intyg.certificateservice.application.certificate.dto.config;

public enum MessageLevel {
  INFO, OBSERVE, ERROR;

  public static MessageLevel toMessageLevel(
      se.inera.intyg.certificateservice.domain.certificatemodel.model.MessageLevel messageLevel) {
    return switch (messageLevel) {
      case INFO -> INFO;
      case ERROR -> ERROR;
      case OBSERVE -> OBSERVE;
    };
  }
}
