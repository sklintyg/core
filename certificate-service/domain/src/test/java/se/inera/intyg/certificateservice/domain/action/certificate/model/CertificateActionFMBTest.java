package se.inera.intyg.certificateservice.domain.action.certificate.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProvider.ALFA_REGIONEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnit.ALFA_MEDICINCENTRUM;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.ATHENA_REACT_ANDERSSON;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnit.ALFA_ALLERGIMOTTAGNINGEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUser.AJLA_DOKTOR;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation.ActionEvaluationBuilder;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate.CertificateBuilder;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionSpecification;

@ExtendWith(MockitoExtension.class)
class CertificateActionFMBTest {

  private CertificateActionFMB certificateActionFMB;

  private static final CertificateActionSpecification CERTIFICATE_ACTION_SPECIFICATION =
      CertificateActionSpecification.builder()
          .certificateActionType(CertificateActionType.FMB)
          .build();
  private CertificateBuilder certificateBuilder;
  private ActionEvaluationBuilder actionEvaluationBuilder;

  @InjectMocks
  CertificateActionFactory certificateActionFactory;

  @BeforeEach
  void setUp() {
    certificateActionFMB = (CertificateActionFMB) certificateActionFactory.create(
        CERTIFICATE_ACTION_SPECIFICATION
    );

    actionEvaluationBuilder = ActionEvaluation.builder()
        .user(AJLA_DOKTOR)
        .subUnit(ALFA_ALLERGIMOTTAGNINGEN)
        .patient(ATHENA_REACT_ANDERSSON)
        .careProvider(ALFA_REGIONEN)
        .careUnit(ALFA_MEDICINCENTRUM);

    certificateBuilder = Certificate.builder()
        .status(Status.DRAFT);
  }

  @Test
  void shallReturnName() {
    assertEquals("FMB",
        certificateActionFMB.getName(Optional.empty()));
  }

  @Test
  void shallReturnType() {
    assertEquals(CertificateActionType.FMB,
        certificateActionFMB.getType());
  }

  @Test
  void shallReturnDescription() {
    assertEquals(
        "Läs FMB - ett stöd för ifyllnad och bedömning.",
        certificateActionFMB.getDescription(Optional.empty()));
  }

  @Test
  void shallReturnReasonNotAllowed() {
    assertEquals("Du saknar behörighet för den begärda åtgärden."
            + " För att utföra denna uppgift krävs särskilda rättigheter eller en specifik befattning.",
        certificateActionFMB.reasonNotAllowed(
                Optional.of(certificateBuilder.status(Status.SIGNED).build()),
                Optional.of(actionEvaluationBuilder.build())
            )
            .getFirst());
  }

  @Test
  void shallEvaluateFalseIfCertificateIsNotDraft() {
    assertFalse(certificateActionFMB.evaluate(
        Optional.of(certificateBuilder.status(Status.SIGNED).build()),
        Optional.of(actionEvaluationBuilder.build())
    ));
  }

  @Test
  void shallEvaluateTrueIfCertificateIsDraft() {
    assertTrue(certificateActionFMB.evaluate(
        Optional.of(certificateBuilder.build()),
        Optional.of(actionEvaluationBuilder.build())
    ));
  }
}