package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.common.model.HsaId;
import se.inera.intyg.certificateservice.domain.common.model.PersonId;
import se.inera.intyg.certificateservice.domain.patient.model.Patient;
import se.inera.intyg.certificateservice.domain.unit.model.CareProvider;
import se.inera.intyg.certificateservice.domain.unit.model.CareUnit;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.PatientRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.UnitRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateStatus;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateStatusEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.CertificateEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.CertificateModelEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.StaffEntityRepository;

@Component
@RequiredArgsConstructor
public class PlaceholderCertificateEntityMapper {

  private static final String PLACEHOLDER = "PLACEHOLDER";
  private final CertificateEntityRepository certificateEntityRepository;
  private final CertificateModelEntityRepository certificateModelEntityRepository;
  private final StaffEntityRepository staffEntityRepository;
  private final PatientRepository patientRepository;
  private final UnitRepository unitRepository;

  public CertificateEntity toEntity(Certificate certificate) {
    final var certificateEntity = certificateEntityRepository.findByCertificateId(
        certificate.id().id()
    ).orElse(toCertificateEntity(certificate));

    final var certificateStatus = CertificateStatus.valueOf(certificate.status().name());
    certificateEntity.setStatus(
        CertificateStatusEntity.builder()
            .key(certificateStatus.getKey())
            .status(certificateStatus.name())
            .build()
    );

    certificateEntity.setPatient(
        patientRepository.patient(
            Patient.builder()
                .id(
                    PersonId.builder()
                        .id(PLACEHOLDER)
                        .build()
                )
                .build()
        )
    );
    certificateEntity.setCareProvider(
        unitRepository.careProvider(
            CareProvider.builder()
                .hsaId(new HsaId(PLACEHOLDER))
                .build()
        )
    );
    certificateEntity.setCareUnit(
        unitRepository.careUnit(
            CareUnit.builder()
                .hsaId(new HsaId(PLACEHOLDER))
                .build()
        )
    );
    certificateEntity.setIssuedOnUnit(
        unitRepository.issuingUnit(
            CareUnit.builder()
                .hsaId(new HsaId(PLACEHOLDER))
                .build()
        )
    );
    certificateEntity.setIssuedBy(
        staffEntityRepository.findByHsaId(PLACEHOLDER)
            .orElseThrow()
    );

    certificateEntity.setCreatedBy(certificateEntity.getIssuedBy());

    certificateEntity.setCertificateModel(
        certificateModelEntityRepository.findByTypeAndVersion(PLACEHOLDER, PLACEHOLDER)
            .orElseThrow()
    );

    return certificateEntity;
  }

  private CertificateEntity toCertificateEntity(Certificate certificate) {
    return CertificateEntity.builder()
        .certificateId(certificate.id().id())
        .created(LocalDateTime.now())
        .modified(LocalDateTime.now())
        .revision(0L)
        .build();
  }
}