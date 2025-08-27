package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository;

import java.time.LocalDateTime;
import org.springframework.data.jpa.domain.Specification;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateEntity;

public class CertificateEntitySpecification {

  private CertificateEntitySpecification() {
  }

  public static Specification<CertificateEntity> modifiedEqualsAndGreaterThan(LocalDateTime from) {
    return (root, query, criteriaBuilder) ->
        criteriaBuilder.greaterThanOrEqualTo(root.get("modified"), from);
  }

  public static Specification<CertificateEntity> modifiedEqualsAndLesserThan(LocalDateTime to) {
    return (root, query, criteriaBuilder) ->
        criteriaBuilder.lessThanOrEqualTo(root.get("modified"), to);
  }

  public static Specification<CertificateEntity> createdEqualsAndGreaterThan(LocalDateTime from) {
    return (root, query, criteriaBuilder) ->
        criteriaBuilder.greaterThanOrEqualTo(root.get("created"), from);
  }

  public static Specification<CertificateEntity> createdEqualsAndLesserThan(LocalDateTime to) {
    return (root, query, criteriaBuilder) ->
        criteriaBuilder.lessThanOrEqualTo(root.get("created"), to);
  }

  public static Specification<CertificateEntity> notPlacerholderCertificate() {
    return (root, query, criteriaBuilder) ->
        criteriaBuilder.isFalse(root.get("placeholder"));
  }
}