package se.inera.intyg.certificateservice.domain.event.model;

public enum CertificateEventType {

  READ("read-certificate", Constants.ACTION_TYPE_ACCESSED),
  CREATED("created-certificate", Constants.ACTION_TYPE_CREATION),
  UPDATED("updated-certificate", Constants.ACTION_TYPE_CHANGE),
  DELETED("deleted-certificate", Constants.ACTION_TYPE_DELETION),
  VALIDATED("validated-certificate", Constants.ACTION_TYPE_ACCESSED),
  SIGNED("sign-certificate", Constants.ACTION_TYPE_CHANGE),
  SENT("sent-certificate", Constants.ACTION_TYPE_CHANGE),
  PRINT("print-certificate", Constants.ACTION_TYPE_ACCESSED),
  REVOKED("revoked-certificate", Constants.ACTION_TYPE_CHANGE);

  private final String action;
  private final String actionType;
  private final String messageType;

  CertificateEventType(String action, String actionType, String messageType) {
    this.action = action;
    this.actionType = actionType;
    this.messageType = messageType;
  }

  public String action() {
    return action;
  }

  public String actionType() {
    return actionType;
  }

  public String messageType() {
    return messageType;
  }

  public boolean hasMessageType() {
    return messageType != null;
  }

  private static class Constants {

    private static final String ACTION_TYPE_CHANGE = "change";
    private static final String ACTION_TYPE_DELETION = "deletion";
    private static final String ACTION_TYPE_CREATION = "creation";
    private static final String ACTION_TYPE_ACCESSED = "accessed";
  }
}
