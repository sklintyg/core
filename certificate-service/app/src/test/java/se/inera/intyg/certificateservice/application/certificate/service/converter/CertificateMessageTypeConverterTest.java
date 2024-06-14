package se.inera.intyg.certificateservice.application.certificate.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.message.dto.QuestionTypeDTO;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateMessageType;
import se.inera.intyg.certificateservice.domain.message.model.MessageType;
import se.inera.intyg.certificateservice.domain.message.model.Subject;

class CertificateMessageTypeConverterTest {

  private static final Subject SUBJECT = new Subject("subject");

  private CertificateMessageTypeConverter certificateMessageTypeConverter;

  @BeforeEach
  void setUp() {
    certificateMessageTypeConverter = new CertificateMessageTypeConverter();
  }

  @Nested
  class QuestionTypeTests {


    @Test
    void shallConvertMissing() {
      final var certificateMessageType = CertificateMessageType.builder()
          .type(MessageType.MISSING)
          .subject(SUBJECT)
          .build();

      assertEquals(QuestionTypeDTO.MISSING,
          certificateMessageTypeConverter.convert(certificateMessageType).getType());
    }

    @Test
    void shallConvertContact() {
      final var certificateMessageType = CertificateMessageType.builder()
          .type(MessageType.CONTACT)
          .subject(SUBJECT)
          .build();

      assertEquals(QuestionTypeDTO.CONTACT,
          certificateMessageTypeConverter.convert(certificateMessageType).getType());
    }

    @Test
    void shallConvertOther() {
      final var certificateMessageType = CertificateMessageType.builder()
          .type(MessageType.OTHER)
          .subject(SUBJECT)
          .build();

      assertEquals(QuestionTypeDTO.OTHER,
          certificateMessageTypeConverter.convert(certificateMessageType).getType());
    }

    @Test
    void shallConvertComplement() {
      final var certificateMessageType = CertificateMessageType.builder()
          .type(MessageType.COMPLEMENT)
          .subject(SUBJECT)
          .build();

      assertEquals(QuestionTypeDTO.COMPLEMENT,
          certificateMessageTypeConverter.convert(certificateMessageType).getType());
    }
  }

  @Test
  void shallConvertSubject() {
    final var certificateMessageType = CertificateMessageType.builder()
        .type(MessageType.COMPLEMENT)
        .subject(SUBJECT)
        .build();

    assertEquals(SUBJECT.subject(),
        certificateMessageTypeConverter.convert(certificateMessageType).getSubject());
  }
}
