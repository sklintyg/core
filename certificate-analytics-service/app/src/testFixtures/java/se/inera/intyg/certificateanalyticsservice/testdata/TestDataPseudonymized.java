package se.inera.intyg.certificateanalyticsservice.testdata;

import java.time.LocalDateTime;
import se.inera.intyg.certificateanalyticsservice.application.messages.model.PseudonymizedAnalyticsMessage;
import se.inera.intyg.certificateanalyticsservice.application.messages.model.PseudonymizedAnalyticsMessage.PseudonymizedAnalyticsMessageBuilder;

public class TestDataPseudonymized {

  public static PseudonymizedAnalyticsMessageBuilder draftPseudonymizedMessageBuilder() {
    return PseudonymizedAnalyticsMessage.builder()
        .messageId("M-P4rHB5bMLzQQrCTlprRA")
        .eventTimestamp(LocalDateTime.parse("2025-09-29T17:49:58.616648"))
        .eventMessageType("certificate.analytics.event")
        .eventStaffId("IlXi3vfzsRwLaNRjpqYxOQ")
        .eventRole("LAKARE")
        .eventUnitId("TSTNMT2321000156-ALMC")
        .eventCareProviderId("TSTNMT2321000156-ALFA")
        .eventOrigin("NORMAL")
        .eventSessionId("GRmmGqqMdm6mFSy9ZCfT5w")
        .certificateId("Xsg4sVYtNq_zMGU_wWrJgw")
        .certificateType("fk7210")
        .certificateTypeVersion("1.0")
        .certificatePatientId("v4WI46Ymy08FKdhJJFDocw")
        .certificateUnitId("TSTNMT2321000156-ALMC")
        .certificateCareProviderId("TSTNMT2321000156-ALFA");
  }
}
