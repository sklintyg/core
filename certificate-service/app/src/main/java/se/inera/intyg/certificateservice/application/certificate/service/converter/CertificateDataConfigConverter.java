package se.inera.intyg.certificateservice.application.certificate.service.converter;

import static se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementType.CATEGORY;
import static se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementType.DATE;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfig;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfigCategory;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfigDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

@Component
public class CertificateDataConfigConverter {

  public CertificateDataConfig convert(ElementSpecification elementSpecification) {
    if (CATEGORY.equals(elementSpecification.configuration().type())) {
      return CertificateDataConfigCategory.builder()
          .text(elementSpecification.configuration().name())
          .build();
    }
    if (DATE.equals(elementSpecification.configuration().type())) {
      final var configuration = (ElementConfigurationDate) elementSpecification.configuration();
      return CertificateDataConfigDate.builder()
          .id(configuration.id().value())
          .text(configuration.name())
          .minDate(date(configuration.min()))
          .maxDate(date(configuration.max()))
          .build();
    }
    throw new IllegalStateException(
        "Config '%s' is not supported".formatted(elementSpecification.configuration().type())
    );
  }

  private static LocalDate date(Period period) {
    return period == null ? null : LocalDate.now(ZoneId.systemDefault()).plus(period);
  }
}
