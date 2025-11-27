package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements;

import static java.util.Map.entry;
import static org.junit.jupiter.api.Assertions.assertAll;
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
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0005.SYNHABILITERINGEN;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0005.VARD_UTOMLANDS;

import java.time.Period;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationMedicalInvestigationList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleLimit;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.MedicalInvestigationConfig;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationMedicalInvestigationList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleLimit;
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
        SYNHABILITERINGEN,
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
            "Skriv från vilken vårdgivare Försäkringskassan kan hämta information om utredningen/underlaget, exempelvis Neuropsykiatriska kliniken på X-stads sjukhus eller om patienten själv kommer att bifoga utredningen till sin ansökan.")
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
                .legacyMapping(Map.of("SYNHABILITERING", SYNHABILITERINGEN))
                .build(),
            MedicalInvestigationConfig.builder()
                .id(new FieldId("medicalInvestigation2"))
                .dateId(new FieldId("medicalInvestigation2_DATE"))
                .investigationTypeId(new FieldId("medicalInvestigation2_INVESTIGATION_TYPE"))
                .informationSourceId(new FieldId("medicalInvestigation2_INFORMATION_SOURCE"))
                .typeOptions(codes)
                .min(null)
                .max(Period.ofDays(0))
                .legacyMapping(Map.of("SYNHABILITERING", SYNHABILITERINGEN))
                .build(),
            MedicalInvestigationConfig.builder()
                .id(new FieldId("medicalInvestigation3"))
                .dateId(new FieldId("medicalInvestigation3_DATE"))
                .investigationTypeId(new FieldId("medicalInvestigation3_INVESTIGATION_TYPE"))
                .informationSourceId(new FieldId("medicalInvestigation3_INFORMATION_SOURCE"))
                .typeOptions(codes)
                .min(null)
                .max(Period.ofDays(0))
                .legacyMapping(Map.of("SYNHABILITERING", SYNHABILITERINGEN))
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
            .build(),
        ElementRuleLimit.builder()
            .id(new ElementId("4"))
            .type(ElementRuleType.TEXT_LIMIT)
            .limit(new RuleLimit((short) 53))
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
            .limit(53)
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
        entry(SYNHABILITERINGEN.code(),
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

    final var element = QuestionUtredningEllerUnderlag.questionUtredningEllerUnderlag();

    final var configuration = (PdfConfigurationMedicalInvestigationList) element.pdfConfiguration();

    assertAll(
        () -> assertEquals(3, configuration.list().size()),
        () -> assertEquals(
            new PdfFieldId("form1[0].#subform[0].flt_datumUnderlagUtredning[0]"),
            configuration.list().get(new FieldId("medicalInvestigation1")).datePdfFieldId()
        ),
        () -> assertEquals(
            new PdfFieldId("form1[0].#subform[0].lbx_listVardeUtredningUnderlag[0]"),
            configuration.list().get(new FieldId("medicalInvestigation1")).investigationPdfFieldId()
        ),
        () -> assertEquals(
            new PdfFieldId("form1[0].#subform[0].flt_txtVilkenVardgivare[0]"),
            configuration.list().get(new FieldId("medicalInvestigation1")).sourceTypePdfFieldId()
        ),
        () -> assertEquals(
            new PdfFieldId("form1[0].#subform[0].flt_datumUnderlagUtredning2[0]"),
            configuration.list().get(new FieldId("medicalInvestigation2")).datePdfFieldId()
        ),
        () -> assertEquals(
            new PdfFieldId("form1[0].#subform[0].lbx_listVardeUnderlagUtredning2[0]"),
            configuration.list().get(new FieldId("medicalInvestigation2")).investigationPdfFieldId()
        ),
        () -> assertEquals(
            new PdfFieldId("form1[0].#subform[0].flt_txtVilkenVardgivare2[0]"),
            configuration.list().get(new FieldId("medicalInvestigation2")).sourceTypePdfFieldId()
        ),
        () -> assertEquals(
            new PdfFieldId("form1[0].#subform[0].flt_datumUnderlagUtredning3[0]"),
            configuration.list().get(new FieldId("medicalInvestigation3")).datePdfFieldId()
        ),
        () -> assertEquals(
            new PdfFieldId("form1[0].#subform[0].lbx_listVardeUnderlagUtredning3[0]"),
            configuration.list().get(new FieldId("medicalInvestigation3")).investigationPdfFieldId()
        ),
        () -> assertEquals(
            new PdfFieldId("form1[0].#subform[0].flt_txtVilkenVardgivare3[0]"),
            configuration.list().get(new FieldId("medicalInvestigation3")).sourceTypePdfFieldId()
        ),
        () -> assertEquals(
            configuration.list().get(new FieldId("medicalInvestigation1")).investigationPdfOptions()
                .size(), expectedOptions.size()
        ),
        () -> assertEquals(
            configuration.list().get(new FieldId("medicalInvestigation2")).investigationPdfOptions()
                .size(), expectedOptions.size()
        ),
        () -> assertEquals(
            configuration.list().get(new FieldId("medicalInvestigation3")).investigationPdfOptions()
                .size(), expectedOptions.size()
        )
    );
  }
}