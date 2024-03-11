package se.inera.intyg.certificateservice.application.certificate.service.converter;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDataElement;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValue;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

@Component
public class ElementDataConverter {

  public ElementData convert(String questionId, CertificateDataElement certificateDataElement) {
    final var value = certificateDataElement.getValue();
    return ElementData.builder()
        .id(new ElementId(questionId))
        .value(convertValue(value))
        .build();
  }

  private ElementValue convertValue(CertificateDataValue value) {
    return switch (value.getType()) {
      case DATE -> {
        final var dateValue = (CertificateDataValueDate) value;
        yield ElementValueDate.builder()
            .dateId(new FieldId(dateValue.getId()))
            .date(dateValue.getDate())
            .build();
      }
    };
  }
}
