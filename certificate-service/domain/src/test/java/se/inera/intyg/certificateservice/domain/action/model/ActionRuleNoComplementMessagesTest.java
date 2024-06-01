package se.inera.intyg.certificateservice.domain.action.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.message.model.Answer;
import se.inera.intyg.certificateservice.domain.message.model.Message;
import se.inera.intyg.certificateservice.domain.message.model.MessageStatus;
import se.inera.intyg.certificateservice.domain.message.model.MessageType;

class ActionRuleNoComplementMessagesTest {

  private static final ActionRuleNoComplementMessages actionRule = new ActionRuleNoComplementMessages();

  @Test
  void shouldReturnFalseIfMessageIsComplementAndNotAnsweredOrHandled() {
    final var result = actionRule.evaluate(
        Optional.of(
            Certificate.builder()
                .messages(List.of(
                    Message.builder()
                        .type(MessageType.COMPLEMENT)
                        .status(MessageStatus.SENT)
                        .build()
                ))
                .build()
        ),
        Optional.empty()
    );

    assertFalse(result);
  }

  @Test
  void shouldReturnTrueIfNoComplement() {
    final var result = actionRule.evaluate(
        Optional.of(
            Certificate.builder()
                .messages(List.of(
                    Message.builder()
                        .type(MessageType.REMINDER)
                        .status(MessageStatus.SENT)
                        .build()
                )).build()
        ),
        Optional.empty()
    );

    assertTrue(result);
  }

  @Test
  void shouldReturnTrueIfNoMessages() {
    final var result = actionRule.evaluate(
        Optional.of(
            Certificate.builder()
                .messages(Collections.emptyList())
                .build()
        ),
        Optional.empty()
    );

    assertTrue(result);
  }

  @Test
  void shouldReturnTrueIfNullMessages() {
    final var result = actionRule.evaluate(
        Optional.of(
            Certificate.builder()
                .build()
        ),
        Optional.empty()
    );

    assertTrue(result);
  }

  @Test
  void shouldReturnTrueIfHandledComplement() {
    final var result = actionRule.evaluate(
        Optional.of(
            Certificate.builder()
                .messages(List.of(
                    Message.builder()
                        .type(MessageType.COMPLEMENT)
                        .status(MessageStatus.HANDLED)
                        .build()
                )).build()
        ),
        Optional.empty()
    );

    assertTrue(result);
  }

  @Test
  void shouldReturnTrueIfAnsweredComplement() {
    final var result = actionRule.evaluate(
        Optional.of(
            Certificate.builder()
                .messages(List.of(
                    Message.builder()
                        .type(MessageType.COMPLEMENT)
                        .status(MessageStatus.SENT)
                        .answer(Answer.builder().build())
                        .build()
                )).build()
        ),
        Optional.empty()
    );

    assertTrue(result);
  }
}