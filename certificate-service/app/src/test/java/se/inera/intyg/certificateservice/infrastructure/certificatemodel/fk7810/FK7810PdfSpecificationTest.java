package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.FK7810PdfSpecification.PDF_FK_7810_PDF;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.FK7810PdfSpecification.PDF_NO_ADDRESS_FK7810_PDF;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfSignature;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfTagIndex;

class FK7810PdfSpecificationTest {

  @Test
  void shallIncludePdfTemplatePathWithAddress() {
    final var pdfSpecification = FK7810PdfSpecification.create();

    assertEquals(PDF_FK_7810_PDF, pdfSpecification.pdfTemplatePath());
  }

  @Test
  void shallIncludePdfTemplatePathNoAddress() {
    final var pdfSpecification = FK7810PdfSpecification.create();

    assertEquals(PDF_NO_ADDRESS_FK7810_PDF,
        pdfSpecification.pdfNoAddressTemplatePath());
  }

  @Test
  void shallIncludePatientFieldId() {
    final var expected = List.of(
        new PdfFieldId("form1[0].#subform[0].flt_txtPersonNr[0]"),
        new PdfFieldId("form1[0].Sida2[0].flt_txtPersonNr[0]"),
        new PdfFieldId("form1[0].Sida3[0].flt_txtPersonNr[0]"),
        new PdfFieldId("form1[0].Sida4[0].flt_txtPersonNr[0]"),
        new PdfFieldId("form1[0].#subform[5].flt_txtPersonNr[1]"),
        new PdfFieldId("form1[0].#subform[6].flt_txtPersonNr[2]")
    );

    final var pdfSpecification = FK7810PdfSpecification.create();

    assertEquals(expected, pdfSpecification.patientIdFieldIds());
  }

  @Test
  void shallIncludeSignatureFields() {
    final var expected = PdfSignature.builder()
        .signaturePageIndex(4)
        .signatureWithAddressTagIndex(new PdfTagIndex(24))
        .signatureWithoutAddressTagIndex(new PdfTagIndex(24))
        .signedDateFieldId(new PdfFieldId("form1[0].#subform[5].flt_datUnderskrift[0]"))
        .signedByNameFieldId(new PdfFieldId("form1[0].#subform[5].flt_txtNamnfortydligande[0]"))
        .paTitleFieldId(new PdfFieldId("form1[0].#subform[5].flt_txtBefattning[0]"))
        .specialtyFieldId(
            new PdfFieldId("form1[0].#subform[5].flt_txtEventuellSpecialistkompetens[0]"))
        .hsaIdFieldId(new PdfFieldId("form1[0].#subform[5].flt_txtHSAid[0]"))
        .workplaceCodeFieldId(new PdfFieldId("form1[0].#subform[5].flt_txtArbetsplatskod[0]"))
        .contactInformation(
            new PdfFieldId("form1[0].#subform[5].flt_txtVardgivarensNamnAdressTelefon[0]"))
        .build();

    final var pdfSpecification = FK7810PdfSpecification.create();

    assertEquals(expected, pdfSpecification.signature());
  }

  @Test
  void shallIncludeMcid() {
    final var expected = 200;
    final var pdfSpecification = FK7810PdfSpecification.create();
    assertEquals(expected, pdfSpecification.pdfMcid().value());
  }

  @Test
  void shallIncludeOverflowPageIndex() {
    final var expected = 5;
    final var pdfSpecification = FK7810PdfSpecification.create();
    assertEquals(expected, pdfSpecification.overFlowPageIndex().value());
  }

  @Test
  void shallIncludeHasPageNumberFalse() {
    final var pdfSpecification = FK7810PdfSpecification.create();
    assertFalse(pdfSpecification.hasPageNbr());
  }
}