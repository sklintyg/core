package se.inera.intyg.certificateservice.domain.testdata;

import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfMcid;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfTagIndex;

public class TestDataPdfSpecificationConstants {

  //7210
  public static final PdfFieldId FK7210_PDF_PATIENT_ID_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[0].flt_txtPersonNr[0]");
  public static final PdfFieldId FK7210_PDF_FODELSEDATUM_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[0].flt_dat[0]");
  public static final ElementId FK7210_QUESTION_BERAKNAT_FODELSEDATUM_ID = new ElementId("54");
  public static final PdfMcid FK_7210_PDF_PDF_MCID = new PdfMcid(100);
  public static final PdfTagIndex FK7210_PDF_SIGNATURE_WITH_ADDRESS_TAG_INDEX = new PdfTagIndex(15);
  public static final PdfTagIndex FK7210_PDF_SIGNATURE_WITHOUT_ADDRESS_TAG_INDEX = new PdfTagIndex(
      7);
  public static final int FK7210_PDF_SIGNATURE_PAGE_INDEX = 0;

  public static final PdfFieldId FK7210_PDF_SIGNED_DATE_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[0].flt_datUnderskrift[0]");
  public static final PdfFieldId FK7210_PDF_SIGNED_BY_NAME_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[0].flt_txtNamnfortydligande[0]");
  public static final PdfFieldId FK7210_PDF_PA_TITLE_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[0].flt_txtBefattning[0]");
  public static final PdfFieldId FK7210_PDF_SPECIALTY_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[0].flt_txtEventuellSpecialistkompetens[0]");
  public static final PdfFieldId FK7210_PDF_HSA_ID_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[0].flt_txtLakarensHSA-ID[0]");
  public static final PdfFieldId FK7210_PDF_WORKPLACE_CODE_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[0].flt_txtArbetsplatskod[0]");
  public static final PdfFieldId FK7210_PDF_CONTACT_INFORMATION = new PdfFieldId(
      "form1[0].#subform[0].flt_txtVardgivarensNamnAdressTelefon[0]");

  //3226
  public static final PdfFieldId FK3226_PDF_PATIENT_ID_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[0].flt_txtPnr[0]");
  public static final PdfMcid FK_3226_PDF_PDF_MCID = new PdfMcid(100);
  public static final int FK3226_PDF_SIGNATURE_PAGE_INDEX = 1;
  public static final PdfTagIndex FK3226_PDF_SIGNATURE_WITH_ADDRESS_TAG_INDEX = new PdfTagIndex(15);
  public static final PdfTagIndex FK3226_PDF_SIGNATURE_WITHOUT_ADDRESS_TAG_INDEX = new PdfTagIndex(
      7);
  public static final PdfFieldId FK3226_PDF_STATEMENT_BASED_ON_FIELD_ID_PREFIX = new PdfFieldId(
      "form1[0].#subform[0].");
  public static final PdfFieldId FK3226_PDF_STATEMENT_BASED_ON_OTHER_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[0].flt_txtAnnatAngeVad[0]");
  public static final PdfFieldId FK3226_PDF_DIAGNOSIS_FIELD_ID_PREFIX = new PdfFieldId(
      "form1[0].#subform[0].flt_txt");
  public static final PdfFieldId FK3226_PDF_TREATMENT_AND_CARE_SITUATION_FIELD_ID_PREFIX = new PdfFieldId(
      "form1[0].#subform[1].ksr_");
  public static final PdfFieldId FK3226_PDF_WHEN_ACTIVE_TREATMENT_WAS_STOPPED_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[1].flt_datumBehandlingenAvslutad[0]");
  public static final PdfFieldId FK3226_PDF_WHEN_CONDITION_BECAME_LIFE_THREATENING_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[1].flt_datumAkutLivshotande[0]");
  public static final PdfFieldId FK3226_PDF_TANGIBLE_THREAT_TO_PATIENTS_LIFE_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[1].flt_txtSjukdomstillstand[0]");
  public static final PdfFieldId FK3226_PDF_ESTIMATE_HOW_LONG_CONDITION_WILL_BE_LIFE_THREATENING_FIELD_ID_PREFIX = new PdfFieldId(
      "form1[0].#subform[1].ksr_");
  public static final PdfFieldId FK3226_PDF_CONDITION_IS_LIFE_THREATENING_TO_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[1].flt_datumTillMed[0]");
  public static final PdfFieldId FK3226_PDF_CONDITION_IS_LIFE_THREATENING_OTHER_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[1].flt_txtBeskrivSjukdomstillstandet[0]");
  public static final PdfFieldId FK3226_PDF_CAN_PATIENT_CONSENT_FIELD_ID_PREFIX = new PdfFieldId(
      "form1[0].#subform[1].ksr_");

