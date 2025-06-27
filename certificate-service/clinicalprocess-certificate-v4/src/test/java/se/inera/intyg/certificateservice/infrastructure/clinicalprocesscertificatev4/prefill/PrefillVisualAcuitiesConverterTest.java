package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.Correction;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueVisualAcuities;
import se.inera.intyg.certificateservice.domain.certificate.model.VisualAcuity;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationVisualAcuities;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementVisualAcuity;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.converter.PrefillVisualAcuitiesConverter;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;
import se.riv.clinicalprocess.healthcond.certificate.v33.Forifyllnad;

class PrefillVisualAcuitiesConverterTest {

  private static final ElementId ELEMENT_ID = new ElementId("1");
  private static final FieldId FIELD_ID = new FieldId("F");

  private static final String RIGHT_EYE_WITHOUT_CORRECTION_ID = "5.1";
  private static final String RIGHT_EYE_WITH_CORRECTION_ID = "5.4";
  private static final String LEFT_EYE_WITHOUT_CORRECTION_ID = "5.2";
  private static final String LEFT_EYE_WITH_CORRECTION_ID = "5.5";
  private static final String BINOCULAR_WITHOUT_CORRECTION_ID = "5.3";
  private static final String BINOCULAR_WITH_CORRECTION_ID = "5.6";
  private static final ElementSpecification SPECIFICATION = ElementSpecification.builder()
      .id(ELEMENT_ID)
      .configuration(
          ElementConfigurationVisualAcuities.builder()
              .id(FIELD_ID)
              .rightEye(
                  ElementVisualAcuity.builder()
                      .label("Höger öga")
                      .withoutCorrectionId(RIGHT_EYE_WITHOUT_CORRECTION_ID)
                      .withCorrectionId(RIGHT_EYE_WITH_CORRECTION_ID)
                      .build()
              )
              .leftEye(
                  ElementVisualAcuity.builder()
                      .label("Vänster öga")
                      .withoutCorrectionId(LEFT_EYE_WITHOUT_CORRECTION_ID)
                      .withCorrectionId(LEFT_EYE_WITH_CORRECTION_ID)
                      .build()
              )
              .binocular(
                  ElementVisualAcuity.builder()
                      .label("Binokulärt")
                      .withoutCorrectionId(BINOCULAR_WITHOUT_CORRECTION_ID)
                      .withCorrectionId(BINOCULAR_WITH_CORRECTION_ID)
                      .build()
              )
              .build()
      )
      .build();
  private static final ElementData EXPECTED_ELEMENT_DATA = ElementData.builder()
      .id(ELEMENT_ID)
      .value(
          ElementValueVisualAcuities.builder()
              .rightEye(
                  VisualAcuity.builder()
                      .withCorrection(
                          Correction.builder()
                              .id(new FieldId(RIGHT_EYE_WITH_CORRECTION_ID))
                              .value(1.1)
                              .build()
                      )
                      .withoutCorrection(
                          Correction.builder()
                              .id(new FieldId(RIGHT_EYE_WITHOUT_CORRECTION_ID))
                              .value(1.2)
                              .build()
                      )
                      .build()
              )
              .leftEye(
                  VisualAcuity.builder()
                      .withCorrection(
                          Correction.builder()
                              .id(new FieldId(LEFT_EYE_WITH_CORRECTION_ID))
                              .value(1.3)
                              .build()
                      )
                      .withoutCorrection(
                          Correction.builder()
                              .id(new FieldId(LEFT_EYE_WITHOUT_CORRECTION_ID))
                              .value(1.4)
                              .build()
                      )
                      .build()
              )
              .binocular(
                  VisualAcuity.builder()
                      .withCorrection(
                          Correction.builder()
                              .id(new FieldId(BINOCULAR_WITH_CORRECTION_ID))
                              .value(1.5)
                              .build()
                      )
                      .withoutCorrection(
                          Correction.builder()
                              .id(new FieldId(BINOCULAR_WITHOUT_CORRECTION_ID))
                              .value(1.6)
                              .build()
                      )
                      .build()
              )
              .build()
      ).build();

  private final PrefillVisualAcuitiesConverter prefillVisualAcuitiesConverter = new PrefillVisualAcuitiesConverter();

  @Test
  void shouldReturnSupportsConfigurationVisualAcuities() {
    assertEquals(ElementConfigurationVisualAcuities.class,
        prefillVisualAcuitiesConverter.supports());
  }

  @Nested
  class PrefillAnswerWithForifyllnad {

    @Test
    void shouldReturnNullIfNoAnswerExists() {
      Forifyllnad prefill = new Forifyllnad();

      PrefillAnswer result = prefillVisualAcuitiesConverter.prefillAnswer(
          SPECIFICATION,
          prefill
      );

      assertNull(result);
    }

    @Test
    void shouldReturnErrorIfWrongConfigurationType() {
      final var prefill = new Forifyllnad();
      final var wrongSpec = ElementSpecification.builder()
          .id(ELEMENT_ID)
          .configuration(ElementConfigurationCategory.builder().build())
          .build();

      final var result = prefillVisualAcuitiesConverter.prefillAnswer(wrongSpec, prefill);

      assertEquals(
          PrefillErrorType.TECHNICAL_ERROR,
          result.getErrors().getFirst().type()
      );
    }

    @Test
    void shouldReturnPrefillAnswerWithErrorWrongNumberOfSubAnswers() {
      final var prefill = new Forifyllnad();
      final var svar = new Svar();
      svar.setId(ELEMENT_ID.id());
      prefill.getSvar().add(svar);

      final var result = prefillVisualAcuitiesConverter.prefillAnswer(SPECIFICATION,
          prefill);

      assertEquals(
          PrefillErrorType.WRONG_NUMBER_OF_ANSWERS,
          result.getErrors().getFirst().type()
      );
    }

