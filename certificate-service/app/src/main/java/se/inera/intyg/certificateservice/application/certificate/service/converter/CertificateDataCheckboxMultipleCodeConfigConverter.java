package se.inera.intyg.certificateservice.application.certificate.service.converter;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfig;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfigCheckboxMultipleCode;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CheckboxMultipleCode;
import se.inera.intyg.certificateservice.application.certificate.dto.config.Layout;
import se.inera.intyg.certificateservice.application.certificate.dto.config.Message;
import se.inera.intyg.certificateservice.application.certificate.dto.config.MessageLevel;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxMultipleCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementType;

@Component
public class CertificateDataCheckboxMultipleCodeConfigConverter implements
    CertificateDataConfigConverter {

  @Override
  public ElementType getType() {
    return ElementType.CHECKBOX_MULTIPLE_CODE;
  }

  public CertificateDataConfig convert(ElementSpecification elementSpecification,
      Certificate certificate) {
    if (!(elementSpecification.configuration() instanceof ElementConfigurationCheckboxMultipleCode configuration)) {
      throw new IllegalStateException(
          "Invalid config type. Type was '%s'".formatted(
              elementSpecification.configuration().type())
      );
    }
    return CertificateDataConfigCheckboxMultipleCode.builder()
        .text(configuration.name())
        .label(configuration.label())
        .description(configuration.description())
        .message(
            shouldIncludeMessage(certificate, configuration) ?
                Message.builder()
                    .content(configuration.message().content())
                    .level(MessageLevel.toMessageLevel(
                        configuration.message().level()))
                    .build()
                : null
        )
        .layout(Layout.toLayout(configuration.elementLayout()))
        .list(
            configuration.list().stream()
                .map(code ->
                    CheckboxMultipleCode.builder()
                        .id(code.id().value())
                        .label(code.label())
                        .build()
                )
                .toList()
        )
        .build();
  }

  private static boolean shouldIncludeMessage(Certificate certificate,
      ElementConfigurationCheckboxMultipleCode elementConfigurationCheckboxMultipleCode) {
    return elementConfigurationCheckboxMultipleCode.message() != null
        && elementConfigurationCheckboxMultipleCode.message().include(certificate);
  }
}
