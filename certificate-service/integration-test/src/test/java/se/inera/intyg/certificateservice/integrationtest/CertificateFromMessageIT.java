package se.inera.intyg.certificateservice.integrationtest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO.SIGNED;
import static se.inera.intyg.certificateservice.application.testdata.TestDataIncomingMessage.incomingComplementDTOBuilder;
import static se.inera.intyg.certificateservice.application.testdata.TestDataIncomingMessage.incomingComplementMessageBuilder;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultGetCertificateFromMessageRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultGetCertificateMessageRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultTestablilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.certificateId;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.messageId;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.questions;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public abstract class CertificateFromMessageIT extends BaseIntegrationIT {

  protected abstract String type();

  protected abstract String typeVersion();

  protected abstract String questionId();

  @Test
  @DisplayName("Ska returnera intyget om det finns")
  void shallReturnCertificate() {
    final var testCertificates = testabilityApi.addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED)
    );

    api.receiveMessage(
        incomingComplementMessageBuilder()
            .certificateId(certificateId(testCertificates))
            .complements(List.of(incomingComplementDTOBuilder()
                .questionId(questionId())
                .build()))
            .build()
    );

    final var messagesForCertificate = api.getMessagesForCertificate(
        defaultGetCertificateMessageRequest(),
        certificateId(testCertificates)
    );

    final var questions = questions(messagesForCertificate.getBody());

    final var response = api.certificateFromMessage(
        defaultGetCertificateFromMessageRequest(),
        messageId(questions.get(0))
    );

    assertEquals(
        certificateId(testCertificates),
        certificateId(response.getBody())
    );
  }

  @Test
  @DisplayName("Om intyget inte finns i tjänsten skall felkod 400 (BAD_REQUEST) returneras")
  void shallNotReturnCertificateIfMissing() {
    final var response = api.certificateFromMessage(
        defaultGetCertificateFromMessageRequest(),
        "message-not-exists");

    assertEquals(400, response.getStatusCode().value());
  }
}
