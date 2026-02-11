package se.inera.intyg.certificateservice.application.message.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_ID_WITHOUT_DASH;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.message.repository.MessageRepository;

@ExtendWith(MockitoExtension.class)
class GetSentMessageCountInternalServiceTest {

  @Mock
  private MessageRepository messageRepository;
  @InjectMocks
  private GetSentMessageCountInternalService getSentMessageCountInternalService;

  private static final Integer MAX_DAYS = 7;

  @Nested
  class EmptyOrNullInput {

    @Test
    void shallReturnEmptyMapWhenPatientIdsIsNull() {
      final var result = getSentMessageCountInternalService.get(null, MAX_DAYS);

      assertEquals(Map.of(), result.getMessages());
    }

    @Test
    void shallReturnEmptyMapWhenPatientIdsIsEmpty() {
      final var result = getSentMessageCountInternalService.get(Collections.emptyList(), MAX_DAYS);

      assertEquals(Map.of(), result.getMessages());
    }

    @Test
    void shallNotCallRepositoriesWhenPatientIdsIsNull() {
      getSentMessageCountInternalService.get(null, MAX_DAYS);

      verify(messageRepository,
          never()).findCertificateMessageCountByPatientKeyAndStatusSentAndCreatedAfter(
          anyList(), anyInt());
    }
  }

  @Nested
  class InvalidPatientIds {

    @Test
    void shallSkipNullPatientId() {
      final var INVALID_PATIENT_ID = "9999-9999-9999-9999";
      final var patientIds = new ArrayList<String>();
      patientIds.add(ATHENA_REACT_ANDERSSON_ID);
      patientIds.add(INVALID_PATIENT_ID);
      final var certId = "certA";
      doReturn(List.of(
          se.inera.intyg.certificateservice.domain.message.model.CertificateMessageCount.builder()
              .certificateId(certId).complementsCount(1).othersCount(1).build()
      )).when(messageRepository)
          .findCertificateMessageCountByPatientKeyAndStatusSentAndCreatedAfter(
              List.of(ATHENA_REACT_ANDERSSON_ID_WITHOUT_DASH),
              MAX_DAYS);

      final var result = getSentMessageCountInternalService.get(patientIds, MAX_DAYS);

      assertEquals(1, result.getMessages().size());
      assertTrue(result.getMessages().containsKey(certId));
    }

    @Test
    void shallSkipBlankPatientId() {
      final var patientIds = List.of(ATHENA_REACT_ANDERSSON_ID, "   ");
      final var certId = "certB";
      doReturn(List.of(
          se.inera.intyg.certificateservice.domain.message.model.CertificateMessageCount.builder()
              .certificateId(certId).complementsCount(1).othersCount(1).build()
      )).when(messageRepository)
          .findCertificateMessageCountByPatientKeyAndStatusSentAndCreatedAfter(
              List.of(ATHENA_REACT_ANDERSSON_ID_WITHOUT_DASH),
              MAX_DAYS);

      final var result = getSentMessageCountInternalService.get(patientIds, MAX_DAYS);

      assertEquals(1, result.getMessages().size());
      assertTrue(result.getMessages().containsKey(certId));
    }

    @Test
    void shallReturnEmptyMapWhenPatientNotFound() {
      final var patientIds = List.of(ATHENA_REACT_ANDERSSON_ID);

      doReturn(Collections.emptyList()).when(messageRepository)
          .findCertificateMessageCountByPatientKeyAndStatusSentAndCreatedAfter(
              List.of(ATHENA_REACT_ANDERSSON_ID_WITHOUT_DASH),
              MAX_DAYS);

      final var result = getSentMessageCountInternalService.get(patientIds, MAX_DAYS);

      assertEquals(Map.of(), result.getMessages());
    }
  }

  @Nested
  class ProcessPatientMessages {

    @Test
    void shallHandleExceptionWhenPatientNotFoundAndContinueProcessing() {
      final var validPatientId = "191212121212";
      final var invalidPatientId = "999999999999";
      final var patientIds = List.of(validPatientId, invalidPatientId);
      final var certId = "certC";

      doReturn(List.of(
          se.inera.intyg.certificateservice.domain.message.model.CertificateMessageCount.builder()
              .certificateId(certId).complementsCount(1).othersCount(1).build()
      )).when(messageRepository)
          .findCertificateMessageCountByPatientKeyAndStatusSentAndCreatedAfter(
              List.of(validPatientId, invalidPatientId),
              MAX_DAYS);

      final var result = getSentMessageCountInternalService.get(patientIds, MAX_DAYS);

      assertEquals(1, result.getMessages().size());
      assertTrue(result.getMessages().containsKey(certId));
    }

