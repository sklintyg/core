package se.inera.intyg.certificateservice.application.certificate.service.converter;

import static se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueType.OCULAR_ACUITIES;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValue;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueDouble;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueOcularAcuities;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueType;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueOcularAcuities;
import se.inera.intyg.certificateservice.domain.certificate.model.OcularAcuity;
import se.inera.intyg.certificateservice.domain.certificate.model.WithCorrection;
import se.inera.intyg.certificateservice.domain.certificate.model.WithoutCorrection;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

@Component
@RequiredArgsConstructor
public class ElementValueConverterOcularAcuities implements ElementValueConverter {

  @Override
  public CertificateDataValueType getType() {
    return OCULAR_ACUITIES;
  }

  @Override
  public ElementValue convert(CertificateDataValue value) {
    if (!(value instanceof CertificateDataValueOcularAcuities ocularAcuities)) {
      throw new IllegalStateException(
          "Invalid value type. Type was '%s'".formatted(value.getType())
      );
    }

    return ElementValueOcularAcuities.builder()
        .rightEye(
            OcularAcuity.builder()
                .withCorrection(
                    WithCorrection.builder()
                        .id(new FieldId(ocularAcuities.getRightEye().getWithCorrection().getId()))
                        .value(getIfPresent(ocularAcuities.getRightEye().getWithCorrection()))
                        .build()
                )
                .withoutCorrection(
                    WithoutCorrection.builder()
                        .id(new FieldId(
                            ocularAcuities.getRightEye().getWithoutCorrection().getId()))
                        .value(getIfPresent(ocularAcuities.getRightEye().getWithoutCorrection()))
                        .build()
                )
                .build()
        )
        .leftEye(
            OcularAcuity.builder()
                .withCorrection(
                    WithCorrection.builder()
                        .id(new FieldId(ocularAcuities.getLeftEye().getWithCorrection().getId()))
                        .value(getIfPresent(ocularAcuities.getLeftEye().getWithCorrection()))
                        .build()
                )
                .withoutCorrection(
                    WithoutCorrection.builder()
                        .id(new FieldId(ocularAcuities.getLeftEye().getWithoutCorrection().getId()))
                        .value(getIfPresent(ocularAcuities.getLeftEye().getWithoutCorrection()))
                        .build()
                )
                .build()
        )
        .binocular(
            OcularAcuity.builder()
                .withCorrection(
                    WithCorrection.builder()
                        .id(new FieldId(ocularAcuities.getBinocular().getWithCorrection().getId()))
                        .value(getIfPresent(ocularAcuities.getBinocular().getWithCorrection()))
                        .build()
                )
                .withoutCorrection(
                    WithoutCorrection.builder()
                        .id(new FieldId(
                            ocularAcuities.getBinocular().getWithoutCorrection().getId()))
                        .value(getIfPresent(ocularAcuities.getBinocular().getWithoutCorrection()))
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