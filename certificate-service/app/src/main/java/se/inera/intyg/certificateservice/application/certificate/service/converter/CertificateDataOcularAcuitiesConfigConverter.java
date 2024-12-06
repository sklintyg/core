package se.inera.intyg.certificateservice.application.certificate.service.converter;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfig;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfigOcularAcuity;
import se.inera.intyg.certificateservice.application.certificate.dto.config.OcularAcuity;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationOcularAcuities;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementType;

@Component
public class CertificateDataOcularAcuitiesConfigConverter implements
    CertificateDataConfigConverter {

  @Override
  public ElementType getType() {
    return ElementType.OCULAR_ACUITIES;
  }

  public CertificateDataConfig convert(ElementSpecification elementSpecification,
      Certificate certificate) {
    if (!(elementSpecification.configuration() instanceof ElementConfigurationOcularAcuities configurationOcularAcuities)) {
      throw new IllegalStateException(
          "Invalid config type. Type was '%s'".formatted(
              elementSpecification.configuration().type())
      );
    }

    return CertificateDataConfigOcularAcuity.builder()
        .id(configurationOcularAcuities.id().value())
        .withCorrectionLabel(configurationOcularAcuities.withCorrectionLabel())
        .withoutCorrectionLabel(configurationOcularAcuities.withoutCorrectionLabel())
        .rightEye(
            OcularAcuity.builder()
                .withoutCorrectionId(configurationOcularAcuities.rightEye().withoutCorrectionId())
                .withCorrectionId(configurationOcularAcuities.rightEye().withCorrectionId())
                .label(configurationOcularAcuities.rightEye().label())
                .build()
        )
        .leftEye(
            OcularAcuity.builder()
                .withoutCorrectionId(configurationOcularAcuities.leftEye().withoutCorrectionId())
                .withCorrectionId(configurationOcularAcuities.leftEye().withCorrectionId())
                .label(configurationOcularAcuities.leftEye().label())
                .build()
        )
        .binocular(
            OcularAcuity.builder()
                .withoutCorrectionId(configurationOcularAcuities.binocular().withoutCorrectionId())
                .withCorrectionId(configurationOcularAcuities.binocular().withCorrectionId())
                .label(configurationOcularAcuities.binocular().label())
                .build()
        )
        .build();
  }
}