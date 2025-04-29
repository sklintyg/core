package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfSignature;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfTagIndex;

class FK7426PdfSpecificationTest {

  @Test
  void shallIncludePdfTemplatePathWithAddress() {
    final var pdfSpecification = FK7426PdfSpecification.create();

    assertEquals(FK7426PdfSpecification.PDF_FK_7426_PDF, pdfSpecification.pdfTemplatePath());
  }

  @Test
  void shallIncludePdfTemplatePathNoAddress() {
    final var pdfSpecification = FK7426PdfSpecification.create();

    assertEquals(FK7426PdfSpecification.PDF_FK_7426_PDF_NO_ADDRESS,
        pdfSpecification.pdfNoAddressTemplatePath());
  }

  @Test
  void shallIncludePatientFieldIds() {
    final var expected = List.of(
        new PdfFieldId("form1[0].#subform[0].flt_txtBarnPersonNr[0]"),
        new PdfFieldId("form1[0].#subform[2].flt_txtBarnPersonNr[1]"),
        new PdfFieldId("form1[0].#subform[3].flt_txtBarnPersonNr[2]"),
        new PdfFieldId("form1[0].#subform[4].flt_txtBarnPersonNr[3]")
    );

    final var pdfSpecification = FK7426PdfSpecification.create();

    assertEquals(expected, pdfSpecification.patientIdFieldIds());
  }

  @Test
  void shallIncludeSignatureFields() {
    final var expected = PdfSignature.builder()
        .signaturePageIndex(2)
        .signatureWithAddressTagIndex(new PdfTagIndex(31))
        .signatureWithoutAddressTagIndex(new PdfTagIndex(31))
        .signedDateFieldId(new PdfFieldId("form1[0].#subform[3].flt_datUnderskrift[0]"))
        .signedByNameFieldId(new PdfFieldId("form1[0].#subform[3].flt_txtNamnfortydligande[0]"))
        .paTitleFieldId(new PdfFieldId("form1[0].#subform[3].flt_txtBefattning[0]"))
        .specialtyFieldId(
            new PdfFieldId("form1[0].#subform[3].flt_txtEventuellSpecialistkompetens[0]"))
        .hsaIdFieldId(new PdfFieldId("form1[0].#subform[3].flt_txtLakarensHSA-ID[0]"))
        .workplaceCodeFieldId(new PdfFieldId("form1[0].#subform[3].flt_txtArbetsplatskod[0]"))
        .contactInformation(
            new PdfFieldId("form1[0].#subform[3].flt_txtVardgivarensNamnAdressTelefon[0]"))
        .build();

    final var pdfSpecification = FK7426PdfSpecification.create();

    assertEquals(expected, pdfSpecification.signature());
  }

  @Test
  void shallIncludeMcid() {
    final var expected = 300;
    final var pdfSpecification = FK7426PdfSpecification.create();

    assertEquals(expected, pdfSpecification.pdfMcid().value());
  }

  @Test
  void shallIncludeOverflowPageIndex() {
    final var expected = 3;
    final var pdfSpecification = FK7426PdfSpecification.create();

    assertEquals(expected, pdfSpecification.overFlowPageIndex().value());
  }

  @Test
  void shallIncludeHasPageNumberFalse() {
    final var pdfSpecification = FK7426PdfSpecification.create();

    assertFalse(pdfSpecification.hasPageNbr());
  }
}
