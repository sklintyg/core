package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.Correction;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueOcularAcuities;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificate.model.OcularAcuity;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;

class XmlGeneratorOcularAcuitiesTest {

  private static final String ID = "id";
  private static final ElementSpecification ELEMENT_SPECIFICATION = ElementSpecification.builder()
      .build();
  private static final String ID_1 = "ID_1";
  private static final String ID_2 = "ID_2";
  private static final String ID_3 = "ID_3";
  private static final String ID_4 = "ID_4";
  private static final String ID_5 = "ID_5";
  private static final String ID_6 = "ID_6";
  private static final String ELEMENT_ID = "ELEMENT_ID";
  XmlGeneratorOcularAcuities xmlGeneratorOcularAcuities;

  @BeforeEach
  void setUp() {
    xmlGeneratorOcularAcuities = new XmlGeneratorOcularAcuities();
  }

  @Test
  void shallSupportElementValueOcularAcuities() {
    assertEquals(ElementValueOcularAcuities.class, xmlGeneratorOcularAcuities.supports());
  }

  @Test
  void shallReturnEmptyListIfWrongElementValueType() {
    final var elementDataWithValueText = ElementData.builder()
        .value(ElementValueText.builder().build())
        .build();

    final var actualResult = xmlGeneratorOcularAcuities.generate(elementDataWithValueText,
        ELEMENT_SPECIFICATION);
    assertEquals(Collections.emptyList(), actualResult);
  }

  @Test
  void shallReturnEmptyListIfOcularAcuitiesIsEmpty() {
    final var emptyOcularAcuities = ElementData.builder()
        .value(
            ElementValueOcularAcuities.builder()
                .rightEye(
                    defaultEmptyOcularAcuity()
                )
                .leftEye(
                    defaultEmptyOcularAcuity()
                )
                .binocular(
                    defaultEmptyOcularAcuity()
                )
                .build()
        )
        .build();

    final var actualResult = xmlGeneratorOcularAcuities.generate(emptyOcularAcuities,
        ELEMENT_SPECIFICATION);
    assertEquals(Collections.emptyList(), actualResult);
  }

  @Nested
  class TestOneOcularAcuity {


    @Test
    void shallReturnAnswerForRightEye() {
      final var answer = new Svar();
      answer.getDelsvar().addAll(
          List.of(
              buildDelsvar(ID_1, 2.0),
              buildDelsvar(ID_2, 1.0)
          )
      );

      final var emptyOcularAcuities = ElementData.builder()
          .id(new ElementId(ELEMENT_ID))
          .value(
              ElementValueOcularAcuities.builder()
                  .rightEye(
                      OcularAcuity.builder()
                          .withoutCorrection(
                              Correction.builder()
                                  .id(new FieldId(ID_1))
                                  .value(2.0)
                                  .build()
                          )
                          .withCorrection(
                              Correction.builder()
                                  .id(new FieldId(ID_2))
                                  .value(1.0)
                                  .build()
                          )
                          .build()
                  )
                  .leftEye(defaultEmptyOcularAcuity())
                  .binocular(defaultEmptyOcularAcuity())
                  .build()
          )
          .build();

      final var actualResult = xmlGeneratorOcularAcuities.generate(emptyOcularAcuities,
          ELEMENT_SPECIFICATION);

      assertAll(
          () -> assertEquals(ELEMENT_ID, actualResult.getFirst().getId()),
          () -> assertEquals(ID_1, actualResult.getFirst().getDelsvar().getFirst().getId()),
          () -> assertEquals(ID_2, actualResult.getFirst().getDelsvar().getLast().getId()),
          () -> assertEquals(2.0,
              actualResult.getFirst().getDelsvar().getFirst().getContent().getFirst()),
          () -> assertEquals(1.0,
              actualResult.getFirst().getDelsvar().getLast().getContent().getFirst())
      );
    }

    @Test
    void shallReturnAnswerForLeftEye() {
      final var answer = new Svar();
      answer.getDelsvar().addAll(
          List.of(
              buildDelsvar(ID_1, 2.0),
              buildDelsvar(ID_2, 1.0)
          )
      );

      final var emptyOcularAcuities = ElementData.builder()
          .id(new ElementId(ELEMENT_ID))
          .value(
              ElementValueOcularAcuities.builder()
                  .leftEye(
                      OcularAcuity.builder()
                          .withoutCorrection(
                              Correction.builder()
                                  .id(new FieldId(ID_1))
                                  .value(2.0)
                                  .build()
                          )
                          .withCorrection(
                              Correction.builder()
                                  .id(new FieldId(ID_2))
                                  .value(1.0)
                                  .build()
                          )
                          .build()
                  )
                  .rightEye(defaultEmptyOcularAcuity())
                  .binocular(defaultEmptyOcularAcuity())
                  .build()
          )
          .build();

      final var actualResult = xmlGeneratorOcularAcuities.generate(emptyOcularAcuities,
          ELEMENT_SPECIFICATION);

      assertAll(
          () -> assertEquals(ELEMENT_ID, actualResult.getFirst().getId()),
          () -> assertEquals(ID_1, actualResult.getFirst().getDelsvar().getFirst().getId()),
          () -> assertEquals(ID_2, actualResult.getFirst().getDelsvar().getLast().getId()),
          () -> assertEquals(2.0,
              actualResult.getFirst().getDelsvar().getFirst().getContent().getFirst()),
          () -> assertEquals(1.0,
              actualResult.getFirst().getDelsvar().getLast().getContent().getFirst())
      );
    }

