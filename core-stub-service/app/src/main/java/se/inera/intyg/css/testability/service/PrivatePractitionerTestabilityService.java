package se.inera.intyg.css.testability.service;

import org.springframework.stereotype.Service;
import se.inera.intyg.css.infrastructure.persistence.PrivatePractitionerRepository;

@Service
public class PrivatePractitionerTestabilityService {

  private final PrivatePractitionerRepository privatePractitionerRepository;

  public PrivatePractitionerTestabilityService(
      PrivatePractitionerRepository privatePractitionerRepository) {
    this.privatePractitionerRepository = privatePractitionerRepository;
  }

  public boolean isCareProviderErased(String careProviderId) {
    return privatePractitionerRepository.isCareProviderErased(careProviderId);
  }

  public void clearErasedCareProviders() {
    privatePractitionerRepository.clearErasedCareProviders();
  }
}
