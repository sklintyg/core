package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements;

import java.util.List;
import java.util.Map;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxDateRangeList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationDateRangeCheckbox;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationDateRangeList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationDateRangeList;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0003;

public class QuestionNedsattningArbetsformaga {

  public static final ElementId QUESTION_NEDSATTNING_ARBETSFORMAGA_ID = new ElementId("32");
  private static final String QUESTION_NEDSATTNING_ARBETSFORMAGA_FIELD_ID = "32.1";

  private QuestionNedsattningArbetsformaga() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionNedsattningArbetsformaga(
      ElementSpecification... children) {
    final var dateRanges = List.of(
        new ElementConfigurationCode(
            new FieldId(CodeSystemKvFkmu0003.EN_FJARDEDEL.code()),
            "25 procent",
            CodeSystemKvFkmu0003.EN_FJARDEDEL
        ),
        new ElementConfigurationCode(
            new FieldId(CodeSystemKvFkmu0003.HALFTEN.code()),
            "50 procent",
            CodeSystemKvFkmu0003.HALFTEN
        ),
        new ElementConfigurationCode(
            new FieldId(CodeSystemKvFkmu0003.TRE_FJARDEDEL.code()),
            "75 procent",
            CodeSystemKvFkmu0003.TRE_FJARDEDEL
        ),
        new ElementConfigurationCode(
            new FieldId(CodeSystemKvFkmu0003.HELT_NEDSATT.code()),
            "100 procent",
            CodeSystemKvFkmu0003.HELT_NEDSATT
        )
    );

    return ElementSpecification.builder()
        .id(QUESTION_NEDSATTNING_ARBETSFORMAGA_ID)
        .includeWhenRenewing(false)
        .configuration(
            ElementConfigurationCheckboxDateRangeList.builder()
                .name("Min bedömning av patientens nedsättning av arbetsförmågan")
                .description(
                    "Utgångspunkten är att patientens arbetsförmåga ska bedömas i förhållande till patientens normala arbetstid.")
                .id(new FieldId(QUESTION_NEDSATTNING_ARBETSFORMAGA_FIELD_ID))
                .dateRanges(dateRanges)
                .hideWorkingHours(false)
                .build()
        )
        .pdfConfiguration(
            PdfConfigurationDateRangeList.builder()
                .dateRanges(Map.of(
                    new FieldId(CodeSystemKvFkmu0003.HELT_NEDSATT.code()),
                    PdfConfigurationDateRangeCheckbox.builder()
                        .checkbox(new PdfFieldId("form1[0].Sida2[0].ksr_100procent[0]"))
                        .from(new PdfFieldId("form1[0].Sida2[0].flt_datumFranMed[0]"))
                        .to(new PdfFieldId("form1[0].Sida2[0].flt_datumtTillMed[0]"))
                        .build(),
                    new FieldId(CodeSystemKvFkmu0003.TRE_FJARDEDEL.code()),
                    PdfConfigurationDateRangeCheckbox.builder()
                        .checkbox(new PdfFieldId("form1[0].Sida2[0].ksr_75procent[0]"))
                        .from(new PdfFieldId("form1[0].Sida2[0].flt_datumFranMed75Procent[0]"))
                        .to(new PdfFieldId("form1[0].Sida2[0].flt_datumTillMed75Procent[0]"))
                        .build(),
                    new FieldId(CodeSystemKvFkmu0003.HALFTEN.code()),
                    PdfConfigurationDateRangeCheckbox.builder()
                        .checkbox(new PdfFieldId("form1[0].Sida2[0].ksr_50procent[0]"))
                        .from(new PdfFieldId("form1[0].Sida2[0].flt_datumFranMed50Procent[0]"))
                        .to(new PdfFieldId("form1[0].Sida2[0].flt_datumTillMed50Procent[0]"))
                        .build(),
                    new FieldId(CodeSystemKvFkmu0003.EN_FJARDEDEL.code()),
                    PdfConfigurationDateRangeCheckbox.builder()
                        .checkbox(new PdfFieldId("form1[0].Sida2[0].ksr_25procent[0]"))
                        .from(new PdfFieldId("form1[0].Sida2[0].flt_datumFranMed25Procent[0]"))
                        .to(new PdfFieldId("form1[0].Sida2[0].flt_datumTillMed25Procent[0]"))
                        .build()
                ))
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatory(
                    QUESTION_NEDSATTNING_ARBETSFORMAGA_ID,
                    dateRanges.stream().map(ElementConfigurationCode::id).toList()
                )
            )
        )
        .validations(
            List.of(
                ElementValidationDateRangeList.builder()
                    .mandatory(true)
                    .build()
            )
        )
        .children(List.of(children))
        .build();
  }
}
