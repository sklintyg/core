package se.inera.intyg.certificateservice.domain.action.message.model;

import java.util.List;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateAction;
import se.inera.intyg.certificateservice.domain.message.model.Message;
import se.inera.intyg.certificateservice.domain.message.model.MessageActionLink;
import se.inera.intyg.certificateservice.domain.message.model.MessageActionType;

public interface MessageAction {

  MessageActionType type();

  MessageActionLink actionLink();

  boolean evaluate(List<CertificateAction> certificateActions, Message message);
}
