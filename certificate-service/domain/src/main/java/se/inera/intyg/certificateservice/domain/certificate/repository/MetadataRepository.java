package se.inera.intyg.certificateservice.domain.certificate.repository;

import java.time.LocalDateTime;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateMetaData;

public interface MetadataRepository {

  CertificateMetaData getMetadataFromSignInstance(CertificateMetaData metadata,
      LocalDateTime signedAt);

}
