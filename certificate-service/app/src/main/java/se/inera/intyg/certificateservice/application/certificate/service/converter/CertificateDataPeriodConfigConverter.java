package se.inera.intyg.certificateservice.application.certificate.service.converter;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfig;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfigPeriod;
import se.inera.intyg.certificateservice.application.certificate.dto.config.DatePeriodDate;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationPeriod;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementType;

@Component
public class CertificateDataPeriodConfigConverter implements CertificateDataConfigConverter {

  @Override
  public ElementType getType() {
    return ElementType.PERIOD;
  }

  public CertificateDataConfig convert(ElementSpecification elementSpecification,
      Certificate certificate) {
    if (!(elementSpecification.configuration() instanceof ElementConfigurationPeriod configuration)) {
      throw new IllegalStateException(
          "Invalid config type. Type was '%s'".formatted(
              elementSpecification.configuration().type())
      );
    }

    return CertificateDataConfigPeriod.builder()
        .id(elementSpecification.id().id())
        .text(configuration.name())
        .fromDate(DatePeriodDate.builder()
            .label(configuration.fromDate().label())
            .minDate(date(configuration.fromDate().minDate()))
            .maxDate(date(configuration.fromDate().maxDate()))
            .build())
        .toDate(DatePeriodDate.builder()
            .label(configuration.toDate().label())
            .minDate(date(configuration.toDate().minDate()))
            .maxDate(date(configuration.toDate().maxDate()))
            .build())
        .build();
  }

  private static LocalDate date(Period period) {
    return period == null ? null : LocalDate.now(ZoneId.systemDefault()).plus(period);
  }
}
