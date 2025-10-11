package se.inera.intyg.certificateservice.integrationtest.common.tests;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO.SIGNED;
import static se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO.UNSIGNED;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.ALVE_REACT_ALFREDSSON_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.ANONYMA_REACT_ATTILA_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.ATHENA_REACT_ANDERSSON_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_ALLERGIMOTTAGNINGEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_HUDMOTTAGNINGEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.AJLA_DOCTOR_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.BERTIL_BARNMORSKA_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.ajlaDoktorDtoBuilder;
import static se.inera.intyg.certificateservice.application.testdata.TestDataIncomingMessage.incomingComplementDTOBuilder;
import static se.inera.intyg.certificateservice.application.testdata.TestDataIncomingMessage.incomingComplementMessageBuilder;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.customGetCertificateMessageRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.customGetUnitMessagesRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.customSendCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.customTestabilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultGetCertificateMessageRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultGetCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultSendCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultTestablilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.certificateId;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.hasQuestions;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.questions;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import se.inera.intyg.certificateservice.application.certificate.dto.PersonIdDTO;
import se.inera.intyg.certificateservice.application.common.dto.PersonIdTypeDTO;
import se.inera.intyg.certificateservice.application.common.dto.ResourceLinkDTO;
import se.inera.intyg.certificateservice.application.common.dto.ResourceLinkTypeDTO;
import se.inera.intyg.certificateservice.application.common.dto.UserDTO;
import se.inera.intyg.certificateservice.application.unit.dto.MessagesQueryCriteriaDTO;
import se.inera.intyg.certificateservice.application.unit.dto.QuestionSenderTypeDTO;
import se.inera.intyg.certificateservice.integrationtest.common.setup.BaseIntegrationIT;
import se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil;

public abstract class ComplementMessagesIT extends BaseIntegrationIT {

  @Test
  @DisplayName("Skall returnera lista av frågor som finns på intyget")
  void shallReturnListOfQuestionsForCertificate() {
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

    final var messagesForCertificate = api().getMessagesForCertificate(
        defaultGetCertificateMessageRequest(),
        certificateId(testCertificates)
    );

    assertTrue(
        hasQuestions(messagesForCertificate.getBody())
    );
  }

  @Test
  @DisplayName("Ska returnera lista av frågor för enheten när ärenden filtreras fram")
  void shallReturnListOfQuestionsForUnitWhenApplyingMatchingFilters() {
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

    final var response = api().getMessagesForUnit(
        customGetUnitMessagesRequest()
            .messagesQueryCriteria(
                MessagesQueryCriteriaDTO.builder()
                    .senderType(QuestionSenderTypeDTO.FK)
                    .forwarded(false)
                    .sentDateFrom(LocalDateTime.now().minusDays(1).toLocalDate().atStartOfDay())
                    .sentDateTo(LocalDateTime.now().plusDays(1).toLocalDate().atStartOfDay())
                    .issuedByStaffId(AJLA_DOCTOR_DTO.getId())
                    .patientId(
                        PersonIdDTO.builder()
                            .id(ATHENA_REACT_ANDERSSON_DTO.getId().getId())
                            .type(PersonIdTypeDTO.PERSONAL_IDENTITY_NUMBER.name())
                            .build()
                    )
                    .issuedOnUnitIds(List.of(ALFA_ALLERGIMOTTAGNINGEN_DTO.getId()))
                    .build()
            )
            .build()
    );

    assertAll(
        () -> assertEquals(1, Objects.requireNonNull(response.getBody()).getQuestions().size()),
        () -> assertEquals(1, Objects.requireNonNull(response.getBody()).getCertificates().size())
    );
  }

  @Test
  @DisplayName("Ska returnera ärende om filter för skickat är satt till samma dag som meddelandet skickats")
  void shallReturnListOfQuestionsForUnitWhenApplyingFilterSetToSameDayAsSent() {
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

    final var response = api().getMessagesForUnit(
        customGetUnitMessagesRequest()
            .messagesQueryCriteria(
                MessagesQueryCriteriaDTO.builder()
                    .senderType(QuestionSenderTypeDTO.FK)
                    .forwarded(false)
                    .sentDateFrom(LocalDateTime.now().toLocalDate().atStartOfDay())
                    .sentDateTo(LocalDateTime.now().toLocalDate().atStartOfDay())
                    .issuedByStaffId(AJLA_DOCTOR_DTO.getId())
                    .patientId(
                        PersonIdDTO.builder()
                            .id(ATHENA_REACT_ANDERSSON_DTO.getId().getId())
                            .type(PersonIdTypeDTO.PERSONAL_IDENTITY_NUMBER.name())
                            .build()
                    )
                    .issuedOnUnitIds(List.of(ALFA_ALLERGIMOTTAGNINGEN_DTO.getId()))
                    .build()
            )
            .build()
    );

    assertAll(
        () -> assertEquals(1, Objects.requireNonNull(response.getBody()).getQuestions().size()),
        () -> assertEquals(1, Objects.requireNonNull(response.getBody()).getCertificates().size())
    );
  }

