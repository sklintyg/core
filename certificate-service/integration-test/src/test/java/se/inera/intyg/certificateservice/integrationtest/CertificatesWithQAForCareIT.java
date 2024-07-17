package se.inera.intyg.certificateservice.integrationtest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO.SIGNED;
import static se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO.UNSIGNED;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_ALLERGIMOTTAGNINGEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataIncomingMessage.incomingQuestionMessageBuilder;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.customGetPatientCertificatesWithQARequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultCreateMessageRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultGetPatientCertificatesWithQARequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultSendCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultSendMessageRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultTestablilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.certificateId;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.decodeXml;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.messageId;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.patientCertificatesWithQAResponse;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.question;

import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public abstract class CertificatesWithQAForCareIT extends BaseIntegrationIT {

  private static final String INTYG = "intyg";

  protected abstract String type();

  protected abstract String typeVersion();

  protected abstract boolean canRecieveQuestions();

  @Test
  @DisplayName("Skall returnera en lista av signerade intyg för patienten på vårdgivare")
  void shallReturnListOfCertificatesForPatientOnCareProvider() {
    final var testCertificates = testabilityApi.addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED)
    );

    final var patientCertificatesWithQA = internalApi.getPatientCertificatesWithQA(
        defaultGetPatientCertificatesWithQARequest()
    );

    final var getPatientCertificatesWithQAResponse = patientCertificatesWithQAResponse(
        patientCertificatesWithQA
    );

    final var decodedXml = decodeXml(getPatientCertificatesWithQAResponse.getList());
    assertTrue(decodedXml.contains(Objects.requireNonNull(certificateId(testCertificates))));
  }

  @Test
  @DisplayName("Skall returnera en lista av signerade intyg för patienten på enhet")
  void shallReturnListOfCertificatesForPatientOnUnit() {
    final var testCertificates = testabilityApi.addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED)
    );

    final var patientCertificatesWithQA = internalApi.getPatientCertificatesWithQA(
        customGetPatientCertificatesWithQARequest()
            .unitIds(List.of(ALFA_ALLERGIMOTTAGNINGEN_DTO.getId()))
            .build()
    );

    final var getPatientCertificatesWithQAResponse = patientCertificatesWithQAResponse(
        patientCertificatesWithQA
    );

    final var decodedXml = decodeXml(getPatientCertificatesWithQAResponse.getList());
    assertTrue(decodedXml.contains(Objects.requireNonNull(certificateId(testCertificates))));
  }

  @Test
  @DisplayName("Skall returnera en lista av utkast för patienten på vårdgivare")
  void shallReturnListOfDraftsForPatientOnCareProvider() {
    final var testCertificates = testabilityApi.addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), UNSIGNED)
    );

    final var patientCertificatesWithQA = internalApi.getPatientCertificatesWithQA(
        defaultGetPatientCertificatesWithQARequest()
    );

    final var getPatientCertificatesWithQAResponse = patientCertificatesWithQAResponse(
        patientCertificatesWithQA
    );

    final var decodedXml = decodeXml(getPatientCertificatesWithQAResponse.getList());
    assertTrue(decodedXml.contains(Objects.requireNonNull(certificateId(testCertificates))));
  }

  @Test
  @DisplayName("Skall returnera en lista av utkast för patienten på enhet")
  void shallReturnListOfDraftsForPatientOnUnit() {
    final var testCertificates = testabilityApi.addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), UNSIGNED)
    );

    final var patientCertificatesWithQA = internalApi.getPatientCertificatesWithQA(
        customGetPatientCertificatesWithQARequest()
            .unitIds(List.of(ALFA_ALLERGIMOTTAGNINGEN_DTO.getId()))
            .build()
    );

    final var getPatientCertificatesWithQAResponse = patientCertificatesWithQAResponse(
        patientCertificatesWithQA
    );

    final var decodedXml = decodeXml(getPatientCertificatesWithQAResponse.getList());
    assertTrue(decodedXml.contains(Objects.requireNonNull(certificateId(testCertificates))));
  }

  @Test
  @DisplayName("Skall returnera en tom lista för patienten om inga intyg fins utfärdade")
  void shallReturnEmptyListIfNoCertificatesAreFoundForPatient() {
    final var patientCertificatesWithQA = internalApi.getPatientCertificatesWithQA(
        defaultGetPatientCertificatesWithQARequest()
    );

    final var getPatientCertificatesWithQAResponse = patientCertificatesWithQAResponse(
        patientCertificatesWithQA
    );

    final var decodedXml = decodeXml(getPatientCertificatesWithQAResponse.getList());
    assertFalse(decodedXml.contains(INTYG));
  }

  @Test
  @DisplayName("Skall returnera information om meddelanden som finns på intyget")
  void shallReturnInformationAboutMessagesOnCertificateForPatient() {
    if (canRecieveQuestions()) {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED)
      );

      api.sendCertificate(
          defaultSendCertificateRequest(),
          certificateId(testCertificates)
      );

      api.receiveMessage(
          incomingQuestionMessageBuilder()
              .certificateId(certificateId(testCertificates))
              .build()
      );

      final var createdQuestion = question(
          api.createMessage(defaultCreateMessageRequest(),
              certificateId(testCertificates)).getBody()
      );

      api.sendMessage(defaultSendMessageRequest(), messageId(createdQuestion));

      final var patientCertificatesWithQA = internalApi.getPatientCertificatesWithQA(
          defaultGetPatientCertificatesWithQARequest()
      );

      final var getPatientCertificatesWithQAResponse = patientCertificatesWithQAResponse(
          patientCertificatesWithQA
      );
      
      final var sentQuestions = "<ns5:skickadeFragor><ns2:totalt>1</ns2:totalt><ns2:ejBesvarade>1</ns2:ejBesvarade><ns2:besvarade>0</ns2:besvarade><ns2:hanterade>0</ns2:hanterade></ns5:skickadeFragor>";
      final var recievedQuestions = "<ns5:mottagnaFragor><ns2:totalt>1</ns2:totalt><ns2:ejBesvarade>1</ns2:ejBesvarade><ns2:besvarade>0</ns2:besvarade><ns2:hanterade>0</ns2:hanterade></ns5:mottagnaFragor>";
      final var decodedXml = decodeXml(getPatientCertificatesWithQAResponse.getList());

      assertAll(
          () -> assertTrue(decodedXml.contains(sentQuestions)),
          () -> assertTrue(decodedXml.contains(recievedQuestions))
      );
    }
  }
}
