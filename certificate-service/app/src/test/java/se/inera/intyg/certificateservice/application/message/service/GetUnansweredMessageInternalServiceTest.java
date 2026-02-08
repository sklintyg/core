package se.inera.intyg.certificateservice.application.message.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCertificateEntity.certificateEntityBuilder;
import static se.inera.intyg.certificateservice.application.testdata.TestDataPatientEntity.athenaReactAnderssonEntityBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessage.complementMessageBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_ID_WITHOUT_DASH;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.message.repository.MessageRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.PatientEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.CertificateEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.PatientEntityRepository;

@ExtendWith(MockitoExtension.class)
class GetUnansweredMessageInternalServiceTest {

  @Mock
  private MessageRepository messageRepository;
  @Mock
  private PatientEntityRepository patientEntityRepository;
  @Mock
  private CertificateEntityRepository certificateEntityRepository;
  @InjectMocks
  private GetUnansweredMessageInternalService getUnansweredMessageInternalService;

  private static final String CERTIFICATE_ID = "certificateId";
  private static final String CERTIFICATE_ID_2 = "certificateId2";
  private static final Integer PATIENT_KEY = 1;
  private static final Long CERTIFICATE_KEY = 100L;
  private static final Long CERTIFICATE_KEY_2 = 200L;
  private static final Integer MAX_DAYS = 7;

  private PatientEntity patientEntity;
  private CertificateEntity certificateEntity;
  private CertificateEntity certificateEntity2;

  @BeforeEach
  void setUp() {
    patientEntity = athenaReactAnderssonEntityBuilder()
        .key(PATIENT_KEY)
        .id(ATHENA_REACT_ANDERSSON_ID_WITHOUT_DASH)
        .build();

    certificateEntity = certificateEntityBuilder()
        .key(CERTIFICATE_KEY)
        .certificateId(CERTIFICATE_ID)
        .patient(patientEntity)
        .build();

    certificateEntity2 = certificateEntityBuilder()
        .key(CERTIFICATE_KEY_2)
        .certificateId(CERTIFICATE_ID_2)
        .patient(patientEntity)
        .build();
  }

  @Nested
  class EmptyOrNullInput {

    @Test
    void shallReturnEmptyMapWhenPatientIdsIsNull() {
      final var result = getUnansweredMessageInternalService.get(null, MAX_DAYS);

      assertEquals(Map.of(), result.getMessages());
    }

    @Test
    void shallReturnEmptyMapWhenPatientIdsIsEmpty() {
      final var result = getUnansweredMessageInternalService.get(Collections.emptyList(), MAX_DAYS);

      assertEquals(Map.of(), result.getMessages());
    }

    @Test
    void shallNotCallRepositoriesWhenPatientIdsIsNull() {
      getUnansweredMessageInternalService.get(null, MAX_DAYS);

      verify(patientEntityRepository, never()).findById(anyString());
      verify(certificateEntityRepository, never()).findCertificateEntitiesByPatient_Key(anyInt());
      verify(messageRepository, never()).findMessagesByCertificateKeyAndStatusSentAndCreatedAfter(
          any(), any(LocalDateTime.class));
    }
  }

  @Nested
  class InvalidPatientIds {

    @Test
    void shallSkipNullPatientId() {
      final var patientIds = Arrays.asList(ATHENA_REACT_ANDERSSON_ID, null);

      doReturn(Optional.of(patientEntity)).when(patientEntityRepository)
          .findById(ATHENA_REACT_ANDERSSON_ID_WITHOUT_DASH);
      doReturn(Optional.of(List.of(certificateEntity))).when(certificateEntityRepository)
          .findCertificateEntitiesByPatient_Key(PATIENT_KEY);
      doReturn(List.of(complementMessageBuilder().build())).when(messageRepository)
          .findMessagesByCertificateKeyAndStatusSentAndCreatedAfter(any(),
              any(LocalDateTime.class));

      final var result = getUnansweredMessageInternalService.get(patientIds, MAX_DAYS);

      assertEquals(1, result.getMessages().size());
      assertTrue(result.getMessages().containsKey(CERTIFICATE_ID));
    }

