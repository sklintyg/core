package se.inera.intyg.certificateservice.infrastructure.certificatemodel.common;

import se.inera.intyg.certificateservice.domain.common.model.Recipient;
import se.inera.intyg.certificateservice.domain.common.model.RecipientId;

public class CertificateRecipientFactory {

  private CertificateRecipientFactory() {
    throw new IllegalStateException("Utility class");
  }

  public static Recipient fkassa(String logicalAddress) {
    return new Recipient(
        new RecipientId("FKASSA"),
        "Försäkringskassan",
        logicalAddress
    );
  }

  public static Recipient transp(String logicalAddress) {
    return new Recipient(
        new RecipientId("TRANSP"),
        "Transportstyrelsen",
        logicalAddress,
        "ts/transportstyrelsen-logo.png",
        "Läkarintyg Transportstyrelsen"
    );
  }

  public static Recipient skr() {
    return new Recipient(
        new RecipientId("SKR"),
        "Sveriges Kommuner och Regioner",
        null,
        "skr/skr_logo.png",
        "Läkarintyg om arbetsförmåga – arbetsgivaren"
    );
  }
}
