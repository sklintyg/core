package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0002.ARBETSSOKANDE;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0002.FORALDRALEDIG;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0002.NUVARANDE_ARBETE;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0002.STUDIER;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionSmittbararpenning.QUESTION_SMITTBARARPENNING_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionSmittbararpenning.QUESTION_SMITTBARARPENNING_ID;

import java.util.List;
import java.util.Map;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxMultipleCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementLayout;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationCodeList;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ElementDataPredicateFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeFactory;

public class QuestionSysselsattning {

  public static final ElementId QUESTION_SYSSELSATTNING_ID = new ElementId("28");
  public static final FieldId QUESTION_SYSSELSATTNING_FIELD_ID = new FieldId("28.1");

  private QuestionSysselsattning() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionSysselsattning(ElementSpecification... children) {
    final var checkboxes = List.of(
        CodeFactory.elementConfigurationCode(NUVARANDE_ARBETE),
        CodeFactory.elementConfigurationCode(ARBETSSOKANDE),
        CodeFactory.elementConfigurationCode(FORALDRALEDIG),
        CodeFactory.elementConfigurationCode(STUDIER)
    );

    return ElementSpecification.builder()
        .id(QUESTION_SYSSELSATTNING_ID)
        .configuration(
            ElementConfigurationCheckboxMultipleCode.builder()
                .id(QUESTION_SYSSELSATTNING_FIELD_ID)
                .name("I relation till vilken sysselsättning bedömer du arbetsförmågan?")
                .description(
                    "Om du kryssar i flera val är det viktigt att du tydliggör under \"Övriga upplysningar\" om sjukskrivningens omfattning eller period skiljer sig åt mellan olika sysselsättningar.")
                .elementLayout(ElementLayout.ROWS)
                .list(checkboxes)
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatoryOrExist(
                    QUESTION_SYSSELSATTNING_ID,
                    List.of(
                        new FieldId(ARBETSSOKANDE.code()),
                        new FieldId(FORALDRALEDIG.code()),
                        new FieldId(NUVARANDE_ARBETE.code()),
                        new FieldId(STUDIER.code()
                        )
                    )
                ),
                CertificateElementRuleFactory.hide(
                    QUESTION_SMITTBARARPENNING_ID,
                    QUESTION_SMITTBARARPENNING_FIELD_ID
                )
            )
        )
        .validations(
            List.of(
                ElementValidationCodeList.builder()
                    .mandatory(true)
                    .build()
            )
        )
        .shouldValidate(
            ElementDataPredicateFactory.checkboxBoolean(QUESTION_SMITTBARARPENNING_ID, false)
        )
        .children(
            List.of(children)
        )
        .pdfConfiguration(
            PdfConfigurationCode.builder()
                .codes(Map.of(
                    new FieldId(NUVARANDE_ARBETE.code()),
                    new PdfFieldId("form1[0].#subform[0].ksr_NuvarandeArbete[0]"),
                    new FieldId(ARBETSSOKANDE.code()),
                    new PdfFieldId("form1[0].#subform[0].ksr_Arbetssokande[0]"),
                    new FieldId(FORALDRALEDIG.code()),
                    new PdfFieldId("form1[0].#subform[0].ksr_Foraldraledighet[0]"),
                    new FieldId(STUDIER.code()),
                    new PdfFieldId("form1[0].#subform[0].ksr_Studier[0]")
                ))
                .build()
        )
        .build();
  }
}