    @Test
    void shallSkipBlankPatientId() {
      final var patientIds = List.of(ATHENA_REACT_ANDERSSON_ID, "   ");

      doReturn(Optional.of(patientEntity)).when(patientEntityRepository)
          .findById(ATHENA_REACT_ANDERSSON_ID_WITHOUT_DASH);
      doReturn(Optional.of(List.of(certificateEntity))).when(certificateEntityRepository)
          .findCertificateEntitiesByPatient_Key(PATIENT_KEY);
      doReturn(List.of(complementMessageBuilder().build())).when(messageRepository)
          .findMessagesByCertificateKeyAndStatusSentAndCreatedAfter(any(),
              any(LocalDateTime.class));

      final var result = getUnansweredMessageInternalService.get(patientIds, MAX_DAYS);

      assertEquals(1, result.getMessages().size());
      assertTrue(result.getMessages().containsKey(CERTIFICATE_ID));
    }

    @Test
    void shallReturnEmptyMapWhenPatientNotFound() {
      final var patientIds = List.of(ATHENA_REACT_ANDERSSON_ID);

      doReturn(Optional.empty()).when(patientEntityRepository)
          .findById(ATHENA_REACT_ANDERSSON_ID_WITHOUT_DASH);

      final var result = getUnansweredMessageInternalService.get(patientIds, MAX_DAYS);

      assertEquals(Map.of(), result.getMessages());
    }

    @Test
    void shallReturnEmptyMapWhenCertificatesNotFound() {
      final var patientIds = List.of(ATHENA_REACT_ANDERSSON_ID);

      doReturn(Optional.of(patientEntity)).when(patientEntityRepository)
          .findById(ATHENA_REACT_ANDERSSON_ID_WITHOUT_DASH);
      doReturn(Optional.empty()).when(certificateEntityRepository)
          .findCertificateEntitiesByPatient_Key(PATIENT_KEY);

      final var result = getUnansweredMessageInternalService.get(patientIds, MAX_DAYS);

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

      final var validPatientEntity = athenaReactAnderssonEntityBuilder()
          .key(2)
          .id(validPatientId)
          .build();

      doReturn(Optional.empty()).when(patientEntityRepository)
          .findById(invalidPatientId);
      doReturn(Optional.of(validPatientEntity)).when(patientEntityRepository)
          .findById(validPatientId);
      doReturn(Optional.of(List.of(certificateEntity))).when(certificateEntityRepository)
          .findCertificateEntitiesByPatient_Key(2);
      doReturn(List.of(complementMessageBuilder().build())).when(messageRepository)
          .findMessagesByCertificateKeyAndStatusSentAndCreatedAfter(any(),
              any(LocalDateTime.class));

      final var result = getUnansweredMessageInternalService.get(patientIds, MAX_DAYS);

      assertEquals(1, result.getMessages().size());
      assertTrue(result.getMessages().containsKey(CERTIFICATE_ID));
    }

    @Test
    void shallHandleExceptionWhenCertificateNotFoundAndContinueProcessing() {
      final var validPatientId = "191212121212";
      final var patientWithNoCertsId = "181818181818";
      final var patientIds = List.of(validPatientId, patientWithNoCertsId);

      final var validPatientEntity = athenaReactAnderssonEntityBuilder()
          .key(2)
          .id(validPatientId)
          .build();

      final var patientWithNoCerts = athenaReactAnderssonEntityBuilder()
          .key(3)
          .id(patientWithNoCertsId)
          .build();

      doReturn(Optional.of(validPatientEntity)).when(patientEntityRepository)
          .findById(validPatientId);
      doReturn(Optional.of(patientWithNoCerts)).when(patientEntityRepository)
          .findById(patientWithNoCertsId);
      doReturn(Optional.of(List.of(certificateEntity))).when(certificateEntityRepository)
          .findCertificateEntitiesByPatient_Key(2);
      doReturn(Optional.empty()).when(certificateEntityRepository)
          .findCertificateEntitiesByPatient_Key(3);
      doReturn(List.of(complementMessageBuilder().build())).when(messageRepository)
          .findMessagesByCertificateKeyAndStatusSentAndCreatedAfter(any(),
              any(LocalDateTime.class));

      final var result = getUnansweredMessageInternalService.get(patientIds, MAX_DAYS);

      assertEquals(1, result.getMessages().size());
      assertTrue(result.getMessages().containsKey(CERTIFICATE_ID));
    }

