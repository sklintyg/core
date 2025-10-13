package se.inera.intyg.certificateservice.integrationtest.common.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO.SIGNED;
import static se.inera.intyg.certificateservice.application.testdata.TestDataIncomingMessage.incomingComplementMessageBuilder;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultSendCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultTestablilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.certificateId;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.integrationtest.common.setup.BaseIntegrationIT;

public abstract class MessagingNotAvailableIT extends BaseIntegrationIT {


  @Test
  @DisplayName("Intyg skall inte kunna ta emot kompletteringsbegäran - 403 (FORBIDDEN)")
  void shallReturn403IfComplementIsReceived() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED)
    );

    api().sendCertificate(
        defaultSendCertificateRequest(),
        certificateId(testCertificates)
    );

    final var response = api().receiveMessage(
        incomingComplementMessageBuilder()
            .certificateId(
                certificateId(testCertificates)
            )
            .build()
    );

    assertEquals(403, response.getStatusCode().value());
  }
}
