package se.inera.intyg.certificateservice.application.certificate.service.converter;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfig;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfigCheckboxBoolean;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementType;

@Component
public class CertificateDataCheckboxBooleanConfigConverter implements
    CertificateDataConfigConverter {

  @Override
  public ElementType getType() {
    return ElementType.CHECKBOX_BOOLEAN;
  }

  public CertificateDataConfig convert(ElementSpecification elementSpecification,
      Certificate certificate) {
    if (!(elementSpecification.configuration() instanceof ElementConfigurationCheckboxBoolean configurationCheckboxBoolean)) {
      throw new IllegalStateException(
          "Invalid config type. Type was '%s'".formatted(
              elementSpecification.configuration().type())
      );
    }

    return CertificateDataConfigCheckboxBoolean.builder()
        .id(configurationCheckboxBoolean.id().value())
        .description(configurationCheckboxBoolean.description())
        .text(elementSpecification.configuration().name())
        .label(configurationCheckboxBoolean.label())
        .selectedText(configurationCheckboxBoolean.selectedText())
        .unselectedText(configurationCheckboxBoolean.unselectedText())
        .build();
  }
}
