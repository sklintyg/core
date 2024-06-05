package se.inera.intyg.certificateservice.application.certificate.service.converter;

import java.util.Collections;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValue;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueDate;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueDateList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxMultipleDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementType;

@Component
public class CertificateDataValueConverterCheckboxDateList implements
    CertificateDataValueConverter {

  @Override
  public ElementType getType() {
    return ElementType.CHECKBOX_MULTIPLE_DATE;
  }

  @Override
  public CertificateDataValue convert(ElementSpecification elementSpecification,
      ElementValue elementValue) {
    if (elementValue != null && !(elementValue instanceof ElementValueDateList)) {
      throw new IllegalStateException(
          "Invalid value type. Type was '%s'".formatted(elementValue.getClass())
      );
    }

    if (!(elementSpecification.configuration() instanceof ElementConfigurationCheckboxMultipleDate elementConfigurationCheckboxMultipleDate)) {
      throw new IllegalStateException(
          "Invalid configuration type. Type was '%s'".formatted(
              elementSpecification.configuration().type())
      );
    }

    return CertificateDataValueDateList.builder()
        .id(elementConfigurationCheckboxMultipleDate.id().value())
        .list(
            isValueDefined(elementValue)
                ? ((ElementValueDateList) elementValue).dateList()
                .stream()
                .map(date ->
                    CertificateDataValueDate.builder()
                        .id(date.dateId().value())
                        .date(date.date())
                        .build()
                ).toList()
                : Collections.emptyList())
        .build();
  }

  private static boolean isValueDefined(ElementValue elementValue) {
    if (elementValue == null) {
      return false;
    }

    final var value = (ElementValueDateList) elementValue;

    return value.dateList() != null && !value.dateList().isEmpty();
  }
}
