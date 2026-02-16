package se.inera.intyg.certificateservice.domain.message.repository;

import java.util.List;
import se.inera.intyg.certificateservice.domain.common.model.MessagesRequest;
import se.inera.intyg.certificateservice.domain.common.model.PersonId;
import se.inera.intyg.certificateservice.domain.message.model.CertificateMessageCount;
import se.inera.intyg.certificateservice.domain.message.model.Message;
import se.inera.intyg.certificateservice.domain.message.model.MessageId;

public interface MessageRepository {

  Message save(Message message);

  boolean exists(MessageId messageId);

  Message getById(MessageId messageId);

  Message findById(MessageId messageId);

  List<Message> findByMessagesRequest(MessagesRequest messagesRequest);

  List<CertificateMessageCount> findCertificateMessageCountByPatientKeyAndStatusSentAndCreatedAfter(
      List<PersonId> patientIds, int maxDays);
}