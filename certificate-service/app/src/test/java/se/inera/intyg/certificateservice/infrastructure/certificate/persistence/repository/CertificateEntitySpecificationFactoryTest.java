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
          .modifiedFrom(LocalDateTime.now(ZoneId.systemDefault()))
          .build();
      try (
          MockedStatic<CertificateEntitySpecification> specification = mockStatic(
              CertificateEntitySpecification.class)
      ) {
        specification.when(
                () -> CertificateEntitySpecification.modifiedEqualsAndGreaterThan(
                    certificatesRequest.modifiedFrom())
            )
            .thenReturn(mock(Specification.class));

        assertNotNull(certificateEntitySpecificationFactory.create(certificatesRequest));

        specification.verify(
            () -> CertificateEntitySpecification.modifiedEqualsAndGreaterThan(
                certificatesRequest.modifiedFrom())
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
          .modifiedTo(LocalDateTime.now(ZoneId.systemDefault()))
          .build();
      try (
          MockedStatic<CertificateEntitySpecification> specification = mockStatic(
              CertificateEntitySpecification.class)
      ) {
        specification.when(
                () -> CertificateEntitySpecification.modifiedEqualsAndLesserThan(
                    certificatesRequest.modifiedTo())
            )
            .thenReturn(mock(Specification.class));

        assertNotNull(certificateEntitySpecificationFactory.create(certificatesRequest));

        specification.verify(
            () -> CertificateEntitySpecification.modifiedEqualsAndLesserThan(
                certificatesRequest.modifiedTo())
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
    void shallIncludeCreatedFrom() {
      final var certificatesRequest = CertificatesRequest.builder()
          .createdFrom(LocalDateTime.now(ZoneId.systemDefault()))
          .build();
      try (
          MockedStatic<CertificateEntitySpecification> specification = mockStatic(
              CertificateEntitySpecification.class)
      ) {
        specification.when(
                () -> CertificateEntitySpecification.createdEqualsAndGreaterThan(
                    certificatesRequest.createdFrom())
            )
            .thenReturn(mock(Specification.class));

        assertNotNull(certificateEntitySpecificationFactory.create(certificatesRequest));

        specification.verify(
            () -> CertificateEntitySpecification.createdEqualsAndGreaterThan(
                certificatesRequest.createdFrom())
        );
      }
    }

    @Test
    void shallNotIncludeCreatedFrom() {
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
    void shallIncludeCreatedTo() {
      final var certificatesRequest = CertificatesRequest.builder()
          .createdTo(LocalDateTime.now(ZoneId.systemDefault()))
          .build();
      try (
          MockedStatic<CertificateEntitySpecification> specification = mockStatic(
              CertificateEntitySpecification.class)
      ) {
        specification.when(
                () -> CertificateEntitySpecification.createdEqualsAndLesserThan(
                    certificatesRequest.createdTo())
            )
            .thenReturn(mock(Specification.class));

        assertNotNull(certificateEntitySpecificationFactory.create(certificatesRequest));

        specification.verify(
            () -> CertificateEntitySpecification.createdEqualsAndLesserThan(
                certificatesRequest.createdTo())
        );
      }
    }

    @Test
    void shallNotIncludeCreatedTo() {
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
}