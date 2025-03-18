package se.inera.intyg.certificateservice.infrastructure.certificate.persistence;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateExportPage;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.Revision;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.common.model.CertificatesRequest;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.CertificateEntityMapper;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.CertificateEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.CertificateEntitySpecificationFactory;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.CertificateRelationRepository;
import se.inera.intyg.certificateservice.testability.certificate.service.repository.TestabilityCertificateRepository;

@Repository
@RequiredArgsConstructor
public class JpaCertificateRepository implements TestabilityCertificateRepository {

  private final CertificateEntityRepository certificateEntityRepository;
  private final CertificateEntityMapper certificateEntityMapper;
  private final CertificateEntitySpecificationFactory certificateEntitySpecificationFactory;
  private final CertificateRelationRepository certificateRelationRepository;

  @Override
  public Certificate create(CertificateModel certificateModel) {
    if (certificateModel == null) {
      throw new IllegalArgumentException("Unable to create, certificateModel was null");
    }

    return Certificate.builder()
        .id(new CertificateId(UUID.randomUUID().toString()))
        .created(LocalDateTime.now(ZoneId.systemDefault()))
        .certificateModel(certificateModel)
        .revision(new Revision(0))
        .build();
  }

  @Override
  public Certificate save(Certificate certificate) {
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

    return certificateEntityMapper.toDomain(savedEntity);
  }

  @Override
  public Certificate getById(CertificateId certificateId) {
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

    return certificateEntityMapper.toDomain(certificateEntity);
  }


  @Override
  public List<Certificate> getByIds(List<CertificateId> certificateIds) {
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
        .map(certificateEntityMapper::toDomain)
        .toList();
  }

  @Override
  public List<Certificate> findByIds(List<CertificateId> certificateIds) {
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
        .map(certificateEntityMapper::toDomain)
        .toList();
  }

  @Override
  public boolean exists(CertificateId certificateId) {
    if (certificateId == null) {
      throw new IllegalArgumentException(
          "Cannot check if certificate exists since certificateId is null");
    }

    return certificateEntityRepository.findByCertificateId(certificateId.id()).isPresent();
  }

  @Override
  public List<Certificate> findByCertificatesRequest(CertificatesRequest request) {
    final var specification = certificateEntitySpecificationFactory.create(request);

    return certificateEntityRepository.findAll(specification).stream()
        .map(certificateEntityMapper::toDomain)
        .toList();
  }

  @Override
  public CertificateExportPage getExportByCareProviderId(String careProviderId, int page, int size) {
    if (careProviderId == null) {
      throw new IllegalArgumentException("Cannot get certificates if careProviderId is null");
    }

    final var pageable = PageRequest.of(page, size, Sort.by(Direction.ASC, "signed", "certificateId"));

    final var certificateEntitiesPage = certificateEntityRepository.findCertificateEntitiesByCareProviderHsaId(
        careProviderId, pageable
    );

    final var revokedCertificatesOnCareProvider = certificateEntityRepository.findRevokedCertificateEntitiesByCareProviderHsaId(
        careProviderId
    );

    return CertificateExportPage.builder()
        .total(certificateEntitiesPage.getTotalElements())
        .totalRevoked(revokedCertificatesOnCareProvider)
        .certificates(
            certificateEntitiesPage.getContent().stream()
                .map(certificateEntityMapper::toDomain)
                .toList()
        )
        .build();
  }

  @Override
  public long deleteByCareProviderId(String careProviderId, int size) {
    if (careProviderId == null) {
      throw new IllegalArgumentException("Cannot delete certificates if careProviderId is null");
    }

    final var pageable = PageRequest.of(0, size, Sort.by(Direction.ASC, "signed", "certificateId"));
    Page<CertificateEntity> certificateEntitiesPage;

    do {
      certificateEntitiesPage = certificateEntityRepository.findCertificateEntitiesByCareProviderHsaId(
          careProviderId, pageable
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

  @Override
  public Certificate insert(Certificate certificate) {
    final var savedEntity = certificateEntityRepository.save(
        certificateEntityMapper.toEntity(certificate)
    );

    certificateRelationRepository.save(
        certificate, savedEntity
    );

    return certificateEntityMapper.toDomain(savedEntity);
  }

  @Override
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
}