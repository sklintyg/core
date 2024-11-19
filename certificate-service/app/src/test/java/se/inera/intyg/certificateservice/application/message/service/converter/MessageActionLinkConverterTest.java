package se.inera.intyg.certificateservice.application.message.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.application.common.dto.ResourceLinkTypeDTO.COMPLEMENT_CERTIFICATE;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.message.model.MessageActionLink;
import se.inera.intyg.certificateservice.domain.message.model.MessageActionType;

class MessageActionLinkConverterTest {

  private static final String NAME = "name";
  private static final String DESCRIPTION = "description";
  private MessageActionLinkConverter messageActionLinkConverter;
  private MessageActionLink messageActionLink;

  @BeforeEach
  void setUp() {
    messageActionLinkConverter = new MessageActionLinkConverter();
    messageActionLink = MessageActionLink.builder()
        .type(MessageActionType.COMPLEMENT)
        .name(NAME)
        .description(DESCRIPTION)
        .enabled(true)
        .build();
  }

  @Test
  void shallIncludeType() {
    assertEquals(COMPLEMENT_CERTIFICATE,
        messageActionLinkConverter.convert(messageActionLink).getType());
  }

  @Test
  void shallIncludeName() {
    assertEquals(NAME, messageActionLinkConverter.convert(messageActionLink).getName());
  }

  @Test
  void shallIncludeDescription() {
    assertEquals(DESCRIPTION,
        messageActionLinkConverter.convert(messageActionLink).getDescription());
  }

  @Test
  void shallIncludeEnabled() {
    assertTrue(messageActionLinkConverter.convert(messageActionLink).isEnabled());
  }
}