  @Test
  @DisplayName("Ska filtrera ärenden om avsändare inte matchar filtreringen")
  void shallFilterQuestionIfAuthorDoesNotMatch() {
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

    final var response = api().getMessagesForUnit(
        customGetUnitMessagesRequest()
            .messagesQueryCriteria(
                MessagesQueryCriteriaDTO.builder()
                    .senderType(QuestionSenderTypeDTO.WC)
                    .forwarded(false)
                    .sentDateFrom(LocalDateTime.now().minusDays(1))
                    .sentDateTo(LocalDateTime.now().plusDays(1))
                    .issuedByStaffId(AJLA_DOCTOR_DTO.getId())
                    .patientId(
                        PersonIdDTO.builder()
                            .id(ATHENA_REACT_ANDERSSON_DTO.getId().getId())
                            .type(PersonIdTypeDTO.PERSONAL_IDENTITY_NUMBER.name())
                            .build()
                    )
                    .issuedOnUnitIds(List.of(ALFA_ALLERGIMOTTAGNINGEN_DTO.getId()))
                    .build()
            )
            .build()
    );

    assertAll(
        () -> assertEquals(0, Objects.requireNonNull(response.getBody()).getQuestions().size()),
        () -> assertEquals(0, Objects.requireNonNull(response.getBody()).getCertificates().size())
    );
  }

  @Test
  @DisplayName("Ska filtrera ärenden om vidarebefordrat inte matchar filtreringen")
  void shallFilterQuestionIfForwardedDoesNotMatch() {
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

    final var response = api().getMessagesForUnit(
        customGetUnitMessagesRequest()
            .messagesQueryCriteria(
                MessagesQueryCriteriaDTO.builder()
                    .senderType(QuestionSenderTypeDTO.FK)
                    .forwarded(true)
                    .sentDateFrom(LocalDateTime.now().minusDays(1))
                    .sentDateTo(LocalDateTime.now().plusDays(1))
                    .issuedByStaffId(AJLA_DOCTOR_DTO.getId())
                    .patientId(
                        PersonIdDTO.builder()
                            .id(ATHENA_REACT_ANDERSSON_DTO.getId().getId())
                            .type(PersonIdTypeDTO.PERSONAL_IDENTITY_NUMBER.name())
                            .build()
                    )
                    .issuedOnUnitIds(List.of(ALFA_ALLERGIMOTTAGNINGEN_DTO.getId()))
                    .build()
            )
            .build()
    );

    assertAll(
        () -> assertEquals(0, Objects.requireNonNull(response.getBody()).getQuestions().size()),
        () -> assertEquals(0, Objects.requireNonNull(response.getBody()).getCertificates().size())
    );
  }

  @Test
  @DisplayName("Ska filtrera ärenden om skickat från inte matchar filtreringen")
  void shallFilterQuestionIfSentFromDoesNotMatch() {
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

    final var response = api().getMessagesForUnit(
        customGetUnitMessagesRequest()
            .messagesQueryCriteria(
                MessagesQueryCriteriaDTO.builder()
                    .senderType(QuestionSenderTypeDTO.FK)
                    .forwarded(false)
                    .sentDateFrom(LocalDateTime.now().plusDays(1))
                    .sentDateTo(LocalDateTime.now().plusDays(1))
                    .issuedByStaffId(AJLA_DOCTOR_DTO.getId())
                    .patientId(
                        PersonIdDTO.builder()
                            .id(ATHENA_REACT_ANDERSSON_DTO.getId().getId())
                            .type(PersonIdTypeDTO.PERSONAL_IDENTITY_NUMBER.name())
                            .build()
                    )
                    .issuedOnUnitIds(List.of(ALFA_ALLERGIMOTTAGNINGEN_DTO.getId()))
                    .build()
            )
            .build()
    );

    assertAll(
        () -> assertEquals(0, Objects.requireNonNull(response.getBody()).getQuestions().size()),
        () -> assertEquals(0, Objects.requireNonNull(response.getBody()).getCertificates().size())
    );
  }

