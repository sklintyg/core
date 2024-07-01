package se.inera.intyg.certificateservice.application.certificate.service.converter;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.FK3226_CERTIFICATE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.FK7210_CERTIFICATE;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfigMedicalInvestigation;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationMedicalInvestigationList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.MedicalInvestigationConfig;
import se.inera.intyg.certificateservice.domain.common.model.Code;

class CertificateDataMedicalInvestigationConfigConverterTest {

  private CertificateDataMedicalInvestigationConfigConverter converter;

  private static final ElementSpecification ELEMENT_SPECIFICATION = ElementSpecification.builder()
      .configuration(ElementConfigurationMedicalInvestigationList.builder()
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

  @BeforeEach
  void setUp() {
    converter = new CertificateDataMedicalInvestigationConfigConverter();
  }

  @Test
  void shouldThrowExceptionIfWrongClass() {
    final var elementSpecification = ElementSpecification.builder()
        .configuration(
            ElementConfigurationDate.builder().build()
        )
        .build();

    assertThrows(IllegalStateException.class,
        () -> converter.convert(elementSpecification,
            FK7210_CERTIFICATE)
    );
  }

  @Test
  void shouldReturnMedicalInvestigationType() {
    assertEquals(ElementType.MEDICAL_INVESTIGATION_LIST, converter.getType());
  }

  @Test
  void shouldSetCorrectText() {
    final CertificateDataConfigMedicalInvestigation result = (CertificateDataConfigMedicalInvestigation)
        converter.convert(ELEMENT_SPECIFICATION, FK3226_CERTIFICATE);

    assertEquals(ELEMENT_SPECIFICATION.configuration().name(), result.getText());
  }

  @Test
  void shouldSetCorrectDescription() {
    final CertificateDataConfigMedicalInvestigation result = (CertificateDataConfigMedicalInvestigation)
        converter.convert(ELEMENT_SPECIFICATION, FK3226_CERTIFICATE);

    assertEquals(ELEMENT_SPECIFICATION.configuration().description(), result.getDescription());
  }

  @Test
  void shouldSetCorrectHeader() {
    final CertificateDataConfigMedicalInvestigation result = (CertificateDataConfigMedicalInvestigation)
        converter.convert(ELEMENT_SPECIFICATION, FK3226_CERTIFICATE);

    assertEquals(ELEMENT_SPECIFICATION.configuration().header(), result.getHeader());
  }

  @Test
  void shouldSetCorrectLabel() {
    final CertificateDataConfigMedicalInvestigation result = (CertificateDataConfigMedicalInvestigation)
        converter.convert(ELEMENT_SPECIFICATION, FK3226_CERTIFICATE);

    assertEquals(ELEMENT_SPECIFICATION.configuration().label(), result.getLabel());
  }

  @Test
  void shouldSetCorrectInformationSourceText() {
    final CertificateDataConfigMedicalInvestigation result = (CertificateDataConfigMedicalInvestigation)
        converter.convert(ELEMENT_SPECIFICATION, FK3226_CERTIFICATE);

    final var configuration = (ElementConfigurationMedicalInvestigationList) ELEMENT_SPECIFICATION.configuration();

    assertEquals(configuration.informationSourceText(), result.getInformationSourceText());
  }

  @Test
  void shouldSetCorrectInformationSourceDescription() {
    final CertificateDataConfigMedicalInvestigation result = (CertificateDataConfigMedicalInvestigation)
        converter.convert(ELEMENT_SPECIFICATION, FK3226_CERTIFICATE);

    final var configuration = (ElementConfigurationMedicalInvestigationList) ELEMENT_SPECIFICATION.configuration();

    assertEquals(configuration.informationSourceDescription(),
        result.getInformationSourceDescription());
  }

  @Test
  void shouldSetCorrectValuesForList() {
    final CertificateDataConfigMedicalInvestigation result = (CertificateDataConfigMedicalInvestigation)
        converter.convert(ELEMENT_SPECIFICATION, FK3226_CERTIFICATE);

    final var listItem1 = result.getList().get(0);
    final var listItem2 = result.getList().get(1);

    assertAll(
        () -> assertEquals("TYPE_ID_1", listItem1.getInvestigationTypeId()),
        () -> assertEquals("SOURCE_ID_1", listItem1.getInformationSourceId()),
        () -> assertEquals("DATE_ID_1", listItem1.getDateId()),
        () -> assertEquals("CODE_1", listItem1.getTypeOptions().get(0).getId()),
        () -> assertEquals("CODE_1", listItem1.getTypeOptions().get(0).getCode()),
        () -> assertEquals("DISPLAY_NAME_1", listItem1.getTypeOptions().get(0).getLabel()),
        () -> assertEquals(LocalDate.now().plusDays(1), listItem1.getMaxDate()),
        () -> assertEquals(LocalDate.now().plusDays(1), listItem1.getMinDate()),

        () -> assertEquals("TYPE_ID_2", listItem2.getInvestigationTypeId()),
        () -> assertEquals("SOURCE_ID_2", listItem2.getInformationSourceId()),
        () -> assertEquals("DATE_ID_2", listItem2.getDateId()),
        () -> assertEquals("CODE_2", listItem2.getTypeOptions().get(0).getId()),
        () -> assertEquals("CODE_2", listItem2.getTypeOptions().get(0).getCode()),
        () -> assertEquals("DISPLAY_NAME_2", listItem2.getTypeOptions().get(0).getLabel()),
        () -> assertEquals(LocalDate.now().plusDays(2), listItem2.getMaxDate()),
        () -> assertEquals(LocalDate.now().plusDays(2), listItem2.getMinDate())
    );
  }
}
