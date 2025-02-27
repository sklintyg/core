package se.inera.intyg.certificateservice.infrastructure.logging;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataAction.ACTION_EVALUATION;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProviderConstants.ALFA_REGIONEN_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.CERTIFICATE_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.FK7210_CERTIFICATE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7210_TYPE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7210_VERSION;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_ALLERGIMOTTAGNINGEN_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_HSA_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_ROLE;
import static se.inera.intyg.certificateservice.logging.MdcLogConstants.EVENT_ACTION;
import static se.inera.intyg.certificateservice.logging.MdcLogConstants.EVENT_CATEGORY;
import static se.inera.intyg.certificateservice.logging.MdcLogConstants.EVENT_CERTIFICATE_CARE_PROVIDER_ID;
import static se.inera.intyg.certificateservice.logging.MdcLogConstants.EVENT_CERTIFICATE_CARE_UNIT_ID;
import static se.inera.intyg.certificateservice.logging.MdcLogConstants.EVENT_CERTIFICATE_ID;
import static se.inera.intyg.certificateservice.logging.MdcLogConstants.EVENT_CERTIFICATE_PATIENT_ID;
import static se.inera.intyg.certificateservice.logging.MdcLogConstants.EVENT_CERTIFICATE_TYPE;
import static se.inera.intyg.certificateservice.logging.MdcLogConstants.EVENT_CERTIFICATE_UNIT_ID;
import static se.inera.intyg.certificateservice.logging.MdcLogConstants.EVENT_CERTIFICATE_VERSION;
import static se.inera.intyg.certificateservice.logging.MdcLogConstants.EVENT_DURATION;
import static se.inera.intyg.certificateservice.logging.MdcLogConstants.EVENT_END;
import static se.inera.intyg.certificateservice.logging.MdcLogConstants.EVENT_START;
import static se.inera.intyg.certificateservice.logging.MdcLogConstants.EVENT_TYPE;
import static se.inera.intyg.certificateservice.logging.MdcLogConstants.ORGANIZATION_CARE_PROVIDER_ID;
import static se.inera.intyg.certificateservice.logging.MdcLogConstants.ORGANIZATION_CARE_UNIT_ID;
import static se.inera.intyg.certificateservice.logging.MdcLogConstants.ORGANIZATION_ID;
import static se.inera.intyg.certificateservice.logging.MdcLogConstants.USER_ID;
import static se.inera.intyg.certificateservice.logging.MdcLogConstants.USER_ROLE;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.Appender;
import java.time.LocalDateTime;
import java.time.ZoneId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEvent;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEvent.CertificateEventBuilder;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEventType;
import se.inera.intyg.certificateservice.logging.HashUtility;

@ExtendWith(MockitoExtension.class)
class CertificateEventLogServiceTest {

  @Spy
  private HashUtility hashUtility;
  @Mock
  private Appender<ILoggingEvent> mockAppender;
  @Captor
  private ArgumentCaptor<LoggingEvent> captorLoggingEvent;
  @InjectMocks
  private CertificateEventLogService certificateEventLogService;
  private CertificateEvent certificateEvent;

  @BeforeEach
  void setUp() {
    final Logger logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
    logger.addAppender(mockAppender);

    final var now = LocalDateTime.now(ZoneId.systemDefault());
    certificateEvent = getCertificateEventBuilder(now)
        .build();
  }

  private static CertificateEventBuilder getCertificateEventBuilder(LocalDateTime now) {
    return CertificateEvent.builder()
        .start(now.minusSeconds(1))
        .end(now)
        .type(CertificateEventType.READ)
        .certificate(FK7210_CERTIFICATE)
        .actionEvaluation(ACTION_EVALUATION);
  }

  @AfterEach
  void tearDown() {
    final Logger logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
    logger.detachAppender(mockAppender);
  }

  @Test
  void shallLogMessage() {
    certificateEventLogService.event(certificateEvent);
    assertEquals(
        "CertificateEvent '%s' occurred on certificate '%s'."
            .formatted(CertificateEventType.READ.name(), CERTIFICATE_ID.id()),
        getFormattedMessage()
    );
  }

  @Test
  void shallIncludeEventActionInMDC() {
    certificateEventLogService.event(certificateEvent);
    assertEquals(certificateEvent.type().action(), getValueFromMDC(EVENT_ACTION));
  }

  @Test
  void shallIncludeEventTypeInMDC() {
    certificateEventLogService.event(certificateEvent);
    assertEquals("[" + certificateEvent.type().actionType() + "]", getValueFromMDC(EVENT_TYPE));
  }

  @Test
  void shallIncludeEventCategoryInMDC() {
    certificateEventLogService.event(certificateEvent);
    assertEquals("[" + "process" + "]", getValueFromMDC(EVENT_CATEGORY));
  }

  @Test
  void shallIncludeEventStartInMDC() {
    certificateEventLogService.event(certificateEvent);
    assertEquals(certificateEvent.start().toString(), getValueFromMDC(EVENT_START));
  }

  @Test
  void shallIncludeEventEndInMDC() {
    certificateEventLogService.event(certificateEvent);
    assertEquals(certificateEvent.end().toString(), getValueFromMDC(EVENT_END));
  }

  @Test
  void shallIncludeEventDurationInMDC() {
    certificateEventLogService.event(certificateEvent);
    assertEquals("1000", getValueFromMDC(EVENT_DURATION));
  }

  @Test
  void shallIncludeCertificateIdInMDC() {
    certificateEventLogService.event(certificateEvent);
    assertEquals(CERTIFICATE_ID.id(), getValueFromMDC(EVENT_CERTIFICATE_ID));
  }

