package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.certificate.model.Correction;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueVisualAcuities;
import se.inera.intyg.certificateservice.domain.certificate.model.VisualAcuity;

@Value
@Builder
public class ElementConfigurationVisualAcuities implements ElementConfiguration {

  @Getter(onMethod = @__(@Override))
  String name;
  @Getter(onMethod = @__(@Override))
  ElementType type = ElementType.VISUAL_ACUITIES;
  @Getter(onMethod = @__(@Override))
  ElementMessage message;
  FieldId id;
  String withoutCorrectionLabel;
  String withCorrectionLabel;
  ElementVisualAcuity rightEye;
  ElementVisualAcuity leftEye;
  ElementVisualAcuity binocular;

  @Override
  public ElementValue emptyValue() {
    return ElementValueVisualAcuities.builder()
        .rightEye(
            VisualAcuity.builder()
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
            VisualAcuity.builder()
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
            VisualAcuity.builder()
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