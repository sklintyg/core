package se.inera.intyg.cts.domain.repository;

import se.inera.intyg.cts.domain.model.CertificateBatch;
import se.inera.intyg.cts.domain.model.Termination;

public interface CertificateBatchRepository {

  CertificateBatch nextBatch(Termination termination);
}
