package se.inera.intyg.certificateservice.pdfboxgenerator;

import java.io.IOException;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.pdfboxgenerator.value.PdfDateValueGenerator;
import se.inera.intyg.certificateservice.pdfboxgenerator.value.PdfValueGenerator;


public class FK7211PdfFillService implements CertificateTypePdfFillService {

    public static final String PATIENT_ID_FIELD_ID = "form1[0].#subform[0].flt_pnr[0]";

    public static final String BERAKNAT_NEDKOMSTDATUM_FIELD_ID = "form1[0].#subform[0].flt_dat[0]";
    public static final String CERTIFIER_DOCTOR_FIELD_ID = "form1[0].#subform[0].ksr_kryssruta[0]";
    public static final String CERTIFIER_MIDWIFE_FIELD_ID = "form1[0].#subform[0].ksr_kryssruta[1]";
    public static final String CERTIFIER_NURSE_FIELD_ID = "form1[0].#subform[0].ksr_kryssruta[2]";
    public static final ElementId QUESTION_BERAKNAT_NEDKOMSTDATUM_ID = new ElementId("1");

    private PdfValueGenerator pdfValueGenerator;
    private PdfDateValueGenerator pdfDateValueGenerator;

    @Override
    public CertificateType getType() {
        return new CertificateType("fk7211");
    }

    @Override
    public String getPatientIdFieldId() {
        return PATIENT_ID_FIELD_ID;
    }

    @Override
    public void fillDocument(PDAcroForm acroForm, Certificate certificate) throws IOException {
        pdfDateValueGenerator = new PdfDateValueGenerator();
        pdfValueGenerator = new PdfValueGenerator();
        setExpectedDeliveryDate(acroForm, certificate);
        setIssuerRole(acroForm, certificate);
    }

    private void setExpectedDeliveryDate(PDAcroForm acroForm, Certificate certificate)
        throws IOException {
        pdfDateValueGenerator.generate(acroForm,
            certificate,
            QUESTION_BERAKNAT_NEDKOMSTDATUM_ID,
            BERAKNAT_NEDKOMSTDATUM_FIELD_ID
        );
    }

    private void setIssuerRole(PDAcroForm acroForm, Certificate certificate) throws IOException {
        final var role = certificate.certificateMetaData().issuer().role();

        switch (role) {
            case DOCTOR, PRIVATE_DOCTOR, DENTIST -> pdfValueGenerator.setCheckedBoxValue(
                acroForm,
                CERTIFIER_DOCTOR_FIELD_ID
            );
            case MIDWIFE -> pdfValueGenerator.setCheckedBoxValue(
                acroForm,
                CERTIFIER_MIDWIFE_FIELD_ID
            );
            case NURSE -> pdfValueGenerator.setCheckedBoxValue(
                acroForm,
                CERTIFIER_NURSE_FIELD_ID
            );
            default -> {
            }
        }
    }
}
