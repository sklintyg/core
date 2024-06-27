package se.inera.intyg.certificateservice.application.certificate.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueCode;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueDate;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueMedicalInvestigation;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueMedicalInvestigationList;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueText;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueType;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueMedicalInvestigationList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

class ElementValueConverterMedicalInvestigationListTest {

  private static final CertificateDataValueMedicalInvestigationList CERTIFICATE_DATA_VALUE
      = CertificateDataValueMedicalInvestigationList.builder()
      .id("MI_ID_LIST")
      .list(List.of(
          CertificateDataValueMedicalInvestigation.builder()
              .id("MI_ID")
              .date(CertificateDataValueDate.builder()
                  .id("DATE_ID")
                  .date(LocalDate.now())
                  .build())
              .informationSource(CertificateDataValueText.builder()
                  .id("TEXT_ID")
                  .text("TEXT")
                  .build())
              .investigationType(CertificateDataValueCode.builder()
                  .id("CODE_ID")
                  .code("CODE")
                  .build())
              .build()

      ))
      .build();

  private ElementValueConverterMedicalInvestigationList converter;

  @BeforeEach
  void setUp() {
    converter = new ElementValueConverterMedicalInvestigationList();
  }

  @Test
  void shouldReturnCorrectType() {
    assertEquals(CertificateDataValueType.MEDICAL_INVESTIGATION_LIST, converter.getType());
  }

  @Test
  void shouldThrowExceptionIfInvalidValueType() {
    final var invalidType = CertificateDataValueText.builder().build();

    assertThrows(IllegalStateException.class,
        () -> converter.convert(invalidType)
    );
  }

  @Test
  void shouldSetDateId() {
    final var result = converter.convert(CERTIFICATE_DATA_VALUE);
    final var medicalInvestigationResult = (ElementValueMedicalInvestigationList) result;

    assertEquals("DATE_ID", medicalInvestigationResult.list().get(0).date().dateId().value());
  }

  @Test
  void shouldSetDateValue() {
    final var result = converter.convert(CERTIFICATE_DATA_VALUE);
    final var medicalInvestigationResult = (ElementValueMedicalInvestigationList) result;

    assertEquals(LocalDate.now(), medicalInvestigationResult.list().get(0).date().date());
  }

  @Test
  void shouldSetInformationSourceId() {
    final var result = converter.convert(CERTIFICATE_DATA_VALUE);
    final var medicalInvestigationResult = (ElementValueMedicalInvestigationList) result;

    assertEquals("TEXT_ID",
        medicalInvestigationResult.list().get(0).informationSource().textId().value());
  }

  @Test
  void shouldSetInformationSourceValue() {
    final var result = converter.convert(CERTIFICATE_DATA_VALUE);
    final var medicalInvestigationResult = (ElementValueMedicalInvestigationList) result;

    assertEquals("TEXT",
        medicalInvestigationResult.list().get(0).informationSource().text());
  }

  @Test
  void shouldSetInvestigationTypeId() {
    final var result = converter.convert(CERTIFICATE_DATA_VALUE);
    final var medicalInvestigationResult = (ElementValueMedicalInvestigationList) result;

    assertEquals("CODE_ID",
        medicalInvestigationResult.list().get(0).investigationType().codeId().value());
  }

  @Test
  void shouldSetInvestigationTypeValue() {
    final var result = converter.convert(CERTIFICATE_DATA_VALUE);
    final var medicalInvestigationResult = (ElementValueMedicalInvestigationList) result;

    assertEquals("CODE",
        medicalInvestigationResult.list().get(0).investigationType().code());
  }

  @Test
  void shouldSetId() {
    final var result = converter.convert(CERTIFICATE_DATA_VALUE);
    final var medicalInvestigationResult = (ElementValueMedicalInvestigationList) result;

    assertEquals(new FieldId("MI_ID"), medicalInvestigationResult.list().get(0).id());
  }

  @Test
  void shouldSetListFieldId() {
    final var result = converter.convert(CERTIFICATE_DATA_VALUE);
    final var medicalInvestigationResult = (ElementValueMedicalInvestigationList) result;

    assertEquals(new FieldId("MI_ID_LIST"), medicalInvestigationResult.id());
  }
}
