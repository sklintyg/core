package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.xml.bind.JAXBElement;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueUnitContactInformation;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioMultipleCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.common.model.Code;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.CVType;

@ExtendWith(MockitoExtension.class)
class XmlGeneratorCodeTest {

  private static final String QUESTION_ID = "QUESTION_ID";
  private static final String ANSWER_ID = "ANSWER_ID";
  private final ElementSpecification ELEMENT_SPECIFICATION = ElementSpecification.builder()
      .configuration(
          ElementConfigurationRadioMultipleCode.builder()
              .id(new FieldId(ANSWER_ID))
              .list(
                  List.of(
                      new ElementConfigurationCode(
                          new FieldId("1"),
                          "LABEL_ONE",
                          new Code("CODE_ONE", "CODE_SYSTEM", "DISPLAY_NAME_ONE")
                      ),
                      new ElementConfigurationCode(
                          new FieldId("2"),
                          "LABEL_TWO",
                          new Code("CODE_TWO", "CODE_SYSTEM", "DISPLAY_NAME_TWO")
                      )
                  )
              )
              .build()
      )
      .build();

  @InjectMocks
  private XmlGeneratorCode xmlGeneratorCode;

  @Test
  void shouldMapCode() {
    final var data = ElementData.builder()
        .id(new ElementId(QUESTION_ID))
        .value(
            ElementValueCode.builder()
                .codeId(new FieldId("2"))
                .code("CODE_TWO")
                .build()
        )
        .build();

    final var response = xmlGeneratorCode.generate(data, ELEMENT_SPECIFICATION);

    final var svar = response.get(0);
    final var delsvar = svar.getDelsvar();
    final var delsvarCode = delsvar.get(0);
    final var jaxbElement = (JAXBElement<CVType>) delsvarCode.getContent().get(0);
    final var cvType = jaxbElement.getValue();

    assertAll(
        () -> assertEquals(1, response.size()),
        () -> assertEquals(QUESTION_ID, svar.getId()),
        () -> assertEquals(1, delsvar.size()),
        () -> assertEquals(ANSWER_ID, delsvarCode.getId()),
        () -> assertEquals("CODE_TWO", cvType.getCode()),
        () -> assertEquals("CODE_SYSTEM", cvType.getCodeSystem()),
        () -> assertEquals("DISPLAY_NAME_TWO", cvType.getDisplayName())
    );
  }

  @Test
  void shallThrowIfIncorrectConfiguration() {
    final var data = ElementData.builder()
        .id(new ElementId(QUESTION_ID))
        .value(
            ElementValueCode.builder()
                .codeId(new FieldId("2"))
                .code("CODE_TWO")
                .build()
        )
        .build();

    final var elementSpecification = ElementSpecification.builder()
        .configuration(
            ElementConfigurationDate.builder().build()
        )
        .build();

    assertThrows(IllegalArgumentException.class,
        () -> xmlGeneratorCode.generate(data, elementSpecification)
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

    final var response = xmlGeneratorCode.generate(data, ELEMENT_SPECIFICATION);

    assertTrue(response.isEmpty());
  }

  @Test
  void shouldMapEmptyIfValueIsNotDate() {
    final var data = ElementData.builder()
        .value(ElementValueUnitContactInformation.builder()
            .build()
        )
        .id(new ElementId(QUESTION_ID))
        .build();

    final var response = xmlGeneratorCode.generate(data, ELEMENT_SPECIFICATION);

    assertTrue(response.isEmpty());
  }
}