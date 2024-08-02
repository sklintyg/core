package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDiagnosis;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementDiagnosisListItem;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementDiagnosisTerminology;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleLimit;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleLimit;
import se.inera.intyg.certificateservice.domain.diagnosiscode.repository.DiagnosisCodeRepository;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationDiagnosis;

@ExtendWith(MockitoExtension.class)
class QuestionDiagnosTest {

  @Mock
  private DiagnosisCodeRepository diagnosisCodeRepository;

  private static final ElementId ELEMENT_ID = new ElementId("58");

  @Test
  void shallIncludeId() {
    final var element = QuestionDiagnos.questionDiagnos(diagnosisCodeRepository);

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationDiagnosis.builder()
        .id(new FieldId("58.1"))
        .name(
            "Diagnos eller diagnoser")
        .description(
            "Ange diagnoskod med så många positioner som möjligt. Använd inga andra tecken än bokstäver och siffror.")
        .terminology(
            List.of(
                new ElementDiagnosisTerminology("ICD_10_SE", "ICD-10-SE",
                    "1.2.752.116.1.1.1.1.8")
            )
        )
        .list(
            List.of(
                new ElementDiagnosisListItem(new FieldId("huvuddiagnos")),
                new ElementDiagnosisListItem(new FieldId("diagnos2")),
                new ElementDiagnosisListItem(new FieldId("diagnos3")),
                new ElementDiagnosisListItem(new FieldId("diagnos4")),
                new ElementDiagnosisListItem(new FieldId("diagnos5"))
            )
        )
        .build();

    final var element = QuestionDiagnos.questionDiagnos(diagnosisCodeRepository);

    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shallIncludeRules() {
    final var expectedRules = List.of(
        ElementRuleExpression.builder()
            .id(ELEMENT_ID)
            .type(ElementRuleType.MANDATORY)
            .expression(
                new RuleExpression(
                    "exists($huvuddiagnos)"
                )
            )
            .build(),
        ElementRuleLimit.builder()
            .id(ELEMENT_ID)
            .type(ElementRuleType.TEXT_LIMIT)
            .limit(
                new RuleLimit((short) 81)
            )
            .build()
    );

    final var element = QuestionDiagnos.questionDiagnos(diagnosisCodeRepository);

    assertEquals(expectedRules, element.rules());
  }

  @Test
  void shallIncludeValidations() {
    final var expectedValidations = List.of(
        ElementValidationDiagnosis.builder()
            .mandatoryField(new FieldId("huvuddiagnos"))
            .order(
                List.of(
                    new FieldId("huvuddiagnos"),
                    new FieldId("diagnos2"),
                    new FieldId("diagnos3"),
                    new FieldId("diagnos4"),
                    new FieldId("diagnos5")
                )
            )
            .diagnosisCodeRepository(diagnosisCodeRepository)
            .build()
    );

    final var element = QuestionDiagnos.questionDiagnos(diagnosisCodeRepository);

    assertEquals(expectedValidations, element.validations());
  }
}