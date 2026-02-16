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

import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.common.model.PersonId;
import se.inera.intyg.certificateservice.domain.message.model.CertificateMessageCount;
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

      assertEquals(Map.of(), result.messages());
    }

    @Test
    void shallReturnEmptyMapWhenPatientIdsIsEmpty() {
      final var result = getSentMessageCountInternalService.get(Collections.emptyList(), MAX_DAYS);

      assertEquals(Map.of(), result.messages());
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
    void shallReturnEmptyMapWhenPatientNotFound() {
      final var patientIds = List.of(ATHENA_REACT_ANDERSSON_ID);

      doReturn(Collections.emptyList()).when(messageRepository)
          .findCertificateMessageCountByPatientKeyAndStatusSentAndCreatedAfter(
              List.of(PersonId.builder().id(ATHENA_REACT_ANDERSSON_ID_WITHOUT_DASH).build()),
              MAX_DAYS);

      final var result = getSentMessageCountInternalService.get(patientIds, MAX_DAYS);

      assertEquals(Map.of(), result.messages());
    }
  }

  @Nested
  class ProcessPatientMessages {

    @Test
    void shallHandleExceptionWhenPatientNotFoundAndContinueProcessing() {
      final var validPatientId = PersonId.builder().id("191212121212").build();
      final var invalidPatientId = PersonId.builder().id("999999999999").build();
      final var patientIds = List.of(validPatientId.id(), invalidPatientId.id());
      final var certId = "certC";

      doReturn(List.of(
          new CertificateMessageCount(new CertificateId(certId), 1, 1)
      )).when(messageRepository)
          .findCertificateMessageCountByPatientKeyAndStatusSentAndCreatedAfter(
              List.of(validPatientId, invalidPatientId),
              MAX_DAYS);

      final var result = getSentMessageCountInternalService.get(patientIds, MAX_DAYS);

      assertEquals(1, result.messages().size());
      assertTrue(result.messages().containsKey(certId));
    }

    @Test
    void shallProcessMultiplePatientsSuccessfully() {
      final var patientId1 = PersonId.builder().id("191212121212").build();
      final var patientId2 = PersonId.builder().id("181818181818").build();
      final var patientIds = List.of(patientId1.id(), patientId2.id());

      doReturn(List.of(
          new CertificateMessageCount(new CertificateId("cert1"), 1, 1),
          new CertificateMessageCount(new CertificateId("cert2"), 2, 1)
      )).when(messageRepository)
          .findCertificateMessageCountByPatientKeyAndStatusSentAndCreatedAfter(
              List.of(patientId1, patientId2),
              MAX_DAYS);

      final var result = getSentMessageCountInternalService.get(patientIds, MAX_DAYS);

      assertEquals(2, result.messages().size());
      assertTrue(result.messages().containsKey("cert1"));
      assertTrue(result.messages().containsKey("cert2"));
      assertEquals(1, result.messages().get("cert1").complement());
      assertEquals(2, result.messages().get("cert2").complement());
    }

    @Test
    void shallPopulateMessagesMapWithCorrectCertificateIds() {
      final var patientIds = List.of(ATHENA_REACT_ANDERSSON_ID);

      final var message1 = new CertificateMessageCount(new CertificateId("certX"), 1, 1);
      doReturn(List.of(message1)).when(messageRepository)
          .findCertificateMessageCountByPatientKeyAndStatusSentAndCreatedAfter(
              List.of(PersonId.builder().id(ATHENA_REACT_ANDERSSON_ID_WITHOUT_DASH).build()),
              MAX_DAYS);

      final var result = getSentMessageCountInternalService.get(patientIds, MAX_DAYS);

      assertEquals(1, result.messages().size());
      assertTrue(result.messages().containsKey("certX"));
      assertEquals(1, result.messages().get("certX").complement());
    }
  }

  @Nested
  class SuccessfulRetrieval {

    @Test
    void shallReturnMessagesForSinglePatient() {
      final var patientIds = List.of(ATHENA_REACT_ANDERSSON_ID);
      final var messages = List.of(
          new CertificateMessageCount(new CertificateId("certY"), 2, 1),
          new CertificateMessageCount(new CertificateId("certY"), 2, 1)
      );

      doReturn(messages).when(messageRepository)
          .findCertificateMessageCountByPatientKeyAndStatusSentAndCreatedAfter(
              List.of(PersonId.builder().id(ATHENA_REACT_ANDERSSON_ID_WITHOUT_DASH).build()),
              MAX_DAYS);

      final var result = getSentMessageCountInternalService.get(patientIds, MAX_DAYS);

      assertEquals(1, result.messages().size());
      assertTrue(result.messages().containsKey(messages.getFirst().certificateId().id()));
      assertEquals(2, result.messages().get(messages.getFirst().certificateId().id()).complement());
    }

    @Test
    void shallReturnMessagesForMultipleCertificates() {
      final var patientIds = List.of(ATHENA_REACT_ANDERSSON_ID);

      final var message1 = new CertificateMessageCount(new CertificateId("certZ"), 3, 1);
      final var message2 = new CertificateMessageCount(new CertificateId("certZ"), 3, 1);
      final var message3 = new CertificateMessageCount(new CertificateId("certZ"), 3, 1);
      doReturn(List.of(message1, message2, message3)).when(messageRepository)
          .findCertificateMessageCountByPatientKeyAndStatusSentAndCreatedAfter(
              List.of(PersonId.builder().id(ATHENA_REACT_ANDERSSON_ID_WITHOUT_DASH).build()),
              MAX_DAYS);

      final var result = getSentMessageCountInternalService.get(patientIds, MAX_DAYS);

      assertEquals(1, result.messages().size());
      assertTrue(result.messages().containsKey("certZ"));
      assertEquals(3, result.messages().get("certZ").complement());
    }

    @Test
    void shallRemoveDashesFromPatientId() {
      final var patientIds = List.of(ATHENA_REACT_ANDERSSON_ID);

      doReturn(Collections.emptyList()).when(messageRepository)
          .findCertificateMessageCountByPatientKeyAndStatusSentAndCreatedAfter(
              List.of(PersonId.builder().id(ATHENA_REACT_ANDERSSON_ID_WITHOUT_DASH).build()),
              MAX_DAYS);

      final var result = getSentMessageCountInternalService.get(patientIds, MAX_DAYS);

      assertEquals(0, result.messages().size());
    }
  }
}
