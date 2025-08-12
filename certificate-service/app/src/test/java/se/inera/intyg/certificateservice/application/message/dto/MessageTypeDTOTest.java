package se.inera.intyg.certificateservice.application.message.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.application.message.dto.MessageTypeDTO.AVSTMN;
import static se.inera.intyg.certificateservice.application.message.dto.MessageTypeDTO.KOMPLT;
import static se.inera.intyg.certificateservice.application.message.dto.MessageTypeDTO.KONTKT;
import static se.inera.intyg.certificateservice.application.message.dto.MessageTypeDTO.OVRIGT;
import static se.inera.intyg.certificateservice.application.message.dto.MessageTypeDTO.PAMINN;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.message.model.MessageType;

class MessageTypeDTOTest {

  @Test
  void shallConvertToContact() {
    assertEquals(MessageType.CONTACT, KONTKT.toMessageType());
  }

  @Test
  void shallConvertToComplement() {
    assertEquals(MessageType.COMPLEMENT, KOMPLT.toMessageType());
  }

  @Test
  void shallConvertToReminder() {
    assertEquals(MessageType.REMINDER, PAMINN.toMessageType());
  }

  @Test
  void shallConvertToOther() {
    assertEquals(MessageType.OTHER, OVRIGT.toMessageType());
  }

  @Test
  void shouldConvertToMeeting() {
    assertEquals(MessageType.COORDINATION, AVSTMN.toMessageType());
  }
}