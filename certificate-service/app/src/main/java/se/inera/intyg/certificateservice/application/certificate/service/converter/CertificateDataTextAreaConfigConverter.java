package se.inera.intyg.certificateservice.application.certificate.service.converter;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfig;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfigTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementType;

@Component
public class CertificateDataTextAreaConfigConverter implements CertificateDataConfigConverter {

  @Override
  public ElementType getType() {
    return ElementType.TEXT_AREA;
  }

  public CertificateDataConfig convert(ElementSpecification elementSpecification) {
    final var configuration = (ElementConfigurationTextArea) elementSpecification.configuration();
    return CertificateDataConfigTextArea.builder()
        .id(configuration.id().value())
        .text(configuration.name())
        .build();
  }
}