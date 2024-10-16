package se.inera.intyg.certificateservice.infrastructure.certificatemodel.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.common.model.Recipient;
import se.inera.intyg.certificateservice.domain.common.model.RecipientId;

class CertificateRecipientFactoryTest {

  @Test
  void shouldReturnFkassa() {
    final var expected = new Recipient(
        new RecipientId("FKASSA"),
        "Försäkringskassan",
        "Logisk adress"
    );

    final var response = CertificateRecipientFactory.fkassa("Logisk adress");

    assertEquals(expected, response);
  }

}