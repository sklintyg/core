package se.inera.intyg.certificateservice.integrationtest.fk3226;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.elements.QuestionForutsattningarForAttLamnaSkriftligtSamtycke.FORUTSATTNINGAR_FOR_ATT_LAMNA_SKRIFTLIGT_SAMTYCKE_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.elements.QuestionForutsattningarForAttLamnaSkriftligtSamtycke.FORUTSATTNINGAR_FOR_ATT_LAMNA_SKRIFTLIGT_SAMTYCKE_ID;

import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;
import se.inera.intyg.certificateservice.integrationtest.common.setup.BaseTestabilityUtilities;
import se.inera.intyg.certificateservice.integrationtest.common.setup.TestabilityAccess;
import se.inera.intyg.certificateservice.integrationtest.common.setup.TestabilityCertificate;

public class FK3226TestSetup {

  public static final String TYPE = "FK3226 - ";
  private static final String CODE = "LUNSP";
  private static final String CERTIFICATE_TYPE = "fk3226";
  private static final String ACTIVE_CERTIFICATE_TYPE_VERSION = "1.0";
  private static final String RECIPIENT = "FKASSA";
  private static final Boolean VALUE = false;

  public static BaseTestabilityUtilities.BaseTestabilityUtilitiesBuilder fk3226TestSetup() {
    return BaseTestabilityUtilities.builder()
        .testabilityCertificate(
            TestabilityCertificate.builder()
                .type(CERTIFICATE_TYPE)
                .code(CODE)
                .activeVersion(ACTIVE_CERTIFICATE_TYPE_VERSION)
                .recipient(RECIPIENT)
                .valueForTest(
                    ElementData.builder()
                        .id(FORUTSATTNINGAR_FOR_ATT_LAMNA_SKRIFTLIGT_SAMTYCKE_ID)
                        .value(
                            ElementValueBoolean.builder()
                                .booleanId(
                                    FORUTSATTNINGAR_FOR_ATT_LAMNA_SKRIFTLIGT_SAMTYCKE_FIELD_ID)
                                .value(VALUE)
                                .build()
                        )
                        .build()
                )
                .testabilityAccess(
                    TestabilityAccess.builder()
                        .canReceiveQuestions(true)
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
