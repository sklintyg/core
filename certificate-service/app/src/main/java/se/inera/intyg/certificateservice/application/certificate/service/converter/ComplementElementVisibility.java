package se.inera.intyg.certificateservice.application.certificate.service.converter;

import java.util.Map;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDataElement;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementVisibilityConfiguration;

/**
 * Interface for handling visibility of "complement" elements.
 *
 * <p>A complement element is an element that should be included in the certificate data
 * when the visibility rules (described by an {@link ElementVisibilityConfiguration}) indicate it is
 * relevant even though the original/parent element may not be visible.
 */
public interface ComplementElementVisibility {

  void handle(Map<String, CertificateDataElement> dataElementMap,
      ElementVisibilityConfiguration visibilityConfiguration);

  boolean supports(ElementVisibilityConfiguration elementVisibilityConfiguration);
}