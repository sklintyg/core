package se.inera.intyg.certificateservice.application.certificate.service.converter;

import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.SickLeaveCertificateDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.SickLeaveCertificateItemDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.SickLeaveCertificateItemWorkCapacityDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.SickLeaveCertificateWorkCapacityDTO;
import se.inera.intyg.certificateservice.domain.certificate.model.DateRange;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosis;
import se.inera.intyg.certificateservice.domain.certificate.model.SickLeaveCertificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

@Component
public class SickLeaveConverter {

  public SickLeaveCertificateItemDTO toSickLeaveCertificateItem(
      SickLeaveCertificate sickLeaveCertificate) {
    if (sickLeaveCertificate == null) {
      return null;
    }

    final var workCapacities = sickLeaveCertificate.workCapacities().stream()
        .map(this::mapDateRangeToItemWorkCapacity)
        .toList();

    final var employments = sickLeaveCertificate.employment().stream()
        .map(ElementValueCode::code)
        .filter(code -> code != null && !code.isBlank())
        .collect(Collectors.joining(","));

    return SickLeaveCertificateItemDTO.builder()
        .certificateId(sickLeaveCertificate.id().id())
        .certificateType(sickLeaveCertificate.type().code().toUpperCase())
        .signingDateTime(sickLeaveCertificate.signingDateTime())
        .personalHsaId(sickLeaveCertificate.signingDoctorId().id())
        .personalFullName(sickLeaveCertificate.signingDoctorName().fullName())
        .careUnitId(sickLeaveCertificate.issuingUnitId().id())
        .careUnitName(sickLeaveCertificate.issuingUnitName().name())
        .careProviderId(sickLeaveCertificate.careGiverId().id())
        .personId(sickLeaveCertificate.civicRegistrationNumber().idWithDash())
        .patientFullName(sickLeaveCertificate.patientName().fullName())
        .diagnoseCode(
            sickLeaveCertificate.diagnoseCode() != null ? sickLeaveCertificate.diagnoseCode().code()
                : null)
        .secondaryDiagnoseCodes(
            Stream.of(
                    sickLeaveCertificate.biDiagnoseCode1(),
                    sickLeaveCertificate.biDiagnoseCode2()
                ).filter(Objects::nonNull)
                .map(ElementValueDiagnosis::code)
                .toList()
        )
        .occupation(employments)
        .deleted(sickLeaveCertificate.deleted() != null)
        .workCapacityList(workCapacities)
        .testCertificate(false)
        .build();
  }

  public SickLeaveCertificateDTO toSickLeaveCertificate(SickLeaveCertificate sickLeaveCertificate) {
    if (sickLeaveCertificate == null) {
      return null;
    }

    final var workCapacities =
        sickLeaveCertificate.workCapacities()
            .stream()
            .map(this::mapDateRangeToWorkCapacity)
            .toList();

    final var employments =
        sickLeaveCertificate.employment().stream()
            .map(ElementValueCode::code)
            .filter(code -> code != null && !code.isBlank())
            .collect(Collectors.joining(","));

    return SickLeaveCertificateDTO.builder()
        .id(sickLeaveCertificate.id().id())
        .type(sickLeaveCertificate.type().code().toLowerCase())
        .signingDoctorId(sickLeaveCertificate.signingDoctorId().id())
        .signingDoctorName(sickLeaveCertificate.signingDoctorName().fullName())
        .signingDateTime(sickLeaveCertificate.signingDateTime())
        .careUnitId(sickLeaveCertificate.issuingUnitId().id())
        .careUnitName(sickLeaveCertificate.issuingUnitName().name())
        .careGiverId(sickLeaveCertificate.careGiverId().id())
        .civicRegistrationNumber(sickLeaveCertificate.civicRegistrationNumber().idWithDash())
        .patientName(sickLeaveCertificate.patientName().fullName())
        .diagnoseCode(
            sickLeaveCertificate.diagnoseCode() != null ? sickLeaveCertificate.diagnoseCode().code()
                : null)
        .biDiagnoseCode1(
            sickLeaveCertificate.biDiagnoseCode1() != null
                ? sickLeaveCertificate.biDiagnoseCode1().code()
                : null
        )
        .biDiagnoseCode2(
            sickLeaveCertificate.biDiagnoseCode2() != null
                ? sickLeaveCertificate.biDiagnoseCode2().code()
                : null
        )
        .employment(employments)
        .deleted(sickLeaveCertificate.deleted() != null)
        .sjukfallCertificateWorkCapacity(workCapacities)
        .testCertificate(false)
        .extendsCertificateId(sickLeaveCertificate.extendsCertificateId())
        .build();
  }

  private SickLeaveCertificateItemWorkCapacityDTO mapDateRangeToItemWorkCapacity(
      DateRange dateRange) {
    final var from = dateRange.from();
    final var to = dateRange.to();
    return SickLeaveCertificateItemWorkCapacityDTO.builder()
        .reduction(toCapacityPercentage(dateRange.dateRangeId()))
        .startDate(from)
        .endDate(to)
        .build();
  }

  private SickLeaveCertificateWorkCapacityDTO mapDateRangeToWorkCapacity(DateRange dateRange) {
    final var from =
        dateRange.from() != null ? dateRange.from().format(DateTimeFormatter.ISO_LOCAL_DATE) : null;
    final var to =
        dateRange.to() != null ? dateRange.to().format(DateTimeFormatter.ISO_LOCAL_DATE) : null;
    return SickLeaveCertificateWorkCapacityDTO.builder()
        .capacityPercentage(toCapacityPercentage(dateRange.dateRangeId()))
        .fromDate(from)
        .toDate(to)
        .build();
  }

  private int toCapacityPercentage(FieldId fieldId) {
    try {
      return Integer.parseInt(fieldId.value());
    } catch (NumberFormatException e) {
      return switch (SickLeaveWorkcapacity.valueOf(fieldId.value())) {
        case EN_FJARDEDEL -> 25;
        case HALFTEN -> 50;
        case TRE_FJARDEDEL -> 75;
        case HELT_NEDSATT -> 100;
      };
    }
  }

  enum SickLeaveWorkcapacity {
    HELT_NEDSATT, TRE_FJARDEDEL, HALFTEN, EN_FJARDEDEL
  }
}