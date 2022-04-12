package se.inera.intyg.cts.domain.repository;

import se.inera.intyg.cts.domain.model.CertificateBatch;
import se.inera.intyg.cts.domain.model.Termination;

public class InMemoryCertificateBatchRepository implements CertificateBatchRepository {

  private CertificateBatch certificateBatch;

  @Override
  public CertificateBatch nextBatch(Termination termination) {
    return certificateBatch;
  }

  public void prepare(CertificateBatch certificateBatch) {
    this.certificateBatch = certificateBatch;
  }
}
