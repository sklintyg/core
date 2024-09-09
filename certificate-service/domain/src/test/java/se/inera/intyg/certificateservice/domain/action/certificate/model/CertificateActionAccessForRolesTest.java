package se.inera.intyg.certificateservice.domain.action.certificate.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUser.AJLA_DOKTOR;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUser.ALVA_VARDADMINISTRATOR;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateUnitAccessEvaluationRepository;
import se.inera.intyg.certificateservice.domain.common.model.Role;

@ExtendWith(MockitoExtension.class)
class CertificateActionAccessForRolesTest {

  private CertificateActionAccessForRoles certificateActionAccessForRoles;
  private ActionEvaluation.ActionEvaluationBuilder actionEvaluationBuilder;
  private static final CertificateActionSpecification CERTIFICATE_ACTION_SPECIFICATION =
      CertificateActionSpecification.builder()
          .certificateActionType(CertificateActionType.ACCESS_FOR_ROLES)
          .allowedRoles(List.of(Role.DOCTOR))
          .build();

  @Mock
  CertificateUnitAccessEvaluationRepository certificateUnitAccessEvaluationRepository;
  @InjectMocks
  CertificateActionFactory certificateActionFactory;

  @BeforeEach
  void setUp() {
    certificateActionAccessForRoles = (CertificateActionAccessForRoles) certificateActionFactory.create(
        CERTIFICATE_ACTION_SPECIFICATION
    );

    actionEvaluationBuilder = ActionEvaluation.builder()
        .user(AJLA_DOKTOR);
  }

  @Test
  void shallReturnFalseIfRoleIsNotPresentInAllowedRoles() {
    final var actionEvaluation = actionEvaluationBuilder
        .user(ALVA_VARDADMINISTRATOR)
        .build();

    assertFalse(
        certificateActionAccessForRoles.evaluate(Optional.empty(), Optional.of(actionEvaluation))
    );
  }

  @Test
  void shallReturnTrueIfRoleIsPresentInAllowedRoles() {
    final var actionEvaluation = actionEvaluationBuilder
        .build();

    assertTrue(
        certificateActionAccessForRoles.evaluate(Optional.empty(), Optional.of(actionEvaluation))
    );
  }

  @Test
  void shallReturnFalseForInclude() {
    assertFalse(certificateActionAccessForRoles.include(Optional.empty(), Optional.empty()));
  }
}
