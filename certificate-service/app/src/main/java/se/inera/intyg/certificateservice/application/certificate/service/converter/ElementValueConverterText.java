package se.inera.intyg.certificateservice.application.certificate.service.converter;

import static se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueType.TEXT;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValue;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueText;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueType;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

@Component
public class ElementValueConverterText implements ElementValueConverter {

  @Override
  public CertificateDataValueType getType() {
    return TEXT;
  }

  @Override
  public ElementValue convert(CertificateDataValue value) {
    if (!(value instanceof CertificateDataValueText textValue)) {
      throw new IllegalStateException(
          "Invalid value type. Type was '%s'".formatted(value.getType())
      );
    }
    return ElementValueText.builder()
        .textId(new FieldId(textValue.getId()))
        .text(textValue.getText())
        .build();
  }
}
