package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.fk7210;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.CertificateTypePdfFillService;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfField;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value.PdfDateValueGenerator;


@Service
@RequiredArgsConstructor
public class FK7210PdfFillService implements CertificateTypePdfFillService {

  public static final String PATIENT_ID_FIELD_ID = "form1[0].#subform[0].flt_txtPersonNr[0]";

  public static final String BERAKNAT_FODELSEDATUM_FIELD_ID = "form1[0].#subform[0].flt_dat[0]";
  public static final ElementId QUESTION_BERAKNAT_FODELSEDATUM_ID = new ElementId("54");

  private final PdfDateValueGenerator pdfDateValueGenerator;

  @Override
  public CertificateType getType() {
    return new CertificateType("fk7210");
  }

  @Override
  public int getAvailableMcid() {
    return 100;
  }

  @Override
  public int getSignatureTagIndex() {
    return 8;
  }

  @Override
  public String getPatientIdFieldId() {
    return PATIENT_ID_FIELD_ID;
  }

  @Override
  public List<PdfField> getFields(Certificate certificate) {
    return setExpectedDeliveryDate(certificate);
  }

  private List<PdfField> setExpectedDeliveryDate(Certificate certificate) {
    return pdfDateValueGenerator.generate(
        certificate,
        QUESTION_BERAKNAT_FODELSEDATUM_ID,
        BERAKNAT_FODELSEDATUM_FIELD_ID
    );
  }
}
