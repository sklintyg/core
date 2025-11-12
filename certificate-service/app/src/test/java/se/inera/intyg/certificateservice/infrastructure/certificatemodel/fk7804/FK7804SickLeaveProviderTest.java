package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.FK7804_CERTIFICATE;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
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

    final var result = provider.build(certificate, false);
    assertTrue(result.isEmpty());
  }

  @Test
  void shallMapId() {
    final var sickLeaveCertificate = provider.build(FK7804_CERTIFICATE, false);
    assertEquals(FK7804_CERTIFICATE.id(), sickLeaveCertificate.orElseThrow().id());
  }

  @Test
  void shallMapCareGiverId() {
    final var sickLeaveCertificate = provider.build(FK7804_CERTIFICATE, false);
    assertEquals(FK7804_CERTIFICATE.certificateMetaData().careProvider().hsaId(),
        sickLeaveCertificate.orElseThrow().careGiverId());
  }

  @Test
  void shallMapCareUnitId() {
    final var sickLeaveCertificate = provider.build(FK7804_CERTIFICATE, false);
    assertEquals(FK7804_CERTIFICATE.certificateMetaData().issuingUnit().hsaId(),
        sickLeaveCertificate.orElseThrow().issuingUnitId());
  }

  @Test
  void shallMapCareUnitName() {
    final var sickLeaveCertificate = provider.build(FK7804_CERTIFICATE, false);
    assertEquals(FK7804_CERTIFICATE.certificateMetaData().issuingUnit().name(),
        sickLeaveCertificate.orElseThrow().issuingUnitName());
  }

  @Test
  void shallMapType() {
    final var sickLeaveCertificate = provider.build(FK7804_CERTIFICATE, false);
    assertEquals(FK7804_CERTIFICATE.certificateModel().type(),
        sickLeaveCertificate.orElseThrow().type());
  }

  @Test
  void shallMapCivicRegistrationNumber() {
    final var sickLeaveCertificate = provider.build(FK7804_CERTIFICATE, false);
    assertEquals(FK7804_CERTIFICATE.certificateMetaData().patient().id(),
        sickLeaveCertificate.orElseThrow().civicRegistrationNumber());
  }

  @Test
  void shallMapSigningDoctorName() {
    final var sickLeaveCertificate = provider.build(FK7804_CERTIFICATE, false);
    assertEquals(FK7804_CERTIFICATE.certificateMetaData().issuer().name(),
        sickLeaveCertificate.orElseThrow().signingDoctorName());
  }

  @Test
  void shallMapPatientName() {
    final var sickLeaveCertificate = provider.build(FK7804_CERTIFICATE, false);
    assertEquals(FK7804_CERTIFICATE.certificateMetaData().patient().name(),
        sickLeaveCertificate.orElseThrow().patientName());
  }

  @Test
  void shallMapDiagnoseCode() {
    final var sickLeaveCertificate = provider.build(FK7804_CERTIFICATE, false);
    assertEquals("A013", sickLeaveCertificate.orElseThrow().diagnoseCode().code());
  }

  @Test
  void shallNotMapBiDiagnoseCode1IfMissing() {
    final var sickLeaveCertificate = provider.build(FK7804_CERTIFICATE, false);
    assertNull(sickLeaveCertificate.orElseThrow().biDiagnoseCode1());
  }

  @Test
  void shallNotMapBiDiagnoseCode2IfMissing() {
    final var sickLeaveCertificate = provider.build(FK7804_CERTIFICATE, false);
    assertNull(sickLeaveCertificate.orElseThrow().biDiagnoseCode2());
  }


  @Test
  void shallMapSigningDoctorId() {
    final var sickLeaveCertificate = provider.build(FK7804_CERTIFICATE, false);
    assertEquals(FK7804_CERTIFICATE.certificateMetaData().issuer().hsaId(),
        sickLeaveCertificate.orElseThrow().signingDoctorId());
  }

  @Test
  void shallMapSigningDateTime() {
    final var sickLeaveCertificate = provider.build(FK7804_CERTIFICATE, false);
    assertEquals(FK7804_CERTIFICATE.signed(), sickLeaveCertificate.orElseThrow().signingDateTime());
  }

  @Test
  void shallMapDeleted() {
    final var sickLeaveCertificate = provider.build(FK7804_CERTIFICATE, false);
    assertEquals(FK7804_CERTIFICATE.revoked(), sickLeaveCertificate.orElseThrow().deleted());
  }

  @Test
  void shallMapWorkCapacities() {
    final var expectedDateRange = DateRange.builder()
        .dateRangeId(new FieldId(("EN_FJARDEDEL")))
        .from(LocalDate.now())
        .to(LocalDate.now().plusDays(30))
        .build();

    final var sickLeaveCertificate = provider.build(FK7804_CERTIFICATE, false);
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

    final var sickLeaveCertificate = provider.build(FK7804_CERTIFICATE, false);
    assertEquals(expectedEmployment, sickLeaveCertificate.orElseThrow().employment());
  }

  @Test
  void shallBuildSickLeaveCertificateWithoutIgnoredModelRules() {
    final var result = provider.build(FK7804_CERTIFICATE, false);

    final var sickLeaveCertificate = result.orElseThrow();

    assertTrue(result.isPresent());
    assertEquals(FK7804_CERTIFICATE.id(), sickLeaveCertificate.id());
    assertEquals(FK7804_CERTIFICATE.certificateModel().type(), sickLeaveCertificate.type());
    assertEquals(FK7804_CERTIFICATE.certificateMetaData().issuer().hsaId(),
        sickLeaveCertificate.signingDoctorId());
    assertEquals(FK7804_CERTIFICATE.certificateMetaData().issuer().name(),
        sickLeaveCertificate.signingDoctorName());
    assertEquals(FK7804_CERTIFICATE.signed(), sickLeaveCertificate.signingDateTime());
    assertEquals(FK7804_CERTIFICATE.certificateMetaData().issuingUnit().hsaId(),
        sickLeaveCertificate.issuingUnitId());
    assertEquals(FK7804_CERTIFICATE.certificateMetaData().issuingUnit().name(),
        sickLeaveCertificate.issuingUnitName());
    assertEquals(FK7804_CERTIFICATE.certificateMetaData().careProvider().hsaId(),
        sickLeaveCertificate.careGiverId());
    assertEquals(FK7804_CERTIFICATE.certificateMetaData().patient().id(),
        sickLeaveCertificate.civicRegistrationNumber());
    assertEquals(FK7804_CERTIFICATE.certificateMetaData().patient().name(),
        sickLeaveCertificate.patientName());
    assertEquals(FK7804_CERTIFICATE.revoked(), sickLeaveCertificate.deleted());
    assertTrue(sickLeaveCertificate.partOfSickLeaveChain());

    assertEquals("A013", sickLeaveCertificate.diagnoseCode().code());
    assertNull(sickLeaveCertificate.biDiagnoseCode1());
    assertNull(sickLeaveCertificate.biDiagnoseCode2());

    assertEquals(1, sickLeaveCertificate.workCapacities().size());
    final var workCapacity = sickLeaveCertificate.workCapacities().getFirst();
    assertEquals("EN_FJARDEDEL", workCapacity.dateRangeId().value());
    assertEquals(LocalDate.now(), workCapacity.from());
    assertEquals(LocalDate.now().plusDays(30), workCapacity.to());

    assertEquals(1, sickLeaveCertificate.employment().size());
    assertEquals("NUVARANDE_ARBETE", sickLeaveCertificate.employment().getFirst().code());
  }

  @Test
  void shallBuildSickLeaveCertificateWithIgnoredModelRules() {
    final var result = provider.build(FK7804_CERTIFICATE, true);

    final var sickLeaveCertificate = result.orElseThrow();

    assertTrue(result.isPresent());
    assertEquals(FK7804_CERTIFICATE.id(), sickLeaveCertificate.id());
    assertEquals(FK7804_CERTIFICATE.certificateModel().type(), sickLeaveCertificate.type());
    assertEquals(FK7804_CERTIFICATE.certificateMetaData().issuer().hsaId(),
        sickLeaveCertificate.signingDoctorId());
    assertEquals(FK7804_CERTIFICATE.certificateMetaData().issuer().name(),
        sickLeaveCertificate.signingDoctorName());
    assertEquals(FK7804_CERTIFICATE.signed(), sickLeaveCertificate.signingDateTime());
    assertEquals(FK7804_CERTIFICATE.certificateMetaData().issuingUnit().hsaId(),
        sickLeaveCertificate.issuingUnitId());
    assertEquals(FK7804_CERTIFICATE.certificateMetaData().issuingUnit().name(),
        sickLeaveCertificate.issuingUnitName());
    assertEquals(FK7804_CERTIFICATE.certificateMetaData().careProvider().hsaId(),
        sickLeaveCertificate.careGiverId());
    assertEquals(FK7804_CERTIFICATE.certificateMetaData().patient().id(),
        sickLeaveCertificate.civicRegistrationNumber());
    assertEquals(FK7804_CERTIFICATE.certificateMetaData().patient().name(),
        sickLeaveCertificate.patientName());
    assertEquals(FK7804_CERTIFICATE.revoked(), sickLeaveCertificate.deleted());
    assertTrue(sickLeaveCertificate.partOfSickLeaveChain());

    assertEquals("A013", sickLeaveCertificate.diagnoseCode().code());
    assertNull(sickLeaveCertificate.biDiagnoseCode1());
    assertNull(sickLeaveCertificate.biDiagnoseCode2());

    assertEquals(1, sickLeaveCertificate.workCapacities().size());
    assertEquals(1, sickLeaveCertificate.employment().size());
  }

  @Test
  void shallThrowNoSuchElementExceptionWhenEmploymentIsMissingAndIgnoreModuleRulesIsFalse() {
    final var certificate = MedicalCertificate.builder()
        .elementData(
            FK7804_CERTIFICATE.elementData().stream()
                .filter(elementData -> !elementData.id().equals(
                    se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionSysselsattning.QUESTION_SYSSELSATTNING_ID))
                .toList()
        )
        .certificateMetaData(FK7804_CERTIFICATE.certificateMetaData())
        .certificateModel(FK7804_CERTIFICATE.certificateModel())
        .id(FK7804_CERTIFICATE.id())
        .signed(FK7804_CERTIFICATE.signed())
        .build();

    assertThrows(NoSuchElementException.class, () -> provider.build(certificate, false));
  }

  @Test
  void shallThrowNoSuchElementExceptionWhenWorkCapacitiesIsMissingAndIgnoreModuleRulesIsFalse() {
    final var certificate = MedicalCertificate.builder()
        .elementData(
            FK7804_CERTIFICATE.elementData().stream()
                .filter(elementData -> !elementData.id().equals(
                    se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionNedsattningArbetsformaga.QUESTION_NEDSATTNING_ARBETSFORMAGA_ID))
                .toList()
        )
        .certificateMetaData(FK7804_CERTIFICATE.certificateMetaData())
        .certificateModel(FK7804_CERTIFICATE.certificateModel())
        .id(FK7804_CERTIFICATE.id())
        .signed(FK7804_CERTIFICATE.signed())
        .build();

    assertThrows(NoSuchElementException.class, () -> provider.build(certificate, false));
  }

  @Test
  void shallThrowNoSuchElementExceptionWhenDiagnoseCodeIsMissingAndIgnoreModuleRulesIsFalse() {
    final var certificate = MedicalCertificate.builder()
        .elementData(
            FK7804_CERTIFICATE.elementData().stream()
                .filter(elementData -> !elementData.id().equals(
                    se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionDiagnos.QUESTION_DIAGNOS_ID))
                .toList()
        )
        .certificateMetaData(FK7804_CERTIFICATE.certificateMetaData())
        .certificateModel(FK7804_CERTIFICATE.certificateModel())
        .id(FK7804_CERTIFICATE.id())
        .signed(FK7804_CERTIFICATE.signed())
        .build();

    assertThrows(NoSuchElementException.class, () -> provider.build(certificate, false));
  }

  @Test
  void shallReturnEmptyListForEmploymentWhenMissingAndIgnoreModuleRulesIsTrue() {
    final var certificate = MedicalCertificate.builder()
        .elementData(
            FK7804_CERTIFICATE.elementData().stream()
                .filter(elementData -> !elementData.id().equals(
                    se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionSysselsattning.QUESTION_SYSSELSATTNING_ID))
                .toList()
        )
        .certificateMetaData(FK7804_CERTIFICATE.certificateMetaData())
        .certificateModel(FK7804_CERTIFICATE.certificateModel())
        .id(FK7804_CERTIFICATE.id())
        .signed(FK7804_CERTIFICATE.signed())
        .build();

    final var result = provider.build(certificate, true);

    assertTrue(result.isPresent());
    assertEquals(List.of(), result.orElseThrow().employment());
  }

  @Test
  void shallReturnEmptyListForWorkCapacitiesWhenMissingAndIgnoreModuleRulesIsTrue() {
    final var certificate = MedicalCertificate.builder()
        .elementData(
            FK7804_CERTIFICATE.elementData().stream()
                .filter(elementData -> !elementData.id().equals(
                    se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionNedsattningArbetsformaga.QUESTION_NEDSATTNING_ARBETSFORMAGA_ID))
                .toList()
        )
        .certificateMetaData(FK7804_CERTIFICATE.certificateMetaData())
        .certificateModel(FK7804_CERTIFICATE.certificateModel())
        .id(FK7804_CERTIFICATE.id())
        .signed(FK7804_CERTIFICATE.signed())
        .build();

    final var result = provider.build(certificate, true);

    assertTrue(result.isPresent());
    assertEquals(List.of(), result.orElseThrow().workCapacities());
  }

  @Test
  void shallReturnNullForDiagnoseCodeWhenMissingAndIgnoreModuleRulesIsTrue() {
    final var certificate = MedicalCertificate.builder()
        .elementData(
            FK7804_CERTIFICATE.elementData().stream()
                .filter(elementData -> !elementData.id().equals(
                    se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionDiagnos.QUESTION_DIAGNOS_ID))
                .toList()
        )
        .certificateMetaData(FK7804_CERTIFICATE.certificateMetaData())
        .certificateModel(FK7804_CERTIFICATE.certificateModel())
        .id(FK7804_CERTIFICATE.id())
        .signed(FK7804_CERTIFICATE.signed())
        .build();

    final var result = provider.build(certificate, true);

    assertTrue(result.isPresent());
    assertNull(result.orElseThrow().diagnoseCode());
  }


}