package se.inera.intyg.certificateservice.application.certificate.service.converter;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValue;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueDouble;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueOcularAcuities;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueOcularAcuity;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueOcularAcuities;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementType;

@Component
public class CertificateDataValueConverterOcularAcuities implements CertificateDataValueConverter {

  @Override
  public ElementType getType() {
    return ElementType.OCULAR_ACUITIES;
  }

  @Override
  public CertificateDataValue convert(ElementSpecification elementSpecification,
      ElementValue elementValue) {
    if (elementValue != null && !(elementValue instanceof ElementValueOcularAcuities)) {
      throw new IllegalStateException(
          "Invalid value type. Type was '%s'".formatted(elementValue.getClass())
      );
    }

    final var elementValueOcularAcuities = getElementValueOcularAcuities(elementValue);
    return CertificateDataValueOcularAcuities.builder()
        .rightEye(
            elementValueOcularAcuities.rightEye() != null ?
                CertificateDataValueOcularAcuity.builder()
                    .withCorrection(
                        CertificateDataValueDouble.builder()
                            .id(elementValueOcularAcuities.rightEye().withCorrection().id().value())
                            .value(elementValueOcularAcuities.rightEye().withCorrection().value())
                            .build()
                    )
                    .withoutCorrection(
                        CertificateDataValueDouble.builder()
                            .id(elementValueOcularAcuities.rightEye().withoutCorrection().id()
                                .value())
                            .value(
                                elementValueOcularAcuities.rightEye().withoutCorrection().value())
                            .build()
                    )
                    .build()
                : null

        )
        .leftEye(
            elementValueOcularAcuities.leftEye() != null ?
                CertificateDataValueOcularAcuity.builder()
                    .withCorrection(
                        CertificateDataValueDouble.builder()
                            .id(elementValueOcularAcuities.leftEye().withCorrection().id().value())
                            .value(elementValueOcularAcuities.leftEye().withCorrection().value())
                            .build()
                    )
                    .withoutCorrection(
                        CertificateDataValueDouble.builder()
                            .id(elementValueOcularAcuities.leftEye().withoutCorrection().id()
                                .value())
                            .value(elementValueOcularAcuities.leftEye().withoutCorrection().value())
                            .build()
                    )
                    .build()
                : null

        )
        .binocular(
            elementValueOcularAcuities.binocular() != null ?
                CertificateDataValueOcularAcuity.builder()
                    .withCorrection(
                        CertificateDataValueDouble.builder()
                            .id(elementValueOcularAcuities.binocular().withCorrection().id()
                                .value())
                            .value(elementValueOcularAcuities.binocular().withCorrection().value())
                            .build()
                    )
                    .withoutCorrection(
                        CertificateDataValueDouble.builder()
                            .id(elementValueOcularAcuities.binocular().withoutCorrection().id()
                                .value())
                            .value(
                                elementValueOcularAcuities.binocular().withoutCorrection().value())
                            .build()
                    )
                    .build()
                : null

        )
        .build();
  }

  private static ElementValueOcularAcuities getElementValueOcularAcuities(
      ElementValue elementValue) {
    return elementValue != null ? (ElementValueOcularAcuities) elementValue
        : ElementValueOcularAcuities.builder().build();
  }

}