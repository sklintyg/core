package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfSignature;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfTagIndex;

class FK7427PdfSpecificationTest {

  @Test
  void shallIncludePdfTemplatePathWithAddress() {
    final var pdfSpecification = FK7427PdfSpecification.create();

    assertEquals(FK7427PdfSpecification.PDF_FK_7427_PDF, pdfSpecification.pdfTemplatePath());
  }

  @Test
  void shallIncludePdfTemplatePathNoAddress() {
    final var pdfSpecification = FK7427PdfSpecification.create();

    assertEquals(FK7427PdfSpecification.PDF_FK_7427_PDF_NO_ADDRESS,
        pdfSpecification.pdfNoAddressTemplatePath());
  }

  @Test
  void shallIncludePatientFieldIds() {
    final var expected = List.of(
        new PdfFieldId("form1[0].#subform[0].flt_txtPersonNrBarnet[0]"),
        new PdfFieldId("form1[0].#subform[2].flt_txtPersonNrBarnet[1]"),
        new PdfFieldId("form1[0].#subform[3].flt_txtPersonNrBarnet[2]")
    );

    final var pdfSpecification = FK7427PdfSpecification.create();

    assertEquals(expected, pdfSpecification.patientIdFieldIds());
  }

  @Test
  void shallIncludeSignatureFields() {
    final var expected = PdfSignature.builder()
        .signaturePageIndex(1)
        .signatureWithAddressTagIndex(new PdfTagIndex(28))
        .signatureWithoutAddressTagIndex(new PdfTagIndex(28))
        .signedDateFieldId(new PdfFieldId("form1[0].#subform[2].flt_datUnderskrift[0]"))
        .signedByNameFieldId(new PdfFieldId("form1[0].#subform[2].flt_txtNamnfortydligande[0]"))
        .paTitleFieldId(new PdfFieldId("form1[0].#subform[2].flt_txtBefattning[0]"))
        .specialtyFieldId(
            new PdfFieldId("form1[0].#subform[2].flt_txtEventuellSpecialistkompetens[0]"))
        .hsaIdFieldId(new PdfFieldId("form1[0].#subform[2].flt_txtLakarensHSA-ID[0]"))
        .workplaceCodeFieldId(new PdfFieldId("form1[0].#subform[2].flt_txtArbetsplatskod[0]"))
        .contactInformation(
            new PdfFieldId("form1[0].#subform[2].flt_txtVardgivarensNamnAdressTelefon[0]"))
        .build();

    final var pdfSpecification = FK7427PdfSpecification.create();

    assertEquals(expected, pdfSpecification.signature());
  }

  @Test
  void shallIncludeMcid() {
    final var expected = 300;
    final var pdfSpecification = FK7427PdfSpecification.create();

    assertEquals(expected, pdfSpecification.pdfMcid().value());
  }

  @Test
  void shallIncludeOverflowPageIndex() {
    final var expected = 2;
    final var pdfSpecification = FK7427PdfSpecification.create();

    assertEquals(expected, pdfSpecification.overFlowPageIndex().value());
  }

  @Test
  void shallIncludeHasPageNumberFalse() {
    final var pdfSpecification = FK7427PdfSpecification.create();

    assertFalse(pdfSpecification.hasPageNbr());
  }
}
