package se.inera.intyg.certificateprintservice.playwright.element;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.Metadata;
import se.inera.intyg.certificateprintservice.playwright.converters.FooterConverter;

class FooterConverterTest {


  @Test
  void shouldReturnCreatedHeader() {
    assertEquals("""
        <div style="font-family: 'Liberation Sans', sans-serif;
        height: 25mm;
        width: 100%;
        font-size: 10pt;
        margin: 0 20mm;
        border-top: black solid 1px;
        justify-content: space-between;
        display: flex;
        ">
         <div>
          <span style="display: block;
        margin-top: 5mm;
        margin-bottom: 2mm;
        ">Utskriften skapades med origin - en tjänst som drivs av Inera AB</span><a href="https://inera.se">www.inera.se</a>
         </div>
         <div style=" margin-top: 5mm;">
          <span class="pageNumber"></span><span> (</span><span class="totalPages"></span><span>)</span>
         </div>
        </div>""", FooterConverter.createFooter(Metadata.builder()
        .applicationOrigin("origin")
        .build()));
  }
}