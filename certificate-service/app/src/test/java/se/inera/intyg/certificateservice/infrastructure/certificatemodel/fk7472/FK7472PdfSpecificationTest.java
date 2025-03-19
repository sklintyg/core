package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7472;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7472.FK7472PdfSpecification.PDF_FK_7472_PDF;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7472.FK7472PdfSpecification.PDF_NO_ADDRESS_FK_7472_PDF;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfSignature;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfTagIndex;

class FK7472PdfSpecificationTest {

  @Test
  void shallIncludePdfTemplatePathWithAddress() {
    final var certificateModel = FK7472PdfSpecification.create();

    assertEquals(PDF_FK_7472_PDF, certificateModel.pdfTemplatePath());
  }

  @Test
  void shallIncludePdfTemplatePathNoAddress() {
    final var certificateModel = FK7472PdfSpecification.create();

    assertEquals(PDF_NO_ADDRESS_FK_7472_PDF,
        certificateModel.pdfNoAddressTemplatePath());
  }

  @Test
  void shallIncludePatientFieldId() {
    final var expected = List.of(new PdfFieldId("form1[0].#subform[0].flt_txtPersonNrBarn[0]"));

    final var certificateModel = FK7472PdfSpecification.create();

    assertEquals(expected, certificateModel.patientIdFieldIds());
  }

  @Test
  void shallIncludeSignatureFields() {
    final var expected = PdfSignature.builder()
        .signaturePageIndex(0)
        .signatureWithAddressTagIndex(new PdfTagIndex(50))
        .signatureWithoutAddressTagIndex(new PdfTagIndex(47))
        .signedDateFieldId(new PdfFieldId("form1[0].#subform[0].flt_datUnderskrift[0]"))
        .signedByNameFieldId(new PdfFieldId("form1[0].#subform[0].flt_txtNamnfortydligande[0]"))
        .paTitleFieldId(new PdfFieldId("form1[0].#subform[0].flt_txtBefattning[0]"))
        .specialtyFieldId(
            new PdfFieldId("form1[0].#subform[0].flt_txtEventuellSpecialistkompetens[0]"))
        .hsaIdFieldId(new PdfFieldId("form1[0].#subform[0].flt_txtLakarensHSA-ID[0]"))
        .workplaceCodeFieldId(new PdfFieldId("form1[0].#subform[0].flt_txtArbetsplatskod[0]"))
        .contactInformation(
            new PdfFieldId("form1[0].#subform[0].flt_txtVardgivarensNamnAdressTelefon[0]"))
        .build();

    final var certificateModel = FK7472PdfSpecification.create();

    assertEquals(expected, certificateModel.signature());
  }

  @Test
  void shallIncludeMcid() {
    final var expected = 120;
    final var certificateModel = FK7472PdfSpecification.create();

    assertEquals(expected, certificateModel.pdfMcid().value());
  }
}