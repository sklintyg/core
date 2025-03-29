package se.inera.intyg.certificateservice.infrastructure.logging;

import static se.inera.intyg.certificateservice.logging.MdcLogConstants.EVENT_ACTION;
import static se.inera.intyg.certificateservice.logging.MdcLogConstants.EVENT_CATEGORY;
import static se.inera.intyg.certificateservice.logging.MdcLogConstants.EVENT_CATEGORY_PROCESS;
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

import java.util.Arrays;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEvent;
import se.inera.intyg.certificateservice.domain.event.service.CertificateEventSubscriber;
import se.inera.intyg.certificateservice.logging.HashUtility;
import se.inera.intyg.certificateservice.logging.MdcCloseableMap;

@Service
@Slf4j
@RequiredArgsConstructor
public class CertificateEventLogService implements CertificateEventSubscriber {

  private final HashUtility hashUtility;

  @Override
  public void event(CertificateEvent event) {
    final var actionEvaluation = Optional.ofNullable(event.actionEvaluation());
    try (MdcCloseableMap mdc = MdcCloseableMap.builder()
        .put(EVENT_ACTION, eventAction(event))
        .put(EVENT_CATEGORY, eventCategory())
        .put(EVENT_TYPE, eventType(event))
        .put(EVENT_START, eventStart(event))
        .put(EVENT_END, eventEnd(event))
        .put(EVENT_DURATION, eventDuration(event))
        .put(EVENT_CERTIFICATE_ID, eventCertificateId(event))
        .put(EVENT_CERTIFICATE_TYPE, eventCertificateType(event))
        .put(EVENT_CERTIFICATE_VERSION, eventCertificateVersion(event))
        .put(EVENT_CERTIFICATE_UNIT_ID, eventCertificateUnitId(event))
        .put(EVENT_CERTIFICATE_CARE_UNIT_ID, eventCertificateCareUnitId(event))
        .put(EVENT_CERTIFICATE_CARE_PROVIDER_ID, eventCertificateCareProviderId(event))
        .put(EVENT_CERTIFICATE_PATIENT_ID, eventCertificatePatientId(event))
        .put(USER_ID,
            actionEvaluation.isPresent() ? userId(event) : eventCertificatePatientId(event)
        )
        .put(USER_ROLE, actionEvaluation.isPresent() ? userRoles(event) : "-")
        .put(ORGANIZATION_ID, actionEvaluation.isPresent() ? organizationId(event) : "-")
        .put(ORGANIZATION_CARE_UNIT_ID,
            actionEvaluation.isPresent() ? organizationCareUnitId(event) : "-"
        )
        .put(ORGANIZATION_CARE_PROVIDER_ID,
            actionEvaluation.isPresent() ? organizationCareProviderId(event) : "-"
        )
        .build()) {
      log.info("CertificateEvent '{}' occurred on certificate '{}'.",
          event.type().name(), event.certificate().id().id()
      );
    }
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

  private String eventCertificatePatientId(CertificateEvent event) {
    return hashUtility.hash(event.certificate().certificateMetaData().patient().id().id());
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