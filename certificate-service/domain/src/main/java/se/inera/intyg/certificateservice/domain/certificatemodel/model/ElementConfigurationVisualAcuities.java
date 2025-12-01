package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import java.util.List;
import java.util.Optional;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.certificate.model.Correction;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValueTable;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValueText;
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
  Double min;
  Double max;
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

  @Override
  public Optional<ElementSimplifiedValue> simplified(ElementValue value) {
    if (!(value instanceof ElementValueVisualAcuities elementValue)) {
      throw new IllegalStateException("Wrong value type");
    }

    if (elementValue.isEmpty()) {
      return Optional.of(ElementSimplifiedValueText.builder()
          .text("Ej angivet")
          .build());
    }

    return Optional.of(
        ElementSimplifiedValueTable.builder()
            .headings(List.of(withoutCorrectionLabel, withCorrectionLabel))
            .values(
                List.of(
                    elementValue.rightEye().simplified(rightEye.label()),
                    elementValue.leftEye().simplified(leftEye.label()),
                    elementValue.binocular().simplified(binocular.label())
                )
            )
            .build()
    );
  }
}