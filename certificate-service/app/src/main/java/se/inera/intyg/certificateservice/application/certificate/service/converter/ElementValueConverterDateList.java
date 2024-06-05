package se.inera.intyg.certificateservice.application.certificate.service.converter;

import static se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueType.DATE_LIST;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValue;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueDateList;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueType;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

@Component
@RequiredArgsConstructor
public class ElementValueConverterDateList implements ElementValueConverter {

  @Override
  public CertificateDataValueType getType() {
    return DATE_LIST;
  }

  @Override
  public ElementValue convert(CertificateDataValue value) {
    if (!(value instanceof CertificateDataValueDateList certificateDataValueDates)) {
      throw new IllegalStateException(
          "Invalid value type. Type was '%s'".formatted(value.getType())
      );
    }

    return ElementValueDateList.builder()
        .dateListId(new FieldId(certificateDataValueDates.getId()))
        .dateList(
            certificateDataValueDates.getList().stream()
                .map(date ->
                    ElementValueDate.builder()
                        .dateId(new FieldId(date.getId()))
                        .date(date.getDate())
                        .build()
                )
                .toList()
        )
        .build();
  }
}
