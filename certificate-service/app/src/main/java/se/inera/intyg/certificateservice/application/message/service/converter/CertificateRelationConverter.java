package se.inera.intyg.certificateservice.application.message.service.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateRelationDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateRelationTypeDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.RelationType;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;

@Component
@RequiredArgsConstructor
public class CertificateRelationConverter {

  private final CertificateRepository certificateRepository;

  public CertificateRelationDTO convert(CertificateId certificateId) {
    final var certificate = certificateRepository.getById(certificateId);
    return certificate.children().stream()
        .filter(child -> child.type().equals(RelationType.COMPLEMENT))
        .findFirst()
        .map(relation ->
            CertificateRelationDTO.builder()
                .certificateId(relation.certificate().id().id())
                .status(CertificateStatusTypeDTO.toType(relation.certificate().status()))
                .created(relation.created())
                .type(CertificateRelationTypeDTO.toType(relation.type()))
                .build()
        )
        .orElse(null);
  }
}
