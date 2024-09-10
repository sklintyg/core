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
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateAction;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionFactory;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.action.message.model.MessageAction;
import se.inera.intyg.certificateservice.domain.action.message.model.MessageActionFactory;
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateActionConfigurationRepository;

@ExtendWith(MockitoExtension.class)
class CertificateModelTest {

  @Mock
  CertificateActionConfigurationRepository certificateActionConfigurationRepository;
  @Mock
  CertificateActionFactory certificateActionFactory;

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
          .certificateActionFactory(certificateActionFactory)
          .build();

      doReturn(certificateAction).when(certificateActionFactory)
          .create(certificateActionSpecification);

      final var actualActions = certificateModel.actions();

      assertEquals(expectedActions, actualActions);
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
          .certificateActionFactory(certificateActionFactory)
          .build();

      doReturn(null).when(certificateActionFactory)
          .create(certificateActionSpecification);

      final var actualActions = certificateModel.actions();

      assertEquals(expectedActions, actualActions);
    }

    @Test
    void shallReturnEmptyActionsIfNoActionsExistInSpecification() {
      final var expectedActions = Collections.emptyList();

      final var certificateModel = CertificateModel.builder()
          .certificateActionSpecifications(
              Collections.emptyList()
          )
          .certificateActionFactory(certificateActionFactory)
          .build();

      final var actualActions = certificateModel.actions();

      assertEquals(expectedActions, actualActions);
    }
  }

  @Nested
  class TestMessageActions {

    @Test
    void shallReturnActionIfExists() {
      final var messageActionSpecification = MessageActionSpecification.builder().build();
      final var messageAction = mock(MessageAction.class);
      final var expectedActions = List.of(messageAction);

      final var certificateModel = CertificateModel.builder()
          .messageActionSpecifications(
              List.of(
                  messageActionSpecification
              )
          )
          .certificateActionFactory(certificateActionFactory)
          .build();

      try (MockedStatic<MessageActionFactory> messageActionFactory = mockStatic(
          MessageActionFactory.class)) {

        messageActionFactory
            .when(() -> MessageActionFactory.create(messageActionSpecification))
            .thenReturn(messageAction);

        final var actualActions = certificateModel.messageActions();

        assertEquals(expectedActions, actualActions);
      }
    }

    @Test
    void shallReturnEmptyActionsIfNoActionExists() {
      final var messageActionSpecification = MessageActionSpecification.builder().build();
      final var expectedActions = Collections.emptyList();

      final var certificateModel = CertificateModel.builder()
          .messageActionSpecifications(
              List.of(
                  messageActionSpecification
              )
          )
          .build();

      try (MockedStatic<MessageActionFactory> messageActionFactory = mockStatic(
          MessageActionFactory.class)) {

        messageActionFactory
            .when(() -> MessageActionFactory.create(messageActionSpecification))
            .thenReturn(null);

        final var actualActions = certificateModel.messageActions();

        assertEquals(expectedActions, actualActions);
      }
    }

    @Test
    void shallReturnEmptyActionsIfNoActionsExistInSpecification() {
      final var expectedActions = Collections.emptyList();

      final var certificateModel = CertificateModel.builder()
          .certificateActionSpecifications(
              Collections.emptyList()
          )
          .build();

      final var actualActions = certificateModel.messageActions();

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
          .certificateActionFactory(certificateActionFactory)
          .build();

      doReturn(certificateAction).when(certificateActionFactory)
          .create(certificateActionSpecification);

      doReturn(true).when(certificateAction)
          .evaluate(Optional.empty(), Optional.of(actionEvaluation));

      final var actualActions = certificateModel.actions(Optional.of(actionEvaluation));

      assertEquals(expectedActions, actualActions);
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
          .certificateActionFactory(certificateActionFactory)
          .build();

      doReturn(certificateAction).when(certificateActionFactory)
          .create(certificateActionSpecification);

      doReturn(false).when(certificateAction)
          .evaluate(Optional.empty(), Optional.of(actionEvaluation));

      final var actualActions = certificateModel.actions(Optional.of(actionEvaluation));

      assertEquals(expectedActions, actualActions);
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
          .certificateActionFactory(certificateActionFactory)
          .build();

      doReturn(null).when(certificateActionFactory)
          .create(certificateActionSpecification);

      final var actualActions = certificateModel.actions(Optional.of(actionEvaluation));

      assertEquals(expectedActions, actualActions);
    }

    @Test
    void shallReturnEmptyActionsIfNoActionsExistInSpecification() {
      final var expectedActions = Collections.emptyList();
      final var actionEvaluation = ActionEvaluation.builder().build();

      final var certificateModel = CertificateModel.builder()
          .certificateActionSpecifications(
              Collections.emptyList()
          )
          .certificateActionFactory(certificateActionFactory)
          .build();

      final var actualActions = certificateModel.actions(Optional.of(actionEvaluation));

      assertEquals(expectedActions, actualActions);
    }
  }

  @Nested
  class TestActionsIncludeWithEvaluation {

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
          .certificateActionFactory(certificateActionFactory)
          .build();

      doReturn(certificateAction).when(certificateActionFactory)
          .create(certificateActionSpecification);

      doReturn(true).when(certificateAction)
          .include(Optional.empty(), Optional.of(actionEvaluation));

      final var actualActions = certificateModel.actionsInclude(Optional.of(actionEvaluation));

      assertEquals(expectedActions, actualActions);
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
          .certificateActionFactory(certificateActionFactory)
          .build();

      doReturn(certificateAction).when(certificateActionFactory)
          .create(certificateActionSpecification);

      doReturn(false).when(certificateAction)
          .include(Optional.empty(), Optional.of(actionEvaluation));

      final var actualActions = certificateModel.actionsInclude(Optional.of(actionEvaluation));

      assertEquals(expectedActions, actualActions);
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
          .certificateActionFactory(certificateActionFactory)
          .build();

      doReturn(null).when(certificateActionFactory)
          .create(certificateActionSpecification);

      final var actualActions = certificateModel.actionsInclude(Optional.of(actionEvaluation));

      assertEquals(expectedActions, actualActions);
    }

    @Test
    void shallReturnEmptyActionsIfNoActionsExistInSpecification() {
      final var expectedActions = Collections.emptyList();
      final var actionEvaluation = ActionEvaluation.builder().build();

      final var certificateModel = CertificateModel.builder()
          .certificateActionSpecifications(
              Collections.emptyList()
          )
          .certificateActionFactory(certificateActionFactory)
          .build();

      final var actualActions = certificateModel.actionsInclude(Optional.of(actionEvaluation));

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
          .certificateActionFactory(certificateActionFactory)
          .build();

      doReturn(certificateAction).when(certificateActionFactory)
          .create(certificateActionSpecification);

      doReturn(CertificateActionType.CREATE).when(certificateAction).getType();
      doReturn(true).when(certificateAction)
          .evaluate(Optional.empty(), Optional.of(actionEvaluation));

      final var actualResult = certificateModel.allowTo(CertificateActionType.CREATE,
          Optional.of(actionEvaluation));

      assertTrue(actualResult, "Expected allowTo to return 'true'");
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
          .certificateActionFactory(certificateActionFactory)
          .build();

      doReturn(certificateAction).when(certificateActionFactory)
          .create(certificateActionSpecification);

      doReturn(CertificateActionType.CREATE).when(certificateAction).getType();
      doReturn(false).when(certificateAction)
          .evaluate(Optional.empty(), Optional.of(actionEvaluation));

      final var actualResult = certificateModel.allowTo(CertificateActionType.CREATE,
          Optional.of(actionEvaluation));

      assertFalse(actualResult, "Expected allowTo to return 'false'");
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
          .certificateActionFactory(certificateActionFactory)
          .build();

      doReturn(certificateAction).when(certificateActionFactory)
          .create(certificateActionSpecification);

      final var actualResult = certificateModel.allowTo(CertificateActionType.CREATE,
          Optional.of(actionEvaluation));

      assertFalse(actualResult, "Expected allowTo to return 'false'");

    }
  }

  @Nested
  class TestReasonNotAllowed {

    @Test
    void shallReturnEmptyList() {
      final var certificateActionSpecification = CertificateActionSpecification.builder().build();
      final var actionEvaluation = ActionEvaluation.builder().build();
      final var certificateAction = mock(CertificateAction.class);

      final var certificateModel = CertificateModel.builder()
          .certificateActionSpecifications(
              List.of(
                  certificateActionSpecification
              )
          )
          .certificateActionFactory(certificateActionFactory)
          .build();

      doReturn(certificateAction).when(certificateActionFactory)
          .create(certificateActionSpecification);

      doReturn(CertificateActionType.CREATE).when(certificateAction).getType();
      doReturn(Collections.emptyList()).when(certificateAction)
          .reasonNotAllowed(Optional.empty(), Optional.of(actionEvaluation));

      final var actualResult = certificateModel.reasonNotAllowed(CertificateActionType.CREATE,
          Optional.of(actionEvaluation));

      assertTrue(actualResult.isEmpty(), "Expected reasonNotAllowed to return empty list");
    }

    @Test
    void shallReturnReasons() {
      final var expectedReasons = List.of("expectedReasons");
      final var certificateActionSpecification = CertificateActionSpecification.builder().build();
      final var actionEvaluation = ActionEvaluation.builder().build();
      final var certificateAction = mock(CertificateAction.class);

      final var certificateModel = CertificateModel.builder()
          .certificateActionSpecifications(
              List.of(
                  certificateActionSpecification
              )
          )
          .certificateActionFactory(certificateActionFactory)
          .build();

      doReturn(certificateAction).when(certificateActionFactory)
          .create(certificateActionSpecification);

      doReturn(CertificateActionType.CREATE).when(certificateAction).getType();
      doReturn(expectedReasons).when(certificateAction)
          .reasonNotAllowed(Optional.empty(), Optional.of(actionEvaluation));

      final var actualResult = certificateModel.reasonNotAllowed(CertificateActionType.CREATE,
          Optional.of(actionEvaluation));

      assertEquals(expectedReasons, actualResult);
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

  @Nested
  class TestCertificateActionExists {

    @Test
    void shouldReturnTrueIfCertificateActionTypeExists() {
      final var certificateModel = CertificateModel.builder()
          .certificateActionSpecifications(
              List.of(CertificateActionSpecification.builder()
                  .certificateActionType(CertificateActionType.CREATE)
                  .build())
          )
          .build();

      assertTrue(certificateModel.certificateActionExists(CertificateActionType.CREATE));
    }

    @Test
    void shouldReturnFalseIfCertificateActionTypeDontExists() {
      final var certificateModel = CertificateModel.builder()
          .certificateActionSpecifications(
              List.of(CertificateActionSpecification.builder()
                  .certificateActionType(CertificateActionType.SIGN)
                  .build())
          )
          .build();

      assertFalse(certificateModel.certificateActionExists(CertificateActionType.CREATE));
    }
  }
}
