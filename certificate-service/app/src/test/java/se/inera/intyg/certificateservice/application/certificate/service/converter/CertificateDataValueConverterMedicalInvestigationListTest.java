package se.inera.intyg.certificateservice.application.certificate.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0005.SYNHABILITERINGEN;

import java.time.LocalDate;
import java.time.Period;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueCode;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueDate;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueMedicalInvestigation;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueMedicalInvestigationList;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueText;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueMedicalInvestigationList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalInvestigation;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationMedicalInvestigationList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.MedicalInvestigationConfig;
import se.inera.intyg.certificateservice.domain.common.model.Code;

class CertificateDataValueConverterMedicalInvestigationListTest {

  private CertificateDataValueConverterMedicalInvestigationList converter;

  private static final ElementSpecification ELEMENT_SPECIFICATION = ElementSpecification.builder()
      .configuration(ElementConfigurationMedicalInvestigationList.builder()
          .id(new FieldId("ID"))
          .name("NAME")
          .description("DESCRIPTION")
          .typeText("TYPE_TEXT")
          .dateText("DATE_TEXT")
          .header("HEADER")
          .label("LABEL")
          .informationSourceDescription("INFORMATION_SOURCE_DESCRIPTION")
          .informationSourceText("INFORMATION_SOURCE_TEXT")
          .list(List.of(
              MedicalInvestigationConfig.builder()
                  .id(new FieldId("ID_1"))
                  .investigationTypeId(new FieldId("TYPE_ID_1"))
                  .informationSourceId(new FieldId("SOURCE_ID_1"))
                  .dateId(new FieldId("DATE_ID_1"))
                  .typeOptions(List.of(
                      new Code("CODE_1", "CODE_SYSTEM_1", "DISPLAY_NAME_1")
                  ))
                  .max(Period.ofDays(1))
                  .min(Period.ofDays(1))
                  .build(),
              MedicalInvestigationConfig.builder()
                  .id(new FieldId("ID_2"))
                  .investigationTypeId(new FieldId("TYPE_ID_2"))
                  .informationSourceId(new FieldId("SOURCE_ID_2"))
                  .dateId(new FieldId("DATE_ID_2"))
                  .typeOptions(List.of(
                      new Code("CODE_2", "CODE_SYSTEM_2", "DISPLAY_NAME_2")
                  ))
                  .max(Period.ofDays(2))
                  .min(Period.ofDays(2))
                  .build()
          ))
          .build())
      .build();
  private static final ElementConfigurationMedicalInvestigationList CONFIGURATION = (ElementConfigurationMedicalInvestigationList) ELEMENT_SPECIFICATION.configuration();
  private static final CertificateDataValueMedicalInvestigationList EXPECTED_EMPTY_VALUE = CertificateDataValueMedicalInvestigationList.builder()
      .id("ID")
      .list(
          List.of(
              CertificateDataValueMedicalInvestigation.builder()
                  .id(CONFIGURATION.list().getFirst().id().value())
                  .date(
                      CertificateDataValueDate.builder()
                          .id(CONFIGURATION.list().getFirst().dateId().value())
                          .build()
                  )
                  .investigationType(
                      CertificateDataValueCode.builder()
                          .id(CONFIGURATION.list().getFirst().investigationTypeId().value())
                          .build()
                  )
                  .informationSource(
                      CertificateDataValueText.builder()
                          .id(CONFIGURATION.list().getFirst().informationSourceId().value())
                          .build()
                  )
                  .build(),
              CertificateDataValueMedicalInvestigation.builder()
                  .id(CONFIGURATION.list().get(1).id().value())
                  .date(
                      CertificateDataValueDate.builder()
                          .id(CONFIGURATION.list().get(1).dateId().value())
                          .build()
                  )
                  .investigationType(
                      CertificateDataValueCode.builder()
                          .id(CONFIGURATION.list().get(1).investigationTypeId().value())
                          .build()
                  )
                  .informationSource(
                      CertificateDataValueText.builder()
                          .id(CONFIGURATION.list().get(1).informationSourceId().value())
                          .build()
                  )
                  .build()
          )
      )
      .build();

