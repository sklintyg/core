package se.inera.intyg.cts.infrastructure.integration;

import java.util.List;
import se.inera.intyg.cts.domain.model.CertificateBatch;
import se.inera.intyg.cts.domain.model.CertificateText;
import se.inera.intyg.cts.domain.model.Termination;
import se.inera.intyg.cts.domain.repository.CertificateBatchRepository;

public class IntegrationCertificateBatchRepository implements CertificateBatchRepository {

  private final GetCertificateBatch getCertificateBatch;
  private final int batchSize;
  private final GetCertificateTexts getCertificateTexts;

  public IntegrationCertificateBatchRepository(GetCertificateBatch getCertificateBatch,
      int batchSize, GetCertificateTexts getCertificateTexts) {
    this.getCertificateBatch = getCertificateBatch;
    this.batchSize = batchSize;
    this.getCertificateTexts = getCertificateTexts;
  }

  @Override
  public CertificateBatch nextBatch(Termination termination) {
    return getCertificateBatch.get(termination.careProvider().hsaId().id(), batchSize,
        termination.export().certificateSummary().total());
  }

  @Override
  public List<CertificateText> certificateTexts(Termination termination) {
    return getCertificateTexts.get();
  }
}
