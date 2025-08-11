package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationRadioCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0006;

class QuestionPrognosTest {

  @Test
  void shouldContainCorrectPdfConfiguration() {
    final var elementSpecification = QuestionPrognos.questionPrognos();
    final var expected = PdfConfigurationRadioCode.builder()
        .radioGroupFieldId(new PdfFieldId("form1[0].Sida3[0].RadioButtonList4[0]"))
        .codes(Map.of(
            new FieldId(CodeSystemKvFkmu0006.STOR_SANNOLIKHET.code()), new PdfFieldId("1"),
            new FieldId(CodeSystemKvFkmu0006.ATER_X_ANTAL_MANADER.code()), new PdfFieldId("2"),
            new FieldId(CodeSystemKvFkmu0006.SANNOLIKT_INTE.code()), new PdfFieldId("4"),
            new FieldId(CodeSystemKvFkmu0006.PROGNOS_OKLAR.code()), new PdfFieldId("3")
        ))
        .build();
    assertThat(elementSpecification.pdfConfiguration()).isEqualTo(expected);
  }
}

