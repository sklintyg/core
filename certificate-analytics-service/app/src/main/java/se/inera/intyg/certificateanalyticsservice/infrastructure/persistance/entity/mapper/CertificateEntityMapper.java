package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateanalyticsservice.application.messages.model.PseudonymizedAnalyticsMessage;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.CertificateEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.CertificateRelationEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.CertificateRelationTypeEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.CareProviderRepository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.CertificateEntityRepository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.CertificateRelationEntityRepository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.CertificateRelationTypeEntityRepository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.CertificateTypeRepository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.PatientRepository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.UnitRepository;

@Component
@RequiredArgsConstructor
public class CertificateEntityMapper {

  private final CareProviderRepository careProviderRepository;
  private final UnitRepository unitRepository;
  private final PatientRepository patientRepository;
  private final CertificateEntityRepository certificateEntityRepository;
  private final CertificateTypeRepository certificateTypeRepository;
  private final CertificateRelationEntityRepository certificateRelationEntityRepository;
  private final CertificateRelationTypeEntityRepository certificateRelationTypeEntityRepository;

  public CertificateEntity map(PseudonymizedAnalyticsMessage message) {
    final var certificateEntity = certificateEntityRepository.findByCertificateId(
            message.getCertificateId())
        .orElseGet(() ->
            certificateEntityRepository.save(CertificateEntity.builder()
                .certificateId(message.getCertificateId())
                .certificateType(
                    certificateTypeRepository.findOrCreate(
                        message.getCertificateType(), message.getCertificateTypeVersion()
                    )
                )
                .patient(patientRepository.findOrCreate(message.getCertificatePatientId()))
                .unit(unitRepository.findOrCreate(message.getCertificateUnitId()))
                .careProvider(
                    careProviderRepository.findOrCreate(message.getCertificateCareProviderId())
                )
                .build()
            )
        );

    if (message.getCertificateRelationParentId() != null
        && message.getCertificateRelationParentType() != null) {
      certificateEntityRepository.findByCertificateId(message.getCertificateRelationParentId())
          .ifPresent(parent -> {
                final var relationType = certificateRelationTypeEntityRepository
                    .findByRelationType(message.getCertificateRelationParentType())
                    .orElseGet(() ->
                        certificateRelationTypeEntityRepository.save(
                            CertificateRelationTypeEntity.builder()
                                .relationType(message.getCertificateRelationParentType())
                                .build()
                        )
                    );

                final var exists = certificateRelationEntityRepository
                    .findAll().stream()
                    .anyMatch(rel ->
                        rel.getParentCertificate().equals(parent)
                            && rel.getChildCertificate().equals(certificateEntity)
                            && rel.getRelationType().equals(relationType)
                    );

                if (!exists) {
                  certificateRelationEntityRepository.save(
                      CertificateRelationEntity.builder()
                          .parentCertificate(parent)
                          .childCertificate(certificateEntity)
                          .relationType(relationType)
                          .build()
                  );
                }
              }
          );
    }
    return certificateEntity;
  }
}