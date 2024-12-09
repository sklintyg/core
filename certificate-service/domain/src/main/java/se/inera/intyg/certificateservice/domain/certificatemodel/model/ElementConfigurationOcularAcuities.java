package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.certificate.model.Correction;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueOcularAcuities;
import se.inera.intyg.certificateservice.domain.certificate.model.OcularAcuity;

@Value
@Builder
public class ElementConfigurationOcularAcuities implements ElementConfiguration {

  @Getter(onMethod = @__(@Override))
  String name;
  @Getter(onMethod = @__(@Override))
  ElementType type = ElementType.OCULAR_ACUITIES;
  @Getter(onMethod = @__(@Override))
  ElementMessage message;
  FieldId id;
  String withoutCorrectionLabel;
  String withCorrectionLabel;
  ElementOcularAcuity rightEye;
  ElementOcularAcuity leftEye;
  ElementOcularAcuity binocular;

  @Override
  public ElementValue emptyValue() {
    return ElementValueOcularAcuities.builder()
        .rightEye(
            OcularAcuity.builder()
                .withoutCorrection(
                    Correction.builder()
                        .id(new FieldId(rightEye.withoutCorrectionId()))
                        .build()
                )
                .withCorrection(
                    Correction.builder()
                        .id(new FieldId(rightEye.withCorrectionId()))
                        .build()
                )
                .build()
        )
        .leftEye(
            OcularAcuity.builder()
                .withoutCorrection(
                    Correction.builder()
                        .id(new FieldId(leftEye.withoutCorrectionId()))
                        .build()
                )
                .withCorrection(
                    Correction.builder()
                        .id(new FieldId(leftEye.withCorrectionId()))
                        .build()
                )
                .build()
        )
        .binocular(
            OcularAcuity.builder()
                .withoutCorrection(
                    Correction.builder()
                        .id(new FieldId(binocular.withoutCorrectionId()))
                        .build()
                )
                .withCorrection(
                    Correction.builder()
                        .id(new FieldId(binocular.withCorrectionId()))
                        .build()
                )
                .build()
        )
        .build();
  }
}
