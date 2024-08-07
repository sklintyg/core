package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7472;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfMcid;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfSignature;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfTagIndex;

public class FK7472PdfSpecification {

  public static final String PDF_FK_7472_PDF = "fk7472/pdf/fk7472_v1.pdf";
  public static final String PDF_NO_ADDRESS_FK_7472_PDF = "fk7472/pdf/fk7472_v1_no_address.pdf";
  private static final PdfFieldId PDF_PATIENT_ID_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[0].flt_txtPersonNrBarn[0]");
  private static final PdfMcid PDF_PDF_MCID = new PdfMcid(120);
  private static final PdfTagIndex PDF_SIGNATURE_WITH_ADDRESS_TAG_INDEX = new PdfTagIndex(50);
  private static final PdfTagIndex PDF_SIGNATURE_WITHOUT_ADDRESS_TAG_INDEX = new PdfTagIndex(42);
  private static final int PDF_SIGNATURE_PAGE_INDEX = 0;
  private static final PdfFieldId PDF_SIGNED_DATE_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[0].flt_datUnderskrift[0]");
  private static final PdfFieldId PDF_SIGNED_BY_NAME_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[0].flt_txtNamnfortydligande[0]");
  private static final PdfFieldId PDF_PA_TITLE_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[0].flt_txtBefattning[0]");
  private static final PdfFieldId PDF_SPECIALTY_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[0].flt_txtEventuellSpecialistkompetens[0]");
  private static final PdfFieldId PDF_HSA_ID_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[0].flt_txtLakarensHSA-ID[0]");
  private static final PdfFieldId PDF_WORKPLACE_CODE_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[0].flt_txtArbetsplatskod[0]");
  private static final PdfFieldId PDF_CONTACT_INFORMATION = new PdfFieldId(
      "form1[0].#subform[0].flt_txtVardgivarensNamnAdressTelefon[0]");

  public static PdfSpecification create() {
    return PdfSpecification.builder()
        .pdfTemplatePath(PDF_FK_7472_PDF)
        .pdfNoAddressTemplatePath(PDF_NO_ADDRESS_FK_7472_PDF)
        .patientIdFieldIds(List.of(PDF_PATIENT_ID_FIELD_ID))
        .signature(PdfSignature.builder()
            .signaturePageIndex(PDF_SIGNATURE_PAGE_INDEX)
            .signatureWithAddressTagIndex(PDF_SIGNATURE_WITH_ADDRESS_TAG_INDEX)
            .signatureWithoutAddressTagIndex(PDF_SIGNATURE_WITHOUT_ADDRESS_TAG_INDEX)
            .signedDateFieldId(PDF_SIGNED_DATE_FIELD_ID)
            .signedByNameFieldId(PDF_SIGNED_BY_NAME_FIELD_ID)
            .paTitleFieldId(PDF_PA_TITLE_FIELD_ID)
            .specialtyFieldId(PDF_SPECIALTY_FIELD_ID)
            .hsaIdFieldId(PDF_HSA_ID_FIELD_ID)
            .workplaceCodeFieldId(PDF_WORKPLACE_CODE_FIELD_ID)
            .contactInformation(PDF_CONTACT_INFORMATION)
            .build())
        .pdfMcid(PDF_PDF_MCID)
        .build();
  }
}
