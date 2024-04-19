package se.inera.intyg.certificateservice.pdfboxgenerator;

import java.io.IOException;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.pdfboxgenerator.value.PdfDateRangeListValueGenerator;
import se.inera.intyg.certificateservice.pdfboxgenerator.value.PdfTextValueGenerator;


public class FK7443PdfGenerator implements PdfCertificateFillService {

  public static final String DIAGNOSIS_FIELD_ID = "form1[0].#subform[0].flt_txtDiagnos[0]";
  public static final ElementId QUESTION_SYMPTOM_ID = new ElementId("2");
  public static final ElementId QUESTION_PERIOD_ID = new ElementId("3");
  public static final String PATIENT_ID = "form1[0].#subform[0].flt_txtPersonNrBarn[0]";
  public static final String PERIOD_FIELD_NAME_PREFIX = "form1[0].#subform[0]";

  private PdfTextValueGenerator pdfTextValueGenerator;
  private PdfDateRangeListValueGenerator pdfDateRangeListValueGenerator;

  @Override
  public CertificateType getType() {
    return new CertificateType("fk7443");
  }

  @Override
  public String getPatientIdFormId() {
    return PATIENT_ID;
  }

  @Override
  public void fillDocument(PDAcroForm acroForm, Certificate certificate) throws IOException {
    pdfTextValueGenerator = new PdfTextValueGenerator();
    pdfDateRangeListValueGenerator = new PdfDateRangeListValueGenerator();
    fillDiagnosisQuestion(acroForm, certificate);
    fillPeriodQuestion(acroForm, certificate);
  }

  private void fillPeriodQuestion(PDAcroForm acroForm, Certificate certificate) {
    pdfDateRangeListValueGenerator.generate(
        acroForm, certificate, QUESTION_PERIOD_ID, PERIOD_FIELD_NAME_PREFIX
    );
  }

  private void fillDiagnosisQuestion(PDAcroForm acroForm, Certificate certificate)
      throws IOException {
    pdfTextValueGenerator.generate(acroForm, certificate, QUESTION_SYMPTOM_ID, DIAGNOSIS_FIELD_ID);
  }


}
