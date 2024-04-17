package se.inera.intyg.certificateservice.application.certificate.service.converter;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfig;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfigCheckboxDateRangeList;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CheckboxDateRangeConfig;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxDateRangeList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.SubTextType;

@Component
public class CertificateDataCheckboxDateRangeListConfigConverter implements
    CertificateDataConfigConverter {

  @Override
  public ElementType getType() {
    return ElementType.CHECKBOX_DATE_RANGE_LIST;
  }

  public CertificateDataConfig convert(ElementSpecification elementSpecification) {
    if (!(elementSpecification.configuration() instanceof ElementConfigurationCheckboxDateRangeList configuration)) {
      throw new IllegalStateException(
          "Invalid value type. Type was '%s'".formatted(elementSpecification.configuration().type())
      );
    }
    return CertificateDataConfigCheckboxDateRangeList.builder()
        .text(configuration.name())
        .label(configuration.subText().value().getOrDefault(SubTextType.LABEL, null))
        .hideWorkingHours(configuration.hideWorkingHours())
        .previousDateRangeText(configuration.previousDateRangeText())
        .list(
            configuration.dateRanges().stream()
                .map(dateRange ->
                    CheckboxDateRangeConfig.builder()
                        .id(dateRange.id().value())
                        .label(dateRange.label())
                        .build()
                )
                .toList()
        )
        .build();
  }
}
