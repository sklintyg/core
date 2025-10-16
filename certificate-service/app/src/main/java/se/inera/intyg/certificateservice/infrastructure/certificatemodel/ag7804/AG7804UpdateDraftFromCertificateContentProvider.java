package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804;

import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionContentProvider;

public class AG7804UpdateDraftFromCertificateContentProvider implements
    CertificateActionContentProvider {

  private static final String NAME = "Hjälp med ifyllnad?";
  private static final String BODY = """
      <p>Det finns ett Läkarintyg för sjukpenning för denna patient som är utfärdat <span class='iu-fw-bold'>%s</span> på en enhet som du har åtkomst till. Vill du kopiera de svar som givits i det intyget till detta intygsutkast?</p>
      """;

  @Override
  public String body(Certificate certificate) {
    if (certificate == null) {
      return null;
    }

    return certificate.candidateForUpdate()
        .map(cert -> BODY.formatted(cert.signed().toLocalDate()))
        .orElse(null);
  }

  @Override
  public String name(Certificate certificate) {
    return NAME;
  }
}