package se.inera.intyg.certificateservice.integrationtest.fk7472;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7472.elements.QuestionSymptom.QUESTION_SYMPTOM_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7472.elements.QuestionSymptom.QUESTION_SYMPTOM_ID;

import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.integrationtest.common.setup.BaseTestabilityUtilities;
import se.inera.intyg.certificateservice.integrationtest.common.setup.TestabilityAccess;
import se.inera.intyg.certificateservice.integrationtest.common.setup.TestabilityCertificate;

public class FK7472TestSetup {

  public static final String TYPE = "FK7472 - ";
  private static final String CODE = "ITFP";
  private static final String CERTIFICATE_TYPE = "fk7472";
  private static final String ACTIVE_CERTIFICATE_TYPE_VERSION = "1.0";
  private static final String RECIPIENT = "FKASSA";
  private static final String VALUE = "Svarstext for symptom";

  public static BaseTestabilityUtilities.BaseTestabilityUtilitiesBuilder fk7472TestSetup() {
    return BaseTestabilityUtilities.builder()
        .testabilityCertificate(
            TestabilityCertificate.builder()
                .type(CERTIFICATE_TYPE)
                .code(CODE)
                .activeVersion(ACTIVE_CERTIFICATE_TYPE_VERSION)
                .recipient(RECIPIENT)
                .valueForTest(
                    ElementData.builder()
                        .id(QUESTION_SYMPTOM_ID)
                        .value(
                            ElementValueText.builder()
                                .textId(QUESTION_SYMPTOM_FIELD_ID)
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
                        .canDentistsUseType(false)
                        .availableForPatient(false)
                        .midwifeCanMarkReadyForSignCertificate(false)
                        .nurseCanMarkReadyForSignCertificate(false)
                        .build()
                )
                .build()
        );
  }
}
