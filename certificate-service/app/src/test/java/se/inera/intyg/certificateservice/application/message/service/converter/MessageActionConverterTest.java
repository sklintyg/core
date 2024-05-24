package se.inera.intyg.certificateservice.application.message.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.application.common.dto.ResourceLinkTypeDTO.COMPLEMENT_CERTIFICATE;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.message.model.MessageAction;
import se.inera.intyg.certificateservice.domain.message.model.MessageActionType;

class MessageActionConverterTest {

  private static final String NAME = "name";
  private static final String DESCRIPTION = "description";
  private MessageActionConverter messageActionConverter;
  private MessageAction messageAction;

  @BeforeEach
  void setUp() {
    messageActionConverter = new MessageActionConverter();
    messageAction = MessageAction.builder()
        .type(MessageActionType.COMPLEMENT)
        .name(NAME)
        .description(DESCRIPTION)
        .enabled(true)
        .build();
  }

  @Test
  void shallIncludeType() {
    assertEquals(COMPLEMENT_CERTIFICATE, messageActionConverter.convert(messageAction).getType());
  }

  @Test
  void shallIncludeName() {
    assertEquals(NAME, messageActionConverter.convert(messageAction).getName());
  }

  @Test
  void shallIncludeDescription() {
    assertEquals(DESCRIPTION, messageActionConverter.convert(messageAction).getDescription());
  }

  @Test
  void shallIncludeEnabled() {
    assertTrue(messageActionConverter.convert(messageAction).isEnabled());
  }
}
