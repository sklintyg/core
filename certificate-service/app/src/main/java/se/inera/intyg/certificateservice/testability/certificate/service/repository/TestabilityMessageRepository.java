package se.inera.intyg.certificateservice.testability.certificate.service.repository;

import java.util.List;
import se.inera.intyg.certificateservice.domain.message.repository.MessageRepository;

public interface TestabilityMessageRepository extends MessageRepository {

  void remove(List<String> messageIds);
}
