package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.FK7804_CERTIFICATE;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.DateRange;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalCertificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionSmittbararpenning;

@ExtendWith(MockitoExtension.class)
class FK7804SickLeaveProviderTest {

  private final FK7804SickLeaveProvider provider = new FK7804SickLeaveProvider();

  @Test
  void shallReturnEmptyWhenSmittbararpenningIsTrue() {
    final var element = ElementData.builder()
        .id(QuestionSmittbararpenning.QUESTION_SMITTBARARPENNING_ID)
        .value(
            ElementValueBoolean.builder()
                .booleanId(new FieldId("smitt"))
                .value(true)
                .build()
        )
        .build();

    final var certificate = MedicalCertificate.builder()
        .elementData(List.of(element))
        .build();

    final var result = provider.build(certificate);
    assertTrue(result.isEmpty());
  }

  @Test
  void shallMapId() {
    final var sickLeaveCertificate = provider.build(FK7804_CERTIFICATE);
    assertEquals(FK7804_CERTIFICATE.id(), sickLeaveCertificate.orElseThrow().id());
  }

  @Test
  void shallMapCareGiverId() {
    final var sickLeaveCertificate = provider.build(FK7804_CERTIFICATE);
    assertEquals(FK7804_CERTIFICATE.certificateMetaData().careProvider().hsaId(),
        sickLeaveCertificate.orElseThrow().careGiverId());
  }

  @Test
  void shallMapCareUnitId() {
    final var sickLeaveCertificate = provider.build(FK7804_CERTIFICATE);
    assertEquals(FK7804_CERTIFICATE.certificateMetaData().careUnit().hsaId(),
        sickLeaveCertificate.orElseThrow().careUnitId());
  }

  @Test
  void shallMapCareUnitName() {
    final var sickLeaveCertificate = provider.build(FK7804_CERTIFICATE);
    assertEquals(FK7804_CERTIFICATE.certificateMetaData().careUnit().name(),
        sickLeaveCertificate.orElseThrow().careUnitName());
  }

  @Test
  void shallMapType() {
    final var sickLeaveCertificate = provider.build(FK7804_CERTIFICATE);
    assertEquals(FK7804_CERTIFICATE.certificateModel().type(),
        sickLeaveCertificate.orElseThrow().type());
  }

  @Test
  void shallMapCivicRegistrationNumber() {
    final var sickLeaveCertificate = provider.build(FK7804_CERTIFICATE);
    assertEquals(FK7804_CERTIFICATE.certificateMetaData().patient().id(),
        sickLeaveCertificate.orElseThrow().civicRegistrationNumber());
  }

  @Test
  void shallMapSigningDoctorName() {
    final var sickLeaveCertificate = provider.build(FK7804_CERTIFICATE);
    assertEquals(FK7804_CERTIFICATE.certificateMetaData().issuer().name(),
        sickLeaveCertificate.orElseThrow().signingDoctorName());
  }

  @Test
  void shallMapPatientName() {
    final var sickLeaveCertificate = provider.build(FK7804_CERTIFICATE);
    assertEquals(FK7804_CERTIFICATE.certificateMetaData().patient().name(),
        sickLeaveCertificate.orElseThrow().patientName());
  }

  @Test
  void shallMapDiagnoseCode() {
    final var sickLeaveCertificate = provider.build(FK7804_CERTIFICATE);
    assertEquals("A013", sickLeaveCertificate.orElseThrow().diagnoseCode().code());
  }

  @Test
  void shallNotMapBiDiagnoseCode1IfMissing() {
    final var sickLeaveCertificate = provider.build(FK7804_CERTIFICATE);
    assertNull(sickLeaveCertificate.orElseThrow().biDiagnoseCode1());
  }

  @Test
  void shallNotMapBiDiagnoseCode2IfMissing() {
    final var sickLeaveCertificate = provider.build(FK7804_CERTIFICATE);
    assertNull(sickLeaveCertificate.orElseThrow().biDiagnoseCode2());
  }


  @Test
  void shallMapSigningDoctorId() {
    final var sickLeaveCertificate = provider.build(FK7804_CERTIFICATE);
    assertEquals(FK7804_CERTIFICATE.certificateMetaData().issuer().hsaId(),
        sickLeaveCertificate.orElseThrow().signingDoctorId());
  }

  @Test
  void shallMapSigningDateTime() {
    final var sickLeaveCertificate = provider.build(FK7804_CERTIFICATE);
    assertEquals(FK7804_CERTIFICATE.signed(), sickLeaveCertificate.orElseThrow().signingDateTime());
  }

  @Test
  void shallMapDeleted() {
    final var sickLeaveCertificate = provider.build(FK7804_CERTIFICATE);
    assertEquals(FK7804_CERTIFICATE.revoked(), sickLeaveCertificate.orElseThrow().deleted());
  }

  @Test
  void shallMapWorkCapacities() {
    final var expectedDateRange = DateRange.builder()
        .dateRangeId(new FieldId(("EN_FJARDEDEL")))
        .from(LocalDate.now())
        .to(LocalDate.now().plusDays(30))
        .build();

    final var sickLeaveCertificate = provider.build(FK7804_CERTIFICATE);
    assertEquals(List.of(expectedDateRange), sickLeaveCertificate.orElseThrow().workCapacities());
  }

  @Test
  void shallMapEmployment() {
    final var expectedEmployment = List.of(
        ElementValueCode.builder()
            .code("NUVARANDE_ARBETE")
            .codeId(new FieldId("NUVARANDE_ARBETE"))
            .build()
    );

    final var sickLeaveCertificate = provider.build(FK7804_CERTIFICATE);
    assertEquals(expectedEmployment, sickLeaveCertificate.orElseThrow().employment());
  }
}