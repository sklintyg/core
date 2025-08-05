package se.inera.intyg.certificateservice.application.certificate.service.converter;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfig;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfigRadioMultipleCode;
import se.inera.intyg.certificateservice.application.certificate.dto.config.Layout;
import se.inera.intyg.certificateservice.application.certificate.dto.config.RadioMultipleCode;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioMultipleCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementType;

@Component
public class CertificateDataRadioMultipleCodeConfigConverter implements
    CertificateDataConfigConverter {

  @Override
  public ElementType getType() {
    return ElementType.RADIO_MULTIPLE_CODE;
  }

  public CertificateDataConfig convert(ElementSpecification elementSpecification,
      Certificate certificate) {
    if (!(elementSpecification.configuration() instanceof ElementConfigurationRadioMultipleCode configuration)) {
      throw new IllegalStateException(
          "Invalid config type. Type was '%s'".formatted(
              elementSpecification.configuration().type())
      );
    }
    return CertificateDataConfigRadioMultipleCode.builder()
        .text(configuration.name())
        .description(configuration.description())
        .layout(Layout.toLayout(configuration.elementLayout()))
        .list(
            configuration.list().stream()
                .map(radioMultipleCode ->
                    RadioMultipleCode.builder()
                        .id(radioMultipleCode.id().value())
                        .label(radioMultipleCode.label())
                        .build()
                )
                .toList()
        )
        .build();
  }
}
