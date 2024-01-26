package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import se.inera.intyg.certificateservice.domain.action.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.model.CertificateAction;
import se.inera.intyg.certificateservice.domain.action.model.CertificateActionFactory;

class CertificateModelTest {

  @Test
  void shallReturnActionIfExistsAndEvaluateTrue() {
    final var certificateActionSpecification = CertificateActionSpecification.builder().build();
    final var actionEvaluation = ActionEvaluation.builder().build();
    final var certificateAction = mock(CertificateAction.class);
    final var expectedActions = List.of(certificateAction);

    final var certificateModel = CertificateModel.builder()
        .certificateActionSpecifications(
            List.of(
                certificateActionSpecification
            )
        )
        .build();

    try (MockedStatic<CertificateActionFactory> certificateActionFactory = mockStatic(
        CertificateActionFactory.class)) {

      certificateActionFactory
          .when(() -> CertificateActionFactory.create(certificateActionSpecification))
          .thenReturn(certificateAction);

      doReturn(true).when(certificateAction).evaluate(actionEvaluation);

      final var actualActions = certificateModel.actions(actionEvaluation);

      assertEquals(expectedActions, actualActions);
    }
  }

  @Test
  void shallNotReturnActionIfExistsAndEvaluateFalse() {
    final var certificateActionSpecification = CertificateActionSpecification.builder().build();
    final var actionEvaluation = ActionEvaluation.builder().build();
    final var certificateAction = mock(CertificateAction.class);
    final var expectedActions = Collections.emptyList();

    final var certificateModel = CertificateModel.builder()
        .certificateActionSpecifications(
            List.of(
                certificateActionSpecification
            )
        )
        .build();

    try (MockedStatic<CertificateActionFactory> certificateActionFactory = mockStatic(
        CertificateActionFactory.class)) {

      certificateActionFactory
          .when(() -> CertificateActionFactory.create(certificateActionSpecification))
          .thenReturn(certificateAction);

      doReturn(false).when(certificateAction).evaluate(actionEvaluation);

      final var actualActions = certificateModel.actions(actionEvaluation);

      assertEquals(expectedActions, actualActions);
    }
  }

  @Test
  void shallReturnEmptyActionsIfNoActionExistsInFactory() {
    final var certificateActionSpecification = CertificateActionSpecification.builder().build();
    final var actionEvaluation = ActionEvaluation.builder().build();
    final var expectedActions = Collections.emptyList();

    final var certificateModel = CertificateModel.builder()
        .certificateActionSpecifications(
            List.of(
                certificateActionSpecification
            )
        )
        .build();

    try (MockedStatic<CertificateActionFactory> certificateActionFactory = mockStatic(
        CertificateActionFactory.class)) {

      certificateActionFactory
          .when(() -> CertificateActionFactory.create(certificateActionSpecification))
          .thenReturn(null);

      final var actualActions = certificateModel.actions(actionEvaluation);

      assertEquals(expectedActions, actualActions);
    }
  }

  @Test
  void shallReturnEmptyActionsIfNoActionsExistInSpecification() {
    final var expectedActions = Collections.emptyList();
    final var actionEvaluation = ActionEvaluation.builder().build();

    final var certificateModel = CertificateModel.builder()
        .certificateActionSpecifications(
            Collections.emptyList()
        )
        .build();

    final var actualActions = certificateModel.actions(actionEvaluation);

    assertEquals(expectedActions, actualActions);
  }
}