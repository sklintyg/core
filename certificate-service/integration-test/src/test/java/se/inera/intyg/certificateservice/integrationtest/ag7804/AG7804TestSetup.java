package se.inera.intyg.certificateservice.integrationtest.ag7804;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionMedicinskBehandling.QUESTION_MEDICINSK_BEHANDLING_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionMedicinskBehandling.QUESTION_MEDICINSK_BEHANDLING_ID;

import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.integrationtest.common.setup.BaseTestabilityUtilities;
import se.inera.intyg.certificateservice.integrationtest.common.setup.TestabilityAccess;
import se.inera.intyg.certificateservice.integrationtest.common.setup.TestabilityCertificate;

public class AG7804TestSetup {

  public static final String TYPE = "AG7804 - ";
  private static final String CERTIFICATE_TYPE = "ag7804";
  private static final String ACTIVE_CERTIFICATE_TYPE_VERSION = "2.0";
  private static final String CODE = "AG7804";
  private static final String VALUE = "Svarstext för medicinsk behandling";

  public static BaseTestabilityUtilities.BaseTestabilityUtilitiesBuilder ag7804TestSetup() {
    return BaseTestabilityUtilities.builder()
        .testabilityCertificate(
            TestabilityCertificate.builder()
                .type(CERTIFICATE_TYPE)
                .code(CODE)
                .activeVersion(ACTIVE_CERTIFICATE_TYPE_VERSION)
                .valueForTest(
                    ElementData.builder()
                        .id(QUESTION_MEDICINSK_BEHANDLING_ID)
                        .value(
                            ElementValueText.builder()
                                .textId(QUESTION_MEDICINSK_BEHANDLING_FIELD_ID)
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
