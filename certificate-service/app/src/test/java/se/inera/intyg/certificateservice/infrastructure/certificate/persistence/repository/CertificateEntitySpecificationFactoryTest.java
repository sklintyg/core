package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.ATHENA_REACT_ANDERSSON;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataStaff.AJLA_DOKTOR;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnit.ALFA_ALLERGIMOTTAGNINGEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnit.ALFA_MEDICINSKT_CENTRUM;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.data.jpa.domain.Specification;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.common.model.CertificatesRequest;
import se.inera.intyg.certificateservice.domain.patient.model.CertificatesWithQARequest;

class CertificateEntitySpecificationFactoryTest {

  private CertificateEntitySpecificationFactory certificateEntitySpecificationFactory;

  @BeforeEach
  void setUp() {
    certificateEntitySpecificationFactory = new CertificateEntitySpecificationFactory();
  }

  @Nested
  class CertificateRequestTests {

    @Test
    void shallIncludePatientId() {
      final var certificatesRequest = CertificatesRequest.builder()
          .personId(ATHENA_REACT_ANDERSSON.id())
          .build();
      try (
          MockedStatic<PatientEntitySpecification> specification = mockStatic(
              PatientEntitySpecification.class)
      ) {
        specification.when(
                () -> PatientEntitySpecification.equalsPatient(certificatesRequest.personId()))
            .thenReturn(mock(Specification.class));

        assertNotNull(certificateEntitySpecificationFactory.create(certificatesRequest));

        specification.verify(
            () -> PatientEntitySpecification.equalsPatient(certificatesRequest.personId())
        );
      }
    }

    @Test
    void shallNotIncludePatientId() {
      final var certificatesRequest = CertificatesRequest.builder()
          .build();
      try (
          MockedStatic<PatientEntitySpecification> specification = mockStatic(
              PatientEntitySpecification.class)
      ) {
        assertNotNull(certificateEntitySpecificationFactory.create(certificatesRequest));

        specification.verifyNoInteractions();
      }
    }

    @Test
    void shallIncludeIssuedUnitId() {
      final var certificatesRequest = CertificatesRequest.builder()
          .issuedUnitId(ALFA_ALLERGIMOTTAGNINGEN.hsaId())
          .build();
      try (
          MockedStatic<UnitEntitySpecification> specification = mockStatic(
              UnitEntitySpecification.class)
      ) {
        specification.when(
                () -> UnitEntitySpecification.equalsIssuedOnUnit(certificatesRequest.issuedUnitId()))
            .thenReturn(mock(Specification.class));

        assertNotNull(certificateEntitySpecificationFactory.create(certificatesRequest));

        specification.verify(
            () -> UnitEntitySpecification.equalsIssuedOnUnit(certificatesRequest.issuedUnitId())
        );
      }
    }

    @Test
    void shallNotIncludeIssuedUnitId() {
      final var certificatesRequest = CertificatesRequest.builder()
          .build();
      try (
          MockedStatic<UnitEntitySpecification> specification = mockStatic(
              UnitEntitySpecification.class)
      ) {
        assertNotNull(certificateEntitySpecificationFactory.create(certificatesRequest));

        specification.verifyNoInteractions();
      }
    }

    @Test
    void shallIncludeCareUnitId() {
      final var certificatesRequest = CertificatesRequest.builder()
          .careUnitId(ALFA_MEDICINSKT_CENTRUM.hsaId())
          .build();
      try (
          MockedStatic<UnitEntitySpecification> specification = mockStatic(
              UnitEntitySpecification.class)
      ) {
        specification.when(
                () -> UnitEntitySpecification.equalsCareUnit(certificatesRequest.careUnitId()))
            .thenReturn(mock(Specification.class));

        assertNotNull(certificateEntitySpecificationFactory.create(certificatesRequest));

        specification.verify(
            () -> UnitEntitySpecification.equalsCareUnit(certificatesRequest.careUnitId())
        );
      }
    }

    @Test
    void shallNotIncludeCareUnitId() {
      final var certificatesRequest = CertificatesRequest.builder()
          .build();
      try (
          MockedStatic<UnitEntitySpecification> specification = mockStatic(
              UnitEntitySpecification.class)
      ) {
        assertNotNull(certificateEntitySpecificationFactory.create(certificatesRequest));

        specification.verifyNoInteractions();
      }
    }

