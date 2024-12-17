package se.inera.intyg.certificateservice.application.certificate.service.converter;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfig;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfigVisualAcuity;
import se.inera.intyg.certificateservice.application.certificate.dto.config.VisualAcuity;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationVisualAcuities;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementType;

@Component
public class CertificateDataVisualAcuitiesConfigConverter implements
    CertificateDataConfigConverter {

  @Override
  public ElementType getType() {
    return ElementType.VISUAL_ACUITIES;
  }

  public CertificateDataConfig convert(ElementSpecification elementSpecification,
      Certificate certificate) {
    if (!(elementSpecification.configuration() instanceof ElementConfigurationVisualAcuities configurationVisualAcuities)) {
      throw new IllegalStateException(
          "Invalid config type. Type was '%s'".formatted(
              elementSpecification.configuration().type())
      );
    }

    return CertificateDataConfigVisualAcuity.builder()
        .id(configurationVisualAcuities.id().value())
        .text(configurationVisualAcuities.name())
        .withCorrectionLabel(configurationVisualAcuities.withCorrectionLabel())
        .withoutCorrectionLabel(configurationVisualAcuities.withoutCorrectionLabel())
        .min(configurationVisualAcuities.min())
        .max(configurationVisualAcuities.max())
        .rightEye(
            VisualAcuity.builder()
                .withoutCorrectionId(configurationVisualAcuities.rightEye().withoutCorrectionId())
                .withCorrectionId(configurationVisualAcuities.rightEye().withCorrectionId())
                .label(configurationVisualAcuities.rightEye().label())
                .build()
        )
        .leftEye(
            VisualAcuity.builder()
                .withoutCorrectionId(configurationVisualAcuities.leftEye().withoutCorrectionId())
                .withCorrectionId(configurationVisualAcuities.leftEye().withCorrectionId())
                .label(configurationVisualAcuities.leftEye().label())
                .build()
        )
        .binocular(
            VisualAcuity.builder()
                .withoutCorrectionId(configurationVisualAcuities.binocular().withoutCorrectionId())
                .withCorrectionId(configurationVisualAcuities.binocular().withCorrectionId())
                .label(configurationVisualAcuities.binocular().label())
                .build()
        )
        .build();
  }
}