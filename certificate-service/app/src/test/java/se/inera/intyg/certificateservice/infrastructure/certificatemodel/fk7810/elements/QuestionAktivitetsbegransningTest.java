package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemAktivitetsbegransning.FORFLYTTNING_BEGRANSNING;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemAktivitetsbegransning.KOMMUNIKATION_BEGRANSNING;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemAktivitetsbegransning.LARANDE_BEGRANSNING;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemAktivitetsbegransning.OVRIGA_BEGRANSNING;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemAktivitetsbegransning.PERSONLIG_VARD_BEGRANSNING;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionAktivitetsbegransning.questionAktivitetsbegransning;

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

class QuestionAktivitetsbegransningTest {

  private static final ElementId ELEMENT_ID = new ElementId("aktivitetsbegransning");

  @Test
  void shallIncludeId() {
    final var element = questionAktivitetsbegransning();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationCheckboxMultipleCode.builder()
        .id(new FieldId("aktivitetsbegransning"))
        .name("Välj alternativ för att visa fritextfält. Välj minst ett:")
        .elementLayout(ElementLayout.COLUMNS)
        .list(List.of(
            new ElementConfigurationCode(new FieldId("15.2"),
                LARANDE_BEGRANSNING.displayName(),
                LARANDE_BEGRANSNING),
            new ElementConfigurationCode(new FieldId("16.2"),
                KOMMUNIKATION_BEGRANSNING.displayName(),
                KOMMUNIKATION_BEGRANSNING),
            new ElementConfigurationCode(new FieldId("17.2"),
                FORFLYTTNING_BEGRANSNING.displayName(),
                FORFLYTTNING_BEGRANSNING),
            new ElementConfigurationCode(new FieldId("18.2"),
                PERSONLIG_VARD_BEGRANSNING.displayName(),
                PERSONLIG_VARD_BEGRANSNING),
            new ElementConfigurationCode(new FieldId("19.2"), OVRIGA_BEGRANSNING.displayName(),
                OVRIGA_BEGRANSNING)
        ))
        .build();

    final var element = questionAktivitetsbegransning();

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
                    "$15.2 || $16.2 || $17.2 || $18.2 || $19.2"))
            .build()
    );

    final var element = questionAktivitetsbegransning();

    assertEquals(expectedRules, element.rules());
  }

  @Test
  void shallIncludeValidations() {
    final var expectedValidations = List.of(
        ElementValidationCodeList.builder()
            .mandatory(true)
            .build()
    );

    final var element = questionAktivitetsbegransning();

    assertEquals(expectedValidations, element.validations());
  }


  @Test
  void shallIncludePdfConfiguration() {
    final var expected = PdfConfigurationHidden.builder()
        .build();

    final var element = questionAktivitetsbegransning();

    assertEquals(expected, element.pdfConfiguration());
  }
}