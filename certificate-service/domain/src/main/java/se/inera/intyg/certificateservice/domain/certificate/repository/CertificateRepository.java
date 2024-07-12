package se.inera.intyg.certificateservice.domain.certificate.repository;

import java.time.LocalDateTime;
import java.util.List;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.common.model.CertificatesRequest;

public interface CertificateRepository {

  Certificate create(CertificateModel certificateModel);

  Certificate save(Certificate certificate);

  Certificate getById(CertificateId certificateId);

  List<Certificate> getByIds(List<CertificateId> certificateIds);

  boolean exists(CertificateId certificateId);

  List<Certificate> findByCertificatesRequest(CertificatesRequest request);

  List<Certificate> draftsCreatedBefore(LocalDateTime cutoffDate);
}
