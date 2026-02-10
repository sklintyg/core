package se.inera.intyg.certificateservice.application.message.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
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
import se.inera.intyg.certificateservice.domain.message.repository.MessageRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.CertificateEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.PatientEntityRepository;

@ExtendWith(MockitoExtension.class)
class GetSentMessageInternalServiceTest {

  @Mock
  private MessageRepository messageRepository;
  @Mock
  private PatientEntityRepository patientEntityRepository;
  @Mock
  private CertificateEntityRepository certificateEntityRepository;
  @InjectMocks
  private GetSentMessageInternalService getSentMessageInternalService;

  private static final Integer MAX_DAYS = 7;

  @Nested
  class EmptyOrNullInput {

    @Test
    void shallReturnEmptyMapWhenPatientIdsIsNull() {
      final var result = getSentMessageInternalService.get(null, MAX_DAYS);

      assertEquals(Map.of(), result.getMessages());
    }

    @Test
    void shallReturnEmptyMapWhenPatientIdsIsEmpty() {
      final var result = getSentMessageInternalService.get(Collections.emptyList(), MAX_DAYS);

      assertEquals(Map.of(), result.getMessages());
    }

    @Test
    void shallNotCallRepositoriesWhenPatientIdsIsNull() {
      getSentMessageInternalService.get(null, MAX_DAYS);

      verify(patientEntityRepository, never()).findById(anyString());
      verify(certificateEntityRepository, never()).findCertificateEntitiesByPatient_Key(anyInt());
      verify(messageRepository,
          never()).findCertificateMessageCountByPatientKeyAndStatusSentAndCreatedAfter(
          anyList(), anyInt());
    }
  }

  @Nested
  class InvalidPatientIds {

    @Test
    void shallSkipNullPatientId() {
      final List<String> patientIds = new java.util.ArrayList<>();
      patientIds.add(ATHENA_REACT_ANDERSSON_ID);
      patientIds.add(null);
      final var certId = "certA";
      doReturn(List.of(
          se.inera.intyg.certificateservice.domain.message.model.CertificateMessageCount.builder()
              .certificateId(certId).messageCount(1).build()
      )).when(messageRepository)
          .findCertificateMessageCountByPatientKeyAndStatusSentAndCreatedAfter(
              eq(List.of(ATHENA_REACT_ANDERSSON_ID_WITHOUT_DASH)),
              eq(MAX_DAYS));

      final var result = getSentMessageInternalService.get(patientIds, MAX_DAYS);

      assertEquals(1, result.getMessages().size());
      assertTrue(result.getMessages().containsKey(certId));
    }

    @Test
    void shallSkipBlankPatientId() {
      final var patientIds = List.of(ATHENA_REACT_ANDERSSON_ID, "   ");
      final var certId = "certB";
      doReturn(List.of(
          se.inera.intyg.certificateservice.domain.message.model.CertificateMessageCount.builder()
              .certificateId(certId).messageCount(1).build()
      )).when(messageRepository)
          .findCertificateMessageCountByPatientKeyAndStatusSentAndCreatedAfter(
              eq(List.of(ATHENA_REACT_ANDERSSON_ID_WITHOUT_DASH)),
              eq(MAX_DAYS));

      final var result = getSentMessageInternalService.get(patientIds, MAX_DAYS);

      assertEquals(1, result.getMessages().size());
      assertTrue(result.getMessages().containsKey(certId));
    }

    @Test
    void shallReturnEmptyMapWhenPatientNotFound() {
      final var patientIds = List.of(ATHENA_REACT_ANDERSSON_ID);

      doReturn(Collections.emptyList()).when(messageRepository)
          .findCertificateMessageCountByPatientKeyAndStatusSentAndCreatedAfter(
              eq(List.of(ATHENA_REACT_ANDERSSON_ID_WITHOUT_DASH)),
              eq(MAX_DAYS));

      final var result = getSentMessageInternalService.get(patientIds, MAX_DAYS);

      assertEquals(Map.of(), result.getMessages());
    }

    @Test
    void shallReturnEmptyMapWhenCertificatesNotFound() {
      final var patientIds = List.of(ATHENA_REACT_ANDERSSON_ID);

      doReturn(Collections.emptyList()).when(messageRepository)
          .findCertificateMessageCountByPatientKeyAndStatusSentAndCreatedAfter(
              eq(List.of(ATHENA_REACT_ANDERSSON_ID_WITHOUT_DASH)),
              eq(MAX_DAYS));

      final var result = getSentMessageInternalService.get(patientIds, MAX_DAYS);

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
              .certificateId(certId).messageCount(1).build()
      )).when(messageRepository)
          .findCertificateMessageCountByPatientKeyAndStatusSentAndCreatedAfter(
              eq(List.of(validPatientId, invalidPatientId)),
              eq(MAX_DAYS));

      final var result = getSentMessageInternalService.get(patientIds, MAX_DAYS);

