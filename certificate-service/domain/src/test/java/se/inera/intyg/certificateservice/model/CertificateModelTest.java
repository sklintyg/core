package se.inera.intyg.certificateservice.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

class CertificateModelTest {

  @Test
  void shallReturnCreateActionIfPatientIsNotDeceased() {
    final var expectedActions = List.of(
        new CertificateAction(CertificationActionType.CREATE)
    );

    final var actionEvaluation = ActionEvaluation.builder()
        .patient(
            Patient.builder()
                .deceased(false)
                .build()
        )
        .build();

    final var certificateModel = CertificateModel.builder()
        .certificateActionSpecifications(
            List.of(
                CertificateActionSpecification.builder()
                    .certificationActionType(CertificationActionType.CREATE)
                    .build()
            )
        )
        .build();

    final var actualActions = certificateModel.actions(actionEvaluation);

    assertEquals(expectedActions, actualActions);
  }

  @Test
  void shallNotReturnCreateActionIfPatientIsDeceased() {
    final var expectedActions = Collections.emptyList();

    final var actionEvaluation = ActionEvaluation.builder()
        .patient(
            Patient.builder()
                .deceased(true)
                .build()
        )
        .build();

    final var certificateModel = CertificateModel.builder()
        .certificateActionSpecifications(
            List.of(
                CertificateActionSpecification.builder()
                    .certificationActionType(CertificationActionType.CREATE)
                    .build()
            )
        )
        .build();

    final var actualActions = certificateModel.actions(actionEvaluation);

    assertEquals(expectedActions, actualActions);
  }

  @Test
  void shallNotReturnCreateActionIfCreateNotInSpecification() {
    final var expectedActions = Collections.emptyList();

    final var actionEvaluation = ActionEvaluation.builder()
        .patient(
            Patient.builder()
                .deceased(false)
                .build()
        )
        .build();

    final var certificateModel = CertificateModel.builder()
        .certificateActionSpecifications(
            Collections.emptyList()
        )
        .build();

    final var actualActions = certificateModel.actions(actionEvaluation);

    assertEquals(expectedActions, actualActions);
  }
}