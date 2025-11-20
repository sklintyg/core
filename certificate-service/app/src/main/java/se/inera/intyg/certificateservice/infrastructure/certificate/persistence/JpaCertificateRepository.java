package se.inera.intyg.certificateservice.infrastructure.certificate.persistence;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateExportPage;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateMetaData;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalCertificate;
import se.inera.intyg.certificateservice.domain.certificate.model.PlaceholderCertificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Relation;
import se.inera.intyg.certificateservice.domain.certificate.model.RelationType;
import se.inera.intyg.certificateservice.domain.certificate.model.Revision;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PlaceholderCertificateRequest;
import se.inera.intyg.certificateservice.domain.common.model.CertificatesRequest;
import se.inera.intyg.certificateservice.domain.common.model.HsaId;
import se.inera.intyg.certificateservice.domain.patient.model.Patient;
import se.inera.intyg.certificateservice.domain.staff.model.Staff;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateStatus;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.StaffVersionEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.UnitEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.UnitVersionEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.CertificateEntityMapper;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.PatientEntityMapper;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.PatientVersionEntityMapper;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.PlaceholderCertificateEntityMapper;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.StaffEntityMapper;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.StaffVersionEntityMapper;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.UnitEntityMapper;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.UnitVersionEntityMapper;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.CertificateEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.CertificateEntitySpecificationFactory;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.CertificateRelationRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.PatientVersionEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.StaffVersionEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.UnitVersionEntityRepository;

@Repository
@RequiredArgsConstructor
public class JpaCertificateRepository {

  @Value("${erase.certificates.page.size:1000}")
  private int eraseCertificatesPageSize;

  private final PlaceholderCertificateEntityMapper placeholderCertificateEntityMapper;
  private final CertificateEntityRepository certificateEntityRepository;
  private final CertificateEntityMapper certificateEntityMapper;
  private final CertificateEntitySpecificationFactory certificateEntitySpecificationFactory;
  private final CertificateRelationRepository certificateRelationRepository;
  private final StaffVersionEntityRepository staffVersionEntityRepository;
  private final PatientVersionEntityRepository patientVersionEntityRepository;
  private final UnitVersionEntityRepository unitVersionEntityRepository;

  public Certificate create(CertificateModel certificateModel,
      CertificateRepository certificateRepository) {
    if (certificateModel == null) {
      throw new IllegalArgumentException("Unable to create, certificateModel was null");
    }

    return MedicalCertificate.builder()
        .id(new CertificateId(UUID.randomUUID().toString()))
        .created(LocalDateTime.now(ZoneId.systemDefault()))
        .certificateModel(certificateModel)
        .revision(new Revision(0))
        .certificateRepository(certificateRepository)
        .build();
  }


  public Certificate createFromPlaceholder(PlaceholderCertificateRequest request,
      CertificateModel model, CertificateRepository certificateRepository) {
    final var placeholderCertificate = PlaceholderCertificate.builder()
        .id(request.certificateId())
        .status(request.status())
        .certificateMetaData(
            CertificateMetaData.builder()
                .issuingUnit(request.issuingUnit())
                .build()
        )
        .build();

    certificateEntityRepository.save(
        placeholderCertificateEntityMapper.toEntity(placeholderCertificate)
    );

    return MedicalCertificate.builder()
        .id(new CertificateId(UUID.randomUUID().toString()))
        .created(LocalDateTime.now(ZoneId.systemDefault()))
        .certificateModel(model)
        .revision(new Revision(0))
        .parent(
            Relation.builder()
                .certificate(placeholderCertificate)
                .type(RelationType.RENEW)
                .created(LocalDateTime.now(ZoneId.systemDefault()))
                .build()
        )
        .certificateRepository(certificateRepository)
        .build();
  }


