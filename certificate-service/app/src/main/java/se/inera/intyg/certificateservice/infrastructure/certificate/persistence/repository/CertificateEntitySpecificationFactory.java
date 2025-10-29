package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository;

import static org.springframework.data.jpa.domain.Specification.where;
import static se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.CertificateEntitySpecification.createdEqualsAndGreaterThan;
import static se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.CertificateEntitySpecification.createdEqualsAndLesserThan;
import static se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.CertificateEntitySpecification.modifiedEqualsAndGreaterThan;
import static se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.CertificateEntitySpecification.modifiedEqualsAndLesserThan;
import static se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.CertificateEntitySpecification.notPlacerholderCertificate;
import static se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.CertificateEntitySpecification.signedEqualsAndGreaterThan;
import static se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.CertificateEntitySpecification.signedEqualsAndLesserThan;
import static se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.CertificateModelEntitySpecification.containsTypes;
import static se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.PatientEntitySpecification.equalsPatient;
import static se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.StaffEntitySpecification.issuedByStaffIdIn;
import static se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.StatusEntitySpecification.containsStatus;
import static se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.UnitEntitySpecification.equalsCareUnit;
import static se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.UnitEntitySpecification.issuedOnUnitIdIn;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.common.model.CertificatesRequest;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateEntity;

@Component
public class CertificateEntitySpecificationFactory {

  public Specification<CertificateEntity> create(CertificatesRequest certificatesRequest) {
    Specification<CertificateEntity> specification = where(null);
    if (certificatesRequest.modifiedFrom() != null) {
      specification = specification.and(
          modifiedEqualsAndGreaterThan(certificatesRequest.modifiedFrom())
      );
    }

    if (certificatesRequest.modifiedTo() != null) {
      specification = specification.and(
          modifiedEqualsAndLesserThan(certificatesRequest.modifiedTo())
      );
    }

    if (certificatesRequest.createdFrom() != null) {
      specification = specification.and(
          createdEqualsAndGreaterThan(certificatesRequest.createdFrom())
      );
    }

    if (certificatesRequest.createdTo() != null) {
      specification = specification.and(
          createdEqualsAndLesserThan(certificatesRequest.createdTo())
      );
    }

    if (certificatesRequest.issuedUnitIds() != null) {
      specification = specification.and(
          issuedOnUnitIdIn(certificatesRequest.issuedUnitIds())
      );
    }

    if (certificatesRequest.careUnitId() != null) {
      specification = specification.and(
          equalsCareUnit(certificatesRequest.careUnitId())
      );
    }

    if (certificatesRequest.issuedByStaffIds() != null && !certificatesRequest.issuedByStaffIds()
        .isEmpty()) {
      specification = specification.and(
          issuedByStaffIdIn(certificatesRequest.issuedByStaffIds())
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

    if (certificatesRequest.types() != null && !certificatesRequest.types().isEmpty()) {
      specification = specification.and(
          containsTypes(certificatesRequest.types())
      );
    }

    specification = specification.and(
        notPlacerholderCertificate()
    );

    if (certificatesRequest.signedFrom() != null) {
      specification = specification.and(
          signedEqualsAndGreaterThan(certificatesRequest.signedFrom())
      );
    }

    if (certificatesRequest.signedTo() != null) {
      specification = specification.and(
          signedEqualsAndLesserThan(certificatesRequest.signedTo())
      );
    }

    return specification;
  }
}
