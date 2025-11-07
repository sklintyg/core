package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3221;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.OverflowPageIndex;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfMcid;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfSignature;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfTagIndex;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.TemplatePdfSpecification;

public class FK3221PdfSpecification {

  public static final Integer PDF_TEXT_FIELD_LENGTH = 52;
  public static final String PDF_FK_3221_PDF = "fk3221/pdf/fk3221_v1.pdf";
  public static final String PDF_NO_ADDRESS_FK_3221_PDF = "fk3221/pdf/fk3221_v1_no_address.pdf";
  public static final PdfMcid PDF_MCID = new PdfMcid(200);
  private static final int PDF_SIGNATURE_PAGE_INDEX = 3;
  private static final PdfTagIndex PDF_SIGNATURE_WITH_ADDRESS_TAG_INDEX = new PdfTagIndex(
      10);
  private static final PdfTagIndex PDF_SIGNATURE_WITHOUT_ADDRESS_TAG_INDEX = new PdfTagIndex(
      10);
  private static final List<PdfFieldId> PDF_PATIENT_ID_FIELD_IDS = List.of(
      new PdfFieldId("form1[0].#subform[0].flt_txtPersonNr[0]"),
      new PdfFieldId("form1[0].#subform[1].flt_txtPersonNr[1]"),
      new PdfFieldId("form1[0].#subform[2].flt_txtPersonNr[2]"),
      new PdfFieldId("form1[0].#subform[3].flt_txtPersonNr[3]"),
      new PdfFieldId("form1[0].#subform[4].flt_txtPersonNr[4]")
  );
  private static final PdfFieldId PDF_SIGNED_DATE_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[3].flt_datUnderskrift[0]");
  private static final PdfFieldId PDF_SIGNED_BY_NAME_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[3].flt_txtNamnfortydligande[0]");
  private static final PdfFieldId PDF_SIGNED_BY_PA_TITLE = new PdfFieldId(
      "form1[0].#subform[3].flt_txtBefattning[0]");
  private static final PdfFieldId PDF_SIGNED_BY_SPECIALTY = new PdfFieldId(
      "form1[0].#subform[3].flt_txtEventuellSpecialistkompetens[0]");
  private static final PdfFieldId PDF_HSA_ID_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[3].flt_txtLakarensHSA-ID[0]");
  private static final PdfFieldId PDF_WORKPLACE_CODE_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[3].flt_txtArbetsplatskod[0]");
  private static final PdfFieldId PDF_CONTACT_INFORMATION = new PdfFieldId(
      "form1[0].#subform[3].flt_txtVardgivarensNamnAdressTelefon[0]");

  private FK3221PdfSpecification() {
    throw new IllegalStateException("Utility class");
  }

  public static TemplatePdfSpecification create() {
    return TemplatePdfSpecification.builder()
        .pdfTemplatePath(PDF_FK_3221_PDF)
        .pdfNoAddressTemplatePath(PDF_NO_ADDRESS_FK_3221_PDF)
        .patientIdFieldIds(PDF_PATIENT_ID_FIELD_IDS)
        .pdfMcid(PDF_MCID)
        .signature(PdfSignature.builder()
            .signatureWithAddressTagIndex(PDF_SIGNATURE_WITH_ADDRESS_TAG_INDEX)
            .signatureWithoutAddressTagIndex(PDF_SIGNATURE_WITHOUT_ADDRESS_TAG_INDEX)
            .signaturePageIndex(PDF_SIGNATURE_PAGE_INDEX)
            .signedDateFieldId(PDF_SIGNED_DATE_FIELD_ID)
            .signedByNameFieldId(PDF_SIGNED_BY_NAME_FIELD_ID)
            .paTitleFieldId(PDF_SIGNED_BY_PA_TITLE)
            .specialtyFieldId(PDF_SIGNED_BY_SPECIALTY)
            .hsaIdFieldId(PDF_HSA_ID_FIELD_ID)
            .workplaceCodeFieldId(PDF_WORKPLACE_CODE_FIELD_ID)
            .contactInformation(PDF_CONTACT_INFORMATION)
            .build()
        )
        .hasPageNbr(false)
        .overFlowPageIndex(new OverflowPageIndex(4))
        .untaggedWatermarks(
            List.of("32210501", "FK 3221 (001 F 001) Fastställd av Försäkringskassan"))
        .build();
  }

}