package se.inera.intyg.cts.domain.service;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.inera.intyg.cts.domain.model.EraseService;
import se.inera.intyg.cts.domain.model.Termination;
import se.inera.intyg.cts.domain.model.TerminationStatus;
import se.inera.intyg.cts.domain.repository.CertificateBatchRepository;
import se.inera.intyg.cts.domain.repository.TerminationRepository;

public class EraseDataForCareProvider {

  private final static Logger LOG = LoggerFactory.getLogger(EraseDataForCareProvider.class);

  private final List<EraseDataInService> services;
  private final CertificateBatchRepository certificateBatchRepository;
  private final TerminationRepository terminationRepository;

  public EraseDataForCareProvider(List<EraseDataInService> services,
      CertificateBatchRepository certificateBatchRepository,
      TerminationRepository terminationRepository) {
    this.services = services;
    this.certificateBatchRepository = certificateBatchRepository;
    this.terminationRepository = terminationRepository;
  }

  public void erase(Termination termination) {
    if (termination.status().equals(TerminationStatus.START_ERASE)) {
      initializeErasingCareProvider(termination);
    } else {
      eraseCareProvider(termination);
    }

    terminationRepository.store(termination);
  }

  private void initializeErasingCareProvider(Termination termination) {
    if (verifyNoChangeSinceExport(termination)) {
      termination.eraseCancelled();
      return;
    }

    termination.startErase(
        services.stream()
            .map(eraseDataInService -> new EraseService(eraseDataInService.serviceId(), false))
            .collect(Collectors.toList())
    );
  }

  private boolean verifyNoChangeSinceExport(Termination termination) {
    final var certificateSummary = certificateBatchRepository.certificateSummary(termination);
    if (!certificateSummary.equals(termination.export().certificateSummary())) {
      LOG.error(String.format(
          "Certificates for termination '%s' has changed since export. Exported '%s' and current '%s'. Erase will be cancelled!",
          termination.terminationId().id(), termination.export().certificateSummary(),
          certificateSummary));
      return true;
    }
    return false;
  }

  private void eraseCareProvider(Termination termination) {
    services.stream()
        .filter(filterOutAlreadyErasedServices(termination))
        .forEach(eraseDataInService(termination));
  }

  private Predicate<EraseDataInService> filterOutAlreadyErasedServices(Termination termination) {
    return eraseDataInService ->
        termination.erase().eraseServices().stream()
            .anyMatch(
                eraseService -> eraseDataInService.serviceId().equals(eraseService.serviceId())
                    && !eraseService.erased()
            );
  }

  private Consumer<EraseDataInService> eraseDataInService(Termination termination) {
    return eraseDataInService -> {
      try {
        eraseDataInService.erase(termination.careProvider());
        termination.erased(eraseDataInService.serviceId());
        LOG.info(String.format("Erased care provider for termination '%s' in service '%s'",
            termination.terminationId().id(),
            eraseDataInService.serviceId().id()));
      } catch (EraseException e) {
        LOG.error(String.format(
            "Could not erase care provider for termination '%s' in service '%s' due to message '%s'",
            termination.terminationId().id(), eraseDataInService.serviceId().id(),
            e.getMessage()));
      }
    };
  }
}
