package se.inera.intyg.certificateservice.pdfboxgenerator.value;

import java.io.IOException;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.DateRange;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateRangeList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.WorkCapacityType;
import se.inera.intyg.certificateservice.pdfboxgenerator.toolkits.PdfGeneratorValueToolkit;

public class PdfDateRangeListValueGenerator implements PdfElementValueGenerator {

  private static final String CHECKBOX_PERIOD_PREFIX_ID = ".ksr_kryssruta";
  private static final String DATE_FROM_PERIOD_PREFIX_ID = ".flt_datFranMed";
  private static final String DATE_TO_PERIOD_PREFIX_ID = ".flt_datLangstTillMed";
  private static final String PERIOD_SUFFIX_ID = "[0]";

  private PdfGeneratorValueToolkit pdfGeneratorValueToolkit;

  @Override
  public void generate(PDAcroForm acroForm, Certificate certificate, ElementId questionId,
      String fieldName) {
    pdfGeneratorValueToolkit = new PdfGeneratorValueToolkit();
    final var question = certificate.getElementDataById(questionId);

    if (question.isEmpty()) {
      throw new IllegalStateException(
          "Could not find question with id: " + certificate.elementData().get(0).id().id());
    }

    if (!(question.get().value() instanceof ElementValueDateRangeList elementValueDateRangeList)) {
      throw new IllegalStateException(
          String.format(
              "Expected ElementValueText but was: '%s'",
              question.get().value().getClass()
          )
      );
    }

    elementValueDateRangeList.dateRangeList()
        .forEach(dateRange -> {
          try {
            fillPeriod(acroForm, dateRange, fieldName);
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        });
  }

  private void fillPeriod(PDAcroForm acroForm, DateRange dateRange, String fieldName)
      throws IOException {
    pdfGeneratorValueToolkit.setCheckedBoxValue(
        acroForm,
        getPeriodCheckboxId(dateRange, fieldName)
    );

    pdfGeneratorValueToolkit.setValue(
        acroForm,
        getPeriodFromId(dateRange, fieldName),
        dateRange.from().toString()
    );

    pdfGeneratorValueToolkit.setValue(
        acroForm,
        getPeriodToId(dateRange, fieldName),
        dateRange.to().toString()
    );
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
        return "2";
      }
      case TRE_FJARDEDELAR -> {
        return "1";
      }
      case HELA -> {
        return "";
      }
    }
    return "";
  }
}
