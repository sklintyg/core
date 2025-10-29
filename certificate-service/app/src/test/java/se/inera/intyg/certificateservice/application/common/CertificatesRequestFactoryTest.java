package se.inera.intyg.certificateservice.application.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.ATHENA_REACT_ANDERSSON;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataStaff.AJLA_DOKTOR;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnit.ALFA_MEDICINSKT_CENTRUM;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.GetSickLeaveCertificatesInternalRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.PersonIdDTO;
import se.inera.intyg.certificateservice.application.unit.dto.CertificatesQueryCriteriaDTO;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.common.model.CertificatesRequest;
import se.inera.intyg.certificateservice.domain.testdata.TestDataPatient;

class CertificatesRequestFactoryTest {

  @Test
  void shallCreateDefaultCertificatesRequest() {
    final var expectedCertificateRequest = CertificatesRequest.builder()
        .statuses(Status.unsigned())
        .build();

    assertEquals(expectedCertificateRequest,
        CertificatesRequestFactory.create()
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
          CertificatesRequestFactory.create(queryCriteriaDTO).modifiedFrom()
      );
    }

    @Test
    void shallIncludeTo() {
      final var expectedTo = LocalDateTime.now(ZoneId.systemDefault());
      final var queryCriteriaDTO = CertificatesQueryCriteriaDTO.builder()
          .to(expectedTo)
          .build();

      assertEquals(expectedTo,
          CertificatesRequestFactory.create(queryCriteriaDTO).modifiedTo()
      );
    }

    @Test
    void shallIncludeIssuedByStaffIds() {
      final var expectedIssuedByStaffId = List.of(AJLA_DOKTOR.hsaId());
      final var queryCriteriaDTO = CertificatesQueryCriteriaDTO.builder()
          .issuedByStaffId(AJLA_DOKTOR.hsaId().id())
          .build();

      assertEquals(expectedIssuedByStaffId,
          CertificatesRequestFactory.create(queryCriteriaDTO).issuedByStaffIds()
      );
    }

    @Test
    void shallNotIncludeIssuedByStaffIds() {
      final var queryCriteriaDTO = CertificatesQueryCriteriaDTO.builder()
          .build();

      assertNull(CertificatesRequestFactory.create(queryCriteriaDTO).issuedByStaffIds());
    }

