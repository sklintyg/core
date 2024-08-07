package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements;

import static java.util.Map.entry;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0005.ARBETSTERAPEUT;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0005.AUDIONOM;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0005.DIETIST;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0005.FYSIOTERAPEUT;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0005.HABILITERING;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0005.HORSELHABILITERING;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0005.LOGOPED;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0005.NEUROPSYKIATRISKT;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0005.ORTOPEDTEKNIKER;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0005.ORTOPTIST;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0005.OVRIGT;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0005.PSYKOLOG;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0005.SPECIALISTKLINIK;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0005.SYNHABILITERING;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0005.VARD_UTOMLANDS;

import java.time.Period;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationMedicalInvestigationList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.MedicalInvestigationConfig;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationMedicalInvestigation;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationMedicalInvestigationList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
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
        NEUROPSYKIATRISKT,
        HABILITERING,
        ARBETSTERAPEUT,
        FYSIOTERAPEUT,
        LOGOPED,
        PSYKOLOG,
        SPECIALISTKLINIK,
        VARD_UTOMLANDS,
        HORSELHABILITERING,
        SYNHABILITERING,
        AUDIONOM,
        DIETIST,
        CodeSystemKvFkmu0005.ORTOPTIST,
        CodeSystemKvFkmu0005.ORTOPEDTEKNIKER,
        OVRIGT
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

  @Test
  void shallIncludePdfConfiguration() {
    final var expectedOptions = Map.ofEntries(
        entry(NEUROPSYKIATRISKT.code(),
            "Neuropsykiatriskt utlåtande"),
        entry(HABILITERING.code(),
            "Underlag från habiliteringen"),
        entry(ARBETSTERAPEUT.code(),
            "Underlag från arbetsterapeut"),
        entry(FYSIOTERAPEUT.code(),
            "Underlag från fysioterapeut"),
        entry(LOGOPED.code(),
            "Underlag från logoped"),
        entry(PSYKOLOG.code(),
            "Underlag från psykolog"),
        entry(SPECIALISTKLINIK.code(),
            "Utredning av annan specialistklinik"),
        entry(VARD_UTOMLANDS.code(),
            "Utredning från vårdinrättning utomlands"),
        entry(HORSELHABILITERING.code(),
            "Underlag från hörselhabiliteringen"),
        entry(SYNHABILITERING.code(),
            "Underlag från synhabiliteringen"),
        entry(AUDIONOM.code(),
            "Underlag från audionom"),
        entry(DIETIST.code(),
            "Underlag från dietist"),
        entry(ORTOPTIST.code(),
            "Underlag från ortopist"),
        entry(ORTOPEDTEKNIKER.code(),
            "Underlag från ortopedtekniker eller ortopedingenjör"),
        entry(OVRIGT.code(),
            "Övrigt")
    );

    final var expected = PdfConfigurationMedicalInvestigationList.builder()
        .list(
            Map.of(
                new FieldId("medicalInvestigation1"),
                PdfConfigurationMedicalInvestigation.builder()
                    .datePdfFieldId(
                        new PdfFieldId("form1[0].#subform[0].flt_datumUnderlagUtredning[0]"))
                    .investigationPdfFieldId(
                        new PdfFieldId("form1[0].#subform[0].lbx_listVardeUtredningUnderlag[0]"))
                    .sourceTypePdfFieldId(new PdfFieldId(
                        "form1[0].#subform[0].flt_txtVilkenVardgivare[0]"))
                    .investigationPdfOptions(expectedOptions)
                    .build(),
                new FieldId("medicalInvestigation2"),
                PdfConfigurationMedicalInvestigation.builder()
                    .datePdfFieldId(
                        new PdfFieldId("form1[0].#subform[0].flt_datumUnderlagUtredning2[0]"))
                    .investigationPdfFieldId(
                        new PdfFieldId("form1[0].#subform[0].lbx_listVardeUtredningUnderlag2[0]"))
                    .sourceTypePdfFieldId(new PdfFieldId(
                        "form1[0].#subform[0].flt_txtVilkenVardgivare2[0]"))
                    .investigationPdfOptions(expectedOptions)
                    .build(),
                new FieldId("medicalInvestigation3"),
                PdfConfigurationMedicalInvestigation.builder()
                    .datePdfFieldId(
                        new PdfFieldId("form1[0].#subform[0].flt_datumUnderlagUtredning3[0]"))
                    .investigationPdfFieldId(
                        new PdfFieldId("form1[0].#subform[0].lbx_listVardeUtredningUnderlag3[0]"))
                    .sourceTypePdfFieldId(new PdfFieldId(
                        "form1[0].#subform[0].flt_txtVilkenVardgivare3[0]"))
                    .investigationPdfOptions(expectedOptions)
                    .build()
            )
        )
        .build();

    final var element = QuestionUtredningEllerUnderlag.questionUtredningEllerUnderlag();

    assertEquals(expected, element.pdfConfiguration());
  }

}