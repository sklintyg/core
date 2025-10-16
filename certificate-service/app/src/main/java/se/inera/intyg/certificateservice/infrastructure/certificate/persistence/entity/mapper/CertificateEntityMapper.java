package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateMetaData;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalCertificate;
import se.inera.intyg.certificateservice.domain.certificate.model.ReadyForSign;
import se.inera.intyg.certificateservice.domain.certificate.model.Revision;
import se.inera.intyg.certificateservice.domain.certificate.model.Revoked;
import se.inera.intyg.certificateservice.domain.certificate.model.Sent;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.certificate.model.Xml;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateVersion;
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateModelRepository;
import se.inera.intyg.certificateservice.domain.common.model.ExternalReference;
import se.inera.intyg.certificateservice.domain.common.model.HsaId;
import se.inera.intyg.certificateservice.domain.common.model.RevokedInformation;
import se.inera.intyg.certificateservice.domain.message.model.Forwarded;
import se.inera.intyg.certificateservice.domain.staff.model.Staff;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.PatientRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.StaffRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.UnitRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateModelEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateRelationEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateStatus;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateStatusEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateXmlEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.ExternalReferenceEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageTypeEnum;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.RevokedReason;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.RevokedReasonEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.StaffEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.CertificateEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.CertificateModelEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.CertificateRelationRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.MessageEntityRepository;

@Component
@RequiredArgsConstructor
public class CertificateEntityMapper {

  private final CertificateEntityRepository certificateEntityRepository;
  private final CertificateModelEntityRepository certificateModelEntityRepository;
  private final CertificateModelRepository certificateModelRepository;
  private final PatientRepository patientRepository;
  private final UnitRepository unitRepository;
  private final StaffRepository staffRepository;
  private final CertificateDataEntityMapper certificateDataEntityMapper;
  private final CertificateRelationRepository certificateRelationRepository;
  private final MessageEntityRepository messageEntityRepository;
  private final MessageEntityMapper messageEntityMapper;
  private final PlaceholderCertificateEntityMapper placeholderCertificateEntityMapper;

  public Certificate toDomain(CertificateEntity certificateEntity,
      CertificateRepository certificateRepository) {
    return toDomain(certificateEntity, true, certificateRepository);
  }

