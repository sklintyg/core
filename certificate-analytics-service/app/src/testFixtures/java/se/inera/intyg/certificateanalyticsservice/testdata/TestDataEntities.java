package se.inera.intyg.certificateanalyticsservice.testdata;

import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.CARE_PROVIDER_ID;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.CERTIFICATE_ID;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.CERTIFICATE_TYPE;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.CERTIFICATE_TYPE_VERSION;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.ORIGIN;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.PATIENT_ID;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.ROLE;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.ROLE_KEY;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.SESSION_ID;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.STAFF_ID;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.TIMESTAMP;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.UNIT_ID;

import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.CareProviderEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.CertificateEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.CertificateTypeEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.EventEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.EventTypeEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.OriginEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.PatientEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.RoleEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.SessionEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.TimeEntity;
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
        .certificateId(CERTIFICATE_ID)
        .certificateType(certificateTypeEntity())
        .patient(patientEntity())
        .unit(unitEntity())
        .careProvider(careProviderEntity())
        .build();
  }

  public static PatientEntity patientEntity() {
    return PatientEntity.builder()
        .patientId(PATIENT_ID)
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
        .time(timeEntity())
        .origin(originEntity())
        .eventType(eventTypeEntity())
        .role(roleEntity())
        .build();
  }

  public static UserEntity userEntity() {
    return UserEntity.builder()
        .userId(STAFF_ID)
        .build();
  }

  public static SessionEntity sessionEntity() {
    return SessionEntity.builder()
        .sessionId(SESSION_ID)
        .build();
  }

  public static TimeEntity timeEntity() {
    return TimeEntity.builder()
        .date(TIMESTAMP)
        .year(TIMESTAMP.getYear())
        .month(TIMESTAMP.getMonthValue())
        .day(TIMESTAMP.getDayOfMonth())
        .hour(TIMESTAMP.getHour())
        .minute(TIMESTAMP.getMinute())
        .second(TIMESTAMP.getSecond())
        .build();
  }

  public static OriginEntity originEntity() {
    return OriginEntity.builder()
        .originKey(1)
        .origin(ORIGIN)
        .build();
  }

  public static EventTypeEntity eventTypeEntity() {
    return EventTypeEntity.builder()
        .eventTypeKey(1)
        .eventType("CREATED")
        .build();
  }

  public static RoleEntity roleEntity() {
    return RoleEntity.builder()
        .roleKey(ROLE_KEY)
        .role(ROLE)
        .build();
  }
}
