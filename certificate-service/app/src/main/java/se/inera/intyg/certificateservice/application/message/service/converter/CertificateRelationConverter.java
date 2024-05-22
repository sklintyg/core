package se.inera.intyg.certificateservice.application.message.service.converter;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateRelationDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateRelationTypeDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO;
import se.inera.intyg.certificateservice.domain.certificate.model.Relation;

@Component
@RequiredArgsConstructor
public class CertificateRelationConverter {

  public CertificateRelationDTO convert(Optional<Relation> optionalRelation) {
    return optionalRelation.map(relation ->
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
