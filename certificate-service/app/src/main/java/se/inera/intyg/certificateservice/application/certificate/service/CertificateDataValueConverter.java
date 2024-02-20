package se.inera.intyg.certificateservice.application.certificate.service;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValue;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementType;

@Component
public class CertificateDataValueConverter {

  public CertificateDataValue convert(ElementSpecification elementSpecification,
      ElementValue elementValue) {
    if (ElementType.DATE.equals(elementSpecification.configuration().type())) {
      final var value = elementValue != null ? ((ElementValueDate) elementValue).date() : null;
      final var configuration = (ElementConfigurationDate) elementSpecification.configuration();
      return CertificateDataValueDate.builder()
          .id(configuration.id().value())
          .date(value)
          .build();
    }
    if (ElementType.CATEGORY.equals(elementSpecification.configuration().type())) {
      return null;
    }
    throw new IllegalStateException(
        "Config '%s' is not supported ".formatted(elementSpecification.configuration().type())
    );
  }
}
