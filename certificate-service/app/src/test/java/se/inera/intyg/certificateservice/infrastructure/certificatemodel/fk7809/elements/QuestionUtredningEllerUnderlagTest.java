package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;
import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationMedicalInvestigationList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.MedicalInvestigationConfig;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationMedicalInvestigationList;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0005;

class QuestionUtredningEllerUnderlagTest {

  private static final ElementId ELEMENT_ID = new ElementId("4");

  @Test
  void shallIncludeId() {
    final var element = QuestionUtredningEllerUnderlag.questionUtredningEllerUnderlag();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var codes = List.of(
        CodeSystemKvFkmu0005.NEUROPSYKIATRISKT,
        CodeSystemKvFkmu0005.HABILITERING,
        CodeSystemKvFkmu0005.ARBETSTERAPEUT,
        CodeSystemKvFkmu0005.FYSIOTERAPEUT,
        CodeSystemKvFkmu0005.LOGOPED,
        CodeSystemKvFkmu0005.PSYKOLOG,
        CodeSystemKvFkmu0005.SPECIALISTKLINIK,
        CodeSystemKvFkmu0005.VARD_UTOMLANDS,
        CodeSystemKvFkmu0005.HORSELHABILITERING,
        CodeSystemKvFkmu0005.SYNHABILITERING,
        CodeSystemKvFkmu0005.AUDIONOM,
        CodeSystemKvFkmu0005.DIETIST,
        CodeSystemKvFkmu0005.ORTOPTIST,
        CodeSystemKvFkmu0005.ORTOPEDTEKNIKER,
        CodeSystemKvFkmu0005.OVRIGT
    );
    final var expectedConfiguration = ElementConfigurationMedicalInvestigationList.builder()
        .name(
            "Ange utredning eller underlag")
        .id(new FieldId("4.1"))
        .informationSourceDescription(
            "Skriv exempelvis Neuropsykiatriska kliniken på X-stads sjukhus eller om patienten själv kommer att bifoga utredningen till sin ansökan.")
        .informationSourceText("Från vilken vårdgivare")
        .dateText("Datum")
        .typeText("Utredning eller underlag")
        .list(List.of(
            MedicalInvestigationConfig.builder()
                .id(new FieldId("medicalInvestigation1"))
                .dateId(new FieldId("medicalInvestigation1_DATE"))
                .investigationTypeId(new FieldId("medicalInvestigation1_INVESTIGATION_TYPE"))
                .informationSourceId(new FieldId("medicalInvestigation1_INFORMATION_SOURCE"))
                .typeOptions(codes)
                .min(null)
                .max(Period.ofDays(0))
                .build(),
            MedicalInvestigationConfig.builder()
                .id(new FieldId("medicalInvestigation2"))
                .dateId(new FieldId("medicalInvestigation2_DATE"))
                .investigationTypeId(new FieldId("medicalInvestigation2_INVESTIGATION_TYPE"))
                .informationSourceId(new FieldId("medicalInvestigation2_INFORMATION_SOURCE"))
                .typeOptions(codes)
                .min(null)
                .max(Period.ofDays(0))
                .build(),
            MedicalInvestigationConfig.builder()
                .id(new FieldId("medicalInvestigation3"))
                .dateId(new FieldId("medicalInvestigation3_DATE"))
                .investigationTypeId(new FieldId("medicalInvestigation3_INVESTIGATION_TYPE"))
                .informationSourceId(new FieldId("medicalInvestigation3_INFORMATION_SOURCE"))
                .typeOptions(codes)
                .min(null)
                .max(Period.ofDays(0))
                .build()
        ))
        .build();

    final var element = QuestionUtredningEllerUnderlag.questionUtredningEllerUnderlag();

    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shallIncludeRules() {
    final var expectedRule = List.of(
        ElementRuleExpression.builder()
            .id(ELEMENT_ID)
            .type(ElementRuleType.MANDATORY)
            .expression(
                new RuleExpression(
                    "!empty($medicalInvestigation1_DATE) "
                        + "|| !empty($medicalInvestigation1_INVESTIGATION_TYPE) "
                        + "|| !empty($medicalInvestigation1_INFORMATION_SOURCE)"
                )
            )
            .build(),
        ElementRuleExpression.builder()
            .id(new ElementId("3"))
            .type(ElementRuleType.SHOW)
            .expression(
                new RuleExpression(
                    "$3.1"
                )
            )
            .build()
    );

    final var element = QuestionUtredningEllerUnderlag.questionUtredningEllerUnderlag();

    assertEquals(expectedRule, element.rules());
  }

  @Test
  void shallIncludeValidation() {
    final var expectedValidations = List.of(
        ElementValidationMedicalInvestigationList.builder()
            .mandatory(true)
            .max(Period.ofDays(0))
            .min(null)
            .limit(4000)
            .build()
    );

    final var element = QuestionUtredningEllerUnderlag.questionUtredningEllerUnderlag();

    assertEquals(expectedValidations, element.validations());
  }

}