package se.inera.intyg.certificateservice.domain.testdata;

import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK3226_PDF_PATH;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK3226_PDF_PATH_NO_ADDRESS;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7210_PDF_PATH;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7210_PDF_PATH_NO_ADDRESS;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK3226_PDF_CONTACT_INFORMATION;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK3226_PDF_HSA_ID_FIELD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK3226_PDF_PATIENT_ID_FIELD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK3226_PDF_SIGNATURE_PAGE_INDEX;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK3226_PDF_SIGNATURE_WITHOUT_ADDRESS_TAG_INDEX;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK3226_PDF_SIGNATURE_WITH_ADDRESS_TAG_INDEX;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK3226_PDF_SIGNED_BY_NAME_FIELD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK3226_PDF_SIGNED_BY_PA_TITLE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK3226_PDF_SIGNED_BY_SPECIALTY;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK3226_PDF_SIGNED_DATE_FIELD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK3226_PDF_WORKPLACE_CODE_FIELD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK7210_PDF_CONTACT_INFORMATION;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK7210_PDF_HSA_ID_FIELD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK7210_PDF_PATIENT_ID_FIELD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK7210_PDF_PA_TITLE_FIELD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK7210_PDF_SIGNATURE_PAGE_INDEX;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK7210_PDF_SIGNATURE_WITHOUT_ADDRESS_TAG_INDEX;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK7210_PDF_SIGNATURE_WITH_ADDRESS_TAG_INDEX;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK7210_PDF_SIGNED_BY_NAME_FIELD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK7210_PDF_SIGNED_DATE_FIELD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK7210_PDF_SPECIALTY_FIELD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK7210_PDF_WORKPLACE_CODE_FIELD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK7472_PDF_CONTACT_INFORMATION;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK7472_PDF_HSA_ID_FIELD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK7472_PDF_PATH;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK7472_PDF_PATH_NO_ADDRESS;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK7472_PDF_PATIENT_ID_FIELD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK7472_PDF_PA_TITLE_FIELD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK7472_PDF_SIGNATURE_PAGE_INDEX;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK7472_PDF_SIGNATURE_WITHOUT_ADDRESS_TAG_INDEX;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK7472_PDF_SIGNATURE_WITH_ADDRESS_TAG_INDEX;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK7472_PDF_SIGNED_BY_NAME_FIELD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK7472_PDF_SIGNED_DATE_FIELD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK7472_PDF_SPECIALTY_FIELD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK7472_PDF_WORKPLACE_CODE_FIELD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK7809_PDF_CONTACT_INFORMATION;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK7809_PDF_HSA_ID_FIELD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK7809_PDF_MCID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK7809_PDF_PATH;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK7809_PDF_PATH_NO_ADDRESS;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK7809_PDF_PATIENT_ID_FIELD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK7809_PDF_SIGNATURE_PAGE_INDEX;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK7809_PDF_SIGNATURE_WITHOUT_ADDRESS_TAG_INDEX;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK7809_PDF_SIGNATURE_WITH_ADDRESS_TAG_INDEX;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK7809_PDF_SIGNED_BY_NAME_FIELD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK7809_PDF_SIGNED_BY_PA_TITLE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK7809_PDF_SIGNED_BY_SPECIALTY;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK7809_PDF_SIGNED_DATE_FIELD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK7809_PDF_WORKPLACE_CODE_FIELD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK_3226_PDF_PDF_MCID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK_7210_PDF_PDF_MCID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK_7472_PDF_PDF_MCID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.OverflowPageIndex;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfSignature;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.TemplatePdfSpecification;

public class TestDataPdfSpecification {

  public static final PdfSpecification FK7210_PDF_SPECIFICATION = fk7210PdfSpecification();
  public static final PdfSpecification FK3226_PDF_SPECIFICATION = fk3226PdfSpecification();
  public static final PdfSpecification FK7472_PDF_SPECIFICATION = fk7472PdfSpecification();
  public static final PdfSpecification FK7809_PDF_SPECIFICATION = fk7809PdfSpecification();

  public static PdfSpecification fk7210PdfSpecification() {
    return TemplatePdfSpecification.builder()
        .signature(PdfSignature.builder()
            .signaturePageIndex(FK7210_PDF_SIGNATURE_PAGE_INDEX)
            .signatureWithAddressTagIndex(FK7210_PDF_SIGNATURE_WITH_ADDRESS_TAG_INDEX)
            .signatureWithoutAddressTagIndex(FK7210_PDF_SIGNATURE_WITHOUT_ADDRESS_TAG_INDEX)
            .signedDateFieldId(FK7210_PDF_SIGNED_DATE_FIELD_ID)
            .signedByNameFieldId(FK7210_PDF_SIGNED_BY_NAME_FIELD_ID)
            .paTitleFieldId(FK7210_PDF_PA_TITLE_FIELD_ID)
            .specialtyFieldId(FK7210_PDF_SPECIALTY_FIELD_ID)
            .hsaIdFieldId(FK7210_PDF_HSA_ID_FIELD_ID)
            .workplaceCodeFieldId(FK7210_PDF_WORKPLACE_CODE_FIELD_ID)
            .contactInformation(FK7210_PDF_CONTACT_INFORMATION)
            .build())
        .pdfTemplatePath(FK7210_PDF_PATH)
        .pdfNoAddressTemplatePath(FK7210_PDF_PATH_NO_ADDRESS)
        .patientIdFieldIds(List.of(FK7210_PDF_PATIENT_ID_FIELD_ID))
        .pdfMcid(FK_7210_PDF_PDF_MCID)
        .build();
  }