  public Certificate save(Certificate certificate, CertificateRepository certificateRepository) {
    if (certificate == null) {
      throw new IllegalArgumentException(
          "Unable to save, certificate was null"
      );
    }

    if (Status.DELETED_DRAFT.equals(certificate.status())) {
      certificateEntityRepository.findByCertificateId(certificate.id().id())
          .ifPresent(entity -> {
                certificateRelationRepository.deleteRelations(entity);
                certificateEntityRepository.delete(entity);
              }
          );
      return certificate;
    }

    if (Status.LOCKED_DRAFT.equals(certificate.status())) {
      certificateEntityRepository.findByCertificateId(certificate.id().id())
          .ifPresent(certificateRelationRepository::deleteRelations);
    }

    final var savedEntity = certificateEntityRepository.save(
        certificateEntityMapper.toEntity(certificate)
    );

    certificateRelationRepository.save(
        certificate, savedEntity
    );

    return certificateEntityMapper.toDomain(savedEntity, certificateRepository);
  }


  public Certificate getById(CertificateId certificateId,
      CertificateRepository certificateRepository) {
    if (certificateId == null) {
      throw new IllegalArgumentException("Cannot get certificate if certificateId is null");
    }

    final var certificateEntity = certificateEntityRepository.findByCertificateId(
            certificateId.id())
        .orElseThrow(() ->
            new IllegalArgumentException(
                "CertificateId '%s' not present in repository".formatted(certificateId)
            )
        );

    return certificateEntityMapper.toDomain(certificateEntity, certificateRepository);
  }


  public List<Certificate> getByIds(List<CertificateId> certificateIds,
      CertificateRepository certificateRepository) {
    if (certificateIds == null || certificateIds.isEmpty()) {
      throw new IllegalArgumentException(
          "Cannot get certificate if certificateIds is null or empty '%s'".formatted(
              certificateIds)
      );
    }

    final var certificateEntities = certificateEntityRepository.findCertificateEntitiesByCertificateIdIn(
        certificateIds.stream()
            .map(CertificateId::id)
            .toList()
    );

    if (certificateEntities.size() != certificateIds.size()) {
      throw new IllegalStateException(
          "Missing certificate for ids '%s'".formatted(
              certificateIds.stream()
                  .map(CertificateId::id)
                  .filter(
                      certificateId -> certificateEntities.stream()
                          .noneMatch(entity -> entity.getCertificateId().equals(certificateId))
                  )
                  .toList()
          )
      );
    }

    return certificateEntities.stream()
        .map(certificateEntity -> certificateEntityMapper.toDomain(certificateEntity,
            certificateRepository))
        .toList();
  }


  public List<Certificate> findByIds(List<CertificateId> certificateIds,
      CertificateRepository certificateRepository) {
    if (certificateIds == null) {
      throw new IllegalArgumentException(
          "Cannot get certificate if certificateIds is null"
      );
    }

    if (certificateIds.isEmpty()) {
      return Collections.emptyList();
    }

    final var certificateEntities = certificateEntityRepository.findCertificateEntitiesByCertificateIdIn(
        certificateIds.stream()
            .map(CertificateId::id)
            .toList()
    );

    return certificateEntities.stream()
        .map(certificateEntity -> certificateEntityMapper.toDomain(certificateEntity,
            certificateRepository))
        .toList();
  }


  public boolean exists(CertificateId certificateId) {
    if (certificateId == null) {
      throw new IllegalArgumentException(
          "Cannot check if certificate exists since certificateId is null");
    }

    return certificateEntityRepository.findByCertificateId(certificateId.id()).isPresent();
  }


  public List<Certificate> findByCertificatesRequest(CertificatesRequest request,
      CertificateRepository certificateRepository) {
    final var specification = certificateEntitySpecificationFactory.create(request);

    return certificateEntityRepository.findAll(specification).stream()
        .map(certificateEntity -> certificateEntityMapper.toDomain(certificateEntity,
            certificateRepository))
        .toList();
  }

  public List<CertificateId> findIdsByCreatedBeforeAndStatusIn(CertificatesRequest request) {
    return certificateEntityRepository.findCertificateIdsByCreatedBeforeAndStatusIn(
        request.createdTo(),
        request.statuses().stream()
            .map(status -> CertificateStatus.valueOf(status.name()).getKey())
            .toList()
    ).stream().map(CertificateId::new).toList();
  }

