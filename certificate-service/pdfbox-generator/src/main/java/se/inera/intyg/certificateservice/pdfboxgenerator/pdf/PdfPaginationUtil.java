package se.inera.intyg.certificateservice.pdfboxgenerator.pdf;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.factory.TextFieldAppearanceFactory;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.text.OverFlowLineSplit;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.text.TextUtil;

@Slf4j
@RequiredArgsConstructor
@Component
public class PdfPaginationUtil {

  private final TextUtil textUtil;

  private final TextFieldAppearanceFactory textFieldAppearanceFactory;

  public List<List<PdfField>> paginateFields(CertificatePdfContext context,
      List<PdfField> appendedFields, PDField overflowField) {

    final var textFieldAppearance = textFieldAppearanceFactory.create(overflowField)
        .orElseThrow(() -> new IllegalStateException(
            "Overflow field is not a variable text field: "
                + overflowField.getFullyQualifiedName()));

    List<List<PdfField>> pages = new ArrayList<>();
    List<PdfField> currentPage = new ArrayList<>();

    for (PdfField field : appendedFields) {

      final var overflows = textUtil.getOverflowingLines(
          currentPage,
          field,
          overflowField.getWidgets().getFirst().getRectangle(),
          textFieldAppearance.getFontSize(),
          textFieldAppearance.getFont(context.getAcroForm().getDefaultResources())
      );

      if (overflows.isEmpty()) {
        currentPage.add(field);
        continue;
      }

      final var firstSplit = splitField(field, overflows.getFirst());
      currentPage.add(firstSplit.first());
      pages.add(currentPage);
      currentPage = new ArrayList<>();

      if (firstSplit.second() != null) {
        currentPage.add(firstSplit.second());
      }

      for (int i = 1; i < overflows.size(); i++) {
        final var additionalSplit = splitField(field, overflows.get(i));

        if (additionalSplit.first() != null && !additionalSplit.first().getValue().isEmpty()) {
          currentPage.add(additionalSplit.first());
        }

        pages.add(currentPage);
        currentPage = new ArrayList<>();

        if (additionalSplit.second() != null) {
          currentPage.add(additionalSplit.second());
        }
      }
    }

    if (!currentPage.isEmpty()) {
      pages.add(currentPage);
    }

    return pages;
  }


  private FieldSplit splitField(PdfField original, OverFlowLineSplit parts) {

    PdfField first = null;
    PdfField second = null;

    if (parts.partOne() != null) {
      first = original.withValue(parts.partOne());
    }

    if (parts.partTwo() != null) {
      second = PdfField.builder()
          .id(original.getId())
          .value(parts.partTwo())
          .appearance(original.getAppearance())
          .append(true)
          .build();
    }

    return new FieldSplit(first, second);
  }
}
