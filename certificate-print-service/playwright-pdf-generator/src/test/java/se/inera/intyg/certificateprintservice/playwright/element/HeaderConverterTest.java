package se.inera.intyg.certificateprintservice.playwright.element;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.Metadata;

class HeaderConverterTest {

  @Test
  void shouldReturnCreateHeader() {
    final var metadata = Metadata.builder()
        .name("Name")
        .typeId("TypeId")
        .version("1.0")
        .recipientName("Recipient name")
        .recipientLogo("Logo".getBytes())
        .applicationOrigin("Webcert")
        .description("Description")
        .personId("191212121212")
        .fileName("File name")
        .certificateId("123ABC")
        .build();

    assertEquals("""
            <div title="headerElement" style="display: grid;
            width: 17cm;
            font-size: 10pt;
            margin: 10mm 20mm 0 20mm;
            ">
             <div style="  margin: 0 0 10mm 0;
              display: flex;
            ">
              <div>
               <img src="data:image/png;base64, TG9nbw==" alt="recipient-logo" style="max-height: 15mm; max-width: 35mm;">
              </div>
              <div style="width: 100%">
               <div style="float: right; text-align: right;">
                <span style="font-weight: bold;">Person- /samordningsnr</span>
                <br><span>191212121212</span>
               </div>
              </div>
             </div>
             <div style="margin-bottom: 5mm;">
              <div style="font-size: 14pt;
            padding-bottom: 1mm;
            border-bottom: black solid 1px;
            ">
               <span style="font-weight: bold;">Name</span><span> (TypeId v1.0)</span>
              </div>
              <div style="margin-top: 5mm;
            padding: 3mm 5mm;
            border: red solid 1px;
            ">
               <span>Detta är en utskrift av ett elektroniskt intygsutkast och ska INTE skickas till Recipient name.</span>
              </div>
             </div>
            </div>
            <div style="position: absolute;
            left: 1cm;
            bottom: 35mm;
            font-size: 10pt;
            transform: rotate(-90deg) translateY(-50%);
            transform-origin: top left;
            ">
             <span>TypeId - Fastställd av Recipient name</span>
            </div>
            <div style="position: absolute;
            top: 50%;
            left: 50%;
            transform: translateX(-50%) translateY(-50%) rotate(315deg);
            font-size: 100pt;
            color: rgb(128, 128, 128);
            opacity: 0.5;
            z-index: -1;
            ">
             UTKAST
            </div>""",
        HeaderConverter.createHeader(metadata)
    );
  }
}