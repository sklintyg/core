package se.inera.intyg.certificateservice.application.certificate.service.converter;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfig;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfigCheckboxDateRangeList;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CheckboxDateRangeConfig;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxDateRangeList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementType;

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
            .label(configuration.label())
            .hideWorkingHours(configuration.hideWorkingHours())
            .previousDateRangeText(configuration.previousDateRangeText())
            .min(date(configuration.min()))
            .max(date(configuration.max()))
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

    private static LocalDate date(Period period) {
        return period == null ? null : LocalDate.now(ZoneId.systemDefault()).plus(period);
    }
}
