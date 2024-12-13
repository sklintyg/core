package se.inera.intyg.certificateservice.certificate.converter;

import static se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationUnitContactInformation.UNIT_CONTACT_INFORMATION;

import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValueList;

@Component
public class PrintCertificateUnitInformationConverter {

  public List<String> convert(Certificate certificate) {
    final var elementData = certificate.getElementDataById(UNIT_CONTACT_INFORMATION);

    if (elementData.isEmpty()) {
      return Collections.emptyList();
    }

    final var simplifiedValue = certificate.certificateModel()
        .elementSpecification(UNIT_CONTACT_INFORMATION)
        .configuration()
        .simplified(elementData.get().value());

    if (simplifiedValue.isEmpty()) {
      return Collections.emptyList();
    }

    if (!(simplifiedValue.get() instanceof ElementSimplifiedValueList elementSimplifiedValueList)) {
      throw new IllegalStateException(
          "Wrong format of simplified value for unit info expecting List");
    }

    return elementSimplifiedValueList.list();
  }

}
