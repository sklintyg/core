package se.inera.intyg.certificateservice.application.message.service.validator;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.application.testdata.TestDataIncomingMessage.incomingComplementMessageBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_ID;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.common.dto.PersonIdDTO;

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
  void shallThrowIfSubjectIsNull() {
    final var request = incomingComplementMessageBuilder()
        .subject(null)
        .build();

    final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
        () -> validator.validate(request)
    );

    assertTrue(illegalArgumentException.getMessage().contains("Message.subject"),
        illegalArgumentException.getMessage()
    );
  }

  @Test
  void shallThrowIfSubjectIsBlank() {
    final var request = incomingComplementMessageBuilder()
        .subject(" ")
        .build();

    final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
        () -> validator.validate(request)
    );

    assertTrue(illegalArgumentException.getMessage().contains("Message.subject"),
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
  }
}