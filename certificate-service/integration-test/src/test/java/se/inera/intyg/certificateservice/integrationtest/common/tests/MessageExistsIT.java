package se.inera.intyg.certificateservice.integrationtest.common.tests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO.SIGNED;
import static se.inera.intyg.certificateservice.application.testdata.TestDataIncomingMessage.incomingComplementDTOBuilder;
import static se.inera.intyg.certificateservice.application.testdata.TestDataIncomingMessage.incomingComplementMessageBuilder;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultGetCertificateMessageRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultTestablilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.certificateId;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.exists;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.messageId;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.questions;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.integrationtest.common.setup.BaseIntegrationIT;

public abstract class MessageExistsIT extends BaseIntegrationIT {

  @Test
  @DisplayName("Om meddelandet finns så returneras true")
  void shallReturnTrueIfMessageExists() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED)
    );

    api().receiveMessage(
        incomingComplementMessageBuilder()
            .certificateId(certificateId(testCertificates))
            .complements(List.of(incomingComplementDTOBuilder()
                .questionId(questionId())
                .build()))
            .build()
    );

    final var messagesForCertificate = api().getMessagesForCertificate(
        defaultGetCertificateMessageRequest(),
        certificateId(testCertificates)
    );

    final var questions = questions(messagesForCertificate.getBody());

    final var response = api().messageExists(
        messageId(questions.get(0))
    );

    assertTrue(
        exists(response.getBody()),
        "Should return true when message exists!"
    );
  }

  @Test
  @DisplayName("Om meddelandet inte finns lagrat så returneras false")
  void shallReturnFalseIfMessageDoesntExist() {
    final var response = api().messageExists("message-not-exists");

    assertFalse(
        exists(response.getBody()),
        "Should return false when message doesnt exists!"
    );
  }
}
