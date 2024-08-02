package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionFunktionsnedsattning.FUNKTIONSNEDSATTNING_UPPMAKRMSAHET_ID;

import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

public class QuestionUppmarksamhetMotivering extends
    AbstractFunktionsnedsattningMotivering {

  public static final ElementId FUNKTIONSNEDSATTNING_MOTIVERING_UPPMAKRMSAHET_ID = new ElementId(
      "10");
  private static final FieldId FUNKTIONSNEDSATTNING_MOTIVERING_UPPMAKRMSAHET_FIELD_ID = new FieldId(
      "10.1");

  public static ElementSpecification questionUppmarksamhetMotivering() {
    return getFunktionsnedsattningMotivering(
        FUNKTIONSNEDSATTNING_MOTIVERING_UPPMAKRMSAHET_ID,
        FUNKTIONSNEDSATTNING_MOTIVERING_UPPMAKRMSAHET_FIELD_ID,
        FUNKTIONSNEDSATTNING_UPPMAKRMSAHET_ID,
        "Uppmärksamhet, koncentration och exekutiv funktion",
        GENERAL_LABEL_FUNKTIONSNEDSATTNING,
        "Uppmärksamhet handlar om förmågan att rikta fokus på rätt sak vid rätt tillfälle samt att skifta, fördela och vidmakthålla uppmärksamheten. En person behöver även viljemässigt kunna rikta sin uppmärksamhet under en längre tid. Med exekutiv funktion menas förmågan att planera, initiera, genomföra, korrigera och avsluta en handling."
    );
  }
}
