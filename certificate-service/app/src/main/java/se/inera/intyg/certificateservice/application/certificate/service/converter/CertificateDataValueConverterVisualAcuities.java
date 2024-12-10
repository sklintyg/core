package se.inera.intyg.certificateservice.application.certificate.service.converter;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValue;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueDouble;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueVisualAcuities;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueVisualAcuity;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueVisualAcuities;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementType;

@Component
public class CertificateDataValueConverterVisualAcuities implements CertificateDataValueConverter {

  @Override
  public ElementType getType() {
    return ElementType.VISUAL_ACUITIES;
  }

  @Override
  public CertificateDataValue convert(ElementSpecification elementSpecification,
      ElementValue elementValue) {
    if (elementValue != null && !(elementValue instanceof ElementValueVisualAcuities)) {
      throw new IllegalStateException(
          "Invalid value type. Type was '%s'".formatted(elementValue.getClass())
      );
    }

    final var elementValueVisualAcuities = getElementValueVisualAcuities(
        elementValue,
        elementSpecification
    );

    return CertificateDataValueVisualAcuities.builder()
        .rightEye(
            CertificateDataValueVisualAcuity.builder()
                .withCorrection(
                    CertificateDataValueDouble.builder()
                        .id(elementValueVisualAcuities.rightEye().withCorrection().id().value())
                        .value(elementValueVisualAcuities.rightEye().withCorrection().value())
                        .build()
                )
                .withoutCorrection(
                    CertificateDataValueDouble.builder()
                        .id(elementValueVisualAcuities.rightEye().withoutCorrection().id()
                            .value())
                        .value(
                            elementValueVisualAcuities.rightEye().withoutCorrection().value())
                        .build()
                )
                .build()
        )
        .leftEye(
            CertificateDataValueVisualAcuity.builder()
                .withCorrection(
                    CertificateDataValueDouble.builder()
                        .id(elementValueVisualAcuities.leftEye().withCorrection().id().value())
                        .value(elementValueVisualAcuities.leftEye().withCorrection().value())
                        .build()
                )
                .withoutCorrection(
                    CertificateDataValueDouble.builder()
                        .id(elementValueVisualAcuities.leftEye().withoutCorrection().id()
                            .value())
                        .value(elementValueVisualAcuities.leftEye().withoutCorrection().value())
                        .build()
                )
                .build()
        )
        .binocular(
            CertificateDataValueVisualAcuity.builder()
                .withCorrection(
                    CertificateDataValueDouble.builder()
                        .id(elementValueVisualAcuities.binocular().withCorrection().id()
                            .value())
                        .value(elementValueVisualAcuities.binocular().withCorrection().value())
                        .build()
                )
                .withoutCorrection(
                    CertificateDataValueDouble.builder()
                        .id(elementValueVisualAcuities.binocular().withoutCorrection().id()
                            .value())
                        .value(
                            elementValueVisualAcuities.binocular().withoutCorrection().value())
                        .build()
                )
                .build()
        )
        .build();
  }

  private static ElementValueVisualAcuities getElementValueVisualAcuities(
      ElementValue elementValue, ElementSpecification elementSpecification) {
    return elementValue != null ? (ElementValueVisualAcuities) elementValue
        : (ElementValueVisualAcuities) elementSpecification.configuration().emptyValue();
  }

}