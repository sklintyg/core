package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository;

import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import se.inera.intyg.certificateservice.domain.common.model.HsaId;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.StaffEntity;

public class StaffEntitySpecification {

  private StaffEntitySpecification() {
  }

  public static Specification<CertificateEntity> equalsIssuedByStaff(HsaId issuedByStaff) {
    return (root, query, criteriaBuilder) ->
    {
      Join<StaffEntity, CertificateEntity> certificateIssuedBy = root.join("issuedBy");
      return criteriaBuilder.equal(certificateIssuedBy.get("hsaId"), issuedByStaff.id());
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