  private static final ElementValueMedicalInvestigationList ELEMENT_VALUE =
      ElementValueMedicalInvestigationList.builder()
          .id(new FieldId("LIST_ID"))
          .list(List.of(
              MedicalInvestigation.builder()
                  .id(new FieldId("MEDICAL_INVESTIGATION_ID"))
                  .informationSource(ElementValueText.builder()
                      .textId(new FieldId("TEXT_ID"))
                      .text("TEXT")
                      .build())
                  .date(ElementValueDate.builder()
                      .dateId(new FieldId("DATE_ID"))
                      .date(LocalDate.now())
                      .build())
                  .investigationType(ElementValueCode.builder()
                      .codeId(new FieldId("CODE_ID"))
                      .code("CODE")
                      .build())
                  .build()
          ))
          .build();

  @BeforeEach
  void setUp() {
    converter = new CertificateDataValueConverterMedicalInvestigationList();
  }

  @Test
  void shouldReturnCorrectType() {
    assertEquals(ElementType.MEDICAL_INVESTIGATION_LIST, converter.getType());
  }

  @Test
  void shouldThrowExceptionIfInvalidValueType() {
    final var invalidType = ElementValueDate.builder().build();

    assertThrows(IllegalStateException.class,
        () -> converter.convert(ELEMENT_SPECIFICATION, invalidType)
    );
  }

  @Test
  void shouldThrowExceptionIfInvalidConfigurationType() {
    final var invalidConfig = ElementSpecification.builder()
        .configuration(ElementConfigurationDate.builder().build())
        .build();

    assertThrows(IllegalStateException.class,
        () -> converter.convert(invalidConfig, ELEMENT_VALUE)
    );
  }

  @Test
  void shouldReturnEmptyValueIfValueIsNull() {
    final var result = converter.convert(ELEMENT_SPECIFICATION, null);

    assertEquals(EXPECTED_EMPTY_VALUE, result);
  }

  @Test
  void shouldReturnEmptyListIfValueListIsNull() {
    final var elementValue = ElementValueMedicalInvestigationList.builder()
        .id(new FieldId("LIST_ID"))
        .build();

    final var result = converter.convert(ELEMENT_SPECIFICATION, elementValue);

    assertEquals(EXPECTED_EMPTY_VALUE, result);
  }

  @Test
  void shouldReturnEmptyListIfValueListIsEmpty() {
    final var elementValue = ElementValueMedicalInvestigationList.builder()
        .id(new FieldId("LIST_ID"))
        .list(Collections.emptyList())
        .build();

    final var result = converter.convert(ELEMENT_SPECIFICATION, elementValue);

    assertEquals(EXPECTED_EMPTY_VALUE, result);
  }

  @Test
  void shouldSetId() {
    final var result = converter.convert(ELEMENT_SPECIFICATION, ELEMENT_VALUE);
    final var medicalInvestigationResult = (CertificateDataValueMedicalInvestigationList) result;

    assertEquals("ID", medicalInvestigationResult.getId());
  }

  @Test
  void shouldSetMedicalInvestigationId() {
    final var result = converter.convert(ELEMENT_SPECIFICATION, ELEMENT_VALUE);
    final var medicalInvestigationResult = (CertificateDataValueMedicalInvestigationList) result;

    assertEquals("ID_1", medicalInvestigationResult.getList().getFirst().getId());
  }

  @Test
  void shouldSetDateId() {
    final var result = converter.convert(ELEMENT_SPECIFICATION, ELEMENT_VALUE);
    final var medicalInvestigationResult = (CertificateDataValueMedicalInvestigationList) result;

    assertEquals("DATE_ID_1", medicalInvestigationResult.getList().getFirst().getDate().getId());
  }

