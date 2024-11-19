package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.elements.QuestionPatientensBehandlingOchVardsituation.AKUT_LIVSHOTANDE_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.elements.QuestionPatientensBehandlingOchVardsituation.QUESTION_PATIENTENS_BEHANDLING_OCH_VARDSITUATION_ID;

import java.time.Period;
import java.util.List;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationDate;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionNarTillstandetBlevAkutLivshotande {

  public static final ElementId QUESTION_NAR_TILLSTANDET_BLEV_AKUT_LIVSHOTANDE_ID = new ElementId(
      "52.3");
  private static final FieldId QUESTION_NAR_TILLSTANDET_BLEV_AKUT_LIVSHOTANDE_FIELD_ID = new FieldId(
      "52.3");
  private static final PdfFieldId PDF_WHEN_CONDITION_BECAME_LIFE_THREATENING_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[1].flt_datumAkutLivshotande[0]");

  private QuestionNarTillstandetBlevAkutLivshotande() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionNarTillstandetBlevAkutLivshotande() {
    return ElementSpecification.builder()
        .id(QUESTION_NAR_TILLSTANDET_BLEV_AKUT_LIVSHOTANDE_ID)
        .configuration(
            ElementConfigurationDate.builder()
                .name("Ange när tillståndet blev akut livshotande")
                .id(QUESTION_NAR_TILLSTANDET_BLEV_AKUT_LIVSHOTANDE_FIELD_ID)
                .max(Period.ofDays(0))
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatory(
                    QUESTION_NAR_TILLSTANDET_BLEV_AKUT_LIVSHOTANDE_ID,
                    QUESTION_NAR_TILLSTANDET_BLEV_AKUT_LIVSHOTANDE_FIELD_ID
                ),
                CertificateElementRuleFactory.show(
                    QUESTION_PATIENTENS_BEHANDLING_OCH_VARDSITUATION_ID,
                    AKUT_LIVSHOTANDE_FIELD_ID
                )
            )
        )
        .validations(
            List.of(
                ElementValidationDate.builder()
                    .mandatory(true)
                    .max(Period.ofDays(0))
                    .build()
            )
        )
        .shouldValidate(
            elementData -> elementData.stream()
                .filter(
                    data -> data.id().equals(QUESTION_PATIENTENS_BEHANDLING_OCH_VARDSITUATION_ID))
                .map(element -> (ElementValueCode) element.value())
                .anyMatch(value -> value.codeId().equals(AKUT_LIVSHOTANDE_FIELD_ID))
        )
        .mapping(
            new ElementMapping(QUESTION_PATIENTENS_BEHANDLING_OCH_VARDSITUATION_ID, null)
        )
        .pdfConfiguration(
            PdfConfigurationDate.builder()
                .pdfFieldId(PDF_WHEN_CONDITION_BECAME_LIFE_THREATENING_FIELD_ID)
                .build()
        )
        .build();
  }
}
