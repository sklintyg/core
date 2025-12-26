package se.inera.intyg.certificateservice.domain.action.certificate.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProvider.ALFA_REGIONEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnit.ALFA_MEDICINCENTRUM;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.ATHENA_REACT_ANDERSSON;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnit.ALFA_ALLERGIMOTTAGNINGEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUser.AJLA_DOKTOR;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUser.ALF_DOKTOR;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation.ActionEvaluationBuilder;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalCertificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionSpecification;

@ExtendWith(MockitoExtension.class)
class CertificateActionSrsSignedTest {


  private CertificateActionSrsSigned certificateActionSrsSigned;

  private static final CertificateActionSpecification CERTIFICATE_ACTION_SPECIFICATION =
      CertificateActionSpecification.builder()
          .certificateActionType(CertificateActionType.SRS_SIGNED)
          .build();
  private MedicalCertificate.MedicalCertificateBuilder certificateBuilder;
  private ActionEvaluationBuilder actionEvaluationBuilder;

  @InjectMocks
  CertificateActionFactory certificateActionFactory;

  @BeforeEach
  void setUp() {
    certificateActionSrsSigned = (CertificateActionSrsSigned) certificateActionFactory.create(
        CERTIFICATE_ACTION_SPECIFICATION
    );

    actionEvaluationBuilder = ActionEvaluation.builder()
        .user(AJLA_DOKTOR)
        .subUnit(ALFA_ALLERGIMOTTAGNINGEN)
        .patient(ATHENA_REACT_ANDERSSON)
        .careProvider(ALFA_REGIONEN)
        .careUnit(ALFA_MEDICINCENTRUM);

    certificateBuilder = MedicalCertificate.builder()
        .status(Status.SIGNED);
  }

  @Test
  void shallReturnName() {
    assertEquals("Risk och råd",
        certificateActionSrsSigned.getName(Optional.empty()));
  }

  @Test
  void shallReturnType() {
    assertEquals(CertificateActionType.SRS_SIGNED,
        certificateActionSrsSigned.getType());
  }

  @Test
  void shallReturnDescription() {
    assertEquals(
        "Risk och råd",
        certificateActionSrsSigned.getDescription(Optional.empty()));
  }

  @Test
  void shallReturnReasonNotAllowed() {
    assertEquals("För att genomföra den begärda åtgärden behöver intygets status vara [SIGNED]",
        certificateActionSrsSigned.reasonNotAllowed(
                Optional.of(certificateBuilder.status(Status.DRAFT).build()),
                Optional.of(actionEvaluationBuilder.build())
            )
            .getFirst());
  }

  @Test
  void shallReturnTrueIfUserHasSrsActivatedAndCertificateIsDraft() {
    final var result = certificateActionSrsSigned.evaluate(
        Optional.of(certificateBuilder.build()),
        Optional.of(actionEvaluationBuilder.build())
    );
    assertTrue(result);
  }

  @Test
  void shallReturnFalseIfUserHasSrsActivatedButCertificateIsNotSigned() {
    final var result = certificateActionSrsSigned.evaluate(
        Optional.of(certificateBuilder.status(Status.DRAFT).build()),
        Optional.of(actionEvaluationBuilder.build())
    );
    assertFalse(result);
  }

  @Test
  void shallReturnFalseIfUserDoesNotHaveSrsActivatedTrue() {
    final var result = certificateActionSrsSigned.evaluate(
        Optional.of(certificateBuilder.build()),
        Optional.of(actionEvaluationBuilder.user(ALF_DOKTOR).build())
    );
    assertFalse(result);
  }
}