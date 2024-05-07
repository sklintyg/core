package se.inera.intyg.certificateservice.application.citizen.service.converter;

import java.util.List;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateMetadataDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateRecipientDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateRelationDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateRelationTypeDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateRelationsDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateSummaryDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.StaffDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.UnitDTO;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.staff.model.Staff;
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
        .issuedBy(convertStaff(certificate.certificateMetaData().issuer()))
        .unit(convertUnit(certificate.certificateMetaData().issuingUnit()))
        .careUnit(convertCareUnit(certificate.certificateMetaData().careUnit()))
        .recipient(convertRecipient(certificate))
        .summary(convertSummary(certificate))
        .relations(convertRelations(certificate))
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

  private StaffDTO convertStaff(Staff staff) {
    return StaffDTO.builder()
        .fullName(staff.name().fullName())
        .build();
  }

  private CertificateRecipientDTO convertRecipient(Certificate certificate) {
    return CertificateRecipientDTO.builder()
        .id(certificate.certificateModel().recipient().id().id())
        .name(certificate.certificateModel().recipient().name())
        .sent(certificate.sent().sentAt())
        .build();
  }

  private CertificateSummaryDTO convertSummary(Certificate certificate) {
    return CertificateSummaryDTO.builder()
        //TODO kolla upp hur vi får ut value och vart vi ska sätta label
        .build();
  }

  private CertificateRelationsDTO convertRelations(Certificate certificate) {
    return CertificateRelationsDTO.builder()
        .parent(convertParent(certificate))
        .children(convertChildren(certificate))
        .build();
  }

  private CertificateRelationDTO convertParent(Certificate certificate) {
    return CertificateRelationDTO.builder()
        .certificateId(certificate.parent().certificateId().id())
        .created(certificate.parent().created())
        .type(CertificateRelationTypeDTO.valueOf(certificate.parent().type().name()))
        .status(CertificateStatusTypeDTO.valueOf(certificate.parent().status().name()))
        .build();
  }

  private List<CertificateRelationDTO> convertChildren(Certificate certificate) {
    return null;
  }
}