      assertEquals(1, result.getMessages().size());
      assertTrue(result.getMessages().containsKey(certId));
    }

    @Test
    void shallHandleExceptionWhenCertificateNotFoundAndContinueProcessing() {
      final var validPatientId = "191212121212";
      final var patientWithNoCertsId = "181818181818";
      final var patientIds = List.of(validPatientId, patientWithNoCertsId);
      final var certId = "certD";

      doReturn(List.of(
          se.inera.intyg.certificateservice.domain.message.model.CertificateMessageCount.builder()
              .certificateId(certId).messageCount(1).build()
      )).when(messageRepository)
          .findCertificateMessageCountByPatientKeyAndStatusSentAndCreatedAfter(
              eq(List.of(validPatientId, patientWithNoCertsId)),
              eq(MAX_DAYS));

      final var result = getSentMessageInternalService.get(patientIds, MAX_DAYS);

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
              .certificateId("cert1").messageCount(1).build(),
          se.inera.intyg.certificateservice.domain.message.model.CertificateMessageCount.builder()
              .certificateId("cert2").messageCount(2).build()
      )).when(messageRepository)
          .findCertificateMessageCountByPatientKeyAndStatusSentAndCreatedAfter(
              eq(List.of(patientId1, patientId2)),
              eq(MAX_DAYS));

      final var result = getSentMessageInternalService.get(patientIds, MAX_DAYS);

      assertEquals(2, result.getMessages().size());
      assertTrue(result.getMessages().containsKey("cert1"));
      assertTrue(result.getMessages().containsKey("cert2"));
      assertEquals(1, result.getMessages().get("cert1").complement());
      assertEquals(2, result.getMessages().get("cert2").complement());
    }

    @Test
    void shallCalculateCutoffDateBasedOnMaxDays() {
      final var patientIds = List.of(ATHENA_REACT_ANDERSSON_ID);
      final var maxDays30 = 30;
      final var certId = "certE";
      doReturn(List.of(
          se.inera.intyg.certificateservice.domain.message.model.CertificateMessageCount.builder()
              .certificateId(certId).messageCount(1).build()
      )).when(messageRepository)
          .findCertificateMessageCountByPatientKeyAndStatusSentAndCreatedAfter(
              eq(List.of(ATHENA_REACT_ANDERSSON_ID_WITHOUT_DASH)),
              eq(maxDays30));

      getSentMessageInternalService.get(patientIds, maxDays30);

      verify(messageRepository).findCertificateMessageCountByPatientKeyAndStatusSentAndCreatedAfter(
          eq(List.of(ATHENA_REACT_ANDERSSON_ID_WITHOUT_DASH)),
          eq(maxDays30)
      );
    }

    @Test
    void shallPopulateMessagesMapWithCorrectCertificateIds() {
      final var patientIds = List.of(ATHENA_REACT_ANDERSSON_ID);

      final var message1 = se.inera.intyg.certificateservice.domain.message.model.CertificateMessageCount.builder()
          .certificateId("certX").messageCount(1).build();
      doReturn(List.of(message1)).when(messageRepository)
          .findCertificateMessageCountByPatientKeyAndStatusSentAndCreatedAfter(
              eq(List.of(ATHENA_REACT_ANDERSSON_ID_WITHOUT_DASH)),
              eq(MAX_DAYS));

      final var result = getSentMessageInternalService.get(patientIds, MAX_DAYS);

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
              .certificateId("certY").messageCount(2).build(),
          se.inera.intyg.certificateservice.domain.message.model.CertificateMessageCount.builder()
              .certificateId("certY").messageCount(2).build()
      );

      doReturn(messages).when(messageRepository)
          .findCertificateMessageCountByPatientKeyAndStatusSentAndCreatedAfter(
              eq(List.of(ATHENA_REACT_ANDERSSON_ID_WITHOUT_DASH)),
              eq(MAX_DAYS));

      final var result = getSentMessageInternalService.get(patientIds, MAX_DAYS);

      assertEquals(1, result.getMessages().size());
      assertTrue(result.getMessages().containsKey(messages.get(0).certificateId()));
      assertEquals(2, result.getMessages().get(messages.get(0).certificateId()).complement());
    }

    @Test
    void shallReturnMessagesForMultipleCertificates() {
      final var patientIds = List.of(ATHENA_REACT_ANDERSSON_ID);

      final var message1 = se.inera.intyg.certificateservice.domain.message.model.CertificateMessageCount.builder()
          .certificateId("certZ").messageCount(3).build();
      final var message2 = se.inera.intyg.certificateservice.domain.message.model.CertificateMessageCount.builder()
          .certificateId("certZ").messageCount(3).build();
      final var message3 = se.inera.intyg.certificateservice.domain.message.model.CertificateMessageCount.builder()
          .certificateId("certZ").messageCount(3).build();
      doReturn(List.of(message1, message2, message3)).when(messageRepository)
          .findCertificateMessageCountByPatientKeyAndStatusSentAndCreatedAfter(
              eq(List.of(ATHENA_REACT_ANDERSSON_ID_WITHOUT_DASH)),
              eq(MAX_DAYS));

      final var result = getSentMessageInternalService.get(patientIds, MAX_DAYS);

      assertEquals(1, result.getMessages().size());
      assertTrue(result.getMessages().containsKey("certZ"));
      assertEquals(3, result.getMessages().get("certZ").complement());
    }

    @Test
    void shallRemoveDashesFromPatientId() {
      final var patientIds = List.of(ATHENA_REACT_ANDERSSON_ID);

      doReturn(Collections.emptyList()).when(messageRepository)
          .findCertificateMessageCountByPatientKeyAndStatusSentAndCreatedAfter(
              eq(List.of(ATHENA_REACT_ANDERSSON_ID_WITHOUT_DASH)),
              eq(MAX_DAYS));

      final var result = getSentMessageInternalService.get(patientIds, MAX_DAYS);

      assertEquals(0, result.getMessages().size());
    }
  }
}