    @Test
    void shallProcessMultiplePatientsSuccessfully() {
      final var patientId1 = "191212121212";
      final var patientId2 = "181818181818";
      final var patientIds = List.of(patientId1, patientId2);

      doReturn(List.of(
          se.inera.intyg.certificateservice.domain.message.model.CertificateMessageCount.builder()
              .certificateId("cert1").complementsCount(1).othersCount(1).build(),
          se.inera.intyg.certificateservice.domain.message.model.CertificateMessageCount.builder()
              .certificateId("cert2").complementsCount(2).othersCount(1).build()
      )).when(messageRepository)
          .findCertificateMessageCountByPatientKeyAndStatusSentAndCreatedAfter(
              List.of(patientId1, patientId2),
              MAX_DAYS);

      final var result = getSentMessageCountInternalService.get(patientIds, MAX_DAYS);

      assertEquals(2, result.getMessages().size());
      assertTrue(result.getMessages().containsKey("cert1"));
      assertTrue(result.getMessages().containsKey("cert2"));
      assertEquals(1, result.getMessages().get("cert1").complement());
      assertEquals(2, result.getMessages().get("cert2").complement());
    }

    @Test
    void shallPopulateMessagesMapWithCorrectCertificateIds() {
      final var patientIds = List.of(ATHENA_REACT_ANDERSSON_ID);

      final var message1 = se.inera.intyg.certificateservice.domain.message.model.CertificateMessageCount.builder()
          .certificateId("certX").complementsCount(1).othersCount(1).build();
      doReturn(List.of(message1)).when(messageRepository)
          .findCertificateMessageCountByPatientKeyAndStatusSentAndCreatedAfter(
              List.of(ATHENA_REACT_ANDERSSON_ID_WITHOUT_DASH),
              MAX_DAYS);

      final var result = getSentMessageCountInternalService.get(patientIds, MAX_DAYS);

      assertEquals(1, result.getMessages().size());
      assertTrue(result.getMessages().containsKey("certX"));
      assertEquals(1, result.getMessages().get("certX").complement());
    }
  }

  @Nested
  class SuccessfulRetrieval {

    @Test
    void shallReturnMessagesForSinglePatient() {
      final var patientIds = List.of(ATHENA_REACT_ANDERSSON_ID);
      final var messages = List.of(
          se.inera.intyg.certificateservice.domain.message.model.CertificateMessageCount.builder()
              .certificateId("certY").complementsCount(2).othersCount(1).build(),
          se.inera.intyg.certificateservice.domain.message.model.CertificateMessageCount.builder()
              .certificateId("certY").complementsCount(2).othersCount(1).build()
      );

      doReturn(messages).when(messageRepository)
          .findCertificateMessageCountByPatientKeyAndStatusSentAndCreatedAfter(
              List.of(ATHENA_REACT_ANDERSSON_ID_WITHOUT_DASH),
              MAX_DAYS);

      final var result = getSentMessageCountInternalService.get(patientIds, MAX_DAYS);

      assertEquals(1, result.getMessages().size());
      assertTrue(result.getMessages().containsKey(messages.getFirst().certificateId()));
      assertEquals(2, result.getMessages().get(messages.getFirst().certificateId()).complement());
    }

    @Test
    void shallReturnMessagesForMultipleCertificates() {
      final var patientIds = List.of(ATHENA_REACT_ANDERSSON_ID);

      final var message1 = se.inera.intyg.certificateservice.domain.message.model.CertificateMessageCount.builder()
          .certificateId("certZ").complementsCount(3).othersCount(1).build();
      final var message2 = se.inera.intyg.certificateservice.domain.message.model.CertificateMessageCount.builder()
          .certificateId("certZ").complementsCount(3).othersCount(1).build();
      final var message3 = se.inera.intyg.certificateservice.domain.message.model.CertificateMessageCount.builder()
          .certificateId("certZ").complementsCount(3).othersCount(1).build();
      doReturn(List.of(message1, message2, message3)).when(messageRepository)
          .findCertificateMessageCountByPatientKeyAndStatusSentAndCreatedAfter(
              List.of(ATHENA_REACT_ANDERSSON_ID_WITHOUT_DASH),
              MAX_DAYS);

      final var result = getSentMessageCountInternalService.get(patientIds, MAX_DAYS);

      assertEquals(1, result.getMessages().size());
      assertTrue(result.getMessages().containsKey("certZ"));
      assertEquals(3, result.getMessages().get("certZ").complement());
    }

    @Test
    void shallRemoveDashesFromPatientId() {
      final var patientIds = List.of(ATHENA_REACT_ANDERSSON_ID);

      doReturn(Collections.emptyList()).when(messageRepository)
          .findCertificateMessageCountByPatientKeyAndStatusSentAndCreatedAfter(
              List.of(ATHENA_REACT_ANDERSSON_ID_WITHOUT_DASH),
              MAX_DAYS);

      final var result = getSentMessageCountInternalService.get(patientIds, MAX_DAYS);

      assertEquals(0, result.getMessages().size());
    }
  }
}
