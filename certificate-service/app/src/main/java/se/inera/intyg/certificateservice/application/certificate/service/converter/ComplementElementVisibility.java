package se.inera.intyg.certificateservice.application.certificate.service.converter;

import java.util.Map;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDataElement;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementVisibilityConfiguration;

public interface ComplementElementVisibility {

  void handle(Map<String, CertificateDataElement> dataElementMap,
      ElementVisibilityConfiguration visibilityConfiguration);

  boolean supports(ElementVisibilityConfiguration elementVisibilityConfiguration);
}