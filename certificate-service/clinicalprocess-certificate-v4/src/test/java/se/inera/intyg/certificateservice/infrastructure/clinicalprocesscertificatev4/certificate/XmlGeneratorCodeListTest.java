package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.xml.bind.JAXBElement;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCodeList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueUnitContactInformation;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxMultipleCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.common.model.Code;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.CVType;

@ExtendWith(MockitoExtension.class)
class XmlGeneratorCodeListTest {

  private static final String QUESTION_ID = "QUESTION_ID";
  private static final String VALUE_ID = "ANSWER_ID";
  private static final String CODE_ID_ONE = "CODE_ID_ONE";
  private static final String CODE_ONE = "CODE_ONE";

  private static final String CODE_ID_TWO = "CODE_ID_TWO";
  private static final String CODE_TWO = "CODE_TWO";

  private static ElementData data;
  private ElementSpecification elementSpecification;

  @InjectMocks
  private XmlGeneratorCodeList xmlGenerator;

  @Nested
  class TestOneCode {

    @BeforeEach
    void setup() {
      data = ElementData.builder()
          .id(new ElementId(QUESTION_ID))
          .value(
              ElementValueCodeList.builder()
                  .id(new FieldId(VALUE_ID))
                  .list(
                      List.of(
                          ElementValueCode.builder()
                              .codeId(new FieldId(CODE_ID_ONE))
                              .code(CODE_ONE)
                              .build()
                      )
                  )
                  .build()
          )
          .build();
      elementSpecification = ElementSpecification.builder()
          .configuration(
              ElementConfigurationCheckboxMultipleCode.builder()
                  .id(new FieldId(VALUE_ID))
                  .list(
                      List.of(
                          new ElementConfigurationCode(
                              new FieldId(CODE_ID_ONE),
                              "label",
                              new Code(
                                  "CODE",
                                  "CODE_SYSTEM",
                                  "DISPLAY_NAME"
                              )
                          ))
                  )
                  .build()
          )
          .build();
    }

    @Test
    void shallMapSvar() {
      final var response = xmlGenerator.generate(data, elementSpecification);

      final var first = response.getFirst();
      assertAll(
          () -> assertEquals(1, response.size()),
          () -> assertEquals(QUESTION_ID, first.getId()),
          () -> assertEquals(1, first.getInstans())
      );
    }

    @Test
    void shallMapDelsvarForCode() {
      final var response = xmlGenerator.generate(data, elementSpecification);

      final var delsvar = response.getFirst().getDelsvar();
      final var delsvarCode = response.getFirst().getDelsvar().getFirst();
      final var jaxbElement = (JAXBElement<CVType>) delsvarCode.getContent().getFirst();
      final var cvType = jaxbElement.getValue();

      assertAll(
          () -> assertEquals(1, delsvar.size()),
          () -> assertEquals(VALUE_ID, delsvarCode.getId()),
          () -> assertEquals("CODE", cvType.getCode()),
          () -> assertEquals("CODE_SYSTEM", cvType.getCodeSystem()),
          () -> assertEquals("DISPLAY_NAME", cvType.getDisplayName())
      );
    }
  }

  @Nested
  class TestMultipleCodes {

    @BeforeEach
    void setup() {
      data = ElementData.builder()
          .id(new ElementId(QUESTION_ID))
          .value(
              ElementValueCodeList.builder()
                  .id(new FieldId(VALUE_ID))
                  .list(
                      List.of(
                          ElementValueCode.builder()
                              .codeId(new FieldId(CODE_ID_ONE))
                              .code(CODE_ONE)
                              .build(),
                          ElementValueCode.builder()
                              .codeId(new FieldId(CODE_ID_TWO))
                              .code(CODE_TWO)
                              .build()
                      )
                  )
                  .build()
          )
          .build();

      elementSpecification = ElementSpecification.builder()
          .configuration(
              ElementConfigurationCheckboxMultipleCode.builder()
                  .id(new FieldId(VALUE_ID))
                  .list(
                      List.of(
                          new ElementConfigurationCode(
                              new FieldId(CODE_ID_ONE),
                              "label",
                              new Code(
                                  "CODE",
                                  "CODE_SYSTEM",
                                  "DISPLAY_NAME"
                              )
                          ),
                          new ElementConfigurationCode(
                              new FieldId(CODE_ID_TWO),
                              "labelTwo",
                              new Code(
                                  "CODE_TWO",
                                  "CODE_SYSTEM",
                                  "DISPLAY_NAME_TWO"
                              )
                          )
                      )
                  )
                  .build()
          )
          .build();
    }

