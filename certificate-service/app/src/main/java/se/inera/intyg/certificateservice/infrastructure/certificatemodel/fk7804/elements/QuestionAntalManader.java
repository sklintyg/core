package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionPrognos.QUESTION_PROGNOS_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationInteger;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationInteger;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ElementDataPredicateFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0006;

public class QuestionAntalManader {

  public static final ElementId QUESTION_ANTAL_MANADER_ID = new ElementId(
      "39.4");
  public static final FieldId QUESTION_ANTAL_MANADER_FIELD_ID = new FieldId(
      "39.4");

  private QuestionAntalManader() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionAntalManader() {
    return ElementSpecification.builder()
        .id(QUESTION_ANTAL_MANADER_ID)
        .includeWhenRenewing(false)
        .configuration(
            ElementConfigurationInteger.builder()
                .id(QUESTION_ANTAL_MANADER_FIELD_ID)
                .name("Ange antal månader")
                .min(1)
                .max(99)
                .build()
        )
        .pdfConfiguration(
            PdfConfigurationText.builder()
                .pdfFieldId(new PdfFieldId("form1[0].Sida3[0].flt_numManadManader[0]"))
                .offset(-8)
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatory(
                    QUESTION_ANTAL_MANADER_ID,
                    QUESTION_ANTAL_MANADER_FIELD_ID
                ),
                CertificateElementRuleFactory.show(
                    QUESTION_PROGNOS_ID,
                    new FieldId(CodeSystemKvFkmu0006.ATER_X_ANTAL_MANADER.code())
                )
            )
        )
        .validations(
            List.of(
                ElementValidationInteger.builder()
                    .mandatory(true)
                    .min(1)
                    .max(99)
                    .build()
            )
        )
        .shouldValidate(
            ElementDataPredicateFactory.codes(
                QUESTION_PROGNOS_ID,
                List.of(
                    new FieldId(CodeSystemKvFkmu0006.ATER_X_ANTAL_MANADER.code())
                )
            )
        )
        .mapping(
            new ElementMapping(QUESTION_PROGNOS_ID, CodeSystemKvFkmu0006.ATER_X_ANTAL_MANADER)
        )
        .includeWhenRenewing(false)
        .build();
  }

}