package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.message.model.MessageActionType;

class FK3226MessageActionSpecificationTest {

  @Test
  void shallIncludeMessageActionAnswer() {
    final var expectedType = MessageActionType.ANSWER;

    final var certificateModel = FK3226MessageActionSpecification.create();

    assertTrue(certificateModel.stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.messageActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeMessageActionHandleComplement() {
    final var expectedType = MessageActionType.HANDLE_COMPLEMENT;

    final var certificateModel = FK3226MessageActionSpecification.create();

    assertTrue(certificateModel.stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.messageActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeMessageActionCannotComplement() {
    final var expectedType = MessageActionType.CANNOT_COMPLEMENT;

    final var certificateModel = FK3226MessageActionSpecification.create();

    assertTrue(certificateModel.stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.messageActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeMessageActionComplement() {
    final var expectedType = MessageActionType.COMPLEMENT;

    final var certificateModel = FK3226MessageActionSpecification.create();

    assertTrue(certificateModel.stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.messageActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeMessageActionForward() {
    final var expectedType = MessageActionType.FORWARD;

    final var certificateModel = FK3226MessageActionSpecification.create();

    assertTrue(certificateModel.stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.messageActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeMessageActionHandleMessage() {
    final var expectedType = MessageActionType.HANDLE_MESSAGE;

    final var certificateModel = FK3226MessageActionSpecification.create();

    assertTrue(certificateModel.stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.messageActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }
}