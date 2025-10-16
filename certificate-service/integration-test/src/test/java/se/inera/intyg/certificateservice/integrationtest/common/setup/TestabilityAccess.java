package se.inera.intyg.certificateservice.integrationtest.common.setup;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TestabilityAccess {

  boolean canReceiveQuestions;

  boolean nurseCanForwardCertificate;
  boolean midwifeCanForwardCertificate;

  boolean canDentistsUseType;

  boolean availableForPatient;

  boolean midwifeCanMarkReadyForSignCertificate;
  boolean nurseCanMarkReadyForSignCertificate;
}
