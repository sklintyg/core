package se.inera.intyg.certificateservice.domain.certificate.repository;

import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;

public interface CertificateRepository {

  Certificate create(CertificateModel certificateModel);
}