  public CertificateExportPage getExportByCareProviderId(HsaId careProviderId, int page, int size,
      CertificateRepository certificateRepository) {
    if (careProviderId == null) {
      throw new IllegalArgumentException("Cannot get certificates if careProviderId is null");
    }

    final var pageable = PageRequest.of(page, size,
        Sort.by(Direction.ASC, "signed", "certificateId"));

    final var certificateEntitiesPage = certificateEntityRepository.findSignedCertificateEntitiesByCareProviderHsaId(
        careProviderId.id(), pageable
    );

    final var revokedCertificatesOnCareProvider = certificateEntityRepository.findRevokedCertificateEntitiesByCareProviderHsaId(
        careProviderId.id()
    );

    return CertificateExportPage.builder()
        .total(certificateEntitiesPage.getTotalElements())
        .totalRevoked(revokedCertificatesOnCareProvider)
        .certificates(
            certificateEntitiesPage.getContent().stream()
                .map(certificateEntity -> certificateEntityMapper.toDomain(certificateEntity,
                    certificateRepository))
                .toList()
        )
        .build();
  }


  public long deleteByCareProviderId(HsaId careProviderId) {
    if (careProviderId == null) {
      throw new IllegalArgumentException("Cannot delete certificates if careProviderId is null");
    }

    final var pageable = PageRequest.of(0, eraseCertificatesPageSize,
        Sort.by(Direction.ASC, "signed", "certificateId"));
    Page<CertificateEntity> certificateEntitiesPage;

    do {
      certificateEntitiesPage = certificateEntityRepository.findCertificateEntitiesByCareProviderHsaId(
          careProviderId.id(), pageable
      );

      final var certificateEntities = certificateEntitiesPage.getContent();

      certificateEntities.forEach(certificateRelationRepository::deleteRelations);

      certificateEntityRepository.deleteAllByCertificateIdIn(
          certificateEntities.stream()
              .map(CertificateEntity::getCertificateId)
              .toList()
      );

    } while (certificateEntitiesPage.hasNext());

    return certificateEntitiesPage.getTotalElements();
  }


  public boolean placeholderExists(CertificateId certificateId) {
    if (certificateId == null) {
      throw new IllegalArgumentException(
          "Cannot check if placeholder certificate exists since certificateId is null");
    }

    return certificateEntityRepository.findPlaceholderByCertificateId(certificateId.id())
        .isPresent();
  }

  public PlaceholderCertificate getPlaceholderById(CertificateId certificateId) {
    if (certificateId == null) {
      throw new IllegalArgumentException(
          "Cannot get placeholder certificate if certificateId is null");
    }

    final var certificateEntity = certificateEntityRepository.findPlaceholderByCertificateId(
            certificateId.id())
        .orElseThrow(() ->
            new IllegalArgumentException(
                "CertificateId '%s' not present in repository".formatted(certificateId)
            )
        );

    return placeholderCertificateEntityMapper.toDomain(certificateEntity);
  }

  public PlaceholderCertificate save(PlaceholderCertificate placeholderCertificate) {
    if (placeholderCertificate == null) {
      throw new IllegalArgumentException(
          "Unable to save, placeholderCertificate was null"
      );
    }

    final var certificateEntity = certificateEntityRepository.save(
        placeholderCertificateEntityMapper.toEntity(placeholderCertificate)
    );

    return placeholderCertificateEntityMapper.toDomain(certificateEntity);
  }

  public Certificate insert(Certificate certificate, Revision revision,
      CertificateRepository certificateRepository) {
    final var entity = certificateEntityMapper.toEntity(certificate);
    entity.setRevision(revision.value());

    final var savedEntity = certificateEntityRepository.save(entity);

    certificateRelationRepository.save(
        certificate, savedEntity
    );

    return certificateEntityMapper.toDomain(savedEntity, certificateRepository);
  }

  public void remove(List<CertificateId> certificateIds) {
    certificateIds.stream()
        .map(certificateId -> certificateEntityRepository.findByCertificateId(certificateId.id()))
        .filter(Optional::isPresent)
        .map(Optional::get)
        .forEach(certificateRelationRepository::deleteRelations);

    certificateEntityRepository.deleteAllByCertificateIdIn(
        certificateIds.stream()
            .map(CertificateId::id)
            .toList()
    );
  }

