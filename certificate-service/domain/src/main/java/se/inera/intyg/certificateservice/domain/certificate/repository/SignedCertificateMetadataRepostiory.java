package se.inera.intyg.certificateservice.domain.certificate.repository;

import java.time.LocalDateTime;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateMetaData;

public interface SignedCertificateMetadataRepostiory {

  CertificateMetaData getMetadataWhenSigned(CertificateMetaData metadata,
      LocalDateTime signedAt);
}
