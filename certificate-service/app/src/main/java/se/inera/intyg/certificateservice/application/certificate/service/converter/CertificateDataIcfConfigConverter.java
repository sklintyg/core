package se.inera.intyg.certificateservice.application.certificate.service.converter;

import static se.inera.intyg.certificateservice.application.certificate.dto.config.IcfCodesPropertyTypeDTO.toIcfCodesPropertyType;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfig;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfigIcf;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationIcf;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementType;

@Component
public class CertificateDataIcfConfigConverter implements CertificateDataConfigConverter {

  @Override
  public ElementType getType() {
    return ElementType.ICF;
  }

  @Override
  public CertificateDataConfig convert(ElementSpecification elementSpecification,
      Certificate certificate) {
    if (!(elementSpecification.configuration() instanceof ElementConfigurationIcf configurationIcf)) {
      throw new IllegalStateException(
          "Invalid config type. Type was '%s'".formatted(
              elementSpecification.configuration().type())
      );
    }

    return CertificateDataConfigIcf.builder()
        .id(configurationIcf.id().value())
        .icfCodesPropertyName(toIcfCodesPropertyType(configurationIcf.icfCodesPropertyName()))
        .description(configurationIcf.description())
        .text(configurationIcf.name())
        .header(configurationIcf.header())
        .modalLabel(configurationIcf.modalLabel())
        .collectionsLabel(configurationIcf.collectionsLabel())
        .placeholder(configurationIcf.placeholder())
        .build();
  }
}