    @Test
    void shallProcessMultiplePatientsSuccessfully() {
      final var patientId1 = "191212121212";
      final var patientId2 = "181818181818";
      final var patientIds = List.of(patientId1, patientId2);

      final var patientEntity1 = athenaReactAnderssonEntityBuilder()
          .key(2)
          .id(patientId1)
          .build();

      final var patientEntity2 = athenaReactAnderssonEntityBuilder()
          .key(3)
          .id(patientId2)
          .build();

      final var certificateEntity1 = certificateEntityBuilder()
          .key(101L)
          .certificateId("cert1")
          .patient(patientEntity1)
          .build();

      final var certificateEntity2 = certificateEntityBuilder()
          .key(102L)
          .certificateId("cert2")
          .patient(patientEntity2)
          .build();

      doReturn(Optional.of(patientEntity1)).when(patientEntityRepository)
          .findById(patientId1);
      doReturn(Optional.of(patientEntity2)).when(patientEntityRepository)
          .findById(patientId2);
      doReturn(Optional.of(List.of(certificateEntity1))).when(certificateEntityRepository)
          .findCertificateEntitiesByPatient_Key(2);
      doReturn(Optional.of(List.of(certificateEntity2))).when(certificateEntityRepository)
          .findCertificateEntitiesByPatient_Key(3);
      doReturn(List.of(complementMessageBuilder().build())).when(messageRepository)
          .findMessagesByCertificateKeyAndStatusSentAndCreatedAfter(eq(101L),
              any(LocalDateTime.class));
      doReturn(List.of(complementMessageBuilder().build(), complementMessageBuilder().build()))
          .when(messageRepository)
          .findMessagesByCertificateKeyAndStatusSentAndCreatedAfter(eq(102L),
              any(LocalDateTime.class));

      final var result = getUnansweredMessageInternalService.get(patientIds, MAX_DAYS);

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

      doReturn(Optional.of(patientEntity)).when(patientEntityRepository)
          .findById(ATHENA_REACT_ANDERSSON_ID_WITHOUT_DASH);
      doReturn(Optional.of(List.of(certificateEntity))).when(certificateEntityRepository)
          .findCertificateEntitiesByPatient_Key(PATIENT_KEY);
      doReturn(List.of(complementMessageBuilder().build())).when(messageRepository)
          .findMessagesByCertificateKeyAndStatusSentAndCreatedAfter(any(),
              any(LocalDateTime.class));

      getUnansweredMessageInternalService.get(patientIds, maxDays30);

      verify(messageRepository).findMessagesByCertificateKeyAndStatusSentAndCreatedAfter(
          eq(CERTIFICATE_KEY),
          any(LocalDateTime.class)
      );
    }

    @Test
    void shallPopulateMessagesMapWithCorrectCertificateIds() {
      final var patientIds = List.of(ATHENA_REACT_ANDERSSON_ID);

      doReturn(Optional.of(patientEntity)).when(patientEntityRepository)
          .findById(ATHENA_REACT_ANDERSSON_ID_WITHOUT_DASH);
      doReturn(Optional.of(List.of(certificateEntity, certificateEntity2)))
          .when(certificateEntityRepository)
          .findCertificateEntitiesByPatient_Key(PATIENT_KEY);
      doReturn(List.of(complementMessageBuilder().build())).when(messageRepository)
          .findMessagesByCertificateKeyAndStatusSentAndCreatedAfter(eq(CERTIFICATE_KEY),
              any(LocalDateTime.class));
      doReturn(Collections.emptyList()).when(messageRepository)
          .findMessagesByCertificateKeyAndStatusSentAndCreatedAfter(eq(CERTIFICATE_KEY_2),
              any(LocalDateTime.class));

      final var result = getUnansweredMessageInternalService.get(patientIds, MAX_DAYS);

      assertEquals(2, result.getMessages().size());
      assertEquals(CERTIFICATE_ID, certificateEntity.getCertificateId());
      assertEquals(CERTIFICATE_ID_2, certificateEntity2.getCertificateId());
      assertTrue(result.getMessages().containsKey(CERTIFICATE_ID));
      assertTrue(result.getMessages().containsKey(CERTIFICATE_ID_2));
    }
  }

  @Nested
  class SuccessfulRetrieval {

