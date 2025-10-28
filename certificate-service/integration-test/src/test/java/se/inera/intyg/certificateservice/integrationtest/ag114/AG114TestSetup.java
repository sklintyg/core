package se.inera.intyg.certificateservice.integrationtest.ag114;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionSysselsattning.QUESTION_SYSSELSATTNING_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionSysselsattning.QUESTION_SYSSELSATTNING_ID;

import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.integrationtest.common.setup.BaseTestabilityUtilities;
import se.inera.intyg.certificateservice.integrationtest.common.setup.TestabilityAccess;
import se.inera.intyg.certificateservice.integrationtest.common.setup.TestabilityCertificate;

public class AG114TestSetup {

  public static final String TYPE = "AG114 - ";

  private static final String CERTIFICATE_TYPE = "ag114";
  private static final String ACTIVE_CERTIFICATE_TYPE_VERSION = "2.0";
  private static final String CODE = "AG1-14";
  private static final String VALUE = "Svarstext för sysselsättning";

  public static BaseTestabilityUtilities.BaseTestabilityUtilitiesBuilder ag114TestSetup() {
    return BaseTestabilityUtilities.builder()
        .testabilityCertificate(
            TestabilityCertificate.builder()
                .type(CERTIFICATE_TYPE)
                .code(CODE)
                .activeVersion(ACTIVE_CERTIFICATE_TYPE_VERSION)
                .valueForTest(
                    ElementData.builder()
                        .id(QUESTION_SYSSELSATTNING_ID)
                        .value(
                            ElementValueText.builder()
                                .textId(QUESTION_SYSSELSATTNING_FIELD_ID)
                                .text(VALUE)
                                .build()
                        )
                        .build()
                )
                .testabilityAccess(
                    TestabilityAccess.builder()
                        .canReceiveQuestions(false)
                        .nurseCanForwardCertificate(false)
                        .midwifeCanForwardCertificate(false)
                        .canDentistsUseType(true)
                        .availableForPatient(true)
                        .midwifeCanMarkReadyForSignCertificate(false)
                        .nurseCanMarkReadyForSignCertificate(false)
                        .build()
                )
                .build()
        );
  }
}
