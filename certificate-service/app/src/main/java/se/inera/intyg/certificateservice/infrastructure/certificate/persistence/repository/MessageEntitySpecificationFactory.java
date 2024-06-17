package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository;

import static org.springframework.data.jpa.domain.Specification.where;
import static se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.MessageEntitySpecification.equalsAuthor;
import static se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.MessageEntitySpecification.equalsForwarded;
import static se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.MessageEntitySpecification.sentEqualsAndGreaterThan;
import static se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.MessageEntitySpecification.sentEqualsAndLesserThan;
import static se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.PatientEntitySpecification.equalsPatientForMessage;
import static se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.StaffEntitySpecification.equalsIssuedByStaffForMessage;
import static se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.UnitEntitySpecification.inIssuedOnUnitIds;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.common.model.MessagesRequest;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageEntity;

@Component
public class MessageEntitySpecificationFactory {

  public Specification<MessageEntity> create(MessagesRequest request) {
    Specification<MessageEntity> specification = where(null);
    if (request.sentDateFrom() != null) {
      specification = specification.and(
          sentEqualsAndGreaterThan(request.sentDateFrom())
      );
    }

    if (request.sentDateTo() != null) {
      specification = specification.and(
          sentEqualsAndLesserThan(request.sentDateTo())
      );
    }

    if (request.issuedByStaffId() != null) {
      specification = specification.and(
          equalsIssuedByStaffForMessage(request.issuedByStaffId())
      );
    }

    if (request.issuedOnUnitIds() != null && !request.issuedOnUnitIds().isEmpty()) {
      specification = specification.and(
          inIssuedOnUnitIds(request.issuedOnUnitIds())
      );
    }

    if (request.personId() != null) {
      specification = specification.and(
          equalsPatientForMessage(request.personId())
      );
    }

    if (request.forwarded() != null) {
      specification = specification.and(
          equalsForwarded(request.forwarded())
      );
    }

    if (request.author() != null) {
      specification = specification.and(
          equalsAuthor(request.author())
      );
    }

    return specification;
  }
}
