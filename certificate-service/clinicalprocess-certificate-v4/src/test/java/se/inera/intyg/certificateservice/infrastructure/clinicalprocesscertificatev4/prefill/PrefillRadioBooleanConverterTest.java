package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorBoolean;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;

class PrefillRadioBooleanConverterTest {

  private static final ElementId ELEMENT_ID = new ElementId("1");
  private static final FieldId RADIOBOOLEAN_ID = new FieldId("2");
  private static final Boolean BOOLEAN = true;
  private static final ElementSpecification SPECIFICATION = ElementSpecification.builder()
      .id(ELEMENT_ID)
      .configuration(
          ElementConfigurationRadioBoolean.builder()
              .id(RADIOBOOLEAN_ID)
              .build()
      )
      .build();
  private static final ElementData EXPECTED_ELEMENT_DATA = ElementData.builder()
      .id(ELEMENT_ID)
      .value(
          ElementValueBoolean.builder()
              .booleanId(RADIOBOOLEAN_ID)
              .value(BOOLEAN)
              .build()
      ).build();

  private final PrefillRadioBooleanConverter prefillRadioBooleanConverter = new PrefillRadioBooleanConverter();
  private final XmlGeneratorBoolean xmlGeneratorRadioBoolean = new XmlGeneratorBoolean();

  @Test
  void shouldReturnSupportsRadioBoolean() {
    assertEquals(ElementConfigurationRadioBoolean.class, prefillRadioBooleanConverter.supports());
  }

  @Nested
  class Answer {

    @Test
    void shouldReturnEmptyListForUnknownIds() {
      assertEquals(
          Collections.emptyList(),
          prefillRadioBooleanConverter.unknownIds(null, null)
      );
    }

    @Test
    void shouldCreatPrefillAnswerForRadioBoolean() {
      final var expected = PrefillAnswer.builder()
          .elementData(EXPECTED_ELEMENT_DATA)
          .build();

      final var answer = xmlGeneratorRadioBoolean.generate(EXPECTED_ELEMENT_DATA, SPECIFICATION);

      final var result = prefillRadioBooleanConverter.prefillAnswer(answer, SPECIFICATION);

      assertEquals(expected, result);
    }

    @Test
    void shouldReturnErrorIfWrongConfigurationType() {
      final var wrongConfiguration = ElementSpecification.builder()
          .id(ELEMENT_ID)
          .configuration(ElementConfigurationCategory.builder().build())
          .build();

      final var answer = xmlGeneratorRadioBoolean.generate(
          EXPECTED_ELEMENT_DATA,
          wrongConfiguration
      );

      final var result = prefillRadioBooleanConverter.prefillAnswer(answer, wrongConfiguration);

      assertEquals(
          PrefillErrorType.TECHNICAL_ERROR,
          result.getErrors().getFirst().type()
      );
    }

    @Test
    void shouldReturnErrorIfMoreThanOneAnswer() {
      final var answer = xmlGeneratorRadioBoolean.generate(
          EXPECTED_ELEMENT_DATA,
          SPECIFICATION
      );
      final var answer2 = xmlGeneratorRadioBoolean.generate(
          EXPECTED_ELEMENT_DATA,
          SPECIFICATION
      );

      final var result = prefillRadioBooleanConverter.prefillAnswer(
          List.of(answer.getFirst(), answer2.getFirst()), SPECIFICATION);

      assertEquals(
          PrefillErrorType.WRONG_NUMBER_OF_ANSWERS,
          result.getErrors().getFirst().type()
      );
    }

    @Test
    void shouldReturnErrorIfNoAnswers() {
      final var result = prefillRadioBooleanConverter.prefillAnswer(List.of(), SPECIFICATION);

      assertEquals(
          PrefillErrorType.WRONG_NUMBER_OF_ANSWERS,
          result.getErrors().getFirst().type()
      );
    }

    @Test
    void shouldReturnErrorIfInvalidRadioBooleanFormat() {
      final var answer = new Svar();
      final var subAnswer = new Delsvar();
      final var content = List.of("invalid-radioBoolean-format");
      subAnswer.getContent().add(content);
      answer.getDelsvar().add(subAnswer);

      final var result = prefillRadioBooleanConverter.prefillAnswer(List.of(answer), SPECIFICATION);

      assertEquals(
          PrefillErrorType.INVALID_FORMAT,
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

      final var answer = xmlGeneratorRadioBoolean.generate(EXPECTED_ELEMENT_DATA, SPECIFICATION);

      final var result = prefillRadioBooleanConverter.prefillSubAnswer(
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

      final var answer = xmlGeneratorRadioBoolean.generate(
          EXPECTED_ELEMENT_DATA,
          wrongConfiguration
      );

      final var result = prefillRadioBooleanConverter.prefillSubAnswer(
          answer.getFirst().getDelsvar(),
          wrongConfiguration);

      assertEquals(
          PrefillErrorType.TECHNICAL_ERROR,
          result.getErrors().getFirst().type()
      );
    }

    @Test
    void shouldReturnErrorIfMoreThanOneSubAnswer() {
      final var answer = xmlGeneratorRadioBoolean.generate(
          EXPECTED_ELEMENT_DATA,
          SPECIFICATION
      );
      final var answer2 = xmlGeneratorRadioBoolean.generate(
          EXPECTED_ELEMENT_DATA,
          SPECIFICATION
      );

      final var result = prefillRadioBooleanConverter.prefillSubAnswer(
          List.of(answer.getFirst().getDelsvar().getFirst(),
              answer2.getFirst().getDelsvar().getFirst()), SPECIFICATION);

      assertEquals(
          PrefillErrorType.WRONG_NUMBER_OF_ANSWERS,
          result.getErrors().getFirst().type()
      );
    }

    @Test
    void shouldReturnErrorIfNoSubAnswers() {
      final var result = prefillRadioBooleanConverter.prefillSubAnswer(List.of(), SPECIFICATION);

      assertEquals(
          PrefillErrorType.WRONG_NUMBER_OF_ANSWERS,
          result.getErrors().getFirst().type()
      );
    }
  }
}