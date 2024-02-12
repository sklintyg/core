package se.inera.intyg.certificateservice.application.certificate.service;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfig;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfigCategory;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfigDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

@Component
public class CertificateDataConfigConverter {

  public CertificateDataConfig convert(ElementSpecification elementSpecification) {
    return switch (elementSpecification.configuration().type()) {
      case CATEGORY -> CertificateDataConfigCategory.builder()
          .text(elementSpecification.configuration().name())
          .build();
      case DATE -> {
        final var configuration = (ElementConfigurationDate) elementSpecification.configuration();
        yield CertificateDataConfigDate.builder()
            .id(configuration.id())
            .text(configuration.name())
            .minDate(configuration.minDate())
            .maxDate(configuration.maxDate())
            .build();
      }
    };
  }
}
