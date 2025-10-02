package se.inera.intyg.certificateservice.application.certificate.service.converter;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDataElement;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementVisibilityConfiguration;

@Service
@RequiredArgsConstructor
public class HandleComplementElementVisibilityService {

  private final List<ComplementElementVisibility> complementElementVisibility;

  public void handle(ElementId complementElementId,
      Map<String, CertificateDataElement> dataElementMap,
      Certificate certificate, ElementVisibilityConfiguration visibilityConfiguration) {
    if (elementIsVisible(certificate, complementElementId)) {
      return;
    }

    final var service = complementElementVisibility.stream()
        .filter(visibilityService -> visibilityService.supports(visibilityConfiguration))
        .findFirst()
        .orElseThrow(() -> new IllegalStateException(
            "No ComplementElementVisibility found for visibility configuration: %s"
                .formatted(visibilityConfiguration.type())));

    service.handle(dataElementMap, visibilityConfiguration);
  }

  private static boolean elementIsVisible(Certificate certificate, ElementId elementId) {
    final var shouldValidate = certificate.certificateModel()
        .elementSpecification(elementId)
        .shouldValidate();

    if (shouldValidate == null) {
      return true;
    }

    return shouldValidate.test(certificate.elementData());
  }
}