    @Test
    void shallReturnMessagesForSinglePatient() {
      final var patientIds = List.of(ATHENA_REACT_ANDERSSON_ID);
      final var messages = List.of(
          complementMessageBuilder().build(),
          complementMessageBuilder().build()
      );

      doReturn(Optional.of(patientEntity)).when(patientEntityRepository)
          .findById(ATHENA_REACT_ANDERSSON_ID_WITHOUT_DASH);
      doReturn(Optional.of(List.of(certificateEntity))).when(certificateEntityRepository)
          .findCertificateEntitiesByPatient_Key(PATIENT_KEY);
      doReturn(messages).when(messageRepository)
          .findMessagesByCertificateKeyAndStatusSentAndCreatedAfter(any(),
              any(LocalDateTime.class));

      final var result = getUnansweredMessageInternalService.get(patientIds, MAX_DAYS);

      assertEquals(1, result.getMessages().size());
      assertTrue(result.getMessages().containsKey(CERTIFICATE_ID));
      assertEquals(2, result.getMessages().get(CERTIFICATE_ID).complement());
    }

    @Test
    void shallReturnMessagesForMultipleCertificates() {
      final var patientIds = List.of(ATHENA_REACT_ANDERSSON_ID);
      final var messages1 = List.of(complementMessageBuilder().build());
      final var messages2 = List.of(
          complementMessageBuilder().build(),
          complementMessageBuilder().build()
      );

      doReturn(Optional.of(patientEntity)).when(patientEntityRepository)
          .findById(ATHENA_REACT_ANDERSSON_ID_WITHOUT_DASH);
      doReturn(Optional.of(List.of(certificateEntity, certificateEntity2)))
          .when(certificateEntityRepository)
          .findCertificateEntitiesByPatient_Key(PATIENT_KEY);
      doReturn(messages1).when(messageRepository)
          .findMessagesByCertificateKeyAndStatusSentAndCreatedAfter(eq(CERTIFICATE_KEY),
              any(LocalDateTime.class));
      doReturn(messages2).when(messageRepository)
          .findMessagesByCertificateKeyAndStatusSentAndCreatedAfter(eq(CERTIFICATE_KEY_2),
              any(LocalDateTime.class));

      final var result = getUnansweredMessageInternalService.get(patientIds, MAX_DAYS);

      assertEquals(2, result.getMessages().size());
      assertTrue(result.getMessages().containsKey(CERTIFICATE_ID));
      assertTrue(result.getMessages().containsKey(CERTIFICATE_ID_2));
      assertEquals(1, result.getMessages().get(CERTIFICATE_ID).complement());
      assertEquals(2, result.getMessages().get(CERTIFICATE_ID_2).complement());
    }

    @Test
    void shallRemoveDashesFromPatientId() {
      final var patientIds = List.of(ATHENA_REACT_ANDERSSON_ID);

      doReturn(Optional.of(patientEntity)).when(patientEntityRepository)
          .findById(ATHENA_REACT_ANDERSSON_ID_WITHOUT_DASH);
      doReturn(Optional.of(List.of(certificateEntity))).when(certificateEntityRepository)
          .findCertificateEntitiesByPatient_Key(PATIENT_KEY);
      doReturn(Collections.emptyList()).when(messageRepository)
          .findMessagesByCertificateKeyAndStatusSentAndCreatedAfter(any(),
              any(LocalDateTime.class));

      getUnansweredMessageInternalService.get(patientIds, MAX_DAYS);

      verify(patientEntityRepository).findById(ATHENA_REACT_ANDERSSON_ID_WITHOUT_DASH);
    }

    @Test
    void shallReturnEmptyMessagesWhenNoMessagesFound() {
      final var patientIds = List.of(ATHENA_REACT_ANDERSSON_ID);

      doReturn(Optional.of(patientEntity)).when(patientEntityRepository)
          .findById(ATHENA_REACT_ANDERSSON_ID_WITHOUT_DASH);
      doReturn(Optional.of(List.of(certificateEntity))).when(certificateEntityRepository)
          .findCertificateEntitiesByPatient_Key(PATIENT_KEY);
      doReturn(Collections.emptyList()).when(messageRepository)
          .findMessagesByCertificateKeyAndStatusSentAndCreatedAfter(any(),
              any(LocalDateTime.class));

      final var result = getUnansweredMessageInternalService.get(patientIds, MAX_DAYS);

      assertEquals(1, result.getMessages().size());
      assertTrue(result.getMessages().containsKey(CERTIFICATE_ID));
      assertEquals(0, result.getMessages().get(CERTIFICATE_ID).complement());
    }
  }
}