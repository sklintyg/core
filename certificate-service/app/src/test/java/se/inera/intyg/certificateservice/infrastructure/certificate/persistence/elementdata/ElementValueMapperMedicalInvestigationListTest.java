package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.elementdata;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueMedicalInvestigationList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalInvestigation;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

class ElementValueMapperMedicalInvestigationListTest {

  private ElementValueMapperMedicalInvestigationList elementValueMapper;

  @BeforeEach
  void setUp() {
    elementValueMapper = new ElementValueMapperMedicalInvestigationList();
  }

  @Test
  void shallReturnTrueIfClassMappedIsMappedElementValueMedicalInvestigationList() {
    assertTrue(elementValueMapper.supports(MappedElementValueMedicalInvestigationList.class));
  }

  @Test
  void shallReturnTrueIfClassElementValueIsElementValueMedicalInvestigationList() {
    assertTrue(elementValueMapper.supports(ElementValueMedicalInvestigationList.class));
  }

  @Test
  void shallReturnFalseForUnsupportedClass() {
    assertFalse(elementValueMapper.supports(String.class));
  }

  @Test
  void shallThrowErrorIfWrongMappedValueType() {
    final var mappedElementValue = MappedElementValueCode.builder().build();
    assertThrows(IllegalStateException.class,
        () -> elementValueMapper.toDomain(mappedElementValue));
  }

  @Test
  void shallThrowErrorIfWrongValueType() {
    final var mappedElementValue = ElementValueCode.builder().build();
    assertThrows(IllegalStateException.class,
        () -> elementValueMapper.toMapped(mappedElementValue));
  }


  @Test
  void shallMapToDomain() {
    final var date = LocalDate.now();
    final var text = "text";
    final var code = "code";

    final var expectedValue = ElementValueMedicalInvestigationList.builder()
        .id(new FieldId("ID"))
        .list(
            List.of(
                MedicalInvestigation.builder()
                    .id(new FieldId("MEDICALINVESTIGATION_ID"))
                    .date(ElementValueDate.builder()
                        .dateId(new FieldId("DATE"))
                        .date(date)
                        .build())
                    .informationSource(ElementValueText.builder()
                        .text(text)
                        .textId(new FieldId("TEXT"))
                        .build())
                    .investigationType(ElementValueCode.builder()
                        .codeId(new FieldId("CODE"))
                        .code(code)
                        .build())
                    .build()
            )
        )
        .build();

    final var mappedElementValue = MappedElementValueMedicalInvestigationList.builder()
        .id("ID")
        .list(
            List.of(
                MappedMedicalInvestigation.builder()
                    .id("MEDICALINVESTIGATION_ID")
                    .date(MappedDate.builder()
                        .id("DATE")
                        .date(date)
                        .build())
                    .informationSource(
                        MappedText.builder()
                            .id("TEXT")
                            .text(text)
                            .build()
                    )
                    .investigationType(
                        MappedCode.builder()
                            .id("CODE")
                            .code(code)
                            .build()
                    )
                    .build()
            )
        )
        .build();

    final var actualValue = elementValueMapper.toDomain(
        mappedElementValue
    );

    assertEquals(expectedValue, actualValue);
  }

  @Test
  void shallMapToMapped() {
    final var date = LocalDate.now();
    final var text = "text";
    final var code = "code";

    final var expectedValue = MappedElementValueMedicalInvestigationList.builder()
        .id("ID")
        .list(
            List.of(
                MappedMedicalInvestigation.builder()
                    .id("MEDICALINVESTIGATION_ID")
                    .date(MappedDate.builder()
                        .id("DATE")
                        .date(date)
                        .build())
                    .informationSource(
                        MappedText.builder()
                            .id("TEXT")
                            .text(text)
                            .build()
                    )
                    .investigationType(
                        MappedCode.builder()
                            .id("CODE")
                            .code(code)
                            .build()
                    )
                    .build()
            )
        )
        .build();

    final var elementValue = ElementValueMedicalInvestigationList.builder()
        .id(new FieldId("ID"))
        .list(
            List.of(
                MedicalInvestigation.builder()
                    .id(new FieldId("MEDICALINVESTIGATION_ID"))
                    .date(ElementValueDate.builder()
                        .dateId(new FieldId("DATE"))
                        .date(date)
                        .build())
                    .informationSource(ElementValueText.builder()
                        .text(text)
                        .textId(new FieldId("TEXT"))
                        .build())
                    .investigationType(ElementValueCode.builder()
                        .codeId(new FieldId("CODE"))
                        .code(code)
                        .build())
                    .build()
            )
        )
        .build();

    final var actualValue = elementValueMapper.toMapped(
        elementValue
    );

    assertEquals(expectedValue, actualValue);
  }
}