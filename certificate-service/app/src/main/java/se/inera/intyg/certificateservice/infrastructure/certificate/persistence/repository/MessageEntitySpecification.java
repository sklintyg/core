package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository;

import java.time.LocalDateTime;
import org.springframework.data.jpa.domain.Specification;
import se.inera.intyg.certificateservice.domain.message.model.Author;
import se.inera.intyg.certificateservice.domain.message.model.Forwarded;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageEntity;

public class MessageEntitySpecification {

  private MessageEntitySpecification() {
  }

  public static Specification<MessageEntity> sentEqualsAndGreaterThan(LocalDateTime from) {
    return (root, query, criteriaBuilder) ->
        criteriaBuilder.greaterThanOrEqualTo(root.get("sent"), from.toLocalDate().atStartOfDay());
  }

  public static Specification<MessageEntity> sentEqualsAndLesserThan(LocalDateTime to) {
    return (root, query, criteriaBuilder) ->
        criteriaBuilder.lessThanOrEqualTo(root.get("sent"),
            to.toLocalDate().plusDays(1).atStartOfDay());
  }

  public static Specification<MessageEntity> equalsForwarded(Forwarded forwarded) {
    return (root, query, criteriaBuilder) ->
        criteriaBuilder.equal(root.get("forwarded"), forwarded.value());
  }

  public static Specification<MessageEntity> equalsAuthor(Author author) {
    return (root, query, criteriaBuilder) ->
        criteriaBuilder.equal(root.get("author"), author.author());
  }
}