  @Test
  void shallIncludeCertificateTypeInMDC() {
    certificateEventLogService.event(certificateEvent);
    assertEquals(FK7210_TYPE.type(), getValueFromMDC(EVENT_CERTIFICATE_TYPE));
  }

  @Test
  void shallIncludeCertificateVersionInMDC() {
    certificateEventLogService.event(certificateEvent);
    assertEquals(FK7210_VERSION.version(), getValueFromMDC(EVENT_CERTIFICATE_VERSION));
  }

  @Test
  void shallIncludeCertificateUnitIdInMDC() {
    certificateEventLogService.event(certificateEvent);
    assertEquals(ALFA_ALLERGIMOTTAGNINGEN_ID, getValueFromMDC(EVENT_CERTIFICATE_UNIT_ID));
  }

  @Test
  void shallIncludeCertificateCareUnitIdInMDC() {
    certificateEventLogService.event(certificateEvent);
    assertEquals(ALFA_MEDICINCENTRUM_ID, getValueFromMDC(EVENT_CERTIFICATE_CARE_UNIT_ID));
  }

  @Test
  void shallIncludeCertificateCareProviderIdInMDC() {
    certificateEventLogService.event(certificateEvent);
    assertEquals(ALFA_REGIONEN_ID, getValueFromMDC(EVENT_CERTIFICATE_CARE_PROVIDER_ID));
  }

  @Test
  void shallIncludeCertificatePatientIdAsHashInMDC() {
    certificateEventLogService.event(certificateEvent);
    assertEquals(
        hashUtility.hash(ATHENA_REACT_ANDERSSON_ID),
        getValueFromMDC(EVENT_CERTIFICATE_PATIENT_ID)
    );
  }

  @Test
  void shallIncludeUserIdInMDC() {
    certificateEventLogService.event(certificateEvent);
    assertEquals(AJLA_DOCTOR_HSA_ID, getValueFromMDC(USER_ID));
  }

  @Test
  void shallIncludeUserRoleInMDC() {
    certificateEventLogService.event(certificateEvent);
    assertEquals("[" + AJLA_DOCTOR_ROLE + "]", getValueFromMDC(USER_ROLE));
  }

  @Test
  void shallIncludeOrganizationUnitIdInMDC() {
    certificateEventLogService.event(certificateEvent);
    assertEquals(ALFA_ALLERGIMOTTAGNINGEN_ID, getValueFromMDC(ORGANIZATION_ID));
  }

  @Test
  void shallIncludeOrganizationCareUnitIdInMDC() {
    certificateEventLogService.event(certificateEvent);
    assertEquals(ALFA_MEDICINCENTRUM_ID, getValueFromMDC(ORGANIZATION_CARE_UNIT_ID));
  }

  @Test
  void shallIncludeOrganizationCareProviderIdInMDC() {
    certificateEventLogService.event(certificateEvent);
    assertEquals(ALFA_REGIONEN_ID, getValueFromMDC(ORGANIZATION_CARE_PROVIDER_ID));
  }

  @Test
  void shallIncludeUserIdInMDCWithPatientId() {
    final var now = LocalDateTime.now(ZoneId.systemDefault());
    final var event = getCertificateEventBuilder(now)
        .actionEvaluation(null)
        .build();
    certificateEventLogService.event(event);
    assertEquals(
        hashUtility.hash(ATHENA_REACT_ANDERSSON_ID),
        getValueFromMDC(USER_ID));
  }

  @Test
  void shallExcludeUserRoleInMDC() {
    final var now = LocalDateTime.now(ZoneId.systemDefault());
    final var event = getCertificateEventBuilder(now)
        .actionEvaluation(null)
        .build();
    certificateEventLogService.event(event);
    assertEquals("-", getValueFromMDC(USER_ROLE));
  }

  @Test
  void shallExcludeOrganizationUnitIdInMDC() {
    final var now = LocalDateTime.now(ZoneId.systemDefault());
    final var event = getCertificateEventBuilder(now)
        .actionEvaluation(null)
        .build();
    certificateEventLogService.event(event);
    assertEquals("-", getValueFromMDC(ORGANIZATION_ID));
  }

  @Test
  void shallExcludeOrganizationCareUnitIdInMDC() {
    final var now = LocalDateTime.now(ZoneId.systemDefault());
    final var event = getCertificateEventBuilder(now)
        .actionEvaluation(null)
        .build();
    certificateEventLogService.event(event);
    assertEquals("-", getValueFromMDC(ORGANIZATION_CARE_UNIT_ID));
  }

  @Test
  void shallExcludeOrganizationCareProviderIdInMDC() {
    final var now = LocalDateTime.now(ZoneId.systemDefault());
    final var event = getCertificateEventBuilder(now)
        .actionEvaluation(null)
        .build();
    certificateEventLogService.event(event);
    assertEquals("-", getValueFromMDC(ORGANIZATION_CARE_PROVIDER_ID));
  }

  private String getValueFromMDC(String key) {
    verify(mockAppender).doAppend(captorLoggingEvent.capture());
    final var loggingEvent = captorLoggingEvent.getValue();
    return loggingEvent.getMDCPropertyMap().get(key);
  }

  private String getFormattedMessage() {
    verify(mockAppender).doAppend(captorLoggingEvent.capture());
    final var loggingEvent = captorLoggingEvent.getValue();
    return loggingEvent.getFormattedMessage();
  }
}