  @Test
  @DisplayName("Ska filtrera ärenden om skickat till inte matchar filtreringen")
  void shallFilterQuestionIfSentToDoesNotMatch() {
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

    final var response = api().getMessagesForUnit(
        customGetUnitMessagesRequest()
            .messagesQueryCriteria(
                MessagesQueryCriteriaDTO.builder()
                    .senderType(QuestionSenderTypeDTO.FK)
                    .forwarded(false)
                    .sentDateFrom(LocalDateTime.now().minusDays(1))
                    .sentDateTo(LocalDateTime.now().minusDays(1))
                    .issuedByStaffId(AJLA_DOCTOR_DTO.getId())
                    .patientId(
                        PersonIdDTO.builder()
                            .id(ATHENA_REACT_ANDERSSON_DTO.getId().getId())
                            .type(PersonIdTypeDTO.PERSONAL_IDENTITY_NUMBER.name())
                            .build()
                    )
                    .issuedOnUnitIds(List.of(ALFA_ALLERGIMOTTAGNINGEN_DTO.getId()))
                    .build()
            )
            .build()
    );

    assertAll(
        () -> assertEquals(0, Objects.requireNonNull(response.getBody()).getQuestions().size()),
        () -> assertEquals(0, Objects.requireNonNull(response.getBody()).getCertificates().size())
    );
  }

  @Test
  @DisplayName("Ska filtrera ärenden om intygsutfärdare inte matchar filtreringen")
  void shallFilterQuestionIfIssuerDoesNotMatch() {
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

    final var response = api().getMessagesForUnit(
        customGetUnitMessagesRequest()
            .messagesQueryCriteria(
                MessagesQueryCriteriaDTO.builder()
                    .senderType(QuestionSenderTypeDTO.FK)
                    .forwarded(false)
                    .sentDateFrom(LocalDateTime.now().minusDays(1))
                    .sentDateTo(LocalDateTime.now().plusDays(1))
                    .issuedByStaffId(BERTIL_BARNMORSKA_DTO.getId())
                    .patientId(
                        PersonIdDTO.builder()
                            .id(ATHENA_REACT_ANDERSSON_DTO.getId().getId())
                            .type(PersonIdTypeDTO.PERSONAL_IDENTITY_NUMBER.name())
                            .build()
                    )
                    .issuedOnUnitIds(List.of(ALFA_ALLERGIMOTTAGNINGEN_DTO.getId()))
                    .build()
            )
            .build()
    );

    assertAll(
        () -> assertEquals(0, Objects.requireNonNull(response.getBody()).getQuestions().size()),
        () -> assertEquals(0, Objects.requireNonNull(response.getBody()).getCertificates().size())
    );
  }

  @Test
  @DisplayName("Ska filtrera ärenden om enhet inte matchar filtreringen")
  void shallFilterQuestionIfUnitDoesNotMatch() {
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

    final var response = api().getMessagesForUnit(
        customGetUnitMessagesRequest()
            .messagesQueryCriteria(
                MessagesQueryCriteriaDTO.builder()
                    .senderType(QuestionSenderTypeDTO.FK)
                    .forwarded(false)
                    .sentDateFrom(LocalDateTime.now().minusDays(1))
                    .sentDateTo(LocalDateTime.now().plusDays(1))
                    .issuedByStaffId(AJLA_DOCTOR_DTO.getId())
                    .patientId(
                        PersonIdDTO.builder()
                            .id(ATHENA_REACT_ANDERSSON_DTO.getId().getId())
                            .type(PersonIdTypeDTO.PERSONAL_IDENTITY_NUMBER.name())
                            .build()
                    )
                    .issuedOnUnitIds(List.of(ALFA_MEDICINCENTRUM_DTO.getId()))
                    .build()
            )
            .build()
    );

    assertAll(
        () -> assertEquals(0, Objects.requireNonNull(response.getBody()).getQuestions().size()),
        () -> assertEquals(0, Objects.requireNonNull(response.getBody()).getCertificates().size())
    );
  }