  public CertificateMetaData getMetadataFromSignInstance(CertificateMetaData metadata,
      LocalDateTime signedAt) {

    if (signedAt == null) {
      throw new IllegalStateException(
          "SignedAt is required to fetch metadata from signed instance");
    }

    final var patientWhenSigned = getPatientVersionWhenSigned(metadata.patient(),
        signedAt);
    final var staffMap = getStaffVersionsWhenSigned(
        List.of(metadata.issuer(), metadata.creator()), signedAt);
    final var unitMap = getUnitVersionsWhenSigned(
        List.of(metadata.careProvider().hsaId().id(),
            metadata.careUnit().hsaId().id(),
            metadata.issuingUnit().hsaId().id()
        ),
        signedAt);

    return CertificateMetaData.builder()
        .patient(patientWhenSigned)
        .issuer(staffMap.get(metadata.issuer().hsaId().id()))
        .creator(staffMap.get(metadata.creator().hsaId().id()))
        .issuingUnit(
            unitMap.containsKey(metadata.issuingUnit().hsaId().id())
                ? UnitEntityMapper.toIssuingUnitDomain(
                unitMap.get(metadata.issuingUnit().hsaId().id()))
                : metadata.issuingUnit()
        )
        .careProvider(
            unitMap.containsKey(metadata.careProvider().hsaId().id())
                ? UnitEntityMapper.toCareProviderDomain(
                unitMap.get(metadata.careProvider().hsaId().id()))
                : metadata.careProvider()
        )
        .careUnit(
            unitMap.containsKey(metadata.careUnit().hsaId().id())
                ? UnitEntityMapper.toCareUnitDomain(unitMap.get(metadata.careUnit().hsaId().id()))
                : metadata.careUnit()
        )
        .build();
  }

  private Patient getPatientVersionWhenSigned(Patient patient,
      LocalDateTime signedAt) {
    return patientVersionEntityRepository
        .findFirstCoveringTimestampOrderByMostRecent(patient.id().idWithoutDash(), signedAt)
        .map(PatientVersionEntityMapper::toPatient)
        .map(PatientEntityMapper::toDomain)
        .orElse(patient);
  }

  private Map<String, Staff> getStaffVersionsWhenSigned(List<Staff> staffs,
      LocalDateTime signedAt) {
    List<StaffVersionEntity> entities = staffVersionEntityRepository
        .findAllCoveringTimestampByHsaIdIn(
            staffs.stream().map(s -> s.hsaId().id()).toList(), signedAt);

    Map<String, Staff> versionedStaffMap = entities.stream()
        .map(StaffVersionEntityMapper::toStaff)
        .map(StaffEntityMapper::toDomain)
        .collect(Collectors.toMap(s -> s.hsaId().id(), Function.identity(), (a, b) -> a));

    return staffs.stream()
        .collect(Collectors.toMap(
            s -> s.hsaId().id(),
            s -> versionedStaffMap.getOrDefault(s.hsaId().id(), s),
            (a, b) -> a
        ));
  }


  private Map<String, UnitEntity> getUnitVersionsWhenSigned(List<String> unitHsaIds,
      LocalDateTime signedAt) {
    List<UnitVersionEntity> entities =
        unitVersionEntityRepository.findAllCoveringTimestampByHsaIdIn(unitHsaIds, signedAt);
    return entities.stream()
        .map(UnitVersionEntityMapper::toUnit)
        .collect(Collectors.toMap(UnitEntity::getHsaId, Function.identity(), (a, b) -> a));
  }

  public List<CertificateId> findValidSickLeavesByIds(List<CertificateId> certificateId) {
    return certificateEntityRepository.findValidSickLeaveCertificatesByIds(
            certificateId.stream()
                .map(CertificateId::id)
                .toList()
        )
        .stream()
        .map(CertificateId::new)
        .toList();
  }