    @Test
    void shallNotIncludeIssuedUnitId() {
      final var queryCriteriaDTO = CertificatesQueryCriteriaDTO.builder()
          .build();

      assertNull(CertificatesRequestFactory.create(queryCriteriaDTO).issuedUnitIds());
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
          CertificatesRequestFactory.create(queryCriteriaDTO).personId()
      );
    }

    @Test
    void shallNotIncludePersonId() {
      final var queryCriteriaDTO = CertificatesQueryCriteriaDTO.builder()
          .build();

      assertNull(CertificatesRequestFactory.create(queryCriteriaDTO).personId());
    }

    @Test
    void shallIncludeEmptyListIfStatusesEmpty() {
      final var queryCriteriaDTO = CertificatesQueryCriteriaDTO.builder()
          .statuses(Collections.emptyList())
          .build();

      assertEquals(Collections.emptyList(),
          CertificatesRequestFactory.create(queryCriteriaDTO).statuses()
      );
    }

    @Test
    void shallIncludeEmptyListIfStatusesNull() {
      final var queryCriteriaDTO = CertificatesQueryCriteriaDTO.builder()
          .build();

      assertEquals(Collections.emptyList(),
          CertificatesRequestFactory.create(queryCriteriaDTO).statuses()
      );
    }

    @Test
    void shallIncludeStatusDRAFTIfStatusesContainsUNSIGNED() {
      final var expectedStatuses = List.of(Status.DRAFT);
      final var queryCriteriaDTO = CertificatesQueryCriteriaDTO.builder()
          .statuses(List.of(CertificateStatusTypeDTO.UNSIGNED))
          .build();

      assertEquals(expectedStatuses,
          CertificatesRequestFactory.create(queryCriteriaDTO).statuses()
      );
    }

    @Test
    void shallIncludeStatusSIGNEDTIfStatusesContainsSIGNED() {
      final var expectedStatuses = List.of(Status.SIGNED);
      final var queryCriteriaDTO = CertificatesQueryCriteriaDTO.builder()
          .statuses(List.of(CertificateStatusTypeDTO.SIGNED))
          .build();

      assertEquals(expectedStatuses,
          CertificatesRequestFactory.create(queryCriteriaDTO).statuses()
      );
    }

    @Test
    void shallIncludeStatusLOCKED_DRAFTTIfStatusesContainsLOCKED() {
      final var expectedStatuses = List.of(Status.LOCKED_DRAFT);
      final var queryCriteriaDTO = CertificatesQueryCriteriaDTO.builder()
          .statuses(List.of(CertificateStatusTypeDTO.LOCKED))
          .build();

      assertEquals(expectedStatuses,
          CertificatesRequestFactory.create(queryCriteriaDTO).statuses()
      );
    }

    @Test
    void shallNotIncludeValidCertificateIfValidForSignIsNull() {
      final var queryCriteriaDTO = CertificatesQueryCriteriaDTO.builder().build();

      assertNull(CertificatesRequestFactory.create(queryCriteriaDTO).validCertificates());
    }

    @Test
    void shallIncludeValidCertificateTrueIfValidForSignIsTrue() {
      final var queryCriteriaDTO = CertificatesQueryCriteriaDTO.builder()
          .validForSign(Boolean.TRUE)
          .build();

      Assertions.assertTrue(
          CertificatesRequestFactory.create(queryCriteriaDTO).validCertificates());
    }

    @Test
    void shallIncludeValidCertificateFalseIfValidForSignIsFalse() {
      final var queryCriteriaDTO = CertificatesQueryCriteriaDTO.builder()
          .validForSign(Boolean.FALSE)
          .build();

      Assertions.assertFalse(
          CertificatesRequestFactory.create(queryCriteriaDTO).validCertificates());
    }
  }

  @Nested
  class ConvertFromGetSickLeaveCertificatesInternalRequest {

    private static final String TYPE = "type";
    private static final PersonIdDTO PERSON_ID = PersonIdDTO.builder()
        .id(TestDataPatient.ATHENA_REACT_ANDERSSON.id().id())
        .type(TestDataPatient.ATHENA_REACT_ANDERSSON.id().type().name())
        .build();

    @Test
    void shouldIncludePersonId() {
      final var request = GetSickLeaveCertificatesInternalRequest.builder()
          .personId(
              PERSON_ID
          )
          .build();

      assertEquals(ATHENA_REACT_ANDERSSON.id(),
          CertificatesRequestFactory.convert(request).personId());
    }

    @Test
    void shouldIncludeSignedFrom() {
      final var expected = LocalDateTime.now().toLocalDate();
      final var request = GetSickLeaveCertificatesInternalRequest.builder()
          .personId(PERSON_ID)
          .signedFrom(expected)
          .build();

      assertEquals(expected, CertificatesRequestFactory.convert(request).signedFrom());
    }

    @Test
    void shouldIncludeSignedTo() {
      final var expected = LocalDateTime.now().toLocalDate();
      final var request = GetSickLeaveCertificatesInternalRequest.builder()
          .personId(PERSON_ID)
          .signedTo(expected)
          .build();

      assertEquals(expected, CertificatesRequestFactory.convert(request).signedTo());
    }

    @Test
    void shouldIncludeCertificateTypes() {
      final var expected = List.of(new CertificateType(TYPE));
      final var request = GetSickLeaveCertificatesInternalRequest.builder()
          .personId(PERSON_ID)
          .certificateTypes(List.of(TYPE))
          .build();

      assertEquals(expected, CertificatesRequestFactory.convert(request).types());
    }

    @Test
    void shouldIncludeIssuedByUnitIds() {
      final var expected = List.of(ALFA_MEDICINSKT_CENTRUM.hsaId());
      final var request = GetSickLeaveCertificatesInternalRequest.builder()
          .personId(PERSON_ID)
          .issuedByUnitIds(List.of(ALFA_MEDICINSKT_CENTRUM.hsaId().id()))
          .build();

      assertEquals(expected, CertificatesRequestFactory.convert(request).issuedUnitIds());
    }

    @Test
    void shouldIncludeIssuedByStaffIds() {
      final var expected = List.of(AJLA_DOKTOR.hsaId());
      final var request = GetSickLeaveCertificatesInternalRequest.builder()
          .personId(PERSON_ID)
          .issuedByStaffIds(List.of(AJLA_DOKTOR.hsaId().id()))
          .build();

      assertEquals(expected, CertificatesRequestFactory.convert(request).issuedByStaffIds());
    }
  }
}

