package se.inera.intyg.certificateservice.infrastructure.certificate.persistence;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateMetaData;
import se.inera.intyg.certificateservice.domain.certificate.repository.SignedCertificateMetadataRepostiory;
import se.inera.intyg.certificateservice.domain.patient.model.Patient;
import se.inera.intyg.certificateservice.domain.staff.model.Staff;
import se.inera.intyg.certificateservice.domain.unit.model.IssuingUnit;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.PatientEntityMapper;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.PatientVersionEntityMapper;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.StaffEntityMapper;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.StaffVersionEntityMapper;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.UnitEntityMapper;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.UnitVersionEntityMapper;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.PatientVersionEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.StaffVersionEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.UnitVersionEntityRepository;

@Repository
@RequiredArgsConstructor
public class SignedCertificateMetadataRepository implements SignedCertificateMetadataRepostiory {

  private final StaffVersionEntityRepository staffVersionEntityRepository;
  private final PatientVersionEntityRepository patientVersionEntityRepository;
  private final UnitVersionEntityRepository unitVersionEntityRepository;


  @Override
  public CertificateMetaData getMetadataWhenSigned(CertificateMetaData metadata,
      LocalDateTime signedAt) {

    if (signedAt == null) {
      throw new IllegalStateException(
          "SignedAt is required to fetch metadata from signed instance");
    }

    final var patientWhenSigned = getPatientVersionWhenSigned(metadata.patient(), signedAt);
    final var staffWhenSigned = getStaffVersionWhenSigned(metadata.issuer(), signedAt);
    final var unitWhenSigned = getUnitVersionWhenSigned(metadata.issuingUnit(), signedAt);

    //TODO: add careprovider,unit,responsibleissuer?
    return CertificateMetaData.builder()
        .patient(patientWhenSigned)
        .issuer(staffWhenSigned)
        .issuingUnit(unitWhenSigned)
        .build();


  }

  private Patient getPatientVersionWhenSigned(Patient patient,
      LocalDateTime signedAt) {
    return patientVersionEntityRepository
        .findFirstCoveringTimestampOrderByMostRecent(patient.id().id(), signedAt)
        .map(PatientVersionEntityMapper::toPatient)
        .map(PatientEntityMapper::toDomain)
        .orElse(patient);
  }

  private Staff getStaffVersionWhenSigned(
      Staff staff, LocalDateTime signedAt) {
    return staffVersionEntityRepository
        .findFirstCoveringTimestampOrderByMostRecent(staff.hsaId().id(), signedAt)
        .map(StaffVersionEntityMapper::toStaff)
        .map(StaffEntityMapper::toDomain)
        .orElse(staff);
  }

  private IssuingUnit getUnitVersionWhenSigned(
      IssuingUnit unit, LocalDateTime signedAt) {
    return unitVersionEntityRepository
        .findFirstCoveringTimestampOrderByMostRecent(unit.hsaId().id(), signedAt)
        .map(UnitVersionEntityMapper::toUnit)
        .map(UnitEntityMapper::toIssuingUnitDomain)
        .orElse(unit);
  }

}
