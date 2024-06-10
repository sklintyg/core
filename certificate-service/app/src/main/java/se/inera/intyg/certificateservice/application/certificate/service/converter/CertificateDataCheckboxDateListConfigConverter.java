package se.inera.intyg.certificateservice.application.certificate.service.converter;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfig;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfigCheckboxMultipleDate;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CheckboxMultipleDate;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxMultipleDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementType;

@Component
public class CertificateDataCheckboxDateListConfigConverter implements
    CertificateDataConfigConverter {

  @Override
  public ElementType getType() {
    return ElementType.CHECKBOX_MULTIPLE_DATE;
  }

  public CertificateDataConfig convert(ElementSpecification elementSpecification,
      Certificate certificate) {
    if (!(elementSpecification.configuration() instanceof ElementConfigurationCheckboxMultipleDate configuration)) {
      throw new IllegalStateException(
          "Invalid value type. Type was '%s'".formatted(elementSpecification.configuration().type())
      );
    }
    return CertificateDataConfigCheckboxMultipleDate.builder()
        .text(configuration.name())
        .label(configuration.label())
        .list(
            configuration.dates().stream()
                .map(dateRange ->
                    CheckboxMultipleDate.builder()
                        .id(dateRange.id().value())
                        .label(dateRange.label())
                        .build()
                )
                .toList()
        )
        .build();
  }
}
