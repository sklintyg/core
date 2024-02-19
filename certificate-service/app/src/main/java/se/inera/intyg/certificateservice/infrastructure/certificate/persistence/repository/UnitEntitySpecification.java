package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository;

import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import se.inera.intyg.certificateservice.domain.common.model.HsaId;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.UnitEntity;

public class UnitEntitySpecification {

  private UnitEntitySpecification() {
  }

  public static Specification<CertificateEntity> equalsIssuedOnUnit(HsaId issuedOnUnit) {
    return (root, query, criteriaBuilder) ->
    {
      Join<UnitEntity, CertificateEntity> certificateIssuedOn = root.join("issuedOnUnit");
      return criteriaBuilder.equal(certificateIssuedOn.get("hsaId"), issuedOnUnit.id());
    };
  }

  public static Specification<CertificateEntity> equalsCareUnit(HsaId careUnit) {
    return (root, query, criteriaBuilder) ->
    {
      Join<UnitEntity, CertificateEntity> certificateCareUnit = root.join("careUnit");
      return criteriaBuilder.equal(certificateCareUnit.get("hsaId"), careUnit.id());
    };
  }
}
