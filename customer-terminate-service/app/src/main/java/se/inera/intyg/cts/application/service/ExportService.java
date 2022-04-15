package se.inera.intyg.cts.application.service;

import java.util.Arrays;
import se.inera.intyg.cts.domain.model.TerminationStatus;
import se.inera.intyg.cts.domain.repository.TerminationRepository;
import se.inera.intyg.cts.domain.service.CollectExportContent;

public class ExportService {

  private final TerminationRepository terminationRepository;
  private final CollectExportContent collectExportContent;

  public ExportService(TerminationRepository terminationRepository,
      CollectExportContent collectExportContent) {
    this.terminationRepository = terminationRepository;
    this.collectExportContent = collectExportContent;
  }

  public void collectCertificatesToExport() {
    final var terminations = terminationRepository.findByStatuses(
        Arrays.asList(TerminationStatus.CREATED, TerminationStatus.COLLECTING)
    );

    terminations.forEach(
        termination -> collectExportContent.collectCertificates(termination.terminationId())
    );
  }
}
