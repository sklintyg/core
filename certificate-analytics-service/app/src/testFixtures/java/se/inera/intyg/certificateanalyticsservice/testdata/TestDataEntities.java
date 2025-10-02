package se.inera.intyg.certificateanalyticsservice.testdata;

import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.CARE_PROVIDER_ID;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.CERTIFICATE_TYPE;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.CERTIFICATE_TYPE_VERSION;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.HASHED_PATIENT_ID;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.HASHED_USER_ID;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.ORIGIN;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.RECIPIENT;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.ROLE;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.TIMESTAMP;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.UNIT_ID;

import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.CareProviderEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.CertificateEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.CertificateTypeEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.EventEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.EventTypeEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.OriginEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.PatientEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.RecipientEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.RoleEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.SessionEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.UnitEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.UserEntity;

public class TestDataEntities {

  private TestDataEntities() {
    throw new IllegalStateException("Utility class");
  }

  public static CertificateTypeEntity certificateTypeEntity() {
    return CertificateTypeEntity.builder()
        .certificateType(CERTIFICATE_TYPE)
        .certificateTypeVersion(CERTIFICATE_TYPE_VERSION)
        .build();
  }

  public static CertificateEntity certificateEntity() {
    return CertificateEntity.builder()
        .certificateId(TestDataConstants.HASHED_CERTIFICATE_ID)
        .certificateType(certificateTypeEntity())
        .patient(patientEntity())
        .unit(unitEntity())
        .careProvider(careProviderEntity())
        .build();
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

  public static EventEntity createdEventEntity() {
    return EventEntity.builder()
        .certificate(certificateEntity())
        .unit(unitEntity())
        .careProvider(careProviderEntity())
        .user(userEntity())
        .session(sessionEntity())
        .timestamp(TIMESTAMP)
        .origin(originEntity())
        .eventType(eventTypeEntity())
        .role(roleEntity())
        .messageId(TestDataConstants.HASHED_MESSAGE_ID)
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
        .eventType(TestDataConstants.EVENT_TYPE_DRAFT_CREATED)
        .build();
  }

  public static RoleEntity roleEntity() {
    return RoleEntity.builder()
        .role(ROLE)
        .build();
  }

  public static RecipientEntity recipientEntity() {
    return RecipientEntity.builder()
        .recipient(RECIPIENT)
        .build();
  }
}
