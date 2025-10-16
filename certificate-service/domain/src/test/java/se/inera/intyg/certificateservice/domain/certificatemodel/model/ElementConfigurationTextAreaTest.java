package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValueText;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueIcf;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;

class ElementConfigurationTextAreaTest {

  private final static String EMPTY_TEXT = "Ej angivet";

  @Test
  void shouldReturnSimplifiedValue() {
    final var text = "Test text for this test";
    final var value = ElementValueText.builder()
        .text(text)
        .build();

    final var config = ElementConfigurationTextArea.builder()
        .build();

    assertEquals(
        text,
        ((ElementSimplifiedValueText) config.simplified(value).get()).text()
    );
  }

  @Test
  void shouldReturnEmptyOptionalForNull() {
    final var text = "";
    final var value = ElementValueText.builder()
        .text(text)
        .build();

    final var config = ElementConfigurationTextArea.builder()
        .build();

    assertEquals(
        EMPTY_TEXT,
        ((ElementSimplifiedValueText) config.simplified(value).get()).text()
    );
  }

  @Nested
  class TestConvert {

    @Test
    void shouldReturnSameElementDataIfValueAlreadyText() {
      final var original = ElementData.builder()
          .id(new ElementId("E1"))
          .value(ElementValueText.builder().textId(new FieldId("F1")).text("Already text").build())
          .build();

      final var spec = ElementSpecification.builder()
          .id(new ElementId("E1"))
          .configuration(ElementConfigurationTextArea.builder().id(new FieldId("F1")).build())
          .build();

      final var config = ElementConfigurationTextArea.builder().id(new FieldId("F1")).build();

      final var result = config.convert(original, spec).get();

      assertSame(original, result);
    }

    @Test
    void shouldConvertFromIcfWithoutCodesSetTextId() {
      final var fieldId = new FieldId("F2");
      final var icfConfig = ElementConfigurationIcf.builder()
          .id(fieldId)
          .collectionsLabel("Collections")
          .build();
      final var spec = ElementSpecification.builder()
          .id(new ElementId("E2"))
          .configuration(icfConfig)
          .build();
      final var icfValue = ElementValueIcf.builder()
          .id(fieldId)
          .text("Plain text")
          .build();
      final var elementData = ElementData.builder()
          .id(new ElementId("E2"))
          .value(icfValue)
          .build();

      final var textAreaConfig = ElementConfigurationTextArea.builder().id(fieldId).build();

      final var converted = textAreaConfig.convert(elementData, spec).get();

      assertEquals(fieldId, ((ElementValueText) converted.value()).textId());
    }

    @Test
    void shouldConvertFromIcfWithoutCodesSetText() {
      final var fieldId = new FieldId("F2b");
      final var icfConfig = ElementConfigurationIcf.builder()
          .id(fieldId)
          .collectionsLabel("Collections")
          .build();
      final var spec = ElementSpecification.builder()
          .id(new ElementId("E2b"))
          .configuration(icfConfig)
          .build();
      final var textContent = "Plain text";
      final var icfValue = ElementValueIcf.builder()
          .id(fieldId)
          .text(textContent)
          .build();
      final var elementData = ElementData.builder()
          .id(new ElementId("E2b"))
          .value(icfValue)
          .build();

      final var textAreaConfig = ElementConfigurationTextArea.builder().id(fieldId).build();

      final var converted = textAreaConfig.convert(elementData, spec).get();

      assertEquals(textContent, ((ElementValueText) converted.value()).text());
    }

    @Test
    void shouldConvertFromIcfWithCodesSetFormattedText() {
      final var fieldId = new FieldId("F3");
      final var icfConfig = ElementConfigurationIcf.builder()
          .id(fieldId)
          .collectionsLabel("Samlingar")
          .build();
      final var spec = ElementSpecification.builder()
          .id(new ElementId("E3"))
          .configuration(icfConfig)
          .build();
      final var icfValue = ElementValueIcf.builder()
          .id(fieldId)
          .text("Description")
          .icfCodes(List.of("A1", "B2"))
          .build();
      final var elementData = ElementData.builder()
          .id(new ElementId("E3"))
          .value(icfValue)
          .build();

      final var textAreaConfig = ElementConfigurationTextArea.builder().id(fieldId).build();

      final var converted = textAreaConfig.convert(elementData, spec).get();

      final var expected = "Samlingar A1 - B2\n\nDescription\n"; // format ends with newline due to text block
      assertEquals(expected, ((ElementValueText) converted.value()).text());
    }

    @Test
    void shouldConvertFromIcfWithCodesSetTextId() {
      final var fieldId = new FieldId("F3b");
      final var icfConfig = ElementConfigurationIcf.builder()
          .id(fieldId)
          .collectionsLabel("Samlingar")
          .build();
      final var spec = ElementSpecification.builder()
          .id(new ElementId("E3b"))
          .configuration(icfConfig)
          .build();
      final var icfValue = ElementValueIcf.builder()
          .id(fieldId)
          .text("Description")
          .icfCodes(List.of("A1", "B2"))
          .build();
      final var elementData = ElementData.builder()
          .id(new ElementId("E3b"))
          .value(icfValue)
          .build();

      final var textAreaConfig = ElementConfigurationTextArea.builder().id(fieldId).build();

      final var converted = textAreaConfig.convert(elementData, spec).get();

      assertEquals(fieldId, ((ElementValueText) converted.value()).textId());
    }

    @Test
    void shouldReturnEmptyIfIcfButSpecificationNotIcfConfiguration() {
      final var fieldId = new FieldId("F4");
      final var icfValue = ElementValueIcf.builder()
          .id(fieldId)
          .text("Some text")
          .build();
      final var elementData = ElementData.builder()
          .id(new ElementId("E4"))
          .value(icfValue)
          .build();

      final var wrongSpec = ElementSpecification.builder()
          .id(new ElementId("E4"))
          .configuration(ElementConfigurationTextArea.builder().id(new FieldId("OTHER"))
              .build())
          .build();

      final var textAreaConfig = ElementConfigurationTextArea.builder().id(fieldId).build();

      final var result = textAreaConfig.convert(elementData, wrongSpec);

      assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnEmptyIfUnsupportedValueType() {
      final var elementData = ElementData.builder()
          .id(new ElementId("E5"))
          .value(ElementValueCode.builder().code("CODE").codeId(new FieldId("CID"))
              .build())
          .build();

      final var icfSpec = ElementSpecification.builder()
          .id(new ElementId("E5"))
          .configuration(ElementConfigurationIcf.builder().id(new FieldId("FICF")).build())
          .build();

      final var textAreaConfig = ElementConfigurationTextArea.builder().id(new FieldId("FICF"))
          .build();

      final var result = textAreaConfig.convert(elementData, icfSpec);

      assertTrue(result.isEmpty());
    }
  }
}