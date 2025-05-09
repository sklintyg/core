package se.inera.intyg.certificateservice.domain.action.message.model;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateAction;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.MessageActionSpecification;
import se.inera.intyg.certificateservice.domain.message.model.Message;
import se.inera.intyg.certificateservice.domain.message.model.MessageActionLink;
import se.inera.intyg.certificateservice.domain.message.model.MessageActionType;

@Builder
@Getter(AccessLevel.NONE)
public class MessageActionHandleComplement implements MessageAction {

  private final MessageActionSpecification specification;

  @Override
  public MessageActionType type() {
    return specification.messageActionType();
  }

  @Override
  public boolean evaluate(List<CertificateAction> certificateActions,
      Message message) {
    if (!message.actionAvailable(CertificateActionType.HANDLE_COMPLEMENT, certificateActions)) {
      return false;
    }

    return message.isUnhandledComplement();
  }

  @Override
  public MessageActionLink actionLink() {
    return MessageActionLink.builder()
        .type(MessageActionType.HANDLE_COMPLEMENT)
        .name("Hantera")
        .description("Hantera kompletteringsbegäran.")
        .enabled(true)
        .build();
  }
}
