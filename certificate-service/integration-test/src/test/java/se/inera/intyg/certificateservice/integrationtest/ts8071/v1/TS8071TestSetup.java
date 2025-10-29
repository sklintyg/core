package se.inera.intyg.certificateservice.integrationtest.ts8071.v1;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionOvrigBeskrivning.QUESTION_OVRIG_BESKRIVNING_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionOvrigBeskrivning.QUESTION_OVRIG_BESKRIVNING_ID;

import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.integrationtest.common.setup.BaseTestabilityUtilities;
import se.inera.intyg.certificateservice.integrationtest.common.setup.TestabilityAccess;
import se.inera.intyg.certificateservice.integrationtest.common.setup.TestabilityCertificate;

public class TS8071TestSetup {

  public static final String TYPE = "TS8071 - ";
  private static final String CODE = "TS8071";
  private static final String CERTIFICATE_TYPE = "ts8071";
  private static final String ACTIVE_CERTIFICATE_TYPE_VERSION = "1.0";
  private static final String RECIPIENT = "TRANSP";
  private static final String VALUE = "Svarstext för övrig beskrivning.";

  public static BaseTestabilityUtilities.BaseTestabilityUtilitiesBuilder ts8071TestSetup() {
    return BaseTestabilityUtilities.builder()
        .testabilityCertificate(
            TestabilityCertificate.builder()
                .type(CERTIFICATE_TYPE)
                .code(CODE)
                .activeVersion(ACTIVE_CERTIFICATE_TYPE_VERSION)
                .recipient(RECIPIENT)
                .valueForTest(
                    ElementData.builder()
                        .id(QUESTION_OVRIG_BESKRIVNING_ID)
                        .value(
                            ElementValueText.builder()
                                .textId(QUESTION_OVRIG_BESKRIVNING_FIELD_ID)
                                .text(VALUE)
                                .build()
                        )
                        .build()
                )
                .testabilityAccess(
                    TestabilityAccess.builder()
                        .canReceiveQuestions(false)
                        .nurseCanForwardCertificate(true)
                        .midwifeCanForwardCertificate(true)
                        .canDentistsUseType(false)
                        .availableForPatient(true)
                        .midwifeCanMarkReadyForSignCertificate(true)
                        .nurseCanMarkReadyForSignCertificate(true)
                        .build()
                )
                .build()
        );
  }
}
