package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataElementDataConstants.DATE_ELEMENT_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataElementSpecification.DATE_ELEMENT_SPECIFICATION;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataElementSpecification.dateElementSpecificationBuilder;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.action.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.model.CertificateAction;
import se.inera.intyg.certificateservice.domain.action.model.CertificateActionFactory;
import se.inera.intyg.certificateservice.domain.action.model.CertificateActionType;

@ExtendWith(MockitoExtension.class)
class CertificateModelTest {

  @Nested
  class TestActions {

    @Test
    void shallReturnActionIfExists() {
      final var certificateActionSpecification = CertificateActionSpecification.builder().build();
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

        final var actualActions = certificateModel.actions();

        assertEquals(expectedActions, actualActions);
      }
    }

    @Test
    void shallReturnEmptyActionsIfNoActionExists() {
      final var certificateActionSpecification = CertificateActionSpecification.builder().build();
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

        final var actualActions = certificateModel.actions();

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

  @Nested
  class TestActionsWithEvaluation {

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

  @Nested
  class TestAllowTo {

    @Test
    void shallReturnTrueIfActionEvaluateTrue() {
      final var certificateActionSpecification = CertificateActionSpecification.builder().build();
      final var actionEvaluation = ActionEvaluation.builder().build();
      final var certificateAction = mock(CertificateAction.class);

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

        doReturn(CertificateActionType.CREATE).when(certificateAction).getType();
        doReturn(true).when(certificateAction).evaluate(actionEvaluation);

        final var actualResult = certificateModel.allowTo(CertificateActionType.CREATE,
            actionEvaluation);

        assertTrue(actualResult, "Expected allowTo to return 'true'");
      }
    }

    @Test
    void shallReturnFalseIfActionEvaluateFalse() {
      final var certificateActionSpecification = CertificateActionSpecification.builder().build();
      final var actionEvaluation = ActionEvaluation.builder().build();
      final var certificateAction = mock(CertificateAction.class);

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

        doReturn(CertificateActionType.CREATE).when(certificateAction).getType();
        doReturn(false).when(certificateAction).evaluate(actionEvaluation);

        final var actualResult = certificateModel.allowTo(CertificateActionType.CREATE,
            actionEvaluation);

        assertFalse(actualResult, "Expected allowTo to return 'false'");
      }
    }

    @Test
    void shallReturnFalseIfActionMissing() {
      final var certificateActionSpecification = CertificateActionSpecification.builder().build();
      final var actionEvaluation = ActionEvaluation.builder().build();
      final var certificateAction = mock(CertificateAction.class);

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

        final var actualResult = certificateModel.allowTo(CertificateActionType.CREATE,
            actionEvaluation);

        assertFalse(actualResult, "Expected allowTo to return 'false'");
      }
    }
  }

  @Nested
  class TestElementSpecificationExists {

    @Test
    void shallReturnTrueIfElementExitsOnRootLevel() {
      final var certificateModel = CertificateModel.builder()
          .elementSpecifications(
              List.of(DATE_ELEMENT_SPECIFICATION)
          )
          .build();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId(DATE_ELEMENT_ID)),
          "Expected to find '%s' among specifications '%s'".formatted(DATE_ELEMENT_ID,
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallReturnTrueIfElementExitsOnRootLevelAsSecond() {
      final var certificateModel = CertificateModel.builder()
          .elementSpecifications(
              List.of(
                  dateElementSpecificationBuilder()
                      .id(new ElementId("ANOTHER_ELEMENT_ID"))
                      .build(),
                  DATE_ELEMENT_SPECIFICATION
              )
          )
          .build();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId(DATE_ELEMENT_ID)),
          "Expected to find '%s' among specifications '%s'".formatted(DATE_ELEMENT_ID,
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallReturnTrueIfElementExitsOnNextLevel() {
      final var certificateModel = CertificateModel.builder()
          .elementSpecifications(
              List.of(
                  dateElementSpecificationBuilder()
                      .id(new ElementId("ANOTHER_ELEMENT_ID"))
                      .children(List.of(DATE_ELEMENT_SPECIFICATION))
                      .build()
              )
          )
          .build();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId(DATE_ELEMENT_ID)),
          "Expected to find '%s' among specifications '%s'".formatted(DATE_ELEMENT_ID,
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallReturnTrueIfElementExitsOnNextLevelOnSecondRoot() {
      final var certificateModel = CertificateModel.builder()
          .elementSpecifications(
              List.of(
                  dateElementSpecificationBuilder()
                      .id(new ElementId("ANOTHER_ELEMENT_ID"))
                      .build(),
                  dateElementSpecificationBuilder()
                      .id(new ElementId("ANOTHER_ELEMENT_ID"))
                      .children(List.of(DATE_ELEMENT_SPECIFICATION))
                      .build()
              )
          )
          .build();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId(DATE_ELEMENT_ID)),
          "Expected to find '%s' among specifications '%s'".formatted(DATE_ELEMENT_ID,
              certificateModel.elementSpecifications())
      );
    }
  }

  @Nested
  class TestElementSpecificationWithId {

    @Test
    void shallReturnElementIfElementExitsOnRootLevel() {
      final var certificateModel = CertificateModel.builder()
          .elementSpecifications(
              List.of(DATE_ELEMENT_SPECIFICATION)
          )
          .build();

      assertEquals(DATE_ELEMENT_SPECIFICATION,
          certificateModel.elementSpecification(DATE_ELEMENT_SPECIFICATION.id())
      );
    }

    @Test
    void shallReturnElementIfElementExitsOnRootLevelAsSecond() {
      final var certificateModel = CertificateModel.builder()
          .elementSpecifications(
              List.of(
                  dateElementSpecificationBuilder()
                      .id(new ElementId("ANOTHER_ELEMENT_ID"))
                      .build(),
                  DATE_ELEMENT_SPECIFICATION
              )
          )
          .build();

      assertEquals(DATE_ELEMENT_SPECIFICATION,
          certificateModel.elementSpecification(DATE_ELEMENT_SPECIFICATION.id())
      );
    }

    @Test
    void shallReturnElementIfElementExitsOnNextLevel() {
      final var certificateModel = CertificateModel.builder()
          .elementSpecifications(
              List.of(
                  dateElementSpecificationBuilder()
                      .id(new ElementId("ANOTHER_ELEMENT_ID"))
                      .children(List.of(DATE_ELEMENT_SPECIFICATION))
                      .build()
              )
          )
          .build();

      assertEquals(DATE_ELEMENT_SPECIFICATION,
          certificateModel.elementSpecification(DATE_ELEMENT_SPECIFICATION.id())
      );
    }

    @Test
    void shallReturnElementIfElementExitsOnNextLevelOnSecondRoot() {
      final var certificateModel = CertificateModel.builder()
          .elementSpecifications(
              List.of(
                  dateElementSpecificationBuilder()
                      .id(new ElementId("ANOTHER_ELEMENT_ID"))
                      .build(),
                  dateElementSpecificationBuilder()
                      .id(new ElementId("ANOTHER_ELEMENT_ID"))
                      .children(List.of(DATE_ELEMENT_SPECIFICATION))
                      .build()
              )
          )
          .build();

      assertEquals(DATE_ELEMENT_SPECIFICATION,
          certificateModel.elementSpecification(DATE_ELEMENT_SPECIFICATION.id())
      );
    }

    @Test
    void shallThrowExceptionIfElementDoesntExists() {
      final var certificateModel = CertificateModel.builder()
          .elementSpecifications(
              List.of(
                  dateElementSpecificationBuilder()
                      .id(new ElementId("ANOTHER_ELEMENT_ID"))
                      .build()
              )
          )
          .build();

      final var elementId = DATE_ELEMENT_SPECIFICATION.id();
      assertThrows(IllegalArgumentException.class,
          () -> certificateModel.elementSpecification(elementId)
      );
    }
  }
}