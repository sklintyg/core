package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateMetaData;
import se.inera.intyg.certificateservice.domain.certificate.model.Revision;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateVersion;
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateModelRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.PatientRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.StaffRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.UnitRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateModelEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.CertificateEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.CertificateModelEntityRepository;

@Component
@RequiredArgsConstructor
public class CertificateEntityMapper {

  private final CertificateEntityRepository certificateEntityRepository;
  private final CertificateModelEntityRepository certificateModelEntityRepository;
  private final CertificateModelRepository certificateModelRepository;
  private final PatientRepository patientRepository;
  private final UnitRepository unitRepository;
  private final StaffRepository staffRepository;

  public Certificate toDomain(CertificateEntity certificateEntity) {
    return toDomain(certificateEntity,
        certificateModelRepository.getById(
            CertificateModelId.builder()
                .type(
                    new CertificateType(certificateEntity.getCertificateModel().getType())
                )
                .version(
                    new CertificateVersion(
                        certificateEntity.getCertificateModel().getVersion()
                    )
                )
                .build()
        )
    );
  }

  public CertificateEntity toEntity(Certificate certificate) {
    final var certificateEntity = certificateEntityRepository.findByCertificateId(
            certificate.id().id())
        .map(result -> {
              result.setRevision(certificate.revision().value());
              result.setModified(LocalDateTime.now());
              return result;
            }
        )
        .orElse(toCertificateEntity(certificate));

    final var certificateMetaData = certificate.certificateMetaData();

    certificateEntity.setPatient(
        patientRepository.patient(certificateMetaData.patient())
    );
    certificateEntity.setCareProvider(
        unitRepository.careProvider(certificateMetaData.careProvider())
    );
    certificateEntity.setCareUnit(
        unitRepository.careUnit(certificateMetaData.careUnit())
    );
    certificateEntity.setIssuedOnUnit(
        unitRepository.issuingUnit(certificateMetaData.issuingUnit())
    );
    certificateEntity.setIssuedBy(
        staffRepository.staff(certificateMetaData.issuer())
    );

    if (certificateEntity.getCreatedBy() == null) {
      certificateEntity.setCreatedBy(certificateEntity.getIssuedBy());
    }

    certificateEntity.setCertificateModel(
        certificateModel(certificate.certificateModel())
    );

    final var certificateDataEntity = CertificateDataEntityMapper.toEntity(
        certificate.elementData());
    certificateDataEntity.setKey(certificateEntity.getKey());
    certificateDataEntity.setCertificate(certificateEntity);
    certificateEntity.setData(certificateDataEntity);

    return certificateEntity;
  }

  private CertificateModelEntity certificateModel(CertificateModel certificateModel) {
    return certificateModelEntityRepository.findByTypeAndVersion(
            certificateModel.id().type().type(),
            certificateModel.id().version().version()
        )
        .orElseGet(() -> certificateModelEntityRepository.save(
            CertificateModelEntityMapper.toEntity(certificateModel)
        ));
  }

  public CertificateEntity toCertificateEntity(Certificate certificate) {
    return CertificateEntity.builder()
        .certificateId(certificate.id().id())
        .created(certificate.created())
        .revision(certificate.revision().value())
        .modified(LocalDateTime.now())
        .build();
  }

  public Certificate toDomain(CertificateEntity certificateEntity, CertificateModel model) {
    return Certificate.builder()
        .id(new CertificateId(certificateEntity.getCertificateId()))
        .created(certificateEntity.getCreated())
        .status(Status.DRAFT)
        .revision(new Revision(certificateEntity.getRevision()))
        .certificateModel(model)
        .certificateMetaData(
            CertificateMetaData.builder()
                .careProvider(
                    UnitEntityMapper.toCareProviderDomain(certificateEntity.getCareProvider())
                )
                .careUnit(
                    UnitEntityMapper.toCareUnitDomain(certificateEntity.getCareUnit())
                )
                .issuingUnit(
                    UnitEntityMapper.toIssuingUnitDomain(certificateEntity.getIssuedOnUnit())
                )
                .issuer(
                    StaffEntityMapper.toDomain(certificateEntity.getIssuedBy())
                )
                .patient(
                    PatientEntityMapper.toDomain(certificateEntity.getPatient())
                )
                .build()
        )
        .elementData(
            CertificateDataEntityMapper.toDomain(certificateEntity.getData())
        )
        .build();
  }
}
