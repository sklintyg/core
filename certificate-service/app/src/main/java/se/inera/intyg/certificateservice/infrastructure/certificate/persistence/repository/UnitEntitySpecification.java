package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository;

import jakarta.persistence.criteria.Join;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import se.inera.intyg.certificateservice.domain.common.model.HsaId;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.UnitEntity;

public class UnitEntitySpecification {

  private static final String HSA_ID = "hsaId";
  private static final String ISSUED_ON_UNIT = "issuedOnUnit";

  private UnitEntitySpecification() {
  }

  public static Specification<MessageEntity> inIssuedOnUnitIds(List<HsaId> unitIds) {
    return (root, query, criteriaBuilder) ->
    {
      Join<CertificateEntity, MessageEntity> certificate = root.join("certificate");
      Join<UnitEntity, CertificateEntity> certificateIssuedOn = certificate.join(ISSUED_ON_UNIT);
      return certificateIssuedOn.get(HSA_ID).in(
          unitIds.stream()
              .map(HsaId::id)
              .toList()
      );
    };
  }

  public static Specification<CertificateEntity> issuedOnUnitIdIn(List<HsaId> unitIds) {
    return (root, query, criteriaBuilder) ->
    {
      Join<UnitEntity, CertificateEntity> certificateIssuedOn = root.join(ISSUED_ON_UNIT);
      return certificateIssuedOn.get(HSA_ID).in(
          unitIds.stream()
              .map(HsaId::id)
              .toList()
      );
    };
  }

  public static Specification<CertificateEntity> equalsCareUnit(HsaId careUnit) {
    return (root, query, criteriaBuilder) ->
    {
      Join<UnitEntity, CertificateEntity> certificateCareUnit = root.join("careUnit");
      return criteriaBuilder.equal(certificateCareUnit.get(HSA_ID), careUnit.id());
    };
  }
}
