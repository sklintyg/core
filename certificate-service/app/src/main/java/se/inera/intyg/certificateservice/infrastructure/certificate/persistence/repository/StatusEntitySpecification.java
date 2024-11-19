package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository;

import jakarta.persistence.criteria.Join;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateStatusEntity;

public class StatusEntitySpecification {

  private StatusEntitySpecification() {
  }

  public static Specification<CertificateEntity> containsStatus(List<Status> statuses) {
    final var statusNames = statuses.stream()
        .map(Enum::name)
        .toList();

    return (root, query, criteriaBuilder) ->
    {
      Join<CertificateStatusEntity, CertificateEntity> status = root.join("status");
      return criteriaBuilder.upper(status.get("status")).in(statusNames);
    };
  }
}
