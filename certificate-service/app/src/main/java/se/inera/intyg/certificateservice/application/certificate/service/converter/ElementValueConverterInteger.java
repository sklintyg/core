package se.inera.intyg.certificateservice.application.certificate.service.converter;

import static se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueType.INTEGER;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValue;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueInteger;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueType;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueInteger;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

@Component
public class ElementValueConverterInteger implements ElementValueConverter {

  @Override
  public CertificateDataValueType getType() {
    return INTEGER;
  }

  @Override
  public ElementValue convert(CertificateDataValue value) {
    if (!(value instanceof CertificateDataValueInteger valueInteger)) {
      throw new IllegalStateException(
          "Invalid value type. Type was '%s'".formatted(value.getType())
      );
    }
    return ElementValueInteger.builder()
        .integerId(new FieldId(valueInteger.getId()))
        .value(valueInteger.getValue())
        .unitOfMeasurement(valueInteger.getUnitOfMeasurement())
        .build();
  }
}
