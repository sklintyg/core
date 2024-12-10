package se.inera.intyg.certificateservice.application.certificate.service.converter;

import static se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueType.VISUAL_ACUITIES;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValue;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueDouble;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueType;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueVisualAcuities;
import se.inera.intyg.certificateservice.domain.certificate.model.Correction;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueVisualAcuities;
import se.inera.intyg.certificateservice.domain.certificate.model.VisualAcuity;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

@Component
@RequiredArgsConstructor
public class ElementValueConverterVisualAcuities implements ElementValueConverter {

  @Override
  public CertificateDataValueType getType() {
    return VISUAL_ACUITIES;
  }

  @Override
  public ElementValue convert(CertificateDataValue value) {
    if (!(value instanceof CertificateDataValueVisualAcuities visualAcuities)) {
      throw new IllegalStateException(
          "Invalid value type. Type was '%s'".formatted(value.getType())
      );
    }

    return ElementValueVisualAcuities.builder()
        .rightEye(
            VisualAcuity.builder()
                .withCorrection(
                    Correction.builder()
                        .id(new FieldId(visualAcuities.getRightEye().getWithCorrection().getId()))
                        .value(getIfPresent(visualAcuities.getRightEye().getWithCorrection()))
                        .build()
                )
                .withoutCorrection(
                    Correction.builder()
                        .id(new FieldId(
                            visualAcuities.getRightEye().getWithoutCorrection().getId()))
                        .value(getIfPresent(visualAcuities.getRightEye().getWithoutCorrection()))
                        .build()
                )
                .build()
        )
        .leftEye(
            VisualAcuity.builder()
                .withCorrection(
                    Correction.builder()
                        .id(new FieldId(visualAcuities.getLeftEye().getWithCorrection().getId()))
                        .value(getIfPresent(visualAcuities.getLeftEye().getWithCorrection()))
                        .build()
                )
                .withoutCorrection(
                    Correction.builder()
                        .id(new FieldId(visualAcuities.getLeftEye().getWithoutCorrection().getId()))
                        .value(getIfPresent(visualAcuities.getLeftEye().getWithoutCorrection()))
                        .build()
                )
                .build()
        )
        .binocular(
            VisualAcuity.builder()
                .withCorrection(
                    Correction.builder()
                        .id(new FieldId(visualAcuities.getBinocular().getWithCorrection().getId()))
                        .value(getIfPresent(visualAcuities.getBinocular().getWithCorrection()))
                        .build()
                )
                .withoutCorrection(
                    Correction.builder()
                        .id(new FieldId(
                            visualAcuities.getBinocular().getWithoutCorrection().getId()))
                        .value(getIfPresent(visualAcuities.getBinocular().getWithoutCorrection()))
                        .build()
                )
                .build()
        )
        .build();
  }

  private static Double getIfPresent(CertificateDataValueDouble certificateDataValueDouble) {
    return certificateDataValueDouble != null ? certificateDataValueDouble.getValue() : null;
  }
}