package se.inera.intyg.certificateservice.application.citizen.service.converter;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateMetadataDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateRecipientDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateRelationsDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateSummaryDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.StaffDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.UnitDTO;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.unit.model.CareUnit;
import se.inera.intyg.certificateservice.domain.unit.model.IssuingUnit;

@Component
public class CitizenCertificateMetadataConverter {

  public CertificateMetadataDTO convert(Certificate certificate) {
    return CertificateMetadataDTO.builder()
        .name(certificate.certificateModel().name())
        .type(certificate.certificateModel().id().type().type())
        .typeVersion(certificate.certificateModel().id().version().version())
        .typeName(certificate.certificateModel().type().code())
        .created(certificate.created())
        .issuedBy(convertStaff())
        .unit(convertUnit(certificate.certificateMetaData().issuingUnit()))
        .careUnit(convertCareUnit(certificate.certificateMetaData().careUnit()))
        .recipient(convertRecipient())
        .summary(convertSummary())
        .relations(convertRelations())
        .build();
  }

  private UnitDTO convertUnit(IssuingUnit issuingUnit) {
    return UnitDTO.builder()
        .unitId(issuingUnit.hsaId().id())
        .address(issuingUnit.address().address())
        .city(issuingUnit.address().city())
        .zipCode(issuingUnit.address().zipCode())
        .email(issuingUnit.contactInfo().email())
        .unitName(issuingUnit.name().name())
        .isInactive(issuingUnit.inactive().value())
        .phoneNumber(issuingUnit.contactInfo().phoneNumber())
        .build();
  }

  private UnitDTO convertCareUnit(CareUnit careUnit) {
    return UnitDTO.builder()
        .unitId(careUnit.hsaId().id())
        .address(careUnit.address().address())
        .city(careUnit.address().city())
        .zipCode(careUnit.address().zipCode())
        .email(careUnit.contactInfo().email())
        .unitName(careUnit.name().name())
        .isInactive(careUnit.inactive().value())
        .phoneNumber(careUnit.contactInfo().phoneNumber())
        .build();
  }

  private StaffDTO convertStaff() {
    return StaffDTO.builder().build();
  }

  private CertificateRecipientDTO convertRecipient() {
    return CertificateRecipientDTO.builder().build();
  }

  private CertificateSummaryDTO convertSummary(Certificate certificate) {
    return CertificateSummaryDTO.builder()
        .label()
        .value()
        .build();
  }

  private CertificateRelationsDTO convertRelations() {
    return CertificateRelationsDTO.builder().build();
  }
}
