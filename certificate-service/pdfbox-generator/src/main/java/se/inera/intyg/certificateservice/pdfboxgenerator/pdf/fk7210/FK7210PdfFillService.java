package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.fk7210;

import static se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfConstants.CHECKED_BOX_VALUE;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.common.model.Role;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.CertificateTypePdfFillService;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfField;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value.PdfDateValueGenerator;


@Service
@RequiredArgsConstructor
public class FK7210PdfFillService implements CertificateTypePdfFillService {

  public static final String PATIENT_ID_FIELD_ID = "form1[0].#subform[0].flt_pnr[0]";

  public static final String BERAKNAT_FODELSEDATUM_FIELD_ID = "form1[0].#subform[0].flt_dat[0]";
  public static final String CERTIFIER_DOCTOR_FIELD_ID = "form1[0].#subform[0].ksr_kryssruta[0]";
  public static final String CERTIFIER_MIDWIFE_FIELD_ID = "form1[0].#subform[0].ksr_kryssruta[1]";
  public static final String CERTIFIER_NURSE_FIELD_ID = "form1[0].#subform[0].ksr_kryssruta[2]";
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
    return 18;
  }

  @Override
  public String getPatientIdFieldId() {
    return PATIENT_ID_FIELD_ID;
  }

  @Override
  public List<PdfField> getFields(Certificate certificate) {
    final var expectedDelivery = setExpectedDeliveryDate(certificate);
    final var issuer = setIssuerRole(certificate);

    return Stream.concat(expectedDelivery.stream(), issuer.stream()).toList();
  }

  private List<PdfField> setExpectedDeliveryDate(Certificate certificate) {
    return pdfDateValueGenerator.generate(
        certificate,
        QUESTION_BERAKNAT_FODELSEDATUM_ID,
        BERAKNAT_FODELSEDATUM_FIELD_ID
    );
  }

  private List<PdfField> setIssuerRole(Certificate certificate) {
    final var role = certificate.certificateMetaData().issuer().role();
    final var id = getIssuerId(role);

    return id.isEmpty() ? Collections.emptyList() :
        List.of(
            PdfField.builder()
                .id(id)
                .value(CHECKED_BOX_VALUE)
                .build()
        );
  }

  private String getIssuerId(Role role) {
    switch (role) {
      case DOCTOR, PRIVATE_DOCTOR -> {
        return CERTIFIER_DOCTOR_FIELD_ID;
      }
      case MIDWIFE -> {
        return CERTIFIER_MIDWIFE_FIELD_ID;
      }
      case NURSE -> {
        return CERTIFIER_NURSE_FIELD_ID;
      }
      default -> {
        return "";
      }
    }
  }
}