  public static final PdfFieldId FK3226_PDF_STATEMENT_BASED_ON_INVESTIGATION_CHECKBOX_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[0].ksr_UndersokningPatient[0]");
  public static final PdfFieldId FK3226_PDF_STATEMENT_BASED_ON_JOURNAL_CHECKBOX_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[0].ksr_Journaluppgifter[0]");
  public static final PdfFieldId FK3226_PDF_STATEMENT_BASED_ON_OTHER_CHECKBOX_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[0].ksr_Annat[0]");
  public static final PdfFieldId FK3226_PDF_STATEMENT_BASED_ON_INVESTIGATION_DATE_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[0].flt_datUl_1[0]");
  public static final PdfFieldId FK3226_PDF_STATEMENT_BASED_ON_JOURNAL_DATE_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[0].flt_datUl_2[0]");
  public static final PdfFieldId FK3226_PDF_STATEMENT_BASED_ON_OTHER_DATE_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[0].flt_datUl_3[0]");
  public static final PdfFieldId FK3226_PDF_DIAGNOSE_ID_1 = new PdfFieldId(
      "form1[0].#subform[0].flt_txtDiagnoser[0]");
  public static final PdfFieldId FK_3226_PDF_CODE_ID_1_1 = new PdfFieldId(
      "form1[0].#subform[0].flt_txtDiaKod1[0]");
  public static final PdfFieldId FK_3226_PDF_CODE_ID_1_2 = new PdfFieldId(
      "form1[0].#subform[0].flt_txtDiaKod2[0]");
  public static final PdfFieldId FK_3226_PDF_CODE_ID_1_3 = new PdfFieldId(
      "form1[0].#subform[0].flt_txtDiaKod3[0]");
  public static final PdfFieldId FK_3226_PDF_CODE_ID_1_4 = new PdfFieldId(
      "form1[0].#subform[0].flt_txtDiaKod4[0]");
  public static final PdfFieldId FK_3226_PDF_CODE_ID_1_5 = new PdfFieldId(
      "form1[0].#subform[0].flt_txtDiaKod5[0]");

  public static final PdfFieldId FK_3226_PDF_DIAGNOSE_ID_2 = new PdfFieldId(
      "form1[0].#subform[0].flt_txtDiagnoser2[0]");
  public static final PdfFieldId FK_3226_PDF_CODE_ID_2_1 = new PdfFieldId(
      "form1[0].#subform[0].flt_txtDiaKod6[0]");
  public static final PdfFieldId FK_3226_PDF_CODE_ID_2_2 = new PdfFieldId(
      "form1[0].#subform[0].flt_txtDiaKod7[0]");
  public static final PdfFieldId FK_3226_PDF_CODE_ID_2_3 = new PdfFieldId(
      "form1[0].#subform[0].flt_txtDiaKod8[0]");
  public static final PdfFieldId FK_3226_PDF_CODE_ID_2_4 = new PdfFieldId(
      "form1[0].#subform[0].flt_txtDiaKod9[0]");
  public static final PdfFieldId FK_3226_PDF_CODE_ID_2_5 = new PdfFieldId(
      "form1[0].#subform[0].flt_txtDiaKod10[0]");

  public static final PdfFieldId FK_3226_PDF_DIAGNOSE_ID_3 = new PdfFieldId(
      "form1[0].#subform[0].flt_txtDiagnoser3[0]");
  public static final PdfFieldId FK_3226_PDF_CODE_ID_3_1 = new PdfFieldId(
      "form1[0].#subform[0].flt_txtDiaKod11[0]");
  public static final PdfFieldId FK_3226_PDF_CODE_ID_3_2 = new PdfFieldId(
      "form1[0].#subform[0].flt_txtDiaKod12[0]");
  public static final PdfFieldId FK_3226_PDF_CODE_ID_3_3 = new PdfFieldId(
      "form1[0].#subform[0].flt_txtDiaKod13[0]");
  public static final PdfFieldId FK_3226_PDF_CODE_ID_3_4 = new PdfFieldId(
      "form1[0].#subform[0].flt_txtDiaKod14[0]");
  public static final PdfFieldId FK_3226_PDF_CODE_ID_3_5 = new PdfFieldId(
      "form1[0].#subform[0].flt_txtDiaKod15[0]");