  public static PdfSpecification fk3226PdfSpecification() {
    return TemplatePdfSpecification.builder()
        .signature(PdfSignature.builder()
            .signaturePageIndex(FK3226_PDF_SIGNATURE_PAGE_INDEX)
            .signatureWithAddressTagIndex(FK3226_PDF_SIGNATURE_WITH_ADDRESS_TAG_INDEX)
            .signatureWithoutAddressTagIndex(FK3226_PDF_SIGNATURE_WITHOUT_ADDRESS_TAG_INDEX)
            .signedDateFieldId(FK3226_PDF_SIGNED_DATE_FIELD_ID)
            .signedByNameFieldId(FK3226_PDF_SIGNED_BY_NAME_FIELD_ID)
            .paTitleFieldId(FK3226_PDF_SIGNED_BY_PA_TITLE)
            .specialtyFieldId(FK3226_PDF_SIGNED_BY_SPECIALTY)
            .hsaIdFieldId(FK3226_PDF_HSA_ID_FIELD_ID)
            .workplaceCodeFieldId(FK3226_PDF_WORKPLACE_CODE_FIELD_ID)
            .contactInformation(FK3226_PDF_CONTACT_INFORMATION)
            .build())
        .pdfTemplatePath(FK3226_PDF_PATH)
        .pdfNoAddressTemplatePath(FK3226_PDF_PATH_NO_ADDRESS)
        .patientIdFieldIds(List.of(FK3226_PDF_PATIENT_ID_FIELD_ID))
        .pdfMcid(FK_3226_PDF_PDF_MCID)
        .build();
  }

  public static PdfSpecification fk7472PdfSpecification() {
    return TemplatePdfSpecification.builder()
        .signature(PdfSignature.builder()
            .signaturePageIndex(FK7472_PDF_SIGNATURE_PAGE_INDEX)
            .signatureWithAddressTagIndex(FK7472_PDF_SIGNATURE_WITH_ADDRESS_TAG_INDEX)
            .signatureWithoutAddressTagIndex(FK7472_PDF_SIGNATURE_WITHOUT_ADDRESS_TAG_INDEX)
            .signedDateFieldId(FK7472_PDF_SIGNED_DATE_FIELD_ID)
            .signedByNameFieldId(FK7472_PDF_SIGNED_BY_NAME_FIELD_ID)
            .paTitleFieldId(FK7472_PDF_PA_TITLE_FIELD_ID)
            .specialtyFieldId(FK7472_PDF_SPECIALTY_FIELD_ID)
            .hsaIdFieldId(FK7472_PDF_HSA_ID_FIELD_ID)
            .workplaceCodeFieldId(FK7472_PDF_WORKPLACE_CODE_FIELD_ID)
            .contactInformation(FK7472_PDF_CONTACT_INFORMATION)
            .build())
        .pdfTemplatePath(FK7472_PDF_PATH)
        .pdfNoAddressTemplatePath(FK7472_PDF_PATH_NO_ADDRESS)
        .patientIdFieldIds(List.of(FK7472_PDF_PATIENT_ID_FIELD_ID))
        .pdfMcid(FK_7472_PDF_PDF_MCID)
        .build();
  }

  public static PdfSpecification fk7809PdfSpecification() {
    return TemplatePdfSpecification.builder()
        .signature(PdfSignature.builder()
            .signaturePageIndex(FK7809_PDF_SIGNATURE_PAGE_INDEX)
            .signatureWithAddressTagIndex(FK7809_PDF_SIGNATURE_WITH_ADDRESS_TAG_INDEX)
            .signatureWithoutAddressTagIndex(FK7809_PDF_SIGNATURE_WITHOUT_ADDRESS_TAG_INDEX)
            .signedDateFieldId(FK7809_PDF_SIGNED_DATE_FIELD_ID)
            .signedByNameFieldId(FK7809_PDF_SIGNED_BY_NAME_FIELD_ID)
            .paTitleFieldId(FK7809_PDF_SIGNED_BY_PA_TITLE)
            .specialtyFieldId(FK7809_PDF_SIGNED_BY_SPECIALTY)
            .hsaIdFieldId(FK7809_PDF_HSA_ID_FIELD_ID)
            .workplaceCodeFieldId(FK7809_PDF_WORKPLACE_CODE_FIELD_ID)
            .contactInformation(FK7809_PDF_CONTACT_INFORMATION)
            .build())
        .pdfTemplatePath(FK7809_PDF_PATH)
        .pdfNoAddressTemplatePath(FK7809_PDF_PATH_NO_ADDRESS)
        .patientIdFieldIds(List.of(FK7809_PDF_PATIENT_ID_FIELD_ID))
        .pdfMcid(FK7809_PDF_MCID)
        .overFlowPageIndex(new OverflowPageIndex(4))
        .build();
  }
}
