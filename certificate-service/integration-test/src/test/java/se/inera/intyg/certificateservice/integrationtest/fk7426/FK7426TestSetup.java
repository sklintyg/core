package se.inera.intyg.certificateservice.integrationtest.fk7426;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements.QuestionPagaendeBehandlingar.QUESTION_PAGAENDE_BEHANDLING_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements.QuestionPagaendeBehandlingar.QUESTION_PAGAENDE_BEHANDLING_ID;

import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.integrationtest.common.setup.BaseTestabilityUtilities;
import se.inera.intyg.certificateservice.integrationtest.common.setup.TestabilityAccess;
import se.inera.intyg.certificateservice.integrationtest.common.setup.TestabilityCertificate;

public class FK7426TestSetup {

  public static final String TYPE = "FK7426 - ";
  private static final String CODE = "LU_TFP_ASB18";
  private static final String CERTIFICATE_TYPE = "fk7426";
  private static final String ACTIVE_CERTIFICATE_TYPE_VERSION = "1.0";
  private static final String RECIPIENT = "FKASSA";
  private static final String VALUE = "Svarstext för pågående behandling.";

  public static BaseTestabilityUtilities.BaseTestabilityUtilitiesBuilder fk7426TestSetup() {
    return BaseTestabilityUtilities.builder()
        .testabilityCertificate(
            TestabilityCertificate.builder()
                .type(CERTIFICATE_TYPE)
                .code(CODE)
                .activeVersion(ACTIVE_CERTIFICATE_TYPE_VERSION)
                .recipient(RECIPIENT)
                .valueForTest(
                    ElementData.builder()
                        .id(QUESTION_PAGAENDE_BEHANDLING_ID)
                        .value(
                            ElementValueText.builder()
                                .textId(QUESTION_PAGAENDE_BEHANDLING_FIELD_ID)
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
