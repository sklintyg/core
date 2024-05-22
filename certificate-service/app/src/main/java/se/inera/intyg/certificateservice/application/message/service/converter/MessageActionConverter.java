package se.inera.intyg.certificateservice.application.message.service.converter;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.common.dto.ResourceLinkDTO;
import se.inera.intyg.certificateservice.application.common.dto.ResourceLinkTypeDTO;
import se.inera.intyg.certificateservice.domain.message.model.MessageAction;

@Component
public class MessageActionConverter {

  public ResourceLinkDTO convert(MessageAction messageAction) {
    return ResourceLinkDTO.builder()
        .type(ResourceLinkTypeDTO.toResourceLinkType(messageAction.type()))
        .name(messageAction.name())
        .description(messageAction.description())
        .enabled(messageAction.enabled())
        .build();
  }
}
