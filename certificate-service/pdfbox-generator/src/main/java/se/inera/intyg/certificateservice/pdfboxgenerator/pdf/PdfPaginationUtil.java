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

      final var overflow = textUtil.getOverflowingLines(
          currentPage,
          field,
          overflowField.getWidgets().getFirst().getRectangle(),
          textFieldAppearance.getFontSize(),
          textFieldAppearance.getFont(context.getAcroForm().getDefaultResources())
      );

      if (overflow.isEmpty()) {
        currentPage.add(field);
        continue;
      }

      FieldSplit split = splitField(field, overflow.get());
      currentPage.add(split.first());

      pages.add(currentPage);

      final var overflows = split.overflows();

      if (overflows.isEmpty()) {
        currentPage = new ArrayList<>();
      } else {
        pages.addAll(overflows.subList(0, overflows.size() - 1));

        currentPage = new ArrayList<>(overflows.getLast());
      }
    }

    if (!currentPage.isEmpty()) {
      pages.add(currentPage);
    }

    return pages;
  }


  private FieldSplit splitField(PdfField original, OverFlowLineSplit parts) {

    PdfField first = null;

    if (parts.partOne() != null) {
      first = original.withValue(parts.partOne());
    }

    final var overflowingFields = parts.overflowPages().stream()
        .map(text -> PdfField.builder()
            .id(original.getId())
            .value(text)
            .appearance(original.getAppearance())
            .append(true)
            .build())
        .map(List::of)
        .toList();

    return new FieldSplit(first, overflowingFields);
  }
}
