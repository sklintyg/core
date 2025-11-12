package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.ag7804CertificateBuilder;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.DateRange;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

@ExtendWith(MockitoExtension.class)
class AG7804SickLeaveProviderTest {

  private final AG7804SickLeaveProvider provider = new AG7804SickLeaveProvider();

  @Test
  void shouldMapId() {
    final var sickLeaveCertificate = provider.build(ag7804CertificateBuilder().build(), false);
    assertEquals(ag7804CertificateBuilder().build().id(), sickLeaveCertificate.orElseThrow().id());
  }

  @Test
  void shouldMapCareGiverId() {
    final var sickLeaveCertificate = provider.build(ag7804CertificateBuilder().build(), false);
    assertEquals(ag7804CertificateBuilder().build().certificateMetaData().careProvider().hsaId(),
        sickLeaveCertificate.orElseThrow().careGiverId());
  }

  @Test
  void shouldMapCareUnitId() {
    final var sickLeaveCertificate = provider.build(ag7804CertificateBuilder().build(), false);
    assertEquals(ag7804CertificateBuilder().build().certificateMetaData().issuingUnit().hsaId(),
        sickLeaveCertificate.orElseThrow().issuingUnitId());
  }

  @Test
  void shouldMapCareUnitName() {
    final var sickLeaveCertificate = provider.build(ag7804CertificateBuilder().build(), false);
    assertEquals(ag7804CertificateBuilder().build().certificateMetaData().issuingUnit().name(),
        sickLeaveCertificate.orElseThrow().issuingUnitName());
  }

  @Test
  void shouldMapType() {
    final var sickLeaveCertificate = provider.build(ag7804CertificateBuilder().build(), false);
    assertEquals(ag7804CertificateBuilder().build().certificateModel().type(),
        sickLeaveCertificate.orElseThrow().type());
  }

  @Test
  void shouldMapCivicRegistrationNumber() {
    final var sickLeaveCertificate = provider.build(ag7804CertificateBuilder().build(), false);
    assertEquals(ag7804CertificateBuilder().build().certificateMetaData().patient().id(),
        sickLeaveCertificate.orElseThrow().civicRegistrationNumber());
  }

  @Test
  void shouldMapSigningDoctorName() {
    final var sickLeaveCertificate = provider.build(ag7804CertificateBuilder().build(), false);
    assertEquals(ag7804CertificateBuilder().build().certificateMetaData().issuer().name(),
        sickLeaveCertificate.orElseThrow().signingDoctorName());
  }

  @Test
  void shouldMapPatientName() {
    final var sickLeaveCertificate = provider.build(ag7804CertificateBuilder().build(), false);
    assertEquals(ag7804CertificateBuilder().build().certificateMetaData().patient().name(),
        sickLeaveCertificate.orElseThrow().patientName());
  }

  @Test
  void shouldMapDiagnoseCode() {
    final var sickLeaveCertificate = provider.build(ag7804CertificateBuilder().build(), false);
    assertEquals("A013", sickLeaveCertificate.orElseThrow().diagnoseCode().code());
  }

  @Test
  void shouldUseDefaultDiagnosisWhenNoDiagnosisPresent() {
    final var certificate = ag7804CertificateBuilder()
        .elementData(
            ag7804CertificateBuilder().build().elementData().stream()
                .filter(elementData -> !elementData.id().id().equals("6"))
                .toList()
        )
        .build();

    final var sickLeaveCertificate = provider.build(certificate, false);

    assertEquals("X", sickLeaveCertificate.orElseThrow().diagnoseCode().code());
    assertEquals("Diagnoskod X är okänd och har ingen beskrivning",
        sickLeaveCertificate.orElseThrow().diagnoseCode().description());
  }

  @Test
  void shouldNotMapBiDiagnoseCode1IfMissing() {
    final var sickLeaveCertificate = provider.build(ag7804CertificateBuilder().build(), false);
    assertNull(sickLeaveCertificate.orElseThrow().biDiagnoseCode1());
  }

  @Test
  void shouldNotMapBiDiagnoseCode2IfMissing() {
    final var sickLeaveCertificate = provider.build(ag7804CertificateBuilder().build(), false);
    assertNull(sickLeaveCertificate.orElseThrow().biDiagnoseCode2());
  }


  @Test
  void shouldMapSigningDoctorId() {
    final var sickLeaveCertificate = provider.build(ag7804CertificateBuilder().build(), false);
    assertEquals(ag7804CertificateBuilder().build().certificateMetaData().issuer().hsaId(),
        sickLeaveCertificate.orElseThrow().signingDoctorId());
  }

  @Test
  void shouldMapSigningDateTime() {
    final var sickLeaveCertificate = provider.build(ag7804CertificateBuilder().build(), false);
    assertEquals(ag7804CertificateBuilder().build().signed(),
        sickLeaveCertificate.orElseThrow().signingDateTime());
  }

  @Test
  void shouldMapDeleted() {
    final var sickLeaveCertificate = provider.build(ag7804CertificateBuilder().build(), false);
    assertEquals(ag7804CertificateBuilder().build().revoked(),
        sickLeaveCertificate.orElseThrow().deleted());
  }

  @Test
  void shouldMapWorkCapacities() {
    final var expectedDateRange = DateRange.builder()
        .dateRangeId(new FieldId(("EN_FJARDEDEL")))
        .from(LocalDate.now())
        .to(LocalDate.now().plusDays(30))
        .build();

    final var sickLeaveCertificate = provider.build(ag7804CertificateBuilder().build(), false);
    assertEquals(List.of(expectedDateRange), sickLeaveCertificate.orElseThrow().workCapacities());
  }

  @Test
  void shouldMapEmployment() {
    final var expectedEmployment = List.of(
        ElementValueCode.builder()
            .code("Nuvarande arbete")
            .codeId(new FieldId("NUVARANDE_ARBETE"))
            .build()
    );

    final var sickLeaveCertificate = provider.build(ag7804CertificateBuilder().build(), false);
    assertEquals(expectedEmployment, sickLeaveCertificate.orElseThrow().employment());
  }
}