    @Test
    void shallMapFirstSvar() {
      final var response = xmlGenerator.generate(data, elementSpecification);

      final var first = response.getFirst();
      assertAll(
          () -> assertEquals(2, response.size()),
          () -> assertEquals(QUESTION_ID, first.getId()),
          () -> assertEquals(1, first.getInstans())
      );
    }

    @Test
    void shallMapSecondSvar() {
      final var response = xmlGenerator.generate(data, elementSpecification);

      final var first = response.get(1);
      assertAll(
          () -> assertEquals(2, response.size()),
          () -> assertEquals(QUESTION_ID, first.getId()),
          () -> assertEquals(2, first.getInstans())
      );
    }

    @Test
    void shallMapDelsvarForFirstCode() {
      final var response = xmlGenerator.generate(data, elementSpecification);

      final var delsvar = response.getFirst().getDelsvar();
      final var delsvarCode = delsvar.getFirst();
      final var jaxbElement = (JAXBElement<CVType>) delsvarCode.getContent().getFirst();
      final var cvType = jaxbElement.getValue();

      assertAll(
          () -> assertEquals(1, delsvar.size()),
          () -> assertEquals(VALUE_ID, delsvarCode.getId()),
          () -> assertEquals("CODE", cvType.getCode()),
          () -> assertEquals("CODE_SYSTEM", cvType.getCodeSystem()),
          () -> assertEquals("DISPLAY_NAME", cvType.getDisplayName())
      );
    }

    @Test
    void shallMapDelsvarForSecondCode() {
      final var response = xmlGenerator.generate(data, elementSpecification);

      final var delsvar = response.get(1).getDelsvar();
      final var delsvarCode = delsvar.getFirst();
      final var jaxbElement = (JAXBElement<CVType>) delsvarCode.getContent().getFirst();
      final var cvType = jaxbElement.getValue();

      assertAll(
          () -> assertEquals(1, delsvar.size()),
          () -> assertEquals(VALUE_ID, delsvarCode.getId()),
          () -> assertEquals("CODE_TWO", cvType.getCode()),
          () -> assertEquals("CODE_SYSTEM", cvType.getCodeSystem()),
          () -> assertEquals("DISPLAY_NAME_TWO", cvType.getDisplayName())
      );
    }
  }

  @Test
  void shallMapEmptyIfNoValue() {
    final var data = ElementData.builder()
        .id(new ElementId(QUESTION_ID))
        .value(
            ElementValueCodeList.builder()
                .id(new FieldId(VALUE_ID))
                .build()
        )
        .build();

    final var response = xmlGenerator.generate(data, elementSpecification);

    assertTrue(response.isEmpty());
  }

  @Test
  void shallThrowIfIncorrectConfiguration() {
    final var data = ElementData.builder()
        .id(new ElementId(QUESTION_ID))
        .value(
            ElementValueCodeList.builder()
                .id(new FieldId(VALUE_ID))
                .list(
                    List.of(
                        ElementValueCode.builder()
                            .codeId(new FieldId(CODE_ID_ONE))
                            .code(CODE_ONE)
                            .build()
                    )
                )
                .build()
        )
        .build();

    elementSpecification = ElementSpecification.builder()
        .configuration(
            ElementConfigurationDate.builder().build()
        )
        .build();

    assertThrows(IllegalArgumentException.class,
        () -> xmlGenerator.generate(data, elementSpecification)
    );
  }

  @Test
  void shallMapEmptyIfValueIsNotCodeRangeList() {
    final var data = ElementData.builder()
        .id(new ElementId(QUESTION_ID))
        .value(
            ElementValueUnitContactInformation.builder()
                .build()
        )
        .build();

    final var response = xmlGenerator.generate(data, elementSpecification);

    assertTrue(response.isEmpty());
  }

}
