package se.inera.intyg.certificateservice.domain.certificate.repository;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateExportPage;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PlaceholderCertificateRequest;
import se.inera.intyg.certificateservice.domain.common.model.CertificatesRequest;
import se.inera.intyg.certificateservice.domain.common.model.HsaId;

public interface CertificateRepository {

  Certificate create(CertificateModel certificateModel);

  Certificate createFromPlaceholder(PlaceholderCertificateRequest request, CertificateModel model);

  Certificate save(Certificate certificate);

  Certificate getById(CertificateId certificateId);

  List<Certificate> getByIds(List<CertificateId> certificateIds);

  List<Certificate> findByIds(List<CertificateId> certificateIds);

  boolean exists(CertificateId certificateId);

  List<Certificate> findByCertificatesRequest(CertificatesRequest request);

  CertificateExportPage getExportByCareProviderId(HsaId careProviderId, int page, int size);

  long deleteByCareProviderId(HsaId careProviderId);
}