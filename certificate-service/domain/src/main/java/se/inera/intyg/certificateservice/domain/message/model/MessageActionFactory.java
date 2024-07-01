package se.inera.intyg.certificateservice.domain.message.model;

public class MessageActionFactory {

  private MessageActionFactory() {
    throw new IllegalStateException("Utility class");
  }

  public static MessageAction complement() {
    return MessageAction.builder()
        .type(MessageActionType.COMPLEMENT)
        .name("Komplettera")
        .description("Öppnar ett nytt intygsutkast.")
        .enabled(true)
        .build();
  }

  public static MessageAction cannotComplement() {
    return MessageAction.builder()
        .type(MessageActionType.CANNOT_COMPLEMENT)
        .name("Kan ej komplettera")
        .description("Öppnar en dialogruta med mer information.")
        .enabled(true)
        .build();
  }

  public static MessageAction handleComplement() {
    return MessageAction.builder()
        .type(MessageActionType.HANDLE_COMPLEMENT)
        .name("Hantera")
        .description("Hantera kompletteringsbegäran.")
        .enabled(true)
        .build();
  }

  public static MessageAction forward() {
    return MessageAction.builder()
        .type(MessageActionType.FORWARD)
        .name("Vidarebefordra")
        .description("Skapar ett e-postmeddelande med länk till intyget.")
        .enabled(true)
        .build();
  }

  public static MessageAction answer() {
    return MessageAction.builder()
        .type(MessageActionType.ANSWER)
        .name("Skapa")
        .description("Svara på fråga")
        .enabled(true)
        .build();
  }

  public static MessageAction handleMessage() {
    return MessageAction.builder()
        .type(MessageActionType.HANDLE_MESSAGE)
        .name("Hantera")
        .description("Hantera fråga")
        .enabled(true)
        .build();
  }
}
