package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value;

import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValueText;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueIcf;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationText;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfField;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.text.TextSplitRenderSpec;

@Component
public class PdfIcfValueGenerator implements PdfElementValue<ElementValueIcf> {

  @Override
  public Class<ElementValueIcf> getType() {
    return ElementValueIcf.class;
  }

  @Override
  public List<PdfField> generate(ElementSpecification elementSpecification,
      ElementValueIcf elementValueIcf) {
    if (elementValueIcf.text() == null && elementValueIcf.icfCodes().isEmpty()) {
      return Collections.emptyList();
    }

    final var pdfConfiguration = (PdfConfigurationText) elementSpecification.pdfConfiguration();
    final var simplifiedValue = elementSpecification.configuration().simplified(elementValueIcf);
    if (simplifiedValue.isEmpty()) {
      return Collections.emptyList();
    }
    final var simplifiedValueText = (ElementSimplifiedValueText) simplifiedValue.get();

    if (hasOverflow(simplifiedValueText, pdfConfiguration)) {
      final var splitIcf = PdfValueGeneratorUtil.splitByLimit(
          TextSplitRenderSpec.builder()
              .limit(pdfConfiguration.maxLength())
              .fieldText(simplifiedValueText.text())
              .shouldRemoveLineBreaks(false)
              .build());
      if (hasOverFlowSheet(pdfConfiguration)) {
        return getFieldsWithOverflowSheet(elementSpecification, pdfConfiguration, splitIcf);
      }
      return getFieldsWithoutOverflowSheet(simplifiedValueText, pdfConfiguration);
    }

    return List.of(
        PdfField.builder()
            .id(pdfConfiguration.pdfFieldId().id())
            .value(simplifiedValueText.text())
            .build()
    );
  }

  private static boolean hasOverflow(ElementSimplifiedValueText elementSimplifiedValueText,
      PdfConfigurationText pdfConfiguration) {
    return pdfConfiguration.maxLength() != null
        && pdfConfiguration.maxLength() < elementSimplifiedValueText.text().length();
  }

  private static List<PdfField> getFieldsWithoutOverflowSheet(
      ElementSimplifiedValueText elementSimplifiedValueText,
      PdfConfigurationText pdfConfiguration) {
    return List.of(
        PdfField.builder()
            .id(pdfConfiguration.pdfFieldId().id())
            .value(
                PdfValueGeneratorUtil.splitByLimit(
                        TextSplitRenderSpec.builder()
                            .limit(pdfConfiguration.maxLength())
                            .fieldText(elementSimplifiedValueText.text())
                            .shouldRemoveLineBreaks(false)
                            .informationMessage("...")
                            .build())
                    .getFirst()
            )
            .build()
    );
  }

  private static List<PdfField> getFieldsWithOverflowSheet(
      ElementSpecification elementSpecification,
      PdfConfigurationText pdfConfiguration, List<String> splitIcf) {
    return List.of(
        PdfField.builder()
            .id(pdfConfiguration.pdfFieldId().id())
            .value(splitIcf.get(0))
            .build(),
        PdfField.builder()
            .id(pdfConfiguration.overflowSheetFieldId().id())
            .value(elementSpecification.configuration().name())
            .append(true)
            .build(),
        PdfField.builder()
            .id(pdfConfiguration.overflowSheetFieldId().id())
            .value(splitIcf.get(1) + "\n")
            .append(true)
            .build()
    );
  }

  private static boolean hasOverFlowSheet(PdfConfigurationText pdfConfiguration) {
    return pdfConfiguration.overflowSheetFieldId() != null
        && !pdfConfiguration.overflowSheetFieldId().id().isEmpty();
  }
}
