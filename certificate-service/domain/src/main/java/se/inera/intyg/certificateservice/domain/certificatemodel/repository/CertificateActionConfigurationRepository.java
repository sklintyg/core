package se.inera.intyg.certificateservice.domain.certificatemodel.repository;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.configuration.limitedcertificatefunctionality.dto.LimitedCertificateFunctionalityConfiguration;
import se.inera.intyg.certificateservice.domain.configuration.unitaccess.dto.CertificateAccessConfiguration;

public interface CertificateActionConfigurationRepository {

  List<CertificateAccessConfiguration> findAccessConfiguration(CertificateType certificateType);

  LimitedCertificateFunctionalityConfiguration findLimitedCertificateFunctionalityConfiguration(
      CertificateModelId certificateModelId);
}