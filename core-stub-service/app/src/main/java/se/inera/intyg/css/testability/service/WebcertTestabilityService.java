package se.inera.intyg.css.testability.service;

import org.springframework.stereotype.Service;
import se.inera.intyg.css.infrastructure.persistence.WebcertRepository;

@Service
public class WebcertTestabilityService {

  private final WebcertRepository webcertRepository;

  public WebcertTestabilityService(WebcertRepository webcertRepository) {
    this.webcertRepository = webcertRepository;
  }

  public boolean isCareProviderErased(String careProviderId) {
    return webcertRepository.isCareProviderErased(careProviderId);
  }

  public void clearErasedCareProviders() {
    webcertRepository.clearErasedCareProviders();
  }
}
