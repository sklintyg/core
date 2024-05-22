package se.inera.intyg.certificateservice.application.message.service.converter;

import java.util.Comparator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateRelationDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateRelationTypeDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Relation;
import se.inera.intyg.certificateservice.domain.certificate.model.RelationType;

@Component
@RequiredArgsConstructor
public class CertificateRelationConverter {

  public CertificateRelationDTO convert(Certificate certificate) {
    return certificate.children().stream()
        .filter(child -> child.type().equals(RelationType.COMPLEMENT))
        .max(Comparator.comparing(Relation::created))
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
