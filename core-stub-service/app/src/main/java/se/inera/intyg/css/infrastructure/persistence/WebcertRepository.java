package se.inera.intyg.css.infrastructure.persistence;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class WebcertRepository {

  private final List<String> erasedCareProviders = new ArrayList<>();

  public void eraseCareProvider(String careProviderId) {
    erasedCareProviders.add(careProviderId);
  }

  public void clearErasedCareProviders() {
    erasedCareProviders.clear();
  }

  public boolean isCareProviderErased(String careProviderId) {
    return erasedCareProviders.contains(careProviderId);
  }
}
