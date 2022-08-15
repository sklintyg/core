package se.inera.intyg.css.testability.service;

import org.springframework.stereotype.Service;
import se.inera.intyg.css.infrastructure.persistence.StatistikRepository;

@Service
public class StatistikTestabilityService {

  private final StatistikRepository statistikRepository;

  public StatistikTestabilityService(StatistikRepository statistikRepository) {
    this.statistikRepository = statistikRepository;
  }

  public boolean isCertificateErased(String certificateId) {
    return statistikRepository.isCertificateErased(certificateId);
  }

  public void clearErasedCertificates() {
    statistikRepository.clearErasedCertificateIds();
  }

  public boolean isCareProviderErased(String careProviderId) {
    return statistikRepository.isCareProviderErased(careProviderId);
  }

  public void clearErasedCareProviders() {
    statistikRepository.clearErasedCareProviders();
  }
}
