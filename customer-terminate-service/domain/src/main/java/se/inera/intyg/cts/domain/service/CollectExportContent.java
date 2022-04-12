package se.inera.intyg.cts.domain.service;

import se.inera.intyg.cts.domain.model.Termination;
import se.inera.intyg.cts.domain.model.TerminationId;
import se.inera.intyg.cts.domain.repository.CertificateBatchRepository;
import se.inera.intyg.cts.domain.repository.CertificateRepository;
import se.inera.intyg.cts.domain.repository.TerminationRepository;

public class CollectExportContent {

  private final TerminationRepository terminationRepository;
  private final CertificateBatchRepository certificateBatchRepository;
  private final CertificateRepository certificateRepository;

  public CollectExportContent(TerminationRepository terminationRepository,
      CertificateBatchRepository certificateBatchRepository,
      CertificateRepository certificateRepository) {
    this.terminationRepository = terminationRepository;
    this.certificateBatchRepository = certificateBatchRepository;
    this.certificateRepository = certificateRepository;
  }

  public void collectCertificates(TerminationId terminationId) {
    final var termination = getTermination(terminationId);

    final var certificateBatch = certificateBatchRepository.nextBatch(termination);
    termination.collect(certificateBatch);

    certificateRepository.store(termination, certificateBatch.certificateList());
    terminationRepository.store(termination);
  }

  private Termination getTermination(TerminationId terminationId) {
    return terminationRepository.findByTerminationId(terminationId)
        .orElseThrow(() ->
            new IllegalArgumentException(String.format("Termination with id: '%s' doesn't exist!"))
        );
  }
}
