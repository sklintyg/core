package se.inera.intyg.css.application.service;

import org.springframework.stereotype.Service;
import se.inera.intyg.css.infrastructure.persistence.WebcertRepository;

@Service
public class WebcertService {

  private final WebcertRepository webcertRepository;

  public WebcertService(WebcertRepository webcertRepository) {
    this.webcertRepository = webcertRepository;
  }

  public void eraseCareProvider(String careProviderId) {
    webcertRepository.eraseCareProvider(careProviderId);
  }
}
