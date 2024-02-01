package se.inera.intyg.certificateservice.domain.certificatemodel.repository;

import java.util.List;
import java.util.Optional;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;

public interface CertificateModelRepository {

  List<CertificateModel> findAllActive();

  Optional<CertificateModel> findLatestActiveByType(CertificateType certificateType);

  CertificateModel getById(CertificateModelId certificateModelId);
}
