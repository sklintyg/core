package se.inera.intyg.certificateservice.application.certificate.service.converter;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValue;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementType;

@Component
public class CertificateDataValueConverterDate implements CertificateDataValueConverter {

  @Override
  public ElementType getType() {
    return ElementType.DATE;
  }

  @Override
  public CertificateDataValue convert(ElementSpecification elementSpecification,
      ElementValue elementValue) {
    if (!(elementValue instanceof ElementValueDate)) {
      throw new IllegalStateException(
          "Invalid value type. Type was '%s'".formatted(elementSpecification.configuration().type())
      );
    }

    if (!(elementSpecification.configuration() instanceof ElementConfigurationDate elementConfiguration)) {
      throw new IllegalStateException(
          "Invalid configuration type. Type was '%s'".formatted(
              elementSpecification.configuration().type())
      );
    }

    return CertificateDataValueDate.builder()
        .id(elementConfiguration.id().value())
        .date(((ElementValueDate) elementValue).date())
        .build();
  }
}
