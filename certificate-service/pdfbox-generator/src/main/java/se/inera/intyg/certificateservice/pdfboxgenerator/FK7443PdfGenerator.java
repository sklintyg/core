package se.inera.intyg.certificateservice.pdfboxgenerator;

import java.io.IOException;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.DateRange;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateRangeList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.WorkCapacityType;


public class FK7443PdfGenerator implements PdfCertificateFillService {

  private static final String DIAGNOSIS_ID = "form1[0].#subform[0].flt_txtDiagnos[0]";
  private static final String CHECKBOX_PERIOD_PREFIX_ID = "form1[0].#subform[0].ksr_kryssruta";
  private static final String DATE_FROM_PERIOD_PREFIX_ID = "form1[0].#subform[0].flt_datFranMed";
  private static final String DATE_TO_PERIOD_PREFIX_ID = "form1[0].#subform[0].flt_datLangstTillMed";
  public static final ElementId QUESTION_SYMPTOM_ID = new ElementId("2");
  public static final ElementId QUESTION_PERIOD_ID = new ElementId("3");

  private final PdfTextValueGenerator pdfTextValueGenerator = new PdfTextValueGenerator();

  @Override
  public CertificateType getType() {
    return new CertificateType("fk7443");
  }

  @Override
  public String getPatientIdFormId() {
    return "form1[0].#subform[0].flt_txtPersonNrBarn[0]";
  }

  @Override
  public void fillDocument(PDAcroForm acroForm, Certificate certificate) throws IOException {
    fillDiagnosisQuestion(acroForm, certificate);
    fillPeriodQuestion(acroForm, certificate);
  }

  private void fillDiagnosisQuestion(PDAcroForm acroForm, Certificate certificate)
      throws IOException {
    pdfTextValueGenerator.generate(acroForm, certificate, QUESTION_SYMPTOM_ID, DIAGNOSIS_ID);
  }

  private void fillPeriodQuestion(PDAcroForm acroForm, Certificate certificate) throws IOException {
    final var question = certificate.getElementDataById(QUESTION_PERIOD_ID);

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
            fillPeriod(acroForm, dateRange);
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        });
  }

  private void fillPeriod(PDAcroForm acroForm, DateRange dateRange) throws IOException {
    PdfGeneratorValueToolkit.setCheckedBoxValue(acroForm, getPeriodCheckboxId(dateRange));
    PdfGeneratorValueToolkit.setValue(acroForm, getPeriodFromId(dateRange),
        dateRange.from().toString());
    PdfGeneratorValueToolkit.setValue(acroForm, getPeriodToId(dateRange),
        dateRange.to().toString());
  }

  private String getPeriodCheckboxId(DateRange dateRange) {
    return CHECKBOX_PERIOD_PREFIX_ID + getFieldSuffixFromDateRange(dateRange) + "[0]";
  }

  private String getPeriodToId(DateRange dateRange) {
    return DATE_TO_PERIOD_PREFIX_ID + getFieldSuffixFromDateRange(dateRange) + "[0]";
  }

  private String getPeriodFromId(DateRange dateRange) {
    return DATE_FROM_PERIOD_PREFIX_ID + getFieldSuffixFromDateRange(dateRange) + "[0]";
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
