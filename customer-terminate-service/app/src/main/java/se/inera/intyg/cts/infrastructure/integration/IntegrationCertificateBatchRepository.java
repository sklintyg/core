package se.inera.intyg.cts.infrastructure.integration;

import se.inera.intyg.cts.domain.model.CertificateBatch;
import se.inera.intyg.cts.domain.model.Termination;
import se.inera.intyg.cts.domain.repository.CertificateBatchRepository;

public class IntegrationCertificateBatchRepository implements CertificateBatchRepository {

  private final GetCertificateBatch getCertificateBatch;
  private final int batchSize;

  public IntegrationCertificateBatchRepository(GetCertificateBatch getCertificateBatch,
      int batchSize) {
    this.getCertificateBatch = getCertificateBatch;
    this.batchSize = batchSize;
  }

  @Override
  public CertificateBatch nextBatch(Termination termination) {
    return getCertificateBatch.get(termination.careProvider().hsaId().id(), batchSize,
        termination.export().certificateSummary().total());
  }
}
