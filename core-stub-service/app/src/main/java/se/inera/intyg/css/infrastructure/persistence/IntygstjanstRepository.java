package se.inera.intyg.css.infrastructure.persistence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;
import se.inera.intyg.css.application.dto.CertificateTextDTO;
import se.inera.intyg.css.application.dto.CertificateXmlDTO;

@Repository
public class IntygstjanstRepository {

  private final Map<String, List<CertificateXmlDTO>> certificates = new HashMap<>();
  private final List<CertificateTextDTO> certificateTexts = new ArrayList<>();

  public void store(String careProvider, List<CertificateXmlDTO> certificateXmlDTOList) {
    certificates.put(careProvider, certificateXmlDTOList);
  }

  public List<CertificateXmlDTO> get(String careProvider, int limit, int offset) {
    return certificates.getOrDefault(careProvider, Collections.emptyList()).stream()
        .skip(offset)
        .limit(limit)
        .collect(Collectors.toList());
  }

  public void remove(String careProvider) {
    certificates.remove(careProvider);
  }

  public long count(String careProvider) {
    return certificates.getOrDefault(careProvider, Collections.emptyList()).size();
  }

  public long countRevoked(String careProvider) {
    return certificates.getOrDefault(careProvider, Collections.emptyList()).stream()
        .filter(CertificateXmlDTO::revoked)
        .count();
  }

  public void storeCertificateTexts(List<CertificateTextDTO> certificateTextDTOList) {
    certificateTexts.clear();
    certificateTexts.addAll(certificateTextDTOList);
  }

  public void removeCertificateTexts() {
    certificateTexts.clear();
  }

  public List<CertificateTextDTO> getCertificateTexts() {
    return certificateTexts;
  }
}