    @Test
    void shallIncludeIssuedByStaffId() {
      final var certificatesRequest = CertificatesRequest.builder()
          .issuedByStaffId(AJLA_DOKTOR.hsaId())
          .build();
      try (
          MockedStatic<StaffEntitySpecification> specification = mockStatic(
              StaffEntitySpecification.class)
      ) {
        specification.when(
                () -> StaffEntitySpecification.equalsIssuedByStaff(
                    certificatesRequest.issuedByStaffId()))
            .thenReturn(mock(Specification.class));

        assertNotNull(certificateEntitySpecificationFactory.create(certificatesRequest));

        specification.verify(
            () -> StaffEntitySpecification.equalsIssuedByStaff(
                certificatesRequest.issuedByStaffId())
        );
      }
    }

    @Test
    void shallNotIncludeIssuedByStaffId() {
      final var certificatesRequest = CertificatesRequest.builder()
          .build();
      try (
          MockedStatic<StaffEntitySpecification> specification = mockStatic(
              StaffEntitySpecification.class)
      ) {
        assertNotNull(certificateEntitySpecificationFactory.create(certificatesRequest));

        specification.verifyNoInteractions();
      }
    }

    @Test
    void shallIncludeModifiedFrom() {
      final var certificatesRequest = CertificatesRequest.builder()
          .from(LocalDateTime.now(ZoneId.systemDefault()))
          .build();
      try (
          MockedStatic<CertificateEntitySpecification> specification = mockStatic(
              CertificateEntitySpecification.class)
      ) {
        specification.when(
                () -> CertificateEntitySpecification.modifiedEqualsAndGreaterThan(
                    certificatesRequest.from())
            )
            .thenReturn(mock(Specification.class));

        assertNotNull(certificateEntitySpecificationFactory.create(certificatesRequest));

        specification.verify(
            () -> CertificateEntitySpecification.modifiedEqualsAndGreaterThan(
                certificatesRequest.from())
        );
      }
    }

    @Test
    void shallNotIncludeModifiedFrom() {
      final var certificatesRequest = CertificatesRequest.builder()
          .build();
      try (
          MockedStatic<CertificateEntitySpecification> specification = mockStatic(
              CertificateEntitySpecification.class)
      ) {
        assertNotNull(certificateEntitySpecificationFactory.create(certificatesRequest));

        specification.verifyNoInteractions();
      }
    }

    @Test
    void shallIncludeModifiedTo() {
      final var certificatesRequest = CertificatesRequest.builder()
          .to(LocalDateTime.now(ZoneId.systemDefault()))
          .build();
      try (
          MockedStatic<CertificateEntitySpecification> specification = mockStatic(
              CertificateEntitySpecification.class)
      ) {
        specification.when(
                () -> CertificateEntitySpecification.modifiedEqualsAndLesserThan(
                    certificatesRequest.to())
            )
            .thenReturn(mock(Specification.class));

        assertNotNull(certificateEntitySpecificationFactory.create(certificatesRequest));

        specification.verify(
            () -> CertificateEntitySpecification.modifiedEqualsAndLesserThan(
                certificatesRequest.to())
        );
      }
    }

    @Test
    void shallNotIncludeModifiedTo() {
      final var certificatesRequest = CertificatesRequest.builder()
          .build();
      try (
          MockedStatic<CertificateEntitySpecification> specification = mockStatic(
              CertificateEntitySpecification.class)
      ) {
        assertNotNull(certificateEntitySpecificationFactory.create(certificatesRequest));

        specification.verifyNoInteractions();
      }
    }

    @Test
    void shallIncludeStatuses() {
      final var certificatesRequest = CertificatesRequest.builder()
          .statuses(List.of(Status.SIGNED))
          .build();
      try (
          MockedStatic<StatusEntitySpecification> specification = mockStatic(
              StatusEntitySpecification.class)
      ) {
        specification.when(
                () -> StatusEntitySpecification.containsStatus(certificatesRequest.statuses()))
            .thenReturn(mock(Specification.class));

        assertNotNull(certificateEntitySpecificationFactory.create(certificatesRequest));

        specification.verify(
            () -> StatusEntitySpecification.containsStatus(certificatesRequest.statuses())
        );
      }
    }

