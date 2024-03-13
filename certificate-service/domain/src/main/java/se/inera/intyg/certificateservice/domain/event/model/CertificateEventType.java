package se.inera.intyg.certificateservice.domain.event.model;

public enum CertificateEventType {
  READ("read-certificate", "accessed"),
  CREATED("created-certificate", "creation"),
  UPDATED("updated-certificate", "change"),
  DELETED("deleted-certificate", "deletion"),
  VALIDATED("validated-certificate", "accessed"),
  SIGNED("sign-certificate", "change");

  private final String action;
  private final String actionType;

  CertificateEventType(String action, String actionType) {
    this.action = action;
    this.actionType = actionType;
  }

  public String action() {
    return action;
  }

  public String actionType() {
    return actionType;
  }
}
