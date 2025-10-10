package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804;

import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionContentProvider;

public class FK7804CertificateCreateDraftFromCertificateContentProvider implements
    CertificateActionContentProvider {

  private static final String NAME = "Skapa AG7804";
  private static final String DESCRIPTION = "Skapar ett intyg till arbetsgivaren utifrån Försäkringskassans intyg.";
  private static final String BODY = """
      <div><div class="ic-alert ic-alert--status ic-alert--info">
      <i class="ic-alert__icon ic-info-icon"></i>
      Kom ihåg att stämma av med patienten om hen vill att du skickar Läkarintyget för sjukpenning till Försäkringskassan. Gör detta i så fall först.</div>
      <p class='iu-pt-400'>Skapa ett Läkarintyg om arbetsförmåga - arbetsgivaren (AG7804) utifrån ett Läkarintyg för sjukpenning innebär att informationsmängder som är gemensamma för båda intygen automatiskt förifylls.
      </p></div>
      """;

  @Override
  public String body(Certificate certificate) {
    return BODY;
  }

  @Override
  public String name(Certificate certificate) {
    return NAME;
  }

  @Override
  public String description(Certificate certificate) {
    return DESCRIPTION;
  }
}