    @Test
    void shouldReturnPrefillAnswerWithErrorWrongNumberOfAnswers() {
      final var prefill = new Forifyllnad();
      final var svar1 = new Svar();
      final var svar2 = new Svar();
      svar1.setId(ELEMENT_ID.id());
      svar2.setId(ELEMENT_ID.id());

      final var delsvar1 = new Delsvar();
      final var delsvar2 = new Delsvar();

      svar1.getDelsvar().add(delsvar1);
      svar2.getDelsvar().add(delsvar2);

      prefill.getSvar().add(svar1);
      prefill.getSvar().add(svar2);

      final var result = prefillVisualAcuitiesConverter.prefillAnswer(SPECIFICATION,
          prefill);

      assertEquals(
          PrefillErrorType.WRONG_NUMBER_OF_ANSWERS,
          result.getErrors().getFirst().type()
      );
    }

    @Test
    void shouldReturnPrefillAnswerWithElementData() {
      final var prefill = new Forifyllnad();
      final var svar = new Svar();
      svar.setId(ELEMENT_ID.id());

      final var delsvar1 = new Delsvar();
      delsvar1.setId(RIGHT_EYE_WITH_CORRECTION_ID);
      delsvar1.getContent().add("1,1");
      final var delsvar2 = new Delsvar();
      delsvar2.setId(RIGHT_EYE_WITHOUT_CORRECTION_ID);
      delsvar2.getContent().add("1,2");
      final var delsvar3 = new Delsvar();
      delsvar3.setId(LEFT_EYE_WITH_CORRECTION_ID);
      delsvar3.getContent().add("1,3");
      final var delsvar4 = new Delsvar();
      delsvar4.setId(LEFT_EYE_WITHOUT_CORRECTION_ID);
      delsvar4.getContent().add("1,4");
      final var delsvar5 = new Delsvar();
      delsvar5.setId(BINOCULAR_WITH_CORRECTION_ID);
      delsvar5.getContent().add("1,5");
      final var delsvar6 = new Delsvar();
      delsvar6.setId(BINOCULAR_WITHOUT_CORRECTION_ID);
      delsvar6.getContent().add("1,6");

      svar.getDelsvar().add(delsvar1);
      svar.getDelsvar().add(delsvar2);
      svar.getDelsvar().add(delsvar3);
      svar.getDelsvar().add(delsvar4);
      svar.getDelsvar().add(delsvar5);
      svar.getDelsvar().add(delsvar6);

      prefill.getSvar().add(svar);

      final var result = prefillVisualAcuitiesConverter.prefillAnswer(SPECIFICATION, prefill);

      final var expected = PrefillAnswer.builder()
          .elementData(EXPECTED_ELEMENT_DATA)
          .build();

      assertEquals(expected, result);
    }

    @Test
    void shouldReturnPrefillAnswerWithPartialElementData() {
      final var expectedElementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueVisualAcuities.builder()
                  .rightEye(
                      VisualAcuity.builder()
                          .withCorrection(
                              Correction.builder()
                                  .id(new FieldId(RIGHT_EYE_WITH_CORRECTION_ID))
                                  .value(1.1)
                                  .build()
                          )
                          .withoutCorrection(
                              Correction.builder()
                                  .id(new FieldId(RIGHT_EYE_WITHOUT_CORRECTION_ID))
                                  .value(1.2)
                                  .build()
                          )
                          .build()
                  )
                  .leftEye(
                      VisualAcuity.builder()
                          .withCorrection(
                              Correction.builder()
                                  .id(new FieldId(LEFT_EYE_WITH_CORRECTION_ID))
                                  .value(1.3)
                                  .build()
                          )
                          .withoutCorrection(null)
                          .build()
                  )
                  .build()
          )
          .build();
      final var prefill = new Forifyllnad();
      final var svar = new Svar();
      svar.setId(ELEMENT_ID.id());

      final var delsvar1 = new Delsvar();
      delsvar1.setId(RIGHT_EYE_WITH_CORRECTION_ID);
      delsvar1.getContent().add("1,1");
      final var delsvar2 = new Delsvar();
      delsvar2.setId(RIGHT_EYE_WITHOUT_CORRECTION_ID);
      delsvar2.getContent().add("1,2");
      final var delsvar3 = new Delsvar();
      delsvar3.setId(LEFT_EYE_WITH_CORRECTION_ID);
      delsvar3.getContent().add("1,3");

      svar.getDelsvar().add(delsvar1);
      svar.getDelsvar().add(delsvar2);
      svar.getDelsvar().add(delsvar3);

      prefill.getSvar().add(svar);

      final var result = prefillVisualAcuitiesConverter.prefillAnswer(SPECIFICATION, prefill);

      final var expected = PrefillAnswer.builder()
          .elementData(expectedElementData)
          .build();

      assertEquals(expected, result);
    }

    @Test
    void shouldReturnPrefillAnswerWithErrorInvalidFormatIfValueIsNotDouble() {
      final var prefill = new Forifyllnad();
      final var svar = new Svar();
      svar.setId(ELEMENT_ID.id());

      final var delsvar1 = new Delsvar();
      delsvar1.setId(RIGHT_EYE_WITH_CORRECTION_ID);
      delsvar1.getContent().add("notDouble");

      svar.getDelsvar().add(delsvar1);

      prefill.getSvar().add(svar);

      final var result = prefillVisualAcuitiesConverter.prefillAnswer(SPECIFICATION, prefill);

      assertEquals(
          PrefillErrorType.INVALID_FORMAT,
          result.getErrors().getFirst().type()
      );
    }
  }
}