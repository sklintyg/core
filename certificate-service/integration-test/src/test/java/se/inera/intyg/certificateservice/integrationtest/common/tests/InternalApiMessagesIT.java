package se.inera.intyg.certificateservice.integrationtest.common.tests;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO.SIGNED;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.ATHENA_REACT_ANDERSSON_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataIncomingMessage.incomingComplementDTOBuilder;
import static se.inera.intyg.certificateservice.application.testdata.TestDataIncomingMessage.incomingComplementMessageBuilder;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultSendCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultTestablilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.certificateId;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.messages;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.GetSentInternalRequest;
import se.inera.intyg.certificateservice.integrationtest.common.setup.BaseIntegrationIT;

public abstract class InternalApiMessagesIT extends BaseIntegrationIT {

  @Test
  @DisplayName("Ärendekommunikation för intyget skall gå att hämta")
  void shallReturnQuestionsAndAnswers() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED)
    );

    api().sendCertificate(
        defaultSendCertificateRequest(),
        certificateId(testCertificates)
    );

    api().receiveMessage(
        incomingComplementMessageBuilder()
            .certificateId(certificateId(testCertificates))
            .complements(List.of(incomingComplementDTOBuilder()
                .questionId(questionId())
                .build()))
            .build()
    );

    final var response = internalApi().getMessages(
        certificateId(testCertificates)
    );

    final var messages = messages(response.getBody());

    assertAll(
        () -> assertEquals(1, messages.size()),
        () -> assertEquals(certificateId(testCertificates), messages.getFirst().getCertificateId())
    );
  }

  @Test
  @DisplayName("Obesvarade kompletteringar skall gå att hämta för patient")
  void shallReturnUnansweredCommunicationMessagesForPatient() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED)
    );

    api().sendCertificate(
        defaultSendCertificateRequest(),
        certificateId(testCertificates)
    );

    api().receiveMessage(
        incomingComplementMessageBuilder()
            .certificateId(certificateId(testCertificates))
            .complements(List.of(incomingComplementDTOBuilder()
                .questionId(questionId())
                .build()))
            .build()
    );

    final var request = GetSentInternalRequest.builder()
        .patientIdList(List.of(ATHENA_REACT_ANDERSSON_DTO.getId().getId()))
        .maxDays(7)
        .build();

    final var response = internalApi().getUnansweredCommunicationMessages(request);

    assertAll(
        () -> assertNotNull(response.getBody()),
        () -> assertNotNull(response.getBody().messages()),
        () -> assertEquals(1, response.getBody().messages().size()),
        () -> assertTrue(
            response.getBody().messages().containsKey(certificateId(testCertificates))),
        () -> assertEquals(1,
            response.getBody().messages().get(certificateId(testCertificates)).complement())
    );
  }


}
