package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3221;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfSignature;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfTagIndex;

class FK3221PdfSpecificationTest {

  @Test
  void shallIncludePdfTemplatePathWaitAddress() {
    final var pdfSpecification = FK3221PdfSpecification.create();

    assertEquals(FK3221PdfSpecification.PDF_FK_3221_PDF, pdfSpecification.pdfTemplatePath());
  }

  @Test
  void shallIncludePdfTemplatePathNoAddress() {
    final var pdfSpecification = FK3221PdfSpecification.create();

    assertEquals(FK3221PdfSpecification.PDF_NO_ADDRESS_FK_3221_PDF,
        pdfSpecification.pdfNoAddressTemplatePath());
  }

  @Test
  void shallIncludePatientFieldId() {
    final var expected = List.of(
        new PdfFieldId("form1[0].#subform[0].flt_txtPersonNr[0]"),
        new PdfFieldId("form1[0].#subform[1].flt_txtPersonNr[1]"),
        new PdfFieldId("form1[0].#subform[2].flt_txtPersonNr[2]"),
        new PdfFieldId("form1[0].#subform[3].flt_txtPersonNr[3]"),
        new PdfFieldId("form1[0].#subform[4].flt_txtPersonNr[4]")
    );

    final var pdfSpecification = FK3221PdfSpecification.create();

    assertEquals(expected, pdfSpecification.patientIdFieldIds());
  }

  @Test
  void shallIncludeSignatureFields() {
    final var expected = PdfSignature.builder()
        .signaturePageIndex(3)
        .signatureWithAddressTagIndex(new PdfTagIndex(10))
        .signatureWithoutAddressTagIndex(new PdfTagIndex(10))
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

    final var pdfSpecification = FK3221PdfSpecification.create();

    assertEquals(expected, pdfSpecification.signature());
  }

  @Test
  void shallIncludeOverflowPageIndex() {
    final var expected = 4;
    final var pdfSpecification = FK3221PdfSpecification.create();

    assertEquals(expected, pdfSpecification.overFlowPageIndex().value());
  }

  @Test
  void shallIncludeHasPageNumberFalse() {
    final var pdfSpecification = FK3221PdfSpecification.create();

    assertFalse(pdfSpecification.hasPageNbr());
  }

  @Test
  void shallIncludeMcid() {
    final var expected = 200;
    final var pdfSpecification = FK3221PdfSpecification.create();

    assertEquals(expected, pdfSpecification.pdfMcid().value());
  }
}