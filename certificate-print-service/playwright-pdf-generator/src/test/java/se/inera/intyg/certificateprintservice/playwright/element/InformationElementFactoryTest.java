package se.inera.intyg.certificateprintservice.playwright.element;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.Metadata;

class InformationElementFactoryTest {

  @Test
  void shouldReturnAlert() {
    final var text = "Test text";

    assertEquals(
        """
            <div style="margin-top: 5mm;
            padding: 3mm 5mm;
            border: red solid 1px;
            ">
             <span>Test text</span>
            </div>""",
        InformationElementFactory.alert(text).toString()
    );
  }

  @Test
  void shouldReturnWatermark() {
    final var text = "UTKAST";

    assertEquals(
        """
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
        InformationElementFactory.watermark(text).toString()
    );
  }

  @Test
  void shouldReturnLeftMargin() {
    final var text = "Left margin text";

    assertEquals(
        """
            <div style="font-family: 'Liberation Sans', sans-serif;
            position: absolute;
            left: 1cm;
            bottom: 35mm;
            font-size: 10pt;
            transform: rotate(-90deg) translateY(-50%);
            transform-origin: top left;
            ">
             <span>Left margin text</span>
            </div>""",
        InformationElementFactory.leftMargin(text).toString()
    );
  }

  @Test
  void shouldReturnRightMargin() {
    final var text = "Right margin text";

    assertEquals(
        """
            <div style="font-family: 'Liberation Sans', sans-serif;
            position: absolute;
            width: 100%;
            left: 20cm;
            bottom: 35mm;
            font-size: 10pt;
            transform: rotate(-90deg) translateY(-50%);
            transform-origin: top left;
            ">
             <span>Right margin text</span>
            </div>""",
        InformationElementFactory.rightMargin(text).toString()
    );
  }

  @Test
  void shouldReturnPersonId() {
    final var text = "191212121212";

    assertEquals(
        """
            <div style="width: 100%">
             <div style="float: right; text-align: right;">
              <span style="font-weight: bold;">Person- /samordningsnr</span>
              <br><span>191212121212</span>
             </div>
            </div>""",
        InformationElementFactory.personId(text).toString()
    );
  }

  @Test
  void shouldReturnTitle() {
    assertEquals(
        """
            <div style="font-size: 14pt;
            padding-bottom: 1mm;
            border-bottom: black solid 1px;
            ">
             <span style="font-weight: bold;">Certificate name</span><span> (TS v1.0)</span>
            </div>""",
        InformationElementFactory.title(Metadata.builder()
            .name("Certificate name")
            .typeId("TS")
            .version("1.0")
            .build()).toString()
    );
  }

}