  @Test
  @DisplayName("Ska filtrera ärenden om patient id inte matchar filtreringen")
  void shallFilterQuestionIfPatientIdDoesNotMatch() {
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

    final var response = api().getMessagesForUnit(
        customGetUnitMessagesRequest()
            .messagesQueryCriteria(
                MessagesQueryCriteriaDTO.builder()
                    .senderType(QuestionSenderTypeDTO.FK)
                    .forwarded(false)
                    .sentDateFrom(LocalDateTime.now().minusDays(1))
                    .sentDateTo(LocalDateTime.now().plusDays(1))
                    .issuedByStaffId(AJLA_DOCTOR_DTO.getId())
                    .patientId(
                        PersonIdDTO.builder()
                            .id(ALVE_REACT_ALFREDSSON_DTO.getId().getId())
                            .type(PersonIdTypeDTO.PERSONAL_IDENTITY_NUMBER.name())
                            .build()
                    )
                    .issuedOnUnitIds(List.of(ALFA_ALLERGIMOTTAGNINGEN_DTO.getId()))
                    .build()
            )
            .build()
    );

    assertAll(
        () -> assertEquals(0, Objects.requireNonNull(response.getBody()).getQuestions().size()),
        () -> assertEquals(0, Objects.requireNonNull(response.getBody()).getCertificates().size())
    );
  }

  @Test
  @DisplayName("Om intyget är utfärdat på samma mottagning ska hantering av frågor vara tillgänglig")
  void shallReturnListOfQuestionsForCertificateOnSameUnit() {
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

    final var messagesForCertificate = api().getMessagesForCertificate(
        defaultGetCertificateMessageRequest(),
        certificateId(testCertificates)
    );

    assertTrue(
        CertificateUtil.resourceLink(messagesForCertificate.getBody())
            .stream()
            .anyMatch(link -> link.getType().equals(ResourceLinkTypeDTO.COMPLEMENT_CERTIFICATE)),
        "Should return link!"
    );
  }

  @Test
  @DisplayName("Om intyget är utfärdat på mottagning men på samma vårdenhet ska hantering av frågor vara tillgänglig")
  void shallReturnQuestionWithComplementCertificateIfIssuedOnSameCareUnitDifferentSubUnit() {
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

    final var messagesForCertificate = api().getMessagesForCertificate(
        customGetCertificateMessageRequest()
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .build(),
        certificateId(testCertificates)
    );

    assertTrue(
        CertificateUtil.resourceLink(messagesForCertificate.getBody())
            .stream()
            .anyMatch(link -> link.getType().equals(ResourceLinkTypeDTO.COMPLEMENT_CERTIFICATE)),
        "Should return link!"
    );
  }

