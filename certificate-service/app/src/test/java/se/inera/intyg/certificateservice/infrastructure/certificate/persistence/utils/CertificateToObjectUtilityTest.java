package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.CareProvider;
import se.inera.intyg.certificateservice.domain.certificate.model.CareUnit;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateMetaData;
import se.inera.intyg.certificateservice.domain.certificate.model.HsaId;
import se.inera.intyg.certificateservice.domain.certificate.model.Staff;
import se.inera.intyg.certificateservice.domain.certificate.model.SubUnit;
import se.inera.intyg.certificateservice.domain.certificate.model.UnitName;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.patient.model.Patient;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.Unit;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.UnitType;

class CertificateToObjectUtilityTest {

  private static final Certificate CERTIFICATE = Certificate.builder()
      .certificateModel(CertificateModel.builder().build())
      .certificateMetaData(
          CertificateMetaData.builder()
              .issuer(Staff.builder().build())
              .patient(Patient.builder().build())
              .issuingUnit(SubUnit.builder()
                  .name(new UnitName("NAME"))
                  .hsaId(new HsaId("HSA_ID"))
                  .build())
              .careProvider(CareProvider.builder()
                  .hsaId(new HsaId("HSA_ID_PROVIDER"))
                  .name(new UnitName("NAME_PROVIDER"))
                  .build())
              .careUnit(CareUnit.builder()
                  .hsaId(new HsaId("HSA_ID_CARE_UNIT"))
                  .name(new UnitName("NAME_CARE_UNIT"))
                  .build())
              .build()
      )
      .build();

  private static final Certificate CERTIFICATE_WITH_CARE_UNIT = Certificate.builder()
      .certificateMetaData(
          CertificateMetaData.builder()
              .issuer(Staff.builder().build())
              .patient(Patient.builder().build())
              .issuingUnit(CareUnit.builder()
                  .name(new UnitName("NAME"))
                  .hsaId(new HsaId("HSA_ID"))
                  .build())
              .build()
      )
      .build();

  @Test
  void shouldReturnIssuer() {
    final var response = CertificateToObjectUtility.getIssuer(CERTIFICATE);

    assertEquals(CERTIFICATE.certificateMetaData().issuer(), response);
  }

  @Test
  void shouldReturnPatient() {
    final var response = CertificateToObjectUtility.getPatient(CERTIFICATE);

    assertEquals(CERTIFICATE.certificateMetaData().patient(), response);
  }

  @Test
  void shouldReturnModel() {
    final var response = CertificateToObjectUtility.getCertificateModel(CERTIFICATE);

    assertEquals(CERTIFICATE.certificateModel(), response);
  }

  @Test
  void shouldReturnIssuingUnitAsSubUnit() {
    final var expected = Unit.builder()
        .type(UnitType.CARE_UNIT)
        .name("NAME")
        .hsaId("HSA_ID")
        .build();

    final var response = CertificateToObjectUtility.getIssuingUnit(CERTIFICATE_WITH_CARE_UNIT);

    assertEquals(expected, response);
  }

  @Test
  void shouldReturnCareProvider() {
    final var expected = Unit.builder()
        .type(UnitType.CARE_PROVIDER)
        .name("NAME_PROVIDER")
        .hsaId("HSA_ID_PROVIDER")
        .build();

    final var response = CertificateToObjectUtility.getCareProvider(CERTIFICATE);

    assertEquals(expected, response);
  }

  @Test
  void shouldReturnCareUnit() {
    final var expected = Unit.builder()
        .type(UnitType.CARE_UNIT)
        .name("NAME_CARE_UNIT")
        .hsaId("HSA_ID_CARE_UNIT")
        .build();

    final var response = CertificateToObjectUtility.getCareUnit(CERTIFICATE);

    assertEquals(expected, response);
  }

}