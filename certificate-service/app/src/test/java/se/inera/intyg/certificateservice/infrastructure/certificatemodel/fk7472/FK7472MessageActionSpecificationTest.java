package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7472;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.message.model.MessageActionType;

class FK7472MessageActionSpecificationTest {

  @Test
  void shallIncludeMessageActionHandleComplement() {
    final var expectedType = MessageActionType.HANDLE_COMPLEMENT;

    final var actions = FK7472MessageActionSpecification.create();

    assertTrue(actions.stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.messageActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeMessageActionCannotComplement() {
    final var expectedType = MessageActionType.CANNOT_COMPLEMENT;

    final var actions = FK7472MessageActionSpecification.create();

    assertTrue(actions.stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.messageActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeMessageActionComplement() {
    final var expectedType = MessageActionType.COMPLEMENT;

    final var actions = FK7472MessageActionSpecification.create();

    assertTrue(actions.stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.messageActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeMessageActionForward() {
    final var expectedType = MessageActionType.FORWARD;

    final var actions = FK7472MessageActionSpecification.create();

    assertTrue(actions.stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.messageActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }
}