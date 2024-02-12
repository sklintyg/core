package se.inera.intyg.certificateservice.application.certificate.service;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValue;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

@Component
public class CertificateDataValueConverter {

  public CertificateDataValue convert(ElementSpecification elementSpecification,
      ElementValue elementValue) {
    return switch (elementSpecification.configuration().type()) {
      case CATEGORY -> null;
      case DATE -> {
        final var value = elementValue != null ? ((ElementValueDate) elementValue).date() : null;
        final var configuration = (ElementConfigurationDate) elementSpecification.configuration();
        yield CertificateDataValueDate.builder()
            .id(configuration.id())
            .date(value)
            .build();
      }
    };
  }
}
