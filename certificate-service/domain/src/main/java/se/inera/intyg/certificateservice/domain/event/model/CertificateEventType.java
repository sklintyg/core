package se.inera.intyg.certificateservice.domain.event.model;

public enum CertificateEventType {

  READ("read-certificate", Constants.ACTION_TYPE_ACCESSED, null),
  CREATED("created-certificate", Constants.ACTION_TYPE_CREATION, null),
  UPDATED("updated-certificate", Constants.ACTION_TYPE_CHANGE, null),
  DELETED("deleted-certificate", Constants.ACTION_TYPE_DELETION, null),
  VALIDATED("validated-certificate", Constants.ACTION_TYPE_ACCESSED, null),
  SIGNED("sign-certificate", Constants.ACTION_TYPE_CHANGE, "certificate-signed"),
  PRINT("print-certificate", Constants.ACTION_TYPE_ACCESSED, null),
  SENT("sent-certificate", Constants.ACTION_TYPE_CHANGE, "certificate-sent"),
  REPLACE("replace-certificate", Constants.ACTION_TYPE_CREATION, null),
  RENEW("renew-certificate", Constants.ACTION_TYPE_CREATION, null),
  COMPLEMENT("complement-certificate", Constants.ACTION_TYPE_CREATION, null),
  ANSWER_COMPLEMENT("answer-complement-message", Constants.ACTION_TYPE_CREATION,
      "message-sent"),
  REVOKED("revoked-certificate", Constants.ACTION_TYPE_CHANGE, "certificate-revoked"),
  READY_FOR_SIGN("ready-for-sign-certificate", Constants.ACTION_TYPE_CHANGE, null),
  CREATE_DRAFT_FROM_CERTIFICATE("create-draft-from-certificate", Constants.ACTION_TYPE_CREATION,
      null),
  UPDATE_WITH_CERTIFICATE_CANDIDATE("update-with-certificate-candidate",
      Constants.ACTION_TYPE_CHANGE, null);

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