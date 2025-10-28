package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public record CertificateVersion(String version) {

  public boolean isLastestActiveVersion(List<CertificateVersion> certificateVersions) {
    return certificateVersions.stream()
        .map(CertificateVersion::version)
        .filter(Objects::nonNull)
        .map(BigDecimal::new)
        .max(Comparator.naturalOrder())
        .map(max -> max.compareTo(new BigDecimal(version)) == 0)
        .orElse(false);
  }
}