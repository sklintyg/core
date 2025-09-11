package se.inera.intyg.certificateservice.application.certificate.service.converter;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.DateRange;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosis;
import se.inera.intyg.certificateservice.domain.certificate.model.Revoked;
import se.inera.intyg.certificateservice.domain.certificate.model.SickLeaveCertificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.common.model.Code;
import se.inera.intyg.certificateservice.domain.common.model.HsaId;
import se.inera.intyg.certificateservice.domain.common.model.PersonId;
import se.inera.intyg.certificateservice.domain.patient.model.Name;
import se.inera.intyg.certificateservice.domain.staff.model.Staff;
import se.inera.intyg.certificateservice.domain.unit.model.UnitName;

class SickLeaveConverterTest {

  private static final CertificateId CERTIFICATE_ID = new CertificateId("CERT_ID");
  private static final Code TYPE = new Code("TYPE", "CODE_SYSTEM", "CODE_SYSTEM_NAME");
  private static final HsaId SIGNING_DOCTOR_ID = new HsaId("DOCTOR_ID");
  private static final Name SIGNING_DOCTOR_NAME = Name.builder().firstName("FIRST_NAME")
      .middleName("MIDDLE_NAME").lastName("LAST_NAME").build();
  private static final LocalDateTime SIGNING_DATE_TIME = LocalDateTime.of(2023, 1, 1, 10, 0);
  private static final HsaId CARE_UNIT_ID = new HsaId("CU_ID");
  private static final UnitName CARE_UNIT_NAME = new UnitName("CARE_UNIT_NAME");
  private static final HsaId CARE_GIVER_ID = new HsaId("CG_ID");
  private static final PersonId CIVIC_REGISTRATION_NUMBER = PersonId.builder().id("CIV_ID").build();
  private static final Name PATIENT_NAME = Name.builder().firstName("PATIENT_NAME")
      .lastName("PATIENT_NAME").middleName("PATIENT_MIDDLE_NAME").build();
  private static final ElementValueDiagnosis DIAGNOSIS_CODE = ElementValueDiagnosis.builder()
      .code("D1").build();
  private static final ElementValueDiagnosis BI_DIAGNOSE_CODE1 = ElementValueDiagnosis.builder()
      .code("B1").build();
  private static final ElementValueDiagnosis BI_DIAGNOSE_CODE2 = ElementValueDiagnosis.builder()
      .code("B2").build();
  private static final List<ElementValueCode> EMPLOYMENT = List.of(
      ElementValueCode.builder().code("EMP1").build(),
      ElementValueCode.builder().code("EMP2").build());
  private static final Revoked DELETED = Revoked.builder()
      .revokedBy(Staff.builder().name(SIGNING_DOCTOR_NAME).hsaId(CARE_UNIT_ID).build())
      .revokedAt(LocalDateTime.of(2050, 1, 1, 1, 1, 0)).build();
  private static final List<DateRange> WORK_CAPACITIES = List.of(
      DateRange.builder().dateRangeId(new FieldId("EN_FJARDEDEL")).from(LocalDate.of(2023, 1, 1))
          .to(LocalDate.of(2023, 1, 10)).build(),
      DateRange.builder().dateRangeId(new FieldId("HELT_NEDSATT")).from(LocalDate.of(2023, 1, 10))
          .to(LocalDate.of(2023, 1, 15)).build()
  );
  private static final boolean TEST_CERTIFICATE = false;

  private SickLeaveConverter converter;

  private SickLeaveCertificate sickLeaveCertificate;

  @BeforeEach
  void setUp() {
    converter = new SickLeaveConverter();

    sickLeaveCertificate = SickLeaveCertificate.builder()
        .id(CERTIFICATE_ID)
        .type(TYPE)
        .signingDoctorId(SIGNING_DOCTOR_ID)
        .signingDoctorName(SIGNING_DOCTOR_NAME)
        .signingDateTime(SIGNING_DATE_TIME)
        .careUnitId(CARE_UNIT_ID)
        .careUnitName(CARE_UNIT_NAME)
        .careGiverId(CARE_GIVER_ID)
        .civicRegistrationNumber(CIVIC_REGISTRATION_NUMBER)
        .patientName(PATIENT_NAME)
        .diagnoseCode(DIAGNOSIS_CODE)
        .biDiagnoseCode1(BI_DIAGNOSE_CODE1)
        .biDiagnoseCode2(BI_DIAGNOSE_CODE2)
        .employment(EMPLOYMENT)
        .deleted(DELETED)
        .workCapacities(WORK_CAPACITIES)
        .testCertificate(TEST_CERTIFICATE)
        .build();
  }

  @Test
  void shouldReturnNullIfInputIsNull() {
    assertNull(converter.convert(null));
  }

  @Test
  void shallConvertId() {
    final var dto = converter.convert(sickLeaveCertificate);
    assertNotNull(dto);
    assertEquals("CERT_ID", dto.getId());
  }

