package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.elements.QuestionPatientensBehandlingOchVardsituation.AKUT_LIVSHOTANDE_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.elements.QuestionPatientensBehandlingOchVardsituation.QUESTION_PATIENTENS_BEHANDLING_OCH_VARDSITUATION_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionUppskattaHurLangeTillstandetKommerVaraLivshotande {

  public static final ElementId QUESTION_UPSKATTA_HUR_LANGE_TILLSTANDET_KOMMER_VARA_LIVSHOTANDE_ID = new ElementId(
      "52.5");
  public static final FieldId QUESTION_UPSKATTA_HUR_LANGE_TILLSTANDET_KOMMER_VARA_LIVSHOTANDE_FIELD_ID = new FieldId(
      "52.5");
  private static final PdfFieldId PDF_ESTIMATE_YES_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[1].ksr_Ja[0]");
  private static final PdfFieldId PDF_ESTIMATE_NO_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[1].ksr_Nej[0]");

  private QuestionUppskattaHurLangeTillstandetKommerVaraLivshotande() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionUppskattaHurLangeTillstandetKommerVaraLivshotande() {
    return ElementSpecification.builder()
        .id(QUESTION_UPSKATTA_HUR_LANGE_TILLSTANDET_KOMMER_VARA_LIVSHOTANDE_ID)
        .configuration(
            ElementConfigurationRadioBoolean.builder()
                .id(QUESTION_UPSKATTA_HUR_LANGE_TILLSTANDET_KOMMER_VARA_LIVSHOTANDE_FIELD_ID)
                .name("Kan du uppskatta hur länge tillståndet kommer vara livshotande?")
                .selectedText("Ja")
                .unselectedText("Nej")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatoryExist(
                    QUESTION_UPSKATTA_HUR_LANGE_TILLSTANDET_KOMMER_VARA_LIVSHOTANDE_ID,
                    QUESTION_UPSKATTA_HUR_LANGE_TILLSTANDET_KOMMER_VARA_LIVSHOTANDE_FIELD_ID
                ),
                CertificateElementRuleFactory.show(
                    QUESTION_PATIENTENS_BEHANDLING_OCH_VARDSITUATION_ID,
                    AKUT_LIVSHOTANDE_FIELD_ID
                )
            )
        )
        .validations(
            List.of(
                ElementValidationBoolean.builder()
                    .mandatory(true)
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
            PdfConfigurationBoolean.builder()
                .checkboxTrue(PDF_ESTIMATE_YES_FIELD_ID)
                .checkboxFalse(PDF_ESTIMATE_NO_FIELD_ID)
                .build()
        )
        .build();
  }
}