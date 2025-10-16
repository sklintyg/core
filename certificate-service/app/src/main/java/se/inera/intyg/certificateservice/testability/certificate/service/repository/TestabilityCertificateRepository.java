package se.inera.intyg.certificateservice.testability.certificate.service.repository;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.Revision;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;

public interface TestabilityCertificateRepository extends CertificateRepository {

  Certificate insert(Certificate certificate, Revision revision);

  void remove(List<CertificateId> certificateIds);
}
