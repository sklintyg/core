package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueUnitContactInformation;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;

@ExtendWith(MockitoExtension.class)
class XmlGeneratorBooleanTest {

  private static final String QUESTION_ID = "QUESTION_ID";
  private static final String ANSWER_ID = "ANSWER_ID";
  private static final Boolean BOOLEAN = Boolean.TRUE;
  private final ElementSpecification ELEMENT_SPECIFICATION = ElementSpecification.builder().build();

  @InjectMocks
  private XmlGeneratorBoolean xmlGeneratorBoolean;

  @Test
  void shouldMapBoolean() {
    final var data = ElementData.builder()
        .id(new ElementId(QUESTION_ID))
        .value(
            ElementValueBoolean.builder()
                .value(BOOLEAN)
                .booleanId(new FieldId(ANSWER_ID))
                .build()
        )
        .build();
    final var expectedData = new Svar();
    final var subAnswer = new Delsvar();
    subAnswer.getContent().add(BOOLEAN.toString());
    expectedData.setId(QUESTION_ID);
    subAnswer.setId(ANSWER_ID);
    expectedData.getDelsvar().add(subAnswer);

    final var response = xmlGeneratorBoolean.generate(data, ELEMENT_SPECIFICATION);

    assertAll(
        () -> assertEquals(expectedData.getId(), response.get(0).getId()),
        () -> assertEquals(expectedData.getDelsvar().get(0).getId(),
            response.get(0).getDelsvar().get(0).getId()),
        () -> assertEquals(expectedData.getDelsvar().get(0).getContent().get(0),
            response.get(0).getDelsvar().get(0).getContent().get(0))
    );
  }

  @Test
  void shouldMapEmptyIfNoValue() {
    final var data = ElementData.builder()
        .value(
            ElementValueBoolean.builder()
                .booleanId(new FieldId(ANSWER_ID))
                .build()
        )
        .id(new ElementId(QUESTION_ID))
        .build();

    final var response = xmlGeneratorBoolean.generate(data, ELEMENT_SPECIFICATION);

    assertTrue(response.isEmpty());
  }

  @Test
  void shouldMapEmptyIfValueIsNotBoolean() {
    final var data = ElementData.builder()
        .value(
            ElementValueUnitContactInformation.builder()
                .build()
        )
        .id(new ElementId(QUESTION_ID))
        .build();

    final var response = xmlGeneratorBoolean.generate(data, ELEMENT_SPECIFICATION);

    assertTrue(response.isEmpty());
  }
}