  @Test
  void shallConvertType() {
    final var dto = converter.convert(sickLeaveCertificate);
    assertEquals("type", dto.getType());
  }

  @Test
  void shallConvertSigningDoctorId() {
    final var dto = converter.convert(sickLeaveCertificate);
    assertEquals("DOCTOR_ID", dto.getSigningDoctorId());
  }

  @Test
  void shallConvertSigningDoctorName() {
    final var dto = converter.convert(sickLeaveCertificate);
    assertEquals("FIRST_NAME MIDDLE_NAME LAST_NAME", dto.getSigningDoctorName());
  }

  @Test
  void shallConvertSigningDateTime() {
    final var dto = converter.convert(sickLeaveCertificate);
    assertEquals(LocalDateTime.of(2023, 1, 1, 10, 0), dto.getSigningDateTime());
  }

  @Test
  void shallConvertCareUnitAndCareGiverFields() {

    final var dto = converter.convert(sickLeaveCertificate);

    assertEquals("CU_ID", dto.getCareUnitId());
    assertEquals("CARE_UNIT_NAME", dto.getCareUnitName());
    assertEquals("CG_ID", dto.getCareGiverId());
  }

  @Test
  void shallConvertPatientFields() {

    final var dto = converter.convert(sickLeaveCertificate);

    assertEquals("CIV_ID", dto.getCivicRegistrationNumber());
    assertEquals("PATIENT_NAME PATIENT_MIDDLE_NAME PATIENT_NAME", dto.getPatientName());
  }

  @Test
  void shallConvertDiagnoseAndBiDiagnoseFields() {

    final var dto = converter.convert(sickLeaveCertificate);

    assertAll(
        () -> assertEquals("D1", dto.getDiagnoseCode()),
        () -> assertEquals("B1", dto.getBiDiagnoseCode1()),
        () -> assertEquals("B2", dto.getBiDiagnoseCode2()));

  }

  @Test
  void shallConvertEmploymentField() {

    final var dto = converter.convert(sickLeaveCertificate);

    assertEquals("EMP1,EMP2", dto.getEmployment());
  }

  @Test
  void shallConvertTestCertificateFields() {

    final var dto = converter.convert(sickLeaveCertificate);

    assertFalse(dto.isTestCertificate());
  }

  @Test
  void shallConvertWorkCapacityListSize() {

    final var dto = converter.convert(sickLeaveCertificate);

    assertNotNull(dto.getSjukfallCertificateWorkCapacity());
    assertEquals(2, dto.getSjukfallCertificateWorkCapacity().size());
  }

  @Test
  void shallConvertFirstWorkCapacity() {

    final var dto = converter.convert(sickLeaveCertificate);

    final var wc1 = dto.getSjukfallCertificateWorkCapacity().getFirst();
    assertEquals(25, wc1.getCapacityPercentage());
    assertEquals("2023-01-01", wc1.getFromDate());
    assertEquals("2023-01-10", wc1.getToDate());
  }

  @Test
  void shallConvertSecondWorkCapacity() {

    final var dto = converter.convert(sickLeaveCertificate);

    final var wc2 = dto.getSjukfallCertificateWorkCapacity().get(1);

    assertAll(
        () -> assertEquals(100, wc2.getCapacityPercentage()),
        () -> assertEquals("2023-01-10", wc2.getFromDate()),
        () -> assertEquals("2023-01-15", wc2.getToDate()));
  }

  @Test
  void shouldHandleNullBiDiagnoseCodes() {

    sickLeaveCertificate = SickLeaveCertificate.builder()
        .id(CERTIFICATE_ID)
        .type(TYPE)
        .signingDoctorId(SIGNING_DOCTOR_ID)
        .signingDoctorName(SIGNING_DOCTOR_NAME)
        .signingDateTime(SIGNING_DATE_TIME)
        .careUnitId(CARE_UNIT_ID)
        .careUnitName(CARE_UNIT_NAME)
        .careGiverId(CARE_GIVER_ID)
        .civicRegistrationNumber(CIVIC_REGISTRATION_NUMBER)
        .patientName(PATIENT_NAME)
        .diagnoseCode(DIAGNOSIS_CODE)
        .biDiagnoseCode1(null)
        .biDiagnoseCode2(null)
        .employment(EMPLOYMENT)
        .deleted(DELETED)
        .workCapacities(WORK_CAPACITIES)
        .testCertificate(TEST_CERTIFICATE)
        .build();

    final var dto = converter.convert(sickLeaveCertificate);

    assertNull(dto.getBiDiagnoseCode1());
    assertNull(dto.getBiDiagnoseCode2());
  }

  @Test
  void shallConvertDeleted() {

    final var dto = converter.convert(sickLeaveCertificate);

    Assertions.assertTrue(dto.getDeleted());
  }
}