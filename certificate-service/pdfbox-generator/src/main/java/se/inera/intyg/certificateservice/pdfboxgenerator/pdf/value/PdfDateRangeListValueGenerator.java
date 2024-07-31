package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value;

import static se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfConstants.CHECKED_BOX_VALUE;
import static se.inera.intyg.certificateservice.pdfboxgenerator.pdf.codesystems.CodeSystemKvFkmu0008.EN_ATTONDEL;
import static se.inera.intyg.certificateservice.pdfboxgenerator.pdf.codesystems.CodeSystemKvFkmu0008.EN_FJARDEDEL;
import static se.inera.intyg.certificateservice.pdfboxgenerator.pdf.codesystems.CodeSystemKvFkmu0008.HALVA;
import static se.inera.intyg.certificateservice.pdfboxgenerator.pdf.codesystems.CodeSystemKvFkmu0008.HELA;
import static se.inera.intyg.certificateservice.pdfboxgenerator.pdf.codesystems.CodeSystemKvFkmu0008.TRE_FJARDEDELAR;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.DateRange;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateRangeList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfField;

@Component
public class PdfDateRangeListValueGenerator implements PdfElementValue {

  private static final String CHECKBOX_PERIOD_PREFIX_ID = ".ksr_";
  private static final String DATE_FROM_PERIOD_PREFIX_ID = ".flt_datFranMed";
  private static final String DATE_TO_PERIOD_PREFIX_ID = ".flt_datLangstTillMed";
  private static final String PERIOD_SUFFIX_ID = "[0]";

  @Override
  public Class<? extends ElementValue> getType() {
    return ElementValueDateRangeList.class;
  }

  @Override
  public List<PdfField> generate(Certificate certificate, ElementId questionId, String fieldId) {
    if (certificate.elementData() == null || certificate.elementData().isEmpty()) {
      return Collections.emptyList();
    }

    final var question = certificate.getElementDataById(questionId);

    if (question.isEmpty()) {
      return Collections.emptyList();
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
    return fieldName + CHECKBOX_PERIOD_PREFIX_ID + getCheckboxSuffixFromDateRange(dateRange)
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
    final var workCapacityType = dateRange.dateRangeId().value();

    if (EN_ATTONDEL.code().equalsIgnoreCase(workCapacityType)) {
      return "5";
    }
    if (EN_FJARDEDEL.code().equalsIgnoreCase(workCapacityType)) {
      return "4";
    }
    if (HALVA.code().equalsIgnoreCase(workCapacityType)) {
      return "3";
    }
    if (TRE_FJARDEDELAR.code().equalsIgnoreCase(workCapacityType)) {
      return "2";
    }
    if (HELA.code().equalsIgnoreCase(workCapacityType)) {
      return "";
    }
    return "";
  }

  private String getCheckboxSuffixFromDateRange(DateRange dateRange) {
    final var workCapacityType = dateRange.dateRangeId().value();

    if (EN_ATTONDEL.code().equalsIgnoreCase(workCapacityType)) {
      return "EnAttondel";
    }
    if (EN_FJARDEDEL.code().equalsIgnoreCase(workCapacityType)) {
      return "Enfjardedela";
    }
    if (HALVA.code().equalsIgnoreCase(workCapacityType)) {
      return "Halva";
    }
    if (TRE_FJARDEDELAR.code().equalsIgnoreCase(workCapacityType)) {
      return "Trefjardedela";
    }
    if (HELA.code().equalsIgnoreCase(workCapacityType)) {
      return "Hela";
    }
    return "";
  }
}
