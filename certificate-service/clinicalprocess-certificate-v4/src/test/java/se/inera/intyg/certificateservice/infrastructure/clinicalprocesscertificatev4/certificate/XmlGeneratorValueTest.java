package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueUnitContactInformation;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;

@ExtendWith(MockitoExtension.class)
class XmlGeneratorValueTest {

  private static final String QUESTION_ID = "QUESTION_ID";
  private static final String ANSWER_ID = "ANSWER_ID";
  private static final LocalDate DATE = LocalDate.now();

  @InjectMocks
  private XmlGeneratorValue xmlGeneratorValue;

  @Test
  void shouldMapDate() {
    final var data = ElementData.builder()
        .id(new ElementId(QUESTION_ID))
        .value(ElementValueDate.builder()
            .date(DATE)
            .dateId(new FieldId(ANSWER_ID))
            .build())
        .build();
    final var expectedData = new Svar();
    final var subAnswer = new Delsvar();
    subAnswer.getContent().add(DATE.toString());
    expectedData.setId(QUESTION_ID);
    subAnswer.setId(ANSWER_ID);
    expectedData.getDelsvar().add(subAnswer);

    final var response = xmlGeneratorValue.generate(List.of(data)).get(0);

    assertAll(
        () -> assertEquals(expectedData.getId(), response.getId()),
        () -> assertEquals(expectedData.getDelsvar().get(0).getId(),
            response.getDelsvar().get(0).getId()),
        () -> assertEquals(expectedData.getDelsvar().get(0).getContent().get(0),
            response.getDelsvar().get(0).getContent().get(0))
    );
  }

  @Test
  void shouldMapEmptyIfNoValue() {
    final var data = ElementData.builder()
        .value(ElementValueDate.builder()
            .dateId(new FieldId(ANSWER_ID))
            .build()
        )
        .id(new ElementId(QUESTION_ID))
        .build();

    final var response = xmlGeneratorValue.generate(List.of(data));

    assertEquals(Collections.emptyList(), response);
  }

  @Test
  void shouldMapEmptyIfValueIsNotSupportedType() {
    final var data = ElementData.builder()
        .value(ElementValueUnitContactInformation.builder()
            .build()
        )
        .id(new ElementId(QUESTION_ID))
        .build();

    final var response = xmlGeneratorValue.generate(List.of(data));

    assertEquals(Collections.emptyList(), response);
  }
}
