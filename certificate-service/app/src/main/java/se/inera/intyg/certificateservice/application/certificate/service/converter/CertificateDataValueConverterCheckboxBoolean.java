package se.inera.intyg.certificateservice.application.certificate.service.converter;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValue;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueBoolean;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementType;

@Component
public class CertificateDataValueConverterCheckboxBoolean implements CertificateDataValueConverter {

  @Override
  public ElementType getType() {
    return ElementType.CHECKBOX_BOOLEAN;
  }

  @Override
  public CertificateDataValue convert(ElementSpecification elementSpecification,
      ElementValue elementValue) {
    if (elementValue != null && !(elementValue instanceof ElementValueBoolean)) {
      throw new IllegalStateException(
          "Invalid value type. Type was '%s'".formatted(elementValue.getClass())
      );
    }

    if (!(elementSpecification.configuration() instanceof ElementConfigurationCheckboxBoolean elementConfigurationBoolean)) {
      throw new IllegalStateException(
          "Invalid configuration type. Type was '%s'".formatted(
              elementSpecification.configuration().type())
      );
    }

    return CertificateDataValueBoolean.builder()
        .id(elementConfigurationBoolean.id().value())
        .selected(elementValue != null ? ((ElementValueBoolean) elementValue).value() : null)
        .build();
  }
}
