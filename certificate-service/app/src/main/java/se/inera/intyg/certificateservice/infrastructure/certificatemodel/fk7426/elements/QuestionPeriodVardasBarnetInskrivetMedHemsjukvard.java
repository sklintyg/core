package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements.QuestionVardasBarnetInneliggandePaSjukhus.QUESTION_VARDAS_BARNET_INNELIGGANDE_PA_SJUKHUS_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements.QuestionVardasBarnetInskrivetMedHemsjukvard.QUESTION_VARDAS_BARN_INSKRIVET_MED_HEMSJUKVARD_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements.QuestionVardasBarnetInskrivetMedHemsjukvard.QUESTION_VARDAS_BARN_INSKRIVET_MED_HEMSJUKVARD_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDateRange;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationDateRange;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationDateRange;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ShouldValidateFactory;

public class QuestionPeriodVardasBarnetInskrivetMedHemsjukvard {

  public static final ElementId QUESTION_PERIOD_VARDAS_BARN_INSKRIVET_MED_HEMSJUKVARD_ID = new ElementId(
      "62.4");
  private static final FieldId QUESTION_PERIOD_VARDAS_BARN_INSKRIVET_MED_HEMSJUKVARD_FIELD_ID = new FieldId(
      "62.4");
  private static final PdfFieldId PDF_FIELD_ID_FROM = new PdfFieldId(
      "form1[0].#subform[3].flt_datFranMed2[0]");
  private static final PdfFieldId PDF_FIELD_ID_TO = new PdfFieldId(
      "form1[0].#subform[3].flt_datTillMed2[0]");

  private QuestionPeriodVardasBarnetInskrivetMedHemsjukvard() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionPeriodVardasBarnetInskrivetMedHemsjukvard() {
    return ElementSpecification.builder()
        .id(QUESTION_PERIOD_VARDAS_BARN_INSKRIVET_MED_HEMSJUKVARD_ID)
        .configuration(
            ElementConfigurationDateRange.builder()
                .name("Ange period")
                .labelFrom("Fr.o.m")
                .labelTo("T.o.m")
                .id(QUESTION_PERIOD_VARDAS_BARN_INSKRIVET_MED_HEMSJUKVARD_FIELD_ID)
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatory(
                    QUESTION_PERIOD_VARDAS_BARN_INSKRIVET_MED_HEMSJUKVARD_ID,
                    QUESTION_PERIOD_VARDAS_BARN_INSKRIVET_MED_HEMSJUKVARD_FIELD_ID),
                CertificateElementRuleFactory.show(
                    QUESTION_VARDAS_BARN_INSKRIVET_MED_HEMSJUKVARD_ID,
                    QUESTION_VARDAS_BARN_INSKRIVET_MED_HEMSJUKVARD_FIELD_ID)
            )
        )
        .validations(
            List.of(
                ElementValidationDateRange.builder()
                    .mandatory(true)
                    .build()
            )
        )
        .shouldValidate(
            ShouldValidateFactory.valueBoolean(QUESTION_VARDAS_BARN_INSKRIVET_MED_HEMSJUKVARD_ID)
        )
        .pdfConfiguration(
            PdfConfigurationDateRange.builder()
                .from(PDF_FIELD_ID_FROM)
                .to(PDF_FIELD_ID_TO)
                .build()
        )
        .includeWhenRenewing(false)
        .mapping(new ElementMapping(QUESTION_VARDAS_BARNET_INNELIGGANDE_PA_SJUKHUS_ID, null))
        .build();
  }
}
