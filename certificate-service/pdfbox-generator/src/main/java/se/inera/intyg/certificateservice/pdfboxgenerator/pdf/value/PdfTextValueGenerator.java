package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value;

import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationText;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfField;

@Component
public class PdfTextValueGenerator implements PdfElementValue<ElementValueText> {

  private static final String OVERFLOW_MESSAGE = "... Se fortsättningsblad!";
  private static final String SMALL_OVERFLOW_MESSAGE = "...";

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
    if (pdfConfiguration.maxLength() != null
        && pdfConfiguration.maxLength() < elementValueText.text().length()) {
      final var hasSpaceForOverflowMessage =
          pdfConfiguration.maxLength() > OVERFLOW_MESSAGE.length();
      final var firstBreakpoint =
          hasSpaceForOverflowMessage ? pdfConfiguration.maxLength() - OVERFLOW_MESSAGE.length() - 1
              : pdfConfiguration.maxLength() - 1 - SMALL_OVERFLOW_MESSAGE.length();
      return List.of(
          PdfField.builder()
              .id(pdfConfiguration.pdfFieldId().id())
              .value(elementValueText.text().substring(0, firstBreakpoint)
                  + (hasSpaceForOverflowMessage ? OVERFLOW_MESSAGE : SMALL_OVERFLOW_MESSAGE))
              .build(),
          PdfField.builder()
              .id(pdfConfiguration.overflowSheetFieldId().id())
              .value(elementSpecification.configuration().name())
              .append(true)
              .build(),
          PdfField.builder()
              .id(pdfConfiguration.overflowSheetFieldId().id())
              .value(SMALL_OVERFLOW_MESSAGE + elementValueText.text().substring(firstBreakpoint)
                  + "\n")
              .append(true)
              .build()
      );
    }

    return List.of(
        PdfField.builder()
            .id(pdfConfiguration.pdfFieldId().id())
            .value(elementValueText.text())
            .build()
    );
  }
}
