package se.inera.intyg.certificateservice.application.certificate.service.converter;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.SickLeaveCertificateItemWorkCapacityDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.SickLeaveCertificateWorkCapacityDTO;
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
  private static final HsaId ISSUING_UNIT_ID = new HsaId("ISSUING_UNIT_ID");
  private static final UnitName ISSUING_UNIT_NAME = new UnitName("ISSUING_UNIT_NAME");
  private static final HsaId CARE_GIVER_ID = new HsaId("CG_ID");
  private static final PersonId CIVIC_REGISTRATION_NUMBER = PersonId.builder().id("191212121212")
      .build();
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
      .revokedBy(Staff.builder().name(SIGNING_DOCTOR_NAME).hsaId(ISSUING_UNIT_ID).build())
      .revokedAt(LocalDateTime.of(2050, 1, 1, 1, 1, 0)).build();
  private static final List<DateRange> WORK_CAPACITIES = List.of(
      DateRange.builder().dateRangeId(new FieldId("10")).from(LocalDate.of(2023, 1, 1))
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
        .issuingUnitId(ISSUING_UNIT_ID)
        .issuingUnitName(ISSUING_UNIT_NAME)
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

  @Nested
  class SickLeaveCertificateDTOTest {

    @Test
    void shouldReturnNullIfInputIsNull() {
      assertNull(converter.toSickLeaveCertificate(null));
    }

    @Test
    void shallConvertId() {
      final var dto = converter.toSickLeaveCertificate(sickLeaveCertificate);
      assertNotNull(dto);
      assertEquals("CERT_ID", dto.getId());
    }

    @Test
    void shallConvertType() {
      final var dto = converter.toSickLeaveCertificate(sickLeaveCertificate);
      assertEquals("type", dto.getType());
    }

    @Test
    void shallConvertSigningDoctorId() {
      final var dto = converter.toSickLeaveCertificate(sickLeaveCertificate);
      assertEquals("DOCTOR_ID", dto.getSigningDoctorId());
    }

    @Test
    void shallConvertSigningDoctorName() {
      final var dto = converter.toSickLeaveCertificate(sickLeaveCertificate);
      assertEquals("FIRST_NAME MIDDLE_NAME LAST_NAME", dto.getSigningDoctorName());
    }

    @Test
    void shallConvertSigningDateTime() {
      final var dto = converter.toSickLeaveCertificate(sickLeaveCertificate);
      assertEquals(LocalDateTime.of(2023, 1, 1, 10, 0), dto.getSigningDateTime());
    }

    @Test
    void shallConvertCareUnitAndCareGiverFields() {

      final var dto = converter.toSickLeaveCertificate(sickLeaveCertificate);

      assertEquals("ISSUING_UNIT_ID", dto.getCareUnitId());
      assertEquals("ISSUING_UNIT_NAME", dto.getCareUnitName());
      assertEquals("CG_ID", dto.getCareGiverId());
    }

    @Test
    void shallConvertPatientFields() {

      final var dto = converter.toSickLeaveCertificate(sickLeaveCertificate);

      assertEquals("19121212-1212", dto.getCivicRegistrationNumber());
      assertEquals("PATIENT_NAME PATIENT_MIDDLE_NAME PATIENT_NAME", dto.getPatientName());
    }

    @Test
    void shallConvertDiagnoseAndBiDiagnoseFields() {

      final var dto = converter.toSickLeaveCertificate(sickLeaveCertificate);

      assertAll(
          () -> assertEquals("D1", dto.getDiagnoseCode()),
          () -> assertEquals("B1", dto.getBiDiagnoseCode1()),
          () -> assertEquals("B2", dto.getBiDiagnoseCode2()));

    }

    @Test
    void shallConvertEmploymentField() {

      final var dto = converter.toSickLeaveCertificate(sickLeaveCertificate);

      assertEquals("EMP1,EMP2", dto.getEmployment());
    }

    @Test
    void shallConvertTestCertificateFields() {

      final var dto = converter.toSickLeaveCertificate(sickLeaveCertificate);

      assertFalse(dto.isTestCertificate());
    }

    @Test
    void shallConvertWorkCapacityListSize() {

      final var dto = converter.toSickLeaveCertificate(sickLeaveCertificate);

      assertNotNull(dto.getSjukfallCertificateWorkCapacity());
      assertEquals(2, dto.getSjukfallCertificateWorkCapacity().size());
    }

    @Test
    void shallConvertFirstWorkCapacity() {

      final var dto = converter.toSickLeaveCertificate(sickLeaveCertificate);

      final var wc1 = dto.getSjukfallCertificateWorkCapacity().getFirst();
      assertEquals(10, wc1.getCapacityPercentage());
      assertEquals("2023-01-01", wc1.getFromDate());
      assertEquals("2023-01-10", wc1.getToDate());
    }


    @Test
    void shallConvertPartialWorkCapacityWithoutFrom() {
      sickLeaveCertificate = SickLeaveCertificate.builder()
          .id(CERTIFICATE_ID)
          .type(TYPE)
          .signingDoctorId(SIGNING_DOCTOR_ID)
          .signingDoctorName(SIGNING_DOCTOR_NAME)
          .signingDateTime(SIGNING_DATE_TIME)
          .issuingUnitId(ISSUING_UNIT_ID)
          .issuingUnitName(ISSUING_UNIT_NAME)
          .careGiverId(CARE_GIVER_ID)
          .civicRegistrationNumber(CIVIC_REGISTRATION_NUMBER)
          .patientName(PATIENT_NAME)
          .diagnoseCode(DIAGNOSIS_CODE)
          .biDiagnoseCode1(BI_DIAGNOSE_CODE1)
          .biDiagnoseCode2(BI_DIAGNOSE_CODE2)
          .employment(EMPLOYMENT)
          .deleted(DELETED)
          .workCapacities(
              List.of(
                  DateRange.builder().dateRangeId(new FieldId("10"))
                      .to(LocalDate.of(2023, 1, 10)).build()
              )
          )
          .testCertificate(TEST_CERTIFICATE)
          .extendsCertificateId(CERTIFICATE_ID.id())
          .build();

      final var dto = converter.toSickLeaveCertificate(sickLeaveCertificate);

      final var wc1 = dto.getSjukfallCertificateWorkCapacity().getFirst();
      assertEquals(10, wc1.getCapacityPercentage());
      assertNull(wc1.getFromDate());
      assertEquals("2023-01-10", wc1.getToDate());
    }

    @Test
    void shallConvertPartialWorkCapacityWithoutTo() {
      sickLeaveCertificate = SickLeaveCertificate.builder()
          .id(CERTIFICATE_ID)
          .type(TYPE)
          .signingDoctorId(SIGNING_DOCTOR_ID)
          .signingDoctorName(SIGNING_DOCTOR_NAME)
          .signingDateTime(SIGNING_DATE_TIME)
          .issuingUnitId(ISSUING_UNIT_ID)
          .issuingUnitName(ISSUING_UNIT_NAME)
          .careGiverId(CARE_GIVER_ID)
          .civicRegistrationNumber(CIVIC_REGISTRATION_NUMBER)
          .patientName(PATIENT_NAME)
          .diagnoseCode(DIAGNOSIS_CODE)
          .biDiagnoseCode1(BI_DIAGNOSE_CODE1)
          .biDiagnoseCode2(BI_DIAGNOSE_CODE2)
          .employment(EMPLOYMENT)
          .deleted(DELETED)
          .workCapacities(
              List.of(
                  DateRange.builder().dateRangeId(new FieldId("10"))
                      .from(LocalDate.of(2023, 1, 10)).build()
              )
          )
          .testCertificate(TEST_CERTIFICATE)
          .extendsCertificateId(CERTIFICATE_ID.id())
          .build();

      final var dto = converter.toSickLeaveCertificate(sickLeaveCertificate);

      final var wc1 = dto.getSjukfallCertificateWorkCapacity().getFirst();
      assertEquals(10, wc1.getCapacityPercentage());
      assertNull(wc1.getToDate());
      assertEquals("2023-01-10", wc1.getFromDate());
    }

    @Test
    void shallConvertSecondWorkCapacity() {

      final var dto = converter.toSickLeaveCertificate(sickLeaveCertificate);

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
          .issuingUnitId(ISSUING_UNIT_ID)
          .issuingUnitName(ISSUING_UNIT_NAME)
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
          .extendsCertificateId(CERTIFICATE_ID.id())
          .build();

      final var dto = converter.toSickLeaveCertificate(sickLeaveCertificate);

      assertNull(dto.getBiDiagnoseCode1());
      assertNull(dto.getBiDiagnoseCode2());
    }

    @Test
    void shouldHandleNullMainDiagnoseCodes() {

      sickLeaveCertificate = SickLeaveCertificate.builder()
          .id(CERTIFICATE_ID)
          .type(TYPE)
          .signingDoctorId(SIGNING_DOCTOR_ID)
          .signingDoctorName(SIGNING_DOCTOR_NAME)
          .signingDateTime(SIGNING_DATE_TIME)
          .issuingUnitId(ISSUING_UNIT_ID)
          .issuingUnitName(ISSUING_UNIT_NAME)
          .careGiverId(CARE_GIVER_ID)
          .civicRegistrationNumber(CIVIC_REGISTRATION_NUMBER)
          .patientName(PATIENT_NAME)
          .diagnoseCode(null)
          .biDiagnoseCode1(BI_DIAGNOSE_CODE1)
          .biDiagnoseCode2(BI_DIAGNOSE_CODE2)
          .employment(EMPLOYMENT)
          .deleted(DELETED)
          .workCapacities(WORK_CAPACITIES)
          .testCertificate(TEST_CERTIFICATE)
          .extendsCertificateId(CERTIFICATE_ID.id())
          .build();

      final var dto = converter.toSickLeaveCertificate(sickLeaveCertificate);

      assertNull(dto.getDiagnoseCode());
    }

    @Test
    void shallConvertDeleted() {

      final var dto = converter.toSickLeaveCertificate(sickLeaveCertificate);

      assertTrue(dto.getDeleted());
    }

    @Test
    void shouldConvertWorkCapacityEnum() {
      final var expected = List.of(
          SickLeaveCertificateWorkCapacityDTO.builder()
              .capacityPercentage(10)
              .fromDate("2023-01-01")
              .toDate("2023-01-10")
              .build(),
          SickLeaveCertificateWorkCapacityDTO.builder()
              .capacityPercentage(100)
              .fromDate("2023-01-10")
              .toDate("2023-01-15")
              .build()
      );

      final var dto = converter.toSickLeaveCertificate(sickLeaveCertificate);

      assertEquals(expected, dto.getSjukfallCertificateWorkCapacity());
    }

    @Test
    void shouldConvertExtendsCertificateId() {
      sickLeaveCertificate = SickLeaveCertificate.builder()
          .id(CERTIFICATE_ID)
          .type(TYPE)
          .signingDoctorId(SIGNING_DOCTOR_ID)
          .signingDoctorName(SIGNING_DOCTOR_NAME)
          .signingDateTime(SIGNING_DATE_TIME)
          .issuingUnitId(ISSUING_UNIT_ID)
          .issuingUnitName(ISSUING_UNIT_NAME)
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
          .extendsCertificateId("EXTENDS_CERT_ID")
          .build();

      final var dto = converter.toSickLeaveCertificate(sickLeaveCertificate);

      assertEquals("EXTENDS_CERT_ID", dto.getExtendsCertificateId());
    }

    @Test
    void shouldHandleNullExtendsCertificateId() {
      sickLeaveCertificate = SickLeaveCertificate.builder()
          .id(CERTIFICATE_ID)
          .type(TYPE)
          .signingDoctorId(SIGNING_DOCTOR_ID)
          .signingDoctorName(SIGNING_DOCTOR_NAME)
          .signingDateTime(SIGNING_DATE_TIME)
          .issuingUnitId(ISSUING_UNIT_ID)
          .issuingUnitName(ISSUING_UNIT_NAME)
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
          .extendsCertificateId(null)
          .build();

      final var dto = converter.toSickLeaveCertificate(sickLeaveCertificate);

      assertNull(dto.getExtendsCertificateId());
    }
  }

  @Nested
  class SickLeaveCertificateItemDTOTest {

    @Test
    void shouldReturnNullIfInputIsNull() {
      assertNull(converter.toSickLeaveCertificateItem(null));
    }

    @Test
    void shallConvertId() {
      final var dto = converter.toSickLeaveCertificateItem(sickLeaveCertificate);
      assertNotNull(dto);
      assertEquals("CERT_ID", dto.getCertificateId());
    }

    @Test
    void shallConvertType() {
      final var dto = converter.toSickLeaveCertificateItem(sickLeaveCertificate);
      assertEquals("TYPE", dto.getCertificateType());
    }

    @Test
    void shallConvertSigningDoctorId() {
      final var dto = converter.toSickLeaveCertificateItem(sickLeaveCertificate);
      assertEquals("DOCTOR_ID", dto.getPersonalHsaId());
    }

    @Test
    void shallConvertSigningDoctorName() {
      final var dto = converter.toSickLeaveCertificateItem(sickLeaveCertificate);
      assertEquals("FIRST_NAME MIDDLE_NAME LAST_NAME", dto.getPersonalFullName());
    }

    @Test
    void shallConvertSigningDateTime() {
      final var dto = converter.toSickLeaveCertificateItem(sickLeaveCertificate);
      assertEquals(LocalDateTime.of(2023, 1, 1, 10, 0), dto.getSigningDateTime());
    }

    @Test
    void shallConvertCareUnitAndCareGiverFields() {

      final var dto = converter.toSickLeaveCertificateItem(sickLeaveCertificate);

      assertEquals("ISSUING_UNIT_ID", dto.getCareUnitId());
      assertEquals("ISSUING_UNIT_NAME", dto.getCareUnitName());
      assertEquals("CG_ID", dto.getCareProviderId());
    }

    @Test
    void shallConvertPatientFields() {

      final var dto = converter.toSickLeaveCertificateItem(sickLeaveCertificate);

      assertEquals("19121212-1212", dto.getPersonId());
      assertEquals("PATIENT_NAME PATIENT_MIDDLE_NAME PATIENT_NAME", dto.getPatientFullName());
    }

    @Test
    void shallConvertDiagnoseAndBiDiagnoseFields() {

      final var dto = converter.toSickLeaveCertificateItem(sickLeaveCertificate);

      assertAll(
          () -> assertEquals("D1", dto.getDiagnoseCode()),
          () -> assertEquals("B1", dto.getSecondaryDiagnoseCodes().getFirst()),
          () -> assertEquals("B2", dto.getSecondaryDiagnoseCodes().get(1)));

    }

    @Test
    void shallConvertEmploymentField() {

      final var dto = converter.toSickLeaveCertificateItem(sickLeaveCertificate);

      assertEquals("EMP1,EMP2", dto.getOccupation());
    }

    @Test
    void shallConvertTestCertificateFields() {

      final var dto = converter.toSickLeaveCertificateItem(sickLeaveCertificate);

      assertFalse(dto.isTestCertificate());
    }

    @Test
    void shouldHandleNullBiDiagnoseCodes() {

      sickLeaveCertificate = SickLeaveCertificate.builder()
          .id(CERTIFICATE_ID)
          .type(TYPE)
          .signingDoctorId(SIGNING_DOCTOR_ID)
          .signingDoctorName(SIGNING_DOCTOR_NAME)
          .signingDateTime(SIGNING_DATE_TIME)
          .issuingUnitId(ISSUING_UNIT_ID)
          .issuingUnitName(ISSUING_UNIT_NAME)
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
          .extendsCertificateId(CERTIFICATE_ID.id())
          .build();

      final var dto = converter.toSickLeaveCertificateItem(sickLeaveCertificate);

      assertTrue(dto.getSecondaryDiagnoseCodes().isEmpty());
    }

    @Test
    void shallConvertDeleted() {

      final var dto = converter.toSickLeaveCertificateItem(sickLeaveCertificate);

      assertTrue(dto.isDeleted());
    }

    @Test
    void shouldConvertWorkCapacity() {
      final var expected = List.of(
          SickLeaveCertificateItemWorkCapacityDTO.builder()
              .reduction(10)
              .startDate(LocalDate.of(2023, 1, 1))
              .endDate(LocalDate.of(2023, 1, 10))
              .build(),
          SickLeaveCertificateItemWorkCapacityDTO.builder()
              .reduction(100)
              .startDate(LocalDate.of(2023, 1, 10))
              .endDate(LocalDate.of(2023, 1, 15))
              .build()
      );

      final var dto = converter.toSickLeaveCertificateItem(sickLeaveCertificate);

      assertEquals(expected, dto.getWorkCapacityList());
    }
  }
}