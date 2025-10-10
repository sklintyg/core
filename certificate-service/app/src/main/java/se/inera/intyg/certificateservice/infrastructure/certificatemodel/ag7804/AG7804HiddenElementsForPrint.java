package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionDiagnos.QUESTION_DIAGNOS_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionFormedlaInfoOmDiagnosTillAG.QUESTION_FORMEDLA_DIAGNOS_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValueText;
import se.inera.intyg.certificateservice.domain.certificate.model.HiddenElement;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ElementDataPredicateFactory;

public class AG7804HiddenElementsForPrint {

  private AG7804HiddenElementsForPrint() {
    throw new IllegalStateException("Utility class");
  }

  public static final String HIDDEN_DIAGNOSIS_TEXT = "På patientens begäran uppges inte diagnos";

  public static List<HiddenElement> create() {
    return List.of(
        HiddenElement.builder()
            .hiddenBy(QUESTION_DIAGNOS_ID)
            .id(QUESTION_FORMEDLA_DIAGNOS_ID)
            .value(
                ElementSimplifiedValueText.builder()
                    .text(HIDDEN_DIAGNOSIS_TEXT)
                    .build()
            )
            .shouldHideByValue(
                ElementDataPredicateFactory.radioBooleans(List.of(QUESTION_FORMEDLA_DIAGNOS_ID),
                    false))
            .build(),
        HiddenElement.builder()
            .hiddenBy(QUESTION_DIAGNOS_ID)
            .id(QUESTION_DIAGNOS_ID)
            .value(
                ElementSimplifiedValueText.builder()
                    .text(HIDDEN_DIAGNOSIS_TEXT)
                    .build()
            )
            .shouldHideByValue(
                ElementDataPredicateFactory.radioBooleans(List.of(QUESTION_FORMEDLA_DIAGNOS_ID),
                    false))
            .build()
    );
  }
}