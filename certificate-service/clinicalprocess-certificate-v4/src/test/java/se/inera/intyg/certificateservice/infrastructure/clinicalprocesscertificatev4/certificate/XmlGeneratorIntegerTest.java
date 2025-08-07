package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueInteger;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;

@ExtendWith(MockitoExtension.class)
class XmlGeneratorIntegerTest {

  private static final String QUESTION_ID = "QUESTION_ID";
  private static final String ANSWER_ID = "ANSWER_ID";
  private static final Integer VALUE = 42;
  private final ElementSpecification ELEMENT_SPECIFICATION = ElementSpecification.builder().build();

  @InjectMocks
  private XmlGeneratorInteger xmlGeneratorInteger;

  @Test
  void shouldMapInteger() {
    final var data = ElementData.builder()
        .id(new ElementId(QUESTION_ID))
        .value(ElementValueInteger.builder()
            .value(VALUE)
            .integerId(new FieldId(ANSWER_ID))
            .build())
        .build();
    final var expectedData = new Svar();
    final var subAnswer = new Delsvar();
    subAnswer.getContent().add(String.valueOf(VALUE));
    expectedData.setId(QUESTION_ID);
    subAnswer.setId(ANSWER_ID);
    expectedData.getDelsvar().add(subAnswer);

    final var response = xmlGeneratorInteger.generate(data, ELEMENT_SPECIFICATION);

    assertAll(
        () -> assertEquals(expectedData.getId(), response.getFirst().getId()),
        () -> assertEquals(expectedData.getDelsvar().getFirst().getId(),
            response.getFirst().getDelsvar().getFirst().getId()),
        () -> assertEquals(expectedData.getDelsvar().getFirst().getContent().getFirst(),
            response.getFirst().getDelsvar().getFirst().getContent().getFirst())
    );
  }

  @Test
  void shouldMapEmptyIfNullValue() {
    final var data = ElementData.builder()
        .value(ElementValueInteger.builder()
            .integerId(new FieldId(ANSWER_ID))
            .build()
        )
        .id(new ElementId(QUESTION_ID))
        .build();

    final var response = xmlGeneratorInteger.generate(data, ELEMENT_SPECIFICATION);

    assertTrue(response.isEmpty());
  }

  @Test
  void shouldMapEmptyIfValueIsNotInteger() {
    final var data = ElementData.builder()
        .value(ElementValueText.builder()
            .text("not an integer")
            .textId(new FieldId(ANSWER_ID))
            .build()
        )
        .id(new ElementId(QUESTION_ID))
        .build();

    final var response = xmlGeneratorInteger.generate(data, ELEMENT_SPECIFICATION);

    assertTrue(response.isEmpty());
  }
}

