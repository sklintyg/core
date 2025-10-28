package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository;

import jakarta.persistence.criteria.Join;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import se.inera.intyg.certificateservice.domain.common.model.HsaId;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.StaffEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.UnitEntity;

public class StaffEntitySpecification {

  private StaffEntitySpecification() {
  }

  public static Specification<CertificateEntity> issuedByStaffIdIn(List<HsaId> staffIds) {
    return (root, query, criteriaBuilder) ->
    {
      Join<UnitEntity, CertificateEntity> certificateIssuedOn = root.join("issuedBy");
      return certificateIssuedOn.get("hsaId").in(
          staffIds.stream()
              .map(HsaId::id)
              .toList()
      );
    };
  }

  public static Specification<MessageEntity> equalsIssuedByStaffForMessage(HsaId issuedByStaff) {
    return (root, query, criteriaBuilder) ->
    {
      Join<CertificateEntity, MessageEntity> certificate = root.join("certificate");
      Join<StaffEntity, CertificateEntity> certificateIssuedBy = certificate.join("issuedBy");
      return criteriaBuilder.equal(certificateIssuedBy.get("hsaId"), issuedByStaff.id());
    };
  }
}
