package se.inera.intyg.certificateprintservice.playwright.element;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.Metadata;

class HeaderConverterTest {

  @Test
  void shouldReturnHeader() {
    final var metadata = Metadata.builder()
        .name("Name")
        .typeId("TypeId")
        .version("1.0")
        .recipientName("Recipient name")
        .recipientLogo("Logo".getBytes())
        .isSent(true)
        .applicationOrigin("Webcert")
        .description("Description")
        .personId("191212121212")
        .fileName("File name")
        .certificateId("123ABC")
        .build();

    assertEquals(
        "<div style=\"position: absolute;\n"
            + "width: 100%;\n"
            + "left: 20cm;\n"
            + "bottom: 15mm;\n"
            + "border: red solid 1px;\n"
            + "font-size: 10pt;\n"
            + "transform: rotate(-90deg) translateY(-50%);\n"
            + "transform-origin: top left;\n"
            + "\">\n"
            + " <span>Intygs-ID: 123ABC</span>\n"
            + "</div>\n"
            + "<div style=\"display: grid; width: 100%; font-size: 10pt;\" title=\"headerElement\">\n"
            + " <div style=\"  margin: 10mm 20mm 10mm 20mm;\n"
            + "  display: flex;\n"
            + "  border: green solid 1px;\n"
            + "\">\n"
            + "  <div>\n"
            + "   <img src=\"data:image/png;base64, TG9nbw==\" alt=\"recipient-logo\" style=\"max-height: 15mm; max-width: 35mm; border: blue solid 1px;\">\n"
            + "  </div>\n"
            + "  <div style=\"width: 100%\">\n"
            + "   <div style=\"border: red solid 1px; float: right; text-align: right;\">\n"
            + "    <span style=\"font-weight: bold;\">Person- /samordningsnr</span>\n"
            + "    <br><span>191212121212</span>\n"
            + "   </div>\n"
            + "  </div>\n"
            + " </div>\n"
            + " <div style=\"margin: 0 20mm 10mm 20mm;\">\n"
            + "  <div style=\"font-size: 14pt;\n"
            + "font-weight: bold;\n"
            + "border-bottom: black solid 0.5px;\n"
            + "padding-bottom: 1mm;\n"
            + "\">\n"
            + "   <span>Name (TypeId v1.0)</span>\n"
            + "  </div>\n"
            + "  <div style=\"margin-top: 5mm;\n"
            + "padding: 3mm 5mm;\n"
            + "border: red solid 1px;\n"
            + "\">\n"
            + "   <span>Detta är en utskrift av ett elektroniskt intyg. Intyget har signerats elektroniskt av intygsutfärdaren. Notera att intyget redan har skickats till Recipient name.</span>\n"
            + "  </div>\n"
            + " </div>\n"
            + "</div>\n"
            + "<div style=\"position: absolute;\n"
            + "left: 1cm;\n"
            + "bottom: 15mm;\n"
            + "border: red solid 1px;\n"
            + "font-size: 10pt;\n"
            + "transform: rotate(-90deg) translateY(-50%);\n"
            + "transform-origin: top left;\n"
            + "\">\n"
            + " <span>TypeId - Fastställd av Recipient name</span>\n"
            + "</div>\n"
            + "<div style=\"position: absolute;\n"
            + "top: 50%;\n"
            + "left: 50%;\n"
            + "transform: translateX(-50%) translateY(-50%) rotate(315deg);\n"
            + "font-size: 100pt;\n"
            + "color: rgb(128, 128, 128);\n"
            + "opacity: 0.5;\n"
            + "z-index: -1;\n"
            + "\">\n"
            + " UTKAST\n"
            + "</div>",
        HeaderConverter.header(
            metadata,
            true
        )
    );
  }
}