  public static final PdfFieldId FK_3226_PDF_DIAGNOSE_ID_4 = new PdfFieldId(
      "form1[0].#subform[0].flt_txtDiagnoser4[0]");
  public static final PdfFieldId FK_3226_PDF_CODE_ID_4_1 = new PdfFieldId(
      "form1[0].#subform[0].flt_txtDiaKod16[0]");
  public static final PdfFieldId FK_3226_PDF_CODE_ID_4_2 = new PdfFieldId(
      "form1[0].#subform[0].flt_txtDiaKod17[0]");
  public static final PdfFieldId FK_3226_PDF_CODE_ID_4_3 = new PdfFieldId(
      "form1[0].#subform[0].flt_txtDiaKod18[0]");
  public static final PdfFieldId FK_3226_PDF_CODE_ID_4_4 = new PdfFieldId(
      "form1[0].#subform[0].flt_txtDiaKod19[0]");
  public static final PdfFieldId FK_3226_PDF_CODE_ID_4_5 = new PdfFieldId(
      "form1[0].#subform[0].flt_txtDiaKod20[0]");

  public static final PdfFieldId FK_3226_PDF_DIAGNOSE_ID_5 = new PdfFieldId(
      "form1[0].#subform[0].flt_txtDiagnoser5[0]");
  public static final PdfFieldId FK_3226_PDF_CODE_ID_5_1 = new PdfFieldId(
      "form1[0].#subform[0].flt_txtDiaKod21[0]");
  public static final PdfFieldId FK_3226_PDF_CODE_ID_5_2 = new PdfFieldId(
      "form1[0].#subform[0].flt_txtDiaKod22[0]");
  public static final PdfFieldId FK_3226_PDF_CODE_ID_5_3 = new PdfFieldId(
      "form1[0].#subform[0].flt_txtDiaKod23[0]");
  public static final PdfFieldId FK_3226_PDF_CODE_ID_5_4 = new PdfFieldId(
      "form1[0].#subform[0].flt_txtDiaKod24[0]");
  public static final PdfFieldId FK_3226_PDF_CODE_ID_5_5 = new PdfFieldId(
      "form1[0].#subform[0].flt_txtDiaKod25[0]");

  public static final FieldId FK3226_UTLATANDE_BASERAT_PA_ANNAT_FIELD_ID = new FieldId("annat");
  public static final FieldId FK3226_UTLATANDE_BASERAT_PA_JOURNALUPPGIFTER_FIELD_ID = new FieldId(
      "journaluppgifter");
  public static final FieldId FK3226_UTLATANDE_BASERAT_PA_UNDERSOKNING_AV_PATIENTEN_FIELD_ID = new FieldId(
      "undersokningAvPatienten");
  public static final ElementId FK3226_DIAGNOSIS_ID = new ElementId("58");
  public static final ElementId FK3226_QUESTION_PATIENTENS_BEHANDLING_OCH_VARDSITUATION_ID = new ElementId(
      "52");
  public static final ElementId FK3226_QUESTION_UTLATANDE_BASERAT_PA_ID = new ElementId("1");
  public static final ElementId FK3226_QUESTION_UTLATANDE_BASERAT_PA_ANNAT_ID = new ElementId(
      "1.3");
  public static final ElementId FK3226_QUESTION_NAR_TILLSTANDET_BLEV_AKUT_LIVSHOTANDE_ID = new ElementId(
      "52.3");
  public static final ElementId FK3226_QUESTION_NAR_AKTIVA_BEHANDLINGEN_AVSLUTADES_ID = new ElementId(
      "52.2");
  public static final ElementId FK3226_QUESTION_PATAGLIGT_HOT_MOT_PATIENTENS_LIV_AKUT_LIVSHOTANDE_ID = new ElementId(
      "52.4");
  public static final ElementId FK3226_QUESTION_UPSKATTA_HUR_LANGE_TILLSTANDET_KOMMER_VARA_LIVSHOTANDE_ID = new ElementId(
      "52.5");
  public static final ElementId FK3226_QUESTION_TILLSTANDET_UPPSKATTAS_LIVSHOTANDE_TILL_OCH_MED_ID = new ElementId(
      "52.6");
  public static final ElementId FK3226_QUESTION_PATAGLIGT_HOT_MOT_PATIENTENS_LIV_ANNAT_ID = new ElementId(
      "52.7");
  public static final ElementId FK3226_FORUTSATTNINGAR_FOR_ATT_LAMNA_SKRIFTLIGT_SAMTYCKE_ID = new ElementId(
      "53");

