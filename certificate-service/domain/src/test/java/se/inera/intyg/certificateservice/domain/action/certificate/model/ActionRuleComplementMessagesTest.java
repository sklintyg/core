package se.inera.intyg.certificateservice.domain.action.certificate.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalCertificate;
import se.inera.intyg.certificateservice.domain.message.model.Answer;
import se.inera.intyg.certificateservice.domain.message.model.Message;
import se.inera.intyg.certificateservice.domain.message.model.MessageStatus;
import se.inera.intyg.certificateservice.domain.message.model.MessageType;

class ActionRuleComplementMessagesTest {

  private static final ActionRuleComplementMessages actionRule = new ActionRuleComplementMessages();

  @Test
  void shouldReturnTrueIfMessageIsComplementAndNotAnsweredOrHandled() {
    final var result = actionRule.evaluate(
        Optional.of(
            MedicalCertificate.builder()
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

    assertTrue(result);
  }

  @Test
  void shouldReturnFalseIfNoComplement() {
    final var result = actionRule.evaluate(
        Optional.of(
            MedicalCertificate.builder()
                .messages(List.of(
                    Message.builder()
                        .type(MessageType.REMINDER)
                        .status(MessageStatus.SENT)
                        .build()
                )).build()
        ),
        Optional.empty()
    );

    assertFalse(result);
  }

  @Test
  void shouldReturnFalseIfNoMessages() {
    final var result = actionRule.evaluate(
        Optional.of(
            MedicalCertificate.builder()
                .messages(Collections.emptyList())
                .build()
        ),
        Optional.empty()
    );

    assertFalse(result);
  }

  @Test
  void shouldReturnFalseIfNullMessages() {
    final var result = actionRule.evaluate(
        Optional.of(
            MedicalCertificate.builder()
                .build()
        ),
        Optional.empty()
    );

    assertFalse(result);
  }

  @Test
  void shouldReturnFalseIfHandledComplement() {
    final var result = actionRule.evaluate(
        Optional.of(
            MedicalCertificate.builder()
                .messages(List.of(
                    Message.builder()
                        .type(MessageType.COMPLEMENT)
                        .status(MessageStatus.HANDLED)
                        .build()
                )).build()
        ),
        Optional.empty()
    );

    assertFalse(result);
  }

  @Test
  void shouldReturnFalseIfAnsweredComplement() {
    final var result = actionRule.evaluate(
        Optional.of(
            MedicalCertificate.builder()
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

    assertFalse(result);
  }
}