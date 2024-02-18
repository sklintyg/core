package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository;

import static org.springframework.data.jpa.domain.Specification.where;
import static se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.CertificateEntitySpecification.modifiedEqualsAndGreaterThan;
import static se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.CertificateEntitySpecification.modifiedEqualsAndLesserThan;
import static se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.PatientEntitySpecification.equalsPatient;
import static se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.StaffEntitySpecification.equalsIssuedByStaff;
import static se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.UnitEntitySpecification.equalsCareUnit;
import static se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.UnitEntitySpecification.equalsIssuedOnUnit;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.common.model.CertificatesRequest;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateEntity;

@Component
public class CertificateEntitySpecificationFactory {

  public Specification<CertificateEntity> create(CertificatesRequest certificatesRequest) {
    Specification<CertificateEntity> specification = where(null);
    if (certificatesRequest.from() != null) {
      specification = specification.and(
          modifiedEqualsAndGreaterThan(certificatesRequest.from())
      );
    }

    if (certificatesRequest.to() != null) {
      specification = specification.and(
          modifiedEqualsAndLesserThan(certificatesRequest.to())
      );
    }

    if (certificatesRequest.issuedUnitId() != null) {
      specification = specification.and(
          equalsIssuedOnUnit(certificatesRequest.issuedUnitId())
      );
    }

    if (certificatesRequest.careUnitId() != null) {
      specification = specification.and(
          equalsCareUnit(certificatesRequest.careUnitId())
      );
    }

    if (certificatesRequest.issuedByStaffId() != null) {
      specification = specification.and(
          equalsIssuedByStaff(certificatesRequest.issuedByStaffId())
      );
    }

    if (certificatesRequest.personId() != null) {
      specification = specification.and(
          equalsPatient(certificatesRequest.personId())
      );
    }
    return specification;
  }
}