    @Test
    void shallReturnAnswerForBinocular() {
      final var answer = new Svar();
      answer.getDelsvar().addAll(
          List.of(
              buildDelsvar(ID_1, 2.0),
              buildDelsvar(ID_2, 1.0)
          )
      );

      final var emptyOcularAcuities = ElementData.builder()
          .id(new ElementId(ELEMENT_ID))
          .value(
              ElementValueOcularAcuities.builder()
                  .binocular(
                      OcularAcuity.builder()
                          .withoutCorrection(
                              Correction.builder()
                                  .id(new FieldId(ID_1))
                                  .value(2.0)
                                  .build()
                          )
                          .withCorrection(
                              Correction.builder()
                                  .id(new FieldId(ID_2))
                                  .value(1.0)
                                  .build()
                          )
                          .build()
                  )
                  .rightEye(defaultEmptyOcularAcuity())
                  .leftEye(defaultEmptyOcularAcuity())
                  .build()
          )
          .build();

      final var actualResult = xmlGeneratorOcularAcuities.generate(emptyOcularAcuities,
          ELEMENT_SPECIFICATION);

      assertAll(
          () -> assertEquals(ELEMENT_ID, actualResult.getFirst().getId()),
          () -> assertEquals(ID_1, actualResult.getFirst().getDelsvar().getFirst().getId()),
          () -> assertEquals(ID_2, actualResult.getFirst().getDelsvar().getLast().getId()),
          () -> assertEquals(2.0,
              actualResult.getFirst().getDelsvar().getFirst().getContent().getFirst()),
          () -> assertEquals(1.0,
              actualResult.getFirst().getDelsvar().getLast().getContent().getFirst())
      );
    }
  }

  @Nested
  class TestMultipleOcularAcuities {

    @Test
    void shallReturnAnswerWithAllValuesInCorrectOrder() {
      final var answer = new Svar();
      answer.getDelsvar().addAll(
          List.of(
              buildDelsvar(ID_1, 1.0),
              buildDelsvar(ID_4, 1.0),
              buildDelsvar(ID_2, 1.0),
              buildDelsvar(ID_5, 1.0),
              buildDelsvar(ID_3, 1.0),
              buildDelsvar(ID_6, 1.0)
          )
      );

      final var emptyOcularAcuities = ElementData.builder()
          .id(new ElementId(ELEMENT_ID))
          .value(
              ElementValueOcularAcuities.builder()
                  .rightEye(
                      OcularAcuity.builder()
                          .withoutCorrection(
                              Correction.builder()
                                  .id(new FieldId(ID_1))
                                  .value(1.0)
                                  .build()
                          )
                          .withCorrection(
                              Correction.builder()
                                  .id(new FieldId(ID_4))
                                  .value(1.0)
                                  .build()
                          )
                          .build()
                  )
                  .leftEye(
                      OcularAcuity.builder()
                          .withoutCorrection(
                              Correction.builder()
                                  .id(new FieldId(ID_2))
                                  .value(1.0)
                                  .build()
                          )
                          .withCorrection(
                              Correction.builder()
                                  .id(new FieldId(ID_5))
                                  .value(1.0)
                                  .build()
                          )
                          .build()
                  )
                  .binocular(
                      OcularAcuity.builder()
                          .withoutCorrection(
                              Correction.builder()
                                  .id(new FieldId(ID_3))
                                  .value(1.0)
                                  .build()
                          )
                          .withCorrection(
                              Correction.builder()
                                  .id(new FieldId(ID_6))
                                  .value(1.0)
                                  .build()
                          )
                          .build()
                  )
                  .build()
          )
          .build();

      final var actualResult = xmlGeneratorOcularAcuities.generate(emptyOcularAcuities,
          ELEMENT_SPECIFICATION);

      assertAll(
          () -> assertEquals(ELEMENT_ID, actualResult.getFirst().getId()),
          () -> assertEquals(ID_1, actualResult.getFirst().getDelsvar().get(0).getId()),
          () -> assertEquals(ID_4, actualResult.getFirst().getDelsvar().get(1).getId()),
          () -> assertEquals(ID_2, actualResult.getFirst().getDelsvar().get(2).getId()),
          () -> assertEquals(ID_5, actualResult.getFirst().getDelsvar().get(3).getId()),
          () -> assertEquals(ID_3, actualResult.getFirst().getDelsvar().get(4).getId()),
          () -> assertEquals(ID_6, actualResult.getFirst().getDelsvar().get(5).getId()),
          () -> assertEquals(1.0,
              actualResult.getFirst().getDelsvar().get(0).getContent().getFirst()),
          () -> assertEquals(1.0,
              actualResult.getFirst().getDelsvar().get(1).getContent().getFirst()),
          () -> assertEquals(1.0,
              actualResult.getFirst().getDelsvar().get(2).getContent().getFirst()),
          () -> assertEquals(1.0,
              actualResult.getFirst().getDelsvar().get(3).getContent().getFirst()),
          () -> assertEquals(1.0,
              actualResult.getFirst().getDelsvar().get(4).getContent().getFirst()),
          () -> assertEquals(1.0,
              actualResult.getFirst().getDelsvar().get(5).getContent().getFirst())
      );
    }
  }

  private static Delsvar buildDelsvar(String id, Double value) {
    final var subAnswer = new Delsvar();
    subAnswer.setId(id);
    subAnswer.getContent().add(value);
    return subAnswer;
  }

  private static OcularAcuity defaultEmptyOcularAcuity() {
    return OcularAcuity.builder()
        .withCorrection(
            Correction.builder()
                .build()
        )
        .withoutCorrection(
            Correction.builder()
                .build()
        )
        .build();
  }
}