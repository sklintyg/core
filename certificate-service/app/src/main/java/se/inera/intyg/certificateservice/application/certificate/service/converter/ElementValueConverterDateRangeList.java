package se.inera.intyg.certificateservice.application.certificate.service.converter;

import static se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueType.DATE_RANGE_LIST;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValue;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueDateRangeList;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueType;
import se.inera.intyg.certificateservice.domain.certificate.model.DateRange;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateRangeList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

@Component
@RequiredArgsConstructor
public class ElementValueConverterDateRangeList implements ElementValueConverter {

  @Override
  public CertificateDataValueType getType() {
    return DATE_RANGE_LIST;
  }

  @Override
  public ElementValue convert(CertificateDataValue value) {
    if (!(value instanceof CertificateDataValueDateRangeList dateRangeListValue)) {
      throw new IllegalStateException(
          "Invalid value type. Type was '%s'".formatted(value.getType())
      );
    }

    return ElementValueDateRangeList.builder()
        .dateRangeListId(new FieldId(dateRangeListValue.getId()))
        .dateRangeList(
            dateRangeListValue.getList().stream()
                .map(dateRange ->
                    DateRange.builder()
                        .dateRangeId(new FieldId(dateRange.getId()))
                        .from(dateRange.getFrom())
                        .to(dateRange.getTo())
                        .build()
                )
                .toList()
        )
        .build();
  }
}
