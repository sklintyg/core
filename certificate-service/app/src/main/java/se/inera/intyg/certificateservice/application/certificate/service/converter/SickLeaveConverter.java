package se.inera.intyg.certificateservice.application.certificate.service.converter;

import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.SickLeaveCertificateDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.SickLeaveCertificateWorkCapacityDTO;
import se.inera.intyg.certificateservice.domain.certificate.model.DateRange;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificate.model.SickLeaveCertificate;

@Component
public class SickLeaveConverter {

  public SickLeaveCertificateDTO convert(SickLeaveCertificate sickLeaveCertificate) {
    if (sickLeaveCertificate == null) {
      return null;
    }

    final var workCapacities = sickLeaveCertificate.workCapacities().stream()
        .map(this::mapDateRangeToWorkCapacity)
        .collect(Collectors.toList());

    final var employments = sickLeaveCertificate.employment().stream()
        .map(ElementValueCode::code)
        .filter(code -> code != null && !code.isBlank())
        .collect(Collectors.joining(","));

    return SickLeaveCertificateDTO.builder()
        .id(sickLeaveCertificate.id().id())
        .type(sickLeaveCertificate.type())
        .signingDoctorId(sickLeaveCertificate.signingDoctorId().id())
        .signingDoctorName(sickLeaveCertificate.signingDoctorName().fullName())
        .signingDateTime(sickLeaveCertificate.signingDateTime())
        .careUnitId(sickLeaveCertificate.careUnitId().id())
        .careUnitName(sickLeaveCertificate.careUnitName().name())
        .careGiverId(sickLeaveCertificate.careGiverId().id())
        .civicRegistrationNumber(sickLeaveCertificate.civicRegistrationNumber().id())
        .patientName(sickLeaveCertificate.patientName().fullName())
        .diagnoseCode(sickLeaveCertificate.diagnoseCode().code())
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
        .build();
  }

  private SickLeaveCertificateWorkCapacityDTO mapDateRangeToWorkCapacity(DateRange dateRange) {
    final var from = dateRange.from().format(DateTimeFormatter.ISO_LOCAL_DATE);
    final var to = dateRange.to().format(DateTimeFormatter.ISO_LOCAL_DATE);
    return SickLeaveCertificateWorkCapacityDTO.builder()
        .capacityPercentage(null)
        .fromDate(from)
        .toDate(to)
        .build();
  }
}