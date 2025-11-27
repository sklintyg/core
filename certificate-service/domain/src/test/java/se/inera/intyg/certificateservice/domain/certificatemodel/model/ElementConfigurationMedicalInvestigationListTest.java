package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueMedicalInvestigationList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalInvestigation;
import se.inera.intyg.certificateservice.domain.common.model.Code;

class ElementConfigurationMedicalInvestigationListTest {

  private static final String FIELD_ID = "FIELD_ID";
  private static final String ROW_FIELD_ID = "ROW_FIELD_ID";
  private static final String SECOND_ROW_FIELD_ID = "ROW_2_FIELD_ID";
  private static final String CODE_FIELD_ID = "CODE_FIELD_ID";
  private static final String SECOND_CODE_FIELD_ID = "CODE_2_FIELD_ID";
  private static final String DATE_ID = "FIELD_ID";
  private static final String SECOND_DATE_ID = "FIELD_ID";
  private static final String INFORMATION_SOURCE_ID = "FIELD_ID";
  private static final String SECOND_INFO_ID = "FIELD_ID";

  private static final String LABEL = "LABEL";
  private static final String LABEL_TWO = "LABEL_TWO";
  private static final String CODE_SYSTEM = "CODE_SYSTEM";
  private static final String CODE = "CODE";
  private static final String CODE_TWO = "CODE_TWO";

  private static final ElementConfigurationMedicalInvestigationList CONFIG = ElementConfigurationMedicalInvestigationList.builder()
      .id(new FieldId(FIELD_ID))
      .list(
          List.of(
              MedicalInvestigationConfig.builder()
                  .id(new FieldId(ROW_FIELD_ID))
                  .investigationTypeId(new FieldId(CODE_FIELD_ID))
                  .typeOptions(
                      List.of(
                          new Code(CODE, CODE_SYSTEM, LABEL),
                          new Code(CODE_TWO, CODE_SYSTEM, LABEL_TWO)
                      )
                  )
                  .dateId(new FieldId(DATE_ID))
                  .informationSourceId(new FieldId(INFORMATION_SOURCE_ID))
                  .build(),
              MedicalInvestigationConfig.builder()
                  .id(new FieldId(SECOND_ROW_FIELD_ID))
                  .investigationTypeId(new FieldId(SECOND_CODE_FIELD_ID))
                  .typeOptions(
                      List.of(
                          new Code(CODE, CODE_SYSTEM, LABEL),
                          new Code(CODE_TWO, CODE_SYSTEM, LABEL_TWO)
                      )
                  )
                  .dateId(new FieldId(SECOND_DATE_ID))
                  .informationSourceId(new FieldId(SECOND_INFO_ID))
                  .build()
          )
      )
      .build();

  @Test
  void shallReturnMatchingCode() {
    final var expectedCode = new Code(CODE_TWO, CODE_SYSTEM, LABEL_TWO);

    final var value = ElementValueCode.builder()
        .codeId(new FieldId(SECOND_CODE_FIELD_ID))
        .code(CODE_TWO)
        .build();

    assertEquals(expectedCode, CONFIG.code(value));
  }

  @Test
  void shallThrowIfNoMatchingRow() {
    final var value = ElementValueCode.builder()
        .codeId(new FieldId("NOT_IT"))
        .code(CODE)
        .build();

    assertThrows(IllegalArgumentException.class,
        () -> CONFIG.code(value)
    );
  }

  @Test
  void shallThrowIfNoMatchingCode() {
    final var value = ElementValueCode.builder()
        .codeId(new FieldId(SECOND_CODE_FIELD_ID))
        .code("NOT_IT")
        .build();

    assertThrows(IllegalArgumentException.class,
        () -> CONFIG.code(value)
    );
  }

  @Test
  void shallMapLegacyCodeSynhabiliteringToCurrentCode() {
    final var legacyCode = "SYNHABILITERING";
    final var currentCode = "SYNHABILITERINGEN";
    final var displayName = "Underlag från synhabiliteringen";

    final var configWithLegacySupport = ElementConfigurationMedicalInvestigationList.builder()
        .id(new FieldId(FIELD_ID))
        .list(
            List.of(
                MedicalInvestigationConfig.builder()
                    .id(new FieldId(ROW_FIELD_ID))
                    .investigationTypeId(new FieldId(CODE_FIELD_ID))
                    .typeOptions(
                        List.of(
                            new Code(currentCode, CODE_SYSTEM, displayName)
                        )
                    )
                    .dateId(new FieldId(DATE_ID))
                    .informationSourceId(new FieldId(INFORMATION_SOURCE_ID))
                    .legacyMapping(
                        Map.of("SYNHABILITERING", new Code(currentCode, CODE_SYSTEM, displayName)))
                    .build()
            )
        )
        .build();

    final var valueWithLegacyCode = ElementValueCode.builder()
        .codeId(new FieldId(CODE_FIELD_ID))
        .code(legacyCode)
        .build();

    final var result = configWithLegacySupport.code(valueWithLegacyCode);

    assertEquals(currentCode, result.code());
    assertEquals(displayName, result.displayName());
  }
  
  @Nested
  class EmptyValue {

    @Test
    void shallReturnEmptyValue() {
      final var emptyValue = ElementValueMedicalInvestigationList.builder()
          .id(new FieldId(FIELD_ID))
          .list(
              List.of(
                  MedicalInvestigation.builder()
                      .id(new FieldId(ROW_FIELD_ID))
                      .date(ElementValueDate.builder()
                          .dateId(new FieldId(FIELD_ID))
                          .build())
                      .investigationType(ElementValueCode.builder()
                          .codeId(new FieldId(CODE_FIELD_ID))
                          .build())
                      .informationSource(ElementValueText.builder()
                          .textId(new FieldId(FIELD_ID))
                          .build())
                      .build(),
                  MedicalInvestigation.builder()
                      .id(new FieldId(SECOND_ROW_FIELD_ID))
                      .date(ElementValueDate.builder()
                          .dateId(new FieldId(FIELD_ID))
                          .build())
                      .investigationType(ElementValueCode.builder()
                          .codeId(new FieldId(SECOND_CODE_FIELD_ID))
                          .build())
                      .informationSource(ElementValueText.builder()
                          .textId(new FieldId(FIELD_ID))
                          .build())
                      .build()
              ))
          .build();

      assertEquals(emptyValue, CONFIG.emptyValue());
    }
  }

  @Test
  void shallThrowIfLegacyMappingReturnsEmpty() {
    final var configWithNoMatchingCodes = ElementConfigurationMedicalInvestigationList.builder()
        .id(new FieldId(FIELD_ID))
        .list(
            List.of(
                MedicalInvestigationConfig.builder()
                    .id(new FieldId(ROW_FIELD_ID))
                    .investigationTypeId(new FieldId(CODE_FIELD_ID))
                    .typeOptions(List.of())
                    .dateId(new FieldId(DATE_ID))
                    .informationSourceId(new FieldId(INFORMATION_SOURCE_ID))
                    .build()
            )
        )
        .build();

    final var valueWithUnknownCode = ElementValueCode.builder()
        .codeId(new FieldId(CODE_FIELD_ID))
        .code("UNKNOWN_CODE")
        .build();

    assertThrows(IllegalArgumentException.class,
        () -> configWithNoMatchingCodes.code(valueWithUnknownCode)
    );
  }
}