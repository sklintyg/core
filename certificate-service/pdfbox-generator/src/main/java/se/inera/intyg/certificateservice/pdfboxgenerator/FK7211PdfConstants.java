package se.inera.intyg.certificateservice.pdfboxgenerator;

public class FK7211PdfConstants {

  public static final String PATIENT_NAME_FIELD_ID =
      "form1[0].#subform[0].flt_txtNormal[0]";
  public static final String PATIENT_ID_FIELD_ID =
      "form1[0].#subform[0].flt_pnr[0]";
  public static final String QUESTION_BERAKNAT_NEDKOMSTDATUM_DATE_FIELD_ID =
      "form1[0].#subform[0].flt_dat[0]";
  public static final String QUESTION_BERAKNAT_NEDKOMSTDATUM_CERTIFIER_DOCTOR_FIELD_ID =
      "form1[0].#subform[0].ksr_kryssruta[0]";
  public static final String QUESTION_BERAKNAT_NEDKOMSTDATUM_CERTIFIER_MIDWIFE_FIELD_ID =
      "form1[0].#subform[0].ksr_kryssruta[1]";
  public static final String QUESTION_BERAKNAT_NEDKOMSTDATUM_CERTIFIER_NURSE_FIELD_ID =
      "form1[0].#subform[0].ksr_kryssruta[2]";
  public static final String SIGNATURE_DATE_FIELD_ID =
      "form1[0].#subform[0].flt_datUnderskrift[0]";
  public static final float SIGNATURE_SIGNATURE_POSITION_X = 171;
  public static final float SIGNATURE_SIGNATURE_POSITION_Y = 522;
  public static final String SIGNATURE_FULL_NAME_FIELD_ID =
      "form1[0].#subform[0].flt_txtNamnfortydligande[0]";
  public static final String SIGNATURE_PA_TITLE_FIELD_ID =
      "form1[0].#subform[0].flt_txtBefattning[0]";
  public static final String SIGNATURE_SPECIALITY_FIELD_ID =
      "form1[0].#subform[0].flt_txtEventuellSpecialistkompetens[0]";
  public static final String SIGNATURE_HSA_ID_FIELD_ID =
      "form1[0].#subform[0].flt_txtLakarensHSA-ID[0]";
  public static final String SIGNATURE_WORKPLACE_CODE_FIELD_ID =
      "form1[0].#subform[0].flt_txtArbetsplatskod[0]";
  public static final String SIGNATURE_CERTIFIER_PERSON_ID_FIELD_ID =
      "form1[0].#subform[0].flt_txtPersonNrLakare[0]";
  public static final String SIGNATURE_CARE_UNIT_CONTACT_INFORMATION_FIELD_ID =
      "form1[0].#subform[0].flt_txtVardgivarensNamnAdressTelefon[0]";
  public static final String CHECKED_BOX_VALUE = "1";
  public static final String UNCHECKED_BOX_VALUE = "Off";
  public static final String WATERMARK_DRAFT = "UTKAST";
  public static final String DIGITALLY_SIGNED_TEXT =
      "Detta är en utskrift av ett elektroniskt intyg. "
          + "Intyget har signerats elektroniskt av intygsutfärdaren.";

}
