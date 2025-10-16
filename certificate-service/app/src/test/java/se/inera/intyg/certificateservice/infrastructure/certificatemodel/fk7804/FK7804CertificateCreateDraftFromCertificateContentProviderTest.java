package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class FK7804CertificateCreateDraftFromCertificateContentProviderTest {

  FK7804CertificateCreateDraftFromCertificateContentProvider provider = new FK7804CertificateCreateDraftFromCertificateContentProvider();

  @Test
  void shouldReturnBody() {
    assertEquals("""
        <div><div class="ic-alert ic-alert--status ic-alert--info">
        <i class="ic-alert__icon ic-info-icon"></i>
        Kom ihåg att stämma av med patienten om hen vill att du skickar Läkarintyget för sjukpenning till Försäkringskassan. Gör detta i så fall först.</div>
        <p class='iu-pt-400'>Skapa ett Läkarintyg om arbetsförmåga - arbetsgivaren (AG7804) utifrån ett Läkarintyg för sjukpenning innebär att informationsmängder som är gemensamma för båda intygen automatiskt förifylls.
        </p></div>
        """, provider.body(null));
  }

  @Test
  void shouldReturnDescription() {
    assertEquals("Skapar ett intyg till arbetsgivaren utifrån Försäkringskassans intyg.",
        provider.description(null));
  }

  @Test
  void shouldReturnName() {
    assertEquals("Skapa AG7804", provider.name(null));
  }
}