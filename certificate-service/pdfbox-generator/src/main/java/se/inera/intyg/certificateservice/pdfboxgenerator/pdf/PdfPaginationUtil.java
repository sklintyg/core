package se.inera.intyg.certificateservice.pdfboxgenerator.pdf;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.text.OverFlowLineSplit;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.text.TextUtil;

@Slf4j
@RequiredArgsConstructor
@Component
public class PdfPaginationUtil {

  private final TextUtil textUtil;

  public List<List<PdfField>> paginateFields(CertificatePdfContext context,
      List<PdfField> appendedFields, PDField overflowField) {

    List<List<PdfField>> pages = new ArrayList<>();
    List<PdfField> currentPage = new ArrayList<>();

    for (PdfField field : appendedFields) {

      final var overflow = textUtil.getOverflowingLines(
          currentPage,
          field,
          overflowField.getWidgets().getFirst().getRectangle(),
          context.getFontSize(),
          context.getFont()
      );

      if (overflow.isEmpty()) {
        currentPage.add(field);
        continue;
      }

      FieldSplit split = splitField(field, overflow.get());
      currentPage.add(split.first());

      pages.add(currentPage);

      currentPage = new ArrayList<>();

      if (split.second() != null) {
        currentPage.add(split.second());
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
