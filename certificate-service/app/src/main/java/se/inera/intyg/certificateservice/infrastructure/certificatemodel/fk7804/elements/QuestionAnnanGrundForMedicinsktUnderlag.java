package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionGrundForMedicinsktUnderlag.QUESTION_GRUND_FOR_MEDICINSKT_UNDERLAG_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionGrundForMedicinsktUnderlag.UTLATANDE_BASERAT_PA_ANNAT_FIELD_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextField;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0001;

public class QuestionAnnanGrundForMedicinsktUnderlag {

  public static final ElementId QUESTION_ANNAN_GRUND_FOR_MEDICINSKT_UNDERLAG_ID = new ElementId(
      "1.3");
  public static final FieldId QUESTION_ANNAN_GRUND_FOR_MEDICINSKT_UNDERLAG_FIELD_ID = new FieldId(
      "1.3");

  private QuestionAnnanGrundForMedicinsktUnderlag() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionAnnanGrundForMedicinsktUnderlag() {
    return ElementSpecification.builder()
        .id(QUESTION_ANNAN_GRUND_FOR_MEDICINSKT_UNDERLAG_ID)
        .configuration(
            ElementConfigurationTextField.builder()
                .id(QUESTION_ANNAN_GRUND_FOR_MEDICINSKT_UNDERLAG_FIELD_ID)
                .name("Ange vad annat är")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatory(
                    QUESTION_ANNAN_GRUND_FOR_MEDICINSKT_UNDERLAG_ID,
                    QUESTION_ANNAN_GRUND_FOR_MEDICINSKT_UNDERLAG_FIELD_ID
                ),
                CertificateElementRuleFactory.limit(
                    QUESTION_ANNAN_GRUND_FOR_MEDICINSKT_UNDERLAG_ID,
                    (short) 50),
                CertificateElementRuleFactory.show(
                    QUESTION_GRUND_FOR_MEDICINSKT_UNDERLAG_ID,
                    UTLATANDE_BASERAT_PA_ANNAT_FIELD_ID
                )
            )
        )
        .validations(
            List.of(
                ElementValidationText.builder()
                    .limit(50)
                    .mandatory(true)
                    .build()
            )
        )
        .mapping(
            new ElementMapping(
                QUESTION_GRUND_FOR_MEDICINSKT_UNDERLAG_ID,
                CodeSystemKvFkmu0001.ANNAT
            )
        )
        .shouldValidate(
            elementData -> elementData.stream()
                .filter(data -> data.id().equals(QUESTION_GRUND_FOR_MEDICINSKT_UNDERLAG_ID))
                .map(element -> (ElementValueDateList) element.value())
                .anyMatch(value -> value.dateList().stream().anyMatch(
                    valueDate -> valueDate.dateId().equals(UTLATANDE_BASERAT_PA_ANNAT_FIELD_ID))
                )
        )
        .build();
  }
}

