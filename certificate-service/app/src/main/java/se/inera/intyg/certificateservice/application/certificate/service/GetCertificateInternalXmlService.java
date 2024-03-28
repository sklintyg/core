package se.inera.intyg.certificateservice.application.certificate.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateRecipientDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateInternalXmlResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.RevokedDTO;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.Revoked;
import se.inera.intyg.certificateservice.domain.certificate.model.Sent;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;

@Service
@RequiredArgsConstructor
public class GetCertificateInternalXmlService {

  private final CertificateRepository certificateRepository;

  public GetCertificateInternalXmlResponse get(String certificateId) {
    final var certificate = certificateRepository.getById(new CertificateId(certificateId));

    return GetCertificateInternalXmlResponse.builder()
        .certificateId(certificate.id().id())
        .certificateType(certificate.certificateModel().id().type().type())
        .unitId(certificate.certificateMetaData().issuingUnit().hsaId().id())
        .xml(certificate.xml().base64())
        .revoked(convertRevoked(Optional.of(certificate.revoked())))
        .recipient(convertRecipient(Optional.of(certificate.sent())))
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
                    .revokedBy(revoke.revokedBy())
                    .reason(revoke.revokedInformation().reason())
                    .message(revoke.revokedInformation().message())
                    .build()
        )
        .orElse(null);
  }
}
