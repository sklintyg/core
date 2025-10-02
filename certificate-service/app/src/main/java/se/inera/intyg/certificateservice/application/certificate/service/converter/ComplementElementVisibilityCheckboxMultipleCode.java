package se.inera.intyg.certificateservice.application.certificate.service.converter;

import static se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementType.CHECKBOX_MULTIPLE_CODE;

import java.util.ArrayList;
import java.util.Map;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDataElement;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueCode;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueCodeList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementVisibilityConfiguration;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementVisibilityConfigurationsCheckboxMultipleCode;

@Component
public class ComplementElementVisibilityCheckboxMultipleCode implements
    ComplementElementVisibility {

  @Override
  public boolean supports(ElementVisibilityConfiguration elementVisibilityConfiguration) {
    return elementVisibilityConfiguration.type().equals(CHECKBOX_MULTIPLE_CODE);
  }

  @Override
  public void handle(Map<String, CertificateDataElement> dataElementMap,
      ElementVisibilityConfiguration visibilityConfiguration) {
    if (!(visibilityConfiguration instanceof ElementVisibilityConfigurationsCheckboxMultipleCode visibilityConfigurationsCodeList)) {
      throw new IllegalStateException(
          "visibilityConfiguration is not of type ElementVisibilityConfigurationsCodeList"
      );
    }

    final var certificateDataElement = dataElementMap.get(
        visibilityConfigurationsCodeList.elementId().id()
    );
    final var value = (CertificateDataValueCodeList) certificateDataElement.getValue();
    final var certificateDataValueCodes = new ArrayList<>(value.getList());
    final var certificateDataValueCode = certificateDataValueCode(visibilityConfigurationsCodeList);

    if (certificateDataValueCodes.contains(certificateDataValueCode)) {
      return;
    }

    certificateDataValueCodes.add(certificateDataValueCode);

    final var certificateDataValueCodeList = value.withList(certificateDataValueCodes);
    final var certificateDataElementWithUpdatedValue = certificateDataElement.withValue(
        certificateDataValueCodeList
    );

    dataElementMap.put(
        visibilityConfigurationsCodeList.elementId().id(),
        certificateDataElementWithUpdatedValue
    );
  }

  private static CertificateDataValueCode certificateDataValueCode(
      ElementVisibilityConfigurationsCheckboxMultipleCode visibilityConfigurationsCodeList) {
    return CertificateDataValueCode.builder()
        .id(visibilityConfigurationsCodeList.fieldId().value())
        .code(visibilityConfigurationsCodeList.fieldId().value())
        .build();
  }
}