  private Certificate toDomain(CertificateEntity certificateEntity, boolean includeRelations,
      CertificateRepository certificateRepository) {
    if (certificateEntity.isPlaceHolder()) {
      return placeholderCertificateEntityMapper.toDomain(certificateEntity);
    }

    return toDomain(
        certificateEntity,
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
        ),
        includeRelations,
        certificateRepository
    );
  }

  public CertificateEntity toEntity(Certificate certificate) {
    final var certificateEntity = certificateEntityRepository.findByCertificateId(
            certificate.id().id())
        .map(result -> {
              result.setRevision(certificate.revision().value());
              result.setModified(LocalDateTime.now());
              result.setSigned(certificate.signed());
              result.setLocked(certificate.locked());
              return result;
            }
        )
        .orElse(toCertificateEntity(certificate));

    final var certificateStatus = CertificateStatus.valueOf(certificate.status().name());
    certificateEntity.setStatus(
        CertificateStatusEntity.builder()
            .key(certificateStatus.getKey())
            .status(certificateStatus.name())
            .build()
    );

    final var staffMap = staffRepository.staffs(certificate);

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
        staffMap.get(certificateMetaData.issuer().hsaId())
    );

    if (certificateEntity.getCreatedBy() == null) {
      certificateEntity.setCreatedBy(certificateEntity.getIssuedBy());
    }

    certificateEntity.setCertificateModel(
        certificateModel(certificate.certificateModel())
    );

    final var certificateDataEntity = certificateDataEntityMapper.toEntity(
        certificate.elementData()
    );
    certificateDataEntity.setKey(certificateEntity.getKey());
    certificateDataEntity.setCertificate(certificateEntity);
    certificateEntity.setData(certificateDataEntity);

    if (certificate.xml() != null) {
      final var certificateXmlEntity = new CertificateXmlEntity(certificate.xml().xml());
      certificateXmlEntity.setKey(certificateEntity.getKey());
      certificateXmlEntity.setCertificate(certificateEntity);
      certificateEntity.setXml(certificateXmlEntity);
    }

    if (certificate.sent() != null) {
      certificateEntity.setSentBy(
          certificate.sent().sentBy() != null
              ? staffMap.get(certificate.sent().sentBy().hsaId())
              : null
      );

      certificateEntity.setSent(
          certificate.sent().sentAt()
      );
    }

    if (certificate.readyForSign() != null) {
      certificateEntity.setReadyForSignBy(
          staffMap.get(certificate.readyForSign().readyForSignBy().hsaId())
      );

      certificateEntity.setReadyForSign(
          certificate.readyForSign().readyForSignAt()
      );
    }

    if (certificate.revoked() != null) {
      handleRevoked(certificate, certificateEntity, staffMap);
    }

    if (certificate.externalReference() != null) {
      certificateEntity.setExternalReference(
          ExternalReferenceEntity.builder()
              .key(certificateEntity.getKey())
              .certificate(certificateEntity)
              .reference(certificate.externalReference().value())
              .build()
      );
    }

    if (certificate.forwarded() != null) {
      certificateEntity.setForwarded(certificate.forwarded().value());
    }

    certificateEntity.setPlaceholder(false);

    return certificateEntity;
  }

  private static void handleRevoked(Certificate certificate, CertificateEntity certificateEntity,
      Map<HsaId, StaffEntity> staffMap) {
    final var reason = RevokedReason.valueOf(
        certificate.revoked().revokedInformation().reason()
    );

    certificateEntity.setRevokedReason(
        RevokedReasonEntity.builder()
            .key(reason.getKey())
            .reason(reason.name())
            .build()
    );

    certificateEntity.setRevokedMessage(
        certificate.revoked().revokedInformation().message()
    );

    certificateEntity.setRevokedBy(
        staffMap.get(certificate.revoked().revokedBy().hsaId())
    );

    certificateEntity.setRevoked(
        certificate.revoked().revokedAt()
    );
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

  private CertificateEntity toCertificateEntity(Certificate certificate) {
    return CertificateEntity.builder()
        .certificateId(certificate.id().id())
        .created(certificate.created())
        .signed(certificate.signed())
        .revision(certificate.revision().value())
        .modified(LocalDateTime.now())
        .locked(certificate.locked())
        .build();
  }

  public Certificate toDomain(CertificateEntity certificateEntity, CertificateModel model,
      CertificateRepository certificateRepository) {
    return toDomain(certificateEntity, model, true, certificateRepository);
  }

  private MedicalCertificate toDomain(CertificateEntity certificateEntity, CertificateModel model,
      boolean includeRelations, CertificateRepository certificateRepository) {
    final var relations = getRelations(certificateEntity, includeRelations);
    final var messages = messageEntityRepository.findMessageEntitiesByCertificate(certificateEntity)
        .stream()
        .filter(removeRemindersAndAnswers())
        .map(messageEntityMapper::toDomain)
        .toList();

    return MedicalCertificate.builder()
        .certificateRepository(certificateRepository)
        .id(new CertificateId(certificateEntity.getCertificateId()))
        .created(certificateEntity.getCreated())
        .signed(certificateEntity.getSigned())
        .modified(certificateEntity.getModified())
        .locked(certificateEntity.getLocked())
        .status(Status.valueOf(certificateEntity.getStatus().getStatus()))
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
                .creator(StaffEntityMapper.toDomain(certificateEntity.getCreatedBy()))
                .build()
        )
        .elementData(
            certificateDataEntityMapper.toDomain(certificateEntity.getData())
        )
        .xml(
            certificateEntity.getXml() != null
                ? new Xml(certificateEntity.getXml().getData())
                : null
        )
        .sent(
            certificateEntity.getSent() != null
                ? Sent.builder()
                .sentBy(getSentBy(certificateEntity))
                .sentAt(certificateEntity.getSent())
                .recipient(model.recipient())
                .build() : null
        )
        .revoked(
            certificateEntity.getRevoked() != null
                ? Revoked.builder()
                .revokedBy(StaffEntityMapper.toDomain(certificateEntity.getRevokedBy()))
                .revokedAt(certificateEntity.getRevoked())
                .revokedInformation(
                    new RevokedInformation(certificateEntity.getRevokedReason().getReason(),
                        certificateEntity.getRevokedMessage())
                )
                .build() : null
        )
        .readyForSign(
            certificateEntity.getReadyForSign() != null
                ? ReadyForSign.builder()
                .readyForSignBy(StaffEntityMapper.toDomain(certificateEntity.getReadyForSignBy()))
                .readyForSignAt(certificateEntity.getReadyForSign())
                .build() : null
        )
        .externalReference(
            certificateEntity.getExternalReference() != null
                ? new ExternalReference(certificateEntity.getExternalReference().getReference())
                : null
        )
        .parent(
            relations.stream()
                .filter(entity -> entity.getChildCertificate().getCertificateId()
                    .equals(certificateEntity.getCertificateId())
                )
                .findFirst()
                .map(relation ->
                    RelationEntityMapper.parentToDomain(
                        relation,
                        toDomain(relation.getParentCertificate(), false, certificateRepository)
                    )
                )
                .orElse(null)
        )
        .children(
            relations.stream()
                .filter(entity -> entity.getParentCertificate().getCertificateId()
                    .equals(certificateEntity.getCertificateId())
                )
                .map(relation ->
                    RelationEntityMapper.childToDomain(
                        relation,
                        toDomain(relation.getChildCertificate(), false, certificateRepository)
                    )
                )
                .toList()
        )
        .messages(messages)
        .forwarded(
            certificateEntity.getForwarded() != null
                ? new Forwarded(certificateEntity.getForwarded())
                : null
        )
        .build();
  }

  private static Staff getSentBy(CertificateEntity certificateEntity) {
    return certificateEntity.getSentBy() != null
        ? StaffEntityMapper.toDomain(certificateEntity.getSentBy())
        : null;
  }

  private static Predicate<MessageEntity> removeRemindersAndAnswers() {
    return messageEntity -> !messageEntity.getMessageType().getType()
        .equals(MessageTypeEnum.REMINDER.name())
        && !messageEntity.getMessageType().getType().equals(MessageTypeEnum.ANSWER.name());
  }

  private List<CertificateRelationEntity> getRelations(CertificateEntity certificateEntity,
      boolean includeRelations) {
    if (includeRelations) {
      return certificateRelationRepository.relations(certificateEntity);
    }
    return Collections.emptyList();
  }

}