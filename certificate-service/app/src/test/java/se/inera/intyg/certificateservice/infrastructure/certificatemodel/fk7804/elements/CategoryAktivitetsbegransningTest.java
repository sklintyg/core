package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.CategoryAktivitetsbegransning.categoryAktivitetsbegransning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionSmittbararpenning.QUESTION_SMITTBARARPENNING_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionSmittbararpenning.QUESTION_SMITTBARARPENNING_ID;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;

class CategoryAktivitetsbegransningTest {

  private static final ElementId ELEMENT_ID = new ElementId("KAT_6");

  @Test
  void shallIncludeId() {
    final var element = categoryAktivitetsbegransning();
    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationCategory.builder()
        .name("Aktivitetsbegränsning")
        .description("""
            Beskriv aktivitetsbegränsningens svårighetsgrad, till exempel lätt, medelsvår, svår, total eller motsvarande.
            
            Om patienten är arbetssökande eller har varit sjukskriven en längre tid kan det vara svårt att beskriva aktivitetsbegränsningar enbart i relation till patientens nuvarande arbetsuppgifter. Då kan det vara bra att även beskriva hur aktivitetsbegränsningarna yttrar sig i andra situationer, t. ex vardagliga situationer.\s
            """)
        .build();

    final var element = categoryAktivitetsbegransning();

    assertEquals(expectedConfiguration, element.elementSpecification(ELEMENT_ID).configuration());
  }

  @Test
  void shallIncludeRules() {
    final var element = categoryAktivitetsbegransning();
    final var expectedRules = List.of(
        ElementRuleExpression.builder()
            .id(QUESTION_SMITTBARARPENNING_ID)
            .type(ElementRuleType.HIDE)
            .expression(new RuleExpression("$" + QUESTION_SMITTBARARPENNING_FIELD_ID.value()))
            .build()
    );
    assertEquals(expectedRules, element.rules());
  }
}
