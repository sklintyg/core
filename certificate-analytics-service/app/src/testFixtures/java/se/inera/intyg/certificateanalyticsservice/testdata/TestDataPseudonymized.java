package se.inera.intyg.certificateanalyticsservice.testdata;

import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.CARE_PROVIDER_ID;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.CERTIFICATE_TYPE;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.CERTIFICATE_TYPE_VERSION;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.EVENT_TIMESTAMP;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.EVENT_TYPE_DRAFT_CREATED;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.HASHED_CERTIFICATE_ID;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.HASHED_MESSAGE_ID;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.HASHED_PATIENT_ID;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.HASHED_SESSION_ID;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.HASHED_STAFF_ID;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.ORIGIN;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.ROLE;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants.UNIT_ID;

import java.time.LocalDateTime;
import se.inera.intyg.certificateanalyticsservice.application.messages.model.PseudonymizedAnalyticsMessage;
import se.inera.intyg.certificateanalyticsservice.application.messages.model.PseudonymizedAnalyticsMessage.PseudonymizedAnalyticsMessageBuilder;

public class TestDataPseudonymized {

  private TestDataPseudonymized() {
    throw new IllegalStateException("Utility class");
  }

  public static PseudonymizedAnalyticsMessageBuilder draftPseudonymizedMessageBuilder() {
    return PseudonymizedAnalyticsMessage.builder()
        .messageId(HASHED_MESSAGE_ID)
        .eventTimestamp(LocalDateTime.parse(EVENT_TIMESTAMP))
        .eventMessageType(EVENT_TYPE_DRAFT_CREATED)
        .eventUserId(HASHED_STAFF_ID)
        .eventRole(ROLE)
        .eventUnitId(UNIT_ID)
        .eventCareProviderId(CARE_PROVIDER_ID)
        .eventOrigin(ORIGIN)
        .eventSessionId(HASHED_SESSION_ID)
        .certificateId(HASHED_CERTIFICATE_ID)
        .certificateType(CERTIFICATE_TYPE)
        .certificateTypeVersion(CERTIFICATE_TYPE_VERSION)
        .certificatePatientId(HASHED_PATIENT_ID)
        .certificateUnitId(UNIT_ID)
        .certificateCareProviderId(CARE_PROVIDER_ID);
  }
}
