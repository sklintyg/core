package se.inera.intyg.certificateservice.domain.certificatemodel.repository;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.unitaccess.dto.CertificateAccessConfiguration;

public interface CertificateUnitAccessEvaluationRepository {

  List<CertificateAccessConfiguration> get(CertificateType certificateType);
}