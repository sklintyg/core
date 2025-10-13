package se.inera.intyg.certificateservice.integrationtest.common.setup;

import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.integrationtest.common.util.ApiUtil;
import se.inera.intyg.certificateservice.integrationtest.common.util.InternalApiUtil;
import se.inera.intyg.certificateservice.integrationtest.common.util.TestabilityApiUtil;

public abstract class BaseIntegrationIT {

  protected String wrongVersion() {
    return "wrongVersion";
  }

  protected String codeSystem() {
    return "b64ea353-e8f6-4832-b563-fc7d46f29548";
  }

  protected BaseTestabilityUtilities testabilityUtilities() {
    return BaseTestabilityUtilities.builder().build();
  }

  protected ApiUtil api() {
    return testabilityUtilities().getTestabilityUtilities().getApi();
  }

  protected InternalApiUtil internalApi() {
    return testabilityUtilities().getTestabilityUtilities().getInternalApi();
  }

  protected TestabilityApiUtil testabilityApi() {
    return testabilityUtilities().getTestabilityUtilities().getTestabilityApi();
  }

  protected String type() {
    return testabilityUtilities().getTestabilityCertificate().getType();
  }

  protected String typeVersion() {
    return testabilityUtilities().getTestabilityCertificate().getActiveVersion();
  }

  protected ElementId element() {
    return testabilityUtilities()
        .getTestabilityCertificate()
        .getValueForTest()
        .id();
  }

  protected Object value() {
    final var elementValue = testabilityUtilities()
        .getTestabilityCertificate()
        .getValueForTest()
        .value();

    if (elementValue instanceof ElementValueText elementValueText) {
      return elementValueText.text();
    }

    if (elementValue instanceof ElementValueBoolean elementValueBoolean) {
      return elementValueBoolean.value();
    }

    if (elementValue instanceof ElementValueDate elementValueDate) {
      return elementValueDate.date();
    }

    throw new IllegalStateException("Unknown element value type: " + elementValue.getClass());
  }

  protected String code() {
    return testabilityUtilities()
        .getTestabilityCertificate()
        .getCode();
  }

  protected Boolean canReceiveQuestions() {
    return testabilityUtilities()
        .getTestabilityCertificate()
        .getTestabilityAccess()
        .isCanReceiveQuestions();
  }

  protected boolean nurseCanForwardCertificate() {
    return testabilityUtilities()
        .getTestabilityCertificate()
        .getTestabilityAccess()
        .isNurseCanForwardCertificate();
  }

  protected boolean midwifeCanForwardCertificate() {
    return testabilityUtilities()
        .getTestabilityCertificate()
        .getTestabilityAccess()
        .isMidwifeCanForwardCertificate();
  }

  protected boolean canDentistsUseType() {
    return testabilityUtilities()
        .getTestabilityCertificate()
        .getTestabilityAccess()
        .isCanDentistsUseType();
  }

  protected boolean midwifeCanMarkReadyForSignCertificate() {
    return testabilityUtilities()
        .getTestabilityCertificate()
        .getTestabilityAccess()
        .isMidwifeCanMarkReadyForSignCertificate();
  }

  protected boolean nurseCanMarkReadyForSignCertificate() {
    return testabilityUtilities()
        .getTestabilityCertificate()
        .getTestabilityAccess()
        .isNurseCanMarkReadyForSignCertificate();
  }

  protected boolean isAvailableForPatient() {
    return testabilityUtilities()
        .getTestabilityCertificate()
        .getTestabilityAccess()
        .isAvailableForPatient();
  }

  protected String recipient() {
    return testabilityUtilities()
        .getTestabilityCertificate()
        .getRecipient();
  }

  protected String questionId() {
    return testabilityUtilities()
        .getTestabilityCertificate()
        .getValueForTest()
        .id()
        .id();
  }
}
