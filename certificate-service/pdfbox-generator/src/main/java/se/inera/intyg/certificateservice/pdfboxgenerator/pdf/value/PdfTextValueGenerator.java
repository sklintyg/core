package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value;

import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationText;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfField;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.text.TextSplitRenderSpec;

@Component
public class PdfTextValueGenerator implements PdfElementValue<ElementValueText> {

  @Override
  public Class<ElementValueText> getType() {
    return ElementValueText.class;
  }

  @Override
  public List<PdfField> generate(ElementSpecification elementSpecification,
      ElementValueText elementValueText) {
    if (elementValueText.text() == null) {
      return Collections.emptyList();
    }

    final var pdfConfiguration = (PdfConfigurationText) elementSpecification.pdfConfiguration();
    if (hasOverflow(elementValueText, pdfConfiguration)) {
      final var splitText = PdfValueGeneratorUtil.splitByLimit(
          TextSplitRenderSpec.builder()
              .fieldText(elementValueText.text())
              .limit(pdfConfiguration.maxLength())
              .shouldRemoveLineBreaks(false)
              .build());
      if (hasOverFlowSheet(pdfConfiguration)) {
        return getFieldsWithOverflowSheet(elementSpecification, pdfConfiguration, splitText);
      }
      return getFieldsWithoutOverflowSheet(elementValueText, pdfConfiguration);
    }

    return List.of(
        PdfField.builder()
            .id(pdfConfiguration.pdfFieldId().id())
            .value(elementValueText.text())
            .offset(pdfConfiguration.offset())
            .build()
    );
  }

  private static boolean hasOverflow(ElementValueText elementValueText,
      PdfConfigurationText pdfConfiguration) {
    return pdfConfiguration.maxLength() != null
        && pdfConfiguration.maxLength() < elementValueText.text().length();
  }

  private static List<PdfField> getFieldsWithoutOverflowSheet(ElementValueText elementValueText,
      PdfConfigurationText pdfConfiguration) {
    return List.of(
        PdfField.builder()
            .id(pdfConfiguration.pdfFieldId().id())
            .value(
                PdfValueGeneratorUtil.splitByLimit(
                    TextSplitRenderSpec.builder()
                        .limit(pdfConfiguration.maxLength())
                        .fieldText(elementValueText.text())
                        .informationMessage("...")
                        .build()).getFirst()
            )
            .build()
    );
  }

  private static List<PdfField> getFieldsWithOverflowSheet(
      ElementSpecification elementSpecification,
      PdfConfigurationText pdfConfiguration, List<String> splitText) {
    return List.of(
        PdfField.builder()
            .id(pdfConfiguration.pdfFieldId().id())
            .value(splitText.get(0))
            .build(),
        PdfField.builder()
            .id(pdfConfiguration.overflowSheetFieldId().id())
            .value(elementSpecification.configuration().name())
            .append(true)
            .build(),
        PdfField.builder()
            .id(pdfConfiguration.overflowSheetFieldId().id())
            .value(splitText.get(1) + "\n")
            .append(true)
            .build()
    );
  }

  private static boolean hasOverFlowSheet(PdfConfigurationText pdfConfiguration) {
    return pdfConfiguration.overflowSheetFieldId() != null
        && !pdfConfiguration.overflowSheetFieldId().id().isEmpty();
  }
}
