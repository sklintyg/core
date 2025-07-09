package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioMultipleCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementLayout;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationCode;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0009;

class QuestionPatientensBehandlingOchVardsituationTest {

  private static final ElementId ELEMENT_ID = new ElementId("52");

  @Test
  void shallIncludeId() {
    final var element = QuestionPatientensBehandlingOchVardsituation.questionPatientBehandlingOchVardsituation();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationRadioMultipleCode.builder()
        .id(new FieldId("52.1"))
        .name("Patientens behandling och vårdsituation")
        .elementLayout(ElementLayout.ROWS)
        .list(
            List.of(
                new ElementConfigurationCode(
                    new FieldId("ENDAST_PALLIATIV"),
                    "Endast palliativ vård ges och all aktiv behandling mot sjukdomstillståndet har avslutats",
                    CodeSystemKvFkmu0009.ENDAST_PALLIATIV
                ),
                new ElementConfigurationCode(
                    new FieldId("AKUT_LIVSHOTANDE"),
                    "Akut livshotande tillstånd (till exempel vård på intensivvårdsavdelning)",
                    CodeSystemKvFkmu0009.AKUT_LIVSHOTANDE
                ),
                new ElementConfigurationCode(
                    new FieldId("ANNAT"),
                    "Annat",
                    CodeSystemKvFkmu0009.ANNAT
                )
            )
        )
        .build();

    final var element = QuestionPatientensBehandlingOchVardsituation.questionPatientBehandlingOchVardsituation();

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
                    "exists($ENDAST_PALLIATIV) || exists($AKUT_LIVSHOTANDE) || exists($ANNAT)"
                )
            )
            .build()
    );

    final var element = QuestionPatientensBehandlingOchVardsituation.questionPatientBehandlingOchVardsituation();

    assertEquals(expectedRules, element.rules());
  }

  @Test
  void shallIncludeValidations() {
    final var expectedValidations = List.of(
        ElementValidationCode.builder()
            .mandatory(true)
            .build()
    );

    final var element = QuestionPatientensBehandlingOchVardsituation.questionPatientBehandlingOchVardsituation();

    assertEquals(expectedValidations, element.validations());
  }

  @Test
  void shallIncludePdfConfiguration() {
    final var expected = PdfConfigurationCode.builder()
        .codes(
            Map.of(
                new FieldId("ENDAST_PALLIATIV"),
                new PdfFieldId("form1[0].#subform[1].ksr_PalliativVard[0]"),
                new FieldId("AKUT_LIVSHOTANDE"),
                new PdfFieldId("form1[0].#subform[1].ksr_AkutLivshotande[0]"),
                new FieldId("ANNAT"), new PdfFieldId("form1[0].#subform[1].ksr_Annat2[0]")
            )
        )
        .build();

    final var element = QuestionPatientensBehandlingOchVardsituation.questionPatientBehandlingOchVardsituation();

    assertEquals(expected, element.pdfConfiguration());
  }
}