  public static final FieldId FK3226_ENDAST_PALLIATIV_FIELD_ID = new FieldId("ENDAST_PALLIATIV");
  public static final FieldId FK3226_AKUT_LIVSHOTANDE_FIELD_ID = new FieldId("AKUT_LIVSHOTANDE");
  public static final FieldId FK3226_ANNAT_FIELD_ID = new FieldId("ANNAT");
  public static final PdfFieldId FK3226_PDF_ONLY_PALLIATIVE_CARE_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[1].ksr_PalliativVard[0]");
  public static final PdfFieldId FK3226_PDF_ACUTE_LIFE_THREATENING_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[1].ksr_AkutLivshotande[0]");
  public static final PdfFieldId FK3226_PDF_OTHER_THREAT_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[1].ksr_Annat2[0]");

  public static final FieldId FK3226_QUESTION_UPSKATTA_HUR_LANGE_TILLSTANDET_KOMMER_VARA_LIVSHOTANDE_FIELD_ID = new FieldId(
      "52.5");
  public static final PdfFieldId FK3226_PDF_ESTIMATE_YES_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[1].ksr_Ja[0]");
  public static final PdfFieldId FK3226_PDF_ESTIMATE_NO_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[1].ksr_Nej[0]");
  public static final FieldId FK3226_FORUTSATTNINGAR_FOR_ATT_LAMNA_SKRIFTLIGT_SAMTYCKE_FIELD_ID = new FieldId(
      "53.1");
  public static final PdfFieldId FK3226_PDF_CAN_CONSENT_YES_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[1].ksr_Ja_Modul3[0]");
  public static final PdfFieldId FK3226_PDF_CAN_CONSENT_NO_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[1].ksr_Nej_Modul3[0]");

  public static final FieldId FK3226_DIAGNOS_1 = new FieldId("huvuddiagnos");
  public static final FieldId FK_3226_DIAGNOS_2 = new FieldId("diagnos2");
  public static final FieldId FK_3226_DIAGNOS_3 = new FieldId("diagnos3");
  public static final FieldId FK_3226_DIAGNOS_4 = new FieldId("diagnos4");
  public static final FieldId FK_3226_DIAGNOS_5 = new FieldId("diagnos5");

  public static final PdfFieldId FK3226_PDF_SIGNED_DATE_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[1].flt_datUnderskrift[0]");
  public static final PdfFieldId FK3226_PDF_SIGNED_BY_NAME_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[1].flt_txtNamnfortydligande[0]");
  public static final PdfFieldId FK3226_PDF_SIGNED_BY_PA_TITLE = new PdfFieldId(
      "form1[0].#subform[1].flt_txtBefattning[0]");
  public static final PdfFieldId FK3226_PDF_SIGNED_BY_SPECIALTY = new PdfFieldId(
      "form1[0].#subform[1].flt_txtEventuellSpecialistkompetens[0]");
  public static final PdfFieldId FK3226_PDF_HSA_ID_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[1].flt_txtLakarensHSA-ID[0]");
  public static final PdfFieldId FK3226_PDF_WORKPLACE_CODE_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[1].flt_txtArbetsplatskod[0]");
  public static final PdfFieldId FK3226_PDF_CONTACT_INFORMATION = new PdfFieldId(
      "form1[0].#subform[1].flt_txtVardgivarensNamnAdressTelefon[0]");

  //7472
  public static final ElementId FK7472_QUESTION_SYMPTOM_ID = new ElementId("55");
  public static final ElementId FK7472_QUESTION_PERIOD_ID = new ElementId("56");

