package se.inera.intyg.certificateservice.domain.event.model;

public enum MessageEventType {


  ANSWER_COMPLEMENT("answer-complement-message", Constants.ACTION_TYPE_CREATION,
      "message-sent"),
  SEND_QUESTION("send-question-message", Constants.ACTION_TYPE_CREATION,
      "message-sent"),
  SEND_ANSWER("send-answer-message", Constants.ACTION_TYPE_CREATION,
      "message-sent");

  private final String action;
  private final String actionType;
  private final String messageType;

  MessageEventType(String action, String actionType, String messageType) {
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

    private static final String ACTION_TYPE_CREATION = "creation";
  }
}
