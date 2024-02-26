package se.inera.intyg.certificateservice.infrastructure.logging;

import static se.inera.intyg.certificateservice.infrastructure.logging.MDCLogConstants.EVENT_ACTION;
import static se.inera.intyg.certificateservice.infrastructure.logging.MDCLogConstants.EVENT_CATEGORY;
import static se.inera.intyg.certificateservice.infrastructure.logging.MDCLogConstants.EVENT_CATEGORY_PROCESS;
import static se.inera.intyg.certificateservice.infrastructure.logging.MDCLogConstants.EVENT_CERTIFICATE_CARE_PROVIDER_ID;
import static se.inera.intyg.certificateservice.infrastructure.logging.MDCLogConstants.EVENT_CERTIFICATE_CARE_UNIT_ID;
import static se.inera.intyg.certificateservice.infrastructure.logging.MDCLogConstants.EVENT_CERTIFICATE_ID;
import static se.inera.intyg.certificateservice.infrastructure.logging.MDCLogConstants.EVENT_CERTIFICATE_PATIENT_ID;
import static se.inera.intyg.certificateservice.infrastructure.logging.MDCLogConstants.EVENT_CERTIFICATE_TYPE;
import static se.inera.intyg.certificateservice.infrastructure.logging.MDCLogConstants.EVENT_CERTIFICATE_UNIT_ID;
import static se.inera.intyg.certificateservice.infrastructure.logging.MDCLogConstants.EVENT_CERTIFICATE_VERSION;
import static se.inera.intyg.certificateservice.infrastructure.logging.MDCLogConstants.EVENT_DURATION;
import static se.inera.intyg.certificateservice.infrastructure.logging.MDCLogConstants.EVENT_END;
import static se.inera.intyg.certificateservice.infrastructure.logging.MDCLogConstants.EVENT_START;
import static se.inera.intyg.certificateservice.infrastructure.logging.MDCLogConstants.EVENT_TYPE;
import static se.inera.intyg.certificateservice.infrastructure.logging.MDCLogConstants.ORGANIZATION_CARE_PROVIDER_ID;
import static se.inera.intyg.certificateservice.infrastructure.logging.MDCLogConstants.ORGANIZATION_CARE_UNIT_ID;
import static se.inera.intyg.certificateservice.infrastructure.logging.MDCLogConstants.ORGANIZATION_ID;
import static se.inera.intyg.certificateservice.infrastructure.logging.MDCLogConstants.USER_ID;
import static se.inera.intyg.certificateservice.infrastructure.logging.MDCLogConstants.USER_ROLE;

import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEvent;
import se.inera.intyg.certificateservice.domain.event.service.CertificateEventSubscriber;

@Service
@Slf4j
public class CertificateEventLogService implements CertificateEventSubscriber {

  @Override
  public void event(CertificateEvent event) {
    populateMDC(event);

    log.info("CertificateEvent '{}' occurred on certificate '{}'.",
        event.type().name(), event.certificate().id().id()
    );
  }

  private void populateMDC(CertificateEvent event) {
    MDC.put(EVENT_ACTION, eventAction(event));
    MDC.put(EVENT_CATEGORY, eventCategory());
    MDC.put(EVENT_TYPE, eventType(event));
    MDC.put(EVENT_START, eventStart(event));
    MDC.put(EVENT_END, eventEnd(event));
    MDC.put(EVENT_DURATION, eventDuration(event));
    MDC.put(EVENT_CERTIFICATE_ID, eventCertificateId(event));
    MDC.put(EVENT_CERTIFICATE_TYPE, eventCertificateType(event));
    MDC.put(EVENT_CERTIFICATE_VERSION, eventCertificateVersion(event));
    MDC.put(EVENT_CERTIFICATE_UNIT_ID, eventCertificateUnitId(event));
    MDC.put(EVENT_CERTIFICATE_CARE_UNIT_ID, eventCertificateCareUnitId(event));
    MDC.put(EVENT_CERTIFICATE_CARE_PROVIDER_ID,
        eventCertificateCareProviderId(event));
    MDC.put(EVENT_CERTIFICATE_PATIENT_ID, eventCertificatePatientId(event));
    MDC.put(USER_ID, userId(event));
    MDC.put(USER_ROLE, userRoles(event));
    MDC.put(ORGANIZATION_ID, organizationId(event));
    MDC.put(ORGANIZATION_CARE_UNIT_ID, organizationCareUnitId(event));
    MDC.put(ORGANIZATION_CARE_PROVIDER_ID, organizationCareProviderId(event));
  }

  private static String eventAction(CertificateEvent event) {
    return event.type().action();
  }

  private static String eventCategory() {
    return EVENT_CATEGORY_PROCESS;
  }

  private static String eventType(CertificateEvent event) {
    return Arrays.toString(new String[]{event.type().actionType()});
  }

  private static String eventStart(CertificateEvent event) {
    return event.start().toString();
  }

  private static String eventEnd(CertificateEvent event) {
    return event.end().toString();
  }

  private static String eventDuration(CertificateEvent event) {
    return Long.toString(event.duration());
  }

  private static String eventCertificateId(CertificateEvent event) {
    return event.certificate().id().id();
  }

  private static String eventCertificateType(CertificateEvent event) {
    return event.certificate().certificateModel().id().type().type();
  }

  private static String eventCertificateVersion(CertificateEvent event) {
    return event.certificate().certificateModel().id().version().version();
  }

  private static String eventCertificateUnitId(CertificateEvent event) {
    return event.certificate().certificateMetaData().issuingUnit().hsaId().id();
  }

  private static String eventCertificateCareUnitId(CertificateEvent event) {
    return event.certificate().certificateMetaData().careUnit().hsaId().id();
  }

  private static String eventCertificateCareProviderId(CertificateEvent event) {
    return event.certificate().certificateMetaData().careProvider().hsaId().id();
  }

  private static String eventCertificatePatientId(CertificateEvent event) {
    return HashUtility.hash(event.certificate().certificateMetaData().patient().id().id());
  }

  private static String userId(CertificateEvent event) {
    return event.actionEvaluation().user().hsaId().id();
  }

  private static String userRoles(CertificateEvent event) {
    return Arrays.toString(new String[]{event.actionEvaluation().user().role().name()});
  }

  private static String organizationId(CertificateEvent event) {
    return event.actionEvaluation().subUnit().hsaId().id();
  }

  private static String organizationCareUnitId(CertificateEvent event) {
    return event.actionEvaluation().careUnit().hsaId().id();
  }

  private static String organizationCareProviderId(CertificateEvent event) {
    return event.actionEvaluation().careProvider().hsaId().id();
  }
}
