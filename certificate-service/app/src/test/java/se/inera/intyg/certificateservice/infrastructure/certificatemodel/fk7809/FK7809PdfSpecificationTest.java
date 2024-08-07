package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.FK7809PdfSpecification.PDF_FK_7809_PDF;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.FK7809PdfSpecification.PDF_NO_ADDRESS_FK_7809_PDF;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfSignature;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfTagIndex;

class FK7809PdfSpecificationTest {

  @Test
  void shallIncludePdfTemplatePathWithAddress() {
    final var pdfSpecification = FK7809PdfSpecification.create();

    assertEquals(PDF_FK_7809_PDF, pdfSpecification.pdfTemplatePath());
  }

  @Test
  void shallIncludePdfTemplatePathNoAddress() {
    final var pdfSpecification = FK7809PdfSpecification.create();

    assertEquals(PDF_NO_ADDRESS_FK_7809_PDF,
        pdfSpecification.pdfNoAddressTemplatePath());
  }

  @Test
  void shallIncludePatientFieldId() {
    final var expected = List.of(
        new PdfFieldId("form1[0].#subform[0].flt_txtPersonNr[0]"),
        new PdfFieldId("form1[0].Sida2[0].flt_txtPersonNr[0]"),
        new PdfFieldId("form1[0].Sida3[0].flt_txtPersonNr[0]"),
        new PdfFieldId("form1[0].Sida4[0].flt_txtPersonNr[0]"),
        new PdfFieldId("form1[0].#subform[4].flt_txtPersonNr[1]")
    );

    final var pdfSpecification = FK7809PdfSpecification.create();

    assertEquals(expected, pdfSpecification.patientIdFieldIds());
  }

  @Test
  void shallIncludeSignatureFields() {
    final var expected = PdfSignature.builder()
        .signaturePageIndex(3)
        .signatureWithAddressTagIndex(new PdfTagIndex(10))
        .signatureWithoutAddressTagIndex(new PdfTagIndex(10))
        .signedDateFieldId(new PdfFieldId("form1[0].Sida4[0].flt_datUnderskrift[0]"))
        .signedByNameFieldId(new PdfFieldId("form1[0].Sida4[0].flt_txtNamnfortydligande[0]"))
        .paTitleFieldId(new PdfFieldId("form1[0].Sida4[0].flt_txtBefattning[0]"))
        .specialtyFieldId(
            new PdfFieldId("form1[0].Sida4[0].flt_txtEventuellSpecialistkompetens[0]"))
        .hsaIdFieldId(new PdfFieldId("form1[0].Sida4[0].flt_txtLakarensHSA-ID[0]"))
        .workplaceCodeFieldId(new PdfFieldId("form1[0].Sida4[0].flt_txtArbetsplatskod[0]"))
        .contactInformation(
            new PdfFieldId("form1[0].Sida4[0].flt_txtVardgivarensNamnAdressTelefon[0]"))
        .build();

    final var pdfSpecification = FK7809PdfSpecification.create();

    assertEquals(expected, pdfSpecification.signature());
  }

  @Test
  void shallIncludeMcid() {
    final var expected = 200;
    final var pdfSpecification = FK7809PdfSpecification.create();

    assertEquals(expected, pdfSpecification.pdfMcid().value());
  }
}