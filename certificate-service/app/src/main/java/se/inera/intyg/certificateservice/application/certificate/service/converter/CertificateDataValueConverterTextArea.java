package se.inera.intyg.certificateservice.application.certificate.service.converter;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValue;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueText;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementType;

@Component
public class CertificateDataValueConverterTextArea implements CertificateDataValueConverter {

  @Override
  public ElementType getType() {
    return ElementType.TEXT_AREA;
  }

  @Override
  public CertificateDataValue convert(ElementSpecification elementSpecification,
      ElementValue elementValue) {
    final var value = elementValue != null ? ((ElementValueText) elementValue).text() : null;
    final var configuration = (ElementConfigurationTextArea) elementSpecification.configuration();
    return CertificateDataValueText.builder()
        .id(configuration.id().value())
        .text(value)
        .build();
  }
}
