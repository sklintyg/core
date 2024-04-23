package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.fk7443;

import java.util.List;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.CertificateTypePdfFillService;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfField;
import se.inera.intyg.certificateservice.pdfboxgenerator.value.PdfDateRangeListValueGenerator;
import se.inera.intyg.certificateservice.pdfboxgenerator.value.PdfTextValueGenerator;


@Service
@RequiredArgsConstructor
public class FK7443PdfFillService implements CertificateTypePdfFillService {

  public static final String SYMPTOM_FIELD_ID = "form1[0].#subform[0].flt_txtDiagnos[0]";
  public static final ElementId QUESTION_SYMPTOM_ID = new ElementId("2");
  public static final ElementId QUESTION_PERIOD_ID = new ElementId("3");
  public static final String PATIENT_ID_FIELD_ID = "form1[0].#subform[0].flt_txtPersonNrBarn[0]";
  public static final String PERIOD_FIELD_ID_PREFIX = "form1[0].#subform[0]";

  private final PdfTextValueGenerator pdfTextValueGenerator;
  private final PdfDateRangeListValueGenerator pdfDateRangeListValueGenerator;

  @Override
  public CertificateType getType() {
    return new CertificateType("fk7443");
  }

  @Override
  public String getPatientIdFieldId() {
    return PATIENT_ID_FIELD_ID;
  }

  @Override
  public List<PdfField> getFields(Certificate certificate) {
    final var diagnosis = fillDiagnosisQuestion(certificate);
    final var period = fillPeriodQuestion(certificate);

    return Stream.concat(diagnosis.stream(), period.stream()).toList();
  }

  private List<PdfField> fillPeriodQuestion(Certificate certificate) {
    return pdfDateRangeListValueGenerator.generate(
        certificate, QUESTION_PERIOD_ID, PERIOD_FIELD_ID_PREFIX
    );
  }

  private List<PdfField> fillDiagnosisQuestion(Certificate certificate) {
    return pdfTextValueGenerator.generate(certificate, QUESTION_SYMPTOM_ID, SYMPTOM_FIELD_ID);
  }
}
