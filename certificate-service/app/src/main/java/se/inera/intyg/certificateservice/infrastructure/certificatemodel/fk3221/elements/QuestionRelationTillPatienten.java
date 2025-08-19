package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3221.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3221.elements.QuestionGrundForMedicinsktUnderlag.QUESTION_GRUND_FOR_MEDICINSKT_UNDERLAG_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3221.elements.QuestionGrundForMedicinsktUnderlag.UTLATANDE_BASERAT_PA_ANHORIG_FIELD_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextField;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0001;

public class QuestionRelationTillPatienten {

  public static final ElementId QUESTION_RELATION_TILL_PATIENTEN_ID = new ElementId("1.4");
  private static final FieldId QUESTION_RELATION_TILL_PATIENTEN_FIELD_ID = new FieldId("1.4");
  public static final String PDF_FIELD_ID = "form1[0].#subform[0].flt_txtAnhorigAnnan[0]";

  private QuestionRelationTillPatienten() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionRelationTillPatienten() {
    return ElementSpecification.builder()
        .id(QUESTION_RELATION_TILL_PATIENTEN_ID)
        .configuration(
            ElementConfigurationTextField.builder()
                .id(QUESTION_RELATION_TILL_PATIENTEN_FIELD_ID)
                .name(
                    "Ange anhörigs eller annans relation till patienten")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatory(
                    QUESTION_RELATION_TILL_PATIENTEN_ID,
                    QUESTION_RELATION_TILL_PATIENTEN_FIELD_ID
                ),
                CertificateElementRuleFactory.limit(
                    QUESTION_RELATION_TILL_PATIENTEN_ID,
                    (short) 4000),
                CertificateElementRuleFactory.show(
                    QUESTION_GRUND_FOR_MEDICINSKT_UNDERLAG_ID,
                    UTLATANDE_BASERAT_PA_ANHORIG_FIELD_ID
                )
            )
        )
        .validations(
            List.of(
                ElementValidationText.builder()
                    .mandatory(true)
                    .limit(4000)
                    .build()
            )
        )
        .mapping(
            new ElementMapping(
                QUESTION_GRUND_FOR_MEDICINSKT_UNDERLAG_ID,
                CodeSystemKvFkmu0001.ANHORIG_V1
            )
        )
        .shouldValidate(
            elementData -> elementData.stream()
                .filter(data -> data.id().equals(QUESTION_GRUND_FOR_MEDICINSKT_UNDERLAG_ID))
                .map(element -> (ElementValueDateList) element.value())
                .anyMatch(value -> value.dateList().stream().anyMatch(
                    valueDate -> valueDate.dateId().equals(UTLATANDE_BASERAT_PA_ANHORIG_FIELD_ID))
                )
        )
        .pdfConfiguration(
            PdfConfigurationText.builder()
                .pdfFieldId(new PdfFieldId(PDF_FIELD_ID))
                .maxLength(50)
                .overflowSheetFieldId(
                    new PdfFieldId(("form1[0].#subform[4].flt_txtFortsattningsblad[0]")))
                .build()
        )
        .includeWhenRenewing(false)
        .build();
  }
}
