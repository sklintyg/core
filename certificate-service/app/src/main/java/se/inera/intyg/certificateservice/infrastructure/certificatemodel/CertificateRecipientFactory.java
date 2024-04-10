package se.inera.intyg.certificateservice.infrastructure.certificatemodel;

import se.inera.intyg.certificateservice.domain.common.model.Recipient;
import se.inera.intyg.certificateservice.domain.common.model.RecipientId;

public class CertificateRecipientFactory {

  private CertificateRecipientFactory() {
    throw new IllegalStateException("Utility class");
  }

  public static Recipient fkassa() {
    return new Recipient(
        new RecipientId("FKASSA"),
        "Försäkringskassan"
    );
  }
}
