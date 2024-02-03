package se.inera.intyg.certificateservice.testability.certificate.service.repository;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateModelRepository;

public interface TestabilityCertificateModelRepository extends CertificateModelRepository {

  List<CertificateModel> all();
}
