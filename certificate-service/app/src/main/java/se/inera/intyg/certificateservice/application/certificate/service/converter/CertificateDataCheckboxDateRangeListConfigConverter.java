package se.inera.intyg.certificateservice.application.certificate.service.converter;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfig;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfigCheckboxDateRangeList;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CheckboxDateRange;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateRangeList;
import se.inera.intyg.certificateservice.domain.certificate.model.RelationType;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxDateRangeList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementType;

@Component
public class CertificateDataCheckboxDateRangeListConfigConverter implements
    CertificateDataConfigConverter {

  @Override
  public ElementType getType() {
    return ElementType.CHECKBOX_DATE_RANGE_LIST;
  }

  public CertificateDataConfig convert(ElementSpecification elementSpecification,
      Certificate certificate) {
    if (!(elementSpecification.configuration() instanceof ElementConfigurationCheckboxDateRangeList configuration)) {
      throw new IllegalStateException(
          "Invalid config type. Type was '%s'".formatted(
              elementSpecification.configuration().type())
      );
    }
    return CertificateDataConfigCheckboxDateRangeList.builder()
        .text(configuration.name())
        .description(configuration.description())
        .label(configuration.label())
        .hideWorkingHours(configuration.hideWorkingHours())
        .min(date(configuration.min()))
        .max(date(configuration.max()))
        .list(
            configuration.dateRanges().stream()
                .map(dateRange ->
                    CheckboxDateRange.builder()
                        .id(dateRange.id().value())
                        .label(dateRange.label())
                        .build()
                )
                .toList()
        )
        .previousDateRangeText(
            previousDateRangeText(elementSpecification, configuration, certificate)
        )
        .build();
  }

  private static LocalDate date(Period period) {
    return period == null ? null : LocalDate.now(ZoneId.systemDefault()).plus(period);
  }

  private String previousDateRangeText(ElementSpecification elementSpecification,
      ElementConfigurationCheckboxDateRangeList configuration, Certificate certificate) {
    if (isNotRenewingCertificate(certificate)) {
      return null;
    }

    if (!Status.DRAFT.equals(certificate.status())) {
      return null;
    }

    final var previousValue = certificate.parent().certificate()
        .getElementDataById(elementSpecification.id())
        .filter(elementData -> elementData.value() instanceof ElementValueDateRangeList)
        .map(elementData -> (ElementValueDateRangeList) elementData.value())
        .orElse(null);

    if (previousValue == null) {
      return null;
    }

    final var previousLastDateRange = previousValue.lastRange();
    if (previousLastDateRange == null) {
      return null;
    }

    return "På det ursprungliga intyget var slutdatumet för den sista perioden %s och omfattningen var %s."
        .formatted(
            previousLastDateRange.to(),
            configuration.checkboxDateRange(previousLastDateRange.dateRangeId())
                .map(ElementConfigurationCode::label)
                .orElse("<saknas>")
        );
  }

  private boolean isNotRenewingCertificate(Certificate certificate) {
    return certificate.parent() == null || !RelationType.RENEW.equals(certificate.parent().type());
  }
}