    @Test
    void shallNotIncludeStatusesIfNull() {
      final var certificatesRequest = CertificatesRequest.builder()
          .build();
      try (
          MockedStatic<StatusEntitySpecification> specification = mockStatic(
              StatusEntitySpecification.class)
      ) {
        assertNotNull(certificateEntitySpecificationFactory.create(certificatesRequest));
        specification.verifyNoInteractions();
      }
    }

    @Test
    void shallNotIncludeStatusesIfEmptyList() {
      final var certificatesRequest = CertificatesRequest.builder()
          .statuses(Collections.emptyList())
          .build();
      try (
          MockedStatic<StatusEntitySpecification> specification = mockStatic(
              StatusEntitySpecification.class)
      ) {
        assertNotNull(certificateEntitySpecificationFactory.create(certificatesRequest));
        specification.verifyNoInteractions();
      }
    }
  }

  @Nested
  class CertificatesWithQARequestTests {

    @Test
    void shallIncludePatientId() {
      final var certificatesRequest = CertificatesWithQARequest.builder()
          .personId(ATHENA_REACT_ANDERSSON.id())
          .build();
      try (
          MockedStatic<PatientEntitySpecification> specification = mockStatic(
              PatientEntitySpecification.class)
      ) {
        specification.when(
                () -> PatientEntitySpecification.equalsPatient(certificatesRequest.personId()))
            .thenReturn(mock(Specification.class));

        assertNotNull(certificateEntitySpecificationFactory.create(certificatesRequest));

        specification.verify(
            () -> PatientEntitySpecification.equalsPatient(certificatesRequest.personId())
        );
      }
    }

    @Test
    void shallIncludeCareProviderId() {
      final var certificatesRequest = CertificatesWithQARequest.builder()
          .careProviderId(ALFA_MEDICINSKT_CENTRUM.hsaId())
          .build();
      try (
          MockedStatic<UnitEntitySpecification> specification = mockStatic(
              UnitEntitySpecification.class)
      ) {
        specification.when(
                () -> UnitEntitySpecification.equalsCareProvider(certificatesRequest.careProviderId()))
            .thenReturn(mock(Specification.class));

        assertNotNull(certificateEntitySpecificationFactory.create(certificatesRequest));

        specification.verify(
            () -> UnitEntitySpecification.equalsCareProvider(certificatesRequest.careProviderId())
        );
      }
    }

    @Test
    void shallNotIncludeCareProviderId() {
      final var certificatesRequest = CertificatesWithQARequest.builder()
          .build();
      try (
          MockedStatic<UnitEntitySpecification> specification = mockStatic(
              UnitEntitySpecification.class)
      ) {
        assertNotNull(certificateEntitySpecificationFactory.create(certificatesRequest));

        specification.verifyNoInteractions();
      }
    }

    @Test
    void shallIncludeIssuedOnUnitIds() {
      final var certificatesRequest = CertificatesWithQARequest.builder()
          .unitIds(List.of(ALFA_MEDICINSKT_CENTRUM.hsaId()))
          .build();
      try (
          MockedStatic<UnitEntitySpecification> specification = mockStatic(
              UnitEntitySpecification.class)
      ) {
        specification.when(
                () -> UnitEntitySpecification.issuedOnUnitIdIn(certificatesRequest.unitIds()))
            .thenReturn(mock(Specification.class));

        assertNotNull(certificateEntitySpecificationFactory.create(certificatesRequest));

        specification.verify(
            () -> UnitEntitySpecification.issuedOnUnitIdIn(certificatesRequest.unitIds())
        );
      }
    }

    @Test
    void shallNotIncludeIssuedOnUnitIds() {
      final var certificatesRequest = CertificatesWithQARequest.builder()
          .build();
      try (
          MockedStatic<UnitEntitySpecification> specification = mockStatic(
              UnitEntitySpecification.class)
      ) {
        assertNotNull(certificateEntitySpecificationFactory.create(certificatesRequest));

        specification.verifyNoInteractions();
      }
    }
  }
}