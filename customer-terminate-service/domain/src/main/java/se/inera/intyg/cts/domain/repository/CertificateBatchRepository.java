package se.inera.intyg.cts.domain.repository;

import java.util.List;
import se.inera.intyg.cts.domain.model.CertificateBatch;
import se.inera.intyg.cts.domain.model.CertificateText;
import se.inera.intyg.cts.domain.model.Termination;

public interface CertificateBatchRepository {

  CertificateBatch nextBatch(Termination termination);

  List<CertificateText> certificateTexts(Termination termination);
}
