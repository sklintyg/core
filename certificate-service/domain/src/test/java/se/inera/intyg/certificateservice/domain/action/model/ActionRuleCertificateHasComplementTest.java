package se.inera.intyg.certificateservice.domain.action.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.message.model.Complement;
import se.inera.intyg.certificateservice.domain.message.model.Message;

class ActionRuleCertificateHasComplementTest {

  private static final ActionRuleCertificateHasComplement actionRule = new ActionRuleCertificateHasComplement();

  @Test
  void shouldReturnTrueIfOneComplement() {
    final var result = actionRule.evaluate(
        Optional.of(
            Certificate.builder()
                .messages(List.of(
                    Message.builder()
                        .complements(List.of(Complement.builder().build()))
                        .build()
                ))
                .build()
        ),
        Optional.empty()
    );

    assertTrue(result);
  }

  @Test
  void shouldReturnTrueIfSeveralComplements() {
    final var result = actionRule.evaluate(
        Optional.of(
            Certificate.builder()
                .messages(List.of(
                    Message.builder()
                        .complements(List.of(Complement.builder().build()))
                        .build(),
                    Message.builder()
                        .build()
                )).build()
        ),
        Optional.empty()
    );

    assertTrue(result);
  }

  @Test
  void shouldReturnFalseIfNoMessages() {
    final var result = actionRule.evaluate(
        Optional.of(
            Certificate.builder()
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
            Certificate.builder()
                .build()
        ),
        Optional.empty()
    );

    assertFalse(result);
  }

  @Test
  void shouldReturnFalseIfNoComplements() {
    final var result = actionRule.evaluate(
        Optional.of(
            Certificate.builder()
                .messages(List.of(
                    Message.builder()
                        .complements(Collections.emptyList())
                        .build(),
                    Message.builder()
                        .complements(Collections.emptyList())
                        .build()
                )).build()
        ),
        Optional.empty()
    );

    assertFalse(result);
  }

  @Test
  void shouldReturnFalseIfNullComplements() {
    final var result = actionRule.evaluate(
        Optional.of(
            Certificate.builder()
                .messages(List.of(
                    Message.builder()
                        .build(),
                    Message.builder()
                        .build()
                )).build()
        ),
        Optional.empty()
    );

    assertFalse(result);
  }
}