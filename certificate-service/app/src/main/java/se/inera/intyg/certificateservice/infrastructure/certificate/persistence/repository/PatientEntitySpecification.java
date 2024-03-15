package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository;

import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import se.inera.intyg.certificateservice.domain.patient.model.PersonId;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.PatientEntity;

public class PatientEntitySpecification {

  private PatientEntitySpecification() {
  }

  public static Specification<CertificateEntity> equalsPatient(PersonId personId) {
    return (root, query, criteriaBuilder) ->
    {
      Join<PatientEntity, CertificateEntity> patient = root.join("patient");
      return criteriaBuilder.equal(patient.get("id"), personId.id());
    };
  }
}
