package se.inera.intyg.certificateservice.application.certificate.service;

import static se.inera.intyg.certificateservice.application.common.dto.PersonIdTypeDTO.toPersonIdTypeDTO;
import static se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationUnitContactInformation.UNIT_CONTACT_INFORMATION;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateRecipientDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateInternalXmlResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.RevokedDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.StaffDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.UnitDTO;
import se.inera.intyg.certificateservice.application.certificate.service.converter.CertificateUnitConverter;
import se.inera.intyg.certificateservice.application.common.dto.PersonIdDTO;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.Revoked;
import se.inera.intyg.certificateservice.domain.certificate.model.Sent;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.certificate.service.XmlGenerator;
import se.inera.intyg.certificateservice.domain.common.model.PersonId;
import se.inera.intyg.certificateservice.domain.staff.model.Staff;

@Service
@RequiredArgsConstructor
public class GetCertificateInternalXmlService {

  private final CertificateRepository certificateRepository;
  private final CertificateUnitConverter certificateUnitConverter;
  private final XmlGenerator xmlGenerator;

  public GetCertificateInternalXmlResponse get(String certificateId) {
    final var certificate = certificateRepository.getById(new CertificateId(certificateId));

    final var xml = certificate.xml() != null ? certificate.xml()
        : xmlGenerator.generate(certificate, false);

    return GetCertificateInternalXmlResponse.builder()
        .certificateId(certificate.id().id())
        .certificateType(certificate.certificateModel().id().type().type())
        .unit(
            certificateUnitConverter.convert(
                certificate.certificateMetaData().issuingUnit(),
                certificate.elementData().stream()
                    .filter(data -> data.id().equals(UNIT_CONTACT_INFORMATION))
                    .findFirst())
        )
        .careProvider(
            UnitDTO.builder()
                .unitId(certificate.certificateMetaData().careProvider().hsaId().id())
                .unitName(certificate.certificateMetaData().careProvider().name().name())
                .build()
        )
        .xml(xml.base64())
        .revoked(convertRevoked(Optional.ofNullable(certificate.revoked())))
        .recipient(convertRecipient(Optional.ofNullable(certificate.sent())))
        .patientId(convertPersonId(certificate.certificateMetaData().patient().id()))
        .build();
  }

  private PersonIdDTO convertPersonId(PersonId id) {
    return PersonIdDTO.builder()
        .id(id.id())
        .type(toPersonIdTypeDTO(id.type()))
        .build();
  }

  private CertificateRecipientDTO convertRecipient(Optional<Sent> sent) {
    return sent.map(
            send ->
                CertificateRecipientDTO.builder()
                    .sent(send.sentAt())
                    .id(send.recipient().id().id())
                    .name(send.recipient().name())
                    .build()
        )
        .orElse(null);
  }

  private RevokedDTO convertRevoked(Optional<Revoked> revoked) {
    return revoked.map(
            revoke ->
                RevokedDTO.builder()
                    .revokedAt(revoke.revokedAt())
                    .revokedBy(convertRevokedBy(revoke.revokedBy()))
                    .reason(revoke.revokedInformation().reason())
                    .message(revoke.revokedInformation().message())
                    .build()
        )
        .orElse(null);
  }

  private StaffDTO convertRevokedBy(Staff staff) {
    return StaffDTO.builder()
        .personId(staff.hsaId().id())
        .firstName(staff.name().firstName())
        .middleName(staff.name().middleName())
        .lastName(staff.name().lastName())
        .fullName(staff.name().fullName())
        .build();
  }
}