  public void updateCertificateMetadataFromSignInstances(List<Certificate> certificates) {
    if (certificates == null || certificates.isEmpty()) {
      return;
    }

    final var staffHsaIds = new HashSet<String>();
    final var unitHsaIds = new HashSet<String>();
    final var patientIds = new HashSet<String>();

    certificates.forEach(certificate -> {
      final var metadata = certificate.certificateMetaData();
      patientIds.add(metadata.patient().id().idWithoutDash());
      staffHsaIds.add(metadata.creator().hsaId().id());
      staffHsaIds.add(metadata.issuer().hsaId().id());
      unitHsaIds.add(metadata.careProvider().hsaId().id());
      unitHsaIds.add(metadata.careUnit().hsaId().id());
      unitHsaIds.add(metadata.issuingUnit().hsaId().id());
    });

    final var patientVersions = patientVersionEntityRepository
        .findAllByIdIn(patientIds.stream().toList());

    final var staffVersions = staffVersionEntityRepository
        .findAllByHsaIdIn(staffHsaIds.stream().toList());

    final var unitVersions = unitVersionEntityRepository
        .findAllByHsaIdIn(unitHsaIds.stream().toList());

    certificates.forEach(certificate -> {
      final var signedAt = certificate.signed();

      if (signedAt != null) {
        final var metadata = certificate.certificateMetaData();

        final var patientWhenSigned = patientVersions.stream()
            .filter(pv -> pv.getId().equals(
                metadata.patient().id().idWithoutDash()))
            .filter(pv -> (pv.getValidFrom() == null || !pv.getValidFrom().isAfter(signedAt))
                && pv.getValidTo().isAfter(signedAt))
            .findFirst()
            .map(PatientVersionEntityMapper::toPatient)
            .map(PatientEntityMapper::toDomain)
            .orElse(metadata.patient());

        final var staffMap = staffVersions.stream()
            .filter(sv -> sv.getHsaId().equals(metadata.issuer().hsaId().id())
                || sv.getHsaId().equals(metadata.creator().hsaId().id()))
            .filter(sv -> (sv.getValidFrom() == null || !sv.getValidFrom().isAfter(signedAt))
                && sv.getValidTo().isAfter(signedAt))
            .map(StaffVersionEntityMapper::toStaff)
            .map(StaffEntityMapper::toDomain)
            .collect(Collectors.toMap(s -> s.hsaId().id(), Function.identity(), (a, b) -> a));

        final var unitMap = unitVersions.stream()
            .filter(uv -> uv.getHsaId().equals(metadata.careProvider().hsaId().id())
                || uv.getHsaId().equals(metadata.careUnit().hsaId().id())
                || uv.getHsaId().equals(metadata.issuingUnit().hsaId().id()))
            .filter(uv -> (uv.getValidFrom() == null || !uv.getValidFrom().isAfter(signedAt))
                && uv.getValidTo().isAfter(signedAt))
            .map(UnitVersionEntityMapper::toUnit)
            .collect(Collectors.toMap(UnitEntity::getHsaId, Function.identity(), (a, b) -> a));

        certificate.updateMetadata(
            CertificateMetaData.builder()
                .issuer(staffMap.getOrDefault(metadata.issuer().hsaId().id(), metadata.issuer()))
                .creator(staffMap.getOrDefault(metadata.creator().hsaId().id(), metadata.creator()))
                .careProvider(
                    unitMap.containsKey(metadata.careProvider().hsaId().id())
                        ? UnitEntityMapper.toCareProviderDomain(
                        unitMap.get(metadata.careProvider().hsaId().id()))
                        : metadata.careProvider()
                )
                .careUnit(
                    unitMap.containsKey(metadata.careUnit().hsaId().id())
                        ? UnitEntityMapper.toCareUnitDomain(
                        unitMap.get(metadata.careUnit().hsaId().id()))
                        : metadata.careUnit()
                )
                .issuingUnit(
                    unitMap.containsKey(metadata.issuingUnit().hsaId().id())
                        ? UnitEntityMapper.toIssuingUnitDomain(
                        unitMap.get(metadata.issuingUnit().hsaId().id()))
                        : metadata.issuingUnit()
                )
                .patient(patientWhenSigned)
                .build()
        );

      }
    });
  }
}