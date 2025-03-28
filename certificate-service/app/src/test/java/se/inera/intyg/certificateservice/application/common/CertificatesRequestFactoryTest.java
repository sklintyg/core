package se.inera.intyg.certificateservice.application.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.ATHENA_REACT_ANDERSSON;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataStaff.AJLA_DOKTOR;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.PersonIdDTO;
import se.inera.intyg.certificateservice.application.unit.dto.CertificatesQueryCriteriaDTO;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.common.model.CertificatesRequest;

class CertificatesRequestFactoryTest {

  private CertificatesRequestFactory certificatesRequestFactory;

  @BeforeEach
  void setUp() {
    certificatesRequestFactory = new CertificatesRequestFactory();
  }

  @Test
  void shallCreateDefaultCertificatesRequest() {
    final var expectedCertificateRequest = CertificatesRequest.builder()
        .statuses(Status.unsigned())
        .build();

    assertEquals(expectedCertificateRequest,
        certificatesRequestFactory.create()
    );
  }

  @Nested
  class CreateFromCertificatesQueryCriteriaDTO {

    @Test
    void shallIncludeFrom() {
      final var expectedFrom = LocalDateTime.now(ZoneId.systemDefault());
      final var queryCriteriaDTO = CertificatesQueryCriteriaDTO.builder()
          .from(expectedFrom)
          .build();

      assertEquals(expectedFrom,
          certificatesRequestFactory.create(queryCriteriaDTO).modifiedFrom()
      );
    }

    @Test
    void shallIncludeTo() {
      final var expectedTo = LocalDateTime.now(ZoneId.systemDefault());
      final var queryCriteriaDTO = CertificatesQueryCriteriaDTO.builder()
          .to(expectedTo)
          .build();

      assertEquals(expectedTo,
          certificatesRequestFactory.create(queryCriteriaDTO).modifiedTo()
      );
    }

    @Test
    void shallIncludeIssuedByStaffId() {
      final var expectedIssuedByStaffId = AJLA_DOKTOR.hsaId();
      final var queryCriteriaDTO = CertificatesQueryCriteriaDTO.builder()
          .issuedByStaffId(expectedIssuedByStaffId.id())
          .build();

      assertEquals(expectedIssuedByStaffId,
          certificatesRequestFactory.create(queryCriteriaDTO).issuedByStaffId()
      );
    }

    @Test
    void shallNotIncludeIssuedByStaffId() {
      final var queryCriteriaDTO = CertificatesQueryCriteriaDTO.builder()
          .build();

      assertNull(certificatesRequestFactory.create(queryCriteriaDTO).issuedByStaffId());
    }

    @Test
    void shallNotIncludeIssuedUnitId() {
      final var queryCriteriaDTO = CertificatesQueryCriteriaDTO.builder()
          .build();

      assertNull(certificatesRequestFactory.create(queryCriteriaDTO).issuedUnitId());
    }

    @Test
    void shallIncludePersonId() {
      final var expectedPersonId = ATHENA_REACT_ANDERSSON.id();
      final var queryCriteriaDTO = CertificatesQueryCriteriaDTO.builder()
          .personId(
              PersonIdDTO.builder()
                  .id(expectedPersonId.id())
                  .type(expectedPersonId.type().name())
                  .build()
          )
          .build();

      assertEquals(expectedPersonId,
          certificatesRequestFactory.create(queryCriteriaDTO).personId()
      );
    }

    @Test
    void shallNotIncludePersonId() {
      final var queryCriteriaDTO = CertificatesQueryCriteriaDTO.builder()
          .build();

      assertNull(certificatesRequestFactory.create(queryCriteriaDTO).personId());
    }

    @Test
    void shallIncludeEmptyListIfStatusesEmpty() {
      final var queryCriteriaDTO = CertificatesQueryCriteriaDTO.builder()
          .statuses(Collections.emptyList())
          .build();

      assertEquals(Collections.emptyList(),
          certificatesRequestFactory.create(queryCriteriaDTO).statuses()
      );
    }

    @Test
    void shallIncludeEmptyListIfStatusesNull() {
      final var queryCriteriaDTO = CertificatesQueryCriteriaDTO.builder()
          .build();

      assertEquals(Collections.emptyList(),
          certificatesRequestFactory.create(queryCriteriaDTO).statuses()
      );
    }

    @Test
    void shallIncludeStatusDRAFTIfStatusesContainsUNSIGNED() {
      final var expectedStatuses = List.of(Status.DRAFT);
      final var queryCriteriaDTO = CertificatesQueryCriteriaDTO.builder()
          .statuses(List.of(CertificateStatusTypeDTO.UNSIGNED))
          .build();

      assertEquals(expectedStatuses,
          certificatesRequestFactory.create(queryCriteriaDTO).statuses()
      );
    }

    @Test
    void shallIncludeStatusSIGNEDTIfStatusesContainsSIGNED() {
      final var expectedStatuses = List.of(Status.SIGNED);
      final var queryCriteriaDTO = CertificatesQueryCriteriaDTO.builder()
          .statuses(List.of(CertificateStatusTypeDTO.SIGNED))
          .build();

      assertEquals(expectedStatuses,
          certificatesRequestFactory.create(queryCriteriaDTO).statuses()
      );
    }

    @Test
    void shallIncludeStatusLOCKED_DRAFTTIfStatusesContainsLOCKED() {
      final var expectedStatuses = List.of(Status.LOCKED_DRAFT);
      final var queryCriteriaDTO = CertificatesQueryCriteriaDTO.builder()
          .statuses(List.of(CertificateStatusTypeDTO.LOCKED))
          .build();

      assertEquals(expectedStatuses,
          certificatesRequestFactory.create(queryCriteriaDTO).statuses()
      );
    }

    @Test
    void shallNotIncludeValidCertificateIfValidForSignIsNull() {
      final var queryCriteriaDTO = CertificatesQueryCriteriaDTO.builder().build();

      assertNull(certificatesRequestFactory.create(queryCriteriaDTO).validCertificates());
    }

    @Test
    void shallIncludeValidCertificateTrueIfValidForSignIsTrue() {
      final var queryCriteriaDTO = CertificatesQueryCriteriaDTO.builder()
          .validForSign(Boolean.TRUE)
          .build();

      Assertions.assertTrue(
          certificatesRequestFactory.create(queryCriteriaDTO).validCertificates());
    }

    @Test
    void shallIncludeValidCertificateFalseIfValidForSignIsFalse() {
      final var queryCriteriaDTO = CertificatesQueryCriteriaDTO.builder()
          .validForSign(Boolean.FALSE)
          .build();

      Assertions.assertFalse(
          certificatesRequestFactory.create(queryCriteriaDTO).validCertificates());
    }
  }
}