  @Test
  @DisplayName("Om intyget är utfärdat på samma vårdenhet ska hantering av frågor vara tillgänglig")
  void shallReturnQuestionWithComplementCertificateIfIssuedOnSameCareUnit() {
    final var testCertificates = testabilityApi().addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion(), SIGNED)
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .build()
    );

    api().sendCertificate(
        customSendCertificateRequest()
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .build(),
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

    final var messagesForCertificate = api().getMessagesForCertificate(
        customGetCertificateMessageRequest()
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .build(),
        certificateId(testCertificates)
    );

    assertTrue(
        CertificateUtil.resourceLink(messagesForCertificate.getBody())
            .stream()
            .anyMatch(link -> link.getType().equals(ResourceLinkTypeDTO.COMPLEMENT_CERTIFICATE)),
        "Should return link!"
    );
  }

  @Test
  @DisplayName("Om intyget är utfärdat på en annan mottagning ska hantering av frågor inte vara tillgänglig")
  void shallReturn403IfUnitIsSubUnitAndNotOnSameUnit() {
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

    final var messagesForCertificate = api().getMessagesForCertificate(
        customGetCertificateMessageRequest()
            .unit(ALFA_HUDMOTTAGNINGEN_DTO).build(),
        certificateId(testCertificates)
    );

    assertEquals(403, messagesForCertificate.getStatusCode().value());
  }


  @ParameterizedTest
  @DisplayName("Om intyget är utfärdat på en patient som har skyddade personuppgifter skall felkod 403 (FORBIDDEN) returneras")
  @MethodSource("rolesNoAccessToProtectedPerson")
  void shallReturn403IfPatientIsProtectedPerson(UserDTO userDTO) {
    final var testCertificates = testabilityApi().addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion(), SIGNED)
            .patient(ANONYMA_REACT_ATTILA_DTO)
            .build()
    );

    api().sendCertificate(
        defaultSendCertificateRequest(),
        certificateId(testCertificates)
    );

    api().receiveMessage(
        incomingComplementMessageBuilder()
            .personId(ANONYMA_REACT_ATTILA_DTO.getId())
            .certificateId(certificateId(testCertificates))
            .complements(List.of(incomingComplementDTOBuilder()
                .questionId(questionId())
                .build()))
            .build()
    );

    final var messagesForCertificate = api().getMessagesForCertificate(
        customGetCertificateMessageRequest()
            .user(userDTO)
            .build(),
        certificateId(testCertificates)
    );

    assertEquals(403, messagesForCertificate.getStatusCode().value());
  }

  @Test
  @DisplayName("Läkare - Om intyget är utfärdat på en patient som har skyddade personuppgifter ska inte hantering av frågor vara tillgänglig")
  void shallReturnComplementCertificateLinkIfPatientIsProtectedPersonAndUserIsDoctor() {
    final var testCertificates = testabilityApi().addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion(), SIGNED)
            .patient(ANONYMA_REACT_ATTILA_DTO)
            .build()
    );

    api().sendCertificate(
        defaultSendCertificateRequest(),
        certificateId(testCertificates)
    );

    api().receiveMessage(
        incomingComplementMessageBuilder()
            .personId(ANONYMA_REACT_ATTILA_DTO.getId())
            .certificateId(certificateId(testCertificates))
            .complements(List.of(incomingComplementDTOBuilder()
                .questionId(questionId())
                .build()))
            .build()
    );

    final var messagesForCertificate = api().getMessagesForCertificate(
        customGetCertificateMessageRequest()
            .user(AJLA_DOCTOR_DTO)
            .build(),
        certificateId(testCertificates)
    );

    assertTrue(
        CertificateUtil.resourceLink(messagesForCertificate.getBody())
            .stream()
            .anyMatch(link -> link.getType().equals(ResourceLinkTypeDTO.COMPLEMENT_CERTIFICATE)),
        "Should return link!"
    );
  }

  @Test
  @DisplayName("Om användaren är blockerad ska inte hantering av frågor vara tillgänglig")
  void shallNotReturnComplementCertificateIfUserIsBlocked() {
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

    final var messagesForCertificate = api().getMessagesForCertificate(
        customGetCertificateMessageRequest()
            .user(
                ajlaDoktorDtoBuilder()
                    .blocked(Boolean.TRUE)
                    .build()
            )
            .build(),
        certificateId(testCertificates)
    );

    assertTrue(
        questions(messagesForCertificate.getBody()).getFirst().getLinks()
            .isEmpty(),
        "Should not return link!"
    );
  }

  @Test
  @DisplayName("Om intyget inte är skickat skall information om att meddelanden ej är tillgängliga visas")
  void shallDisplayMessagesNotAvailableIfCertificateIsNotSent() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED)
    );

    final var response = api().getCertificate(
        defaultGetCertificateRequest(),
        certificateId(testCertificates)
    );
    final var resourceLinkTypes = Objects.requireNonNull(response.getBody()).getCertificate()
        .getLinks()
        .stream()
        .map(ResourceLinkDTO::getType)
        .toList();

    assertTrue(resourceLinkTypes.contains(ResourceLinkTypeDTO.QUESTIONS_NOT_AVAILABLE),
        "Should return true if resource link questions not available is included"
    );
  }

  @Test
  @DisplayName("Om intyget är skickat skall information om att meddelanden ej är tillgängliga inte visas")
  void shallNotDisplayMessagesNotAvailableIfCertificateIsSent() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED)
    );

    api().sendCertificate(defaultSendCertificateRequest(), certificateId(testCertificates));

    final var response = api().getCertificate(
        defaultGetCertificateRequest(),
        certificateId(testCertificates)
    );
    final var resourceLinkTypes = Objects.requireNonNull(response.getBody()).getCertificate()
        .getLinks()
        .stream()
        .map(ResourceLinkDTO::getType)
        .toList();

    assertFalse(resourceLinkTypes.contains(ResourceLinkTypeDTO.QUESTIONS_NOT_AVAILABLE),
        "Should return false if resource link questions not available is included"
    );
  }

  @Test
  @DisplayName("Om intyget är osignerat skall information om att meddelanden ej är tillgängliga inte visas")
  void shallNotDisplayMessagesNotAvailableIfCertificateIsUnsigned() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), UNSIGNED)
    );

    final var response = api().getCertificate(
        defaultGetCertificateRequest(),
        certificateId(testCertificates)
    );
    final var resourceLinkTypes = Objects.requireNonNull(response.getBody()).getCertificate()
        .getLinks()
        .stream()
        .map(ResourceLinkDTO::getType)
        .toList();

    assertFalse(resourceLinkTypes.contains(ResourceLinkTypeDTO.QUESTIONS_NOT_AVAILABLE),
        "Should return false if resource link questions not available is included"
    );
  }
}