  @Test
  void shouldSetDateValue() {
    final var result = converter.convert(ELEMENT_SPECIFICATION, ELEMENT_VALUE);
    final var medicalInvestigationResult = (CertificateDataValueMedicalInvestigationList) result;

    assertEquals(LocalDate.now(),
        medicalInvestigationResult.getList().getFirst().getDate().getDate());
  }

  @Test
  void shouldSetInformationSourceId() {
    final var result = converter.convert(ELEMENT_SPECIFICATION, ELEMENT_VALUE);
    final var medicalInvestigationResult = (CertificateDataValueMedicalInvestigationList) result;

    assertEquals("SOURCE_ID_1",
        medicalInvestigationResult.getList().getFirst().getInformationSource().getId());
  }

  @Test
  void shouldSetInformationSourceValue() {
    final var result = converter.convert(ELEMENT_SPECIFICATION, ELEMENT_VALUE);
    final var medicalInvestigationResult = (CertificateDataValueMedicalInvestigationList) result;

    assertEquals("TEXT",
        medicalInvestigationResult.getList().getFirst().getInformationSource().getText());
  }

  @Test
  void shouldSetInvestigationTypeId() {
    final var result = converter.convert(ELEMENT_SPECIFICATION, ELEMENT_VALUE);
    final var medicalInvestigationResult = (CertificateDataValueMedicalInvestigationList) result;

    assertEquals("TYPE_ID_1",
        medicalInvestigationResult.getList().getFirst().getInvestigationType().getId());
  }

  @Test
  void shouldSetInvestigationTypeValue() {
    final var result = converter.convert(ELEMENT_SPECIFICATION, ELEMENT_VALUE);
    final var medicalInvestigationResult = (CertificateDataValueMedicalInvestigationList) result;

    assertEquals("CODE",
        medicalInvestigationResult.getList().getFirst().getInvestigationType().getCode());
  }

  @Test
  void shouldMapLegacyCodeToCurrentCode() {
    final var legacyCode = "SYNHABILITERING";
    final var currentCode = "SYNHABILITERINGEN";

    final var elementSpecification = ElementSpecification.builder()
        .configuration(ElementConfigurationMedicalInvestigationList.builder()
            .id(new FieldId("ID"))
            .list(List.of(
                MedicalInvestigationConfig.builder()
                    .id(new FieldId("ID_1"))
                    .investigationTypeId(new FieldId("TYPE_ID_1"))
                    .informationSourceId(new FieldId("SOURCE_ID_1"))
                    .dateId(new FieldId("DATE_ID_1"))
                    .typeOptions(List.of(
                        new Code(currentCode, "CODE_SYSTEM", "Display Name")
                    ))
                    .legacyMapping(Map.of(legacyCode, SYNHABILITERINGEN))
                    .build()
            ))
            .build())
        .build();

    final var elementValue = ElementValueMedicalInvestigationList.builder()
        .id(new FieldId("ID"))
        .list(List.of(
            MedicalInvestigation.builder()
                .id(new FieldId("ID_1"))
                .investigationType(ElementValueCode.builder()
                    .codeId(new FieldId("TYPE_ID_1"))
                    .code(legacyCode)
                    .build())
                .date(ElementValueDate.builder()
                    .dateId(new FieldId("DATE_ID_1"))
                    .date(LocalDate.now())
                    .build())
                .informationSource(ElementValueText.builder()
                    .textId(new FieldId("SOURCE_ID_1"))
                    .text("Source")
                    .build())
                .build()
        ))
        .build();

    final var result = converter.convert(elementSpecification, elementValue);
    final var medicalInvestigationResult = (CertificateDataValueMedicalInvestigationList) result;

    assertEquals(currentCode,
        medicalInvestigationResult.getList().getFirst().getInvestigationType().getCode());
  }
}