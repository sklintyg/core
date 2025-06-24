package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorText;

class PrefillTextAreaConverterTest {

  private static final ElementId ELEMENT_ID = new ElementId("1");
  private static final FieldId RADIOTEXT_ID = new FieldId("2");
  private static final String TEXT = "Text";
  private static final ElementSpecification SPECIFICATION = ElementSpecification.builder()
      .id(ELEMENT_ID)
      .configuration(
          ElementConfigurationTextArea.builder()
              .id(RADIOTEXT_ID)
              .build()
      )
      .build();
  private static final ElementData EXPECTED_ELEMENT_DATA = ElementData.builder()
      .id(ELEMENT_ID)
      .value(
          ElementValueText.builder()
              .textId(RADIOTEXT_ID)
              .text(TEXT)
              .build()
      ).build();

  private final PrefillTextAreaConverter prefillRadioTextConverter = new PrefillTextAreaConverter();
  private final XmlGeneratorText xmlGeneratorRadioText = new XmlGeneratorText();


  @Test
  void shouldReturnSupportsRadioText() {
    assertEquals(ElementConfigurationTextArea.class, prefillRadioTextConverter.supports());
  }

  @Nested
  class Answer {

    @Test
    void shouldReturnEmptyListForUnknownIds() {
      assertEquals(
          Collections.emptyList(),
          prefillRadioTextConverter.unknownIds(null, null)
      );
    }

    @Test
    void shouldCreatPrefillAnswerForRadioText() {
      final var expected = PrefillAnswer.builder()
          .elementData(EXPECTED_ELEMENT_DATA)
          .build();

      final var answer = xmlGeneratorRadioText.generate(EXPECTED_ELEMENT_DATA, SPECIFICATION);

      final var result = prefillRadioTextConverter.prefillAnswer(answer, SPECIFICATION);

      assertEquals(expected, result);
    }

    @Test
    void shouldReturnErrorIfWrongConfigurationType() {
      final var wrongConfiguration = ElementSpecification.builder()
          .id(ELEMENT_ID)
          .configuration(ElementConfigurationCategory.builder().build())
          .build();

      final var answer = xmlGeneratorRadioText.generate(
          EXPECTED_ELEMENT_DATA,
          wrongConfiguration
      );

      final var result = prefillRadioTextConverter.prefillAnswer(answer, wrongConfiguration);

      assertEquals(
          PrefillErrorType.TECHNICAL_ERROR,
          result.getErrors().getFirst().type()
      );
    }

    @Test
    void shouldReturnErrorIfMoreThanOneAnswer() {
      final var answer = xmlGeneratorRadioText.generate(
          EXPECTED_ELEMENT_DATA,
          SPECIFICATION
      );
      final var answer2 = xmlGeneratorRadioText.generate(
          EXPECTED_ELEMENT_DATA,
          SPECIFICATION
      );

      final var result = prefillRadioTextConverter.prefillAnswer(
          List.of(answer.getFirst(), answer2.getFirst()), SPECIFICATION);

      assertEquals(
          PrefillErrorType.WRONG_NUMBER_OF_ANSWERS,
          result.getErrors().getFirst().type()
      );
    }

    @Test
    void shouldReturnErrorIfNoAnswers() {
      final var result = prefillRadioTextConverter.prefillAnswer(List.of(), SPECIFICATION);

      assertEquals(
          PrefillErrorType.WRONG_NUMBER_OF_ANSWERS,
          result.getErrors().getFirst().type()
      );
    }
  }

  @Nested
  class SubAnswer {

    @Test
    void shouldReturnPrefillAnswerForSubAnswer() {
      final var expected = PrefillAnswer.builder()
          .elementData(EXPECTED_ELEMENT_DATA)
          .build();

      final var answer = xmlGeneratorRadioText.generate(EXPECTED_ELEMENT_DATA, SPECIFICATION);

      final var result = prefillRadioTextConverter.prefillSubAnswer(
          answer.getFirst().getDelsvar(),
          SPECIFICATION);

      assertEquals(expected, result);
    }

    @Test
    void shouldReturnErrorIfWrongConfigurationTypeForSubAnswer() {
      final var wrongConfiguration = ElementSpecification.builder()
          .id(ELEMENT_ID)
          .configuration(ElementConfigurationCategory.builder().build())
          .build();

      final var answer = xmlGeneratorRadioText.generate(
          EXPECTED_ELEMENT_DATA,
          wrongConfiguration
      );

      final var result = prefillRadioTextConverter.prefillSubAnswer(
          answer.getFirst().getDelsvar(),
          wrongConfiguration);

      assertEquals(
          PrefillErrorType.TECHNICAL_ERROR,
          result.getErrors().getFirst().type()
      );
    }

    @Test
    void shouldReturnErrorIfMoreThanOneSubAnswer() {
      final var answer = xmlGeneratorRadioText.generate(
          EXPECTED_ELEMENT_DATA,
          SPECIFICATION
      );
      final var answer2 = xmlGeneratorRadioText.generate(
          EXPECTED_ELEMENT_DATA,
          SPECIFICATION
      );

      final var result = prefillRadioTextConverter.prefillSubAnswer(
          List.of(answer.getFirst().getDelsvar().getFirst(),
              answer2.getFirst().getDelsvar().getFirst()), SPECIFICATION);

      assertEquals(
          PrefillErrorType.WRONG_NUMBER_OF_ANSWERS,
          result.getErrors().getFirst().type()
      );
    }

    @Test
    void shouldReturnErrorIfNoSubAnswers() {
      final var result = prefillRadioTextConverter.prefillSubAnswer(List.of(), SPECIFICATION);

      assertEquals(
          PrefillErrorType.WRONG_NUMBER_OF_ANSWERS,
          result.getErrors().getFirst().type()
      );
    }
  }
}