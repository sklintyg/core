package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.elements.QuestionAnnanGrundForMedicinsktUnderlag.questionAnnanGrundForMedicinsktUnderlag;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;

class TextFieldPrefillerTest {

  private static final String DELSVAR_ID = "1.3";
  private final TextFieldPrefiller textFieldPrefiller = new TextFieldPrefiller();

  @Test
  void shallParseDelsvar() {
    String text = "This is some extra information";
    var expected = PrefillAnswer.builder()
        .elementData(ElementData.builder()
            .id(new ElementId(DELSVAR_ID))
            .value(ElementValueText.builder()
                .textId(new FieldId(DELSVAR_ID))
                .text(text)
                .build())
            .build())
        .build();

    var delsvar = new Delsvar();
    delsvar.getContent().add(text);
    delsvar.setId(DELSVAR_ID);

    var result = textFieldPrefiller.prefillSubAnswer(List.of(delsvar),
        questionAnnanGrundForMedicinsktUnderlag());

    assertEquals(expected, result);
  }

}