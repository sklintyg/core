package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueIcf;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationIcf;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.IcfCodesPropertyType;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;

@ExtendWith(MockitoExtension.class)
class XmlGeneratorIcfValueTest {

  private static final String QUESTION_ID = "QUESTION_ID";
  private static final String ANSWER_ID = "ANSWER_ID";
  private static final String TEXT = "TEXT";
  private static final List<String> VALUES = List.of("code1", "code2");
  private ElementSpecification ELEMENT_SPECIFICATION;

  @InjectMocks
  private XmlGeneratorIcfValue xmlGeneratorIcfValue;

  @BeforeEach
  void setup() {
    ELEMENT_SPECIFICATION = ElementSpecification.builder()
        .configuration(
            ElementConfigurationIcf.builder()
                .id(new FieldId(QUESTION_ID))
                .modalLabel("Modal_Label")
                .collectionsLabel("Collections_Label")
                .placeholder("Placeholder")
                .icfCodesPropertyName(IcfCodesPropertyType.FUNKTIONSNEDSATTNINGAR)
                .build()
        )
        .build();
  }

  @Test
  void shouldMapIcfWithCodes() {
    final var data = ElementData.builder()
        .id(new ElementId(QUESTION_ID))
        .value(ElementValueIcf.builder()
            .id(new FieldId(ANSWER_ID))
            .text(TEXT)
            .icfCodes(VALUES)
            .build())
        .build();

    var expectedText = """
        %s %s
        
        %s
        """.formatted(
        ((ElementConfigurationIcf) ELEMENT_SPECIFICATION.configuration()).collectionsLabel(),
        String.join(" - ", VALUES),
        TEXT);

    final var expectedData = new Svar();
    final var subAnswer = new Delsvar();
    subAnswer.getContent().add(expectedText);
    expectedData.setId(QUESTION_ID);
    subAnswer.setId(ANSWER_ID);
    expectedData.getDelsvar().add(subAnswer);

    final var response = xmlGeneratorIcfValue.generate(data, ELEMENT_SPECIFICATION);

    assertAll(
        () -> assertEquals(expectedData.getId(), response.getFirst().getId()),
        () -> assertEquals(expectedData.getDelsvar().getFirst().getId(),
            response.getFirst().getDelsvar().getFirst().getId()),
        () -> assertEquals(expectedData.getDelsvar().getFirst().getContent().getFirst(),
            response.getFirst().getDelsvar().getFirst().getContent().getFirst())
    );
  }

  @Test
  void shouldMapIcfWithoutCodes() {
    final var data = ElementData.builder()
        .id(new ElementId(QUESTION_ID))
        .value(ElementValueIcf.builder()
            .id(new FieldId(ANSWER_ID))
            .text(TEXT)
            .build())
        .build();

    final var expectedData = new Svar();
    final var subAnswer = new Delsvar();
    subAnswer.getContent().add(TEXT);
    expectedData.setId(QUESTION_ID);
    subAnswer.setId(ANSWER_ID);
    expectedData.getDelsvar().add(subAnswer);

    final var response = xmlGeneratorIcfValue.generate(data, ELEMENT_SPECIFICATION);

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
        .value(ElementValueIcf.builder()
            .id(new FieldId(ANSWER_ID))
            .build()
        )
        .id(new ElementId(QUESTION_ID))
        .build();

    final var response = xmlGeneratorIcfValue.generate(data, ELEMENT_SPECIFICATION);

    assertTrue(response.isEmpty());
  }

  @Test
  void shouldMapEmptyIfValueIsNotIcf() {
    final var data = ElementData.builder()
        .value(ElementValueText.builder()
            .text("not an icf value")
            .textId(new FieldId(ANSWER_ID))
            .build()
        )
        .id(new ElementId(QUESTION_ID))
        .build();

    final var response = xmlGeneratorIcfValue.generate(data, ELEMENT_SPECIFICATION);

    assertTrue(response.isEmpty());
  }

}