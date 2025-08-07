package se.inera.intyg.certificateservice.application.certificate.service.converter;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfig;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfigInteger;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationInteger;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementType;

@Component
public class CertificateDataIntegerConfigConverter implements
    CertificateDataConfigConverter {

  @Override
  public ElementType getType() {
    return ElementType.INTEGER;
  }

  public CertificateDataConfig convert(ElementSpecification elementSpecification,
      Certificate certificate) {
    if (!(elementSpecification.configuration() instanceof ElementConfigurationInteger configurationInteger)) {
      throw new IllegalStateException(
          "Invalid config type. Type was '%s'".formatted(
              elementSpecification.configuration().type())
      );
    }

    return CertificateDataConfigInteger.builder()
        .id(configurationInteger.id().value())
        .description(configurationInteger.description())
        .text(elementSpecification.configuration().name())
        .header(configurationInteger.header())
        .min(configurationInteger.min())
        .max(configurationInteger.max())
        .unitOfMeasurement(configurationInteger.unitOfMeasurement())
        .build();
  }
}
