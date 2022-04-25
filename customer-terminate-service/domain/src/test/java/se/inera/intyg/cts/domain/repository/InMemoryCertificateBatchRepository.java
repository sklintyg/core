package se.inera.intyg.cts.domain.repository;

import java.util.List;
import se.inera.intyg.cts.domain.model.CertificateBatch;
import se.inera.intyg.cts.domain.model.CertificateText;
import se.inera.intyg.cts.domain.model.Termination;

public class InMemoryCertificateBatchRepository implements CertificateBatchRepository {

  private CertificateBatch certificateBatch;
  private List<CertificateText> certificateTexts;

  @Override
  public CertificateBatch nextBatch(Termination termination) {
    return certificateBatch;
  }

  @Override
  public List<CertificateText> certificateTexts(Termination termination) {
    return certificateTexts;
  }

  public void prepare(CertificateBatch certificateBatch) {
    this.certificateBatch = certificateBatch;
  }

  public void prepare(List<CertificateText> certificateTexts) {
    this.certificateTexts = certificateTexts;
  }
}
