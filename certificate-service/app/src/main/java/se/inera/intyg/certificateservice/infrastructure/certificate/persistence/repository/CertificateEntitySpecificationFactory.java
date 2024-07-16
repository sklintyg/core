package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository;

import static org.springframework.data.jpa.domain.Specification.where;
import static se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.CertificateEntitySpecification.modifiedEqualsAndGreaterThan;
import static se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.CertificateEntitySpecification.modifiedEqualsAndLesserThan;
import static se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.PatientEntitySpecification.equalsPatient;
import static se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.StaffEntitySpecification.equalsIssuedByStaff;
import static se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.StatusEntitySpecification.containsStatus;
import static se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.UnitEntitySpecification.equalsCareProvider;
import static se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.UnitEntitySpecification.equalsCareUnit;
import static se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.UnitEntitySpecification.equalsIssuedOnUnit;
import static se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.UnitEntitySpecification.issuedOnUnitIdIn;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.common.model.CertificatesRequest;
import se.inera.intyg.certificateservice.domain.patient.model.CertificatesWithQARequest;
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

    if (certificatesRequest.statuses() != null && !certificatesRequest.statuses().isEmpty()) {
      specification = specification.and(
          containsStatus(certificatesRequest.statuses())
      );
    }

    return specification;
  }

  public Specification<CertificateEntity> create(CertificatesWithQARequest certificatesRequest) {
    //TODO: If from or two is provided in the request, they should be used to find certificates with events
    Specification<CertificateEntity> specification = where(null);
    if (certificatesRequest.careProviderId() != null) {
      specification = specification.and(
          equalsCareProvider(certificatesRequest.careProviderId())
      );
    } else if (certificatesRequest.unitIds() != null) {
      specification = specification.and(
          issuedOnUnitIdIn(certificatesRequest.unitIds())
      );
    }

    specification = specification.and(
        equalsPatient(certificatesRequest.personId())
    );

    return specification;
  }
}
