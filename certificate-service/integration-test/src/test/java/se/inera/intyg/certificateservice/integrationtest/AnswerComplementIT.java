package se.inera.intyg.certificateservice.integrationtest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO.SIGNED;
import static se.inera.intyg.certificateservice.application.testdata.TestDataIncomingMessage.incomingComplementDTOBuilder;
import static se.inera.intyg.certificateservice.application.testdata.TestDataIncomingMessage.incomingComplementMessageBuilder;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultAnswerComplementRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultGetCertificateMessageRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultSendCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultTestablilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.certificateId;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.questions;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public abstract class AnswerComplementIT extends BaseIntegrationIT {

  protected abstract String type();

  protected abstract String typeVersion();

  protected abstract String questionId();

  @Test
  @DisplayName("Skall markera komplettering som hanterad om besvarad med meddelande")
  void shallSetComplementedQuestionsToHandledWitAnswers() {
    final var testCertificates = testabilityApi.addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED)
    );

    api.sendCertificate(
        defaultSendCertificateRequest(),
        certificateId(testCertificates)
    );

    api.receiveMessage(
        incomingComplementMessageBuilder()
            .certificateId(certificateId(testCertificates))
            .complements(List.of(incomingComplementDTOBuilder()
                .questionId(questionId())
                .build()))
            .build()
    );

    api.answerComplement(
        defaultAnswerComplementRequest(),
        certificateId(testCertificates)
    );

    final var messagesForCertificate = api.getMessagesForCertificate(
        defaultGetCertificateMessageRequest(),
        certificateId(testCertificates)
    );

    assertTrue(
        questions(messagesForCertificate.getBody()).get(0).isHandled(),
        "Should return true when answer complement is handled!"
    );
  }
}
