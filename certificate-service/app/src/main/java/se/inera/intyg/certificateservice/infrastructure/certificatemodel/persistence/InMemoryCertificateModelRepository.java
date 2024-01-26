package se.inera.intyg.certificateservice.infrastructure.certificatemodel.persistence;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
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
        .filter(certificateModel ->
            certificateModel.getActiveFrom().isBefore(LocalDateTime.now(ZoneId.systemDefault()))
        )
        .toList();
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
}
