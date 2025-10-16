package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository;

import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateEntity;

public class CertificateModelEntitySpecification {

  private CertificateModelEntitySpecification() {

  }

  public static Specification<CertificateEntity> containsTypes(List<CertificateType> types) {
    final var typeNames = types.stream()
        .map(CertificateType::type)
        .toList();

    return (root, query, criteriaBuilder) ->
    {
      final var type = root.join("certificateModel");
      return criteriaBuilder.upper(type.get("type")).in(typeNames);
    };
  }
}