  public static final String FK7472_PDF_PATH = "fk7472/pdf/fk7472_v1.pdf";
  public static final String FK7472_PDF_PATH_NO_ADDRESS = "fk7472/pdf/fk7472_v1_no_address.pdf";

  public static final PdfMcid FK_7472_PDF_PDF_MCID = new PdfMcid(120);
  public static final int FK7472_PDF_SIGNATURE_PAGE_INDEX = 0;
  public static final PdfTagIndex FK7472_PDF_SIGNATURE_WITH_ADDRESS_TAG_INDEX = new PdfTagIndex(50);
  public static final PdfTagIndex FK7472_PDF_SIGNATURE_WITHOUT_ADDRESS_TAG_INDEX = new PdfTagIndex(
      42);

  public static final PdfFieldId FK7472_PDF_SIGNED_DATE_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[0].flt_datUnderskrift[0]");
  public static final PdfFieldId FK7472_PDF_SIGNED_BY_NAME_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[0].flt_txtNamnfortydligande[0]");
  public static final PdfFieldId FK7472_PDF_PA_TITLE_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[0].flt_txtBefattning[0]");
  public static final PdfFieldId FK7472_PDF_SPECIALTY_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[0].flt_txtEventuellSpecialistkompetens[0]");
  public static final PdfFieldId FK7472_PDF_HSA_ID_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[0].flt_txtLakarensHSA-ID[0]");
  public static final PdfFieldId FK7472_PDF_WORKPLACE_CODE_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[0].flt_txtArbetsplatskod[0]");
  public static final PdfFieldId FK7472_PDF_CONTACT_INFORMATION = new PdfFieldId(
      "form1[0].#subform[0].flt_txtVardgivarensNamnAdressTelefon[0]");

  public static final PdfFieldId FK7472_PDF_PATIENT_ID_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[0].flt_txtPersonNrBarn[0]");
  public static final PdfFieldId FK7472_PDF_SYMPTOM_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[0].flt_txtDiagnos[0]");
  public static final PdfFieldId FK7472_PDF_PERIOD_FIELD_ID_PREFIX = new PdfFieldId(
      "form1[0].#subform[0]");

  public static final String FK7809_PDF_PATH = "fk7809/pdf/fk7809_v1_no_address.pdf"; //TODO: update when new template has been provided
  public static final String FK7809_PDF_PATH_NO_ADDRESS = "fk7809/pdf/fk7809_v1_no_address.pdf";
  public static final PdfMcid FK7809_PDF_MCID = new PdfMcid(200);
  public static final int FK7809_PDF_SIGNATURE_PAGE_INDEX = 3;
  public static final PdfTagIndex FK7809_PDF_SIGNATURE_WITH_ADDRESS_TAG_INDEX = new PdfTagIndex(10);
  public static final PdfTagIndex FK7809_PDF_SIGNATURE_WITHOUT_ADDRESS_TAG_INDEX = new PdfTagIndex(
      10);
  public static final PdfFieldId FK7809_PDF_PATIENT_ID_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[0].flt_txtPersonNr[0]");
  public static final PdfFieldId FK7809_PDF_SIGNED_DATE_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[3].flt_datUnderskrift[0]");
  public static final PdfFieldId FK7809_PDF_SIGNED_BY_NAME_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[3].flt_txtNamnfortydligande[0]");
  public static final PdfFieldId FK7809_PDF_SIGNED_BY_PA_TITLE = new PdfFieldId(
      "form1[0].#subform[3].flt_txtBefattning[0]");
  public static final PdfFieldId FK7809_PDF_SIGNED_BY_SPECIALTY = new PdfFieldId(
      "form1[0].#subform[3].flt_txtEventuellSpecialistkompetens[0]");
  public static final PdfFieldId FK7809_PDF_HSA_ID_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[3].flt_txtLakarensHSA-ID[0]");
  public static final PdfFieldId FK7809_PDF_WORKPLACE_CODE_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[3].flt_txtArbetsplatskod[0]");
  public static final PdfFieldId FK7809_PDF_CONTACT_INFORMATION = new PdfFieldId(
      "form1[0].#subform[3].flt_txtVardgivarensNamnAdressTelefon[0]");
  public static final PdfFieldId FK7809_PDF_FIELD_OVERFLOW_SHEET = new PdfFieldId(
      "form1[0].#subform[4].flt_txtFortsattningsblad[0]");
}
