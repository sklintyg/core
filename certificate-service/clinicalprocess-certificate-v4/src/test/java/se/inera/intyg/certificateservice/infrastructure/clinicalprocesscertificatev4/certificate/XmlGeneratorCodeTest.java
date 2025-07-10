package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.xml.bind.JAXBElement;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueUnitContactInformation;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDropdownCode;
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

  @InjectMocks
  private XmlGeneratorCode xmlGeneratorCode;

  @ParameterizedTest
  @MethodSource("provideElementSpecifications")
  void shouldMapCode(ElementSpecification elementSpecification) {
    final var data = ElementData.builder()
        .id(new ElementId(QUESTION_ID))
        .value(
            ElementValueCode.builder()
                .codeId(new FieldId("2"))
                .code("CODE_TWO")
                .build()
        )
        .build();

    final var response = xmlGeneratorCode.generate(data, elementSpecification);

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

  @ParameterizedTest
  @MethodSource("provideElementSpecifications")
  void shouldMapEmptyIfNoValue(ElementSpecification elementSpecification) {
    final var data = ElementData.builder()
        .id(new ElementId(QUESTION_ID))
        .build();

    final var response = xmlGeneratorCode.generate(data, elementSpecification);

    assertTrue(response.isEmpty());
  }

  @ParameterizedTest
  @MethodSource("provideElementSpecifications")
  void shouldMapEmptyIfValueIEmpty(ElementSpecification elementSpecification) {
    final var data = ElementData.builder()
        .id(new ElementId(QUESTION_ID))
        .value(
            ElementValueCode.builder()
                .codeId(new FieldId("2"))
                .code("")
                .build()
        )
        .build();

    final var response = xmlGeneratorCode.generate(data, elementSpecification);

    assertTrue(response.isEmpty());
  }

  @ParameterizedTest
  @MethodSource("provideElementSpecifications")
  void shouldMapEmptyIfValueIsEmpty(ElementSpecification elementSpecification) {
    final var data = ElementData.builder()
        .value(ElementValueUnitContactInformation.builder()
            .build()
        )
        .id(new ElementId(QUESTION_ID))
        .build();

    final var response = xmlGeneratorCode.generate(data, elementSpecification);

    assertTrue(response.isEmpty());
  }

  static Stream<Arguments> provideElementSpecifications() {
    final var codes = List.of(
        new ElementConfigurationCode(
            new FieldId("1"),
            "LABEL_ONE",
            new Code("CODE_ONE", "CODE_SYSTEM", "DISPLAY_NAME_ONE")
        ),
        new ElementConfigurationCode(
            new FieldId("2"),
            "LABEL_TWO",
            new Code("CODE_TWO", "CODE_SYSTEM", "DISPLAY_NAME_TWO")
        ));

    return Stream.of(
        Arguments.of(ElementSpecification.builder()
            .configuration(
                ElementConfigurationRadioMultipleCode.builder()
                    .id(new FieldId(ANSWER_ID))
                    .list(codes)
                    .build()
            )
            .build()),
        Arguments.of(
            ElementSpecification.builder()
                .configuration(
                    ElementConfigurationDropdownCode.builder()
                        .id(new FieldId(ANSWER_ID))
                        .list(codes)
                        .build()
                )
                .build()));
  }
}