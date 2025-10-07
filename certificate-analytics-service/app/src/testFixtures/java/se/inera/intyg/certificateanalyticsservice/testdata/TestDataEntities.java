package se.inera.intyg.certificateanalyticsservice.testdata;

import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.CARE_PROVIDER_ID;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.CERTIFICATE_TYPE;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.CERTIFICATE_TYPE_VERSION;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.HASHED_CERTIFICATE_PARENT_ID;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.HASHED_PATIENT_ID;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.HASHED_USER_ID;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.ORIGIN;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.RECIPIENT;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.ROLE;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.TIMESTAMP;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.UNIT_ID;

import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.CareProviderEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.CertificateEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.CertificateEntity.CertificateEntityBuilder;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.CertificateRelationEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.CertificateRelationTypeEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.EventEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.EventEntity.EventEntityBuilder;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.EventTypeEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.MessageEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.MessageEntity.MessageEntityBuilder;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.MessageTypeEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.OriginEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.PartyEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.PatientEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.RoleEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.SessionEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.UnitEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.UserEntity;

public class TestDataEntities {

  private TestDataEntities() {
    throw new IllegalStateException("Utility class");
  }

  public static EventEntityBuilder sentEventEntityBuilder() {
    return EventEntity.builder()
        .certificate(certificateEntity().build())
        .messageId(TestDataConstants.HASHED_ID)
        .timestamp(TIMESTAMP)
        .eventType(eventTypeEntity())
        .role(roleEntity())
        .patient(patientEntity())
        .unit(unitEntity())
        .careProvider(careProviderEntity())
        .user(userEntity())
        .session(sessionEntity())
        .origin(originEntity())
        .recipient(recipientPartyEntity());
  }

  public static EventEntityBuilder messageSentEventEntityBuilder() {
    return EventEntity.builder()
        .certificate(certificateEntity().build())
        .message(messageEntity().build())
        .messageId(TestDataConstants.HASHED_ID)
        .timestamp(TIMESTAMP)
        .eventType(
            EventTypeEntity.builder()
                .eventType(TestDataConstants.EVENT_TYPE_COMPLEMENT_FROM_RECIPIENT)
                .build()
        )
        .role(roleEntity())
        .patient(patientEntity())
        .unit(unitEntity())
        .careProvider(careProviderEntity())
        .user(userEntity())
        .session(sessionEntity())
        .origin(originEntity())
        .recipient(recipientPartyEntity());
  }

  public static CertificateEntityBuilder certificateEntity() {
    return CertificateEntity.builder()
        .certificateId(TestDataConstants.HASHED_CERTIFICATE_ID)
        .certificateType(CERTIFICATE_TYPE)
        .certificateTypeVersion(CERTIFICATE_TYPE_VERSION)
        .unit(unitEntity())
        .careProvider(careProviderEntity());
  }

  public static PatientEntity patientEntity() {
    return PatientEntity.builder()
        .patientId(HASHED_PATIENT_ID)
        .build();
  }

  public static CareProviderEntity careProviderEntity() {
    return CareProviderEntity.builder()
        .hsaId(CARE_PROVIDER_ID)
        .build();
  }

  public static UnitEntity unitEntity() {
    return UnitEntity.builder()
        .hsaId(UNIT_ID)
        .build();
  }

  public static UserEntity userEntity() {
    return UserEntity.builder()
        .userId(HASHED_USER_ID)
        .build();
  }

  public static SessionEntity sessionEntity() {
    return SessionEntity.builder()
        .sessionId(TestDataConstants.HASHED_SESSION_ID)
        .build();
  }

  public static OriginEntity originEntity() {
    return OriginEntity.builder()
        .origin(ORIGIN)
        .build();
  }

  public static EventTypeEntity eventTypeEntity() {
    return EventTypeEntity.builder()
        .eventType(TestDataConstants.EVENT_TYPE_CERTIFICATE_SENT)
        .build();
  }

  public static RoleEntity roleEntity() {
    return RoleEntity.builder()
        .role(ROLE)
        .build();
  }

  public static PartyEntity recipientPartyEntity() {
    return PartyEntity.builder()
        .party(RECIPIENT)
        .build();
  }

  public static CertificateRelationEntity certificateRelationEntity() {
    return CertificateRelationEntity.builder()
        .parentCertificate(
            certificateEntity()
                .certificateId(HASHED_CERTIFICATE_PARENT_ID)
                .build()
        )
        .childCertificate(certificateEntity().build())
        .relationType(
            CertificateRelationTypeEntity.builder()
                .relationType(TestDataConstants.CERTIFICATE_PARENT_TYPE)
                .build()
        )
        .build();
  }

  public static MessageTypeEntity messageTypeEntity() {
    return MessageTypeEntity.builder()
        .type(TestDataConstants.MESSAGE_TYPE)
        .build();
  }

  public static PartyEntity messageSenderPartyEntity() {
    return PartyEntity.builder()
        .party(TestDataConstants.MESSAGE_SENDER)
        .build();
  }

  public static PartyEntity messageRecipientPartyEntity() {
    return PartyEntity.builder()
        .party(TestDataConstants.MESSAGE_RECIPIENT)
        .build();
  }

  public static MessageEntityBuilder messageEntity() {
    return MessageEntity.builder()
        .messageId(TestDataConstants.HASHED_MESSAGE_ID)
        .messageAnswerId(TestDataConstants.HASHED_MESSAGE_ANSWER_ID)
        .messageReminderId(TestDataConstants.HASHED_MESSAGE_REMINDER_ID)
        .messageType(messageTypeEntity())
        .sent(TestDataConstants.MESSAGE_SENT)
        .lastDateToAnswer(TestDataConstants.MESSAGE_LAST_DATE_TO_ANSWER)
        .questionIds(java.util.List.of(
            TestDataConstants.MESSAGE_QUESTION_ID_1,
            TestDataConstants.MESSAGE_QUESTION_ID_2)
        )
        .sender(messageSenderPartyEntity())
        .recipient(messageRecipientPartyEntity());
  }
}
