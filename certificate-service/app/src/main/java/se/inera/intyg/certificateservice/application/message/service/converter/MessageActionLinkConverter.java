package se.inera.intyg.certificateservice.application.message.service.converter;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.common.dto.ResourceLinkDTO;
import se.inera.intyg.certificateservice.application.common.dto.ResourceLinkTypeDTO;
import se.inera.intyg.certificateservice.domain.message.model.MessageActionLink;

@Component
public class MessageActionLinkConverter {

  public ResourceLinkDTO convert(MessageActionLink messageActionLink) {
    return ResourceLinkDTO.builder()
        .type(ResourceLinkTypeDTO.toResourceLinkType(messageActionLink.type()))
        .name(messageActionLink.name())
        .description(messageActionLink.description())
        .enabled(messageActionLink.enabled())
        .build();
  }
}
