package se.inera.intyg.certificateservice.application.message.service.validator;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.application.testdata.TestDataIncomingMessage.incomingComplementMessageBuilder;
import static se.inera.intyg.certificateservice.application.testdata.TestDataIncomingMessage.incomingReminderMessageBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_ID;

import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.common.dto.PersonIdDTO;
import se.inera.intyg.certificateservice.application.message.dto.IncomingComplementDTO;

@ExtendWith(MockitoExtension.class)
class IncomingMessageValidatorTest {

  @InjectMocks
  private IncomingMessageValidator validator;

  @Test
  void shallThrowIfIdIsNull() {
    final var request = incomingComplementMessageBuilder()
        .id(null)
        .build();

    final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
        () -> validator.validate(request)
    );

    assertTrue(illegalArgumentException.getMessage().contains("Message.id"),
        illegalArgumentException.getMessage()
    );
  }

  @Test
  void shallThrowIfIdIsBlank() {
    final var request = incomingComplementMessageBuilder()
        .id(" ")
        .build();

    final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
        () -> validator.validate(request)
    );

    assertTrue(illegalArgumentException.getMessage().contains("Message.id"),
        illegalArgumentException.getMessage()
    );
  }

  @Test
  void shallThrowIfCertificateIdIsNull() {
    final var request = incomingComplementMessageBuilder()
        .certificateId(null)
        .build();

    final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
        () -> validator.validate(request)
    );

    assertTrue(illegalArgumentException.getMessage().contains("Message.certificateId"),
        illegalArgumentException.getMessage()
    );
  }

  @Test
  void shallThrowIfCertificateIdIsBlank() {
    final var request = incomingComplementMessageBuilder()
        .certificateId(" ")
        .build();

    final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
        () -> validator.validate(request)
    );

    assertTrue(illegalArgumentException.getMessage().contains("Message.certificateId"),
        illegalArgumentException.getMessage()
    );
  }

  @Test
  void shallThrowIfContentIsNull() {
    final var request = incomingComplementMessageBuilder()
        .content(null)
        .build();

    final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
        () -> validator.validate(request)
    );

    assertTrue(illegalArgumentException.getMessage().contains("Message.content"),
        illegalArgumentException.getMessage()
    );
  }

  @Test
  void shallThrowIfContentIsBlank() {
    final var request = incomingComplementMessageBuilder()
        .content(" ")
        .build();

    final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
        () -> validator.validate(request)
    );

    assertTrue(illegalArgumentException.getMessage().contains("Message.content"),
        illegalArgumentException.getMessage()
    );
  }

  @Test
  void shallThrowIfSentIsNull() {
    final var request = incomingComplementMessageBuilder()
        .sent(null)
        .build();

    final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
        () -> validator.validate(request)
    );

    assertTrue(illegalArgumentException.getMessage().contains("Message.sent"),
        illegalArgumentException.getMessage()
    );
  }

  @Test
  void shallThrowIfSentByIsNull() {
    final var request = incomingComplementMessageBuilder()
        .sentBy(null)
        .build();

    final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
        () -> validator.validate(request)
    );

    assertTrue(illegalArgumentException.getMessage().contains("Message.sentBy"),
        illegalArgumentException.getMessage()
    );
  }

  @Test
  void shallThrowIfContactInfoIsNull() {
    final var request = incomingComplementMessageBuilder()
        .contactInfo(null)
        .build();

    final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
        () -> validator.validate(request)
    );

    assertTrue(illegalArgumentException.getMessage().contains("Message.contactInfo"),
        illegalArgumentException.getMessage()
    );
  }

  @Test
  void shallThrowIfPersonIdIsNull() {
    final var request = incomingComplementMessageBuilder()
        .personId(null)
        .build();

    final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
        () -> validator.validate(request)
    );

    assertTrue(illegalArgumentException.getMessage().contains("Message.personId"),
        illegalArgumentException.getMessage()
    );
  }

  @Test
  void shallThrowIfPersonIdIdIsNull() {
    final var request = incomingComplementMessageBuilder()
        .personId(
            PersonIdDTO.builder()
                .build()
        )
        .build();

    final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
        () -> validator.validate(request)
    );

    assertTrue(illegalArgumentException.getMessage().contains("Message.personId.id"),
        illegalArgumentException.getMessage()
    );
  }

  @Test
  void shallThrowIfPersonIdIdIsBlank() {
    final var request = incomingComplementMessageBuilder()
        .personId(
            PersonIdDTO.builder()
                .id("  ")
                .build()
        )
        .build();

    final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
        () -> validator.validate(request)
    );

    assertTrue(illegalArgumentException.getMessage().contains("Message.personId.id"),
        illegalArgumentException.getMessage()
    );
  }

  @Test
  void shallThrowIfPersonIdTypeIsNull() {
    final var request = incomingComplementMessageBuilder()
        .personId(
            PersonIdDTO.builder()
                .id(ATHENA_REACT_ANDERSSON_ID)
                .build()
        )
        .build();

    final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
        () -> validator.validate(request)
    );

    assertTrue(illegalArgumentException.getMessage().contains("Message.personId.type"),
        illegalArgumentException.getMessage()
    );
  }

  @Nested
  class ValidateComplementQuestion {

    @Test
    void shallThrowIfComplementIsNull() {
      final var request = incomingComplementMessageBuilder()
          .complements(null)
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> validator.validate(request)
      );

      assertTrue(illegalArgumentException.getMessage().contains("Message.complement"),
          illegalArgumentException.getMessage()
      );
    }

    @Test
    void shallThrowIfComplementQuestionIdIsNull() {
      final var request = incomingComplementMessageBuilder()
          .complements(
              List.of(
                  IncomingComplementDTO.builder()
                      .questionId(null)
                      .build()
              )
          )
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> validator.validate(request)
      );

      assertTrue(illegalArgumentException.getMessage().contains("Message.complement.questionId"),
          illegalArgumentException.getMessage()
      );
    }

    @Test
    void shallThrowIfComplementQuestionIdIsBlank() {
      final var request = incomingComplementMessageBuilder()
          .complements(
              List.of(
                  IncomingComplementDTO.builder()
                      .questionId("  ")
                      .build()
              )
          )
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> validator.validate(request)
      );

      assertTrue(illegalArgumentException.getMessage().contains("Message.complement.questionId"),
          illegalArgumentException.getMessage()
      );
    }

    @Test
    void shallThrowIfComplementContentIsNull() {
      final var request = incomingComplementMessageBuilder()
          .complements(
              List.of(
                  IncomingComplementDTO.builder()
                      .questionId("questionId")
                      .content(null)
                      .build()
              )
          )
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> validator.validate(request)
      );

      assertTrue(illegalArgumentException.getMessage().contains("Message.complement.content"),
          illegalArgumentException.getMessage()
      );
    }

    @Test
    void shallThrowIfComplementContentIsBlank() {
      final var request = incomingComplementMessageBuilder()
          .complements(
              List.of(
                  IncomingComplementDTO.builder()
                      .questionId("questionId")
                      .content("  ")
                      .build()
              )
          )
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> validator.validate(request)
      );

      assertTrue(illegalArgumentException.getMessage().contains("Message.complement.content"),
          illegalArgumentException.getMessage()
      );
    }
  }

  @Nested
  class ValidateReminderQuestion {

    @Test
    void shallThrowIfReminderMessageIdIsNull() {
      final var request = incomingReminderMessageBuilder()
          .reminderMessageId(null)
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> validator.validate(request)
      );

      assertTrue(illegalArgumentException.getMessage().contains("Message.reminderMessageId"),
          illegalArgumentException.getMessage()
      );
    }

    @Test
    void shallThrowIfReminderMessageIdIsEmpty() {
      final var request = incomingReminderMessageBuilder()
          .reminderMessageId(" ")
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> validator.validate(request)
      );

      assertTrue(illegalArgumentException.getMessage().contains("Message.reminderMessageId"),
          illegalArgumentException.getMessage()
      );
    }
  }
}
