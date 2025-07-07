package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemFunktionsnedsattning.ANDNINGS_FUNKTION;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemFunktionsnedsattning.ANNAN_KROPPSILIG_FUNKTION;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemFunktionsnedsattning.INTELLEKTUELL_FUNKTION;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemFunktionsnedsattning.KOMMUNIKATION_SOCIAL_INTERAKTION;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemFunktionsnedsattning.KOORDINATION;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemFunktionsnedsattning.PSYKISK_FUNKTION;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemFunktionsnedsattning.SINNESFUNKTION_V2;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemFunktionsnedsattning.UPPMARKSAMHET;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionFunktionsnedsattning.questionFunktionsnedsattning;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxMultipleCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementLayout;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationHidden;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationCodeList;

class QuestionFunktionsnedsattningTest {

  private static final ElementId ELEMENT_ID = new ElementId("funktionsnedsattning");

  @Test
  void shallIncludeId() {
    final var element = questionFunktionsnedsattning();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationCheckboxMultipleCode.builder()
        .id(new FieldId("funktionsnedsattning"))
        .name("Välj alternativ för att visa fritextfält. Välj minst ett:")
        .elementLayout(ElementLayout.COLUMNS)
        .list(List.of(
            new ElementConfigurationCode(new FieldId("8.2"),
                INTELLEKTUELL_FUNKTION.displayName(),
                INTELLEKTUELL_FUNKTION),
            new ElementConfigurationCode(new FieldId("9.2"),
                KOMMUNIKATION_SOCIAL_INTERAKTION.displayName(),
                KOMMUNIKATION_SOCIAL_INTERAKTION),
            new ElementConfigurationCode(new FieldId("10.2"), UPPMARKSAMHET.displayName(),
                UPPMARKSAMHET),
            new ElementConfigurationCode(new FieldId("11.2"), PSYKISK_FUNKTION.displayName(),
                PSYKISK_FUNKTION),
            new ElementConfigurationCode(new FieldId("12.2"), SINNESFUNKTION_V2.displayName(),
                SINNESFUNKTION_V2),
            new ElementConfigurationCode(new FieldId("13.2"), KOORDINATION.displayName(),
                KOORDINATION),
            new ElementConfigurationCode(new FieldId("64.2"),
                ANDNINGS_FUNKTION.displayName(), ANDNINGS_FUNKTION),
            new ElementConfigurationCode(new FieldId("14.2"),
                ANNAN_KROPPSILIG_FUNKTION.displayName(), ANNAN_KROPPSILIG_FUNKTION)
        ))
        .build();

    final var element = questionFunktionsnedsattning();

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
                    "$8.2 || $9.2 || $10.2 || $11.2 || $12.2 || $13.2 || $64.2 || $14.2"))
            .build()
    );

    final var element = questionFunktionsnedsattning();

    assertEquals(expectedRules, element.rules());
  }

  @Test
  void shallIncludeValidations() {
    final var expectedValidations = List.of(
        ElementValidationCodeList.builder()
            .mandatory(true)
            .build()
    );

    final var element = questionFunktionsnedsattning();

    assertEquals(expectedValidations, element.validations());
  }

  @Test
  void shallIncludePdfConfiguration() {
    final var expected = PdfConfigurationHidden.builder()
        .build();

    final var element = questionFunktionsnedsattning();

    assertEquals(expected, element.pdfConfiguration());
  }
}