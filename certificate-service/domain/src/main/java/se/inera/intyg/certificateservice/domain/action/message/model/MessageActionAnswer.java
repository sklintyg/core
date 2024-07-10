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
import se.inera.intyg.certificateservice.domain.message.model.MessageStatus;
import se.inera.intyg.certificateservice.domain.message.model.MessageType;

@Builder
@Getter(AccessLevel.NONE)
public class MessageActionAnswer implements MessageAction {

  private final MessageActionSpecification specification;

  @Override
  public MessageActionType type() {
    return specification.messageActionType();
  }

  @Override
  public boolean evaluate(List<CertificateAction> certificateActions,
      Message message) {
    if (!message.actionAvailable(CertificateActionType.ANSWER_MESSAGE, certificateActions)) {
      return false;
    }

    return message.authoredStaff() == null
        && message.answer() == null
        && message.type() != MessageType.COMPLEMENT
        && message.status() != MessageStatus.HANDLED;
  }

  @Override
  public MessageActionLink actionLink() {
    return MessageActionLink.builder()
        .type(MessageActionType.ANSWER)
        .name("Skapa")
        .description("Svara på fråga")
        .enabled(true)
        .build();
  }
}
