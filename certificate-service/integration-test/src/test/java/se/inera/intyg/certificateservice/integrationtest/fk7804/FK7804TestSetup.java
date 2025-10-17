package se.inera.intyg.certificateservice.integrationtest.fk7804;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionMedicinskBehandling.QUESTION_MEDICINSK_BEHANDLING_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionMedicinskBehandling.QUESTION_MEDICINSK_BEHANDLING_ID;

import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.integrationtest.common.setup.BaseTestabilityUtilities;
import se.inera.intyg.certificateservice.integrationtest.common.setup.TestabilityAccess;
import se.inera.intyg.certificateservice.integrationtest.common.setup.TestabilityCertificate;

public class FK7804TestSetup {

  public static final String TYPE = "FK7804 - ";
  private static final String CODE = "LISJP";
  public static final String CERTIFICATE_TYPE = "fk7804";
  public static final String ACTIVE_CERTIFICATE_TYPE_VERSION = "2.0";
  private static final String RECIPIENT = "FKASSA";
  private static final String VALUE = "Svarstext för medicinsk behandling.";

  public static BaseTestabilityUtilities.BaseTestabilityUtilitiesBuilder fk7804TestSetup() {
    return BaseTestabilityUtilities.builder()
        .testabilityCertificate(
            TestabilityCertificate.builder()
                .type(CERTIFICATE_TYPE)
                .code(CODE)
                .activeVersion(ACTIVE_CERTIFICATE_TYPE_VERSION)
                .recipient(RECIPIENT)
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
                        .canReceiveQuestions(true)
                        .nurseCanForwardCertificate(true)
                        .midwifeCanForwardCertificate(true)
                        .canDentistsUseType(true)
                        .availableForPatient(true)
                        .midwifeCanMarkReadyForSignCertificate(true)
                        .nurseCanMarkReadyForSignCertificate(true)
                        .build()
                )
                .build()
        );
  }
}
