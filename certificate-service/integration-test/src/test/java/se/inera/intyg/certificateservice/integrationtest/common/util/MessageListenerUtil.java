package se.inera.intyg.certificateservice.integrationtest.common.util;

import jakarta.jms.Message;
import java.time.Duration;
import java.util.Objects;
import se.inera.intyg.certificateservice.integrationtest.common.setup.MessageListener;

public final class MessageListenerUtil {

  private static volatile MessageListener messageListener;

  private MessageListenerUtil() {
  }

  public static void setMessageListener(MessageListener messageListener) {
    MessageListenerUtil.messageListener = messageListener;
  }

  private static MessageListener getMessageListener() {
    return Objects.requireNonNull(messageListener, "MessageListener not initialized!");
  }

  public static void clear() {
    getMessageListener().clear();
  }

  public static Message awaitMessage(Duration timeout) {
    return getMessageListener().awaitMessage(timeout);
  }
}