package se.inera.intyg.certificateservice.pdfboxgenerator;

import java.io.IOException;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.pdfboxgenerator.value.PdfDateRangeListValueGenerator;
import se.inera.intyg.certificateservice.pdfboxgenerator.value.PdfTextValueGenerator;


public class FK7443PdfGenerator implements PdfCertificateFillService {

  private static final String DIAGNOSIS_ID = "form1[0].#subform[0].flt_txtDiagnos[0]";
  private static final ElementId QUESTION_SYMPTOM_ID = new ElementId("2");
  private static final ElementId QUESTION_PERIOD_ID = new ElementId("3");
  private static final String PATIENT_ID = "form1[0].#subform[0].flt_txtPersonNrBarn[0]";
  private static final String DIAGNOSIS_FIELD_NAME = "form1[0].#subform[0]";

  private final PdfTextValueGenerator pdfTextValueGenerator = new PdfTextValueGenerator();
  private final PdfDateRangeListValueGenerator pdfDateRangeListValueGenerator = new PdfDateRangeListValueGenerator();

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
    fillDiagnosisQuestion(acroForm, certificate);
    fillPeriodQuestion(acroForm, certificate);
  }

  private void fillPeriodQuestion(PDAcroForm acroForm, Certificate certificate) throws IOException {
    pdfDateRangeListValueGenerator.generate(
        acroForm, certificate, QUESTION_PERIOD_ID, DIAGNOSIS_FIELD_NAME
    );
  }

  private void fillDiagnosisQuestion(PDAcroForm acroForm, Certificate certificate)
      throws IOException {
    pdfTextValueGenerator.generate(acroForm, certificate, QUESTION_SYMPTOM_ID, DIAGNOSIS_ID);
  }


}
