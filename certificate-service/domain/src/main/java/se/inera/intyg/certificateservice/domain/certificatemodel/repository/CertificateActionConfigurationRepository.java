package se.inera.intyg.certificateservice.domain.certificatemodel.repository;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.configuration.inactive.dto.CertificateInactiveConfiguration;
import se.inera.intyg.certificateservice.domain.configuration.unitaccess.dto.CertificateAccessConfiguration;

public interface CertificateActionConfigurationRepository {

  List<CertificateAccessConfiguration> findAccessConfiguration(CertificateType certificateType);

  List<CertificateInactiveConfiguration> findInactiveConfiguration(
      CertificateModelId certificateModelId);
}