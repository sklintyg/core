package se.inera.intyg.css.infrastructure.persistence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import org.springframework.stereotype.Repository;
import se.inera.intyg.css.application.dto.CertificateTextDTO;
import se.inera.intyg.css.application.dto.CertificateXmlDTO;

@Repository
public class IntygstjanstRepository {

  private final Map<String, List<CertificateXmlDTO>> certificates = new HashMap<>();
  @Getter
  private final List<CertificateTextDTO> certificateTexts = new ArrayList<>();
  private final List<String> erasedCareProvider = new ArrayList<>();

  public void store(String careProvider, List<CertificateXmlDTO> certificateXmlDTOList) {
    certificates.put(careProvider, certificateXmlDTOList);
  }

  public List<CertificateXmlDTO> get(String careProviderId, int collected, int batchSize) {
    return certificates.getOrDefault(careProviderId, Collections.emptyList()).stream()
        .skip(collected)
        .limit(batchSize)
        .sorted(Comparator.comparing(CertificateXmlDTO::id))
        .toList();
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

  public void eraseCareProvider(String careProviderId) {
    remove(careProviderId);
    erasedCareProvider.add(careProviderId);
  }

  public void clearErasedCareProviders() {
    erasedCareProvider.clear();
  }
}
