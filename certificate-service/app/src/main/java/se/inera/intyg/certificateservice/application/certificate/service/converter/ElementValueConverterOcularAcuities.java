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
        .rightEye(OcularAcuity.builder()
            .doubleId(new FieldId(ocularAcuities.getRightEye().getId()))
            .withCorrection(getIfPresent(ocularAcuities.getRightEye().getWithCorrection()))
            .withoutCorrection(getIfPresent(ocularAcuities.getRightEye().getWithoutCorrection()))
            .build()
        )
        .leftEye(OcularAcuity.builder()
            .doubleId(new FieldId(ocularAcuities.getLeftEye().getId()))
            .withCorrection(getIfPresent(ocularAcuities.getLeftEye().getWithCorrection()))
            .withoutCorrection(getIfPresent(ocularAcuities.getLeftEye().getWithoutCorrection()))
            .build()
        )
        .binocular(OcularAcuity.builder()
            .doubleId(new FieldId(ocularAcuities.getBinocular().getId()))
            .withCorrection(getIfPresent(ocularAcuities.getBinocular().getWithCorrection()))
            .withoutCorrection(getIfPresent(ocularAcuities.getBinocular().getWithoutCorrection()))
            .build()
        )
        .build();
  }

  private static Double getIfPresent(CertificateDataValueDouble certificateDataValueDouble) {
    return certificateDataValueDouble != null ? certificateDataValueDouble.getValue() : null;
  }
}
