package se.inera.intyg.certificateservice.pdfboxgenerator.value;

import static se.inera.intyg.certificateservice.pdfboxgenerator.PdfConstants.CHECKED_BOX_VALUE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.DateRange;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateRangeList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.WorkCapacityType;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfField;

@Component
public class PdfDateRangeListValueGenerator implements PdfElementValueGenerator {

  private static final String CHECKBOX_PERIOD_PREFIX_ID = ".ksr_kryssruta";
  private static final String DATE_FROM_PERIOD_PREFIX_ID = ".flt_datFranMed";
  private static final String DATE_TO_PERIOD_PREFIX_ID = ".flt_datLangstTillMed";
  private static final String PERIOD_SUFFIX_ID = "[0]";

  @Override
  public List<PdfField> generate(Certificate certificate, ElementId questionId, String fieldId) {
    if (certificate.elementData() == null || certificate.elementData().isEmpty()) {
      return Collections.emptyList();
    }

    final var question = certificate.getElementDataById(questionId);

    if (question.isEmpty()) {
      throw new IllegalStateException(
          "Could not find question with id: %s".formatted(questionId));
    }

    if (!(question.get().value() instanceof ElementValueDateRangeList elementValueDateRangeList)) {
      throw new IllegalStateException(
          String.format(
              "Expected ElementValueDateRangeList but was: '%s'",
              question.get().value().getClass()
          )
      );
    }

    return elementValueDateRangeList.dateRangeList().stream()
        .map(dateRange -> getPeriodFields(dateRange, fieldId))
        .flatMap(Collection::stream)
        .toList();
  }

  private List<PdfField> getPeriodFields(DateRange dateRange, String fieldName) {
    final var fields = new ArrayList<PdfField>();

    fields.add(
        PdfField.builder()
            .id(getPeriodCheckboxId(dateRange, fieldName))
            .value(CHECKED_BOX_VALUE)
            .build()
    );

    if (dateRange.from() != null) {
      fields.add(
          PdfField.builder()
              .id(getPeriodFromId(dateRange, fieldName))
              .value(dateRange.from().toString())
              .build()
      );
    }

    if (dateRange.to() != null) {
      fields.add(
          PdfField.builder()
              .id(getPeriodToId(dateRange, fieldName))
              .value(dateRange.to().toString())
              .build()
      );
    }

    return fields;
  }

  private String getPeriodCheckboxId(DateRange dateRange, String fieldName) {
    return fieldName + CHECKBOX_PERIOD_PREFIX_ID + getFieldSuffixFromDateRange(dateRange)
        + PERIOD_SUFFIX_ID;
  }

  private String getPeriodToId(DateRange dateRange, String fieldName) {
    return fieldName + DATE_TO_PERIOD_PREFIX_ID + getFieldSuffixFromDateRange(dateRange)
        + PERIOD_SUFFIX_ID;
  }

  private String getPeriodFromId(DateRange dateRange, String fieldName) {
    return fieldName + DATE_FROM_PERIOD_PREFIX_ID + getFieldSuffixFromDateRange(dateRange)
        + PERIOD_SUFFIX_ID;
  }

  private String getFieldSuffixFromDateRange(DateRange dateRange) {
    final var workCapacityType = WorkCapacityType.valueOf(dateRange.dateRangeId().value());
    switch (workCapacityType) {
      case EN_ATTANDEL -> {
        return "5";
      }
      case EN_FJARDEDEL -> {
        return "4";
      }
      case HALVA -> {
        return "3";
      }
      case TRE_FJARDEDELAR -> {
        return "2";
      }
      case HELA -> {
        return "";
      }
    }
    return "";
  }
}
