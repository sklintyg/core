package se.inera.intyg.certificateprintservice.playwright.element;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ElementProviderTest {

  @Test
  void shouldReturnLogo() {
    final var logo = ElementProvider.logo("Test loog".getBytes());

    assertEquals(
        """
            <div>
             <img src="data:image/png;base64, VGVzdCBsb29n" alt="recipient-logo" style="max-height: 15mm; max-width: 35mm; border: blue solid 1px;">
            </div>""",
        logo.toString()
    );
  }

}