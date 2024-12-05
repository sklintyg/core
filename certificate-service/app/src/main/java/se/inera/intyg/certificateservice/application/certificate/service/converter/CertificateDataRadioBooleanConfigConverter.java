package se.inera.intyg.certificateservice.application.certificate.service.converter;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfig;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfigRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementType;

@Component
public class CertificateDataRadioBooleanConfigConverter implements CertificateDataConfigConverter {

  @Override
  public ElementType getType() {
    return ElementType.RADIO_BOOLEAN;
  }

  public CertificateDataConfig convert(ElementSpecification elementSpecification,
      Certificate certificate) {
    if (!(elementSpecification.configuration() instanceof ElementConfigurationRadioBoolean configurationRadioBoolean)) {
      throw new IllegalStateException(
          "Invalid config type. Type was '%s'".formatted(
              elementSpecification.configuration().type())
      );
    }

    return CertificateDataConfigRadioBoolean.builder()
        .id(configurationRadioBoolean.id().value())
        .description(configurationRadioBoolean.description())
        .text(elementSpecification.configuration().name())
        .selectedText(configurationRadioBoolean.selectedText())
        .unselectedText(configurationRadioBoolean.unselectedText())
        .build();
  }
}
