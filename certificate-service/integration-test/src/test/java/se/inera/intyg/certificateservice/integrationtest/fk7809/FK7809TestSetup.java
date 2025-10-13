package se.inera.intyg.certificateservice.integrationtest.fk7809;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionPrognos.QUESTION_PROGNOS_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionPrognos.QUESTION_PROGNOS_ID;

import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.integrationtest.common.setup.BaseTestabilityUtilities;
import se.inera.intyg.certificateservice.integrationtest.common.setup.TestabilityAccess;
import se.inera.intyg.certificateservice.integrationtest.common.setup.TestabilityCertificate;

public class FK7809TestSetup {

  public static final String TYPE = "FK7809 - ";
  private static final String CODE = "LUMEK";
  private static final String CERTIFICATE_TYPE = "fk7809";
  private static final String ACTIVE_CERTIFICATE_TYPE_VERSION = "1.0";
  private static final String RECIPIENT = "FKASSA";
  private static final String VALUE = "Svarstext för prognos.";

  public static BaseTestabilityUtilities.BaseTestabilityUtilitiesBuilder fk7809TestSetup() {
    return BaseTestabilityUtilities.builder()
        .testabilityCertificate(
            TestabilityCertificate.builder()
                .type(CERTIFICATE_TYPE)
                .code(CODE)
                .activeVersion(ACTIVE_CERTIFICATE_TYPE_VERSION)
                .recipient(RECIPIENT)
                .valueForTest(
                    ElementData.builder()
                        .id(QUESTION_PROGNOS_ID)
                        .value(
                            ElementValueText.builder()
                                .textId(QUESTION_PROGNOS_FIELD_ID)
                                .text(VALUE)
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
