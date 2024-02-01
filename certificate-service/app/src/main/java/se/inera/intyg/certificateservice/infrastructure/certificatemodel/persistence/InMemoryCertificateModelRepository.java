package se.inera.intyg.certificateservice.infrastructure.certificatemodel.persistence;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateModelRepository;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.CertificateModelFactory;

@Repository
@RequiredArgsConstructor
@Slf4j
public class InMemoryCertificateModelRepository implements CertificateModelRepository {

  private final List<CertificateModelFactory> certificateModelFactories;
  private Map<CertificateModelId, CertificateModel> certificateModelMap;

  @Override
  public List<CertificateModel> findAllActive() {
    return getCertificateModelMap().values().stream()
        .filter(filterActiveCertificateModels())
        .toList();
  }

  @Override
  public Optional<CertificateModel> findLatestActiveByType(CertificateType certificateType) {
    if (certificateType == null) {
      throw new IllegalArgumentException("CertificateType is null!");
    }

    return getCertificateModelMap().values().stream()
        .filter(certificateModel -> certificateType.equals(certificateModel.getId().getType()))
        .filter(filterActiveCertificateModels())
        .max(Comparator.comparing(CertificateModel::getActiveFrom));
  }

  @Override
  public CertificateModel getById(CertificateModelId certificateModelId) {
    if (certificateModelId == null) {
      throw new IllegalArgumentException("CertificateModelId is null!");
    }

    final var certificateModel = getCertificateModelMap().get(certificateModelId);
    if (certificateModel == null) {
      throw new IllegalArgumentException(
          "CertificateModel missing: %s".formatted(certificateModelId)
      );
    }

    if (LocalDateTime.now(ZoneId.systemDefault()).isBefore(certificateModel.getActiveFrom())) {
      throw new IllegalArgumentException(
          "CertificateModel with id '%s' not active until '%s'".formatted(
              certificateModel.getId(),
              certificateModel.getActiveFrom()
          )
      );
    }

    return certificateModel;
  }

  private Map<CertificateModelId, CertificateModel> getCertificateModelMap() {
    if (certificateModelMap == null) {
      log.info("Initiate certificate model repository");
      certificateModelMap = new HashMap<>();
      certificateModelFactories.forEach(certificateModelFactory -> {
            final var certificateModel = certificateModelFactory.create();
            certificateModelMap.put(certificateModel.getId(), certificateModel);
            log.info("Loaded certificate model '{}' to repository", certificateModel.getId());
          }
      );
    }
    return certificateModelMap;
  }

  private static Predicate<CertificateModel> filterActiveCertificateModels() {
    return certificateModel ->
        certificateModel.getActiveFrom().isBefore(LocalDateTime.now(ZoneId.systemDefault()));
  }
}
