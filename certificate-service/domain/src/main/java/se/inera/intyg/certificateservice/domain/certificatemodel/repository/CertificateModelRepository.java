package se.inera.intyg.certificateservice.domain.certificatemodel.repository;

import java.util.List;
import java.util.Optional;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.common.model.Code;

public interface CertificateModelRepository {

  List<CertificateModel> findAllActive();

  Optional<CertificateModel> findLatestActiveByType(CertificateType certificateType);

  Optional<CertificateModel> findLatestActiveByExternalType(Code code);

  CertificateModel getById(CertificateModelId certificateModelId);

  CertificateModel getActiveById(CertificateModelId certificateModelId);
}