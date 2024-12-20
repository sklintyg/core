package se.inera.intyg.certificateprintservice.print.element;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

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
            <div style="position: absolute;
            left: 1cm;
            bottom: 15mm;
            border: red solid 1px;
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
            <div style="position: absolute;
            width: 100%;
            left: 20cm;
            bottom: 15mm;
            border: red solid 1px;
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
             <div style="border: red solid 1px; float: right; text-align: right;">
              <span style="font-weight: bold;">Person- /samordningsnr</span>
              <br><span>191212121212</span>
             </div>
            </div>""",
        InformationElementFactory.personId(text).toString()
    );
  }

  @Test
  void shouldReturnTitle() {
    final var text = "Certificate title";

    assertEquals(
        """
            <div style="font-size: 14pt;
            font-weight: bold;
            border-bottom: black solid 0.5px;
            padding-bottom: 1mm;
            ">
             <span>Certificate title</span>
            </div>""",
        InformationElementFactory.title(text).toString()
    );
  }

}