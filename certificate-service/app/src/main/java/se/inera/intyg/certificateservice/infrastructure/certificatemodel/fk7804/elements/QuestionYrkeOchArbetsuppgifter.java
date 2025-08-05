package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0002.NUVARANDE_ARBETE;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionSysselsattning.SYSSELSATTNING_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextField;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ShouldValidateFactory;

public class QuestionYrkeOchArbetsuppgifter {

  public static final ElementId YRKE_ARBETSUPPGIFTER_ID = new ElementId("29");
  public static final FieldId YRKE_ARBETSUPPGIFTER_FIELD_ID = new FieldId("29.1");

  private QuestionYrkeOchArbetsuppgifter() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionYrkeOchArbetsuppgifter() {
    return ElementSpecification.builder()
        .id(YRKE_ARBETSUPPGIFTER_ID)
        .configuration(
            ElementConfigurationTextField.builder()
                .id(YRKE_ARBETSUPPGIFTER_FIELD_ID)
                .name("Ange yrke och arbetsuppgifter")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.show(
                    SYSSELSATTNING_ID,
                    new RuleExpression(String.format("$%s", NUVARANDE_ARBETE.code()))
                ),
                CertificateElementRuleFactory.mandatory(
                    YRKE_ARBETSUPPGIFTER_ID,
                    YRKE_ARBETSUPPGIFTER_FIELD_ID
                )
            )
        )
        .validations(
            List.of(
                ElementValidationText.builder()
                    .mandatory(false)
                    .build()
            )
        )
        .mapping(
            new ElementMapping(SYSSELSATTNING_ID, NUVARANDE_ARBETE)
        )
        .shouldValidate(
            ShouldValidateFactory.codeList(
                SYSSELSATTNING_ID,
                List.of(new FieldId(NUVARANDE_ARBETE.code()))
            )
        )
        .build();
  }
}

